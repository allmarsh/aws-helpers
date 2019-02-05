package net.allmarsh.awshelpers.utils;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.cli.Args;
import net.allmarsh.awshelpers.models.S3ListSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class S3KeyRandomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3KeyRandomizer.class);

    public List<S3ObjectSummary> generateRandomizedKeys(final Args args) {

        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(args.getInputRegion())
                .build();

        final ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(args.getInput().getBucketName())
                .withPrefix(args.getInput().getPrefix());

        final S3ListSummary s3ListSummary = generateS3ListSummary(s3Client, req);

        final List<S3ObjectSummary> s3Objects = new ArrayList<>();
        final List<Integer> filterIndexes = generateRandomNumbers(0, s3ListSummary.getNumObjects(), args.getNumFiles());

        int objectCounter = 0;
        ListObjectsV2Result listResult;

        do {
            listResult = s3Client.listObjectsV2(req);
            final List<S3ObjectSummary> currentList = listResult.getObjectSummaries();
            final int objCount = objectCounter;
            s3Objects.addAll(
                    IntStream.range(0, currentList.size() - 1)
                            .filter(ind -> filterIndexes.contains(ind + objCount))
                            .mapToObj(currentList::get)
                            .collect(Collectors.toList())
            );
            objectCounter += listResult.getKeyCount();
            final String token = listResult.getNextContinuationToken();
            LOGGER.debug("Setting next cont token: " + token);
            req.setContinuationToken(token);
        } while (listResult.isTruncated());

        return s3Objects;

    }

    // TODO : Make sure not changing param value
    S3ListSummary generateS3ListSummary(final AmazonS3 s3Client, final ListObjectsV2Request req) {

        ListObjectsV2Result listResult;

        int numContinuationTokens = 0;
        int numS3Objects = 0;

        do {
            listResult = s3Client.listObjectsV2(req);
            numS3Objects += listResult.getKeyCount();
            numContinuationTokens += 1;
            final String token = listResult.getNextContinuationToken();
            LOGGER.debug("Setting next cont token: " + token);
            req.setContinuationToken(token);
        } while (listResult.isTruncated());

        return new S3ListSummary(numS3Objects, numContinuationTokens);

    }

    List<Integer> generateRandomNumbers(final int rangeStart, final int rangeEnd, final int numberRandom) {

        if (numberRandom > (rangeEnd - rangeStart)) {
            throw new ParameterException(String.format("Not a valid input." +
                    "Range of %s to %s does not meet required range of %s", rangeStart, rangeEnd, numberRandom));
        }
        return new Random()
                .ints(rangeStart, rangeEnd)
                .distinct()
                .limit(numberRandom)
                .boxed()
                .collect(Collectors.toList());
    }


    void awsS3Regionless(final Args args) {

        final ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(args.getInput().getBucketName())
                .withPrefix(args.getInput().getPrefix());

        for (final Regions region : Regions.values()) {
            final ListObjectsV2Result listResult = AmazonS3ClientBuilder.standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withRegion(region)
                    .build()
                    .listObjectsV2(req);
        }
    }


}

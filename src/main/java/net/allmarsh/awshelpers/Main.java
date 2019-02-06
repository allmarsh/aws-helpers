package net.allmarsh.awshelpers;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beust.jcommander.JCommander;
import net.allmarsh.awshelpers.cli.Args;
import net.allmarsh.awshelpers.models.S3ListSummary;
import net.allmarsh.awshelpers.utils.S3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String... args) {

        final Args mainArgs = new Args();
        JCommander.newBuilder()
                .addObject(mainArgs)
                .build()
                .parse(args);

        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(mainArgs.getInputRegion())
                .build();

        final ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(mainArgs.getInput().getBucketName())
                .withPrefix(mainArgs.getInput().getPrefix());

        // Get a summary of the list call - including the total number of objects
        final S3ListSummary s3ListSummary = S3Utils.generateS3ListSummary(s3Client, req);
        // Generate X random numbers within the total object range
        final List<Integer> filterIndexes = S3Utils.generateRandomNumbers(0, s3ListSummary.getNumObjects(), mainArgs.getNumFiles());
        // Get the random objects
        final List<S3ObjectSummary> keys = S3Utils.generateFilteredIndexedSummaries(s3Client, req, filterIndexes);
        // TODO: Copy random objects to output location


    }


}
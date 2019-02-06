package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.models.S3ListSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class S3Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Utils.class);

    /**
     * Generates a filtered list of S3ObjectSummary objects that corresponds to the provided @filteredIndexes.
     *
     * @param s3Client      S3 Client
     * @param req           Request containing bucket and prefix information
     * @param filterIndexes List of indexes that correspond to the provided request.
     * @return A list of filtered S3ObjectSummary objects. Only the indexed summaries corresponding to the
     * filterIndexes will be returned.
     */
    public static List<S3ObjectSummary> generateFilteredIndexedSummaries(
            final AmazonS3 s3Client,
            final ListObjectsV2Request req,
            final List<Integer> filterIndexes
    ) {

        final List<S3ObjectSummary> s3Objects = new ArrayList<>();
        int objectCounter = 0;
        ListObjectsV2Result listResult;

        do {
            listResult = s3Client.listObjectsV2(req);
            final List<S3ObjectSummary> currentList = listResult.getObjectSummaries();
            final int objCount = objectCounter;
            s3Objects.addAll(
                    IntStream.range(0, currentList.size())
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

    /**
     * Generates a summary of a listObjectsV2 call. Not just the first X objects.
     *
     * @param s3Client S3 Client providing region information
     * @param req      Request providing bucket adn key information
     * @return A S3ListSummary object providing total s3 objects and the number of continuation tokens (windows)
     */
    public static S3ListSummary generateS3ListSummary(final AmazonS3 s3Client, final ListObjectsV2Request req) {

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

    /**
     * Generates a random list of size @numberRandom of Integers in the range between @rangeStart and @rangeEnd
     *
     * @param rangeStart   Inclusive range start number
     * @param rangeEnd     Exclusive range end number
     * @param numberRandom Number of random numbers
     * @return A list of random numbers in the provided range.
     * @throws ParameterException when the requested number exceeds the range.
     */
    public static List<Integer> generateRandomNumbers(final int rangeStart, final int rangeEnd, final int numberRandom) {

        if (numberRandom > (rangeEnd - rangeStart)) {
            throw new ParameterException(String.format("Not a valid input." +
                    "Range of %s to %s does not meet required range of %s", rangeStart, rangeEnd, numberRandom));
        }
        return new Random()
                .ints(rangeStart, rangeEnd - 1)
                .distinct()
                .limit(numberRandom)
                .boxed()
                .collect(Collectors.toList());
    }


}

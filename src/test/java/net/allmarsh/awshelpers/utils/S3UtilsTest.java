package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.models.S3ListSummary;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;

public class S3UtilsTest {

    @Test
    public void testGenerateRandomNumbers() {
        final int testStart = 0;
        final int testEnd = 10;
        final int testNum = 3;
        final List<Integer> randos = S3Utils.generateRandomNumbers(testStart, testEnd, testNum);
        randos.forEach(
                r -> Assert.assertTrue(String.format("Validating random number %s is in range %s and %s", r, testStart, testEnd),
                        r >= testStart && r < testEnd)
        );
        Assert.assertEquals(
                "Validating correct number of random numbers returned",
                testNum,
                randos.size()
        );
    }

    @Test(expected = ParameterException.class)
    public void testGenerateRandomNumbersInvalidRange() {
        S3Utils.generateRandomNumbers(0, 2, 3);
    }


    @Test
    public void testGenerateS3ListSummary() {

        final int keyCount1 = 10;
        final int keyCount2 = 5;

        final ListObjectsV2Result result1 = new ListObjectsV2Result();
        result1.setKeyCount(keyCount1);
        result1.setContinuationToken("");
        result1.setTruncated(true);

        final ListObjectsV2Result result2 = new ListObjectsV2Result();
        result2.setKeyCount(keyCount2);
        result2.setContinuationToken("");
        result2.setTruncated(false);

        final ListObjectsV2Request req = new ListObjectsV2Request();

        final AmazonS3 mockS3 = mock(AmazonS3.class);
        when(mockS3.listObjectsV2(req))
                .thenReturn(result1)
                .thenReturn(result2);

        final S3ListSummary listSummary = S3Utils.generateS3ListSummary(mockS3, req);

        Assert.assertEquals("Validating all keys counted",
                keyCount1 + keyCount2,
                listSummary.getNumObjects());
        Assert.assertEquals("Validating num continuation tokens",
                2,
                listSummary.getNumContinuationTokens());
    }


    @Test
    public void testGenerateRandomizedKeys() {

        // Indices of summaries
        final List<Integer> indexList = Arrays.asList(0, 1, 5);

        final S3ObjectSummary summary0 = createObjectSummary("0", "0");
        final S3ObjectSummary summary1 = createObjectSummary("1", "1");
        final S3ObjectSummary summary5 = createObjectSummary("5", "5");
        final List<S3ObjectSummary> expectedSummaries = Arrays.asList(summary0, summary1, summary5);
        final S3ObjectSummary summaryGarbage = createObjectSummary("garbage", "garbage");

        final ListObjectsV2Result result1 = new ListObjectsV2Result();
        final List<S3ObjectSummary> summaries1 = Arrays.asList(
                summary0, summary1, summaryGarbage, summaryGarbage
        );
        Whitebox.setInternalState(result1, "objectSummaries", summaries1);
        result1.setKeyCount(summaries1.size());
        result1.setTruncated(true);

        final ListObjectsV2Result result2 = new ListObjectsV2Result();
        final List<S3ObjectSummary> summaries2 = Arrays.asList(
                summaryGarbage, summary5
        );
        Whitebox.setInternalState(result2, "objectSummaries", summaries2);
        result2.setContinuationToken("");
        result2.setKeyCount(summaries2.size());
        result2.setTruncated(false);

        final ListObjectsV2Request req = new ListObjectsV2Request();

        final AmazonS3 mockS3 = mock(AmazonS3.class);
        when(mockS3.listObjectsV2(req))
                .thenReturn(result1)
                .thenReturn(result2);

        final List<S3ObjectSummary> summaryList = S3Utils.generateFilteredIndexedSummaries(mockS3, req, indexList);

        Assert.assertArrayEquals(
                "Expecting the corresponding object summaries to the provided index list",
                expectedSummaries.toArray(),
                summaryList.toArray()
        );
    }

    private S3ObjectSummary createObjectSummary(final String bucket, final String key) {
        final S3ObjectSummary summary = new S3ObjectSummary();
        summary.setBucketName(bucket);
        summary.setKey(key);
        return summary;
    }

}

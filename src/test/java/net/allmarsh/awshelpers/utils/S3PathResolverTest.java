package net.allmarsh.awshelpers.utils;


import com.amazonaws.services.macie.model.S3Resource;
import org.junit.Assert;
import org.junit.Test;

public class S3PathResolverTest {

    private static final String TEST_BUCKET = "test-bucket";
    private static final String TEST_PREFIX = "key1/key2/key3";

    @Test
    public void testParseResourceFromStringConsolePath() {
        Assert.assertEquals(
                "Testing s3 web path",
                new S3Resource().withBucketName(TEST_BUCKET).withPrefix(TEST_PREFIX + "/"),
                S3PathResolver.parseResourceFromString("https://s3.console.aws.amazon.com/s3/buckets/" + TEST_BUCKET + "/" +  TEST_PREFIX + "/?region=us-east-1&tab=overview"));
    }

    @Test
    public void testParseResourceFromStringCliPath() {
        Assert.assertEquals(
                "Testing s3 cli path",
                new S3Resource().withBucketName(TEST_BUCKET).withPrefix(TEST_PREFIX + "/"),
                S3PathResolver.parseResourceFromString("s3://" + TEST_BUCKET + "/" +  TEST_PREFIX + "/"));
    }

    @Test
    public void testParseResourceFromStringArnPath() {
        Assert.assertEquals(
                "Testing s3 arn path",
                new S3Resource().withBucketName(TEST_BUCKET).withPrefix(TEST_PREFIX + "/"),
                S3PathResolver.parseResourceFromString("arn:aws:s3:::" + TEST_BUCKET + "/" +  TEST_PREFIX + "/"));
    }

    @Test
    public void testParseResourceFromStringInvalidPath() {

        Assert.assertEquals(
                "Testing invalid s3 path",
                new S3Resource(),
                S3PathResolver.parseResourceFromString(TEST_BUCKET + TEST_PREFIX));

    }

}

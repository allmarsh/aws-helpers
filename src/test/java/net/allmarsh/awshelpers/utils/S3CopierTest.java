package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.macie.model.S3Resource;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Arrays;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class S3CopierTest {

    @Test
    public void testCopyObjects() {
        final AmazonS3 mockS3Client = mock(AmazonS3.class);
        when(mockS3Client.copyObject(Matchers.any(CopyObjectRequest.class)))
                .thenReturn(new CopyObjectResult());

        final List<S3ObjectSummary> s3Objects = Arrays.asList(
                new S3ObjectSummary(), new S3ObjectSummary(), new S3ObjectSummary()
        );

        final S3Resource dest = new S3Resource().withBucketName("bucket").withPrefix("prefix");

        final List<CopyObjectResult> copyResults = S3Copier.copyObjects(
                mockS3Client,
                s3Objects,
                dest
        );

        Assert.assertEquals(
                "Validating number of requests returned to results",
                s3Objects.size(),
                copyResults.size()
        );
    }


}

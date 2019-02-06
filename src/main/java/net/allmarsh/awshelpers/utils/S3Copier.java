package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.macie.model.S3Resource;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;
import java.util.stream.Collectors;

public class S3Copier {

    public static List<CopyObjectResult> copyObjects(final AmazonS3 s3Client,
                                              final List<S3ObjectSummary> s3Objects,
                                              final S3Resource destinationPath) {
        return s3Objects
                .stream()
                .map(obj -> createCopyRequest(obj, destinationPath))
                .map(s3Client::copyObject)
                .collect(Collectors.toList());
    }

    private static CopyObjectRequest createCopyRequest(final S3ObjectSummary input, final S3Resource output) {
        final String destinationKey = output.getPrefix() + input.getKey();
        return new CopyObjectRequest(
                input.getBucketName(),
                input.getKey(),
                output.getBucketName(),
                destinationKey
        );

    }

}

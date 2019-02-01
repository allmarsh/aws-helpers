package net.allmarsh.awshelpers.cli;


import com.amazonaws.services.macie.model.S3Resource;
import com.beust.jcommander.IStringConverter;


/**
 * Converts a String to a S3Resource object with
 */
public class StringToS3PathConverter implements IStringConverter<S3Resource> {
    @Override
    public S3Resource convert(final String value) {
        return new S3Resource()
                .withBucketName(ParseBucket(value))
                .withPrefix(ParseKey(value));
    }

    private String ParseBucket(final String s3Path){

        return "";
    }

    private String ParseKey(final String s3Path){

        return "";
    }

}
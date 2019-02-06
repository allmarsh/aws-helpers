package net.allmarsh.awshelpers.cli.converters;


import com.amazonaws.services.macie.model.S3Resource;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.utils.S3PathResolver;

import java.util.Objects;


/**
 * Converts a String to a S3Resource object
 */
public class StringToS3PathConverter implements IStringConverter<S3Resource> {

    /**
     * Converts a valid String to a S3Resource
     * e.g. s3://bucket/key -> S3Resource(bucket=bucket, key=key)
     *
     * @param value Input String
     * @return S3Resource object given valid input
     * @throws ParameterException when an invalid input is provided
     */
    @Override
    public S3Resource convert(final String value) {
        final S3Resource resource = S3PathResolver.parseResourceFromString(value);
        if (Objects.isNull(resource.getBucketName())) {
            throw new ParameterException(String.format("Input %s is not a valid s3 path.", value));
        }
        return resource;
    }

}
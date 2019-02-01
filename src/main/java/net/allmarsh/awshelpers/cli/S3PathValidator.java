package net.allmarsh.awshelpers.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.utils.S3PathResolver;

public class S3PathValidator implements IParameterValidator {

    public void validate(final String name, final String value)
            throws ParameterException {

        if (!S3PathResolver.isValidPrefix(value)) {
            throw new ParameterException(
                    String.format("Parameter %s must start with one of the following s3 prefixes: %s.\nFound: %s",
                            name, S3PathResolver.S3_PREFIXES.toString(), value)
            );
        }
    }
}
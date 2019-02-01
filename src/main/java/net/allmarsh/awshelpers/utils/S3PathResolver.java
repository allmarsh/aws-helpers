package net.allmarsh.awshelpers.utils;


import com.amazonaws.services.macie.model.S3Resource;
import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class S3PathResolver {

    public static final String S3_PREFIX_ARN = "arn:aws:s3:::";
    public static final String S3_PREFIX_CLI = "s3://";
    public static final String S3_PREFIX_WEB = "https://s3.console.aws.amazon.com/s3/buckets/";

    public static final List<String> S3_PREFIXES = Arrays.asList(S3_PREFIX_ARN, S3_PREFIX_CLI, S3_PREFIX_WEB);

    /**
     * Determines if input string is a valid s3 path
     *
     * @param input Input String
     * @return Returns true when input string is a valid s3 prefix. False otherwise.
     */
    public static boolean isValidPrefix(final String input) {
        return S3_PREFIXES.stream()
                .anyMatch(input::startsWith);
    }

    /**
     * @param input
     * @return
     */
    public static String stripPrefixFromPath(final String input) {
        return S3_PREFIXES.stream()
                .map(prefix -> StringUtils.removeStart(input, prefix)) // Returns input string if not found
                .filter(entry -> !entry.equals(input))
                .findFirst()
                .orElse(input);
    }


}

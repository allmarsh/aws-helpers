package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.macie.model.S3Resource;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3PathResolver {

    private static final String S3_REGEX_ARN = "^(arn:aws:s3:::)([^\\/]*)(.*)$";
    private static final String S3_REGEX_CLI = "^(s3:\\/\\/)([^\\/]*)(.*)$";
    private static final String S3_REGEX_WEB = "^(https:\\/\\/s3\\.console\\.aws\\.amazon\\.com\\/s3\\/buckets\\/)([^\\/]*)([^?]*)";

    private static final List<String> S3_REGEXES = Arrays.asList(S3_REGEX_ARN, S3_REGEX_CLI, S3_REGEX_WEB);

    /**
     * Converts a String of an s3 path to an S3Resource with populated bucket and prefix fields.
     *
     * @param input Input String
     * @return Returns an S3Resource with populated bucket and prefix field if given a valid input.
     * Otherwise returns an empty S3Resource.
     */
    public static S3Resource parseResourceFromString(final String input) {

        return S3_REGEXES.stream()
                .map(Pattern::compile)
                .map(p -> p.matcher(input))
                .filter(Matcher::find)
                .filter(m -> m.groupCount() == 3)
                .map(groups -> new S3Resource().withBucketName(groups.group(2)).withPrefix(formatKey(groups.group(3))))
                .findFirst()
                .orElse(new S3Resource());

    }

    private static String formatKey(final String key) {
        return formatKeyEnd(formatKeyStart(key));
    }

    private static String formatKeyEnd(final String key) {
        return !key.endsWith("/")
                ? key + "/"
                : key;
    }

    private static String formatKeyStart(final String key) {
        return key.startsWith("/")
                ? key.substring(1)
                : key;
    }

}

package net.allmarsh.awshelpers.utils;

import com.amazonaws.services.macie.model.S3Resource;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3PathResolver {

//    public static final String S3_PREFIX_ARN = "arn:aws:s3:::";
//    public static final String S3_PREFIX_CLI = "s3://";
//    public static final String S3_PREFIX_WEB = "https://s3.console.aws.amazon.com/s3/buckets/";

    public static final String S3_REGEX_ARN = "^(arn:aws:s3:::)([^\\/]*)(.*)$";
    public static final String S3_REGEX_CLI = "^(s3:\\/\\/)([^\\/]*)(.*)$";
    public static final String S3_REGEX_WEB = "^(https:\\/\\/s3\\.console\\.aws\\.amazon\\.com\\/s3\\/buckets\\/)([^\\/]*)([^?]*)";

    //    public static final List<String> S3_PREFIXES = Arrays.asList(S3_PREFIX_ARN, S3_PREFIX_CLI, S3_PREFIX_WEB);
    public static final List<String> S3_REGEXES = Arrays.asList(S3_REGEX_ARN, S3_REGEX_CLI, S3_REGEX_WEB);

    /**
     * Determines if input string is a valid s3 path
     *
     * @param input Input String
     * @return Returns true when input string is a valid s3 prefix. False otherwise.
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

    static String formatKey(final String key) {
        return formatKeyEnd(formatKeyStart(key));
    }

    static String formatKeyEnd(final String key) {
        return !key.endsWith("/")
                ? key + "/"
                : key;
    }

    static String formatKeyStart(final String key) {
        return key.startsWith("/")
                ? key.substring(1)
                : key;
    }

}

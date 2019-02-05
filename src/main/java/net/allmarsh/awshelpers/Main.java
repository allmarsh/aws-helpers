package net.allmarsh.awshelpers;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.cli.Args;
import net.allmarsh.awshelpers.models.S3ListSummary;
import net.allmarsh.awshelpers.utils.S3KeyRandomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(final String... args) {

        final Args mainArgs = new Args();
        JCommander.newBuilder()
                .addObject(mainArgs)
                .build()
                .parse(args);
        final List<S3ObjectSummary> keys = new S3KeyRandomizer().generateRandomizedKeys(mainArgs);
        keys.forEach(key -> LOGGER.info(key.toString()));
        keys.forEach(System.out::println);
    }

}
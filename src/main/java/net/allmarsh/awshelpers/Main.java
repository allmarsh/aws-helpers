package net.allmarsh.awshelpers;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {

        final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
//                .withRegion(clientRegion)
                .build();


        final String bucket = "";
        final String prefix = "";

        final ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(bucket)
                .withPrefix(prefix);
//                .withMaxKeys();

        final ListObjectsV2Result listResult = s3Client.listObjectsV2(bucket, prefix);

    }




}
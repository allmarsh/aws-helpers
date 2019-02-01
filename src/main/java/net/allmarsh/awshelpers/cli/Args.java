package net.allmarsh.awshelpers.cli;

import com.amazonaws.services.snowball.model.S3Resource;
import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = {"-i", "--input"},
            description = "S3 input path. Either s3:// or https://console...",
            validateWith = S3PathValidator.class,
            converter = StringToS3PathConverter.class)
    private S3Resource input;

    @Parameter(names = {"-o", "--output"},
            description = "S3 output path. Either s3:// or https://console...",
            validateWith = S3PathValidator.class,
            converter = StringToS3PathConverter.class)
    private S3Resource output;

}
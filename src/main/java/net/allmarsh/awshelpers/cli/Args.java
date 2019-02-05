package net.allmarsh.awshelpers.cli;


import com.amazonaws.services.macie.model.S3Resource;
import com.beust.jcommander.Parameter;
import net.allmarsh.awshelpers.cli.converters.StringToRegionConverter;
import net.allmarsh.awshelpers.cli.converters.StringToS3PathConverter;
import com.amazonaws.regions.Regions;

public class Args {

    @Parameter(names = {"-i", "--input"},
            description = "S3 input path. Either s3:// or arn:::... or https://console...",
            converter = StringToS3PathConverter.class,
            required = true)
    private S3Resource input;

    @Parameter(names = {"-ir", "--input-region"},
            description = "AWS region for input - e.g. us-west-2",
            converter = StringToRegionConverter.class,
            required = true)
    private Regions inputRegion;

    @Parameter(names = {"-o", "--output"},
            description = "S3 output path. Either s3:// or arn:::... or https://console...",
            converter = StringToS3PathConverter.class,
            required = true)
    private S3Resource output;

    @Parameter(names = {"-or", "--output-region"},
            description = "AWS region for output - e.g. us-west-2",
            converter = StringToRegionConverter.class,
            required = true)
    private Regions outputRegion;

    @Parameter(names = {"-nf", "--num-files"},
            description = "Number of files desired",
            required = true)
    private int numFiles;

    public S3Resource getInput() {
        return input;
    }

    public void setInput(S3Resource input) {
        this.input = input;
    }

    public Regions getInputRegion() {
        return inputRegion;
    }

    public void setInputRegion(Regions inputRegion) {
        this.inputRegion = inputRegion;
    }

    public S3Resource getOutput() {
        return output;
    }

    public void setOutput(S3Resource output) {
        this.output = output;
    }

    public Regions getOutputRegion() {
        return outputRegion;
    }

    public void setOutputRegion(Regions outputRegion) {
        this.outputRegion = outputRegion;
    }

    public int getNumFiles() {
        return numFiles;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }
}
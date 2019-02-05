package net.allmarsh.awshelpers.models;

public class S3ListSummary {


    private int numObjects;
    private int numContinuationTokens;

    public S3ListSummary(final int numObjects, final int numContinuationTokens) {
        this.numObjects = numObjects;
        this.numContinuationTokens = numContinuationTokens;
    }

    public int getNumObjects() {
        return numObjects;
    }

    public int getNumContinuationTokens() {
        return numContinuationTokens;
    }

}

package net.allmarsh.awshelpers.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.macie.model.S3Resource;
import net.allmarsh.awshelpers.cli.Args;
import org.junit.Test;

public class S3KeyRandomizerTest {

    @Test
    public void testGenerateRandomizedKeys(){

        final int expectedNumFiles = 3;
        final Regions expectedRegions = Regions.US_WEST_2;

        final Args testArgs = new Args();
        testArgs.setNumFiles(expectedNumFiles);
        testArgs.setInput(new S3Resource());
        testArgs.setInputRegion(expectedRegions);



    }

}

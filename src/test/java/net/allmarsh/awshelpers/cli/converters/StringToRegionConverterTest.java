package net.allmarsh.awshelpers.cli.converters;

import com.amazonaws.regions.Regions;
import com.beust.jcommander.ParameterException;
import org.junit.Assert;
import org.junit.Test;

public class StringToRegionConverterTest {

    @Test
    public void testConvertValid() {
        Assert.assertEquals(
                "Validating String to Regions conversion",
                Regions.US_EAST_1,
                new StringToRegionConverter().convert("us-east-1")
        );
    }

    @Test(expected = ParameterException.class)
    public void testConvertInvalid() {
        new StringToRegionConverter().convert("invalid-region");
    }


}

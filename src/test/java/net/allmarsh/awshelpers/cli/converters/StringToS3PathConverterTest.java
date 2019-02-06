package net.allmarsh.awshelpers.cli.converters;

import com.amazonaws.services.macie.model.S3Resource;
import com.beust.jcommander.ParameterException;
import net.allmarsh.awshelpers.utils.S3PathResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(S3PathResolver.class)
public class StringToS3PathConverterTest {

    private static final String VALID_STRING = "valid-string";
    private static final String INVALID_STRING = "invalid-string";

    private static final S3Resource VALID_RESOURCE = new S3Resource().withBucketName(VALID_STRING).withPrefix(VALID_STRING);
    private static final S3Resource INVALID_RESOURCE = new S3Resource();

    @Before
    public void setup() {
        PowerMockito.mockStatic(S3PathResolver.class);
        PowerMockito.when(S3PathResolver.parseResourceFromString(VALID_STRING))
                .thenReturn(VALID_RESOURCE);
        PowerMockito.when(S3PathResolver.parseResourceFromString(INVALID_STRING))
                .thenReturn(INVALID_RESOURCE);
    }

    @Test
    public void testConvertValid() {
        Assert.assertEquals(
                "Testing valid input",
                VALID_RESOURCE,
                new StringToS3PathConverter().convert(VALID_STRING)
        );
    }

    @Test(expected = ParameterException.class)
    public void testConvertInvalid() {
        new StringToS3PathConverter().convert(INVALID_STRING);
    }

}

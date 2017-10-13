
package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

public class QualityTest {

    private static final String VALUES = StringUtils.toString(' ', Quality.values());

    private static final Logger LOGGER = LoggerFactory.getLogger(QualityTest.class, BUNDLE_NAME);

    @Test
    public void test() throws UnsupportedQualityException {
        assertEquals(Quality.COLOR, Quality.parse("color"));
        assertEquals(Quality.DEFAULT, Quality.parse("default"));
        assertEquals(Quality.BITONAL, Quality.parse("bitonal"));
        assertEquals(Quality.GRAY, Quality.parse("gray"));
    }

    @Test(expected = UnsupportedQualityException.class)
    public void testException() throws UnsupportedQualityException {
        final String value = "purple";

        try {
            Quality.parse(value);
        } catch (final UnsupportedQualityException details) {
            final String expected = LOGGER.getMessage(MessageCodes.EXC_012, value, Quality.values().length, VALUES);
            assertEquals(expected, details.getMessage());
            throw details;
        }
    }

}

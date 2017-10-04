
package info.freelibrary.iiif.image;

import static info.freelibrary.iiif.image.Constants.BUNDLE_NAME;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.Quality;
import info.freelibrary.iiif.image.UnsupportedQualityException;
import info.freelibrary.iiif.image.util.TestConstants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * An exception thrown when the supplied quality is not a valid IIIF quality.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class UnsupportedQualityExceptionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnsupportedQualityExceptionTest.class, BUNDLE_NAME);

    private static final String VALUES = StringUtils.toString(' ', Quality.values());

    private Locale myDefaultLocale;

    /**
     * Sets up the tests of <code>UnsupportedQualityException</code>.
     *
     * @throws Exception If the test set up fails
     */
    @Before
    public void setUp() throws Exception {
        myDefaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    /**
     * Tears down the tests of <code>UnsupportedQualityException</code>.
     *
     * @throws Exception If the test tear down fails
     */
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(myDefaultLocale);
    }

    @Test
    public void testUnsupportedQualityExceptionString() {
        try {
            throw new UnsupportedQualityException(MessageCodes.TEST_001);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    /**
     * Tests the exception thrown when <code>ImageQuality</code> is initialized with bad data.
     */
    @Test
    public void testUnsupportedQualityExceptionStringObjectArray() {
        final String value = "bad";

        try {
            Quality.parse(value);
        } catch (final UnsupportedQualityException details) {
            final String expected = LOGGER.getMessage(MessageCodes.EXC_012, value, Quality.values().length, VALUES);
            assertEquals(expected, details.getMessage());
        }
    }

    @Test
    public void testUnsupportedQualityExceptionLocaleString() {
        try {
            throw new UnsupportedQualityException(Locale.US, MessageCodes.TEST_001);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testLocaleStringVarargs() {
        try {
            throw new UnsupportedQualityException(Locale.US, MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test(expected = UnsupportedQualityException.class)
    public void testThrowable() throws UnsupportedQualityException {
        throw new UnsupportedQualityException(new Exception());
    }

    @Test
    public void testThrowableString() {
        try {
            throw new UnsupportedQualityException(new Exception(), MessageCodes.TEST_001);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testThrowableLocaleString() {
        try {
            throw new UnsupportedQualityException(new Exception(), Locale.US, MessageCodes.TEST_001);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testThrowableStringVargs() {
        try {
            throw new UnsupportedQualityException(new Exception(), MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testThrowableLocaleStringVargs() {
        try {
            throw new UnsupportedQualityException(new Exception(), Locale.US, MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final UnsupportedQualityException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

}


package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.iiif.image.util.TestConstants;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * An exception thrown when the supplied format is not a valid IIIF format.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class UnsupportedFormatExceptionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnsupportedFormatExceptionTest.class, BUNDLE_NAME);

    private static final String VALUES = StringUtils.toString(' ', Format.values());

    private Locale myDefaultLocale;

    /**
     * Sets up the tests of <code>UnsupportedFormatException</code>.
     *
     * @throws Exception If the test set up fails
     */
    @Before
    public void setUp() throws Exception {
        myDefaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    /**
     * Tears down the tests of <code>UnsupportedFormatException</code>.
     *
     * @throws Exception If the test tear down fails
     */
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(myDefaultLocale);
    }

    @Test
    public void testString() {
        try {
            throw new UnsupportedFormatException(MessageCodes.TEST_001);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testLocaleString() {
        try {
            throw new UnsupportedFormatException(Locale.US, MessageCodes.TEST_001);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testLocaleStringVarargs() {
        try {
            throw new UnsupportedFormatException(Locale.US, MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test(expected = UnsupportedFormatException.class)
    public void testThrowable() throws UnsupportedFormatException {
        throw new UnsupportedFormatException(new Exception());
    }

    @Test
    public void testThrowableString() {
        try {
            throw new UnsupportedFormatException(new Exception(), MessageCodes.TEST_001);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testThrowableStringVarargs() {
        try {
            throw new UnsupportedFormatException(new Exception(), MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testThrowableLocaleString() {
        try {
            throw new UnsupportedFormatException(new Exception(), Locale.US, MessageCodes.TEST_001);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testThrowableLocaleStringVarargs() {
        try {
            throw new UnsupportedFormatException(new Exception(), Locale.US, MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final UnsupportedFormatException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    /**
     * Tests the exception thrown when <code>ImageFormat</code> is initialized with bad data.
     */
    @Test
    public void testUnsupportedFormatException() {
        final String value = "doc";

        try {
            Format.parseExtension(value);
        } catch (final UnsupportedFormatException details) {
            final String expected = LOGGER.getMessage(MessageCodes.EXC_012, value, Format.values().length, VALUES);
            assertEquals(expected, details.getMessage());
        }
    }

}

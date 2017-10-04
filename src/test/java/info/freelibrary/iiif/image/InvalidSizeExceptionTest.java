
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.util.TestConstants;

/**
 * A test of {@link info.freelibrary.jiiify.iiif.InvalidSizeException}.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class InvalidSizeExceptionTest {

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
    public void testInvalidSizeExceptionString() {
        try {
            throw new InvalidSizeException(MessageCodes.TEST_001);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionStringObjectArray() {
        try {
            throw new InvalidSizeException(MessageCodes.TEST_002, TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionLocaleString() {
        try {
            throw new InvalidSizeException(Locale.US, MessageCodes.TEST_001);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionLocaleStringObjectArray() {
        try {
            throw new InvalidSizeException(Locale.US, MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test(expected = InvalidSizeException.class)
    public void testInvalidSizeExceptionThrowable() throws InvalidSizeException {
        throw new InvalidSizeException(new Exception());
    }

    @Test
    public void testInvalidSizeExceptionThrowableString() {
        try {
            throw new InvalidSizeException(new Exception(), MessageCodes.TEST_001);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionThrowableLocaleString() {
        try {
            throw new InvalidSizeException(new Exception(), Locale.US, MessageCodes.TEST_001);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionThrowableStringObjectArray() {
        try {
            throw new InvalidSizeException(new Exception(), MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testInvalidSizeExceptionThrowableLocaleStringObjectArray() {
        try {
            throw new InvalidSizeException(new Exception(), Locale.US, MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final InvalidSizeException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

}


package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.iiif.image.util.TestConstants;

public class InvalidInfoExceptionTest {

    private Locale myDefaultLocale;

    /**
     * Sets up the tests of <code>InvalidInfoException</code>.
     *
     * @throws Exception If the test set up fails
     */
    @Before
    public void setUp() throws Exception {
        myDefaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    /**
     * Tears down the tests of <code>InvalidInfoException</code>.
     *
     * @throws Exception If the test tear down fails
     */
    @After
    public void tearDown() throws Exception {
        Locale.setDefault(myDefaultLocale);
    }

    @Test
    public void testInvalidInfoExceptionString() {
        try {
            throw new InvalidInfoException(MessageCodes.TEST_001);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionStringObjectArray() {
        try {
            throw new InvalidInfoException(MessageCodes.TEST_002, TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionLocaleString() {
        try {
            throw new InvalidInfoException(Locale.US, MessageCodes.TEST_001);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionLocaleStringObjectArray() {
        try {
            throw new InvalidInfoException(Locale.US, MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test(expected = InvalidInfoException.class)
    public void testInvalidInfoExceptionThrowable() throws InvalidInfoException {
        throw new InvalidInfoException(new Exception());
    }

    @Test
    public void testInvalidInfoExceptionThrowableString() {
        try {
            throw new InvalidInfoException(new Exception(), MessageCodes.TEST_001);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionThrowableLocaleString() {
        try {
            throw new InvalidInfoException(new Exception(), Locale.US, MessageCodes.TEST_001);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionThrowableStringObjectArray() {
        try {
            throw new InvalidInfoException(new Exception(), MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testInvalidInfoExceptionThrowableLocaleStringObjectArray() {
        try {
            throw new InvalidInfoException(new Exception(), Locale.US, MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final InvalidInfoException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

}

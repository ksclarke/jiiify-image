
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.util.TestConstants;

/**
 * A test of {@link info.freelibrary.ImageException.iiif.IIIFImageException}
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class ImageExceptionTest {

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
    public void testIIIFImageExceptionString() {
        try {
            new ImageException(TestConstants.BAD_MSG_KEY);
            fail("Failed to notice a bad message key was supplied");
        } catch (final MissingResourceException details) {
            // This exception is expected...
        }

        try {
            new ImageException(TestConstants.EMPTY_STRING);
            fail("Could not create an exception without a message");
        } catch (final MissingResourceException details) {
            // This exception is expected...
        }
    }

    @Test
    public void testIIIFImageExceptionStringObjectArray() {
        try {
            throw new ImageException(MessageCodes.TEST_002, TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testIIIFImageExceptionLocaleString() {
        try {
            throw new ImageException(Locale.US, MessageCodes.TEST_001);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testIIIFImageExceptionLocaleStringObjectArray() {
        try {
            throw new ImageException(Locale.US, MessageCodes.TEST_002, TestConstants.DETAIL_ONE,
                    TestConstants.DETAIL_TWO);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test(expected = ImageException.class)
    public void testIIIFImageExceptionThrowable() throws ImageException {
        throw new ImageException(new IOException(TestConstants.TEST_MSG));
    }

    @Test
    public void testIIIFImageExceptionThrowableString() {
        try {
            throw new ImageException(new IOException(TestConstants.TEST_MSG), MessageCodes.TEST_001);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testIIIFImageExceptionThrowableLocaleString() {
        try {
            throw new ImageException(new IOException(TestConstants.TEST_MSG), Locale.US, MessageCodes.TEST_001);
        } catch (final Exception details) {
            assertEquals(TestConstants.TEST_MSG, details.getMessage());
        }
    }

    @Test
    public void testIIIFImageExceptionThrowableStringObjectArray() {
        try {
            throw new ImageException(new IOException(TestConstants.TEST_MSG), MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

    @Test
    public void testIIIFImageExceptionThrowableLocaleStringObjectArray() {
        try {
            throw new ImageException(new IOException(TestConstants.TEST_MSG), Locale.US, MessageCodes.TEST_002,
                    TestConstants.DETAIL_ONE, TestConstants.DETAIL_TWO);
        } catch (final ImageException details) {
            assertEquals(TestConstants.TEST_MSG_DETAILED, details.getMessage());
        }
    }

}

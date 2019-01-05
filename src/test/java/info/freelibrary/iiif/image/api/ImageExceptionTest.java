
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.iiif.image.util.TestConstants;

/**
 * A test of {@link info.freelibrary.ImageException.iiif.IIIFImageException}
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


package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import info.freelibrary.iiif.image.util.MessageCodes;

public class IIIFRuntimeExceptionTest {

    @Test
    public void testIIIFRuntimeException() {
        assertEquals(null, new IIIFRuntimeException().getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionThrowable() {
        assertEquals("asdf", new IIIFRuntimeException(new RuntimeException("asdf")).getCause().getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionString() {
        assertEquals("test message", new IIIFRuntimeException(MessageCodes.TEST_001).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionLocaleString() {
        assertEquals("test message", new IIIFRuntimeException(Locale.US, MessageCodes.TEST_001).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionStringObjectArray() {
        assertEquals("test message: asdf and aaaa", new IIIFRuntimeException(MessageCodes.TEST_002, new Object[] {
            "asdf", "aaaa" }).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionThrowableString() {
        assertEquals("test message", new IIIFRuntimeException(new RuntimeException(), MessageCodes.TEST_001)
                .getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionLocaleStringObjectArray() {
        assertEquals("test message: asdf and aaaa", new IIIFRuntimeException(Locale.US, MessageCodes.TEST_002,
                new Object[] { "asdf", "aaaa" }).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionThrowableLocaleString() {
        assertEquals("test message", new IIIFRuntimeException(new RuntimeException(), Locale.US,
                MessageCodes.TEST_001).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionThrowableStringObjectArray() {
        assertEquals("test message: asdf and aaaa", new IIIFRuntimeException(new RuntimeException(),
                MessageCodes.TEST_002, new Object[] { "asdf", "aaaa" }).getMessage());
    }

    @Test
    public void testIIIFRuntimeExceptionThrowableLocaleStringObjectArray() {
        assertEquals("test message: asdf and aaaa", new IIIFRuntimeException(new RuntimeException(), Locale.US,
                MessageCodes.TEST_002, new Object[] { "asdf", "aaaa" }).getMessage());
    }

}

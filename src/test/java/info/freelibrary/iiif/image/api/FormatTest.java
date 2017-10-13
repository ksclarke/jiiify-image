
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.image.api.Format;
import info.freelibrary.iiif.image.api.UnsupportedFormatException;

public class FormatTest {

    @Test
    public void testParseExt() throws UnsupportedFormatException {
        final Format format = Format.parseExtension("jpg");
        assertEquals(Format.JPG, format);
    }

    @Test
    public void testParseMimeType() throws UnsupportedFormatException {
        final Format format = Format.parseMimeType("image/jpeg");
        assertEquals(Format.JPG, format);
    }

    @Test
    public void testGetExt() throws UnsupportedFormatException {
        assertEquals(Format.JPG.getExtension(), Format.getExtension("image/jpeg"));
    }

    @Test
    public void testGetMimeType() throws UnsupportedFormatException {
        assertEquals(Format.JPG.getMimeType(), Format.getMimeType("jpg"));
    }

}

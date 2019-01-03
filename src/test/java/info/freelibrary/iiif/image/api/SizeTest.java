
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * A test of {@link info.freelibrary.Size.iiif.ImageSize}.
 */
public class SizeTest {

    @Test
    public void testImageSizeFull() {
        Size size;

        try {
            size = Size.parse(Size.FULL);
            assertTrue(size.isFullSize());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            size = Size.parse("pct:100");
            assertTrue(size.isFullSize());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            size = Size.parse("pct:50");
            assertFalse(size.isFullSize());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            size = Size.parse("50,");
            assertFalse(size.isFullSize());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testImageSizePercent() {
        try {
            Size.parse("pct:50");
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            Size.parse("pct:0");
            fail("Failed to throw an exception for a 0 value");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse("pct:101");
            fail("Failed to throw an exception for a 101 value");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse("pct:bad_percent");
            fail("Failed to throw an exception for a non-int percent");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void testImageSizeHeight() {
        try {
            Size.parse(",200");
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            Size.parse(",bad_size");
            fail("Failed to throw exception on non-integer height value");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void testHasWidth() throws InvalidSizeException {
        assertTrue(Size.parse("10,10").hasWidth());
    }

    @Test
    public void testHasHeight() throws InvalidSizeException {
        assertTrue(Size.parse("10,10").hasHeight());
    }

    @Test
    public void testToString() throws InvalidSizeException {
        assertEquals("full", Size.parse("full").toString());
        assertEquals("pct:50", Size.parse("pct:50").toString());
        assertEquals("100,", Size.parse("100,").toString());
        assertEquals(",100", Size.parse(",100").toString());
        assertEquals("60,60", Size.parse("60,60").toString());
        assertEquals("!60,60", Size.parse("!60,60").toString());
    }

    @Test
    public void testImageSizeWidth() {
        try {
            Size.parse("200,");
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            Size.parse("bad_size,");
            fail("Failed to throw exception on non-integer width value");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void testImageSizeWidthHeight() {
        try {
            Size.parse("200,200");
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            Size.parse("bad_size,200");
            fail("Failed to catch bad width");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse("200,bad_size");
            fail("Failed to catch bad height");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void canBeScaled() {
        try {
            assertTrue(Size.parse("!200,200").isScalable());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertFalse(Size.parse("200,200").isScalable());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testScalableRequest() {
        try {
            Size.parse("!200,200");
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            Size.parse("!bad_size,200");
            fail("Failed to catch bad width");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse("!200,bad_size");
            fail("Failed to catch bad height");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void testGetPercentage() {
        try {
            assertEquals(50, Size.parse("pct:50").getPercentage());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testIsPercentage() {
        try {
            assertTrue(Size.parse("pct:100").isPercentage());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertFalse(Size.parse("100,").isPercentage());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testImageSizeCommas() {
        try {
            Size.parse("200,200,200");
            fail("Failed to catch extra comma");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse(",");
            fail("Failed to catch missing width and height");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse("200,200,");
            fail("Failed to catch extra comma");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test
    public void testEmptyImageSize() {
        try {
            Size.parse("");
            fail("Failed to catch empty ImageSize string");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }

        try {
            Size.parse(null);
            fail("Failed to catch null ImageSize string");
        } catch (final InvalidSizeException details) {
            // Expected exception
        }
    }

    @Test(expected = InvalidSizeException.class)
    public void testNonIntegerSize() throws InvalidSizeException {
        Size.parse("invalid size");
    }

    @Test
    public void testGetHeight() {
        try {
            assertEquals(200, Size.parse("100,200").getHeight());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(0, Size.parse("pct:100").getHeight());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testGetHeightMinimum() {
        try {
            assertEquals(50, Size.parse("100,200").getHeight(50, 50));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(200, Size.parse("100,200").getHeight(300, 50));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(50, Size.parse("pct:100").getHeight(50, 100));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testGetWidth() {
        try {
            assertEquals(100, Size.parse("100,200").getWidth());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(0, Size.parse("pct:100").getWidth());
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testGetWidthMinimum() {
        try {
            assertEquals(50, Size.parse("100,200").getWidth(50, 50));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(100, Size.parse("100,200").getWidth(300, 50));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }

        try {
            assertEquals(50, Size.parse("pct:100").getWidth(50, 100));
        } catch (final InvalidSizeException details) {
            fail(details.getMessage());
        }
    }

}


package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import info.freelibrary.iiif.image.util.LoggingUtils;

import ch.qos.logback.classic.Level;

/**
 * A test of {@link info.freelibrary.Request.iiif.ImageRequest}
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class RequestTest {

    private static final String IMAGE_REQUEST = "/iiif/asdf/full/full/0/default.jpg";

    @Test
    public void testImageRequest() throws ImageException {
        Request.parse(IMAGE_REQUEST);
    }

    @Test
    public void testImageRequestWithoutLogging() throws ImageException {
        final String logLevel = LoggingUtils.getLogLevel(Request.class);
        LoggingUtils.setLogLevel(Request.class, Level.OFF.levelStr);
        Request.parse(IMAGE_REQUEST);
        LoggingUtils.setLogLevel(Request.class, logLevel);
    }

    @Test(expected = ImageException.class)
    public void testImageRequestMissingDot() throws ImageException {
        Request.parse("/iiif/asdf/full/full/0/default-jpg");
    }

    @Test
    public void testToString() throws ImageException {
        final Request request = Request.parse(IMAGE_REQUEST);
        assertEquals(IMAGE_REQUEST, request.toString());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);
            final Request clone = request.clone();

            assertTrue(request != clone);
            assertTrue(request.getClass() == clone.getClass());
            assertEquals(request, clone);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testSetSize() {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);
            final Size size = Size.parse("100,");

            assertNotEquals(request.getSize(), size);
            request.setSize(size);
            assertEquals(request.getSize(), size);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testSetFormat() {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);
            final Format format = Format.parseExtension("png");

            assertNotEquals(request.getFormat(), format);
            request.setFormat(format);
            assertEquals(request.getFormat(), format);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testSetQuality() {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);

            assertNotEquals(request.getQuality(), Quality.GRAY);
            request.setQuality(Quality.GRAY);
            assertEquals(request.getQuality(), Quality.GRAY);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testSetRotation() {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);
            final Rotation rotation = new Rotation(90);

            assertNotEquals(request.getRotation(), rotation);
            request.setRotation(rotation);
            assertEquals(request.getRotation(), rotation);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testSetRegion() {
        try {
            final Request request = Request.parse(IMAGE_REQUEST);
            final Region region = Region.parse("100,100,100,100");

            assertNotEquals(request.getRegion(), region);
            request.setRegion(region);
            assertEquals(request.getRegion(), region);
        } catch (final ImageException details) {
            fail(details.getMessage());
        }
    }

}

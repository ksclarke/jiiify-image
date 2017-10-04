
package info.freelibrary.iiif;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;

import info.freelibrary.iiif.image.Format;
import info.freelibrary.iiif.image.InvalidRotationException;
import info.freelibrary.iiif.image.Quality;
import info.freelibrary.iiif.image.Region;
import info.freelibrary.iiif.image.Request;
import info.freelibrary.iiif.image.Rotation;
import info.freelibrary.iiif.image.Size;

/**
 * Tests for the Java implementation of image.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class JavaImageTest {

    @Test
    public void testJavaImageByteArray() throws IOException {
        new JavaImage(Files.readAllBytes(TestResource.TEST_TIF.toPath()));
    }

    @Test
    public void testJavaImageFile() throws IOException {
        new JavaImage(TestResource.TEST_TIF);
    }

    @Test
    public void testGetWidth() throws IOException {
        assertEquals(2000, new JavaImage(TestResource.TEST_TIF).getWidth());
    }

    @Test
    public void testGetHeight() throws IOException {
        assertEquals(2000, new JavaImage(TestResource.TEST_TIF).getHeight());
    }

    @Test
    public void testTransformFull() throws IOException, InvalidRotationException {
        final JavaImage expected = new JavaImage(TestResource.TEST_TRANFORM_FULL);
        final JavaImage image = new JavaImage(TestResource.TEST_TIF);
        final String id = UUID.randomUUID().toString();
        final Region region = new Region(0, 0, 1000, 1000);
        final Size size = new Size(500, 500);
        final Rotation rotation = new Rotation(45);
        final Request request = new Request(id, "/iiif", region, size, rotation, Quality.COLOR, Format.TIF);

        assertEquals(expected, image.transform(request));
    }

    @Test
    public void testExtract() throws IOException {
        final JavaImage expected = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage image = new JavaImage(TestResource.TEST_TIF);

        assertEquals(expected, image.extract(new Region(0, 0, 1000, 1000)));
    }

    @Test
    public void testResize() throws IOException {
        final JavaImage image = new JavaImage(TestResource.TEST_TIF);
        final JavaImage expected = new JavaImage(TestResource.TEST_RESIZED);

        assertEquals(expected, image.resizeTo(new Size(1000, 1000)));
    }

    @Test
    public void testRotateScalr() throws IOException, InvalidRotationException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage expected = new JavaImage(TestResource.TEST_ROTATED_90);

        assertEquals(expected, image.rotateTo(new Rotation(90)));
    }

    @Test
    public void testRotateTransform() throws IOException, InvalidRotationException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage expected = new JavaImage(TestResource.TEST_ROTATED_45);

        assertEquals(expected, image.rotateTo(new Rotation(45)));
    }

    @Test
    public void testAdjustGray() throws IOException, InvalidRotationException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage expected = new JavaImage(TestResource.TEST_ADJUST_GRAY);

        assertEquals(expected, image.adjust(Quality.GRAY));
    }

    @Test
    public void testAdjustBitonal() throws IOException, InvalidRotationException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage expected = new JavaImage(TestResource.TEST_ADJUST_BITONAL);

        assertEquals(expected, image.adjust(Quality.BITONAL));
    }

    @Test
    public void testHashCodeFileSource() throws IOException {
        assertEquals(-980278941, new JavaImage(TestResource.TEST_EXTRACTED).hashCode());
    }

    @Test
    public void testHashCodeByteArraySource() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());

        assertEquals(-980278941, new JavaImage(bytes).hashCode());
    }

    @Test
    public void testEquals() throws IOException {
        final JavaImage image1 = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage image2 = new JavaImage(TestResource.TEST_EXTRACTED);

        assertEquals(image1, image2);
    }

    @Test
    public void testNotEquals() throws IOException {
        final JavaImage image1 = new JavaImage(TestResource.TEST_EXTRACTED);
        final JavaImage image2 = new JavaImage(TestResource.TEST_RESIZED);

        assertNotEquals(image1, image2);
    }

    @Test
    public void testWriteFormatFileTIF() throws IOException {
        final File tiffFile = File.createTempFile(getClass().getSimpleName() + "-", "." + Format.TIF.getExtension());
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);

        tiffFile.deleteOnExit();
        image.write(Format.TIF, tiffFile);
        assertEquals(image, new JavaImage(tiffFile));
    }

    @Test
    public void testWriteFormatFileJPG() throws IOException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final File jpgFile = File.createTempFile(getClass().getSimpleName() + "-", ".jpg");

        jpgFile.deleteOnExit();
        image.write(Format.JPG, jpgFile).free();
        assertEquals(new JavaImage(TestResource.TEST_FORMAT_JPG), new JavaImage(jpgFile));
    }

    @Test
    public void testWriteFormatFilePNG() throws IOException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final File pngFile = File.createTempFile(getClass().getSimpleName() + "-", ".png");

        pngFile.deleteOnExit();
        image.write(Format.PNG, pngFile).free();
        assertEquals(new JavaImage(TestResource.TEST_FORMAT_PNG), new JavaImage(pngFile));
    }

    @Test
    public void testWriteFormatFileGIF() throws IOException {
        final JavaImage image = new JavaImage(TestResource.TEST_EXTRACTED);
        final File gifFile = File.createTempFile(getClass().getSimpleName() + "-", ".gif");

        gifFile.deleteOnExit();
        image.write(Format.GIF, gifFile).free();
        assertEquals(new JavaImage(TestResource.TEST_FORMAT_GIF), new JavaImage(gifFile));
    }

    @Test
    public void testWriteFormatByteArrayOutputStream() throws IOException {
        final byte[] imageBytes = Files.readAllBytes(TestResource.TEST_TIF.toPath());
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        new JavaImage(imageBytes).write(Format.TIF, out).free();
        Arrays.equals(imageBytes, out.toByteArray());
    }

}

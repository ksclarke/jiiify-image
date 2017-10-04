
package info.freelibrary.iiif;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;

public class ImageFactoryTest {

    @Test
    public void testGetImageByteArray() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());
        final Image image = ImageFactory.getImage(bytes);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

    @Test
    public void testGetImageImageImplByteArray() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());
        final Image image = ImageFactory.getImage(ImageImpl.JAVA, bytes);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

    @Test
    public void testGetImageFile() throws IOException {
        final Image image = ImageFactory.getImage(TestResource.TEST_EXTRACTED);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

    @Test
    public void testGetImageImageImplFile() throws IOException {
        final Image image = ImageFactory.getImage(ImageImpl.JAVA, TestResource.TEST_EXTRACTED);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

}

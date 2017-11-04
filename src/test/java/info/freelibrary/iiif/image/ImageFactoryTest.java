
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;

import org.junit.Test;

public class ImageFactoryTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        final Constructor<ImageFactory> constructor = ImageFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testGetImageByteArray() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());
        final Image image = ImageFactory.getImage(bytes);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

    @Test
    public void testGetImageByteArrayBoolean() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());
        final Image image = ImageFactory.getImage(bytes, false);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

    @Test
    public void testCache() throws IOException {
        ImageFactory.cache(true);
        assertEquals(true, ImageFactory.caches());
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
    public void testGetImageImageImplByteArrayBoolean() throws IOException {
        final byte[] bytes = Files.readAllBytes(TestResource.TEST_EXTRACTED.toPath());
        final Image image = ImageFactory.getImage(ImageImpl.JAVA, bytes, false);

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
    public void testGetImageFileBoolean() throws IOException {
        final Image image = ImageFactory.getImage(TestResource.TEST_EXTRACTED, false);

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

    @Test
    public void testGetImageImageImplFileBoolean() throws IOException {
        final Image image = ImageFactory.getImage(ImageImpl.JAVA, TestResource.TEST_EXTRACTED, false);

        assertEquals(JavaImage.class.getName(), image.getClass().getName());
        assertEquals(1000, image.getHeight());
        assertEquals(1000, image.getWidth());
    }

}

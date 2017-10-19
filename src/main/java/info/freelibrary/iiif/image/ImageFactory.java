
package info.freelibrary.iiif.image;

import java.io.File;
import java.io.IOException;

/**
 * A factory to create image objects.
 */
public final class ImageFactory {

    private static final ImageImpl DEFAULT_IMAGE_IMPL = ImageImpl.JAVA;

    private ImageFactory() {
    }

    /**
     * Creates an image object from the supplied byte array.
     *
     * @param aImageByteArray An image byte array
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final byte[] aImageByteArray) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageByteArray);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType An image implementation type
     * @param aImageByteArray A source image byte array
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final ImageImpl aImageType, final byte[] aImageByteArray) throws IOException {
        return aImageType.getImage(aImageByteArray);
    }

    /**
     * Creates an image object from the supplied image file.
     *
     * @param aImageFile A source image file
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final File aImageFile) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageFile);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType An image implementation type
     * @param aImageFile A source image file
     * @return An image
     * @throws IOException If the image can't be ready
     */
    public static Image getImage(final ImageImpl aImageType, final File aImageFile) throws IOException {
        return aImageType.getImage(aImageFile);
    }

}

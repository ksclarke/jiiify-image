
package info.freelibrary.iiif;

import java.io.File;
import java.io.IOException;

/**
 * A factory to create image objects.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
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
        return DEFAULT_IMAGE_IMPL.getInstance(aImageByteArray);
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
        return aImageType.getInstance(aImageByteArray);
    }

    /**
     * Creates an image object from the supplied image file.
     *
     * @param aImageFile A source image file
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final File aImageFile) throws IOException {
        return DEFAULT_IMAGE_IMPL.getInstance(aImageFile);
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
        return aImageType.getInstance(aImageFile);
    }

}


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
     * @return An image object
     */
    public static Image getImage(final byte[] aImageByteArray) throws IOException {
        return DEFAULT_IMAGE_IMPL.getInstance(aImageByteArray);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType A type of image object
     * @param aImageByteArray An image byte array
     * @return An image object
     */
    public static Image getImage(final ImageImpl aImageType, final byte[] aImageByteArray) throws IOException {
        return aImageType.getInstance(aImageByteArray);
    }

    /**
     * Creates an image object from the supplied image file.
     *
     * @param aImageFile A image file
     * @return An image object
     */
    public static Image getImage(final File aImageFile) throws IOException {
        return DEFAULT_IMAGE_IMPL.getInstance(aImageFile);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType
     * @param aImageFile
     * @return
     */
    public static Image getImage(final ImageImpl aImageType, final File aImageFile) throws IOException {
        return aImageType.getInstance(aImageFile);
    }

}

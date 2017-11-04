
package info.freelibrary.iiif.image;

import java.io.File;
import java.io.IOException;

/**
 * A factory to create image objects.
 */
public final class ImageFactory {

    private static final ImageImpl DEFAULT_IMAGE_IMPL = ImageImpl.JAVA;

    private static boolean myImagesReset;

    private ImageFactory() {
    }

    /**
     * Whether images should keep a cached version of themselves that can be used to rollback any transformations
     * performed on the image.
     *
     * @param aImageCanBeReset True if images keep a cached version of themselves; else, false
     */
    public static void cache(final boolean aImageCanBeReset) {
        myImagesReset = aImageCanBeReset;
    }

    /**
     * Returns true if images cache the original untransformed image so that it can be reset; else, false.
     *
     * @return True if images cache the original untransformed image so that it can be reset; else, false
     */
    public static boolean caches() {
        return myImagesReset;
    }

    /**
     * Creates an image object from the supplied byte array.
     *
     * @param aImageByteArray An image byte array
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final byte[] aImageByteArray) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageByteArray, myImagesReset);
    }

    /**
     * Creates an image object from the supplied byte array.
     *
     * @param aImageByteArray An image byte array
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final byte[] aImageByteArray, final boolean aCachedImage) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageByteArray, aCachedImage);
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
        return aImageType.getImage(aImageByteArray, myImagesReset);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType An image implementation type
     * @param aImageByteArray A source image byte array
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final ImageImpl aImageType, final byte[] aImageByteArray, final boolean aCachedImage)
            throws IOException {
        return aImageType.getImage(aImageByteArray, aCachedImage);
    }

    /**
     * Creates an image object from the supplied image file.
     *
     * @param aImageFile A source image file
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final File aImageFile) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageFile, myImagesReset);
    }

    /**
     * Creates an image object from the supplied image file.
     *
     * @param aImageFile A source image file
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If the image can't be read
     */
    public static Image getImage(final File aImageFile, final boolean aCachedImage) throws IOException {
        return DEFAULT_IMAGE_IMPL.getImage(aImageFile, aCachedImage);
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
        return aImageType.getImage(aImageFile, myImagesReset);
    }

    /**
     * Creates an image object of the supplied type from the supplied byte array.
     *
     * @param aImageType An image implementation type
     * @param aImageFile A source image file
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If the image can't be ready
     */
    public static Image getImage(final ImageImpl aImageType, final File aImageFile, final boolean aCachedImage)
            throws IOException {
        return aImageType.getImage(aImageFile, aCachedImage);
    }

}


package info.freelibrary.iiif.image;

import java.io.File;
import java.io.IOException;

/**
 * Supported image types.
 */
public enum ImageImpl {
    JAVA {

        @Override
        public JavaImage getImage(final byte[] aImageBytes, final boolean aCachedImage) throws IOException {
            return new JavaImage(aImageBytes, aCachedImage);
        }

        @Override
        public JavaImage getImage(final File aImageFile, final boolean aCachedImage) throws IOException {
            return new JavaImage(aImageFile, aCachedImage);
        }
    },
    OPENCV {

        @Override
        public OpenCVImage getImage(final byte[] aImageBytes, final boolean aCachedImage) throws IOException {
            return new OpenCVImage(aImageBytes, aCachedImage);
        }

        @Override
        public OpenCVImage getImage(final File aImageFile, final boolean aCachedImage) throws IOException {
            return new OpenCVImage(aImageFile, aCachedImage);
        }
    };

    /**
     * Gets an image instance from the supplied image byte array.
     *
     * @param aImageBytes An image byte array
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If there was trouble reading from the byte array
     */
    public abstract Image getImage(byte[] aImageBytes, boolean aCachedImage) throws IOException;

    /**
     * Gets an image instance from the supplied image file.
     *
     * @param aImageFile An image file
     * @param aCachedImage If the original image is cached in memory before transformation
     * @return An image
     * @throws IOException If there was trouble reading from the byte array
     */
    public abstract Image getImage(File aImageFile, boolean aCachedImage) throws IOException;

}

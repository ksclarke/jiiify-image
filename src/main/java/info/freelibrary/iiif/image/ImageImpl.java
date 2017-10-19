
package info.freelibrary.iiif.image;

import java.io.File;
import java.io.IOException;

/**
 * Supported image types.
 */
public enum ImageImpl {
    JAVA {

        @Override
        public JavaImage getImage(final byte[] aImageBytes) throws IOException {
            return new JavaImage(aImageBytes);
        }

        @Override
        public JavaImage getImage(final File aImageFile) throws IOException {
            return new JavaImage(aImageFile);
        }
    },
    OPENCV {

        @Override
        public OpenCVImage getImage(final byte[] aImageBytes) throws IOException {
            return new OpenCVImage(aImageBytes);
        }

        @Override
        public OpenCVImage getImage(final File aImageFile) throws IOException {
            return new OpenCVImage(aImageFile);
        }
    };

    /**
     * Gets an image instance from the supplied image byte array.
     *
     * @param aImageBytes An image byte array
     * @return An image
     * @throws IOException If there was trouble reading from the byte array
     */
    public abstract Image getImage(byte[] aImageBytes) throws IOException;

    /**
     * Gets an image instance from the supplied image file.
     *
     * @param aImageFile An image file
     * @return An image
     * @throws IOException If there was trouble reading from the byte array
     */
    public abstract Image getImage(File aImageFile) throws IOException;

}

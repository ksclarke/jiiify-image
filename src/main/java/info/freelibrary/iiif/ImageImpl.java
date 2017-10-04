
package info.freelibrary.iiif;

import java.io.File;
import java.io.IOException;

/**
 * Supported image types.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public enum ImageImpl {
    JAVA {

        @Override
        public JavaImage getInstance(final byte[] aImageBytes) throws IOException {
            return new JavaImage(aImageBytes);
        }

        @Override
        public JavaImage getInstance(final File aImageFile) throws IOException {
            return new JavaImage(aImageFile);
        }
    },
    OPENCV {

        @Override
        public OpenCVImage getInstance(final byte[] aImageBytes) throws IOException {
            return new OpenCVImage(aImageBytes);
        }

        @Override
        public OpenCVImage getInstance(final File aImageFile) throws IOException {
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
    public abstract Image getInstance(byte[] aImageBytes) throws IOException;

    /**
     * Gets an image instance from the supplied image file.
     *
     * @param aImageFile An image file
     * @return An image
     * @throws IOException If there was trouble reading from the byte array
     */
    public abstract Image getInstance(File aImageFile) throws IOException;

}

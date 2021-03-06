
package info.freelibrary.iiif.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import info.freelibrary.iiif.image.api.Format;
import info.freelibrary.iiif.image.api.Quality;
import info.freelibrary.iiif.image.api.Region;
import info.freelibrary.iiif.image.api.Request;
import info.freelibrary.iiif.image.api.Rotation;
import info.freelibrary.iiif.image.api.Size;

/**
 * An OpenCV image implementation. Note that this is not yet implemented.
 */
public class OpenCVImage extends AbstractImage implements Image {

    /**
     * Creates an OpenCVImage from the supplied image byte array.
     *
     * @param aImageByteArray An image byte array
     */
    @SuppressWarnings("PMD")
    public OpenCVImage(final byte[] aImageByteArray) {

    }

    /**
     * Creates an OpenCVImage from the supplied image byte array.
     *
     * @param aImageByteArray An image byte array
     * @param aCachedImage If the original image is cached in memory before transformation
     */
    @SuppressWarnings("PMD")
    public OpenCVImage(final byte[] aImageByteArray, final boolean aCachedImage) {

    }

    /**
     * Creates an OpenCVImage from the supplied image file.
     *
     * @param aImageFile An image file
     * @throws IOException If the supplied image file can't be read
     * @throws FileNotFoundException If the supplied image file can't be found
     */
    @SuppressWarnings("PMD")
    public OpenCVImage(final File aImageFile) throws IOException, FileNotFoundException {

    }

    /**
     * Creates an OpenCVImage from the supplied image file.
     *
     * @param aImageFile An image file
     * @param aCachedImage If the original image is cached in memory before transformation
     * @throws IOException If the supplied image file can't be read
     * @throws FileNotFoundException If the supplied image file can't be found
     */
    @SuppressWarnings("PMD")
    public OpenCVImage(final File aImageFile, final boolean aCachedImage) throws IOException, FileNotFoundException {

    }

    @Override
    public int getWidth() throws IOException {
        return 0;
    }

    @Override
    public int getHeight() throws IOException {
        return 0;
    }

    @Override
    public Image free() {
        return null;
    }

    @Override
    public Image revert() {
        return null;
    }

    @Override
    public Image transform(final Request aRequest) throws IOException {
        return null;
    }

    @Override
    public Image extract(final Region aRegion) throws IOException {
        return null;
    }

    @Override
    public Image resizeTo(final Size aSize) throws IOException {
        return null;
    }

    @Override
    public Image rotateTo(final Rotation aRotation) throws IOException {
        return null;
    }

    @Override
    public Image adjust(final Quality aQuality) throws IOException {
        return null;
    }

    @Override
    public Image write(final Format aFormat, final File aFile) throws IOException {
        return null;
    }

    @Override
    public Image write(final Format aFormat, final float aQuality, final File aFile) throws IOException {
        return null;
    }

    @Override
    public Image write(final Format aFormat, final ByteArrayOutputStream aStream) throws IOException {
        return null;
    }

    @Override
    public Image write(final Format aFormat, final float aQuality, final ByteArrayOutputStream aStream)
            throws IOException {
        return null;
    }

}

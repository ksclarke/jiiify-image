
package info.freelibrary.iiif;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import info.freelibrary.iiif.image.Format;
import info.freelibrary.iiif.image.Quality;
import info.freelibrary.iiif.image.Region;
import info.freelibrary.iiif.image.Request;
import info.freelibrary.iiif.image.Rotation;
import info.freelibrary.iiif.image.Size;

/**
 * A IIIF image.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public interface Image {

    /**
     * Gets the width of the image.
     *
     * @return The image's width
     * @throws IOException If there is trouble reading the image data
     */
    int getWidth() throws IOException;

    /**
     * Gets the height of the image.
     *
     * @return The image's height
     * @throws IOException If there is trouble reading the image data
     */
    int getHeight() throws IOException;

    /**
     * Frees any system resources alloted to the image.
     *
     * @return The image
     */
    Image free();

    /**
     * Transforms the image according to the IIIF request.
     *
     * @param aRequest A IIIF request
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image transform(Request aRequest) throws IOException;

    /**
     * Extracts the supplied region, replacing the existing image with the image region.
     *
     * @param aRegion A IIIF region
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image extract(Region aRegion) throws IOException;

    /**
     * Resizes the image to the supplied size.
     *
     * @param aSize A desired size for the image
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image resizeTo(Size aSize) throws IOException;

    /**
     * Rotates the image to the supplied rotation.
     *
     * @param aRotation A desired rotation for the image
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image rotateTo(Rotation aRotation) throws IOException;

    /**
     * Adjusts the image quality to the supplied value.
     *
     * @param aQuality The desired quality of the image
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image adjust(Quality aQuality) throws IOException;

    /**
     * Writes the image to the supplied file in the supplied format.
     *
     * @param aFormat A desired image format
     * @param aFile An output image file
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image write(Format aFormat, File aFile) throws IOException;

    /**
     * Writes the image to the supplied file in the supplied format.
     *
     * @param aFormat A desired image format
     * @param aQuality A compression quality expressed as a float between 0f and 1f
     * @param aFile An output image file
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image write(Format aFormat, float aQuality, File aFile) throws IOException;

    /**
     * Writes the image to the supplied {@link java.io.ByteArrayOutputStream}.
     *
     * @param aFormat A desired image format
     * @param aStream An output {@link java.io.ByteArrayOutputStream}
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image write(Format aFormat, ByteArrayOutputStream aStream) throws IOException;

    /**
     * Writes the image to the supplied {@link java.io.ByteArrayOutputStream}.
     *
     * @param aFormat A desired image format
     * @param aQuality A compression quality expressed as a float between 0f and 1f
     * @param aStream An output {@link java.io.ByteArrayOutputStream}
     * @return The image
     * @throws IOException If there is trouble reading the image data
     */
    Image write(Format aFormat, float aQuality, ByteArrayOutputStream aStream) throws IOException;

}

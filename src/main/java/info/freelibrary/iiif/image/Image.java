
package info.freelibrary.iiif.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import info.freelibrary.iiif.image.api.Format;
import info.freelibrary.iiif.image.api.Quality;
import info.freelibrary.iiif.image.api.Region;
import info.freelibrary.iiif.image.api.Request;
import info.freelibrary.iiif.image.api.Rotation;
import info.freelibrary.iiif.image.api.Size;

/**
 * A IIIF image.
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
     * Gets the center of the image.
     *
     * @return An image region representing the center of the image
     * @throws IOException If there is trouble reading image for center dimensions
     */
    Region getCenter() throws IOException;

    /**
     * Gets the ratio of the image's width and height.
     *
     * @return A string representation of the aspect ratio
     * @throws IOException If there is trouble reading image for aspect ration
     */
    String getAspectRatio() throws IOException;

    /**
     * Gets the scale factors for the image.
     *
     * @param aTileSize A tile size
     * @return The scale factors for the image using the supplied tile size
     * @throws IOException If there is trouble reading image for scale factors
     */
    List<Integer> getScaleFactors(int aTileSize) throws IOException;

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

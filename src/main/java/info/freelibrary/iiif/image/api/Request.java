
package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import java.nio.file.Paths;
import java.util.StringJoiner;

import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.URLUtils;

/**
 * IIIF image request.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Request implements Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Request.class, BUNDLE_NAME);

    private static final String DELIM = "/";

    private static final String EMPTY = "";

    private String myID;

    private String myServicePrefix;

    private Region myRegion;

    private Size mySize;

    private Rotation myRotation;

    private Quality myQuality;

    private Format myFormat;

    /**
     * Creates a IIIF image request object for a particular image region.
     *
     * @param aID An ID for the image being requested
     * @param aServicePrefix A IIIF service prefix
     * @param aRegion An image region being requested
     */
    public Request(final String aID, final String aServicePrefix, final Region aRegion) {
        myRegion = aRegion;
        myID = aID;
        myServicePrefix = aServicePrefix.replace(DELIM, EMPTY);
        mySize = new Size();
        myRotation = new Rotation();
        myQuality = Quality.DEFAULT;
        myFormat = Format.JPG;
    }

    /**
     * Creates a IIIF image request object for a particular image region and with a particular size.
     *
     * @param aID An ID for the image being requested
     * @param aServicePrefix A IIIF service prefix
     * @param aRegion An image region being requested
     * @param aSize A requested image size
     */
    public Request(final String aID, final String aServicePrefix, final Region aRegion, final Size aSize) {
        myRegion = aRegion;
        myID = aID;
        myServicePrefix = aServicePrefix.replace(DELIM, EMPTY);
        mySize = aSize;
        myRotation = new Rotation();
        myQuality = Quality.DEFAULT;
        myFormat = Format.JPG;
    }

    /**
     * Creates a IIIF image request object for image with a particular size.
     *
     * @param aID An ID for the image being requested
     * @param aServicePrefix A IIIF service prefix
     * @param aSize A requested image size
     */
    public Request(final String aID, final String aServicePrefix, final Size aSize) {
        mySize = aSize;
        myID = aID;
        myServicePrefix = aServicePrefix.replace(DELIM, EMPTY);
        myRegion = new Region();
        myRotation = new Rotation();
        myQuality = Quality.DEFAULT;
        myFormat = Format.JPG;
    }

    /**
     * Creates a IIIF image request object for a region from an image with a particular size, rotation, and quality.
     *
     * @param aID An ID for the image being requested
     * @param aServicePrefix A IIIF service prefix
     * @param aRegion A region from the requested image
     * @param aSize A requested image size
     * @param aRotation A rotation to perform on the requested image region
     * @param aQuality A quality of image to return
     */
    public Request(final String aID, final String aServicePrefix, final Region aRegion, final Size aSize,
            final Rotation aRotation, final Quality aQuality) {
        mySize = aSize;
        myID = aID;
        myServicePrefix = aServicePrefix.replace(DELIM, EMPTY);
        myRegion = aRegion;
        myRotation = aRotation;
        myQuality = aQuality;
        myFormat = Format.JPG;
    }

    /**
     * Creates a IIIF image request object for a region from an image with a particular size, rotation, quality, and
     * format.
     *
     * @param aID An ID for the image being requested
     * @param aServicePrefix A IIIF service prefix
     * @param aRegion A region from the requested image
     * @param aSize A requested image size
     * @param aRotation A rotation to perform on the requested image region
     * @param aQuality A quality of image to return
     * @param aFormat A format of image to return
     */
    public Request(final String aID, final String aServicePrefix, final Region aRegion, final Size aSize,
            final Rotation aRotation, final Quality aQuality, final Format aFormat) {
        mySize = aSize;
        myID = aID;
        myServicePrefix = aServicePrefix.replace(DELIM, EMPTY);
        myRegion = aRegion;
        myRotation = aRotation;
        myQuality = aQuality;
        myFormat = aFormat;
    }

    /**
     * Parse a IIIF image path into a request.
     *
     * @param aIIIFImagePath The path of the requested image
     * @return A IIIF request
     * @throws UnsupportedFormatException If there a problem with the format in the image path
     * @throws UnsupportedQualityException If there is a problem with the quality in the image path
     * @throws InvalidSizeException If there is a problem with the size in the image path
     * @throws InvalidRegionException If there is a problem with the region in the image path
     * @throws InvalidRotationException If there is a problem with the rotation in the image path
     */
    public static final Request parse(final String aIIIFImagePath) throws UnsupportedFormatException,
            UnsupportedQualityException, InvalidSizeException, InvalidRegionException, InvalidRotationException {
        final String[] pathComponents = aIIIFImagePath.substring(1).split(DELIM);
        final int dotIndex = pathComponents[5].lastIndexOf('.');

        LOGGER.debug(MessageCodes.DBG_071, aIIIFImagePath);

        if (dotIndex == -1) {
            throw new UnsupportedFormatException(MessageCodes.EXC_087);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MessageCodes.DBG_072, pathComponents[0], pathComponents[1], pathComponents[2],
                    pathComponents[3], pathComponents[4], pathComponents[5]);
        }

        final String servicePrefix = pathComponents[0];
        final String id = URLUtils.decode(pathComponents[1]);
        final Region region = Region.parse(pathComponents[2]);
        final Size size = Size.parse(pathComponents[3]);
        final Rotation rotation = Rotation.parse(pathComponents[4]);
        final Quality quality = Quality.parse(pathComponents[5].substring(0, dotIndex));
        final Format format = Format.parseExtension(pathComponents[5].substring(dotIndex + 1));

        return new Request(id, servicePrefix, region, size, rotation, quality, format);
    }

    /**
     * Gets the IIIF service prefix at which the image request was made.
     *
     * @return The IIIF service prefix at which the image request was made
     */
    public String getPrefix() {
        return myServicePrefix;
    }

    /**
     * Sets the IIIF service prefix for the request.
     *
     * @param aPrefix A IIIF service prefix
     * @return The request
     */
    public Request setPrefix(final String aPrefix) {
        myServicePrefix = aPrefix;
        return this;
    }

    /**
     * Gets the ID of the requested image.
     *
     * @return The ID of the requested image
     */
    public String getID() {
        return myID;
    }

    /**
     * Sets the ID of the requested image.
     *
     * @param aID An image ID
     * @return The request
     */
    public Request setID(final String aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the desired region of the requested image.
     *
     * @return The desired region of the requested image
     */
    public Region getRegion() {
        return myRegion;
    }

    /**
     * Resets the region for the image request.
     *
     * @param aImageRegion A new image region
     */
    public void setRegion(final Region aImageRegion) {
        myRegion = aImageRegion;
    }

    /**
     * Gets the size of the requested image.
     *
     * @return The size of the requested image
     */
    public Size getSize() {
        return mySize;
    }

    /**
     * Resets the image size for the image request.
     *
     * @param aImageSize A new image size
     */
    public void setSize(final Size aImageSize) {
        mySize = aImageSize;
    }

    /**
     * Gets the rotation of the requested image.
     *
     * @return The rotation of the requested image
     */
    public Rotation getRotation() {
        return myRotation;
    }

    /**
     * Resets the image rotation for the image request
     *
     * @param aImageRotation A new image rotation
     */
    public void setRotation(final Rotation aImageRotation) {
        myRotation = aImageRotation;
    }

    /**
     * Gets the quality of the requested image.
     *
     * @return The quality of the requested image
     */
    public Quality getQuality() {
        return myQuality;
    }

    /**
     * Resets the quality for the image request.
     *
     * @param aImageQuality A new image quality
     */
    public void setQuality(final Quality aImageQuality) {
        myQuality = aImageQuality;
    }

    /**
     * Gets the format of the requested image.
     *
     * @return The format of the requested image
     */
    public Format getFormat() {
        return myFormat;
    }

    /**
     * Resets the format for the image request.
     *
     * @param aImageFormat A new image format
     */
    public void setFormat(final Format aImageFormat) {
        myFormat = aImageFormat;
    }

    @Override
    public String toString() {
        final String fileName = StringUtils.toString('.', myQuality, myFormat);
        final String id = URLUtils.encode(myID, false);

        return new StringJoiner(DELIM, DELIM, EMPTY).add(myServicePrefix).add(id).add(myRegion.toString()).add(mySize
                .toString()).add(myRotation.toString()).add(fileName).toString();
    }

    /**
     * Returns a path for this image request.
     *
     * @return A path for this image request
     */
    public String getPath() {
        final String fileName = StringUtils.toString('.', myQuality, myFormat);
        return Paths.get(myRegion.toString(), mySize.toString(), myRotation.toString(), fileName).toString();
    }

    /**
     * Determines whether the supplied object is an ImageRequest that is the same as the supplied one.
     */
    @Override
    public boolean equals(final Object aObject) {
        final boolean result;

        if (aObject instanceof Request) {
            final Request request = (Request) aObject;
            final String servicePrefix = request.getPrefix();
            final String id = request.getID();
            final Format format = request.getFormat();
            final Quality quality = request.getQuality();
            final Region region = request.getRegion();
            final Rotation rotation = request.getRotation();
            final Size size = request.getSize();

            if (servicePrefix.equals(myServicePrefix) && id.equals(myID) && format.equals(myFormat) && quality.equals(
                    myQuality) && region.equals(myRegion) && rotation.equals(myRotation) && size.equals(mySize)) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result += myServicePrefix.hashCode();
        result += myID.hashCode();
        result += myFormat.hashCode();
        result += myQuality.hashCode();
        result += myRegion.hashCode();
        result += myRotation.hashCode();
        result += mySize.hashCode();

        return 37 * result;
    }

    /**
     * Creates a clone of the ImageRequest.
     */
    @Override
    public Request clone() throws CloneNotSupportedException {
        return (Request) super.clone();
    }

}


package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import info.freelibrary.iiif.image.util.Constants;
import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * IIIF image size.
 */
public class Size {

    public static final String FULL = "full";

    private static final Logger LOGGER = LoggerFactory.getLogger(Size.class, BUNDLE_NAME);

    private static final String DELIM = ",";

    private static final String SCALE_DELIM = "!";

    private static final String PCT_LABEL = "pct:";

    private boolean mySizeIsPercentage;

    private boolean mySizeIsScalable;

    private int myPercentage;

    private int myHeight;

    private int myWidth;

    /**
     * Creates a new image size object.
     */
    public Size() {
        mySizeIsPercentage = true;
        myPercentage = 100;
    }

    /**
     * Creates a new image size object from the supplied percentage.
     *
     * @param aPercent A percent of the image
     * @throws InvalidSizeException If the supplied percent is not between one and 360 degrees
     */
    public Size(final int aPercent) throws InvalidSizeException {
        mySizeIsPercentage = true;
        myPercentage = validate(aPercent);
    }

    /**
     * Creates a new image size object from the supplied width and height.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     */
    public Size(final int aWidth, final int aHeight) {
        myWidth = aWidth;
        myHeight = aHeight;
    }

    /**
     * Creates a new image size object from the supplied IIIF URI width and height string.
     *
     * @param aSizeString A IIIF URI string with width and height
     * @return This size
     * @throws InvalidSizeException If the supplied string isn't a valid height and width representation
     */
    public static Size parse(final String aSizeString) throws InvalidSizeException {
        final Size size;

        if (aSizeString == null) {
            throw new InvalidSizeException(MessageCodes.EXC_014, "null");
        } else if (aSizeString.equals(FULL)) {
            size = new Size();
        } else if (aSizeString.startsWith(PCT_LABEL)) {
            try {
                size = new Size(Integer.parseInt(aSizeString.substring(4)));
            } catch (final NumberFormatException details) {
                throw new InvalidSizeException(details, MessageCodes.EXC_014, aSizeString);
            }
        } else if (aSizeString.contains(DELIM)) {
            final String[] parts = aSizeString.split(DELIM);

            if (parts.length == 0) {
                throw new InvalidSizeException(MessageCodes.EXC_015);
            } else if ((aSizeString.length() - aSizeString.replace(DELIM, "").length()) > 1) {
                throw new InvalidSizeException(MessageCodes.EXC_016, aSizeString);
            }

            if (aSizeString.startsWith(DELIM)) {
                try {
                    size = new Size(0, Integer.parseInt(parts[1]));
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.HEIGHT, parts[1]);
                }
            } else if (aSizeString.startsWith(SCALE_DELIM)) {
                final int width;

                try {
                    // Chop off the exclamation point at the start of the string
                    width = Integer.parseInt(parts[0].substring(1));
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.WIDTH, parts[0]);
                }

                final int height;

                try {
                    height = Integer.parseInt(parts[1]);
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.HEIGHT, parts[0]);
                }

                size = new Size(width, height).isScalable(true);
            } else if (aSizeString.endsWith(DELIM)) {
                try {
                    size = new Size(Integer.parseInt(parts[0]), 0);
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.WIDTH, parts[0]);
                }
            } else {
                final int width;

                try {
                    width = Integer.parseInt(parts[0]);
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.WIDTH, parts[0]);
                }

                final int height;

                try {
                    height = Integer.parseInt(parts[1]);
                } catch (final NumberFormatException details) {
                    throw new InvalidSizeException(details, MessageCodes.EXC_017, Constants.HEIGHT, parts[0]);
                }

                size = new Size(width, height);
            }
        } else {
            throw new InvalidSizeException(MessageCodes.EXC_014, aSizeString);
        }

        return size;
    }

    /**
     * Returns true if the image size request is for a percentage of the actual image size.
     *
     * @return True if the image size request is for a percentage of the actual image size
     */
    public boolean isPercentage() {
        return mySizeIsPercentage;
    }

    /**
     * Returns the percent of the image size request as an integer.
     *
     * @return The percent of the image size request as an integer
     */
    public int getPercentage() {
        return myPercentage;
    }

    /**
     * Returns true if this image size request is for the full size of the image.
     *
     * @return True if this image size request is for the full size of the image
     */
    public boolean isFullSize() {
        return mySizeIsPercentage && (myPercentage == 100);
    }

    /**
     * Returns true if the width is set.
     *
     * @return True if the width is set
     */
    public boolean hasWidth() {
        return myWidth != 0;
    }

    /**
     * Returns true if the height is set.
     *
     * @return True if the height is set
     */
    public boolean hasHeight() {
        return myHeight != 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (isFullSize()) {
            sb.append(FULL);
        } else if (isPercentage()) {
            sb.append(PCT_LABEL).append(getPercentage());
        } else if (isScalable()) {
            sb.append(SCALE_DELIM).append(getWidth()).append(DELIM).append(getHeight());
        } else if (hasWidth() && !hasHeight()) {
            sb.append(getWidth()).append(DELIM);
        } else if (hasHeight() && !hasWidth()) {
            sb.append(DELIM).append(getHeight());
        } else {
            sb.append(getWidth()).append(DELIM).append(getHeight());
        }

        return sb.toString();
    }

    /**
     * Returns true if a scaled response is acceptable; else, false.
     *
     * @return True if a scaled response is acceptable; else, false
     */
    public boolean isScalable() {
        return mySizeIsScalable;
    }

    /**
     * Sets whether or not a scaled response is acceptable.
     *
     * @param aScalableSize Whether the size may be scaled
     * @return True if a scaled response is acceptable; else, false
     */
    public Size isScalable(final boolean aScalableSize) {
        mySizeIsScalable = aScalableSize;
        return this;
    }

    /**
     * Returns the height of the image size request.
     *
     * @return The height of the image size request
     */
    public int getHeight() {
        return myHeight;
    }

    /**
     * Returns the width of the image size request.
     *
     * @return The width of the image size request
     */
    public int getWidth() {
        return myWidth;
    }

    /**
     * Returns height of the image request taking into consideration the supplied actual height of the image. If the
     * supplied height is less than the requested height, the supplied height is returned. If the image size request
     * is for a percentage of the original image, the percentage of the supplied number is returned.
     *
     * @param aImageHeight The image's actual height in pixels
     * @param aImageWidth The image's actual width in pixels
     * @return The computed height of the image request
     */
    public int getHeight(final int aImageHeight, final int aImageWidth) {
        final int height;

        if (myHeight == 0) {
            if (mySizeIsPercentage) {
                LOGGER.debug(MessageCodes.DBG_073, myPercentage);
                height = (myPercentage / 100) * aImageHeight;
            } else {
                height = Math.round(scale(myWidth, aImageWidth) * aImageHeight);
                LOGGER.debug(MessageCodes.DBG_074, height);
            }
        } else {
            height = aImageHeight < myHeight ? aImageHeight : myHeight;

            if (height == myHeight) {
                LOGGER.debug(MessageCodes.DBG_075, myHeight);
            } else {
                LOGGER.debug(MessageCodes.DBG_076, aImageHeight);
            }
        }

        LOGGER.debug(MessageCodes.DBG_077, height, aImageHeight);

        return height;
    }

    /**
     * Returns width of the image request taking into consideration the supplied actual width of the image. If the
     * supplied width is less than the requested width, the supplied width is returned. If the image size request is
     * for a percentage of the original image, the percentage of the supplied number is returned.
     *
     * @param aImageWidth The image's actual width in pixels
     * @param aImageHeight The image's actual height in pixels
     * @return The computed width of the image request
     */
    public int getWidth(final int aImageWidth, final int aImageHeight) {
        final int width;

        if (myWidth == 0) {
            if (mySizeIsPercentage) {
                LOGGER.debug(MessageCodes.DBG_078, myPercentage);
                width = (myPercentage / 100) * aImageWidth;
            } else {
                width = Math.round(scale(myHeight, aImageHeight) * aImageWidth);
                LOGGER.debug(MessageCodes.DBG_079, width);
            }
        } else {
            width = aImageWidth < myWidth ? aImageWidth : myWidth;

            if (width == myWidth) {
                LOGGER.debug(MessageCodes.DBG_080, myWidth);
            } else {
                LOGGER.debug(MessageCodes.DBG_081, aImageWidth);
            }
        }

        LOGGER.debug(MessageCodes.DBG_082, width, aImageWidth);

        return width;
    }

    private int validate(final int aPercent) throws InvalidSizeException {
        if ((aPercent < 1) || (aPercent > 100)) {
            throw new InvalidSizeException(MessageCodes.EXC_014, aPercent);
        }

        return aPercent;
    }

    private float scale(final int aSizeValue, final int aImageValue) {
        final float scale;

        if (aSizeValue >= aImageValue) {
            scale = 1f;
        } else {
            scale = (float) aSizeValue / (float) aImageValue;
        }

        LOGGER.debug(MessageCodes.DBG_083, scale);

        return scale;
    }
}


package info.freelibrary.iiif.image.api;

import info.freelibrary.iiif.image.util.MessageCodes;

/**
 * IIIF image region.
 */
public class Region {

    public static final String FULL = "full";

    public static final String PERCENT = "pct:";

    // X-WIDTH (horizontal) Y-HEIGHT (vertical)
    public enum Coordinate {
        X, Y, WIDTH, HEIGHT
    }

    private final float[] myDimensions;

    private boolean myRegionIsPercentage;

    private boolean myRegionIsFullImage;

    /**
     * Creates a new image region object representing the whole image.
     */
    public Region() {
        myRegionIsFullImage = true;
        myRegionIsPercentage = true;
        myDimensions = new float[] { 100f, 100f, 100f, 100f };
    }

    /**
     * Creates a new image region representing a percentage of the whole image.
     *
     * @param aPercent A percentage of the whole image
     */
    public Region(final float aPercent) {
        myRegionIsPercentage = true;

        if (aPercent == 100f) {
            myRegionIsFullImage = true;
        }

        myDimensions = new float[] { 100f, 100f, 100f, 100f };
    }

    /**
     * Creates a new image region using percentages of the whole image.
     *
     * @param aX A X dimension for an image region as a percentage
     * @param aY A Y dimension for an image region as a percentage
     * @param aWidth A width for an image region as a percentage
     * @param aHeight A height for an image region as a percentage
     */
    public Region(final float aX, final float aY, final float aWidth, final float aHeight) {
        myRegionIsPercentage = true;

        if ((aX == 100f) && (aY == 100f) && (aWidth == 100f) && (aHeight == 100f)) {
            myRegionIsFullImage = true;
        }

        myDimensions = new float[] { aX, aY, aWidth, aHeight };
    }

    /**
     * Creates a new image region object from the supplied X, Y, width and height.
     *
     * @param aX A X dimension for an image region
     * @param aY A Y dimension for an image region
     * @param aWidth A width for an image region
     * @param aHeight A height for an image region
     */
    public Region(final int aX, final int aY, final int aWidth, final int aHeight) {
        myDimensions = new float[] { aX, aY, aWidth, aHeight };
    }

    /**
     * Creates a new image region object from the supplied IIIF URI image region string.
     *
     * @param aRegionString A region string from a IIIF URI
     * @return This region
     * @throws InvalidRegionException If the supplied string isn't a valid representation of a IIIF region
     */
    public static Region parse(final String aRegionString) throws InvalidRegionException {
        final Region region;

        if (aRegionString == null) {
            throw new InvalidRegionException(MessageCodes.EXC_020, "null");
        } else if (aRegionString.equals(FULL)) {
            region = new Region(100f);
        } else if (aRegionString.startsWith(PERCENT)) {
            final float[] dims = parseDimensions(aRegionString.substring(4));
            region = new Region(dims[0], dims[1], dims[2], dims[3]);
        } else {
            final float[] dims = parseDimensions(aRegionString);
            region = new Region((int) dims[0], (int) dims[1], (int) dims[2], (int) dims[3]);
        }

        return region;
    }

    /**
     * Gets the integer value for the supplied region coordinate.
     *
     * @param aCoordinate A region coordinate
     * @return An integer value for the supplied region coordinate
     */
    public int getInt(final Coordinate aCoordinate) {
        return (int) getFloat(aCoordinate);
    }

    /**
     * Gets the float value for the supplied region coordinate.
     *
     * @param aCoordinate A region coordinate
     * @return A float value for the supplied region coordinate
     */
    public float getFloat(final Coordinate aCoordinate) {
        final float coordinate;

        switch (aCoordinate) {
            case X:
                coordinate = myDimensions[0];
                break;
            case Y:
                coordinate = myDimensions[1];
                break;
            case WIDTH:
                coordinate = myDimensions[2];
                break;
            default:
                coordinate = myDimensions[3];
                break;
        }

        return coordinate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (isFullImage()) {
            sb.append(FULL);
        } else {
            if (isPercentage()) {
                sb.append(PERCENT);
            }

            sb.append(prettyPrint(myDimensions[0])).append(',');
            sb.append(prettyPrint(myDimensions[1])).append(',');
            sb.append(prettyPrint(myDimensions[2])).append(',');
            sb.append(prettyPrint(myDimensions[3]));
        }

        return sb.toString();
    }

    /**
     * Returns whether this image region is represented with percentages.
     *
     * @return True if this image region is represented with percentages
     */
    public boolean isPercentage() {
        return myRegionIsPercentage;
    }

    /**
     * Returns whether this image region represents the full image.
     *
     * @return True if this image region represents the full image
     */
    public boolean isFullImage() {
        return myRegionIsFullImage;
    }

    private String prettyPrint(final float aValue) {
        final String floatValue = Float.toString(aValue);
        return floatValue.endsWith(".0") ? floatValue.substring(0, floatValue.length() - 2) : floatValue;
    }

    private static float[] parseDimensions(final String aRegionString) throws InvalidRegionException {
        final String[] parts = aRegionString.split(",");

        if (parts.length != 4) {
            throw new InvalidRegionException(MessageCodes.EXC_020, aRegionString);
        }

        final float[] dimensions = new float[4];

        for (int index = 0; index < parts.length; index++) {
            try {
                dimensions[index] = Float.parseFloat(parts[index]);
            } catch (final NumberFormatException details) {
                throw new InvalidRegionException(details, MessageCodes.EXC_018, parts[index]);
            }
        }

        return dimensions;
    }
}

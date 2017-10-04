
package info.freelibrary.iiif.image;

/**
 * IIIF image region.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Region {

    public static final String FULL = "full";

    public static final String PERCENT = "pct:";

    // X-WIDTH (horizontal) Y-HEIGHT (vertical)
    public enum Coordinate {
        X, Y, WIDTH, HEIGHT
    }

    private final float[] myDimensions;

    private boolean isPercentage;

    private boolean isFullImage;

    /**
     * Creates a new image region object representing the whole image.
     */
    public Region() {
        isFullImage = true;
        isPercentage = true;
        myDimensions = new float[] { 100f, 100f, 100f, 100f };
    }

    /**
     * Creates a new image region representing a percentage of the whole image.
     *
     * @param aPercent A percentage of the whole image
     */
    public Region(final float aPercent) {
        isPercentage = true;

        if (aPercent == 100f) {
            isFullImage = true;
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
        isPercentage = true;

        if ((aX == 100f) && (aY == 100f) && (aWidth == 100f) && (aHeight == 100f)) {
            isFullImage = true;
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
     * @throws InvalidRegionException If the supplied string isn't a valid representation of a IIIF region
     */
    public static Region parse(final String aRegionString) throws InvalidRegionException {
        final Region region;

        if (aRegionString == null) {
            throw new InvalidRegionException(new NullPointerException());
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
        switch (aCoordinate) {
            case X:
                return myDimensions[0];
            case Y:
                return myDimensions[1];
            case WIDTH:
                return myDimensions[2];
            default:
                return myDimensions[3];
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        if (isFullImage()) {
            sb.append(FULL);
        } else {
            if (usesPercentages()) {
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
    public boolean usesPercentages() {
        return isPercentage;
    }

    /**
     * Returns whether this image region represents the full image.
     *
     * @return True if this image region represents the full image
     */
    public boolean isFullImage() {
        return isFullImage;
    }

    private String prettyPrint(final float aValue) {
        final String floatValue = Float.toString(aValue);
        return floatValue.endsWith(".0") ? floatValue.substring(0, floatValue.length() - 2) : floatValue;
    }

    private static float[] parseDimensions(final String aRegionString) throws InvalidRegionException {
        final String[] parts = aRegionString.split(",");
        final float[] dimensions = new float[4];

        if (parts.length != 4) {
            throw new InvalidRegionException(MessageCodes.EXC_020, aRegionString);
        }

        for (int index = 0; index < parts.length; index++) {
            try {
                dimensions[index] = Float.parseFloat(parts[index]);
            } catch (final NumberFormatException details) {
                throw new InvalidRegionException(MessageCodes.EXC_018, parts[index]);
            }
        }

        return dimensions;
    }
}

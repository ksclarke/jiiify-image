
package info.freelibrary.iiif.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.freelibrary.iiif.image.api.Region;

abstract class AbstractImage {

    AbstractImage() {
        super();
    }

    /**
     * Gets the width of the image.
     *
     * @return The image's width
     * @throws IOException If there is trouble reading the image data
     */
    public abstract int getWidth() throws IOException;

    /**
     * Gets the height of the image.
     *
     * @return The image's height
     * @throws IOException If there is trouble reading the image data
     */
    public abstract int getHeight() throws IOException;

    /**
     * Gets the center of the image.
     *
     * @return An image region representing the center of the image
     * @throws IOException If there is trouble reading center region from image
     */
    public Region getCenter() throws IOException {
        final int width = getWidth();
        final int height = getHeight();
        final int smallSide = Math.min(height, width);
        final Region region;

        if (smallSide == height) {
            region = new Region((width - smallSide) / 2, 0, smallSide, smallSide);
        } else {
            region = new Region(0, (height - smallSide) / 2, smallSide, smallSide);
        }

        return region;
    }

    /**
     * Gets the ratio of the image's width and height.
     *
     * @return A string representation of the aspect ratio
     * @throws IOException If there is trouble reading image for aspect ratio
     */
    public String getAspectRatio() throws IOException {
        final int height = getHeight();
        final int gcd = gcd(getWidth(), height);
        return (height / gcd) + ":" + (height / gcd);
    }

    /**
     * Gets the scale factors for the image.
     *
     * @param aTileSize A tile size
     * @return The scale factors for the image using the supplied tile size
     * @throws IOException If there is trouble reading image for scale factors
     */
    public List<Integer> getScaleFactors(final int aTileSize) throws IOException {
        final int longDimension = Math.max(getWidth(), getHeight());
        final List<Integer> scaleFactors = new ArrayList<>();

        for (int multiplier = 1; (multiplier * aTileSize) < longDimension; multiplier *= 2) {
            scaleFactors.add(multiplier);
        }

        if (scaleFactors.isEmpty()) {
            scaleFactors.add(1);
        }

        return scaleFactors;
    }

    private int gcd(final int aWidth, final int aHeight) {
        final int gcd;

        if (aHeight == 0) {
            gcd = aWidth;
        } else {
            gcd = gcd(aHeight, aWidth % aHeight);
        }

        return gcd;
    }

}

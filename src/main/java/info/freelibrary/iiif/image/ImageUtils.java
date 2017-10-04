
package info.freelibrary.iiif.image;

import static info.freelibrary.iiif.image.Constants.BUNDLE_NAME;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.IOUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A utility class for some common image processing needs.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class, BUNDLE_NAME);

    /* Template for the region part of the IIIF request */
    private static final String REGION = "{},{},{},{}";

    /* All out-of-the-box tiles are not rotated */
    private static final String LABEL = "0/default.jpg";

    private ImageUtils() {
    }

    /**
     * Return a list of derivative images to be pre-generated so that the OpenSeadragon viewer can use them.
     *
     * @param aService A IIIF service name
     * @param aID An image ID
     * @param aTileSize A tile size
     * @param aWidth An image width
     * @param aHeight An image height
     * @return A list of derivative images to be pre-generated
     */
    public static List<String> getTilePaths(final String aService, final String aID, final int aTileSize,
            final double aWidth, final double aHeight) {
        return getTilePaths(aService, aID, aTileSize, (int) aWidth, (int) aHeight);
    }

    /**
     * Return a list of derivative images to be pre-generated so that the OpenSeadragon viewer can use them.
     *
     * @param aService A IIIF service name
     * @param aID An image ID
     * @param aTileSize A tile size
     * @param aWidth An image width
     * @param aHeight An image height
     * @return A list of derivative images to be pre-generated
     */
    public static List<String> getTilePaths(final String aService, final String aID, final int aTileSize,
            final int aWidth, final int aHeight) {
        final ArrayList<String> list = new ArrayList<>();
        final int longDim = Math.max(aWidth, aHeight);
        final String id;

        // Object ID may need to be URL encoded for use on the Web
        try {
            id = URLEncoder.encode(aID, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException details) {
            throw new RuntimeException(details); // All JVMs required to support UTF-8
        }

        LOGGER.debug(MessageCodes.DBG_094, aID, aTileSize, aWidth, aHeight);

        for (int multiplier = 1; (multiplier * aTileSize) < longDim; multiplier *= 2) {
            final int tileSize = multiplier * aTileSize;

            int x = 0;
            int y = 0;
            int xTileSize;
            int yTileSize;

            String region;
            String path;
            String size;

            LOGGER.debug(MessageCodes.DBG_095, multiplier);

            for (x = 0; x < (aWidth + tileSize); x += tileSize) {
                xTileSize = (x + tileSize) < aWidth ? tileSize : aWidth - x;
                yTileSize = tileSize < aHeight ? tileSize : aHeight;

                if ((xTileSize > 0) && (yTileSize > 0)) {
                    region = StringUtils.format(REGION, x, y, xTileSize, yTileSize);
                    size = getSize(multiplier, xTileSize, yTileSize);

                    // Tiles always maintain the aspect ratio so use the canonical URI syntax
                    size = size.substring(0, size.indexOf(',') + 1);

                    path = StringUtils.toString('/', aService, id, region, size, LABEL);

                    if (!list.add(path)) {
                        LOGGER.warn(MessageCodes.WARN_015, path);
                        LOGGER.warn(StringUtils.toString('/', aService, id, region, size, LABEL));
                    }
                }

                for (y = tileSize; y < (aHeight + tileSize); y += tileSize) {
                    xTileSize = (x + tileSize) < aWidth ? tileSize : aWidth - x;
                    yTileSize = (y + tileSize) < aHeight ? tileSize : aHeight - y;

                    if ((xTileSize > 0) && (yTileSize > 0)) {
                        region = StringUtils.format(REGION, x, y, xTileSize, yTileSize);
                        size = getSize(multiplier, xTileSize, yTileSize);

                        // Tiles always maintain the aspect ratio so use the canonical URI syntax
                        size = size.substring(0, size.indexOf(',') + 1);

                        path = StringUtils.toString('/', aService, id, region, size, LABEL);

                        if (!list.add(path)) {
                            LOGGER.warn(MessageCodes.WARN_015, path);
                        }
                    }
                }

                y = 0;
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MessageCodes.DBG_096, list.size(), aID);

            for (final Object path : list.toArray()) {
                LOGGER.debug(MessageCodes.DBG_097, path);
            }
        }

        Collections.reverse(list);
        return list;
    }

    /**
     * This gets the image dimension without reading the whole file into memory.
     *
     * @param aImageFile A file from which to pull dimension
     * @return An image dimension
     * @throws IOException If there is trouble reading the dimensions from the supplied image file
     */
    public static Dimension getImageDimension(final File aImageFile) throws IOException, UnsupportedFormatException {
        final String mimeType = Format.getMimeType(FileUtils.getExt(aImageFile.getName()));
        // final String mimeType = Format.getMimeType(FileUtils.getExt(aImageFile.getName()));
        final Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType(mimeType);

        LOGGER.debug(MessageCodes.DBG_098, aImageFile);

        if (readers.hasNext()) {
            final ImageReader reader = readers.next();
            final ImageInputStream inStream = ImageIO.createImageInputStream(aImageFile);

            try {
                reader.setInput(inStream);
                return new Dimension(reader.getWidth(0), reader.getHeight(0));
            } finally {
                IOUtils.closeQuietly(inStream);
                reader.dispose();
            }
        }

        throw new RuntimeException(LOGGER.getMessage(MessageCodes.EXC_026, mimeType));
    }

    /**
     * Gets the scale factors for an image.
     *
     * @param aWidth An image width
     * @param aHeight An image height
     * @param aTileSize A tile size
     * @return The scale factors for an image with the supplied characteristics
     */
    public static List<Integer> getScaleFactors(final int aWidth, final int aHeight, final int aTileSize) {
        final int longDimension = Math.max(aWidth, aHeight);
        final List<Integer> scaleFactors = new ArrayList<>();

        for (int multiplier = 1; (multiplier * aTileSize) < longDimension; multiplier *= 2) {
            scaleFactors.add(multiplier);
        }

        return scaleFactors;
    }

    /**
     * Gets the center of an image.
     *
     * @param aImageFile A source image file
     * @return An image region representing the center of the image
     * @throws IOException If there is trouble reading the source image file
     */
    public static Region getCenter(final File aImageFile) throws IOException, UnsupportedFormatException {
        return getCenter(getImageDimension(aImageFile));
    }

    /**
     * Gets the center of an image, represented by a {@link java.awt.Dimension} object.
     *
     * @param aDimension Dimensions to use as the source for the calculation of center
     * @return An image region representing the center of the dimensions
     */
    public static Region getCenter(final Dimension aDimension) {
        final int smallSide = Math.min(aDimension.height, aDimension.width);
        final Region region;

        if (smallSide == aDimension.height) {
            region = new Region((aDimension.width - smallSide) / 2, 0, smallSide, smallSide);
        } else {
            region = new Region(0, (aDimension.height - smallSide) / 2, smallSide, smallSide);
        }

        return region;
    }

    /**
     * Gets the ratio of the supplied width and height.
     *
     * @param aWidth Width to use in getting the ratio
     * @param aHeight Height to use in getting the ratio
     * @return A string representation of the ratio
     */
    public static String ratio(final int aWidth, final int aHeight) {
        final int gcd = gcd(aWidth, aHeight);
        return (aHeight / gcd) + ":" + (aHeight / gcd);
    }

    /**
     * Gets the ratio from the supplied IIIF size string.
     *
     * @param aSize A IIIF image size string
     * @return A string representation of the ratio
     */
    public static String ratio(final String aSize) {
        final String[] widthHeight = aSize.split("\\,");

        if (widthHeight.length != 2) {
            throw new IllegalArgumentException(LOGGER.getMessage(MessageCodes.EXC_073, aSize));
        }

        return ratio(Integer.parseInt(widthHeight[0]), Integer.parseInt(widthHeight[1]));
    }

    private static String getSize(final double aMultiplier, final int aXTileSize, final int aYTileSize) {
        return (int) Math.ceil(aXTileSize / aMultiplier) + "," + (int) Math.ceil(aYTileSize / aMultiplier);
    }

    private static int gcd(final int aWidth, final int aHeight) {
        if (aHeight == 0) {
            return aWidth;
        } else {
            return gcd(aHeight, aWidth % aHeight);
        }
    }
}

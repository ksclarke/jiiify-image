
package info.freelibrary.iiif.image.util;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A utility class for some common image processing needs.
 */
public final class Tiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tiler.class, BUNDLE_NAME);

    /* Template for the region part of the IIIF request */
    private static final String REGION = "{},{},{},{}";

    /* All out-of-the-box tiles are not rotated */
    private static final String DEFAULT_LABEL = "0/default.jpg";

    private Tiler() {
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
    public static List<String> getPaths(final String aService, final String aID, final int aTileSize,
            final double aWidth, final double aHeight) {
        return getPaths(aService, aID, aTileSize, (int) aWidth, (int) aHeight);
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
    public static List<String> getPaths(final String aService, final String aID, final int aTileSize,
            final int aWidth, final int aHeight) {
        final String id;

        // Object ID may need to be URL encoded for use on the Web
        try {
            id = URLEncoder.encode(aID, StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException details) {
            throw new info.freelibrary.util.UnsupportedEncodingException(details);
        }

        LOGGER.debug(MessageCodes.DBG_094, aID, aTileSize, aWidth, aHeight);

        final ArrayList<String> list = new ArrayList<>();
        final int longDim = Math.max(aWidth, aHeight);

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

                    path = StringUtils.toString('/', aService, id, region, size, DEFAULT_LABEL);

                    if (!list.add(path)) {
                        LOGGER.warn(MessageCodes.WARN_015, path);
                        LOGGER.warn(StringUtils.toString('/', aService, id, region, size, DEFAULT_LABEL));
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

                        path = StringUtils.toString('/', aService, id, region, size, DEFAULT_LABEL);

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

    private static String getSize(final double aMultiplier, final int aXTileSize, final int aYTileSize) {
        return (int) Math.ceil(aXTileSize / aMultiplier) + "," + (int) Math.ceil(aYTileSize / aMultiplier);
    }

}


package info.freelibrary.iiif.image.util;

/**
 * A class of constant values used in the IIIF image library.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public final class Constants {

    /**
     * Name of I18n message bundle.
     */
    public static final String BUNDLE_NAME = "iiif_image_messages";

    public static final String WIDTH = "width";

    public static final String HEIGHT = "height";

    public static final String SCALE_FACTORS = "scaleFactors";

    public static final String ID = "@id";

    public static final String TYPE = "@type";

    public static final String TYPE_VALUE = "iiif:Image";

    public static final String SERVICE = "service";

    public static final String SIZES = "sizes";

    public static final String TILES = "tiles";

    public static final String FORMATS = "formats";

    public static final String QUALITIES = "qualities";

    public static final String CONTEXT = "@context";

    public static final String CONTEXT_URI = "http://iiif.io/api/image/2/context.json";

    public static final String PROTOCOL = "protocol";

    public static final String PROTOCOL_URI = "http://iiif.io/api/image";

    public static final String PROFILE = "profile";

    public static final String MAX_AREA = "maxArea";

    public static final String MAX_HEIGHT = "maxHeight";

    public static final String MAX_WIDTH = "maxWidth";

    public static final String SUPPORTS = "supports";

    /**
     * Private constructor since class is never instantiated.
     */
    private Constants() {
    }

}

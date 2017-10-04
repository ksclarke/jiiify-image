
package info.freelibrary.iiif.image;

import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.IIIFRuntimeException;
import info.freelibrary.util.StringUtils;

/**
 * An image format that represents the types of image formats supported by IIIF.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public enum Format {

    JPG("image/jpeg"),
    TIF("image/tiff"),
    PNG("image/png"),
    GIF("image/gif"),
    JP2("image/jp2"),
    PDF("application/pdf"),
    WEBP("image/webp");

    private static final String VALUES = StringUtils.toString(' ', values());

    private final String myValue;

    /**
     * Creates a image format.
     *
     * @param aValue A value of the image format
     */
    Format(final String aValue) {
        myValue = aValue;
    }

    @Override
    public String toString() {
        return getExtension();
    }

    /**
     * Gets the format's MIME type.
     *
     * @return The MIME type of the format
     */
    public String getMimeType() {
        return myValue;
    }

    /**
     * Gets the format's supported extension.
     *
     * @return The extension of the format
     */
    @JsonValue
    public String getExtension() {
        for (final Format format : values()) {
            if (format.myValue.equals(myValue)) {
                return format.name().toLowerCase(Locale.US);
            }
        }

        throw new IIIFRuntimeException("Didn't find a matching ext");
    }

    /**
     * Gets the preferred file extension for the supplied MIME type.
     *
     * @param aMimeType A MIME type for which to return a file extension
     * @return A file extension for the supplied MIME type
     * @throws UnsupportedFormatException If the supplied media type doesn't map to a known format
     */
    public static final String getExtension(final String aMimeType) throws UnsupportedFormatException {
        return parseMimeType(aMimeType).getExtension();
    }

    /**
     * Gets the MIME type for the supplied file extension.
     *
     * @param aFileExt A file extension for which to return a MIME type
     * @return A MIME type for the supplied file extension
     * @throws UnsupportedFormatException If the supplied file extension doesn't map to a know format
     */
    public static final String getMimeType(final String aFileExt) throws UnsupportedFormatException {
        return parseExtension(aFileExt).myValue;
    }

    /**
     * Parse a string value into an image Format.
     *
     * @param aValue A format string value
     * @return An image Format
     * @throws UnsupportedFormatException If the supplied string value is not one of the supported Format values
     */
    public static Format parseExtension(final String aValue) throws UnsupportedFormatException {
        Objects.requireNonNull(aValue, "");

        final String ext = (aValue.charAt(0) == '.') ? aValue.substring(1) : aValue;
        final Format[] values = values();

        for (final Format format : values) {
            if (format.name().equalsIgnoreCase(ext)) {
                return format;
            }
        }

        throw new UnsupportedFormatException(MessageCodes.EXC_012, ext, values.length, VALUES);
    }

    /**
     * Parse a string value into an image Format.
     *
     * @param aValue A format string value
     * @return An image Format
     * @throws UnsupportedFormatException If the supplied string value is not one of the supported Format values
     */
    public static Format parseMimeType(final String aValue) throws UnsupportedFormatException {
        Objects.requireNonNull(aValue, "");

        final Format[] values = values();

        for (final Format format : values) {
            if (format.myValue.equalsIgnoreCase(aValue)) {
                return format;
            }
        }

        throw new UnsupportedFormatException(MessageCodes.EXC_012, aValue, values.length, VALUES);
    }

}

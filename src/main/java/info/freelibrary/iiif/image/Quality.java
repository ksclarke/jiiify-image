
package info.freelibrary.iiif.image;

import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.util.StringUtils;

public enum Quality {

    DEFAULT("default"), COLOR("color"), GRAY("gray"), BITONAL("bitonal");

    private static final String VALUES = StringUtils.toString(' ', values());

    private final String myValue;

    Quality(final String aValue) {
        myValue = aValue;
    }

    @Override
    @JsonValue
    public String toString() {
        return myValue;
    }

    /**
     * Parse a string value into an image Quality.
     *
     * @param aValue A quality string value
     * @return An image Quality
     * @throws UnsupportedFormatException If the supplied string value is not one of the supported Quality values
     */
    public static Quality parse(final String aValue) throws UnsupportedQualityException {
        final Quality[] values = values();

        for (final Quality quality : values) {
            if (quality.name().equalsIgnoreCase(aValue)) {
                return quality;
            }
        }

        throw new UnsupportedQualityException(MessageCodes.EXC_012, aValue, values.length, VALUES);
    }

}

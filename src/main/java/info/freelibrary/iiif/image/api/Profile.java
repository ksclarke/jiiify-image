
package info.freelibrary.iiif.image.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;

import info.freelibrary.iiif.image.util.Constants;

/**
 * A profile description.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Profile {

    /**
     * The features a profile might support.
     *
     * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
     */
    enum Feature {
        baseUriRedirect,
        canonicalLinkHeader,
        cors,
        jsonldMediaType,
        mirroring,
        profileLinkHeader,
        regionByPct,
        regionByPx,
        regionSquare,
        rotationArbitrary,
        rotationBy90s,
        sizeAboveFull,
        sizeByConfinedWh,
        sizeByDistortedWh,
        sizeByH,
        sizeByPct,
        sizeByW,
        sizeByWh
    }

    private final APIComplianceLevel myLevel;

    private final List<Format> myFormats;

    private final List<Quality> myQualities;

    /**
     * Creates a profile from the supplied IIIF API compliance level.
     *
     * @param aLevel A IIIF API compliance level
     */
    public Profile(final APIComplianceLevel aLevel) {
        myLevel = aLevel;
        myFormats = new ArrayList<>();
        myQualities = new ArrayList<>();
    }

    /**
     * Gets the profile's API compliance level.
     *
     * @return The profile's API compliance level
     */
    @JsonIgnore
    public String getLevel() {
        return myLevel.toString();
    }

    /**
     * Gets the formats the profile supports.
     *
     * @return The formats the profile supports
     */
    @JsonIgnore
    public List<Format> getFormats() {
        return myFormats;
    }

    /**
     * Sets the profile's supported formats.
     *
     * @param aFormat Formats the profile supports
     * @return The profile itself
     */
    @JsonSetter
    public Profile setFormats(final Format... aFormat) {
        Objects.requireNonNull(aFormat, "");

        for (final Format format : aFormat) {
            Objects.requireNonNull(format, "");
            myFormats.add(format);
        }

        return this;
    }

    /**
     * Gets the qualities the profile supports.
     *
     * @return The qualities the profile supports
     */
    @JsonIgnore
    public List<Quality> getQualities() {
        return myQualities;
    }

    /**
     * Sets the profile's supported qualities.
     *
     * @param aQuality Qualities the profile supports
     * @return The profile itself
     */
    @JsonSetter
    public Profile setQualities(final Quality... aQuality) {
        Objects.requireNonNull(aQuality, "");

        for (final Quality quality : aQuality) {
            Objects.requireNonNull(quality, "");
            myQualities.add(quality);
        }

        return this;
    }

    /**
     * Gets the structure of the profile in a IIIF JSON friendly way.
     *
     * @return The profile expressed for JSON serialization
     */
    @JsonValue
    private Object asJson() {
        final List<Object> json = new ArrayList<>();
        final Map<String, List<?>> map = new HashMap<>();

        json.add(myLevel.toURL());

        if (!myFormats.isEmpty()) {
            map.put(Constants.FORMATS, myFormats);
        }

        if (!myQualities.isEmpty()) {
            map.put(Constants.QUALITIES, myQualities);
        }

        json.add(map);

        return json;
    }

}

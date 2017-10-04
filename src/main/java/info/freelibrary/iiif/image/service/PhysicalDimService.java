
package info.freelibrary.iiif.image.service;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * A physical dimension service.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonPropertyOrder({ PhysicalDimConstants.CONTEXT_PROPERTY, PhysicalDimConstants.PROFILE_PROPERTY,
    PhysicalDimConstants.PHYSICAL_SCALE, PhysicalDimConstants.PHYSICAL_UNITS })
public class PhysicalDimService extends Service {

    private final double myPhysicalScale;

    private final String myPhysicalScaleUnits;

    /**
     * Creates a physical dimension service representation.
     *
     * @param aPhysicalScale A physical scale
     * @param aPhysicalScaleUnits A unit for the physical scale
     */
    public PhysicalDimService(final double aPhysicalScale, final String aPhysicalScaleUnits) {
        myPhysicalScale = aPhysicalScale;
        myPhysicalScaleUnits = aPhysicalScaleUnits;
    }

    /**
     * Gets the service context.
     *
     * @return The context of the service
     */
    @Override
    @JsonGetter(PhysicalDimConstants.CONTEXT_PROPERTY)
    public String getContext() {
        return PhysicalDimConstants.CONTEXT;
    }

    /**
     * Gets the service profile.
     *
     * @return The profile of the service
     */
    @JsonGetter(PhysicalDimConstants.PROFILE_PROPERTY)
    public String getProfile() {
        return PhysicalDimConstants.PROFILE;
    }

    /**
     * Gets the physical scale conveyed by the service.
     *
     * @return The physical scale
     */
    @JsonGetter(PhysicalDimConstants.PHYSICAL_SCALE)
    public double getPhysicalScale() {
        return myPhysicalScale;
    }

    /**
     * Gets the physical scale units conveyed by the service.
     *
     * @return The physical scale units
     */
    @JsonGetter(PhysicalDimConstants.PHYSICAL_UNITS)
    public String getPhysicalScaleUnits() {
        return myPhysicalScaleUnits;
    }

}

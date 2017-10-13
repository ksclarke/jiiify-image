
package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.image.api.service.Service;
import info.freelibrary.iiif.image.util.Constants;
import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * IIIF image info document.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.TYPE, Constants.PROTOCOL, Constants.WIDTH,
    Constants.HEIGHT, Constants.SIZES, Constants.TILES, Constants.PROFILE })
public class ImageInfo {

    public static final String FILE_NAME = "info.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageInfo.class, BUNDLE_NAME);

    private String myID;

    private int myWidth;

    private int myHeight;

    private int myTileSize;

    private List<Service> myServices;

    private Profile myProfile;

    /**
     * Creates an image info object from the supplied image ID.
     *
     * @param aID The image ID of the image info object
     */
    public ImageInfo(final String aID) {
        LOGGER.debug(MessageCodes.DBG_070, aID);
        myID = aID;
    }

    /**
     * Sets the ID of the image info object.
     *
     * @param aID The ID of the image info object
     * @return The image info object with the set ID
     */
    @JsonSetter(Constants.ID)
    public ImageInfo setID(final String aID) {
        myID = aID;
        return this;
    }

    /**
     * Gets the ID of the image info object.
     *
     * @return The ID of the image info object
     */
    @JsonGetter(Constants.ID)
    public String getID() {
        return myID;
    }

    /**
     * Sets the width of the image info object.
     *
     * @param aWidth The width of the image info object
     * @return The image info object with width set
     */
    @JsonSetter(Constants.WIDTH)
    public ImageInfo setWidth(final int aWidth) {
        myWidth = aWidth;
        return this;
    }

    /**
     * Gets the width of the image info object.
     *
     * @return The image info object's width
     */
    @JsonGetter(Constants.WIDTH)
    public int getWidth() {
        return myWidth;
    }

    /**
     * Sets the height of the image info object.
     *
     * @param aHeight The height of the image info object
     * @return The image info object with height set
     */
    @JsonSetter(Constants.HEIGHT)
    public ImageInfo setHeight(final int aHeight) {
        myHeight = aHeight;
        return this;
    }

    /**
     * Gets the height of the image info object.
     *
     * @return The image info object's height
     */
    @JsonGetter(Constants.HEIGHT)
    public int getHeight() {
        return myHeight;
    }

    /**
     * Sets the tile size of the image info object.
     *
     * @param aTileSize The tile size of the image info object
     * @return The image info object with the tile size set
     */
    @JsonIgnore
    public ImageInfo setTileSize(final int aTileSize) {
        myTileSize = aTileSize;
        return this;
    }

    /**
     * Gets the tile size of the image info object
     *
     * @return The image info object's tile size
     */
    @JsonIgnore
    public int getTileSize() {
        return myTileSize;
    }

    /**
     * Sets the services for this image info object.
     *
     * @param aService A list of services to set
     * @return The image info object
     */
    @JsonSetter(Constants.SERVICE)
    public ImageInfo setServices(final Service... aService) {
        if (myServices != null) {
            myServices.clear();
        }

        return addServices(aService);
    }

    /**
     * Adds the supplied services to this image info object.
     *
     * @param aService A list of services to add
     * @return The image info object
     */
    @JsonIgnore
    public ImageInfo addServices(final Service... aService) {
        Objects.requireNonNull(aService, LOGGER.getMessage(MessageCodes.EXC_086, Service.class.getSimpleName()));

        if (myServices == null) {
            myServices = new ArrayList<>();
        }

        for (final Service service : aService) {
            Objects.requireNonNull(service, LOGGER.getMessage(MessageCodes.EXC_086, Service.class.getSimpleName()));
            myServices.add(service);
        }

        return this;
    }

    /**
     * Gets the services encapsulated in the image info object.
     *
     * @return The image info services
     */
    @JsonIgnore
    public List<Service> getServices() {
        return myServices;
    }

    /**
     * Gets image info profile.
     *
     * @return The image info profile
     */
    @JsonGetter(Constants.PROFILE)
    public Profile getProfile() {
        return myProfile;
    }

    /**
     * Sets the image info profile.
     *
     * @param aProfile A profile to set
     * @return The image info object
     */
    @JsonSetter(Constants.PROFILE)
    public ImageInfo setProfile(final Profile aProfile) {
        Objects.requireNonNull(aProfile, LOGGER.getMessage(MessageCodes.EXC_086, Profile.class.getSimpleName()));

        myProfile = aProfile;
        return this;
    }

    /**
     * Gets the Image API context.
     *
     * @return The Image API context
     */
    @JsonGetter(Constants.CONTEXT)
    public String getContext() {
        return Constants.CONTEXT_URI;
    }

    /**
     * Gets the Image API protocol.
     *
     * @return The Image API protocol
     */
    @JsonGetter(Constants.PROTOCOL)
    public String getProtocol() {
        return Constants.PROTOCOL_URI;
    }

    /**
     * Gets the image info type.
     *
     * @return The image info type
     */
    @JsonGetter(Constants.TYPE)
    public String getType() {
        return Constants.TYPE_VALUE;
    }

    /**
     * Structures our service in IIIF-friendly friendly way.
     *
     * @return A service to be converted into JSON
     */
    @JsonGetter(Constants.SERVICE)
    private Object getServicesJson() {
        final Object result;

        if (myServices == null) {
            result = null;
        } else if (myServices.size() == 1) {
            result = myServices.get(0);
        } else {
            result = myServices;
        }

        return result;
    }

}

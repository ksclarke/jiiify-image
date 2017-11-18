
package info.freelibrary.iiif.image.api;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import info.freelibrary.iiif.image.api.service.Service;
import info.freelibrary.iiif.image.util.Constants;
import info.freelibrary.iiif.image.util.MessageCodes;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * IIIF image info document.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ Constants.CONTEXT, Constants.ID, Constants.TYPE, Constants.PROTOCOL, Constants.WIDTH,
    Constants.HEIGHT, Constants.SIZES, Constants.TILES, Constants.PROFILE })
public class ImageInfo {

    public static final String FILE_NAME = "info.json";

    public static final String MIME_TYPE = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageInfo.class, BUNDLE_NAME);

    private String myID;

    private int myWidth;

    private int myHeight;

    private int myTileSize;

    private List<Size> mySizes;

    private List<TileSet> myTiles;

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
     * Add a set of square tiles, specified using width and tiling scale factors.
     *
     * @param aWidth A tile width
     * @param aScaleFactors A list of tiling scale factors
     * @return The image info
     */
    @JsonIgnore
    public ImageInfo addTile(final int aWidth, final List<Integer> aScaleFactors) {
        if (!getTiles().add(new TileSet(aWidth, aScaleFactors))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Add a set of tiles, specified using width and tiling scale factors.
     *
     * @param aWidth A tile width
     * @param aHeight A tile height
     * @param aScaleFactors A list of tiling scale factors
     * @return The image info
     */
    @JsonIgnore
    public ImageInfo addTile(final int aWidth, final int aHeight, final List<Integer> aScaleFactors) {
        if (!getTiles().add(new TileSet(aWidth, aHeight, aScaleFactors))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Gets the image info tiles.
     *
     * @return The image info tiles
     */
    @JsonGetter(Constants.TILES)
    public List<TileSet> getTiles() {
        if (myTiles == null) {
            myTiles = new ArrayList<TileSet>();
        }

        return myTiles;
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
     * Gets the image info sizes.
     *
     * @return The image info sizes
     */
    @JsonGetter(Constants.SIZES)
    public List<Size> getSizes() {
        if (mySizes == null) {
            mySizes = new ArrayList<Size>();
        }

        return mySizes;
    }

    /**
     * Sets the image info sizes.
     *
     * @return The image info
     */
    @JsonSetter(Constants.SIZES)
    public ImageInfo setSizes(final List<Size> aSizesList) {
        mySizes = aSizesList;

        return this;
    }

    /**
     * Adds a size with width.
     *
     * @param aWidth A width of a size to add
     * @return The image info
     */
    public ImageInfo addSize(final int aWidth) {
        if (!getSizes().add(new Size(aWidth, 0))) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    /**
     * Adds a size with width and height.
     *
     * @param aWidth A width of a size to add
     * @param aHeight A height of a size to add
     * @return The image info
     */
    public ImageInfo addSize(final int aWidth, final int aHeight) {
        if (!getSizes().add(new Size(aWidth, aHeight))) {
            throw new UnsupportedOperationException();
        }

        return this;
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

    /**
     * The information one would need to create a set of tiles.
     */
    @JsonInclude(value = Include.NON_DEFAULT)
    @JsonPropertyOrder({ Constants.WIDTH, Constants.HEIGHT, Constants.SCALE_FACTORS })
    public class TileSet {

        private final int myWidth;

        private final int myHeight;

        private final List<Integer> myScaleFactors;

        /**
         * Creates a new representation of a tile set.
         *
         * @param aWidth The tile width
         * @param aHeight The tile height
         * @param aScaleFactors Scale factors used for creating tiles
         */
        public TileSet(final int aWidth, final int aHeight, final List<Integer> aScaleFactors) {
            myWidth = aWidth;
            myHeight = aHeight;
            myScaleFactors = aScaleFactors;
        }

        /**
         * Creates a new representation of a square tile set.
         *
         * @param aWidth The tile width (and height since it's square)
         * @param aScaleFactors Scale factors used for creating tiles
         */
        public TileSet(final int aWidth, final List<Integer> aScaleFactors) {
            myWidth = aWidth;
            myHeight = aWidth;
            myScaleFactors = aScaleFactors;
        }

        /**
         * Gets the tile width.
         *
         * @return The tile width
         */
        public int getWidth() {
            return myWidth;
        }

        /**
         * Gets the tile height.
         *
         * @return The tile height
         */
        @JsonIgnore
        public int getHeight() {
            return myHeight;
        }

        /**
         * The scale factors to be used when creating tiles.
         *
         * @return A list of scale factors
         */
        public List<Integer> getScaleFactors() {
            return myScaleFactors;
        }

        /**
         * We want more control over how this gets output in the JSON so we've "overriding" the default get for
         * height.
         *
         * @return The tile height or nothing if it's equal to the tile's width
         */
        @JsonGetter(Constants.HEIGHT)
        private int getJsonHeight() {
            return myHeight == myWidth ? 0 : myHeight;
        }
    }
}

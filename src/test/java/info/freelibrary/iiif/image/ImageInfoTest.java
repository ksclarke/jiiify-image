
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.image.service.PhysicalDimConstants;
import info.freelibrary.iiif.image.service.PhysicalDimService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * A test of {@link info.freelibrary.jiiify.iiif.ImageInfo}
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class ImageInfoTest {

    private static final String ID = "https://example.org/iiif/ark:%2F21198%2Fz1p84cpw";

    private static final int WIDTH = 8176;

    private static final int HEIGHT = 6132;

    private static final double PHYSICAL_SCALE = 0.0296037296;

    private static final String PHYSICAL_SCALE_UNIT = "mm";

    @Test
    public void testImageInfo() throws ImageException {
        final JsonObject expected = new JsonObject().put(Constants.CONTEXT, Constants.CONTEXT_URI);
        final ImageInfo info = new ImageInfo(ID);

        info.setWidth(WIDTH).setHeight(HEIGHT);
        info.addServices(new PhysicalDimService(PHYSICAL_SCALE, PHYSICAL_SCALE_UNIT));
        info.setProfile(new Profile(APIComplianceLevel.ZERO).setFormats(Format.JPG).setQualities(Quality.COLOR));

        expected.put(Constants.ID, ID).put(Constants.TYPE, Constants.TYPE_VALUE);
        expected.put(Constants.PROTOCOL, Constants.PROTOCOL_URI).put(Constants.WIDTH, WIDTH);
        expected.put(Constants.HEIGHT, HEIGHT);
        expected.put(Constants.PROFILE, new JsonArray().add(APIComplianceLevel.ZERO.toString()).add(new JsonObject()
                .put(Constants.FORMATS, new JsonArray().add(Format.JPG.toString())).put(Constants.QUALITIES,
                        new JsonArray().add(Quality.COLOR.toString()))));
        expected.put(Constants.SERVICE, new JsonObject().put(PhysicalDimConstants.CONTEXT_PROPERTY,
                PhysicalDimConstants.CONTEXT).put(PhysicalDimConstants.PROFILE_PROPERTY, PhysicalDimConstants.PROFILE)
                .put(PhysicalDimConstants.PHYSICAL_SCALE, PHYSICAL_SCALE).put(PhysicalDimConstants.PHYSICAL_UNITS,
                        PHYSICAL_SCALE_UNIT));

        assertEquals(expected, JsonObject.mapFrom(info));
    }

}

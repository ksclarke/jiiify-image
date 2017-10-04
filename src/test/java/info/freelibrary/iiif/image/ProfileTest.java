
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import info.freelibrary.iiif.image.APIComplianceLevel;
import info.freelibrary.iiif.image.Constants;
import info.freelibrary.iiif.image.Format;
import info.freelibrary.iiif.image.ImageInfo;
import info.freelibrary.iiif.image.Profile;
import info.freelibrary.iiif.image.Quality;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ProfileTest {

    private static final String ID = "FAKE_ID";

    private JsonObject myExpected;

    @Before
    public void setUp() throws Exception {
        myExpected = new JsonObject().put(Constants.CONTEXT, Constants.CONTEXT_URI).put(Constants.ID, ID);
        myExpected.put(Constants.TYPE, Constants.TYPE_VALUE).put(Constants.PROTOCOL, Constants.PROTOCOL_URI);
        myExpected.put(Constants.WIDTH, 0).put(Constants.HEIGHT, 0);
    }

    @Test
    public void testDoubleQualities() {
        final Profile profile = new Profile(APIComplianceLevel.ZERO).setQualities(Quality.COLOR, Quality.BITONAL)
                .setFormats(Format.JPG);
        final JsonObject found = JsonObject.mapFrom(new ImageInfo(ID).setProfile(profile));

        final JsonObject profileObj = new JsonObject().put(Constants.FORMATS, new JsonArray().add(Format.JPG
                .toString())).put(Constants.QUALITIES, new JsonArray().add(Quality.COLOR.toString()).add(
                        Quality.BITONAL.toString()));

        myExpected.put(Constants.PROFILE, new JsonArray().add(APIComplianceLevel.ZERO.toString()).add(profileObj));
        assertEquals(myExpected, found);
    }

    @Test
    public void testDoubleFormats() {
        final Profile profile = new Profile(APIComplianceLevel.ZERO).setFormats(Format.JPG, Format.GIF).setQualities(
                Quality.COLOR);
        final JsonObject found = JsonObject.mapFrom(new ImageInfo(ID).setProfile(profile));

        final JsonObject profileObj = new JsonObject().put(Constants.FORMATS, new JsonArray().add(Format.JPG
                .toString()).add(Format.GIF.toString())).put(Constants.QUALITIES, new JsonArray().add(Quality.COLOR
                        .toString()));

        myExpected.put(Constants.PROFILE, new JsonArray().add(APIComplianceLevel.ZERO.toString()).add(profileObj));
        assertEquals(myExpected, found);
    }

    @Test
    public void testDoubleFormatsQualities() {
        final Profile profile = new Profile(APIComplianceLevel.ZERO).setFormats(Format.JPG, Format.GIF).setQualities(
                Quality.COLOR, Quality.GRAY);
        final JsonObject found = JsonObject.mapFrom(new ImageInfo(ID).setProfile(profile));

        final JsonObject profileObj = new JsonObject().put(Constants.FORMATS, new JsonArray().add(Format.JPG
                .toString()).add(Format.GIF.toString())).put(Constants.QUALITIES, new JsonArray().add(Quality.COLOR
                        .toString()).add(Quality.GRAY.toString()));

        myExpected.put(Constants.PROFILE, new JsonArray().add(APIComplianceLevel.ZERO.toString()).add(profileObj));
        assertEquals(myExpected, found);
    }

}

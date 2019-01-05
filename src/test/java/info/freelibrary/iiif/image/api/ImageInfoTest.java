
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import info.freelibrary.iiif.image.api.service.PhysicalDimService;
import info.freelibrary.util.StringUtils;

import io.vertx.core.json.JsonObject;

/**
 * A test of {@link info.freelibrary.jiiify.iiif.ImageInfo}
 */
public class ImageInfoTest {

    private static final File[] EXPECTED = new File[] { new File("src/test/resources/json/info-1.json"), new File(
            "src/test/resources/json/info-2.json"), new File("src/test/resources/json/info-3.json") };

    private static final String ID = "https://example.org/iiif/ark:%2F21198%2Fz1p84cpw";

    private static final int WIDTH = 8176;

    private static final int HEIGHT = 6132;

    private static final double PHYSICAL_SCALE = 0.0296037296;

    private static final String PHYSICAL_SCALE_UNIT = "mm";

    @Test
    public void testImageInfo() throws ImageException, IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(EXPECTED[0]));
        final ImageInfo info = new ImageInfo(ID);

        info.setWidth(WIDTH).setHeight(HEIGHT);
        info.addServices(new PhysicalDimService(PHYSICAL_SCALE, PHYSICAL_SCALE_UNIT));
        info.setProfile(new Profile(APIComplianceLevel.ZERO).setFormats(Format.JPG).setQualities(Quality.COLOR));
        info.addSize(100, 100);

        assertEquals(expected, JsonObject.mapFrom(info));
    }

    @Test
    public void testImageInfoSizes() throws ImageException, IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(EXPECTED[1]));
        final ImageInfo info = new ImageInfo(ID);

        info.setWidth(1000).setHeight(1000);
        info.addSize(100, 100).addSize(150, 150);

        assertEquals(expected, JsonObject.mapFrom(info));
    }

    @Test
    public void testImageInfoTiles() throws ImageException, IOException {
        final JsonObject expected = new JsonObject(StringUtils.read(EXPECTED[2]));
        final ImageInfo info = new ImageInfo(ID);

        info.setWidth(6300).setHeight(4500);
        info.addTile(1024, Arrays.asList(new Integer[] { 1, 2, 4, 8, 16 }));

        assertEquals(expected, JsonObject.mapFrom(info));
    }

    @Test
    public void testSizesEmpty() {
        assertTrue(new ImageInfo(ID).getSizes().isEmpty());
    }

    @Test
    public void testSizesAdd() {
        final ImageInfo info = new ImageInfo(ID);
        final List<Size> sizes = info.addSize(100, 100).getSizes();
        final Size size = sizes.get(0);

        assertEquals(1, sizes.size());
        assertEquals(100, size.getWidth());
        assertEquals(100, size.getHeight());
    }

    @Test
    public void testTileSetGetHeight() {
        final List<Integer> scaleFactors = Arrays.asList(new Integer[] { 1, 2, 4, 8 });
        final ImageInfo.TileSet tileSet = new ImageInfo(ID).new TileSet(5000, 5000, scaleFactors);

        assertEquals(5000, tileSet.getHeight());
    }

    @Test
    public void testSizesAddWidth() {
        final ImageInfo info = new ImageInfo(ID);
        final List<Size> sizes = info.addSize(100, 0).getSizes();
        final Size size = sizes.get(0);

        assertEquals(1, sizes.size());
        assertEquals(100, size.getWidth());
        assertEquals(0, size.getHeight());
    }

    @Test
    public void testSizesTwo() {
        final ImageInfo info = new ImageInfo(ID);
        final List<Size> sizes = info.addSize(100, 100).addSize(500, 500).getSizes();

        assertEquals(2, sizes.size());
        assertEquals(100, sizes.get(0).getWidth());
        assertEquals(500, sizes.get(1).getWidth());
    }
}

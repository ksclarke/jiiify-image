
package info.freelibrary.iiif.image.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.Test;

public class TilerTest {

    private static final String PREFIX = "/iiif";

    private static final String ID = "asdf";

    private static final int TILE_SIZE = 500;

    private static final int WIDTH = 2000;

    private static final int HEIGHT = 2000;

    private static final String[] PATHS = new String[] { "/iiif/asdf/1000,1000,1000,1000/500,/0/default.jpg",
        "/iiif/asdf/1000,0,1000,1000/500,/0/default.jpg", "/iiif/asdf/0,1000,1000,1000/500,/0/default.jpg",
        "/iiif/asdf/0,0,1000,1000/500,/0/default.jpg", "/iiif/asdf/1500,1500,500,500/500,/0/default.jpg",
        "/iiif/asdf/1500,1000,500,500/500,/0/default.jpg", "/iiif/asdf/1500,500,500,500/500,/0/default.jpg",
        "/iiif/asdf/1500,0,500,500/500,/0/default.jpg", "/iiif/asdf/1000,1500,500,500/500,/0/default.jpg",
        "/iiif/asdf/1000,1000,500,500/500,/0/default.jpg", "/iiif/asdf/1000,500,500,500/500,/0/default.jpg",
        "/iiif/asdf/1000,0,500,500/500,/0/default.jpg", "/iiif/asdf/500,1500,500,500/500,/0/default.jpg",
        "/iiif/asdf/500,1000,500,500/500,/0/default.jpg", "/iiif/asdf/500,500,500,500/500,/0/default.jpg",
        "/iiif/asdf/500,0,500,500/500,/0/default.jpg", "/iiif/asdf/0,1500,500,500/500,/0/default.jpg",
        "/iiif/asdf/0,1000,500,500/500,/0/default.jpg", "/iiif/asdf/0,500,500,500/500,/0/default.jpg",
        "/iiif/asdf/0,0,500,500/500,/0/default.jpg" };

    @Test
    public void testGetPathsStringStringIntDoubleDouble() {
        final List<String> paths = Tiler.getPaths(PREFIX, ID, TILE_SIZE, (double) WIDTH, (double) HEIGHT);
        assertArrayEquals(PATHS, paths.toArray(new String[paths.size()]));
    }

    @Test
    public void testGetPathsStringStringIntIntInt() {
        final List<String> paths = Tiler.getPaths(PREFIX, ID, TILE_SIZE, WIDTH, HEIGHT);
        assertArrayEquals(PATHS, paths.toArray(new String[paths.size()]));
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        final Constructor<Tiler> constructor = Tiler.class.getDeclaredConstructor();
        assertTrue("Constructor should be private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}

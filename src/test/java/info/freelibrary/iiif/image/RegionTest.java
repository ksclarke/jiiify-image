
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import info.freelibrary.iiif.image.Region.Coordinate;

/**
 * A test of {@link info.freelibrary.Region.iiif.ImageRegion}
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class RegionTest {

    @Test
    public void testParse() {
        try {
            Region.parse("0,0,100,100");
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }

        // Check value of the static final FULL
        assertEquals("full", Region.FULL);

        // Check our Region enumeration values
        assertEquals(4, Coordinate.values().length);
        assertEquals(Coordinate.X, Coordinate.valueOf("X"));
        assertEquals(Coordinate.Y, Coordinate.valueOf("Y"));
        assertEquals(Coordinate.WIDTH, Coordinate.valueOf("WIDTH"));
        assertEquals(Coordinate.HEIGHT, Coordinate.valueOf("HEIGHT"));
    }

    @Test
    public void testParseBadString() {
        try {
            Region.parse("0,0,100,bad");
        } catch (final InvalidRegionException details) {
            // Expected exception
        }

        try {
            Region.parse("0,0,100,100,0");
        } catch (final InvalidRegionException details) {
            // Expected exception
        }
    }

    @Test
    public void testParseEmptyDimensions() {
        try {
            Region.parse(null);
        } catch (final InvalidRegionException details) {
            // Expected exception
        }

        try {
            Region.parse("");
        } catch (final InvalidRegionException details) {
            // Expected exception
        }
    }

    @Test
    public void testGetInt() {
        try {
            final Region region = Region.parse("10,20,60,80");

            assertEquals(10, region.getInt(Coordinate.X));
            assertEquals(20, region.getInt(Coordinate.Y));
            assertEquals(60, region.getInt(Coordinate.WIDTH));
            assertEquals(80, region.getInt(Coordinate.HEIGHT));
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testGetFloat() {
        try {
            final Region region = Region.parse("10,20,60,80");

            assertEquals(10f, region.getFloat(Coordinate.X), 0.0000);
            assertEquals(20f, region.getFloat(Coordinate.Y), 0.0000);
            assertEquals(60f, region.getFloat(Coordinate.WIDTH), 0.0000);
            assertEquals(80f, region.getFloat(Coordinate.HEIGHT), 0.0000);
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testUsesPercentages() {
        try {
            assertTrue(Region.parse("pct:100,100,100,100").usesPercentages());
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }

        try {
            assertFalse(Region.parse("100,100,100,100").usesPercentages());
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }
    }

    @Test
    public void testToString() throws InvalidRegionException {
        assertEquals("100,100,100,100", Region.parse("100,100,100,100").toString());
        assertEquals("10,10,10,10", Region.parse("10.5,10.5,10.5,10.5").toString());
        assertEquals("pct:10.5,10.5,10.5,10.5", Region.parse("pct:10.5,10.5,10.5,10.5").toString());
        assertEquals("full", Region.parse("pct:100,100,100,100").toString());
        assertEquals("pct:50,50,50,50", Region.parse("pct:50,50,50,50").toString());
    }

    @Test
    public void testIsFullImage() {
        try {
            assertTrue(Region.parse("pct:100,100,100,100").isFullImage());
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }

        try {
            assertFalse(Region.parse("pct:100,99,100,100").isFullImage());
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }

        try {
            assertTrue(Region.parse(Region.FULL).isFullImage());
        } catch (final InvalidRegionException details) {
            fail(details.getMessage());
        }
    }

}

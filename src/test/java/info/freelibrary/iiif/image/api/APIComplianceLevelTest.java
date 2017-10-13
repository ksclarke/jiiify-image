
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.Test;

import info.freelibrary.iiif.image.api.APIComplianceLevel;

public class APIComplianceLevelTest {

    private static final String LEVEL_ZERO = "http://iiif.io/api/image/2/level0.json";

    private static final String LEVEL_ONE = "http://iiif.io/api/image/2/level1.json";

    private static final String LEVEL_TWO = "http://iiif.io/api/image/2/level2.json";

    @Test
    public void testToString() {
        assertEquals(LEVEL_ZERO, APIComplianceLevel.ZERO.toString());
        assertEquals(LEVEL_ONE, APIComplianceLevel.ONE.toString());
        assertEquals(LEVEL_TWO, APIComplianceLevel.TWO.toString());
    }

    @Test
    public void testToURI() {
        assertEquals(URI.create(LEVEL_ZERO), APIComplianceLevel.ZERO.toURI());
        assertEquals(URI.create(LEVEL_ONE), APIComplianceLevel.ONE.toURI());
        assertEquals(URI.create(LEVEL_TWO), APIComplianceLevel.TWO.toURI());
    }

    @Test
    public void testToURL() throws MalformedURLException {
        assertEquals(new URL(LEVEL_ZERO), APIComplianceLevel.ZERO.toURL());
        assertEquals(new URL(LEVEL_ONE), APIComplianceLevel.ONE.toURL());
        assertEquals(new URL(LEVEL_TWO), APIComplianceLevel.TWO.toURL());
    }

}

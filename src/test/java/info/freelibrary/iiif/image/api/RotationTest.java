
package info.freelibrary.iiif.image.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.freelibrary.iiif.image.api.InvalidRotationException;
import info.freelibrary.iiif.image.api.Rotation;

/**
 * Tests <code>ImageRotation</code>.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class RotationTest {

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test
    public void testImageRotationBasic() throws InvalidRotationException {
        assertEquals(0.0f, Rotation.parse("0").getValue(), 0.0f);
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test(expected = InvalidRotationException.class)
    public void testImageRotationLessThanZero() throws InvalidRotationException {
        Rotation.parse("-10");
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test(expected = InvalidRotationException.class)
    public void testImageRotationGreaterThan360() throws InvalidRotationException {
        Rotation.parse("361");
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test
    public void testImageRotationFloat() throws InvalidRotationException {
        final Rotation rotation = Rotation.parse("10.1");

        assertEquals(10.1f, rotation.getValue(), 0.0f);
        assertEquals(false, rotation.isMirrored());
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test
    public void testImageRotationFloatMirrored() throws InvalidRotationException {
        final Rotation rotation = Rotation.parse("!10.1");

        assertEquals(10.1f, rotation.getValue(), 0.0f);
        assertEquals(true, rotation.isMirrored());
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test
    public void testImageRotationAsString() throws InvalidRotationException {
        assertEquals("360.0", Float.toString(Rotation.parse("!360").getValue()));
    }

    /**
     * Test method for {@link info.freelibrary.Rotation.iiif.ImageRotation#ImageRotation(java.lang.String)}.
     */
    @Test(expected = InvalidRotationException.class)
    public void testImageRotationBadValue() throws InvalidRotationException {
        Rotation.parse("oops");
    }

    @Test
    public void testToString() throws InvalidRotationException {
        assertEquals("90", Rotation.parse("90").toString());
        assertEquals("!90", Rotation.parse("!90").toString());
        assertEquals("50.25", Rotation.parse("50.25").toString());
        assertEquals("!50.25", Rotation.parse("!50.25").toString());
    }
}

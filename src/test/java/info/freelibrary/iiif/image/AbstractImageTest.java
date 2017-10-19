
package info.freelibrary.iiif.image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class AbstractImageTest {

    @Test
    public void testGetCenter1() throws IOException {
        assertEquals("0,500,2000,2000", new GenericImage1().getCenter().toString());
    }

    @Test
    public void testGetCenter2() throws IOException {
        assertEquals("500,0,2000,2000", new GenericImage2().getCenter().toString());
    }

    @Test
    public void testGetAspectRation() throws IOException {
        assertEquals("3:3", new GenericImage1().getAspectRatio());
    }

    @Test
    public void testGetScaleFactors() throws IOException {
        final List<Integer> list = new GenericImage1().getScaleFactors(500);
        final Integer[] expected = new Integer[] { new Integer(1), new Integer(2), new Integer(4) };

        assertArrayEquals(expected, list.toArray(new Integer[expected.length]));
    }

    class GenericImage1 extends AbstractImage {

        @Override
        public int getWidth() throws IOException {
            return 2000;
        }

        @Override
        public int getHeight() throws IOException {
            return 3000;
        }

    }

    class GenericImage2 extends AbstractImage {

        @Override
        public int getWidth() throws IOException {
            return 3000;
        }

        @Override
        public int getHeight() throws IOException {
            return 2000;
        }
    }
}


package info.freelibrary.iiif.image;

import java.io.File;

public final class TestResource {

    public static final File TEST_DIR = new File("src/test/resources/images");

    public static final File TEST_TIF = new File(TEST_DIR, "test.tif");

    public static final File TEST_EXTRACTED = new File(TEST_DIR, "test-extracted.tif");

    public static final File TEST_RESIZED = new File(TEST_DIR, "test-resized.tif");

    public static final File TEST_ROTATED_90 = new File(TEST_DIR, "test-rotated-90.tif");

    public static final File TEST_ROTATED_45 = new File(TEST_DIR, "test-rotated-45.tif");

    public static final File TEST_ADJUST_GRAY = new File(TEST_DIR, "test-adjust-gray.tif");

    public static final File TEST_ADJUST_BITONAL = new File(TEST_DIR, "test-adjust-bitonal.tif");

    public static final File TEST_TRANFORM_FULL = new File(TEST_DIR, "test-transform-full.tif");

    public static final File TEST_FORMAT_JPG = new File(TEST_DIR, "test-format.jpg");

    public static final File TEST_FORMAT_PNG = new File(TEST_DIR, "test-format.png");

    public static final File TEST_FORMAT_GIF = new File(TEST_DIR, "test-format.gif");

    public TestResource() {
    }

}

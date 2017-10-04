
package info.freelibrary.iiif;

import static info.freelibrary.iiif.image.Constants.BUNDLE_NAME;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;

import info.freelibrary.iiif.image.Format;
import info.freelibrary.iiif.image.MessageCodes;
import info.freelibrary.iiif.image.Quality;
import info.freelibrary.iiif.image.Region;
import info.freelibrary.iiif.image.Request;
import info.freelibrary.iiif.image.Rotation;
import info.freelibrary.iiif.image.Size;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A native Java IIIF image implementation.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class JavaImage implements Image {

    private static final float DEFAULT_GIF_COMPRESSION = 0.7f;

    private static final float DEFAULT_JPG_COMPRESSION = 0.9f;

    // Whether a file system cache should be used when reading and writing images
    static {
        ImageIO.setUseCache(true);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaImage.class, BUNDLE_NAME);

    private ImageReader myReader;

    private BufferedImage myImage;

    private IIOMetadata myMetadata;

    private String myFormat;

    /**
     * Creates an image from the supplied byte array.
     *
     * @param aImageByteArray An image byte array
     * @throws IOException If there is trouble reading the image
     */
    public JavaImage(final byte[] aImageByteArray) throws IOException {
        final ByteArrayInputStream inStream = new ByteArrayInputStream(aImageByteArray);
        final ImageInputStream input = ImageIO.createImageInputStream(inStream);
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

        if (readers.hasNext()) {
            final ImageReader reader = readers.next();

            try {
                reader.setInput(input);
                myImage = reader.read(0);
                myFormat = reader.getFormatName();
                myMetadata = reader.getImageMetadata(0);
            } catch (final IllegalArgumentException details) {
                throw new IIIFRuntimeException(details);
            } finally {
                reader.dispose();
                input.close();
            }
        }

        if (myImage == null) {
            throw new IOException(LOGGER.getMessage(MessageCodes.EXC_072));
        }
    }

    /**
     * Creates an image from the supplied image file.
     *
     * @param aImageFile An image file
     * @throws IOException If there is trouble reading the image
     */
    public JavaImage(final File aImageFile) throws IOException {
        final ImageInputStream input = ImageIO.createImageInputStream(aImageFile);
        final Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

        if (readers.hasNext()) {
            myReader = readers.next();

            try {
                myReader.setInput(input);
                myFormat = myReader.getFormatName();
            } catch (final IllegalArgumentException details) {
                myReader.dispose();
                throw new IIIFRuntimeException(details);
            }
        }

        if (myReader == null) {
            throw new IIIFRuntimeException(MessageCodes.EXC_026, aImageFile);
        }
    }

    /**
     * Configures whether a file system cache should be used when reading and writing images.
     *
     * @param aCacheRequired Whether a file system cache should be used when reading and writing images
     */
    public static final void setUseCache(final boolean aCacheRequired) {
        ImageIO.setUseCache(aCacheRequired);
    }

    @Override
    public int getWidth() throws IOException {
        return myImage == null ? myReader.getWidth(0) : myImage.getWidth();
    }

    @Override
    public int getHeight() throws IOException {
        return myImage == null ? myReader.getHeight(0) : myImage.getHeight();
    }

    @Override
    public Image free() {
        if (myReader != null) {
            myReader.dispose();
        }

        if (myImage != null) {
            myImage.flush();
            myImage.getGraphics().dispose();
        }

        return this;
    }

    @Override
    public Image transform(final Request aRequest) throws IOException {
        final Region region = aRequest.getRegion();
        final Size size = aRequest.getSize();
        final Rotation rotation = aRequest.getRotation();
        final Quality quality = aRequest.getQuality();

        extract(region).resizeTo(size).rotateTo(rotation).adjust(quality);

        return this;
    }

    @Override
    public Image extract(final Region aRegion) throws IOException {
        if (!aRegion.isFullImage()) {
            final int x = aRegion.getInt(Region.Coordinate.X);
            final int y = aRegion.getInt(Region.Coordinate.Y);
            final int width = aRegion.getInt(Region.Coordinate.WIDTH);
            final int height = aRegion.getInt(Region.Coordinate.HEIGHT);
            final BufferedImage image = getBufferedImage();

            LOGGER.debug(MessageCodes.DBG_084, image, x, y, width, height);
            myImage = Scalr.crop(image, x, y, width, height);
        }

        return this;
    }

    @Override
    public Image resizeTo(final Size aSize) throws IOException {
        if (!aSize.isFullSize()) {
            final BufferedImage image = getBufferedImage();
            final int height = aSize.getHeight(image.getHeight(), image.getWidth());
            final int width = aSize.getWidth(image.getWidth(), image.getHeight());

            LOGGER.debug(MessageCodes.DBG_085, image, width, height);
            myImage = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width, height);
        }

        return this;
    }

    @Override
    public Image rotateTo(final Rotation aRotation) throws IOException {
        if (aRotation.isRotated()) {
            final BufferedImage image = getBufferedImage();

            if (aRotation.isMultipleOf90) {
                myImage = Scalr.rotate(image, Scalr.Rotation.valueOf("CW_" + aRotation.getValueAsInt()));
            } else {
                final AffineTransform transform = new AffineTransform();
                final int x = image.getWidth() / 2;
                final int y = image.getHeight() / 2;

                transform.rotate((aRotation.getValue() * Math.PI) / 180.0, x, y);
                transform.preConcatenate(adjustTransformation(transform, myImage));

                myImage = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR).filter(image, null);
            }
        }

        return this;
    }

    @Override
    public Image adjust(final Quality aQuality) throws IOException {
        if ((aQuality != Quality.COLOR) && (aQuality != Quality.DEFAULT)) {
            final BufferedImage buffer = getBufferedImage();
            final int width = buffer.getWidth();
            final int height = buffer.getHeight();
            final BufferedImage image;
            final Graphics graphics;

            if (aQuality == Quality.GRAY) {
                image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            } else if (aQuality == Quality.BITONAL) {
                image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
            } else {
                final Quality[] qualities = Quality.values();
                final int qCount = qualities.length;
                final String message = LOGGER.getMessage(MessageCodes.EXC_012, aQuality, qCount, qualities);

                throw new UnsupportedOperationException(message);
            }

            graphics = image.getGraphics();
            graphics.drawImage(myImage, 0, 0, null);

            myImage = image;
            graphics.dispose();
        }

        return this;
    }

    @Override
    public Image write(final Format aFormat, final File aFile) throws IOException {
        return write(aFormat, 0f, aFile);
    }

    @Override
    public Image write(final Format aFormat, final float aQuality, final File aFile) throws IOException {
        final Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(aFormat.getMimeType());

        if (iterator.hasNext()) {
            final FileOutputStream fileOutStream = new FileOutputStream(aFile);
            final ImageWriter writer = iterator.next();

            try {
                return write(aFormat, aQuality, fileOutStream, writer);
            } finally {
                fileOutStream.close();
            }
        } else {
            throw new IOException(LOGGER.getMessage(MessageCodes.EXC_071, aFormat.getMimeType()));
        }
    }

    @Override
    public Image write(final Format aFormat, final ByteArrayOutputStream aByteStream) throws IOException {
        return write(aFormat, 0.0f, aByteStream);
    }

    @Override
    public Image write(final Format aFormat, final float aQuality, final ByteArrayOutputStream aByteStream)
            throws IOException {
        final Iterator<ImageWriter> iterator = ImageIO.getImageWritersByMIMEType(aFormat.getMimeType());

        if (iterator.hasNext()) {
            return write(aFormat, aQuality, aByteStream, iterator.next());
        } else {
            throw new IOException(LOGGER.getMessage(MessageCodes.EXC_071, aFormat.getMimeType()));
        }
    }

    @Override
    public int hashCode() {
        try {
            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            final ImageOutputStream outStream = ImageIO.createImageOutputStream(byteStream);
            final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(myFormat);
            final ImageWriter writer = writers.next();
            final byte[] bytes;

            writer.setOutput(outStream);
            writer.write(getBufferedImage());
            outStream.flush();
            bytes = byteStream.toByteArray();
            outStream.close();

            return Arrays.hashCode(bytes);
        } catch (final IOException | NoSuchElementException details) {
            LOGGER.error(details, details.getMessage());
            return 0;
        }
    }

    /**
     * Compares two different JavaImages pixel by pixel.
     *
     * @param aImage An image against which to test equality
     * @return True if the images are equal; else, false
     */
    @Override
    public boolean equals(final Object aImage) {
        if (aImage instanceof JavaImage) {
            try {
                final BufferedImage image = ((JavaImage) aImage).getBufferedImage();

                if (image == getBufferedImage()) {
                    return true;
                }

                if ((myImage.getWidth() == image.getWidth()) && (myImage.getHeight() == image.getHeight())) {
                    for (int x = 0; x < myImage.getWidth(); x++) {
                        for (int y = 0; y < myImage.getHeight(); y++) {
                            if (myImage.getRGB(x, y) != image.getRGB(x, y)) {
                                return false;
                            }
                        }
                    }
                } else {
                    return false;
                }

                return true;
            } catch (final IOException details) {
                return false;
            }
        } else {
            return false;
        }
    }

    private AffineTransform adjustTransformation(final AffineTransform aTransform, final BufferedImage aImage) {
        final AffineTransform transform = new AffineTransform();
        Point2D p2dIn = new Point2D.Double(0.0, 0.0);
        Point2D p2dOut = aTransform.transform(p2dIn, null);
        final double ytrans;
        final double xtrans;

        ytrans = p2dOut.getY();
        p2dIn = new Point2D.Double(0, aImage.getHeight());
        p2dOut = aTransform.transform(p2dIn, null);
        xtrans = p2dOut.getX();

        transform.translate(-xtrans, -ytrans);
        return transform;
    }

    private Image write(final Format aFormat, final float aQuality, final OutputStream aOutputStream,
            final ImageWriter aWriter) throws IOException {
        final ImageOutputStream outStream = ImageIO.createImageOutputStream(aOutputStream);
        final ImageWriteParam param = aWriter.getDefaultWriteParam();

        // Set our image to progressively load
        if (param.canWriteProgressive()) {
            LOGGER.debug(MessageCodes.DBG_119, aFormat.getExtension().toUpperCase(Locale.US));
            param.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        }

        // Format specific tweaks go here...
        if (aFormat == Format.JPG) {
            // Clean up because JPEGImageWriter does not write JPEGs with alpha in a way other software understands
            // (https://stackoverflow.com/questions/36030307/stripping-alpha-channel-from-images/36057374#36057374)
            if (getBufferedImage().getTransparency() == Transparency.TRANSLUCENT) {
                final int width = myImage.getWidth();
                final int height = myImage.getHeight();
                final BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                final Graphics2D graphics = copy.createGraphics();

                graphics.drawImage(myImage, 0, 0, Color.WHITE, null);
                graphics.dispose();

                free();
                myImage = copy;
            }

            // Set a lower compression value than the default value of .7f
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(aQuality == 0.0f ? DEFAULT_JPG_COMPRESSION : aQuality);
            }
        } else if (aFormat == Format.GIF) {
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionType("LZW");
                param.setCompressionQuality(aQuality == 0.0f ? DEFAULT_GIF_COMPRESSION : aQuality);
            }
        }

        try {
            aWriter.setOutput(outStream);
            // TODO: Have to do something with myMetadata before it can be written across formats
            aWriter.write(null, new IIOImage(getBufferedImage(), null, null), param);
            outStream.flush();
        } finally {
            outStream.close();
            aWriter.dispose();
        }

        return this;
    }

    /**
     * Checks that the image has been read into memory and reads it if it hasn't been.
     *
     * @return The internal buffered image
     * @throws IOException If there is trouble reading the data into the buffered image
     */
    private BufferedImage getBufferedImage() throws IOException {
        if (myImage == null) {
            myImage = myReader.read(0);
            myMetadata = myReader.getImageMetadata(0);
        }

        return myImage;
    }

}

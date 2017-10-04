
package info.freelibrary.iiif.image;

/**
 * IIIF image rotation.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class Rotation {

    private static final String MIRRORED_LABEL = "!";

    public boolean isMirroredRotation;

    public boolean isMultipleOf90Rotation;

    public float myRotation;

    /**
     * Creates an image rotation.
     */
    public Rotation() {
        myRotation = 0f;
    }

    /**
     * Creates an image rotation object from a supplied float.
     *
     * @param aRotation A rotation float
     * @throws InvalidRotationException If the supplied float isn't a valid IIIF rotation value
     */
    public Rotation(final float aRotation) throws InvalidRotationException {
        myRotation = aRotation;
        validate();
    }

    /**
     * Creates an image rotation from the supplied IIIF URI rotation string.
     *
     * @param aRotationString A IIIF URI rotation string
     * @return This rotation
     * @throws InvalidRotationException If the supplied string isn't a valid IIIF URI rotation string
     */
    public static Rotation parse(final String aRotationString) throws InvalidRotationException {
        final Rotation rotation = new Rotation();

        if (aRotationString.startsWith(MIRRORED_LABEL)) {
            rotation.isMirrored(true).parseRotation(aRotationString.substring(1));
        } else {
            rotation.parseRotation(aRotationString);
        }

        return rotation;
    }

    /**
     * Returns the rotation value.
     *
     * @return The rotation value
     */
    public float getValue() {
        return myRotation;
    }

    /**
     * Returns the rotation value as an int.
     *
     * @return The rotation value as an int
     */
    public int getValueAsInt() {
        return (int) myRotation;
    }

    @Override
    public String toString() {
        final String rotation;

        // Will not work for values larger than max int, which is okay for our purposes
        if (myRotation == (long) myRotation) {
            rotation = String.format("%d", (long) myRotation);
        } else {
            rotation = String.format("%s", myRotation);
        }

        return isMirrored() ? MIRRORED_LABEL + rotation : rotation;
    }

    /**
     * Returns whether the image is mirrored or not.
     *
     * @return Whether the image is mirrored
     */
    public boolean isMirrored() {
        return isMirroredRotation;
    }

    /**
     * Sets whether the image is mirrored or not.
     *
     * @param aMirror True if mirrored; else, false
     * @return The rotation
     */
    public Rotation isMirrored(final boolean aMirror) {
        isMirroredRotation = aMirror;
        return this;
    }

    /**
     * Returns whether the image is rotated or not.
     *
     * @return Whether the image is rotated
     */
    public boolean isRotated() {
        return myRotation != 0;
    }

    /**
     * Returns true if the rotation is a multiple of ninety.
     *
     * @return True if the rotation is a multiple of ninety; else, false
     */
    public boolean isMultipleOf90() {
        return isMultipleOf90Rotation;
    }

    private void parseRotation(final String aRotationString) throws InvalidRotationException {
        try {
            myRotation = Float.parseFloat(aRotationString);
            validate();
        } catch (final NumberFormatException details) {
            throw new InvalidRotationException(details, MessageCodes.EXC_013, aRotationString);
        }
    }

    private void validate() throws InvalidRotationException {
        if ((myRotation < 0) || (myRotation > 360)) {
            throw new InvalidRotationException(MessageCodes.EXC_013, myRotation);
        }

        if ((myRotation % 90) == 0) {
            isMultipleOf90Rotation = true;
        }
    }

}

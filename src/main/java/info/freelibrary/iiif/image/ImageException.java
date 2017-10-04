
package info.freelibrary.iiif.image;

import static info.freelibrary.iiif.image.Constants.BUNDLE_NAME;

import java.util.Locale;

import info.freelibrary.util.I18nException;

/**
 * A generic exception related to the IIIF Image API service.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class ImageException extends I18nException {

    /**
     * The <code>serialVersionUID</code> for an image exception.
     */
    private static final long serialVersionUID = 8725269140335533371L;

    /**
     * Constructs an image exception.
     *
     * @param aMessageKey A message key to look up the exception message
     */
    public ImageException(final String aMessageKey) {
        super(BUNDLE_NAME, aMessageKey);
    }

    /**
     * Constructs an image exception.
     *
     * @param aMessageKey A message key to look up the exception message
     * @param aVarargs Additional details to add to the exception message
     */
    public ImageException(final String aMessageKey, final Object... aVarargs) {
        super(BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Constructs an image exception.
     *
     * @param aLocale A locale to use when constructing the exception message
     * @param aMessageKey A message key to look up the exception message
     */
    public ImageException(final Locale aLocale, final String aMessageKey) {
        super(aLocale, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Constructs an image exception.
     *
     * @param aLocale A locale to use when constructing the exception message
     * @param aMessageKey A message key to look up the exception message
     * @param aVarargs Additional details to add to the exception message
     */
    public ImageException(final Locale aLocale, final String aMessageKey, final Object... aVarargs) {
        super(aLocale, BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Constructs an image exception.
     *
     * @param aCause The root cause of the exception
     */
    public ImageException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Constructs an image exception.
     *
     * @param aCause The root cause of the exception
     * @param aMessageKey A message key to look up the exception message
     */
    public ImageException(final Throwable aCause, final String aMessageKey) {
        super(aCause, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Constructs an image exception.
     *
     * @param aCause The root cause of the exception
     * @param aLocale A locale to use when constructing the exception message
     * @param aMessageKey A message key to look up the exception message
     */
    public ImageException(final Throwable aCause, final Locale aLocale, final String aMessageKey) {
        super(aCause, aLocale, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Constructs an image exception.
     *
     * @param aCause The root cause of the exception
     * @param aMessageKey A message key to look up the exception message
     * @param aVarargs Additional details to add to the exception message
     */
    public ImageException(final Throwable aCause, final String aMessageKey, final Object... aVarargs) {
        super(aCause, BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Constructs an image exception.
     *
     * @param aCause The root cause of the exception
     * @param aLocale A locale to use when constructing the exception message
     * @param aMessageKey A message key to look up the exception message
     * @param aVarargs Additional details to add to the exception message
     */
    public ImageException(final Throwable aCause, final Locale aLocale, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aLocale, BUNDLE_NAME, aMessageKey, aVarargs);
    }

}

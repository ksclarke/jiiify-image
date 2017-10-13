
package info.freelibrary.iiif.image;

import static info.freelibrary.iiif.image.util.Constants.BUNDLE_NAME;

import java.util.Locale;

import info.freelibrary.util.I18nRuntimeException;

/**
 * A runtime exception for the IIIF environment.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public class IIIFRuntimeException extends I18nRuntimeException {

    /**
     * The <code>serialVersionUID</code> for this class.
     */
    private static final long serialVersionUID = 542945529526400678L;

    /**
     * Creates a IIIF runtime exception.
     */
    public IIIFRuntimeException() {
        super();
    }

    /**
     * Creates a IIIF runtime exception from the supplied throwable.
     *
     * @param aCause A root cause for the runtime exception
     */
    public IIIFRuntimeException(final Throwable aCause) {
        super(aCause);
    }

    /**
     * Creates a IIIF runtime exception from the supplied message key.
     *
     * @param aMessageKey A message key for the I18N message
     */
    public IIIFRuntimeException(final String aMessageKey) {
        super(BUNDLE_NAME, aMessageKey);
    }

    /**
     * Creates a IIIF runtime exception from the supplied locale and message key.
     *
     * @param aLocale A locale for the exception's message
     * @param aMessageKey A message key for the I18N message
     */
    public IIIFRuntimeException(final Locale aLocale, final String aMessageKey) {
        super(aLocale, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Creates a IIIF runtime exception from the supplied message key and additional details.
     *
     * @param aMessageKey A message key for the I18N message
     * @param aVarargs Additional details to add to the I18N message
     */
    public IIIFRuntimeException(final String aMessageKey, final Object... aVarargs) {
        super(BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Creates a IIIF runtime exception from the supplied root exception and message key.
     *
     * @param aCause A root cause of the runtime exception
     * @param aMessageKey A message key for the I18N message
     */
    public IIIFRuntimeException(final Throwable aCause, final String aMessageKey) {
        super(aCause, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Creates a IIIF runtime exception from the supplied locale, message key, and additional details.
     *
     * @param aLocale A locale for the I18N message
     * @param aMessageKey A message key for the I18N message
     * @param aVarargs Additional details to add to the I18N message
     */
    public IIIFRuntimeException(final Locale aLocale, final String aMessageKey, final Object... aVarargs) {
        super(aLocale, BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Creates a IIIF runtime exception from the supplied root cause, message locale, and message key.
     *
     * @param aCause A root cause of the runtime exception
     * @param aLocale A locale for the I18N message
     * @param aMessageKey A message key for the I18N message
     */
    public IIIFRuntimeException(final Throwable aCause, final Locale aLocale, final String aMessageKey) {
        super(aCause, aLocale, BUNDLE_NAME, aMessageKey);
    }

    /**
     * Creates a IIIF runtime exception from the supplied root cause, message key, and additional details.
     *
     * @param aCause A root cause of the runtime exception
     * @param aMessageKey A message key for the I18N message
     * @param aVarargs Additional details to add to the I18N message
     */
    public IIIFRuntimeException(final Throwable aCause, final String aMessageKey, final Object... aVarargs) {
        super(aCause, BUNDLE_NAME, aMessageKey, aVarargs);
    }

    /**
     * Creates a IIIF runtime exception from the supplied root cause, locale, message key and additional details.
     *
     * @param aCause The root cause of the runtime exception
     * @param aLocale A locale for the I18N message
     * @param aMessageKey A message key for the I18N message
     * @param aVarargs Additional details to add to the I18N message
     */
    public IIIFRuntimeException(final Throwable aCause, final Locale aLocale, final String aMessageKey,
            final Object... aVarargs) {
        super(aCause, aLocale, BUNDLE_NAME, aMessageKey, aVarargs);
    }

}

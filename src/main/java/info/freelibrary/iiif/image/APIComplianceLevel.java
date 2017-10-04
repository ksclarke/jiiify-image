
package info.freelibrary.iiif.image;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * The Image Info profile level.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">Kevin S. Clarke</a>
 */
public enum APIComplianceLevel {

    ZERO("http://iiif.io/api/image/2/level0.json"),
    ONE("http://iiif.io/api/image/2/level1.json"),
    TWO("http://iiif.io/api/image/2/level2.json");

    private String myURL;

    /**
     * Creates an Image Info profile level.
     *
     * @param aLevel A profile level
     */
    APIComplianceLevel(final String aLevel) {
        myURL = aLevel;
    }

    @Override
    public String toString() {
        return myURL;
    }

    /**
     * Returns a URI representation of the profile level.
     *
     * @return A URI representation of the profile level
     */
    public URI toURI() {
        return URI.create(myURL);
    }

    /**
     * Returns a URL representation of the profile level.
     *
     * @return A URL representation of the profile level
     */
    public URL toURL() {
        try {
            return new URL(myURL);
        } catch (final MalformedURLException details) {
            // The provided URLs should not have syntax exceptions
            throw new RuntimeException(details);
        }
    }

}

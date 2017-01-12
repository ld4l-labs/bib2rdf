package org.ld4l.bib2lod.uri;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An abstract implementation.
 */
public abstract class BaseUriMinter implements UriMinter {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String localNamespace;

    /**
     * Constructor.
     * @param configuration
     */
    public BaseUriMinter(Configuration configuration) {
        this.localNamespace = configuration.getLocalNamespace();
    }
    
    /**
     * Returns the local namespace.
     * @return the local namespace
     */
    public String getLocalNamespace() {
        return localNamespace;
    }
    
    /**
     * Mints a URI by concatenating the local namespace and the local name
     * generated by the concrete implementation. 
     * @return the URI
     */
    // TODO Do we ever need to mint a URI in a different namespace? If so, add
    // another method and pass in local namespace.
    public String mint() {
        String uri = localNamespace + mintLocalName();
        LOGGER.debug("URI = " + uri);
        return uri;
    }
    
    protected abstract String mintLocalName();


}

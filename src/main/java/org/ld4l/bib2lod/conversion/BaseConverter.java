/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An abstract implementation.
 */
public abstract class BaseConverter implements Converter {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected Configuration configuration;

    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public BaseConverter(Configuration configuration) {
        this.configuration = configuration;
    }
}
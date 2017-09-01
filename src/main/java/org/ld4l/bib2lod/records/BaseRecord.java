/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An abstract implementation.
 */
// TODO Not using at this point. Not clear whether it serves any purpose or just
// gets in the way. What are the methods common to XML and non-XML input?
public abstract class BaseRecord implements Record {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 
    

}

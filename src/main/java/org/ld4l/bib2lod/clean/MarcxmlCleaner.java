package org.ld4l.bib2lod.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

public class MarcxmlCleaner extends BaseCleaner {
 
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public MarcxmlCleaner(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.clean.Cleaner#clean()
     */
    @Override
    public String clean() {
        // TODO Auto-generated method stub
        return null;
    }

}

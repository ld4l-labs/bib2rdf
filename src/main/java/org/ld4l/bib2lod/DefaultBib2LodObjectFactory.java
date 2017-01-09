/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.clean.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.configuration.StubConfiguration;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.MarcxmlToLd4lRdfConverter;
import org.ld4l.bib2lod.uri.RandomUriMinter;
import org.ld4l.bib2lod.uri.UriMinter;

/**
 * A simple implementation.
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(java.lang.String[])
     */
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return new JsonOptionsReader(args);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(java.lang.String[])
     */
    @Override
    public Configuration createConfiguration(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
            ParseException {
        // return new ConfigurationFromJson(args);
        return new StubConfiguration();
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Cleaner createCleaner(Configuration configuration) {
        return new MarcxmlCleaner(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Converter createConverter(Configuration configuration) {
        // TODO Auto-generated method stub
        return new MarcxmlToLd4lRdfConverter(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createUriMinter(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public UriMinter createUriMinter(Configuration configuration) {
        return new RandomUriMinter(configuration);
    }
    


}

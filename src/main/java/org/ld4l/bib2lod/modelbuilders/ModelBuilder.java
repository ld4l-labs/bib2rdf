/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.modelbuilders;

import org.apache.jena.rdf.model.Model;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;

/**
 * Create RDF from the Entity.
 */
public interface ModelBuilder {

    /**
     * Factory method
     * @param configuration 
     * @param type - the Entity for which to instantiate a ModelBuilder
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    static ModelBuilder instance(Entity resource, Configuration configuration) 
            throws  InstantiationException,IllegalAccessException, 
                ClassNotFoundException {
        return Bib2LodObjectFactory.instance().createModelBuilder(
                resource, configuration);
    }
    
    // TODO Better to not set resource in constructor and pass it in here??
    public Model build();
    
}
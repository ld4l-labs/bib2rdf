/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Instance;

/**
 * Builds a Model from an Entity.
 */
public interface ResourceBuilder {
    
    /**
     * Signals an error in building a Resource.
     */
    public static class ResourceBuilderException
            extends RecordConversionException {
        private static final long serialVersionUID = 1L;

        public ResourceBuilderException(String message, Throwable cause) {
            super(message, cause);
        }

        public ResourceBuilderException(String message) {
            super(message);
        }

        public ResourceBuilderException(Throwable cause) {
            super(cause);
        }
    }    
    

    /**
     * Factory method
     * @param type - the Entity for which to instantiate a ResourceBuilder
     * @throws ResourceBuilderException 
     */
    static ResourceBuilder instance(Entity entity) 
            throws ResourceBuilderException {

        if (entity instanceof Instance) {
            return new InstanceBuilder((Instance) entity);
        }
        // etc
        throw new ResourceBuilderException(
                "Model builders other than InstanceResourceBuilder not implemented.");
    }
    
    /**
     * Returns a Resource.
     */
    public Resource build();
    
}

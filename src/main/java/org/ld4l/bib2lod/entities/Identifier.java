/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

public class Identifier extends BaseEntity {  

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Define the Identifier types.
     */
    // TODO This is a place where I'd like to use the ontology files to get 
    // back all the Identifier classes (i.e., the superclass and subclasses).
    // TODO Right now this is only used internally. It's not useful for
    // external classes to refer to this, because then they have to refer to
    // Instance.Type, Work.Type, etc.
    // OR: could put these all in the same package
    public static enum Type {

        IDENTIFIER(Namespace.BIBFRAME, "Identifier"),                     
        LOCAL(Namespace.BIBFRAME, "Local");
        
        private final String uri;
        
        Type(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
        }
 
        public String uri() {
            return uri;
        }
    }
    
    private static Type SUPERTYPE = Type.IDENTIFIER;
    
    private String value;


    @Override
    public String getSuperType() {
        return SUPERTYPE.uri;
    }
    
    public void addType(Type type) {
        super.addType(type.uri);
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

}

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Instance types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lInstanceType implements Type {
    
    /* List in alpha order */
    ARCHIVAL(Ld4lNamespace.BIBFRAME, "Archival"),
    ELECTRONIC(Ld4lNamespace.BIBFRAME, "Electronic"),
    INSTANCE(Ld4lNamespace.BIBFRAME, "Instance"),
    MANUSCRIPT(Ld4lNamespace.BIBFRAME, "Manuscript"),
    PRINT(Ld4lNamespace.BIBFRAME, "Print");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lInstanceType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 
    
    @Override
    public Type superclass() {
        return defaultType();
    }

    public static Type defaultType() {
        return INSTANCE;
    }

}

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.OntologyProp;

/**
 * Enumerates the object properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lObjectProp implements OntologyProp {

    HAS_ACTIVITY(Ld4lNamespace.LD4L, "hasActivity"),
    HAS_LANGUAGE(Ld4lNamespace.DCTERMS, "language"),
    HAS_ITEM(Ld4lNamespace.BIBFRAME, "hasItem"),
    HAS_PART(Ld4lNamespace.DCTERMS, "hasPart"),
    HAS_PREFERRED_TITLE(Ld4lNamespace.LD4L, "hasPreferredTitle"),
    HAS_TITLE(Ld4lNamespace.BIBFRAME, "title"),
    IS_AT_LOCATION(Ld4lNamespace.LD4L, "atLocation"),
    IS_IDENTIFIED_BY(Ld4lNamespace.BIBFRAME, "identifiedBy"),
    IS_INSTANCE_OF(Ld4lNamespace.BIBFRAME, "instanceOf");

   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    Ld4lObjectProp(Ld4lNamespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }
    
    @Override
    public Property property() {
        return property;
    }

}

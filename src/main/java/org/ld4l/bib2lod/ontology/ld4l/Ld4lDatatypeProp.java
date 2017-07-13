package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;

/**
 * Enumerates the datatype properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lDatatypeProp implements DatatypeProp {
    
    /* List in alpha order */
    CHANGE_DATE(Ld4lNamespace.BIBFRAME, "changeDate"),
    CODE(Ld4lNamespace.BIBFRAME, "code"),
    DATE(Ld4lNamespace.DCTERMS, "date"),
    EDITION_STATEMENT(Ld4lNamespace.BIBFRAME, "editionStatement"),
    EDITORIAL_NOTE(Ld4lNamespace.SKOS, "editorialNote"),
    /*
     * rdfs:label is technically an annotation property, but so far functions 
     * the same as a datatype property for our purposes.
     */
    LABEL(Ld4lNamespace.RDFS, "label"),
    NAME(Ld4lNamespace.FOAF, "name"),
    PROVISION_ACTIVITY_STATEMENT(Ld4lNamespace.BIBFRAME, "provisionActivityStatement"),
    RANK(Ld4lNamespace.VIVO, "rank"),
    RESPONSIBILITY_STATEMENT(Ld4lNamespace.BIBFRAME, "responsibilityStatement"),
    /*
     * rdf:value is an RDF property with range rdfs:Resource. It is defined both
     * as an Ld4lDatatypeProp and an Ld4lObjectProp for use with either a literal
     * or non-literal object. For now it doesn't appear that the lack of 
     * technical accuracy will cause any problems.
     */
    VALUE(Ld4lNamespace.RDF, "value");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    Ld4lDatatypeProp(Ld4lNamespace namespace, String localName) {
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

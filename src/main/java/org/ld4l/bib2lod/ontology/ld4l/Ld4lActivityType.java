package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lActivityType implements Type {
    
    ACTIVITY(Ld4lNamespace.LD4L, "Activity", "Activity"),
    PUBLISHER_ACTIVITY(Ld4lNamespace.LD4L, "PublisherActivity", "Publisher");
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    /**
     * Constructor
     */
    Ld4lActivityType(Namespace namespace, String localName) {
        this(namespace, localName, null);
    }
    
    Ld4lActivityType(Namespace namespace, String localName, String label) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri); 
        /*
         * TODO Here's a case where reading in the ontology file would be really
         * useful: the rdfs:label can be retrieved from the property definition
         * rather than hard-coded here.
         */
        this.label = label;
    }
    

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    }
    
    public String label() {
        return label;
    }

    public static Type superClass() {
        return ACTIVITY;
    }
}

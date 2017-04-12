/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Enumerates the ontology namespaces used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
// TODO Any gain in using different enums for ontology namespaces and dataset
// namespaces?
public enum Ld4lNamespace implements Namespace {

    // Ontologies
    BIBFRAME("http://id.loc.gov/ontologies/bibframe/", "bf"),
    DCTERMS("http://purl.org/dc/terms/", "dcterms"),    
    FOAF("http://xmlns.com/foaf/0.1/", "foaf"),
    LD4L("http://bib.ld4l.org/ontology/", "ld4l"),
    LINGVO("http://www.lingvoj.org/ontology#", "lingvo"),
    OA("http://www.w3.org/ns/oa#", "oa"),
    OWL("http://www.w3.org/2002/07/owl#", "owl"),
    PROV("http://www.w3.org/ns/prov#", "prov"),
    RDF("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf"),
    RDFS("http://www.w3.org/2000/01/rdf-schema#", "rdfs"),
    SCHEMA("http://schema.org/", "schema"),
    SKOS("http://www.w3.org/2004/02/skos/core#", "skos"),
    VIVO("http://vivoweb.org/ontology/core#", "vivo"),
    
    // Datasets/controlled vocabularies
    COUNTRIES("http://id.loc.gov/vocabulary/countries/"),
    LEXVO("http://lexvo.org/id/iso639-3/", "lexvo");

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private final String uri;
    private final String prefix;
    
    Ld4lNamespace(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
    }
    
    Ld4lNamespace(String uri) {
        this(uri, null);
    }
    
    @Override
    public String uri() {
        return this.uri;
    }
    
    @Override
    public String prefix() {
        return this.prefix;
    }
    
}

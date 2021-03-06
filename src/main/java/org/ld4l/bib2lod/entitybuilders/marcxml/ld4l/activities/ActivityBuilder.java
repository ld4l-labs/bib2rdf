package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlTaggedField;

public class ActivityBuilder extends MarcxmlEntityBuilder {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType DEFAULT_TYPE = 
            (Ld4lActivityType) Ld4lActivityType.defaultType();

    protected Entity activity;
    protected MarcxmlTaggedField field;
    protected Entity parent;
    protected DatatypeProp property;
    protected MarcxmlRecord record;
    protected List<MarcxmlSubfield> subfields;
    protected Ld4lActivityType type;
    protected String value;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
               
        reset();      
        parseBuildParams(params);
        
        build();
        
        // The build() method may return without creating an Activity.
        if (activity != null) {
            activity.addAttribute(Ld4lDatatypeProp.LABEL, 
                    new Attribute(type.label()));        
            
            parent.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
        }
        
        return activity;
    }
    
    private void reset() {
        this.activity = null;
        this.parent = null;
        this.field = null;
        this.property = null;
        this.subfields = new ArrayList<>();
        this.record = null;
        this.type = null;
        this.value = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {

        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "A parent entity is required to build an activity.");
        }

        // May not ever need record - in any case, not required.
        this.record = (MarcxmlRecord) params.getRecord();

        RecordField field = params.getField();
        if (field != null) {
            if (! (field instanceof MarcxmlTaggedField)) {
                throw new EntityBuilderException("A data field or control " + 
                        "field is required to build an activity");
            }        
            this.field = (MarcxmlTaggedField) field;
        }
        
        this.property = params.getProperty();
        this.value = params.getValue();
        
        if (field == null && property == null) {
            throw new EntityBuilderException(
                    "A field or property and value is required to build an activity.");
        }
        if (field == null && value == null) {
            throw new EntityBuilderException("A field or property and " +
                    "value is required to build an activity.");            
        }
        
        Type type = params.getType();
        if (type != null) {
            if (! (type instanceof Ld4lActivityType)) {
                throw new EntityBuilderException("Invalid type.");
            }
            this.type = (Ld4lActivityType) type;
        } else {
            this.type = DEFAULT_TYPE;
        }
        
        /* 
         * This needs to be a list of MarcxmlSubfields in order
         * to get the codes, but in BuildParams it's just a list of 
         * RecordFields. Is there a better way to do this?
         */
        for (RecordField subfield : params.getSubfields()) {
            this.subfields.add((MarcxmlSubfield) subfield);
        }
    }
    
    protected void build() throws EntityBuilderException {

        if (property == null || value == null) {
            throw new EntityBuilderException(
                    "A property and value are needed to build a generic Activity.");
        }
        this.activity = new Entity(type);
        activity.addAttribute(property, value);       
    }

}

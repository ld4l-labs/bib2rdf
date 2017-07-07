package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lActivityBuilder extends BaseEntityBuilder {
    
    private Entity bibEntity;
    private MarcxmlRecord record;
    private MarcxmlField field;
    private Entity activity;
    private Ld4lActivityType type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.bibEntity = params.getRelatedEntity();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related entity is required to build an activity.");
        }

        this.field = (MarcxmlField) params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "An input field is required to build an activity.");
        }
        
        this.record = (MarcxmlRecord) params.getRecord();
        
        Type typeParam = params.getType();
        this.type = (Ld4lActivityType) (typeParam != null ? 
                typeParam : Ld4lActivityType.superClass());
        this.activity = new Entity(type);
        
        addLabel();
    
        // Add publication year and place from 008
        // TODO Move to a method once we see what other attributes we need to
        // deal with.
        addAgent();
        addDate();
        addLocation();

        bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
        return activity;
    }
    
    private void addLabel() {
        activity.addAttribute(Ld4lDatatypeProp.LABEL, new Attribute(type.label()));
    }
    
    private void addAgent() {
        // Method not yet implemented
    }
    
    private void addLocation() throws EntityBuilderException {

        if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
            try {
                if (field.getTag() == 8) {    
                    String location = field.getTextSubstring(15, 18);
                    if (! StringUtils.isBlank(location)) {
                        activity.addExternalRelationship(Ld4lObjectProp.IS_AT_LOCATION, 
                                Ld4lNamespace.LC_COUNTRIES.uri() + location);
                    }
                }
            } catch (RecordFieldException e) {
                throw new EntityBuilderException(e);
            }
        }       
    }
    
    private void addDate() throws EntityBuilderException {

        if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
            try {
                if (field.getTag() == 8) {
                    String year = field.getTextSubstring(7, 11);
                    if (! StringUtils.isBlank(year)) {
                        activity.addAttribute(Ld4lDatatypeProp.DATE, year);
                    }
                }
            } catch (RecordFieldException e) {
                throw new EntityBuilderException(e);
            }
        }        
    }

}

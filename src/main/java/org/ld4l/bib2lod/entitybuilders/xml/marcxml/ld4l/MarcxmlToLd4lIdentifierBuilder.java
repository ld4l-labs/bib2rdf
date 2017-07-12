package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.BaseMarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

/**
 * Builds an Identifier for a bib resource from a field in the record.
 */
public class MarcxmlToLd4lIdentifierBuilder extends BaseEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger();
    
    private static Map<String, Entity> sources;
    static {
        buildSources();
    }
    
    private static final Pattern PATTERN_035_IDENTIFIER = 
            /* 
             * Example values:
             * 102063, (CStRLIN)NYCX86B63464, (NIC)notisAAL3258, (OCoLC)1345399
             *
             * Organization code format: alphas and dashes enclosed in 
             * parentheses. See https://www.loc.gov/marc/organizations/
             * 
             * TODO Not sure which chars are allowed in identifier; using \w for
             * now
             */
            Pattern.compile("^(?:\\(([a-zA-Z-]+)\\))?(\\w+)$");
    
    private BaseMarcxmlField field;
    private Entity relatedEntity;
    
    private static void buildSources() {
        
        sources = new HashMap<>();
        
        List<String> sourceStrings =  Arrays.asList(
                "CStRLIN", "NIC", "OCoLC"   
                );
        
        for (String label : sourceStrings) {
            buildSource(label);
        }        
    }
    
    public static Map<String, Entity> getSources() {
        return sources;
    }
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.relatedEntity = params.getRelatedEntity();
        if (relatedEntity == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without a related entity.");
        }
        
        this.field = (BaseMarcxmlField) params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without an input field.");
        }
        
        Entity identifier;
        if (field instanceof MarcxmlControlField) {
            identifier = build((MarcxmlControlField) field);
        } else if (field instanceof MarcxmlDataField) {
            identifier = build((MarcxmlDataField) field, params);
        } else {
            throw new EntityBuilderException("Invalid field type.");
        }

        if (identifier != null) {
            relatedEntity.addRelationship(
                    Ld4lObjectProp.IDENTIFIED_BY, identifier);
        }
        
        return identifier;
    }
    
    /**
     * Returns a source Entity built from a label. No type is specified, and the 
     * label is the organization code in the identifier.
     */
    private static final Entity buildSource(String label) {
        EntityBuilder builder = new MarcxmlToLd4lLegacyDataEntityBuilder();
        BuildParams params = new BuildParams()
                .setValue(label);
        try {
            Entity source = builder.build(params);
            sources.put(label, source);
            return source;
        } catch (EntityBuilderException e) {
            // Throw an exception so we don't have to keep testing for 
            // whether the key exists or whether the value is null.
            throw new RuntimeException(e);
        }
    }              
    
    /**
     * Builds an Identifier from a control field. Returns null if the control
     * field is not recognized or implemented.
     */   
    private Entity build(MarcxmlControlField field) {
        
        Entity identifier = null;
        
        if (((MarcxmlControlField) field).getTag() == 1) {
            identifier = new Entity(Ld4lIdentifierType.LOCAL);
            String value = field.getTextValue();
            identifier.addAttribute(Ld4lDatatypeProp.VALUE, value);    
        }
        
        return identifier;
    }
    /**
     * Builds an identifier from a data field. Returns null if the field is
     * not recognized or implemented.
     */
    private Entity build(MarcxmlDataField field, BuildParams params)
             throws EntityBuilderException {       

        Entity identifier = null;
        
        MarcxmlSubfield subfield = (MarcxmlSubfield) params.getSubfield();
 
        if (field.getTag() == 35) {
            identifier = convert035(subfield);
        }
        
        return identifier;
    }

    /**
     * Builds an identifier from field 035. Returns null if the identifier value
     * is already attached to the resource's AdminMetadata object.
     */
    private Entity convert035(MarcxmlSubfield subfield) 
            throws EntityBuilderException {
        
        Entity identifier;
        
        char code = subfield.getCode();
        
        // Ignoring $6 and $8 (not mapped by LC)
        if (code != 'a' && code != 'z') {
            throw new EntityBuilderException("Invalid subfield for field 035.");                
        }

        Matcher matcher = 
                PATTERN_035_IDENTIFIER.matcher(subfield.getTextValue());
        if (! matcher.matches()) {
            throw new EntityBuilderException("Invalid value for field 035.");
        }
        
        identifier = new Entity(Ld4lIdentifierType.LOCAL);
        
        // Identifier source
        String orgCode = matcher.group(1);

        // Identifier value
        String value = matcher.group(2);
        
        // Don't build an identifier when the 035 value is the same as the
        // AdminMetadata identifier.
        if (orgCode == null) {           
            Entity adminMetadata = 
                    relatedEntity.getChild(Ld4lObjectProp.HAS_ADMIN_METADATA);
            if (adminMetadata != null) {
                Entity id = adminMetadata.getChild(Ld4lObjectProp.IDENTIFIED_BY);
                String idValue = id.getValue(Ld4lDatatypeProp.VALUE);
                if (value.equals(idValue)) {
                    return null;
                }
            } 
        } else {
            // Add source entity
            Entity source = sources.get(orgCode);
            /*
             * If source does not already exist in the sources map, build a new
             * source Entity and add it to the map so it doesn't have to be 
             * rebuilt next time through.
             */
            if (source == null) {                
                source = buildSource(orgCode);
            }
            identifier.addRelationship(Ld4lObjectProp.HAS_SOURCE, source);            
        }

        // Add identifier value
        identifier.addAttribute(Ld4lDatatypeProp.VALUE, value);

        // Identifier status
        if (code == 'z') {
            identifier.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                    Ld4lNamedIndividual.CANCELLED);
        }

        return identifier;
    }
    
}

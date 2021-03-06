package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

// TODO Might be abstract - do we ever build a generic provider activity?
public class ProviderActivityBuilder extends ActivityBuilder {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    

    /**
     * Builds an untyped date attribute from a subfield with uncontrolled  
     * values (e.g., 260$c as opposed to the controlled 008 values).
     */
    // TODO Move up to ActivityBuilder if it works for other activities
    protected void buildUntypedDate(MarcxmlSubfield subfield) 
            throws EntityBuilderException {
        
        if (subfield == null) {
            return;
        }
        
        String date = subfield.getTrimmedTextValue();
        activity.addAttribute(Ld4lDatatypeProp.DATE, date);
    }
   
    // TODO Move up to ActivityBuilder if it works for other activities   
    protected void buildLocation(MarcxmlSubfield subfield) 
            throws EntityBuilderException {
              
        if (subfield == null) {
            return;
        }       
        
        EntityBuilder builder = getBuilder(Ld4lLocationType.defaultType());
              
        BuildParams params = new BuildParams() 
              .setGrandparent(parent)
              .setParent(activity)
              .addSubfield(subfield);
        builder.build(params);
    }

    // TODO Move up to ActivityBuilder if it works for other activities
    protected void buildAgent(MarcxmlSubfield subfield) 
            throws EntityBuilderException { 
 
        if (subfield == null) {
            return;
        }
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.defaultType());

        BuildParams params = new BuildParams() 
              .setField(field)
              .setGrandparent(parent)
              .setParent(activity)
              .setSubfield(subfield);
        builder.build(params);  
    }
    
    /**
     * note: code list must be in ascending order
     */
    public static List<List<RecordField>> getActivitySubfields(
            MarcxmlDataField field, List<Character> codes) { 
        
        List<MarcxmlSubfield> subfields = field.getSubfields(codes);
        
        // List of lists, where each sublist contains the subfields for a
        // single activity.
        List<List<RecordField>> activities = new ArrayList<>();
        
        // Subfields for a single activity
        List<RecordField> activity = new ArrayList<>();        
        Character last = null;
        Character current = null;
        for (MarcxmlSubfield subfield : subfields) {
            current = subfield.getCode();
            // First subfield, or a subfield sequentially following the
            // previous subfield
            if (last == null || current > last) {
                activity.add(subfield);
            } else {
                // Close the current list and add it to the list of lists.
                // Reinitialize the list and add the current subfield.
                activities.add(activity);
                activity = new ArrayList<>();
                activity.add(subfield);
            }
            last = current;
        }
        
        // Add the last list to the larger list, if not empty
        if (! activity.isEmpty()) {
            activities.add(activity);
        }
        
        return activities;
    }

}

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class ItemBuilder.
 */
public class ItemBuilderTest extends AbstractTestClass {
    
    private ItemBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new ItemBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRelatedInstance_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A related instance is required");
        BuildParams params = new BuildParams() 
                .setRecord(MINIMAL_RECORD.toRecord());  
        builder.build(params);        
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams() 
                .setParent(new Entity()); 
        builder.build(params);        
    }
    
    @Test 
    public void testItemType() throws Exception {
        Entity item = buildItem(MINIMAL_RECORD);     
        Assert.assertTrue(item.hasType(Ld4lItemType.ITEM));
    }
    
    @Test 
    public void testInstanceHasItem() throws Exception {
        Entity instance = new InstanceEntity();
        Entity item = buildItem(instance, MINIMAL_RECORD);   
        Assert.assertTrue(instance.hasChild(Ld4lObjectProp.HAS_ITEM, item));
    }   
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
         
    private Entity buildItem(MockMarcxml input) 
            throws Exception {
        return buildItem(new Entity(), input);
    }
    
    private Entity buildItem(Entity parent, MockMarcxml input) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setRecord(input.toRecord());
        return builder.build(params);
    }
    
}

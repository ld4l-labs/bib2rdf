package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class TitleElementBuilder
 */
public class TitleElementBuilderTest extends AbstractTestClass {
    
    public static final String NON_SORT_ELEMENT_WITH_FINAL_SPACE = "L' ";
          
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE = 
            " main title ";
 
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_COLON = 
            "main title:";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_PERIOD = 
            "main title.";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON = 
            "main title :";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON_SPACE = 
            "main title : ";
    
    public static final String SUBTITLE = "subtitle";
    
    public static final String SUBTITLE_WITH_WHITESPACE = " subtitle ";
    
    
    private TitleElementBuilder builder;
      
    @Before
    public void setUp() throws Exception {    
        this.builder = new TitleElementBuilder();
    }  
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullTitle_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, "title is required");
        BuildParams params = new BuildParams()
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setValue("title element");
        builder.build(params);
    }    

    @Test 
    public void nullTitleElementType_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, "type is required");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setValue("title element");
        builder.build(params);
    }
       
    @Test 
    public void invalidTitleElementType_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "invalid title element type");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setType(Ld4lTitleType.TITLE);
        builder.build(params);
    } 
    
    @Test 
    public void nullValue_ThrowsException() throws Exception {         
        expectException(EntityBuilderException.class, 
                "Non-empty string value required");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT);
        builder.build(params);
    }
    
    @Test
    public void emptyValue_ThrowsException() throws Exception {       
        expectException(EntityBuilderException.class, 
                "Non-empty string value required");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setValue("");
        builder.build(params);
    }

    @Test
    public void testTrimMainTitleElement() throws Exception {        
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE, "main title");             
    }
    
    @Test
    public void testRemoveFinalSpaceColon() 
            throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON, "main title");                 
    }
    
    @Test
    public void testRemoveFinalColon() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_COLON, "main title");       
    }
    
    @Test
    public void testRemoveFinalPeriod() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_PERIOD, "main title");       
    }
    
    @Test
    public void testRemoveFinalSpacePunctSpace() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON_SPACE, "main title");       
    }
    
    @Test 
    public void testTrimSubtitleValue() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.SUBTITLE_ELEMENT, 
                SUBTITLE_WITH_WHITESPACE, "subtitle");             
    }
    
    @Test
    public void testPreserveFinalSpaceInNonSortElement() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.NON_SORT_ELEMENT, 
                NON_SORT_ELEMENT_WITH_FINAL_SPACE, "L' ");             
    }
    
    @Test
    public void testRelationshipToTitle() throws Exception {
        Entity bibResource = new Entity();
        Entity titleElement = buildTitleElement(
                Ld4lTitleElementType.MAIN_TITLE_ELEMENT, "main title", 
                        bibResource);
        Assert.assertTrue(bibResource.hasChild(
                Ld4lObjectProp.HAS_PART, titleElement));        
    }

    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private void buildAndExpectValue(Ld4lTitleElementType type, 
            String value, String expectedValue) throws Exception {
        Entity titleElement = buildTitleElement(type, value);
        Assert.assertEquals(expectedValue, titleElement.getAttribute(
                Ld4lDatatypeProp.VALUE).getValue());                  
    }
    
    private Entity buildTitleElement(Ld4lTitleElementType type, 
            String value, Entity title) throws Exception {
        BuildParams params = new BuildParams()
                .setParent(title) 
                .setValue(value)
                .setType(type); 
        return builder.build(params);        
    }
    
    private Entity buildTitleElement(Ld4lTitleElementType type, 
            String value) throws Exception {
        return buildTitleElement(type, value, new Entity());      
    }
    
}

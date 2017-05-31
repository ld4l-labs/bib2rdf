package org.ld4l.bib2lod.entitybuilders;  

import java.util.HashMap;

import org.apache.jena.rdf.model.Resource;
import org.junit.Test;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class BaseEntityBuilderFactory
 */
public class BaseEntityBuilderFactoryTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------
    
    /**
     * Concrete implementation used to test BaseEntityBuilderFactory
     */
    public static class MockEntityBuilderFactory extends BaseEntityBuilderFactory {
        
        private static HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> typeToBuilderClass = 
                new HashMap<>();
        static {
            typeToBuilderClass.put(MockType.class, null);
        }

        @Override
        public HashMap<Class<? extends Type>, Class<? extends EntityBuilder>> 
                getTypeToBuilderClassMap() {
            return typeToBuilderClass;
        }
        
    }
    
    public static enum MockType implements Type {
        
        TEST_TYPE;

        @Override
        public String uri() {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public Resource ontClass() {
            throw new RuntimeException("Method not implemented.");
        }
    }
    

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = RuntimeException.class)
    public void nullEntityBuilderClassForType_ThrowsRuntimeException() {
        new MockEntityBuilderFactory();       
    }

}

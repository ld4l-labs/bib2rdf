package org.ld4l.bib2lod.entitybuilders;

import java.util.HashMap;
import java.util.Map.Entry;

import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;

public abstract class BaseEntityBuilders implements EntityBuilders {

    // Maps an ontology Type to an EntityBuilder class used to build Entities
    // of that type.
    private HashMap<Class<? extends Type>, EntityBuilder> builderMap;
    
    /**
     * Constructor
     */
    public BaseEntityBuilders() {
        builderMap = new HashMap<>();
        instantiateBuilders();
    }
              
    @Override 
    public void instantiateBuilders() throws EntityBuildersException {
        
        for (Entry<Class<? extends Type>, Class<? extends EntityBuilder>> entry : 
                    getTypeToBuilderClassMap().entrySet()) {
            EntityBuilder builder;
            try {
                builder = EntityBuilder.instance(entry.getValue());
            } catch (EntityBuilderException e) {
                throw new EntityBuildersException(e);
            }
            builderMap.put(entry.getKey(), builder);
        }
    }
    
    @Override
    public HashMap<Class<? extends Type>, EntityBuilder> getBuilders() {
        return builderMap;
    }
    
    @Override
    public EntityBuilder getBuilder(Class<? extends Type> type) {
        return builderMap.get(type);
    }
    

    
    
}

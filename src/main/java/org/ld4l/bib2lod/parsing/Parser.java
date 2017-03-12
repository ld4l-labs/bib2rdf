/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Entity.EntityInstantiationException;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.w3c.dom.Element;

/**
 * Parses input into Resources.
 */
public interface Parser {
    public static class ParserException extends Exception {
        private static final long serialVersionUID = 1L;

        public ParserException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParserException(String message) {
            super(message);
        }

        public ParserException(Throwable cause) {
            super(cause);
        }
    }
    
    /**
     * Signals an exception during parsing of the input. 
     */
    public static class InputParseException extends RuntimeException {         
        protected InputParseException(String msg, Throwable cause) {
            super(msg, cause);                 
        }        
    }
       
// Removing this because the converter knows what type of parser it needs and
// must ask for it specifically, so this doesn't seem to have a purpose.
//    /**
//     * Factory method
//     * @param configuration - the program Configuration
//     * @return the Parser instance
//     * @throws ClassNotFoundException
//     * @throws FileNotFoundException
//     * @throws IOException
//     * @throws ParseException
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     */
//    static Parser instance(Configuration configuration) {
//        return Bib2LodObjectFactory.instance().createParser(configuration);
//    }
    
    /**
     * Parses the input into a list of records.
     * @return a List of records
     */
    public <T> List<T> getRecords(InputDescriptor input);
    
    /**
     * Parses a record into a list of Resources.
     * @param <T> - the type of the record (varies between implementations)
     * @param record - the record to parse
     * @return a List of Resources
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws EntityInstantiationException 
     */
    //public <T> List<Entity> parseRecord(T record);
    // TEMPORARY!! Can't specify Element type here - not common to all parsers
    public List<Entity> parseRecord(Element record) throws 
            ParserException;
    
}

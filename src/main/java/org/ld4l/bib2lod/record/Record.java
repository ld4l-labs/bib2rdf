/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

/**
 * Represents an input record. Record objects should be immutable, providing no 
 * public setter methods, and only final private or protected setters.
 */
public interface Record {
    
    public static class RecordException extends Exception {
        private static final long serialVersionUID = 1L;

        public RecordException(String message, Throwable cause) {
            super(message, cause);
        }

        public RecordException(String message) {
            super(message);
        }

        public RecordException(Throwable cause) {
            super(cause);
        }
    }
    
      public static class RecordInstantiationException extends RecordException {
          private static final long serialVersionUID = 1L;
        
          public RecordInstantiationException(String message, Throwable cause) {
              super(message, cause);
          }
        
          public RecordInstantiationException(String message) {
              super(message);
          }
        
          public RecordInstantiationException(Throwable cause) {
              super(cause);
          }
    }
    
    /**
     * Returns true iff the record is valid.
     * @return
     */
    // TODO Change return type to String: return an error message to be logged
    // for auditing, empty string if valid.
    public boolean isValid();
    

   
}
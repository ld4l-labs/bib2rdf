/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a subfield in a MARCXML record.
 */
public class MarcxmlSubfield extends MarcxmlField {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private String code;

    /**
     * Constructor
     */
    public MarcxmlSubfield(Element element) throws RecordFieldException {
        super(element);       
        code = element.getAttribute("code");
        isValid();
    }

    public String getCode() {
        return code;
    }

    private void isValid() throws RecordFieldException {

        // Here we test only the code format, not whether specific codes are
        // valid for specific data fields.
        if (code == null) {
            throw new RecordFieldException("code is null");
        }
        if (code.equals("")) {
            throw new RecordFieldException("code is empty");
        }
        if (code.equals(" ")) {
            throw new RecordFieldException("code is blank");
        }
        if (textValue == null) {
            throw new RecordFieldException("text value is null");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("texst value is null");
        }
    }

}
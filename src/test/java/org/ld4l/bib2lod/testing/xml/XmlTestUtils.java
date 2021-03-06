/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Helper methods for testing XML conversion.
 */
public final class XmlTestUtils {

    public static Element buildElementFromString(String element)  
            throws RecordFieldException {
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(element.getBytes()))
                    .getDocumentElement();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RecordFieldException(e);
        }
    }
}

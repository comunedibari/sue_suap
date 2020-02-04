/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.client;

import java.io.IOException;
import java.io.StringReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Giuseppe
 */
public class EGrammataIgnoreEntityValidationResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        InputSource is = new InputSource();
        StringReader sr = new StringReader("");
        is.setCharacterStream(sr);
        return is;
    }

}

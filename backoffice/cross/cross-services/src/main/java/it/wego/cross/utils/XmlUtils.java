/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author aleph
 */
public class XmlUtils {

    public static String doXmlTransformation(byte[] templateData, byte[] xmlData) throws TransformerConfigurationException, TransformerException {
        Preconditions.checkNotNull(xmlData);
        Preconditions.checkNotNull(templateData);
        Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new ByteArrayInputStream(templateData)));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter stringWriter = new StringWriter();
        transformer.transform(new StreamSource(new ByteArrayInputStream(xmlData)), new StreamResult(stringWriter));
        return stringWriter.toString();
    }
}

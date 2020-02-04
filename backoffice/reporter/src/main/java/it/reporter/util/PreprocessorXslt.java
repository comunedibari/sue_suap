/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.reporter.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Piergiorgio
 */
public class PreprocessorXslt {

    public String PreprocessorXslt(String xmlInput) throws Exception {
        Writer outWriter = new StringWriter();
        StreamResult result = new StreamResult(outWriter);
        //StreamSource xsltFile = new StreamSource(new File(getClass().getClassLoader().getResource("").getPath()+"includeXml.xsl"));
        StreamSource xsltFile = new StreamSource(getClass().getResourceAsStream("/includeXml.xsl"));
        TransformerFactory xsltFactory = TransformerFactory.newInstance();
        Transformer transformer = xsltFactory.newTransformer(xsltFile);
        StreamSource xmlIn = new StreamSource(new StringReader(xmlInput));
        transformer.transform(xmlIn, result);
        return outWriter.toString();
    }
}

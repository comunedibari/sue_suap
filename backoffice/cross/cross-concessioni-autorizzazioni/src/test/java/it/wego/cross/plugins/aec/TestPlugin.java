/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.aec;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Giuseppe
 */
public class TestPlugin {

    private static final String PRATICA_TEST = "/pratica.xml";
    private static final String TEMPLATE_TEST = "/cross-aec.xsl";

    @Test
    public void testXsltTransform() throws TransformerConfigurationException, TransformerException, IOException {
        Assert.assertNotNull("Template file missing", getClass().getResource(TEMPLATE_TEST));
        InputStream template = getClass().getResourceAsStream(TEMPLATE_TEST);
        Source templateSrc = new StreamSource(template);
        Assert.assertNotNull("XML file missing", getClass().getResource(PRATICA_TEST));
        InputStream xmlToParse = getClass().getResourceAsStream(PRATICA_TEST);
        Source xml = new StreamSource(xmlToParse);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(templateSrc);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        transformer.transform(xml, result);
        String xmlOutput = writer.toString();
        Assert.assertNotNull(xmlOutput);
        Assert.assertTrue(!xmlOutput.isEmpty());
    }

}

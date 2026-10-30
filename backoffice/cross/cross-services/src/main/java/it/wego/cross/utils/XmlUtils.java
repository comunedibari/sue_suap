/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.StringWriter;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import com.google.common.base.Preconditions;


/**
 *
 * @author aleph
 */
public class XmlUtils {

	public static String XPATH_TECHNICIAN_NAME = "//suapcommon:datiProcuratore//aggregate:nome";
	public static String XPATH_TECHNICIAN_SURNAME = "//suapcommon:datiProcuratore//aggregate:cognome";
	public static String XPATH_TECHNICIAN_TAX_CODE = "//suapcommon:datiProcuratore//aggregate:codiceFiscale";
	
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
    
    public static XPath createXpath() {
    	 XPath xpath = XPathFactory.newInstance().newXPath();
         NamespaceContext context = new XmlNamespaceContext(
        		 "acconcestet", "http://www.agid.gov.it/MODULI_PA/SUAP/modulo_acconciatori_estetisti",
                 "suapcommon", "http://www.agid.gov.it/MODULI_PA/SUAP/suap_common_components",
                 "aggregate", "http://www.agid.gov.it/AgID_aggregate_components");
         xpath.setNamespaceContext(context);
         return xpath;
    }
    
    public static String getDataFromPath(String path) throws Exception {
    	StringBuffer sb = new StringBuffer();
    	BufferedReader br = new BufferedReader(new FileReader(path));
    	while(br.ready())
    		sb.append(br.readLine());
    	br.close();
    	String data = sb.toString();
    	data = Utils.convertXmlCharset(data, "ISO-8859-1", "UTF-8");
        return data;
    }
}

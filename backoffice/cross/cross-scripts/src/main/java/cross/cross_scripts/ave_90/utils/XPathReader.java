package cross.cross_scripts.ave_90.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class XPathReader {
	
	public static final String IDENTIFICATIVO_SUAP_XPATH= "//intestazione/ufficio-destinatario/@identificativo-suap"; 
	
	private String attributeValue;
	
	public XPathReader(String xml, String expression) {
        try {
            // 1. Crea un DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Parsing dell'XML dalla stringa
            InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(stream);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xpathExpression = xpath.compile(expression);
            attributeValue = (String) xpathExpression.evaluate(doc, XPathConstants.STRING);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getAttributeValue() {
		return attributeValue;
	}    
	
//    public static void main(String[] args) {
//    	new XPathExample();
//    }
}
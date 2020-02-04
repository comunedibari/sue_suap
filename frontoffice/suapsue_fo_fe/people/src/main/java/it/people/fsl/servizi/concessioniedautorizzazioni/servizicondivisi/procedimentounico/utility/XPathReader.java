/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XPathReader {
	    
//    private Document xmlDocument;
//    private XPath xPath;
//    private ByteArrayInputStream bais;
	private String xmlString;
// PC - problemi encoding - inizio       
        private String encoding = "UTF-8";
    
	    
	public XPathReader(String xmlString, String encoding) {
		this.xmlString = xmlString;
                this.encoding = encoding;
//		this.bais = new ByteArrayInputStream(xmlString.getBytes());
//		initObjects();
	}
// PC - problemi encoding - fine         
	public XPathReader(String xmlString) {
		this.xmlString = xmlString;
//                this.encode = encode;
//		this.bais = new ByteArrayInputStream(xmlString.getBytes());
//		initObjects();
	}
	    
//	private void initObjects(){        
//        try {
//            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//            builderFactory.setNamespaceAware(true);
//            builderFactory.setValidating(false);
//            DocumentBuilder builder = null;
//            builder = builderFactory.newDocumentBuilder();
//            Document xmlDoc = builder.parse(bais);
//
////            org.apache.xpath.XPathAPI.eval(xmlDoc.get, str)
////            
////            XPathExpression xPathExpression = xPath.compile(expression);
////            return xPathExpression.evaluate(xmlDocument, XPathConstants.NODESET);
//        	
//            //xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);  
//            xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("c:\\prova.xml"); 
//            xPath =  XPathFactory.newInstance().newXPath();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (SAXException ex) {
//            ex.printStackTrace();
//        } catch (ParserConfigurationException ex) {
//            ex.printStackTrace();
//        }       
//    }
//    
////    public Object read(String expression, QName returnType){
////        try {
////            XPathExpression xPathExpression = xPath.compile(expression);
////            return xPathExpression.evaluate(xmlDocument, returnType);
////        } catch (XPathExpressionException ex) {
////            ex.printStackTrace();
////            return null;
////        }
////    }
	public String readElementString(String path){
		String ret = null;
		try {

			it.gruppoinit.commons.Config c = new it.gruppoinit.commons.Config(null);
// PC - problemi encoding - inizio                          
			c.setStringXml(new String(xmlString.getBytes(encoding)));
// PC - problemi encoding - fine                         
			Vector ss = (Vector) c.getElemento(path);
			if (ss!=null && ss.size()==1){
				ret = (String) ss.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}

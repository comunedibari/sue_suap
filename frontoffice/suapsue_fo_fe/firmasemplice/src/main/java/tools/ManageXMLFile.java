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
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package tools;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ManageXMLFile {
	
	/**
	 * Write the document in a file
	 * 
	 * @param doc
	 * @param filename
	 */
	public static void writeXmlFile(Document doc, String pathFile) {
	    try {
	        // Prepare the DOM document for writing
	        Source source = new DOMSource(doc);
	        
	        // Prepare the output file
	        File file = new File(pathFile);
	        Result result = new StreamResult(file);

	        // Write the DOM document to the file
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
	        xformer.transform(source, result);
	        
	    } catch (TransformerConfigurationException e) {
	    	e.printStackTrace();
	    } catch (TransformerException e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * Read the file XML
	 * 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException
	 * @throws IOException 
	 * @throws SAXException 
	 * 
	 * @return document
	 */
	public static Document readFileXml(String pathFile) {
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		Document xmlDocument = null;
		try {
			builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(pathFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return xmlDocument;
	}

        public static Document readFileXml(InputStream fileStream) {


		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		Document xmlDocument = null;
		try {
			builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(fileStream);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return xmlDocument;
	}

}

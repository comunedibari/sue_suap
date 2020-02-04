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
package it.people.feservice.utils;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class ConfigDocumentReader {
	
	public static AvailableService getAvailableService(Base64 base64, String serviceConfigFilePath, String basePath) throws ParserConfigurationException, SAXException, IOException {
		
		//AvailableService result = null;
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(serviceConfigFilePath);
		
		Node serviceName = document.getElementsByTagName("nome").item(0).getFirstChild();
		Node activity = document.getElementsByTagName("attivita").item(0).getFirstChild();
		Node subActivity = document.getElementsByTagName("sottoattivita").item(0).getFirstChild();
		Node _package = document.getElementsByTagName("package").item(0).getFirstChild();
		
		AvailableService availableService = new AvailableService(serviceName.getNodeValue(), activity.getNodeValue(), 
				subActivity.getNodeValue(), base64.encode(getRelativeConfigFilePath(serviceConfigFilePath, basePath).getBytes()));
		availableService.set_package(_package.getNodeValue());
		
		return availableService;
		
	}

	private static String getRelativeConfigFilePath(String serviceConfigFilePath, String basePath) {
		
		String result = serviceConfigFilePath;
		
		result = serviceConfigFilePath.substring(basePath.length());
		
		return result;
		
	}
	
}

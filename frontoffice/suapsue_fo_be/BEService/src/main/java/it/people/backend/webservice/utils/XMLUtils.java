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
package it.people.backend.webservice.utils;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 *
 */
public class XMLUtils {

	private static Logger logger = LoggerFactory.getLogger(XMLUtils.class);
	
	public static String marshall(Object bean) {	

	    StringWriter sw = new  StringWriter();
		BeanWriter bw = new BeanWriter(sw);

		try {
			bw.setWriteIDs(false);
			bw.getXMLIntrospector().setAttributesForPrimitives(false);
			bw.getXMLIntrospector().setWrapCollectionsInElement(false);
			bw.enablePrettyPrint();
			bw.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"iso-8859-15\"?>");			
			bw.write(bean);
			bw.flush();
			bw.close();
		}
		catch (Exception e) {
		   logger.error("", e);
		}

		return sw.toString();
	}
	
	public static Object unmarshall(Class clazz,String xml) {
		Object newData = null;
		try {
			BeanReader br = new BeanReader();
			br.getXMLIntrospector().setWrapCollectionsInElement(false);
			br.setMatchIDs(false);
			br.registerBeanClass(clazz);
			newData = br.parse(new StringReader(xml));
		}
		catch (Exception e) {
		   logger.error("", e);
		}
		return newData;
	}
	
}

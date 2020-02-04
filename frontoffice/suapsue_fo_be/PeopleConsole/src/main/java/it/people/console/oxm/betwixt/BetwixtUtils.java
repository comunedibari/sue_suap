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
package it.people.console.oxm.betwixt;

import org.springframework.oxm.XmlMappingException;
import org.xml.sax.SAXException;
import java.beans.IntrospectionException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import it.people.console.oxm.betwixt.BetwixtMarshallingSAXException;
import it.people.console.oxm.betwixt.BetwixtMarshallingIntrospectionException;

import java.lang.OutOfMemoryError;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/gen/2012 11:07:03
 *
 */
public class BetwixtUtils {

	public static XmlMappingException convertBetwixtException(Exception ex, boolean marshalling) {
		if (marshalling) {
			if (ex instanceof SAXException) {
				return new BetwixtMarshallingSAXException((SAXException)ex);
			}
			else if (ex instanceof ParserConfigurationException) {
				return new BetwixtParserConfigurationException((ParserConfigurationException)ex);
			}
			else {
				return new BetwixtMarshallingIntrospectionException((IntrospectionException)ex);
			}
		}
		if (!marshalling) {
			if (ex instanceof TransformerConfigurationException) {
				return new BetwixtTransformerConfigurationException((TransformerConfigurationException)ex);
			}
			else if (ex instanceof SAXException) {
				return new BetwixtUnmarshallingSAXException((SAXException)ex);
			}
			else if (ex instanceof IntrospectionException) {
				return new BetwixtUnmarshallingIntrospectionException((IntrospectionException)ex);
			}
			else {
				return new BetwixtTransformerException((TransformerException)ex);
			}
		}
		return new BetwixtGenericException(ex);
	}
	
	public static XmlMappingException convertBetwixtError(Error err, boolean marshalling) {
		if (!marshalling) {
			if (err instanceof TransformerFactoryConfigurationError) {
				return new BetwixtTransformerFactoryConfigurationError((TransformerFactoryConfigurationError)err);
			}
			else if (err instanceof OutOfMemoryError) {
				return new BetwixtTransformerFactoryConfigurationError((TransformerFactoryConfigurationError)err);
			}
			else {
				return new BetwixtFactoryConfigurationError((FactoryConfigurationError)err);
			}
		}
		return new BetwixtGenericError(err);
	}
	
}

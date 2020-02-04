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
package it.people.backend.webservice.common;

import it.people.backend.webservice.persistence.DBConnector;
import it.people.backend.webservice.persistence.PersistenceException;
import it.people.backend.webservice.persistence.QueryFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.InvalidPropertiesFormatException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public abstract class AbstractProvider implements IProvider {

	protected static Logger _logger = LoggerFactory.getLogger(AbstractProvider.class);

	public AbstractProvider() {
        super();
	}
	
	protected Connection getConnection() throws PersistenceException {
		
		return DBConnector.getInstance().getConnection();
		
	}

	protected final String getXml(XmlObject xmlObject) throws IOException {
		
		String result = "";
		
		XmlOptions optSave = new XmlOptions();
		optSave.setSavePrettyPrint();
		optSave.setSaveOuter();
		optSave.setCharacterEncoding( "ISO-8859-1" );
		optSave.setSaveAggresiveNamespaces();

		InputStream is = xmlObject.newInputStream( optSave );
		BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
		String line = br.readLine();
		while ( line != null ) {
			result += line + "\n";
			line = br.readLine();
		}
		
		br.close();
		is.close();
		
		return result;
		
	}
	
	protected final String getQuery(String queryKey) throws InvalidPropertiesFormatException, IOException {
		
		return QueryFactory.getInstance().getQuery(queryKey);
		
	}
	
}

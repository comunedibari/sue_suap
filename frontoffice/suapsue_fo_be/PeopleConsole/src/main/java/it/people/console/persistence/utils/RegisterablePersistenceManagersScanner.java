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
package it.people.console.persistence.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import it.people.console.persistence.beans.RegisterablePersistenceManager;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 11.02.00
 *
 */
public class RegisterablePersistenceManagersScanner extends AbstractLogger {

	private static final String REGISTERABLE_PERSISTENCE_MANAGERS_FILE = "/registerablepersistencemanagers.properties";
	
	private List<RegisterablePersistenceManager> registerablePersistenceManagers = new ArrayList<RegisterablePersistenceManager>();
	
	public RegisterablePersistenceManagersScanner() {
		
		Properties registerablePersistenceManagersProperties = new Properties();
		
		try {
			registerablePersistenceManagersProperties.load(Class.class.getResourceAsStream(REGISTERABLE_PERSISTENCE_MANAGERS_FILE));
			
			Enumeration<Object> keys = registerablePersistenceManagersProperties.keys();
			
			while(keys.hasMoreElements()) {
				String persistableClass = (String)keys.nextElement();
				String persistenceManagerClass = registerablePersistenceManagersProperties.getProperty(persistableClass);
				RegisterablePersistenceManager registerablePersistenceManager = new RegisterablePersistenceManager(persistableClass, 
						persistenceManagerClass);
				if (logger.isDebugEnabled()) {
					logger.debug("Persistence manager '" + persistenceManagerClass 
							+ "' for persistable class '" + persistableClass + "' " + 
							((registerablePersistenceManager.isRegisterable()) ? "is registerable." 
									: "is not registerable."));
				}
				registerablePersistenceManagers.add(registerablePersistenceManager);
			}
			
		} catch (Exception e) {
			logger.error("Unable to find properties file '" + REGISTERABLE_PERSISTENCE_MANAGERS_FILE  +"'.", e);
		}
		
	}
	
	public final List<RegisterablePersistenceManager> getRegisterablePersistenceManagers() {
		return this.registerablePersistenceManagers;
	}
	
}

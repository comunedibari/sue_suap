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
package it.people.console.persistence.beans;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 11.30.12
 *
 */
public class RegisterablePersistenceManager {

	private Class<?> persistableClass = null;
	
	private Class<?> persistenceManagerClass = null;
	
	private boolean isRegisterable = false;

	public RegisterablePersistenceManager(String persistableClass, String persistenceManagerClass) {
		
		try {
			Class<?> persistableClazz = Class.forName(persistableClass);
			this.setPersistableClass(persistableClazz);
		} catch (ClassNotFoundException e) {
			this.setRegisterable(false);
		}

		try {
			Class<?> persistenceManagerClazz = Class.forName(persistenceManagerClass);
			this.setPersistenceManagerClass(persistenceManagerClazz);
		} catch (ClassNotFoundException e) {
			this.setRegisterable(false);
		}
		
	}

	public final Class<?> getPersistableClass() {
		return persistableClass;
	}

	public final Class<?> getPersistenceManagerClass() {
		return persistenceManagerClass;
	}

	public final boolean isRegisterable() {
		return isRegisterable;
	}

	private void setPersistableClass(Class<?> persistableClass) {
		this.persistableClass = persistableClass;
	}

	private void setPersistenceManagerClass(Class<?> persistenceManagerClass) {
		this.persistenceManagerClass = persistenceManagerClass;
	}

	private void setRegisterable(boolean isRegisterable) {
		this.isRegisterable = isRegisterable;
	}

}

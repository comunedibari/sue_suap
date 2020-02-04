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
package it.people.console.persistence;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import it.people.console.domain.FENode;
import it.people.console.domain.FEService;
import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 09.19.36
 *
 */
public class PersistenceManagerFactory extends AbstractLogger {
	
	private static PersistenceManagerFactory instance = null;
	
	private Map<Class<?>, ?> readPersistenceManagers = new HashMap<Class<?>, Object>();
	
	private Map<Class<?>, ?> writePersistenceManagers = new HashMap<Class<?>, Object>();
	
	private PersistenceManagerFactory() {
		init();
	}
	
	public static PersistenceManagerFactory getInstance() {
		if (instance == null) {
			instance = new PersistenceManagerFactory();
		}
		return instance;
	}
	
	public void registerPersistenceManager(Class<?> persistableClass, Class<?> persistenceManager) {

		if (persistableClass == null) {
			throw new NullPointerException(
					"Unable to register PersistenceManager for null persistableClass!");
		}
		if (persistenceManager == null) {
			throw new NullPointerException(
					"Unable to register PersistenceManager with unspecified class!");
		}

		this.getReadablePersistenceManagers().put(persistableClass,
				createNewInstance(
						persistenceManager, Mode.READ));
		this.getWritablePersistenceManagers().put(persistableClass,
				createNewInstance(
						persistenceManager, Mode.WRITE));

	}
	
	public void unregisterPersistenceManager(Class<?> persistableClass) {

		if (persistableClass == null) {
			throw new NullPointerException(
					"Unable to unregister PersistenceManager for null persistableClass!");
		}
		this.getReadablePersistenceManagers().remove(persistableClass);
		this.getWritablePersistenceManagers().remove(persistableClass);

	}
	
	public IPersistenceManager searchSuitablePersistenceManager(Class<?> persistableClass, Mode persistenceMode) {

		if (persistableClass == null) {
			return null;
		}

		if (persistableClass.isArray()) {
			persistableClass = persistableClass.getComponentType();

		}
		IPersistenceManager persistenceManager = persistenceMode == Mode.READ ?
				(IPersistenceManager) this.getReadablePersistenceManagers().get(persistableClass) :
					(IPersistenceManager) this.getWritablePersistenceManagers().get(persistableClass);
				
		if (persistenceManager == null) {
			Class<?>[] interfaces = persistableClass.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				if ((persistenceManager = searchSuitablePersistenceManager(interfaces[i], persistenceMode)) != null) {
					return persistenceManager;
				}
			}

			Class<?> parent = persistableClass.getSuperclass();
			persistenceManager = searchSuitablePersistenceManager(parent, persistenceMode);
			if (persistenceManager != null) {
				return persistenceManager;
			}
		}

		return persistenceManager;

	}
	
	public IPersistenceManager get(Class<?> persistableClass, Mode persistenceMode) {

		if (persistableClass == null) {
			throw new NullPointerException("Null persistableClass: unable to instantiate PersistenceManager!");
		}

		IPersistenceManager persistenceManager = searchSuitablePersistenceManager(persistableClass, persistenceMode);
		if (persistenceManager == null) {
			throw new UnsupportedOperationException(
					"Unable to instantiate a persistence manager for specified class ("
					+ persistableClass + ")!");
		}

		return persistenceManager;

	}
	
	public final int getReadablePersistenceManagersSize() {
		int result = 0;
		if (this.getReadablePersistenceManagers() != null)  {
			result = this.getReadablePersistenceManagers().size();
		}
		return result;
	}

	public final int getWritablePersistenceManagersSize() {
		int result = 0;
		if (this.getWritablePersistenceManagers() != null)  {
			result = this.getWritablePersistenceManagers().size();
		}
		return result;
	}
	
	private Map getReadablePersistenceManagers() {
		return this.readPersistenceManagers;
	}

	private Map getWritablePersistenceManagers() {
		return this.writePersistenceManagers;
	}
	
	private IPersistenceManager createNewInstance(Class<?> persistenceManager, Mode persistenceMode) {
		try {
			Constructor<?> constructor = persistenceManager.getConstructor(new Class[] {Mode.class});
			return (IPersistenceManager) constructor.newInstance(new Object[] {persistenceMode});
		} catch (Exception ex) {
			return null;
		}

	}
	
	private void init() {
		
		registerPersistenceManager(FENode.class, FENodesPersistenceManager.class);
		registerPersistenceManager(FEService.class, FEServicePersistenceManager.class);
		
	}
	
}

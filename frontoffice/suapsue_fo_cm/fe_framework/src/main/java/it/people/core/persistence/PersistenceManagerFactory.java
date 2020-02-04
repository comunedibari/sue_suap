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
package it.people.core.persistence;

import it.people.City;
import it.people.content.CategoryImpl;
import it.people.content.ContentImpl;
import it.people.core.PplDelegate;
import it.people.core.PplPrincipal;
import it.people.core.PplProcessDelegate;
import it.people.core.PplUser;
import it.people.core.ServiceProfileStore;
import it.people.core.persistence.ContentPersistenceMgr;
import it.people.fsl.servizi.oggetticondivisi.UserSignalledBug;
import it.people.fsl.servizi.oggetticondivisi.UserSuggestion;
import it.people.process.NotSubmittableProcess;
import it.people.process.SubmittedProcess;
import it.people.process.config.ConfigSender;
import it.people.process.data.PplPersistentData;
import it.people.process.sign.SignedDataHolder;
import it.people.process.sign.StepSign;
import it.people.vsl.UnsentProcessPipelineData;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManagerFactory {
    private static PersistenceManagerFactory ourInstance;

    private Map m_obj_readPersistenceManagers = new HashMap();
    private Map m_obj_writePersistenceManagers = new HashMap();

    public synchronized static PersistenceManagerFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PersistenceManagerFactory();
	}
	return ourInstance;
    }

    private PersistenceManagerFactory() {
	initialize();
    }

    public PersistenceManager get(Class p_obj_clazz,
	    PersistenceManager.Mode p_obj_mode) {
	if (p_obj_clazz == null) {
	    throw new NullPointerException(
		    "Null <object to persist>class: unable to instantiate PersistenceManager!");
	}

	PersistenceManager pm = searchSuitablePersistenceManager(p_obj_clazz,
		p_obj_mode);
	if (pm == null) {
	    throw new UnsupportedOperationException(
		    "Unable to instantiate a persistence manager for specified class ("
			    + p_obj_clazz + ")!");
	}

	return pm;
    }

    public void registerPersistenceManager(Class p_obj_objToPersistClass,
	    Class p_obj_persistenceManagerClass) {
	if (p_obj_objToPersistClass == null) {
	    throw new NullPointerException(
		    "Unable to register PersistenceManager for null <object to persist>class!");
	}
	if (p_obj_persistenceManagerClass == null) {
	    throw new NullPointerException(
		    "Unable to register PersistenceManager with unspecified class!");
	}

	m_obj_readPersistenceManagers.put(
		p_obj_objToPersistClass,
		createNewInstance(p_obj_persistenceManagerClass,
			PersistenceManager.Mode.READ));
	m_obj_writePersistenceManagers.put(
		p_obj_objToPersistClass,
		createNewInstance(p_obj_persistenceManagerClass,
			PersistenceManager.Mode.WRITE));
    }

    public void unregisterPersistenceManager(Class p_obj_objToPersistClass) {
	if (p_obj_objToPersistClass == null) {
	    throw new NullPointerException(
		    "Unable to unregister PersistenceManager for null clas object!");
	}
	m_obj_readPersistenceManagers.remove(p_obj_objToPersistClass);
	m_obj_writePersistenceManagers.remove(p_obj_objToPersistClass);
    }

    private PersistenceManager searchSuitablePersistenceManager(
	    Class p_obj_clazz, PersistenceManager.Mode p_obj_mode) {
	if (p_obj_clazz == null) {
	    return null;
	}

	if (p_obj_clazz.isArray()) {
	    p_obj_clazz = p_obj_clazz.getComponentType();

	}
	PersistenceManager persistenceManager = p_obj_mode == PersistenceManager.Mode.READ ? (PersistenceManager) m_obj_readPersistenceManagers
		.get(p_obj_clazz)
		: (PersistenceManager) m_obj_writePersistenceManagers
			.get(p_obj_clazz);
	if (persistenceManager == null) {
	    Class[] interfaces = p_obj_clazz.getInterfaces();
	    for (int i = 0; i < interfaces.length; i++) {
		if ((persistenceManager = searchSuitablePersistenceManager(
			interfaces[i], p_obj_mode)) != null) {
		    return persistenceManager;
		}
	    }

	    Class parent = p_obj_clazz.getSuperclass();
	    persistenceManager = searchSuitablePersistenceManager(parent,
		    p_obj_mode);
	    if (persistenceManager != null) {
		return persistenceManager;
	    }
	}
	return persistenceManager;
    }

    private PersistenceManager createNewInstance(Class p_obj_persistMgrClass,
	    PersistenceManager.Mode p_obj_mode) {
	try {
	    Constructor ctor = p_obj_persistMgrClass
		    .getConstructor(new Class[] { PersistenceManager.Mode.class });
	    return (PersistenceManager) ctor
		    .newInstance(new Object[] { p_obj_mode });
	} catch (Exception ex) {
	    return null;
	}

    }

    private void initialize() {
	registerPersistenceManager(PplPersistentData.class,
		PplPersistentDataPersMgr.class);
	registerPersistenceManager(City.class, CommunePersistenceMgr.class);
	registerPersistenceManager(SubmittedProcess.class,
		SubmittedProcessPersistenceMgr.class);
	registerPersistenceManager(CategoryImpl.class,
		CategoryPersistenceMgr.class);
	registerPersistenceManager(ContentImpl.class,
		ContentPersistenceMgr.class);
	// registerPersistenceManager(config.class, ConfigPersistenceMgr.class);
	registerPersistenceManager(SignedDataHolder.class,
		SignPersistenceManager.class);
	registerPersistenceManager(StepSign.class,
		ProcessStepSignPersistenceMgr.class);
	registerPersistenceManager(PplUser.class,
		PplUserPersistenceManager.class);
	registerPersistenceManager(ConfigSender.class,
		ConfigSenderPersistanceMgr.class);
	registerPersistenceManager(PplProcessDelegate.class,
		PplProcessDelegatePersistanceMgr.class);
	registerPersistenceManager(ServiceProfileStore.class,
		ServiceProfileStorePersistenceManager.class);
	// SignedDataHolder
	registerPersistenceManager(PplPrincipal.class,
		PplPrincipalPersistenceMgr.class);
	registerPersistenceManager(PplDelegate.class,
		PplDelegatePersistenceMgr.class);
	registerPersistenceManager(NotSubmittableProcess.class,
		NotSubmittableProcessPersistenceMgr.class);

	registerPersistenceManager(UnsentProcessPipelineData.class,
		UnsentProcessPipelineDataPersistenceMgr.class);

	registerPersistenceManager(UserSignalledBug.class,
		UserSignalledBugsPersistenceMgr.class);
	registerPersistenceManager(UserSuggestion.class,
		UserSuggestionsPersistenceMgr.class);

    }

    public final static void resetInstance() {
	ourInstance = null;
    }
}

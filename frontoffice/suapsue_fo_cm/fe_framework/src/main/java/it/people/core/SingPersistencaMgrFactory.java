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
package it.people.core;

import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.process.sign.SignedDataHolder;

import java.util.Collection;
import java.util.HashMap;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

public class SingPersistencaMgrFactory {
    private static SingPersistencaMgrFactory ourInstance;

    private HashMap m_configurations;

    public synchronized static SingPersistencaMgrFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new SingPersistencaMgrFactory();
	}
	return ourInstance;
    }

    private SingPersistencaMgrFactory() {
	m_configurations = new HashMap();
    }

    public Collection get(Long p_process, String p_step) throws peopleException {

	if (p_process == null) {
	    return null;
	}
	if (p_step == null || p_step.length() == 0) {
	    return null;
	}

	String searchKey = p_process.toString() + p_step;

	Collection configurations = (Collection) m_configurations
		.get(searchKey);
	if (configurations == null) {
	    configurations = read(p_process, p_step);
	    if (configurations != null) {
		m_configurations.put(searchKey, configurations);
	    }
	}
	return configurations;
	// @todo lanciare una eccezione di CommuneNotFound
    }

    public Collection get(Long p_process) throws peopleException {

	if (p_process == null) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SignedDataHolder.class, PersistenceManager.Mode.READ);

	try {
	    // Compongo i criteri
	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("parentOid", p_process);

	    Collection configuration = persMgr.get(QueryFactory.newQuery(
		    SignedDataHolder.class, crtr));
	    return configuration;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    public Collection read(Long p_process, String p_step)
	    throws peopleException {

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SignedDataHolder.class, PersistenceManager.Mode.READ);

	try {
	    // Compongo i criteri
	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("parentOid", p_process);
	    crtr.addEqualTo("stepOid", p_step);

	    Collection configuration = persMgr.get(QueryFactory.newQuery(
		    SignedDataHolder.class, crtr));

	    if (!configuration.isEmpty()) {
		return configuration;
	    } else {
		return null;
	    }
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

}

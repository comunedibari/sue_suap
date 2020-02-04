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

/*import it.people.core.persistence.PersistenceManager;
 import it.people.core.persistence.PersistenceManagerFactory;*/
import it.people.core.persistence.exception.peopleException;

import java.util.Collection;
import java.util.HashMap;

/**
 * Factory per gli step di firma dei processi.
 */
public class ProcessStepSignFactory {
    private static ProcessStepSignFactory ourInstance;

    private HashMap m_configurations;

    public synchronized static ProcessStepSignFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ProcessStepSignFactory();
	}
	return ourInstance;
    }

    private ProcessStepSignFactory() {
	m_configurations = new HashMap();
    }

    /**
     * Recupera le configurazioni degli step di firma per il processo.
     * 
     * @param p_process
     * @return
     * @throws peopleException
     */
    public Collection get(String p_process) throws peopleException {

	if (p_process == null || p_process.length() == 0) {
	    return null;
	}

	Collection configurations = (Collection) m_configurations
		.get(p_process);
	if (configurations == null) {
	    configurations = read(p_process);
	    if (configurations != null) {
		m_configurations.put(p_process, configurations);
	    }
	}
	return configurations;
	// @todo lanciare una eccezione di CommuneNotFound
    }

    // TODO OJB
    public Collection read(String p_process) throws peopleException {

	/*
	 * PersistenceManager persMgr =
	 * PersistenceManagerFactory.getInstance().get( StepSign.class,
	 * PersistenceManager.Mode.READ);
	 * 
	 * //Compongo i criteri Criteria crtr = new Criteria();
	 * crtr.addEqualTo("processName", p_process);
	 * 
	 * 
	 * Collection configuration =
	 * persMgr.get(QueryFactory.newQuery(StepSign.class,crtr));
	 * 
	 * if (!configuration.isEmpty()) { return configuration; } else { return
	 * null; } }
	 */
	return null;
    }
}

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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 *
 */
public class PplDelegateFactory {

    private static PplDelegateFactory ourInstance;
    private HashMap m_delegates;

    /**
     * 
     * @return Istanza Corrente
     */
    public synchronized static PplDelegateFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplDelegateFactory();
	}
	return ourInstance;
    }

    private PplDelegateFactory() {
	m_delegates = new HashMap();
    }

    /**
     * 
     * @param processOid
     *            Processo
     * @param userID
     *            Utente
     * @param processClass
     *            Classe del processo
     * @param validFrom
     *            Data di inizio validit? della delega
     * @param validTo
     *            Data di fine validit? della delega
     * @return Restituisce il delegato o null se non lo trova
     * @throws peopleException
     */
    public PplDelegate getDelegate(Long processOid, String userID,
	    Class processClass, Timestamp validFrom, Timestamp validTo)
	    throws peopleException {
	Collection processDelegate = getProcessDelegates(userID, processClass,
		validFrom, validTo);
	if (processDelegate != null) {
	    PplProcessDelegate ppd = (PplProcessDelegate) processDelegate
		    .iterator().next();
	    PplDelegate item = new PplDelegate(ppd.getUserId(),
		    ppd.getDelegateId());
	    item.setOid(processOid);
	    return item;
	}
	return null;
    }

    /**
     * 
     * @param item
     *            Oggetto delegato da eliminare
     */
    public void deleteDelegate(PplDelegate item) {
	PersistenceManager pm = PersistenceManagerFactory.getInstance().get(
		PplDelegate.class, PersistenceManager.Mode.WRITE);
	try {
	    ArrayList toDelete = new ArrayList(0);
	    toDelete.add(item);
	    pm.delete(toDelete);
	} finally {
	    if (pm != null) {
		pm.close();
	    }
	}
    }

    /**
     * Svuota la cache
     */
    public void clear() {
	m_delegates.clear();
    }

    /**
     * Cancella il delegato del vecchio utente e restituisce quello nuovo o null
     * se non lo trova
     * 
     * @param processOid
     *            Processo
     * @param newUserID
     *            Nuovo utente
     * @param oldUserID
     *            Vecchio utente
     * @param processClass
     *            Classe del processo
     * @param validFrom
     *            Data di inizio validit? della delega del nuovo utente
     * @param validTo
     *            Data di fine validit? della delega del nuovo utente
     * @return Restituisce il delegato o null se non lo trova
     * @throws peopleException
     */
    public PplDelegate changeDelegate(Long processOid, String newUserID,
	    String oldUserID, Class processClass, Timestamp validFrom,
	    Timestamp validTo) throws peopleException {
	PplDelegate newDelegate = getDelegate(processOid, newUserID,
		processClass, validFrom, validTo);
	PplDelegate oldDelegate = getDelegate(processOid, oldUserID,
		processClass, null, null);

	if (oldDelegate != null)
	    deleteDelegate(oldDelegate);

	return newDelegate;
    }

    /**
     * 
     * @param userID
     *            Id utente
     * @param processClass
     *            Classe del processo
     * @param validFrom
     *            Inizio validit? della delega
     * @param validTo
     *            Fine validit? della delega
     * @return Restituisce un oggetto ProcessDelegates
     * @throws peopleException
     */
    public Collection getProcessDelegates(String userID, Class processClass,
	    Timestamp validFrom, Timestamp validTo) throws peopleException {

	if (userID == null && processClass == null && validFrom == null
		&& validTo == null) {
	    return null;
	}

	String key = "";

	if (userID != null) {
	    key = userID + ".";
	}
	if (processClass != null) {
	    key = key + processClass.getName() + ".";
	}
	if (validFrom != null) {
	    key = key + validFrom + ".";
	}
	if (validTo != null) {
	    key = key + validTo + ".";
	}

	Collection result = (Collection) m_delegates.get(key);

	if (result == null) {
	    result = readProcessDelegates(userID, processClass, validFrom,
		    validTo);
	    if (result != null) {
		m_delegates.put(key, result);
		return result;
	    }
	}
	return result;

    }

    private Collection readProcessDelegates(String userID,
	    Class processClassName, Timestamp validFrom, Timestamp validTo)
	    throws peopleException {
	PersistenceManager pm = PersistenceManagerFactory.getInstance().get(
		PplProcessDelegate.class, PersistenceManager.Mode.READ);

	try {
	    if (userID == null && processClassName == null && validFrom == null
		    && validTo == null) {
		return null;
	    }

	    Criteria userCrit = new Criteria();

	    if (userID != null) {
		userCrit.addEqualTo("userId", userID);
	    }

	    if (processClassName != null) {
		Criteria processCrit = new Criteria();
		processCrit.addEqualTo("processClassName", processClassName);
		userCrit.addAndCriteria(processCrit);
	    }

	    if (validFrom != null) {
		Criteria validFromCrit = new Criteria();
		// validFromCrit.addEqualTo("validFrom", validFrom);
		validFromCrit.addGreaterThan("validFrom", validFrom);
		userCrit.addAndCriteria(validFromCrit);
	    }

	    if (validTo != null) {
		Criteria validToCrit = new Criteria();
		// validToCrit.addEqualTo("validTo", validTo);
		validToCrit.addLessThan("validTo", validTo);
		userCrit.addAndCriteria(validToCrit);
	    }

	    // Collection results
	    // =PplProcessDelegateManager.getInstance().get(null,QueryFactory.newQuery(PplProcessDelegate.class,userCrit));

	    Collection results = pm.get(QueryFactory.newQuery(
		    PplProcessDelegate.class, userCrit));
	    if (!results.isEmpty()) {
		return results;
	    }
	    return null;
	} finally {
	    if (pm != null) {
		pm.close();
	    }
	}
    }
}

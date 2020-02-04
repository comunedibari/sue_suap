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
import java.util.Collection;
import java.util.Iterator;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * 
 * User: Luigi Corollo Date: 8-gen-2004 Time: 11.26.06 <br>
 * <br>
 * Manager dei ProcessDelegate.
 */
public class PplProcessDelegateManager {

    // Log
    private org.apache.log4j.Category cat = org.apache.log4j.Category
	    .getInstance(PplProcessDelegateManager.class.getName());

    private static PplProcessDelegateManager ourInstance;

    /**
     * 
     * @return Istanza
     */
    public synchronized static PplProcessDelegateManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplProcessDelegateManager();
	}
	return ourInstance;
    }

    // costruttore
    private PplProcessDelegateManager() {
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param clazz
     * @return Restiuisce un oggetto PplProcessDelegate
     */
    public PplProcessDelegate create(PeopleContext context, Class clazz) {
	try {
	    PplProcessDelegate newObject = new PplProcessDelegate();
	    return newObject;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Salva sulla base dati
     * 
     * @param PplProcessDelegate
     *            PplProcessDelegate da salvare
     * @throws it.people.core.persistence.exception.peopleException
     * 
     */
    public void set(PplProcessDelegate PplProcessDelegate)
	    throws peopleException {
	if (PplProcessDelegate == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplProcessDelegate.class, PersistenceManager.Mode.WRITE);
	try {
	    persMgr.definePrimaryKey(PplProcessDelegate);
	    persMgr.set(PplProcessDelegate);
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    /**
     * 
     * @param oid
     *            Id della categoria da recuperare
     * @return Restituisce la PplProcessDelegate cercata
     * @throws peopleException
     * 
     */
    public PplProcessDelegate get(Long oid) throws peopleException {
	if (oid == null) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplProcessDelegate.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("oid", oid);

	    process = persMgr.get(QueryFactory.newQuery(
		    PplProcessDelegate.class, crtr));
	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();

		return (PplProcessDelegate) iter.next();

	    }
	    return null;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    // Search

    /**
     * @deprecated
     * @param people
     * @param p_obj_query
     * @return
     * @throws peopleException
     */
    public Collection get(PeopleContext people, Query p_obj_query)
	    throws peopleException {
	return get(p_obj_query);
    }

    // Search by Query

    public Collection get(Query p_obj_query) throws peopleException {
	Collection collection = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    PplProcessDelegate.class, PersistenceManager.Mode.READ);

	    collection = persMgr.get(p_obj_query);
	    return collection;
	} catch (Exception ex) {
	    cat.error(ex);
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
	return collection;
    }

    // Search by UserId ?
    // Search by DelegateId ?

    /**
     * 
     * @param userID
     * @return
     * @throws peopleException
     */
    public Collection getProcessDelegates(String userID) throws peopleException {
	return PplDelegateFactory.getInstance().getProcessDelegates(userID,
		null, null, null);
    }

    /**
     * 
     * @param userID
     * @return
     * @throws peopleException
     */
    public Collection getProcessDelegates(String userID, Class processClass,
	    Timestamp validFrom, Timestamp validTo) throws peopleException {
	return PplDelegateFactory.getInstance().getProcessDelegates(userID,
		processClass, validFrom, validTo);
    }

    /**
     * 
     * @param processDelegate
     * @return
     * @throws peopleException
     */
    public Collection getProcessDelegates(PplProcessDelegate processDelegate)
	    throws peopleException {

	if (processDelegate == null)
	    return null;

	Criteria userCrit = new Criteria();

	if (processDelegate.getUserId() != null) {
	    userCrit.addEqualTo("userId", processDelegate.getUserId());
	}

	if (processDelegate.getDelegateId() != null) {
	    Criteria delegateCrit = new Criteria();
	    delegateCrit.addEqualTo("delegateId",
		    processDelegate.getDelegateId());
	    userCrit.addAndCriteria(delegateCrit);
	}

	if (processDelegate.getCommuneId() != null) {
	    Criteria communeCrit = new Criteria();
	    communeCrit.addEqualTo("communeId", processDelegate.getCommuneId());
	    userCrit.addAndCriteria(communeCrit);
	}

	if (processDelegate.getProcessName() != null) {
	    Criteria processCrit = new Criteria();
	    processCrit.addEqualTo("processName",
		    processDelegate.getProcessName());
	    userCrit.addAndCriteria(processCrit);
	}

	if (processDelegate.getValidFrom() != null) {
	    Criteria validFromCrit = new Criteria();
	    validFromCrit.addLessThan("validFrom",
		    processDelegate.getValidFrom());
	    userCrit.addAndCriteria(validFromCrit);
	}

	if (processDelegate.getValidTo() != null) {
	    Criteria validToCrit = new Criteria();
	    validToCrit.addGreaterThan("validTo", processDelegate.getValidTo());
	    userCrit.addAndCriteria(validToCrit);
	}

	Query query = QueryFactory.newQuery(PplProcessDelegate.class, userCrit);
	return this.get(query);
    }

    // Cancella Solo la PplProcessDelegate per id
    /**
     * 
     * @param oid
     *            Id della categoria da eliminare
     * 
     */
    public void delete(Long oid) {
	if (oid == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(PplProcessDelegate.class, PersistenceManager.Mode.WRITE);
	try {
	    // Creazione query delete
	    PplProcessDelegate PplProcessDelegate = new PplProcessDelegate();
	    PplProcessDelegate.setOid(oid);
	    Query query = new QueryByCriteria(PplProcessDelegate);

	    // delete
	    persMgr.delete(query);
	} catch (Exception ex) {
	    cat.error(ex);
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }
}

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
import it.people.process.SubmittedProcess;
import it.people.process.SubmittedProcessHistory;
import it.people.process.SubmittedProcessState;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * User: sergio Date: Sep 19, 2003 Time: 12:00:14 PM <br>
 * <br>
 * Classe per la gestione dei processi inviati
 */
public class SubmittedProcessManager {

    private Category cat = Category.getInstance(SubmittedProcessManager.class
	    .getName());

    private static SubmittedProcessManager ourInstance;

    /**
     * 
     * @return Istanza SubmittedProcessManager
     */
    public synchronized static SubmittedProcessManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new SubmittedProcessManager();
	}
	return ourInstance;
    }

    private SubmittedProcessManager() {
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @return Restituisce l'oggetto SubmittedProcess creato
     */
    public SubmittedProcess create(PeopleContext context) {
	try {
	    SubmittedProcess newObject = new SubmittedProcess();
	    Timestamp submittionTime = new Timestamp(new Date().getTime());
	    newObject.setSubmittedTime(submittionTime);

	    if (context != null)
		newObject.setUser(context.getUser());

	    SubmittedProcessHistory sph = new SubmittedProcessHistory();
	    sph.setTransactionTime(submittionTime);
	    sph.setState(SubmittedProcessState.INITIALIZING);
	    newObject.addHistoryStates(sph);

	    return newObject;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Salva l'oggetto SubmittedProcess nella base dati
     * 
     * @param context
     *            PeopleContext
     * @param p_obj_process
     *            Oggetto SubmittedProcess da salvare
     * @throws peopleException
     */
    public void set(PeopleContext context, SubmittedProcess p_obj_process)
	    throws peopleException {
	if (p_obj_process == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SubmittedProcess.class, PersistenceManager.Mode.WRITE);
	try {
	    persMgr.definePrimaryKey(p_obj_process);

	    // setto l'user se non e' ancora definito.
	    if (p_obj_process.getUser() == null)
		p_obj_process.setUser(context.getUser());

	    persMgr.set(p_obj_process);
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
     * @param context
     *            PeopleContext
     * @param oid
     *            Id del processo da recuperare
     * @return Restituisce l'oggetto SubmittedProcess cercato
     * @throws peopleException
     */
    public SubmittedProcess get(PeopleContext context, Long oid)
	    throws peopleException {
	if (oid == null) {
	    return null;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SubmittedProcess.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("oid", oid);

	    Criteria crtrUser = new Criteria();
	    crtrUser.addEqualTo("user", context.getUser());
	    crtr.addAndCriteria(crtrUser);

	    process = persMgr.get(QueryFactory.newQuery(SubmittedProcess.class,
		    crtr));
	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();

		return (SubmittedProcess) iter.next();

	    }
	    return null;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param oid
     *            Id del pending process collegato
     * @return Restituisce l'oggetto SubmittedProcess cercato
     * @throws peopleException
     */
    public SubmittedProcess getFromPending(PeopleContext context, Long oid)
	    throws peopleException {
	if (oid == null) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SubmittedProcess.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("editableProcessId", oid);

	    Criteria crtrUser = new Criteria();
	    crtrUser.addEqualTo("user", context.getUser());
	    crtr.addAndCriteria(crtrUser);

	    process = persMgr.get(QueryFactory.newQuery(SubmittedProcess.class,
		    crtr));
	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();

		return (SubmittedProcess) iter.next();

	    }
	    return null;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    public void delete(PeopleContext context, Long oid) {
    }

    public boolean isSendError(Long oid) throws peopleException {

	if (oid == null) {
	    return false;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(SubmittedProcess.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria queryCriteria = new Criteria();
	    queryCriteria.addEqualTo("oid", oid);

	    Criteria sendErrorCriteria = new Criteria();
	    sendErrorCriteria.addEqualTo("send_error", 1);
	    queryCriteria.addAndCriteria(sendErrorCriteria);

	    process = persMgr.get(QueryFactory.newQuery(SubmittedProcess.class,
		    queryCriteria));

	    return process != null && !process.isEmpty() && process.size() == 1;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

}

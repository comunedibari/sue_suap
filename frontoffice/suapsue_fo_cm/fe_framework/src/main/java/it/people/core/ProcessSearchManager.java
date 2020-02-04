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
import it.people.process.data.PplPersistentData;
import it.people.util.ProcessUtils;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * 
 * User: sergio Date: Sep 17, 2003 Time: 7:18:33 PM <br>
 * <br>
 * Manager delle ricerche sui processi.
 */
public class ProcessSearchManager {

    private Category cat = Category.getInstance(ProcessSearchManager.class
	    .getName());

    private static ProcessSearchManager ourInstance;

    public synchronized static ProcessSearchManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ProcessSearchManager();
	}
	return ourInstance;
    }

    private ProcessSearchManager() {
    }

    /**
     * 
     * @param context
     * @param p_obj_criteria
     * @return
     * @throws peopleException
     */
    public Collection get(PeopleContext context, Criteria p_obj_criteria)
	    throws peopleException {
	return get(context, p_obj_criteria, new Boolean(false));
    }

    /**
     * 
     * @param context
     * @param p_obj_criteria
     * @param sent
     * @return
     * @throws peopleException
     */
    public Collection get(PeopleContext context, Criteria p_obj_criteria,
	    Boolean sent) throws peopleException {
	Collection processes = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    PplPersistentData.class, PersistenceManager.Mode.READ);

	    Criteria crtr = new Criteria();
	    if (p_obj_criteria != null) {
		crtr.addAndCriteria(p_obj_criteria);
	    }

	    if (sent != null) {
		Criteria crtrSent = new Criteria();
		crtrSent.addEqualTo("sent", sent);
		crtr.addAndCriteria(crtrSent);
	    }

	    QueryByCriteria qryToExecute = QueryFactory.newQuery(
		    PplPersistentData.class, crtr);
	    qryToExecute.addOrderByAscending("userID");
	    qryToExecute.addOrderByAscending("oid");

	    processes = persMgr.get(qryToExecute);

	    /**
	     * Simulazione del distinct non fattibile via SQL a causa del campo
	     * value di submitted_process uso LinkedHashMap per mantenere
	     * l'ordinamento
	     */
	    if (processes != null && !processes.isEmpty()) {
		LinkedHashMap map = new LinkedHashMap();
		Iterator iter = processes.iterator();
		while (iter.hasNext()) {
		    PplPersistentData item = (PplPersistentData) iter.next();
		    map.put(item.getOid(), item);
		}
		processes = map.values();
	    }
	    return ProcessUtils.processPersistentData2pplProcess(context,
		    processes);

	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    public Collection getMine(PeopleContext context, Criteria criteria,
	    Boolean sent) throws peopleException {
	return getMine(context, criteria, sent, Boolean.TRUE);
    }

    /**
     * 
     * @param context
     * @param p_obj_criteria
     * @param sent
     * @return
     * @throws peopleException
     * 
     */
    public Collection getMine(PeopleContext context, Criteria p_obj_criteria,
	    Boolean sent, Boolean deserialize) throws peopleException {
	Collection processes = null;
	try {
	    processes = getMinePplPersistentData(context, p_obj_criteria, sent);

	    long startTime = 0;
	    if (cat.isDebugEnabled()) {
		startTime = GregorianCalendar.getInstance().getTimeInMillis();
	    }

	    Collection abstractProcesses = ProcessUtils
		    .processPersistentData2pplProcess(context, processes,
			    deserialize.booleanValue());

	    if (cat.isDebugEnabled()) {
		String message = "getMine() - "
			+ "Tempo di copia in abstract process di "
			+ processes.size()
			+ " pratiche "
			+ (GregorianCalendar.getInstance().getTimeInMillis() - startTime)
			+ " millisecondi";

		cat.debug(message);
		startTime = GregorianCalendar.getInstance().getTimeInMillis();
	    }

	    return abstractProcesses;

	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	}
    }

    /**
     * Ritorna la collection dei PplPersistenData dell'utente, gli oggetti non
     * sono deserializzati ne copiati nell'abstract Process, il metodo � utile
     * per ottenere l'elenco delle pratiche dell'utente in modo efficiente
     * quando non � necessario inizializzare ogni processo.
     * 
     * @param context
     * @param p_obj_criteria
     *            eventuale criterio di filtro (null per nessun filtro)
     * @param sent
     *            true per le pratiche inviate, false per le pratiche pendenti
     * @return una collection di PplPersistentData
     * @throws peopleException
     */
    public Collection getMinePplPersistentData(PeopleContext context,
	    Criteria p_obj_criteria, Boolean sent) throws peopleException {
	PersistenceManager persMgr = null;
	try {
	    long startTime = 0;

	    if (cat.isDebugEnabled()) {
		startTime = GregorianCalendar.getInstance().getTimeInMillis();
	    }

	    persMgr = PersistenceManagerFactory.getInstance().get(
		    PplPersistentData.class, PersistenceManager.Mode.READ);
	    Criteria crtr = new Criteria();

	    if (p_obj_criteria != null) {
		crtr.addAndCriteria(p_obj_criteria);
	    }

	    if (sent != null) {
		Criteria crtrSent = new Criteria();
		crtrSent.addEqualTo("sent", sent);
		crtr.addAndCriteria(crtrSent);
	    }

	    Criteria crtrUser = new Criteria();
	    crtrUser.addEqualTo("principal.userID", context.getUser()
		    .getUserID());
	    crtr.addAndCriteria(crtrUser);

	    Criteria crtrCity = new Criteria();
	    crtrCity.addEqualTo("commune", context.getCommune());
	    crtr.addAndCriteria(crtrCity);

	    QueryByCriteria qryToExecute = QueryFactory.newQuery(
		    PplPersistentData.class, crtr);
	    qryToExecute.addOrderByAscending("userID");
	    qryToExecute.addOrderByAscending("oid");
	    Collection persistentDataCollection = persMgr.get(qryToExecute);

	    if (cat.isDebugEnabled()) {
		String message = "getMine() - "
			+ "Tempo per il caricamento di "
			+ persistentDataCollection.size()
			+ " pratiche "
			+ (GregorianCalendar.getInstance().getTimeInMillis() - startTime)
			+ " millisecondi";

		cat.debug(message);
		startTime = GregorianCalendar.getInstance().getTimeInMillis();
	    }

	    /**
	     * Simulazione del distinct non fattibile via SQL a causa del campo
	     * value di submitted_process uso LinkedHashMap per mantenere
	     * l'ordinamento
	     */
	    if (persistentDataCollection != null
		    && !persistentDataCollection.isEmpty()) {
		LinkedHashMap map = new LinkedHashMap();
		Iterator iter = persistentDataCollection.iterator();
		while (iter.hasNext()) {
		    PplPersistentData item = (PplPersistentData) iter.next();
		    map.put(item.getOid(), item);
		}
		persistentDataCollection = map.values();
	    }

	    if (cat.isDebugEnabled()) {
		String message = "getMine() - "
			+ "Tempo di distinct di "
			+ persistentDataCollection.size()
			+ " pratiche "
			+ (GregorianCalendar.getInstance().getTimeInMillis() - startTime)
			+ " millisecondi";

		cat.debug(message);
		startTime = GregorianCalendar.getInstance().getTimeInMillis();
	    }

	    return persistentDataCollection;
	} catch (Exception ex) {
	    cat.error(
		    "getMinePplPersistentData() errore nel caricamento delle pratiche ",
		    ex);
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
     * @param p_obj_criteria
     * @param sent
     * @return
     * @throws peopleException
     */
    public Collection getDelegated(PeopleContext context,
	    Criteria p_obj_criteria, Boolean sent) throws peopleException {
	Collection processes = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    PplPersistentData.class, PersistenceManager.Mode.READ);

	    Criteria crtr2 = new Criteria();
	    if (p_obj_criteria != null) {
		crtr2.addAndCriteria(p_obj_criteria);

	    }

	    if (sent != null) {
		Criteria crtrSent = new Criteria();
		crtrSent.addEqualTo("sent", sent);
		crtr2.addAndCriteria(crtrSent);
	    }

	    // delegati
	    Criteria crtrDelegate = new Criteria();
	    crtrDelegate.addEqualTo("delegate.delegateID", context.getUser()
		    .getUserID());
	    crtr2.addAndCriteria(crtrDelegate);

	    QueryByCriteria qryToExecute2 = QueryFactory.newQuery(
		    PplPersistentData.class, crtr2);
	    qryToExecute2.addOrderByAscending("userID");
	    qryToExecute2.addOrderByAscending("oid");
	    processes = persMgr.get(qryToExecute2);

	    /**
	     * Simulazione del distinct non fattibile via SQL a causa del campo
	     * value di submitted_process uso LinkedHashMap per mantenere
	     * l'ordinamento
	     */
	    if (processes != null && !processes.isEmpty()) {
		LinkedHashMap map = new LinkedHashMap();
		Iterator iter = processes.iterator();
		while (iter.hasNext()) {
		    PplPersistentData item = (PplPersistentData) iter.next();
		    map.put(item.getOid(), item);
		}
		processes = map.values();
	    }

	    return ProcessUtils.processPersistentData2pplProcess(context,
		    processes);
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    // Recupera tutti i processi del comune
    public Collection getAll(PeopleContext context, Criteria p_obj_criteria)
	    throws peopleException {
	return getAll(context, p_obj_criteria, new Boolean(false));
    }

    // Recupera tutti i processi del comune
    public Collection getAll(PeopleContext context, Criteria p_obj_criteria,
	    Boolean sent) throws peopleException {
	Collection processes = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    PplPersistentData.class, PersistenceManager.Mode.READ);

	    Criteria crtr = new Criteria();
	    Criteria crtrSent = new Criteria();
	    if (sent != null)
		crtrSent.addEqualTo("sent", sent);
	    crtr.addAndCriteria(crtrSent);

	    Query qryToExecute = QueryFactory.newQuery(PplPersistentData.class,
		    crtr);
	    processes = persMgr.get(qryToExecute);

	    return ProcessUtils.processPersistentData2pplProcess(context,
		    processes);

	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

}

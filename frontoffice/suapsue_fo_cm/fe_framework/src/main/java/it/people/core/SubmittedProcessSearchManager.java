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

import java.util.Collection;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * 
 * User: sergio Date: Oct 11, 2003 Time: 8:55:10 PM <br>
 * <br>
 * Manager per le ricerche dei processi inviati.
 */
public class SubmittedProcessSearchManager {

    private Category cat = Category
	    .getInstance(SubmittedProcessSearchManager.class.getName());

    private static SubmittedProcessSearchManager ourInstance;

    public synchronized static SubmittedProcessSearchManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new SubmittedProcessSearchManager();
	}
	return ourInstance;
    }

    private SubmittedProcessSearchManager() {
    }

    public Collection get(PeopleContext context, Criteria p_obj_criteria)
	    throws peopleException {
	return get(context, p_obj_criteria, new Boolean(false));
    }

    public Collection get(PeopleContext context, Criteria p_obj_criteria,
	    Boolean completed) throws peopleException {
	Collection processes = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    SubmittedProcess.class, PersistenceManager.Mode.READ);

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("user", context.getUser());
	    if (p_obj_criteria != null)
		crtr.addAndCriteria(p_obj_criteria);

	    Criteria crtrCompleted = new Criteria();
	    if (completed != null)
		crtrCompleted.addEqualTo("completed", completed);
	    crtr.addAndCriteria(crtrCompleted);

	    Criteria crtrCity = new Criteria();
	    crtrCity.addEqualTo("commune", context.getCommune());
	    crtr.addAndCriteria(crtrCity);

	    Query qryToExecute = QueryFactory.newQuery(SubmittedProcess.class,
		    crtr);

	    processes = persMgr.get(qryToExecute);
	    return processes;

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
	    Boolean completed) throws peopleException {
	Collection processes = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    SubmittedProcess.class, PersistenceManager.Mode.READ);

	    if (p_obj_criteria == null)
		p_obj_criteria = new Criteria();

	    Criteria crtrCompleted = new Criteria();
	    if (completed != null) {
		crtrCompleted.addEqualTo("completed", completed);
		p_obj_criteria.addAndCriteria(crtrCompleted);
	    }

	    Query qryToExecute = QueryFactory.newQuery(SubmittedProcess.class,
		    p_obj_criteria);

	    processes = persMgr.get(qryToExecute);
	    return processes;

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

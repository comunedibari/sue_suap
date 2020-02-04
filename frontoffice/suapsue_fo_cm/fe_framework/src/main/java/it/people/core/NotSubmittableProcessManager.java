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
import it.people.process.NotSubmittableProcess;

import java.util.Collection;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * User: sergio Date: Sep 19, 2003 Time: 12:00:14 PM <br>
 * <br>
 * Classe per la gestione dei processi inviati
 */
public class NotSubmittableProcessManager {

    private Category cat = Category
	    .getInstance(NotSubmittableProcessManager.class.getName());

    private static NotSubmittableProcessManager ourInstance;

    /**
     * 
     * @return Istanza NotSubmittableProcessManager
     */
    public synchronized static NotSubmittableProcessManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new NotSubmittableProcessManager();
	}
	return ourInstance;
    }

    private NotSubmittableProcessManager() {
    }

    /**
     * Salva l'oggetto NotSubmittableProcess nella base dati
     * 
     * @param context
     *            PeopleContext
     * @param p_obj_process
     *            Oggetto NotSubmittableProcess da salvare
     * @throws peopleException
     */
    public void set(PeopleContext context, NotSubmittableProcess p_obj_process)
	    throws peopleException {
	if (p_obj_process == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory
		.getInstance()
		.get(NotSubmittableProcess.class, PersistenceManager.Mode.WRITE);
	try {
	    persMgr.definePrimaryKey(p_obj_process);

	    // setto l'user se non e' ancora definito.
	    if (p_obj_process.getUserID() == null)
		p_obj_process.setUserID(context.getUser().getUserID());

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

    public NotSubmittableProcess get(PeopleContext context, String contentId)
	    throws peopleException {
	NotSubmittableProcess process = null;
	PersistenceManager persMgr = null;
	try {
	    persMgr = PersistenceManagerFactory.getInstance().get(
		    NotSubmittableProcess.class, PersistenceManager.Mode.READ);

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("userID", context.getUser().getUserID());

	    Criteria crtrCity = new Criteria();
	    crtrCity.addEqualTo("commune", context.getCommune());
	    crtr.addAndCriteria(crtrCity);

	    Criteria crtrContentId = new Criteria();
	    crtrCity.addEqualTo("contentID", contentId);
	    crtr.addAndCriteria(crtrContentId);

	    Query qryToExecute = QueryFactory.newQuery(
		    NotSubmittableProcess.class, crtr);

	    Collection<?> processes = persMgr.get(qryToExecute);

	    if (processes != null) {
		if (processes.size() > 1) {
		    throw new peopleException(
			    "More than one NotSubmittableProcess found.");
		}
		if (processes.size() == 1) {
		    process = (NotSubmittableProcess) processes.iterator()
			    .next();
		}
	    }

	    return process;

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

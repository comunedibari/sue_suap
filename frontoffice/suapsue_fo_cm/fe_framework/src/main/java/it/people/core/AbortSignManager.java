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

/**
 *
 * User: sergio
 * Date: Sep 16, 2003
 * Time: 4:08:04 PM
 * <br>
 * <br>
 * Classe per la gestione dei processi
 */

import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.dbAccessException;
import it.people.core.persistence.exception.peopleException;
import it.people.process.sign.SignedDataHolder;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;

public class AbortSignManager {

    private Category cat = Category.getInstance(ProcessManager.class.getName());

    private static AbortSignManager ourInstance;

    /**
     * 
     * @return Restituisce l'istanza ProcessManager
     */
    public synchronized static AbortSignManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new AbortSignManager();
	}
	return ourInstance;
    }

    private AbortSignManager() {
    }

    public void abortSign(Long processOid) throws peopleException {
	PersistenceManager pm = PersistenceManagerFactory.getInstance().get(
		SignedDataHolder.class, PersistenceManager.Mode.WRITE);
	try {
	    // Elimina solo il riepilogo finale firmato
	    // In realt� con l'implementazione attuale
	    // il riepilogo finale � creato solo quando non � pi�
	    // possibile eliminarlo, la action � comunque conservata
	    // come punto di ingresso per future espansioni.

	    Criteria crit = new Criteria();
	    crit.addEqualTo("parentOid", processOid);
	    crit.addEqualTo("stepOid", "1.Default");

	    pm.delete(QueryFactory.newQuery(SignedDataHolder.class, crit));
	} catch (Exception e) {
	    throw new dbAccessException();
	} finally {
	    if (pm != null) {
		pm.close();
	    }
	}
    }

}

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

import java.util.Collection;
import java.util.Vector;

import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryByIdentity;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 18, 2003 Time: 5:14:34 PM To
 * change this template use Options | File Templates.
 */
public class ProcessStepSignPersistenceMgr extends PersistenceManager {
    public ProcessStepSignPersistenceMgr() {
	super(PersistenceManager.Mode.READ);
    }

    public ProcessStepSignPersistenceMgr(Mode p_obj_mode) {
	super(PersistenceManager.Mode.READ);
    }

    protected Collection doGet(Class p_obj_esObjectClass) {
	Query query = new QueryByCriteria(p_obj_esObjectClass, null);
	return m_obj_ojbBroker.getCollectionByQuery(query);
    }

    protected Collection doGet(Query p_obj_query) {
	if (p_obj_query instanceof QueryByIdentity) {
	    Vector results = new Vector();
	    Object obj = m_obj_ojbBroker.getObjectByQuery(p_obj_query);
	    if (obj != null) {
		results.add(obj);
	    }
	    return results;
	} else {
	    return m_obj_ojbBroker.getCollectionByQuery(p_obj_query);
	}
    }

    protected void doSet(Object p_obj_esObject) {
    }

    protected void doSet(Collection p_obj_esObjectCollection) {
    }

    protected void doDefinePrimaryKey(Object p_obj_esObject) {
    }

    protected boolean isDefinedPrimaryKey(Object p_obj_object) {
	return true;
    }

    protected void doDelete(Query p_obj_query) {
    }

    protected void doDelete(Collection p_obj_esObjectCollection) {
    }
}

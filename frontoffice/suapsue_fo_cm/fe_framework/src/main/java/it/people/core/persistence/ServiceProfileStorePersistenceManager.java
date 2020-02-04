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
/*
 * Created on 19-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.core.persistence;

import it.people.core.ServiceProfileStore;
import it.people.core.persistence.exception.dbAccessException;
import it.people.core.persistence.exception.peopleException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryByIdentity;

/**
 * @author FabMi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceProfileStorePersistenceManager extends PersistenceManager {
    private Category cat = Category.getInstance(PplUserPersistenceManager.class
	    .getName());

    public ServiceProfileStorePersistenceManager(Mode md) {
	super(md);
    }

    protected Collection doGet(Class p_obj_Class) {
	Query query = new QueryByCriteria(p_obj_Class, null);
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

    protected void doSet(Object p_obj_serviceProfileStore)
	    throws peopleException {
	try {
	    m_obj_ojbBroker.store(p_obj_serviceProfileStore);
	} catch (Exception ex) {
	    throw new dbAccessException(ex.getMessage());
	}
    }

    protected void doSet(Collection p_obj_serviceProfileStoreCollection)
	    throws peopleException {
	for (Iterator iter = p_obj_serviceProfileStoreCollection.iterator(); iter
		.hasNext();) {
	    try {
		doSet(iter.next());
	    } catch (IllegalArgumentException iaEx) {
		cat.error(iaEx);
	    }
	}
    }

    protected void doDefinePrimaryKey(Object p_obj_serviceProfileStore) {
	if (p_obj_serviceProfileStore instanceof ServiceProfileStore) {
	    ((ServiceProfileStore) p_obj_serviceProfileStore)
		    .setProcessName(this.toString());
	}
    }

    protected boolean isDefinedPrimaryKey(Object p_obj_serviceProfileStore) {
	if (p_obj_serviceProfileStore instanceof ServiceProfileStore) {
	    return (((ServiceProfileStore) p_obj_serviceProfileStore)
		    .getProcessName() != null);
	}
	return true;
    }

    protected void doDelete(Query p_obj_query) {
	try {
	    m_obj_ojbBroker.deleteByQuery(p_obj_query);
	} catch (Exception ex) {
	    cat.error(ex);
	}
    }

    protected void doDelete(Collection p_obj_esObjectCollection) {
    }

}

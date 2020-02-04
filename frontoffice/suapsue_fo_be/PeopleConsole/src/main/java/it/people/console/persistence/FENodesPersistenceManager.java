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
package it.people.console.persistence;

import java.util.Collection;
import java.util.Vector;

import org.apache.ojb.broker.Identity;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryByIdentity;

import it.people.console.domain.FENode;
import it.people.console.persistence.exceptions.PersistenceManagerDBAccessException;
import it.people.console.persistence.exceptions.PersistenceManagerException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 17.31.45
 *
 */
public class FENodesPersistenceManager extends AbstractPersistenceManager {

    protected Class<?> typeManaged = FENode.class;
	
	public FENodesPersistenceManager() {
		super(Mode.READ);
	}

	public FENodesPersistenceManager(Mode mode) {
		super(mode);
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_definePrimaryKey(java.lang.Object)
	 */
	@Override
	protected void _definePrimaryKey(Object object) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_delete(java.lang.Object)
	 */
	@Override
	protected void _delete(Object object) {
		getPersistenceBroker().delete(object);
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_delete(org.apache.ojb.broker.query.Query)
	 */
	@Override
	protected void _delete(Query query) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_delete(org.apache.ojb.broker.query.Query, boolean)
	 */
	@Override
	protected void _delete(Query query, boolean autoCommit) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_delete(java.util.Collection)
	 */
	@Override
	protected void _delete(Collection<?> collection) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_get(java.lang.Class)
	 */
	@Override
	protected Collection<?> _get(Class<?> clazz)
			throws PersistenceManagerException {

		Query query = new QueryByCriteria(clazz, null);
		return getPersistenceBroker().getCollectionByQuery(query);
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_get(org.apache.ojb.broker.query.Query)
	 */
	@Override
	protected Collection<?> _get(Query query)
			throws PersistenceManagerException {

		if (query instanceof QueryByIdentity) {
			Vector<Object> results = new Vector<Object>();
			Object obj = getPersistenceBroker().getObjectByQuery(query);
			if (obj != null) {
				results.add(obj);
			}
			return results;
		} else {
			return getPersistenceBroker().getCollectionByQuery(query);
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_isDefinedPrimaryKey(java.lang.Object)
	 */
	@Override
	protected boolean _isDefinedPrimaryKey(Object pObjObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_set(java.lang.Object)
	 */
	@Override
	protected void _set(Object object) throws PersistenceManagerException {

        if (this.typeManaged.isAssignableFrom(object.getClass())) {
            try {
            	getPersistenceBroker().store((FENode) object);
            } catch (Exception ex) {
                logger.error("", ex);
                throw new PersistenceManagerDBAccessException();
            }
        } else {
            throw new IllegalArgumentException("FENode expected!");
        }

	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.AbstractPersistenceManager#_set(java.util.Collection)
	 */
	@Override
	protected void _set(Collection<?> collection)
			throws PersistenceManagerException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceManager#isDefinedPrimaryKey(java.lang.Object)
	 */
	public boolean isDefinedPrimaryKey(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.IPersistenceManager#get(org.apache.ojb.broker.Identity)
	 */
	public FENode get(Object oid) throws PersistenceManagerException {
		Identity identity = getPersistenceBroker().serviceIdentity().buildIdentity(FENode.class, oid);
		return (FENode)getPersistenceBroker().getObjectByIdentity(identity);
	}

}

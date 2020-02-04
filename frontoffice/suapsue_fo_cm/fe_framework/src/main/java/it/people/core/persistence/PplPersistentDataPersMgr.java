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

import it.people.core.persistence.exception.dbAccessException;
import it.people.core.persistence.exception.peopleException;
import it.people.process.data.PplPersistentData;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Category;
import org.apache.ojb.broker.metadata.ClassDescriptor;
import org.apache.ojb.broker.metadata.FieldDescriptor;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryByIdentity;
import org.apache.ojb.broker.util.sequence.SequenceManagerException;

/**
 * <p>
 * Title: Stazione Editoriale Espin
 * </p>
 * <p>
 * Description: Stazione Editoriale Espin
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: espin s.p.a.
 * </p>
 * 
 * @author alberto gasparini
 * @version 1.0
 */

public class PplPersistentDataPersMgr extends PersistenceManager {

    private Category cat = Category.getInstance(PplPersistentDataPersMgr.class
	    .getName());

    protected Class m_typeManaged = PplPersistentData.class;

    public PplPersistentDataPersMgr(Mode p_obj_mode) {
	super(p_obj_mode);
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

    protected void doSet(Object p_obj_process) throws peopleException {
	if (m_typeManaged.isAssignableFrom(p_obj_process.getClass())) {
	    try {
		PplPersistentData ppd = (PplPersistentData) p_obj_process;
		ppd.setLastModifiedTime(new Timestamp((new Date()).getTime()));
		m_obj_ojbBroker.store(ppd);
	    } catch (Exception ex) {
		cat.error(ex);
		throw new dbAccessException();
	    }
	} else {
	    throw new IllegalArgumentException("PplPersistentData expected!");
	}
    }

    protected void doSet(Collection p_obj_esObjectCollection)
	    throws peopleException {
	for (Iterator iter = p_obj_esObjectCollection.iterator(); iter
		.hasNext();) {
	    try {
		doSet(iter.next());
	    } catch (IllegalArgumentException iaEx) {
		cat.error(iaEx);
	    }
	}
    }

    protected void doDelete(Query p_obj_query) {
	// m_obj_ojbBroker.deleteByQuery(p_obj_query);
    }

    protected void doDelete(Collection p_obj_esObjectCollection) {
	for (Iterator iter = p_obj_esObjectCollection.iterator(); iter
		.hasNext();) {
	    PplPersistentData persistenData = (PplPersistentData) iter.next();
	    if (persistenData == null) {
		continue;
	    }
	    m_obj_ojbBroker.delete(persistenData);
	}
	cat.debug("doDelete() - end.");
    }

    protected void finalize() throws Throwable {
	// super.finalize();
    }

    public void doDefinePrimaryKey(Object p_obj_Object) {
	if (m_typeManaged.isAssignableFrom(p_obj_Object.getClass())) {
	    PplPersistentData p_obj_process = (PplPersistentData) p_obj_Object;

	    ClassDescriptor cd = m_obj_ojbBroker
		    .getClassDescriptor(m_typeManaged);
	    if (cd == null) {
		throw new IllegalArgumentException(
			"Unable to find class descriptor for class "
				+ m_typeManaged + "!");
	    }
	    FieldDescriptor fd = cd.getFieldDescriptorByName("oid");
	    if (fd == null) {
		throw new IllegalArgumentException(
			"Unable to find field descriptor for field 'OID'! ");
	    }

	    try {
		// Porting a OJB 1.0.0
		Long newOID = (Long) m_obj_ojbBroker.serviceSequenceManager()
			.getUniqueValue(fd);
		// Long newOID = new
		// Long(m_obj_ojbBroker.serviceSequenceManager().getUniqueLong(fd));
		if (newOID == null) {
		    throw new NullPointerException(
			    "Error retrieving PplProcess OID from Sequence");
		}
		p_obj_process.setOid(newOID);
	    } catch (SequenceManagerException smEx) {
		cat.error(smEx);
		throw new NullPointerException(
			"Error retrieving PplProcess OID from Sequence");
	    }
	} else {
	    throw new IllegalArgumentException("PplPersistentData expected!");
	}
    }

    protected boolean isDefinedPrimaryKey(Object p_obj_object) {
	if (m_typeManaged.isAssignableFrom(p_obj_object.getClass())) {
	    PplPersistentData p_obj_process = (PplPersistentData) p_obj_object;
	    return p_obj_process.getOid() != null;
	} else {
	    throw new IllegalArgumentException("PplPersistentData expected!");
	}
    }

}

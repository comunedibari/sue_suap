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
import it.people.propertymgr.PropertyFormatException;
import it.people.util.PeopleProperties;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PBKey;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Query;

public abstract class PersistenceManager {
    private static Logger logger = Logger.getLogger(PersistenceManager.class);

    public static final class Mode {
	private final int m_i_value;

	private Mode(int p_i_value) {
	    m_i_value = p_i_value;
	}

	public int getValue() {
	    return m_i_value;
	}

	public static final Mode READ = new Mode(1);
	public static final Mode WRITE = new Mode(2);
	public static final Mode READWRITE = new Mode(3);
    }

    protected PersistenceBroker m_obj_ojbBroker = null;
    private Mode m_obj_mode;
    private int MAX_CONNECTION_RETRY = 3;

    PersistenceManager(Mode p_obj_mode) {

	setMode(p_obj_mode);

	initializeBroker();
    }

    private void initializeBroker() {
	String jcdAlias = "DB_ALIAS";
	try {
	    jcdAlias = PeopleProperties.OJB_DB_ALIAS.getValueString();
	} catch (PropertyFormatException pfEx) {
	    logger.error(pfEx);
	}
	int retry = 0;
	do {
	    try {

		if (retry > 0) {
		}

		if (m_obj_ojbBroker != null) {
		    m_obj_ojbBroker.close();
		    m_obj_ojbBroker = null;
		}

		m_obj_ojbBroker = PersistenceBrokerFactory
			.createPersistenceBroker(new PBKey(jcdAlias));

		if (m_obj_ojbBroker == null) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
		    } else {
			return;
		    }
		} else
		    return;
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		} else {
		    logger.error(ex);
		    if (ex instanceof SQLException
			    && ((SQLException) ex).getErrorCode() == 03113) {
		    }
		    if (m_obj_ojbBroker == null)
			return;
		}
	    }
	} while (true);
    }

    public final Collection get(Class p_obj_sampleClass) throws peopleException {

	canRead();

	int retry = 0;
	do {
	    try {
		if (p_obj_sampleClass == null) {
		    return new ArrayList();
		}

		if (!isConnected()) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    } else {
			throw new dbAccessException();
		    }
		}

		return doGet(p_obj_sampleClass);
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (!isConnected())
			initializeBroker();
		} else {
		    logger.error(ex);
		    throw new dbAccessException();
		}
	    }
	} while (true);
    }

    public final Collection get(Query p_obj_query) throws peopleException {
	canRead();

	int retry = 0;
	do {
	    try {
		if (!isConnected()) {
		    // throw new peopleException();
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    } else {
			throw new dbAccessException();
		    }

		}
		if (p_obj_query == null) {
		    throw new NullPointerException(
			    "Unable to execute a null Query!");
		}

		return doGet(p_obj_query);
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (!isConnected())
			initializeBroker();
		} else {
		    logger.error(ex);
		    throw new dbAccessException();
		}
	    }
	} while (true);
    }

    public synchronized final void set(Object p_obj_esObject)
	    throws peopleException {
	canWrite();
	set(p_obj_esObject, true);
    }

    public synchronized final void set(Object p_obj_esObject,
	    boolean p_b_autoCommit) throws peopleException {
	canWrite();

	int retry = 0;
	do {
	    try {
		if (p_obj_esObject == null) {
		    return;
		}

		if (!isConnected()) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    } else {
			throw new dbAccessException();
		    }
		}

		if (isConnected()) {
		    if (p_b_autoCommit) {
			beginTransaction();
		    }
		    doSet(p_obj_esObject);
		    if (p_b_autoCommit) {
			commitTransaction();
		    }
		    return;
		}
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (isConnected())
			abortTransaction();
		    else
			initializeBroker();
		} else {
		    logger.error(ex);
		    throw new peopleException();
		}
	    }
	} while (true);
    }

    public synchronized final void set(Collection p_obj_esObjectCollection)
	    throws peopleException {
	canWrite();
	set(p_obj_esObjectCollection, true);
    }

    public synchronized final void set(Collection p_obj_esObjectCollection,
	    boolean p_b_autoCommit) throws peopleException {
	canWrite();

	int retry = 0;
	do {
	    try {
		if (p_obj_esObjectCollection == null) {
		    return;
		}

		if (!isConnected()) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    } else {
			throw new dbAccessException();
		    }
		}

		if (isConnected()) {
		    if (p_b_autoCommit) {
			beginTransaction();
		    }
		    doSet(p_obj_esObjectCollection);
		    if (p_b_autoCommit) {
			commitTransaction();
		    }
		    return;
		}
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (isConnected())
			abortTransaction();
		    else
			initializeBroker();
		} else {
		    logger.error(ex);
		    throw new peopleException();
		}
	    }
	} while (true);
    }

    public synchronized final void delete(Query p_obj_query) {
	canWrite();
	delete(p_obj_query, true);
    }

    public synchronized final void delete(Query p_obj_query,
	    boolean p_b_autoCommit) {
	canWrite();

	int retry = 0;
	do {
	    try {
		if (p_obj_query == null) {
		    throw new NullPointerException(
			    "Unable to execute a null Query!");
		}

		if (!isConnected()) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    }
		}

		if (isConnected()) {
		    if (p_b_autoCommit) {
			beginTransaction();
		    }
		    doDelete(p_obj_query);
		    if (p_b_autoCommit) {
			commitTransaction();
		    }
		    return;
		}
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (isConnected())
			abortTransaction();
		    else
			initializeBroker();
		} else {
		    logger.error(ex);
		    break;
		}
	    }
	} while (true);
    }

    public synchronized final void delete(Collection p_obj_esObjectCollection) {
	canWrite();
	delete(p_obj_esObjectCollection, true);
    }

    public synchronized final void delete(Collection p_obj_esObjectCollection,
	    boolean p_b_autoCommit) {
	canWrite();

	int retry = 0;
	do {
	    try {
		if (p_obj_esObjectCollection == null) {
		    return;
		}

		if (!isConnected()) {
		    if (retry++ < MAX_CONNECTION_RETRY) {
			initializeBroker();
		    }
		}

		if (isConnected()) {
		    if (p_b_autoCommit) {
			beginTransaction();
		    }
		    doDelete(p_obj_esObjectCollection);
		    if (p_b_autoCommit) {
			commitTransaction();
		    }
		    return;
		}
	    } catch (Exception ex) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    logger.warn(ex);
		    if (isConnected())
			abortTransaction();
		    else
			initializeBroker();
		} else {
		    logger.error(ex);
		    break;
		}
	    }
	} while (true);
    }

    public synchronized void beginTransaction() {
	canWrite();

	try {
	    if (m_obj_ojbBroker.isInTransaction()) {
		return;
	    }
	    m_obj_ojbBroker.beginTransaction();
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }

    public synchronized void commitTransaction() {
	canWrite();

	try {
	    if (!m_obj_ojbBroker.isInTransaction()) {
		return;
	    }

	    m_obj_ojbBroker.commitTransaction();
	} catch (Exception ex) {
	    logger.error(ex);
	    abortTransaction();
	}
    }

    public synchronized void abortTransaction() {
	canWrite();

	try {
	    if (!m_obj_ojbBroker.isInTransaction()) {
		return;
	    }

	    m_obj_ojbBroker.abortTransaction();
	} catch (Exception ex) {
	    logger.error(ex);
	}
    }

    private boolean isConnected() {
	try {
	    if (m_obj_ojbBroker == null
		    || !m_obj_ojbBroker.serviceConnectionManager().isAlive(
			    m_obj_ojbBroker.serviceConnectionManager()
				    .getConnection()))
		return false;
	    else
		return true;
	} catch (Exception ex) {
	    logger.error(ex);
	    return false;
	}
    }

    public final Mode getMode() {
	return m_obj_mode;
    }

    public void setMode(Mode p_obj_mode) {
	if (p_obj_mode == null) {
	    throw new NullPointerException(
		    "Unspecified PersistenceManager Mode!");
	}
	m_obj_mode = p_obj_mode;
    }

    private void canWrite() {
	if (m_obj_mode == Mode.READ) {
	    throw new IllegalStateException("PersistenceManager is READ only!");
	}
    }

    private void canRead() {
	if (m_obj_mode == Mode.WRITE) {
	    throw new IllegalStateException("PersistenceManager is WRITE only!");
	}
    }

    public void close() {
	try {
	    m_obj_ojbBroker.close();
	    m_obj_ojbBroker = null;
	} catch (Exception ex) {
	    logger.warn(ex);
	    m_obj_ojbBroker = null;
	}
    }

    public synchronized void definePrimaryKey(Object p_obj_Object)
	    throws Exception {

	if (!isConnected()) {
	    int retry = 0;
	    boolean tryConnection = true;
	    while (tryConnection) {
		if (retry++ < MAX_CONNECTION_RETRY) {
		    if (!isConnected()) {
			initializeBroker();
		    } else {
			tryConnection = false;
		    }
		} else {
		    tryConnection = false;
		}
	    }
	    if (!isConnected()) {
		throw new Exception(
			"Database access exception, unable to connect broker.");
	    }
	}

	if (!isDefinedPrimaryKey(p_obj_Object)) {
	    doDefinePrimaryKey(p_obj_Object);
	}
    }

    protected void finalize() throws Throwable {
	close();
	super.finalize();
    }

    protected abstract Collection doGet(Class p_obj_esObjectClass)
	    throws peopleException;

    protected abstract Collection doGet(Query p_obj_query)
	    throws peopleException;

    protected abstract void doSet(Object p_obj_esObject) throws peopleException;

    protected abstract void doSet(Collection p_obj_esObjectCollection)
	    throws peopleException;

    protected abstract void doDefinePrimaryKey(Object p_obj_esObject);

    protected abstract boolean isDefinedPrimaryKey(Object p_obj_object);

    protected abstract void doDelete(Query p_obj_query);

    protected abstract void doDelete(Collection p_obj_esObjectCollection);

}

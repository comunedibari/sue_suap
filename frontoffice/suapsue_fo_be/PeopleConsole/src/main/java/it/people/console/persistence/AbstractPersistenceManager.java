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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.ojb.broker.PBKey;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Query;

import it.people.console.persistence.exceptions.PersistenceManagerDBAccessException;
import it.people.console.persistence.exceptions.PersistenceManagerException;
import it.people.console.system.AbstractLogger;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 01/dic/2010 22.08.54
 *
 */
public abstract class AbstractPersistenceManager extends AbstractLogger implements IPersistenceManager {

    private static final String JCD_ALIAS = "PEOPLE_DB";
    
    private static final int DEFAULT_MAX_CONNECTION_RETRY_NUMBER = 3;
	
    protected PersistenceBroker persistenceBroker = null;

    private Mode mode;
    
    private int maxConnectionRetry = DEFAULT_MAX_CONNECTION_RETRY_NUMBER;

    public AbstractPersistenceManager(Mode mode) {
        
        setMode(mode);
        
        initializeBroker();
        
    }
    
    public final Mode getMode() {
        return mode;
    }
    
    public final void setMode(Mode mode) {
        if (mode == null) {
            throw new NullPointerException(
                    "Unspecified PersistenceManager Mode!");
        }
        this.mode = mode;
    }
    
    public final int getMaxConnectionRetry() {
    	return this.maxConnectionRetry;
    }
    
    public final void setMaxConnectionRetry(int connectionRetryNumber) {
    	
    	if (connectionRetryNumber <= 0) {
    		connectionRetryNumber = DEFAULT_MAX_CONNECTION_RETRY_NUMBER;
    	}
    	
    	this.maxConnectionRetry = connectionRetryNumber;
    	
    }
    
    public final Collection<?> get(Class<?> persistableClass) throws PersistenceManagerException {
        
        canRead();
        
        int retry = 0;
        do {
            try {
                if (persistableClass == null) {
                    return new ArrayList<Object>();
                }
                
                if (retry > 0) {
                }
                
                return _get(persistableClass);
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (!isConnected()) {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);
                    throw new PersistenceManagerDBAccessException();
                }
            }
        } while (true);
    	
    }
    
    public final Collection<?> get(Query query) throws PersistenceManagerException {

        canRead();
        
        int retry = 0;
        do {
            try {
                if (query == null) {
                    throw new NullPointerException("Unable to execute a null Query!");
                }
                
                return _get(query);
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (!isConnected()) {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);
                    throw new PersistenceManagerDBAccessException();
                }
            }
        } while (true);
    	
    }

    public synchronized final void set(Object object) throws PersistenceManagerException {

        canWrite();
        set(object, true);
    	
    }
    
    public synchronized final void set(Object object, boolean autoCommit) throws PersistenceManagerException {

        canWrite();
        
        int retry = 0;
        do {
            try {
                if (object == null) {
                    return;
                }
                
                if (retry > 0) {
                }
                if (autoCommit) {
                    beginTransaction();
                }
                _set(object);
                if (autoCommit) {
                    commitTransaction();
                }
                return;
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (isConnected()) {
                        abortTransaction(); 
                    } else {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);                    
                	throw new PersistenceManagerException();
		        }
            }
        } while (true);
    	
    }
    
    public synchronized final void set(Collection<?> collection) throws PersistenceManagerException {

        canWrite();
        set(collection, true);
    	
    }
    
    public synchronized final void set(Collection<?> collection, boolean autoCommit) throws PersistenceManagerException {

        canWrite();
        
        int retry = 0;
        do {
            try {
                if (collection == null) {
                    return;
                }
                
                if (autoCommit) {
                    beginTransaction();
                }
                _set(collection);
                if (autoCommit) {
                    commitTransaction();
                }
                return;
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (isConnected()) {
                        abortTransaction(); 
                    } else {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);                    
                	throw new PersistenceManagerException();
			    }
            }
        } while (true);
    	
    }

    public synchronized final void delete(Object object) {

        canWrite();
        delete(object, true);
    	
    }

    public synchronized final void delete(Object object, boolean autoCommit) {

        canWrite();

        int retry = 0;
        do {
            try {
                if (object == null) {
                    throw new NullPointerException("Unable to delete a null Object!");
                }
                
                if (autoCommit) {
                    beginTransaction();
                }
                _delete(object);
                if (autoCommit) {
                    commitTransaction();
                }
                return;
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (isConnected()) {
                        abortTransaction(); 
                    } else {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);
                	break;
                }                
            }
        } while (true);        
    	
    }
    
    public synchronized final void delete(Query query) {

        canWrite();
        delete(query, true);
    	
    }
    
    public synchronized final void delete(Query query, boolean autoCommit) {

        canWrite();
        
        int retry = 0;
        do {
            try {
                if (query == null) {
                    throw new NullPointerException("Unable to execute a null Query!");
                }
                
                if (autoCommit) {
                    beginTransaction();
                }
                _delete(query);
                if (autoCommit) {
                    commitTransaction();
                }
                return;
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (isConnected()) {
                        abortTransaction(); 
                    } else {
                        initializeBroker();
                    }
                } else {
                    logger.error("", ex);
                	break;
                }                
            }
        } while (true);
    	
    }

    public synchronized final void delete(Collection<?> collection) {

        canWrite();        
        delete(collection, true);
    	
    }
    
    public synchronized final void delete(Collection<?> collection, boolean autoCommit) {

        canWrite();
        
        int retry = 0;
        do {
            try {
                if (collection == null) {
                    return;
                }
                
                if (autoCommit) {
                    beginTransaction();
                }
                _delete(collection);
                if (autoCommit) {
                    commitTransaction();
                }
                return;
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                    if (isConnected()) {
                        abortTransaction(); 
                    } else {
                        initializeBroker();
                    }
	            } else {
	                logger.error("", ex);
	            	break;
	            }                                
            }
        } while (true);
    	
    }
    
    public synchronized final void beginTransaction() {

        canWrite();
        
        try {
            if (persistenceBroker.isInTransaction()) {
                return;
            }
            persistenceBroker.beginTransaction();
        } catch (Exception ex) {
            logger.error("", ex);
        }
    	
    }
    
    public synchronized final void commitTransaction() {

        canWrite();
        
        try {
            if (!persistenceBroker.isInTransaction()) {
                return;
            }
            
            persistenceBroker.commitTransaction();
        } catch (Exception ex) {
            logger.error("", ex);
            abortTransaction();
        }
    	
    }
    
    public synchronized final void abortTransaction() {

        canWrite();
        
        try {
            if (!persistenceBroker.isInTransaction()) {
                return;
            }
            
            persistenceBroker.abortTransaction();
        } catch (Exception ex) {
            logger.error("", ex);
        }
    	
    }
    
    public final void close() {

        try {
        	if (persistenceBroker != null) {
        		persistenceBroker.close();
        	}
        	persistenceBroker = null;
        } catch (Exception ex) {
            logger.warn("", ex);
            persistenceBroker = null;
        }
    	
    }
    
    public synchronized final void definePrimaryKey(Object object) throws Exception {

        if (!isDefinedPrimaryKey(object)) {
            _definePrimaryKey(object);
        }
    	
    }
    
    
    
    
    protected abstract Collection<?> _get(Class<?> clazz) throws PersistenceManagerException;
    
    protected abstract Collection<?> _get(Query query) throws PersistenceManagerException;

    protected abstract void _set(Object object) throws PersistenceManagerException;
    
    protected abstract void _set(Collection<?> collection) throws PersistenceManagerException;

    protected abstract void _delete(Object object);
    
    protected abstract void _delete(Query query);
    
    protected abstract void _delete(Query query, boolean autoCommit);

    protected abstract void _delete(Collection<?> collection);
    
    protected abstract void _definePrimaryKey(Object object) throws Exception;

    protected abstract boolean _isDefinedPrimaryKey(Object p_obj_object);
    
    
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }    
    
    protected void initializeBroker() {
        
        int retry = 0;
        
        do {
            try {
                
                if (retry > 0) {
                }
                
                if (persistenceBroker != null) {
                	persistenceBroker.close();
                	persistenceBroker = null;
                }
                
                persistenceBroker = PersistenceBrokerFactory.createPersistenceBroker(new PBKey(JCD_ALIAS));
                
                if (persistenceBroker == null) {
                    if (retry++ < this.getMaxConnectionRetry()) {
                    	
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } catch (Exception ex) {
                if (retry++ < this.getMaxConnectionRetry()) {
                    logger.warn("", ex);
                } else {
                    logger.error("", ex);
                    if (ex instanceof SQLException && ((SQLException) ex).getErrorCode() == 03113) {
                    	
                    }
                    if (persistenceBroker == null) {
                        return;
                    }
                }
            }
        } while (true);
    	
    }
    
    private void canWrite() {
        if (mode == Mode.READ) {
            throw new IllegalStateException(
                    "PersistenceManager is READ only!");
        }
    }
    
    private void canRead() {
        if (mode == Mode.WRITE) {
            throw new IllegalStateException(
                    "PersistenceManager is WRITE only!");
        }
    }

    private boolean isConnected() {
        try {
            if (persistenceBroker == null || !persistenceBroker.serviceConnectionManager().isAlive(
            		persistenceBroker.serviceConnectionManager().getConnection())) {
            	return false;
            } else {
            	return true;
            }
        } catch (Exception ex) {
            logger.error("", ex);
            return false;
        }
    }
    
    public static final class Mode {

        private final int mode;
        
        private Mode(int value) {
            mode = value;
        }
        
        public int getValue() {
            return mode;
        }
        
        public static final Mode READ = new Mode(1);
        public static final Mode WRITE = new Mode(2);
        public static final Mode READWRITE = new Mode(3);
    	
    }

	protected final PersistenceBroker getPersistenceBroker() {
		if (persistenceBroker == null) {
			initializeBroker();
		}
		return persistenceBroker;
	}

	protected final void setPersistenceBroker(PersistenceBroker persistenceBroker) {
		this.persistenceBroker = persistenceBroker;
	}

}

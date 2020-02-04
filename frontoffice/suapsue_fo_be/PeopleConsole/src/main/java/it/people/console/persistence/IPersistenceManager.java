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

import org.apache.ojb.broker.query.Query;

import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.persistence.exceptions.PersistenceManagerException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 09.01.35
 *
 */
public interface IPersistenceManager {

    public Collection<?> get(Class<?> persistableClass) throws PersistenceManagerException;
    
    public Collection<?> get(Query query) throws PersistenceManagerException;
    
    public Object get(Object oid) throws PersistenceManagerException;
    
    public void set(Object object) throws PersistenceManagerException;
    
    public void set(Object object, boolean autoCommit) throws PersistenceManagerException;
    
    public void set(Collection<?> collection) throws PersistenceManagerException;
    
    public void set(Collection<?> collection, boolean autoCommit) throws PersistenceManagerException;

    public void delete(Object object);
    
    public void delete(Query query);
    
    public void delete(Query query, boolean autoCommit);

    public void delete(Collection<?> collection);
    
    public void delete(Collection<?> collection, boolean autoCommit);
    
    public void beginTransaction();
    
    public void commitTransaction();
    
    public void abortTransaction();
    
    public Mode getMode();
    
    public void setMode(Mode mode);
    
    public void close();
    
    public void definePrimaryKey(Object object) throws Exception;

    public boolean isDefinedPrimaryKey(Object object);
    
}

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
package it.people.core;

import it.people.core.exception.InvalidArgumentException;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Category;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.xmlbeans.XmlObject;

/**
 * @author Michele Fabbri
 * 
 *         Classe per la gestione dei ServiceProfileStore
 */
public class ServiceProfileStoreManager {
    // Nome della classe root del ServiceProfile
    private static final String DOCUMENT_CLASSNAME = "it.people.sirac.serviceprofile.xml.PeopleServiceProfileDocument";
    private Category cat = Category.getInstance(SubmittedProcessManager.class
	    .getName());
    private static ServiceProfileStoreManager ourInstance;

    /**
     * 
     * @return Istanza ServiceProfileStoreManager
     */
    public synchronized static ServiceProfileStoreManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ServiceProfileStoreManager();
	}
	return ourInstance;
    }

    private ServiceProfileStoreManager() {
    }

    /**
     * Consente di creare un oggetto di tipo ServiceProfileStore, l'oggetto
     * rappresenta il service profile salvato su DB, in particolare il metodo
     * effettua anche la validazione rispetto all'xsd del service profile.
     * 
     * @param processName
     *            nome del servizio
     * @param profileSerialization
     *            xml del service profile
     * @return Restituisce l'oggetto ServiceProfileStore
     */
    public ServiceProfileStore create(String processName,
	    String profileSerialization) {
	try {
	    if (checkEmptyValue(processName))
		throw new InvalidArgumentException("processName non valido.");

	    if (checkEmptyValue(profileSerialization))
		throw new InvalidArgumentException(
			"profileSerialization non valido.");

	    validateXML(profileSerialization);

	    ServiceProfileStore profileStore = new ServiceProfileStore();
	    profileStore.processName = processName;
	    profileStore.profile = profileSerialization;
	    return profileStore;
	} catch (peopleException ex) {
	    cat.error(ex);
	    return null;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Consente di creare un oggetto di tipo ServiceProfileStore, l'oggetto
     * rappresenta il service profile salvato su DB, in particolare il metodo
     * effettua anche la validazione rispetto all'xsd del service profile.
     * 
     * @param processName
     *            nome del servizio
     * @param bookmarkId
     *            id del bookmark
     * @param profileSerialization
     *            xml del service profile
     * @return Restituisce l'oggetto ServiceProfileStore
     */
    public ServiceProfileStore create(String processName, String bookmarkId,
	    String profileSerialization) {
	try {
	    if (checkEmptyValue(bookmarkId))
		throw new InvalidArgumentException("bookmarkId non valido.");

	    return create(getCompositeId(processName, bookmarkId),
		    profileSerialization);
	} catch (peopleException ex) {
	    cat.error(ex);
	    return null;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Salva l'oggetto ServiceProfileStore nella base dati
     * 
     * @param p_obj_process
     *            Oggetto ServiceProfileStore da salvare
     * @throws peopleException
     */
    public void set(ServiceProfileStore p_obj_process) throws peopleException {
	if (p_obj_process == null)
	    return;

	PersistenceManager persMgr = null;
	try {
	    // Il controllo va fatto sia nella create che nel salvataggio,
	    // � sempre possibile istanziare direttamente la classe
	    // ServiceProfileStore
	    // con la new ed evitare i controlli, il metodo create ha utilit�
	    // proprio perch� effettua i controlli.

	    if (checkEmptyValue(p_obj_process.processName))
		throw new InvalidArgumentException("processName non valido.");

	    if (checkEmptyValue(p_obj_process.profile))
		throw new InvalidArgumentException("processName non valido.");

	    validateXML(p_obj_process.getProfile());

	    persMgr = PersistenceManagerFactory.getInstance().get(
		    ServiceProfileStore.class, PersistenceManager.Mode.WRITE);
	    persMgr.definePrimaryKey(p_obj_process);
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

    protected boolean checkEmptyValue(String value) {
	return (value == null || "".equals(value));
    }

    protected void validateXML(String value) throws InvalidArgumentException {
	// Per evitare dipendenze circolari con il sirac ho evitato di usare
	// direttamente la libreria xmlbeans_serviceprofile.
	// Viene caricata dinamicamente la classe root del service profile.

	try {
	    // ricava il document root tipizzato per l'xml passato
	    Class documentClassFactory = Class.forName(DOCUMENT_CLASSNAME
		    + "$Factory");
	    Method parseMethod = documentClassFactory.getMethod("parse",
		    new Class[] { String.class });
	    // il metodo � statico e quindi � sufficiente passare null come
	    // istanza
	    XmlObject xmlDocument = (XmlObject) parseMethod.invoke(null,
		    new Object[] { value });

	    // invoca la validazione
	    if (!xmlDocument.validate())
		throw new Exception("Xml non valido.");

	} catch (Exception ex) {
	    throw new InvalidArgumentException(ex.getMessage());
	}
    }

    /**
     * Ritorna il ServiceProfileStore identificato dal nome del servizio
     * 
     * @param processName
     *            nome del servizio
     * @return profilo collegato al bookmark
     * @throws peopleException
     */
    public ServiceProfileStore get(String processName) throws peopleException {
	if (processName == null)
	    return null;

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(ServiceProfileStore.class, PersistenceManager.Mode.READ);

	try {
	    Criteria criteria = new Criteria();
	    criteria.addEqualTo("processName", processName);

	    Collection serviceProfiles = persMgr.get(QueryFactory.newQuery(
		    ServiceProfileStore.class, criteria));
	    if (serviceProfiles != null && !serviceProfiles.isEmpty()) {
		Iterator iter = serviceProfiles.iterator();
		return (ServiceProfileStore) iter.next();
	    }

	    return null;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    /**
     * Ritorna il ServiceProfileStore identificato dalla coppia nome servizio e
     * bookmark
     * 
     * @param processName
     *            nome del servizio
     * @param bookmarkId
     *            identificativo del bookmark
     * @return profilo collegato al bookmark
     * @throws peopleException
     */
    public ServiceProfileStore get(String processName, String bookmarkId)
	    throws peopleException {
	if (checkEmptyValue(bookmarkId))
	    return null;

	return get(getCompositeId(processName, bookmarkId));
    }

    protected String getCompositeId(String processName, String bookmarkId) {
	return processName + "." + bookmarkId;
    }

    public void delete(String processName, String bookmarkId) {
	if (checkEmptyValue(bookmarkId))
	    return;

	delete(getCompositeId(processName, bookmarkId));
    }

    public void delete(String processName) {
	if (checkEmptyValue(processName))
	    return;

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(ServiceProfileStore.class, PersistenceManager.Mode.WRITE);

	try {
	    // Creazione query delete
	    ServiceProfileStore profile = new ServiceProfileStore();
	    profile.setProcessName(processName);
	    Query query = new QueryByCriteria(profile);

	    // delete
	    persMgr.delete(query);
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }
}

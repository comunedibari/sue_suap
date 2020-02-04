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

import it.people.content.Category;
import it.people.content.CategoryImpl;
import it.people.content.ContentImpl;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.exception.peopleException;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * 
 * User: Luigi Corollo Date: 20-gen-2004 Time: 10.51.49 <br>
 * <br>
 * Manager per i procedimenti, 'Content'.
 */
public class ContentManager {

    private org.apache.log4j.Category cat = org.apache.log4j.Category
	    .getInstance(CategoryManager.class.getName());
    private static ContentManager ourInstance;
    private HashMap contentMap = new HashMap();

    /**
     * 
     * @return Istanza ContentManager
     */
    public synchronized static ContentManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new ContentManager();
	}
	return ourInstance;
    }

    private ContentManager() {
    }

    /**
     * Recupera il content dato il nome del processo
     * 
     * @param contentKey
     *            Id del content da recuperare
     * @return Restituisce la Content cercata
     * @throws peopleException
     */
    public ContentImpl getForProcessName(String processName)
	    throws peopleException {
	if (processName == null || "".equals(processName)) {
	    return null;
	}

	if (contentMap.containsKey(processName)) {
	    return (ContentImpl) contentMap.get(processName);
	} else {
	    // Michele Fabbri Cedaf: Ho riscritto la funzione per leggere i dati
	    // dalla
	    // tabella fedb.service e non pi� dalla people.contentcategory che
	    // non � pi�
	    // utilizzata

	    Statement statement = null;
	    ResultSet res = null;
	    Connection connection = null;

	    // per compatibilit� con l'esistente se non trovo il servizio
	    // ritorna comunque un contentimpl vuoto.
	    ContentImpl content = null;

	    try {
		DBConnector dbC = DBConnector.getInstance();
		connection = dbC.connect(DBConnector.FEDB);
		statement = connection.createStatement();
		String sql = "SELECT * FROM service s WHERE package = '"
			+ processName + "'";
		res = statement.executeQuery(sql);

		// un servizio pu� essere presente una sola volta
		if (res.next()) {
		    try {
			content = new ContentImpl();
			Class processClass = Class.forName(res
				.getString("process"));
			String name = res.getString("nome");

			content.setProcessName(processName);
			content.setName(name);
			content.setProcessClass(processClass);
		    } catch (Exception ex) {
			content = null;
			cat.warn("Problema nella inizializzazione del contentuto:\n"
				+ ex);
		    }
		}
	    } catch (SQLException e) {
		cat.error(e);
	    } catch (PeopleDBException e) {
		cat.error(e);
	    } finally {
		try {
		    if (res != null)
			res.close();
		} catch (SQLException e) {
		}

		try {
		    if (statement != null)
			statement.close();
		} catch (SQLException e) {
		}

		try {
		    if (connection != null)
			connection.close();
		} catch (SQLException e) {
		}
	    }

	    if (content != null) {
		// � stato trovato su DB la descrizione del servizio
		contentMap.put(processName, content);
		return content;
	    } else {
		// ritorna un content vuoto
		return new ContentImpl();
	    }
	}

	// Questo � il codice originale che leggeva i dati da
	// people.contentcategory
	/*
	 * PersistenceManager persMgr =
	 * PersistenceManagerFactory.getInstance().get(ContentImpl.class,
	 * PersistenceManager.Mode.READ); Criteria crtr = new Criteria();
	 * crtr.addEqualTo("PROCESS_NAME", processName);
	 * 
	 * Collection content = null; content =
	 * persMgr.get(QueryFactory.newQuery(ContentImpl.class, crtr)); if
	 * (content != null && !content.isEmpty()) { Iterator iter =
	 * content.iterator(); return (ContentImpl)iter.next(); } return null;
	 */
    }

    /**
     * Recupera il content data la chiave.
     * 
     * @param contentKey
     *            Id del content da recuperare
     * @return Restituisce la Content cercata
     * @throws peopleException
     */
    public ContentImpl get(String contentKey) throws peopleException {
	if (contentKey == null || "".equals(contentKey)) {
	    return null;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(ContentImpl.class, PersistenceManager.Mode.READ);

	try {
	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("CONTENT_KEY", contentKey);

	    Collection content = null;
	    content = persMgr.get(QueryFactory
		    .newQuery(ContentImpl.class, crtr));
	    if (content != null && !content.isEmpty()) {
		Iterator iter = content.iterator();
		return (ContentImpl) iter.next();
	    }
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
	return null;
    }

    /**
     * Recupera tutti i content
     * 
     * @param contentKey
     *            Id del content da recuperare
     * @return Restituisce la Content cercata
     * @throws peopleException
     */
    public Collection getAll() throws peopleException {

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(ContentImpl.class, PersistenceManager.Mode.READ);

	try {
	    Criteria crtr = new Criteria();

	    Collection content = null;
	    // N.B. : il terzo parametro inserisce la clausola DISTINCT
	    content = persMgr.get(QueryFactory.newQuery(ContentImpl.class,
		    crtr, true));

	    return content;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

    }

    /**
     * Data la categoria, restituisco gli ID delle categorie figlie dell suo
     * sotto-albero
     * 
     * @param category
     * @return
     * @throws peopleException
     */
    private Collection retriveChilds(PeopleContext context, Category category)
	    throws peopleException {
	// prendo l'id della category con nome codice comune
	// prendo le category figlie e faccio vedere solo i processi
	// associati a questi CAT_ID

	Collection figli = CategoryManager.getInstance().getChilds(context,
		category);
	if (figli != null && figli.isEmpty())
	    return new ArrayList();
	Iterator iter = figli.iterator();
	Collection nipoti = new ArrayList();
	while (iter.hasNext()) {
	    Category catTmp = (Category) iter.next();
	    nipoti.addAll(retriveChilds(context, catTmp));
	}
	figli.addAll(nipoti);
	return figli;
    }

    /**
     * Recupera tutti i content legati al Comune
     * 
     * @param contentKey
     *            Id del content da recuperare
     * @return Restituisce la Content cercata
     * @throws peopleException
     */
    public Collection getContentByCategory(PeopleContext context,
	    Category category) throws peopleException {

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(ContentImpl.class, PersistenceManager.Mode.READ);

	try {
	    Criteria crtr = new Criteria();
	    Collection childs = retriveChilds(context, category);
	    Collection objId = new ArrayList();
	    Iterator iter = childs.iterator();
	    while (iter.hasNext()) {
		objId.add(((Category) iter.next()).getOid());
	    }

	    crtr.addIn("oid", objId);

	    Collection content = null;
	    content = persMgr.get(QueryFactory
		    .newQuery(ContentImpl.class, crtr));

	    return content;
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    /**
     * Cancella Solo il Content.
     * 
     * @param contentToDelete
     *            Contenuto da eliminare
     */
    public void deleteContent(ContentImpl contentToDelete) {
	if (contentToDelete == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(CategoryImpl.class, PersistenceManager.Mode.WRITE);
	try {
	    // Creazione query delete
	    Query query = new QueryByCriteria(contentToDelete);
	    // delete
	    persMgr.delete(query);
	} catch (Exception ex) {
	    cat.error(ex);
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }
}

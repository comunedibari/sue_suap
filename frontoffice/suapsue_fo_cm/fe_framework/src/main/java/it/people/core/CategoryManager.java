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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;

/**
 * 
 * User: sergio Date: Oct 5, 2003 Time: 2:24:01 PM <br>
 * <br>
 * Manager delle categorie.
 */
public class CategoryManager {

    private org.apache.log4j.Category cat = org.apache.log4j.Category
	    .getInstance(CategoryManager.class.getName());

    private static CategoryManager ourInstance;

    /**
     * 
     * @return Istanza CategoryManager
     */
    public synchronized static CategoryManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new CategoryManager();
	}
	return ourInstance;
    }

    private CategoryManager() {
    }

    /**
     * 
     * @param context
     *            PeopleContext
     * @param clazz
     * @return Restiuisce un oggetto CategoryImpl
     */
    public Category create(PeopleContext context, Class clazz) {
	try {
	    Category newObject = new CategoryImpl();
	    return newObject;
	} catch (Exception ex) {
	    cat.error(ex);
	    return null;
	}
    }

    /**
     * Salva sulla base dati la categoria passata
     * 
     * @param context
     *            PeopleContext
     * @param category
     *            Category da salvare
     * @throws peopleException
     */
    public void set(PeopleContext context, Category category)
	    throws peopleException {
	if (category == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(CategoryImpl.class, PersistenceManager.Mode.WRITE);
	try {
	    persMgr.definePrimaryKey(category);
	    persMgr.set(category);
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new peopleException(ex.getMessage());
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}
    }

    /**
     * Recupera i figli della categoria
     * 
     * @param cat
     * @return
     */
    public Collection getChilds(PeopleContext context, Category cat)
	    throws peopleException {
	Category[] childs = cat.getChild();
	if (childs != null) {
	    Collection ris = new ArrayList();
	    for (int i = 0; i < childs.length; i++) {
		ris.add(get(context, childs[i].getOid()));
	    }
	    return ris;
	} else {
	    return null;
	}
    }

    /**
     * Recupera la categoria.
     * 
     * @param context
     *            PeopleContext
     * @param oid
     *            Id della categoria da recuperare
     * @return Restituisce la Category cercata
     * @throws peopleException
     */
    public Category get(PeopleContext context, Long oid) throws peopleException {
	if (oid == null) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(CategoryImpl.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("oid", oid);

	    process = persMgr.get(QueryFactory.newQuery(CategoryImpl.class,
		    crtr));
	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();

		return (Category) iter.next();

	    }
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

	return null;
    }

    /**
     * Recupera la categoria.
     * 
     * @param context
     * @param keys
     * @return
     * @throws peopleException
     */
    private Category get(PeopleContext context, String[] keys)
	    throws peopleException {
	if (keys == null || keys.length == 0) {
	    return null;
	}

	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(CategoryImpl.class, PersistenceManager.Mode.READ);

	try {
	    Collection process = null;

	    Criteria crtr = new Criteria();
	    crtr.addEqualTo("name", keys[0]);

	    process = persMgr.get(QueryFactory.newQuery(CategoryImpl.class,
		    crtr));
	    if (process != null && !process.isEmpty()) {
		Iterator iter = process.iterator();

		Category cat = (Category) iter.next();
		for (int i = 1; i < keys.length; i++) {
		    Category[] children = cat.getChild();
		    boolean categoryFound = false;
		    for (int k = 0; k < children.length; k++)
			if (keys[i].equals(children[k].getName())) {
			    cat = children[k];
			    categoryFound = true;
			    break;
			}
		    if (!categoryFound)
			return null;

		}
		return cat;
	    }
	} finally {
	    if (persMgr != null) {
		persMgr.close();
	    }
	}

	return null;
    }

    /**
     * Recupera la categoria.
     * 
     * @param context
     *            PeopleContext
     * @param path
     *            Percorso della categoria da cercare
     * @return Restituisce la categoria cercata per percorso
     * @throws peopleException
     */
    public Category get(PeopleContext context, String path)
	    throws peopleException {
	if (path == null || path.length() == 0) {
	    return null;
	}

	StringTokenizer st = new StringTokenizer(path, "/");
	String[] keys = new String[st.countTokens()];
	int count = 0;
	while (st.hasMoreElements())
	    keys[count++] = st.nextToken();
	return get(context, keys);
    }

    /**
     * Cancella Solo la Category per id
     * 
     * @param context
     *            PeopleContext
     * @param oid
     *            Id della categoria da eliminare
     */
    public void delete(PeopleContext context, Long oid) {
	if (oid == null) {
	    return;
	}
	PersistenceManager persMgr = PersistenceManagerFactory.getInstance()
		.get(CategoryImpl.class, PersistenceManager.Mode.WRITE);
	try {
	    // Creazione query delete
	    CategoryImpl category = new CategoryImpl();
	    category.setOid(oid);
	    Query query = new QueryByCriteria(category);

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

    /**
     * Cancella Solo la Content per id
     * 
     * @param context
     *            PeopleContext
     * @param contentToDelete
     *            Contenuto da eliminare
     */
    public void deleteContent(PeopleContext context, ContentImpl contentToDelete) {
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

    /**
     * Cancella la Category i figli di tipo 'Modulo' e il collegamento con il
     * padre
     * 
     * @param context
     *            PeopleContext
     * @param categoryToDelete
     *            Categoria da eliminare
     * @param categoryParent
     *            Categoria padre di quella da eliminare
     * @throws peopleException
     */
    public void delete(PeopleContext context, Category categoryToDelete,
	    Category categoryParent) throws peopleException {
	if (categoryToDelete == null || categoryParent == null) {
	    return;
	}

	// Cancello i figli 'Modulo' della CategoryToDelete
	ContentImpl[] processChildren = categoryToDelete.getProcess();
	for (int i = 0; i < processChildren.length; i++) {
	    this.deleteContent(context, processChildren[i]);
	}

	/*
	 * categoryToDelete.setProcess(new ContentImpl[0]);
	 * this.set(context,categoryToDelete);
	 */

	// Cancello il collegamento del categoryParent con la categoryToDelete
	Vector vetChildren = new Vector();
	Category[] children = categoryParent.getChild();
	for (int i = 0; i < children.length; i++) {
	    if (children[i].getOid() != categoryToDelete.getOid())
		vetChildren.add(children[i]);
	}
	children = new Category[vetChildren.size()];
	vetChildren.copyInto(children);
	categoryParent.setChild(children);
	this.set(context, categoryParent);

	// Cancello il CategoryToDelete
	this.delete(context, categoryToDelete.getOid());
    }

    // Cancellazione del albero della category
    /**
     * Elimina la categoria passata e tutte le sue categorie figlie
     * 
     * @param context
     *            PeopleContext
     * @param categoryToDelete
     *            Categoria da eliminare
     * @param categoryParent
     *            Categoria padre di quella da eliminare
     * @throws peopleException
     */
    public void deleteTree(PeopleContext context, Category categoryToDelete,
	    Category categoryParent) throws peopleException {
	// Eseguo la deleteTree
	deleteTreeRecursive(context, categoryToDelete, categoryParent);
    }

    // recursiva
    private void deleteTreeRecursive(PeopleContext context,
	    Category categoryToDelete, Category categoryParent)
	    throws peopleException {
	if (categoryToDelete.getChild() != null) {
	    for (int i = 0; i < categoryToDelete.getChild().length; i++) {
		deleteTreeRecursive(context, categoryToDelete.getChild(i),
			categoryToDelete);
	    }
	}
	delete(context, categoryToDelete, categoryParent);
    }
}

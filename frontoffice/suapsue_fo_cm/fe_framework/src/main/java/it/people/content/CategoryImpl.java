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
package it.people.content;

import it.people.core.CategoryManager;
import it.people.core.PeopleContext;
import it.people.core.persistence.exception.peopleException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * User: sergio Date: Oct 3, 2003 Time: 7:38:54 PM <br>
 * <br>
 * La classe categoria, contenitore di procedimenti.
 */
public class CategoryImpl extends CategoryAbstractImpl {
    private String m_name;
    private String m_label;
    private List m_children = new ArrayList();
    private List m_processes = new ArrayList();

    public String getName() {
	return m_name;
    }

    public void setName(String p_name) {
	m_name = p_name;
    }

    public String getLabel() {
	return m_label;
    }

    public void setLabel(String p_label) {
	m_label = p_label;
    }

    /**
     * Recupera tutte le categorie figlie.
     * 
     * @return
     * @throws peopleException
     */
    public Category[] getChild() throws peopleException {
	Category[] children = new Category[m_children.size()];
	for (int i = 0; i < m_children.size(); i++) {
	    children[i] = getChild(i);
	}
	return children;
    }

    public Category getChild(int index) throws peopleException {
	Category subCat = (Category) m_children.get(index);
	if (subCat instanceof CategoryImpl)
	    return (Category) m_children.get(index);
	Category cat = CategoryManager.getInstance().get(
		PeopleContext.create("Admin"), subCat.getOid());
	setChild(index, cat);
	return cat;
    }

    public Category getChild(Long oid) {
	return null;
    }

    /**
     * Setta le categorie figlie.
     * 
     * @param children
     */
    public void setChild(Category[] children) {
	m_children.clear();
	for (int i = 0; i < children.length; i++)
	    m_children.add(children[i]);
    }

    public void setChild(int index, Category child) {
	m_children.set(index, child);
    }

    public void setChild(Category child) {
    }

    public void addChild(Category child) {
	m_children.add(child);
    }

    /**
     * Recupera i procedimenti 'Content' della categoria.
     * 
     * @return
     */
    public ContentImpl[] getProcess() {
	return (ContentImpl[]) m_processes.toArray(new ContentImpl[m_processes
		.size()]);
    }

    public ContentImpl getProcess(int index) {
	return (ContentImpl) m_processes.get(index);
    }

    // Ritorna il Process che ha chiave 'key'
    /*
     * public ContentImpl getProcess(String key) { ContentImpl[] process =
     * this.getProcess(); for(int i=0;i<process.length;i++){
     * if(process[i].getKey().equals(key)){ return process[i]; } } return null;
     * }
     */

    /**
     * Setta i procedimenti figli della categoria.
     * 
     * @param processClasses
     */
    public void setProcess(ContentImpl[] processClasses) {
	m_processes.clear();
	for (int i = 0; i < processClasses.length; i++)
	    m_processes.add(processClasses[i]);
    }

    public void setProcess(int index, ContentImpl processClass) {
	m_processes.set(index, processClass);
    }

    public void addProcess(ContentImpl process) {
	m_processes.add(process);
    }
}

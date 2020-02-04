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

import it.people.core.persistence.exception.peopleException;

/**
 * 
 * User: sergio Date: Oct 5, 2003 Time: 2:17:07 PM
 * 
 */
public class CategoryAbstractImpl implements Category {
    private Long m_oid;

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public String getName() {
	return null;
    }

    public void setName(String name) {
    }

    public String getLabel() {
	return null;
    }

    public void setLabel(String label) {
    }

    public Category[] getChild() throws peopleException {
	return new Category[0];
    }

    public Category getChild(int index) throws peopleException {
	return null;
    }

    public Category getChild(Long oid) {
	return null;
    }

    public void setChild(Category[] children) {
    }

    public void setChild(int index, Category child) {
    }

    public void setChild(Category child) {
    }

    public void addChild(Category child) {

    }

    public ContentImpl[] getProcess() {
	return new ContentImpl[0];
    }

    public ContentImpl getProcess(int index) {
	return null;
    }

    public void setProcess(ContentImpl[] processes) {
    }

    public void setProcess(int index, ContentImpl process) {
    }

    public void addProcess(ContentImpl process) {

    }
}

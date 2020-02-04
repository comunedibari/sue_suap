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
 * User: sergio Date: Oct 1, 2003 Time: 8:15:56 PM <br>
 * <br>
 * Classe che rappresenta una categoria, contenitore di procedimenti.
 */
public interface Category {
    public Long getOid();

    public void setOid(Long oid);

    public String getName();

    public void setName(String name);

    public String getLabel();

    public void setLabel(String label);

    public Category[] getChild() throws peopleException;

    public Category getChild(int index) throws peopleException;

    public Category getChild(Long oid);

    public void setChild(Category[] children);

    public void setChild(int index, Category child);

    public void setChild(Category child);

    public void addChild(Category child);

    public ContentImpl[] getProcess();

    public ContentImpl getProcess(int index);

    public void setProcess(ContentImpl[] processes);

    public void setProcess(int index, ContentImpl process);

    public void addProcess(ContentImpl process);
}

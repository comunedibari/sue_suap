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
 * Creato il 10-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.filters.AdminAuthentication;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class AdminConfig {
    protected ArrayList elements = new ArrayList();

    public void add(AdminConfigElement element) {
	this.elements.add(element);
    }

    public void remove(AdminConfigElement element) {
	this.elements.remove(element);
    }

    public Iterator iterator() {
	return this.elements.iterator();
    }

    /**
     * Ritorna l'elemento di configurazione dell'utente per il quale � dato lo
     * userName
     * 
     * @param userName
     *            nome dell'utente
     * @return
     */
    public AdminConfigElement getConfigElement(String userName) {
	for (Iterator iter = this.elements.iterator(); iter.hasNext();) {
	    AdminConfigElement element = (AdminConfigElement) iter.next();
	    if (element.getUserName().equals(userName))
		return element;
	}

	return null;
    }

}

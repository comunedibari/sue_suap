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
package it.people;

import it.people.annotations.DeveloperTaskStart;
import it.people.annotations.DeveloperTaskEnd;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 10, 2003 Time: 10:48:01 AM
 * To change this template use Options | File Templates.
 */
public class ActivityState {
    protected int m_code;
    protected String m_label;

    protected ActivityState(int code, String label) {
	m_code = code;
	m_label = label;
    }

    public int getCode() {
	return m_code;
    }

    public void setCode(int p_code) {
	m_code = p_code;
    }

    public String getLabel() {
	return m_label;
    }

    public void setLabel(String p_label) {
	m_label = p_label;
    }

    public boolean equals(ActivityState other) {
	return this.getCode() == other.getCode();
    }

    public String toString() {
	return m_label;
    }

    public static ActivityState ACTIVE = new ActivityState(0, "Active");
    public static ActivityState INACTIVE = new ActivityState(1, "Inactive");
    public static ActivityState COMPLETED = new ActivityState(2, "Completed");
    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "14.05.2011", bugDescription = "Nel caso di workflow con steps differenti come logica, ma con lo stesso nome, "
	    + "i nomi nella barra delle attivit� compaiono duplicati.", description = "Aggiunta la propriet� HIDDEN alle attivit�"
	    + " per nasconderle nella barra delle attivit�.")
    public static ActivityState HIDDEN = new ActivityState(3, "Hidden");

    @DeveloperTaskEnd(name = "Riccardo Foraf�", date = "14.05.2011")
    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "14.05.2011", bugDescription = "Nel caso di workflow con steps differenti come logica, ma con lo stesso nome, "
	    + "i nomi nella barra delle attivit� compaiono duplicati.", description = "Aggiunta la resituzione dello stato HIDDEN")
    public static ActivityState getState(int code) {
	if (code == ACTIVE.getCode())
	    return ACTIVE;
	else if (code == INACTIVE.getCode())
	    return INACTIVE;
	else if (code == COMPLETED.getCode())
	    return COMPLETED;
	else if (code == HIDDEN.getCode())
	    return HIDDEN;
	return null;
    }

}

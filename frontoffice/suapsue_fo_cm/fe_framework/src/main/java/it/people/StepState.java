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

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 10, 2003 Time: 11:24:45 AM
 * To change this template use Options | File Templates.
 */
public class StepState {
    protected int m_code;
    protected String m_label;

    protected StepState(int code, String label) {
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

    public boolean equals(StepState other) {
	return this.getCode() == other.getCode();
    }

    public String toString() {
	return m_label;
    }

    public static StepState ACTIVE = new StepState(0, "Active");
    public static StepState INACTIVE = new StepState(1, "Inactive");
    public static StepState COMPLETED = new StepState(2, "Completed");
    public static StepState HIDDEN = new StepState(3, "Hidden");

    public static StepState getState(int code) {
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

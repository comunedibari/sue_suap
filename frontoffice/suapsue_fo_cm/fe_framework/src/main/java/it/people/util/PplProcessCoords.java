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
package it.people.util;

import it.people.process.view.ConcreteView;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 6-ott-2003 Time: 11.14.45 To
 * change this template use Options | File Templates.
 */
public class PplProcessCoords {
    private int m_activityIndex;
    private int m_stepIndex;

    public PplProcessCoords(ConcreteView p_concreteView) {
	if (p_concreteView != null) {

	    m_activityIndex = p_concreteView.getCurrentActivityIndex();
	    m_stepIndex = p_concreteView.getCurrentActivity()
		    .getCurrentStepIndex();
	} else
	    throw new IllegalArgumentException("Invalid Concrete View");
    }

    public PplProcessCoords(PplProcessCoords oldValue) {
	if (oldValue != null) {

	    m_activityIndex = oldValue.getActivityIndex();
	    m_stepIndex = oldValue.getStepIndex();
	} else
	    throw new IllegalArgumentException(
		    "Invalid Activity & Step reference");
    }

    /*
     * public PplProcessCoords(ConcreteView p_concreteView, int p_actIndex, int
     * p_stepIndex){ if( p_concreteView != null && isActivityIndexOk(p_actIndex)
     * && isStepIndexOk(p_actIndex, p_stepIndex) ){ m_concreteView =
     * p_concreteView; m_activityIndex = p_actIndex; m_stepIndex = p_stepIndex;
     * } }
     */

    public int getActivityIndex() {
	return m_activityIndex;
    }

    public void setActivityIndex(int p_activityIndex) {
	m_activityIndex = p_activityIndex;
    }

    public int getStepIndex() {
	return m_stepIndex;
    }

    public void setStepIndex(int p_stepIndex) {
	m_stepIndex = p_stepIndex;
    }

    public boolean check(ConcreteView p_concreteView) {
	if (p_concreteView != null) {
	    // return (p_activityIndex >= 0 && p_activityIndex <=
	    // m_concreteView.getActivities().length -1);
	    return true;
	} else
	    throw new IllegalArgumentException("Invalid Concrete View");

    }

    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PplProcessCoords))
	    return false;

	final PplProcessCoords pplProcessCoords = (PplProcessCoords) o;

	if (m_activityIndex != pplProcessCoords.m_activityIndex)
	    return false;
	if (m_stepIndex != pplProcessCoords.m_stepIndex)
	    return false;

	return true;
    }

}

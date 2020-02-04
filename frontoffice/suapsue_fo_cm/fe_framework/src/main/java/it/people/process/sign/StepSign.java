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
package it.people.process.sign;

import it.people.Step;
import it.people.StepState;
import it.people.core.PplACE;
import it.people.util.frontend.WorkflowController;

public class StepSign extends Step {

    private StepSignDo m_do;

    private String m_processName;
    private String m_jsp;
    private String m_help;

    private Long m_orderNumber;
    private boolean m_active;
    private Long m_oid;

    /***********************************************/

    public StepSign() {
	super();
    }

    public StepSign(String p_jspPath, String p_helpUrl, StepState state,
	    StepSignDo p_doBefore) {
	super(p_jspPath, p_helpUrl, state);
	m_do = p_doBefore;
    }

    public StepSign(String p_jspPath, String p_helpUrl,
	    WorkflowController controller, StepSignDo p_doBefore) {
	super(p_jspPath, p_helpUrl, controller);
	m_do = p_doBefore;
    }

    public StepSign(String p_jspPath, String p_helpUrl, StepState state,
	    WorkflowController controller, StepSignDo p_doBefore, PplACE[] ACL) {
	super(p_jspPath, p_helpUrl, state, controller, ACL);
	m_do = p_doBefore;
    }

    public StepSignDo getDo() {
	return m_do;
    }

    public void setDo(StepSignDo p_do) {
	m_do = p_do;
    }

    public void setProcessName(String p_processName) {
	m_processName = p_processName;
    }

    public String getProcessName() {
	return m_processName;
    }

    public String getJsp() {
	return m_jsp;
    }

    public void setJsp(String p_jsp) {
	m_jsp = p_jsp;
	super.setJspPath(p_jsp);
    }

    public String getHelp() {
	return m_help;
    }

    public void setHelp(String p_help) {
	m_help = p_help;
	super.setHelpUrl(p_help);
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setOrderNumber(Long p_orderNumber) {
	m_orderNumber = p_orderNumber;
    }

    public Long getOrderNumber() {
	return m_orderNumber;
    }

    public boolean getActive() {
	return m_active;
    }

    public void setActive(boolean p_active) {
	m_active = p_active;
    }

}

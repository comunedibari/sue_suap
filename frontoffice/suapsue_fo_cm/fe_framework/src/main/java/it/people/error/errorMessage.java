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
package it.people.error;

import java.io.Serializable;
import java.util.ArrayList;

public class errorMessage implements Serializable {

    private ArrayList m_queue;

    private String m_errorSender = ""; // pagina che "ordina" l'invio
				       // dell'errore all'utente
    private String m_processId = ""; // id del processo in cui si genera
				     // l'errore
    private String m_processName = ""; // nome del processo padre
    private String m_errorForward = ""; // pagina di errore

    public errorMessage() {
	m_queue = new ArrayList();
    }

    public void addErrorMessage(String message) {
	m_queue.add(message);
    }

    public void cleanQueue() {
	m_queue.clear();
    }

    public ArrayList getQueue() {
	return m_queue;
    }

    public String getErrorSender() {
	return m_errorSender;
    }

    public void setErrorSender(String p_errorSender) {
	m_errorSender = p_errorSender;
    }

    public String getProcessId() {
	return m_processId;
    }

    public void setProcessId(String p_processId) {
	m_processId = p_processId;
    }

    public String getProcessName() {
	return m_processName;
    }

    public void setProcessName(String p_processName) {
	m_processName = p_processName;
    }

    public void setErrorForward(String p_errorJsp) {
	m_errorForward = p_errorJsp;
    }

    public String getErrorForward() {
	return m_errorForward;
    }

}

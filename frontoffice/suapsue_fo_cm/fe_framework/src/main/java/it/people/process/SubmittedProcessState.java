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
package it.people.process;

import java.util.HashMap;

/**
 * 
 * User: sergio Date: Sep 19, 2003 Time: 10:32:07 AM <br>
 * <br>
 * Questa classe rappresenta lo stato di un processo inviato.
 */
public class SubmittedProcessState {
    private static HashMap m_states = new HashMap();

    protected Long m_code;
    protected String m_label;
    protected String m_messageKey;

    protected SubmittedProcessState(Long code, String label) {
	this(code, label, null);
    }

    protected SubmittedProcessState(Long code, String label, String messageKey) {
	m_code = code;
	m_label = label;
	m_messageKey = messageKey;

	SubmittedProcessState.m_states.put(code, this);
    }

    public Long getCode() {
	return m_code;
    }

    public void setCode(Long p_code) {
	m_code = p_code;
    }

    public String getLabel() {
	return m_label;
    }

    public void setLabel(String p_label) {
	m_label = p_label;
    }

    /**
     * @return the messageKey
     */
    public final String getMessageKey() {
	return this.m_messageKey;
    }

    /**
     * @param messageKey
     *            the messageKey to set
     */
    public final void setMessageKey(String messageKey) {
	this.m_messageKey = messageKey;
    }

    public boolean equals(SubmittedProcessState other) {
	return this.getCode() == other.getCode();
    }

    public String toString() {
	return m_label;
    }

    public static SubmittedProcessState get(Long code) {
	return (SubmittedProcessState) m_states.get(code);
    }

    public static SubmittedProcessState INITIALIZING = new SubmittedProcessState(
	    new Long(0), "Initializing", "SubmittedProcessState.Initializing");
    public static SubmittedProcessState SUBMITTED = new SubmittedProcessState(
	    new Long(1), "Submitted", "SubmittedProcessState.Submitted");
    public static SubmittedProcessState RECEIVED = new SubmittedProcessState(
	    new Long(2), "Received", "SubmittedProcessState.Received");
    public static SubmittedProcessState PROTOCOLLED = new SubmittedProcessState(
	    new Long(3), "Protocolled", "SubmittedProcessState.Protocolled");
    public static SubmittedProcessState REJECTED = new SubmittedProcessState(
	    new Long(4), "Rejected", "SubmittedProcessState.Rejected");
    public static SubmittedProcessState COMPLETED = new SubmittedProcessState(
	    new Long(5), "Completed", "SubmittedProcessState.Completed");
    public static SubmittedProcessState SEND_ERROR = new SubmittedProcessState(
	    new Long(6), "Send error", "SubmittedProcessState.SendError");

    // ETNO
    public static SubmittedProcessState SAVED = new SubmittedProcessState(
	    new Long(10), "Saved", "SubmittedProcessState.Saved");
    // pagamento
    public static SubmittedProcessState PAYMENTPENDING = new SubmittedProcessState(
	    new Long(10), "Payment Pending",
	    "SubmittedProcessState.PaymentPending");
    public static SubmittedProcessState PAYMENTDONEOK = new SubmittedProcessState(
	    new Long(10), "Payment Completed",
	    "SubmittedProcessState.PaymentDone");

}

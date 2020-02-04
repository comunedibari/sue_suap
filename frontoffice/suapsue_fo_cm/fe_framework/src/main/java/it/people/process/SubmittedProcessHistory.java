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

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * User: sergio Date: Sep 19, 2003 Time: 10:29:20 AM <br>
 * <br>
 * Questa classe rappresenta la storia dei processi inviati a People
 * 
 */
public class SubmittedProcessHistory {
    private Long m_oid;
    private Timestamp m_transactionTime;
    private SubmittedProcessState m_state;
    private String m_errorInfo;

    public SubmittedProcessHistory() {
	this(SubmittedProcessState.INITIALIZING, new Timestamp(
		new Date().getTime()));
    }

    public SubmittedProcessHistory(SubmittedProcessState sps) {
	this(sps, new Timestamp(new Date().getTime()));
    }

    public SubmittedProcessHistory(SubmittedProcessState sps,
	    Timestamp transTime) {
	this(sps, transTime, null);
    }

    public SubmittedProcessHistory(SubmittedProcessState sps,
	    Timestamp transTime, String errorInfo) {
	m_state = sps;
	m_transactionTime = transTime;
    }

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Timestamp getTransactionTime() {
	return m_transactionTime;
    }

    public void setTransactionTime(Timestamp p_transactionTime) {
	m_transactionTime = p_transactionTime;
    }

    public SubmittedProcessState getState() {
	return m_state;
    }

    public void setState(SubmittedProcessState p_state) {
	m_state = p_state;
    }

    /**
     * @return the errorInfo
     */
    public final String getErrorInfo() {
	return this.m_errorInfo;
    }

    /**
     * @param errorInfo
     *            the errorInfo to set
     */
    public final void setErrorInfo(String errorInfo) {
	this.m_errorInfo = errorInfo;
    }

}

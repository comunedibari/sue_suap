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

import it.people.City;
import it.people.core.PplUser;
import it.people.process.data.PplPersistentData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * User: sergio Date: Sep 19, 2003 Time: 10:25:13 AM <br>
 * <br>
 * Questa classe rappresenta i processi che hanno terminato la fase di
 * compilazione e di firma e che sono stati presi incarico da People. Ognuno di
 * questi processi ha associata la sua storia e le informazioni correlate
 * 
 */
public class SubmittedProcess {
    private Long m_oid;
    private Long m_editableProcessId;
    private PplUser m_user;
    private String m_peopleProtocollId;
    private String m_communeProtocollId;
    private String m_transportTrackingNumber;
    private City m_commune;

    private Timestamp m_submittedTime;

    private ArrayList m_history = new ArrayList();
    private HashMap m_informations = new HashMap();

    private Boolean m_completed;
    private boolean sendError;

    private PplPersistentData persistentData;

    private boolean signOnLineEnabled;
    private boolean signOffLineEnabled;
    private boolean signRequired;
    private boolean onLineSigned;
    private boolean offLineSigned;

    private boolean delegate;

    public Long getOid() {
	return m_oid;
    }

    public void setOid(Long p_oid) {
	m_oid = p_oid;
    }

    public Long getEditableProcessId() {
	return m_editableProcessId;
    }

    public void setEditableProcessId(Long p_editableProcessId) {
	m_editableProcessId = p_editableProcessId;
    }

    public PplUser getUser() {
	return m_user;
    }

    public void setUser(PplUser p_user) {
	m_user = p_user;
    }

    public String getPeopleProtocollId() {
	return m_peopleProtocollId;
    }

    public void setPeopleProtocollId(String p_peopleProtollId) {
	m_peopleProtocollId = p_peopleProtollId;
    }

    public String getCommuneProtocollId() {
	return m_communeProtocollId;
    }

    public void setCommuneProtocollId(String p_communeProtollId) {
	m_communeProtocollId = p_communeProtollId;
    }

    public City getCommune() {
	return m_commune;
    }

    public void setCommune(City p_commune) {
	m_commune = p_commune;
    }

    public Timestamp getSubmittedTime() {
	return m_submittedTime;
    }

    public void setSubmittedTime(Timestamp p_submittedTime) {
	m_submittedTime = p_submittedTime;
    }

    public String getTransportTrackingNumber() {
	return m_transportTrackingNumber;
    }

    public void setTransportTrackingNumber(String p_transportTrackingNumber) {
	m_transportTrackingNumber = p_transportTrackingNumber;
    }

    public Boolean getCompleted() {
	return m_completed;
    }

    public void setCompleted(Boolean p_completed) {
	m_completed = p_completed;
    }

    /**
     * Aggiunge una collezione di stati alla storia del processo
     * 
     * @param histStates
     *            Oggetti SubmittedProcessHistory da aggiungere
     */
    public void setHistoryState(SubmittedProcessHistory[] histStates) {
	m_history.clear();
	for (int i = 0; i < histStates.length; i++)
	    m_history.add(histStates[i]);
    }

    /**
     * 
     * @return Restituisce gli stati che costituiscono la storia del processo
     */
    public SubmittedProcessHistory[] getHistoryState() {
	return (SubmittedProcessHistory[]) m_history
		.toArray(new SubmittedProcessHistory[0]);
    }

    /**
     * Aggiunge uno stato alla storia del processo
     * 
     * @param histState
     *            Oggetto SubmittedProcessHistory da aggiungere
     */
    public void addHistoryStates(SubmittedProcessHistory histState) {
	m_history.add(histState);
    }

    /**
     * 
     * @param index
     *            Posizione in cui aggiungere l'oggetto
     * @param histState
     *            Oggetto SubmittedProcessHistory da aggiungere
     */
    public void setHistoryState(int index, SubmittedProcessHistory histState) {
	if (index >= 0 && index < m_history.size())
	    m_history.set(index, histState);
    }

    /**
     * 
     * @param index
     *            Indice dello stato da cercare
     * @return Restituisce l'oggetto SubmittedProcessHistory specificato
     */
    public SubmittedProcessHistory getHistoryState(int index) {
	if (index >= 0 && index < m_history.size())
	    return (SubmittedProcessHistory) m_history.get(index);
	return null;
    }

    /**
     * 
     * @param infos
     *            Vettore di ogetti SubmittedProcessInformation da aggiungere
     */
    public void setProcessInformation(SubmittedProcessInformation[] infos) {
	m_informations.clear();
	for (int i = 0; i < infos.length; i++)
	    m_informations.put(infos[i].getKey(), infos[i]);
    }

    /**
     * 
     * @return Restituisce un vettore contenente gli oggetti
     *         SubmittedProcessInformation del processo
     */
    public SubmittedProcessInformation[] getProcessInformation() {
	Iterator iter = m_informations.keySet().iterator();
	ArrayList result = new ArrayList();
	int count = 0;
	while (iter.hasNext()) {
	    result.add(m_informations.get(iter.next()));
	    count++;
	}
	return (SubmittedProcessInformation[]) result
		.toArray(new SubmittedProcessInformation[count]);
    }

    /**
     * 
     * @param key
     *            Chiave di ricerca
     * @return Restituisce l'oggetto SubmittedProcessInformation cercato per
     *         chiave
     */
    public SubmittedProcessInformation getProcessInformation(String key) {
	SubmittedProcessInformation spi = (SubmittedProcessInformation) m_informations
		.get(key);
	spi.setKey(key);
	return spi;
    }

    /**
     * 
     * @param key
     *            Chiave da associare all'oggetto
     * @param info
     *            Oggetto SubmittedProcessInformation da aggiungere
     */
    public void setProcessInformation(String key,
	    SubmittedProcessInformation info) {
	info.setKey(key);
	m_informations.put(key, info);
    }

    /**
     * @return Returns the persistentData.
     */
    public PplPersistentData getPersistentData() {
	return persistentData;
    }

    /**
     * @param persistentData
     *            The persistentData to set.
     */
    public void setPersistentData(PplPersistentData persistentData) {
	this.persistentData = persistentData;
    }

    /**
     * @return the signOnLineEnabled
     */
    public final boolean isSignOnLineEnabled() {
	return this.signOnLineEnabled;
    }

    /**
     * @param signOnLineEnabled
     *            the signOnLineEnabled to set
     */
    public final void setSignOnLineEnabled(boolean signOnLineEnabled) {
	this.signOnLineEnabled = signOnLineEnabled;
    }

    /**
     * @return the signOffLineEnabled
     */
    public final boolean isSignOffLineEnabled() {
	return this.signOffLineEnabled;
    }

    /**
     * @param signOffLineEnabled
     *            the signOffLineEnabled to set
     */
    public final void setSignOffLineEnabled(boolean signOffLineEnabled) {
	this.signOffLineEnabled = signOffLineEnabled;
    }

    /**
     * @return the signRequired
     */
    public final boolean isSignRequired() {
	return this.signRequired;
    }

    /**
     * @param signRequired
     *            the signRequired to set
     */
    public final void setSignRequired(boolean signRequired) {
	this.signRequired = signRequired;
    }

    /**
     * @return the onLineSigned
     */
    public final boolean isOnLineSigned() {
	return this.onLineSigned;
    }

    /**
     * @param onLineSigned
     *            the onLineSigned to set
     */
    public final void setOnLineSigned(boolean onLineSigned) {
	this.onLineSigned = onLineSigned;
    }

    /**
     * @return the offLineSigned
     */
    public final boolean isOffLineSigned() {
	return this.offLineSigned;
    }

    /**
     * @param offLineSigned
     *            the offLineSigned to set
     */
    public final void setOffLineSigned(boolean offLineSigned) {
	this.offLineSigned = offLineSigned;
    }

    /**
     * @return the delegate
     */
    public final boolean isDelegate() {
	return this.delegate;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public final void setDelegate(boolean delegate) {
	this.delegate = delegate;
    }

    /**
     * @param sendError
     *            the sendError to set
     */
    public final void setSendError(boolean sendError) {
	this.sendError = sendError;
    }

    /**
     * @return
     */
    public final boolean isSendError() {
	return this.sendError;
    }

}

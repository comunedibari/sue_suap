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
 * Creato il 14-giu-2007 da Cedaf s.r.l.
 *
 */
package it.people.util.status;

import it.people.City;
import it.people.util.payment.EsitoPagamento;
import it.people.util.payment.request.ObserverData;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class ProcessStatusTable {

    private String peopleId;
    private String observerClassName;
    private Long savedProcessId;
    private String email;
    private City commune;
    private String processName;
    private String userId;
    private ObserverData observerData;
    private int status;
    private EsitoPagamento paymentResult;

    public ProcessStatusTable(String peopleId, String observerClassName,
	    ObserverData observerData, Long savedProcessId, String email,
	    City commune, String processName, String userId, int status,
	    EsitoPagamento paymentResult) {
	this.peopleId = peopleId;
	this.observerClassName = observerClassName;
	this.savedProcessId = savedProcessId;
	this.email = email;
	this.commune = commune;
	this.processName = processName;
	this.userId = userId;
	this.observerData = observerData;
	this.status = status;
	this.paymentResult = paymentResult;
    }

    /**
     * @return Returns the email.
     */
    public String getEmail() {
	return email;
    }

    /**
     * @return Returns the observerClassName.
     */
    public String getObserverClassName() {
	return observerClassName;
    }

    /**
     * @return Returns the peopleId.
     */
    public String getPeopleId() {
	return peopleId;
    }

    /**
     * @return Returns the savedProcessId.
     */
    public Long getSavedProcessId() {
	return savedProcessId;
    }

    public City getCommune() {
	return commune;
    }

    public String getProcessName() {
	return processName;
    }

    /**
     * Identificativo dell'utente nella forma CODICEFISCALE@ca-people
     * 
     * @return
     */
    public String getUserId() {
	return userId;
    }

    public ObserverData getObserverData() {
	return observerData;
    }

    public int getStatus() {
	return status;
    }

    public EsitoPagamento getPaymentResult() {
	return paymentResult;
    }
}

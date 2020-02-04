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
 * Created on 20-apr-2006
 *
 */
package it.people.util.payment.request;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Michele Fabbri - Cedaf s.r.l. Questa classe � il contenitore di
 *         tutti i parametri che possono essere passati dal servizio al sistema
 *         di pagamento.
 * 
 */
public class PaymentRequestParameter {
    protected ServiceData serviceData;
    protected UserData userData;
    protected PaymentData paymentData;
    protected AccountingData accountingData;
    protected ObserverData observerData;
    // Aggiunta per gestione dati specifici dei server di pagamento
    protected Map<String, String> paymentManagerSpecificData;

    // Aggiunto temporaneamente per A&C da rimuovere e sistemare le anagrafiche
    protected it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica titolarePagamento;

    // Personalizzazione installazione comune di Bologna
    protected UserDataExt userDataExt;

    public PaymentRequestParameter() {
	this.serviceData = new ServiceData();
	this.userData = new UserData();
	this.paymentData = new PaymentData();
	this.accountingData = new AccountingData();
	this.observerData = new ObserverData();
	this.paymentManagerSpecificData = new HashMap<String, String>();

	// Personalizzazione installazione comune di Bologna
	this.userDataExt = null;

	this.titolarePagamento = null;

    }

    public AccountingData getAccountingData() {
	return accountingData;
    }

    public void setAccountingData(AccountingData accountingData) {
	this.accountingData = accountingData;
    }

    public PaymentData getPaymentData() {
	return paymentData;
    }

    public void setPaymentData(PaymentData paymentData) {
	this.paymentData = paymentData;
    }

    public ServiceData getServiceData() {
	return serviceData;
    }

    public void setServiceData(ServiceData serviceData) {
	this.serviceData = serviceData;
    }

    public UserData getUserData() {
	return userData;
    }

    public void setUserData(UserData userData) {
	this.userData = userData;
    }

    public ObserverData getObserverData() {
	return observerData;
    }

    public void setObserverData(ObserverData observerData) {
	this.observerData = observerData;
    }

    /**
     * @return the paymentManagerSpecificData
     */
    public final Map<String, String> getPaymentManagerSpecificData() {
	return this.paymentManagerSpecificData;
    }

    /**
     * @param paymentManagerSpecificData
     *            the paymentManagerSpecificData to set
     */
    public final void setPaymentManagerSpecificData(
	    Map<String, String> paymentManagerSpecificData) {
	this.paymentManagerSpecificData = paymentManagerSpecificData;
    }

    // Personalizzazione installazione comune di Bologna
    public UserDataExt getUserDataExt() {
	return userDataExt;
    }

    // Personalizzazione installazione comune di Bologna
    public UserDataExt addUserDataExt() {
	userDataExt = new UserDataExt();
	return userDataExt;
    }

    // Personalizzazione installazione comune di Bologna
    public void setUserDataExt(UserDataExt userDataExt) {
	this.userDataExt = userDataExt;
    }

    /**
     * @return the titolarePagamento
     */
    public final it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica getTitolarePagamento() {
	return this.titolarePagamento;
    }

    /**
     * @param titolare
     *            the titolarePagamento to set
     */
    public final void setTitolarePagamento(
	    it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica titolarePagamento) {
	this.titolarePagamento = titolarePagamento;
	if (titolarePagamento != null) {
	    try {
		this.getUserData().setEmailUtente(
			new InternetAddress(titolarePagamento
				.getDomicilioElettronico()));
		it.people.fsl.servizi.oggetticondivisi.PersonaFisica personaFisica = new it.people.fsl.servizi.oggetticondivisi.PersonaFisica();
		personaFisica.setCodiceFiscale(titolarePagamento
			.getCodiceFiscale());
		this.getUserData().setPersonaFisica(personaFisica);
	    } catch (AddressException e) {
		e.printStackTrace();
	    }
	}
    }

}

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
 * ProcessStatus.java
 *
 * Created on 15 febbraio 2005, 18.18
 */

package it.people.util.status;

/**
 * Definisce lo stato di un processo inviato al backend
 * 
 * @author manelli
 */
public class ProcessStatus {
    protected int statusCode;
    protected String description;

    public ProcessStatus(int statusCode, String description) {
	this.statusCode = statusCode;
	this.description = description;
    }

    public int getStatusCode() {
	return statusCode;
    }

    public String getDescription() {
	return description;
    }

    // Aggiungere gli altri tipi di stato
    public final static ProcessStatus SAVED = new ProcessStatus(1, "Salvato");
    public final static ProcessStatus SENT = new ProcessStatus(2, "Inviato");
    // public final static ProcessStatus PAYMENT_PENDING = new ProcessStatus(10,
    // "In attesa di pagamento");
    // public final static ProcessStatus PAYMENT_OK = new ProcessStatus(11,
    // "Pagamento concluso");
    // public final static ProcessStatus PAYMENT_ABORTED = new ProcessStatus(12,
    // "Pagamento abortito");

    public final static ProcessStatus PAYMENT_PENDING = new ProcessStatus(
	    PaymentStatusEnum.paymentPending.getCode(),
	    PaymentStatusEnum.paymentPending.getDescription());
    public final static ProcessStatus PAYMENT_OK = new ProcessStatus(
	    PaymentStatusEnum.paymentSuccessfull.getCode(),
	    PaymentStatusEnum.paymentSuccessfull.getDescription());
    public final static ProcessStatus PAYMENT_ABORTED = new ProcessStatus(
	    PaymentStatusEnum.paymentAborted.getCode(),
	    PaymentStatusEnum.paymentAborted.getDescription());

}

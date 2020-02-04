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
 * Creato il 6-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.util.payment;

/**
 * @author Michele Fabbri - Cedaf s.r.l. Implementare l'interfaccia per
 *         intercettare la comunicazione asincrona del risultato del pagamento
 */
public interface IPaymentObserver {
    /**
     * Il metodo sar� invocato dal framework comunicando l'esito del pagamento
     * effettuato nello svolgimento di una pratica.
     * 
     * @param esitoPagamento
     *            l'istanza � passata al metodo valorizzata con tutte le
     *            informazioni relative al pagamento ed al suo esito.
     * @param paymentProcess
     *            l'istanza mette a disposizione i meccanismi per inviare email,
     *            invocare web-service people e registrare messaggi di log.
     * @throws PaymentException
     *             l'eccezione � da lanciare nel caso si verifico errori
     *             bloccanti, nel caso in cui il framework intercetti
     *             l'eccezione tenter� di rieseguire il metodo service, il
     *             numero di tentativi � limitato.
     */
    public void service(EsitoPagamento esitoPagamento,
	    PaymentProcess paymentProcess) throws PaymentException;
}

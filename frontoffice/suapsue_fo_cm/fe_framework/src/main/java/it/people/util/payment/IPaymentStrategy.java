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
package it.people.util.payment;

import it.people.City;
import it.people.process.AbstractPplProcess;
import it.people.util.payment.request.PaymentRequestParameter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

public interface IPaymentStrategy {
    public static final String URL_BACK = "abortPayment.do";
    public static final String URL_RITORNO = "returnFromPayment.do";
    public static final String URL_ERRORE = "Errore.htm";
    public static final String URL_NOTIFICA = "framework/payment/notification.jsp";
    public static final String URL_CSS_BASE = "css/mip";

    public void initialize(String propFile);

    public ActionForward sendToPayment(AbstractPplProcess pplProcess,
	    PaymentRequestParameter paymentRequestParameter, String paymentId,
	    City commune, HttpServletRequest request);

    public EsitoPagamento getEsitoPagamento(HttpServletRequest request,
	    String buffer) throws PaymentException;
}

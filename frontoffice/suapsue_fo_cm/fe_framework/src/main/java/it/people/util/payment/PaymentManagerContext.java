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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;

public class PaymentManagerContext {
    private String propFile;
    protected IPaymentStrategy paymentStrategy;

    public PaymentManagerContext(String propFilePath)
	    throws FileNotFoundException, IOException, InstantiationException,
	    IllegalAccessException, ClassNotFoundException {
	propFile = propFilePath;
	Properties oConfig = new Properties();
	oConfig.load(new FileInputStream(propFile));

	String paymentStrategyClassName = oConfig
		.getProperty("payment.strategy.class");
	paymentStrategy = (IPaymentStrategy) Class.forName(
		paymentStrategyClassName).newInstance();
	paymentStrategy.initialize(propFile);
    }

    public ActionForward sendToPayment(AbstractPplProcess pplProcess,
	    PaymentRequestParameter paymentRequestParameter, String paymentId,
	    City commune, HttpServletRequest request) {
	return paymentStrategy.sendToPayment(pplProcess,
		paymentRequestParameter, paymentId, commune, request);
    }

    public EsitoPagamento getEsitoPagamento(HttpServletRequest request,
	    String buffer) throws PaymentException {
	return paymentStrategy.getEsitoPagamento(request, buffer);
    }

    public String getCommitMessage(String portaleID, String numeroOperazione,
	    String idOrdine, boolean ok) {
	return "<CommitMsg>" + "<PortaleID>" + portaleID + "</PortaleID>"
		+ "<NumeroOperazione>" + numeroOperazione
		+ "</NumeroOperazione>" + "<IDOrdine>" + idOrdine
		+ "</IDOrdine>" + "<Commit>" + (ok ? "OK" : "NOK")
		+ "</Commit>" + "</CommitMsg>";
    }

    public ObserverSetting getObserverSetting() {
	ObserverSetting settings = new ObserverSetting();
	try {
	    Properties oConfig = new Properties();
	    oConfig.load(new FileInputStream(propFile));
	    int retryNumber = Integer.parseInt(oConfig
		    .getProperty("observer.retryNumbers"));
	    int retryInterval = Integer.parseInt(oConfig
		    .getProperty("observer.retryIntervalSeconds"));
	    boolean doNotSendConfirmationMail = Boolean.valueOf(
		    oConfig.getProperty("observer.doNotSendConfirmationMail"))
		    .booleanValue();

	    if (retryInterval >= 0)
		settings.setRetryIntervalSeconds(retryInterval);

	    if (retryNumber >= 0)
		settings.setRetryNumbers(retryNumber);

	    settings.setDoNotSendConfirmationMail(doNotSendConfirmationMail);
	} catch (Exception ex) {
	}
	return settings;
    }

    public String getPortaleId() throws FileNotFoundException, IOException {
	Properties oConfig = new Properties();
	oConfig.load(new FileInputStream(propFile));
	return oConfig.getProperty("paymentsystem.portalid");
    }
}

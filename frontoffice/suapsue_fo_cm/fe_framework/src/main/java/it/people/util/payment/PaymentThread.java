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
 * Created on 11-mag-2005
 *
 */
package it.people.util.payment;

import it.people.City;
import it.people.exceptions.PeopleDBException;
import it.people.logger.Logger;
import it.people.process.AbstractPplProcess;
import it.people.util.ActivityLogger;
import it.people.util.EMailSender;
import it.people.util.MessageBundleHelper;
import it.people.util.status.ProcessStatus;
import it.people.util.status.ProcessStatusOwnerTable;
import it.people.util.status.ProcessStatusTable;
import it.people.util.status.StatusHelper;

import java.text.DateFormat;
import java.text.NumberFormat;

/**
 * @author Michele Fabbri
 * 
 *         Thread di gestione delle chiamate asincrone di pagamento
 */
public class PaymentThread extends Thread {
    private ActivityLogger tracer = ActivityLogger.getInstance();

    private EsitoPagamento esitoPagamento;
    private int retryNumbers;
    private int retryIntervalSeconds;
    private boolean doNotSendConfirmationMail;

    public PaymentThread(EsitoPagamento esitoPagamento, ObserverSetting setting) {
	this.esitoPagamento = esitoPagamento;
	this.retryNumbers = setting.getRetryNumbers();
	this.retryIntervalSeconds = setting.getRetryIntervalSeconds();
	this.doNotSendConfirmationMail = setting.isDoNotSendConfirmationMail();
    }

    public void run() {
	String peopleId = "indeterminato";
	City commune = null;
	String servicePackage = null;

	while (true) {
	    try {
		// Determina le informazioni dello stato del process
		ProcessStatusTable ps = StatusHelper
			.getProcessStatusTableFromPaymentId(this.esitoPagamento
				.getNumeroOperazione());
		commune = ps.getCommune();
		servicePackage = ps.getProcessName();
		peopleId = ps.getPeopleId();

		tracer.log(this.getClass(),
			"Avvio thread di pagamento (peopleId = ." + peopleId
				+ ")", ActivityLogger.DEBUG);
		runObserver(ps);

		// Salva lo stato del pagamento in base al risultato dell'esito
		updateProcessStatus();
		tracer.log(this.getClass(), "Thread pagamento (peopleId = ."
			+ peopleId + "): salvato lo stato.",
			ActivityLogger.DEBUG);

		// Invia il mail di conferma in caso di esito positivo
		if (!doNotSendConfirmationMail) {
		    if (esitoPagamento.getEsito().equals(
			    EsitoPagamento.ES_PAGAMENTO_OK)) {
			sendAckMail(ps, true);
		    } else if (esitoPagamento.getEsito().equals(
			    EsitoPagamento.ES_PAGAMENTO_KO)) {
			sendAckMail(ps, false);
		    } else {
			throw new PaymentException(
				"Codice di risposta del MIP non valido.");
		    }

		}

		// Se tutte le operazioni sono state eseguite esce
		break;
	    } catch (Exception ex) {
		String errorMessage = "Errore nel thread di gestione Pagamento (peopleId = ."
			+ peopleId + "):\n" + ex.toString();
		tracer.log(this.getClass(),
			"Errore nel thread di gestione Pagamento (peopleId = ."
				+ peopleId + "):\n" + ex.toString(),
			ActivityLogger.ERROR);

		// Se l'errore lo consente effettua il log al livello di
		// servizio
		if (commune != null && servicePackage != null)
		    Logger.getLogger(servicePackage, commune).error(
			    errorMessage);

		// terminati i tentativi esce
		if (--retryNumbers <= 0) {
		    tracer.log(this.getClass(),
			    "Thread pagamento: numero massimo tentativi raggiunto (peopleId = ."
				    + peopleId + ")", ActivityLogger.ERROR);
		    break;
		}

		try {
		    sleep((long) (retryIntervalSeconds * 1000));
		} catch (InterruptedException iEx) {
		    tracer.log(PaymentThread.class, iEx.getMessage(),
			    ActivityLogger.WARN);
		}
	    }
	}

	tracer.log(this.getClass(), "Thread pagamento completato (peopleId = ."
		+ peopleId + ")", ActivityLogger.DEBUG);
    }

    protected void runObserver(ProcessStatusTable peopleStatus)
	    throws PaymentException {
	String observerClassName = peopleStatus.getObserverClassName();

	try {
	    // Verifica se � previsto un observer per il servizio
	    if (observerClassName != null && observerClassName != "") {
		// Class[] interfaces =
		// Class.forName(observerClassName).getInterfaces();
		Class observerClass = Class.forName(observerClassName);

		// Solo una delle due interfacce � invocata
		if (IPaymentObserver.class.isAssignableFrom(observerClass)) {
		    // if (findInterface(interfaces,
		    // IPaymentObserver.class.getName())) {
		    // invoca il service() della classe Observer del servizio
		    IPaymentObserver observer = (IPaymentObserver) Class
			    .forName(observerClassName).newInstance();

		    PaymentProcess paymentProcess = new PaymentProcess(
			    peopleStatus.getCommune(),
			    peopleStatus.getProcessName(),
			    peopleStatus.getEmail(),
			    peopleStatus.getPeopleId(),
			    peopleStatus.getUserId(),
			    peopleStatus.getSavedProcessId(),
			    peopleStatus.getObserverData());

		    observer.service(esitoPagamento, paymentProcess);
		} else if (IPaymentNotificationObserver.class
			.isAssignableFrom(observerClass)) {
		    // } else if (findInterface(interfaces,
		    // IPaymentNotificationObserver.class.getName())) {
		    // invoca il service() della classe Observer del servizio
		    IPaymentNotificationObserver observer = (IPaymentNotificationObserver) Class
			    .forName(observerClassName).newInstance();
		    observer.service(this.esitoPagamento);
		}

	    }
	} catch (Exception ex) {
	    throw new PaymentException("Errore nell'esecuzione del Observer.\n"
		    + ex);
	}
    }

    private boolean findInterface(Class[] interfaces, String interfaceName) {
	for (int i = 0; i < interfaces.length; i++) {
	    if (interfaces[i].getName().equals(interfaceName))
		return true;
	}
	return false;
    }

    // Invia il mail di avvenuto pagamento al cittadino
    private void sendAckMail(ProcessStatusTable ps, boolean paymentOk) {
	String contentKey;
	String objKey;

	ProcessStatusOwnerTable pso = StatusHelper
		.getProcessStatusOwnerTableFromPaymentId(this.esitoPagamento
			.getNumeroOperazione());

	if (paymentOk) {
	    // Esito positivo
	    if (AbstractPplProcess.isAnonymousPplProcess(ps.getPeopleId())) {
		if (pso != null && pso.isBusiness()) {
		    objKey = "payment.auth.pg.mail.ok.object";
		    contentKey = "payment.auth.pg.mail.ok.content";
		} else if (pso != null && !pso.isBusiness()) {
		    objKey = "payment.auth.mail.ok.object";
		    contentKey = "payment.auth.mail.ok.content";
		} else {
		    objKey = "payment.anon.mail.ok.object";
		    contentKey = "payment.anon.mail.ok.content";
		}
	    } else {
		if (pso != null && pso.isBusiness()) {
		    objKey = "payment.auth.pg.mail.ok.object";
		    contentKey = "payment.auth.pg.mail.ok.content";
		} else {
		    objKey = "payment.auth.mail.ok.object";
		    contentKey = "payment.auth.mail.ok.content";
		}
	    }
	} else {
	    // Esito Negativo
	    if (AbstractPplProcess.isAnonymousPplProcess(ps.getPeopleId())) {
		objKey = "payment.anon.mail.ko.object";
		contentKey = "payment.anon.mail.ko.content";
	    } else {
		if (pso != null && pso.isBusiness()) {
		    objKey = "payment.auth.pg.mail.ko.object";
		    contentKey = "payment.auth.pg.mail.ko.content";
		} else {
		    objKey = "payment.auth.mail.ko.object";
		    contentKey = "payment.auth.mail.ko.content";
		}
	    }
	}

	// Parametri per la costruzione del messaggio
	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	NumberFormat nf = NumberFormat.getInstance();

	if (pso == null) {
	    pso = new ProcessStatusOwnerTable();
	}

	String[] params = new String[] { ps.getPeopleId(),
		this.esitoPagamento.getCircuitoAutorizzativo(),
		this.esitoPagamento.getCircuitoSelezionato(),
		df.format(this.esitoPagamento.getDataOrdine()),
		this.esitoPagamento.getDescrizioneCircuitoAutorizzativo(),
		this.esitoPagamento.getDescrizioneCircuitoSelezionato(),
		this.esitoPagamento.getDescrizioneEsito(),
		this.esitoPagamento.getDescrizioneSistemaPagamento(),
		this.esitoPagamento.getEsito(),
		this.esitoPagamento.getIDOrdine(),
		this.esitoPagamento.getIDTransazione(),
		nf.format(this.esitoPagamento.getImportoCommissioni() / 100.0),
		nf.format(this.esitoPagamento.getImportoPagato() / 100.0),
		this.esitoPagamento.getNumeroAutorizzazione(),
		this.esitoPagamento.getNumeroOperazione(),
		this.esitoPagamento.getSistemaPagamento(), pso.getFirstName(),
		pso.getLastName(), pso.getTaxCode(), pso.getBusinessName(),
		pso.getVatCode(), pso.getInfo1(), pso.getInfo2() };

	// se non indicato il comune (posto a null) non � necessario
	// locale, processName � cercato nei bundle comunque
	String messageContent = MessageBundleHelper.message(contentKey, params,
		"", null, null);
	String messageObj = MessageBundleHelper.message(objKey, params, "",
		null, null);

	EMailSender emailSender = new EMailSender(ps.getCommune().getKey());
	try {
	    emailSender.sendMail(ps.getEmail(), messageObj, messageContent);
	} catch (Exception ex) {
	    tracer.log(PaymentThread.class,
		    "Impossibile inviare il mail di conferma avvenuto pagamento per la pratica '"
			    + ps.getPeopleId() + "'. Messaggio eccezione:\n "
			    + ex.getMessage(), ActivityLogger.ERROR);
	}
	;
    }

    /**
     * Salva lo stato del pagamento in base al risultato dell'esito.
     * 
     * @throws PeopleDBException
     * @throws PaymentException
     */
    private void updateProcessStatus() throws PeopleDBException,
	    PaymentException {
	// salva lo stato
	ProcessStatus stato;
	if (this.esitoPagamento.getEsito().equals(
		EsitoPagamento.ES_PAGAMENTO_OK))
	    stato = ProcessStatus.PAYMENT_OK;
	else if (this.esitoPagamento.getEsito().equals(
		EsitoPagamento.ES_PAGAMENTO_KO))
	    stato = ProcessStatus.PAYMENT_ABORTED;
	else
	    throw new PaymentException("Codice di esito pagamento non valido");

	StatusHelper.updateProcessStatus(
		this.esitoPagamento.getNumeroOperazione(), stato,
		esitoPagamento);
    }

}

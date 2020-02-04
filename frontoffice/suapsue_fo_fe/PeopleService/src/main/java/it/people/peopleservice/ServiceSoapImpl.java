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
package it.people.peopleservice;

import it.people.backend.webservice.BEService;
import it.people.backend.webservice.BEServiceServiceLocator;
import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelope;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.IResponseEnvelope;
import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.exceptions.InvalidEnvelopeException;
import it.people.envelope.exceptions.NotAnEnvelopeException;
import it.people.envelope.util.DelegheHelper;
import it.people.envelope.util.EnvelopeHelper;
import it.people.peopleservice.dal.BENode;
import it.people.peopleservice.dal.FENode;
import it.people.peopleservice.dal.Reference;
import it.people.peopleservice.dal.ServiceSoapDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;

public class ServiceSoapImpl implements it.people.peopleservice.ServiceSoap {
	protected static final String EVIROMENT_CONTEXT_NAME_DUMP_PATH = "xml_logs";
	protected static final String EVIROMENT_CONTEXT_NAME_DUMP_STORICIZZA = "storicizza_dump";
	protected static final String EVIROMENT_CONTEXT_NAME_DUMP_SUDDIVIDI = "suddividi_dump";

	private Logger logger = LoggerFactory.getLogger(ServiceSoapImpl.class);
	private String dburi;

	public ServiceSoapImpl() {
	}

	public java.lang.String processWithEnvelope(java.lang.String communeKey, java.lang.String _package,
			java.lang.String beService, java.lang.String data, java.lang.String envelopeXML)
			throws java.rmi.RemoteException {

		ServiceSoapDao dao = new ServiceSoapDao();

		try {
			// Carica la configurazione del servizio
			dao.open();
			FENode feNode = dao.getFENode(communeKey);
			Reference reference = dao.getReference(communeKey, _package, beService);
			boolean dump = reference.isDump();
			String nomeServizio = reference.getValue();
			// Modifica associazione be a nodi
			// BENode beNode = dao.getBENode(nomeServizio);
			BENode beNode = dao.getBENode(nomeServizio, communeKey);
			String beURL = beNode.getURL();
			dao.close();

			IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();

			// Ricrea il bean della busta utilizzato anche dal controllo deleghe
			IRequestEnvelope envelopedRequestBean = createRequestEnvelope(envelopeFactory, envelopeXML, data,
					nomeServizio);

			// Effettua il controllo delle deleghe
			// il controllo delle deleghe � da effettuare a prescindere dal collegamento
			// a connect e se:
			// - sul nodo di front-end � stato abilitato il controllo delle deleghe
			// - sul singolo back-end non si inibito il controllo
			if (feNode.isCheckDelegate() && !beNode.isDisableCheckDelegate()) {
				boolean skipCheckDeleghe = false;

				// Nel caso di persona fisica che faccia una richiesta per se stessa
				// il controllo delle deleghe non deve essere effettuato
				//
				if (envelopedRequestBean.getEstremiTitolare().isEstremiPersonaFisica()) {
					String cfRichiedente = envelopedRequestBean.getEstremiRichiedente().getCodiceFiscale();
					String cfTitolare = envelopedRequestBean.getEstremiTitolare().getEstremiPersonaFisica()
							.getCodiceFiscale();
					skipCheckDeleghe = cfRichiedente.equalsIgnoreCase(cfTitolare);
				}

				if (!skipCheckDeleghe)
					DelegheHelper.checkDelega(envelopedRequestBean, feNode.getCheckDelegateURL());
			}

			// Determina se si sta usando Connects
			if (beNode.isEnvelopEnabled()) {
				// il back-end da utilizzare � Connects quindi utilizza la busta

				// richiama il servizio di BE passandogli la busta
				String envelopedXmlRequest = envelopeFactory.createEnvelopeXmlText(envelopedRequestBean);
				String envelopedXmlResponse = callService(_package, nomeServizio, new URL(beURL), envelopedXmlRequest,
						dump);

				// estrae il contenuto dalla risposta
				IEnvelope envelopedResponseBean = envelopeFactory.createEnvelopeBean(envelopedXmlResponse);
				if (!envelopedResponseBean.isResponseEnvelope())
					throw new Exception("Envelope di risposta del back-end non valido");

				ContenutoBusta responseBean = ((IResponseEnvelope) envelopedResponseBean).getContenuto();

				// verifica se la risposta � un'eccezione xml
				// se � eccezione xml lancia un eccezione java con il messaggio estratto
				// dall'xml
				if (responseBean.isEccezione())
					throw new Exception();

				// se non � un'eccezione xml ritorna il messaggio estratto
				return responseBean.getContenutoXML();

			} else {
				// il back-end � di tipo standard e quindi non utilizza la busta
				return callService(_package, nomeServizio, new URL(beURL), data, dump);
			}
		} catch (Exception ex) {
			logger.error("", ex);
			throw new RemoteException(ex.getMessage());
		} finally {
			if (dao != null)
				try {
					dao.close();
				} catch (Exception ex) {
				}
			;
		}
	}

	/**
	 * Ricrea il bean della busta inserendo il contenuto ed il servizio
	 * 
	 * @param envelopeFactory
	 *            factory per la busta
	 * @param envelopeXML
	 *            busta vuota
	 * @param data
	 *            dati da inserire nella busta
	 * @param nomeServizio
	 *            nome del servizio
	 * @return ritorna il bbean della busta
	 * @throws Exception
	 */
	protected IRequestEnvelope createRequestEnvelope(IEnvelopeFactory envelopeFactory, String envelopeXML, String data,
			String nomeServizio) throws Exception {

		// File file = new File("c:/envelopeXML");
		// FileOutputStream fos = new FileOutputStream(file);
		// fos.write(envelopeXML.getBytes());
		// fos.flush();
		// fos.close();

		// verifica che l'xml sia valido
		if (!EnvelopeHelper.parseXMLAndValidate(envelopeXML, EnvelopeHelper.getDefaultXmlOptions()))
			throw new Exception("Formato della richiesta al back-office non valido.");

		// crea il bean della busta
		IRequestEnvelope envelopedRequestBean = (IRequestEnvelope) envelopeFactory.createEnvelopeBean(envelopeXML);

		// inserisce il contenuto nella busta
		envelopedRequestBean.setContenuto(new ContenutoBusta(data));

		// imposta il nome del servizio di BE
		envelopedRequestBean.setNomeServizio(nomeServizio);

		return envelopedRequestBean;
	}

	public java.lang.String process(java.lang.String communeKey, java.lang.String _package, java.lang.String beService,
			java.lang.String data) throws java.rmi.RemoteException {

		ServiceSoapDao dao = new ServiceSoapDao();
		String nomeServizio = null;
		String beURL = null;
		BENode beNode = null;
		boolean dump = false;

		try {
			dao.open();
			Reference reference = dao.getReference(communeKey, _package, beService);
			dump = reference.isDump();
			nomeServizio = reference.getValue();
			// Modifica associazione be a nodi
			// beNode = dao.getBENode(nomeServizio);
			beNode = dao.getBENode(nomeServizio, communeKey);
			beURL = beNode.getURL();
			dao.close();

			// tento di ricostruire il bean della busta a partire dal tracciato XML della
			// busta ricevuto
			// nel caso in cui questo vada a buon fine estraggo il contenuto che passo al
			// metodo
			// processWithEnvelope insieme al tracciato XML della busta ricevuto
			IEnvelopeFactory envelopeFactory = new EnvelopeFactory_modellazioneb116_envelopeb002();
			IRequestEnvelope requestEnvelopeBean = (IRequestEnvelope) envelopeFactory.createEnvelopeBean(data);
			String xmlContent = requestEnvelopeBean.getContenuto().getContenutoXML();

			String requestEnvelopeXML = envelopeFactory.createEnvelopeXmlText(requestEnvelopeBean);
			return processWithEnvelope(communeKey, _package, beService, xmlContent, requestEnvelopeXML);
		} catch (NotAnEnvelopeException naeex) {
			// nel caso in cui il tracciato ricevuto non corrisponda a quello di una busta,
			// si assume
			// che sia un tracciato normale e viene passato avanti come tale, nel modo
			// tradizionale
			try {
				return callService(_package, nomeServizio, new URL(beURL), data, dump);
			} catch (Exception ex) {
				logger.error("", ex);
				throw new RemoteException(ex.getMessage());
			}
		} catch (InvalidEnvelopeException ieex) {
			// nel caso in cui il tracciato ricevuto sia una busta, ma non valida, il
			// processo si interrompe
			// con il sollevamento di un'eccezione
			logger.error("", ieex);
			throw new RemoteException(ieex.getMessage());
		} catch (Throwable ex) {
			logger.error("", ex);
			throw new RemoteException(ex.getMessage());
		} finally {
			if (dao != null)
				try {
					dao.close();
				} catch (Exception ex) {
				}
			;
		}

	}

	/**
	 * Invocazione del servizio di BE
	 * 
	 * @param serviceName
	 *            nome del servizio di BE
	 * @param serviceURL
	 *            url del servizio di BE
	 * @param data
	 *            xml da inviare
	 * @param dump
	 *            se uguale a true fa il dump su file dei messaggi scambiati
	 * @return risposta del servizio di BE
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	protected String callService(String _package, String backendServiceName, URL serviceURL, String data, boolean dump)
			throws ServiceException, RemoteException {
		// Invoca il servizio di Backend
		BEServiceServiceLocator locator = new BEServiceServiceLocator();
		BEService service = locator.getBEService(serviceURL);
		logger.debug("Invocazione del BE: '" + backendServiceName + "'");

		// if (dump)
		dumpMessage(_package, backendServiceName, true, data);

		String result = service.process(data);

		// if (dump)
		dumpMessage(_package, backendServiceName, false, result);

		// ritorno della risposta
		return result;
	}

	protected void dumpMessage(String _package, String backendServiceName, boolean request, String data) {
		String fileName = "";

		String errorSuffix = (request ? " in ingresso" : " in uscita");
		PrintStream os = null;

		try {
			Context initContext = new InitialContext();
			Context eviromentContext = (Context) initContext.lookup("java:comp/env");

			boolean separaInCartelle = false;
			try {
				separaInCartelle = ((Boolean) eviromentContext
						.lookup(ServiceSoapImpl.EVIROMENT_CONTEXT_NAME_DUMP_SUDDIVIDI)).booleanValue();
			} catch (Exception ex) {
			}
			;

			fileName = (String) eviromentContext.lookup(ServiceSoapImpl.EVIROMENT_CONTEXT_NAME_DUMP_PATH);

			if (separaInCartelle) {
				fileName += File.separator + getDirNameFromPackage(_package);
				(new File(fileName)).mkdir();
			}

			fileName += File.separator + backendServiceName;

			boolean storicizza = false;
			try {
				storicizza = ((Boolean) eviromentContext.lookup(ServiceSoapImpl.EVIROMENT_CONTEXT_NAME_DUMP_STORICIZZA))
						.booleanValue();
			} catch (Exception ex) {
			}
			;

			if (storicizza) {
				String dateString = (new SimpleDateFormat("yyyyMMddHHmmssSSS"))
						.format(Calendar.getInstance().getTime());
				fileName += "-" + dateString;
			}

			if (request)
				fileName += "-toBE.xml";
			else
				fileName += "-fromBE.xml";

			os = new PrintStream(new FileOutputStream(fileName));
			os.print(data);

		} catch (NamingException namEx) {
			logger.error("Impossibile determinare il path su cui effettuare il dump dell'xml" + errorSuffix, namEx);
		} catch (FileNotFoundException fnfEx) {
			logger.error("Impossibile creare il file per il dump dell'xml" + errorSuffix, fnfEx);
		} catch (Exception ex) {
			logger.error("Errore nel dump dell'xml" + errorSuffix, ex);
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (Exception ex) {
				}
			;
		}
	}

	private String getDirNameFromPackage(String _package) {
		String names[] = _package.split("\\.");
		String dirName = "";
		for (int i = names.length - 3; i < names.length; i++)
			dirName += getFirstUpperCaseString(names[i]);
		return dirName;
	}

	private String getFirstUpperCaseString(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
	}
}

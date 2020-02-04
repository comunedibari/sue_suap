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

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;

import it.people.City;
import it.people.layout.multicommune.MultiCommuneCss;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.ActivityLogger;
import it.people.util.payment.request.EnteDestinatario;
import it.people.util.payment.request.ImportoContabile;
import it.people.util.payment.request.PaymentRequestParameter;
import it.people.util.payment.request.UserInfo;
import it.regulus.osa.constant.Constant;
import it.regulus.osa.secretcode.PayServerClient;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.RedirectingActionForward;

public class MIPPaymentManager extends AbstractPaymentManager implements
	IPaymentStrategy {

    private static final int REQOK = 0;
    private static final int REQFAILED = 1;
    private static final int REQERROR = 2;
    private static final int WINDOW_MINUTES = 10;

    private ActivityLogger m_oTracer = ActivityLogger.getInstance();
    private int m_iErrorValue = REQFAILED;
    private String m_sErrorDescr = "";

    public ActionForward sendToPayment(AbstractPplProcess pplProcess,
	    PaymentRequestParameter paymentRequestParameter, String paymentId,
	    City commune, HttpServletRequest request) {
	AbstractData data = (AbstractData) pplProcess.getData();
	m_oTracer.log(this.getClass(), "SendToPaymentSys con operazione "
		+ paymentId, ActivityLogger.DEBUG);

	if (paymentRequestParameter.getTitolarePagamento() == null) {
	    if (data.getProfiloTitolare() != null) {
		if (data.getProfiloTitolare().getProfiloTitolarePF() != null) {
		    try {
			paymentRequestParameter.getUserData().setEmailUtente(
				new InternetAddress(data.getProfiloTitolare()
					.getProfiloTitolarePF()
					.getDomicilioElettronico()));
			it.people.fsl.servizi.oggetticondivisi.PersonaFisica personaFisica = new it.people.fsl.servizi.oggetticondivisi.PersonaFisica();
			personaFisica.setCodiceFiscale(data
				.getProfiloTitolare().getProfiloTitolarePF()
				.getCodiceFiscale());
			paymentRequestParameter.getUserData().setPersonaFisica(
				personaFisica);
		    } catch (AddressException e) {
			e.printStackTrace();
		    }
		} else if (data.getProfiloTitolare().getProfiloTitolarePG() != null
			&& data.getProfiloTitolare().getProfiloTitolarePG()
				.getRappresentanteLegale() != null) {
		    try {
			paymentRequestParameter.getUserData().setEmailUtente(
				new InternetAddress(data.getProfiloTitolare()
					.getProfiloTitolarePG()
					.getRappresentanteLegale()
					.getDomicilioElettronico()));
			it.people.fsl.servizi.oggetticondivisi.PersonaFisica personaFisica = new it.people.fsl.servizi.oggetticondivisi.PersonaFisica();
			personaFisica.setCodiceFiscale(data
				.getProfiloTitolare().getProfiloTitolarePG()
				.getRappresentanteLegale().getCodiceFiscale());
			paymentRequestParameter.getUserData().setPersonaFisica(
				personaFisica);
		    } catch (AddressException e) {
			e.printStackTrace();
		    }
		}
	    }
	} else {
	    try {
		paymentRequestParameter.getUserData().setEmailUtente(
			new InternetAddress(paymentRequestParameter
				.getTitolarePagamento()
				.getDomicilioElettronico()));
		it.people.fsl.servizi.oggetticondivisi.PersonaFisica personaFisica = new it.people.fsl.servizi.oggetticondivisi.PersonaFisica();
		personaFisica.setCodiceFiscale(paymentRequestParameter
			.getTitolarePagamento().getCodiceFiscale());
		paymentRequestParameter.getUserData().setPersonaFisica(
			personaFisica);
	    } catch (AddressException e) {
		e.printStackTrace();
	    }
	}

	String sUrl = this
		.prepareSendBuffer(paymentRequestParameter, paymentId,
		// FIX 2008-02-27 - Nel caso di accesso come intermediario e
		// titolare persona giuridica non funziona
		// Invece del titolare viene usato il richiedente
		// data.getTitolare().getPersonaFisica().getCodiceFiscale(),
			data.getRichiedente().getUtenteAutenticato()
				.getCodiceFiscale(), commune.getKey(), request);

	m_oTracer.log(this.getClass(), "SendToPaymentSys con url " + sUrl,
		ActivityLogger.DEBUG);

	if (sUrl.length() > 0) {
	    ActionForward afwd = new RedirectingActionForward();
	    afwd.setPath(sUrl);
	    return afwd;
	}

	return null;
    }

    public EsitoPagamento getEsitoPagamento(HttpServletRequest request,
	    String buf) throws PaymentException {
	String reqBuffer = request.getParameter("buffer");

	if (reqBuffer == null)
	    throw new PaymentException(
		    "Risposta non valida dal sistema di pagamento.");

	String buffer;
	try {
	    String bt = java.net.URLDecoder.decode(reqBuffer, "utf-8");
	    buffer = readReturnBuffer(bt);
	    executeBufferDump(buffer, "", false);
	} catch (UnsupportedEncodingException e) {
	    return null;
	}

	// testa errore
	if (m_iErrorValue == REQOK) {
	    EsitoPagamento ep = new EsitoPagamento(buffer);
	    return ep;
	} else {
	    return null;
	}
    }

    // <- Personalizzazione installazione comune di Bologna
    private String FillUserInfo(UserInfo oInfoUtente, String sElementName) {
	String sResult = "";
	if (oInfoUtente != null) {
	    sResult = sResult + "<" + sElementName + ">";
	    sResult = sResult + "<IdentificativoUtente>"
		    + oInfoUtente.getIdentificativoUtente()
		    + "</IdentificativoUtente>";
	    sResult = sResult + "<Nome>" + oInfoUtente.getNome() + "</Nome>";
	    sResult = sResult + "<Cognome>" + oInfoUtente.getCognome()
		    + "</Cognome>";
	    sResult = sResult + "</" + sElementName + ">";
	}
	return sResult;
    }

    // Personalizzazione installazione comune di Bologna ->

    protected String prepareSendBuffer(
	    PaymentRequestParameter paymentParameter, String sNumOperazione,
	    String sCodFisc, String communeId, HttpServletRequest request) {
	try {

	    String sBufferData = "";
	    String sServiceUrl = oConfig.getProperty("service.url");
	    String notificationUrl = oConfig.getProperty("notification.url");

	    boolean newPaymentsystemType = "New".equalsIgnoreCase(oConfig
		    .getProperty("paymentsystem.type")) ? true : false;

	    sBufferData = "<PaymentRequest>";
	    sBufferData += "<PortaleID>"
		    + oConfig.getProperty("paymentsystem.portalid")
		    + "</PortaleID>";
	    sBufferData += "<Funzione>PAGAMENTO</Funzione>";
	    sBufferData += "<URLDiRitorno>" + sServiceUrl + URL_RITORNO
		    + "</URLDiRitorno>";

	    sBufferData += "<URLDiErrore>" + sServiceUrl + URL_ERRORE
		    + "</URLDiErrore>";
	    sBufferData += "<URLBack>" + sServiceUrl + URL_BACK + "</URLBack>";
	    sBufferData += "<URLDiNotifica>" + notificationUrl + URL_NOTIFICA
		    + "</URLDiNotifica>";

	    if (newPaymentsystemType)
		sBufferData += "<CommitNotifica>S</CommitNotifica>";
	    sBufferData += "<EmailPortale></EmailPortale>";

	    // Il nodo NotificaEsitiNegativi indica al MIP di non comunicare
	    // gli esiti negativi, questa modifica � necessaria in quanto il
	    // diversamente il MIP notifica pi� di un esito negativo,
	    // diversamente
	    // da quanto atteso dal framework e dai servizi per i quali l'esito
	    // negativo deve essere unico.
	    sBufferData += "<NotificaEsitiNegativi>N</NotificaEsitiNegativi>";

	    // Indica al MIP di ritornare i dati specifici
	    sBufferData += "<RitornaDatiSpecifici>S</RitornaDatiSpecifici>";

	    // PAYMENT DATA
	    sBufferData += "<PaymentData>";
	    sBufferData += "<NumeroOperazione>" + sNumOperazione
		    + "</NumeroOperazione>";
	    sBufferData += "<Valuta>EUR</Valuta>";
	    sBufferData += "<Importo>"
		    + paymentParameter.getPaymentData().getImporto()
		    + "</Importo>";
	    sBufferData += "<ImportoCommissioni></ImportoCommissioni>";
	    sBufferData += "<CalcoloCommissioni>S</CalcoloCommissioni>";
	    sBufferData += "</PaymentData>";

	    // USER DATA
	    sBufferData += "<UserData>";
	    sBufferData += "<EmailUtente>"
		    + paymentParameter.getUserData().getEmailUtente()
			    .toUnicodeString() + "</EmailUtente>";
	    sBufferData += "<IdentificativoUtente>" + sCodFisc
		    + "</IdentificativoUtente>";
	    sBufferData += "<UserID></UserID>";
	    sBufferData += "<TokenID></TokenID>";
	    sBufferData += "</UserData>";

	    // <- Personalizzazione installazione comune di Bologna
	    if (paymentParameter.getUserDataExt() != null) {
		int iPos = sBufferData.length();
		sBufferData = sBufferData + "<UserDataExt>";
		sBufferData = sBufferData
			+ FillUserInfo(paymentParameter.getUserDataExt()
				.getUtenteLogin(), "UtenteLogin");
		sBufferData = sBufferData
			+ FillUserInfo(paymentParameter.getUserDataExt()
				.getUtenteOperante(), "UtenteOperante");
		sBufferData = sBufferData
			+ FillUserInfo(paymentParameter.getUserDataExt()
				.getUtenteMandante(), "UtenteMandante");
		sBufferData = sBufferData + "</UserDataExt>";
		m_oTracer.log(getClass(),
			"SendToPaymentSys - inviato UserDataExt ["
				+ sBufferData.substring(iPos) + "]", 10000);
	    } else {
		m_oTracer.log(getClass(),
			"SendToPaymentSys - UserDataExt non presente", 10000);
	    }
	    // Personalizzazione installazione comune di Bologna ->

	    // SERVICE DATA
	    sBufferData += "<ServiceData>";
	    sBufferData += "<IDServizio>"
		    + paymentParameter.getServiceData().getIdServizio()
		    + "</IDServizio>";
	    if (newPaymentsystemType)
		sBufferData += "<URLCSS>" + sServiceUrl
			+ getCssUrl(request, communeId, URL_CSS_BASE)
			+ "</URLCSS>";
	    if (paymentParameter.getServiceData().getNumeroDocumento() != null)
		sBufferData += "<NumeroDocumento>"
			+ paymentParameter.getServiceData()
				.getNumeroDocumento() + "</NumeroDocumento>";
	    if (paymentParameter.getServiceData().getAnnoDocumento() != null)
		sBufferData += "<AnnoDocumento>"
			+ paymentParameter.getServiceData().getAnnoDocumento()
			+ "</AnnoDocumento>";
	    if (paymentParameter.getServiceData().getDatiSpecifici() != null)
		sBufferData += "<DatiSpecifici><![CDATA["
			+ paymentParameter.getServiceData().getDatiSpecifici()
			+ "]]></DatiSpecifici>";
	    sBufferData += "</ServiceData>";

	    // ACCOUNTING DATA
	    if (paymentParameter.getAccountingData().getEntiDestinatari()
		    .size() > 0
		    || paymentParameter.getAccountingData()
			    .getImportiContabili().size() > 0) {

		sBufferData += "<AccountingData>";

		if (paymentParameter.getAccountingData().getImportiContabili()
			.size() > 0) {
		    sBufferData += "<ImportiContabili>";
		    for (Iterator iter = paymentParameter.getAccountingData()
			    .getImportiContabili().iterator(); iter.hasNext();) {
			ImportoContabile importoContabile = (ImportoContabile) iter
				.next();
			sBufferData += "<ImportoContabile>";
			if (importoContabile.getIdentificativo() != null)
			    sBufferData += "<Identificativo>"
				    + importoContabile.getIdentificativo()
				    + "</Identificativo>";
			if (importoContabile.getValore() != null)
			    sBufferData += "<Valore>"
				    + importoContabile.getValore()
				    + "</Valore>";
			sBufferData += "</ImportoContabile>";
		    }
		    sBufferData += "</ImportiContabili>";
		}

		if (paymentParameter.getAccountingData().getEntiDestinatari()
			.size() > 0) {
		    sBufferData += "<EntiDestinatari>";
		    for (Iterator iter = paymentParameter.getAccountingData()
			    .getEntiDestinatari().iterator(); iter.hasNext();) {
			EnteDestinatario enteDestinatario = (EnteDestinatario) iter
				.next();
			sBufferData += "<EnteDestinatario>";
			if (enteDestinatario.getIdentificativo() != null)
			    sBufferData += "<Identificativo>"
				    + enteDestinatario.getIdentificativo()
				    + "</Identificativo>";
			if (enteDestinatario.getValore() != null)
			    sBufferData += "<Valore>"
				    + enteDestinatario.getValore()
				    + "</Valore>";
			if (enteDestinatario.getCausale() != null)
			    sBufferData += "<Causale>"
				    + enteDestinatario.getCausale()
				    + "</Causale>";
			if (enteDestinatario.getImportoContabileIngresso() != null)
			    sBufferData += "<ImportoContabileIngresso>"
				    + enteDestinatario
					    .getImportoContabileIngresso()
				    + "</ImportoContabileIngresso>";
			if (enteDestinatario.getImportoContabileUscita() != null)
			    sBufferData += "<ImportoContabileUscita>"
				    + enteDestinatario
					    .getImportoContabileUscita()
				    + "</ImportoContabileUscita>";
			sBufferData += "</EnteDestinatario>";
		    }
		    sBufferData += "</EntiDestinatari>";
		}

		sBufferData += "</AccountingData>";
	    }
	    sBufferData += "</PaymentRequest>";

	    executeBufferDump(sBufferData, sNumOperazione, true);

	    m_oTracer.log(this.getClass(), "SendToPaymentSys con buffer "
		    + sBufferData, ActivityLogger.DEBUG);

	    // sezione su cig.xml
	    PayServerClient osc;
	    osc = new PayServerClient("CHIAVE", PayServerClient.PS2S_KT_CLEAR);

	    // comunicazione server2server
	    osc.ServerURL(oConfig.getProperty("paymentsystem.S2SUrl")
		    + "Request2RID.jsp");

	    // Prepara il buffer di protocollo
	    int ret = osc.PS2S_PC_Request2RID(sBufferData,
		    oConfig.getProperty("paymentsystem.symbol"),
		    new java.util.Date());
	    m_oTracer.log(this.getClass(),
		    "PS2S_PC_Request2RID ritorna " + ret, ActivityLogger.DEBUG);
	    if (ret == Constant.RESULT_SUCCESS) {
		try {
		    sBufferData = java.net.URLEncoder.encode(
			    osc.PS2S_NetBuffer(), "utf-8");
		    String sPaymentUrl = oConfig
			    .getProperty("paymentsystem.url");
		    return sPaymentUrl + "pagamentoesterno.do?buffer="
			    + sBufferData;
		} catch (Exception ex) {
		    m_oTracer.log(this.getClass(),
			    "encode exception " + ex.toString(),
			    ActivityLogger.ERROR);
		}
	    }
	} catch (Exception exc) {
	    m_oTracer.log(this.getClass(),
		    "PrepareBuffer exception " + exc.toString(),
		    ActivityLogger.ERROR);
	}

	return ("");
    }

    protected String getCssUrl(HttpServletRequest request, String communeId,
	    String baseUrl) {
	MultiCommuneCss multiCommuneCss = new MultiCommuneCss(request);

	String specializedCssUrl = baseUrl + "_" + communeId + ".css";
	if (multiCommuneCss.cssExist(specializedCssUrl))
	    return specializedCssUrl;

	return baseUrl + ".css";
    }

    private String readReturnBuffer(String ReqBuffer) {
	String BufferDatiXML = "";

	try {
	    Properties oConfig = new Properties();

	    oConfig.load(new FileInputStream(propFile));
	    PayServerClient sc = new PayServerClient("CHIAVE",
		    PayServerClient.PS2S_KT_CLEAR);

	    // comunicazione server2server
	    sc.ServerURL(oConfig.getProperty("paymentsystem.S2SUrl")
		    + "PID2Data.jsp");

	    // leggi il buffer
	    int ret = sc.PS2S_PC_PID2Data(ReqBuffer,
		    oConfig.getProperty("paymentsystem.symbol"),
		    new java.util.Date(), WINDOW_MINUTES);

	    switch (ret) {
	    case Constant.RESULT_SUCCESS:
		BufferDatiXML = sc.PS2S_DataBuffer();
		m_iErrorValue = REQOK;
		break;

	    case PayServerClient.pPS2S_COMPERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Fallita Inizializzazione Applicazione (n.3)";
		break;

	    case PayServerClient.pPS2S_DATEERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato -  Impossibile estrarre buffer dati";
		break;

	    case PayServerClient.pPS2S_DATAERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato -  Data non accettabile";
		break;

	    case PayServerClient.pPS2S_HASHERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Fallita Verifica Hash";
		break;

	    case PayServerClient.pPS2S_HASHNOTFOUND:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Impossibile estrarre buffer HASH";
		break;

	    case PayServerClient.pPS2S_CREATEHASHERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Impossibile creare buffer HASH";
		break;

	    case PayServerClient.pPS2S_TIMEELAPSED:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Finestra temporale scaduta";
		break;

	    case PayServerClient.pPS2S_XMLERROR:
		m_iErrorValue = REQFAILED;
		m_sErrorDescr = "Rifiutato - Documento XML non valido: "
			+ ReqBuffer;
		break;
	    }
	} catch (Exception exc) {
	}

	// torna buffer
	return (BufferDatiXML);
    }
}

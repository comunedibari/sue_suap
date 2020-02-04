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
import java.util.Properties;

import it.people.City;
import it.people.layout.multicommune.MultiCommuneCss;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.util.ActivityLogger;
import it.people.util.ServiceParameters;
import it.people.util.payment.adapter.PayERPaymentRequestParameterAdapter;
import it.people.util.payment.request.PaymentRequestParameter;
import it.regulus.osa.constant.Constant;
import it.regulus.osa.secretcode.PayServerClient;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.RedirectingActionForward;

public class PayERPaymentManager extends AbstractPaymentManager implements
	IPaymentStrategy {

    private static final Logger logger = LogManager
	    .getLogger(PayERPaymentManager.class);

    private static final int REQOK = 0;
    private static final int REQFAILED = 1;
    private static final int REQERROR = 2;
    private static final int WINDOW_MINUTES = 10;

    // private static final String PAYER_RIVERSAMENTO_AUTOMATICO =
    // "RiversamentoAutomatico";
    private static final String PAYER_CODICE_UTENTE = "CodiceUtente";
    private static final String PAYER_CODICE_ENTE = "CodiceEnte";
    private static final String PAYER_TIPO_UFFICIO = "TipoUfficio";
    private static final String PAYER_CODICE_UFFICIO = "CodiceUfficio";
    private static final String PAYER_TIPOLOGIA_SERVIZIO = "TipologiaServizio";

    // private static final String
    // PAYER_ENTE_BENEFICIARIO_CODICE_ENTE_PORTALE_ESTERNO =
    // "CodiceEntePortaleEsterno";
    // private static final String
    // PAYER_ENTE_BENEFICIARIO_DESCR_ENTE_PORTALE_ESTERNO =
    // "DescrEntePortaleEsterno";
    // private static final String PAYER_ENTE_BENEFICIARIO_CAUSALE = "Causale";
    // private static final String PAYER_ENTE_BENEFICIARIO_CODICE_ENTE =
    // "CodiceEnteBeneficiario";
    // private static final String PAYER_ENTE_BENEFICIARIO_TIPO_UFFICIO =
    // "TipoUfficioBeneficiario";
    // private static final String PAYER_ENTE_BENEFICIARIO_CODICE_UFFICIO =
    // "CodiceUfficioBeneficiario";
    // private static final String
    // PAYER_ENTE_BENEFICIARIO_RIVERSAMENTO_AUTOMATICO_ONERE =
    // "riversamentoAutomaticoOnere";

    private ActivityLogger m_oTracer = ActivityLogger.getInstance();
    private int m_iErrorValue = REQFAILED;
    private String m_sErrorDescr = "";

    public ActionForward sendToPayment(AbstractPplProcess pplProcess,
	    PaymentRequestParameter paymentRequestParameter, String paymentId,
	    City commune, HttpServletRequest request) {
	AbstractData data = (AbstractData) pplProcess.getData();
	m_oTracer.log(this.getClass(), "SendToPaymentSys con operazione "
		+ paymentId, ActivityLogger.DEBUG);

	String sUrl = "";

	verifyServiceParameters(pplProcess, paymentRequestParameter);

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

	try {
	    sUrl = this.prepareSendBuffer(paymentRequestParameter, paymentId,
	    // FIX 2008-02-27 - Nel caso di accesso come intermediario e
	    // titolare persona giuridica non funziona
	    // Invece del titolare viene usato il richiedente
	    // data.getTitolare().getPersonaFisica().getCodiceFiscale(),
	    // data.getRichiedente().getUtenteAutenticato().getCodiceFiscale(),
		    cercaCodiceFiscale(data), commune.getKey(), request);
	} catch (PaymentException e) {
	    e.printStackTrace();
	    m_oTracer.log(this.getClass(),
		    "SendToPaymentSys: errore metodo prepareSendBuffer" + sUrl,
		    ActivityLogger.ERROR);
	}

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

    /**
     * @param paymentParameter
     * @param sNumOperazione
     * @param sCodFisc
     * @param communeId
     * @param request
     * @return
     * @throws PaymentException
     */
    protected String prepareSendBuffer(
	    PaymentRequestParameter paymentParameter, String sNumOperazione,
	    String sCodFisc, String communeId, HttpServletRequest request)
	    throws PaymentException {
	try {

	    String sBufferData = PayERPaymentRequestParameterAdapter
		    .getBufferData(oConfig, paymentParameter, sNumOperazione,
			    sCodFisc, communeId, request, URL_RITORNO,
			    URL_NOTIFICA, URL_BACK);
	    String sServiceUrl = oConfig.getProperty("service.url");
	    String notificationUrl = oConfig.getProperty("notification.url");

	    boolean newPaymentsystemType = "New".equalsIgnoreCase(oConfig
		    .getProperty("paymentsystem.type")) ? true : false;

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

    private String cercaCodiceFiscale(AbstractData data) {

	String result = data.getRichiedente().getUtenteAutenticato()
		.getCodiceFiscale();

	if (data.getProfiloTitolare() != null) {

	    if (data.getProfiloTitolare().getProfiloTitolarePF() != null) {
		result = data.getProfiloTitolare().getProfiloTitolarePF()
			.getCodiceFiscale();
	    } else if (data.getProfiloTitolare().getProfiloTitolarePG() != null) {
		if (!StringUtils.isEmpty(data.getProfiloTitolare()
			.getProfiloTitolarePG().getCodiceFiscale())) {
		    result = data.getProfiloTitolare().getProfiloTitolarePG()
			    .getCodiceFiscale();
		}
		if (!StringUtils.isEmpty(data.getProfiloTitolare()
			.getProfiloTitolarePG().getPartitaIva())) {
		    result = data.getProfiloTitolare().getProfiloTitolarePG()
			    .getPartitaIva();
		}
	    }
	}

	return result;

    }

    /**
     * <p>
     * Verify if PayER service required data are filled from service
     * <p>
     * If data are not yet filled search if values are set as service
     * parameters. If values are set as service parameters and are not yet
     * filled from service fill data.
     * 
     * @param pplProcess
     * @param paymentRequestParameter
     * @return
     */
    private PaymentRequestParameter verifyServiceParameters(
	    AbstractPplProcess pplProcess,
	    PaymentRequestParameter paymentRequestParameter) {

	PaymentRequestParameter result = paymentRequestParameter;

	try {
	    HttpSession session = pplProcess.getContext().getSession();
	    ServiceParameters serviceParameters = (ServiceParameters) session
		    .getAttribute("serviceParameters");

	    if (serviceParameters != null) {
		String pcpUserCode = serviceParameters
			.get("PAYER_CODICE_UTENTE");
		String pcpCommuneCode = serviceParameters
			.get("PAYER_CODICE_ENTE");
		String pcpOfficeType = serviceParameters
			.get("PAYER_TIPO_UFFICIO");
		String pcpOfficeCode = serviceParameters
			.get("PAYER_CODICE_UFFICIO");
		String pcpServiceType = serviceParameters
			.get("PAYER_TIPOLOGIA_SERVIZIO");

		String svcUserCode = paymentRequestParameter
			.getPaymentManagerSpecificData().get(
				PAYER_CODICE_UTENTE);
		String svcCommuneCode = paymentRequestParameter
			.getPaymentManagerSpecificData().get(PAYER_CODICE_ENTE);
		String svcOfficeType = paymentRequestParameter
			.getPaymentManagerSpecificData()
			.get(PAYER_TIPO_UFFICIO);
		String svcOfficeCode = paymentRequestParameter
			.getPaymentManagerSpecificData().get(
				PAYER_CODICE_UFFICIO);
		String svcServiceType = paymentRequestParameter
			.getPaymentManagerSpecificData().get(
				PAYER_TIPOLOGIA_SERVIZIO);

		if (!nvl(pcpUserCode) && nvl(svcUserCode)) {
		    result.getPaymentManagerSpecificData().put(
			    PAYER_CODICE_UTENTE, pcpUserCode);
		}

		if (!nvl(pcpCommuneCode) && nvl(svcCommuneCode)) {
		    result.getPaymentManagerSpecificData().put(
			    PAYER_CODICE_ENTE, pcpCommuneCode);
		}

		if (!nvl(pcpOfficeType) && nvl(svcOfficeType)) {
		    result.getPaymentManagerSpecificData().put(
			    PAYER_TIPO_UFFICIO, pcpOfficeType);
		}

		if (!nvl(pcpOfficeCode) && nvl(svcOfficeCode)) {
		    result.getPaymentManagerSpecificData().put(
			    PAYER_CODICE_UFFICIO, pcpOfficeCode);
		}

		if (!nvl(pcpServiceType) && nvl(svcServiceType)) {
		    result.getPaymentManagerSpecificData().put(
			    PAYER_TIPOLOGIA_SERVIZIO, pcpServiceType);
		}
	    }

	} catch (Exception e) {
	    m_oTracer
		    .log(pplProcess,
			    "Error while verifyong and adding PayER service specific data.",
			    ActivityLogger.ERROR);
	}

	return result;

    }

    private boolean nvl(String value) {

	return value == null
		|| (value != null && value.trim().equalsIgnoreCase(""));

    }

}

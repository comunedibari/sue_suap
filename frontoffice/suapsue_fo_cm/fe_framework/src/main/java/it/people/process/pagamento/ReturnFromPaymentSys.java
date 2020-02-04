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
package it.people.process.pagamento;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import it.regulus.osa.constant.*;
//import it.people.fsl.servizi.fiscali.pagamenti.oggetticondivisi.*;
import java.io.*;
import java.util.*;
import it.people.util.ActivityLogger;
import it.regulus.osa.secretcode.PayServerClient;

public class ReturnFromPaymentSys extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset = windows-1252";
    private static final String DOC_TYPE = "<!DOCTYPE html  \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
	    + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
    private static final int REQOK = 0;
    private static final int REQFAILED = 1;
    private static final int REQERROR = 2;
    private static final int WINDOW_MINUTES = 10;

    private static final String URL_RESULT = "nextStepProcess.do";

    private int m_iErrorValue = REQFAILED;
    private String m_sErrorDescr = "";
    private String m_sRedirectUrl = "";

    private ActivityLogger m_oTracer = ActivityLogger.getInstance();

    public void init(ServletConfig config) throws ServletException {
	super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	m_oTracer.log(this.getClass(), "ReturnFromPaymentSys INIZIO ",
		ActivityLogger.DEBUG);

	// prepara il buffer di risposta
	String bt = java.net.URLDecoder.decode(request.getParameter("buffer"),
		"utf-8");
	String buffer = ReadBuffer(bt);

	// testa errore
	if (m_iErrorValue == REQOK) {
	    // /!!! crea il tuo oggetto con i dati di ritorno (vedi
	    // documentazione) e rendilo disponibile al servizio
	    // request.getSession().setAttribute("EsitoPagamento", new
	    // EsitoPagamento(buffer));
	    m_oTracer.log(this.getClass(), "ReturnFromPaymentSys redirect to "
		    + m_sRedirectUrl, ActivityLogger.DEBUG);
	    response.sendRedirect(m_sRedirectUrl);
	} else {
	    // /!!! inserire la gestione dell'errore
	}
    }

    //
    // Prepara il buffer da inviare
    //
    String ReadBuffer(String ReqBuffer) {
	String BufferDatiXML = "";

	try {
	    Properties oConfig = new Properties();
	    String sFile = getServletContext().getRealPath(
		    "/WEB-INF/classes/payment.properties");
	    oConfig.load(new FileInputStream(sFile));

	    m_sRedirectUrl = oConfig.getProperty("service.url") + URL_RESULT;
	    // crea
	    PayServerClient sc = new PayServerClient("CHIAVE",
		    PayServerClient.PS2S_KT_CLEAR);

	    // url di navigazione
	    sc.ServerURL(oConfig.getProperty("paymentsystem.url")
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

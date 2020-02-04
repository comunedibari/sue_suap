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

import it.people.util.ActivityLogger;
import it.regulus.osa.constant.*;
import it.regulus.osa.secretcode.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class SendToPaymentSys extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    // /!!!!!! da sostituire con l'action di riciclo sulla stessa pagina
    private static final String URL_BACK = "prevStepProcess.do";

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
	// /!!! da sostituire con l'identificativo univoco dell'operazione
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String sNumOp = "OP" + formatter.format(new Date());
	m_oTracer.log(this.getClass(), "SendToPaymentSys con operazione "
		+ sNumOp, ActivityLogger.DEBUG);

	String sUrl = this.PrepareBuffer(request.getParameter("ServiceId"),
		request.getParameter("Importo"), sNumOp,
		request.getParameter("Email"),
		request.getParameter("CodiceFiscale"));
	m_oTracer.log(this.getClass(), "SendToPaymentSys con url " + sUrl,
		ActivityLogger.DEBUG);
	response.setContentType(CONTENT_TYPE);
	if (sUrl.length() > 0)
	    response.sendRedirect(sUrl);
	else {
	    // /!!! inserire la gestione dell'errore
	}
    }

    private String PrepareBuffer(String sServiceId, String sImporto,
	    String sNumOperazione, String sEmail, String sCodFisc) {
	try {
	    Properties oConfig = new Properties();
	    String sFile = getServletContext().getRealPath(
		    "/WEB-INF/classes/payment.properties");
	    oConfig.load(new FileInputStream(sFile));

	    String sBufferData = "";
	    String sServiceUrl = oConfig.getProperty("service.url");

	    sBufferData = "<PaymentRequest>";
	    sBufferData += "<PortaleID>"
		    + oConfig.getProperty("paymentsystem.portalid")
		    + "</PortaleID>";
	    sBufferData += "<Funzione>PAGAMENTO</Funzione>";
	    sBufferData += "<URLDiRitorno>" + sServiceUrl
		    + "ReturnFromPaymentSys" + "</URLDiRitorno>";
	    sBufferData += "<URLDiErrore>" + sServiceUrl + "Errore.htm"
		    + "</URLDiErrore>";
	    sBufferData += "<URLDiNotifica></URLDiNotifica>";
	    // sBufferData += "<URLHome></URLHome>";
	    sBufferData += "<URLBack>" + sServiceUrl + URL_BACK + "</URLBack>";

	    sBufferData += "<PaymentData>";
	    sBufferData += "<NumeroOperazione>" + sNumOperazione
		    + "</NumeroOperazione>";
	    sBufferData += "<Valuta>EUR</Valuta>";
	    sBufferData += "<Importo>" + sImporto + "</Importo>";
	    sBufferData += "<CalcoloCommissioni>S</CalcoloCommissioni>";
	    sBufferData += "</PaymentData>";

	    sBufferData += "<UserData>";
	    sBufferData += "<EmailUtente>" + sEmail + "</EmailUtente>";
	    sBufferData += "<IdentificativoUtente>" + sCodFisc
		    + "</IdentificativoUtente>";
	    sBufferData += "<UserID></UserID>";
	    sBufferData += "<TokenID></TokenID>";
	    sBufferData += "</UserData>";

	    sBufferData += "<ServiceData>";
	    sBufferData += "<IDServizio>" + sServiceId + "</IDServizio>";
	    sBufferData += "<DatiSpecifici></DatiSpecifici>";
	    sBufferData += "</ServiceData>";

	    sBufferData += "<ServiziStyle>";
	    sBufferData += "<CustomStyle></CustomStyle>";
	    sBufferData += "<URLLogo></URLLogo>";
	    sBufferData += "</ServiziStyle>";
	    sBufferData += "</PaymentRequest>";

	    m_oTracer.log(this.getClass(), "SendToPaymentSys con buffer "
		    + sBufferData, ActivityLogger.DEBUG);

	    // sezione su cig.xml
	    PayServerClient osc;
	    osc = new PayServerClient("CHIAVE", PayServerClient.PS2S_KT_CLEAR);

	    // url di navigazione
	    String sPaymentUrl = oConfig.getProperty("paymentsystem.url");
	    osc.ServerURL(sPaymentUrl + "Request2RID.jsp");
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
}

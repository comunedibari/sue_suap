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
 * Created on 10-mag-2005
 *
 */
package it.people.util.payment;

import it.people.util.ActivityLogger;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class NotificationManager {
    private ActivityLogger tracer = ActivityLogger.getInstance();

    /**
     * 
     * @param request
     * @return il codice di acknowledge, OK - esito positivo, FAIL - esito
     *         negativo
     */
    public String execute(HttpServletRequest request) {
	String realPath = request.getSession().getServletContext()
		.getRealPath("/WEB-INF/payment.properties");
	PaymentManagerContext pm = null;

	String portaleId = "";
	String numeroOperazione = "";
	String idOrdine = "";

	try {
	    pm = new PaymentManagerContext(realPath);
	    portaleId = pm.getPortaleId();

	    EsitoPagamento esitoPagamento = decodeBuffer(request, pm);
	    numeroOperazione = esitoPagamento.getNumeroOperazione();
	    idOrdine = esitoPagamento.getIDOrdine();

	    // Dal MIP si accettano le sole risposte: OK o KO
	    if (!esitoPagamento.getEsito().equals(
		    EsitoPagamento.ES_PAGAMENTO_OK)
		    && !esitoPagamento.getEsito().equals(
			    EsitoPagamento.ES_PAGAMENTO_KO))
		throw new PaymentException(
			"Codice di risposta del MIP non valido.");

	    new PaymentThread(esitoPagamento, pm.getObserverSetting()).start();
	    tracer.log(this.getClass(),
		    "Avviato thread su notifica Pagamento.",
		    ActivityLogger.DEBUG);

	    return pm.getCommitMessage(portaleId, numeroOperazione, idOrdine,
		    true);
	} catch (Exception ex) {
	    tracer.log(this.getClass(),
		    "Errore nell'avvio del thread su notifica Pagamento:\n"
			    + ex.toString(), ActivityLogger.WARN);
	    // Il messaggio non valido � stato accettato ma non verr�
	    // considerato
	    // nelle specifico serve a gestire l'annullamento di un pagamento.
	    return pm.getCommitMessage(portaleId, numeroOperazione, idOrdine,
		    true);
	}
    }

    /**
     * Decodifica il buffer di risposta
     * 
     * @param request
     * @return
     */
    private EsitoPagamento decodeBuffer(HttpServletRequest request,
	    PaymentManagerContext pm) throws PaymentException {
	EsitoPagamento esitoPagamento = pm.getEsitoPagamento(request,
		request.getParameter("buffer"));

	if (esitoPagamento == null)
	    throw new PaymentException(
		    "Risposta non valida dal sistema di pagamento.");

	return esitoPagamento;
    }
}

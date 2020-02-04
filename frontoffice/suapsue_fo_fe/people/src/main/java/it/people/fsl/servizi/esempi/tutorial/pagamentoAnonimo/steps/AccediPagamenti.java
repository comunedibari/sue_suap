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
 * Created on 29-set-2005
 *
 */
package it.people.fsl.servizi.esempi.tutorial.pagamentoAnonimo.steps;

import java.io.IOException;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;

import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.pagamentoAnonimo.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.util.payment.IStartPayment;
import it.people.util.payment.request.PaymentRequestParameter;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 *
 */
public class AccediPagamenti extends Step implements IStartPayment {
	
	private static final String SERVICE_ID = "PTEST";
	private String datiSpecifici = "";
	private Long importo = new Long(0);
	private InternetAddress email = null;

    public PaymentRequestParameter getPaymentRequestParameter() {
        PaymentRequestParameter param = new PaymentRequestParameter();

		/* Indirizzo di e-mail a cui il sistema di pagamento far�
		 * riferimento per l'invio dell'email di notifica di avvenuto pagamento.
		 * Per utilizzare l'indirizzo dell'utente riportato dal sirac ritornare 
		 * la stringa vuota.
		 */
        try {
            param.getUserData().setEmailUtente(email);
		} catch(Exception ex) {}
        		 
		// Identificativo del servizio utilizzato dal MIP
		param.getServiceData().setIdServizio(SERVICE_ID);
		
		/* Dati specifici che saranno registrati nei flussi di 
		 * rendicontazione e serviranno ad identificare il servizio.
		 * Pu� essere usato un qualunque testo anche in formato XML, un
		 * esempio di dati specifici potrebbe essere la serializzazione della
		 * ProcessData del servizio stesso, pu� essere anche un sottinsieme 
		 * pi� limitato di informazioni.
		 */
		param.getServiceData().setDatiSpecifici(this.datiSpecifici);
		
		// Importo da pagare da esprimere in centesimi di euro
		param.getPaymentData().setImporto(this.importo);
		
        return param;
    }
	
	
	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		
		ProcessData data = (ProcessData) process.getData();
		
		this.importo = new Long((new Double((data.getImporto() * 100))).longValue());
		
		try {
			this.email = new InternetAddress(data.getEmail());
		} catch(Exception ex) {
		}
	}
	
	private String CreateDatiSpecifici()
	{
		return "DatiSpecificiDellaPratica";
	}
}

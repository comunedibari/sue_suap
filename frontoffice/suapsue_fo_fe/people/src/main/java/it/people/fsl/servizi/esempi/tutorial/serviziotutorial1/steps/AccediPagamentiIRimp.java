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
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.steps;

import java.io.IOException;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;

import it.people.Step;
import it.people.fsl.servizi.esempi.tutorial.serviziotutorial1.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.util.payment.IRimp;
import it.people.wrappers.IRequestWrapper;

/**
 * @author FabMi
 * @deprecated al suo posto � utilizzato lo step AccediPagamenti
 * che utilizza la nuova interfaccia @see it.people.util.payment.IStartPayment
 */
public class AccediPagamentiIRimp extends Step implements IRimp {
	
	private static final String SERVICE_ID = "PTEST";
	private String datiSpecifici = "";
	private Long importo = new Long(0);
	private String email = "";

	/** 
	 * Ritorna al framework l'identificativo del servizio utilizzato dal MIP
	 * @return la stringa identificativa
	 */
	public String getServiceId() {
		return SERVICE_ID;
	}


	/**
	 * Ritorna l'indirizzo di e-mail a cui il sistema di pagamento far�
	 * riferimento per l'invio dell'email di notifica di avvenuto pagamento.
	 * Per utilizzare l'indirizzo di registrato dall'utente ritornare la stringa vuota
	 */
	public InternetAddress getEmail() {
		try {
			return new InternetAddress(this.email);
		} catch(Exception ex) {}
		return null;
	}

	/**
	 * Ritorna i dati specifici che saranno registrati nei flussi di 
	 * rendicontazione e serviranno ad identificare il servizio.
	 * Pu� essere usato un qualunque testo anche in formato XML, un
	 * esempio di dati specifici potrebbe essere la serializzazione della
	 * ProcessData del servizio stesso, pu� essere anche un sottinsieme 
	 * pi� limitato di informazioni.
	 * @return la stringa contenente i dati da rendicontare nei flussi
	 */
	public String getDatiSpecifici() {
		return this.datiSpecifici;
	}

	/**
	 * Ritorna l'importo da pagare da esprimere in centesimi di euro
	 */
	public Long getImporto() {
		return importo;
	}

	public void service(AbstractPplProcess process, IRequestWrapper request)
			throws IOException, ServletException {
		
		ProcessData data = (ProcessData) process.getData(); 
		
		super.service(process, request);
		this.datiSpecifici = CreateDatiSpecifici();

		this.importo = new Long((new Double((data.getImporto() * 100))).longValue());
		this.email = data.getEmailAddress();
	}
	
	private String CreateDatiSpecifici()
	{
		return "DatiSpecificiDellaPratica";
	}
	
}

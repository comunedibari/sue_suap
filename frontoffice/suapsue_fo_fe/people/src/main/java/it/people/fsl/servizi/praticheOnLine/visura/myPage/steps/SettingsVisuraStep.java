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
package it.people.fsl.servizi.praticheOnLine.visura.myPage.steps;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import it.people.Validator;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.dao.MyPageDAO;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.MyPageException;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili;
import it.people.process.AbstractPplProcess;
import it.people.util.ServiceParameters;
import it.people.wrappers.IRequestWrapper;


public class SettingsVisuraStep extends BaseStep{

	
	public void service(AbstractPplProcess process, IRequestWrapper request) {

		HttpSession session = process.getContext().getSession();
		ServiceParameters serviceParameters = (ServiceParameters) session.getAttribute("serviceParameters");
		
		try {
			if (initialise(request)) {
				logger.debug("SettingsVisuraStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();

                MyPageDAO delegate = new MyPageDAO();
                dataForm.setFailedSendsNumber(delegate.filesFailedSendNumber(process, request));
				
				String numeroPratichePerPaginaStringa = (String)serviceParameters.get("NUMERO_PRATICHE_PER_PAGINA");
				String ordinamentoPraticheInviate = (String)serviceParameters.get("DEFAULT_ORDINAMENTO_PRATICHE_INVIATE");
				String ordinamentoPraticheInCompilazione = (String)serviceParameters.get("DEFAULT_ORDINAMENTO_PRATICHE_IN_COMPILAZIONE");
				
				if (Validator.isInt(numeroPratichePerPaginaStringa) && Integer.parseInt(numeroPratichePerPaginaStringa) > 0) {
					dataForm.setNumPratichePerPag(numeroPratichePerPaginaStringa);
				} else {
					dataForm.setNumPratichePerPag("10");
				}
				
				if (ordinamentoPraticheInviate != null && ordinamentoPraticheInviate.equalsIgnoreCase("crescente")) {
					dataForm.setOrdinamentoPraticheInviate(ProcessData.ORDINAMENTO_CRESCENTE);
				} else {
					dataForm.setOrdinamentoPraticheInviate(ProcessData.ORDINAMENTO_DECRESCENTE);
				}

				if (ordinamentoPraticheInCompilazione != null && ordinamentoPraticheInCompilazione.equalsIgnoreCase("crescente")) {
					dataForm.setOrdinamentoPraticheInCompilazione(ProcessData.ORDINAMENTO_CRESCENTE);
				} else {
					dataForm.setOrdinamentoPraticheInCompilazione(ProcessData.ORDINAMENTO_DECRESCENTE);
				}
				
			} else {
				throw new MyPageException("Sessione scaduta");
			}
			logger.debug("SettingsVisuraStep - service method END");
		} catch (Exception e){
			gestioneEccezioni(process,0,e);
		}
		
	}
	
	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			dataForm.setInCompilazione(true);
			dataForm.setCodicePratica("");
			
			dataForm.setDataCreazioneA("");
			dataForm.setDataCreazioneDa("");
			
			dataForm.setDataUltimaModificaA("");
			dataForm.setDataUltimaModificaDa("");
			
			dataForm.setDataInvioA("");
			dataForm.setDataInvioDa("");
			
			dataForm.setOggetto("");

	        if(propertyName.equalsIgnoreCase("incompilazione"))
	        {
	            dataForm.setTipologiaSelezionata(TipologiaPraticheSelezionabili.inCompilazione.getCodice());
	            dataForm.setInCompilazione(true);
	        } else
	        {
	            if(propertyName.equalsIgnoreCase("completate"))
	                dataForm.setTipologiaSelezionata(TipologiaPraticheSelezionabili.inviate.getCodice());
	            	dataForm.setInCompilazione(false);
	            if(propertyName.equalsIgnoreCase("noninviate")) {
	                dataForm.setTipologiaSelezionata(TipologiaPraticheSelezionabili.erroriInvio.getCodice());
	                dataForm.setInCompilazione(false);
	            }
	        }
			
		} catch (Exception e){
			gestioneEccezioni(process,0,e);
		}
	}
	
}

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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import it.gruppoinit.commons.DBCPManager;
import it.gruppoinit.commons.Utilities;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.dao.BandiDAO;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.BandoBean;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class ManagerBandi {
	
    protected String language = "it"; // la lingua di default è 'it' (italiano)
    protected DBCPManager db;
    

	public ManagerBandi(DBCPManager db2, String language2) {
		this.db = db2;
		this.language = language2;
	}

	public void managerMain(AbstractPplProcess process, IRequestWrapper request) {
		String operazione = request.getParameter("operazione");
		
		String azione = operazione.substring(5, operazione.length());
		if (azione!=null){
			if (azione.equalsIgnoreCase("View")){
				managerView(process,request);
			} else if (azione.equalsIgnoreCase("Edit")){
				managerEdit(process,request);
			} else if (azione.equalsIgnoreCase("AddUtente")){
				managerAddUtente(process,request);
			} else if (azione.equalsIgnoreCase("RemoveUtente")){
				managerRemoveUtente(process,request);
			} else if (azione.equalsIgnoreCase("Update")){
				managerUpdate(process,request);
			}
		}
		
	}
	
	
	private void managerUpdate(AbstractPplProcess process,IRequestWrapper request) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			String cod_servizio = (String) request.getParameter("cod");
			String isBandoString = (String) request.getParameter("flg_bando");
			boolean isBando = it.gruppoinit.commons.Utilities.checked(isBandoString);
			String dallaData = (String) request.getParameter("dallaData");
			String allaData = (String) request.getParameter("allaData");
			BandiDAO delegate = new BandiDAO(db,language);
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    Date dallaDataDate = null; 
		    Date allaDataDate = null; 
		    if (dallaData!=null && !dallaData.equalsIgnoreCase("")){
		    	dallaDataDate = (Date)formatter.parse(dallaData);
		    }
		    if (allaData!=null && !allaData.equalsIgnoreCase("")){
		    	allaDataDate = (Date)formatter.parse(allaData);
		    }
			if (!Utilities.isset(dallaData) || !Utilities.isset(allaData)){
			  	delegate.updateInfoBando(cod_servizio,false,null,null);
			} else {
			  	delegate.updateInfoBando(cod_servizio,isBando,new java.sql.Date(dallaDataDate.getTime()),new java.sql.Date(allaDataDate.getTime())); //o aggiorno o inserisco o rimuovo le info sul bando
			}
			BandoBean bando = delegate.getBookmarkDettaglioPerBandi(dataForm.getComuneSelezionato().getCodEnte(),cod_servizio);
			request.setAttribute("edit", "true");
			if (bando!=null){
				request.setAttribute("bando", bando);
			}
		} catch (Exception e) {
			
		}	
	}

	private void managerRemoveUtente(AbstractPplProcess process, IRequestWrapper request) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			String cod_servizio = (String) request.getParameter("cod");
			String cf = (String) request.getParameter("cf");
			BandiDAO delegate = new BandiDAO(db,language);
			if (cod_servizio!=null && !cod_servizio.equalsIgnoreCase("")){
				delegate.removeUtenteAccessList(cod_servizio,cf);
			}
			BandoBean bando = delegate.getBookmarkDettaglioPerBandi(dataForm.getComuneSelezionato().getCodEnte(),cod_servizio);
			request.setAttribute("edit", "true");
			if (bando!=null){
				request.setAttribute("bando", bando);
			}
		} catch (Exception e) {
			
		}
	}

	private void managerAddUtente(AbstractPplProcess process,IRequestWrapper request) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			String cod_servizio = (String) request.getParameter("cod");
			String newUtente = (String) request.getParameter("newUtente");
			BandiDAO delegate = new BandiDAO(db,language);
			if (newUtente!=null && !newUtente.equalsIgnoreCase("")){
				delegate.addUtenteAccessList(cod_servizio,newUtente);
			}
			BandoBean bando = delegate.getBookmarkDettaglioPerBandi(dataForm.getComuneSelezionato().getCodEnte(),cod_servizio);
			request.setAttribute("edit", "true");
			if (bando!=null){
				request.setAttribute("bando", bando);
			}
		} catch (Exception e) {
			
		}
		
	}

	private void managerView(AbstractPplProcess process, IRequestWrapper request) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			BandiDAO delegate = new BandiDAO(db,language);
			ArrayList lista = delegate.getListaBookmarkPerBandi(dataForm.getComuneSelezionato().getCodEnte());
			request.setAttribute("view", "true");
			if (lista.size()>0){
				request.setAttribute("listaBookmark", lista);
			}
		} catch (Exception e) {
			
		}
	}
	
	
	private void managerEdit(AbstractPplProcess process, IRequestWrapper request) {
		try {
			ProcessData dataForm = (ProcessData) process.getData();
			BandiDAO delegate = new BandiDAO(db,language);
			String cod_servizio = (String) request.getParameter("cod");
			BandoBean bando = delegate.getBookmarkDettaglioPerBandi(dataForm.getComuneSelezionato().getCodEnte(),cod_servizio);
			request.setAttribute("edit", "true");
			if (bando!=null){
				request.setAttribute("bando", bando);
			}
		} catch (Exception e) {
			
		}
	}
}

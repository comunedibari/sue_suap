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
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;

import it.people.backend.client.PeopleException;
import it.people.core.PeopleContext;
import it.people.core.ProcessManager;
import it.people.core.persistence.exception.peopleException;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.dao.MyPageDAO;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.MyPageException;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.TipologiaPraticheSelezionabili;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.util.DataSourcePeopleDB;
import it.people.process.AbstractPplProcess;
import it.people.wrappers.IRequestWrapper;

public class ListaPraticheStep extends BaseStep {


	public void service(AbstractPplProcess process, IRequestWrapper request) {
		try {
			if (initialise(request)) {
				logger.debug("ListaPraticheStep - service method");
				ProcessData dataForm = (ProcessData) process.getData();
				MyPageDAO delegate = new MyPageDAO();
				if (dataForm.isInCompilazione()){
					ArrayList listaPratiche = delegate.getListaPraticheInCompilazione(process,request);
					dataForm.setListaPratiche(listaPratiche);
				} else {
                    if(dataForm.getTipologiaSelezionata().equalsIgnoreCase(TipologiaPraticheSelezionabili.inviate.getCodice()))
                    {
                        ArrayList listaPratiche = delegate.getListaPraticheInviate(process, request);
                        dataForm.setListaPratiche(listaPratiche);
                    }
                    if(dataForm.getTipologiaSelezionata().equalsIgnoreCase(TipologiaPraticheSelezionabili.erroriInvio.getCodice()))
                    {
                        ArrayList listaPratiche = delegate.getListaPraticheErroreInvio(process, request);
                        dataForm.setListaPratiche(listaPratiche);
                    }
				}
				showJsp(process, "listaPratiche.jsp", false);
			} else {
				throw new MyPageException("Sessione scaduta");
			}
			logger.debug("SettingsStep - service method END");
		} catch (Exception e){
			gestioneEccezioni(process,0,e);
		}
		
	}
	
	
	
    public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException, ServletException {
		try {
			logger.debug("ListaPraticheStep - loopBack method");
			logger.debug("propertyName=" + propertyName);
			ProcessData dataForm = (ProcessData) process.getData();
			if (propertyName.equalsIgnoreCase("deletePratica.jsp")){
				Iterator it = dataForm.getListaPratiche().iterator();
				boolean trovato=false;
				while (it.hasNext() && !trovato){
					PraticaBean pratica = (PraticaBean) it.next();
					if (pratica.getOid()!=null && Integer.parseInt(pratica.getOid())==index){
						trovato = true;
						dataForm.setPraticaSelezionata(pratica);
					}
				}
				if (trovato){
					showJsp(process, propertyName, false);
				} else {
					showJsp(process, "praticaError.jsp", false);
				}
	        } else if (propertyName.equalsIgnoreCase("listaPratiche.jsp")){
	        	String confirm = request.getParameter("confirm");
				if (confirm!=null && confirm.equalsIgnoreCase("SI")){
					try {
						ProcessManager.getInstance().delete(PeopleContext.create(request.getUnwrappedRequest()), new Long(dataForm.getPraticaSelezionata().getOid()));
					} catch (Exception e){}
					MyPageDAO delegate = new MyPageDAO();
					if (dataForm.isInCompilazione()){
						ArrayList listaPratiche = delegate.getListaPraticheInCompilazione(process,request);
						dataForm.setListaPratiche(listaPratiche);
					} else {
						ArrayList listaPratiche = delegate.getListaPraticheInviate(process,request);
						dataForm.setListaPratiche(listaPratiche);
					}
				}
	        	showJsp(process, "listaPratiche.jsp", false);
	        } else if (propertyName.equalsIgnoreCase("dettaglioPratiche.jsp")){
	        	dataForm.setOid(index);
	        	dataForm.setDettaglioPratica(null);
	        	goToStep(process, request, 1);
	        	// showJsp(process, "dettaglioPratiche.jsp", false);
	        }
		} catch (Exception e) {
			gestioneEccezioni(process,0,e);
		}
    }
	
}

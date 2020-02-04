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
package it.people.fsl.servizi.admin.sirac.accreditamento.steps;

import it.people.Step;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

public class GestioneOAC1Step extends Step {

  private static Logger logger = LoggerFactory.getLogger(GestioneOAC1Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();
    HttpSession session = request.getUnwrappedRequest().getSession();
    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());

    try {

    	String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
    
    	String IAccreditamentoURLString = 
    		SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
    
    
    	// Imposta il tipo di operazione
    	//myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_GESTIONEOPERATORI);
    
    	//myData.setElencoAccreditamenti(null);

    	IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	    ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
		if (profilo == null) {
			rwh.addSiracError("GestioneOAC1Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
		    updateWorkflow(process);
		    return;
	    }
      
		Accreditamento currAccr = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
		if(ProfiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equals(currAccr.getQualifica().getTipoQualifica()) && "Rappresentante Associazione Categoria".equals(currAccr.getQualifica().getDescrizione())){
			session.setAttribute("accreditamentoRCT", currAccr);
		}
      
		session.setAttribute("prevAccrediramento", currAccr);
      
		Accreditamento[] accrs = accr.getAccreditamenti(codiceFiscaleUtente, idComune);
		if (logger.isDebugEnabled()) {
			logger.debug("CreaDOAC1Step::Service():: Recuperati accreditamenti di " 
                    + codiceFiscaleUtente + " sull'ente " + idComune);
		}

		//Sistema il timestamp della data di creazione
		for(int i=0; i<accrs.length; i++) {
			Accreditamento curAccr = accrs[i];
			ProfiloAccreditamento profiloAccr = curAccr.getProfilo();
			String timestamp = profiloAccr.getTimestampAutoCert();
			profiloAccr.setTimestampAutoCert(parseTimestamp(timestamp));
		}
      
		Accreditamento[] accrRCT = filterAccreditamentiByDescription(accrs, "Rappresentante Associazione Categoria");
      
		// Memorizza l'elenco degli accreditamenti nel ProcessData
		myData.setElencoAccreditamenti(accrRCT);
      
		request.setAttribute("accreditamenti", accrRCT);

		request.setAttribute("accreditamentoRCTEsistente", accrRCT.length > 0);
		

    
    } catch (Exception ex) {
      rwh.addSiracError("GestioneOAC1Step::Service()::Errore", ex.getMessage());
    } finally {
      updateWorkflow(process);
    }
  }
  
  private Accreditamento[] filterAccreditamentiByDescription(Accreditamento[] accrs, String descrizioneQualifica){
	  List tempAccrList = new ArrayList();
	  for(int i=0; i < accrs.length; i++){
		  if(ProfiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equals(accrs[i].getQualifica().getTipoQualifica()) && "Rappresentante Associazione Categoria".equals(accrs[i].getQualifica().getDescrizione())){
			  tempAccrList.add(accrs[i]);
		  }
	  }
	  return (Accreditamento[])tempAccrList.toArray(new Accreditamento[0]); 
  }
  
  private String parseTimestamp(String _value) {
	    String result = null;
	    
	    try {
	        long time = Long.parseLong(_value);
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(time);
	        SimpleDateFormat fmt = new SimpleDateFormat();
	        result = fmt.format(cal.getTime());
	    } catch (NumberFormatException e) {
	        result = "";
	    }
	    return result;
  }
  
  public void updateWorkflow(AbstractPplProcess process) {
    ProcessData myData = (ProcessData) process.getData();
    
    if(myData.getElencoAccreditamenti().length == 0){
      process.getView().setBottomNavigationBarEnabled(false);
    } else {
      process.getView().setBottomNavigationBarEnabled(true);
    }
  }

}

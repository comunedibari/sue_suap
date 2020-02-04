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

import it.people.Activity;
import it.people.ActivityState;
import it.people.IStep;
import it.people.Step;
import it.people.StepState;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.authentication.beans.PplUserDataExtended;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SelAccr4Step extends Step {

  private static Logger logger = LoggerFactory.getLogger(SelAccr4Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
  
	super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null) {
    	myData.setPplUser(request.getPplUser());
    }

    try {
		//  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
		String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    
	    //    ServiceParameters sp = (ServiceParameters) request
	    //    .getSessionAttribute("serviceParameters");
	
	    //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");
	
	    String IAccreditamentoURLString = SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	    // Imposta il tipo di operazione
	    //myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_SELACCR);
	
	      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
	          IAccreditamentoURLString);
	      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
		  if (profilo == null) {
			  rwh.addSiracError("SelAccr4Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
			  updateWorkflow(process);
			  return;
		  }
	      
	      HttpServletRequest unwrappedRequest = request.getUnwrappedRequest();
	      HttpSession session = unwrappedRequest.getSession();
	      
	      int accrSelIndex = myData.getSelezioneAccreditamentoIndex();
	      
	      Accreditamento accrSelezionato = null;
	      
	      // Verifica se richiesto annullamento selezione accreditamento e ripristino 
	      // profilo utente
	      if (accrSelIndex!=myData.getAnnullaSelezioneAccreditamentoIndexValue()) {
	        accrSelezionato = myData.getElencoAccreditamenti()[accrSelIndex];
	      } else {
	        // Richiesto annullamento selezione accreditamento e ripristino 
	        // profilo utente 
	        ProfiloPersonaFisica profiloOperatore = myData.getSelaccrOperatore();

	        accrSelezionato =
	          ProfiliHelper.getAccreditamentoUtentePeopleRegistrato(profiloOperatore.getCodiceFiscale(), idComune);

	      }
	      
	      // Aggiorna gli attributi di sessione con i profili selezionati di operatore, richiedente, titolare
	      session.setAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL, accrSelezionato);
	      session.setAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, accrSelezionato.getQualifica().getTipoQualifica());
	      session.setAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, accrSelezionato.getQualifica().getDescrizione());
	
//	      session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, myData.getSelaccrOperatore());
	      if("OAC".equals(accrSelezionato.getQualifica().getIdQualifica()) || "RCT".equals(accrSelezionato.getQualifica().getIdQualifica())){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, new ProfiloPersonaFisica());
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, new ProfiloPersonaGiuridica());
	    	  PplUserDataExtended userdataNew = new PplUserDataExtended();
	    	  userdataNew.setNome("Operatore");
	    	  userdataNew.setCognome(accrSelezionato.getProfilo().getDenominazione());
	    	  userdataNew.setDataNascita("01/01/1970");
	    	  userdataNew.setCapDomicilio("");
	    	  userdataNew.setCapNascita("");
	      	  userdataNew.setCapResidenza("");
	    	  userdataNew.setCellulare("");
	       	  userdataNew.setCittaDomicilio("");
	    	  userdataNew.setCittaResidenza("");
	    	  userdataNew.setIdComuneRegistrazione("");
	    	  userdataNew.setIndirizzoDomicilio("");
	    	  userdataNew.setIndirizzoResidenza("");
	    	  userdataNew.setLavoro("");
	    	  userdataNew.setLuogoNascita("");
	    	  userdataNew.setProvinciaDomicilio("");
	    	  userdataNew.setProvinciaNascita("");
	    	  userdataNew.setSesso("M");
	    	  userdataNew.setStatoDomicilio("");
	    	  userdataNew.setStatoNascita("");
	    	  userdataNew.setStatoResidenza("");
	    	  userdataNew.setTelefono("");
	    	  userdataNew.setTitolo("");
	    	  userdataNew.setUserPassword("");
	    	  userdataNew.setUserPIN("");
	    	  userdataNew.setEmailaddress(accrSelezionato.getProfilo().getDomicilioElettronico());
	    	  userdataNew.setCodiceFiscale(accrSelezionato.getProfilo().getCodiceFiscaleIntermediario());
		      PplUserDataExtended userdataBackup = (PplUserDataExtended)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA);
		      String userBackup = (String)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER);
		      if(userdataBackup==null && userBackup==null){
		    	  session.setAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER, (String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER));
		    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER, accrSelezionato.getProfilo().getCodiceFiscaleIntermediario());
		    	  session.setAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA, (PplUserDataExtended)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA));
		    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA, userdataNew);
		    	  session.removeAttribute("currentLoggedUser");
		      }
	      } else {
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, myData.getSelaccrRichiedente());
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, myData.getSelaccrTitolare());

	    	  if (myData.getSelaccrTitolare() instanceof ProfiloPersonaGiuridica) {
		    	  if (((ProfiloPersonaGiuridica) myData.getSelaccrTitolare()).getRappresentanteLegale().getDataNascita() == null) {
		    		  ((ProfiloPersonaGiuridica) myData.getSelaccrTitolare()).getRappresentanteLegale().setDataNascita(Utilities.parseDateString("01/01/1970"));
		    	  }
		    	  if (((ProfiloPersonaGiuridica) myData.getSelaccrTitolare()).getRappresentanteLegale().getDataNascitaString() == null) {
		    		  ((ProfiloPersonaGiuridica) myData.getSelaccrTitolare()).getRappresentanteLegale().setDataNascitaString("01/01/1970");
		    	  }
	    	  }
	    	  
		      PplUserDataExtended userdataBackup = (PplUserDataExtended)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA);
		      String userBackup = (String)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER);
		      if(userdataBackup!=null && userBackup!=null){
		    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA, userdataBackup);
		    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER, userBackup);
		    	  session.removeAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER);
		    	  session.removeAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA);
		    	  session.removeAttribute("currentLoggedUser");
		      }
	      }
	      

    } catch (Exception ex) {
      rwh.addSiracError("SelAccr4Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
      
    }

  }
  

  
  
  public void updateWorkflow(AbstractPplProcess process) {
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    int currentActivityIndex = process.getView().getCurrentActivityIndex();
    IStep currentStep = currentActivity.getCurrentStep();
    String currentStepId = currentStep.getId();
    int currentStepIndex = currentActivity.getCurrentStepIndex();
    String currentActivityName = currentActivity.getName();
    
    process.getView().setBottomNavigationBarEnabled(false);
    
    currentStep.setState(StepState.COMPLETED);
    currentActivity.setState(ActivityState.COMPLETED);
    
  }

}

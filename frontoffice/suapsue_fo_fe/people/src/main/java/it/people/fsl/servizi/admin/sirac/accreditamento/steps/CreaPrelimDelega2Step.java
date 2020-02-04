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
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreaPrelimDelega2Step extends Step {

  private static Logger logger = LoggerFactory.getLogger(CreaPrelimDelega2Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    HttpServletRequest unwrappedRequest = request.getUnwrappedRequest();
    HttpSession session = unwrappedRequest.getSession();
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());

    ProfiliHelper profiliHelper= null;
    
    try {

		//  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
		String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    
	    String IAccreditamentoURLString = 
	      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	    
	    Accreditamento[] elencoAccreditamenti = myData.getElencoAccreditamenti();
	    
	    Accreditamento accrSelezionato = elencoAccreditamenti[myData.getSelezioneAccreditamentoIndex()];
	    
	    String tipoQualificaAccrSel = accrSelezionato.getQualifica().getTipoQualifica();
	    
	    Qualifica2Persona[] elencoQualifiche2Persona = null;
	
	    IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
        ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	    if (profilo == null) {
	    	rwh.addSiracError("CreaPrelimDelega2Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	        updateWorkflow(process);
	        return;
	    }
	      
	      elencoQualifiche2Persona = accr.getQualifiche2Persona(tipoQualificaAccrSel);
	      myData.setElencoQualifiche2Persona(elencoQualifiche2Persona);
	      
	      PplUserData authUserData = myData.getPplUser().getUserData();
	      
	      ProfiloPersonaFisica profiloOperatore = new ProfiloPersonaFisica();
	      profiloOperatore.setNome(authUserData.getNome());
	      profiloOperatore.setCognome(authUserData.getCognome());
	      profiloOperatore.setCodiceFiscale(authUserData.getCodiceFiscale());
	      profiloOperatore.setDataNascita(Utilities.parseDateString(authUserData.getDataNascita()));
	      profiloOperatore.setLuogoNascita(authUserData.getLuogoNascita());
	      profiloOperatore.setProvinciaNascita(authUserData.getProvinciaNascita());
	      profiloOperatore.setSesso(authUserData.getSesso());
	      profiloOperatore.setIndirizzoResidenza(authUserData.getCittaResidenza() 
	          + "(" + authUserData.getProvinciaResidenza() + "), " + authUserData.getIndirizzoResidenza());
	      // FIX 2007-06-13 - Aggiunto email al ProfiloPersonaFisica, quindi vado a popolarlo
	      profiloOperatore.setDomicilioElettronico(authUserData.getEmailaddress());
	      
	      myData.setSelaccrOperatore(profiloOperatore);
	      
	      profiliHelper = 
	        new ProfiliHelper(elencoQualifiche2Persona, accrSelezionato, profiloOperatore);
	      
	      profiliHelper.setProfiloRichiedente(new ProfiloPersonaFisica());
	      
	      if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE)) {
	        ProfiliHelper.initProfiloPersonaFisica(myData.getSelaccrRichiedente(), profiloOperatore);
	        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
	        profiliHelper.setProfiloRichiedenteDefined(true);
	      } else if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) {
	        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
	        profiliHelper.setProfiloRichiedenteDefined(false);
	      }
	
	      myData.setSelAccrProfiliHelper(profiliHelper);
	      
	      // imposta il titolare provvisoriamente uguale al richiedente/operatore
	      myData.setSelaccrTitolare(myData.getSelaccrRichiedente());
	      profiliHelper.setProfiloTitolare(myData.getSelaccrRichiedente());
	      profiliHelper.setProfiloTitolareDefined(true);  
	      profiliHelper.setSceltaProfiloTitolare(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA);
	      
	      // imposta le variabili di sessioni del SIRAC con l'accreditamento selezionato
	      session.setAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL, accrSelezionato);
	      session.setAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, accrSelezionato.getQualifica().getTipoQualifica());
	      session.setAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, accrSelezionato.getQualifica().getDescrizione());
	
	      session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, myData.getSelaccrOperatore());
	      session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, myData.getSelaccrRichiedente());
	      session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, myData.getSelaccrTitolare());
      
    } catch (Exception ex) {
      rwh.addSiracError("CreaPrelimDelega2Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      request.setAttribute("profiliHelper", profiliHelper);
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

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
import it.people.IActivity;
import it.people.IStep;
import it.people.IValidationErrors;
import it.people.Step;
import it.people.StepState;
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.authentication.beans.PplUserDataExtended;
import it.people.sirac.authz.AuthorizationContextBean;
import it.people.sirac.authz.AuthorizationManager;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.error.ErrorMessagesBean;
import it.people.sirac.error.SiracAuthorizationException;
import it.people.sirac.filters.SiracAuthorizationFilter;
import it.people.sirac.serviceProfile.ServiceProfile;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.GroupsAccreditations;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
public class SelAccr1Step extends Step {

	private static Logger logger = LoggerFactory.getLogger(SelAccr1Step.class.getName());

	public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
    
	super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());

    try {

    	String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
    
    	String IAccreditamentoURLString = 
    		SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
    
    	// Imposta il tipo di operazione
    	myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_SELACCR);
    
    	myData.setElencoAccreditamenti(null);

    	IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	    ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	    if (profilo == null) {
	    	rwh.addSiracError("SelAccr1Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	    	updateWorkflow(process);
	    	return;
	    }
      
	    Accreditamento[] accrs = accr.getAccreditamenti(codiceFiscaleUtente, idComune);
	    if (logger.isDebugEnabled()) {
	    	logger.debug("SelAccr1Step::Service():: Recuperati accreditamenti di " 
                    	+ codiceFiscaleUtente + " sul comune " + idComune);
	    }

	    HttpSession session = request.getUnwrappedRequest().getSession();
    	PplUserData userData = (PplUserData)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
    	Qualifica qualifica = accr.getQualificaById(userData.getRuolo());
    	userData.setRuoloAbilitato(qualifica != null);
    	if (userData.isRuoloDefinito() && userData.isRuoloAbilitato()) {
    		accrs = GroupsAccreditations.addAccreditation(userData, accrs, qualifica, idComune);
    	}
	    
	    //Sistema il timestamp della data di creazione
	    for(int i=0; i<accrs.length; i++) {
	    	Accreditamento curAccr = accrs[i];
	    	ProfiloAccreditamento profiloAccr = curAccr.getProfilo();
	    	String timestamp = profiloAccr.getTimestampAutoCert();
	    	profiloAccr.setTimestampAutoCert(parseTimestamp(timestamp));
	    }
      
	    // Memorizza l'elenco degli accreditamenti nel ProcessData
	    myData.setElencoAccreditamenti(accrs);
      
	    request.setAttribute("accreditamenti", accrs);
    
    } catch (Exception ex) {
      rwh.addSiracError("SelAccr1Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
    }
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
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    int currentActivityIndex = process.getView().getCurrentActivityIndex();
    IStep currentStep = currentActivity.getCurrentStep();
    String currentStepId = currentStep.getId();
    int currentStepIndex = currentActivity.getCurrentStepIndex();
    String currentActivityName = currentActivity.getName();

    ProcessData myData = (ProcessData) process.getData();
    
    IActivity firstActivity = process.getView().getActivityById("0");

    IStep lastStep = currentActivity.getStepById("4");
    if (lastStep.getState().equals(StepState.COMPLETED)) {
      currentActivity.setState(ActivityState.ACTIVE);
      lastStep.setState(StepState.ACTIVE);
    }
    if (myData.getElencoAccreditamenti()==null || myData.getElencoAccreditamenti().length==0) { 
      process.getView().setBottomNavigationBarEnabled(false);
    } else {
      process.getView().setBottomNavigationBarEnabled(true);
    }
    
    //currentActivity.setState(ActivityState.COMPLETED)
    for (int i=0; i< activities.length; i++) {
      String activityName = activities[i].getName(); 
      if (!activityName.equals(firstActivity.getName()) && !activityName.equals(currentActivityName)) {
        activities[i].setState(ActivityState.INACTIVE);
      }
      activities[i].setCurrentStepIndex(0);
    }
    
    currentActivity.setStepOrder("1,2,3,4");

  }

  
  /* (non-Javadoc)
   * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
   */
/*  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    boolean correct= false;
    ProcessData myData = (ProcessData) process.getData();
    errors.add("error.genericError");
    return correct;
  }
*/

  /* (non-Javadoc)
   * @see it.people.IStep#defineControl(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
   */
  public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
    // TODO Auto-generated method stub
    //super.defineControl(process, request);
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    int currentActivityIndex = process.getView().getCurrentActivityIndex();
    IStep currentStep = currentActivity.getCurrentStep();
    String currentStepId = currentStep.getId();
    int currentStepIndex = currentActivity.getCurrentStepIndex();
    String currentActivityName = currentActivity.getName();

    ProcessData myData = (ProcessData) process.getData();
    
    String idComune = process.getCommune().getOid();

    IActivity firstActivity = process.getView().getActivityById("0");

    IStep lastStep = currentActivity.getStepById("4");

    int accrSelIndex = myData.getSelezioneAccreditamentoIndex();
    if (accrSelIndex==myData.getAnnullaSelezioneAccreditamentoIndexValue()) {
      // Richiesto annullamento selezione accreditamento
      HttpServletRequest unwrappedRequest = request.getUnwrappedRequest();
      HttpSession session = unwrappedRequest.getSession();
      
      ProfiloPersonaFisica profiloOperatore = (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);

      Accreditamento accrSelezionato =
        ProfiliHelper.getAccreditamentoUtentePeopleRegistrato(
            profiloOperatore.getCodiceFiscale(), 
            idComune);
      
      myData.setSelaccrOperatore(profiloOperatore);
      myData.setSelaccrRichiedente(profiloOperatore);
      myData.setSelaccrTitolare(profiloOperatore);

      PplUserDataExtended userdata_backup = (PplUserDataExtended)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA);
      String user_backup = (String)session.getAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER);
      if((userdata_backup != null) && (user_backup != null)) {
    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA, userdata_backup);
    	  session.setAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER, user_backup);
    	  session.removeAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USERDATA);
    	  session.removeAttribute(UtilHelper.SIRAC_BACKUP_AUTHENTICATED_USER);
    	  session.removeAttribute("currentLoggedUser");
      }
      
      ProfiliHelper profiliHelper=null;
      try {
        profiliHelper = new ProfiliHelper(null, accrSelezionato, profiloOperatore);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      profiliHelper.setSceltaProfiloRichiedente(ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE);
      profiliHelper.setProfiloRichiedente(profiloOperatore);
      profiliHelper.setProfiloRichiedenteDefined(true);
      profiliHelper.setSceltaProfiloTitolare(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA);
      profiliHelper.setProfiloTitolare(profiloOperatore);
      profiliHelper.setProfiloTitolareDefined(true);
      
      myData.setSelAccrProfiliHelper(profiliHelper);
      
      session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, profiloOperatore);
      session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, profiloOperatore);
      session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, profiloOperatore);
      
      currentActivity.setCurrentStepIndex(2);

    } else {
        Accreditamento accrSel = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
   	    if("OAC".equals(accrSel.getQualifica().getIdQualifica()) || "RCT".equals(accrSel.getQualifica().getIdQualifica())) {
    	    	currentActivity.setCurrentStepIndex(2);
    	}
    }
  }
}

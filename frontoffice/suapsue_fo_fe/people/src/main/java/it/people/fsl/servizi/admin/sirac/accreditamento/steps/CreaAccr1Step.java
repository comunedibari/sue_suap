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
import it.people.Step;
import it.people.StepState;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreaAccr1Step extends Step {

	private static Logger logger = LoggerFactory.getLogger(CreaAccr1Step.class.getName());
  
  

  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();

    try {

      String idComune = process.getCommune().getOid();
      
      if(myData.getPplUser()==null)
        myData.setPplUser(request.getPplUser());

//      String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
      String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
      
      
//      ServiceParameters sp = (ServiceParameters) request
//          .getSessionAttribute("serviceParameters");

//      String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");
      
      String IAccreditamentoURLString = 
        SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
      
      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
      if (profilo == null) {
        rwh.addSiracError("CreaAccr1Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
        updateWorkflow(process);
        return;
      }
      Qualifica[] qualificheAccreditabili = null;
        qualificheAccreditabili = 
                  accr.getQualificheAccreditabili(codiceFiscaleUtente, idComune);
        if (logger.isDebugEnabled()) {
          logger.debug("CreaAccr1Step::Service():: Recuperate qualifiche accreditabili ");
        }
      request.setAttribute("qualificheAccreditabili", qualificheAccreditabili);
      /* 
       * Viene creato e inizializzato nel processData
      AccrIntrmForm accrIntrmForm = new AccrIntrmForm();
      accrIntrmForm.clear();
      myData.setAccrIntrmForm(accrIntrmForm);
      */
      // Imposta il tipo di operazione
      myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_CREAACCR);

    } catch (Exception ex) {
      rwh.addSiracError("CreaAccr1Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      AccrIntrmForm accrIntrmForm = myData.getAccrIntrmForm();
      // FIX 2007-06-28
      // aggiunta direttiva clear() per ripulire la form ad ogni ingresso 
      // nel servizio. Altrimenti scegliendo una qualifica di professionista
      // la form � parzialmente precompilata e tornando indietro e cambiando
      // qualifica (es. intermediario) vengono mantenuti dati scorretti come
      // il codice fiscale di una persona fisica
      accrIntrmForm.clear();
      request.setAttribute("accrIntrmForm", accrIntrmForm);
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
    
    IActivity firstActivity = process.getView().getActivityById("0");
    
    IStep lastStep = currentActivity.getStepById("4");
    if (lastStep.getState().equals(StepState.COMPLETED)) {
      currentActivity.setState(ActivityState.ACTIVE);
      lastStep.setState(StepState.ACTIVE);
      ProcessData myData = (ProcessData) process.getData();
      myData.getAccrIntrmForm().clear();
    }
    process.getView().setBottomNavigationBarEnabled(true);
    //currentActivity.setState(ActivityState.COMPLETED)
    for (int i=0; i< activities.length; i++) {
      String activityName = activities[i].getName(); 
      if (!activityName.equals(firstActivity.getName()) 
          && !activityName.equals(currentActivityName)) {
        activities[i].setState(ActivityState.INACTIVE);
      }
      activities[i].setCurrentStepIndex(0);
    }
      
    
  }

}

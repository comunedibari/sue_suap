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
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;

import javax.servlet.ServletException;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProfiloLocaleStep extends Step {
  /**
   * Logger for this class
   */
  //  private static final Logger logger = Logger
  //      .getLogger(ProfiloLocaleStep.class);
  private static Logger logger = LoggerFactory.getLogger(ProfiloLocaleStep.class
      .getName());

  /*
   * (non-Javadoc)
   * 
   * @see it.people.IStep#service(it.people.process.AbstractPplProcess,
   *      it.people.wrappers.IRequestWrapper)
   */
  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    // TODO Auto-generated method stub
    super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    try {
      ProcessData myData = (ProcessData) process.getData();

      String comuneID = process.getCommune().getOid();
      myData.setPplUser(request.getPplUser());

      String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
      
      //    ServiceParameters sp = (ServiceParameters) request
      //    .getSessionAttribute("serviceParameters");

      //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");

      String IAccreditamentoURLString = 
        SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
      
      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
          IAccreditamentoURLString);

      ProfiloLocale profilo = accr.getProfiloLocale(codiceFiscaleUtente,
          comuneID);

      if (logger.isDebugEnabled()) {
        logger.debug("service() - Recuperato profilo locale:  : profilo = " + profilo);
      }

      myData.setProfiloLocale(profilo);


      if (profilo == null) {
        rwh.addSiracError("ProfiloLocaleStep::Errore1",
            "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
        ;
        //process.getValidationErrors().add("error.genericError");
      }
      updateWorkflow(process);

    } catch (Exception ex) {
      //process.getValidationErrors().add("error.genericError");
      rwh.addSiracError("ProfiloLocaleStep::Eccezione1", ex.getMessage());
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
    
    process.getView().setBottomNavigationBarEnabled(false);
    
    currentActivity.setState(ActivityState.COMPLETED);
    currentActivity.setCurrentStepIndex(0);
    for (int i=0; i< activities.length; i++) {
      String activityName = activities[i].getName(); 
      if (!activityName.equals(currentActivityName) 
          && !activityName.equals(firstActivity.getName())) {
        activities[i].setState(ActivityState.INACTIVE);
      }
      activities[i].setCurrentStepIndex(0);
    }
  }

}

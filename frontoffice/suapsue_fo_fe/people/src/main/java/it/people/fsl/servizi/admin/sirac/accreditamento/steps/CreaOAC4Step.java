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
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.Delega;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.web.forms.DelegaForm;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class CreaOAC4Step extends Step {

  private static Logger logger = LoggerFactory.getLogger(CreaOAC4Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
    
	super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    IAccreditamentoClientAdapter wsAccr = null;
    DelegaForm delegaForm = null;
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());

    try {

    	//  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
    	String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());

    
	    //    ServiceParameters sp = (ServiceParameters) request
	    //    .getSessionAttribute("serviceParameters");
	
	    //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");
	
	    String IAccreditamentoURLString = 
	      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	    
	    // Imposta il tipo di operazione
	    myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_CREADELEGA);
	    
	    //myData.setElencoAccreditamenti(null);
	
	    delegaForm = myData.getDelegaForm();
	    
	    PplUserData userData = myData.getPplUser().getUserData();
	
	    Activity currentActivity = process.getView().getCurrentActivity();
	    int currentActivityIndex = process.getView().getCurrentActivityIndex();
	    IStep currentStep = currentActivity.getCurrentStep();
	    String currentStepId = currentStep.getId();
	    int currentStepIndex = currentActivity.getCurrentStepIndex();
	    String currentActivityName = currentActivity.getName();
	    
	    if (currentStep.getState().equals(StepState.COMPLETED)) {
	      request.setAttribute("delegaForm", delegaForm);
	      updateWorkflow(process);
	      return;
	    }
	    // Lo step corrente non � ancora stato completato (e la delega non � stata creata)

	      wsAccr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, wsAccr, codiceFiscaleUtente, idComune);
	      if (profilo == null) {
	    	  rwh.addSiracError("CreaOAC4Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	    	  updateWorkflow(process);
	    	  return;
	      }
	      
	      HttpServletRequest unwrappedReq = request.getUnwrappedRequest();
	      HttpSession session = unwrappedReq.getSession();
	      Accreditamento curAccr = (Accreditamento) session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
	      
	      ProfiloPersonaFisica operatore = (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);
	
	      Delega delega = new Delega();
	      
	      delega.setNome(delegaForm.getNome());
	      delega.setCognome(delegaForm.getCognome());
	      delega.setIdQualifica(delegaForm.getIdQualifica());
	      delega.setCodiceFiscaleDelegato(delegaForm.getCodiceFiscaleDelegato());
	      delega.setCodiceFiscaleDelegante(operatore.getCodiceFiscale());
	      delega.setCertificazione(delegaForm.getCertificazione().getBytes(SiracConstants.DEFAULT_CHARSET));
	      delega.setIdAccreditamento(curAccr.getId());
	      delega.setTimestampCertificazione(String.valueOf(System.currentTimeMillis()));
	      
	      Date now = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS");
	      
	      String cert_filename = "certdelega_" + codiceFiscaleUtente + "_" + sdf.format(now) + ".pdf.p7m";
	      
	      delega.setCertificazioneFilename(cert_filename);
	      
    	  wsAccr.creaDelega(delega);
	      
	      // ripristina i valori di accreditamento iniziali
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_ACCRSEL) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_ACCRSEL));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_ACCRSEL);
	      }
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TIPOQUALIFICA) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TIPOQUALIFICA));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TIPOQUALIFICA);
	      }
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_DESCRQUALIFICA) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_DESCRQUALIFICA));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_DESCRQUALIFICA);
	      }
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_OPERATORE) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_OPERATORE));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_OPERATORE);
	      }
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_RICHIEDENTE) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_RICHIEDENTE));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_RICHIEDENTE);
	      }
	      if(session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TITOLARE) != null){
	    	  session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, session.getAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TITOLARE));
	    	  session.removeAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TITOLARE);
	      }
      
    } catch (Exception ex) {
      rwh.addSiracError("CreaOAC4Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
      request.setAttribute("delegaForm", delegaForm);
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

    ProcessData myData = (ProcessData) process.getData();

    process.getView().setBottomNavigationBarEnabled(false);
    
    currentStep.setState(StepState.COMPLETED);
    currentActivity.setState(ActivityState.COMPLETED);

    //myData.setShowActivityMenu(false);
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

}

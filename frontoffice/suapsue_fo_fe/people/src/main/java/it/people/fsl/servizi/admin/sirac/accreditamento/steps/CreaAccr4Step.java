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
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.RappresentanteLegale;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.sirac.web.forms.RapprLegaleForm;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreaAccr4Step extends Step {
  /**
   * Logger for this class
   */
  //  private static final Logger logger = Logger
  //      .getLogger(ProfiloLocaleStep.class);
  private static Logger logger = LoggerFactory.getLogger(CreaAccr4Step.class.getName());

  private HttpServletRequestDelegateWrapperHelper rwh = null;
  private AbstractPplProcess process = null;
  
  /*
   * (non-Javadoc)
   * 
   * @see it.people.IStep#service(it.people.process.AbstractPplProcess,
   *      it.people.wrappers.IRequestWrapper)
   */
  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {

  	  this.process = process;
      super.service(process, request);
      rwh = new HttpServletRequestDelegateWrapperHelper(request);

      AccrIntrmForm accrIntrmForm = null;
    
      try {
	      ProcessData myData = (ProcessData) process.getData();
	
	      String idComune = process.getCommune().getOid();
	    
	      if(myData.getPplUser()==null)
	        myData.setPplUser(request.getPplUser());
	
	      //  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
	      String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    
	      String IAccreditamentoURLString = 
	        SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	      accrIntrmForm = myData.getAccrIntrmForm();
	    
	      Activity currentActivity = process.getView().getCurrentActivity();
	      IStep currentStep = currentActivity.getCurrentStep();
	    
	      if (currentStep.getState().equals(StepState.COMPLETED)) {
	        request.setAttribute("accrIntrmForm", accrIntrmForm);
	        updateWorkflow(process);
	        return;
	      }
	      // Lo step corrente non � ancora stato completato (e l'accreditamento non � stato creato)
	      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
	          IAccreditamentoURLString);
	      
	      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	      if (profilo == null) {
	        rwh.addSiracError("CreaAccr4Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	        updateWorkflow(process);
	        return;
	      }
	      
	      ProfiloAccreditamento pa = new ProfiloAccreditamento();
	      pa.setCodiceFiscaleIntermediario(accrIntrmForm.getCodiceFiscaleIntermediario());
	      pa.setPartitaIvaIntermediario(accrIntrmForm.getPartitaIvaIntermediario());
	      pa.setDescrizione(accrIntrmForm.getDescrizione());
	      pa.setDenominazione(accrIntrmForm.getDenominazione());
	      pa.setSedeLegale(accrIntrmForm.getSedeLegale());
	      pa.setDomicilioElettronico(accrIntrmForm.getDomicilioElettronico());
	      pa.setAutoCert(accrIntrmForm.getAutoCert().getBytes(SiracConstants.DEFAULT_CHARSET));
	     
	      String timeStamp = String.valueOf(System.currentTimeMillis());
	      Date now = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss_SSS");
	      
	      pa.setTimestampAutoCert(timeStamp);
	      
	      String accreditamento_filename = "autocertaccr_" + codiceFiscaleUtente + "_" + sdf.format(now) + ".pdf.p7m";
	      
	      pa.setAutoCertFilename(accreditamento_filename);     
	      
	
	      RapprLegaleForm rlForm = accrIntrmForm.getRapprLegaleForm();
	      RappresentanteLegale rl = null;
	
	      if (accrIntrmForm.getHasRappresentanteLegale() && rlForm != null) {
	        rl = new RappresentanteLegale();
	        rl.setCodiceFiscale(rlForm.getCodiceFiscale());
	        rl.setNome(rlForm.getNome());
	        rl.setCognome(rlForm.getCognome());
	        Date dataNascitaRL = null;
	        try {
	          dataNascitaRL = Utilities.parseDateString(rlForm.getDataNascita());
	        } catch (Exception e) {
	          
	        }
	        rl.setDataNascita(dataNascitaRL);
	        rl.setLuogoNascita(rlForm.getLuogoNascita());
	        rl.setProvinciaNascita(rlForm.getProvinciaNascita());
	        rl.setIndirizzoResidenza(rlForm.getIndirizzoResidenza());
	        rl.setSesso(rlForm.getSesso());
	        
	        rl.setCodiceFiscaleIntermediario(rlForm.getCodiceFiscaleIntermediario());
	        rl.setPartitaIvaIntermediario(rlForm.getPartitaIvaIntermediario());
	        
	        pa.setRappresentanteLegale(rl);
	      }
	      
	      String[] qualifiche = { accrIntrmForm.getIdQualifica() };
	      String tipoQualifica = accr.getQualificaById(qualifiche[0]).getTipoQualifica();
	      if(!accr.esisteQualifica(profilo.getCodiceFiscale(), profilo.getIdComune(), qualifiche) || !UtilHelper.isProfessionista(tipoQualifica)){
	    		accr.accreditaIntermediario(profilo.getCodiceFiscale(), profilo.getIdComune(), accrIntrmForm.getIdQualifica(), pa);
	      } else {
	          if (logger.isDebugEnabled()) {
	              logger.debug("CreaAccr4Step - service() - Impossibile inserire accreditamento duplicato: " + pa);
	          }
	          rwh.addSiracError("CreaAccr4Step::Errore1", "In questo ente esiste gi� un accreditamento di tipo " + accrIntrmForm.getIdQualifica() + " per l'intermediario selezionato.");
	   		  updateWorkflow(process);
	          request.setAttribute("accrIntrmForm", accrIntrmForm);
	    	  return;
	      }
	      if (logger.isDebugEnabled()) {
	        logger.debug("CreaAccr4Step - service() - Creato Accreditamento : pa = " + pa);
	      }
	      updateWorkflow(process);
      } catch (Exception ex) {
        rwh.addSiracError("CreaAccr4Step::Service()::Eccezione", ex.getMessage());
      } finally {
        request.setAttribute("accrIntrmForm", accrIntrmForm);
      }

  }
  
  public void updateWorkflow(AbstractPplProcess process) {
    Activity currentActivity = process.getView().getCurrentActivity();
    IStep currentStep = currentActivity.getCurrentStep();
    
    process.getView().setBottomNavigationBarEnabled(false);
    
    currentStep.setState(StepState.COMPLETED);
    currentActivity.setState(ActivityState.COMPLETED);
    //currentActivity.setCurrentStepIndex(0);
    /*
    for (int i=0; i< activities.length; i++) {
      String activityName = activities[i].getName(); 
      if (!activityName.equals("Introduzione")) {
        activities[i].setState(ActivityState.INACTIVE);
      }
      //activities[i].setCurrentStepIndex(0);
    }
    */

  }
  
}

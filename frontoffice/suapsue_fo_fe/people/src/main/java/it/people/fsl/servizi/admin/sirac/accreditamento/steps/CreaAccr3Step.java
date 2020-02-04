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

import it.cefriel.utility.security.P7MValidationStrategy;
import it.cefriel.utility.security.PKCSHelper;
import it.cefriel.utility.security.SignerChecker_P7MValidationStrategy;
import it.cefriel.utility.security.exceptions.InvalidSignerException;
import it.cefriel.utility.security.exceptions.P7MValidationException;
import it.cefriel.utility.security.exceptions.UnrecognizableCertificateException;
import it.people.Activity;
import it.people.IStep;
import it.people.IValidationErrors;
import it.people.Step;
import it.people.Validator;
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.ServiceParameterConstants;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.smartcardprofile.ISmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfiles;
import it.people.sirac.util.CertTemplate;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.sirac.web.forms.RapprLegaleForm;
import it.people.util.PKCS7Parser;
import it.people.util.ServiceParameters;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CreaAccr3Step extends Step {
  
  private static Logger logger = LoggerFactory.getLogger(CreaAccr3Step.class.getName());
  private String smartCardProfilesXmlFile = null;
  private SmartCardProfiles smartCardProfiles = null;
  private ServiceParameters serviceParams = null;

  /*
   * (non-Javadoc)
   * 
   * @see it.people.IStep#service(it.people.process.AbstractPplProcess,
   *      it.people.wrappers.IRequestWrapper)
   */
  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    super.service(process, request);
    
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    AccrIntrmForm accrIntrmForm = null;

    try {
      ProcessData myData = (ProcessData) process.getData();

      String idComune = process.getCommune().getOid();
      
      if(myData.getPplUser()==null)
        myData.setPplUser(request.getPplUser());

//    String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
      String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
      
      //    ServiceParameters sp = (ServiceParameters) request
      //    .getSessionAttribute("serviceParameters");

      //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");

      String IAccreditamentoURLString = 
        SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
      
      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
          IAccreditamentoURLString);
      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
      if (profilo == null) {
        rwh.addSiracError("CreaAccr3Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
        updateWorkflow(process);
        return;
      }
      
      accrIntrmForm = myData.getAccrIntrmForm();
      String autoCertTemplate= accr.getAutoCertTemplate(accrIntrmForm.getTipoQualifica());
      
      PplUserData userData = myData.getPplUser().getUserData();
      
      Map datiAutoCertMap = UtilHelper.createProfileDataMap(userData, accrIntrmForm);
      String autoCert = CertTemplate.getAutoCert(datiAutoCertMap, autoCertTemplate);
      accrIntrmForm.setAutoCert(autoCert);
      request.setAttribute("datiAutoCertMap", datiAutoCertMap);
     
      updateWorkflow(process);
      
    } catch (Exception ex) {
      rwh.addSiracError("CreaAccr3Step::Service()::Eccezione", ex.getMessage());
      logger.error("CreaAccr3Step::Service()::Eccezione", ex);
    }

  }

	public void loopBack(AbstractPplProcess process, IRequestWrapper request, String propertyName, int index) throws IOException,ServletException {
		
	    HttpServletRequestDelegateWrapperHelper rwh = 
	    	      new HttpServletRequestDelegateWrapperHelper(request);
	    	    AccrIntrmForm accrIntrmForm = null;

	    	    try {
	    	      ProcessData myData = (ProcessData) process.getData();

	    	      String idComune = process.getCommune().getOid();
	    	      
	    	      if(myData.getPplUser()==null)
	    	        myData.setPplUser(request.getPplUser());

//	    	    String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
	    	      String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    	      
	    	      //    ServiceParameters sp = (ServiceParameters) request
	    	      //    .getSessionAttribute("serviceParameters");

	    	      //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");

	    	      String IAccreditamentoURLString = 
	    	        SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    	      
	    	      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
	    	          IAccreditamentoURLString);
	    	      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	    	      if (profilo == null) {
	    	        rwh.addSiracError("CreaAccr3Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
	    	        updateWorkflow(process);
	    	        return;
	    	      }
	    	      
	    	      accrIntrmForm = myData.getAccrIntrmForm();
	    	      String autoCertTemplate= accr.getAutoCertTemplate(accrIntrmForm.getTipoQualifica());
	    	      
	    	      PplUserData userData = myData.getPplUser().getUserData();
	    	      
	    	      Map datiAutoCertMap = UtilHelper.createProfileDataMap(userData, accrIntrmForm);
	    	      String autoCert = CertTemplate.getAutoCert(datiAutoCertMap, autoCertTemplate);
	    	      accrIntrmForm.setAutoCert(autoCert);
	    	      request.setAttribute("datiAutoCertMap", datiAutoCertMap);
	    	     
	    	      updateWorkflow(process);
	    	      
	    	    } catch (Exception ex) {
	    	      rwh.addSiracError("CreaAccr3Step::Service()::Eccezione", ex.getMessage());
	    	      logger.error("CreaAccr3Step::Service()::Eccezione", ex);
	    	    }
		
	}
  
  /* (non-Javadoc)
   * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
   */
  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    HttpSession session = request.getUnwrappedRequest().getSession();
	ServletContext ctx = session.getServletContext();
	serviceParams = (ServiceParameters)session.getAttribute("serviceParameters");
    boolean correct= true;
    
    ProcessData myData = (ProcessData) process.getData();
    AccrIntrmForm accrIntrmForm = myData.getAccrIntrmForm();
    
    String base64P7M = request.getParameter("signedData");
    if (process.isWaitingForOffLineSignedDocument()) {
    	base64P7M = process.getOffLineSignedData();
    }
    accrIntrmForm.setAutoCert(base64P7M);
    
    RapprLegaleForm rlForm = accrIntrmForm.getRapprLegaleForm();
    
    if (accrIntrmForm.getHasRappresentanteLegale() && rlForm != null) {
      rlForm.setCodiceFiscaleIntermediario(accrIntrmForm.getCodiceFiscaleIntermediario());
      rlForm.setPartitaIvaIntermediario(accrIntrmForm.getPartitaIvaIntermediario());
    }

    if (Validator.isBlankOrNull(accrIntrmForm.getAutoCert())) {
      errors.add("error.insert.AutoCertificazione");
      correct = false;
    }
    
    
    // FIX 05-02-2007, 03-07-2007
    // controlla se � necessario fare il controllo di validit� del P7M
    // per verificare che il firmatario sia chi sta utilizzando la carta
    boolean isP7MValidationEnabled = new Boolean((String)serviceParams.get(ServiceParameterConstants.ENABLE_P7M_VALIDATION)).booleanValue();
    if(isP7MValidationEnabled){
    	// Caricamento elenco di profili delle smartcard
   	    // per eventuale verifica di consistenza del P7M firmato
    	if(ctx.getAttribute(ServiceParameterConstants.SMARTCARD_PROFILES) == null){
//		    String smartCardProfilesXmlFileRelativePath = ctx.getInitParameter("smartCardProfilesXmlFile");
    		String smartCardProfilesXmlFileRelativePath = serviceParams.get("smartCardProfilesXmlFile");
			this.smartCardProfilesXmlFile = ctx.getRealPath(smartCardProfilesXmlFileRelativePath);
		    try {
		    	ISmartCardProfileFactory smartCardProfileFactory = SmartCardProfileFactory.getInstance();
		    	smartCardProfiles = smartCardProfileFactory.loadSmartCardProfilesFromXml(new FileInputStream(smartCardProfilesXmlFile));
		    	ctx.setAttribute(ServiceParameterConstants.SMARTCARD_PROFILES, smartCardProfiles);
		    	if (logger.isDebugEnabled()) {
		    		logger.debug("Caricamento profili smartcard caricato correttamente.");
		    	}
		    } catch (Exception e) {
				logger.error("Errore durante la lettura dei profili delle smartcard.");
				errors.add("error.validazione.letturaProfili");
				correct = false;				
			}
		} else {
    	  smartCardProfiles = (SmartCardProfiles)ctx.getAttribute(ServiceParameterConstants.SMARTCARD_PROFILES);
    	}
    	
    	if(correct){
	    	String autoCertSignedB64 = accrIntrmForm.getAutoCert();
//			boolean onlyKnownSmartCardAccepted = new Boolean((String)ctx.getInitParameter(ONLY_KNOWN_SMARTCARDS_ACCEPTED)).booleanValue();
	    	boolean onlyKnownSmartCardAccepted = new Boolean((String)serviceParams.get(ServiceParameterConstants.ONLY_KNOWN_SMARTCARDS_ACCEPTED)).booleanValue();
			SmartCardProfiles smartCardProfiles = (SmartCardProfiles) ctx.getAttribute(ServiceParameterConstants.SMARTCARD_PROFILES);
		    SignerChecker_P7MValidationStrategy strategy = new SignerChecker_P7MValidationStrategy(smartCardProfiles, onlyKnownSmartCardAccepted, accrIntrmForm.getCodiceFiscaleIntermediario());
		    P7MValidationStrategy[] strategies = new P7MValidationStrategy[]{strategy};
		    try {
		    	PKCSHelper.validateP7M(strategies, autoCertSignedB64.getBytes());
		    	logger.debug("Verifica del documento p7m firmato completata correttamente.");
		    } catch(P7MValidationException ex){
		    	logger.error("Errore durante la verifica del documento p7m firmato: " + ex.getMessage());
		    	correct = false;
		    	if(ex instanceof UnrecognizableCertificateException){
			    	errors.add("errors.validazione.certificatoNonRiconosciuto");
		    	} else if(ex instanceof InvalidSignerException){
			    	errors.add("error.validazione.firmatarioNonValido", accrIntrmForm.getCodiceFiscaleIntermediario());
			    }
		    }
    	}
    }
    // END FIX 05-02-2007, 03-07-2007
    
//    PKCS7Parser p7mParser = new PKCS7Parser(accrIntrmForm.getAutoCert());
//    if (!p7mParser.isP7mFile()) {
//    	errors.add("error.insert.AutoCertificazioneFirma");
//    	try {
//    		this.service(process,request);
//    	} catch (Exception e){}
//    	return false;
//    }
    
    try {
    	PKCSHelper.extractContentFromP7M( Base64.decodeBase64(accrIntrmForm.getAutoCert().getBytes()));
    } catch (Exception e) {
    	errors.add("error.insert.AutoCertificazioneFirma");
    	try {
    		this.service(process,request);
    	} catch (Exception ex){}
    	return false;
    }
    
    
    PplUserData userData = myData.getPplUser().getUserData();
    Map datiAutoCertMap = UtilHelper.createProfileDataMap(userData, accrIntrmForm);
    request.setAttribute("datiAutoCertMap", datiAutoCertMap);
    //request.setAttribute("accrIntrmForm", accrForm);

    
    
    return correct;

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
    
    //currentStep.setState(StepState.COMPLETED);
    //currentActivity.setState(ActivityState.COMPLETED);
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

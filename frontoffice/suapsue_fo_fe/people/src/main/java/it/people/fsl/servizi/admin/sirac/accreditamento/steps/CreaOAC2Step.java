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
import it.people.IStep;
import it.people.IValidationErrors;
import it.people.Step;
import it.people.Validator;
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.sirac.web.forms.DelegaForm;
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
public class CreaOAC2Step extends Step {
	
  private static Logger logger = LoggerFactory.getLogger(CreaOAC2Step.class.getName());

  public void service(AbstractPplProcess process, IRequestWrapper request) throws IOException, ServletException {
    
	super.service(process, request);
	HttpServletRequestDelegateWrapperHelper rwh = new HttpServletRequestDelegateWrapperHelper(request);
    
	HttpServletRequest unwrappedRequest = request.getUnwrappedRequest();
	HttpSession session = unwrappedRequest.getSession();

	ProcessData myData = (ProcessData) process.getData();

    try {
	    String idComune = process.getCommune().getOid();
	    
	    if(myData.getPplUser()==null)
	      myData.setPplUser(request.getPplUser());
	
		//  String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
		String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
	    
	    //    ServiceParameters sp = (ServiceParameters) request
	    //    .getSessionAttribute("serviceParameters");
	
	    //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");
	
	    String IAccreditamentoURLString = 
	      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
	    
	    // Imposta il tipo di operazione
	    myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_CREADELEGA);
	    
	    Accreditamento[] elencoAccreditamenti = myData.getElencoAccreditamenti();
	    
	    Accreditamento accrSelezionato = null;
	    if(myData.getSelezioneAccreditamentoIndex() == 999){
	    	accrSelezionato = (Accreditamento)session.getAttribute("accreditamentoRCT");
	    } else {
	    	accrSelezionato = elencoAccreditamenti[myData.getSelezioneAccreditamentoIndex()];
	    }
	    
	    String tipoQualificaAccrSel = accrSelezionato.getQualifica().getTipoQualifica();
	    
	    Qualifica2Persona[] elencoQualifiche2Persona = null;
	
	    ProfiliHelper profiliHelper= null;
	    
	
	    IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	    ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
	    if (profilo == null) {
	    	rwh.addSiracError("CreaOAC2Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
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
	      
	    // salva i valori attuali dell'accreditamento in sessione
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_ACCRSEL, session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_OPERATORE, session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_RICHIEDENTE, session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE));
	    }
	    if(session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE) != null){
	    	session.setAttribute("PREV_"+SiracConstants.SIRAC_ACCR_TITOLARE, session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE));
	    }
	      
	    // imposta le variabili di sessioni del SIRAC con l'accreditamento selezionato
	    session.setAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL, accrSelezionato);
	    session.setAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA, accrSelezionato.getQualifica().getTipoQualifica());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA, accrSelezionato.getQualifica().getDescrizione());
	
	    session.setAttribute(SiracConstants.SIRAC_ACCR_OPERATORE, myData.getSelaccrOperatore());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, myData.getSelaccrRichiedente());
	    session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, myData.getSelaccrTitolare());
	    
	    
	    // Nota: DelegaForm � creata e inizializzata nel processData
	    DelegaForm delegaForm = myData.getDelegaForm();
	     
	    emptyDelegaForm(delegaForm);
	      
	    Qualifica qualificaOAC = accr.getQualificaById("OAC");
	    if (logger.isDebugEnabled()) {
	      logger.debug("CreaOAC2Step::Service():: Recuperata descrizione qualifica OAC:"
	          + qualificaOAC);
	    }
	      
	    delegaForm.setIdQualifica("OAC");
	    delegaForm.setDescrQualifica(qualificaOAC.getDescrizione());
    } catch (Exception ex) {
      rwh.addSiracError("CreaOAC2Step::Service()::Eccezione1", ex.getMessage());
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

    ProcessData myData = (ProcessData) process.getData();
    
    //currentStep.setState(StepState.COMPLETED);
    process.getView().setBottomNavigationBarEnabled(true);

  }

  
  /* (non-Javadoc)
   * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
   */
  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    boolean correct= false;
    ProcessData myData = (ProcessData) process.getData();
    
    DelegaForm delegaForm = myData.getDelegaForm();

    if (Validator.isBlankOrNull(delegaForm.getNome())) {
      errors.add("error.insert.nome");
      correct = false;
    }
    if (Validator.isBlankOrNull(delegaForm.getCognome())) {
      errors.add("error.insert.cognome");
      correct = false;
    }
    if (Validator.isBlankOrNull(delegaForm.getCodiceFiscaleDelegato())) {
      errors.add("error.insert.codiceFiscale");
      correct = false;
    }

    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    try{
      
      String IAccreditamentoURLString = 
      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
    
      IAccreditamentoClientAdapter wsAccr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
    
      HttpServletRequest unwrappedReq = request.getUnwrappedRequest();
      HttpSession session = unwrappedReq.getSession();
      Accreditamento curAccr = (Accreditamento) session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
//      int idAccreditamento = curAccr.getId();
      String idComune = process.getCommune().getOid();
      String codiceFiscaleOperatore = myData.getPplUser().getUserData().getCodiceFiscale();
      
//      Delega[] deleghe = wsAccr.getDeleghe(codiceFiscale, idComune, idAccreditamento);
      
//      boolean delegaAlreadyPresent = false;
//      
//      for(int i=0;i< deleghe.length && !delegaAlreadyPresent ;i++){
//        String codiceFiscaleDelegato = deleghe[i].getCodiceFiscaleDelegato();
//        if(codiceFiscaleDelegato.equalsIgnoreCase(delegaForm.getCodiceFiscaleDelegato())){
//          delegaAlreadyPresent = true;
//        }
//      }
      
      boolean delegaAlreadyPresent = UtilHelper.esisteDelega(wsAccr, codiceFiscaleOperatore, delegaForm.getCodiceFiscaleDelegato(), idComune, curAccr);
      
      if(delegaAlreadyPresent){
        errors.add("error.insert.delega.delegaDuplicata");
        correct = false;
      }
    
    } catch (Exception ex) {
      rwh.addSiracError("CreaOAC2Step::Service()::Eccezione1", ex.getMessage());
    } 
    
    //errors.add("error.genericError");
    
    return correct;
  }

  private void emptyDelegaForm(DelegaForm delegaForm){
	  delegaForm.setCertificazione(null);
	  delegaForm.setCodiceFiscaleDelegato(null);
	  delegaForm.setNome(null);
	  delegaForm.setCognome(null);
	  delegaForm.setCertificazione(null);
	  delegaForm.setDescrQualifica(null);
	  delegaForm.setIdAccreditamento(null);
	  delegaForm.setIdQualifica(null);
  }

}

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
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.CheckCodFis;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.Qualifica2Persona;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.DataValidator;
import it.people.sirac.util.Utilities;
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
public class SelAccr2StepOld extends Step {
  /**
   * Logger for this class
   */
  //  private static final Logger logger = Logger
  //      .getLogger(ProfiloLocaleStep.class);
  private static Logger logger = LoggerFactory.getLogger(SelAccr2StepOld.class
      .getName());

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
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());

    String codiceFiscaleUtente = myData.getPplUser().getUserData().getCodiceFiscale();
    
    //    ServiceParameters sp = (ServiceParameters) request
    //    .getSessionAttribute("serviceParameters");

    //    String IAccreditamentoURLString = sp.get("IAccreditamentoServiceURL");

    String IAccreditamentoURLString = 
      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
    
    
    // Imposta il tipo di operazione
    //myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_SELACCR);
    
    Accreditamento[] elencoAccreditamenti = myData.getElencoAccreditamenti();
    
    Accreditamento accrSelezionato = elencoAccreditamenti[myData.getSelezioneAccreditamentoIndex()];
    
    String tipoQualificaAccrSel = accrSelezionato.getQualifica().getTipoQualifica();
    
    Qualifica2Persona[] elencoQualifiche2Persona = null;

    ProfiliHelper profiliHelper= null;
    
    try {

      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
          IAccreditamentoURLString);
      ProfiloLocale profilo = myData.getProfiloLocale();
      if (profilo==null) {
        profilo = accr.getProfiloLocale(codiceFiscaleUtente, idComune);
        myData.setProfiloLocale(profilo);
        if (logger.isDebugEnabled()) {
          logger.debug("service() - Recuperato profilo locale:  : profilo = " + profilo);
        }
        if (profilo == null) {
          rwh.addSiracError("SelAccr2Step::Errore1",
              "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
          updateWorkflow(process);
          return;
        }
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
      
      // verifica se il richiedente � gi� stato inizializzato
      /*
      if (profiliHelper.getProfiloRichiedente()== null) {
        profiliHelper.setProfiloRichiedente(new ProfiloPersonaFisica());
        profiliHelper.setProfiloRichiedenteDefined(false);
        profiliHelper.setSceltaProfiloRichiedente(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA);
      }
      */
      profiliHelper.setProfiloRichiedente(new ProfiloPersonaFisica());
      
      if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PROFILOREGISTRAZIONE)) {
        ProfiliHelper.initProfiloPersonaFisica(myData.getSelaccrRichiedente(), profiloOperatore);
        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
        profiliHelper.setProfiloRichiedenteDefined(true);
      } else if (profiliHelper.getSceltaProfiloRichiedente().equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) {
        ProfiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());
        profiliHelper.setProfiloRichiedenteDefined(false);
      }

      //profiliHelper.initProfiloPersonaFisica(profiliHelper.getProfiloRichiedente(), myData.getSelaccrRichiedente());

      //if (myData.getSelAccrProfiliHelper()==null) {
        myData.setSelAccrProfiliHelper(profiliHelper);
      //}
      
      

    } catch (Exception ex) {
      rwh.addSiracError("SelAccr2Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      request.setAttribute("profiliHelper", profiliHelper);
      //request.setAttribute("profilopf",
      // profiliHelper.getProfiloRichiedente());
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
    
    // do nothing special
    
  }

  public void loopBack(AbstractPplProcess process, IRequestWrapper request,
      String propertyName, int index) throws IOException, ServletException {
    
    final String LOAD_RICHDATA = "loadDatiRich";
    final String CLEAR_RICHDATA = "clearDatiRich";
    
    ProcessData myData = (ProcessData) process.getData();
    PplUserData userData = myData.getPplUser().getUserData();
    
    ProfiliHelper profiliHelper = myData.getSelAccrProfiliHelper();
    
    ProfiloPersonaFisica profiloRichiedente = profiliHelper.getProfiloRichiedente();  
    
    if (propertyName != null && propertyName.equalsIgnoreCase(LOAD_RICHDATA)){
      UtilHelper.fillProfiloPersonaFisicaWithUserData(profiloRichiedente, userData);
    } else if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_RICHDATA)) {
      profiloRichiedente.clear();
    }
    
    //request.setAttribute("accrIntrmForm", accrIntrmForm);
    request.setAttribute("profiliHelper", profiliHelper);
    return;
  }

  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    boolean correct= true;
    ProcessData myData = (ProcessData) process.getData();
    
    ProfiliHelper selAccrProfiliHelper = myData.getSelAccrProfiliHelper();
    String tipoTitolare = selAccrProfiliHelper.getSceltaProfiloTitolare();
    
    ProfiloPersonaFisica profiloRichiedente = selAccrProfiliHelper.getProfiloRichiedente();
    
    if (tipoTitolare==null) {
      errors.add("error.insert.sceltaProfiloTitolare");
      correct = false;
      
    } else if (Validator.isBlankOrNull(tipoTitolare) 
                || (!ProfiliHelper.SCELTAPROFILO_PERSONAFISICA.equals(tipoTitolare)
                    && !ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA.equals(tipoTitolare)
                    && !ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO.equals(tipoTitolare)
                   )
              ) {
      errors.add("error.insert.sceltaProfiloTitolare");
      correct = false;
    }

    if (Validator.isBlankOrNull(profiloRichiedente.getCodiceFiscale())) {
      errors.add("error.personaFisica.insert.codiceFiscale");
      correct = false;
    } else {
    	// FIX 03-11-2006 Check codice fiscale inserito
    	if (!(CheckCodFis.isValid(profiloRichiedente.getCodiceFiscale()))) {
 			  errors.add("error.insert.CFNonValido");
			  correct = false;
    	}
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getNome())) {
        errors.add("error.personaFisica.insert.nome");
        correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getCognome())) {
        errors.add("error.personaFisica.insert.cognome");
        correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getDataNascitaString())) {
        errors.add("error.personaFisica.insert.dataNascita");
        correct = false;
    }
    if (!DataValidator.getInstance().isDate(profiloRichiedente.getDataNascitaString())) {
      errors.add("error.personaFisica.value.dataNascita");
      correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getLuogoNascita())) {
        errors.add("error.personaFisica.insert.luogoNascita");
        correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getProvinciaNascita())) {
        errors.add("error.personaFisica.insert.provinciaNascita");
        correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getSesso())) {
        errors.add("error.personaFisica.insert.sesso");
        correct = false;
    }
    if (Validator.isBlankOrNull(profiloRichiedente.getIndirizzoResidenza())) {
        errors.add("error.personaFisica.insert.indirizzoResidenza");
        correct = false;
    }
  

    if (correct ) {
      selAccrProfiliHelper.setProfiloRichiedenteDefined(true);
      myData.setSelaccrRichiedente(selAccrProfiliHelper.getProfiloRichiedente());
    }
    request.setAttribute("profiliHelper", selAccrProfiliHelper);
    
    return correct;
  }


}

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
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.CheckParIva;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.DataValidator;
import it.people.sirac.web.forms.AccrIntrmForm;
import it.people.sirac.web.forms.RapprLegaleForm;
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
public class CreaAccr2Step extends Step {
  /**
   * Logger for this class
   */
  //  private static final Logger logger = Logger
  //      .getLogger(ProfiloLocaleStep.class);
//  private Category logger = Category.getInstance(CreaAccr2Step.class
//      .getName());
  private static Logger logger = LoggerFactory.getLogger(CreaAccr2Step.class.getName());

  
  private boolean firstTime = true;

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
    String tipoQualificaSel = null;
    
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
        rwh.addSiracError("CreaAccr2Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
        updateWorkflow(process);
        return;
      }
      
      accrIntrmForm = myData.getAccrIntrmForm();
            
      Qualifica qualificaSel = accr.getQualificaById(accrIntrmForm.getIdQualifica());
      accrIntrmForm.setDescrizioneQualifica(qualificaSel.getDescrizione());
      accrIntrmForm.setTipoQualifica(qualificaSel.getTipoQualifica());
      accrIntrmForm.setHasRappresentanteLegale(qualificaSel.getHasRappresentanteLegale());
      // FIX 2007-06-15: popolamento del domicilio elettronico pari a quello del profilo di registrazione
      // ANNULLATO 2007-12-05: per gli accreditamenti di tipo Rappresentante di Associazione di Categoria (intermediari)
      //                       � necessario indicare esplicitamente un accout email dell'associazione
//      accrIntrmForm.setDomicilioElettronico(myData.getPplUser().getUserData().getEmailaddress());
      
      
      Activity currentActivity = process.getView().getCurrentActivity();
      int currentActivityIndex = process.getView().getCurrentActivityIndex();
      IStep currentStep = currentActivity.getCurrentStep();
      
      PplUserData userData = myData.getPplUser().getUserData();
      RapprLegaleForm rlForm = accrIntrmForm.getRapprLegaleForm();
      
      String indirizzoResidenza = 
        userData.getCittaResidenza() 
        + "(" + userData.getProvinciaResidenza() 
        + "), " + userData.getIndirizzoResidenza();

      if (UtilHelper.isProfessionista(qualificaSel.getTipoQualifica())) {
        accrIntrmForm.setCodiceFiscaleIntermediario(userData.getCodiceFiscale());
        accrIntrmForm.setDenominazione(userData.getNome() + " " + userData.getCognome());
        accrIntrmForm.setSedeLegale(indirizzoResidenza);
      }
      if (myData.isPrecompilaFormProfilo()) {
        // TODO Aggiungere campo a tabella qualifica per specificare se l'utente autenticato � rappresentante legale
        if (qualificaSel.getHasRappresentanteLegale()) {
//          rlForm.setNome(userData.getNome());
//          rlForm.setCognome(userData.getCognome());
//          rlForm.setCodiceFiscale(userData.getCodiceFiscale());
//          rlForm.setDataNascita(userData.getDataNascita());
//          rlForm.setLuogoNascita(userData.getLuogoNascita());
//          rlForm.setProvinciaNascita(userData.getProvinciaNascita());
//          rlForm.setSesso(userData.getSesso());
//          //rlForm.setIndirizzoResidenza(userData.getIndirizzoResidenza());
//          rlForm.setIndirizzoResidenza(userData.getCittaResidenza() 
//              + "(" + userData.getProvinciaResidenza() + "), " + userData.getIndirizzoResidenza());
          //UtilHelper.fillRLFormWithUserData(rlForm, userData);
        }
        myData.setPrecompilaFormProfilo(false);
      }

      
    } catch (Exception ex) {
      //process.getValidationErrors().add("error.genericError");
      rwh.addSiracError("CreaAccr2Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
      request.setAttribute("accrIntrmForm", accrIntrmForm);
      
    }

  }
  
  
  public void loopBack(AbstractPplProcess process, IRequestWrapper request,
      String propertyName, int index) throws IOException, ServletException {
    
    final String LOAD_RLDATA = "loadRLData";
    final String CLEAR_RLDATA = "clearRLData";
    final String CLEAR_PROFILEDATA = "clearProfileData";
    
    ProcessData myData = (ProcessData) process.getData();
    PplUserData userData = myData.getPplUser().getUserData();
    AccrIntrmForm accrIntrmForm = myData.getAccrIntrmForm();
    
    RapprLegaleForm rlForm = accrIntrmForm.getRapprLegaleForm();

    if (propertyName != null && propertyName.equalsIgnoreCase(LOAD_RLDATA)){
      UtilHelper.fillRLFormWithUserData(rlForm, userData);
    } else if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_RLDATA)) {
      rlForm.clear();
    } else if (propertyName != null 
        && propertyName.equalsIgnoreCase(CLEAR_PROFILEDATA)) {
      if (! UtilHelper.isProfessionista(accrIntrmForm.getTipoQualifica())) {
        accrIntrmForm.setCodiceFiscaleIntermediario("");
        accrIntrmForm.setDenominazione("");
        accrIntrmForm.setSedeLegale("");
      }
      accrIntrmForm.setPartitaIvaIntermediario("");
      accrIntrmForm.setDescrizione("");
      accrIntrmForm.setDomicilioElettronico("");
    }
    
    request.setAttribute("accrIntrmForm", accrIntrmForm);
    return;
  }

  /* (non-Javadoc)
   * @see it.people.IStep#logicalValidate(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper, it.people.IValidationErrors)
   */
  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    boolean correct= true;
    ProcessData myData = (ProcessData) process.getData();
    AccrIntrmForm accrForm = myData.getAccrIntrmForm();
    RapprLegaleForm rlForm = accrForm.getRapprLegaleForm();

    if (accrForm.getHasRappresentanteLegale() && rlForm != null) {
      rlForm.setCodiceFiscaleIntermediario(accrForm.getCodiceFiscaleIntermediario());
      rlForm.setPartitaIvaIntermediario(accrForm.getPartitaIvaIntermediario());
    }

    if (Validator.isBlankOrNull(accrForm.getDenominazione())) {
      errors.add("error.insert.Denominazione");
      correct = false;
    }
    
    	
  	// FIX 2006-11-03: Possibile immissione PI per CF se qualifica Intermediario o Rappr. Legale Societa
  	// Per i professionisti il CF � quello della persona
  	
  	boolean PIVAasCFAccepted = 
  		("Intermediario".equalsIgnoreCase(accrForm.getTipoQualifica())  
  			||
       "Rappresentante Persona Giuridica".equalsIgnoreCase(accrForm.getTipoQualifica()));
  	
  	if (!Validator.isBlankOrNull(accrForm.getCodiceFiscaleIntermediario())) {
  		if (PIVAasCFAccepted) {
  			 if (!CheckParIva.isValid(accrForm.getCodiceFiscaleIntermediario())) {
    			  errors.add("error.insert.CFNonValidoPerPG");
    			  correct = false;
  			 }
  			 if (Validator.isBlankOrNull(accrForm.getPartitaIvaIntermediario())) {
  				  errors.add("error.insert.partitaIva");
  				  correct = false;
  			 }
  		} else // La qualifica attuale non consente l'inserimento di una partita iva come codice fiscale
  			if (!(CheckCodFis.isValid(accrForm.getCodiceFiscaleIntermediario()))) {
   			  errors.add("error.insert.CFNonValido");
  			  correct = false;
  		} 
  	} else {
      errors.add("error.insert.codiceFiscale");
      correct = false;
  	}

    if (!Validator.isBlankOrNull(accrForm.getPartitaIvaIntermediario())
        && !CheckParIva.isValid(accrForm.getPartitaIvaIntermediario())) {
      errors.add("error.insert.PIVANonValida");
      correct = false;
    }
    
    if (Validator.isBlankOrNull(accrForm.getSedeLegale())) {
      errors.add("error.insert.SedeLegale");
      correct = false;
    }
    
    if (Validator.isBlankOrNull(accrForm.getDescrizione())) {
      errors.add("error.insert.RifDocumenti");
      correct = false;
    }
    
    if (!Validator.isBlankOrNull(accrForm.getDomicilioElettronico())
         && ! it.people.sirac.util.DataValidator.getInstance().isEmail(accrForm.getDomicilioElettronico())) {
      errors.add("error.insert.domicilioElettronicoNonValido");
    }

    
    if (accrForm.getHasRappresentanteLegale()) {
      if (rlForm != null) {
        if (Validator.isBlankOrNull(rlForm.getCodiceFiscale())) {
            errors.add("error.rapprLegale.insert.codiceFiscale");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getNome())) {
            errors.add("error.rapprLegale.insert.nome");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getCognome())) {
            errors.add("error.rapprLegale.insert.cognome");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getDataNascita())) {
            errors.add("error.rapprLegale.insert.dataNascita");
            correct = false;
        }
        if (!DataValidator.getInstance().isDate(rlForm.getDataNascita())) {
          errors.add("error.rapprLegale.value.dataNascita");
          correct = false;
      }
        if (Validator.isBlankOrNull(rlForm.getLuogoNascita())) {
            errors.add("error.rapprLegale.insert.luogoNascita");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getProvinciaNascita())) {
            errors.add("error.rapprLegale.insert.provinciaNascita");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getSesso())) {
            errors.add("error.rapprLegale.insert.sesso");
            correct = false;
        }
        if (Validator.isBlankOrNull(rlForm.getIndirizzoResidenza())) {
            errors.add("error.rapprLegale.insert.indirizzoResidenza");
            correct = false;
        }
        
      } else {
        errors.add("error.rapprLegale.generic");
        correct=false;
      }
      
    }  
    
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    try{
      
      String IAccreditamentoURLString = 
      SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
    
      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(
        IAccreditamentoURLString);
    
      String idComune = process.getCommune().getOid();
      String codiceFiscale = myData.getPplUser().getUserData().getCodiceFiscale();
      
      Accreditamento[] accreditamenti = accr.getAccreditamenti(codiceFiscale, idComune);
      
      boolean accreditamentoAlreadyPresent = false;
     
      // check per accreditamento gi� esistente
      for(int i=0;i< accreditamenti.length && !accreditamentoAlreadyPresent ;i++){
        String idQualifica = accreditamenti[i].getQualifica().getIdQualifica();
        String codFisIntermediario = accreditamenti[i].getProfilo().getCodiceFiscaleIntermediario();
        String partIvaIntermediario = accreditamenti[i].getProfilo().getPartitaIvaIntermediario();
        if(idComune.equals(accreditamenti[i].getIdComune()) &&
           codiceFiscale.equals(accreditamenti[i].getCodiceFiscale()) &&
           idQualifica.equals(accrForm.getIdQualifica()) &&
           ( 
            (     
                  codFisIntermediario.equals(accrForm.getCodiceFiscaleIntermediario()) &&
                  partIvaIntermediario.equals(accrForm.getPartitaIvaIntermediario())
            ) ||
            (
                codFisIntermediario.equals(accrForm.getCodiceFiscaleIntermediario()) &&
                "".equalsIgnoreCase(accrForm.getPartitaIvaIntermediario())
            ) ||
            (
                "".equalsIgnoreCase(accrForm.getCodiceFiscaleIntermediario()) &&
                partIvaIntermediario.equals(accrForm.getPartitaIvaIntermediario())
            )
           ) 
           
        ){
          accreditamentoAlreadyPresent = true;
        }
      }
      if(accreditamentoAlreadyPresent){
        errors.add("error.insert.accreditamento.accreditamentoDuplicato");
        correct = false;
      }
    
    } catch (Exception ex) {
      rwh.addSiracError("SelAccr1Step::Service()::Eccezione1", ex.getMessage());
    } 

    request.setAttribute("accrIntrmForm", accrForm);

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
    
    
    
    //currentStep.setState(StepState.COMPLETED);
    process.getView().setBottomNavigationBarEnabled(true);
    
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

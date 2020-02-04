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
import it.people.PeopleConstants;
import it.people.Step;
import it.people.Validator;
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.CheckCodFis;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.CheckParIva;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.ServiceParameterConstants;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.parser.exception.ParserException;
import it.people.process.AbstractPplProcess;
import it.people.sirac.accr.ProfiliHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloAccreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.accr.beans.RappresentanteLegale;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.deleghe.DelegantiHelper;
import it.people.sirac.deleghe.beans.CriteriRicercaDeleganti;
import it.people.sirac.deleghe.exceptions.DelegantiServiceConnectionException;
import it.people.sirac.deleghe.exceptions.NoDelegantiFoundException;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.DataValidator;
import it.people.sirac.util.Utilities;
import it.people.util.MessageBundleHelper;
import it.people.util.ServiceParameters;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
public class SelAccr3Step extends Step {


  private static Logger logger = LoggerFactory.getLogger(SelAccr3Step.class.getName());

  private ServiceParameters serviceParams = null;
  
  public void service(AbstractPplProcess process, IRequestWrapper request)
      throws IOException, ServletException {
    super.service(process, request);
    HttpServletRequestDelegateWrapperHelper rwh = 
      new HttpServletRequestDelegateWrapperHelper(request);
    
    ProcessData myData = (ProcessData) process.getData();
    HttpSession session = request.getUnwrappedRequest().getSession();
    String idComune = process.getCommune().getOid();
    
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
	
	    request.setAttribute("delegatoString", getDelegatoString(myData));
	    
	    String tipoTitolare = null;
	    // Imposta il tipo di operazione
	    //myData.setOperazione(ProcessData.GESTIONE_ACCR_OPER_SELACCR);
	
	      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	      ProfiloLocale profilo = UtilHelper.getProfiloLocale(myData, accr, codiceFiscaleUtente, idComune);
		  if (profilo == null) {
			  rwh.addSiracError("SelAccr3Step::Errore1", "Non esiste un profilo locale per l'utente " + codiceFiscaleUtente);
			  updateWorkflow(process);
			  return;
		  }
      
	      ProfiliHelper selAccrProfiliHelper = myData.getSelAccrProfiliHelper();
	      tipoTitolare = selAccrProfiliHelper.getSceltaProfiloTitolare();
	      Accreditamento selAccr = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
	      
	      if (//!selAccrProfiliHelper.isProfiloTitolareDefined() &&
	           tipoTitolare.equals(ProfiliHelper.SCELTAPROFILO_PERSONAFISICA)) {
	        selAccrProfiliHelper.setProfiloTitolare(new ProfiloPersonaFisica());
	        if (myData.getSelaccrTitolare() instanceof ProfiloPersonaFisica) {
	          ProfiliHelper.initProfiloPersonaFisica((ProfiloPersonaFisica)selAccrProfiliHelper.getProfiloTitolare(), 
	                                                        (ProfiloPersonaFisica) myData.getSelaccrTitolare());
	        }
	        selAccrProfiliHelper.setProfiloTitolareDefined(false);
	      
	      } else if (//!selAccrProfiliHelper.isProfiloTitolareDefined() &&
	                 tipoTitolare.equals(ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA)) {
	        selAccrProfiliHelper.setProfiloTitolare(new ProfiloPersonaGiuridica());
	      
	        if (myData.getSelaccrTitolare() instanceof ProfiloPersonaGiuridica) {
	        ProfiliHelper.initProfiloPersonaGiuridica((ProfiloPersonaGiuridica)selAccrProfiliHelper.getProfiloTitolare(), 
	            (ProfiloPersonaGiuridica) myData.getSelaccrTitolare());
	        }  
	        selAccrProfiliHelper.setProfiloTitolareDefined(false);
	      } else if (selAccrProfiliHelper.isProfiloTitolareDefined()
	                 && selAccrProfiliHelper.getSceltaProfiloTitolare().equals(ProfiliHelper.SCELTAPROFILO_PROFILOACCREDITAMENTO)) {
	        ProfiloPersonaGiuridica pg = new ProfiloPersonaGiuridica();
	        ProfiloAccreditamento selProfiloAccr = selAccr.getProfilo();
	        pg.setCodiceFiscale(selProfiloAccr.getCodiceFiscaleIntermediario());
	        pg.setSedeLegale(selProfiloAccr.getSedeLegale());
	        pg.setDenominazione(selProfiloAccr.getDenominazione());
	        pg.setDescrizione(selProfiloAccr.getDescrizione());
	        pg.setDomicilioElettronico(selProfiloAccr.getDomicilioElettronico());
	        pg.setPartitaIva(selProfiloAccr.getPartitaIvaIntermediario());
	        if (selProfiloAccr.getRappresentanteLegale()!= null) {
	          RappresentanteLegale rlProfiloAccr = selProfiloAccr.getRappresentanteLegale();
	          pg.getRappresentanteLegale().setNome(rlProfiloAccr.getNome());
	          pg.getRappresentanteLegale().setCognome(rlProfiloAccr.getCognome());
	          pg.getRappresentanteLegale().setCodiceFiscale(rlProfiloAccr.getCodiceFiscale());
	          pg.getRappresentanteLegale().setDataNascita(rlProfiloAccr.getDataNascita());
	          pg.getRappresentanteLegale().setLuogoNascita(rlProfiloAccr.getLuogoNascita());
	          pg.getRappresentanteLegale().setProvinciaNascita(rlProfiloAccr.getProvinciaNascita());
	          pg.getRappresentanteLegale().setSesso(rlProfiloAccr.getSesso());
	          pg.getRappresentanteLegale().setIndirizzoResidenza(rlProfiloAccr.getIndirizzoResidenza());
	        }
	        selAccrProfiliHelper.setProfiloTitolare(pg);
	      } else if (!selAccrProfiliHelper.isProfiloTitolareDefined()) 
	          throw new Exception("Profilo titolare non valido.");
	
	      //selAccrProfiliHelper.setTmpProfiloPF(new ProfiloPersonaFisica());
	      //selAccrProfiliHelper.setTmpProfiloPG(new ProfiloPersonaGiuridica());
      
    } catch (Exception ex) {
      rwh.addSiracError("SelAccr3Step::Service()::Eccezione1", ex.getMessage());
    } finally {
      updateWorkflow(process);
      myData.getCriteriRicercaDeleganti().clear();
      myData.setElencoDelegantiTrovati(null);
      myData.setMostraLinkConferma(false);
      serviceParams = (ServiceParameters)session.getAttribute("serviceParameters");
      boolean isDelegantLookupEnabled = new Boolean((String)serviceParams.get(ServiceParameterConstants.DELEGANT_LOOKUP_ENABLED)).booleanValue();
      if(isDelegantLookupEnabled){
    	  myData.setEnableModifyTitolare(new Boolean(serviceParams.get(ServiceParameterConstants.ENABLE_MODIFY_DELEGANT)).booleanValue());
      } else {
    	  myData.setEnableModifyTitolare(true);
      }
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
    
    
  }

  /* (non-Javadoc)
   * @see it.people.IStep#defineControl(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
   */
  public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
    
    super.defineControl(process, request);
    
    ProcessData myData = (ProcessData) process.getData();

    String idComune = process.getCommune().getOid();
    
    if(myData.getPplUser()==null)
      myData.setPplUser(request.getPplUser());
    
    

  }
  
  public void loopBack(AbstractPplProcess process, IRequestWrapper request,
      String propertyName, int index) throws IOException, ServletException {
    
    final String LOAD_TITPFDATA = "loadDatiTitolarePF";
    final String CLEAR_TITPFDATA = "clearDatiTitolarePF";
    final String CLEAR_TITPGDATA = "clearDatiTitolarePG";
    final String COPY_RICHTOTITPFDATA = "copyDatiRichToTitolarePF";
    final String START_RICERCADELEGANTI = "startRicercaDeleganti";
    final String CLEAR_RICERCADELEGANTIDATA = "clearDatiRicercaDeleganti";
    final String CONFERMA_DELEGANTE = "confermaDelegante";
    
    ProcessData myData = (ProcessData) process.getData();
    PplUserData userData = myData.getPplUser().getUserData();
    HttpSession session = request.getUnwrappedRequest().getSession();
//    CriteriRicercaDeleganti criteriRicercaDeleganti = (CriteriRicercaDeleganti)process.getDto();
    CriteriRicercaDeleganti criteriRicercaDeleganti = (CriteriRicercaDeleganti)myData.getCriteriRicercaDeleganti();
        
    ProfiliHelper selAccrProfiliHelper = myData.getSelAccrProfiliHelper();
    String tipoTitolare = selAccrProfiliHelper.getSceltaProfiloTitolare();
    AbstractProfile profiloTitolare = selAccrProfiliHelper.getProfiloTitolare();
    serviceParams = (ServiceParameters)session.getAttribute("serviceParameters");
        
    String idComune = process.getCommune().getOid();
    String codiceFiscaleDelegato = null;
    if(ProfiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equalsIgnoreCase(myData.getSelAccrProfiliHelper().getTipoQualifica())){
    	Accreditamento accrSelezionato = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
    	codiceFiscaleDelegato = accrSelezionato.getProfilo().getCodiceFiscaleIntermediario();
    } else {
    	codiceFiscaleDelegato = myData.getPplUser().getUserData().getCodiceFiscale();    	
    }
	String bslDelegheWSAddress = serviceParams.get(ServiceParameterConstants.BSL_ELENCODELEGANTI);
	int maxDelegantiFound = new Integer(serviceParams.get(ServiceParameterConstants.MAX_DELEGANTI_FOUND)).intValue();
    ProfiloPersonaFisica profiloRichiedente = selAccrProfiliHelper.getProfiloRichiedente();  
    
    if (profiloTitolare != null 
        && ProfiliHelper.SCELTAPROFILO_PERSONAFISICA.equalsIgnoreCase(tipoTitolare)){
      if (propertyName != null && propertyName.equalsIgnoreCase(LOAD_TITPFDATA)){
          UtilHelper.fillProfiloPersonaFisicaWithUserData((ProfiloPersonaFisica)profiloTitolare, userData);
      } else if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_TITPFDATA)) {
          ((ProfiloPersonaFisica)profiloTitolare).clear();
      } else if (propertyName != null && propertyName.equalsIgnoreCase(COPY_RICHTOTITPFDATA)) {
          UtilHelper.cloneProfiloPersonaFisica(profiloRichiedente, (ProfiloPersonaFisica)profiloTitolare);
      } else if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_RICERCADELEGANTIDATA)) {
    	  myData.getCriteriRicercaDeleganti().clear();
    	  myData.setElencoDelegantiTrovati(null);
    	  myData.setMostraLinkConferma(false);
    	  myData.setCodiceFiscaleDeleganteSelezionato(null);
      } else if (propertyName != null && propertyName.equalsIgnoreCase(START_RICERCADELEGANTI)) {
//    	  if(notEmpty(criteriRicercaDeleganti)){
    		  try { 
    			  List delegantiTrovati = DelegantiHelper.getDelegantiFisici(idComune, codiceFiscaleDelegato, criteriRicercaDeleganti, bslDelegheWSAddress);
    			  if(delegantiTrovati.size() <= maxDelegantiFound){
	    			  myData.setElencoDelegantiTrovati(delegantiTrovati);
	    			  myData.setMostraLinkConferma(true);
    			  } else {
    				  String message = MessageBundleHelper.message(DelegantiHelper.MAX_DELEGANTI_MESSAGE_KEY, new String[]{new Integer(maxDelegantiFound).toString()}, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
    				  request.setAttribute("errorMessage", message);
    			  }
    		  } catch(NoDelegantiFoundException ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    	  	  } catch(DelegantiServiceConnectionException ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    			  String message = MessageBundleHelper.message(DelegantiHelper.BEDELEGHE_CONNECTION_ERROR_KEY, null, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
				  request.setAttribute("errorMessage", message);
    		  } catch(Exception ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    			  String message = MessageBundleHelper.message(DelegantiHelper.BEDELEGHE_GENERIC_ERROR_KEY, new String[]{ex.getMessage()}, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
				  request.setAttribute("errorMessage", message);
    		  }
//    	  } 
      } else if (propertyName != null && propertyName.equalsIgnoreCase(CONFERMA_DELEGANTE)) {
    	  Iterator delegantiIterator = myData.getElencoDelegantiTrovati().iterator();
    	  String codiceFiscaleDeleganteSelezionato = myData.getCodiceFiscaleDeleganteSelezionato();
    	  while(delegantiIterator.hasNext()){
    		  ProfiloPersonaFisica item = new ProfiloPersonaFisica((ProfiloPersonaFisica)delegantiIterator.next());
    		  if(codiceFiscaleDeleganteSelezionato != null && codiceFiscaleDeleganteSelezionato.equals(item.getCodiceFiscale())){
    			  myData.getSelAccrProfiliHelper().setProfiloTitolare(item);
    			  break;
    		  }
    	  }
      }
    } else if (profiloTitolare != null 
        && ProfiliHelper.SCELTAPROFILO_PERSONAGIURIDICA.equalsIgnoreCase(tipoTitolare)){
    	if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_TITPGDATA)){
    		((ProfiloPersonaGiuridica)profiloTitolare).clear();
    	} else if (propertyName != null && propertyName.equalsIgnoreCase(CLEAR_RICERCADELEGANTIDATA)){
    		 myData.getCriteriRicercaDeleganti().clear();
    		 myData.setElencoDelegantiTrovati(null);
    		 myData.setMostraLinkConferma(false);
    		 myData.setCodiceFiscaleDeleganteSelezionato(null);
    	} else if (propertyName != null && propertyName.equalsIgnoreCase(START_RICERCADELEGANTI)) {
//    	  if(notEmpty(criteriRicercaDeleganti)){
    		  try { 
	    		  List delegantiTrovati = DelegantiHelper.getDelegantiGiuridici(idComune, codiceFiscaleDelegato, criteriRicercaDeleganti, bslDelegheWSAddress);
	    		  if(delegantiTrovati.size() <= maxDelegantiFound){
		    		  myData.setElencoDelegantiTrovati(delegantiTrovati);
		    		  myData.setMostraLinkConferma(true);
	    		  } else {
	    			  String message = MessageBundleHelper.message(DelegantiHelper.MAX_DELEGANTI_MESSAGE_KEY, new String[]{new Integer(maxDelegantiFound).toString()}, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
					  request.setAttribute("errorMessage", message);
	    		  }
    		  } catch(NoDelegantiFoundException ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    	  	  } catch(DelegantiServiceConnectionException ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    			  String message = MessageBundleHelper.message(DelegantiHelper.BEDELEGHE_CONNECTION_ERROR_KEY, null, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
				  request.setAttribute("errorMessage", message);
    		  } catch(Exception ex){
    			  myData.setElencoDelegantiTrovati(null);
    	    	  myData.setMostraLinkConferma(false);
    			  logger.debug(ex.getMessage());
    			  String message = MessageBundleHelper.message(DelegantiHelper.BEDELEGHE_GENERIC_ERROR_KEY, new String[]{ex.getMessage()}, process.getProcessName(), process.getCommune().getKey(), getLocale(request.getUnwrappedRequest()));
				  request.setAttribute("errorMessage", message);
    		  }  
//    	  }
    	} else if (propertyName != null && propertyName.equalsIgnoreCase(CONFERMA_DELEGANTE)) {
    	  Iterator delegantiIterator = myData.getElencoDelegantiTrovati().iterator();
    	  String codiceFiscaleDeleganteSelezionato = myData.getCodiceFiscaleDeleganteSelezionato();
    	  while(delegantiIterator.hasNext()){
    		  ProfiloPersonaGiuridica item = new ProfiloPersonaGiuridica((ProfiloPersonaGiuridica)delegantiIterator.next());
    		  if(codiceFiscaleDeleganteSelezionato != null && codiceFiscaleDeleganteSelezionato.equals(item.getCodiceFiscale())){
    			  myData.getSelAccrProfiliHelper().setProfiloTitolare(item);
    			  break;
    		  }
    	  }
      }
    } else {
	  myData.setElencoDelegantiTrovati(null);
	  myData.setCodiceFiscaleDeleganteSelezionato(null);
    }
    request.setAttribute("delegatoString", getDelegatoString(myData));
    boolean isDelegantLookupEnabled = new Boolean((String)serviceParams.get(ServiceParameterConstants.DELEGANT_LOOKUP_ENABLED)).booleanValue();
    if(isDelegantLookupEnabled){
  	  myData.setEnableModifyTitolare(new Boolean(serviceParams.get(ServiceParameterConstants.ENABLE_MODIFY_DELEGANT)).booleanValue());
    } else {
  	  myData.setEnableModifyTitolare(true);
    }

    return;
  }

  
  public boolean logicalValidate(AbstractPplProcess process,
      IRequestWrapper request, IValidationErrors errors) throws ParserException {
    
    boolean correct= true;
    ProcessData myData = (ProcessData) process.getData();
    
    ProfiliHelper selAccrProfiliHelper = myData.getSelAccrProfiliHelper();
    String tipoTitolare = selAccrProfiliHelper.getSceltaProfiloTitolare();
    
    AbstractProfile profiloTitolare = selAccrProfiliHelper.getProfiloTitolare();
    
    if (profiloTitolare instanceof ProfiloPersonaFisica) {
      ProfiloPersonaFisica titolarePF = (ProfiloPersonaFisica) profiloTitolare;
      if (Validator.isBlankOrNull(titolarePF.getCodiceFiscale())) {
        errors.add("error.personaFisica.insert.codiceFiscale");
        correct = false;
      } else {
      	// FIX 03-11-2006 Check codice fiscale inserito
      	if (!(CheckCodFis.isValid(titolarePF.getCodiceFiscale()))) {
   			  errors.add("error.insert.CFNonValido");
  			  correct = false;
      	}
      }
      if (Validator.isBlankOrNull(titolarePF.getNome())) {
          errors.add("error.personaFisica.insert.nome");
          correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getCognome())) {
          errors.add("error.personaFisica.insert.cognome");
          correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getDataNascitaString())) {
          errors.add("error.personaFisica.insert.dataNascita");
          correct = false;
      }
      if (!DataValidator.getInstance().isDate(titolarePF.getDataNascitaString())) {
        errors.add("error.personaFisica.value.dataNascita");
        correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getLuogoNascita())) {
          errors.add("error.personaFisica.insert.luogoNascita");
          correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getProvinciaNascita())) {
          errors.add("error.personaFisica.insert.provinciaNascita");
          correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getSesso())) {
          errors.add("error.personaFisica.insert.sesso");
          correct = false;
      }
      if (Validator.isBlankOrNull(titolarePF.getIndirizzoResidenza())) {
          errors.add("error.personaFisica.insert.indirizzoResidenza");
          correct = false;
      }
      
      
    } else if (profiloTitolare instanceof ProfiloPersonaGiuridica) {
      ProfiloPersonaGiuridica titolarePG = (ProfiloPersonaGiuridica) profiloTitolare;
      ProfiloPersonaFisica rl = titolarePG.getRappresentanteLegale();
      
      if (Validator.isBlankOrNull(titolarePG.getDenominazione())) {
        errors.add("error.insert.Denominazione");
        correct = false;
      }
      // FIX 2007-06-22: resi obbligatori sia CF che P.IVA per utilizzo corretto dell'API it.people.sirac.deleghe.ProfiliDelegaHelper
      if (Validator.isBlankOrNull(titolarePG.getCodiceFiscale())
          || Validator.isBlankOrNull(titolarePG.getPartitaIva())) {
        errors.add("error.insert.CFePIVA");
        correct = false;
      } else {
      	// FIX 03-11-2006 Check codice fiscale o partita iva inserita
    	// FIX 2007-06-22: il codice fiscale di una PG deve sempre validare come una P.IVA
      	if (!CheckParIva.isValid(titolarePG.getCodiceFiscale())) {
   			  errors.add("error.insert.CFNonValido");
  			  correct = false;
      	} 
       	if (!CheckParIva.isValid(titolarePG.getPartitaIva())) {
   			  errors.add("error.insert.PIVANonValida");
   			  correct = false;
       	}
      }
      
      if (Validator.isBlankOrNull(titolarePG.getSedeLegale())) {
        errors.add("error.insert.SedeLegale");
        correct = false;
      }

      if (rl != null) {
        if (Validator.isBlankOrNull(rl.getCodiceFiscale())) {
            errors.add("error.rapprLegale.insert.codiceFiscale");
            correct = false;
        } else {
        	// FIX 03-11-2006 Check codice fiscale inserito
        	if (!(CheckCodFis.isValid(rl.getCodiceFiscale()))) {
     			  errors.add("error.insert.CFRLNonValido");
    			  correct = false;
        	}
        }
        
        if (Validator.isBlankOrNull(rl.getNome())) {
            errors.add("error.rapprLegale.insert.nome");
            correct = false;
        }
        if (Validator.isBlankOrNull(rl.getCognome())) {
            errors.add("error.rapprLegale.insert.cognome");
            correct = false;
        }
//        if (Validator.isBlankOrNull(rl.getDataNascitaString())) {
//            errors.add("error.rapprLegale.insert.dataNascita");
//            correct = false;
//        }
//        if (!DataValidator.getInstance().isDate(rl.getDataNascitaString())) {
//          errors.add("error.rapprLegale.value.dataNascita");
//          correct = false;
//      }
//        if (Validator.isBlankOrNull(rl.getLuogoNascita())) {
//            errors.add("error.rapprLegale.insert.luogoNascita");
//            correct = false;
//        }
//        if (Validator.isBlankOrNull(rl.getProvinciaNascita())) {
//            errors.add("error.rapprLegale.insert.provinciaNascita");
//            correct = false;
//        }
//        if (Validator.isBlankOrNull(rl.getSesso())) {
//            errors.add("error.rapprLegale.insert.sesso");
//            correct = false;
//        }
//        if (Validator.isBlankOrNull(rl.getIndirizzoResidenza())) {
//            errors.add("error.rapprLegale.insert.indirizzoResidenza");
//            correct = false;
//        }
        
      } else {
        errors.add("error.rapprLegale.generic");
        correct=false;
      }

      if (correct) {
        selAccrProfiliHelper.setProfiloTitolareDefined(true);
        myData.setSelaccrTitolare(selAccrProfiliHelper.getProfiloTitolare());
      }

    }

  

    if (correct ) {
      selAccrProfiliHelper.setProfiloTitolareDefined(true);
      myData.setSelaccrTitolare(selAccrProfiliHelper.getProfiloTitolare());
    }
    request.setAttribute("profiliHelper", selAccrProfiliHelper);
    request.setAttribute("delegatoString", getDelegatoString(myData));
    return correct;
  }
  
  private String getDelegatoString(ProcessData myData){
	  String delegatoString = null;
	  if(ProfiliHelper.TIPOQUALIFICA_INTERMEDIARIO.equalsIgnoreCase(myData.getSelAccrProfiliHelper().getTipoQualifica())){
		  Accreditamento accrSelezionato = myData.getElencoAccreditamenti()[myData.getSelezioneAccreditamentoIndex()];
		  String codiceFiscaleDelegato = accrSelezionato.getProfilo().getCodiceFiscaleIntermediario();
		  String denominazioneDelegato = accrSelezionato.getProfilo().getDenominazione();
		  delegatoString = denominazioneDelegato + " (C.F.: " + codiceFiscaleDelegato + ")";
	  } else {
		  String codiceFiscaleDelegato = myData.getPplUser().getUserData().getCodiceFiscale();
		  String nomeDelegato = myData.getPplUser().getUserData().getNome();
		  String cognomeDelegato = myData.getPplUser().getUserData().getCognome();
		  delegatoString = nomeDelegato + " " + cognomeDelegato + " (C.F.: " + codiceFiscaleDelegato + ")";
	  }
	  return delegatoString;
  }
  
  private Locale getLocale(HttpServletRequest request){
	  Locale locale = (Locale) request.getSession().getAttribute(PeopleConstants.USER_LOCALE_KEY);
      if (locale == null) {
          locale = Locale.ITALY;
      }
      return locale;
  }
  
  private boolean notEmpty(CriteriRicercaDeleganti crd){
	  return (crd.getCodiceFiscaleDelegante() != null && !crd.getCodiceFiscaleDelegante().equals("")) ||
	         (crd.getCognomeDelegante() != null && !crd.getCognomeDelegante().equals("")) ||
	         (crd.getNomeDelegante() != null && !crd.getNomeDelegante().equals("")) ||
	         (crd.getPartitaIvaDelegante() != null && !crd.getPartitaIvaDelegante().equals("")) ||
	         (crd.getRagioneSocialeDelegante() != null && !crd.getRagioneSocialeDelegante().equals(""));
  }
}

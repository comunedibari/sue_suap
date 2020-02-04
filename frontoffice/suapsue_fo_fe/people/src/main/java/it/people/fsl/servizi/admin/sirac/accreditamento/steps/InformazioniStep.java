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
import it.people.PeopleConstants;
import it.people.Step;
import it.people.StepState;
import it.people.core.PplACE;
import it.people.fsl.servizi.admin.sirac.accreditamento.model.ProcessData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.AbstractPplProcess;
import it.people.process.PplProcess;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.serviceProfile.IServiceProfile;
import it.people.sirac.serviceProfile.IServiceProfileFactory;
import it.people.sirac.serviceProfile.ServiceProfile;
import it.people.sirac.serviceProfile.ServiceProfileFactory;
import it.people.sirac.serviceProfile.ServiceProfileFactoryFromDB;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.wrappers.HttpServletRequestDelegateWrapperHelper;
import it.people.wrappers.IRequestWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * @author max
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class InformazioniStep extends Step {
  /**
   * Logger for this class
   */
  //  private static final Logger logger = Logger
  //      .getLogger(ProfiloLocaleStep.class);
  private static Logger logger = LoggerFactory.getLogger(InformazioniStep.class
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
    myData.setPrecompilaFormProfilo(true);
    updateWorkflow(process);
    
    
    String scegliAccr = (String) request.getParameter("selAccr");
    if (scegliAccr!=null && scegliAccr.equalsIgnoreCase("true")){
    	ServiceProfile serviceProfileServAccr = (ServiceProfile)this.getServiceProfileServizioAccreditamento(request);
    	if (serviceProfileServAccr!=null){
    		request.getUnwrappedRequest().getSession().setAttribute(SiracConstants.SIRAC_CURRENT_SERVICE_PROFILE,serviceProfileServAccr);
    	}
        
//        Accreditamento accrCorrente = (Accreditamento) session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
//        ProfiloPersonaFisica profiloOperatore = (ProfiloPersonaFisica) session.getAttribute(SiracConstants.SIRAC_ACCR_OPERATORE);
//        AbstractProfile profiloTitolare = (AbstractProfile) session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE);
    	
    	
    	Activity[] activities = process.getView().getActivities();
    	if (activities!=null){
	    	for (int i = 0; i < activities.length; i++) {
				if (activities[i].getName()!=null && activities[i].getName().equalsIgnoreCase("Selezione Accreditamento")){
			    	Iterator it = activities[2].getStepList().iterator();
			    	while (it.hasNext()){
			    		Step step = (Step) it.next();
			    		step.setState(StepState.ACTIVE);
			    	}
			    	process.getView().setCurrentActivityIndex(i);
				} else {
					activities[i].setState(ActivityState.INACTIVE);
				}
			}
    	}
//    	activities[0].setState(ActivityState.INACTIVE);
//    	activities[1].setState(ActivityState.INACTIVE);
//    	activities[2].setState(ActivityState.ACTIVE);
//    	Iterator it = activities[2].getStepList().iterator();
//    	while (it.hasNext()){
//    		Step step = (Step) it.next();
//    		step.setState(StepState.ACTIVE);
//    	}
//    	process.getView().setCurrentActivityIndex(2);
    	goToStep(process, request, 0);
    } else {
    	request.getUnwrappedRequest().getSession().removeAttribute("servizioSel");
    	request.getUnwrappedRequest().getSession().removeAttribute("parametriServizio");
    }



  }
  /* (non-Javadoc)
   * @see it.people.IStep#defineControl(it.people.process.AbstractPplProcess, it.people.wrappers.IRequestWrapper)
   */
  public void defineControl(AbstractPplProcess process, IRequestWrapper request) {
    super.defineControl(process, request);
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    IStep currentStep = currentActivity.getCurrentStep();
    String currentStepId = currentStep.getId();
    int currentStepIndex = currentActivity.getCurrentStepIndex();
   return;
    
  }
  public void updateWorkflow(AbstractPplProcess process) {
    Activity[] activities = process.getView().getActivities();
    Activity currentActivity = process.getView().getCurrentActivity();
    int currentActivityIndex = process.getView().getCurrentActivityIndex();
    String currentActivityName = currentActivity.getName();
    
    IActivity firstActivity = process.getView().getActivityById("0");
    
    ProcessData myData = (ProcessData)process.getData();
    
    process.getView().setBottomNavigationBarEnabled(false);
    
    //currentActivity.setState(ActivityState.COMPLETED)
    if (myData.isShowActivityMenu()) {
      for (int i=0; i< activities.length; i++) {
        String activityName = activities[i].getName();
        Activity curActivity = activities[i];
        ArrayList curStepList = curActivity.getStepList();
        for (int stp=0; stp<curStepList.size(); stp++) {
          Step curStep = (Step) curStepList.get(stp);
          curStep.setState(StepState.ACTIVE);
        }
        curActivity.setState(ActivityState.ACTIVE);
        curActivity.setCurrentStepIndex(0);
      } 
    } else {
      myData.setShowActivityMenu(true);
    }
  }
  
  
  
  protected boolean goToStep(AbstractPplProcess process, IRequestWrapper request, int offset) {
      logger.debug("Inside goToStep with offset " + offset);
      int index = getCurrentStepIndex(process);
      logger.debug("Current index is " + index);
      logger.debug("Next index is " + (index + offset));
      setStep(process, (index + offset));
      
      try {
    	  process.getView().getCurrentActivity().getCurrentStep().service(process, request);
//      String idComune = process.getCommune().getOid();
//  	  String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(request.getUnwrappedRequest().getSession());
//  	  String IAccreditamentoURLString = SiracHelper.getIAccreditamentoServiceURLString(request.getUnwrappedRequest().getSession().getServletContext());
//      IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
//      Accreditamento[] accrs = accr.getAccreditamenti(codiceFiscaleUtente, idComune);
//      ((ProcessData)process.getData()).setElencoAccreditamenti(accrs);
//      request.setAttribute("accreditamenti", accrs);
      } catch (Exception e){}
      /*
      try {
          process.getView().getCurrentActivity().getCurrentStep().service(process, request);
          System.out.println("filtro accreditamenti selezionabili");
          HttpSession session = request.getUnwrappedRequest().getSession();
          if (session!=null){
        	  Accreditamento[] a2=null;
        	  ServiceProfile currentServiceProfile = (ServiceProfile)session.getAttribute(SiracConstants.SIRAC_CURRENT_SERVICE_PROFILE);
        	  if (currentServiceProfile!=null && !currentServiceProfile.isAllQualificheIntermediariEnabled()){
        		  String[] dd = currentServiceProfile.getQualificheIntermediarioAbilitate();
        		  Accreditamento[] a = ((ProcessData)process.getData()).getElencoAccreditamenti();
        		  Accreditamento[] tmp = null;
        		  HashMap newMapAccreditamentiAbilitati = new HashMap();
        		  for (int i = 0; i < dd.length; i++) {
						for (int j = 0; j < a.length; j++) {
							if (dd[i].equalsIgnoreCase(a[j].getQualifica().getDescrizione())){
								if (!newMapAccreditamentiAbilitati.containsKey(String.valueOf(a[j].getId()))){
									newMapAccreditamentiAbilitati.put(String.valueOf(a[j].getId()),a[j]);
								}
							}
						}
					}
	    	    	a2 = new Accreditamento[newMapAccreditamentiAbilitati.size()];
	    	    	Iterator it = newMapAccreditamentiAbilitati.keySet().iterator();
	    	    	int idx=0;
	    	    	while (it.hasNext()){
	    	    		String key = (String)it.next();
	    	    		a2[idx]= (Accreditamento) newMapAccreditamentiAbilitati.get(key);
	    	    		idx++;
	    	    	}
	    	    	a = a2;
	                ((ProcessData)process.getData()).setElencoAccreditamenti(a2);
	                request.setAttribute("accreditamenti", a2);
        	  }
          }

      } catch (IOException e) {
          logger.error(e);
          return false;
      } catch (ServletException ex) {
          // 
          logger.error("", ex);
          return false;
      }
     */
      return true;
  }
  protected int getCurrentStepIndex(AbstractPplProcess process) {
      return process.getView().getCurrentActivity().getCurrentStepIndex();
  }
  
  public void setStep(AbstractPplProcess pplProcess, int index) {
      pplProcess.getView().getCurrentActivity().setCurrentStepIndex(index);
  }
  
  public void updateWorkflow2(AbstractPplProcess process) {
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
//	    if (lastStep.getState().equals(StepState.COMPLETED)) {
//	      currentActivity.setState(ActivityState.ACTIVE);
//	      lastStep.setState(StepState.ACTIVE);
//	    }
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
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  private IServiceProfile getServiceProfileServizioAccreditamento(IRequestWrapper _request) throws IOException, ServletException {
	    String PATH_SEP = "/";
	    HttpServletRequest request = (HttpServletRequest) _request.getUnwrappedRequest();
	    HttpSession session = request.getSession();
	    String queryString = request.getQueryString();
	    Hashtable parsedQueryStringMap = Utilities.parseQueryString(queryString);
	    boolean isInitProcess = true;
	    String idComune = (String)session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE_ID);
	    if (isInitProcess) {    	
	        // FIX: Concessione e Autorizzazione - 22/12/05 - Michele Fabbri Cedaf s.r.l.
		    // la prima parte dell'if gestisce il caso dei bookamark
		    // con il caricamento da DB
			IServiceProfile serviceProfile = null;
			if (isBookmarkProcess(parsedQueryStringMap)) {
				// Carico il service profile associato al bookmark richiesto
	    		IServiceProfileFactory spf = ServiceProfileFactoryFromDB.getInstance();
	    		try {
	    			serviceProfile = spf.loadServiceProfile(new Hashtable(parsedQueryStringMap));
	    		} catch(Exception e) {
	    			// se ci sono problemi con il file viene caricato automaticamente il default service profile
	    			logger.warn("doFilter() - Eccezione durante il caricamento del service profile:" + e.getMessage());
	    			logger.warn("doFilter() - Sostituzione del service profile del servizio con il default Service Profile...");
	    			serviceProfile = getDefaultServiceProfile(request);    			
	    		}
	    	} else {
	    		// Carico service profile associato al servizio richiesto
	    		IServiceProfileFactory spf = ServiceProfileFactory.getInstance();
	    		String[] processNameValues = (String[])parsedQueryStringMap.get("processName");
	    		String processName = null;
	    		String serviceProfileResource = PATH_SEP;
	    		if (processNameValues != null)
	    			processName = (processNameValues.length >0) ? processNameValues[0] : null;
	    		if (processName==null) {
	    			logger.error("doFilter() - Invalid query String in initProcess: processName =" + processName );
	    			serviceProfileResource = null;
	    		} else {
	    			// Start FIX: Caricamento serviceprofile multi-ente - 2006-07-24 - Paolo Selvini CEFRIEL
	    			serviceProfileResource =
	    				PATH_SEP + processName.replaceAll( "\\.", "/" )
						+ PATH_SEP+ "risorse" + PATH_SEP + "serviceprofile_" + idComune + ".xml";
	    			// End FIX: Caricamento serviceprofile multi-ente - 2006-07-24 - Paolo Selvini CEFRIEL
	    		}
	    		
	    		try {
	    			serviceProfile = spf.loadServiceProfile(serviceProfileResource);
	    		} catch (Exception e) {
	    			logger.warn(
	    					"doFilter() - Eccezione durante il caricamento del service profile del comune con codice " + idComune + ". Resource path:  : serviceProfileResource = "
	    					+ serviceProfileResource + ": " + e.getMessage());
	    			logger.warn(
	    					"doFilter() - Sostituzione del service profile del servizio con un " +
	    					"Service Profile generico...");
	    			serviceProfileResource =
	    				PATH_SEP + processName.replaceAll( "\\.", "/" )
						+ PATH_SEP+ "risorse" + PATH_SEP + "serviceprofile.xml";
	      		try {
	      			serviceProfile = spf.loadServiceProfile(serviceProfileResource);
	      		} catch (Exception ex) {
	      			// se ci sono problemi anche con il file generico viene caricato automaticamente il default service profile
	      			logger.warn(
	      					"doFilter() - Eccezione durante il caricamento del service profile. Resource path:  : serviceProfileResource = "
	      					+ serviceProfileResource + ": " + e.getMessage());
	      			logger.warn(
	      					"doFilter() - Sostituzione del service profile del servizio con il " +
	      					"default Service Profile...");
	      			//ex.printStackTrace();
	      			serviceProfile = getDefaultServiceProfile(request);    
	      		}    			
		      }
	   			// End FIX: Caricamento serviceprofile multi-ente - 2006-07-24 - Paolo Selvini CEFRIEL
	    	}
	      
			// Imposta in sessione il service profile corrente
	    	session.setAttribute(SiracConstants.SIRAC_CURRENT_SERVICE_PROFILE, serviceProfile);

	    	// Annulla la variabile di sessione associata a autenticazione forte
			// In questo modo la nuova richiesta di servizio scatener� se necessario la procedura di autenticazione forte
			// La variabile di sessione con il flag di autenticazione debole viene invece mantenuta fra pi� accessi a servizi
			//session.removeAttribute("SIRAC_CUR_SERVICE_STRONG_AUTHENTICATION_COMPLETED");
	    	return serviceProfile;
	    }
	    
	    

	   return null;

	  }


	protected boolean isBookmarkProcess(Hashtable parsedQueryStringMap) {
		  logger.debug("Nome parametro di bookmark atteso: '" + SiracConstants.QUERYSTRING_PARAMNAME_BOOKMARKID +"'");
		  return parsedQueryStringMap.containsKey(SiracConstants.QUERYSTRING_PARAMNAME_BOOKMARKID);
	}
	
	private ServiceProfile getDefaultServiceProfile(HttpServletRequest request){
		ServiceProfile sp = (ServiceProfile)request.getSession().getAttribute("serviceProfileDefault");
		return sp;
	}

}

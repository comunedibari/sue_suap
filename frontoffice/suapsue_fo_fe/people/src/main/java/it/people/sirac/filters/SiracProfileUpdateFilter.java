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
package it.people.sirac.filters;

import it.people.core.PplUser;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.serviceProfile.ServiceProfile;
import it.people.sirac.util.Utilities;
import it.people.util.ServiceParameters;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiracProfileUpdateFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SiracProfileUpdateFilter.class);
  
  protected String profileUpdatePage = null;
  protected String updatedProfileActivationServiceURL = null;

  public void init(FilterConfig filterConfig) throws ServletException {
    profileUpdatePage =  filterConfig.getInitParameter("profileUpdatePage");
    updatedProfileActivationServiceURL = filterConfig.getInitParameter("updatedProfileActivationServiceURL");
  }

  public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _chain) throws IOException, ServletException {
    
	if (logger.isDebugEnabled()) {
      logger.debug("doFilter - start");
    }
 
    HttpServletRequest request = (HttpServletRequest) _request;
    HttpServletResponse response = (HttpServletResponse) _response;
    HttpSession session = ((HttpServletRequest)request).getSession();
    Accreditamento accrSel = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
    
    String requestURL = request.getRequestURI();
    requestURL = requestURL.substring(requestURL.indexOf("/", 1));

    String queryString = request.getQueryString();
    
    String completeURL = requestURL + ((queryString!=null) ? "?"+queryString : "");
    if (logger.isDebugEnabled()) {
      logger.debug("ProfileUpdateFilter - requestURL is: " + requestURL);
      logger.debug("ProfileUpdateFilter - completeURL is: " + completeURL);
    }
    
    // determino se � consentito l'accesso anonimo 
    ServiceProfile currentServiceProfile = (ServiceProfile)session.getAttribute(SiracConstants.SIRAC_CURRENT_SERVICE_PROFILE);
    boolean isAnonymousAccessAllowed = false;
    if(currentServiceProfile != null) {
    	isAnonymousAccessAllowed = SiracHelper.isAnonymousUserAccessAllowedForService(currentServiceProfile);
    }
    
    
    // determino se l'utente attuale � anonimo 
    boolean isAnonymousUser = SiracHelper.isAnonymousUser((String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER));
    
    // � richiesto l'update del profilo nel caso in cui si stia entrando in un servizio
    // con un accreditamento di tipo Operatore Associazione di Categoria o Rappresentante
    // Associazione di Categoria
    boolean accreditamentoAssociazioneCategoria = "OAC".equals(accrSel.getQualifica().getIdQualifica()) || "RCT".equals(accrSel.getQualifica().getIdQualifica());

    String requestedPeopleService = getRequestedPeopleService(request);
    ServiceParameters serviceParams = new ServiceParameters(requestedPeopleService, SiracHelper.getIdComune(session));
    
    // determino se per questo servizio � stato specificato di disabilitare il filtro di aggiornamento del profilo per
    // operatori e rappresentanti di associazioni di categoria
    boolean mustDisableFilterForService = 
    	new Boolean(serviceParams.get("disabilitaFiltroAggiornamentoProfiloPerOperatori")).booleanValue();
    
    // determino se ho richiesto una abortSign
//    boolean isAbortSign = "/abortSign.do".equals(requestURL);
    
    // determino se sto entrando in un servizio (creazione o caricamento)
    boolean isInitProcess = SiracHelper.isPeopleInitProcess(request);
    
    // determino se � stata richiamata la action che gestisce il ritorno dal MIP - pagamenti
    boolean isReturnFromPayment = "/returnFromPayment.do".equals(requestURL);
    
    // determino se la InitProcess invocata � in realt� una loadProcess
    boolean isLoadProcess = (queryString!=null && queryString.indexOf("processId")>=0);
    
    // determino se sono subito dopo la InitProcess in modo da poter presentare la pagina con il riepilogo dei dati caricati e
    // la richiesta di aggiornamento del domicilio elettronico
    boolean isPostLoadProcess = "/postLoadProcess.do".equals(requestURL);
    
    // se sono in una InitProcess ma non sto caricando una pratica, viene mostrata la pagina per l'aggiornamento dei profili
    // richiedente e titolare, oltre al domicilio elettronico
    boolean editAllowed = !isPostLoadProcess;
    
//    if(isReturnFromPayment){
//    	session.setAttribute(UtilHelper.PEOPLE_RETURNING_FROM_PAYMENT, "true");
//    }
//    
//    boolean whileReturningFromPayment = session.getAttribute(UtilHelper.PEOPLE_RETURNING_FROM_PAYMENT)!=null;
    
//    if(isInitProcess) {
//    	int processId_start = queryString.indexOf("processId=");
//    	if(processId_start >=0){
//	    	int processId_end = queryString.indexOf("&", processId_start);
//	    	if(processId_end>=0){
//	    		String processId = queryString.substring(processId_start+10, processId_end);
//		    	
//		    	try {
//					ProcessStatusTable processStatusTable = StatusHelper.getProcessStatusTableFromProcessId(processId);
//					if (processStatusTable != null) {
//						isReturnFromPayment = true;
//					}
//				} catch (Exception e) {
//					logger.error("ProfileUpdateFilter - error while retrieving process payment status: " + e.getMessage());
//				}
//	    	}
//    	} 
//    }
    
    //  se la pagina di aggiornamento del profilo � stata appena mostrata non sar� mostrata di nuovo
    boolean updatePageAlreadyShown = session.getAttribute(UtilHelper.SIRAC_UPDATE_PAGE_ALREADYSHOWN)!=null;
    if(updatePageAlreadyShown) {
		session.removeAttribute(UtilHelper.SIRAC_UPDATE_PAGE_ALREADYSHOWN);
    }
    
    boolean servizioDiAccreditamento = false;
    if (queryString!=null) {
    	servizioDiAccreditamento = queryString.indexOf("processName=it.people.fsl.servizi.admin.sirac.accreditamento")!=-1;
    }
    // regole per la visualizzazione della pagina di aggiornamento del profilo
    boolean mustShowProfileUpdatePage = !servizioDiAccreditamento &&
    									!mustDisableFilterForService && 
    									!updatePageAlreadyShown &&
    									accreditamentoAssociazioneCategoria && 
    									(
    											(isInitProcess && !isLoadProcess && !isPostLoadProcess && !(isAnonymousAccessAllowed && isAnonymousUser) && !isReturnFromPayment) ||
    											(isPostLoadProcess && !(isAnonymousAccessAllowed && isAnonymousUser)) && !isReturnFromPayment);
    
    // al ritorno da un pagamento, dopo che l'attributo di sessione impostato sopra � stato utilizzato per
    // determinare se la pagina di aggiornamento del profilo deve essere visualizzata, tale attributo
    // viene rimosso dalla sessione
//    if(isPostLoadProcess && session.getAttribute(UtilHelper.PEOPLE_RETURNING_FROM_PAYMENT)!=null){
//    	session.removeAttribute(UtilHelper.PEOPLE_RETURNING_FROM_PAYMENT);
//    }
    
    // l'attributo di sessione "currentLoggedUser" viene rimosso in modo da invalidare il
    // PeopleContext e fare in modo che il menu laterale venga aggiornato con i nuovi dati
    // (nome, cognome, profili)
    PplUser pplUser = (PplUser)session.getAttribute("currentLoggedUser");
	if(pplUser != null) {
		if(accreditamentoAssociazioneCategoria) {
			pplUser.setUserID(accrSel.getProfilo().getCodiceFiscaleIntermediario());
	    	pplUser.setUserName(accrSel.getProfilo().getDenominazione());
	    }
	}

    if(mustShowProfileUpdatePage) {
    	// se � stato determinato che deve essere visualizzata la pagina di aggiornamento del profilo 
    	// si procede popolando il bean dei profili con i dati da visualizzare
    	
    	// anzitutto se � consentito l'edit dei dati sulla pagina si svuotano i profili richiedente e 
    	// titolare inizializzandoli rispettivamente con una persona fisica e una giuridica vuote
    	if(editAllowed) {
    		session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, new ProfiloPersonaFisica());
    		session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, new ProfiloPersonaGiuridica());
    	}
    	
    	// si leggono dalla sessione i profili richiedente e titolare (utile nel caso in cui non ne � consentita la modifica
    	// come nel caso di caricamento di una pratica salvata
    	ProfiloPersonaFisica profiloRichiedente = (ProfiloPersonaFisica)session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE);
    	AbstractProfile profiloTitolare = (AbstractProfile)session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE);
    	
    	// viene popolato il bean con i dati dei profili e il domicilio elettronico
    	ProfiloRichiedenteTitolareBean profiliBean = new ProfiloRichiedenteTitolareBean();
    	profiliBean.setProfiloRichiedente(profiloRichiedente);
    	profiliBean.setProfiloTitolare(profiloTitolare);
    	profiliBean.setEditAllowed(editAllowed);

    	String savedDomicilioElettronico = (String)session.getAttribute(UtilHelper.PEOPLE_SAVED_DOMICILIOELETTRONICO);
    	// se � stato precedentemente salvato un domicilio elettronico lo si propone,
    	// altrimenti si usa quello presente nel profilo di accreditamento
    	if(savedDomicilioElettronico == null){
    		profiliBean.setDomicilioElettronicoAssociazione(accrSel.getProfilo().getDomicilioElettronico());
    	} else {
    		profiliBean.setDomicilioElettronicoAssociazione(savedDomicilioElettronico);
    		session.removeAttribute(UtilHelper.PEOPLE_SAVED_DOMICILIOELETTRONICO);
    	}
    	profiliBean.setFormAction(updatedProfileActivationServiceURL);
    	profiliBean.setTarget(completeURL);
    	
    	request.setAttribute("profiloRichiedenteTitolareBean", profiliBean);
        String serviceResource = profileUpdatePage;
        
        // viene mostrata la pagina di aggiornamento del profilo
        RequestDispatcher rd = _request.getRequestDispatcher(serviceResource);
        if (logger.isDebugEnabled()) {
        	logger.debug("doFilter - end");
        }
        rd.forward(request, response);
        
    } else { // L'utente ha gi� aggiornato il profilo
    	
        if (logger.isDebugEnabled()) {
        	logger.debug("doFilter - end");
        }
        _chain.doFilter(request, response);
    }
  }

  public void destroy() {

  }
  
  /*
   * Metodo di utilit� locale per determinare il processo People richiesto dall'utente
   */
  private String getRequestedPeopleService(HttpServletRequest request){

	  String queryString = request.getQueryString();
	  Hashtable parsedQueryStringMap = Utilities.parseQueryString(queryString);
	  String[] processNameValues = (String[])parsedQueryStringMap.get("processName");
	  if(processNameValues != null) {
		  return processNameValues[0];
	  } else {
		  return ((it.people.process.GenericProcess)request.getSession().getAttribute("pplProcess")).getProcessName();
	  }
  }

}

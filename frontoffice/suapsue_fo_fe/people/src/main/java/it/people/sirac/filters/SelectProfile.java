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

import it.people.PeopleConstants;
import it.people.core.PplUser;
import it.people.core.PplUserData;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.process.GenericProcess;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.Accreditamento;
import it.people.sirac.accr.beans.ProfiloLocale;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.util.GroupsAccreditations;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.serviceProfile.IServiceProfile;
import it.people.sirac.serviceProfile.IServiceProfileFactory;
import it.people.sirac.serviceProfile.ServiceProfile;
import it.people.sirac.serviceProfile.ServiceProfileFactory;
import it.people.sirac.serviceProfile.ServiceProfileFactoryFromDB;
import it.people.sirac.services.accr.IAccreditamentoClientAdapter;
import it.people.sirac.util.Utilities;
import it.people.util.ServiceParameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

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

public class SelectProfile implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SelectProfile.class);
	  
	protected String profileUpdatePage = null;
	protected String updatedProfileActivationServiceURL = null;
	IAccreditamentoClientAdapter accrWSClient = null;
	String IAccreditamentoServiceURLString="";
	IServiceProfile defaultServiceProfile = null;

	public void init(FilterConfig filterConfig) throws ServletException {
//		System.out.println("INIT FILTER - SelectProfile");
		profileUpdatePage =  filterConfig.getInitParameter("profileUpdatePage");
		updatedProfileActivationServiceURL = filterConfig.getInitParameter("updatedProfileActivationServiceURL");
		  
	    IAccreditamentoServiceURLString = SiracHelper.getIAccreditamentoServiceURLString(filterConfig.getServletContext());
	    accrWSClient = SiracHelper.getIAccreditamentoClientAdapter(filterConfig.getServletContext());
		      
	    String defaultServiceProfileResource = filterConfig.getInitParameter("defaultserviceprofile");
		      
		// Carico service profile associato al servizio richiesto
	    IServiceProfileFactory spf = ServiceProfileFactory.getInstance();
		      
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("init() - Caricamento default service profile : defaultServiceProfile = "+ defaultServiceProfileResource);
		    }

		    this.defaultServiceProfile = spf.loadServiceProfile(defaultServiceProfileResource);
		} catch (Exception e) {
			logger.warn("init() - Eccezione durante il caricamento del default service profile. Resource path:  : serviceProfileResource = "
					+ defaultServiceProfileResource + ": " + e.getMessage());
			// se ci sono problemi con il file viene caricato automaticamente il default service profile 
			// creato da ServiceProfileFactory
			logger.warn("init() - Sostituzione con default service profile creato da ServiceProfileFactory...");
			this.defaultServiceProfile = ServiceProfileFactory.getDefaultServiceProfile();
			logger.warn("init() - defaultServiceProfile = " + defaultServiceProfile);
		}
		      
		if (logger.isDebugEnabled()) {
			logger.debug("init(filterConfig) - end");
		}	  
	}

	public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _chain) throws IOException, ServletException {
	    
		if (logger.isDebugEnabled()) {
			logger.debug("doFilter - start");
		}
	    HttpServletRequest request = (HttpServletRequest) _request;
	    HttpServletResponse response = (HttpServletResponse) _response;
	    HttpSession session = ((HttpServletRequest)request).getSession();
	    String queryString = request.getQueryString();
	    String processName = "";
	    String accreditato="";
	    HashMap param = new HashMap();
	    if (queryString!=null){
	    	String[] queryStringToken = queryString.split("&");
	    	if (queryStringToken!=null){
	    		for (int i = 0; i < queryStringToken.length; i++) {
					String[] par = queryStringToken[i].split("=");
					if (par!=null && par.length==2){
						param.put(par[0], par[1]);
					}
				}
	    	}
	    }
	    if (param.get("processName")!=null){
	    	processName=(String)param.get("processName");
	    }
	    if (param.get("accreditato")!=null){
	    	accreditato=(String)param.get("accreditato");
	    }
	    session.setAttribute("parametriServizio", param);
	    Accreditamento[] a=null;
	    boolean isAnonymousUser = (session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER)==null);	    
	    if (isAnonymousUser || processName.equalsIgnoreCase("it.people.fsl.servizi.praticheOnLine.visura.myPage") || processName.equalsIgnoreCase("it.people.fsl.servizi.admin.sirac.accreditamento") || accreditato.equalsIgnoreCase("TRUE")   ){
	    	_chain.doFilter(request, response);
	    } else {
	    	try {
	    		
	    		String codiceFiscaleUtente = UtilHelper.getCodiceFiscaleFromProfiloOperatore(session);
	        	String IAccreditamentoURLString = SiracHelper.getIAccreditamentoServiceURLString(session.getServletContext());
	        	IAccreditamentoClientAdapter accr = new IAccreditamentoClientAdapter(IAccreditamentoURLString);
	        	it.people.City city = (it.people.City)session.getAttribute("City");
	        	String idCommune = city.getOid();
	        	a = accr.getAccreditamenti(codiceFiscaleUtente, idCommune);
	    		
	    		
	        	//Gestione accreditamenti dinamici di gruppo
	        	//Verifica esistenza qualifica
	        	PplUserData userData = (PplUserData)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
	        	Qualifica qualifica = accr.getQualificaById(userData.getRuolo());
	        	userData.setRuoloAbilitato(qualifica != null);
	        	if (userData.isRuoloDefinito() && userData.isRuoloAbilitato()) {
	        		a = GroupsAccreditations.addAccreditation(userData, a, qualifica, idCommune);
	        	}
	        	
	    		
	    	    String requestURL = request.getRequestURI();
	    	    requestURL = requestURL.substring(requestURL.indexOf("/", 1));    		
	    	    ServiceProfile currentServiceProfile = (ServiceProfile)getServiceProfile(_request);
	    	    
	    	    boolean isAnonymousAccessAllowed = false;
	    	    if(currentServiceProfile != null) {
	    	    	isAnonymousAccessAllowed = SiracHelper.isAnonymousUserAccessAllowedForService(currentServiceProfile);
	    	    }
	    	    boolean isAnonymousUser2 = SiracHelper.isAnonymousUser((String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER));
	    	    boolean isInitProcess = SiracHelper.isPeopleInitProcess(request);
	    	    boolean isReturnFromPayment = "/returnFromPayment.do".equals(requestURL);
	    	    boolean isLoadProcess = (queryString!=null && queryString.indexOf("processId")>=0);
	    	    boolean isPostLoadProcess = "/postLoadProcess.do".equals(requestURL);
	    	    boolean mustShowSelectProfilePage = 
					(
							(isInitProcess && !isLoadProcess && !isPostLoadProcess && !(isAnonymousAccessAllowed && isAnonymousUser2) && !isReturnFromPayment) ||
							(isPostLoadProcess && !(isAnonymousAccessAllowed && isAnonymousUser2)) && !isReturnFromPayment);	    	    
	    	    
	    	    if (mustShowSelectProfilePage){
		        	
		        	if (a==null||a.length<1){
		        		_chain.doFilter(request, response);
		        		return;
		        	} else {
		        		// System.out.println("Trovati "+a.length+" profili per l'utente "+codiceFiscaleUtente);
		        		String serviceResource = profileUpdatePage;
		        		session.setAttribute("servizioSel",processName);
		     	        session.setAttribute("serviceProfileDefault",(ServiceProfile) defaultServiceProfile);
		     	        RequestDispatcher rd = _request.getRequestDispatcher(serviceResource);
		     	        if (logger.isDebugEnabled()) {
		     	        	logger.debug("doFilter - end");
		     	        }
		     	        rd.forward(request, response);
		        		
		        		return;
		        	}
		        
	    	    } else {
	    	    	_chain.doFilter(request, response);
	    	    	return;
	    	    }
	    	} catch (Exception e){}
	    }
	  }

	  public void destroy() {
//		  System.out.println("DESTROY FILTER - SelectProfile");
	  }

	  
	  private IServiceProfile getServiceProfile(ServletRequest _request) throws IOException, ServletException {
		    String PATH_SEP = "/";
		    HttpServletRequest request = (HttpServletRequest) _request;
		    HttpSession session = request.getSession();
		    String queryString = request.getQueryString();
		    Hashtable parsedQueryStringMap = Utilities.parseQueryString(queryString);
		    boolean isInitProcess = SiracHelper.isPeopleInitProcess(request);
		    String idComune = (String)session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE_ID);
		    if (isInitProcess) {    	
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
		    			serviceProfile = this.defaultServiceProfile;    			
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
		      			serviceProfile = this.defaultServiceProfile;
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
	  
	  

	  
}

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
package it.people.console.web.controllers;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.PageNavigationSettings;
import it.people.console.domain.AccreditamentiQualifica;
import it.people.console.domain.AccreditamentiQualificheFilter;
import it.people.console.domain.AccreditamentiVisibilitaQualifica;
import it.people.console.domain.FENodeToNodeCopy;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer;
import it.people.console.web.controllers.utils.NodeToNodeCopier;
import it.people.console.web.controllers.utils.AccreditamentiManagementViewer.AccreditamentiManagementViewerException;
import it.people.console.web.controllers.validator.AccreditamentiQualificheValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;
import it.people.sirac.accr.beans.Qualifica;
import it.people.sirac.accr.beans.VisibilitaQualifica;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWS;
import it.people.sirac.services.accr.management.IAccreditamentiManagementWSServiceLocator;

/**
 * @author Andrea Piemontese
 *
 */
@Controller
@RequestMapping("/Accreditamenti")
@SessionAttributes({"accrVisibilitaQualifica" })
public class AccreditamentiVisibilitaQualificheController extends
		MessageSourceAwareController {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private IAccreditamentiManagementWS accrService = null;
	
	//Valori combobox
	private List<PairElement<String, String>> feNodeList = null;
	
	//Single node
	private boolean singleNode = false; 
	

	public AccreditamentiVisibilitaQualificheController() {
		this.setCommandObjectName("accreditamentiVisibilitaQualifica");
	}

	
	@RequestMapping(value = "/accreditamentiVisibilitaQualifiche.do", method = RequestMethod.GET)
	public String setupForm(ModelMap model, HttpServletRequest request) {

		
		//init domain bean
		AccreditamentiVisibilitaQualifica accrVisQualifica = null;
		
		if (request.getSession().getAttribute("accrVisibilitaQualifica") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("accreditamentiVisibilitaQualifiche.do")) {
			accrVisQualifica = new AccreditamentiVisibilitaQualifica(3);
    	}
    	else {
    		accrVisQualifica = (AccreditamentiVisibilitaQualifica)
    				request.getSession().getAttribute("accrVisibilitaQualifica");
    	}

		//get Accreditamenti Management WS
		this.accrService = getAccrService(request);
		//Valori combobox presi da WS
		feNodeList = getComuni();
		

    	this.setPageInfo(model, "accreditamentiVisibilitaQualifiche.page1.title", 
    			"accreditamentiVisibilitaQualifiche.page1.subtitle", "accreditamentiVQ"); 
    	
    	
		//Se solo un nodo, salta selezione nodi
		if (this.feNodeList.size() == 1) {
			this.singleNode = true;
			accrVisQualifica.nextPage();
			accrVisQualifica.setSelectedNodeId(this.feNodeList.get(0).getValue());
			//Prepara diettamente pagina 2
			this.validateAndPreparePage(accrVisQualifica.getPage(), model, accrVisQualifica, null, request);
		} else {
			this.singleNode = false;
		}
		
		//Inserisci dati nel model
		model.addAttribute("accrVisibilitaQualifica", accrVisQualifica);
		
		model.addAttribute("feNodeList", this.feNodeList);
		
		model.put("includeTopbarLinks", true);
		
		model.put("page", accrVisQualifica.getPage());

		model.put("sidebar", "/WEB-INF/jsp/accreditamenti/sidebar.jsp");
		
		model.put("singleNode", singleNode);
    	
    	
		return getStaticProperty("accreditamenti.accreditamentiVisibilitaQualifiche.view");
	}
	
	
    @RequestMapping(value = "/accreditamentiVisibilitaQualifiche.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("accrVisibilitaQualifica") 
    	AccreditamentiVisibilitaQualifica accrVisQualifica, BindingResult result, HttpServletRequest request) {
    	
    	boolean isNextPage = isPrefixParamInRequest(request, "nextPage");
    	boolean isPreviousPage = isPrefixParamInRequest(request, "previousPage");
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	boolean isClose = isPrefixParamInRequest(request, "close");
    	

    	//Gestisci navigazione pagine
    	if (isNextPage) {
    		accrVisQualifica.nextPage();
    	}
    	
    	if (isPreviousPage) {
        	//validator.validate(feServiceRegistration, result);
    		accrVisQualifica.previousPage();
    	}
    	

    	//Elabora richiesta
    	if (!isClose) {
    		validateAndPreparePage(accrVisQualifica.getPage(), model, 
    				accrVisQualifica, result, request);
    	}
    	
    	//Esci o ripeti wizard
    	if (isCancel || isClose) {
    		
        	if (accrVisQualifica.isRipetiWizard()) {
        		return  "redirect:accreditamentiVisibilitaQualifiche.do";

        	} 
        	else {
    			//ritorna a gestionq qualifiche
        		return  "redirect:accreditamentiQualifiche.do";
        	}
    	}

    	return getStaticProperty("accreditamenti.accreditamentiVisibilitaQualifiche.view");
    }
    
    
    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		AccreditamentiVisibilitaQualifica accrVisQualifica,
    		BindingResult result, 
    		HttpServletRequest request) {
    	
    	switch(pageNumber) {
    		case 1:
    			
    			accrVisQualifica.setSelectedNodeName(getNomeEnte(accrVisQualifica.getSelectedNodeId()));
    			
    			model.addAttribute("feNodeList", this.feNodeList);
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
        		model.addAttribute("accrVisibilitaQualifica", accrVisQualifica);
            	
        		model.put("page", accrVisQualifica.getPage());
        		
        		model.put("singleNode", singleNode);
            	
            	this.setPageInfo(model, "accreditamentiVisibilitaQualifiche.page1.title", 
            			"accreditamentiVisibilitaQualifiche.page1.subtitle", "accreditamentiVQ"); 
    			break;
    		case 2:

    			accrVisQualifica.setSelectedNodeName(getNomeEnte(accrVisQualifica.getSelectedNodeId()));
    			
            	
        		//recupera la visibilità delle qualifiche
        		VisibilitaQualifica[] visQualifiche = retrieveVisibilitaQualifiche(accrVisQualifica);
            	accrVisQualifica.setVisibilitaQualifiche(Arrays.asList(visQualifiche));
            	//inizializza le checkbox con valori qualifiche
            	accrVisQualifica.initSelectedQualificheCheckbox();
            	
            	this.setPageInfo(model, "accreditamentiVisibilitaQualifiche.page2.title", 
							"accreditamentiVisibilitaQualifiche.page2.subtitle", "accreditamentiVQ");
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

        		model.addAttribute("accrVisibilitaQualifica", accrVisQualifica);
        		
        		model.put("singleNode", singleNode);
            	
        		model.put("page", accrVisQualifica.getPage());
            	
    			break;
    		case 3:
    			
    			//Aggiorna visibilità in base alle checkbox
    			accrVisQualifica.updateVisibilitaQualificheFromCheckbox();
    			//Salva la visibilità
    			saveVisibilitaQualifiche(accrVisQualifica);
    			
            	this.setPageInfo(model, "accreditamentiVisibilitaQualifiche.page3.title", 
						"accreditamentiVisibilitaQualifiche.page3.subtitle", "accreditamentiVQ");
            	
            	model.put("includeTopbarLinks", true);
            	
            	model.addAttribute("accrVisibilitaQualifica", accrVisQualifica);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

        		model.put("page", accrVisQualifica.getPage());
        		
        		model.put("singleNode", singleNode);
            	
    			break;
    			
    	}
    }
    
    
	/**
	 * @param accrVisQualifica
	 */
	private VisibilitaQualifica[] retrieveVisibilitaQualifiche(AccreditamentiVisibilitaQualifica accrVisQualifica) {
		
		VisibilitaQualifica[] visibilitaQualifiche = null;
		try {
			
			//Recupera le qualifiche setta le checkbox
			String feNodeId = accrVisQualifica.getSelectedNodeId();
			visibilitaQualifiche = this.accrService.getVisibilitaQualifiche(feNodeId);

		} catch (RemoteException e) {
			logger.error("Non è stato possibile recuperare la visibilità delle qualifiche per l'ente. " + e.getMessage());
		}
		return visibilitaQualifiche;
	}
	
	
	/**
	 * @param accrVisQualifica
	 */
	private void saveVisibilitaQualifiche(AccreditamentiVisibilitaQualifica accrVisQualifica) {
		
		try {
			
			String feNodeId = accrVisQualifica.getSelectedNodeId();
			//List to Array
			VisibilitaQualifica[] visQualificheArray = accrVisQualifica.getVisibilitaQualifiche()
					.toArray(new VisibilitaQualifica[accrVisQualifica.getVisibilitaQualifiche().size()]);
			//Update tramite WS
			this.accrService.setVisibilitaQualifiche(feNodeId, visQualificheArray);

		} catch (RemoteException e) {
			logger.error("Non è stato possibile salvare la visibilità delle qualifiche per l'ente. " + e.getMessage());
		}
	}
	
	
	


	private IAccreditamentiManagementWS getAccrService(HttpServletRequest request) {
		
		String endpointURLString =  request.getSession().getServletContext()
				.getInitParameter("people.sirac.webservice.accreditamentiManagement.address");
		
		IAccreditamentiManagementWSServiceLocator accrClientLocator = new IAccreditamentiManagementWSServiceLocator();
		accrClientLocator.setIAccreditamentiManagementWSEndpointAddress(endpointURLString);
		IAccreditamentiManagementWS service = null;
		try {
			service = accrClientLocator.getIAccreditamentiManagementWS();
		} catch (ServiceException e) {
			logger.error("Non è stato possibile creare il client per il web service Accreditamenti.");
		}
		
    	return service;
    }
    
	/**
	 * @return la lista degli enti presenti
	 */
	private List<PairElement<String, String>> getComuni() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		String nomeComune = "";
		String codiceComune = "";
		
	    String query = "SELECT comune, codicecomune FROM fenode";
	    		
	    Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			
			while(resultSet.next()) {
				nomeComune = resultSet.getString("comune");
				codiceComune = resultSet.getString("codicecomune");	
				
				result.add(new PairElement<String, String>(codiceComune, nomeComune));
			}
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    
		return result;
	}
		
	/**
	 * @param id idcomune
	 * @return reference e codicecomune, parametri per la connessione
	 */
	private String getNomeEnte(String id_comune) {

		String nomeEnte = "";

        String query = "SELECT comune FROM fenode WHERE codicecomune = " + id_comune;
        
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				nomeEnte = resultSet.getString("comune");
			}
			resultSet.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nomeEnte;
	}


}

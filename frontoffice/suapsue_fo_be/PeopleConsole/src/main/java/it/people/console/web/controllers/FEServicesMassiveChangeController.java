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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.domain.FEServicesMassiveChange;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.FeServicesMassiveChangeExecutor;
import it.people.console.web.controllers.validator.FEServicesMassiveChangeValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziFe/ModificaMassiva/")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feServicesMassiveChange", "feNodesList"})
public class FEServicesMassiveChangeController extends MessageSourceAwareController {

	private FEServicesMassiveChangeValidator validator;

	@Autowired
	private IPersistenceBroker persistenceBroker;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

//	private List<PairElement<String, String>> logLevels = null;
//
//	private List<PairElement<String, String>> statusTypes = null;
//
//	private List<PairElement<String, String>> signProcessTypes = null;
//	
//	private List<PairElement<String, String>> includeAttachmentsInReceiptTypes = null;
//
//	private List<PairElement<String, String>> sendMailToOwnerTypes = null;
	
	@Autowired
	public FEServicesMassiveChangeController(FEServicesMassiveChangeValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feServicesMassiveChange");
	}
	
    @RequestMapping(value = "/modifica.do", method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "tipo", required = false) String action, ModelMap model, HttpServletRequest request) {

    	FEServicesMassiveChange feServicesMassiveChange = null;
    	
    	if (logger.isDebugEnabled()) {
        	logger.debug("Referer = " + WebUtils.getReferer(request));
    	}
    	
    	if (request.getSession().getAttribute("feServicesMassiveChange") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("ServiziFe/ModificaMassiva/parametri.do")) {
    		feServicesMassiveChange = new FEServicesMassiveChange(4);

        	
    	}
    	else {

    		feServicesMassiveChange = (FEServicesMassiveChange)request.getSession().getAttribute("feServicesMassiveChange");
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", persistenceBroker.getFeNodesList());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("feServicesMassiveChange", feServicesMassiveChange);
    	
    	model.put("page", feServicesMassiveChange.getPage());

    	model.put("action", action);

    	String operation = "massiveChange";
    	if (action.equalsIgnoreCase("elimina")) {
    		operation = "massiveDeletion";
    	}

    	model.put("operation", operation);
    	
    	this.setPageInfo(model, "feservice." + operation + ".title", 
				"feservice." + operation + ".page1.subtitle", "feServiceMC");
    	
    	return getStaticProperty("feservices.parametersMassiveChange.view");
    	
    }
    

    @RequestMapping(value = "/modifica.do", method = RequestMethod.POST)
    public String processSubmit(@RequestParam(value = "tipo", required = false) String action, ModelMap model, 
    		@ModelAttribute("feNodesList") 
    		List<TripleElement<String, String, String>> feNodesList, 
    		@ModelAttribute("feServicesMassiveChange") 
    		FEServicesMassiveChange feServicesMassiveChange,     		
    		BindingResult result, 
    		HttpServletRequest request) {

    	boolean isNextPage = isPrefixParamInRequest(request, "nextPage");
    	boolean isPreviousPage = isPrefixParamInRequest(request, "previousPage");
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	boolean isClose = isPrefixParamInRequest(request, "close");
    	boolean isChangeToNodeType = !isNextPage && !isPreviousPage && !isCancel;
    	
    	if (feNodesList == null) {
    		feNodesList = persistenceBroker.getFeNodesList();
    	}
    	
	    	if (isNextPage) {
	    		validator.validate(feServicesMassiveChange, result, isChangeToNodeType, action);
	        	//validator.validate(feServiceRegistration, result);
	    		if (!result.hasErrors()) {
	    			feServicesMassiveChange.nextPage();    		
	    		}
			}

	    	if (isPreviousPage) {
	        	//validator.validate(feServiceRegistration, result);
	    		feServicesMassiveChange.previousPage();
	    	}
		
	    	if (!isClose) {
		    	validateAndPreparePage(feServicesMassiveChange.getPage(), model, 
		    			feServicesMassiveChange, feNodesList, result, request, action);
	    	}
    	
    	if (isCancel || isClose) {
    		return  "redirect:/ServiziFe/elenco.do";
    	}
    	
		return getStaticProperty("feservices.parametersMassiveChange.view");
    	
    }
    
    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		FEServicesMassiveChange feServicesMassiveChange, 
    		List<TripleElement<String, String, String>> feNodesList, BindingResult result, 
    		HttpServletRequest request, String action) {

		FeServicesMassiveChangeExecutor feServicesMassiveChangeExecutor = new FeServicesMassiveChangeExecutor();

    	String operation = "massiveChange";
    	if (action.equalsIgnoreCase("elimina")) {
    		operation = "massiveDeletion";
    	}

    	model.put("operation", operation);
    	
    	this.setPageInfo(model, "feservice." + operation + ".title", 
				"feservice." + operation + ".page" + pageNumber + ".subtitle", "feServiceMC");
		
    	switch(pageNumber) {
    		case 1:

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("feServicesMassiveChange", feServicesMassiveChange);
            	
            	model.put("page", feServicesMassiveChange.getPage());
            	
    		case 2:


            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("feServicesMassiveChange", feServicesMassiveChange);
            	
            	model.put("page", feServicesMassiveChange.getPage());
            	
            	feServicesMassiveChange.setAvailableServices(persistenceBroker.getFeNodesAvailableServices(feServicesMassiveChange.getSelectedNodesToShow()));
    			
    			break;
    		case 3:

    			feServicesMassiveChangeExecutor.prepareCompleteServicesList(feServicesMassiveChange);

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.FE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION)) {
    			
	    	    	model.addAttribute("logLevels", this.getLogLevelsList());
	
	    	    	model.addAttribute("statusTypes", this.getStatusTypesList());
	
	    	    	model.addAttribute("signProcessTypes", this.getSignProcessTypesList());
	
	    	    	model.addAttribute("includeAttachmentsInReceiptTypes", this.getIncludeAttachmentsInReceiptTypesList());
	
	    	    	model.addAttribute("sendMailToOwnerTypes", this.getSendMailToOwnerTypesList());
	    	    	
    			}
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", feServicesMassiveChange.getPage());
            	
    			break;
    		case 4:

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.FE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION)) {
        			model.put("registrationLog", 
        					feServicesMassiveChangeExecutor.doMassiveParametersChange(this.dataSourcePeopleDB, feServicesMassiveChange));
    			}

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.FE_SERVICES_CONTROLLER_MASSIVE_DELETE_ACTION)) {
        			model.put("registrationLog", 
        					feServicesMassiveChangeExecutor.doMassiveDeletion(this.dataSourcePeopleDB, feServicesMassiveChange));
    			}
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", feServicesMassiveChange.getPage());
            	
    			break;
    	}

    	model.put("action", action);    	
    	
    }

	private List<PairElement<String, String>> getLogLevelsList() {

		String queryLogLevelsList = "SELECT * FROM LOG ORDER BY 1";

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryLogLevelsList);
			while(resultSet.next()) {
				result.add(new PairElement<String, String>(resultSet.getString(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
		
	}

	private List<PairElement<String, String>> getStatusTypesList() {

		String queryLogLevelsList = "SELECT * FROM STATUS ORDER BY 1";

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryLogLevelsList);
			while(resultSet.next()) {
				result.add(new PairElement<String, String>(resultSet.getString(1), resultSet.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
		
	}

	private List<PairElement<String, String>> getSignProcessTypesList() {

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("0", "Disattivata"));
		result.add(new PairElement<String, String>("1", "Attivata"));
		
		return result;
		
	}

	private List<PairElement<String, String>> getIncludeAttachmentsInReceiptTypesList() {

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();

		result.add(new PairElement<String, String>("0", "Non Includere"));
		result.add(new PairElement<String, String>("1", "Includi"));
		
		return result;
		
	}

	private List<PairElement<String, String>> getSendMailToOwnerTypesList() {

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();

		result.add(new PairElement<String, String>("0", "Non inviare"));
		result.add(new PairElement<String, String>("1", "Invia"));
		
		return result;
		
	}
	
//	/**
//	 * @return the logLevels
//	 */
//	private List<PairElement<String, String>> getLogLevels() {
//		return logLevels;
//	}
//
//	/**
//	 * @param logLevels the logLevels to set
//	 */
//	private void setLogLevels(List<PairElement<String, String>> logLevels) {
//		this.logLevels = logLevels;
//	}
//
//	/**
//	 * @return the statusTypes
//	 */
//	private List<PairElement<String, String>> getStatusTypes() {
//		return statusTypes;
//	}
//
//	/**
//	 * @param statusTypes the statusTypes to set
//	 */
//	private void setStatusTypes(List<PairElement<String, String>> statusTypes) {
//		this.statusTypes = statusTypes;
//	}
//
//	/**
//	 * @return the signProcessTypes
//	 */
//	public final List<PairElement<String, String>> getSignProcessTypes() {
//		return signProcessTypes;
//	}
//
//	/**
//	 * @param signProcessTypes the signProcessTypes to set
//	 */
//	public final void setSignProcessTypes(
//			List<PairElement<String, String>> signProcessTypes) {
//		this.signProcessTypes = signProcessTypes;
//	}
//
//	/**
//	 * @return the includeAttachmentsInReceiptTypes
//	 */
//	public final List<PairElement<String, String>> getIncludeAttachmentsInReceiptTypes() {
//		return includeAttachmentsInReceiptTypes;
//	}
//
//	/**
//	 * @param includeAttachmentsInReceiptTypes the includeAttachmentsInReceiptTypes to set
//	 */
//	public final void setIncludeAttachmentsInReceiptTypes(
//			List<PairElement<String, String>> includeAttachmentsInReceiptTypes) {
//		this.includeAttachmentsInReceiptTypes = includeAttachmentsInReceiptTypes;
//	}
//
//	/**
//	 * @return the sendMailToOwnerTypes
//	 */
//	public final List<PairElement<String, String>> getSendMailToOwnerTypes() {
//		return this.sendMailToOwnerTypes;
//	}
//
//	/**
//	 * @param sendMailToOwnerTypes the sendMailToOwnerTypes to set
//	 */
//	public final void setSendMailToOwnerTypes(
//			List<PairElement<String, String>> sendMailToOwnerTypes) {
//		this.sendMailToOwnerTypes = sendMailToOwnerTypes;
//	}
    
    
}

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.domain.AvailableServicesListOrderer;
import it.people.console.domain.FENodeDeployedServicesRegistration;
import it.people.console.domain.PairElement;
import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.utils.FeServiceRegister;
import it.people.console.web.controllers.validator.FENodeDeployedServicesRegistrationValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.AvailableService;
import it.people.feservice.beans.NodeDeployedServices;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feNodeDeployedServicesRegistration", "feNodesList"})
public class FENodeDeployedServicesRegistrationController extends MessageSourceAwareController {

	private FENodeDeployedServicesRegistrationValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	@Autowired
	public FENodeDeployedServicesRegistrationController(FENodeDeployedServicesRegistrationValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feNodeDeployedServicesRegistration");
	}
	
    @RequestMapping(value = "/registrazioneServiziDispiegatiNodo.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	FENodeDeployedServicesRegistration feNodeDeployedServicesRegistration = null;
    	
    	if (logger.isDebugEnabled()) {
        	logger.debug("Referer = " + WebUtils.getReferer(request));
    	}
    	
    	if (request.getSession().getAttribute("feNodeDeployedServicesRegistration") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("registrazioneServiziDispiegatiNodo.do")) {
    		feNodeDeployedServicesRegistration = new FENodeDeployedServicesRegistration(3);

        	
    	}
    	else {

    		feNodeDeployedServicesRegistration = (FENodeDeployedServicesRegistration)request.getSession().getAttribute("feNodeDeployedServicesRegistration");
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", getFeNodesList());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("feNodeDeployedServicesRegistration", feNodeDeployedServicesRegistration);
    	
    	model.put("page", feNodeDeployedServicesRegistration.getPage());
    	    	
    	this.setPageInfo(model, "feservice.nodeDeployedServiceRegistration.title", 
    							"feservice.nodeDeployedServiceRegistration.page1.subtitle", "feNodeDSR");
    	
    	return getStaticProperty("feservices.nodeDeployedServicesRegistration.view");
    	
    }
    

    @RequestMapping(value = "/registrazioneServiziDispiegatiNodo.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("feNodeDeployedServicesRegistration") 
    		FENodeDeployedServicesRegistration feNodeDeployedServicesRegistration,  
    		BindingResult result, 
    		HttpServletRequest request) {

    	boolean isNextPage = isPrefixParamInRequest(request, "nextPage");
    	boolean isPreviousPage = isPrefixParamInRequest(request, "previousPage");
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	boolean isClose = isPrefixParamInRequest(request, "close");
    	
    	List<PairElement<String, String>> feNodesList = getFeNodesList();

    	if (isNextPage) {
    		validator.validate(feNodeDeployedServicesRegistration, result);
    		if (!result.hasErrors()) {
    			feNodeDeployedServicesRegistration.nextPage(); 	
    		}
		}
		
    	if (isPreviousPage) {
        	//validator.validate(feServiceRegistration, result);
    		feNodeDeployedServicesRegistration.previousPage();
    	}
    	
    	validateAndPreparePage(feNodeDeployedServicesRegistration.getPage(), model, 
    			feNodeDeployedServicesRegistration, feNodesList, result, request);
    	
    	if (isCancel || isClose) {
    		return  "redirect:elenco.do";
    	}
    	
		return getStaticProperty("feservices.nodeDeployedServicesRegistration.view");
    	
    }
    
    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		FENodeDeployedServicesRegistration feNodeDeployedServicesRegistration, 
    		List<PairElement<String, String>> feNodesList, BindingResult result, 
    		HttpServletRequest request) {
    	
    	switch(pageNumber) {
	    	case 1:
	
	    		feNodeDeployedServicesRegistration.setSelectedNodeName(getSelectedNodeName(feNodeDeployedServicesRegistration.getSelectedNodeId(), feNodesList));
	
	        	model.put("includeTopbarLinks", true);
	        	
	        	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
	        	
	        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
	        	
	        	model.addAttribute("feNodeDeployedServicesRegistration", feNodeDeployedServicesRegistration);
	        	
	        	model.put("page", feNodeDeployedServicesRegistration.getPage());
	        	
	        	this.setPageInfo(model, "feservice.nodeDeployedServiceRegistration.title", 
										"feservice.nodeDeployedServiceRegistration.page1.subtitle", "feNodeDSR");
	        	
				
				break;
    		case 2:

        		feNodeDeployedServicesRegistration.setSelectedNodeName(getSelectedNodeName(feNodeDeployedServicesRegistration.getSelectedNodeId(), feNodesList));

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("feNodeDeployedServicesRegistration", feNodeDeployedServicesRegistration);
            	
            	model.put("page", feNodeDeployedServicesRegistration.getPage());
            	
            	this.setPageInfo(model, "feservice.nodeDeployedServiceRegistration.title", 
										"feservice.nodeDeployedServiceRegistration.page2.subtitle", "feNodeDSR");
            	
            	updateNodeAvailableServices(feNodeDeployedServicesRegistration.getSelectedNodeId(), feNodeDeployedServicesRegistration);
    			
    			break;
    		case 3:
    			
    			FeServiceRegister feServiceRegister = new FeServiceRegister();
    			
    			model.put("registrationLog", feServiceRegister.registerNodeDeployedServices(dataSourcePeopleDB, 
    					feNodeDeployedServicesRegistration));

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", feNodeDeployedServicesRegistration.getPage());
            	
            	this.setPageInfo(model, "feservice.nodeDeployedServiceRegistration.title", 
										"feservice.nodeDeployedServiceRegistration.page3.subtitle", "feNodeDSR");

            	break;
    	}
    	
    }
    
    private String getSelectedNodeName(long communeId, List<PairElement<String, String>> feNodesList) {
    	
    	String result = "";
    	
    	for(PairElement<String, String> element : feNodesList) {
    		if (element.getValue().equalsIgnoreCase(String.valueOf(communeId))) {
    			result = element.getLabel();
    			break;
    		}
    	}
    	
    	return result;
    	
    }
    
	private List<PairElement<String, String>> getFeNodesList() {

		String queryNodesList = "SELECT id, comune FROM fenode";

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryNodesList);
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
	
	private void updateNodeAvailableServices(long communeId, FENodeDeployedServicesRegistration feNodeDeployedServicesRegistration) {
		
		feNodeDeployedServicesRegistration.getAlreadyRegisteredServices().clear();
		feNodeDeployedServicesRegistration.getAvailableServices().clear();
		
		NodeDeployedServices result = null;
		Vector<String> nodeAlreadyRegisteredServices = getNodeRegisteredServices(communeId);
		String feReference = getFeReference(communeId);
		try {
			FEInterface feInterface = this.getFEInterface(feReference);
			result = feInterface.getNodeDeployedServices("");
			for(AvailableService availableService : result.getAvailableServices()) {
				if (nodeAlreadyRegisteredServices.contains(availableService.get_package())) {
					feNodeDeployedServicesRegistration.addAlreadyRegisteredServices(availableService);
				}
				else {
					feNodeDeployedServicesRegistration.addAvailableServices(availableService);
				}
			}
		} catch (FeServiceReferenceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Collections.sort(feNodeDeployedServicesRegistration.getAvailableServices(), 
				new AvailableServicesListOrderer());

		Collections.sort(feNodeDeployedServicesRegistration.getAlreadyRegisteredServices(), 
				new AvailableServicesListOrderer());
		
	}

	private String getFeReference(long communeId) {

		String queryFeReference = "SELECT reference FROM fenode WHERE id = ?";

		String result = "";
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryFeReference);
			preparedStatement.setLong(1, communeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getString(1);
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

	private Vector<String> getNodeRegisteredServices(long communeId) {

		String queryNodeRegisteredServices = "SELECT package FROM service WHERE nodeid = ?";

		Vector<String> result = new Vector<String>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryNodeRegisteredServices);
			preparedStatement.setLong(1, communeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				result.add(resultSet.getString(1));
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
	
}

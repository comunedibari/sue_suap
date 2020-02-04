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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.domain.FENodeToNodeCopy;
import it.people.console.domain.TripleElement;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.AvailableServiceComparator;
import it.people.console.web.controllers.utils.NodeToNodeCopier;
import it.people.console.web.controllers.validator.FENodeToNodeCopyValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feNodeToNodeCopy", "feNodesList"})
public class FENodeToNodeCopyController extends MessageSourceAwareController {

	private FENodeToNodeCopyValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	@Autowired
	public FENodeToNodeCopyController(FENodeToNodeCopyValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feNodeToNodeCopy");
	}
	
    @RequestMapping(value = "/copiaDaNodoANodo.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	FENodeToNodeCopy feNodeToNodeCopy = null;
    	
    	if (logger.isDebugEnabled()) {
        	logger.debug("Referer = " + WebUtils.getReferer(request));
    	}
    	
    	if (request.getSession().getAttribute("feNodeToNodeCopy") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("copiaDaNodoANodo.do")) {
    		feNodeToNodeCopy = new FENodeToNodeCopy(3);

        	
    	}
    	else {

    		feNodeToNodeCopy = (FENodeToNodeCopy)request.getSession().getAttribute("feNodeToNodeCopy");
    		
    	}

    	List<TripleElement<String, String, String>> feNodesList = getFeNodesList();
    	
		feNodeToNodeCopy.setSelectedFromNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedFromNodeId(), feNodesList));
		
		if (!feNodeToNodeCopy.isToNewNode()) {
			feNodeToNodeCopy.setSelectedToNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedToNodeId(), feNodesList));
		}
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", getFeNodesList());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("feNodeToNodeCopy", feNodeToNodeCopy);
    	
    	model.put("page", feNodeToNodeCopy.getPage());
    	
    	this.setPageInfo(model, "feservice.nodeToNodeCopy.title", 
								"feservice.nodeToNodeCopy.page1.subtitle", "feNodesTNC");
    	    	
    	return getStaticProperty("feservices.nodeToNodeCopy.view");
    	
    }
    

    @RequestMapping(value = "/copiaDaNodoANodo.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("feNodeToNodeCopy") 
    		FENodeToNodeCopy feNodeToNodeCopy,  
    		BindingResult result, 
    		HttpServletRequest request) {

    	boolean isNextPage = isPrefixParamInRequest(request, "nextPage");
    	boolean isPreviousPage = isPrefixParamInRequest(request, "previousPage");
    	boolean isCancel = isPrefixParamInRequest(request, "cancel");
    	boolean isClose = isPrefixParamInRequest(request, "close");
    	boolean isChangeToNodeType = !isNextPage && !isPreviousPage && !isCancel;
    	
    	List<TripleElement<String, String, String>> feNodesList = getFeNodesList();
    	
	    	if (isNextPage) {
	    		validator.validate(feNodeToNodeCopy, result, isChangeToNodeType);
	        	//validator.validate(feServiceRegistration, result);
	    		if (!result.hasErrors()) {
	    			feNodeToNodeCopy.nextPage();    		
	    		}
			}

	    	if (isPreviousPage) {
	        	//validator.validate(feServiceRegistration, result);
	    		feNodeToNodeCopy.previousPage();
	    	}
		
	    	if (!isClose) {
		    	validateAndPreparePage(feNodeToNodeCopy.getPage(), model, 
		    			feNodeToNodeCopy, feNodesList, result, request);
	    	}
    	
    	if (isCancel || isClose) {
    		return  "redirect:elenco.do";
    	}
    	
		return getStaticProperty("feservices.nodeToNodeCopy.view");
    	
    }
    
    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		FENodeToNodeCopy feNodeToNodeCopy, 
    		List<TripleElement<String, String, String>> feNodesList, BindingResult result, 
    		HttpServletRequest request) {
    	
    	switch(pageNumber) {
    		case 1:

    			feNodeToNodeCopy.setSelectedFromNodeName(getSelectedNodeName(feNodeToNodeCopy.getSelectedFromNodeId(), feNodesList));
    			feNodeToNodeCopy.setSelectedFromNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedFromNodeId(), feNodesList));
    			
    			if (!feNodeToNodeCopy.isToNewNode()) {
    				feNodeToNodeCopy.setSelectedToNodeName(getSelectedNodeName(feNodeToNodeCopy.getSelectedToNodeId(), feNodesList));
    				feNodeToNodeCopy.setSelectedToNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedToNodeId(), feNodesList));
    			}

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("feNodeToNodeCopy", feNodeToNodeCopy);
            	
            	model.put("page", feNodeToNodeCopy.getPage());
            	
            	this.setPageInfo(model, "feservice.nodeToNodeCopy.title", 
										"feservice.nodeToNodeCopy.page1.subtitle", "feNodesTNC");
    			break;
    		case 2:

    			feNodeToNodeCopy.setSelectedFromNodeName(getSelectedNodeName(feNodeToNodeCopy.getSelectedFromNodeId(), feNodesList));
    			feNodeToNodeCopy.setSelectedFromNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedFromNodeId(), feNodesList));
    			if (!feNodeToNodeCopy.isToNewNode()) {
    				feNodeToNodeCopy.setSelectedToNodeName(getSelectedNodeName(feNodeToNodeCopy.getSelectedToNodeId(), feNodesList));
    				feNodeToNodeCopy.setSelectedToNodeKey(getSelectedNodeKey(feNodeToNodeCopy.getSelectedToNodeId(), feNodesList));
    			}

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("feNodeToNodeCopy", feNodeToNodeCopy);
            	
            	model.put("page", feNodeToNodeCopy.getPage());
            	
            	updateNodesServices(feNodeToNodeCopy);
            	
            	this.setPageInfo(model, "feservice.nodeToNodeCopy.title", 
										"feservice.nodeToNodeCopy.page2.subtitle", "feNodesTNC");
    			
    			break;
    		case 3:

    			collectAreaAndServicesConfigurationData(feNodeToNodeCopy, request);
    			
    			NodeToNodeCopier nodeToNodeCopier = new NodeToNodeCopier();
    			
    			model.put("registrationLog", nodeToNodeCopier.copyNode(dataSourcePeopleDB, 
    					feNodeToNodeCopy));

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", feNodeToNodeCopy.getPage());
            	
            	this.setPageInfo(model, "feservice.nodeToNodeCopy.title", 
										"feservice.nodeToNodeCopy.page3.subtitle", "feNodesTNC");
            	
    			break;
    	}
    	
    }
    
    private String getSelectedNodeName(long communeId, List<TripleElement<String, String, String>> feNodesList) {
    	
    	String result = "";
    	
    	for(TripleElement<String, String, String> element : feNodesList) {
    		if (element.getValue().equalsIgnoreCase(String.valueOf(communeId))) {
    			result = element.getLabel();
    			break;
    		}
    	}
    	
    	return result;
    	
    }

    private String getSelectedNodeKey(long communeId, List<TripleElement<String, String, String>> feNodesList) {
    	
    	String result = "";
    	
    	for(TripleElement<String, String, String> element : feNodesList) {
    		if (element.getValue().equalsIgnoreCase(String.valueOf(communeId))) {
    			result = element.getKey();
    			break;
    		}
    	}
    	
    	return result;
    	
    }
    
	private List<TripleElement<String, String, String>> getFeNodesList() {

		String queryNodesList = "SELECT id, comune, codicecomune FROM fenode";

		List<TripleElement<String, String, String>> result = new ArrayList<TripleElement<String, String, String>>();
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryNodesList);
			while(resultSet.next()) {
				result.add(new TripleElement<String, String, String>(resultSet.getString(1), 
						resultSet.getString(2), resultSet.getString(3)));
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
	
	private void updateNodesServices(FENodeToNodeCopy feNodeToNodeCopy) {
		
		feNodeToNodeCopy.getAlreadyRegisteredServices().clear();
		feNodeToNodeCopy.getAvailableServices().clear();
		
		if (feNodeToNodeCopy.isToNewNode()) {
			feNodeToNodeCopy.setSelectedToNodeId(0);
		}
		
		String fromNodeAvailableServicesQuery = "SELECT id, nome, package, attivita, sottoattivita FROM service WHERE nodeid = ? AND package NOT IN (SELECT package FROM service WHERE nodeid = ?) order by attivita, sottoattivita";
		String toNodeRegisteredServicesQuery = "SELECT id, nome, package, attivita, sottoattivita FROM service WHERE nodeid = ? order by attivita, sottoattivita";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			preparedStatement = connection.prepareStatement(fromNodeAvailableServicesQuery);
			preparedStatement.setLong(1, feNodeToNodeCopy.getSelectedFromNodeId());
			preparedStatement.setLong(2, feNodeToNodeCopy.getSelectedToNodeId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				AvailableService availableService = new AvailableService();
				availableService.setServiceId(resultSet.getLong(1));
				availableService.setServiceName(resultSet.getString(2));
				availableService.set_package(resultSet.getString(3));
				availableService.setActivity(resultSet.getString(4));
				availableService.setSubActivity(resultSet.getString(5));
				feNodeToNodeCopy.getAvailableServices().add(availableService);
			}

			preparedStatement.clearParameters();
			
			preparedStatement = connection.prepareStatement(toNodeRegisteredServicesQuery);
			preparedStatement.setLong(1, feNodeToNodeCopy.getSelectedToNodeId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				AvailableService availableService = new AvailableService();
				availableService.setServiceId(resultSet.getLong(1));
				availableService.setServiceName(resultSet.getString(2));
				availableService.set_package(resultSet.getString(3));
				availableService.setActivity(resultSet.getString(4));
				availableService.setSubActivity(resultSet.getString(5));
				feNodeToNodeCopy.getAlreadyRegisteredServices().add(availableService);
			}
			Collections.sort(feNodeToNodeCopy.getAlreadyRegisteredServices(), 
					new AvailableServiceComparator());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 * @param feNodeToNodeCopy
	 * @param request
	 */
	private void collectAreaAndServicesConfigurationData(FENodeToNodeCopy feNodeToNodeCopy, HttpServletRequest request) {
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.endsWith("_beServiceUrl")) {
				String serviceId = paramName.substring(0, paramName.indexOf("_beServiceUrl"));
				if (feNodeToNodeCopy.getSelectedServicesPackages().contains(serviceId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("SERVICE Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedServicesBEUrl().put(serviceId, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_serviceNamePrefix")) {
				String serviceId = paramName.substring(0, paramName.indexOf("_serviceNamePrefix"));
				if (feNodeToNodeCopy.getSelectedServicesPackages().contains(serviceId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("SERVICE Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedServicesNamePrefix().put(serviceId, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_serviceNameSuffix")) {
				String serviceId = paramName.substring(0, paramName.indexOf("_serviceNameSuffix"));
				if (feNodeToNodeCopy.getSelectedServicesPackages().contains(serviceId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("SERVICE Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedServicesNameSuffix().put(serviceId, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_serviceLogicalNamePrefix")) {
				String serviceId = paramName.substring(0, paramName.indexOf("_serviceLogicalNamePrefix"));
				if (feNodeToNodeCopy.getSelectedServicesPackages().contains(serviceId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("SERVICE Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedServicesLogicalNamePrefix().put(serviceId, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_serviceLogicalNameSuffix")) {
				String serviceId = paramName.substring(0, paramName.indexOf("_serviceLogicalNameSuffix"));
				if (feNodeToNodeCopy.getSelectedServicesPackages().contains(serviceId)) {
					if (logger.isDebugEnabled()) {
						logger.debug("SERVICE Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedServicesLogicalNameSuffix().put(serviceId, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_beAreaUrl")) {
				String areaName = paramName.substring(0, paramName.indexOf("_beAreaUrl"));
				if (feNodeToNodeCopy.getSelectedAreas().contains(areaName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("AREA Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedAreasBEUrl().put(areaName, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_areaServicesNamePrefix")) {
				String areaName = paramName.substring(0, paramName.indexOf("_areaServicesNamePrefix"));
				if (feNodeToNodeCopy.getSelectedAreas().contains(areaName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("AREA Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedAreasServicesNamePrefix().put(areaName, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_areaServicesNameSuffix")) {
				String areaName = paramName.substring(0, paramName.indexOf("_areaServicesNameSuffix"));
				if (feNodeToNodeCopy.getSelectedAreas().contains(areaName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("AREA Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedAreasServicesNameSuffix().put(areaName, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_areaServicesLogicalNamePrefix")) {
				String areaName = paramName.substring(0, paramName.indexOf("_areaServicesLogicalNamePrefix"));
				if (feNodeToNodeCopy.getSelectedAreas().contains(areaName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("AREA Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedAreasServicesLogicalNamePrefix().put(areaName, request.getParameter(paramName));
				}
			}
			if (paramName.endsWith("_areaServicesLogicalNameSuffix")) {
				String areaName = paramName.substring(0, paramName.indexOf("_areaServicesLogicalNameSuffix"));
				if (feNodeToNodeCopy.getSelectedAreas().contains(areaName)) {
					if (logger.isDebugEnabled()) {
						logger.debug("AREA Name: " + paramName + " - Value: " + request.getParameter(paramName));
					}
					feNodeToNodeCopy.getSelectedAreasServicesLogicalNameSuffix().put(areaName, request.getParameter(paramName));
				}
			}
		}
		
	}
	
}

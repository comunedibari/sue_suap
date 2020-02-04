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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import it.people.console.domain.BEServicesMassiveChange;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.BeServicesMassiveChangeExecutor;
import it.people.console.web.controllers.validator.BEServicesMassiveChangeValidator;
import it.people.console.web.servlet.mvc.MessageSourceAwareController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziBe/ModificaMassiva/")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "beServicesMassiveChange", "feNodesList"})
public class BEServicesMassiveChangeController extends MessageSourceAwareController {

	private BEServicesMassiveChangeValidator validator;

	@Autowired
	private IPersistenceBroker persistenceBroker;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	@Autowired
	public BEServicesMassiveChangeController(BEServicesMassiveChangeValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("beServicesMassiveChange");
	}
	
    @RequestMapping(value = "/modifica.do", method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "tipo", required = false) String action, ModelMap model, HttpServletRequest request) {

    	BEServicesMassiveChange beServicesMassiveChange = null;
    	
    	if (logger.isDebugEnabled()) {
        	logger.debug("Referer = " + WebUtils.getReferer(request));
    	}
    	
    	if (request.getSession().getAttribute("beServicesMassiveChange") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("ServiziBe/ModificaMassiva/parametri.do")) {
    		beServicesMassiveChange = new BEServicesMassiveChange(4);
        	
    	}
    	else {

    		beServicesMassiveChange = (BEServicesMassiveChange)request.getSession().getAttribute("beServicesMassiveChange");
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", persistenceBroker.getFeNodesList());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("beServicesMassiveChange", beServicesMassiveChange);
    	
    	model.put("page", beServicesMassiveChange.getPage());

    	model.put("action", action);

    	String operation = "massiveChange";
    	if (action.equalsIgnoreCase("elimina")) {
    		operation = "massiveDeletion";
    	}

    	model.put("operation", operation);
    	
    	this.setPageInfo(model, "beservice." + operation + ".title", 
				"beservice." + operation + ".page1.subtitle", "beServiceMC");
    	
    	return getStaticProperty("beservices.massiveChange.view");
    	
    }
    

    @RequestMapping(value = "/modifica.do", method = RequestMethod.POST)
    public String processSubmit(@RequestParam(value = "tipo", required = false) String action, ModelMap model, 
    		@ModelAttribute("feNodesList") 
    		List<TripleElement<String, String, String>> feNodesList, 
    		@ModelAttribute("beServicesMassiveChange") 
    		BEServicesMassiveChange beServicesMassiveChange,     		
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
	    		validator.validate(beServicesMassiveChange, result, isChangeToNodeType, action);
	        	//validator.validate(feServiceRegistration, result);
	    		if (!result.hasErrors()) {
	    			beServicesMassiveChange.nextPage();    		
	    		}
			}

	    	if (isPreviousPage) {
	        	//validator.validate(feServiceRegistration, result);
	    		beServicesMassiveChange.previousPage();
	    	}
		
	    	if (!isClose) {
		    	validateAndPreparePage(beServicesMassiveChange.getPage(), model, 
		    			beServicesMassiveChange, feNodesList, result, request, action);
	    	}
    	
    	if (isCancel || isClose) {
    		return  "redirect:/ServiziBe/elenco.do";
    	}
    	
		return getStaticProperty("beservices.massiveChange.view");
    	
    }
    
    private void validateAndPreparePage(int pageNumber, ModelMap model, 
    		BEServicesMassiveChange beServicesMassiveChange, 
    		List<TripleElement<String, String, String>> feNodesList, BindingResult result, 
    		HttpServletRequest request, String action) {

		BeServicesMassiveChangeExecutor beServicesMassiveChangeExecutor = new BeServicesMassiveChangeExecutor();

    	String operation = "massiveChange";
    	if (action.equalsIgnoreCase("elimina")) {
    		operation = "massiveDeletion";
    	}

    	model.put("operation", operation);
    	
    	this.setPageInfo(model, "beservice." + operation + ".title", 
				"beservice." + operation + ".page" + pageNumber + ".subtitle", "beServiceMC");
		
    	switch(pageNumber) {
    		case 1:

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("beServicesMassiveChange", beServicesMassiveChange);
            	
            	model.put("page", beServicesMassiveChange.getPage());
            	
            	break;
            	
    		case 2:

    			if (beServicesMassiveChange.getSelectedNodesToShow() != null && beServicesMassiveChange.getSelectedNodesToShow().length == 0) {
    				Iterator<TripleElement<String, String, String>> feNodesListIterator = feNodesList.iterator();
    				String[] allNodesSelected = new String[feNodesList.size()];
    				int index = 0;
    				while(feNodesListIterator.hasNext()) {
    					allNodesSelected[index] = feNodesListIterator.next().getValue();
    					index++;
    				}
    				beServicesMassiveChange.setSelectedNodesToShow(allNodesSelected);
    			}

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	model.addAttribute("beServicesMassiveChange", beServicesMassiveChange);
            	
            	model.put("page", beServicesMassiveChange.getPage());
            	

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.BE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION)) {
                	beServicesMassiveChange.setAvailableServices(persistenceBroker.getAllNodesBeServices(beServicesMassiveChange.getSelectedNodesToShow()));
    			} else {
                	beServicesMassiveChange.setAvailableServices(persistenceBroker.getNodesOrphanedBeServices(beServicesMassiveChange.getSelectedNodesToShow()));
    			}
            	
    			break;
    			
    		case 3:
    			
    	    	model.addAttribute("allowedProtocolsList", this.getAllowedProtocolsList());
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", beServicesMassiveChange.getPage());
            	
    			break;
    		case 4:

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.BE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION)) {
        			model.put("registrationLog", 
        					beServicesMassiveChangeExecutor.doMassiveParametersChange(this.dataSourcePeopleDB, beServicesMassiveChange));
    			}

    			if (action.equalsIgnoreCase(Constants.ControllerUtils.BE_SERVICES_CONTROLLER_MASSIVE_DELETE_ACTION)) {
        			model.put("registrationLog", 
        					beServicesMassiveChangeExecutor.doMassiveDeletion(this.dataSourcePeopleDB, beServicesMassiveChange));
    			}
    			
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

            	model.put("page", beServicesMassiveChange.getPage());
            	
    			break;
    	}

    	model.put("action", action);    	
    	
    }

	private List<PairElement<String, String>> getAllowedProtocolsList() {

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("", ""));
		result.add(new PairElement<String, String>("http", "http"));
		result.add(new PairElement<String, String>("https", "https"));
		
		return result;
		
	}
    
}

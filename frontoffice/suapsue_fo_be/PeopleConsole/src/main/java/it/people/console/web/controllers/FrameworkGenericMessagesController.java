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

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.ListColumnFilter;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.domain.FrameworkGenericMessages;
import it.people.console.domain.VelocityTemplate;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.FENodeDTO;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.security.AbstractCommand;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.utils.velocityTemplates.VelocityTemplatesUtils;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.TableValuePropertyVO;
import it.people.feservice.beans.VelocityTemplateDataVO;

import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/MessaggiGenerali")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "frameworkGenericMessages", Constants.ControllerUtils.APPLIED_FILTERS_KEY, "bundleRefFramework", "bundleRefServices"})
public class FrameworkGenericMessagesController extends AbstractListableController {

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	private String frameworkLabelsQuery = "SELECT bundleProp.id, bundleProp._key 'Chiave', bundleProp._value 'Valore', bundles._locale, IFNULL(pclc._name, 'Generale') AS 'Lingua' FROM bundles_properties bundleProp JOIN bundles ON bundles.id = bundleProp.bundleRef LEFT JOIN pc_languages_codes AS pclc ON pclc.iso_code = bundles._locale WHERE bundles.bundle = 'it.people.resources.FormLabels' AND bundles.nodeId IS NULL AND bundles._locale IS NULL ORDER BY _key";

	private String serviceLabelsQuery = "SELECT bundleProp.id, bundleProp._key 'Chiave', bundleProp._value 'Valore', bundles._locale, IFNULL(pclc._name, 'Generale') AS 'Lingua' FROM bundles_properties bundleProp JOIN bundles ON bundles.id = bundleProp.bundleRef LEFT JOIN pc_languages_codes AS pclc ON pclc.iso_code = bundles._locale WHERE bundles.bundle = ! AND bundles.nodeId IS NULL ORDER BY _key";
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	private List <Option> registeredServicesPackages = null;
	
	private List <Option> registeredServicesPackagesTablevalues = null;
	
	private List <Option> tableValuesTableIdCombo = null;
	
	public FrameworkGenericMessagesController() {
		
	}
	
    @RequestMapping(value = "/elenco.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

		int pageSizeDefault = 10;
		
    	FrameworkGenericMessages frameworkGenericMessages = null;
    	
    	//Init Combobox
    	registeredServicesPackages = this.getFERegisteredServicesPackages();
    	registeredServicesPackagesTablevalues = this.getFERegisteredServicesPackages();
    	
    	
    	if (request.getSession().getAttribute("frameworkGenericMessages") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("MessaggiGenerali/elenco.do")) {

    		//init bean
    		frameworkGenericMessages = new FrameworkGenericMessages();
    		
    		if (registeredServicesPackages != null && !registeredServicesPackages.isEmpty()) {
    			frameworkGenericMessages.setSelectedServicePackage(registeredServicesPackages.get(0).getValue());
    		}
    		
    		//Default value for service package for tablevalues
    		if (registeredServicesPackagesTablevalues != null && !registeredServicesPackagesTablevalues.isEmpty()) {
    			frameworkGenericMessages.setSelectedServicePackageTablevalue(registeredServicesPackagesTablevalues.get(0).getValue());
    			//Default value for tableValuesTableIdCombo
    			tableValuesTableIdCombo = this.getServiceTableValuesTableId(registeredServicesPackagesTablevalues.get(0).getValue());
    			
    			if (tableValuesTableIdCombo != null && !tableValuesTableIdCombo.isEmpty()) {
    				frameworkGenericMessages.setSelectedTableValuesTableId(tableValuesTableIdCombo.get(0).getValue());
    				
    				//Init List Holder
    				try {
    	    			ILazyPagedListHolder tablevaluesList = prepareTablevaluesPropertiesList(frameworkGenericMessages.getSelectedTableValuesTableId(), pageSizeDefault);
    	    			if (!frameworkGenericMessages.getPagedListHolders().containsKey(tablevaluesList.getPagedListId())) {
    	    				frameworkGenericMessages.addPagedListHolder(tablevaluesList);
    	    			}
    	    			else {
    	    				frameworkGenericMessages.updatePagedListHolder(tablevaluesList);
    	    			}
    	    		} catch (PagedListHoldersCacheException e) {
    	    			logger.error("ParsedListException populating tablevalues List");
    	    		} catch (LazyPagedListHolderException e) {
    	    			logger.error("LazyPagedListHolderException populating tablevalues List");
    				}
    				
    			}
    		}
  
        	try {
        		
        		List<String> rowColumnsIdentifiersFrameworkLabels = new ArrayList<String>();
        		rowColumnsIdentifiersFrameworkLabels.add("id");

        		List<String> editableRowColumnsFrameworkLabels = new ArrayList<String>();
        		editableRowColumnsFrameworkLabels.add("_value");

        		String query = frameworkLabelsQuery;
        		List<String> visibleColumnsNamesFrameworkLabels = new ArrayList<String>();
        		visibleColumnsNamesFrameworkLabels.add("_key");
        		visibleColumnsNamesFrameworkLabels.add("_value");
        		visibleColumnsNamesFrameworkLabels.add("Lingua");
        		if (!StringUtils.isEmptyString(frameworkGenericMessages.getSelectedFrameworkLanguage())) {
        			query = this.getProperty(Constants.Queries.FRAMEWORK_MESSAGES_LOCALE_BY_ID).replace("?", "'" + frameworkGenericMessages.getSelectedFrameworkLanguage() + "'");

            		editableRowColumnsFrameworkLabels = new ArrayList<String>();
            		editableRowColumnsFrameworkLabels.add("Valore");
        			
            		visibleColumnsNamesFrameworkLabels = new ArrayList<String>();
            		visibleColumnsNamesFrameworkLabels.add("Chiave");
            		visibleColumnsNamesFrameworkLabels.add("Valore");
            		visibleColumnsNamesFrameworkLabels.add("Lingua");
        		}
        		
        		//Create ListHolder for framework labels
        		ILazyPagedListHolder pagedListHolderFrameworkLabels = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST, dataSourcePeopleDB, query, pageSizeDefault, 
    					rowColumnsIdentifiersFrameworkLabels, editableRowColumnsFrameworkLabels, false);
        		pagedListHolderFrameworkLabels.setDeleteActionEnabled(false);
        		
        		pagedListHolderFrameworkLabels.setVisibleColumnsNames(visibleColumnsNamesFrameworkLabels);

        		frameworkGenericMessages.addPagedListHolder(pagedListHolderFrameworkLabels);
        		
        		//Create ListHolder for service labels
        		frameworkGenericMessages.addPagedListHolder(this.prepareServiceLabelsList(frameworkGenericMessages.getSelectedServicePackage(), 
        				frameworkGenericMessages.getSelectedServicesLanguage(), pageSizeDefault));

        		//Create ListHolder for service labels
        		frameworkGenericMessages.addPagedListHolder(this.prepareTablevaluesPropertiesList(frameworkGenericMessages.getSelectedTableValuesTableId(), pageSizeDefault));
        		
        		
    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {

    		frameworkGenericMessages = (FrameworkGenericMessages)request.getSession().getAttribute("frameworkGenericMessages");
    		processListHoldersRequests(request.getQueryString(), frameworkGenericMessages);
    		
    	}

    	try {
			applyColumnSorting(request, frameworkGenericMessages);
		} catch (LazyPagedListHolderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	if (request.getSession().getAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY) != null) {
    		List<FilterProperties> appliedFilters = (List<FilterProperties>)request.getSession().getAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY);
        	if (logger.isDebugEnabled()) {
        		logger.debug("Applying " + appliedFilters.size() + " active filters...");
        	}
        	try {
        		frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST).applyFilters(appliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}
		
    	
    	//Populate or refresh velocity templates paged list holder
    	try {
			ILazyPagedListHolder velocityTemplatesList = prepareGenericVelocityTemplatesList(pageSizeDefault);
			if (!frameworkGenericMessages.getPagedListHolders().containsKey(velocityTemplatesList.getPagedListId())) {
				frameworkGenericMessages.addPagedListHolder(velocityTemplatesList);
			} else {
				frameworkGenericMessages.updatePagedListHolder(velocityTemplatesList);
			}
		} catch (LazyPagedListHolderException e) {
			logger.error("ParsedListException populating velocity templates List");
		} catch (PagedListHoldersCacheException e) {
			logger.error("LazyPagedListHolderException populating velocity templates List");
		}
    	

    	model.put("includeTopbarLinks", true);
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

    	model.addAttribute("frameworkGenericMessages", frameworkGenericMessages);

    	model.addAttribute("feRegisteredServicesPackages", getFERegisteredServicesPackages());
    	
    	model.addAttribute("feRegisteredServicesPackagesTablevalues", registeredServicesPackagesTablevalues);
    	
    	model.addAttribute("tableValuesTableIdCombo", tableValuesTableIdCombo);
    	
    	model.addAttribute("frameworkLocales", getFrameworkLocales());

    	model.addAttribute("serviceLocales", getServiceLocales(frameworkGenericMessages.getSelectedServicePackage()));

    	model.addAttribute("frameworkRegisterableLocales", getFrameworkRegisterableLocales());

    	model.addAttribute("serviceRegisterableLocales", getServiceRegisterableLocales(frameworkGenericMessages.getSelectedServicePackage()));
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "generalmessages.listAndModify.title", null, "frameworkGM");
    	
    	return getStaticProperty("frameworkGenericMessages.listAndModify.view");
    	
    }
    

	@RequestMapping(value = "/elenco.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    	@ModelAttribute("frameworkGenericMessages") FrameworkGenericMessages frameworkGenericMessages, BindingResult result,
    	@RequestParam(value = "usedCombo", required = false) String usedCombo, HttpServletRequest request) {
		
		boolean isListHolderRequest = isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX);
		
		try {
			

			//Update value for tableValues
    		if ((usedCombo != null) && (usedCombo.equalsIgnoreCase("feRegisteredServicesPackagesTablevalues"))) {
    			tableValuesTableIdCombo = this.getServiceTableValuesTableId(frameworkGenericMessages.getSelectedServicePackageTablevalue());
    			
    			if (tableValuesTableIdCombo != null && !tableValuesTableIdCombo.isEmpty()) {
    				frameworkGenericMessages.setSelectedTableValuesTableId(tableValuesTableIdCombo.get(0).getValue());
    				
    				//Update List Holder
    				try {
    					int pageSizeTablevalues = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.TABLEVAUES_LIST).getPageSize();
    	    			int currPage = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.TABLEVAUES_LIST).getPage();
    					ILazyPagedListHolder tablevaluesList = prepareTablevaluesPropertiesList(frameworkGenericMessages.getSelectedTableValuesTableId(), pageSizeTablevalues);
    					tablevaluesList.setPage(currPage);
    					frameworkGenericMessages.updatePagedListHolder(tablevaluesList);
    	    			
    	    		} catch (PagedListHoldersCacheException e) {
    	    			logger.error("ParsedListException populating tablevalues List");
    	    		} catch (LazyPagedListHolderException e) {
    	    			logger.error("LazyPagedListHolderException populating tablevalues List");
    				}
    			}
    		}
    		else if ((usedCombo != null) && (usedCombo.equalsIgnoreCase("tableValuesTableIdCombo"))) {
    			int pageSizeTablevalues = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.TABLEVAUES_LIST).getPageSize();
    			int currPage = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.TABLEVAUES_LIST).getPage();
    			ILazyPagedListHolder tablevaluesList = prepareTablevaluesPropertiesList(frameworkGenericMessages.getSelectedTableValuesTableId(), pageSizeTablevalues);
    			tablevaluesList.setPage(currPage);
    			frameworkGenericMessages.updatePagedListHolder(tablevaluesList);
    		}
			
    		//Update value for framework labels
	    	if (!it.people.feservice.utils.StringUtils.nullToEmptyString(
	    			frameworkGenericMessages.getSelectedFrameworkLanguage()).equalsIgnoreCase(
	    					it.people.feservice.utils.StringUtils.nullToEmptyString(
	    			    			frameworkGenericMessages.getPreviousSelectedFrameworkLanguage()))) {
	    			int pageSizeFramework = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST).getPageSize();
	    			int currPage = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST).getPage(); 
	    			ILazyPagedListHolder frameworkLabelsList = prepareFrameworkLabelsList(frameworkGenericMessages.getSelectedFrameworkLanguage(), pageSizeFramework);
	    			frameworkLabelsList.setPage(currPage);
	    			frameworkGenericMessages.updatePagedListHolder(frameworkLabelsList);
					frameworkGenericMessages.setPreviousSelectedFrameworkLanguage(frameworkGenericMessages.getSelectedFrameworkLanguage());
	    	}
	    	
	    	//Update value for service labels
	    	if (!it.people.feservice.utils.StringUtils.nullToEmptyString(
	    			frameworkGenericMessages.getSelectedServicesLanguage()).equalsIgnoreCase(
	    					it.people.feservice.utils.StringUtils.nullToEmptyString(
	    			    			frameworkGenericMessages.getPreviousSelectedServicesLanguage())) || 
    			!it.people.feservice.utils.StringUtils.nullToEmptyString(
    	    			frameworkGenericMessages.getSelectedServicePackage()).equalsIgnoreCase(
    	    					it.people.feservice.utils.StringUtils.nullToEmptyString(
    	    			    			frameworkGenericMessages.getPreviousSelectedServicePackage())) ) {
	    			int pageSizeServices = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.SERVICE_LABELS_LIST).getPageSize();
	    			int currPage = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.SERVICE_LABELS_LIST).getPage();
	    			ILazyPagedListHolder serviceLabelsList = prepareServiceLabelsList(frameworkGenericMessages.getSelectedServicePackage(), 
							frameworkGenericMessages.getSelectedServicesLanguage(), pageSizeServices);
	    			serviceLabelsList.setPage(currPage);
	    			frameworkGenericMessages.updatePagedListHolder(serviceLabelsList);
					frameworkGenericMessages.setPreviousSelectedServicesLanguage(frameworkGenericMessages.getSelectedServicesLanguage());
	    	}
	    		    	
		} catch (PagedListHoldersCacheException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LazyPagedListHolderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		
    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state...");
    	}
    	List<FilterProperties> updatedAppliedFilters = updateAppliedFilters(request, filtersList);
    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state done.");
    	}
    	
    	if (isParamInRequest(request, "registerFrameworkLanguage")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Saving framework language...");
        		logger.debug(frameworkGenericMessages.getSelectedFrameworkRegisterableLanguage());
        	}
    		try {
    			FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();
    			
				feInterface.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, null, 
						frameworkGenericMessages.getSelectedFrameworkRegisterableLanguage(), "1", null);
				this.persistenceBroker.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, null, 
						frameworkGenericMessages.getSelectedFrameworkRegisterableLanguage(), "1", null);
				
			} catch (FeServiceReferenceException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (PersistenceBrokerException e) {
				e.printStackTrace();
			}
    	}
    	
    	if ((frameworkGenericMessages.getPreviousSelectedFrameworkLanguage() == null) || 
    			!frameworkGenericMessages.getSelectedFrameworkLanguage().equalsIgnoreCase(frameworkGenericMessages.getPreviousSelectedFrameworkLanguage())) {
			model.addAttribute("bundleRefFramework", this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(
					Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
					null, frameworkGenericMessages.getSelectedFrameworkLanguage()));
    	}

    	if (isParamInRequest(request, "registerServiceLanguage")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Saving service language...");
        		logger.debug(frameworkGenericMessages.getSelectedServicesRegisterableLanguage());
        	}
        	try {
    			FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();
    			
				feInterface.registerBundle(frameworkGenericMessages.getSelectedServicePackage(), null, 
						frameworkGenericMessages.getSelectedServicesRegisterableLanguage(), "1", null);
				this.persistenceBroker.registerBundle(frameworkGenericMessages.getSelectedServicePackage(), null, 
						frameworkGenericMessages.getSelectedServicesRegisterableLanguage(), "1", null);
				
			} catch (FeServiceReferenceException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (PersistenceBrokerException e) {
				e.printStackTrace();
			}
        	
    	}
    	
    	if ((frameworkGenericMessages.getPreviousSelectedServicesLanguage() == null) || 
    			!frameworkGenericMessages.getSelectedServicesLanguage().equalsIgnoreCase(frameworkGenericMessages.getPreviousSelectedServicesLanguage()) || 
    		(frameworkGenericMessages.getPreviousSelectedServicePackage() == null) || 
    			!frameworkGenericMessages.getSelectedServicePackage().equalsIgnoreCase(frameworkGenericMessages.getPreviousSelectedServicePackage())) {
			model.addAttribute("bundleRefServices", this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(
					frameworkGenericMessages.getSelectedServicePackage(), 
					null,  frameworkGenericMessages.getSelectedServicesLanguage()));
    	}
    	
    	if (isParamInRequest(request, "applyFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Applying " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
        		frameworkGenericMessages.getPagedListHolder("frameworkGenericMessages").applyFilters(updatedAppliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
        	model.addAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY, updatedAppliedFilters);
    	}

    	if (isParamInRequest(request, "clearFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Clearing " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
        		frameworkGenericMessages.getPagedListHolder("frameworkGenericMessages").removeFilters();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters cleared.");
        	}
        	model.addAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY, updatedAppliedFilters);
    	}
    	
    	if (isListHolderRequest) {
    		processListHoldersRequests(request, frameworkGenericMessages, model);

    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:/MessaggiGenerali/conferma.do";
    		}
    	}
    	
    	//Update value for Velocity templates
    	try {
			int pageSizeVelocityTemplates = frameworkGenericMessages.getPagedListHolder(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST).getPageSize();
			ILazyPagedListHolder velocityTemplatesList = prepareGenericVelocityTemplatesList(pageSizeVelocityTemplates);
			frameworkGenericMessages.updatePagedListHolder(velocityTemplatesList);
			
		} catch (LazyPagedListHolderException e) {
			logger.error("ParsedListException populating velocity templates List");
		} catch (PagedListHoldersCacheException e) {
			logger.error("LazyPagedListHolderException populating velocity templates List");
		}
    	
    	
    	
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("frameworkGenericMessages", frameworkGenericMessages);

    	model.addAttribute("frameworkLocales", getFrameworkLocales());

    	model.addAttribute("serviceLocales", getServiceLocales(frameworkGenericMessages.getSelectedServicePackage()));

    	model.addAttribute("frameworkRegisterableLocales", getFrameworkRegisterableLocales());

    	model.addAttribute("serviceRegisterableLocales", getServiceRegisterableLocales(frameworkGenericMessages.getSelectedServicePackage()));
    	
    	model.addAttribute("feRegisteredServicesPackages", getFERegisteredServicesPackages());

    	model.addAttribute("feRegisteredServicesPackagesTablevalues", registeredServicesPackagesTablevalues);
    	
    	model.addAttribute("tableValuesTableIdCombo", tableValuesTableIdCombo);
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "generalmessages.listAndModify.title", null, "frameworkGM");

    	return getStaticProperty("frameworkGenericMessages.listAndModify.view");    		
    		
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/generalmessages/sidebar.jsp");
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model,  
    		@ModelAttribute("frameworkGenericMessages") FrameworkGenericMessages frameworkGenericMessages, BindingResult result, 
    		HttpServletRequest request) {
    	
    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		ProcessActionDataHolder processActionDataHolder = this.popProcessActionData(request);
    		
    		String pagedListHolderId = processActionDataHolder.getPagedListHolderId();
    		EditableRowInputData editableRowInputData = processActionDataHolder.getEditableRowInputData(); 

    		//cancellazione confermata
			/* delete */
//			String nodeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
//			String communeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("codicecomune"));
//			String reference = String.valueOf(editableRowInputData.getRowIdentifiers().get("reference"));
//			deleteNode(communeId, reference, nodeId);
//			
//			feNode.getPagedListHolder(pagedListHolderId).update();
 
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:elenco.do";
    }
    
    @Override
    protected IFilters prepareFilters() {
    	    	
    	Vector<IFilter> filters = new Vector<IFilter>();

    	filters.add(getCodiceComuneFilter());
    	filters.add(getNomeComuneFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }
    
    private IFilter getCodiceComuneFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	ListColumnFilter listColumnFilter = new ListColumnFilter("Codice comune", "codicecomune", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	listColumnFilter.addFilterAllowedValue(new Option("007003", "007003"));
    	listColumnFilter.addFilterAllowedValue(new Option("007026", "007026"));
    	listColumnFilter.addFilterAllowedValue(new Option("007030", "007030"));
    	
    	return listColumnFilter;
    	
    }

    private IFilter getNomeComuneFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Nome comune", "comune", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
//    private List<Command> getRowActions() {
//    	
//    	List<Command> result = new ArrayList<Command>();
//    	
//    	result.add(new InputCommand("deleteFENode", "deleteFENode", null, 
//    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
//    	result.add(new LinkCommand("editFENode", "modifica.do", null, 
//    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
//    	result.add(new LinkCommand("viewLog", "viewLog.do", null, 
//    			"log.png", "log-dis.png", AbstractCommand.CommandActions.viewLog));
//    	result.add(new LinkCommand("viewAuditConversations", "viewAuditConversations.do", null, 
//    			"auditConversations.png", "auditConversations-dis.png", AbstractCommand.CommandActions.viewAuditConversations));
//    	result.add(new LinkCommand("nodeAuditStatistiche", "nodeAuditStatistiche.do", null, 
//    			"statistics.png", "statistics-dis.png", AbstractCommand.CommandActions.auditStatistiche));
//    	
//    	return result;
//    	
//    }

	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction()
	 */
	@Override
	protected void processAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap model) {

		
		
		if (logger.isDebugEnabled()) {
			logger.debug("Processing action...");
			logger.debug("Paged list holder id: " + pagedListHolderId);
			logger.debug("Action: " + action);
			logger.debug("Editable row input data: " + editableRowInputData);
		}
		
		Map<String, Object> rowIdentifier = editableRowInputData.getRowIdentifiers();
		
		Connection connection = null;
		
		FrameworkGenericMessages frameworkGenericMessages = (FrameworkGenericMessages) request.getSession().getAttribute("frameworkGenericMessages");
		
		try {	
		
			connection = dataSourcePeopleDB.getConnection();
			FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();
			
			//Manage Tablevalues pagedListholder
			if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.TABLEVAUES_LIST)) {
				
				Map<String, Object> inputData = editableRowInputData.getInputData();
				
				String newValue = (String) inputData.get("value");
				String oldValue = (String) rowIdentifier.get("value");
				int tableValueRef = Integer.parseInt((String) frameworkGenericMessages.getSelectedTableValuesTableId());
				int id=0;
				
				if(!action.equals(AbstractCommand.CommandActions.saveNew)){
					id = Integer.parseInt((String) rowIdentifier.get("id"));	
				}
				
				TableValuePropertyVO tableValueProp = new TableValuePropertyVO(id, oldValue, newValue, tableValueRef, action.getAction());
				
				//Save in FeDB
				boolean savedOnFeDB = executeFeConfigureTableValueProperty(feInterface, tableValueProp);
				
				if (savedOnFeDB) {
					
					//Create - Update - Delete on LocalDB
					if(action.equals(AbstractCommand.CommandActions.saveNew)) {
						this.persistenceBroker.registerNewTableValueProperty(newValue, tableValueRef);
					}	
					else if(action.equals(AbstractCommand.CommandActions.save)) {
						this.persistenceBroker.updateTableValueProperty(oldValue, tableValueRef, newValue, tableValueRef);
					}
					else if(action.equals(AbstractCommand.CommandActions.delete)) {
						this.persistenceBroker.deleteTableValueProperty(oldValue, tableValueRef);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Impossibile modificare i parametri del servizio, per un problema di accesso alla base di dati.");
		}
		finally {
			try {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
					}
				}
			} catch (Exception e) {}
		}
			
			
		if (action.equals(AbstractCommand.CommandActions.save)) {

			try {
				
				FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();
	
				String value = (String) editableRowInputData.getInputData().get("Valore");
				
				if(value==null){
					value = (String) editableRowInputData.getInputData().get("_value");
				}
				
				long messageId = new Long(Integer.parseInt((String) editableRowInputData.getRowIdentifiers().get("id")));
				String messageKey = this.persistenceBroker.getServiceMessageKeyById(messageId);
	
				/* Messaggi del Framework */
				if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST)) {
					String language = frameworkGenericMessages.getSelectedFrameworkLanguage();
					String locale = null;
					if (!StringUtils.isEmptyString(language)) {
						locale = language;
					}
	
					String bundleRefFramework = String.valueOf(request.getSession().getAttribute("bundleRefFramework"));
					
					if (StringUtils.isEmptyString(bundleRefFramework) || bundleRefFramework.equalsIgnoreCase("-1")) {
						feInterface.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								null, frameworkGenericMessages.getSelectedFrameworkLanguage(), "1", null);
						this.persistenceBroker.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								null, frameworkGenericMessages.getSelectedFrameworkLanguage(), "1", null);
						bundleRefFramework = String.valueOf(
								this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								null, locale));
					}
					
					feInterface.updateBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
							null, frameworkGenericMessages.getSelectedFrameworkLanguage(), messageKey, value, "1", null);
					this.persistenceBroker.updateBundle(Long.parseLong(bundleRefFramework), 
							messageKey, value, "1", null);
						
					
				/* Messaggi dei servizi */
				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.SERVICE_LABELS_LIST)) {
					
					String language = frameworkGenericMessages.getSelectedServicesLanguage();
					String locale = null;
					if (!StringUtils.isEmptyString(language)) {
						locale = language;
					}
	
					String bundleRefServices = String.valueOf(request.getSession().getAttribute("bundleRefServices"));
					
					if (StringUtils.isEmptyString(bundleRefServices) || bundleRefServices.equalsIgnoreCase("-1")) {
						feInterface.registerBundle(frameworkGenericMessages.getSelectedServicePackage(), 
								null, frameworkGenericMessages.getSelectedServicesLanguage(), "1", null);
						this.persistenceBroker.registerBundle(frameworkGenericMessages.getSelectedServicePackage(), 
								null, frameworkGenericMessages.getSelectedServicesLanguage(), "1", null);
						bundleRefServices = String.valueOf(
								this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(frameworkGenericMessages.getSelectedServicePackage(), 
								null, locale));
					}
					
					feInterface.updateBundle(frameworkGenericMessages.getSelectedServicePackage(), null, 
							frameworkGenericMessages.getSelectedServicesLanguage(), messageKey, value, "1", null);
					this.persistenceBroker.updateBundle(Long.parseLong(bundleRefServices), 
							messageKey, value, "1", null);
					
				}
				
				
			} catch (FeServiceReferenceException e) {
				e.printStackTrace();
			
			} catch (RemoteException e) {
				e.printStackTrace();
			 
			} catch (PersistenceBrokerException e) {
				e.printStackTrace();
			}	
			
		}
		
		if (action.equals(AbstractCommand.CommandActions.delete)) {
			if ((Boolean) request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Delete confirmation required, no action.");
				}
			}
			
			//IF delete for velocity template list holder
			else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST)) {
				
				//Update and delete
				String communeId = null;
				String serviceId = null;
				String shortkey = null;

				shortkey = (String) rowIdentifier.get("Chiave");

				//Delete only content of template not the template itself: do an update and erase content.
				//Build the VO to perform UPDATE to blank template: user cannot delete generic templates!
				
				VelocityTemplate velocityTemplate = new VelocityTemplate();
				velocityTemplate.setCommuneId(communeId);
				velocityTemplate.setFrontEndServiceId(serviceId);
				//do not retrieve serviceId using pkg.
				velocityTemplate.setServicePackage(null);
				velocityTemplate.setKey(shortkey);
				velocityTemplate.setDescription("");
				velocityTemplate.setTemplateBody("");
				velocityTemplate.setTemplateBodyMapper("");
				velocityTemplate.setTemplateSubject("");
				velocityTemplate.setTemplateSubjectMapper("");
				
				List <VelocityTemplate> templates = new ArrayList <VelocityTemplate> ();
				templates.add(velocityTemplate);
				VelocityTemplateDataVO templatesVO = VelocityTemplatesUtils.buildVelocityTemplateVOToUpdate(templates, 
						null, null);
	
				try {
					FEInterface feInterface = this.getFEInterfaceFromFirstRegisteredNode();
					//UPDATE to blank template NOT DELETE GENERAL TEMPLATES
					feInterface.updateVelocityTemplatesData(templatesVO, false);
					
				} catch (FeServiceReferenceException e) {
					logger.error("FeService Reference Exception while getting FEInterface", e);
					
				} catch (RemoteException e) {
					logger.error("Remote exception while getting FEInterface", e );
				} catch (PersistenceBrokerException e) {
					// TODO Auto-generated catch block
					logger.error("Remote exception while getting FEInterface", e );
				}				
				
			}
				
		}
	}

	
	/**
	 * @return
	 * @throws PersistenceBrokerException
	 * @throws FeServiceReferenceException
	 */
	private FEInterface getFEInterfaceFromFirstRegisteredNode()
			throws PersistenceBrokerException, FeServiceReferenceException {
		
		boolean interfaceFound = false;
		FEInterface feInterface = null;
		
		Map<Integer, FENodeDTO> registeredNodesWithBEServices = persistenceBroker.getRegisteredNodesWithBEServices();
		Iterator<Entry<Integer, FENodeDTO>> iterator = registeredNodesWithBEServices.entrySet().iterator();
		
		while (iterator.hasNext() && !interfaceFound) {
			Entry<Integer, FENodeDTO> current = iterator.next();
			FENodeDTO feNode = current.getValue();
			
			try {
				feInterface = this.getFEInterface(feNode.getFeServiceURL());
				feInterface.echo("test");
				interfaceFound = true;
			}
			catch (FeServiceReferenceException e) {
				//Do nothing
			} catch (RemoteException e) {
				//Do nothing
			}
		}
		
		return feInterface;
	}
	
	
	private boolean executeFeConfigureTableValueProperty(FEInterface feInterface, TableValuePropertyVO tableValuePropertyVO) {
		
		boolean availableService = true;	
		try {
			feInterface.configureTableValueProperty(tableValuePropertyVO);

		} catch (RemoteException e){
			logger.error(e.getMessage());
			availableService = false;
		}
		return availableService;
	}

	/**
	 * @return
	 */
	private List<Option> getFrameworkLocales() {
		return persistenceBroker.getFrameworkLocales();
	}
	
	/**
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceLocales(String servicePackage) {
		return persistenceBroker.getServiceLocales(servicePackage);
	}

	/**
	 * @return
	 */
	private List<Option> getFrameworkRegisterableLocales() {
		return persistenceBroker.getFrameworkRegisterableLocales();
	}
	
	/**
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceRegisterableLocales(String servicePackage) {
		return persistenceBroker.getServiceRegisterableLocales(servicePackage);
	}
	
	/**
	 * @return
	 */
	private List<Option> getFERegisteredServicesPackages() {
		return persistenceBroker.getFEServicesRegisteredPackages();
	}

	private ILazyPagedListHolder prepareServiceLabelsList(String servicePackage, String language, int pageSize) throws LazyPagedListHolderException {

		List<String> rowColumnsIdentifiersServiceLabels = new ArrayList<String>();
		rowColumnsIdentifiersServiceLabels.add("id");

		List<String> editableRowColumnsServiceLabels = new ArrayList<String>();
		editableRowColumnsServiceLabels.add("_value");

		String serviceLabelQuery = serviceLabelsQuery.replace("!", "'" + servicePackage + "'");
		List<String> visibleColumnsNamesServiceLabels = new ArrayList<String>();
		visibleColumnsNamesServiceLabels.add("_key");
		visibleColumnsNamesServiceLabels.add("_value");
		visibleColumnsNamesServiceLabels.add("Lingua");
		if (!StringUtils.isEmptyString(language)) {
			String buffer = this.getProperty(Constants.Queries.SERVICE_MESSAGES_LOCALE_BY_ID).replace("?", "'" + language + "'");
			serviceLabelQuery = buffer.replace("!", "'" + servicePackage + "'");

			editableRowColumnsServiceLabels = new ArrayList<String>();
			editableRowColumnsServiceLabels.add("Valore");
			
			visibleColumnsNamesServiceLabels = new ArrayList<String>();
			visibleColumnsNamesServiceLabels.add("Chiave");
			visibleColumnsNamesServiceLabels.add("Valore");
			visibleColumnsNamesServiceLabels.add("Lingua");
		}
		
		ILazyPagedListHolder pagedListHolderServiceLabels = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.SERVICE_LABELS_LIST, dataSourcePeopleDB, serviceLabelQuery, pageSize, 
				rowColumnsIdentifiersServiceLabels, editableRowColumnsServiceLabels, false);
		pagedListHolderServiceLabels.setDeleteActionEnabled(false);
		
		pagedListHolderServiceLabels.setVisibleColumnsNames(visibleColumnsNamesServiceLabels);
		
		return pagedListHolderServiceLabels;
		
	}

	
	
	/**
	 * Populate tableValues TableId combobox for generic comune
	 * @param processName the process name also corresponds to servicePackage 
	 * @return
	 */
	private List <Option> getServiceTableValuesTableId(String servicePackage) {
		return persistenceBroker.getServiceTableValuesTableId(servicePackage, null);
	}
	
	
	private String setQueryParameters(String query, String[] parameters) {
		 
		StringBuffer result = new StringBuffer();
		String[] splitted = query.split("\\?");
		for(int i=0; i<splitted.length-1; i++){
			result.append(splitted[i]).append(parameters[i]);
		}
		result.append(splitted[splitted.length-1]);
			
		return result.toString();
	}
	
	
	
	private ILazyPagedListHolder prepareFrameworkLabelsList(String language, int pageSize) throws LazyPagedListHolderException {

		List<String> rowColumnsIdentifiersFrameworkLabels = new ArrayList<String>();
		rowColumnsIdentifiersFrameworkLabels.add("id");

		List<String> editableRowColumnsFrameworkLabels = new ArrayList<String>();
		editableRowColumnsFrameworkLabels.add("_value");

		String query = frameworkLabelsQuery;
		List<String> visibleColumnsNamesFrameworkLabels = new ArrayList<String>();
		visibleColumnsNamesFrameworkLabels.add("_key");
		visibleColumnsNamesFrameworkLabels.add("_value");
		visibleColumnsNamesFrameworkLabels.add("Lingua");
		if (!StringUtils.isEmptyString(language)) {
			query = this.getProperty(Constants.Queries.FRAMEWORK_MESSAGES_LOCALE_BY_ID).replace("?", "'" + language + "'");

    		editableRowColumnsFrameworkLabels = new ArrayList<String>();
    		editableRowColumnsFrameworkLabels.add("Valore");
			
    		visibleColumnsNamesFrameworkLabels = new ArrayList<String>();
    		visibleColumnsNamesFrameworkLabels.add("Chiave");
    		visibleColumnsNamesFrameworkLabels.add("Valore");
    		visibleColumnsNamesFrameworkLabels.add("Lingua");
		}
		
		ILazyPagedListHolder pagedListHolderFrameworkLabels = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.FRAMEWORK_LABELS_LIST, dataSourcePeopleDB, query, pageSize, 
				rowColumnsIdentifiersFrameworkLabels, editableRowColumnsFrameworkLabels, false);
		pagedListHolderFrameworkLabels.setDeleteActionEnabled(false);
		
		pagedListHolderFrameworkLabels.setVisibleColumnsNames(visibleColumnsNamesFrameworkLabels);

		return pagedListHolderFrameworkLabels;
		
	}
	
	
	
	/**
	 * Prepare tablevalues paged list holder
	 * 
	 * @param selectedTableId
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareTablevaluesPropertiesList(String selectedTableId, int pageSize) throws LazyPagedListHolderException {
		
		ILazyPagedListHolder pagedListHolderTablevalues = null;
		
		String tableValueRef = selectedTableId;

		/// Populate pagedListHolder for tablevalues
		List<String> rowColumnsIdentifiersTablevalues = new ArrayList<String>();
		rowColumnsIdentifiersTablevalues.add("id");
		rowColumnsIdentifiersTablevalues.add("value");

		List<String> editableRowColumnsTablevalues = new ArrayList<String>();
		editableRowColumnsTablevalues.add("value");
		
		String[] queryParams = new String[] { tableValueRef };
		pagedListHolderTablevalues = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.TABLEVAUES_LIST, dataSourcePeopleDB, 
				setQueryParameters(this.getProperty(Constants.Queries.TABLEVALUES_PROPERTIES_BY_TABLEVALUEREF), queryParams),
				pageSize, rowColumnsIdentifiersTablevalues, 
				editableRowColumnsTablevalues, true);
		
		List<String> visibleColumnsNamesTablevalues = new ArrayList<String>();
		visibleColumnsNamesTablevalues.add("value");
		pagedListHolderTablevalues.setVisibleColumnsNames(visibleColumnsNamesTablevalues);
    		

		return pagedListHolderTablevalues;
	}



	/**
	 * 
	 * @param feService
	 * @param pageSize
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareGenericVelocityTemplatesList(int pageSize) throws LazyPagedListHolderException {
		
		/// Populate pagedListHolder for templates
		List<String> rowColumnsIdentifiers = new ArrayList<String>();
		rowColumnsIdentifiers.add("_communeId");
		rowColumnsIdentifiers.add("_serviceId");
		rowColumnsIdentifiers.add("Chiave");
		
		//Retreive velocity template tables
		try {
			//pass the package to get right templates  
			refreshVelocityTemplateTable();
			
		} catch (SQLException e) {
			logger.error("Error creating Temp table for velocity templates.", e);
		}

		ILazyPagedListHolder plhVelocityTemplatesList = 
				LazyPagedListHolderFactory.getLazyPagedListHolder(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST,
						dataSourcePeopleDB, this.getProperty(Constants.Queries.VELOCITY_TEMPLATES_FOR_SERVICE), pageSize, rowColumnsIdentifiers, 
						getVelocityTemplatesRowActions());
		
		List<String> visibleColumns = new ArrayList<String>();
		visibleColumns.add("Chiave");
		visibleColumns.add("_description");
		plhVelocityTemplatesList.setVisibleColumnsNames(visibleColumns);
		
		return plhVelocityTemplatesList;
	}
	
	
	
	/**
	 * @throws SQLException 
	 * 
	 */
	private void refreshVelocityTemplateTable() throws SQLException {
		
		//Rewrite table 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		VelocityTemplateDataVO velocityTemplatesData = null;
		
		try {
			connection = this.dataSourcePeopleDB.getConnection();
			//Get data from proper WS interface
			FEInterface feInterface = this.getFEInterfaceFromFirstRegisteredNode();
			velocityTemplatesData = (VelocityTemplateDataVO) feInterface.getVelocityTemplatesData(null, null, false);

		} catch (FeServiceReferenceException e) {
			logger.warn("Unable to get FeService reference from first registered node.", e);
		} catch (RemoteException e) {
			logger.warn("Remote exeption refreshing Velocity Template Table.", e);
		} catch (PersistenceBrokerException e) {
			logger.warn("Presistence exception: unable to get FeService reference from first registered node.", e);
		} 
		
		try {
			//Rewrite table (even if newData is null)
			VelocityTemplatesUtils.rewriteVelocityTemplateTable(velocityTemplatesData, connection);
			
		} catch(SQLException e) {
			logger.error("Unable to rewrite table for velocity temoplates data.", e);
			try {
				connection.rollback();
			} catch (Exception ignore) {}
			
			throw new SQLException("Unable to rewrite table for velocity templates data.");
		}
		finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException ignore) {
			}
		}
	}
	
	

	/**
	 * Populate Actions for Velocity Templates
	 * 
	 * @return
	 */
	private List<Command> getVelocityTemplatesRowActions() {

		List<Command> result = new ArrayList<Command>();

		result.add(new InputCommand("deleteVelocityTemplate", "deleteVelocityTemplte", null, "delete.png",
				"delete-dis.png", AbstractCommand.CommandActions.delete));
		result.add(new LinkCommand("editVeloityTemplate", "modificaTemplateVelocity.do", null, "edit.png",
				"edit-dis.png", AbstractCommand.CommandActions.edit));

		return result;
	}
	
	
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.GET)
    public String setupEditVelocityTemplateForm(@RequestParam String action, @RequestParam String _communeId,
    		@RequestParam String _serviceId, @RequestParam String Chiave,
    		@RequestParam String plhId, ModelMap model, HttpServletRequest request) {
		

		//Populate selcted VelocityTemplate to be shown
		VelocityTemplate velocityTemplate = VelocityTemplatesUtils
				.buildVelocityTemplate(dataSourcePeopleDB, null, null, Chiave, "Tutti i servizi", null, "Tutti i nodi");

		model.put("includeTopbarLinks", true);
			
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
				
		model.addAttribute("tab", "velocityTemplates");
		
		model.addAttribute("velocityTemplate", velocityTemplate);
			
		this.setPageInfo(model, "addOrEditVelocityTemplate.title", null, "velocityTemplates");
			
		return getStaticProperty("frameworkGenericMessages.addAndEditVelocityTemplate.view");
		
	}
	
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.POST)
	public String processEditSubmit(@ModelAttribute("velocityTemplate") VelocityTemplate velocityTemplate, 
			BindingResult bindingResult, HttpServletRequest request,  ModelMap model) {
		
		boolean isCancel = isParamInRequest(request, "cancel");
		boolean isUpdate = isParamInRequest(request, "updateVelocityTemplate");
		
		if (isCancel) {
			logger.debug("Template editing canceled");
		}
		
		if (isUpdate) {
			List <VelocityTemplate> templates = new ArrayList <VelocityTemplate> ();
			templates.add(velocityTemplate);
			VelocityTemplateDataVO templateDataVO = VelocityTemplatesUtils.buildVelocityTemplateVOToUpdate(templates, 
					null, null);
			
			FEInterface feInterface;
			try {
				feInterface = this.getFEInterfaceFromFirstRegisteredNode();
				feInterface.updateVelocityTemplatesData(templateDataVO, false);
				
			} catch (FeServiceReferenceException e) {
				logger.error("Exception processing Edit for a Velocity Template: " +
						"unable to get Front End Interface Reference.");
			} catch (RemoteException e) {
				logger.error("Exception processing Edit for a Velocity Template: " +
						"error executing updateVelocityTemplatesData.");
			} catch (PersistenceBrokerException e) {
				logger.error("Persistence Exception processing Edit for a Velocity Template: " +
						"error executing updateVelocityTemplatesData.");
			}
		}
		
		model.put("includeTopbarLinks", true);
		
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "generalmessages.listAndModify.title", null, "frameworkGM");
    	
    	return "redirect:elenco.do";
	}
	
	

}

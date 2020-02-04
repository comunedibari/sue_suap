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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.ListColumnFilter;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.domain.FENode;
import it.people.console.domain.PairElement;
import it.people.console.domain.VelocityTemplate;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.IPersistenceManager;
import it.people.console.persistence.PersistenceManagerFactory;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.beans.support.RowCheckbox;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.persistence.exceptions.PersistenceManagerException;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.utils.velocityTemplates.VelocityTemplatesUtils;
import it.people.console.web.controllers.validator.FeNodeValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.VelocityTemplateDataVO;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/NodiFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feNode", Constants.ControllerUtils.APPLIED_FILTERS_KEY, "bundleRef"})
public class FENodesController extends AbstractListableController {

	private FeNodeValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	private String query = "SELECT codicecomune 'Codice', comune 'Comune', id, nodofe 'Nodo FE', reference 'URL', controllodelegheattivo 'Controllo Deleghe', controllodelegheurl, aooprefix, announcementMessage, showAnnouncement, firmaOnLine, firmaOffLine FROM fenode";

	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	private int pageSize = 10;
	
	@Autowired
	public FENodesController(FeNodeValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feNode");
	}
	
    @RequestMapping(value = "/elenco.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	if (request.getSession().getAttribute("feNodePageSize") != null) {
    		pageSize = (Integer) request.getSession().getAttribute("feNodePageSize"); 
    	}
    	
    	FENode feNode = null;
    	
    	if (request.getSession().getAttribute("feNode") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("NodiFe/elenco.do")) {
        	feNode = new FENode();

        	try {
        		//List of registered FE nodes
        		List<String> rowColumnsIdentifiersFENodes = new ArrayList<String>();
        		rowColumnsIdentifiersFENodes.add("id");
        		rowColumnsIdentifiersFENodes.add("codicecomune");
        		rowColumnsIdentifiersFENodes.add("reference");

        		List<String> editableRowColumnsFENodes = new ArrayList<String>();
        		editableRowColumnsFENodes.add("comune");
        		editableRowColumnsFENodes.add("nodofe");
        		
        		ILazyPagedListHolder pagedListHolderFENodes = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.NODES_LIST, dataSourcePeopleDB, query, pageSize, rowColumnsIdentifiersFENodes, getRowActions());
        		
        		List<String> visibleColumnsNamesFENodes = new ArrayList<String>();
        		visibleColumnsNamesFENodes.add("codicecomune");
        		visibleColumnsNamesFENodes.add("comune");
        		visibleColumnsNamesFENodes.add("nodofe");
        		visibleColumnsNamesFENodes.add("reference");
        		visibleColumnsNamesFENodes.add("controllodelegheattivo");
        		pagedListHolderFENodes.setVisibleColumnsNames(visibleColumnsNamesFENodes);

        		Map<String, Object> feNodesRowModelers = new HashMap<String, Object>();
        		feNodesRowModelers.put("controllodelegheattivo", new RowCheckbox());
        		pagedListHolderFENodes.setRowModelers(feNodesRowModelers);
        		
        		feNode.addPagedListHolder(pagedListHolderFENodes);
        		
    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {

    		feNode = (FENode)request.getSession().getAttribute("feNode");
    		processListHoldersRequests(request.getQueryString(), feNode);
    	}

    	try {
			applyColumnSorting(request, feNode);
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
				feNode.getPagedListHolder(Constants.PagedListHoldersIds.NODES_LIST).applyFilters(appliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}
		

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

    	model.addAttribute("feNode", feNode);
    	    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "fenodes.listAndAdd.title", null, "feNodes");
    	
    	return getStaticProperty("fenodes.listAndAdd.view");
    	
    }
    

    @RequestMapping(value = "/elenco.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("feNode") FENode feNode, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isSaveInRequest = isParamInRequest(request, "saveNewFeNode");
    	
    	if (isParamInRequest(request, "saveNewFeNode")) {
        	validator.validateNew(feNode, result);
    	}

    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state...");
    	}
    	List<FilterProperties> updatedAppliedFilters = updateAppliedFilters(request, filtersList);
    	if (logger.isDebugEnabled()) {
    		logger.debug("Saving filters state done.");
    	}
    	
    	if (isParamInRequest(request, "applyFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Applying " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
				feNode.getPagedListHolder(Constants.PagedListHoldersIds.NODES_LIST).applyFilters(updatedAppliedFilters);
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
				feNode.getPagedListHolder(Constants.PagedListHoldersIds.NODES_LIST).removeFilters();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters cleared.");
        	}
        	model.addAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY, updatedAppliedFilters);
    	}
    	
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, feNode, model);
    		savePageSizeInSession(feNode, request);
    		
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:/NodiFe/conferma.do";
    		}
    	}

    	//Salvataggio del nuovo nodo di FE
    	if (isSaveInRequest && !result.hasErrors()) {
    		if (saveNode(feNode, result)) {
        		feNode.clear();
        		feNode.getPagedListHolder(Constants.PagedListHoldersIds.NODES_LIST).update();
        		return  "redirect:elenco.do";
    		} else {
            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
            	
            	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
            	
            	setRowsPerPageDefaultModelAttributes(model);
            	
            	model.addAttribute("feNode", feNode);
            	
            	this.setPageInfo(model, "fenodes.listAndAdd.title", null, "feNodes");
            	
    			return getStaticProperty("fenodes.listAndAdd.view");
    		}
    	}
    	else {
        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
        	
        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
        	
        	model.addAttribute("feNode", feNode);
        	
        	setRowsPerPageDefaultModelAttributes(model);
        	
        	this.setPageInfo(model, "fenodes.listAndAdd.title", null, "feNodes");
    		
    		return getStaticProperty("fenodes.listAndAdd.view");    		
    		
    	}
    }

	/**
	 * @param feNode
	 * @param request
	 */
	private void savePageSizeInSession(FENode feNode, HttpServletRequest request) {
		int pageSize = feNode.getPagedListHolder(Constants.PagedListHoldersIds.NODES_LIST).getPageSize();
		request.getSession().setAttribute("feNodePageSize", pageSize);
	}

    @RequestMapping(value = "/modifica.do", method = RequestMethod.GET)
    public String setupEditForm(@RequestParam String action, @RequestParam String id, 
    		@RequestParam String plhId, @RequestParam(value = "tab", required = false) String tabName,
    		ModelMap model, HttpServletRequest request) {


    	
    	//Init all controls
    	if (tabName == null) {
    		tabName = "messagesList";
    	}
    	
    	FENode feNode = null;
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Action param value = '" + action + "'.");
    		logger.debug("Id param value = '" + id + "'.");
    		logger.debug("Action param value = '" + plhId + "'.");
    	}
    	
    	if (request.getSession().getAttribute("feNode") == null) {
        	feNode = new FENode();
    		try {
				feNode.addPagedListHolder(this.prepareServiceLabelsList(feNode.getMunicipalityCode(), Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
						feNode.getSelectedServicesLanguage(), model));
			} catch (PagedListHoldersCacheException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
    	}
    	else {

    		feNode = (FENode)request.getSession().getAttribute("feNode");
    		for(Object row : feNode.getPagedListHolder(plhId).getPageList()) {
				Map<String, Object> mappedRow = null;
    			if (row instanceof EditableRow) {
    				EditableRow editableRow = (EditableRow)row;
    				mappedRow = editableRow.getRow();
    			}
    			else {
    				mappedRow = (Map<String, Object>)row;
    			}
    			if (mappedRow != null) {
    				if (String.valueOf(mappedRow.get("id")).equalsIgnoreCase(id)) {
    					feNode.setAooPrefix((String)mappedRow.get("aooprefix"));
    					feNode.setDelegationControlEnabled(CastUtils.integerToBoolean((Integer)mappedRow.get("controllodelegheattivo")));
    					feNode.setDelegationControlServiceURL((String)mappedRow.get("controllodelegheurl"));
    					feNode.setFeServiceURL((String)mappedRow.get("reference"));
    					feNode.setMunicipality((String)mappedRow.get("comune"));
    					feNode.setMunicipalityCode((String)mappedRow.get("codicecomune"));
    					feNode.setName((String)mappedRow.get("nodofe"));
    					feNode.setId(new Long(((Integer)mappedRow.get("id")).intValue()));
    					feNode.setAnnouncementMessage((String)mappedRow.get("announcementMessage"));
    					feNode.setShowAnnouncement(CastUtils.integerToBoolean((Integer)mappedRow.get("showAnnouncement")));
    					feNode.setOnlineSign(CastUtils.integerToBoolean((Integer)mappedRow.get("firmaOnLine")));
    					feNode.setOfflineSign(CastUtils.integerToBoolean((Integer)mappedRow.get("firmaOffLine")));
    					
    	        		try {
    	        			
    	        			if (!feNode.getPagedListHolders().containsKey(Constants.PagedListHoldersIds.NODE_LABELS_LIST)) {
								feNode.addPagedListHolder(this.prepareServiceLabelsList(feNode.getMunicipalityCode(), Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
										feNode.getSelectedServicesLanguage(), model));
    	        			}
							else {
								feNode.updatePagedListHolder(this.prepareServiceLabelsList(feNode.getMunicipalityCode(), Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
										feNode.getSelectedServicesLanguage(), model));
							}
							
						} catch (PagedListHoldersCacheException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LazyPagedListHolderException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}    					
    					break;
    				}
    			}
    		}
    	}
    	
    	
    	//ListHolder population
    	
    	//Populate Plh for VelocityTemplates
    	if (tabName.equals("velocityTemplates")) {
    		
			try {
				ILazyPagedListHolder velocityTemplatesList = prepareVelocityTemplatesList(feNode, pageSize);
				if (!feNode.getPagedListHolders().containsKey(velocityTemplatesList.getPagedListId())) {
					feNode.addPagedListHolder(velocityTemplatesList);
				} else {
					feNode.updatePagedListHolder(velocityTemplatesList);
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating velocity templates List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating velocity templates List");
			}
		}
    	

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
    	
    	model.addAttribute("feNode", feNode);

    	model.addAttribute("nodeLocales", getServiceLocalesByNodeId(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode()));

    	model.addAttribute("nodeRegisterableLocales", getServiceRegisterableLocalesByNodeId(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode()));

    	model.addAttribute("tab", tabName);
    	
    	model.addAttribute("tabbedPaneHeaders", getTabbedPaneHeaders());
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "fenodes.nodeDetails.title", null, "feNodes");
    	
    	return getStaticProperty("fenodes.nodeDetails.view");
    	
    }

    @RequestMapping(value = "/modifica.do", method = RequestMethod.POST)
    public String processEditSubmit(ModelMap model, @RequestParam(value = "tab", required = false) String tabName,
    		@ModelAttribute("feNode") FENode feNode, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isUpdateFeNode = isParamInRequest(request, "updateFeNode");
    	boolean isCancel = isParamInRequest(request, "cancel");

    	if (isParamInRequest(request, "registerNodeLanguage")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Saving node language...");
        		logger.debug(feNode.getSelectedServicesRegisterableLanguage());
        	}
    		try {
				FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
				feInterface.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode(), 
						feNode.getSelectedServicesRegisterableLanguage(), "1", null);
				this.persistenceBroker.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode(), 
						feNode.getSelectedServicesRegisterableLanguage(), "1", null);
			} catch (FeServiceReferenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}

    	if ((feNode.getPreviousSelectedServicesLanguage() == null) || !feNode.getSelectedServicesLanguage().equalsIgnoreCase(feNode.getPreviousSelectedServicesLanguage())) {
			model.addAttribute("bundleRef", this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(
					Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
					feNode.getMunicipalityCode(), 
					"= '" + feNode.getSelectedServicesLanguage() + "'"));
    	}

		try {
	    	if (!it.people.feservice.utils.StringUtils.nullToEmptyString(
	    			feNode.getSelectedServicesLanguage()).equalsIgnoreCase(
	    					it.people.feservice.utils.StringUtils.nullToEmptyString(
	    							feNode.getPreviousSelectedServicesLanguage()))) {
	    		feNode.updatePagedListHolder(this.prepareServiceLabelsList(feNode.getMunicipalityCode(), Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
	    				feNode.getSelectedServicesLanguage(), model));
	    		feNode.setPreviousSelectedServicesLanguage(feNode.getSelectedServicesLanguage());
	    	}
		} catch (PagedListHoldersCacheException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LazyPagedListHolderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		//Process listHolder Requests
		if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, feNode, model);
    	}
		
		//Refresh velocity template PLH
    	if ((tabName != null) && tabName.equals("velocityTemplates")) {

    		try {
				if (!feNode.getPagedListHolders().containsKey(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST)) {
					feNode.addPagedListHolder(prepareVelocityTemplatesList(feNode, pageSize));
				} else {
					int pageSizeVelocityTemplates = feNode.getPagedListHolder(
							Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST).getPageSize();
					
					feNode.updatePagedListHolder(prepareVelocityTemplatesList(feNode, pageSizeVelocityTemplates));
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating velocity templates List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating velocity templates List");
			}

    	}

    	if (isUpdateFeNode) {
        	validator.validateUpdate(feNode, result);

	    	if (!result.hasErrors()) {
	    		//Update locale e remota di FeNode
	    		if (updateNode(feNode, result)) {
	    			
		    		feNode.clear();
		    		return  "redirect:elenco.do";
	    		}
	    	}
    	}

    	if (isCancel) {
    		return  "redirect:elenco.do";
    	} 

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
    	
    	model.addAttribute("feNode", feNode);
    	
    	model.addAttribute("nodeLocales", getServiceLocalesByNodeId(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode()));

    	model.addAttribute("tab", tabName);
    	
    	model.addAttribute("tabbedPaneHeaders", getTabbedPaneHeaders());
    	
    	model.addAttribute("nodeRegisterableLocales", getServiceRegisterableLocalesByNodeId(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode()));

    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "fenodes.nodeDetails.title", null, "feNodes");
    	
    	return getStaticProperty("fenodes.nodeDetails.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model,  
    		@ModelAttribute("feNode") FENode feNode, BindingResult result, 
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
			String nodeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
			String communeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("codicecomune"));
			String reference = String.valueOf(editableRowInputData.getRowIdentifiers().get("reference"));
			deleteNode(communeId, reference, nodeId);
			
			feNode.getPagedListHolder(pagedListHolderId).update();
 
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:elenco.do";
    }
    
    private boolean saveNode(FENode feNode, BindingResult bindingResult) {
    	
    	boolean result = true;
    	
		IPersistenceManager feNodesPersistenceManager = PersistenceManagerFactory.getInstance()
			.get(FENode.class, Mode.WRITE);
		try {
	    	FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
	    	
	    	//Use service to save node
	    	feInterface.registerNodeWithAoo(feNode.getMunicipality(), feNode.getMunicipalityCode(), 
	    			feNode.getName(), feNode.getAooPrefix(), feNode.getAnnouncementMessage(), new Boolean(feNode.isShowAnnouncement()),
	    			feNode.isOnlineSign(), feNode.isOfflineSign());
	    	
			feNodesPersistenceManager.set(feNode);
		} catch (PersistenceManagerException e) {
			logger.error("Error while saving new fe node.", e);
			result = false;
			bindingResult.rejectValue("error", "error.generic");
		} catch (FeServiceReferenceException e) {
			logger.error("Error while saving new fe node.", e);
			result = false;
			bindingResult.rejectValue("error", "error.generic");
		} catch (RemoteException e) {
			logger.error("Error while saving new fe node.", e);
			result = false;
			bindingResult.rejectValue("error", "error.generic");
		}
		finally {
			feNodesPersistenceManager.close();
		}
    	
		return result;
		
    }
    
    private boolean updateNode(FENode feNode, BindingResult bindingResult) {

    	boolean result = true;
    	
    	//recupera le interfacce DB locale
		IPersistenceManager readableFeNodesPersistenceManager = PersistenceManagerFactory.getInstance()
			.get(FENode.class, Mode.READ);

		IPersistenceManager writableFeNodesPersistenceManager = PersistenceManagerFactory.getInstance()
			.get(FENode.class, Mode.WRITE);
    	
    	try {
    		//Aggiorna FeNode in remote DB people
	    	FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
	    	feInterface.registerNodeWithAoo(feNode.getMunicipality(), feNode.getMunicipalityCode(), 
	    			feNode.getName(), feNode.getAooPrefix(), feNode.getAnnouncementMessage(), new Boolean(feNode.isShowAnnouncement()),
	    			feNode.isOnlineSign(), feNode.isOfflineSign());

	    	//Aggiorna in locale DB peopledb
			FENode storedFeNode = (FENode)readableFeNodesPersistenceManager.get(feNode.getId());
			storedFeNode.setAooPrefix(feNode.getAooPrefix());
			storedFeNode.setDelegationControlEnabled(feNode.isDelegationControlEnabled());
			storedFeNode.setDelegationControlServiceURL(feNode.getDelegationControlServiceURL());
			storedFeNode.setFeServiceURL(feNode.getFeServiceURL());
			storedFeNode.setMunicipality(feNode.getMunicipality());
			storedFeNode.setMunicipalityCode(feNode.getMunicipalityCode());
			storedFeNode.setName(feNode.getName());
			
			//Firma online - offline
			storedFeNode.setOnlineSign(feNode.isOnlineSign());
			storedFeNode.setOfflineSign(feNode.isOfflineSign());
			
			//Aggiungi campi messaggio e abilitazione messaggio
			storedFeNode.setAnnouncementMessage(feNode.getAnnouncementMessage());
			storedFeNode.setShowAnnouncement(feNode.isShowAnnouncement());
			
			writableFeNodesPersistenceManager.set(storedFeNode);
			
		} catch (PersistenceManagerException e) {
			logger.error("Error while saving an update for fe node.", e);
			bindingResult.rejectValue("error", "error.generic");
			result = false;

		} catch (FeServiceReferenceException e) {
			logger.debug("Update of fe node through FeInterface failed beacause WS URL is not reachable.", e);
			bindingResult.rejectValue("error", "error.fenodes.nodeDetails.WSURL.invokeException");
			result = false;
		} catch (RemoteException e) {
			logger.debug("Update of fe node through FeInterface failed beacause WS URL is not reachable.", e);
			bindingResult.rejectValue("error", "error.fenodes.nodeDetails.WSURL.invokeException");
			result = false;
		}
		finally {
			writableFeNodesPersistenceManager.close();
			readableFeNodesPersistenceManager.close();
		}
		
		return result;    	
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
    	
    	Collection<String> communeCodes = this.persistenceBroker.getRegisteredNodesCodes();
    	if (!communeCodes.isEmpty()) {
    		for(String communeCode : communeCodes) {
    	    	listColumnFilter.addFilterAllowedValue(new Option(communeCode, communeCode));
    		}
    	}
    	
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
    
    private List<Command> getRowActions() {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new InputCommand("deleteFENode", "deleteFENode", null, 
    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
    	result.add(new LinkCommand("editFENode", "modifica.do", null, 
    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
    	result.add(new LinkCommand("viewLog", "viewLog.do", null, 
    			"log.png", "log-dis.png", AbstractCommand.CommandActions.viewLog));
    	result.add(new LinkCommand("viewAuditConversations", "viewAuditConversations.do", null, 
    			"auditConversations.png", "auditConversations-dis.png", AbstractCommand.CommandActions.viewAuditConversations));
    	result.add(new LinkCommand("nodeAuditStatistiche", "nodeAuditStatistiche.do", null, 
    			"statistics.png", "statistics-dis.png", AbstractCommand.CommandActions.auditStatistiche));
    	result.add(new LinkCommand("userNotificationsSuggestion", "userNotificationsSuggestion.do", null, 
    			"user_suggestion_signal.png", "user_suggestion_signal-dis.png", AbstractCommand.CommandActions.userNotificationsSuggestion));
    	result.add(new LinkCommand("userNotificationsError", "userNotificationsError.do", null, 
    			"user_error_signal.png", "user_error_signal-dis.png", AbstractCommand.CommandActions.userNotificationsError));
    	
    	return result;
    	
    }

    /**
     * @param communeId
     * @param reference
     * @param nodeId
     * @return
     */
    private boolean deleteNode(String communeId, String reference, String nodeId) {
    	
    	boolean result = true;
    	
    	Connection connection = null;
        Statement statementListing = null;
        Statement statementUpdate = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
	    	FEInterface feInterface = this.getFEInterface(reference);
	    	feInterface.deleteAllServices(communeId);

	    	// Elimina il front-end
	    	connection.setAutoCommit(false);
	    	statementListing = connection.createStatement();
	    	statementUpdate = connection.createStatement();

	    	// determina l'ID del servizio
	    	String query = "SELECT id FROM service WHERE nodeid = " + nodeId;
	    	ResultSet rs = statementListing.executeQuery(query);            

	    	while(rs.next()) {
	    		// Esistono servizi da eliminare
	    		String idService = rs.getString(1);

	    		// configuration
	    		statementUpdate.executeUpdate("DELETE FROM configuration WHERE serviceid = " + idService);

	    		// reference
	    		statementUpdate.executeUpdate("DELETE FROM reference WHERE serviceid = " + idService);

	    		// service
	    		statementUpdate.executeUpdate("DELETE FROM service WHERE id = " + idService);
	    		
	    	}
	    	rs.close();

    		// benodes
    		statementUpdate.executeUpdate("DELETE FROM benode WHERE nodeid = " + nodeId);
	    	
	    	// fenode
	    	statementUpdate.executeUpdate("DELETE FROM fenode WHERE id = " + nodeId);

	    	connection.commit();
	    	
	    	
		} catch (RemoteException e) {
			logger.error("Error while deleting fe node " + communeId, e);
			result = false;
//			bindingResult.rejectValue("error", "error.generic");
		} catch (FeServiceReferenceException e) {
			logger.error("Error while deleting fe node " + communeId, e);
			result = false;
//			bindingResult.rejectValue("error", "error.generic");
        } catch (SQLException ex) {
            try {
            	connection.rollback();
            } catch(SQLException rollbackEx) {
                logger.error("Impossibile effettuare il roll-back");
            }
        } finally {
        	try {
	            if (statementListing != null) {
	            	statementListing.close();
	            }
	            if (statementUpdate != null) {
	            	statementUpdate.close();
	            }
	            if (connection != null) {
	            	connection.close();
	            }
        	} catch(SQLException ignore) {
        		
        	}
        }        
    	
		return result;
    	
    }

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
		
		if (action.equals(AbstractCommand.CommandActions.delete)) {
			if ((Boolean) request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Delete confirmation required, no action.");
				}
			} else {
				
				
				//Multi tab management
				if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.NODES_LIST)) {
					
					FENode feNode = (FENode)request.getSession().getAttribute("feNode");
					/* delete */
					String nodeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
					String communeId = String.valueOf(editableRowInputData.getRowIdentifiers().get("codicecomune"));
					String reference = String.valueOf(editableRowInputData.getRowIdentifiers().get("reference"));
					deleteNode(communeId, reference, nodeId);
					
					feNode.getPagedListHolder(pagedListHolderId).update();
					

				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST)) {
					
					Map<String, Object> rowIdentifier = editableRowInputData.getRowIdentifiers();
					
					FENode feNode = (FENode)request.getSession().getAttribute("feNode");
					
					//Update and delete
					String communeId = null;
					String serviceId = null;
					String shortkey = null;
					
					communeId =  rowIdentifier.get("_communeId").equals("null") ? null : (String) rowIdentifier.get("_communeId");
					serviceId = rowIdentifier.get("_serviceId").equals("null") ? null : (String) rowIdentifier.get("_serviceId");
					shortkey = (String) rowIdentifier.get("Chiave");
	
						
					//Delete only already customized templates
					if (communeId != null) {
						
						//Build the VO to perform delete
						VelocityTemplateDataVO templatesVO = VelocityTemplatesUtils
								.buildVelocityTemplateVOToDelete(dataSourcePeopleDB, communeId, null, shortkey);
						
						try {
							FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
							
							//DELETE
							feInterface.updateVelocityTemplatesData(templatesVO, true);
							
						} catch (FeServiceReferenceException e) {
							logger.error("FeService Reference Exception while getting FEInterface", e);
							
						} catch (RemoteException e) {
							logger.error("Remote exception while getting FEInterface", e );
						}				
					}
					
				}
			}
		}
		
		if (action.equals(AbstractCommand.CommandActions.save)) {

			if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.NODE_LABELS_LIST)) {
				
				try {
					FENode feNode = (FENode)request.getSession().getAttribute("feNode");
					FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
					String value = (String) editableRowInputData.getInputData().get("Valore");
					String bundleRef = String.valueOf(request.getSession().getAttribute("bundleRef"));
					if (StringUtils.isEmptyString(bundleRef) || bundleRef.equalsIgnoreCase("-1")) {
						feInterface.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								feNode.getMunicipalityCode(), feNode.getSelectedServicesLanguage(), "1", null);
						this.persistenceBroker.registerBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								feNode.getMunicipalityCode(), feNode.getSelectedServicesLanguage(), "1", null);
						bundleRef = String.valueOf(this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, 
								feNode.getMunicipalityCode(), feNode.getSelectedServicesLanguage()));
					}
					long messageId = new Long(Integer.parseInt((String) editableRowInputData.getRowIdentifiers().get("id")));
					String messageKey = this.persistenceBroker.getServiceMessageKeyById(messageId);
					feInterface.updateBundle(Constants.Bundles.FRAMEWORK_NODES_BUNDLE, feNode.getMunicipalityCode(), 
							feNode.getSelectedServicesLanguage(), messageKey, value, "1", null);
					this.persistenceBroker.updateBundle(Long.parseLong(bundleRef), 
							messageKey, value, "1", null);
				} catch (FeServiceReferenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		}

	}


	/**
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceLocalesByNodeId(String servicePackage, String nodeId) {
		return persistenceBroker.getServiceLocalesByNodeId(servicePackage, nodeId);
	}

	/**
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceRegisterableLocalesByNodeId(String servicePackage, String nodeId) {
		return persistenceBroker.getServiceRegisterableLocalesByNodeId(servicePackage, nodeId);
	}
	
	private ILazyPagedListHolder prepareServiceLabelsList(String communeKey, String servicePackage, 
			String language, ModelMap model) throws LazyPagedListHolderException {

		List<String> rowColumnsIdentifiersServiceLabels = new ArrayList<String>();
		rowColumnsIdentifiersServiceLabels.add("id");

		List<String> editableRowColumnsServiceLabels = new ArrayList<String>();
		editableRowColumnsServiceLabels.add("Valore");

		List<String> visibleColumnsNamesServiceLabels = new ArrayList<String>();
		visibleColumnsNamesServiceLabels.add("Chiave");
		visibleColumnsNamesServiceLabels.add("Valore");
		visibleColumnsNamesServiceLabels.add("Lingua");
		
		String locale = null;
		if (StringUtils.isEmptyString(language)) {
			language = "is null";
		} else {
			language = "= '" + language + "'";
			locale = language;
		}
		String buffer = this.getProperty(Constants.Queries.SERVICE_MESSAGES_LOCALE_BY_NODE_ID_LOCALE).replace("?", language);
		String serviceLabelQuery = buffer.replace("!", "'" + servicePackage + "'").replace("$", "'" + communeKey + "'");
		
		ILazyPagedListHolder pagedListHolderServiceLabels = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.NODE_LABELS_LIST, dataSourcePeopleDB, serviceLabelQuery, 10, 
				rowColumnsIdentifiersServiceLabels, editableRowColumnsServiceLabels, false);
		pagedListHolderServiceLabels.setDeleteActionEnabled(false);
		
		model.addAttribute("bundleRef", this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(servicePackage, communeKey, locale));
		
		pagedListHolderServiceLabels.setVisibleColumnsNames(visibleColumnsNamesServiceLabels);
		
		return pagedListHolderServiceLabels;
		
	}
	
	
	/**
	 * Refresh template table using feNode
	 * 
	 * @param feNode
	 * @throws SQLException
	 */
	private void refreshVelocityTemplateTable(FENode feNode) throws SQLException {
		
		//Rewrite table 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		VelocityTemplateDataVO velocityTemplatesData = null;
		
		try {
			connection = this.dataSourcePeopleDB.getConnection();
			//Get data from proper WS interface 
			;
			FEInterface feInterface = this.getFEInterface(feNode.getFeServiceURL());
			velocityTemplatesData = (VelocityTemplateDataVO) feInterface.getVelocityTemplatesData(
					String.valueOf(feNode.getMunicipalityCode()), null, false);

		} catch (FeServiceReferenceException e) {
			logger.warn("Unable to get FeService reference for node " + feNode.getName(), e);
		} catch (RemoteException e) {
			logger.warn("Remote exeption refreshing Velocity Template Table for node "  + feNode.getName(), e);
		} 
		
		try {
			//Rewrite table (even if newData is null)
			VelocityTemplatesUtils.rewriteVelocityTemplateTable(velocityTemplatesData, connection);
			
		} catch(SQLException e) {
			logger.error("Unable to rewrite table for velocity templates data.", e);
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
			} catch(SQLException ignore) {}
		}
	}

	

	/**
	 * Prepare paged list holder for VelocityTemplates
	 * 
	 * @param feService
	 * @param pageSize
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareVelocityTemplatesList(FENode feNode, int pageSize) throws LazyPagedListHolderException {
		
		/// Populate pagedListHolder for templates
		List<String> rowColumnsIdentifiers = new ArrayList<String>();
		rowColumnsIdentifiers.add("_communeId");
		rowColumnsIdentifiers.add("_serviceId");
		rowColumnsIdentifiers.add("Chiave");
		
		//Retrieve velocity template tables
		try {
			//pass the package to get right templates  
			refreshVelocityTemplateTable(feNode);
			
		} catch (SQLException e) {
			logger.error("Error creating Temp table for velocity templates.", e);
		}

		ILazyPagedListHolder plhVelocityTemplatesList = 
				LazyPagedListHolderFactory.getLazyPagedListHolder(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST,
						dataSourcePeopleDB, this.getProperty(Constants.Queries.VELOCITY_TEMPLATES_FOR_NODE), pageSize, rowColumnsIdentifiers, 
						getVelocityTemplatesRowActions());
		
		List<String> visibleColumns = new ArrayList<String>();
		visibleColumns.add("Chiave");
		visibleColumns.add("_description");
		plhVelocityTemplatesList.setVisibleColumnsNames(visibleColumns);
		
		return plhVelocityTemplatesList;
	}
	
	
	
	/**
	 * Populate Actions for Velocity Templates
	 * @return
	 */
	private List<Command> getVelocityTemplatesRowActions() {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new InputCommand("deleteVelocityTemplate", "deleteVelocityTemplte", null, 
    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
    	result.add(new LinkCommand("editVeloityTemplate", "modificaTemplateVelocity.do", null, 
    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
    	
    	return result;
    }
	
	
	
	private List<PairElement<String, String>> getTabbedPaneHeaders() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("messagesList", "Messaggi"));
		result.add(new PairElement<String, String>("velocityTemplates", "Template Velocity"));
		return result;
		
	}
	
	
	
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.GET)
    public String setupEditVelocityTemplateForm(@RequestParam String action, @RequestParam String _communeId,
    		@RequestParam String _serviceId, @RequestParam String Chiave,
    		@RequestParam String plhId, ModelMap model, HttpServletRequest request,
    		@ModelAttribute("feNode") FENode feNode) {
		
		//Check _serviceID from plh for "null" string
		String serviceId = null;
		if (_serviceId != null && (!_serviceId.equals("null"))) {
			serviceId = _serviceId;
		}
		
		String communeId = null;
		if (_communeId != null && (!_communeId.equals("null"))) {
			communeId = _communeId;
		}

		//Populate selcted VelocityTemplate to be shown
		VelocityTemplate velocityTemplate = VelocityTemplatesUtils
				.buildVelocityTemplate(dataSourcePeopleDB, communeId, serviceId, Chiave,
						"Tutti i servizi del nodo", null, feNode.getName());
		
		//Do work here
		
		model.put("includeTopbarLinks", true);
			
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
			
		model.addAttribute("feNode", feNode);
			
		model.addAttribute("tab", "velocityTemplates");
		
		model.addAttribute("velocityTemplate", velocityTemplate);
			
		this.setPageInfo(model, "addOrEditVelocityTemplate.title", null, "velocityTemplates");
			
		return getStaticProperty("fenodes.addAndEditVelocityTemplate.view");
	}
    
	
	
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.POST)
	public String processEditVelocityTemplateSubmit(@ModelAttribute("feNode") FENode feNode,
			@ModelAttribute("velocityTemplate") VelocityTemplate velocityTemplate, BindingResult bindingResult,
    		HttpServletRequest request,  ModelMap model) {
		
		boolean isCancel = isParamInRequest(request, "cancel");
		boolean isUpdate = isParamInRequest(request, "updateVelocityTemplate");
		
		if (isCancel) {
			logger.debug("Template editing canceled");
		}
		
		if (isUpdate) {
			List <VelocityTemplate> templates = new ArrayList <VelocityTemplate> ();
			templates.add(velocityTemplate);
			VelocityTemplateDataVO templateDataVO = VelocityTemplatesUtils.buildVelocityTemplateVOToUpdate(templates, 
					null, feNode.getMunicipalityCode());
			
			FEInterface feInterface;
			try {
				feInterface = this.getFEInterface(feNode.getFeServiceURL());
				feInterface.updateVelocityTemplatesData(templateDataVO, false);
				
			} catch (FeServiceReferenceException e) {
				logger.error("Exception processing Edit for a Velocity Template: " +
						"unable to get Front End Interface Reference.");
			} catch (RemoteException e) {
				logger.error("Exception processing Edit for a Velocity Template: " +
						"error executing updateVelocityTemplatesData.");
			}
		}
		
		model.put("includeTopbarLinks", true);
		
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
			
		model.addAttribute("tab", "velocityTemplates");
    	
    	model.addAttribute("tabbedPaneHeaders", getTabbedPaneHeaders());
    	
		model.addAttribute("feNode", feNode);
		    	
		this.setPageInfo(model, "fenodes.nodeDetails.title", null, "feNodes");
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	return  "redirect:modifica.do";
    	
	}
	
	
}

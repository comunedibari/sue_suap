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

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.domain.FEService;
import it.people.console.domain.PairElement;
import it.people.console.domain.ProcessActivationType;
import it.people.console.domain.ProcessActivationTypeEnumeration;
import it.people.console.domain.VelocityTemplate;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.IPersistenceManager;
import it.people.console.persistence.PersistenceManagerFactory;
import it.people.console.persistence.beans.support.EditableRowCheckbox;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.EditableRowSelect;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceManagerException;

import it.people.console.persistence.jdbc.support.RowStatusModeler;
import it.people.console.persistence.jdbc.support.RowsStatusModeler;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.utils.velocityTemplates.VelocityTemplatesUtils;
import it.people.console.web.controllers.validator.FeServiceDetailsValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;

import it.people.feservice.FEInterface;
import it.people.feservice.beans.ConfigParameter;
import it.people.feservice.beans.ConfigParameterVO;
import it.people.feservice.beans.DependentModule;
import it.people.feservice.beans.DependentModuleVO;
import it.people.feservice.beans.ServiceAuditProcessorVO;
import it.people.feservice.beans.ServiceVO;
import it.people.feservice.beans.TableValuePropertyVO;
import it.people.feservice.beans.VelocityTemplateDataVO;
import it.people.feservice.client.FEInterfaceServiceLocator;


/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feService", "logLevels", "statusTypes", 
	"signProcessTypes", "includeAttachmentsInReceiptTypes", "bundleRef", "velocityTemplate"})
public class FEServiceDetailsController extends AbstractListableController {

	private FeServiceDetailsValidator validator;

	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private String beReferencesQuery = "SELECT DISTINCT IF(benode.id IS NULL, 0, 1) AS 'rowstatus', reference.id, serviceid, NAME 'Modulo', VALUE 'BE', address, dump 'dump XML', mailincludexml " +
			"FROM reference LEFT JOIN benode ON benode.nodobe = reference.value AND nodeid = ? WHERE serviceid = ? ORDER BY NAME";
	
	private String serviceParametersQuery = "SELECT id, serviceid, NAME 'Nome Parametro', VALUE 'Valore' FROM configuration WHERE serviceid = ? ORDER BY name";
	
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private List<PairElement<String, String>> logLevels = null;

	private List<PairElement<String, String>> statusTypes = null;

	private List<PairElement<String, String>> signProcessTypes = null;
	
	private List<PairElement<String, String>> includeAttachmentsInReceiptTypes = null;

	private List<PairElement<String, String>> sendMailToOwnerTypes = null;
	
	//List for "embedAttachmentInXml" combobox
	private List<PairElement<String, String>> embedAttachmentInXmlTypes = null;
	
	private List <Option> tableValuesTableIdCombo = null;
	

	@Autowired
	public FEServiceDetailsController(FeServiceDetailsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feService");
	}
	
    @RequestMapping(value = "/dettaglio.do", method = RequestMethod.GET)
    public String setupForm(@RequestParam String id, @RequestParam(value = "tab", required = false) String tabName,
    		 ModelMap model, HttpServletRequest request) {

    	int pageSizeDefault = 10;
    	
    	boolean isListHolderRequest = isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX);
    	
    	//Init FeService
    	FEService feService = null;

		try {
			//Avvalora la bean
			feService = readFeServiceData(id);
		} catch (NumberFormatException e1) {
			logger.error("Unable to initialize FEService bean");
		} catch (PersistenceManagerException e1) {
			logger.error("Unable to initialize FEService bean");
		}

    	//Init all controls
    	if (tabName == null) {
    		tabName = "referenze";
    	}
    	
    	if (this.getLogLevels() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing log levels list...");
    		}
    		this.setLogLevels(this.getLogLevelsList());
    	}

    	if (this.getStatusTypes() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing status types list...");
    		}
    		this.setStatusTypes(this.getStatusTypesList());
    	}

    	if (this.getSignProcessTypes() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing sign process types list...");
    		}
    		this.setSignProcessTypes(this.getSignProcessTypesList());
    	}

    	if (this.getIncludeAttachmentsInReceiptTypes() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing include attachments in receipt types list...");
    		}
    		this.setIncludeAttachmentsInReceiptTypes(this.getIncludeAttachmentsInReceiptTypesList());
    	}

    	if (this.getSendMailToOwnerTypes() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing send mail to owner types list...");
    		}
    		this.setSendMailToOwnerTypes(this.getSendMailToOwnerTypesList());
    	}
    	
    	if (this.getEmbedAttachmentInXmlTypes() == null) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Initializing embed attachement in XML types list...");
    		}
    		this.setEmbedAttachmentInXmlTypes(this.getEmbedAttachmentInXmlTypesList());
    	}
    	

    	String nodeid = String.valueOf(feService.getNodeId());
    	
    	//ListHolder population
    	if (tabName.equals("tablevalues")) {
        	this.setTableValuesTableIdCombo(getServiceTableValuesTableId(feService.get_package(), feService.getMunicipality()));
        	//Init selected from combobox
    		if (!getTableValuesTableIdCombo().isEmpty()) {
    			feService.setSelectedTableId(getTableValuesTableIdCombo().get(0).getValue());
    		}
    		try {
    			ILazyPagedListHolder tablevaluesList = prepareTablevaluesPropertiesList(feService.getSelectedTableId(), pageSizeDefault);
    			if (!feService.getPagedListHolders().containsKey(tablevaluesList.getPagedListId())) {
    				feService.addPagedListHolder(tablevaluesList);
    			}
    			else {
    				feService.updatePagedListHolder(tablevaluesList);
    			}
    		} catch (PagedListHoldersCacheException e) {
    			logger.error("ParsedListException populating tablevalues List");
    		} catch (LazyPagedListHolderException e) {
    			logger.error("LazyPagedListHolderException populating tablevalues List");
			}
    	}
    	
    	if (tabName.equals("referenze")) {
    		try {
				ILazyPagedListHolder beReferenceList = prepareBeReferenceList(nodeid, id);
				if (!feService.getPagedListHolders().containsKey(beReferenceList.getPagedListId())) {
					feService.addPagedListHolder(beReferenceList);
				}
				else {
					feService.updatePagedListHolder(beReferenceList);
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating BeReference List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating BeReference List");
			}
    	}
    	
    	if (tabName.equals("messaggi")) {
				try {
					ILazyPagedListHolder serviceLabelsList = prepareServiceLabelsList(feService.getMunicipality(),
							feService.get_package(), feService.getSelectedServicesLanguage(), model);
	    			if (!feService.getPagedListHolders().containsKey(serviceLabelsList.getPagedListId())) {
	    				feService.addPagedListHolder(serviceLabelsList); 
	    			}
	    			else {
						feService.updatePagedListHolder(serviceLabelsList);
					}
				} catch (LazyPagedListHolderException e) {
					logger.error("ParsedListException populating serviceLabels Messages List");
				} catch (PagedListHoldersCacheException e) {
					logger.error("LazyPagedListHolderException populating serviceLabels Messages List");
				}		
    	}
    	
    	if (tabName.equals("parametri")) {
    		try {
				ILazyPagedListHolder parametersList = prepareParametersList(id);
				if (!feService.getPagedListHolders().containsKey(parametersList.getPagedListId())) {
    				feService.addPagedListHolder(parametersList); 
    			}
    			else {
					feService.updatePagedListHolder(parametersList);
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating service parameters List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException service parameters  Messages List");
			}
    	}
    	
    	if (tabName.equals("audit")) {
    		try {
				ILazyPagedListHolder auditProcessorsList = prepareAuditProcessorsList(id, pageSizeDefault);
				if (!feService.getPagedListHolders().containsKey(auditProcessorsList.getPagedListId())) {
					feService.addPagedListHolder(auditProcessorsList);
				}
				else {
					feService.updatePagedListHolder(auditProcessorsList);
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating auditProcessors List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating auditProcessors List");
			}
    	}
    	
		if (tabName.equals("velocityTemplates")) {
			try {
				ILazyPagedListHolder velocityTemplatesList = prepareVelocityTemplatesList(feService, pageSizeDefault);
				if (!feService.getPagedListHolders().containsKey(velocityTemplatesList.getPagedListId())) {
					feService.addPagedListHolder(velocityTemplatesList);
				} else {
					feService.updatePagedListHolder(velocityTemplatesList);
				}
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating velocity templates List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating velocity templates List");
			}
		}

    	processListHoldersRequests(request.getQueryString(), feService);

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("logLevels", this.getLogLevels());

    	model.addAttribute("statusTypes", this.getStatusTypes());

    	model.addAttribute("signProcessTypes", this.getSignProcessTypes());

    	model.addAttribute("includeAttachmentsInReceiptTypes", this.getIncludeAttachmentsInReceiptTypes());

    	model.addAttribute("sendMailToOwnerTypes", this.getSendMailToOwnerTypes());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

    	model.addAttribute("embedAttachmentInXmlTypes", this.getEmbedAttachmentInXmlTypesList());
    	
    	model.addAttribute("feService", feService);

    	model.addAttribute("serviceLocales", getServiceLocalesByNodeId(feService.get_package(), feService.getMunicipality()));

    	model.addAttribute("serviceRegisterableLocales", getServiceRegisterableLocalesByNodeId(feService.get_package(), feService.getMunicipality()));
    	
    	//Populate combobox for Tablevalues
    	model.addAttribute("tableValuesTableIdCombo", this.getTableValuesTableIdCombo());
    	
    	model.addAttribute("tab", tabName);
    	
    	model.addAttribute("tabbedPaneHeaders", getTabbedPaneHeaders());
    	
    	this.setPageInfo(model, "feservice.details.title", null, "feServiceD");
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	return getStaticProperty("feservices.serviceDetails.view");
    }
    

	/**
	 * @return the tableValuesTableIdCombo
	 */
	public List<Option> getTableValuesTableIdCombo() {
		return tableValuesTableIdCombo;
	}

	/**
	 * @param tableValuesTableIdCombo the tableValuesTableIdCombo to set
	 */
	public void setTableValuesTableIdCombo(List<Option> tableValuesTableIdCombo) {
		this.tableValuesTableIdCombo = tableValuesTableIdCombo;
	}

	@RequestMapping(value = "/dettaglio.do", method = RequestMethod.POST)
    public String processSubmit(@RequestParam(value = "tab", required = false) String tabName,
    		@RequestParam(value = "usedCombo", required = false) String usedCombo, ModelMap model, 
    		@ModelAttribute("feService") FEService feService, BindingResult bindingResult, 
    		HttpServletRequest request, SessionStatus sessionStatus) {
    	
    	boolean isCancelInRequest = isParamInRequest(request, "cancel");
    	boolean isUpdateInRequest = isParamInRequest(request, "updateFeService");
    	boolean isListHolderRequest = isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX);
    	
    	if (isUpdateInRequest) {
        	validator.validate(feService, bindingResult);
    	}
    	
    	if (isCancelInRequest) {
    		sessionStatus.setComplete();
    		return  "redirect:/ServiziFe/elenco.do";
    	}
    	

		//UPDATE MESSAGE PagedListHolder
		if ((tabName != null) && tabName.equals("serviceLabelsList")) {
			
			try {
				if (!it.people.feservice.utils.StringUtils.nullToEmptyString(
    	    			feService.getSelectedServicesLanguage()).equalsIgnoreCase(
    	    					it.people.feservice.utils.StringUtils.nullToEmptyString(
    	    							feService.getPreviousSelectedServicesLanguage()))) {
    	    		feService.updatePagedListHolder(this.prepareServiceLabelsList(feService.getMunicipality(), feService.get_package(), 
    	    				feService.getSelectedServicesLanguage(), model));
    	    		feService.setPreviousSelectedServicesLanguage(feService.getSelectedServicesLanguage());
    	    	}
			} catch (PagedListHoldersCacheException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LazyPagedListHolderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

    	//UPDATE TABLEVALUES PagedListHolder
    	if ((tabName != null) && tabName.equals("tablevalues")) {
    		try {
	    		if ((usedCombo != null) && (usedCombo.equalsIgnoreCase("tableValuesTableId"))) {
	    			int pageSizeTablevalues = feService.getPagedListHolder(Constants.PagedListHoldersIds.TABLEVAUES_LIST).getPageSize();
	    	    	feService.updatePagedListHolder(this.prepareTablevaluesPropertiesList(feService.getSelectedTableId(), pageSizeTablevalues));
	    		}
			} catch (PagedListHoldersCacheException e1) {
				logger.error("PagedList HoldersCacheException reloading Tablevalue list");
			} catch (LazyPagedListHolderException e1) {
				logger.error("Lazy PagedList HoldersCacheException reloading Tablevalue list");
			}
	  
    	}
    	
    	if (isListHolderRequest) {
    		processListHoldersRequests(request, feService, model);
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:/ServiziFe/confermaDetails.do";
    		}
    	}
    	
    	//Update Velocity templates
		if ((tabName != null) && tabName.equals("velocityTemplates")) {
			try {
				
				int pageSizeVelocityTemplates = feService.getPagedListHolder(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST).getPageSize();
				ILazyPagedListHolder velocityTemplatesList = prepareVelocityTemplatesList(feService, pageSizeVelocityTemplates);
				feService.updatePagedListHolder(velocityTemplatesList);
				
			} catch (LazyPagedListHolderException e) {
				logger.error("ParsedListException populating velocity templates List");
			} catch (PagedListHoldersCacheException e) {
				logger.error("LazyPagedListHolderException populating velocity templates List");
			}
		}
    	

    	// TODO Filter activation
//    	if (logger.isDebugEnabled()) {
//    		logger.debug("Saving filters state...");
//    	}
//    	List<FilterProperties> updatedAppliedFilters = updateAppliedFilters(request, filtersList);
//    	if (logger.isDebugEnabled()) {
//    		logger.debug("Saving filters state done.");
//    	}
//    	if (isParamInRequest(request, "applyFilters")) {
//        	if (logger.isDebugEnabled()) {
//        		logger.debug("Applying " + updatedAppliedFilters.size() + " active filters...");
//        	}
//        	feServiceRegistration.getPagedListHolder("feServicesList").applyFilters(updatedAppliedFilters);
//        	if (logger.isDebugEnabled()) {
//        		logger.debug("Active filters applied.");
//        	}
//    	}
//    	if (isParamInRequest(request, "clearFilters")) {
//        	if (logger.isDebugEnabled()) {
//        		logger.debug("Clearing " + updatedAppliedFilters.size() + " active filters...");
//        	}
//        	feServiceRegistration.getPagedListHolder("feServicesList").removeFilters();
//        	if (logger.isDebugEnabled()) {
//        		logger.debug("Active filters cleared.");
//        	}
//    	}
    	

    	//Update Request
    	if (isUpdateInRequest && !bindingResult.hasErrors()) {
    		updateService(feService, bindingResult);
    	}

    	//Register Service Language
    	if (isParamInRequest(request, "registerServiceLanguage")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Saving service language...");
        		logger.debug(feService.getSelectedServicesRegisterableLanguage());
        	}
    		try {
				FEInterface feInterface = this.getFEInterface(feService.getReference());
				feInterface.registerBundle(feService.get_package(), feService.getMunicipality(), 
						feService.getSelectedServicesRegisterableLanguage(), "1", null);
				this.persistenceBroker.registerBundle(feService.get_package(), feService.getMunicipality(), 
						feService.getSelectedServicesRegisterableLanguage(), "1", null);
			} catch (FeServiceReferenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    	if ((isCancelInRequest || isUpdateInRequest) && !bindingResult.hasErrors()) {
    		sessionStatus.setComplete();
    		return  "redirect:/ServiziFe/elenco.do";
    	}
    	
    	else {

        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
        	
        	model.addAttribute("logLevels", this.getLogLevels());

        	model.addAttribute("statusTypes", this.getStatusTypes());

        	model.addAttribute("signProcessTypes", this.getSignProcessTypes());

        	model.addAttribute("includeAttachmentsInReceiptTypes", this.getIncludeAttachmentsInReceiptTypes());

        	model.addAttribute("sendMailToOwnerTypes", this.getSendMailToOwnerTypes());
        	
        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

        	model.addAttribute("embedAttachmentInXmlTypes", this.getEmbedAttachmentInXmlTypesList());
        	
        	model.addAttribute("feService", feService);

        	model.addAttribute("serviceLocales", getServiceLocalesByNodeId(feService.get_package(), feService.getMunicipality()));

        	model.addAttribute("serviceRegisterableLocales", getServiceRegisterableLocalesByNodeId(feService.get_package(), feService.getMunicipality()));
        	
        	//Populate combobox for Tablevalues
        	model.addAttribute("tableValuesTableIdCombo", getServiceTableValuesTableId(feService.get_package(), feService.getMunicipality()));
        	
        	model.addAttribute("tab", (tabName == null) ? "referenze" : tabName);

        	model.addAttribute("tabbedPaneHeaders", getTabbedPaneHeaders());
        	
        	this.setPageInfo(model, "feservice.details.title", null, "feServiceD");
        	
        	setRowsPerPageDefaultModelAttributes(model);
    		
    		return getStaticProperty("feservices.serviceDetails.view");
    	}
    	
    }


	@RequestMapping(value = "/confermaDetails.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/confermaDetails.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model,  
    		@ModelAttribute("feService") FEService feService, BindingResult result, 
    		HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	model.addAttribute("logLevels", this.getLogLevels());
    	model.addAttribute("statusTypes", this.getStatusTypes());
    	model.addAttribute("signProcessTypes", this.getSignProcessTypes());
    	model.addAttribute("includeAttachmentsInReceiptTypes", this.getIncludeAttachmentsInReceiptTypes());
    	model.addAttribute("sendMailToOwnerTypes", this.getSendMailToOwnerTypes());
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("feService", feService);
    	model.addAttribute("id",feService.getId());

    	
    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		ProcessActionDataHolder processActionDataHolder = this.popProcessActionData(request);
    		
    		String pagedListHolderId = processActionDataHolder.getPagedListHolderId();
    		EditableRowInputData editableRowInputData = processActionDataHolder.getEditableRowInputData(); 

    		//cancellazione confermata
    		deleteFeServiceDetail(pagedListHolderId, editableRowInputData, feService);
 
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:dettaglio.do";
    	
    }
    
    
	/**
	 * @param pagedListHolderId
	 * @param action
	 * @param editableRowInputData
	 * @param request
	 */
	private void deleteFeServiceDetail(String pagedListHolderId,
			EditableRowInputData editableRowInputData,
			FEService feService) {
		
		
		long nodeid = feService.getNodeId();
		String servicePackage = feService.get_package();

		Map<String, Object> rowIdentifier = editableRowInputData.getRowIdentifiers();
		Map<String, Object> inputData = editableRowInputData.getInputData();
		
		String old_name = (String) rowIdentifier.get("name");
		String old_value = (String) rowIdentifier.get("value");

		String name = (String) inputData.get("name");
		String value = (String) inputData.get("value");

	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = dataSourcePeopleDB.getConnection();
			
			String[] reference_communeid = getReferenceCommuneid(nodeid, connection);

		    //parametro
			if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.PARAMETERS_LIST)) {
				
				ConfigParameterVO parameterVO = getConfigParameterVO(AbstractCommand.CommandActions.delete,
						servicePackage, old_name, old_value, name, value,
						reference_communeid);
				
				//salvataggio fedb
				boolean unavailableService = executeFeConfigureServiceParameter(
						reference_communeid, parameterVO);
		        
		        if (!unavailableService) {
					//salvataggio db locale
					if (logger.isDebugEnabled()) {
						logger.debug("No confirmation required, proceeding with delete action.");
					}
					
					/** elimina - delete */
					long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));					
					deleteConfiguration(id, connection);

		        }
				

			} else //reference 
				if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.BE_REFERENCES_LIST)) {
					
				DependentModuleVO referenceVO = getDependentModuleVO(AbstractCommand.CommandActions.delete,
						servicePackage, old_name, old_value, name, value,
						reference_communeid);
				
				//salvataggio fedb
				boolean unavailableService = executeFeConfigureServiceReference(
						reference_communeid, referenceVO);
		        
		        if (!unavailableService) {
					//salvataggio db locale
					if (logger.isDebugEnabled()) {
						logger.debug("No confirmation required, proceeding with delete action.");
					}
					
					/** elimina - delete */
					long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));
					deleteReference(id, connection);

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
						e.printStackTrace();
					}
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (SQLException e) {

			}
		}
	}

	/**
	 * @param action
	 * @param servicePackage
	 * @param old_name
	 * @param old_value
	 * @param name
	 * @param value
	 * @param reference_communeid
	 * @return
	 */
	private DependentModuleVO getDependentModuleVO(
			AbstractCommand.CommandActions action, String servicePackage,
			String old_name, String old_value, String name, String value,
			String[] reference_communeid) {
		//salvataggio db tramite ws
		DependentModuleVO referenceVO = new DependentModuleVO(); 
		
		//valori precedenti
		DependentModule oldReference = new DependentModule();
		oldReference.setName(old_name);
		oldReference.setValore(old_value);
		
		//valori attuali
		DependentModule newReference = new DependentModule();
		newReference.setName(name);
		newReference.setValore(value);
		
		referenceVO.setCommuneId(reference_communeid[1]);
		referenceVO.setServicePackage(servicePackage); 
		referenceVO.setNewReference(newReference);
		referenceVO.setOldReference(oldReference);
		referenceVO.setAction(action.getAction());
		return referenceVO;
	}

	/**
	 * @param action
	 * @param servicePackage
	 * @param old_name
	 * @param old_value
	 * @param name
	 * @param value
	 * @param reference_communeid
	 * @return
	 */
	private ConfigParameterVO getConfigParameterVO(
			AbstractCommand.CommandActions action, String servicePackage,
			String old_name, String old_value, String name, String value,
			String[] reference_communeid) {
		//salvataggio db tramite ws
		ConfigParameterVO parameterVO = new ConfigParameterVO();
		
		//valori precedenti
		ConfigParameter oldParameter = new ConfigParameter();
		oldParameter.setParameterName(old_name);
		oldParameter.setParameterValue(old_value);
		
		//valori attuali
		ConfigParameter newParameter = new ConfigParameter();
		newParameter.setParameterName(name);
		newParameter.setParameterValue(value);
		
		parameterVO.setCommuneId(reference_communeid[1]);
		parameterVO.setServicePackage(servicePackage); 
		parameterVO.setConfigParameter(newParameter);
		parameterVO.setOldConfigParameter(oldParameter);
		parameterVO.setAction(action.getAction());
		return parameterVO;
	}
    

    private void updateService(FEService feService, BindingResult bindingResult) {
    	
    	String getCommuneServiceLocal = "select codicecomune from fenode where id = ?";
    	String countServiceNameLocal = "select count(id) from service where nome = ?";
    	//Update Query for local db
    	String updateServiceLocal = "update service set nome = ?, loglevel = ?, stato = ?, signenabled = ?, receiptmailattachment = ?, sendmailtoowner = ?, " +
    			"embedAttachmentInXml = ?, showprivacydisclaimer = ?, privacydisclaimerrequireacceptance = ?, firmaOnLine = ?, firmaOffLine = ? where id = ?";
    	String updateReferenceLocal = "update reference set address = ? where serviceid = ? and name = ?";
    	String updateReferenceLocalNone = "update reference set address = ?, value = '' where serviceid = ? and name = ?";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			
			boolean communeFound = false;
			preparedStatement = connection.prepareStatement(getCommuneServiceLocal);
			preparedStatement.setLong(1, feService.getNodeId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				feService.setMunicipality(resultSet.getString(1));
				communeFound = true;
			} else {
				logger.error("Update service error: unable to retrieve commune from service id.");
				ObjectError error = new ObjectError("serviceUpdateError", 
						"Si è verificato un errore nell'aggiornamento del servizio.");
				bindingResult.addError(error);
			}
			
			if (communeFound) {
				boolean serviceNameExists = false;
				if (!feService.getServiceName().equalsIgnoreCase(feService.getBeforeUpdateServiceName())) {
					preparedStatement = connection.prepareStatement(countServiceNameLocal);
					preparedStatement.setString(1, feService.getServiceName());
					resultSet = preparedStatement.executeQuery();
					resultSet.next();
					serviceNameExists = resultSet.getInt(1) > 0;
				}
				
				if (!serviceNameExists) {
					try {
						FEInterface feInterface = this.getFEInterface(this.getReferenceCommuneid(feService.getNodeId(), connection)[0]);
						
						//Update Service Value Object
						ServiceVO theService = new ServiceVO();
						
						theService.setNome(feService.getServiceName());
						theService.setLogLevel(feService.getLogLevel());
						theService.setStato(feService.getServiceStatus());
						theService.setSignEnabled(feService.getProcessSignEnabled());
						theService.setReceiptMailIncludeAttachment(feService.getAttachmentsInCitizenReceipt());
						theService.setSendmailtoowner(feService.getSendmailtoowner().intValue());
						theService.setEmbedAttachmentInXml(feService.getEmbedAttachmentInXml());
						theService.setCommuneId(feService.getMunicipality());
						theService.setServicePackage(feService.get_package());
						
						//Privacy
						theService.setPrivacyDisclaimerRequireAcceptance((feService.isPrivacyDisclaimerRequireAcceptance()) ? 1 : 0);
						theService.setShowPrivacyDisclaimer((feService.isShowPrivacyDisclaimer()) ? 1 : 0);
						
						//Firma online - offline
						theService.setOnlineSign((feService.isOnlineSign()) ? 1 : 0);
						theService.setOfflineSign((feService.isOfflineSign()) ? 1 : 0);
						
						
						boolean noneActivationType = false;
						DependentModule dependentModule = new DependentModule();
						dependentModule.setName(Constants.FEService.SUBMIT_PROCESS_ID);
						switch (feService.getProcessActivationType().getActivationType()) {
							case webService:
								dependentModule.setMailAddress(Constants.FEService.SUBMIT_PROCESS_ADDRESS_UNDEFINED);
								break;
							case eMail:
								dependentModule.setMailAddress(feService.getProcessActivationType().geteMailAddress());
								break;
							default: //notSupported process activation type
								noneActivationType = true;
								dependentModule.setMailAddress(Constants.FEService.SUBMIT_PROCESS_ADDRESS_UNDEFINED);
								break;
						}
						theService.setDependentModules(new DependentModule[] {dependentModule});
						
						//Update remote DB
						feInterface.updateService(theService);
						
						//Update local DB
						preparedStatement = connection.prepareStatement(updateServiceLocal);
						preparedStatement.setString(1, feService.getServiceName());
						preparedStatement.setInt(2, feService.getLogLevel());
						preparedStatement.setInt(3, feService.getServiceStatus());
						preparedStatement.setInt(4, feService.getProcessSignEnabled());
						preparedStatement.setInt(5, feService.getAttachmentsInCitizenReceipt());
						preparedStatement.setInt(6, feService.getSendmailtoowner());
						preparedStatement.setInt(7, feService.getEmbedAttachmentInXml());
						//Privacy
						preparedStatement.setInt(8, (feService.isShowPrivacyDisclaimer()) ? 1 : 0);
						preparedStatement.setInt(9, (feService.isPrivacyDisclaimerRequireAcceptance()) ? 1 : 0);
						
						//Firma
						preparedStatement.setInt(10, (feService.isOnlineSign()) ? 1 : 0);
						preparedStatement.setInt(11, (feService.isOfflineSign()) ? 1 : 0);
						
						//Where clause
						preparedStatement.setLong(12, feService.getId());
						preparedStatement.executeUpdate();
	
						preparedStatement = connection.prepareStatement(noneActivationType ? updateReferenceLocalNone : updateReferenceLocal);
//						preparedStatement.setString(1, feService.getProcessActivationType().geteMailAddress());
						preparedStatement.setString(1, dependentModule.getMailAddress());
						preparedStatement.setLong(2, feService.getId());
						preparedStatement.setString(3, Constants.FEService.SUBMIT_PROCESS_ID);
						preparedStatement.executeUpdate();
						
						
					} catch(Exception e) {
						logger.error("Update service error:", e);
						ObjectError error = new ObjectError("serviceUpdateError", 
								"Si è verificato un errore nell'aggiornamento del servizio.");
						bindingResult.addError(error);
					}
				
				} else {
	    			ObjectError error = new ObjectError("serviceUpdateError", 
	    					this.getProperty("error.feservice.update.nameExists", 
	    							new String[] {feService.getServiceName()}));
	    			bindingResult.addError(error);
				}
			}
			
		} catch(SQLException e) {
			logger.error("Update service error:", e);
			ObjectError error = new ObjectError("serviceUpdateError", 
					"Si è verificato un errore nell'aggiornamento del servizio.");
			bindingResult.addError(error);
		} finally {
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
			} catch(Exception ignore) {
				
			}
		}
    	
    }
    
    private FEService readFeServiceData(String serviceId) throws NumberFormatException, PersistenceManagerException {
    	
    	FEService result = new FEService();
    	
		IPersistenceManager feServicePersistenceManager = PersistenceManagerFactory.getInstance()
			.get(FEService.class, Mode.READ);
    	
		result = (FEService)feServicePersistenceManager.get(Long.parseLong(serviceId));
		
		feServicePersistenceManager.close();
		
		String[] nodeData = getServiceNodeData(result.getNodeId());
		result.setMunicipality(nodeData[0]);
		result.setNodeName(nodeData[1]);
		result.setReference(nodeData[2]);
		result.setProcessActivationType(getProcessActivationType(result.getId()));
		result.setBeforeUpdateServiceName(result.getServiceName());
   
    	return result;
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
    
    private ProcessActivationType getProcessActivationType(long nodeId) {
    	
    	ProcessActivationType result = new ProcessActivationType();
    	result.setActivationType(ProcessActivationTypeEnumeration.notSupported);
    	result.seteMailAddress("");
    	
		String queryServiceSubmitReference = "SELECT address FROM reference WHERE serviceid = ? AND NAME = 'SUBMIT_PROCESS'";

		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryServiceSubmitReference);
			preparedStatement.setLong(1, nodeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			String address = null;
			if (resultSet.next()) {
				address = resultSet.getString(1);
			}
			if (address != null) {
				if (!StringUtils.nullToEmpty(address).equalsIgnoreCase("UNDEFINED")) {
					result.setActivationType(ProcessActivationTypeEnumeration.eMail);
					result.seteMailAddress(address);
				}
				else {
					result.setActivationType(ProcessActivationTypeEnumeration.webService);
				}
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
    
    private String[] getServiceNodeData(long nodeId) {
    	
		String queryServiceNodeName = "SELECT codicecomune, comune, reference FROM fenode WHERE id = ?";

		String[] result = new String[] {"", "", ""};
		Connection connection = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryServiceNodeName);
			preparedStatement.setLong(1, nodeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				result[0] = resultSet.getString(1);
				result[1] = resultSet.getString(2);
				result[2] = resultSet.getString(3);
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
    
    @Override
    protected IFilters prepareFilters() {
    	    	
    	Vector<IFilter> filters = new Vector<IFilter>();

    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }
    
    
//	/* (non-Javadoc)
//	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processListHoldersRequests(javax.servlet.http.HttpServletRequest, it.people.console.domain.IPagedListHolderBean, org.springframework.ui.ModelMap)
//	 */
//	@Override
//	protected void processListHoldersRequests(HttpServletRequest request,
//			IPagedListHolderBean pagedListHolderBean, ModelMap modelMap) {
//		
//		//INTERCEPT "new" and "edit" to redirect user to edit page
//		String[] pagedListHolderRequestParams = WebUtils.getPagedListHolderRequestParams(request);
//		String[] parsedAction = getPagedListHolderAction(pagedListHolderRequestParams);
//
//		if (parsedAction.length > 0) {
//			String pagedListHolderId = parsedAction[3];
//		
//			if (parsedAction[1].equalsIgnoreCase(TagsConstants.LIST_HOLDER_TABLE_ACTION_PREFIX)) {
//				
//				String actionQuery = parsedAction[2];
//				ParsedQueryString parsedQueryString = WebUtils.parseQueryString(actionQuery);
//	
//				String action = parsedQueryString.getQueryStringValues().get(TagsConstants.LIST_HOLDER_TABLE_COMMAND_ACTION_QUERY_PARAM);
//
//				if (action.equalsIgnoreCase(AbstractCommand.CommandActions.edit.getAction())) {
//					List<EditableRow> editableRows = (List<EditableRow>)pagedListHolderBean.getPagedListHolder(pagedListHolderId).getPageList();
//					for(EditableRow row : editableRows) {
//						if (isSelectedRow(pagedListHolderBean.getPagedListHolder(pagedListHolderId).getRowColumnsIdentifiers(), row, parsedQueryString)) {
//							
//							List<String> rowIdentifiers = pagedListHolderBean.getPagedListHolder(pagedListHolderId).getRowColumnsIdentifiers();
//							//Redirect
//							
//						}
//					}
//				//If not "edit" or "new" then process requests in regular way
//				} else {
//					super.processListHoldersRequests(request, pagedListHolderBean, modelMap);
//				}
//			}
//		}
//	}

	
	
	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction()
	 */
	@Override
	protected void processAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap modelMap) {
		
		// delete (no js)
		if (action.equals(AbstractCommand.CommandActions.delete)
				&& ((Boolean) request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Delete confirmation required, no action.");
			}
		} 
		// insert - update - delete (js attivo)
		else {
			FEService feService = (FEService) request.getSession().getAttribute("feService");
			long serviceid = feService.getId();
			long nodeid = feService.getNodeId();
			String servicePackage = feService.get_package();

			Map<String, Object> rowIdentifier = editableRowInputData.getRowIdentifiers();
			Map<String, Object> inputData = editableRowInputData.getInputData();
			
			String old_name = (String) rowIdentifier.get("name");
			String old_value = (String) rowIdentifier.get("value");

			String name = (String) inputData.get("name");
			String value = (String) inputData.get("value");

			if (logger.isDebugEnabled()) {
				logger.debug("Processing action...");
				logger.debug("Paged list holder id: " + pagedListHolderId);
				logger.debug("Action: " + action);
				logger.debug("Editable row input data: " + editableRowInputData);
			}
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = dataSourcePeopleDB.getConnection();
				
				String[] reference_communeid = getReferenceCommuneid(nodeid, connection);

			    //parametro
				if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.PARAMETERS_LIST)) {
					
					ConfigParameterVO parameterVO = getConfigParameterVO(action,
							servicePackage, old_name, old_value, name, value,
							reference_communeid);
					
					//salvataggio fedb
					boolean unavailableService = executeFeConfigureServiceParameter(
							reference_communeid, parameterVO);
			        
			        if (!unavailableService) {
						//salvataggio db locale
			        	/* nuovo - insert */
						if(action.equals(AbstractCommand.CommandActions.saveNew)){
							
							insertConfiguration(serviceid, name, value, connection);
						}
						/* modifica - update */				
						else if(action.equals(AbstractCommand.CommandActions.save)){
							long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));
							
							updateConfiguration(id, name, value, connection);			
						}
						/* elimina - delete */
						else if(action.equals(AbstractCommand.CommandActions.delete)){
							if (logger.isDebugEnabled()) {
								logger.debug("No confirmation required, proceeding with delete action.");
							}
							long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));					
							
							deleteConfiguration(id, connection);
						}	

			        }
					

				} else //reference 
				if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.BE_REFERENCES_LIST)) {

					int dump = 0; 
					if (inputData.containsKey("dump")) {
						String dumpvalue = (String) inputData.get("dump");
						dump = dumpvalue.equalsIgnoreCase("on") ? 1 : 0;
					}
					
					DependentModuleVO referenceVO = getDependentModuleVO(action,
							servicePackage, old_name, old_value, name, value,
							reference_communeid);
					
					//salvataggio fedb
					boolean unavailableService = executeFeConfigureServiceReference(
							reference_communeid, referenceVO);
			        
			        if (!unavailableService) {
						//salvataggio db locale
			        	/* nuovo - insert */
						if(action.equals(AbstractCommand.CommandActions.saveNew)){
							
							insertReference(serviceid, name, value, dump, connection);
						}
						/* modifica - update */				
						else if(action.equals(AbstractCommand.CommandActions.save)){
							long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));
							
							updateReference(id, name, value, dump, connection);
						}
						/* elimina - delete */
						else if(action.equals(AbstractCommand.CommandActions.delete)){
							if (logger.isDebugEnabled()) {
								logger.debug("No confirmation required, proceeding with delete action.");
							}
							long id = new Long(Integer.parseInt((String) rowIdentifier.get("id")));
							
							deleteReference(id, connection);
							
						}
					}
				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.AUDIT_PROCESSORS_LIST)) {
					
					int active = 0; 
					if (inputData.containsKey("Attivo")) {
						String activealue = (String) inputData.get("Attivo");
						active = activealue.equalsIgnoreCase("on") ? 1 : 0;
					}
					
					String auditProcessor = (String) rowIdentifier.get("auditProcessor");
					
					//Create a AuditProcessor Value Object
					ServiceAuditProcessorVO serviceAuditProcessorVO = new ServiceAuditProcessorVO(auditProcessor,servicePackage, active, reference_communeid[1]);
					
					//Save in FeDB
					boolean savedOnFeDB = executeFeConfigureServiceAuditProcessor(reference_communeid, serviceAuditProcessorVO);
					
					if (savedOnFeDB) {
						
						if(action.equals(AbstractCommand.CommandActions.save)) {
							this.persistenceBroker.registerServiceAuditProcessor(serviceid, auditProcessor, active);
						}
					}
											
				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.SERVICE_LABELS_LIST)) {
					
					value = (String) inputData.get("Valore");
					String bundleRef = String.valueOf(request.getSession().getAttribute("bundleRef"));
					FEInterface feInterface = this.getFEInterface(feService.getReference());
					if (StringUtils.isEmptyString(bundleRef) || bundleRef.equalsIgnoreCase("-1")) {
						feInterface.registerBundle(feService.get_package(), 
								feService.getMunicipality(), feService.getSelectedServicesLanguage(), "1", null);
						this.persistenceBroker.registerBundle(feService.get_package(), 
								feService.getMunicipality(), feService.getSelectedServicesLanguage(), "1", null);
						bundleRef = String.valueOf(this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(feService.get_package(), 
								feService.getMunicipality(), feService.getSelectedServicesLanguage()));
					}
					long messageId = new Long(Integer.parseInt((String) rowIdentifier.get("id")));
					String messageKey = this.persistenceBroker.getServiceMessageKeyById(messageId);
					feInterface.updateBundle(feService.get_package(), feService.getMunicipality(), 
							feService.getSelectedServicesLanguage(), messageKey, value, "1", null);
					this.persistenceBroker.updateBundle(Long.parseLong(bundleRef), 
							messageKey, value, "1", null);
					
	
				//Manage Tablevalues pagedListholder
				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.TABLEVAUES_LIST)) {
					
					String newValue = (String) inputData.get("value");
					String oldValue = (String) rowIdentifier.get("value");
					int tableValueRef = Integer.parseInt((String) feService.getSelectedTableId());
					int id=0;
					
					if(!action.equals(AbstractCommand.CommandActions.saveNew)){
						id = Integer.parseInt((String) rowIdentifier.get("id"));	
					}
					
					TableValuePropertyVO tableValueProp = new TableValuePropertyVO(id, oldValue, newValue, tableValueRef, action.getAction());
					
					//Save in FeDB
					boolean savedOnFeDB = executeFeConfigureTableValueProperty(reference_communeid, tableValueProp);
					
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
									
				//Manage Velocity Templates pagedListholder	
				} else if (pagedListHolderId.equalsIgnoreCase(Constants.PagedListHoldersIds.VELOCITY_TEMPLATES_LIST)) {
				
					//Update and delete
					String communeId = null;
					String serviceId = null;
					String shortkey = null;
					
					communeId =  rowIdentifier.get("_communeId").equals("null") ? null : (String) rowIdentifier.get("_communeId");
					serviceId = rowIdentifier.get("_serviceId").equals("null") ? null : (String) rowIdentifier.get("_serviceId");
					shortkey = (String) rowIdentifier.get("Chiave");

					if(action.equals(AbstractCommand.CommandActions.saveNew)) {
						//TODO
					}	
					
					//Custom delete - delete from FEDB
					if(action.equals(AbstractCommand.CommandActions.delete)) {
						
						//Delete only already customized templates
						if (communeId != null && serviceId != null) {
							
							//Build the VO to perform delete
							VelocityTemplateDataVO templatesVO = VelocityTemplatesUtils.buildVelocityTemplateVOToDelete(dataSourcePeopleDB, communeId, serviceId, shortkey);
							try {
								FEInterface feInterface = this.getFEInterface(feService.getReference());
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
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Impossibile modificare i parametri del servizio, per un problema di accesso alla base di dati.");
			} finally {
				try {
					
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (preparedStatement != null) {
						preparedStatement.close();
					}
				} catch (SQLException e) {

				}
			}
		}
	}


	private boolean executeFeConfigureServiceAuditProcessor(String[] reference_communeid, ServiceAuditProcessorVO serviceAuditProcessorVO) {
		boolean availableService = true;	
		FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
		try {
			FEInterface sSoap = locator.getFEInterface(new URL(reference_communeid[0]));
			
			sSoap.configureServiceAuditProcessor(serviceAuditProcessorVO);
		
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
			availableService = false;
		} catch (Exception e){
			logger.error(e.getMessage());
			availableService = false;
		}
		return availableService;
	}
	
	

	/**
	 * Execute actions (Save, Update, Delete) on FEDB for a tablevalue property 
	 * 
	 * @param reference_communeid
	 * @param tableValuePropertyVO
	 * @return
	 */
	private boolean executeFeConfigureTableValueProperty(String[] reference_communeid, TableValuePropertyVO tableValuePropertyVO) {
		boolean availableService = true;	
		FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
		try {
			FEInterface sSoap = locator.getFEInterface(new URL(reference_communeid[0]));
			sSoap.configureTableValueProperty(tableValuePropertyVO);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
			availableService = false;
		} catch (Exception e){
			logger.error(e.getMessage());
			availableService = false;
		}
		return availableService;
	}
	
	
	/**
	 * @param reference_communeid
	 * @param referenceVO
	 * @return
	 */
	private boolean executeFeConfigureServiceReference(
			String[] reference_communeid, DependentModuleVO referenceVO) {
		boolean unavailableService = false;	
		FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
		try {
			FEInterface sSoap = locator.getFEInterface(new URL(reference_communeid[0]));
			sSoap.configureServiceReference(referenceVO);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
			unavailableService = true;
		} catch (Exception e){
			logger.error(e.getMessage());
			unavailableService = true;
		}
		return unavailableService;
	}

	/**
	 * @param reference_communeid
	 * @param parameterVO
	 * @return
	 */
	private boolean executeFeConfigureServiceParameter(
			String[] reference_communeid, ConfigParameterVO parameterVO) {
		boolean unavailableService = false;
		FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
		try {
			FEInterface sSoap = locator.getFEInterface(new URL(reference_communeid[0]));
			sSoap.configureServiceParameter(parameterVO);
		} catch (MalformedURLException e) {
			logger.error("Il formato dell'url del web-service non e' valido:" + e.getMessage());
			unavailableService = true;
		} catch (Exception e){
			logger.error(e.getMessage());
			unavailableService = true;
		}
		return unavailableService;
	}


	/**
	 * @param nodeid
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private String[] getReferenceCommuneid(long nodeid, Connection connection)
			throws SQLException {
		String[] references = new String[2];
		String referenceQuery = "SELECT reference, codicecomune FROM fenode WHERE id = ? ";
		PreparedStatement statement = null;
		statement = connection.prepareStatement(referenceQuery);
		statement.setLong(1, nodeid);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			references[0] = resultSet.getString("reference");
			references[1] = resultSet.getString("codicecomune");
		}
		resultSet.close();
		return references;
	}

	/**
	 * @param id
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void deleteReference(long id, Connection connection)
			throws SQLException {
		PreparedStatement preparedStatement;
		String deleteReferenceQuery = "DELETE FROM reference WHERE id = ?";
		
		preparedStatement = connection.prepareStatement(deleteReferenceQuery);				
		preparedStatement.setLong(1, id);
		preparedStatement.execute();
		preparedStatement.close();	
	}

	/**
	 * @param id
	 * @param name
	 * @param value
	 * @param dump
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void updateReference(long id, String name,
			String value, int dump, Connection connection) throws SQLException {
		PreparedStatement preparedStatement;
		String updateReferenceQuery = "UPDATE reference SET name = ?, value = ?, dump = ? WHERE id = ?";
		
		preparedStatement = connection.prepareStatement(updateReferenceQuery);				
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, value);
		preparedStatement.setInt(3, dump);
		preparedStatement.setLong(4, id);
		preparedStatement.execute();
		preparedStatement.close();	
	}

	/**
	 * @param serviceid
	 * @param name
	 * @param value
	 * @param dump
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void insertReference(long serviceid, String name,
			String value, int dump, Connection connection) throws SQLException {
		PreparedStatement preparedStatement;
		String insertReferenceQuery = "INSERT INTO reference(serviceid, name, value, dump) VALUES( ?, ?, ?, ?)";
		
		preparedStatement = connection.prepareStatement(insertReferenceQuery, Statement.RETURN_GENERATED_KEYS);				
		preparedStatement.setLong(1, serviceid);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, value);
		preparedStatement.setInt(4, dump);
		preparedStatement.execute();
		preparedStatement.close();	
	}

	/**
	 * @param id
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void deleteConfiguration(long id, Connection connection)
			throws SQLException {
		PreparedStatement preparedStatement;
		String deleteConfigurationQuery = "DELETE FROM configuration WHERE id = ?";

		preparedStatement = connection.prepareStatement(deleteConfigurationQuery);				
		preparedStatement.setLong(1, id);
		preparedStatement.execute();
		preparedStatement.close();	
	}

	/**
	 * @param id
	 * @param name
	 * @param value
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void updateConfiguration(long id, String name,
			String value, Connection connection) throws SQLException {
		PreparedStatement preparedStatement;
		String updateConfigurationQuery = "UPDATE configuration SET name = ?, value = ? WHERE id = ?";

		preparedStatement = connection.prepareStatement(updateConfigurationQuery);				
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, value);
		preparedStatement.setLong(3, id);
		preparedStatement.execute();
		preparedStatement.close();
	}

	/**
	 * @param serviceid
	 * @param name
	 * @param value
	 * @param connection
	 * 
	 * @throws SQLException
	 */
	private void insertConfiguration(long serviceid, String name,
			String value, Connection connection) throws SQLException {
		PreparedStatement preparedStatement;
		String insertConfigurationQuery = "INSERT INTO configuration(serviceid, name, value) VALUES( ?, ?, ?)";
		
		preparedStatement = connection.prepareStatement(insertConfigurationQuery, Statement.RETURN_GENERATED_KEYS);				
		preparedStatement.setLong(1, serviceid);
		preparedStatement.setString(2, name);
		preparedStatement.setString(3, value);
		preparedStatement.execute();
		preparedStatement.close();
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
	
	

    /**
	 * @return lista dei valori selezionabili per EmbedAttachmentInXmlTypes 
	 */
	private List<PairElement<String, String>> getEmbedAttachmentInXmlTypesList() {

		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();

		result.add(new PairElement<String, String>("0", "Allegato remoto"));
		result.add(new PairElement<String, String>("1", "Allegato incluso in XML"));
		
		return result;
	}
	
	
	
	/**
	 * @return the logLevels
	 */
	private List<PairElement<String, String>> getLogLevels() {
		return logLevels;
	}

	/**
	 * @param logLevels the logLevels to set
	 */
	private void setLogLevels(List<PairElement<String, String>> logLevels) {
		this.logLevels = logLevels;
	}

	/**
	 * @return the statusTypes
	 */
	private List<PairElement<String, String>> getStatusTypes() {
		return statusTypes;
	}

	/**
	 * @param statusTypes the statusTypes to set
	 */
	private void setStatusTypes(List<PairElement<String, String>> statusTypes) {
		this.statusTypes = statusTypes;
	}

	/**
	 * @return the signProcessTypes
	 */
	public final List<PairElement<String, String>> getSignProcessTypes() {
		return signProcessTypes;
	}

	/**
	 * @param signProcessTypes the signProcessTypes to set
	 */
	public final void setSignProcessTypes(
			List<PairElement<String, String>> signProcessTypes) {
		this.signProcessTypes = signProcessTypes;
	}

	/**
	 * @return the includeAttachmentsInReceiptTypes
	 */
	public final List<PairElement<String, String>> getIncludeAttachmentsInReceiptTypes() {
		return includeAttachmentsInReceiptTypes;
	}

	/**
	 * @param includeAttachmentsInReceiptTypes the includeAttachmentsInReceiptTypes to set
	 */
	public final void setIncludeAttachmentsInReceiptTypes(
			List<PairElement<String, String>> includeAttachmentsInReceiptTypes) {
		this.includeAttachmentsInReceiptTypes = includeAttachmentsInReceiptTypes;
	}

	/**
	 * @return the sendMailToOwnerTypes
	 */
	public final List<PairElement<String, String>> getSendMailToOwnerTypes() {
		return this.sendMailToOwnerTypes;
	}

	/**
	 * @param sendMailToOwnerTypes the sendMailToOwnerTypes to set
	 */
	public final void setSendMailToOwnerTypes(
			List<PairElement<String, String>> sendMailToOwnerTypes) {
		this.sendMailToOwnerTypes = sendMailToOwnerTypes;
	}

	/**
	 * @return the embedAttachmentInXmlTypes
	 */
	public List<PairElement<String, String>> getEmbedAttachmentInXmlTypes() {
		return this.embedAttachmentInXmlTypes;
	}

	/**
	 * @param embedAttachmentInXmlTypes the embedAttachmentInXmlTypes to set
	 */
	public void setEmbedAttachmentInXmlTypes(
			List<PairElement<String, String>> embedAttachmentInXmlTypes) {
		this.embedAttachmentInXmlTypes = embedAttachmentInXmlTypes;
	}

	private List<PairElement<String, String>> getTabbedPaneHeaders() {
		
		List<PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
		
		result.add(new PairElement<String, String>("referenze", "Referenze b.e."));
		result.add(new PairElement<String, String>("parametri", "Parametri"));
		result.add(new PairElement<String, String>("messaggi", "Messaggi"));
		result.add(new PairElement<String, String>("tablevalues", "Elementi di interfaccia"));
		result.add(new PairElement<String, String>("audit", "Monitoraggio del servizio"));
		result.add(new PairElement<String, String>("velocityTemplates", "Template Velocity"));
		return result;
		
	}

	/**
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceLocalesByNodeId(String servicePackage, String nodeId) {
		return persistenceBroker.getServiceLocalesByNodeId(servicePackage, nodeId);
	}

	/**
	 * Populate registrabeleLocales combobox
	 * @param servicePackage
	 * @return
	 */
	private List<Option> getServiceRegisterableLocalesByNodeId(String servicePackage, String nodeId) {
		return persistenceBroker.getServiceRegisterableLocalesByNodeId(servicePackage, nodeId);
	}
	
	
	/**
	 * Populate tableValues TableId combobox 
	 * @param processName the process name also corresponds to servicePackage 
	 * @return
	 */
	private List <Option> getServiceTableValuesTableId(String servicePackage, String comuneId) {
		return persistenceBroker.getServiceTableValuesTableId(servicePackage, comuneId);
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
				Constants.PagedListHoldersIds.SERVICE_LABELS_LIST, dataSourcePeopleDB, serviceLabelQuery, 10, 
				rowColumnsIdentifiersServiceLabels, editableRowColumnsServiceLabels, false);
		pagedListHolderServiceLabels.setDeleteActionEnabled(false);
		
		model.addAttribute("bundleRef", this.persistenceBroker.getServiceMessagesBundleRefByNodeIdLocale(servicePackage, communeKey, locale));
		
		pagedListHolderServiceLabels.setVisibleColumnsNames(visibleColumnsNamesServiceLabels);
		
		return pagedListHolderServiceLabels;
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
	
	private ILazyPagedListHolder prepareBeReferenceList(String nodeId, String serviceId) throws LazyPagedListHolderException {

		/// Populate pagedListHolder for BE references
		List<String> rowColumnsIdentifiersBEReferences = new ArrayList<String>();
		rowColumnsIdentifiersBEReferences.add("id");
		//valori precedenti da inviare per update ws
		rowColumnsIdentifiersBEReferences.add("name");
		rowColumnsIdentifiersBEReferences.add("value");

		List<String> editableRowColumnsBEReferences = new ArrayList<String>();
		editableRowColumnsBEReferences.add("name");
		editableRowColumnsBEReferences.add("value");
		editableRowColumnsBEReferences.add("dump");
		
		String[] parameters = new String[] { nodeId, serviceId };
		ILazyPagedListHolder pagedListHolderBEReferences = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.BE_REFERENCES_LIST, dataSourcePeopleDB, 
				setQueryParameters(beReferencesQuery, parameters), 10,
				rowColumnsIdentifiersBEReferences, 
				editableRowColumnsBEReferences, true);
		
		List<String> visibleColumnsNamesBEReferences = new ArrayList<String>();
		visibleColumnsNamesBEReferences.add("name");
		visibleColumnsNamesBEReferences.add("value");
		visibleColumnsNamesBEReferences.add("dump");
		pagedListHolderBEReferences.setVisibleColumnsNames(visibleColumnsNamesBEReferences);

		Map<String, Object> beReferencesEditableRowModelers = new HashMap<String, Object>();
		beReferencesEditableRowModelers.put("dump", new EditableRowCheckbox());
		beReferencesEditableRowModelers.put("value", new EditableRowSelect(dataSourcePeopleDB, 
				"SELECT nodobe, nodobe FROM benode WHERE nodeid = ?", 
				new Object[] {nodeId}));
		pagedListHolderBEReferences.setEditableRowModelers(beReferencesEditableRowModelers);
		
		RowsStatusModeler beReferenceRowsStatusModelers = new RowsStatusModeler();
		RowStatusModeler rowStatusModeler = new RowStatusModeler("/important-2.png", 
				"BE non censito", "BE non censito");
		beReferenceRowsStatusModelers.addStatusModeler(0, rowStatusModeler);
		rowStatusModeler = new RowStatusModeler("/dialog-accept.png", 
				"BE censito", "BE censito");
		beReferenceRowsStatusModelers.addStatusModeler(1, rowStatusModeler);
		pagedListHolderBEReferences.setRowsStatusModelers(beReferenceRowsStatusModelers);
		
		return pagedListHolderBEReferences;
	}


	/**
	 * Prepare paged list holder for VelocityTemplates
	 * 
	 * @param feService
	 * @param pageSize
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareVelocityTemplatesList(FEService feService, int pageSize) throws LazyPagedListHolderException {
		
		/// Populate pagedListHolder for templates
		List<String> rowColumnsIdentifiers = new ArrayList<String>();
		rowColumnsIdentifiers.add("_communeId");
		rowColumnsIdentifiers.add("_serviceId");
		rowColumnsIdentifiers.add("Chiave");
		
		//Retreive velocity template tables
		try {
			//pass the package to get right templates  
			refreshVelocityTemplateTable(feService.getMunicipality(), feService.get_package(), feService.getNodeId());
			
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
	private void refreshVelocityTemplateTable(String communeId, String servicePkg, long nodeId) throws SQLException {
		
		//Rewrite table 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		VelocityTemplateDataVO velocityTemplatesData = null;
		
		try {
			connection = this.dataSourcePeopleDB.getConnection();
			//Get data from proper WS interface
			FEInterface feInterface = this.getFEInterface(this.getReferenceCommuneid(nodeId, connection)[0]);
			velocityTemplatesData = (VelocityTemplateDataVO) feInterface.getVelocityTemplatesData(
					communeId, servicePkg, false);

		} catch (FeServiceReferenceException e) {
			logger.warn("Unable to get FeService reference for node " + servicePkg , e);
		} catch (RemoteException e) {
			logger.warn("Remote exeption refreshing Velocity Template Table.", e);
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
			} catch(SQLException ignore) {}
		}
	}

	/**
	 * Prepare a Paged List Holder of active and inactive audit processors for a service  
	 * @param serviceId
	 * @param pageSize
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareAuditProcessorsList(String serviceId, int pageSize) throws LazyPagedListHolderException {

		/// Populate pagedListHolder for auditProcessors
		List<String> rowColumnsIdentifiers = new ArrayList<String>();
		rowColumnsIdentifiers.add("id");
		rowColumnsIdentifiers.add("auditProcessor");

		List<String> editableRowColumns = new ArrayList<String>();
		editableRowColumns.add("Attivo");

		String[] parameters = new String[] {serviceId,serviceId};
		ILazyPagedListHolder auditProcessorListHolder = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.AUDIT_PROCESSORS_LIST, dataSourcePeopleDB, 
				setQueryParameters(this.getProperty(Constants.Queries.AUDIT_PROCESSORS_FOR_SERVICE), parameters),
				pageSize, rowColumnsIdentifiers, editableRowColumns, false);
		
		List<String> visibleColumnsNames = new ArrayList<String>();
		visibleColumnsNames.add("Monitoraggio");
		visibleColumnsNames.add("Attivo");
		auditProcessorListHolder.setVisibleColumnsNames(visibleColumnsNames);
		auditProcessorListHolder.setDeleteActionEnabled(false);

		Map<String, Object> editableRowModelers = new HashMap<String, Object>();
		editableRowModelers.put("Attivo", new EditableRowCheckbox());
		
		auditProcessorListHolder.setEditableRowModelers(editableRowModelers);
			
		return auditProcessorListHolder;
	}
	
	
	
	private ILazyPagedListHolder prepareParametersList(String serviceId) throws LazyPagedListHolderException {
		
		/// Populate pagedListHolder for Parameters
		List<String> rowColumnsIdentifiersServiceParameters = new ArrayList<String>();
		rowColumnsIdentifiersServiceParameters.add("id");
		//valori precedenti da inviare per update ws 
		rowColumnsIdentifiersServiceParameters.add("name");
		rowColumnsIdentifiersServiceParameters.add("value");

		List<String> editableRowColumnsServiceParameters = new ArrayList<String>();
		editableRowColumnsServiceParameters.add("name");
		editableRowColumnsServiceParameters.add("value");
		
		String[] parameters = new String[] { serviceId };
		ILazyPagedListHolder pagedListHolderServiceParameters = LazyPagedListHolderFactory.getLazyPagedListHolder(
				Constants.PagedListHoldersIds.PARAMETERS_LIST, dataSourcePeopleDB, setQueryParameters(serviceParametersQuery, parameters), 10, rowColumnsIdentifiersServiceParameters, 
				editableRowColumnsServiceParameters, true);
		
		List<String> visibleColumnsNamesServiceParameters = new ArrayList<String>();
		visibleColumnsNamesServiceParameters.add("name");
		visibleColumnsNamesServiceParameters.add("value");
		pagedListHolderServiceParameters.setVisibleColumnsNames(visibleColumnsNamesServiceParameters);
		
		return pagedListHolderServiceParameters;
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
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.GET)
    public String setupEditVelocityTemplateForm(@RequestParam String action, @RequestParam String _communeId,
    		@RequestParam String _serviceId, @RequestParam String Chiave,
    		@RequestParam String plhId, ModelMap model, HttpServletRequest request,
    		@ModelAttribute("feService") FEService feService) {
		
		//Check _serviceID from plh for "null"
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
						feService.getServiceName(), feService.get_package(), feService.getNodeName());
		
		//Do work here
		
		model.put("includeTopbarLinks", true);
			
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
			
		model.addAttribute("feService", feService);
			
		model.addAttribute("tab", "velocityTemplates");
		
		model.addAttribute("velocityTemplate", velocityTemplate);
			
		this.setPageInfo(model, "addOrEditVelocityTemplate.title", null, "velocityTemplates");
			
		return getStaticProperty("feservices.addAndEditVelocityTemplate.view");
		
	}
	
	
	@RequestMapping(value = "/modificaTemplateVelocity.do", method = RequestMethod.POST)
	public String processEditSubmit(@ModelAttribute("feService") FEService feService,
			@ModelAttribute("velocityTemplate") VelocityTemplate velocityTemplate, BindingResult bindingResult,
    		HttpServletRequest request, SessionStatus sessionStatus, ModelMap model) {
		
		boolean isCancel = isParamInRequest(request, "cancel");
		boolean isUpdate = isParamInRequest(request, "updateVelocityTemplate");
		
		if (isCancel) {
			logger.debug("Template editing canceled");
		}
		
		if (isUpdate) {
			List <VelocityTemplate> templates = new ArrayList <VelocityTemplate> ();
			templates.add(velocityTemplate);
			VelocityTemplateDataVO templateDataVO = VelocityTemplatesUtils.buildVelocityTemplateVOToUpdate(templates, 
					feService.get_package(), feService.getMunicipality());
			
			FEInterface feInterface;
			try {
				feInterface = this.getFEInterface(feService.getReference());
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
    	
    	model.addAttribute("feService", feService);
    	
    	this.setPageInfo(model, "feservice.details.title", null, "feServiceD");
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	return "redirect:dettaglio.do";
	}

	
}

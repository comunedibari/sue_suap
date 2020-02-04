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
package it.people.console.web.controllers.settings;

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.config.ConsoleConfigurationCatalog;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.ConsoleSettingsDTO;
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
import it.people.console.quartz.ConsoleScheduler;
import it.people.console.security.AbstractCommand;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.validator.ConsoleSettingsValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;

@Controller
@RequestMapping("/Amministrazione/Impostazioni")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "consoleSettingsDTO"})
public class ConsoleSettingsController extends AbstractListableController {

	private ConsoleSettingsValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	@Autowired
	private ConsoleScheduler consoleScheduler;
	
	
	private String queryMailSettings = "select _key, _type, _label 'Impostazione', _value 'Valore', _description 'Descrizione' from pc_configuration where _scope = 'mail'";

	private String querySecuritySettings = "select _key, _type, _label 'Impostazione', _value 'Valore', _description 'Descrizione' from pc_configuration where _scope = 'security'";
	
	private String queryMonitoringSettings = "select _key, _type, _label 'Impostazione', _value 'Valore', _description 'Descrizione' from pc_configuration where _scope = 'monitoring'";
	
	private String querySchedulerSettings = "select _key, _type, _label 'Impostazione', _value 'Valore', _description 'Descrizione' from pc_configuration where _scope = 'scheduler'";
	
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	@Autowired
	public ConsoleSettingsController(ConsoleSettingsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("consoleSettingsDTO");
	}
	
    @RequestMapping(value = "/impostazioni.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	ConsoleSettingsDTO consoleSettingsDTO = null;
    	
    	if (request.getSession().getAttribute("consoleSettingsDTO") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("generali.do")) {
    		consoleSettingsDTO = new ConsoleSettingsDTO();

        	try {
        		
        		List<String> rowColumnsIdentifiers = new ArrayList<String>();
        		rowColumnsIdentifiers.add("_key");
        		rowColumnsIdentifiers.add("_type");

        		List<String> editableRowColumns = new ArrayList<String>();
        		editableRowColumns.add("_value");

        		List<String> visibleColumnsNames = new ArrayList<String>();
        		visibleColumnsNames.add("_label");
        		visibleColumnsNames.add("_value");
        		visibleColumnsNames.add("_description");
        		
        		//Monitoring settings
        		ILazyPagedListHolder pagedListHolderMonitoringSettings = LazyPagedListHolderFactory.getLazyPagedListHolder(
        				Constants.PagedListHoldersIds.MONITORING_SETTINGS, dataSourcePeopleDB, queryMonitoringSettings, 10, rowColumnsIdentifiers, 
    					editableRowColumns, false);
        		pagedListHolderMonitoringSettings.setVisibleColumnsNames(visibleColumnsNames);
        		pagedListHolderMonitoringSettings.setDeleteActionEnabled(false);
        		consoleSettingsDTO.addPagedListHolder(pagedListHolderMonitoringSettings);
        		
        		ILazyPagedListHolder pagedListHolderMailSettings = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.MAIL_SETTINGS, dataSourcePeopleDB, queryMailSettings, 10, rowColumnsIdentifiers, 
    					editableRowColumns, false);
        		pagedListHolderMailSettings.setVisibleColumnsNames(visibleColumnsNames);
        		pagedListHolderMailSettings.setDeleteActionEnabled(false);
        		consoleSettingsDTO.addPagedListHolder(pagedListHolderMailSettings);

        		ILazyPagedListHolder pagedListHolderSecuritySettings = LazyPagedListHolderFactory.getLazyPagedListHolder(
        				Constants.PagedListHoldersIds.SECURITY_SETTINGS, dataSourcePeopleDB, querySecuritySettings, 10, rowColumnsIdentifiers, 
    					editableRowColumns, false);
        		pagedListHolderSecuritySettings.setVisibleColumnsNames(visibleColumnsNames);
        		pagedListHolderSecuritySettings.setDeleteActionEnabled(false);
        		consoleSettingsDTO.addPagedListHolder(pagedListHolderSecuritySettings);
        		
        		//Scheduler settings
        		ILazyPagedListHolder pagedListHolderSchedulerSettings = LazyPagedListHolderFactory.getLazyPagedListHolder(
        				Constants.PagedListHoldersIds.SCHEDULER_SETTINGS, dataSourcePeopleDB, querySchedulerSettings, 10, rowColumnsIdentifiers, 
    					editableRowColumns, false);
        		pagedListHolderSchedulerSettings.setVisibleColumnsNames(visibleColumnsNames);
        		pagedListHolderSchedulerSettings.setDeleteActionEnabled(false);
        		consoleSettingsDTO.addPagedListHolder(pagedListHolderSchedulerSettings);
        		

    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {
    		consoleSettingsDTO = (ConsoleSettingsDTO)request.getSession().getAttribute("consoleSettingsDTO");
    		processListHoldersRequests(request.getQueryString(), consoleSettingsDTO);
    	}

    	try {
			applyColumnSorting(request, consoleSettingsDTO);
		} catch (LazyPagedListHolderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("consoleSettingsDTO", consoleSettingsDTO);
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "admin.settings.title", null, "consoleS");

    	return getStaticProperty("settings.view");
    	
    }

    @RequestMapping(value = "/impostazioni.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("consoleSettingsDTO") ConsoleSettingsDTO consoleSettingsDTO, BindingResult result, 
    		HttpServletRequest request) {

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
        		consoleSettingsDTO.getPagedListHolder(Constants.PagedListHoldersIds.MAIL_SETTINGS).applyFilters(updatedAppliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}

    	if (isParamInRequest(request, "clearFilters")) {
        	if (logger.isDebugEnabled()) {
        		logger.debug("Clearing " + updatedAppliedFilters.size() + " active filters...");
        	}
        	try {
        		consoleSettingsDTO.getPagedListHolder(Constants.PagedListHoldersIds.MAIL_SETTINGS).removeFilters();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters cleared.");
        	}
    	}
    	
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, consoleSettingsDTO, model);
    	}
    	
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("consoleSettingsDTO", consoleSettingsDTO);
    	
    	setRowsPerPageDefaultModelAttributes(model);

    	this.setPageInfo(model, "admin.settings.title", null, "consoleS");

		return getStaticProperty("settings.view");
    	
    }

    @Override
    protected IFilters prepareFilters() {
    	    	
    	Vector<IFilter> filters = new Vector<IFilter>();

    	filters.add(getLabelFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }

    private IFilter getLabelFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Impostazione", "_label", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction()
	 */
	@Override
	protected void processAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap modelMap) {

		if (logger.isDebugEnabled()) {
			logger.debug("Processing action...");
			logger.debug("Paged list holder id: " + pagedListHolderId);
			logger.debug("Action: " + action);
			logger.debug("Editable row input data: " + editableRowInputData);
		}
		
		if (action == AbstractCommand.CommandActions.save) {
			String key = String.valueOf(editableRowInputData.getRowIdentifiers().get("_key"));
			String type = String.valueOf(editableRowInputData.getRowIdentifiers().get("_type"));
			String value = String.valueOf(editableRowInputData.getInputData().get("_value"));
			if (logger.isDebugEnabled()) {
				logger.debug("Save values:");
				logger.debug("\n\tkey: " + key);
				logger.debug("\n\ttype: " + type);
				logger.debug("\n\tnew value: " + value);
			}
			
			try {
				this.persistenceBroker.updateConfiguration(key, value);
				modelMap.addAttribute("saveMessageSuccess", this.getProperty("message.save.success").trim());
				modelMap.addAttribute("saveMessageFailed", null);
			} catch (PersistenceBrokerException e) {
				modelMap.addAttribute("saveMessageSuccess", null);
				modelMap.addAttribute("saveMessageFailed", this.getProperty("message.save.failed"));
				e.printStackTrace();
			}
			
			
			//Apply setting for SCHEDULER
			applySchedulerSettings(key, value);

		}
	}    
    
	private void applySchedulerSettings(String key, String newValue) {
		
		if (key.equals(ConsoleConfigurationCatalog.SchedulerCatalog.CONSOLE_INFO_UPDATE_TRIGGER)) {
			//Check new value and re schdule job
			try {
				consoleScheduler.scheduleBeServiceAvailabilityJob("0 0/" + newValue + " * * * ?", 0);
				logger.debug("APPLYING SCHEDULER SETTINGS..");
			} catch (SchedulerException e) {
				logger.error("Unable to apply Scheduler settings", e);
			}	

		}
	}
	
}

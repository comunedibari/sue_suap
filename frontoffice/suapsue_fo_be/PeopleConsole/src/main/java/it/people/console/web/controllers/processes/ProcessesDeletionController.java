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
package it.people.console.web.controllers.processes;

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import it.people.console.beans.support.IFilters;

import it.people.console.domain.PairElement;
import it.people.console.domain.ProcessDeletionBean;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.FENodeDTO;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.security.AbstractCommand;
import it.people.console.system.MessageSourceAwareClass;

import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;

import it.people.console.web.controllers.validator.ProcessDeletionValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.ProcessBean;
import it.people.feservice.beans.ProcessFilter;
import it.people.feservice.beans.ProcessesDeletionResultVO;

/**
 * 
 * @author Andrea Piemontese - Engineering Ingegneria Informatica S.p.A.
 * @created 11/set/2012 11:14:58
 *
 */
@Controller
@RequestMapping("/Amministrazione/Pratiche")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "process"})
public class ProcessesDeletionController extends AbstractListableController {

	MessageSourceAwareClass messagesSource = new MessageSourceAwareClass();
	
	private static int DATE_INTERVAL_FILTER = 1;
	private static int DAY_OLD_FILTER = 2;
	
	//Validator
	private ProcessDeletionValidator validator;
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	//Default pagination settings
	private int defaultPageSize = 10;
	private int defaultLowerPageLimit = 0;
	//Default date settings
	private DateFormat defaultDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	//Combobox
	private Collection <PairElement<String, String>> usersList = null;
	private Collection <PairElement<String, String>> nodesList = null;
	
	@Autowired
	public ProcessesDeletionController(ProcessDeletionValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("process");
	}
	
    @RequestMapping(value = "/cancellazione.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	//Model bean
    	ProcessDeletionBean process = new ProcessDeletionBean();
    	ProcessDeletionBean lastSelectedBean = null;
    	
    	//Referers
    	boolean fromCanc = WebUtils.getReferer(request).contains("cancellazione.do");
    	boolean fromRis = WebUtils.getReferer(request).contains("Pratiche/risultatoOperazione.do");
    	boolean fromConf = WebUtils.getReferer(request).contains("Pratiche/conferma.do");
    	
    	//init ProcessDeletion
    	if (request.getSession().getAttribute("process") == null || !(fromCanc || fromRis || fromConf )) {
    	
    		//Empty filter and lastSelectedBean
	    	ProcessFilter defaultFilter = new ProcessFilter();
	    	request.getSession().removeAttribute("lastSelectedBean");
	    	
        	//Create PagedListHolder
        	try {
        		if (!process.getPagedListHolders().containsKey(
        				Constants.PagedListHoldersIds.PROCESSES_DELETION_LIST)) {
        			process.addPagedListHolder(prepareIndicatorsPagedList(defaultFilter, defaultLowerPageLimit, defaultPageSize, 
        					process.getSelectedUsers(), process.getSelectedNodes()));
        		}
        		else {
        			process.updatePagedListHolder(prepareIndicatorsPagedList(defaultFilter, defaultLowerPageLimit, defaultPageSize,
        					process.getSelectedUsers(), process.getSelectedNodes()));  
        		}
    		} catch (LazyPagedListHolderException e) {
    			logger.error("LazyPagedListHolderException adding paged list holder");
    		} catch (PagedListHoldersCacheException e) {
    			logger.error("PagedListHoldersCacheException adding paged list holder");
    		}
    	}
    	else {
    		//Restore selected bean
    		if (request.getSession().getAttribute("lastSelectedBean") != null) {
				process = new ProcessDeletionBean((ProcessDeletionBean) request.getSession()
						.getAttribute("lastSelectedBean"));
    		} else {
    			//or get already existing ProcessDeletionBean
    			process = (ProcessDeletionBean) request.getSession().getAttribute("process");
    		}
    		//get result to reload pagedListHolder content
    		processGetResult(process);
    		processListHoldersRequests(request.getQueryString(), process);
    		
    	}

    	model.put("includeTopbarLinks", true);
    
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("process", process);
    	
		if (usersList == null) {
			usersList = this.getUsersList();
		}

		if (nodesList == null) {
			nodesList = this.getNodesList();
		}

    	model.addAttribute("usersList", usersList);
    	model.addAttribute("nodesList", nodesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);

    	this.setPageInfo(model, "admin.processes.deletion.title", null, "processes");

    	return getStaticProperty("admin.processes.view");
    }

    
	/**
	 * Get all users
	 * @return list of users 
	 */
	private Collection<PairElement<String, String>> getUsersList() {
		
		List <PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
    	result.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_VALUE), "Qualsiasi"));
		
		//Get users from Front End services 
		try {
			FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();
			String[] processUsers = feInterface.getProcessUsers();
			
			for (int i=0; i < processUsers.length; i++) {
				result.add(new PairElement<String, String>(processUsers[i], processUsers[i]));
			}
			
		} catch (FeServiceReferenceException e) {
			logger.error("Unable to get a FEInterface.", e);
		} catch (RemoteException e) {
			logger.error("Unable to fetch user list from FEService.", e);
		}
    	return result;
	}

	/**
	 * Get nodes list from DB
	 * @return list of Nodes
	 */
    private List <PairElement<String, String>> getNodesList() {
		
    	List <PairElement<String, String>> result = new ArrayList<PairElement<String, String>>();
    	result.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_VALUE), "Qualsiasi"));
    	
    	try {
			Map<Integer, FENodeDTO> registeredNodes = this.persistenceBroker.getRegisteredNodesWithBEServices();
			Iterator<Entry<Integer, FENodeDTO>> nodesIter = registeredNodes.entrySet().iterator();
			while(nodesIter.hasNext()) {
				FENodeDTO node = nodesIter.next().getValue();
				result.add(new PairElement<String, String>(node.getMunicipalityCode(), node.getName()));
			}
			
		} catch (PersistenceBrokerException e) {
				logger.error("Unable to fetch registered nodes.", e);
		}
		return result;
    }
   

    /**
	 * Get the FEInterface (WS interface) from first registered node
	 * 
	 * @return
	 * @throws PersistenceBrokerException
	 * @throws FeServiceReferenceException
	 */
	private FEInterface getFEInterfaceFromFirstRegisteredNode() throws FeServiceReferenceException {
		
		boolean interfaceFound = false;
		FEInterface feInterface = null;
		
		Map<Integer, FENodeDTO> registeredNodesWithBEServices = null;
		
		try {
			registeredNodesWithBEServices = persistenceBroker.getRegisteredNodesWithBEServices();
		} catch (PersistenceBrokerException e1) {
			logger.error("PersistenceBroker was unable to get registered nodes from DB", e1);
		}
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
		
		//Check if found
		if (!interfaceFound) {
			throw new FeServiceReferenceException("No FEService interface found from registered URLs");
		}
		
		return feInterface;
	}
    
    
    @RequestMapping(value = "/cancellazione.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("process") ProcessDeletionBean process, BindingResult result, 
    		HttpServletRequest request) {
    	
		//Navigation actions
		boolean isGetResult = this.isParamInRequest(request, "getResult");	
		boolean isDelete = isPrefixParamInRequest(request, "delete");
		boolean isArchive = isPrefixParamInRequest(request, "archive");
		
		//Handle GET RESULT
		if (isGetResult) {
			validator.validate(process, result);
			
			if (!result.hasErrors()) {
				processGetResult(process);
				
		    	//Store last calculated indicator to be sent
				request.getSession().setAttribute("lastSelectedBean", new ProcessDeletionBean(process));
			}
		}
		
		//Handle archive
		if (isArchive) {
			
			if (request.getSession().getAttribute("lastSelectedBean") != null ) {
				validator.validate(request.getSession().getAttribute("lastSelectedBean"), result);
				if (!result.hasErrors()) {
					return "redirect:conferma.do?action=archive";
				}
			}
		}
		
		//Handle delete 
		if (isDelete) {
			
			if (request.getSession().getAttribute("lastSelectedBean") != null ) {
				validator.validate(request.getSession().getAttribute("lastSelectedBean"), result);
				if (!result.hasErrors()) {
					return "redirect:conferma.do?action=delete";
				}
			}
		}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("process", process);

    	model.addAttribute("usersList", usersList);
    	
    	model.addAttribute("nodesList", nodesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);

    	this.setPageInfo(model, "admin.processes.deletion.title", null, "processes");
    	
    	//Process Listholder requests
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, process, model);
    	}

    	return getStaticProperty("admin.processes.view");
    }
    
    
    /**
     * Archive processes calling WS operation
	 * @param lastSelectedBean
	 */
	private ProcessesDeletionResultVO processArchiveProcesses(ProcessDeletionBean lastSelectedBean) {
		
		ProcessesDeletionResultVO archiveProcesses = null; 
		
		//TODO this operation should be executed for every FEinterface (FE node).
		FEInterface feInterface;
		try {
			feInterface = getFEInterfaceFromFirstRegisteredNode();
			
			ProcessFilter filter = createProcessFilter(lastSelectedBean);
			archiveProcesses = feInterface.deleteProcesses(filter, lastSelectedBean.getSelectedUsers(), lastSelectedBean.getSelectedNodes(), true);
			
		} catch (FeServiceReferenceException e) {
			logger.error("FeServiceReferenceException during Processes Archiviation: unable to get FeService reference", e); 
		} catch (RemoteException e) {
			logger.error("Remote exception during Processes Archiviation", e);
		}
		
		return archiveProcesses;
		
	}
	
	/**
	 * Delete processes calling WS operation
	 * @param lastSelectedBean
	 */
	private ProcessesDeletionResultVO processDeleteProcesses(ProcessDeletionBean lastSelectedBean) {
		
		ProcessesDeletionResultVO deleteProcesses = null; 
		
		//TODO this operation should be executed for every FEinterface (FE node).
		FEInterface feInterface;
		try {
			feInterface = getFEInterfaceFromFirstRegisteredNode();
			
			ProcessFilter filter = createProcessFilter(lastSelectedBean);
			deleteProcesses = feInterface.deleteProcesses(filter, lastSelectedBean.getSelectedUsers(), lastSelectedBean.getSelectedNodes(), false);
			
		} catch (FeServiceReferenceException e) {
			logger.error("FeServiceReferenceException during Processes Archiviation: unable to get FeService reference", e); 
		} catch (RemoteException e) {
			logger.error("Remote exception during Processes Archiviation", e);
		}
		
		return deleteProcesses;
		
	}
	

	@RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
	public String setupConferma(ModelMap model, @RequestParam("action") String action,
			HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	if (action.equals("delete")) {
    		model.put("message", messagesSource.getProperty("warning.processes.confirmDelete"));
    	}
    	else if (action.equals("archive")) {
    		model.put("message", messagesSource.getProperty("warning.processes.confirmFiling"));
    	}
    	
    	//Pass action
    	request.getSession().setAttribute("action", action);
    	
    	return getStaticProperty("confirm.view");
    }
	
	
	@RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
	public String processConferma(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
	    		@ModelAttribute("process") ProcessDeletionBean process, BindingResult result,
	    		HttpServletRequest request) {

	    	model.put("includeTopbarLinks", true);
	    	
	    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

	    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
	    	
	    	model.addAttribute("process", process);

	    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
	    	
			String action = request.getSession().getAttribute("action") == null ? "" : (String) request
					.getSession().getAttribute("action");
	    	
	    	ProcessesDeletionResultVO archiveResult = null;
	    	
	    	//If confirmed do delete or archive
	    	if (isConfirmAction) {
	    		
				if (request.getSession().getAttribute("lastSelectedBean") != null ) {
					
					ProcessDeletionBean lastSelected = (ProcessDeletionBean) request.getSession()
							.getAttribute("lastSelectedBean");
					
					if (action.equals("archive")) {
						archiveResult = processArchiveProcesses(lastSelected);
						request.getSession().setAttribute("archiveResult", archiveResult);
						return "redirect:risultatoOperazione.do?action=archive";
					}
					else if (action.equals("delete")) {
						archiveResult = processDeleteProcesses(lastSelected);
						request.getSession().setAttribute("archiveResult", archiveResult);
						return "redirect:risultatoOperazione.do?action=delete";
					}
				}
	    		
	    	} else {
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("Action canceled.");
	    		}
	    		request.getSession().removeAttribute("action");
	    	} 	
	    	
	    	return "redirect:cancellazione.do";
	    }
	

	@RequestMapping(value = "/risultatoOperazione.do", method = RequestMethod.GET)
    public String setupOperationResult(ModelMap model, @RequestParam("action") String action, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	if (action.equals("archive")) {
    		model.addAttribute("isArchive", true);
    	} else if (action.equals("delete")) {
    		model.addAttribute("isDelete", true);
    	}
    	
    	//Put operation result in model to be visualized in JSP
    	model.put("archiveResult", request.getSession().getAttribute("archiveResult"));
    	
    	return getStaticProperty("admin.processes.result.view");
    }
    
    
    @RequestMapping(value = "/risultatoOperazione.do", method = RequestMethod.POST)
    public String viewOperationResult(ModelMap model,
    		@ModelAttribute("process") ProcessDeletionBean process, BindingResult result, 
    		HttpServletRequest request) {
    	
    	request.getSession().removeAttribute("archiveResult");
    	request.getSession().removeAttribute("action");
    	
    	return "redirect:cancellazione.do";
    }
    

    /**
     * Process get result: update the list of results
     * @param ProcessDeletionBean model bean
     */
	private void processGetResult(ProcessDeletionBean process) {

		ProcessFilter filter = createProcessFilter(process);
		
		try {
			int currentPageSize = process.getPagedListHolder(Constants.PagedListHoldersIds.PROCESSES_DELETION_LIST).getPageSize();
    		process.updatePagedListHolder(prepareIndicatorsPagedList(filter, defaultLowerPageLimit, currentPageSize,
					process.getSelectedUsers(), process.getSelectedNodes()));
		} catch (LazyPagedListHolderException e) {
			logger.error("LazyPagedListHolderException adding paged list holder");
		} catch (PagedListHoldersCacheException e) {
			logger.error("PagedListHoldersCacheException adding paged list holder");
		}
	}
    	
	/**
	 * Create a process filter using user selected data contained in a ProcessDeletionBean
	 * @param process
	 * @return
	 */
	private ProcessFilter createProcessFilter(ProcessDeletionBean process) {
		
		ProcessFilter filter = null;
		
		int nsp = process.isOnlyNotSubmittable()? 1 : 0;
		int pp = process.isOnlyPending()? 1 : 0;
		int sp = process.isOnlySubmitted()? 1 : 0;
		
		//Check if all unselected = retrieve all.
		if ((nsp == 0) && (pp == 0) && (sp == 0)) {
			nsp = pp = sp = 1;
		}

		//Check if data filter type and fill Process filter accordingly
		if (process.getDateFilterType() == DATE_INTERVAL_FILTER) {
			filter = new ProcessFilter(
					defaultDateFormat.format(process.getFromDate().getTime()),
					defaultDateFormat.format(process.getToDate().getTime()),
					-1, nsp, pp, sp);
		} 
		else if (process.getDateFilterType() == DAY_OLD_FILTER) {
			filter = new ProcessFilter("","", process.getOlderThanDays(), nsp, pp, sp);
		}	

		return filter;
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#processAction()
	 */
	@Override
	protected void processAction(String pagedListHolderId, AbstractCommand.CommandActions action, 
			EditableRowInputData editableRowInputData, HttpServletRequest request, 
			ModelMap modelMap) {
	}

	/* (non-Javadoc)
	 * @see it.people.console.web.servlet.mvc.AbstractListableController#prepareFilters()
	 */
	@Override
	protected IFilters prepareFilters() {
		
		return null;
	}    
    
	/**
	 * Build a lazy paged list holder
	 * 
	 * @param filter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @param selectedUsers
	 * @param selectedNodes
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareIndicatorsPagedList(ProcessFilter filter, int lowerPageLimit, int pageSize, String[] selectedUsers, String[] selectedNodes) throws LazyPagedListHolderException {
			
		ILazyPagedListHolder processDeletionPagedListHolder = null;
		

		//Columns to extract (using fields get/set)
        List <String> rowColumnsIdentifiers = new ArrayList <String>();
        rowColumnsIdentifiers.add("codiceEnte");
        rowColumnsIdentifiers.add("stringTimestamp");
        rowColumnsIdentifiers.add("attivitaName");
        rowColumnsIdentifiers.add("serviceName");
        rowColumnsIdentifiers.add("processName");
        rowColumnsIdentifiers.add("username");
        rowColumnsIdentifiers.add("processType");


		//Visible Columns
		List<String> visibleColumnsIdentifiers = new ArrayList<String>();
		visibleColumnsIdentifiers.add("codiceEnte");
		visibleColumnsIdentifiers.add("stringTimestamp"); 
		visibleColumnsIdentifiers.add("attivitaName");
		visibleColumnsIdentifiers.add("serviceName");
		visibleColumnsIdentifiers.add("processName");
		visibleColumnsIdentifiers.add("username");  
		visibleColumnsIdentifiers.add("processType");
		
		
        //Visible columns labels
		List<String> visibleColumnsLabels = new ArrayList<String>();
		visibleColumnsLabels.add("Ente");
		visibleColumnsLabels.add("Data");
		visibleColumnsLabels.add("Area");
		visibleColumnsLabels.add("Sottoarea");
		visibleColumnsLabels.add("Procedimento");
		visibleColumnsLabels.add("Utente");
		visibleColumnsLabels.add("Tipo");
		
        
        
		//Parms to call method on source object
		Class paramtypes[] = new Class[6];
		paramtypes[0] = ProcessFilter.class;
		paramtypes[1] = Integer.TYPE;
		paramtypes[2] = Integer.TYPE;
		paramtypes[3] = String[].class;
		paramtypes[4] = String[].class;
		paramtypes[5] = Boolean.TYPE;
			
		//Paging params
        Object arglist[] = new Object[6];
        arglist[0] = filter;
        arglist[1] = new Integer(lowerPageLimit);
        arglist[2] = new Integer(pageSize);
        arglist[3] = selectedUsers;
        arglist[4] = selectedNodes;
        arglist[5] = false;
		
        try {
        	//TODO this operation should be executed for every FEinterface.
			FEInterface feInterface = getFEInterfaceFromFirstRegisteredNode();

			processDeletionPagedListHolder = LazyPagedListHolderFactory
					.getLazyPagedListHolder(Constants.PagedListHoldersIds.PROCESSES_DELETION_LIST, feInterface ,"getProcesses",
							paramtypes, arglist, 1, 2, rowColumnsIdentifiers, visibleColumnsIdentifiers, visibleColumnsLabels);

		} catch (FeServiceReferenceException e) {
			logger.error("FeServiceReferenceException while preparing IndicatorsPagedList", e);
		}
        
		return processDeletionPagedListHolder;
	}
	
	
}

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

import java.net.URL;
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

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.domain.FENodeListElement;
import it.people.console.domain.FEServiceRegistration;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.utils.FeServiceRegister;
import it.people.console.web.controllers.validator.FeServicesRegistrationValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.client.FEInterfaceServiceLocator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziFe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "feServiceRegistration", "feNodesList", Constants.ControllerUtils.APPLIED_FILTERS_KEY})
public class FEServicesRegistrationController extends AbstractListableController {

	private FeServicesRegistrationValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	private String query = "SELECT ser.id serid, fenode.comune 'Comune', ser.attivita 'Attività', ser.sottoattivita 'Sottoattività', ser.nome 'Nome', stat.value 'Stato', lg.value 'Log', ser.package, ser.nodeid, ser.process FROM service ser, STATUS stat, LOG lg, fenode WHERE stat.id = ser.stato AND lg.id = ser.loglevel AND fenode.id = ser.nodeid";

	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	@Autowired
	public FEServicesRegistrationController(FeServicesRegistrationValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("feServiceRegistration");
	}
	
    @RequestMapping(value = "/elenco.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	int pageSize = 10;
    	if (request.getSession().getAttribute("feServiceRegistrationPageSize") != null) {
    		pageSize = (Integer) request.getSession().getAttribute("feServiceRegistrationPageSize"); 
    	}
    	
    	FEServiceRegistration feServiceRegistration = null;
    	
    	if (request.getSession().getAttribute("feServiceRegistration") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("ServiziFe/elenco.do")) {
    		feServiceRegistration = new FEServiceRegistration();

        	try {
        		
        		List<String> rowColumnsIdentifiersFEServices = new ArrayList<String>();
        		rowColumnsIdentifiersFEServices.add("id");
//        		rowColumnsIdentifiersFEServices.add("nodeid");

        		ILazyPagedListHolder pagedListHolderFEServices = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.FE_SERVICES_LIST, dataSourcePeopleDB, query, pageSize, rowColumnsIdentifiersFEServices, getRowActions());
        		
        		List<String> visibleColumnsNamesFEServices = new ArrayList<String>();
        		visibleColumnsNamesFEServices.add("comune");
        		visibleColumnsNamesFEServices.add("attivita");
        		visibleColumnsNamesFEServices.add("sottoattivita");
        		visibleColumnsNamesFEServices.add("nome");
        		visibleColumnsNamesFEServices.add("value");
        		visibleColumnsNamesFEServices.add("log.value");
        		pagedListHolderFEServices.setVisibleColumnsNames(visibleColumnsNamesFEServices);
        		        		
        		feServiceRegistration.addPagedListHolder(pagedListHolderFEServices);
    			
    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {

    		feServiceRegistration = (FEServiceRegistration)request.getSession().getAttribute("feServiceRegistration");
    		processListHoldersRequests(request.getQueryString(), feServiceRegistration);
    		
    	}

    	try {
			applyColumnSorting(request, feServiceRegistration);
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
        		feServiceRegistration.getPagedListHolder("feServicesList").applyFilters(appliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}
		
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("feNodesList", getFeNodesList());
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("feServiceRegistration", feServiceRegistration);

    	this.setPageInfo(model, "feservices.list.title", null, "feService");
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	return getStaticProperty("feservices.listAndAdd.view");
    	
    }
    

    @RequestMapping(value = "/elenco.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("feServiceRegistration") FEServiceRegistration feServiceRegistration, 
    		BindingResult result, 
    		HttpServletRequest request) {

    	boolean isRegisterInRequest = isParamInRequest(request, "registerFeService");
    	
    	if (isRegisterInRequest) {
        	validator.validate(feServiceRegistration, result);
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
				feServiceRegistration.getPagedListHolder(Constants.PagedListHoldersIds.FE_SERVICES_LIST).applyFilters(updatedAppliedFilters);
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
				feServiceRegistration.getPagedListHolder(Constants.PagedListHoldersIds.FE_SERVICES_LIST).removeFilters();
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
    		processListHoldersRequests(request, feServiceRegistration, model);
    		savePageSizeInSession(feServiceRegistration, request);
    		
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:/ServiziFe/conferma.do";
    		}
    	}
    	
    	if (isRegisterInRequest && !result.hasErrors()) {
    		FeServiceRegister feServiceRegister = new FeServiceRegister();
    		Vector<ObjectError> errors = feServiceRegister.registerService(dataSourcePeopleDB, 
    				feServiceRegistration);
    		if (!errors.isEmpty()) {
    			this.validator.addErrors(feServiceRegistration, result, errors);
    		}
    		if (!result.hasErrors()) {
    			feServiceRegistration.getPagedListHolder(Constants.PagedListHoldersIds.FE_SERVICES_LIST).update();
    			feServiceRegistration.clear();
        		return  "redirect:elenco.do";
    		}
        	else {

            	model.put("includeTopbarLinks", true);
            	
            	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
            	
            	setRowsPerPageDefaultModelAttributes(model);
            	
            	this.setPageInfo(model, "feservices.list.title", null, "feService");
        		
        		return getStaticProperty("feservices.listAndAdd.view");
        	}
    	}
    	else {

        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");

        	this.setPageInfo(model, "feservices.list.title", null, "feService");
        	
        	setRowsPerPageDefaultModelAttributes(model);
    		
    		return getStaticProperty("feservices.listAndAdd.view");
    	}
    }
    
	/**
	 * @param feServiceRegistration
	 * @param request
	 */
	private void savePageSizeInSession(FEServiceRegistration feServiceRegistration, HttpServletRequest request) {
		int pageSize = feServiceRegistration.getPagedListHolder(Constants.PagedListHoldersIds.FE_SERVICES_LIST).getPageSize();
		request.getSession().setAttribute("feServiceRegistrationPageSize", pageSize);
	}

    @RequestMapping(value = "/modifica.do", method = RequestMethod.GET)
    public String setupEditForm(@RequestParam String action, @RequestParam String id, 
    		@RequestParam String plhId, ModelMap model, HttpServletRequest request) {

    	FEServiceRegistration feServiceRegistration = null;
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Action param value = '" + action + "'.");
    		logger.debug("Id param value = '" + id + "'.");
    		logger.debug("Action param value = '" + plhId + "'.");
    	}
    	
    	if (request.getSession().getAttribute("feServiceRegistration") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("modifica.do")) {
    		feServiceRegistration = new FEServiceRegistration();
    	}
    	else {

    		feServiceRegistration = (FEServiceRegistration)request.getSession().getAttribute("feServiceRegistration");
    		for(Object row : feServiceRegistration.getPagedListHolder(plhId).getPageList()) {
				Map<String, Object> mappedRow = null;
    			if (row instanceof EditableRow) {
    				EditableRow editableRow = (EditableRow)row;
    				mappedRow = editableRow.getRow();
    			}
    			else {
    				mappedRow = (Map<String, Object>)row;
    			}
    			if (mappedRow != null) {
//    				if (String.valueOf(mappedRow.get("id")).equalsIgnoreCase(id)) {
//    					beService.setLogicalServiceName((String)mappedRow.get("nodobe"));
//    					beService.setBackEndURL((String)mappedRow.get("reference"));
//    					beService.setTransportEnvelopeEnabled(CastUtils.integerToBoolean((Integer)mappedRow.get("useenvelope")));
//    					beService.setDelegationControlForbidden(CastUtils.integerToBoolean((Integer)mappedRow.get("disablecheckdelegate")));
//    					beService.setServiceId(((Integer)mappedRow.get("id")).intValue());
//    					break;
//    				}
    			}
    		}
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	
    	model.addAttribute("feServiceRegistration", feServiceRegistration);

    	this.setPageInfo(model, "feservice.details.title", null, "feService");
    	
    	return getStaticProperty("feservices.serviceDetails.view");
    	
    }

    @RequestMapping(value = "/modifica.do", method = RequestMethod.POST)
    public String processEditSubmit(ModelMap model, @ModelAttribute("feServiceRegistration") FEServiceRegistration feServiceRegistration, BindingResult result, 
    		HttpServletRequest request) {

    	if (isParamInRequest(request, "updateBeService")) {
        	validator.validate(feServiceRegistration, result);
    	}

    	if (result.hasErrors()) {

        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
        	
        	model.addAttribute("feServiceRegistration", feServiceRegistration);

        	this.setPageInfo(model, "feservice.details.title", null, "feService");
        	
    		return getStaticProperty("feservices.serviceDetails.view");
    	}
    	else {
    		
    		//Save to db and update paged list holder page
    		
    		feServiceRegistration.clear();
    		
    		return  "redirect:elenco.do";
    	}
    	
    }
    
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/feservices/sidebar.jsp");
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model, HttpServletRequest request) {
    	
    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		ProcessActionDataHolder processActionDataHolder = this.popProcessActionData(request);

    		EditableRowInputData editableRowInputData = processActionDataHolder.getEditableRowInputData(); 

    		//cancellazione confermata
    		String serviceId = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
    		deleteFeService(serviceId);
 
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

    	filters.add(getNomeBEFilter());
    	filters.add(getAttivitaFilter());
    	filters.add(getSottoAttivitaFilter());
    	filters.add(getNomeServizioFilter());
    	filters.add(getStatoFilter());
    	filters.add(getLogFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }
    
    private IFilter getNomeBEFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Comune", "comune", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    
    
    private IFilter getAttivitaFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Attività", "attivita", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    
    private IFilter getSottoAttivitaFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Sottoattività", "sottoattivita", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    private IFilter getNomeServizioFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Nome", "nome", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getStatoFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Stato", "stat.value", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getLogFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Log", "lg.value", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    
    
    
    
    
    private List<Command> getRowActions() {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new InputCommand("deleteFeService", "deleteFeService", null, 
    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
    	result.add(new LinkCommand("editFeService", "dettaglio.do", null, 
    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
    	result.add(new LinkCommand("viewAuditConversations", "viewAuditConversations.do", null, 
    			"auditConversations.png", "auditConversations-dis.png", AbstractCommand.CommandActions.viewAuditConversations));

    	
    	return result;
    	
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
		
		if (action.equals(AbstractCommand.CommandActions.delete)
				&& ((Boolean) request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED))) {
			if (logger.isDebugEnabled()) {
				logger.debug("Delete confirmation required, no action.");
			}
		} else {
			String serviceId = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
			deleteFeService(serviceId);
		}


		
	}    
    
	/**
	 * @param pagedListHolderId
	 * @param action
	 * @param editableRowInputData
	 * @param request
	 */
	private void deleteFeService(String serviceId) {
		
		/* delete */
		
        Connection connection = null;
        Statement stmt = null;
        
        try {
			connection = dataSourcePeopleDB.getConnection();
            stmt = connection.createStatement();

        	// Determina l'url dell'FEService ed il package
            String query = "SELECT fenode.reference, fenode.codicecomune, service.package FROM service, fenode" 
            	+ " WHERE service.id = " + serviceId
            	+ " AND fenode.id = service.nodeid";            
            
            ResultSet rs = stmt.executeQuery(query);            
            if (!rs.next()) {
                logger.error("Il Servizio &egrave; inesistente.");
            }            
            String feServiceURL = rs.getString(1);
            String codiceComune = rs.getString(2);
            String _package = rs.getString(3);
            rs.close();
            
            // Registra le modifiche sul nodo di Front-end
            try {
                FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
                FEInterface feInterface = locator.getFEInterface(new URL(feServiceURL));
                if (!feInterface.deleteServiceByPackage(codiceComune, _package))
                    throw new Exception("FEService non riesce ad eliminare il servizio.");
            } catch (Exception e) {
            	logger.error("Errore durante l'eliminazione del servizio dal Front-End", e);
            }
            
            deleteFeServiceFromDB(connection, serviceId);

        } catch (SQLException e) {
        	logger.error("Errore nell'utilizzo del DB", e);
        }      
        finally {
			try {
				if (stmt != null) stmt.close();
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {

			}
		}
		
	}
	
	
	private void deleteFeServiceFromDB(Connection connection, String idService)
			throws SQLException {
		
		PreparedStatement preparedStatement = null;
		PreparedStatement deleteBeServicesPreparedStatement = null;
		Statement statement = null;
		ResultSet resultSet = null;

		String feServiceNodeIdQuery = "select nodeid from service where id = ?";
		
		String associatedBeServicesQuery = new StringBuilder().append("select count(svc.id), ref.value from service svc ")
			.append("join reference ref on ref.serviceid = svc.id ")
			.append("where svc.nodeid = (select svc1.nodeid from service svc1 where svc1.id = ?) and ref.value in (select ")
			.append("ref1.value from reference ref1 where ref1.serviceid = ?) ")
			.append("group by 2").toString();
		
		String deleteBeServiceQuery = "delete from benode where nodobe = ? and nodeid = ?";
		
		try {
			// Elimina il servizio
			connection.setAutoCommit(false);
			
			statement = connection.createStatement();
			
			// Recupera il nodo a cui è associato il servizio
			preparedStatement = connection.prepareStatement(feServiceNodeIdQuery);
			preparedStatement.setInt(1, Integer.parseInt(idService));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int serviceNodeId = resultSet.getInt(1);
				
				// Verifica se ci sono servizi di back end usati solo da questo servizio
				preparedStatement = connection.prepareStatement(associatedBeServicesQuery);
				preparedStatement.setInt(1, Integer.parseInt(idService));
				preparedStatement.setInt(2, Integer.parseInt(idService));
				resultSet = preparedStatement.executeQuery();
				int beServicesToDelete = 0;
				while(resultSet.next()) {
					if (resultSet.getInt(1) == 1) {
						if (deleteBeServicesPreparedStatement == null) {
							deleteBeServicesPreparedStatement = connection.prepareStatement(deleteBeServiceQuery);
						}
						deleteBeServicesPreparedStatement.setString(1, resultSet.getString(2));
						deleteBeServicesPreparedStatement.setInt(2, serviceNodeId);
						deleteBeServicesPreparedStatement.addBatch();
						beServicesToDelete++;
					}
				}
				int[] deletedBeServices = new int[0];
				if (deleteBeServicesPreparedStatement != null) {
					deletedBeServices = deleteBeServicesPreparedStatement.executeBatch();
				}
				if (beServicesToDelete == deletedBeServices.length) {
					logger.info("Deleted " + deletedBeServices.length + " be services for fe service " + idService + ".");
				} else {
					logger.info("Error while deleting be services for fe service " + idService + ".");
				}
			}

			// configuration
			statement.executeUpdate("DELETE FROM configuration WHERE serviceid = " + idService);

			// reference
			statement.executeUpdate("DELETE FROM reference WHERE serviceid = " + idService);

			// service
			statement.executeUpdate("DELETE FROM service WHERE id = " + idService);

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				logger.error("Impossibile effettuare il roll-back");
			}
			throw ex;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (deleteBeServicesPreparedStatement != null) {
				deleteBeServicesPreparedStatement.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
	} 
	
	private List<FENodeListElement> getFeNodesList() {

		String queryNodesList = "SELECT id, comune FROM fenode";

		List<FENodeListElement> result = new ArrayList<FENodeListElement>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryNodesList);
			while(resultSet.next()) {
				result.add(new FENodeListElement(resultSet.getString(1), resultSet.getString(2)));
			}
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
	
}

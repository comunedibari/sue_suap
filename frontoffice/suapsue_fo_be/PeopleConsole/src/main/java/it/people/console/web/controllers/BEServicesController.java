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
import it.people.console.domain.BEService;
import it.people.console.domain.PairElement;
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
import it.people.console.persistence.dao.JDBCUtils;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.core.EditableRow;
import it.people.console.persistence.jdbc.support.BooleanConverter;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.security.InputCommand;
import it.people.console.security.LinkCommand;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.web.controllers.validator.BeServiceValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 29/nov/2010 22.12.30
 *
 */
@Controller
@RequestMapping("/ServiziBe")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "beService", "feNodesList", Constants.ControllerUtils.APPLIED_FILTERS_KEY})
public class BEServicesController extends AbstractListableController {

	private BeServiceValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
//	private String query = "SELECT id, nodeid 'Nodo', nodobe 'Servizio BE', reference 'URL BE', useenvelope 'Utilizza busta', disablecheckdelegate 'Inibizione controllo deleghe' FROM benode";

	private String query = "SELECT be.id, be.nodeid, fe.comune 'Comune', fe.nodofe 'Nodo', be.nodobe 'Servizio BE', be.reference 'URL BE', be.useenvelope 'Utilizza busta', be.disablecheckdelegate 'Inibizione controllo deleghe' FROM benode be, fenode fe where fe.id = be.nodeid";
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	@Autowired
	public BEServicesController(BeServiceValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("beService");
	}
	
    @RequestMapping(value = "/elenco.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	int pageSize = 10;
    	if (request.getSession().getAttribute("beServicePageSize") != null) {
    		pageSize = (Integer) request.getSession().getAttribute("beServicePageSize"); 
    	}
    	
    	BEService beService = null;
    	
    	if (request.getSession().getAttribute("beService") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("ServiziBe/elenco.do")) {
    		beService = new BEService();

        	try {
        		
        		List<String> rowColumnsIdentifiersBEServices = new ArrayList<String>();
        		rowColumnsIdentifiersBEServices.add("id");

        		ILazyPagedListHolder pagedListHolderBEServices = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.BE_SERVICES_LIST, dataSourcePeopleDB, query, pageSize, rowColumnsIdentifiersBEServices, getRowActions());
        		
        		List<String> visibleColumnsNamesBEServices = new ArrayList<String>();
        		visibleColumnsNamesBEServices.add("comune");
        		visibleColumnsNamesBEServices.add("nodofe");
        		visibleColumnsNamesBEServices.add("nodobe");
        		visibleColumnsNamesBEServices.add("reference");
        		visibleColumnsNamesBEServices.add("useenvelope");
        		visibleColumnsNamesBEServices.add("disablecheckdelegate");
        		pagedListHolderBEServices.setVisibleColumnsNames(visibleColumnsNamesBEServices);
        		
        		Map<String, Decodable> converters = new HashMap<String, Decodable>();
        		converters.put("useenvelope", new BooleanConverter("Sì", "No"));
        		converters.put("disablecheckdelegate", new BooleanConverter("Sì", "No"));
//        		converters.put("nodeid", new NodeNameConverter(dataSourcePeopleDB));
        		pagedListHolderBEServices.setConverters(converters);
        		
        		beService.addPagedListHolder(pagedListHolderBEServices);
    			
    		} catch (PagedListHoldersCacheException e) {
				logger.error("Error while reading be nodes data.", e);
				this.putLastExceptioninSession(request, e);
				return  "redirect:/error.do";
    		} catch (LazyPagedListHolderException e) {
				logger.error("Error while reading be nodes data.", e);
				this.putLastExceptioninSession(request, e);
				return  "redirect:/error.do";
    		} catch (Exception e) {
				logger.error("Error while reading be nodes data.", e);
				this.putLastExceptioninSession(request, e);
				return  "redirect:/error.do";
			}
        	
    	}
    	else {

    		beService = (BEService)request.getSession().getAttribute("beService");
    		processListHoldersRequests(request.getQueryString(), beService);
    		
    	}

    	try {
			applyColumnSorting(request, beService);
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
        		beService.getPagedListHolder("beServicesList").applyFilters(appliedFilters);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters applied.");
        	}
    	}
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);

    	model.addAttribute("feNodesList", getFeNodesList());
    	
    	model.addAttribute("beService", beService);
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "beservices.list.title", null, "beServices");
    	
    	return getStaticProperty("beservices.listAndAdd.view");
    	
    }
    

    @RequestMapping(value = "/elenco.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("beService") BEService beService, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isSaveInRequest = isParamInRequest(request, "saveNewBeService");
    	
    	if (isSaveInRequest) {
        	validator.validate(beService, result);
    		if (!result.hasErrors()) {
        		saveBeService(beService, result);
        		beService.getPagedListHolder(Constants.PagedListHoldersIds.BE_SERVICES_LIST).update();
        		beService.clear();
    		}
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
				beService.getPagedListHolder(Constants.PagedListHoldersIds.BE_SERVICES_LIST).applyFilters(updatedAppliedFilters);
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
				beService.getPagedListHolder(Constants.PagedListHoldersIds.BE_SERVICES_LIST).removeFilters();
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
    		processListHoldersRequests(request, beService, model);
    		savePageSizeInSession(beService, request);
    		
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:/ServiziBe/conferma.do";
    		} else {
    			Object beDeleteError = request.getAttribute("BE_DELETE_ERROR");
    			if (beDeleteError != null && (Boolean)beDeleteError) {
    				return "redirect:error.do";
    			}
    			
    		}
    	}
    	
    	if (isSaveInRequest && !result.hasErrors()) {
    		return  "redirect:elenco.do";
    	}
    	else {

        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
        	
        	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
        	
        	model.addAttribute("beService", beService);
        	
        	setRowsPerPageDefaultModelAttributes(model);
        	
        	this.setPageInfo(model, "beservices.list.title", null, "beServices");
        	
    		return getStaticProperty("beservices.listAndAdd.view");
    	}
    }

	/**
	 * @param beService
	 * @param request
	 */
	private void savePageSizeInSession(BEService beService, HttpServletRequest request) {
		int pageSize = beService.getPagedListHolder(Constants.PagedListHoldersIds.BE_SERVICES_LIST).getPageSize();
		request.getSession().setAttribute("beServicePageSize", pageSize);
	}
	
    @RequestMapping(value = "/modifica.do", method = RequestMethod.GET)
    public String setupEditForm(@RequestParam String action, @RequestParam String id, 
    		@RequestParam String plhId, ModelMap model, HttpServletRequest request) {

    	BEService beService = null;
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("Action param value = '" + action + "'.");
    		logger.debug("Id param value = '" + id + "'.");
    		logger.debug("Action param value = '" + plhId + "'.");
    	}
    	
    	if (request.getSession().getAttribute("beService") == null) {
    		beService = new BEService();
    	}
    	else {

    		beService = (BEService)request.getSession().getAttribute("beService");
    		for(Object row : beService.getPagedListHolder(plhId).getPageList()) {
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
    					beService.setSelectedNodeId(new Long(((Integer)mappedRow.get("nodeid")).intValue()));
    					beService.setLogicalServiceName((String)mappedRow.get("nodobe"));
    					beService.setBackEndURL((String)mappedRow.get("reference"));
    					beService.setTransportEnvelopeEnabled(CastUtils.integerToBoolean((Integer)mappedRow.get("useenvelope")));
    					beService.setDelegationControlForbidden(CastUtils.integerToBoolean((Integer)mappedRow.get("disablecheckdelegate")));
    					beService.setId(new Long(((Integer)mappedRow.get("id")).intValue()));
    					break;
    				}
    			}
    		}
    		
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	
    	model.addAttribute("beService", beService);
    	
    	this.setPageInfo(model, "beservices.details.title", null, "beServices");
    	
    	return getStaticProperty("beservices.serviceDetails.view");
    	
    }

    @RequestMapping(value = "/modifica.do", method = RequestMethod.POST)
    public String processEditSubmit(ModelMap model, @ModelAttribute("beService") BEService beService, BindingResult result, 
    		HttpServletRequest request) {

    	
    	if (isParamInRequest(request, "updateBeService")) {
        	validator.validateUpdate(beService, result);
    	}

    	if (result.hasErrors()) {
        	model.put("includeTopbarLinks", true);
        	
        	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
        	
        	model.addAttribute("beService", beService);
        	
        	this.setPageInfo(model, "beservices.details.title", null, "beServices");
    		
    		return getStaticProperty("beservices.serviceDetails.view");
    	}
    	else {
    		
    		updateBeService(beService, result);    		
    		beService.clear();
    		beService.getPagedListHolder(Constants.PagedListHoldersIds.BE_SERVICES_LIST).update();
    		
    		return  "redirect:elenco.do";
    	}
    	
    }
    
    
    @RequestMapping(value = "/error.do", method = RequestMethod.GET)
    public String setupError(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
    	model.put("message", this.getProperty("error.beservices.connected"));
    	
    	return getStaticProperty("error.view");
    	
    }
    @RequestMapping(value = "/error.do", method = RequestMethod.POST)
    public String processError(ModelMap model, HttpServletRequest request) {
    	
    	return "redirect:elenco.do";
    	
    }
    
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {
    	
    	model.put("includeTopbarLinks", true);    	
    	model.put("sidebar", "/WEB-INF/jsp/beservices/sidebar.jsp");
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
    		String idBE = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
    		boolean success = deleteBeService(idBE);
    		if (!success) {
    			return "redirect:error.do";
    		}
    			
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

    	filters.add(getNomeComuneFilter());
    	filters.add(getNomeNodoFilter());
    	filters.add(getNomeBEFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }

    private IFilter getNomeComuneFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Comune", "fe.comune", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getNomeNodoFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Nodo", "fe.nodofe", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getNomeBEFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Servizio BE", "nodobe", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private List<Command> getRowActions() {
    	
    	List<Command> result = new ArrayList<Command>();
    	
    	result.add(new InputCommand("deleteBeService", "deleteBeService", null, 
    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
    	result.add(new LinkCommand("editBeService", "modifica.do", null, 
    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
    	
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
		
		if (action.equals(AbstractCommand.CommandActions.delete)) {
			if ((Boolean) request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Delete confirmation required, no action.");
				}
			} else {
	    		String idBE = String.valueOf(editableRowInputData.getRowIdentifiers().get("id"));
				boolean success = deleteBeService(idBE);
	    		if (!success) {
	    			request.setAttribute("BE_DELETE_ERROR",true);
	    		}
			}
		}
	}    
    
	
	/**
	 * @param idBE
	 */
	private boolean deleteBeService(String idBE) {
		
		boolean success = true;

		/* delete */
		Connection connection = null;
		Statement stmt = null;

		try {
			connection = dataSourcePeopleDB.getConnection();
			stmt = connection.createStatement();
			
	    	// Verifica che il back-end non sia utilizzato da un servizio
			String query = "SELECT COUNT(*) FROM reference, benode, service" 
				+ " WHERE reference.value = benode.nodobe "
				+ " AND service.id = serviceid "
				+ " AND benode.nodeid = service.nodeid "
				+ " AND benode.id = " +idBE;
			
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int foundService = rs.getInt(1);
			if (foundService > 0) {
				logger.debug("Impossibile eliminare un Back-end collegato a servizi.");
				success = false;
			}

			else {
				// Elimina il back-end
				String deleteQuery = "DELETE FROM benode WHERE id = " + idBE;
				stmt.executeUpdate(deleteQuery);
			}
			
		} catch (SQLException e) {
			try {
				connection.rollback();
				success = false;

			} catch(SQLException e1) {
				logger.error("Impossibile effettuare il roll-back");
				success = false;
			}
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
		
		return success;
			
	}  
	
	private void saveBeService(BEService beService, BindingResult result) {

        String nome = beService.getLogicalServiceName();
        String reference = beService.getBackEndURL();
        int useEnvelope = (beService.isTransportEnvelopeEnabled() ? 1 : 0);
        int disableCheckDelegate = (beService.isDelegationControlForbidden() ? 1 : 0);        
        
        Connection connection = null;
        Statement stmt = null;
        
        try {
            connection = dataSourcePeopleDB.getConnection();
            
            // Non è consentito inserire backend con lo stesso nome logico 
            String sql = "SELECT * FROM benode WHERE nodobe = '" + JDBCUtils.sanitizeArgument(nome) + "' and nodeid = " + beService.getSelectedNodeId();
            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()) {
            	ObjectError error = new ObjectError("error", 
            			new String[] {"error.beservices.listAndAdd.service.exists"}, null, null);
            	result.addError(error);
            }
            res.close();            
            
            String query = "INSERT INTO benode (nodeid, nodobe, reference, useenvelope, disablecheckdelegate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            int i = 0;
            ps.setInt(++i, beService.getSelectedNodeId().intValue());
            ps.setString(++i, nome);
            ps.setString(++i, reference);
            ps.setInt(++i, useEnvelope);
            ps.setInt(++i, disableCheckDelegate);
            ps.execute();
            
        } catch (Exception e) {
        	ObjectError error = new ObjectError("error", 
        			new String[] {"error.beservices.listAndAdd.service.dbError"}, null, null);
        	result.addError(error);
        } finally {
        	if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }        
		
	}

	
	/**
	 * @param beService
	 * @param result
	 */
	private void updateBeService(BEService beService, BindingResult result) {

        String nome = beService.getLogicalServiceName();
        String reference = beService.getBackEndURL();
        int useEnvelope = (beService.isTransportEnvelopeEnabled() ? 1 : 0);
        int disableCheckDelegate = (beService.isDelegationControlForbidden() ? 1 : 0);        
        
        Connection connection = null;
        Statement stmt = null;
        
        try {
            connection = dataSourcePeopleDB.getConnection();
            
            String beNodeExistsQuery = "SELECT * FROM benode WHERE id = " + beService.getId() + " and nodeid = " + beService.getSelectedNodeId();
            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(beNodeExistsQuery);
            if(!res.next()) {
            	ObjectError error = new ObjectError("error", 
            			new String[] {"error.beservices.listAndAdd.service.dbError"}, null, null);
            	result.addError(error);
            }
            res.close();
            
            String updateBeNodeQuery = "update benode set nodobe = ?, reference = ?, useenvelope = ?, disablecheckdelegate = ? where id = ? and nodeid = ?";
            PreparedStatement ps = connection.prepareStatement(updateBeNodeQuery);

            ps.setString(1, beService.getLogicalServiceName());
            ps.setString(2, beService.getBackEndURL());
            ps.setInt(3, useEnvelope);
            ps.setInt(4, disableCheckDelegate);
            ps.setLong(5, beService.getId());
            ps.setLong(6, beService.getSelectedNodeId());
            ps.execute();
            
        } catch (Exception e) {
        	ObjectError error = new ObjectError("error", 
        			new String[] {"error.beservices.listAndAdd.service.dbError"}, null, null);
        	result.addError(error);
        } finally {
        	if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }        
		
	}
	
	private List<PairElement<String, String>> getFeNodesList() {

		String queryNodesList = "SELECT id, nodofe FROM fenode";

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
	
}

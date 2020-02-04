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
package it.people.console.web.controllers.users;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.ColumnsFilters;
import it.people.console.beans.Option;
import it.people.console.beans.support.IFilter;
import it.people.console.beans.support.IFilters;
import it.people.console.beans.support.ListColumnFilter;
import it.people.console.beans.support.TextColumnFilter;
import it.people.console.domain.PairElement;
import it.people.console.domain.TripleElement;
import it.people.console.domain.UserAccount;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.dto.ProcessActionDataHolder;
import it.people.console.dto.UserAccountDTO;
import it.people.console.enumerations.EqualityOperators;
import it.people.console.enumerations.IOperatorsEnum;
import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.EditableRowSelect;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.security.AbstractCommand;
import it.people.console.utils.CastUtils;
import it.people.console.utils.Constants;
import it.people.console.web.client.exceptions.FeServiceReferenceException;
import it.people.console.web.controllers.validator.AccountsValidator;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.WebUtils;
import it.people.feservice.FEInterface;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/giu/2011 11.45.25
 *
 */
@Controller
@RequestMapping("/Amministrazione/Utenti")
@SessionAttributes({Constants.ControllerUtils.DETAILS_STATUSES_KEY, "account"})
public class AccountsController extends AbstractListableController {

	private AccountsValidator validator;

	@Autowired
	private DataSource dataSourcePeopleDB;
	
	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	private String query = "select users.id, users.tax_code 'Codice fiscale', users.email 'E-Mail', users.state, states.label 'Stato', 'Ruoli' 'Ruoli' from pc_users users join pc_states states on states.id = users.state";
	
	private Map<String, String> detailsStatuses = new HashMap<String, String>();
	
	private Collection<TripleElement<String, String, String>> rolesList = null;

	private Collection<PairElement<String, String>> nodesList = null;

	private Collection<PairElement<String, String>> feServicesList = null;

	private Collection<PairElement<String, String>> beServicesList = null;
	
	@Autowired
	public AccountsController(AccountsValidator validator) {
		this.validator = validator;
		this.setCommandObjectName("account");
	}
	
    @RequestMapping(value = "/accounts.do", method = RequestMethod.GET)
    public String setupForm(ModelMap model, HttpServletRequest request) {

    	UserAccount account = null;
    	
    	if (request.getSession().getAttribute("account") == null || 
    			!WebUtils.getReferer(request).toLowerCase().contains("accounts.do")) {
    		account = new UserAccount();

        	try {
        		
        		List<String> rowColumnsIdentifiersAccounts = new ArrayList<String>();
        		rowColumnsIdentifiersAccounts.add("id");

        		List<String> editableRowColumnsAccounts = new ArrayList<String>();
        		editableRowColumnsAccounts.add("label");
        		
        		
        		ILazyPagedListHolder pagedListHolderAccounts = LazyPagedListHolderFactory.getLazyPagedListHolder(
    					Constants.PagedListHoldersIds.ACCOUNTS_LIST, dataSourcePeopleDB, query, 10, rowColumnsIdentifiersAccounts, editableRowColumnsAccounts, false);
        		
        		List<String> visibleColumnsNamesAccounts = new ArrayList<String>();
        		visibleColumnsNamesAccounts.add("tax_code");
        		visibleColumnsNamesAccounts.add("email");
        		visibleColumnsNamesAccounts.add("label");
        		visibleColumnsNamesAccounts.add("Ruoli");
        		pagedListHolderAccounts.setVisibleColumnsNames(visibleColumnsNamesAccounts);

        		Map<String, String> columnsQueries = new HashMap<String, String>();
        		columnsQueries.put("Ruoli", "select authCat.label from pc_authorities_catalog authCat join pc_users_authorities userAuths on userAuths.authorityRef = authCat.id join pc_users users on users.id = userAuths.userRef where users.id = ?");
        		pagedListHolderAccounts.setColumnsQueries(columnsQueries);
        		
        		Map<String, Object> statesEditableRowModelers = new HashMap<String, Object>();
        		statesEditableRowModelers.put("label", new EditableRowSelect(dataSourcePeopleDB, 
        				"SELECT id, label FROM pc_states", null));
        		pagedListHolderAccounts.setEditableRowModelers(statesEditableRowModelers);
        		
        		account.addPagedListHolder(pagedListHolderAccounts);
    			
    		} catch (PagedListHoldersCacheException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
    	}
    	else {

    		account = (UserAccount)request.getSession().getAttribute("account");
    		processListHoldersRequests(request.getQueryString(), account);
    		
    	}

    	try {
			applyColumnSorting(request, account);
		} catch (LazyPagedListHolderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("account", account);

		if (rolesList == null) {
			rolesList = this.getAllRoles();
		}

		if (nodesList == null) {
			nodesList = new ArrayList<PairElement<String, String>>();
			nodesList.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_VALUE), "Qualsiasi"));
			nodesList.addAll(this.persistenceBroker.getRegisteredNodes());
		}

		if (feServicesList == null) {
			feServicesList = new ArrayList<PairElement<String, String>>();
			feServicesList.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_STRING_VALUE), "Qualsiasi"));
			feServicesList.addAll(this.persistenceBroker.getRegisteredFEServices());
		}
		
		if (beServicesList == null) {
			beServicesList = new ArrayList<PairElement<String, String>>();
			beServicesList.add(new PairElement<String, String>(String.valueOf(Constants.UNBOUND_STRING_VALUE), "Qualsiasi"));
			beServicesList.addAll(this.persistenceBroker.getRegisteredBEServices());
		}
		
    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("nodesList", nodesList);
    	model.addAttribute("feServicesList", feServicesList);
    	model.addAttribute("beServicesList", beServicesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);
    	
    	this.setPageInfo(model, "admin.accounts.listAndAdd.title", null, "accounts");

    	return getStaticProperty("accounts.listAndAdd.view");
    	
    }

    @RequestMapping(value = "/accounts.do", method = RequestMethod.POST)
    public String processSubmit(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("account") UserAccount account, BindingResult result, 
    		HttpServletRequest request) {

    	boolean isSaveInRequest = isParamInRequest(request, "saveNewAccount");
    	    	
    	if (isSaveInRequest) {
        	validator.validate(account, result);
        	validator.validateRoles(account, result, rolesList);
        	if (this.persistenceBroker.accountExists(account.getTaxCode())) {
        		FieldError error = new FieldError("account", "taxCode", account.getTaxCode(), 
        				false,
        				new String[] {"error.admin.accounts.add.userAccountExists"}, 
        				new String[] {account.getTaxCode()}, "Account esistente.");
        		result.addError(error);
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
				account.getPagedListHolder(Constants.PagedListHoldersIds.ACCOUNTS_LIST).applyFilters(updatedAppliedFilters);
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
				account.getPagedListHolder(Constants.PagedListHoldersIds.ACCOUNTS_LIST).removeFilters();
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if (logger.isDebugEnabled()) {
        		logger.debug("Active filters cleared.");
        	}
    	}
    	
    	if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
    		processListHoldersRequests(request, account, model);
    		Object requestDelete = request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
    		if (requestDelete != null && (Boolean)requestDelete) {
    			return "redirect:conferma.do";
    		}
    	}
    	
    	if (isSaveInRequest && !result.hasErrors()) {
    		saveAccount(account);
    		account.clear();
    		account.getPagedListHolder(Constants.PagedListHoldersIds.ACCOUNTS_LIST).update();
    	}

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("account", account);

    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("nodesList", nodesList);
    	model.addAttribute("feServicesList", feServicesList);
    	model.addAttribute("beServicesList", beServicesList);
    	
    	setRowsPerPageDefaultModelAttributes(model);

    	this.setPageInfo(model, "admin.accounts.listAndAdd.title", null, "accounts");
    	
		return getStaticProperty("accounts.listAndAdd.view");
    	
    }

    @RequestMapping(value = "/conferma.do", method = RequestMethod.GET)
    public String setupConferma(ModelMap model, HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");
    	
    	model.put("message", "Si desidera eliminare l'elemento?");
    	
    	return getStaticProperty("confirm.view");
    	
    }
    
    @RequestMapping(value = "/conferma.do", method = RequestMethod.POST)
    public String processConferma(ModelMap model, @ModelAttribute("filtersList") IFilters filtersList, 
    		@ModelAttribute("account") UserAccount account, BindingResult result, 
    		HttpServletRequest request) {

    	model.put("includeTopbarLinks", true);
    	
    	model.put("sidebar", "/WEB-INF/jsp/admin/sidebar.jsp");

    	model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY, detailsStatuses);
    	
    	model.addAttribute("account", account);

    	model.addAttribute("rolesList", rolesList);
    	model.addAttribute("nodesList", nodesList);
    	model.addAttribute("feServicesList", feServicesList);
    	model.addAttribute("beServicesList", beServicesList);
    	
    	boolean isConfirmAction = isParamInRequest(request, "confirmAction");
    	
    	if (isConfirmAction) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action confirmed.");
    		}
    		ProcessActionDataHolder processActionDataHolder = this.popProcessActionData(request);
    		int userId = Integer.parseInt((String)processActionDataHolder.getEditableRowInputData().getRowIdentifiers().get("id"));
    		try {
				this.deleteAccount(userId);
			} catch (PersistenceBrokerException e) {
				// TODO Redirect to error page
				e.printStackTrace();
			}
    	} else {
    		if (logger.isDebugEnabled()) {
    			logger.debug("Action canceled.");
    		}
    	}
    	
		return "redirect:accounts.do";
    	
    }
    
    private void deleteAccount(int userId) throws PersistenceBrokerException {
    	UserAccountDTO userAccount = persistenceBroker.getAccountData(userId);
    	if (this.isAccountPeopleAdmin(userAccount.getRoles())) {
    		deletePeopleAdministratorAccount(userAccount);
    	}
    	this.persistenceBroker.deleteAccount(userId);
    }
    
    private void deletePeopleAdministratorAccount(UserAccountDTO account) {

		Map<String, Vector<String>> feInterfacesReferences = getFeInterfacesReferencesByCommuneId(persistenceBroker, account.getAllowedPeopleAdministrationNodes());
		if (!feInterfacesReferences.isEmpty()) {

			Iterator<String> feInterfacesReferencesIterator = feInterfacesReferences.keySet().iterator();
			while(feInterfacesReferencesIterator.hasNext()) {
				String feReference = feInterfacesReferencesIterator.next();
				try {
					FEInterface feInterface = this.getFEInterface(feReference);
					feInterface.removeFromPeopleAdministrator(account.getTaxCode());
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
    
    private void saveAccount(UserAccount account) {
    	
    	String insertAccountQuery = "insert into pc_users(tax_code) values(?)";
    	String insertAccountWithReceiverFlagsQuery = "insert into pc_users(tax_code, email_receiver_types_flags) values(?, ?)";
    	String insertAccountRolesQuery = "insert into pc_users_authorities(userRef, authorityRef) values(?, ?)";
    	String insertAccountAllowedNodes = "insert into pc_users_allowed_nodes(userRef, nodeId) values(?, ?)";
    	String insertAccountAllowedFEServices = "insert into pc_users_allowed_feservices(userRef, service_pkg) values(?, ?)";
    	String insertAccountAllowedBEServices = "insert into pc_users_allowed_beservices(userRef, service_pkg) values(?, ?)";
    	String insertAccountAllowedPplAdminNodes = "insert into pc_users_allowed_ppladmin_nodes(userRef, nodeId) values(?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		boolean autoCommit = true;
		int transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
		
		
		try {
			
			connection = dataSourcePeopleDB.getConnection();
			
			autoCommit = connection.getAutoCommit();
			transactionIsolation = connection.getTransactionIsolation();
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			if (isAccountPeopleAdmin(account.getRoles())) {
				preparedStatement = connection.prepareStatement(insertAccountWithReceiverFlagsQuery, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, account.getTaxCode());
				preparedStatement.setString(2, account.getMailReceiverTypesFlags());
			} else {
				preparedStatement = connection.prepareStatement(insertAccountQuery, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, account.getTaxCode());
			}
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				int userRef = resultSet.getInt(1);
				preparedStatement = connection.prepareStatement(insertAccountRolesQuery);
				for (int index = 0; index < account.getRoles().length; index++) {
					int roleRef = Integer.valueOf(account.getRoles()[index]);
					preparedStatement.setInt(1, userRef);
					preparedStatement.setInt(2, roleRef);
					preparedStatement.executeUpdate();
				}
				saveRoleSpecificData(userRef, account, insertAccountAllowedNodes, 
						insertAccountAllowedFEServices, insertAccountAllowedBEServices, 
						insertAccountAllowedPplAdminNodes, connection);
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch(SQLException e1) {
				
			}
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
				connection.setAutoCommit(autoCommit);
				connection.setTransactionIsolation(transactionIsolation);
			} catch(SQLException e) {
				
			}
		}
    	
    }
    
    private void saveRoleSpecificData(int userRef, UserAccount account, 
    		String insertAccountAllowedNodesQuery, 
			String insertAccountAllowedFEServicesQuery, 
			String insertAccountAllowedBEServicesQuery, 
			String insertAccountAllowedPplAdminNodes, 
			Connection connection) throws SQLException {
    	
    	normalizeAllowedElements(account);
    	
    	boolean isValuesString = false;
    	
    	if (isAccountNodeAdmin(account.getRoles())) {
    		insertRoleSpecificData(insertAccountAllowedNodesQuery, userRef, 
    				CastUtils.arrayToVector(account.getAllowedNodes()), connection, isValuesString);
    	}

    	if (isAccountFEServicesAdmin(account.getRoles())) {
    		isValuesString = true;
    		insertRoleSpecificData(insertAccountAllowedFEServicesQuery, userRef, 
    				CastUtils.arrayToVector(account.getAllowedFEServices()), connection, isValuesString);
    	}

    	if (isAccountBEServicesAdmin(account.getRoles())) {
    		isValuesString = true;
    		insertRoleSpecificData(insertAccountAllowedBEServicesQuery, userRef, 
    				CastUtils.arrayToVector(account.getAllowedBEServices()), connection, isValuesString);
    	}
    	
    	if (isAccountPeopleAdmin(account.getRoles())) {

    		isValuesString = false;
    		insertRoleSpecificData(insertAccountAllowedPplAdminNodes, userRef, 
    				CastUtils.arrayToVector(account.getAllowedPeopleAdministrationNodes()), connection, isValuesString);
    		
    		Map<String, Vector<String>> feInterfacesReferences = getFeInterfacesReferencesByCommuneId(persistenceBroker, account.getAllowedPeopleAdministrationNodes());
    		if (!feInterfacesReferences.isEmpty()) {

    			String[] allowedPeopleAdministrationNodes = java.util.Arrays.copyOf(account.getAllowedPeopleAdministrationNodes(), account.getAllowedPeopleAdministrationNodes().length);
    	    	if (account.getAllowedPeopleAdministrationNodes() != null & account.getAllowedPeopleAdministrationNodes().length > 0) {
    	    		for (int index = 0; index < account.getAllowedPeopleAdministrationNodes().length; index++) {
    	    			if (account.getAllowedPeopleAdministrationNodes()[index].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_VALUE))) {
    	    				allowedPeopleAdministrationNodes = new String[] {"*"};
    	    				break;
    	    			}
    	    		}
    	    	}
    			
    			Iterator<String> feInterfacesReferencesIterator = feInterfacesReferences.keySet().iterator();
    			while(feInterfacesReferencesIterator.hasNext()) {
    				String feReference = feInterfacesReferencesIterator.next();
    				try {
						FEInterface feInterface = this.getFEInterface(feReference); 
						feInterface.setAsPeopleAdministrator(account.getTaxCode(), StringUtils.defaultString(account.getEMail()), StringUtils.defaultString(account.getFirstName()) + 
								StringUtils.defaultString(account.getLastName()), 
								allowedPeopleAdministrationNodes, account.getMailReceiverTypesFlags());
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
    	
    }
    
    /**
     * <p>If user select more than one value and select also unbound value then clear set and put only the unbound value
     * 
     * @param account
     */
    private void normalizeAllowedElements(UserAccount account) {
    	
    	if (account.getAllowedNodes() != null & account.getAllowedNodes().length > 0) {
    		for (int index = 0; index < account.getAllowedNodes().length; index++) {
    			if (account.getAllowedNodes()[index].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_VALUE))) {
    				account.setAllowedNodes(new String[] {String.valueOf(Constants.UNBOUND_VALUE)});
    				break;
    			}
    		}
    	}

    	if (account.getAllowedPeopleAdministrationNodes() != null & account.getAllowedPeopleAdministrationNodes().length > 0) {
    		for (int index = 0; index < account.getAllowedPeopleAdministrationNodes().length; index++) {
    			if (account.getAllowedPeopleAdministrationNodes()[index].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_VALUE))) {
    				account.setAllowedPeopleAdministrationNodes(new String[] {String.valueOf(Constants.UNBOUND_VALUE)});
    				break;
    			}
    		}
    	}

    	if (account.getAllowedFEServices() != null & account.getAllowedFEServices().length > 0) {
    		for (int index = 0; index < account.getAllowedFEServices().length; index++) {
    			if (account.getAllowedFEServices()[index].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_STRING_VALUE))) {
    				account.setAllowedFEServices(new String[] {String.valueOf(Constants.UNBOUND_STRING_VALUE)});
    				break;
    			}
    		}
    	}

    	if (account.getAllowedBEServices() != null & account.getAllowedBEServices().length > 0) {
    		for (int index = 0; index < account.getAllowedBEServices().length; index++) {
    			if (account.getAllowedBEServices()[index].equalsIgnoreCase(String.valueOf(Constants.UNBOUND_STRING_VALUE))) {
    				account.setAllowedBEServices(new String[] {String.valueOf(Constants.UNBOUND_STRING_VALUE)});
    				break;
    			}
    		}
    	}
    	
    }
    
    private void insertRoleSpecificData(String query, int userRef, 
    		Vector<String>  values, Connection connection, boolean isValuesString) throws SQLException {
    	
    	PreparedStatement preparedStatement = connection.prepareStatement(query);
    	for(String value : values) {
    		preparedStatement.setInt(1, userRef);
    		if (isValuesString) {
        		preparedStatement.setString(2, value);
    		} else {
        		preparedStatement.setInt(2, Integer.parseInt(value));
    		}
    		preparedStatement.addBatch();
    	}
    	int[] insertedRows = preparedStatement.executeBatch();
    	if (insertedRows.length != values.size()) {
    		throw new SQLException("Error while inserting role specific data values.");
    	}
    	preparedStatement.close();
    	
    }
        
    /**
     * @param userRoles
     * @return
     */
    private boolean isAccountNodeAdmin(String[] userRoles) {
    	return this.isAccountAssignedRole(Constants.Security.NODE_ADMIN_ROLE, userRoles);
    }

    /**
     * @param userRoles
     * @return
     */
    private boolean isAccountFEServicesAdmin(String[] userRoles) {
    	return this.isAccountAssignedRole(Constants.Security.FE_SERVICES_ADMIN_ROLE, userRoles);
    }

    /**
     * @param userRoles
     * @return
     */
    private boolean isAccountBEServicesAdmin(String[] userRoles) {
    	return this.isAccountAssignedRole(Constants.Security.BE_SERVICES_ADMIN_ROLE, userRoles);
    }

    /**
     * @param userRoles
     * @return
     */
    private boolean isAccountPeopleAdmin(String[] userRoles) {
    	return this.isAccountAssignedRole(Constants.Security.PEOPLE_ADMIN_ROLE, userRoles);
    }
    
    /**
     * @param requestedRole
     * @param assignedRoles
     * @return
     */
    private boolean isAccountAssignedRole(String requestedRole, String[] assignedRoles) {
    	
    	boolean result = false;
    	
    	String requestedRoleCode = getRequestedRoleCode(requestedRole);
    	for(int index = 0; index < assignedRoles.length; index++) {
    		if (requestedRoleCode.toLowerCase().equalsIgnoreCase(assignedRoles[index].toLowerCase())) {
    			result = true;
    			break;
    		}
    	}
    	
    	return result;
    	
    }
    
    /**
     * @param requestedRole
     * @return
     */
    private String getRequestedRoleCode(String requestedRole) {
    	
    	String result = requestedRole;
    	
    	Iterator<TripleElement<String, String, String>> rolesListIterator = rolesList.iterator();
    	while(rolesListIterator.hasNext()) {
    		TripleElement<String, String, String> role = rolesListIterator.next();
    		if (role.getKey().toLowerCase().equalsIgnoreCase(requestedRole.toLowerCase())) {
    			result = role.getValue();
    		}
    	}
    	
    	return result;
    	
    }
    
    @Override
    protected IFilters prepareFilters() {
    	    	
    	Vector<IFilter> filters = new Vector<IFilter>();

    	filters.add(getTaxCodeFilter());
    	filters.add(getEMailFilter());
    	filters.add(getStateFilter());
    	
    	IFilters result = new ColumnsFilters(filters);
    	
    	return result;
    	
    }

    private IFilter getTaxCodeFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("Codice fiscale", "tax_code", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }

    private IFilter getEMailFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(LogicalOperators.like);
    	filterAllowedOperators.add(LogicalOperators.is_null);
    	filterAllowedOperators.add(LogicalOperators.is_not_null);
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	TextColumnFilter textColumnFilter = new TextColumnFilter("E-Mail", "email", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	return textColumnFilter;
    	
    }
    
    private IFilter getStateFilter() {

    	Vector<IOperatorsEnum> filterAllowedOperators = new Vector<IOperatorsEnum>();
    	filterAllowedOperators.add(EqualityOperators.equal);
    	
    	ListColumnFilter listColumnFilter = new ListColumnFilter("Stato", "label", Types.VARCHAR, 
    			filterAllowedOperators);
    	
    	listColumnFilter.addFilterAllowedValue(new Option("Attivo", "Attivo"));
    	listColumnFilter.addFilterAllowedValue(new Option("Disabilitato", "Disabilitato"));
    	listColumnFilter.addFilterAllowedValue(new Option("Eliminato", "Eliminato"));
    	
    	return listColumnFilter;
    	
    }

    
//    private List<Command> getRowActions() {
//    	
//    	List<Command> result = new ArrayList<Command>();
//    	
//    	result.add(new InputCommand("deleteAccount", "deleteAccount", null, 
//    			"delete.png", "delete-dis.png", AbstractCommand.CommandActions.delete));
//    	result.add(new LinkCommand("editAccount", "editAccount", null, 
//    			"edit.png", "edit-dis.png", AbstractCommand.CommandActions.edit));
//    	
//    	return result;
//    	
//    }

	/**
	 * @return
	 */
	private Collection<TripleElement<String, String, String>> getAllRoles() {
		
		String query = isUserInRole(Constants.Security.ROOT_ROLE) ? 
				"select id, label, authority from pc_authorities_catalog" : 
					"select id, label, authority from pc_authorities_catalog where authority != 'CONSOLE_ADMIN'";
		
		return this.persistenceBroker.getAllRoles(query);
		
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
		
		if (action == AbstractCommand.CommandActions.delete) {
			if ((Boolean)request.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED)) {
				if (logger.isDebugEnabled()) {
					logger.debug("No confirmation required, proceeding with delete action...");
				}
			} else {
	    		int userId = Integer.parseInt((String)editableRowInputData.getRowIdentifiers().get("id"));

	    		try {
					this.deleteAccount(userId);
				} catch (PersistenceBrokerException e) {
					// TODO Redirect to error page
					e.printStackTrace();
				}
			}
		}
		
	}    
    
}

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
package it.people.console.web.controllers.validator;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.people.console.domain.TripleElement;
import it.people.console.domain.UserAccount;
import it.people.console.utils.Constants;
import it.people.console.utils.IRegExpUtils;
import it.people.console.validation.MessageSourceAwareValidator;
import it.people.console.validation.ValidationUtils;

@Service
public class AccountsValidator extends MessageSourceAwareValidator implements Validator {

	@Autowired
	private IRegExpUtils regExpUtils;
	
	private Collection<TripleElement<String, String, String>> rolesList = null;
	
	public boolean supports(Class<?> clazz) {
		return clazz == UserAccount.class;
	}

	public void validate(Object command, Errors errors) {
		
		UserAccount userAccount = (UserAccount)command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxCode", "error.field.empty", 
			new String[] {this.getProperty("admin.accounts.listAndAdd.taxCode")});
		
		if (errors.getFieldErrorCount("taxCode") == 0 && !regExpUtils.matchTaxCode(userAccount.getTaxCode())) {
			errors.rejectValue("taxCode", "error.field.malformed", 
					new String[] {this.getProperty("admin.accounts.listAndAdd.taxCode")}, "");
		}
		
		if (userAccount == null || (userAccount.getRoles() != null && userAccount.getRoles().length == 0)) {
			errors.rejectValue("roles", "error.admin.accounts.add.noSelectedRoles");
		}
		
		
	}
	
	
	public void validateRoles(Object command, Errors errors, Collection<TripleElement<String, String, String>> roles) {
		rolesList = roles;

		UserAccount userAccount = (UserAccount) command;

		if (userAccount != null && userAccount.getRoles() != null) {
			String[] userRoles = userAccount.getRoles();

			if (isAccountNodeAdmin(userRoles) && userAccount.getAllowedNodes().length == 0) {
				errors.rejectValue("roles", "error.admin.accounts.add.noSelectedNodes");
			}

			if (isAccountFEServicesAdmin(userRoles) && userAccount.getAllowedFEServices().length == 0) {
				errors.rejectValue("roles", "error.admin.accounts.add.noSelectedFEServices");
			}

			if (isAccountBEServicesAdmin(userRoles) && userAccount.getAllowedBEServices().length == 0) {
				errors.rejectValue("roles", "error.admin.accounts.add.noSelectedBEServices");
			}
		}
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

}

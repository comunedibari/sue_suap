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
package it.people.console.domain;

import org.apache.commons.lang.BooleanUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/giu/2011 12.37.33
 *
 */
public class UserAccount extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2047519716432536764L;

	private Long id;
	
	private String firstName;
	
	private String lastName;
		
	private String taxCode;
	
	private String EMail;

	private String[] roles;
	
	private String[] allowedNodes = new String[] {"*"};

	private String[] allowedFEServices = new String[] {"*"};

	private String[] allowedBEServices = new String[] {"*"};
	
	// People administrators
	
	private String[] allowedPeopleAdministrationNodes = new String[] {"*"};
	
	private boolean generalErrorEMailReceiver = false;

	private boolean sendErrorEMailReceiver = false;

	private boolean userSignallingErrorsEMailReceiver = false;

	private boolean userSuggestionsEMailReceiver = false;

	private boolean newAccreditamentoEMailReceiver = false;

	public UserAccount() {
		
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public final String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the taxCode
	 */
	public final String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode the taxCode to set
	 */
	public final void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * @return the eMail
	 */
	public final String getEMail() {
		return EMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public final void setEMail(String eMail) {
		EMail = eMail;
	}

	/**
	 * @return the roles
	 */
	public final String[] getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public final void setRoles(String[] roles) {
		this.roles = roles;
	}

	/**
	 * @return the allowedNodes
	 */
	public final String[] getAllowedNodes() {
		return this.allowedNodes;
	}

	/**
	 * @param allowedNodes the allowedNodes to set
	 */
	public final void setAllowedNodes(String[] allowedNodes) {
		this.allowedNodes = allowedNodes;
	}

	/**
	 * @return the allowedFEServices
	 */
	public final String[] getAllowedFEServices() {
		return this.allowedFEServices;
	}

	/**
	 * @param allowedFEServices the allowedFEServices to set
	 */
	public final void setAllowedFEServices(String[] allowedFEServices) {
		this.allowedFEServices = allowedFEServices;
	}

	/**
	 * @return the allowedBEServices
	 */
	public final String[] getAllowedBEServices() {
		return this.allowedBEServices;
	}

	/**
	 * @param allowedBEServices the allowedBEServices to set
	 */
	public final void setAllowedBEServices(String[] allowedBEServices) {
		this.allowedBEServices = allowedBEServices;
	}
	
	/**
	 * @return the allowedPeopleAdministrationNodes
	 */
	public final String[] getAllowedPeopleAdministrationNodes() {
		return this.allowedPeopleAdministrationNodes;
	}

	/**
	 * @param allowedPeopleAdministrationNodes the allowedPeopleAdministrationNodes to set
	 */
	public final void setAllowedPeopleAdministrationNodes(
			String[] allowedPeopleAdministrationNodes) {
		this.allowedPeopleAdministrationNodes = allowedPeopleAdministrationNodes;
	}

	/**
	 * @return the generalErrorEMailReceiver
	 */
	public final boolean isGeneralErrorEMailReceiver() {
		return this.generalErrorEMailReceiver;
	}

	/**
	 * @param generalErrorEMailReceiver the generalErrorEMailReceiver to set
	 */
	public final void setGeneralErrorEMailReceiver(boolean generalErrorEMailReceiver) {
		this.generalErrorEMailReceiver = generalErrorEMailReceiver;
	}

	/**
	 * @return the sendErrorEMailReceiver
	 */
	public final boolean isSendErrorEMailReceiver() {
		return this.sendErrorEMailReceiver;
	}

	/**
	 * @param sendErrorEMailReceiver the sendErrorEMailReceiver to set
	 */
	public final void setSendErrorEMailReceiver(boolean sendErrorEMailReceiver) {
		this.sendErrorEMailReceiver = sendErrorEMailReceiver;
	}

	/**
	 * @return the userSignallingErrorsEMailReceiver
	 */
	public final boolean isUserSignallingErrorsEMailReceiver() {
		return this.userSignallingErrorsEMailReceiver;
	}

	/**
	 * @param userSignallingErrorsEMailReceiver the userSignallingErrorsEMailReceiver to set
	 */
	public final void setUserSignallingErrorsEMailReceiver(
			boolean userSignallingErrorsEMailReceiver) {
		this.userSignallingErrorsEMailReceiver = userSignallingErrorsEMailReceiver;
	}

	/**
	 * @return the userSuggestionsEMailReceiver
	 */
	public final boolean isUserSuggestionsEMailReceiver() {
		return this.userSuggestionsEMailReceiver;
	}

	/**
	 * @param userSuggestionsEMailReceiver the userSuggestionsEMailReceiver to set
	 */
	public final void setUserSuggestionsEMailReceiver(
			boolean userSuggestionsEMailReceiver) {
		this.userSuggestionsEMailReceiver = userSuggestionsEMailReceiver;
	}

	/**
	 * @return the newAccreditamentoEMailReceiver
	 */
	public final boolean isNewAccreditamentoEMailReceiver() {
		return this.newAccreditamentoEMailReceiver;
	}

	/**
	 * @param newAccreditamentoEMailReceiver the newAccreditamentoEMailReceiver to set
	 */
	public final void setNewAccreditamentoEMailReceiver(
			boolean newAccreditamentoEMailReceiver) {
		this.newAccreditamentoEMailReceiver = newAccreditamentoEMailReceiver;
	}

	/**
	 * @return
	 */
	public final String getMailReceiverTypesFlags() {
		
		return new StringBuilder().append(String.valueOf(BooleanUtils.toInteger(this.isGeneralErrorEMailReceiver())))
			.append(String.valueOf(BooleanUtils.toInteger(this.isSendErrorEMailReceiver())))
			.append(String.valueOf(BooleanUtils.toInteger(this.isUserSignallingErrorsEMailReceiver())))
			.append(String.valueOf(BooleanUtils.toInteger(this.isUserSuggestionsEMailReceiver())))
			.append(String.valueOf(BooleanUtils.toInteger(this.isNewAccreditamentoEMailReceiver())))
			.toString();
		
	}
	
	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setId(null);
		this.setFirstName(null);
		this.setLastName(null);
		this.setEMail(null);
		this.setTaxCode(null);
		this.setRoles(null);
		this.setAllowedNodes(null);
		this.setAllowedFEServices(null);
		this.setAllowedBEServices(null);

		this.setAllowedPeopleAdministrationNodes(null);
		this.setGeneralErrorEMailReceiver(false);
		this.setSendErrorEMailReceiver(false);
		this.setUserSignallingErrorsEMailReceiver(false);
		this.setUserSuggestionsEMailReceiver(false);
		this.setNewAccreditamentoEMailReceiver(false);
		
	}
	
}

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
package it.people.console.dto;

import java.util.Vector;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/giu/2011 11.47.21
 *
 */
public class UserAccountDTO {

	private String firstName;
	
	private String lastName;
		
	private String taxCode;
	
	private String EMail;
	
	private String eMailReceiverTypesFlags;

	private Vector<String> roles;

	private Vector<String> allowedNodes;

	private Vector<String> allowedFEServices;

	private Vector<String> allowedBEServices;
	
	// People administrators
	
	private Vector<String> allowedPeopleAdministrationNodes;
	
	private boolean generalErrorEMailReceiver = false;

	private boolean sendErrorEMailReceiver = false;

	private boolean userSignallingErrorsEMailReceiver = false;

	private boolean userSuggestionsEMailReceiver = false;

	private boolean newAccreditamentoEMailReceiver = false;
	
	public UserAccountDTO() {
		this.setRoles(new Vector<String>());
		this.setAllowedNodes(new Vector<String>());
		this.setAllowedFEServices(new Vector<String>());
		this.setAllowedBEServices(new Vector<String>());
		this.setAllowedPeopleAdministrationNodes(new Vector<String>());
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
	public final Vector<String> getVectorRoles() {
		return roles;
	}

	/**
	 * @return
	 */
	public final String[] getRoles() {
		if (this.getVectorRoles() != null) {
			return this.getVectorRoles().toArray(new String[this.getVectorRoles().size()]);
		} else {
			return new String[] {};
		}
	}
	
	/**
	 * @param roles the roles to set
	 */
	public final void setRoles(Vector<String> roles) {
		this.roles = roles;
	}
	
	/**
	 * @return the eMailReceiverTypesFlags
	 */
	public final String geteMailReceiverTypesFlags() {
		return this.eMailReceiverTypesFlags;
	}

	/**
	 * @param eMailReceiverTypesFlags the eMailReceiverTypesFlags to set
	 */
	public final void seteMailReceiverTypesFlags(String eMailReceiverTypesFlags) {
		this.eMailReceiverTypesFlags = eMailReceiverTypesFlags;
		if (eMailReceiverTypesFlags != null && eMailReceiverTypesFlags.length() == 5) {
			this.setGeneralErrorEMailReceiver(Boolean.parseBoolean(eMailReceiverTypesFlags.substring(0, 1)));
			this.setSendErrorEMailReceiver(Boolean.parseBoolean(eMailReceiverTypesFlags.substring(1, 2)));
			this.setUserSignallingErrorsEMailReceiver(Boolean.parseBoolean(eMailReceiverTypesFlags.substring(2, 3)));
			this.setUserSuggestionsEMailReceiver(Boolean.parseBoolean(eMailReceiverTypesFlags.substring(3, 4)));
			this.setNewAccreditamentoEMailReceiver(Boolean.parseBoolean(eMailReceiverTypesFlags.substring(4, 5)));
		}
	}

	/**
	 * @return the allowedNodes
	 */
	public final Vector<String> getVectorAllowedNodes() {
		return this.allowedNodes;
	}

	/**
	 * @return
	 */
	public final String[] getAllowedNodes() {
		if (this.getVectorAllowedNodes() != null) {
			return this.getVectorAllowedNodes().toArray(new String[this.getVectorAllowedNodes().size()]);
		} else {
			return new String[] {};
		}
	}
	
	/**
	 * @param allowedNodes the allowedNodes to set
	 */
	public final void setAllowedNodes(Vector<String> allowedNodes) {
		this.allowedNodes = allowedNodes;
	}

	/**
	 * @return the allowedFEServices
	 */
	public final Vector<String> getVectorAllowedFEServices() {
		return this.allowedFEServices;
	}

	/**
	 * @return
	 */
	public final String[] getAllowedFEServices() {
		if (this.getVectorAllowedFEServices() != null) {
			return this.getVectorAllowedFEServices().toArray(new String[this.getVectorAllowedFEServices().size()]);
		} else {
			return new String[] {};
		}
	}
	
	/**
	 * @param allowedFEServices the allowedFEServices to set
	 */
	public final void setAllowedFEServices(Vector<String> allowedFEServices) {
		this.allowedFEServices = allowedFEServices;
	}

	/**
	 * @return the allowedBEServices
	 */
	public final Vector<String> getVectorAllowedBEServices() {
		return this.allowedBEServices;
	}

	/**
	 * @return
	 */
	public final String[] getAllowedBEServices() {
		if (this.getVectorAllowedBEServices() != null) {
			return this.getVectorAllowedBEServices().toArray(new String[this.getVectorAllowedBEServices().size()]);
		} else {
			return new String[] {};
		}
	}
	
	/**
	 * @param allowedBEServices the allowedBEServices to set
	 */
	public final void setAllowedBEServices(Vector<String> allowedBEServices) {
		this.allowedBEServices = allowedBEServices;
	}

	/**
	 * @return the allowedPeopleAdministrationNodes
	 */
	public final Vector<String> getVectorAllowedPeopleAdministrationNodes() {
		return this.allowedPeopleAdministrationNodes;
	}

	/**
	 * @return
	 */
	public final String[] getAllowedPeopleAdministrationNodes() {
		if (this.getVectorAllowedPeopleAdministrationNodes() != null) {
			return this.getVectorAllowedPeopleAdministrationNodes().toArray(new String[this.getVectorAllowedPeopleAdministrationNodes().size()]);
		} else {
			return new String[] {};
		}
	}
	
	/**
	 * @param allowedPeopleAdministrationNodes the allowedPeopleAdministrationNodes to set
	 */
	public final void setAllowedPeopleAdministrationNodes(
			Vector<String> allowedPeopleAdministrationNodes) {
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
	
}

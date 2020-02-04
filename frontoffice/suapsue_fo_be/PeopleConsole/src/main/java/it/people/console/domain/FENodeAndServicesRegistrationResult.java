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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.people.console.beans.ObjectError;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 06/feb/2011 10.01.14
 *
 */
public class FENodeAndServicesRegistrationResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8832971194856856638L;

	private boolean newNodeRegistration = false;
	
	private ObjectError newNodeRegistrationError = null;
	
	private List<String> messages = new ArrayList<String>();
	
	private List<T> servicesRegistrationResult = new ArrayList<T>();

	private List<ObjectError> registrationErrors = new ArrayList<ObjectError>();
	
	public FENodeAndServicesRegistrationResult() {
		
	}

	/**
	 * @return the newNodeRegistration
	 */
	public final boolean isNewNodeRegistration() {
		return newNodeRegistration;
	}

	/**
	 * @param newNodeRegistration the newNodeRegistration to set
	 */
	public final void setNewNodeRegistration(boolean newNodeRegistration) {
		this.newNodeRegistration = newNodeRegistration;
	}

	/**
	 * @return the newNodeRegistrationError
	 */
	public final ObjectError getNewNodeRegistrationError() {
		return newNodeRegistrationError;
	}

	/**
	 * @param newNodeRegistrationError the newNodeRegistrationError to set
	 */
	public final void setNewNodeRegistrationError(
			ObjectError newNodeRegistrationError) {
		this.newNodeRegistrationError = newNodeRegistrationError;
	}

	/**
	 * @return the servicesRegistrationResult
	 */
	public final List<T> getServicesRegistrationResult() {
		return servicesRegistrationResult;
	}

	/**
	 * @param servicesRegistrationResult the servicesRegistrationResult to set
	 */
	public final void setServicesRegistrationResult(
			List<T> servicesRegistrationResult) {
		this.servicesRegistrationResult = servicesRegistrationResult;
	}
	
	public final void addServicesRegistrationResult(T result) {
		this.getServicesRegistrationResult().add(result);
	}

	/**
	 * @return the registrationErrors
	 */
	public final List<ObjectError> getRegistrationErrors() {
		return this.registrationErrors;
	}

	/**
	 * @param registrationErrors the registrationErrors to set
	 */
	public final void setRegistrationErrors(List<ObjectError> registrationErrors) {
		this.registrationErrors = registrationErrors;
	}

	public final void addRegistrationErrors(ObjectError registrationError) {
		this.getRegistrationErrors().add(registrationError);
	}
	
	/**
	 * @return the messages
	 */
	public final List<String> getMessages() {
		return this.messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public final void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public final void addMessages(String message) {
		this.getMessages().add(message);
	}
	
}

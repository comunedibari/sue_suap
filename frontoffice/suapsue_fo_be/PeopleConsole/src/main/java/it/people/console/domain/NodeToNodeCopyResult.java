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
import java.util.Vector;

import it.people.console.beans.ObjectError;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 31/gen/2011 23.31.12
 *
 */
public class NodeToNodeCopyResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8624705173217681520L;

	private String servicePackage;
	
	private String serviceName;
	
	private String serviceActivity;
	
	private String serviceSubActivity;
	
	private List<String> messages = new ArrayList<String>();
	
	private Vector<ObjectError> errors = new Vector<ObjectError>();

	public NodeToNodeCopyResult() {
	}
	
	public NodeToNodeCopyResult(String servicePackage, Vector<ObjectError> errors) {
		this.setServicePackage(servicePackage);
		this.setErrors(errors);
	}

	/**
	 * @param servicePackage the servicePackage to set
	 */
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Vector<ObjectError> errors) {
		this.errors = errors;
	}

	/**
	 * @return the servicePackage
	 */
	public final String getServicePackage() {
		return servicePackage;
	}

	/**
	 * @return the serviceName
	 */
	public final String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceActivity
	 */
	public final String getServiceActivity() {
		return serviceActivity;
	}

	/**
	 * @param serviceActivity the serviceActivity to set
	 */
	public final void setServiceActivity(String serviceActivity) {
		this.serviceActivity = serviceActivity;
	}

	/**
	 * @return the serviceSubActivity
	 */
	public final String getServiceSubActivity() {
		return serviceSubActivity;
	}

	/**
	 * @param serviceSubActivity the serviceSubActivity to set
	 */
	public final void setServiceSubActivity(String serviceSubActivity) {
		this.serviceSubActivity = serviceSubActivity;
	}

	/**
	 * @return the errors
	 */
	public final Vector<ObjectError> getErrors() {
		return errors;
	}

	public final void addErrors(ObjectError error) {
		this.getErrors().add(error);
	}
	
	/**
	 * @return
	 */
	public final boolean isHasErrors() {
		return !this.getErrors().isEmpty();
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

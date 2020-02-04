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
import java.util.Vector;

import org.springframework.validation.ObjectError;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 31/gen/2011 23.31.12
 *
 */
public class NodeDeployedServicesResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5911153989011081369L;

	private String servicePackage;
	
	private Vector<ObjectError> errors = new Vector<ObjectError>();
	
	public NodeDeployedServicesResult(String servicePackage, Vector<ObjectError> errors) {
		this.setServicePackage(servicePackage);
		this.setErrors(errors);
	}

	/**
	 * @param servicePackage the servicePackage to set
	 */
	private void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	/**
	 * @param errors the errors to set
	 */
	private void setErrors(Vector<ObjectError> errors) {
		this.errors = errors;
	}

	/**
	 * @return the servicePackage
	 */
	public final String getServicePackage() {
		return servicePackage;
	}

	/**
	 * @return the errors
	 */
	public final Vector<ObjectError> getErrors() {
		return errors;
	}
	
	/**
	 * @return
	 */
	public final boolean isHasErrors() {
		return !this.getErrors().isEmpty();
	}
	
}

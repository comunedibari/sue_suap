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

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 04/mag/2011 14.58.29
 *
 */
public class FEServiceListElement implements Serializable {

	private static final long serialVersionUID = -1293780496033057949L;

	private String feServiceId;
	private String feServiceName;
	
	public FEServiceListElement(final String feServiceId, final String feServiceName) {
		this.setFeServiceId(feServiceId);
		this.setFeServiceName(feServiceName);
	}

	/**
	 * @param feServiceId the feServiceId to set
	 */
	private void setFeServiceId(String feServiceId) {
		this.feServiceId = feServiceId;
	}

	/**
	 * @param feServiceName the feServiceName to set
	 */
	private void setFeServiceName(String feServiceName) {
		this.feServiceName = feServiceName;
	}

	/**
	 * @return the feServiceId
	 */
	public final String getFeServiceId() {
		return feServiceId;
	}

	/**
	 * @return the feServiceName
	 */
	public final String getFeServiceName() {
		return feServiceName;
	}
	
}

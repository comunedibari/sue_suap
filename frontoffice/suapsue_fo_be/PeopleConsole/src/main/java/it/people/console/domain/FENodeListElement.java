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
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 28/gen/2011 15.58.29
 *
 */
public class FENodeListElement implements Serializable {

	private static final long serialVersionUID = 8108767410736813778L;

	private String feNodeId;
	
	private String feNodeName;
	
	public FENodeListElement(final String feNodeId, final String feNodeName) {
		this.setFeNodeId(feNodeId);
		this.setFeNodeName(feNodeName);
	}

	/**
	 * @param feNodeId the feNodeId to set
	 */
	private void setFeNodeId(String feNodeId) {
		this.feNodeId = feNodeId;
	}

	/**
	 * @param feNodeName the feNodeName to set
	 */
	private void setFeNodeName(String feNodeName) {
		this.feNodeName = feNodeName;
	}

	/**
	 * @return the feNodeId
	 */
	public final String getFeNodeId() {
		return feNodeId;
	}

	/**
	 * @return the feNodeName
	 */
	public final String getFeNodeName() {
		return feNodeName;
	}
	
}

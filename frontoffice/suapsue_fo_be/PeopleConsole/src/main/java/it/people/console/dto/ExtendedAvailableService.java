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

import it.people.feservice.beans.AvailableService;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/ott/2011 15.54.58
 *
 */
public class ExtendedAvailableService extends AvailableService {

	private static final long serialVersionUID = -8610494597169050100L;

	private int nodeId;
	
	private String communeKey;

	private String commune;
	
	private String nodeName;
	
	private String reference;
	
	public ExtendedAvailableService() {
		super();
		this.setCommuneKey("");
		this.setCommune("");
		this.setNodeName("");
		this.setReference("");
	}

	/**
	 * @return the nodeId
	 */
	public final int getNodeId() {
		return this.nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public final void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the communeKey
	 */
	public final String getCommuneKey() {
		return this.communeKey;
	}

	/**
	 * @param communeKey the communeKey to set
	 */
	public final void setCommuneKey(String communeKey) {
		this.communeKey = communeKey;
	}

	/**
	 * @return the commune
	 */
	public final String getCommune() {
		return this.commune;
	}

	/**
	 * @param commune the commune to set
	 */
	public final void setCommune(String commune) {
		this.commune = commune;
	}

	/**
	 * @return the nodeName
	 */
	public final String getNodeName() {
		return this.nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public final void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the reference
	 */
	public final String getReference() {
		return this.reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}

}

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

import it.people.console.domain.BaseBean;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 05/ott/2012 21:43:41
 *
 */
public class FEInterfaceDTO extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1981988312306343117L;

	/**
	 * 
	 */
	private Vector<String> communeList;
	
	/**
	 * 
	 */
	private String feReference;
	
	/**
	 * 
	 */
	public FEInterfaceDTO() {
		this.setCommuneList(new Vector<String>());
	}

	/**
	 * @return the communeList
	 */
	public final Vector<String> getCommuneList() {
		return this.communeList;
	}

	/**
	 * @param communeList the communeList to set
	 */
	public final void setCommuneList(Vector<String> communeList) {
		this.communeList = communeList;
	}

	/**
	 * @return the feReference
	 */
	public final String getFeReference() {
		return this.feReference;
	}

	/**
	 * @param feReference the feReference to set
	 */
	public final void setFeReference(String feReference) {
		this.feReference = feReference;
	}
	
	/**
	 * @param communeId
	 */
	public void addCommuneList(String communeId) {
		this.getCommuneList().add(communeId);
	}
	
}

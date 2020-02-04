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
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:30
 */
public class FENodeQuery implements Serializable, Clearable {

	private static final long serialVersionUID = 3635437573892447699L;

	private Long id;
	
	private String name;
	
	private String feServiceURL;
	
	private String municipality;
	
	private String municipalityCode;

	private Boolean delegationControlEnabled;
	
	private String delegationControlServiceURL;
	
	private String aooPrefix;

	public FENodeQuery(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public final Long getId() {
		return this.id;
	}
	
	public final void setId(Long nodeId) {
		this.id = nodeId;
	}
	
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getFeServiceURL() {
		return feServiceURL;
	}

	public final void setFeServiceURL(String feServiceURL) {
		this.feServiceURL = feServiceURL;
	}

	public final String getMunicipality() {
		return municipality;
	}

	public final void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public final String getMunicipalityCode() {
		return municipalityCode;
	}

	public final void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public final Boolean isDelegationControlEnabled() {
		return delegationControlEnabled;
	}

	public final void setDelegationControlEnabled(Boolean delegationControlEnabled) {
		this.delegationControlEnabled = delegationControlEnabled;
	}

	public final String getDelegationControlServiceURL() {
		return delegationControlServiceURL;
	}

	public final void setDelegationControlServiceURL(
			String delegationControlServiceURL) {
		this.delegationControlServiceURL = delegationControlServiceURL;
	}

	public final String getAooPrefix() {
		return aooPrefix;
	}

	public final void setAooPrefix(String aooPrefix) {
		this.aooPrefix = aooPrefix;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setAooPrefix(null);
		this.setDelegationControlEnabled(null);
		this.setDelegationControlServiceURL(null);
		this.setFeServiceURL(null);
		this.setMunicipality(null);
		this.setMunicipalityCode(null);
		this.setName(null);
		this.setId(null);
	}

}

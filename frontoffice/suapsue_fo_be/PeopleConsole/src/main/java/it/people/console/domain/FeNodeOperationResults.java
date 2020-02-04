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
 * @created 02/nov/2011 15.22.13
 *
 */
public class FeNodeOperationResults implements Serializable {
	
	private static final long serialVersionUID = -6200836049887630559L;

	private String id;
	
	private String name;
	
	private String municipality;
	
	private String municipalityCode;

	private String aooPrefix;
	
	private List<String> messages = new ArrayList<String>();
	
	private Vector<ObjectError> errors = new Vector<ObjectError>();
	
	private Vector<FeServiceOperationResults> feServiceOperationResults = new Vector<FeServiceOperationResults>();
	
	public FeNodeOperationResults() {
	}
	
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Vector<ObjectError> errors) {
		this.errors = errors;
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

	/**
	 * @return the id
	 */
	public final String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the municipality
	 */
	public final String getMunicipality() {
		return this.municipality;
	}

	/**
	 * @param municipality the municipality to set
	 */
	public final void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	/**
	 * @return the municipalityCode
	 */
	public final String getMunicipalityCode() {
		return this.municipalityCode;
	}

	/**
	 * @param municipalityCode the municipalityCode to set
	 */
	public final void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	/**
	 * @return the aooPrefix
	 */
	public final String getAooPrefix() {
		return this.aooPrefix;
	}

	/**
	 * @param aooPrefix the aooPrefix to set
	 */
	public final void setAooPrefix(String aooPrefix) {
		this.aooPrefix = aooPrefix;
	}

	/**
	 * @return the feServiceOperationResults
	 */
	public final Vector<FeServiceOperationResults> getFeServiceOperationResults() {
		return this.feServiceOperationResults;
	}

	/**
	 * @param feServiceOperationResults the feServiceOperationResults to set
	 */
	public final void setFeServiceOperationResults(
			Vector<FeServiceOperationResults> feServiceOperationResults) {
		this.feServiceOperationResults = feServiceOperationResults;
	}
	
	/**
	 * @param feServiceOperationResults
	 */
	public final void addFeServiceOperationResults(FeServiceOperationResults feServiceOperationResults) {
		this.getFeServiceOperationResults().add(feServiceOperationResults);
	}

}

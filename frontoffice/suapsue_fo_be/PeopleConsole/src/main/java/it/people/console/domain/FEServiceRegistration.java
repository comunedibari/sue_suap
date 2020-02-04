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

import java.util.List;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:29
 */
public class FEServiceRegistration extends BaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2214209246147381844L;

	private List<FENodeListElement> logicalServiceName;
	
	private String selectedFeNode;
	
	private String feServicePackage;
	
	private String error;
	
	public FEServiceRegistration(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @return the logicalServiceName
	 */
	public final List<FENodeListElement> getLogicalServiceName() {
		return logicalServiceName;
	}

	/**
	 * @param logicalServiceName the logicalServiceName to set
	 */
	public final void setLogicalServiceName(
			List<FENodeListElement> logicalServiceName) {
		this.logicalServiceName = logicalServiceName;
	}

	/**
	 * @return the selectedFeNode
	 */
	public final String getSelectedFeNode() {
		return selectedFeNode;
	}

	/**
	 * @param selectedFeNode the selectedFeNode to set
	 */
	public final void setSelectedFeNode(String selectedFeNode) {
		this.selectedFeNode = selectedFeNode;
	}

	/**
	 * @return the feServicePackage
	 */
	public final String getFeServicePackage() {
		return feServicePackage;
	}

	/**
	 * @param feServicePackage the feServicePackage to set
	 */
	public final void setFeServicePackage(String feServicePackage) {
		this.feServicePackage = feServicePackage;
	}

	/**
	 * @return the error
	 */
	public final String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public final void setError(String error) {
		this.error = error;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setId(null);
		this.setFeServicePackage(null);
	}
	
}

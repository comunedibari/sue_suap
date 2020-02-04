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
package it.people.console.persistence.beans.support;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 20/gen/2011 16.51.28
 *
 */
public class EditableRowInputData {

	private boolean newRow = false;
	
	private Map<String, Object> inputData = new HashMap<String, Object>();
	private Map<String, Object> rowIdentifiers = new HashMap<String, Object>();

	public EditableRowInputData(Map<String, Object> rowIdentifiers, Map<String, Object> inputData) {
		this.setRowIdentifiers(rowIdentifiers);
		this.setInputData(inputData);
		this.setNewRow(false);
	}
	
	public EditableRowInputData(Map<String, Object> rowIdentifiers, Map<String, Object> inputData, boolean newRow) {
		this.setRowIdentifiers(rowIdentifiers);
		this.setInputData(inputData);
		this.setNewRow(newRow);
	}

	/**
	 * @param newRow the newRow to set
	 */
	private void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}

	/**
	 * @param inputData the inputData to set
	 */
	private void setInputData(Map<String, Object> inputData) {
		this.inputData = inputData;
	}

	/**
	 * @param rowIdentifiers the rowIdentifiers to set
	 */
	private void setRowIdentifiers(Map<String, Object> rowIdentifiers) {
		this.rowIdentifiers = rowIdentifiers;
	}

	/**
	 * @return the newRow
	 */
	public final boolean isNewRow() {
		return newRow;
	}

	/**
	 * @return the rowIdentifiers
	 */
	public final Map<String, Object> getRowIdentifiers() {
		return rowIdentifiers;
	}

	/**
	 * @return the inputData
	 */
	public final Map<String, Object> getInputData() {
		return inputData;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = "[\n";
		
		result += "\tnewRow = '" + this.isNewRow() + "';\n";

		result += "\tRow identifiers:\n";
		
		for(String key : this.getRowIdentifiers().keySet()) {
			result += "\t\tkey = '" + key + "'; value = '" + this.getRowIdentifiers().get(key) + "';\n";
		}

		result += "\tInput data:\n";
		
		for(String key : this.getInputData().keySet()) {
			result += "\t\tkey = '" + key + "'; value = '" + this.getInputData().get(key) + "';\n";
		}
		
		return result + "]";
		
	}
	
}

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
package it.people.console.persistence.jdbc.core;

import java.util.Map;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/gen/2011 22.52.03
 *
 */
public class EditableRow {

	private boolean editEnabled = false;

	private Map<String, Object> row;
	
	public EditableRow(Map<String, Object> row) {
		this.setRow(row);
	}

	/**
	 * @return the editEnabled
	 */
	public final boolean isEditEnabled() {
		return editEnabled;
	}

	/**
	 * @param editEnabled the editEnabled to set
	 */
	public final void setEditEnabled(boolean editEnabled) {
		this.editEnabled = editEnabled;
	}

	/**
	 * @return the row
	 */
	public final Map<String, Object> getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public final void setRow(Map<String, Object> row) {
		this.row = row;
	}
	
}

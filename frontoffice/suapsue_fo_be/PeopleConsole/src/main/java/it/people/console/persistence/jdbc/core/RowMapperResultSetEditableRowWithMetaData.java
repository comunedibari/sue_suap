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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 15/gen/2011 09.38.51
 *
 */
public class RowMapperResultSetEditableRowWithMetaData {

	private List<EditableRow> data;
	
	private Map<String, ColumnMetaData> metaData = new HashMap<String, ColumnMetaData>();
	
	private List<ColumnHeaderInformation> columnsAliases = new ArrayList<ColumnHeaderInformation>();

	public RowMapperResultSetEditableRowWithMetaData() {
		
	}

	public final List<EditableRow> getData() {
		return data;
	}

	public final void setData(List<EditableRow> data) {
		this.data = data;
	}

	public final Map<String, ColumnMetaData> getMetaData() {
		return metaData;
	}

	public final void setMetaData(Map<String, ColumnMetaData> metaData) {
		this.metaData = metaData;
	}

	/**
	 * @return the columnsAliases
	 */
	public final List<ColumnHeaderInformation> getColumnsAliases() {
		return columnsAliases;
	}

	/**
	 * @param columnsAliases the columnsAliases to set
	 */
	public final void setColumnsAliases(List<ColumnHeaderInformation> columnsAliases) {
		this.columnsAliases = columnsAliases;
	}

}

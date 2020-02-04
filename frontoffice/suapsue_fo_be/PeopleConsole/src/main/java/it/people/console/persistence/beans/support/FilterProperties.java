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

import it.people.console.enumerations.Types;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 08/gen/2011 10.20.08
 *
 */
public class FilterProperties {

	private String column;
	
	private String operator;
	
	private String value;
	
	private Types type;
	
	public FilterProperties(final String column, final String operator, final String value, final Types type) {
		this.setColumn(column);
		this.setOperator(operator);
		this.setValue(value);
		this.setType(type);
	}

	private void setColumn(String column) {
		this.column = column;
	}

	private void setOperator(String operator) {
		this.operator = operator;
	}

	private void setValue(String value) {
		this.value = value;
	}
	
	private final void setType(Types type) {
		this.type = type;
	}

	public final String getColumn() {
		return column;
	}

	public final String getOperator() {
		return operator;
	}

	public final String getValue() {
		return value;
	}

	public final Types getType() {
		return type;
	}
	
}

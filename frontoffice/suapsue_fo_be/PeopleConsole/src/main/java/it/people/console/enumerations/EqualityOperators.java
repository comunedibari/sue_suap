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
package it.people.console.enumerations;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/dic/2010 12.01.22
 *
 */
public enum EqualityOperators implements IOperatorsEnum {

	equal("=", "="),
	equal_is("is", "is"),
	not_equal("!=", "!="),
	not_equal_is("is not", "is not"),
	lower_than("<", "<"),
	lower_than_or_equal("<=", "<="),
	greater_than(">", ">"),
	greater_than_or_equal(">=", ">=");
	
	private String label;
	
	private String value;
	
	private EqualityOperators(String label, String value) {
		this.setLabel(label);
		this.setValue(value);
	}

	private void setLabel(String label) {
		this.label = label;
	}

	public final String getLabel() {
		return label;
	}

	private void setValue(String value) {
		this.value = value;
	}

	public final String getValue() {
		return value;
	}
	
}

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
package it.people.console.persistence.jdbc.support;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 27/gen/2011 22.33.41
 *
 */
public class BooleanConverter implements Decodable {

	private String trueValue;
	
	private String falseValue;
	
	public BooleanConverter(final String trueConversion, final String falseConversion) {
		this.setTrueValue(trueConversion);
		this.setFalseValue(falseConversion);
	}

	/**
	 * @param trueValue the trueValue to set
	 */
	private void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}

	/**
	 * @param falseValue the falseValue to set
	 */
	private void setFalseValue(String falseValue) {
		this.falseValue = falseValue;
	}

	/**
	 * @return the trueValue
	 */
	private String getTrueValue() {
		return trueValue;
	}

	/**
	 * @return the falseValue
	 */
	private String getFalseValue() {
		return falseValue;
	}

	/* (non-Javadoc)
	 * @see it.people.console.persistence.jdbc.support.Decodable#decode(java.lang.Object)
	 */
	public String decode(Object value) {
		return (Integer.parseInt((String)value) == 1) ? this.getTrueValue() : this.getFalseValue();
	}
	
}

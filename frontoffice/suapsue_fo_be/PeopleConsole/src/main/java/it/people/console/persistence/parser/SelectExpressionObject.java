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
package it.people.console.persistence.parser;

/**
 * @author Riccardo Foraf�
 *
 * Nov 27, 2009
 *
 */
public class SelectExpressionObject {

	private String expression;
	private String alias;
	
	public SelectExpressionObject() {
		this.setExpression(null);
		this.setAlias(null);
	}

	public SelectExpressionObject(String expression) {
		this.setExpression(expression);
		this.setAlias(null);
	}

	public SelectExpressionObject(String expression, String alias) {
		this.setExpression(expression);
		this.setAlias(alias);
	}

	/**
	 * @return the expression
	 */
	public final String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public final void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the alias
	 */
	public final String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public final void setAlias(String alias) {
		this.alias = alias;
	}

}

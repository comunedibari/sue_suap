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

import java.util.Vector;

/**
 * @author Riccardo Forafo'
 *
 * Nov 27, 2009
 *
 */
public class SelectFragmentObject {

	private boolean invalidAlias;
	private boolean validQuery;
	private Vector<String> messages;
	private String select;
	private Vector<SelectExpressionObject> selectExpressions;
	
	public SelectFragmentObject() {
		this.setSelect(null);
		this.setSelectExpressions(new Vector<SelectExpressionObject>());
		this.setMessages(new Vector<String>());
	}

	public SelectFragmentObject(String select) {
		this.setSelect(select);
		this.setSelectExpressions(new Vector<SelectExpressionObject>());
		this.setMessages(new Vector<String>());
	}

	public SelectFragmentObject(String select, Vector<SelectExpressionObject> selectExpressions) {
		this.setSelect(select);
		this.setSelectExpressions(selectExpressions);
		this.setMessages(new Vector<String>());
	}

	/**
	 * @return the select
	 */
	public final String getSelect() {
		return select;
	}

	/**
	 * @param select the select to set
	 */
	public final void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return the selectExpressions
	 */
	public final Vector<SelectExpressionObject> getSelectExpressions() {
		return selectExpressions;
	}

	/**
	 * @param selectExpressions the selectExpressions to set
	 */
	public final void setSelectExpressions(
			Vector<SelectExpressionObject> selectExpressions) {
		this.selectExpressions = selectExpressions;
	}
	
	/**
	 * @param selectExpression
	 */
	public final void addSelectExpressions(SelectExpressionObject selectExpression) {
		this.getSelectExpressions().add(selectExpression);
	}

	/**
	 * @return the validQuery
	 */
	public final boolean isValidQuery() {
		return validQuery;
	}

	/**
	 * @param validQuery the validQuery to set
	 */
	public final void setValidQuery(boolean validQuery) {
		this.validQuery = validQuery;
	}

	/**
	 * @return the messages
	 */
	public final Vector<String> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public final void setMessages(Vector<String> messages) {
		this.messages = messages;
	}
	
	public final void addMessages(String message) {
		this.getMessages().add(message);
	}

	/**
	 * @return the invalidAlias
	 */
	public final boolean isInvalidAlias() {
		return invalidAlias;
	}

	/**
	 * @param invalidAlias the invalidAlias to set
	 */
	public final void setInvalidAlias(boolean invalidAlias) {
		this.invalidAlias = invalidAlias;
	}
	
}

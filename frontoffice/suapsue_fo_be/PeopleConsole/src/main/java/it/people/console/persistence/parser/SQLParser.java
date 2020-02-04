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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Riccardo Foraf�
 *
 * Nov 25, 2009
 *
 */
public class SQLParser {

	private String queryToParse;
	
	public SQLParser(String queryToParse) {
		this.setQueryToParse(queryToParse);
	}
	
	/**
	 * @param queryToParse the queryToParse to set
	 */
	public final void setQueryToParse(String queryToParse) {
		this.queryToParse = queryToParse.toLowerCase();
	}

	/**
	 * @return the queryToParse
	 */
	public final String getQueryToParse() {
		return queryToParse;
	}

	public SelectFragmentObject parseSelect() {
		SelectFragmentObject result = new SelectFragmentObject();
		
		if (this.getQueryToParse().trim().indexOf("select") != 0) {
			result.addMessages("La query non inizia con la clausola SELECT.");
			result.setValidQuery(false);
		}
		else {
			SelectFragmentObject selectFragmentObjectBuffer = getSelectFragment(this.getQueryToParse());
			result.setSelect(selectFragmentObjectBuffer.getSelect());
			result.setSelectExpressions(selectFragmentObjectBuffer.getSelectExpressions());
			result.setMessages(selectFragmentObjectBuffer.getMessages());
			result.setInvalidAlias(selectFragmentObjectBuffer.isInvalidAlias());
			result.setValidQuery(true);
		}
		
		return result;
	}

	public boolean isWherePresent() {
		
		int fromPosition = parseFromStart();
		
		if (fromPosition < 0) {
			return false;
		} 
		else {
			return this.getQueryToParse().substring(fromPosition + " from".length()).indexOf(" where ") > 0;
		}
		
	}
	
	private int parseFromStart() {

		int result = -1;
		
		if (this.getQueryToParse().trim().indexOf("select") != 0) {
			return result;
		}
		else {
			return getFromStart(this.getQueryToParse());
		}
		
	}
	
	private SelectFragmentObject getSelectFragment(final String queryToParse) {

		SelectFragmentObject selectFragmentObject = new SelectFragmentObject();
		String buffer = queryToParse.toLowerCase().replaceAll("\n", " ");
		buffer = buffer.replaceAll("\t", " ");
		StringBuilder builder = new StringBuilder();
		int openedParenthesis = 0;
		
		int expressionStart = getExpressionStart(buffer);
		int expressionNumber = 1;
		
		for(int i=0; i < buffer.length(); i++) {
			char _char = buffer.charAt(i);
			if (_char == '(') {
				openedParenthesis++;
			}
			if (_char == ')' && openedParenthesis > 0) {
				openedParenthesis--;
			}
			if (_char == ',' && openedParenthesis == 0) {
				SelectExpressionObject selectExpressionObject = getExpressionObject(buffer.substring(expressionStart, i));
				if (selectExpressionObject.getAlias() == null) {
					selectFragmentObject.setInvalidAlias(true);
					selectFragmentObject.addMessages(getExpressionObjectErrorMessage(expressionNumber, selectExpressionObject));
				}
				selectFragmentObject.addSelectExpressions(selectExpressionObject);
				expressionStart = i + 1;
				expressionNumber++;
			}
			if (buffer.substring(i).startsWith(" from") && openedParenthesis == 0) {
				SelectExpressionObject selectExpressionObject = getExpressionObject(buffer.substring(expressionStart, i));
				if (selectExpressionObject.getAlias() == null) {
					selectFragmentObject.setInvalidAlias(true);
					selectFragmentObject.addMessages(getExpressionObjectErrorMessage(expressionNumber, selectExpressionObject));
				}
				selectFragmentObject.addSelectExpressions(selectExpressionObject);
				break;
			}
			builder.append(_char);
		}
		
		selectFragmentObject.setSelect(builder.toString());
		return selectFragmentObject;
		
	}
	
	private int getExpressionStart(String query) {
		
		int result = 0;
		
		boolean isSelectDistinct = false;

		Pattern pattern = Pattern.compile("^ *select +distinct +.+$");
		Matcher matcher = pattern.matcher(query);
		isSelectDistinct = matcher.matches();
		
		for(int i=0; i < query.length(); i++) {
			if (isSelectDistinct) {
				if (query.substring(i).startsWith(" distinct ")) {
					result = i + " distinct ".length();
					break;
				}
			}
			else {
				if (query.substring(i).startsWith("select ")) {
					result = i + "select ".length();
					break;
				}
			}
		}
				
		return result;
		
	}
	
	private SelectExpressionObject getExpressionObject(String completeExpression) {
		
		String expression = null;
		String alias = null;

		boolean isAlias = false;

		Pattern pattern = Pattern.compile(".+\\s+as\\s+\"[\\d\\w\\s]+\"\\s*,?\\s*$");
		Matcher matcher = pattern.matcher(completeExpression);
		isAlias = matcher.matches();
		
		if (isAlias) {
			int aliasIndex = completeExpression.lastIndexOf(" as ");
			String aliasBuffer = completeExpression.substring(aliasIndex + " as ".length()).trim();
			expression = completeExpression.substring(0, aliasIndex).trim();
			
			alias = aliasBuffer.substring(1, aliasBuffer.lastIndexOf("\""));
		}
		else {
			expression = completeExpression.trim();
		}
			
		return new SelectExpressionObject(expression, alias);
		
	}
	
	private String getExpressionObjectErrorMessage(int expressionNumber, SelectExpressionObject expressionObject) {
		return "L'espressione n� " + expressionNumber + " non ha un alias associato.\n\tEspressione:" + expressionObject.getExpression() + "\n";
	}

	private int getFromStart(final String queryToParse) {

		int result = -1;
		String buffer = queryToParse.toLowerCase().replaceAll("\n", " ");
		buffer = buffer.replaceAll("\t", " ");
		int openedParenthesis = 0;
		
		int expressionNumber = 1;
		
		for(int i=0; i < buffer.length(); i++) {
			char _char = buffer.charAt(i);
			if (_char == '(') {
				openedParenthesis++;
			}
			if (_char == ')' && openedParenthesis > 0) {
				openedParenthesis--;
			}
			if (_char == ',' && openedParenthesis == 0) {
				expressionNumber++;
			}
			if (buffer.substring(i).startsWith(" from") && openedParenthesis == 0) {
				result = i + 1;
				break;
			}
		}
		
		return result;
		
	}
	
	
}

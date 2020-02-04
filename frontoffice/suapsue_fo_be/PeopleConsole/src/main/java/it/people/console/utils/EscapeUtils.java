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
package it.people.console.utils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 25/set/2011 15.39.39
 *
 */
public class EscapeUtils {

	/**
	 * @param aText
	 * @return
	 */
	public static String forHTML(String aText){
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			if (character == '<') {
				result.append("&lt;");
			}
			else if (character == '>') {
				result.append("&gt;");
			}
			else if (character == '&') {
				result.append("&amp;");
			}
			else if (character == '\"') {
				result.append("&quot;");
			}
			else if (character == '\t') {
				addCharEntity(9, result);
			}
			else if (character == '!') {
				addCharEntity(33, result);
			}
			else if (character == '#') {
				addCharEntity(35, result);
			}
			else if (character == '$') {
				addCharEntity(36, result);
			}
			else if (character == '%') {
				addCharEntity(37, result);
			}
			else if (character == '\'') {
				addCharEntity(39, result);
			}
			else if (character == '(') {
				addCharEntity(40, result);
			}
			else if (character == ')') {
				addCharEntity(41, result);
			}
			else if (character == '*') {
				addCharEntity(42, result);
			}
			else if (character == '+') {
				addCharEntity(43, result);
			}
			else if (character == ',') {
				addCharEntity(44, result);
			}
			else if (character == '-') {
				addCharEntity(45, result);
			}
			else if (character == '.') {
				addCharEntity(46, result);
			}
			else if (character == '/') {
				addCharEntity(47, result);
			}
			else if (character == ':') {
				addCharEntity(58, result);
			}
			else if (character == ';') {
				addCharEntity(59, result);
			}
			else if (character == '=') {
				addCharEntity(61, result);
			}
			else if (character == '?') {
				addCharEntity(63, result);
			}
			else if (character == '@') {
				addCharEntity(64, result);
			}
			else if (character == '[') {
				addCharEntity(91, result);
			}
			else if (character == '\\') {
				addCharEntity(92, result);
			}
			else if (character == ']') {
				addCharEntity(93, result);
			}
			else if (character == '^') {
				addCharEntity(94, result);
			}
			else if (character == '_') {
				addCharEntity(95, result);
			}
			else if (character == '`') {
				addCharEntity(96, result);
			}
			else if (character == '{') {
				addCharEntity(123, result);
			}
			else if (character == '|') {
				addCharEntity(124, result);
			}
			else if (character == '}') {
				addCharEntity(125, result);
			}
			else if (character == '~') {
				addCharEntity(126, result);
			}
			else {
				//the char is not a special one
				//add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * @param aIdx
	 * @param aBuilder
	 */
	private static void addCharEntity(Integer aIdx, StringBuilder aBuilder){
		String padding = "";
		if( aIdx <= 9 ){
			padding = "00";
		}
		else if( aIdx <= 99 ){
			padding = "0";
		}
		else {
			//no prefix
		}
		String number = padding + aIdx.toString();
		aBuilder.append("&#" + number + ";");
	}
	
}

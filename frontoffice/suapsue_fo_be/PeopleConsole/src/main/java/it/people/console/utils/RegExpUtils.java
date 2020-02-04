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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 17.09.46
 *
 */
public class RegExpUtils implements IRegExpUtils {

	public boolean matchIPAddressPattern(String expressionToEvaluate) {
		Pattern ipAddressPattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
		Matcher ipAddressMatcher = ipAddressPattern.matcher(expressionToEvaluate);
		return ipAddressMatcher.matches();
	}

	public boolean matchEMailPattern(String expressionToEvaluate) {
		Pattern eMailPattern = Pattern.compile("^(\\w+((\\.{0,1}\\w+)|(\\-{0,1}\\w+))+)@(\\w+((\\.{0,1}\\w+)|(\\-{0,1}\\w+))+)$");
		Matcher eMailMatcher = eMailPattern.matcher(expressionToEvaluate);
		return eMailMatcher.matches();
	}

	public boolean matchAllowedSelectPatterns(String expressionToEvaluate) {
		return (matchSqlPattern(expressionToEvaluate) || matchSelectListPattern(expressionToEvaluate));
	}

	public boolean matchSqlPattern(String expressionToEvaluate) {
		Pattern sqlExpressionPattern = Pattern.compile("^select\\s.+\\sfrom\\s.+$");
		Matcher sqlExpressionMatcher = sqlExpressionPattern.matcher(expressionToEvaluate);
		return sqlExpressionMatcher.matches();
	}

	public boolean matchSelectListPattern(String expressionToEvaluate) {
		Pattern selectListPattern = Pattern.compile("^(([.[^;]]+;){2})+([.[^;]]+;)([.[^;]]+)$");
		Matcher selectListMatcher = selectListPattern.matcher(expressionToEvaluate);
		return selectListMatcher.matches();
	}
	
	public boolean matchValidHostNameOnly(String expressionToEvaluate) {
		Pattern selectListPattern = Pattern.compile("^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$");
		Matcher selectListMatcher = selectListPattern.matcher(expressionToEvaluate);
		return selectListMatcher.matches();
	}

	public boolean matchValidHostName(String expressionToEvaluate) {
		return matchValidHostNameOnly(expressionToEvaluate) || matchIPAddressPattern(expressionToEvaluate);
	}
	
	public boolean matchTaxCode(String expressionToEvaluate) {

		expressionToEvaluate=expressionToEvaluate.toUpperCase();

		if (expressionToEvaluate.length() == 16){
			char[] Carattere = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
					'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
					'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0',
					'1', '2', '3', '4', '5', '6', '7', '8', '9'};

			int[] ValoriDispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4,
					18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
					25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19,
					21};

			int[] ValoriPari = new int[36];
			for (int i = 0; i < 26; i++){
				ValoriPari[i] = i;
			}
			for (int i = 26; i < 36; i++){
				ValoriPari[i] = i - 26;
			}

			char[] caratteriCF = expressionToEvaluate.toCharArray();
			int valore = 0;
			for (int i = 0; i < caratteriCF.length - 1; i++){

				if ((i+1) % 2 == 0){
					for (int j = 0; j < Carattere.length; j++){

						if (caratteriCF[i] == Carattere[j]){
							valore += ValoriPari[j];
						}
					}

				}else{
					for (int j = 0; j < Carattere.length; j++){
						if (caratteriCF[i] == Carattere[j]){
							valore += ValoriDispari[j];
						}
					}
				}
			}

			valore %= 26;
			for (int i = 0; i < 26; i++){

				if (caratteriCF[caratteriCF.length - 1] == Carattere[i]){
					if (valore == i){
						return true;
					}else{
						return false;
					}
				}
			}
			return false;

		} else {
			return false;
		}

	}

}

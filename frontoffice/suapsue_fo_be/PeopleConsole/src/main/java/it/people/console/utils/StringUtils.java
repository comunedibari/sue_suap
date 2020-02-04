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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 26/nov/2010 17.48.51
 *
 */
public class StringUtils extends org.springframework.util.StringUtils {

	public static String nullToEmpty(String stringToChange) {
		return (stringToChange == null) ? "" : stringToChange.trim();
	}
	
	public static String escapeOracleStringValue(String stringToEscape) {
		return (stringToEscape.replaceAll("'", "''"));
	}
	
    public static String getClearLabel(String labelToClear, String charToClear) {
    	return StringUtils.nullToEmpty(labelToClear.replaceAll(charToClear, "")).trim();
    }
    
    public static boolean isEmptyString(String value) {
    	return nullToEmpty(value).equalsIgnoreCase("");
    }
	
    public static String extractString(String prefix, String suffix, String value) {
    	
    	int indexOfSuffix = value.indexOf(suffix);
    	return value.substring(prefix.length(), indexOfSuffix);
    	
    }
    
    public static String toString(Exception exception) {
    	
    	String result = "";
    	
    	StringWriter stringWriter = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stringWriter);
    	exception.printStackTrace(printWriter);
    	printWriter.flush();
    	printWriter.close();
    	stringWriter.flush();
    	result = stringWriter.toString();
    	try {
			stringWriter.close();
		} catch (IOException e) {
			
		}
    	
    	return result;
    	
    }

    public static String toString(Error error) {
    	
    	String result = "";
    	
    	StringWriter stringWriter = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stringWriter);
    	error.printStackTrace(printWriter);
    	printWriter.flush();
    	printWriter.close();
    	stringWriter.flush();
    	result = stringWriter.toString();
    	try {
			stringWriter.close();
		} catch (IOException e) {
			
		}
    	
    	return result;
    	
    }

    public static String toString(Throwable throwable) {
    	
    	String result = "";
    	
    	StringWriter stringWriter = new StringWriter();
    	PrintWriter printWriter = new PrintWriter(stringWriter);
    	throwable.printStackTrace(printWriter);
    	printWriter.flush();
    	printWriter.close();
    	stringWriter.flush();
    	result = stringWriter.toString();
    	try {
			stringWriter.close();
		} catch (IOException e) {
			
		}
    	
    	return result;
    	
    }
    
}

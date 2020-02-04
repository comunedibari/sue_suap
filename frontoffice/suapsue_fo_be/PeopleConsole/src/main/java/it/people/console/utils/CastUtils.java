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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/gen/2011 18.17.09
 *
 */
public class CastUtils {

	/**
	 * @param value
	 * @return
	 */
	public static Integer nullToInteger(Integer value) {
		return (value == null) ? new Integer(0) : value;
	}

	/**
	 * @param value
	 * @return
	 */
	public static Boolean objectToBoolean(Object value) {
		return (value == null) ? new Boolean(Boolean.FALSE) : (Boolean)value;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static boolean integerToBoolean(Integer value) {
		return (nullToInteger(value).compareTo(new Integer(0)) == 0) ? false : true;
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value) {
		return (value.equalsIgnoreCase("0")) ? false : true;
	}

	/**
	 * @param value
	 * @return
	 */
	public static int booleanToInt(boolean value) {
		return (value) ? 1 : 0;
	}
	
	/**
	 * @param <T>
	 * @param values
	 * @return
	 */
	public static <T> Vector<T> arrayToVector(T[] values) {
		
		Vector<T> result = null;
		
		if (values != null && values.length > 0) {
			result = new Vector<T>();
			for(int index = 0; index < values.length; index++) {
				result.add(values[index]);
			}
		}
		
		return result;
		
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static int stringToInt(String value) {
		return Integer.parseInt(value);
	}
	
	/**
	 * @param outputStream
	 * @return
	 */
	public static String byteArrayOutputStreamToBase64(ByteArrayOutputStream outputStream) {
		
		Base64 base64 = new Base64();
		return base64.encode(outputStream.toByteArray());
		
	}

	/**
	 * @param e
	 * @return
	 */
	public static String exceptionToString(Exception e) {
		
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		printWriter.flush();
		printWriter.close();
		writer.flush();
		
		try {
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return writer.toString();
		
	}
	
}

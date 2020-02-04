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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 17/giu/2011 10.49.16
 *
 */
public class MimeTypeFinder {

	public static final String P12_MIME_TYPE = "P12";
	public static final String WINDOWS_DOC_MIME_TYPE = "DOC";
	public static final String SEVEN_ZIP_MIME_TYPE = "7Z";
	public static final String ZIP_MIME_TYPE = "ZIP";
	public static final String PDF_MIME_TYPE = "PDF";
	public static final String UNKNOWN_MIME_TYPE = "UNKNOWN";

	private static final String P12_MIME_HEX = "3080020103308006";
	private static final String WINDOWS_DOC_MIME_HEX = "d0cf11e0a1b11ae1";
	private static final String SEVEN_ZIP_MIME_HEX = "377abcaf271c0003";
	private static final String ZIP_MIME_HEX = "504b030414000000";
	private static final String PDF_MIME_HEX = "255044462d312e34";
	
	private static Map<String, String> mimeTypes;
	
	static {
		mimeTypes = new HashMap<String, String>();
		mimeTypes.put(P12_MIME_HEX, P12_MIME_TYPE);
		mimeTypes.put(WINDOWS_DOC_MIME_HEX, WINDOWS_DOC_MIME_TYPE);
		mimeTypes.put(SEVEN_ZIP_MIME_HEX, SEVEN_ZIP_MIME_TYPE);
		mimeTypes.put(ZIP_MIME_HEX, ZIP_MIME_TYPE);
		mimeTypes.put(PDF_MIME_HEX, PDF_MIME_TYPE);
	}
	
	public static String findMymeType(byte[] mimeBytes) {
		
		String mimeFromBytes = Hex.encodeHexString(mimeBytes);
		if (mimeTypes.containsKey(mimeFromBytes)) {
			return mimeTypes.get(mimeFromBytes);
		} else {
			return UNKNOWN_MIME_TYPE;
		}
		
	}
	
}

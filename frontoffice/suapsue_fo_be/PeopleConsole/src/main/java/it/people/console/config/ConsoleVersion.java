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
package it.people.console.config;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 02/dic/2010 21.53.11
 *
 */
public class ConsoleVersion {

	public static final String VERSION_KEY = "CONSOLE_VERSION";
	
	private static final String MAJOR_NUMBER =  "1";

	private static final String MINOR_NUMBER =  "3449";
	
	private static final String BUILD_NUMBER =  "20120206.222858";
	
	private static final int DB_MAJORNUMBER =  2;
	private static final int DB_MINORNUMBER =  5;
	
	public final String getMajorNumber() {
		return MAJOR_NUMBER;
	}

	public final String getMinorNumber() {
		return MINOR_NUMBER;
	}

	public final String getBuildNumber() {
		return BUILD_NUMBER;
	}

	public static final int getDbMajorNumber() {
		return DB_MAJORNUMBER;
	}

	public static final int getDbMinorNumber() {
		return DB_MINORNUMBER;
	}

}

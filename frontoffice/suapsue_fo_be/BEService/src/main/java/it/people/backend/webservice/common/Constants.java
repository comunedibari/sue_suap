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
package it.people.backend.webservice.common;

public interface Constants {

	public static final String EMPTY_STRING = "";

	public static final String JNDI_PREFIX = "java:/comp/env/";

	public static final String JDBC_KEY_PREFIX = "jdbc.";
	
	public static final String JDBC_DRIVER_CLASS_NAME_KEY_SUFFIX = ".driverClassName";

	public static final String JDBC_URL_KEY_SUFFIX = ".url";

	public static final String JDBC_USERNAME_KEY_SUFFIX = ".userName";

	public static final String JDBC_PASSWORD_KEY_SUFFIX = ".password";
	
	public static final String ISTAT_CODES_QUERY_KEY = "ricerca.codiciattivitaistat.query";
	
}

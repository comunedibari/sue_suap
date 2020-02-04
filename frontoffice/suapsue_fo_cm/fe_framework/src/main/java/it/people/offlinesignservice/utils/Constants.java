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
package it.people.offlinesignservice.utils;

/**
 * @author Riccardo Forafo'
 * 
 *         18/dic/2010
 * 
 */
public interface Constants {

    public static final String GENERATOR_ID = "olsfds";

    public static final String COMMAND_PROPERTY_PARAMETER_KEY = GENERATOR_ID
	    + ".commandProperty";

    public static final String COMMAND_INDEX_PARAMETER_KEY = GENERATOR_ID
	    + ".commandIndex";

    public static final String INPUT_FORMAT_PARAMETER_KEY = GENERATOR_ID
	    + ".inputFormat";

    public static final String FAILURE_COMMAND_PROPERTY = GENERATOR_ID
	    + ".failure";

    public static final String SUCCESS_COMMAND_PROPERTY = GENERATOR_ID
	    + ".success";

    public static final String STRING_INPUT_FORMAT = "STRING";

    public static final String BINARY_INPUT_FORMAT = "BINARY";

    public static final String DEFAULT_INPUT_FORMAT = STRING_INPUT_FORMAT;

}

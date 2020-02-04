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
package it.people.parser.exception;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 8, 2003 Time: 8:05:59 PM To
 * change this template use Options | File Templates.
 */
public class ParserException extends Exception {

    private String m_propertyName;

    public ParserException() {
	super();
    }

    public ParserException(String mesg) {
	super(mesg);
    }

    public ParserException(String propertyName, String mesg) {
	super(mesg);
	m_propertyName = propertyName;
    }

    public String getPropertyName() {
	return m_propertyName;
    }

    public void setPropertyName(String p_propertyName) {
	m_propertyName = p_propertyName;
    }
}

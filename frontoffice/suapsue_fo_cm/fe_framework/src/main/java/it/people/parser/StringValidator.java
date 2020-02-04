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
package it.people.parser;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 30, 2003 Time: 9:48:36 AM To
 * change this template use Options | File Templates.
 */
public class StringValidator extends ObjectValidator {
    private int m_maxLength;

    public StringValidator() {

	super();
	m_maxLength = -1;

    }

    public StringValidator(boolean required) {
	super(required);
	m_maxLength = -1;
    }

    public StringValidator(boolean required, int maxLenght) {
	super(required);
	m_maxLength = maxLenght;
    }

    public int getMaxLength() {
	return m_maxLength;
    }

    public void setMaxLength(int p_maxLength) {
	m_maxLength = p_maxLength;
    }
}

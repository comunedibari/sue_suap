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

import it.people.parser.exception.ParserException;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 30-ott-2003 Time: 16.47.09 To
 * change this template use Options | File Templates.
 */
public class CharValidator extends ObjectValidator {
    private char[] m_validChars;

    public CharValidator() {
	super();
	m_validChars = new char[0];
    }

    public CharValidator(boolean required) {
	super(required);
	m_validChars = new char[0];
    }

    public CharValidator(char[] p_validChars) {
	super();
	m_validChars = p_validChars;
    }

    public CharValidator(boolean required, char[] p_validChars) {
	super(required);
	m_validChars = p_validChars;
    }

    public void setValidChars(char[] p_validCharsArray) {
	if (p_validCharsArray != null) {
	    m_validChars = p_validCharsArray;
	}
    }

    public char[] getValidChars() {
	return m_validChars;
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null)
	    return !isRequired();
	else {
	    if (p_obj instanceof Character) {
		// SimpleDateFormat sdf = new SimpleDateFormat(m_format);
		for (int i = 0; i < m_validChars.length; i++) {
		    if (((Character) p_obj).charValue() == m_validChars[i])
			return true;
		}
		throw new ParserException("Invalid Character");
	    }
	}
	return false;
    }
}

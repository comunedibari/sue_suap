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
 * Created by IntelliJ IDEA. User: acuffaro Date: 8-ott-2003 Time: 18.38.58 To
 * change this template use Options | File Templates.
 */
public class EmailValidator extends ObjectValidator {
    private int m_maxLength;

    public EmailValidator() {
	super();
	m_maxLength = -1;
    }

    public EmailValidator(boolean required) {
	super(required);
	m_maxLength = -1;
    }

    public EmailValidator(boolean required, int maxLenght) {
	super(required);
	m_maxLength = maxLenght;
    }

    public int getMaxLength() {
	return m_maxLength;
    }

    public void setMaxLength(int p_maxLength) {
	m_maxLength = p_maxLength;
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	// una email � valida se valgono tutte le seguenti condizioni:
	// - esiste un SOLO carattere @
	// - @ � seguita da almeno un .
	// - l'ultimo punto � seguito da almeno due caratteri alfabetici
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof String) {
		String myEmailAddress = (String) p_obj;
		int posizione = myEmailAddress.indexOf("@");
		if (posizione != -1) {
		    myEmailAddress = myEmailAddress.substring(posizione + 1);
		    int pos2 = myEmailAddress.lastIndexOf(".");
		    if (myEmailAddress.indexOf("@") == -1 && pos2 != -1
			    && myEmailAddress.substring(pos2 + 1).length() == 2) {
			myEmailAddress = myEmailAddress.substring(pos2 + 1);
			if (myEmailAddress.matches("[a-zA-Z][a-zA-Z]")) {
			    return true;
			}
		    }
		}
		throw new ParserException("Invalid mail address");
	    }
	}
	return false;
    }
}

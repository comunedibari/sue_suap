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

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 30-ott-2003 Time: 12.07.36 To
 * change this template use Options | File Templates.
 */

public class CivicNumberValidator extends ObjectValidator {

    public CivicNumberValidator() {
	super();
    }

    public CivicNumberValidator(boolean required) {
	super(required);
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof Integer)
		return true;
	    else if (p_obj instanceof String && ((String) p_obj).length() >= 0) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		try {
		    if (((String) p_obj).matches("[1-9][0-9]*")) {
			nf.parse((String) p_obj);
			return true;
		    }
		} catch (ParseException pEx) {
		    // throw new ParserException("Invalid Number");
		}
		throw new ParserException("Invalid Number");
	    }
	}
	return false;
    }

}

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
 * Created by IntelliJ IDEA. User: sergio Date: Sep 10, 2003 Time: 7:26:26 AM To
 * change this template use Options | File Templates.
 */
public class BooleanValidator extends ObjectValidator {
    public BooleanValidator(boolean required) {
	super(required);
    }

    public boolean parseValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof Boolean)
		return true;
	    else if (p_obj instanceof String && ((String) p_obj).length() >= 0) {
		try {
		    Boolean.getBoolean((String) p_obj);
		    return true;
		} catch (Exception pEx) {
		    throw new ParserException(pEx.getMessage());
		}
	    }
	}
	return false;
    }
}

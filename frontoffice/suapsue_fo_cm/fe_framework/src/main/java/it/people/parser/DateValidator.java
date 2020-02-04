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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 8, 2003 Time: 5:46:41 PM To
 * change this template use Options | File Templates.
 */
public class DateValidator extends ObjectValidator {
    protected String m_format;

    public DateValidator() {
	super();
	m_format = "dd/MM/yyyy";

    }

    public DateValidator(boolean required) {
	super(required);
	m_format = "dd/MM/yyyy";
    }

    public DateValidator(boolean required, String format) {
	super(required);
	m_format = format;
    }

    public String getFormat() {
	return m_format;
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof Date)
		return true;
	    else if (p_obj instanceof String && ((String) p_obj).length() >= 0) {
		SimpleDateFormat sdf = new SimpleDateFormat(m_format);
		try {
		    if (((String) p_obj)
			    .matches("[0-9][0-9][./][0-9][0-9][./][1-2][0-9][0-9][0-9]")) {
			Date currDate = sdf.parse((String) p_obj);
			if (p_obj.equals(sdf.format(currDate)))
			    return true;
		    }
		} catch (ParseException pEx) {

		}
		throw new ParserException("Invalid Date");
	    }
	}
	return false;
    }

}

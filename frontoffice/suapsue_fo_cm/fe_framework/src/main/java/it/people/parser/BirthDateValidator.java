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

import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 4-nov-2003 Time: 21.33.25 To
 * change this template use Options | File Templates.
 */
public class BirthDateValidator extends DateValidator {

    public BirthDateValidator() {
	super();
    }

    public BirthDateValidator(boolean required) {
	super(required);
    }

    public BirthDateValidator(boolean required, String format) {
	super(required, format);
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	boolean isValid = super.parserValidate(p_obj);
	if (isValid) {

	    int day = Integer.parseInt(((String) p_obj).substring(0, 2));
	    int mth = Integer.parseInt(((String) p_obj).substring(3, 5)) - 1;
	    int yar = Integer.parseInt(((String) p_obj).substring(6));
	    GregorianCalendar dateToValidate = new GregorianCalendar(yar, mth,
		    day, 0, 0);
	    GregorianCalendar today = new GregorianCalendar();
	    today.set(today.get(GregorianCalendar.YEAR),
		    today.get(GregorianCalendar.MONTH),
		    today.get(GregorianCalendar.DATE), 0, 0, 0);
	    /*
	     * if( today.get(GregorianCalendar.YEAR) !=
	     * dateToValidate.get(GregorianCalendar.YEAR) ||
	     * today.get(GregorianCalendar.MONTH) !=
	     * dateToValidate.get(GregorianCalendar.MONTH) ||
	     * today.get(GregorianCalendar.DATE) !=
	     * dateToValidate.get(GregorianCalendar.DATE)) return
	     * dateToValidate.before(today);
	     */
	    if (dateToValidate.get(GregorianCalendar.YEAR) < today
		    .get(GregorianCalendar.YEAR))
		return true;
	    else if (today.get(GregorianCalendar.YEAR) < dateToValidate
		    .get(GregorianCalendar.YEAR))
		throw new ParserException("Invalid Birth Date");
	    else {
		// stesso anno
		if (dateToValidate.get(GregorianCalendar.MONTH) < today
			.get(GregorianCalendar.MONTH))
		    return true;
		else if (today.get(GregorianCalendar.MONTH) < dateToValidate
			.get(GregorianCalendar.MONTH))
		    throw new ParserException("Invalid Birth Date");
		/*
		 * else if(dateToValidate.get(GregorianCalendar.DATE) <
		 * today.get(GregorianCalendar.DATE)) return true;
		 */
		else if (dateToValidate.get(GregorianCalendar.DATE) < today
			.get(GregorianCalendar.DATE))
		    return true;
		else
		    throw new ParserException("Invalid Birth Date");
	    }
	} else
	    // throw new ParserException("Invalid Birth Date");
	    return isValid;
    }

}

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
 * Created by IntelliJ IDEA. User: acuffaro Date: 25-nov-2003 Time: 16.31.22 To
 * change this template use Options | File Templates.
 */
public class SiglaProvinciaValidator extends StringValidator {

    public SiglaProvinciaValidator() {
	super();
    }

    public SiglaProvinciaValidator(boolean p_required) {
	super(p_required, 2);
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof String) {
		for (int i = 0; i < listaProvince.length; i++) {
		    if ((listaProvince[i]).equalsIgnoreCase((String) p_obj))
			return true;
		}
		throw new ParserException("Invalid sigla provincia");
	    }
	}
	return false;
    }

    final static private String[] listaProvince = { "ag", "al", "an", "ao",
	    "ap", "aq", "ar", "at", "av", "ba", "bg", "bi", "bl", "bn", "bo",
	    "br", "bs", "bz", "ca", "cb", "ce", "ch", "cl", "cn", "co", "cr",
	    "cs", "ct", "cz", "en", "fe", "fg", "fi", "fo", "fr", "ge", "go",
	    "gr", "im", "is", "kr", "lc", "le", "li", "lo", "lt", "lu", "mc",
	    "me", "mi", "mn", "mo", "ms", "mt", "na", "no", "nu", "or", "pa",
	    "pc", "pd", "pe", "pg", "pi", "pn", "po", "pr", "ps", "pt", "pv",
	    "pz", "ra", "rc", "re", "rg", "ri", "rm", "rn", "ro", "sa", "si",
	    "so", "sp", "sr", "ss", "sv", "ta", "te", "tn", "to", "tp", "tr",
	    "ts", "tv", "ud", "va", "vb", "vc", "ve", "vi", "vr", "vt", "vv" };

}

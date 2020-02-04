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

public class FiscalCodeValidator extends ObjectValidator {

    public FiscalCodeValidator() {
    }

    public FiscalCodeValidator(boolean p_required) {
	super(p_required);
    }

    /**
     * 
     * @param p_obj
     *            Oggetto da validare
     * @return esito della validazione
     * @throws ParserException
     *             Se oggetto � obbligatorio e nullo restituisco false Se
     *             oggetto � NON obbligatorio e nullo restituisco true Se
     *             oggetto NON � nullo e validazione eseguita SENZA errori
     *             restituisco true Se oggetto NON � nullo e validazione
     *             eseguita con errori lancio eccezzione.
     */

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && ((String) p_obj).length() == 0))
	    return !isRequired();
	else {
	    if (p_obj != null) {
		// se oggetto non � nullo lo verifico.
		String message = ControllaCF(p_obj.toString());
		if (message.equals("")) {
		    // verifica eseguita senza errori
		    return true;
		} else {
		    // verifica eseguita con errori
		    throw new ParserException(message);
		}
	    } else {
		// se oggetto � nullo ma non obbligatorio restituisco true
		return true;
	    }
	}
    }

    private String ControllaCF(String cf) {
	int i, s, c;
	String cf2;
	int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11,
		3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	if (cf.length() == 0) {
	    return "";
	}
	if (cf.length() != 16) {
	    return "La lunghezza del codice fiscale non �\n"
		    + "corretta: il codice fiscale dovrebbe essere lungo\n"
		    + "esattamente 16 caratteri.";
	}
	cf2 = cf.toUpperCase();
	for (i = 0; i < 16; i++) {
	    c = cf2.charAt(i);
	    if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
		return "Il codice fiscale contiene dei caratteri non validi:\n"
			+ "i soli caratteri validi sono le lettere e le cifre.";
	    }
	}
	s = 0;
	for (i = 1; i <= 13; i += 2) {
	    c = cf2.charAt(i);
	    if (c >= '0' && c <= '9') {
		s = s + c - '0';
	    } else {
		s = s + c - 'A';
	    }
	}
	for (i = 0; i <= 14; i += 2) {
	    c = cf2.charAt(i);
	    if (c >= '0' && c <= '9') {
		c = c - '0' + 'A';
	    }
	    s = s + setdisp[c - 'A'];
	}
	if (s % 26 + 'A' != cf2.charAt(15)) {
	    return "Il codice fiscale non � corretto:\n"
		    + "il codice di controllo non corrisponde.";
	}
	return "";
    }

}

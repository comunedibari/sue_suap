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
 * Created by IntelliJ IDEA. User: acuffaro Date: 24-nov-2003 Time: 17.21.31 To
 * change this template use Options | File Templates.
 */
public class PartitaIvaValidator extends ObjectValidator {

    public PartitaIvaValidator() {
    }

    public PartitaIvaValidator(boolean p_required) {
	super(p_required);
    }

    /**
     * 
     * @param p_obj
     *            Oggetto da validare
     * @return esito della validazione
     * @throws it.people.parser.exception.ParserException
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
	    String message = ControllaPartitaIva(p_obj.toString());
	    if (message.equals("")) {
		// verifica eseguita senza errori
		return true;
	    } else {
		// verifica eseguita con errori
		throw new ParserException(message);
	    }
	}
    }

    private String ControllaPartitaIva(String pIva) {

	if (pIva.length() != 11)
	    return msgLunghezza;
	else if (!pIva.matches("\\d{11}"))
	    return msgSoloCifre;
	else
	    return "";
    }

    final static String msgLunghezza = "La partita IVA deve essere lunga 11 caratteri.";
    final static String msgSoloCifre = "La partita IVA pu� essere composta da soli numeri.";
}

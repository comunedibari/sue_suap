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
 * Created by IntelliJ IDEA. User: acuffaro Date: 31-ott-2003 Time: 10.26.58 To
 * change this template use Options | File Templates.
 */
public class ComboBoxOptionValidator extends ObjectValidator {
    private boolean m_ignoreCase;
    private String[] m_validValues;

    public ComboBoxOptionValidator() {
	super();
	m_ignoreCase = false;
	m_validValues = new String[0];
    }

    public ComboBoxOptionValidator(boolean required) {
	super(required);
	m_ignoreCase = false;
	m_validValues = new String[0];
    }

    public ComboBoxOptionValidator(String[] p_validStrings) {
	super();
	m_ignoreCase = false;
	m_validValues = p_validStrings;
    }

    public ComboBoxOptionValidator(boolean p_required, String[] p_validStrings) {
	super(p_required);
	m_ignoreCase = false;
	m_validValues = p_validStrings;
    }

    public ComboBoxOptionValidator(boolean p_required, boolean p_ignoreCase) {
	super(p_required);
	m_ignoreCase = p_ignoreCase;
	m_validValues = new String[0];
    }

    public ComboBoxOptionValidator(boolean p_required, boolean p_ignoreCase,
	    String[] p_validStrings) {
	super(p_required);
	m_ignoreCase = p_ignoreCase;
	m_validValues = p_validStrings;
    }

    public String[] getValidValues() {
	return m_validValues;
    }

    public void setValidValues(String[] p_validValuesArray) {
	if (p_validValuesArray != null) {
	    m_validValues = p_validValuesArray;
	}
    }

    public boolean parserValidate(Object p_obj) throws ParserException {
	if (p_obj == null
		|| (p_obj instanceof String && p_obj.toString().length() == 0))
	    return !isRequired();
	else {
	    if (p_obj instanceof String) {
		String myString = (String) p_obj;
		boolean areEqual = false;
		// SimpleDateFormat sdf = new SimpleDateFormat(m_format);
		for (int i = 0; i < m_validValues.length; i++) {
		    areEqual = (m_ignoreCase) ? myString
			    .equalsIgnoreCase(m_validValues[i]) : myString
			    .equals(m_validValues[i]);
		    if (areEqual)
			return true;
		}
		throw new ParserException("Invalid ComboBox Option Value");
	    }
	}
	return false;
    }
}

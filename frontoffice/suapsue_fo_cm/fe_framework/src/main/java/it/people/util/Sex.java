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
package it.people.util;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 19-set-2003 Time: 14.07.42 To
 * change this template use Options | File Templates.
 */

public final class Sex implements java.io.Serializable {

    private Sex(String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate Sex with null name.");

	m_type = p_i_code;
	m_name = p_str_name;
    }

    public final int getType() {
	return m_type;
    }

    public final String getName() {
	return m_name;
    }

    public static final Sex valueOf(String p_name) {
	// Mettere if per ogni status return oggetto
	if (MALE_KEY.equals(p_name))
	    return Sex.MALE;
	else if (FEMALE_KEY.equals(p_name))
	    return Sex.FEMALE;

	return null;
    }

    public static final Sex get(int p_code) {
	// Mettere if per ogni status
	switch (p_code) {
	case MALE_CODE:
	    return Sex.MALE;
	case FEMALE_CODE:
	    return Sex.FEMALE;
	default:
	    return null;
	}
    }

    public String toString() {
	return m_name;
    }

    public static final int UNKNOWN_INFORMATION_CODE = 0;
    public static final int MALE_CODE = 1;
    public static final int FEMALE_CODE = 2;

    public static final String UNKNOWN_INFORMATION_KEY = "informazione non disponibile";
    public static final String MALE_KEY = "maschile";
    public static final String FEMALE_KEY = "femminile";

    public static final Sex UNKOWN_INFORMATION = new Sex(
	    UNKNOWN_INFORMATION_KEY, UNKNOWN_INFORMATION_CODE);
    public static final Sex MALE = new Sex(MALE_KEY, MALE_CODE);
    public static final Sex FEMALE = new Sex(FEMALE_KEY, FEMALE_CODE);

    private int m_type;
    private String m_name;
    private ArrayList m_values;
}

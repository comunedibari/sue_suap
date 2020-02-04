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

public final class Device implements java.io.Serializable {

    private Device(String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate Device with null name!");

	m_i_type = p_i_code;
	m_str_name = p_str_name;
    }

    /**
     * Returns an enumeration of all possible instances of ESPermission
     **/
    public static java.util.Enumeration enumerate() {
	return null;

    } // -- java.util.Enumeration enumerate()

    public final int getType() {
	return m_i_type;
    }

    public final String getName() {
	return m_str_name;
    }

    public static final Device valueOf(String p_str_name) {
	// Mettere if per ogni device return oggetto
	if ("html".equals(p_str_name))
	    return Device.HTML;
	else if ("wml".equals(p_str_name))
	    return Device.WML;

	return null;
    }

    public static final Device get(int p_i_code) {
	// Mettere if per ogni device
	switch (p_i_code) {
	case 1:
	    return Device.HTML;
	case 2:
	    return Device.WML;
	default:
	    return null;
	}
    }

    public String toString() {
	return m_str_name;
    }

    public static final int HTML_CODE = 1;
    public static final int WML_CODE = 2;

    public static final Device HTML = new Device("html", HTML_CODE);
    public static final Device WML = new Device("wml", WML_CODE);

    private int m_i_type;
    private String m_str_name;
    private ArrayList m_values;
}

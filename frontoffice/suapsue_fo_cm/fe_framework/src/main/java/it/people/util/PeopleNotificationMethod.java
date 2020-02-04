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
 * Created by IntelliJ IDEA. User: acuffaro Date: 23-set-2003 Time: 15.53.03 To
 * change this template use Options | File Templates.
 */
public class PeopleNotificationMethod implements java.io.Serializable {

    private PeopleNotificationMethod(String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate DataHolderStatus with null name.");

	m_type = p_i_code;
	m_name = p_str_name;
    }

    public final int getType() {
	return m_type;
    }

    public final String getName() {
	return m_name;
    }

    public static final DataHolderStatus valueOf(String p_name) {
	// Mettere if per ogni status return oggetto
	if (SHOP_KEY.equals(p_name))
	    return DataHolderStatus.ASSIGNED;
	else if (AUTH_EMAIL_KEY.equals(p_name))
	    return DataHolderStatus.WORKING;
	else if (NORM_MAIL_KEY.equals(p_name))
	    return DataHolderStatus.COMPLETED;

	return null;
    }

    public static final DataHolderStatus get(int p_code) {
	// Mettere if per ogni status
	switch (p_code) {
	case SHOP_CODE:
	    return DataHolderStatus.ASSIGNED;
	case AUTH_EMAIL_CODE:
	    return DataHolderStatus.WORKING;
	case NORM_MAIL_CODE:
	    return DataHolderStatus.COMPLETED;
	default:
	    return null;
	}
    }

    public String toString() {
	return m_name;
    }

    public static final int SHOP_CODE = 1;
    public static final int AUTH_EMAIL_CODE = 2;
    public static final int NORM_MAIL_CODE = 3;

    public static final String SHOP_KEY = "S";
    public static final String AUTH_EMAIL_KEY = "E";
    public static final String NORM_MAIL_KEY = "P";

    public static final PeopleNotificationMethod SHOP = new PeopleNotificationMethod(
	    SHOP_KEY, SHOP_CODE);
    public static final PeopleNotificationMethod AUTH_EMAIL = new PeopleNotificationMethod(
	    AUTH_EMAIL_KEY, AUTH_EMAIL_CODE);
    public static final PeopleNotificationMethod NORM_MAIL = new PeopleNotificationMethod(
	    NORM_MAIL_KEY, NORM_MAIL_CODE);

    private int m_type;
    private String m_name;
    private ArrayList m_values;
}

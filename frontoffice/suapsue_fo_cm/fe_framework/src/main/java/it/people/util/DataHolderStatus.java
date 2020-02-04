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

/**
 * Created by IntelliJ IDEA.
 * User: acuffaro
 * Date: 12-set-2003
 * Time: 14.39.43
 * To change this template use Options | File Templates.
 */

import java.util.ArrayList;

public final class DataHolderStatus implements java.io.Serializable {

    private DataHolderStatus(String p_str_name, int p_i_code) {

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
	if (ASSIGNED_KEY.equals(p_name))
	    return DataHolderStatus.ASSIGNED;
	else if (WORKING_KEY.equals(p_name))
	    return DataHolderStatus.WORKING;
	else if (COMPLETED_KEY.equals(p_name))
	    return DataHolderStatus.COMPLETED;

	return null;
    }

    public static final DataHolderStatus get(int p_code) {
	// Mettere if per ogni status
	switch (p_code) {
	case ASSIGNED_CODE:
	    return DataHolderStatus.ASSIGNED;
	case WORKING_CODE:
	    return DataHolderStatus.WORKING;
	case COMPLETED_CODE:
	    return DataHolderStatus.COMPLETED;
	case ABORTED_CODE:
	    return DataHolderStatus.ABORTED;
	default:
	    return null;
	}
    }

    public String toString() {
	return m_name;
    }

    private static final int ASSIGNED_CODE = 1;
    private static final int WORKING_CODE = 2;
    private static final int COMPLETED_CODE = 3;
    private static final int ABORTED_CODE = 4;

    private static final String ASSIGNED_KEY = "assigned";
    private static final String WORKING_KEY = "working";
    private static final String COMPLETED_KEY = "completed";
    private static final String ABORTED_KEY = "aborted";

    public static final DataHolderStatus ASSIGNED = new DataHolderStatus(
	    ASSIGNED_KEY, ASSIGNED_CODE);
    public static final DataHolderStatus WORKING = new DataHolderStatus(
	    WORKING_KEY, WORKING_CODE);
    public static final DataHolderStatus COMPLETED = new DataHolderStatus(
	    COMPLETED_KEY, COMPLETED_CODE);
    public static final DataHolderStatus ABORTED = new DataHolderStatus(
	    ABORTED_KEY, ABORTED_CODE);
    private int m_type;
    private String m_name;
    private ArrayList m_values;
}

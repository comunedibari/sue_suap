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
 * Created by IntelliJ IDEA. User: acuffaro Date: 16-set-2003 Time: 17.10.44 To
 * change this template use Options | File Templates.
 */
public class PipelineHandlerType implements java.io.Serializable {

    private PipelineHandlerType(String p_str_name, int p_i_code) {

	if (p_str_name == null)
	    throw new NullPointerException(
		    "Unable to instantiate PipelineHandlerType with null name.");

	m_type = p_i_code;
	m_name = p_str_name;
    }

    public final int getType() {
	return m_type;
    }

    public final String getName() {
	return m_name;
    }

    public String toString() {
	return m_name;
    }

    public static final PipelineHandlerType valueOf(String p_name) {
	// Mettere if per ogni status return oggetto
	if (GUI_KEY.equals(p_name))
	    return PipelineHandlerType.GUI;
	else if (NOT_GUI_KEY.equals(p_name))
	    return PipelineHandlerType.NOT_GUI;

	return null;
    }

    public static final PipelineHandlerType get(int p_code) {
	// Mettere if per ogni status
	switch (p_code) {
	case GUI_CODE:
	    return PipelineHandlerType.GUI;
	case NOT_GUI_CODE:
	    return PipelineHandlerType.NOT_GUI;
	default:
	    return null;
	}
    }

    private int m_type;
    private String m_name;
    private ArrayList m_values;

    public static final int GUI_CODE = 1;
    public static final int NOT_GUI_CODE = 2;

    public static final String GUI_KEY = "gui";
    public static final String NOT_GUI_KEY = "not_gui";

    public static final PipelineHandlerType GUI = new PipelineHandlerType(
	    GUI_KEY, GUI_CODE);
    public static final PipelineHandlerType NOT_GUI = new PipelineHandlerType(
	    NOT_GUI_KEY, NOT_GUI_CODE);
}

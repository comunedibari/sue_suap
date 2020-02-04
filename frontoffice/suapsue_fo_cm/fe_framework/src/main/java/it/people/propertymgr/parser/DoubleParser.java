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
package it.people.propertymgr.parser;

import it.people.propertymgr.ApplicationPropertyParser;
import it.people.propertymgr.PropertyParseException;

/**
 * Insert the type's description here. Creation date: (31/05/2002 17.44.36)
 * 
 * @author: alberto gasparini
 */
public class DoubleParser extends ApplicationPropertyParser {
    private double m_d_max = Double.MAX_VALUE;
    private double m_d_min = Double.MIN_VALUE;

    /**
     * TimeInterval constructor comment.
     */
    public DoubleParser() {
	super();
    }

    /**
     * TimeInterval constructor comment.
     */
    public DoubleParser(double p_d_min, double p_d_max) {
	super();

	m_d_min = p_d_min;
	m_d_max = p_d_max;
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002
     * 17.44.36)
     * 
     * @return java.lang.String
     * @param p_obj_object
     *            java.lang.Object
     */
    public String doFormat(Object p_obj_object) {
	return ((Double) p_obj_object).toString();
    }

    /**
     * Insert the method's description here. Creation date: (31/05/2002
     * 17.44.36)
     * 
     * @return java.lang.Object
     * @param p_str_value
     *            java.lang.String
     */
    public Object doParse(String p_str_value) throws PropertyParseException {
	Double d = null;
	try {
	    d = Double.valueOf(p_str_value.trim());
	} catch (Exception e) {
	    throw new PropertyParseException(e.getMessage());
	}
	if (d != null && d.doubleValue() >= m_d_min
		&& d.doubleValue() <= m_d_max)
	    return d;
	else
	    throw new PropertyParseException("Value must fall between "
		    + m_d_min + " and " + m_d_max);
    }

    /**
     * Insert the method's description here. Creation date: (01/07/2002
     * 14.56.45)
     * 
     * @return java.lang.String
     */
    public String toString() {
	return this.getClass().getName()
		+ " -> Accepted values: [any double value between " + m_d_min
		+ " and " + m_d_max + " ]";
    }
}

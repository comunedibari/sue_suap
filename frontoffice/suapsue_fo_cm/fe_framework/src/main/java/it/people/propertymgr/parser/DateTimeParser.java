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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Insert the type's description here. Creation date: (31/05/2002 17.44.36)
 * 
 * @author: alberto gasparini
 */
public class DateTimeParser extends ApplicationPropertyParser {
    private final SimpleDateFormat m_obj_format;
    private Date m_obj_startDate = null;
    private Date m_obj_endDate = null;

    /**
     * TimeInterval constructor comment.
     */
    public DateTimeParser(String p_str_dateTimeFormat) {
	super();
	m_obj_format = new SimpleDateFormat(p_str_dateTimeFormat);
    }

    public DateTimeParser(String p_str_dateTimeFormat, Date p_obj_startDate,
	    Date p_obj_endDate) {
	super();
	m_obj_format = new SimpleDateFormat(p_str_dateTimeFormat);
	m_obj_startDate = p_obj_startDate;
	m_obj_endDate = p_obj_endDate;
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
	return m_obj_format.format((Date) p_obj_object);
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
	Date date = null;
	try {
	    date = m_obj_format.parse(p_str_value.trim());
	} catch (Exception e) {
	    throw new PropertyParseException(e.getMessage());
	}
	if (m_obj_startDate != null && m_obj_endDate != null) {
	    long time = date.getTime(), startTime = m_obj_startDate.getTime(), endTime = m_obj_endDate
		    .getTime();
	    if (startTime <= time && time <= endTime)
		return date;
	    else
		throw new PropertyParseException("Value must fall between "
			+ m_obj_format.format(m_obj_startDate) + " and "
			+ m_obj_format.format(m_obj_endDate) + "!");
	} else
	    return date;
    }
}

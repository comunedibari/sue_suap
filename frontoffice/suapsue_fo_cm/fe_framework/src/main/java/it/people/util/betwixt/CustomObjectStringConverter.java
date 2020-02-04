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
/**
 * 
 */
package it.people.util.betwixt;

import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.betwixt.expression.Context;
import org.apache.commons.betwixt.strategy.DefaultObjectStringConverter;

/**
 * @author Riccardo Foraf - Engineering Ingegneria Informatica - Genova Mar 22,
 *         2013 2:18:02 PM
 */
public class CustomObjectStringConverter extends DefaultObjectStringConverter {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.betwixt.strategy.DefaultObjectStringConverter#
     * objectToString(java.lang.Object, java.lang.Class, java.lang.String,
     * org.apache.commons.betwixt.expression.Context)
     */
    @Override
    public String objectToString(Object object, Class type, String flavour,
	    Context context) {
	return super.objectToString(object, type, flavour, context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.commons.betwixt.strategy.ObjectStringConverter#objectToString
     * (java.lang.Object, java.lang.Class,
     * org.apache.commons.betwixt.expression.Context)
     */
    @Override
    public String objectToString(Object object, Class type, Context context) {
	return super.objectToString(object, type, context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.betwixt.strategy.DefaultObjectStringConverter#
     * stringToObject(java.lang.String, java.lang.Class, java.lang.String,
     * org.apache.commons.betwixt.expression.Context)
     */
    @Override
    public Object stringToObject(String value, Class type, String flavour,
	    Context context) {
	Object ret = null;
	try {
	    ret = super.stringToObject(value, type, flavour, context);
	} catch (Exception e) {
	    // ignore
	}
	if (ret == null && Date.class.equals(type)) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    calendar.set(Calendar.MONTH, 0);
	    calendar.set(Calendar.YEAR, 1970);
	    ret = calendar.getTime();
	}
	return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.commons.betwixt.strategy.ObjectStringConverter#stringToObject
     * (java.lang.String, java.lang.Class,
     * org.apache.commons.betwixt.expression.Context)
     */
    @Override
    public Object stringToObject(String value, Class type, Context context) {
	Object ret = null;
	try {
	    ret = super.stringToObject(value, type, context);
	} catch (Exception e) {
	    // ignore
	}
	if (ret == null && Date.class.equals(type)) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    calendar.set(Calendar.MONTH, 0);
	    calendar.set(Calendar.YEAR, 1970);
	    ret = calendar.getTime();
	}
	return ret;
    }

}

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

import it.people.process.common.entity.Attachment;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 10, 2003 Time: 8:28:34 PM To
 * change this template use Options | File Templates.
 */
public class DataPropertyUtils {

    public static Method getSetMethod(Class clazz, String property) {
	try {
	    Class retType = clazz.getMethod("get" + capitalize(property),
		    new Class[0]).getReturnType();
	    return clazz.getMethod("set" + capitalize(property),
		    new Class[] { retType });
	} catch (Exception ex) {

	}
	return null;

    }

    public static Method getAddMethod(Class clazz, String property) {
	try {
	    Class retType = clazz.getMethod("get" + capitalize(property),
		    new Class[] { int.class }).getReturnType();
	    return clazz.getMethod("add" + capitalize(property),
		    new Class[] { retType });
	} catch (Exception ex) {

	}
	return null;
    }

    public static Method getAllegatiAddMethod(Class clazz, String property) {
	try {
	    // Class retType = clazz.getMethod("get" + capitalize(property), new
	    // Class[]{int.class}).getReturnType();
	    return clazz.getMethod("addAllegati",
		    new Class[] { Attachment.class });
	} catch (Exception ex) {

	}
	return null;
    }

    public static Method getRemoveMethod(Class clazz, String property) {
	try {
	    return clazz.getMethod("remove" + capitalize(property),
		    new Class[] { int.class });
	} catch (Exception ex) {

	}
	return null;
    }

    private static String capitalize(String propertyName) {
	if (propertyName.length() == 0) {
	    return propertyName;
	}

	char chars[] = propertyName.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return new String(chars);
    }
}

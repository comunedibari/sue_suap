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
package it.people.core.persistence.converters.sql;

import org.apache.log4j.Category;
import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

public class ClassNameConverter implements FieldConversion {
    private Category cat = Category.getInstance(ClassNameConverter.class
	    .getName());

    public Object javaToSql(Object clazz) throws ConversionException {

	if (clazz instanceof Class) {
	    return ((Class) clazz).getName();
	} else {
	    if (clazz == null)
		return clazz;
	    else {
		throw new ConversionException(
			"Unable to convert object to a Class Object");
	    }
	}
    }

    public Object sqlToJava(Object p_obj_className) throws ConversionException {

	if (p_obj_className instanceof String) {
	    try {
		return Class.forName((String) p_obj_className);
	    } catch (ClassNotFoundException cnfEx) {
		cat.error(cnfEx);
	    }
	} else {
	    if (p_obj_className == null)
		return p_obj_className;
	    else {
		throw new ConversionException("Unable to convert object Class");
	    }
	}
	return null;
    }
}

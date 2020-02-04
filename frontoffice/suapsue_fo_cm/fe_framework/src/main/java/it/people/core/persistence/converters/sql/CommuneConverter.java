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

import it.people.City;
import it.people.core.CommuneManager;
import it.people.core.persistence.exception.peopleException;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

/**
 * <p>
 * Title: Stazione Editoriale Espin
 * </p>
 * <p>
 * Description: Stazione Editoriale Espin
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: espin s.p.a.
 * </p>
 * 
 * @author alberto gasparini
 * @version 1.0
 */

public class CommuneConverter implements FieldConversion {

    public Object javaToSql(Object p_obj_commune) throws ConversionException {
	if (p_obj_commune instanceof City) {
	    return ((City) p_obj_commune).getOid();
	} else {
	    if (p_obj_commune == null)
		return p_obj_commune;
	    else {
		throw new ConversionException(
			"Unable to convert object to an City Object");
	    }
	}
    }

    public Object sqlToJava(Object p_obj_commune) throws ConversionException {

	if (p_obj_commune instanceof java.lang.String) {
	    try {
		return CommuneManager.getInstance().get((String) p_obj_commune);
	    } catch (peopleException pex) {
		return null;
	    }
	} else {
	    if (p_obj_commune == null)
		return p_obj_commune;
	    else {

		throw new ConversionException(
			"Unable to convert object to a City Object!");
	    }
	}
    }
}

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

import it.people.process.SubmittedProcessState;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 19, 2003 Time: 11:38:24 AM
 * To change this template use Options | File Templates.
 */
public class SubmittedProcessStateConverter implements FieldConversion {
    public Object javaToSql(Object o) throws ConversionException {
	if (o instanceof SubmittedProcessState)
	    return ((SubmittedProcessState) o).getCode();
	return null;
    }

    public Object sqlToJava(Object o) throws ConversionException {
	if (o instanceof Long) {
	    return SubmittedProcessState.get((Long) o);
	}
	return null;
    }
}
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
package it.people.console.persistence.converters.sql;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;
import org.apache.ojb.broker.accesslayer.conversions.FieldConversion;


public class LongConverter implements FieldConversion {
    
    public LongConverter() {
    }
    public Object javaToSql(Object source) throws ConversionException {
        if (source instanceof Number) {
            return new Long(((Number)source).longValue());
        } else {
            if (source == null)
                return source;
            else {
                throw new ConversionException("Unable to convert object to a Long");
            }
        }
    }
    public Object sqlToJava(Object source) throws ConversionException {
        if (source instanceof java.lang.Number) {
            return new Long(((java.lang.Number)source).longValue());
        } else {
            if (source == null) {
                return null;
            } else {
                throw new ConversionException("Unable to convert object to a Long");
            }
        }
    }
}
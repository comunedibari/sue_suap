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
/*
 * Created on Jul 19, 2004
 *
 */
package it.people;

import org.apache.struts.action.ActionError;

/**
 * @author thweb4
 * 
 */
public class ValidationError extends ActionError implements IValidationError {

    /**
     * @param key
     */
    public ValidationError(String key) {
	super(key);
    }

    /**
     * @param key
     * @param value0
     */
    public ValidationError(String key, Object value0) {
	super(key, value0);
    }

    /**
     * @param key
     * @param value0
     * @param value1
     */
    public ValidationError(String key, Object value0, Object value1) {
	super(key, value0, value1);
    }

    /**
     * @param key
     * @param value0
     * @param value1
     * @param value2
     */
    public ValidationError(String key, Object value0, Object value1,
	    Object value2) {
	super(key, value0, value1, value2);
    }

    /**
     * @param key
     * @param value0
     * @param value1
     * @param value2
     * @param value3
     */
    public ValidationError(String key, Object value0, Object value1,
	    Object value2, Object value3) {
	super(key, value0, value1, value2, value3);
    }

    /**
     * @param key
     * @param values
     */
    public ValidationError(String key, Object[] values) {
	super(key, values);
    }

    public String getKey() {
	return super.getKey();
    }

    public Object[] getValues() {
	return super.getValues();
    }

}

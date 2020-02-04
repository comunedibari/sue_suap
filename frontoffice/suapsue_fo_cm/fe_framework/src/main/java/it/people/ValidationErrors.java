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

import java.util.Iterator;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * @author thweb4
 * 
 */
public class ValidationErrors extends ActionErrors implements IValidationErrors {

    public ValidationErrors() {
	super();
    }

    public void add(String key) {
	ValidationError error = new ValidationError(key);
	super.add(key, error);
    }

    public void add(String key, Object value0) {
	ValidationError error = new ValidationError(key, value0);
	super.add(key, error);
    }

    public void add(String key, Object value0, Object value1) {
	ValidationError error = new ValidationError(key, value0, value1);
	super.add(key, error);
    }

    public void add(String key, Object value0, Object value1, Object value2) {
	ValidationError error = new ValidationError(key, value0, value1, value2);
	super.add(key, error);
    }

    public void add(String key, Object value0, Object value1, Object value2,
	    Object value3) {
	ValidationError error = new ValidationError(key, value0, value1,
		value2, value3);
	super.add(key, error);
    }

    public void add(String key, Object[] values) {
	ValidationError error = new ValidationError(key, values);
	super.add(key, error);
    }

    public int getSize() {
	return super.size();
    }

    public Iterator getIterator() {
	return super.get();
    }

    public boolean isEmpty() {
	return super.isEmpty();
    }

    /**
     * Rimuove gli errori di tipo required.
     */
    public void removeRequired() {
	Iterator iter = this.messages.values().iterator();
	while (iter.hasNext()) {
	    ActionMessageItem messageItem = (ActionMessageItem) iter.next();
	    String property = messageItem.getProperty();
	    Iterator errorsMessage = this.get(property);
	    while (errorsMessage.hasNext()) {
		ActionMessage tmpError = (ActionMessage) errorsMessage.next();
		if (tmpError != null
			&& tmpError.getKey() != null
			&& tmpError.getKey()
				.equalsIgnoreCase("errors.required")) {
		    iter.remove();
		    break;
		}
	    }
	}
    }
}

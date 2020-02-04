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
package it.people.console.validation;

import java.net.URL;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/gen/2011 18.44.56
 *
 */
public class ValidationUtils extends
		org.springframework.validation.ValidationUtils {

	public static void rejectIfInvalidURL(Errors errors, String field, String errorCode, Object[] errorArgs) {
		rejectIfInvalidURL(errors, field, errorCode, errorArgs, null);
	}
	
	public static void rejectIfInvalidURL(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		boolean hasError = false;
		
		try {
			@SuppressWarnings("unused")
			URL url = new URL(String.valueOf(value));
		} catch (Exception e) {
			hasError = true;
		}
		
		if (hasError) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
		
	}

}

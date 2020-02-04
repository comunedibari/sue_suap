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
package it.people.console.web.controllers.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.people.console.domain.AccreditamentiQualifica;
import it.people.console.domain.AccreditamentiQualificheFilter;
import it.people.console.domain.FENode;
import it.people.console.utils.StringUtils;
import it.people.console.validation.MessageSourceAwareValidator;
import it.people.console.validation.ValidationUtils;

/**
 * @author Luca Barbieri - Pradac Informatica S.r.l.
 * @created 15/nov/2011 15.28.43
 *
 */
public class AccreditamentiQualificheValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == FENode.class;
	}

	public void validate(Object command, Errors errors) {}
	
	public void validateNew(Object command, Errors errors) {
		
		
		AccreditamentiQualificheFilter filter = (AccreditamentiQualificheFilter)command;
		AccreditamentiQualifica qualifica = filter.getQualifica();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qualifica.id_qualifica", 
				"error.accreditamenti.qualifiche.empty", 
				new String[] {this.getProperty("accreditamentiQualifiche.label.id_qualifica")});
		
		if (StringUtils.isEmptyString(qualifica.getDescrizione())) {
			errors.rejectValue("qualifica.descrizione", "error.accreditamenti.qualifiche.empty", 
					new String[] {this.getProperty("accreditamentiQualifiche.label.descrizione")}, null);
		}
	}

	public void validateUpdate(Object command, Errors errors) {
		
		AccreditamentiQualifica qualifica = (AccreditamentiQualifica)command;
		
		if (StringUtils.isEmptyString(qualifica.getDescrizione())) {
			errors.rejectValue("descrizione", "error.accreditamenti.qualifiche.empty", 
					new String[] {this.getProperty("accreditamentiQualifiche.label.descrizione")}, null);
		}
		
		/*
		if (StringUtils.isEmptyString(feNode.getFeServiceURL())) {
			errors.rejectValue("feServiceURL", "error.fenodes.listAndAdd.nodeFEWSURL.empty", 
					new String[] {this.getProperty("fenodes.listAndAdd.nodeFEWSURL")}, null);
		}
		else {
			ValidationUtils.rejectIfInvalidURL(errors, "feServiceURL", 
					"error.fenodes.listAndAdd.nodeFEWSURL.wrong", 
					new String[] {this.getProperty("fenodes.listAndAdd.nodeFEWSURL")});
		}
		*/	
	}
	
}

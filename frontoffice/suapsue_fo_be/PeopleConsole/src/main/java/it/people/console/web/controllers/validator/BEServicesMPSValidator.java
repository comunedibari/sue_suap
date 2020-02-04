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

import it.people.console.domain.BEServicesMassiveParametersSettings;
import it.people.console.validation.MessageSourceAwareValidator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import it.people.console.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.apache.commons.validator.GenericValidator;

public class BEServicesMPSValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == BEServicesMassiveParametersSettings.class;
	}

	public void validate(Object command, Errors errors) {
		
		BEServicesMassiveParametersSettings beServicesMassiveParametersSettings = 
			(BEServicesMassiveParametersSettings)command;
		
		switch (beServicesMassiveParametersSettings.getPage()) {
			case 1:
				this.validatePage1(beServicesMassiveParametersSettings, errors);
				break;
			case 2:
				this.validatePage2(beServicesMassiveParametersSettings, errors);
				break;
			case 3:
				this.validatePage3(beServicesMassiveParametersSettings, errors);
				break;
		}
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taxCode", "error.field.empty", 
//			new String[] {this.getProperty("admin.accounts.listAndAdd.taxCode")});
//		
//		if (errors.getFieldErrorCount("taxCode") == 0 && !regExpUtils.matchTaxCode(userAccount.getTaxCode())) {
//			errors.rejectValue("taxCode", "error.field.malformed", 
//					new String[] {this.getProperty("admin.accounts.listAndAdd.taxCode")}, "");
//		}
//		
//		if (userAccount == null || (userAccount.getRoles() != null && userAccount.getRoles().length == 0)) {
//			errors.rejectValue("roles", "error.admin.accounts.add.noSelectedRoles");
//		}
		
		
	}

	private void validatePage1(BEServicesMassiveParametersSettings 
			beServicesMassiveParametersSettings, Errors errors) {
		
		if (beServicesMassiveParametersSettings.getSelectedNodes() == null || (
				beServicesMassiveParametersSettings.getSelectedNodes() != null && beServicesMassiveParametersSettings.getSelectedNodes().isEmpty())) {
			errors.rejectValue("selectedNodes", "error.beservices.massiveParametersSettings.noSelectedNodes");
		}
		
	}

	private void validatePage2(BEServicesMassiveParametersSettings 
			beServicesMassiveParametersSettings, Errors errors) {
		
	}

	private void validatePage3(BEServicesMassiveParametersSettings 
			beServicesMassiveParametersSettings, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newUrlHost", "error.field.empty", 
				new String[] {this.getProperty("beservices.massiveParametersSettings.page3.beUrlHost")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newUrlPort", "error.field.empty", 
				new String[] {this.getProperty("beservices.massiveParametersSettings.page3.beUrlPort")});
		
		if (!GenericValidator.isUrl(beServicesMassiveParametersSettings.getNewUrlSchema() + "://" + 
				beServicesMassiveParametersSettings.getNewUrlHost())) {
			errors.rejectValue("newUrlHost", "error.field.malformed", 
				new String[] {
					this.getProperty("beservices.massiveParametersSettings.page3.beUrlHost")}, "");
		}
		
		if (!GenericValidator.isBlankOrNull(beServicesMassiveParametersSettings.getNewUrlPort()) && 
				!GenericValidator.isInt(beServicesMassiveParametersSettings.getNewUrlPort())) {
			errors.rejectValue("newUrlPort", "error.field.malformed", 
					new String[] {
						this.getProperty("beservices.massiveParametersSettings.page3.beUrlPort")}, "");
		}
		
	}
	
}

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

import it.people.console.config.ConsoleConfiguration;
import it.people.console.dto.CertificateAccountDTO;
import it.people.console.utils.Constants;
import it.people.console.utils.IRegExpUtils;
import it.people.console.validation.MessageSourceAwareValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/gen/2011 17.10.55
 *
 */
@Service
public class CertificatesManagerValidator extends MessageSourceAwareValidator implements Validator {

	@Autowired
	private IRegExpUtils regExpUtils;
	
	public boolean supports(Class<?> clazz) {
		return clazz == CertificateAccountDTO.class;
	}

	public void validate(Object command, Errors errors) {

		ConsoleConfiguration consoleConfiguration = (ConsoleConfiguration)
			this.getServletContext().getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
		
		CertificateAccountDTO certificateAccountDTO = (CertificateAccountDTO)command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "alias", "error.field.empty", 
			new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		if (!certificateAccountDTO.isPasswordAutogeneration()) {
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.field.empty", 
					new String[] {this.getProperty("admin.certificates.generation.password.label")});
	
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordCheck", "error.field.empty", 
					new String[] {this.getProperty("admin.certificates.generation.alias.label")});
		
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commonName", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationUnit", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "locality", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stateName", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eMail", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "validity", "error.field.empty", 
				new String[] {this.getProperty("admin.certificates.generation.alias.label")});

		if (!regExpUtils.matchEMailPattern(certificateAccountDTO.geteMail())) {
			errors.rejectValue("eMail", "error.field.malformed", 
					new String[] {this.getProperty("admin.certificates.generation.eMail.label")}, "");
		}
		
		if (certificateAccountDTO.getAllowedNodes() == null || (certificateAccountDTO.getAllowedNodes() != null && 
				certificateAccountDTO.getAllowedNodes().length == 0)) {
			errors.rejectValue("allowedNodes", "error.admin.certificates.generation.allowedNodes.empty", 
					new String[] {this.getProperty("admin.certificates.generation.allowedNodes.label")}, "");
		}
		
		if (!certificateAccountDTO.isPasswordAutogeneration()) {
		
			if (certificateAccountDTO.getPassword().length() < consoleConfiguration.getRootPasswordMinLength()) {
				errors.rejectValue("password", "error.field.length", 
						new String[] {this.getProperty("admin.certificates.generation.password.label"), 
						String.valueOf(consoleConfiguration.getRootPasswordMinLength())}, "");
			}
	
			if (certificateAccountDTO.getPasswordCheck().length() < consoleConfiguration.getRootPasswordMinLength()) {
				errors.rejectValue("passwordCheck", "error.field.length", 
						new String[] {this.getProperty("admin.certificates.generation.passwordCheck.label"), 
						String.valueOf(consoleConfiguration.getRootPasswordMinLength())}, "");
			}
			
			boolean passwordValid = errors.getFieldErrorCount("password") == 0;
			boolean passwordCheckValid = errors.getFieldErrorCount("passwordCheck") == 0;
		
			if (passwordValid && passwordCheckValid && 
					!certificateAccountDTO.getPassword().trim().equalsIgnoreCase(certificateAccountDTO.getPasswordCheck().trim())) {
				errors.rejectValue("passwordCheck", "error.root.login.passwordCheck.notMatch");
			}			
		
		}			
		
	}

}

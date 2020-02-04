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

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import it.people.console.beans.Root;
import it.people.console.config.ConsoleConfiguration;
import it.people.console.domain.RootLogin;
import it.people.console.security.ICryptoUtils;
import it.people.console.security.exceptions.CryptoUtilsException;
import it.people.console.security.ru.IRUManager;
import it.people.console.utils.Constants;
import it.people.console.utils.IRegExpUtils;
import it.people.console.validation.MessageSourceAwareValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import it.people.console.validation.ValidationUtils;
import it.people.console.web.controllers.exceptions.RootObjectException;

import org.springframework.validation.Validator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 18.20.27
 *
 */
@Service
public class RootLoginValidator extends MessageSourceAwareValidator implements Validator {

	@Autowired
	private ICryptoUtils cryptoUtils;

	@Autowired
	private IRUManager ruManager;
	
	@Autowired
	private IRegExpUtils regExpUtils;
	
	public boolean supports(Class<?> clazz) {
		return clazz == RootLogin.class;
	}

	public void validate(Object command, Errors errors) {
		
		ConsoleConfiguration consoleConfiguration = (ConsoleConfiguration)
			this.getServletContext().getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
		
		RootLogin rootLogin = (RootLogin)command;
		
		if (!rootLogin.isNeedUpdate()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", 
					"error.root.login.password.empty");
			boolean passwordValid = errors.getFieldErrorCount("password") == 0;
			if (passwordValid) {
				try {
					if (!validRootPassword(rootLogin.getPassword())) {
						errors.rejectValue("password", "error.login.password.notValid");
					}
				} catch (RootObjectException e) {
					errors.reject("error.generic");
				}
			}
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", 
				"error.field.empty", 
				new String[] {this.getProperty("root.login.update.firstName.label")});

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", 
					"error.field.empty", 
					new String[] {this.getProperty("root.login.update.lastName.label")});
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eMail", 
					"error.field.empty", 
					new String[] {this.getProperty("root.login.update.eMail.label")});

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", 
				"error.root.login.password.empty");

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordCheck", 
				"error.root.login.passwordCheck.empty");

			if (rootLogin.getPassword().length() < consoleConfiguration.getRootPasswordMinLength()) {
				errors.rejectValue("password", "error.field.length", 
						new String[] {this.getProperty("root.login.password.label"), 
						String.valueOf(consoleConfiguration.getRootPasswordMinLength())}, "");
			}

			if (rootLogin.getPasswordCheck().length() < consoleConfiguration.getRootPasswordMinLength()) {
				errors.rejectValue("passwordCheck", "error.field.length", 
						new String[] {this.getProperty("root.login.passwordCheck.label"), 
						String.valueOf(consoleConfiguration.getRootPasswordMinLength())}, "");
			}
			
			boolean passwordValid = errors.getFieldErrorCount("password") == 0;
			boolean passwordCheckValid = errors.getFieldErrorCount("passwordCheck") == 0;
			
			if (!regExpUtils.matchEMailPattern(rootLogin.geteMail())) {
				errors.rejectValue("eMail", "error.field.malformed", 
						new String[] {this.getProperty("root.login.update.eMail.label")}, "");
			}

			if (passwordValid && passwordCheckValid && 
					!rootLogin.getPassword().trim().equalsIgnoreCase(rootLogin.getPasswordCheck().trim())) {
				errors.rejectValue("passwordCheck", "error.root.login.passwordCheck.notMatch");
			}			
			
		}
		
	}

	/**
	 * @return
	 * @throws RootObjectException
	 */
	private String getActualRootPassword() throws RootObjectException {
		
		String result = null;
		
		try {
			Root root = ruManager.getRootUser("zukowski");
			result = root.getPassword();
		} catch (StreamCorruptedException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeyException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		} catch (InvalidKeySpecException e) {
			throw new RootObjectException(e);
		} catch (NoSuchPaddingException e) {
			throw new RootObjectException(e);
		} catch (CryptoUtilsException e) {
			throw new RootObjectException(e);
		} catch (IOException e) {
			throw new RootObjectException(e);
		} catch (ClassNotFoundException e) {
			throw new RootObjectException(e);
		}
		
		return result;
		
	}
	
	/**
	 * @param loginPassword
	 * @return
	 * @throws RootObjectException
	 */
	private Boolean validRootPassword(String loginPassword) throws RootObjectException {
		
		Boolean result = null;
		
		try {
			result = cryptoUtils.validRootPassword(getActualRootPassword(), loginPassword);
		} catch (UnsupportedEncodingException e) {
			throw new RootObjectException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RootObjectException(e);
		}
		
		return result;
		
	}
	
}

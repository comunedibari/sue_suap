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

import it.people.console.domain.BEService;
import it.people.console.persistence.IPersistenceBroker;
import it.people.console.persistence.exceptions.PersistenceBrokerException;
import it.people.console.utils.StringUtils;
import it.people.console.validation.MessageSourceAwareValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import it.people.console.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/gen/2011 17.10.55
 *
 */
@Service
public class BeServiceValidator extends MessageSourceAwareValidator implements Validator {

	@Autowired
	private IPersistenceBroker persistenceBroker;
	
	public boolean supports(Class<?> clazz) {
		return clazz == BEService.class;
	}

	public void validate(Object command, Errors errors) {
		
		BEService beService = (BEService)command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "logicalServiceName", 
				"error.beservices.listAndAdd.logicalName.empty", 
				new String[] {this.getProperty("beservices.list.logicalName")});
		
		if (StringUtils.isEmptyString(beService.getBackEndURL())) {
			errors.rejectValue("backEndURL", "error.beservices.listAndAdd.backEndURL.empty", 
					new String[] {this.getProperty("beservices.list.beUrl")}, null);
		}
		else {
			ValidationUtils.rejectIfInvalidURL(errors, "backEndURL", 
					"error.beservices.listAndAdd.backEndURL.wrong", 
					new String[] {this.getProperty("beservices.list.beUrl")});
		}
		try {
			if (persistenceBroker.isRegisteredBeNode(beService.getLogicalServiceName(), beService.getSelectedNodeId().intValue())) {
				errors.rejectValue("logicalServiceName", "error.beservices.listAndAdd.service.exists", 
						new String[] {this.getProperty("beservices.list.logicalName")}, null);
			}
		} catch (PersistenceBrokerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void validateUpdate(Object command, Errors errors) {
		
		BEService beService = (BEService)command;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "logicalServiceName", 
				"error.beservices.listAndAdd.logicalName.empty", 
				new String[] {this.getProperty("beservices.list.logicalName")});
		
		if (StringUtils.isEmptyString(beService.getBackEndURL())) {
			errors.rejectValue("backEndURL", "error.beservices.listAndAdd.backEndURL.empty", 
					new String[] {this.getProperty("beservices.list.beUrl")}, null);
		}
		else {
			ValidationUtils.rejectIfInvalidURL(errors, "backEndURL", 
					"error.beservices.listAndAdd.backEndURL.wrong", 
					new String[] {this.getProperty("beservices.list.beUrl")});
		}
	}
	
	

}

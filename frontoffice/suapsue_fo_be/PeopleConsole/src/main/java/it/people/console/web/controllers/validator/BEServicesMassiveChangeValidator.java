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

import it.people.console.domain.BEServicesMassiveChange;
import it.people.console.domain.FEServicesMassiveChange;
import it.people.console.utils.IRegExpUtils;
import it.people.console.utils.StringUtils;
import it.people.console.validation.MessageSourceAwareValidator;

import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/gen/2011 17.10.55
 *
 */
@Service
public class BEServicesMassiveChangeValidator extends MessageSourceAwareValidator implements Validator {

	@Autowired
	private IRegExpUtils regExpUtils;
	
	public boolean supports(Class<?> clazz) {
		return clazz == FEServicesMassiveChange.class;
	}

	public void validate(Object command, Errors errors) {
		
	}
	
	public void validate(Object command, Errors errors, boolean isChangeToNodeType, String action) {
		
		BEServicesMassiveChange beServicesParametersMassiveChange = (BEServicesMassiveChange)command;

		switch(beServicesParametersMassiveChange.getPage()) {
		case 1:

			break;
		case 2:
			
			if ((beServicesParametersMassiveChange.getSelectedServicesId()  == null || (beServicesParametersMassiveChange.getSelectedServicesId() != null && 
					beServicesParametersMassiveChange.getSelectedServicesId().isEmpty())) && (beServicesParametersMassiveChange.getSelectedNodes() == null || (
							beServicesParametersMassiveChange.getSelectedNodes() != null && beServicesParametersMassiveChange.getSelectedNodes().isEmpty()))) {
				errors.rejectValue("selectedServicesId", 
						"error.beservices.massiveChange.invalidChoosedServices.empty");					
			}
			
			break;
		case 3:
			
			if (!beServicesParametersMassiveChange.isAdvancedUrlSubstitution()) {
				if (!StringUtils.isEmptyString(beServicesParametersMassiveChange.getHost()) && !regExpUtils.matchValidHostName(beServicesParametersMassiveChange.getHost())) {
					errors.rejectValue("host", "error.field.malformed", 
							new String[] {this.getProperty("beservice.massiveChange.page3.hostName")}, "");				
				}
				if (!StringUtils.isEmptyString(beServicesParametersMassiveChange.getPort()) 
						&& !IntegerValidator.getInstance().isValid(beServicesParametersMassiveChange.getPort()) 
						&& !IntegerValidator.getInstance().minValue(Integer.parseInt(beServicesParametersMassiveChange.getPort()), 1)) {
					errors.rejectValue("port", "error.field.invalid", 
							new String[] {this.getProperty("beservice.massiveChange.page3.port")}, "");				
				}
			} else {
				
			}
			
			break;
		}
		
	}

}

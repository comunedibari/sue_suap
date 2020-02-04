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

import java.util.Collection;

import it.people.console.domain.FENode;
import it.people.console.domain.FENodeQuery;
import it.people.console.domain.FENodeToNodeCopy;
import it.people.console.domain.FEService;
import it.people.console.persistence.IPersistenceManager;
import it.people.console.persistence.PersistenceManagerFactory;
import it.people.console.persistence.AbstractPersistenceManager.Mode;
import it.people.console.persistence.exceptions.PersistenceManagerException;
import it.people.console.utils.StringUtils;
import it.people.console.validation.MessageSourceAwareValidator;

import org.apache.ojb.broker.query.QueryByCriteria;
import org.springframework.validation.Errors;
import it.people.console.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 03/gen/2011 17.10.55
 *
 */
public class FENodeToNodeCopyValidator extends MessageSourceAwareValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == FENodeToNodeCopy.class;
	}

	public void validate(Object command, Errors errors) {
		
	}
	
	public void validate(Object command, Errors errors, boolean isChangeToNodeType) {
		
		FENodeToNodeCopy feNodeToNodeCopy = (FENodeToNodeCopy)command;

		switch(feNodeToNodeCopy.getPage()) {
		case 1:
			if (feNodeToNodeCopy.getSelectedFromNodeId() == feNodeToNodeCopy.getSelectedToNodeId() 
					&& !feNodeToNodeCopy.isToNewNode() && !isChangeToNodeType) {
				errors.rejectValue("invalidChosedNodes", 
						"error.feservices.nodeToNodeCopy.invalidChoosedNodes.same", null);					
			}

			if (feNodeToNodeCopy.isToNewNode() && !isChangeToNodeType) {

				IPersistenceManager feNodesPersistenceManager = PersistenceManagerFactory.getInstance()
				.get(FENode.class, Mode.READ);

				FENodeQuery searchFeNode = new FENodeQuery();
				searchFeNode.clear();
				searchFeNode.setFeServiceURL(feNodeToNodeCopy.getNewFeNode().getFeServiceURL());
				searchFeNode.setMunicipalityCode(feNodeToNodeCopy.getNewFeNode().getMunicipalityCode());

				QueryByCriteria queryByCriteria = new QueryByCriteria(searchFeNode);
				Collection<?> retriviedFeNodes;
				try {
					retriviedFeNodes = feNodesPersistenceManager.get(queryByCriteria);
					if (retriviedFeNodes.size() > 0) {
						errors.rejectValue("invalidChosedNodes", "error.feservices.nodeToNodeCopy.toNewNode.exists", null);
					}
				} catch (PersistenceManagerException e) {
					e.printStackTrace();
				}
				finally {
					feNodesPersistenceManager.close();
				}

				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newFeNode.name", 
						"error.fenodes.listAndAdd.nodeName.empty", 
						new String[] {this.getProperty("fenodes.listAndAdd.nodeName")});

				if (StringUtils.isEmptyString(feNodeToNodeCopy.getNewFeNode().getFeServiceURL())) {
					errors.rejectValue("newFeNode.feServiceURL", "error.fenodes.listAndAdd.nodeFEWSURL.empty", 
							new String[] {this.getProperty("fenodes.listAndAdd.nodeFEWSURL")}, null);
				}
				else {
					ValidationUtils.rejectIfInvalidURL(errors, "newFeNode.feServiceURL", 
							"error.fenodes.listAndAdd.nodeFEWSURL.wrong", 
							new String[] {this.getProperty("fenodes.listAndAdd.nodeFEWSURL")});
				}

			}

			break;
		case 2:
			if (feNodeToNodeCopy.getSelectedServicesPackages().isEmpty() && 
					feNodeToNodeCopy.getSelectedAreas().isEmpty()) {
				errors.rejectValue("selectedServicesPackages", 
						"error.feservices.nodeToNodeCopy.selectedServices.empty", null);					
			}
			break;
		case 3:
			break;
		}
		
	}

}

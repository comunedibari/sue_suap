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
 * Created on 27-apr-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.validator;

import it.people.City;
import it.people.DtoStep;
import it.people.IStep;
import it.people.Step;
import it.people.core.Logger;
import it.people.process.AbstractPplProcess;
import it.people.process.dto.PeopleDto;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorResources;
import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

/**
 * @author Zoppello
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PeopleValidatorUtil extends Resources {
    public static Validator initValidator(ValidatorResources resources,
	    String key, Object bean, ServletContext application,
	    HttpServletRequest request, ActionErrors errors, int page) {
	Locale locale = getLocale(request);
	Validator validator = new Validator(resources, key);
	validator.setUseContextClassLoader(true);
	validator.setPage(page);
	validator.addResource(SERVLET_CONTEXT_KEY, application);
	validator.addResource(HTTP_SERVLET_REQUEST_KEY, request);
	validator.addResource(Validator.LOCALE_KEY, locale);
	validator.addResource(ACTION_ERRORS_KEY, errors);
	validator.addResource(Validator.BEAN_KEY, bean);
	return validator;
    }

    public static Validator getValidatorForStep(IStep step,
	    AbstractPplProcess process, ServletContext application,
	    HttpServletRequest request, ActionErrors errors) {

	Category cat = Category
		.getInstance(PeopleValidatorUtil.class.getName());

	Object data = null;
	// if ( (step.getDto()==null) || (step.getDto()!=null &&
	// step.getDto().equals(""))){
	if (step instanceof DtoStep) {
	    data = (PeopleDto) process.get("dto");
	} else {
	    data = process.getData();
	}

	City city = process.getCommune();

	if (cat.isDebugEnabled())
	    cat.debug("PeopleValidatorUtil::getValidatorForStep() - carico il validator");

	ValidatorResources validatorResources = ValidatorLoader.getInstance()
		.getResourcesForProcess(process, city);

	if (cat.isDebugEnabled())
	    cat.debug("PeopleValidatorUtil::getValidatorForStep() - validatorResources caricato");

	String stepIdentifier = step.getFullIdentifier();

	if (cat.isDebugEnabled())
	    cat.debug("PeopleValidatorUtil::getValidatorForStep() - inizializzazione validator");

	Validator validator = PeopleValidatorUtil.initValidator(
		validatorResources, step.getFullIdentifier(), data,
		application, request, errors, 0);

	if (cat.isDebugEnabled())
	    cat.debug("PeopleValidatorUtil::getValidatorForStep() - validator inizializzato");

	return validator;
    }
}

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

import it.people.PeopleConstants;
import it.people.parser.FiscalCodeValidator;
import it.people.parser.PartitaIvaValidator;
import it.people.parser.exception.ParserException;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;

import org.apache.struts.validator.Resources;

/**
 * @author Andrea Zoppello
 * 
 *         Questa classe implementa quelle regole di validazione necessarie al
 *         framework people che non esistono nei validatori standard di struts.
 */
public class PeopleValidator implements Serializable {

    public PeopleValidator() {
    }

    public static boolean validateFiscalCode(Object bean, ValidatorAction va,
	    Field field, ActionMessages errors, Validator validator,
	    HttpServletRequest request) {
	String value = null;
	if (isString(bean))
	    value = (String) bean;
	else
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());

	// Delego il controllo at it.people.parser.FiscalCodeValidator
	FiscalCodeValidator aFiscalCodeValidator = new FiscalCodeValidator();
	boolean result = true;
	try {
	    result = aFiscalCodeValidator.parserValidate(value);

	} catch (ParserException pe) {
	    errors.add(field.getKey(),
		    Resources.getActionMessage(validator, request, va, field));
	    return false;
	}
	return true;
    }

    public static boolean validatePartitaIva(Object bean, ValidatorAction va,
	    Field field, ActionMessages errors, Validator validator,
	    HttpServletRequest request) {
	String value = null;
	if (isString(bean))
	    value = (String) bean;
	else
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());

	// Delego il controllo at it.people.parser.FiscalCodeValidator
	PartitaIvaValidator aPartitaIvaValidator = new PartitaIvaValidator();
	boolean result = true;
	try {
	    result = aPartitaIvaValidator.parserValidate(value);
	} catch (ParserException pe) {
	    errors.add(field.getKey(),
		    Resources.getActionMessage(validator, request, va, field));
	    return false;
	}
	return true;
    }

    public static boolean validateBoolean(Object bean, ValidatorAction va,
	    Field field, ActionMessages errors, Validator validator,
	    HttpServletRequest request) {

	String value = null;

	if (isString(bean))
	    value = (String) bean;
	else
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());

	// The boolean value is stored as a String
	if (field.getProperty() != null && field.getProperty().length() > 0) {
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	}
	Boolean result = Boolean.valueOf(value);
	if (result == null) {
	    errors.add(field.getKey(),
		    Resources.getActionMessage(validator, request, va, field));
	}
	// Return true if the value was successfully converted,false otherwise
	return (errors.isEmpty());
    }

    private static boolean isString(Object o) {
	if (o == null)
	    return true;
	else
	    return (java.lang.String.class).isInstance(o);
    }

    /**
     * Checks if the field can safely be converted to a double primitive
     * according to locale present in request </p> if locale is not present in
     * request ITALIAN LOCALE is assumed as default
     * 
     * Nuova versione per struts 1.2.8
     * 
     * @author FabMi
     * 
     */
    public static Double validateLocalizedDouble(Object bean,
	    ValidatorAction va, Field field, ActionMessages errors,
	    Validator validator, HttpServletRequest request) {

	Double result = null;
	String value = null;
	if (isString(bean)) {
	    value = (String) bean;
	} else {
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	}

	if (!GenericValidator.isBlankOrNull(value)) {
	    Locale locale = (Locale) request.getSession().getAttribute(
		    PeopleConstants.USER_LOCALE_KEY);
	    if (locale == null) {
		locale = Locale.ITALY;
	    }

	    // Parse an Italian double
	    try {
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		Number number = nf.parse(value);
		result = new Double(number.doubleValue());
	    } catch (ParseException e) {

		result = null;
	    }

	    if (result == null) {
		errors.add(field.getKey(), Resources.getActionMessage(
			validator, request, va, field));
	    }
	}

	return result;
    }

    /**
     * Nuova versione per struts 1.2.8
     * 
     * @author FabMi
     * 
     */
    public static boolean validateLocalizedDoubleRange(Object bean,
	    ValidatorAction va, Field field, ActionMessages errors,
	    Validator validator, HttpServletRequest request) {

	String value = null;
	if (isString(bean)) {
	    value = (String) bean;
	} else {
	    value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	}

	if (!GenericValidator.isBlankOrNull(value)) {
	    try {
		Locale locale = (Locale) request.getSession().getAttribute(
			PeopleConstants.USER_LOCALE_KEY);
		if (locale == null) {
		    locale = Locale.ITALY;
		}

		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		Number number = nf.parse(value);
		double doubleValue = number.doubleValue();

		number = nf.parse(field.getVarValue("min"));
		double min = number.doubleValue();

		number = nf.parse(field.getVarValue("max"));
		double max = number.doubleValue();
		if (!GenericValidator.isInRange(doubleValue, min, max)) {
		    errors.add(field.getKey(), Resources.getActionMessage(
			    validator, request, va, field));

		    return false;
		} else {
		    return true;
		}
	    } catch (Exception e) {
		errors.add(field.getKey(), Resources.getActionMessage(
			validator, request, va, field));
		return false;
	    }
	}
	return true;
    }

}

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
 * Created on Jul 19, 2004
 *
 */
package it.people;

import java.util.Locale;

import org.apache.commons.validator.GenericValidator;

/**
 * @author thweb4
 * 
 */
public class Validator extends GenericValidator {

    public static boolean isBlankOrNull(String value) {
	return GenericValidator.isBlankOrNull(value);
    }

    /**
     * <p>
     * Checks if the value matches the regular expression.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param regexp
     *            The regular expression.
     */
    public static boolean matchRegexp(String value, String regexp) {
	return GenericValidator.matchRegexp(value, regexp);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a byte primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isByte(String value) {
	return GenericValidator.isByte(value);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a short primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isShort(String value) {
	return GenericValidator.isShort(value);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a int primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isInt(String value) {
	return GenericValidator.isInt(value);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a long primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isLong(String value) {
	return GenericValidator.isLong(value);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a float primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isFloat(String value) {
	return GenericValidator.isFloat(value);
    }

    /**
     * <p>
     * Checks if the value can safely be converted to a double primitive.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isDouble(String value) {
	return GenericValidator.isDouble(value);
    }

    /**
     * <p>
     * Checks if the field is a valid date. The <code>Locale</code> is used with
     * <code>java.text.DateFormat</code>. The setLenient method is set to
     * <code>false</code> for all.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param Locale
     *            The locale to use for the date format, defaults to the default
     *            system default if null.
     */
    public static boolean isDate(String value, Locale locale) {
	return GenericValidator.isDate(value, locale);
    }

    /**
     * <p>
     * Checks if the field is a valid date. The pattern is used with
     * <code>java.text.SimpleDateFormat</code>. If strict is true, then the
     * length will be checked so '2/12/1999' will not pass validation with the
     * format 'MM/dd/yyyy' because the month isn't two digits. The setLenient
     * method is set to <code>false</code> for all.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param datePattern
     *            The pattern passed to <code>SimpleDateFormat</code>.
     * @param strict
     *            Whether or not to have an exact match of the datePattern.
     */
    public static boolean isDate(String value, String datePattern,
	    boolean strict) {
	return GenericValidator.isDate(value, datePattern, strict);
    }

    /**
     * <p>
     * Checks if a value is within a range (min &amp; max specified in the vars
     * attribute).
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param min
     *            The minimum value of the range.
     * @param max
     *            The maximum value of the range.
     */
    public static boolean isInRange(int value, int min, int max) {
	return GenericValidator.isInRange(value, min, max);
    }

    /**
     * <p>
     * Checks if a value is within a range (min &amp; max specified in the vars
     * attribute).
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param min
     *            The minimum value of the range.
     * @param max
     *            The maximum value of the range.
     */
    public static boolean isInRange(float value, float min, float max) {
	return GenericValidator.isInRange(value, min, max);
    }

    /**
     * <p>
     * Checks if a value is within a range (min &amp; max specified in the vars
     * attribute).
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param min
     *            The minimum value of the range.
     * @param max
     *            The maximum value of the range.
     */
    public static boolean isInRange(short value, short min, short max) {
	return GenericValidator.isInRange(value, min, max);
    }

    /**
     * <p>
     * Checks if a value is within a range (min &amp; max specified in the vars
     * attribute).
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param min
     *            The minimum value of the range.
     * @param max
     *            The maximum value of the range.
     */
    public static boolean isInRange(double value, double min, double max) {
	return GenericValidator.isInRange(value, min, max);
    }

    /**
     * <p>
     * Checks if the field is a valid credit card number.
     * </p>
     * <p>
     * Translated to Java by Ted Husted ( <a
     * href="mailto:husted@apache.org">husted@apache.org </a>). <br>
     * &nbsp;&nbsp;&nbsp; Reference Sean M. Burke's script at
     * http://www.ling.nwu.edu/~sburke/pub/luhn_lib.pl
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isCreditCard(String value) {
	return GenericValidator.isCreditCard(value);
    }

    /**
     * <p>
     * Checks for a valid credit card number.
     * </p>
     * <p>
     * Translated to Java by Ted Husted ( <a
     * href="mailto:husted@apache.org">husted@apache.org </a>). <br>
     * &nbsp;&nbsp;&nbsp; Reference Sean M. Burke's script at
     * http://www.ling.nwu.edu/~sburke/pub/luhn_lib.pl
     * </p>
     * 
     * @param cardNumber
     *            Credit Card Number
     */
    protected static boolean validateCreditCardLuhnCheck(String cardNumber) {
	return GenericValidator.validateCreditCardLuhnCheck(cardNumber);
    }

    /**
     * <p>
     * Checks if a field has a valid e-mail address.
     * </p>
     * <p>
     * Based on a script by Sandeep V. Tamhankar (stamhankar@hotmail.com),
     * http://javascript.internet.com
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     */
    public static boolean isEmail(String value) {
	return GenericValidator.isEmail(value);
    }

    /**
     * <p>
     * Checks if the value's length is less than or equal to the max.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param max
     *            The maximum length.
     */
    public static boolean maxLength(String value, int max) {
	return GenericValidator.maxLength(value, max);
    }

    /**
     * <p>
     * Checks if the value's length is greater than or equal to the min.
     * </p>
     * 
     * @param value
     *            The value validation is being performed on.
     * @param min
     *            The minimum length.
     */
    public static boolean minLength(String value, int min) {
	return GenericValidator.minLength(value, min);
    }

    /**
     * Adds a '/' on either side of the regular expression.
     */
    protected static String getDelimittedRegexp(String regexp) {
	return GenericValidator.getDelimittedRegexp(regexp);
    }

}

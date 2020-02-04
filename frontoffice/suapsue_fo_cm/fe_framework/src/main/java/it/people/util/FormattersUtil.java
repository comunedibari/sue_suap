/**
 * 
 */
package it.people.util;

import java.text.DecimalFormat;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         21/mag/2012 08:41:42
 */
public class FormattersUtil {

    public static String addSpaceBetweenChars(String value) {

	int c = 0;

	String result = "";

	while (value.length() > c + 1) {

	    String sub = value.substring(c, c + 1);

	    result += sub + " ";

	    c++;

	}

	if (value.length() > c) {
	    result += value.substring(c, c + 1);
	}

	return result;

    }

    public static String decimalsTruncate(String number) {

	double num = Double.parseDouble(number);

	DecimalFormat df = new DecimalFormat("0");

	return df.format(num);

    }

    public static String decimalsFormat(String number, String decimalsNumber) {

	double num = Double.parseDouble(number);

	int numDecimali = Integer.parseInt(decimalsNumber);

	String dec = "";

	int s = 0;

	while (numDecimali > s) {

	    dec += "0";

	    s++;

	}

	DecimalFormat df = new DecimalFormat("0." + dec);

	return df.format(num);

    }

    public static String replaceDotDecimalsSeparatorWithSpaces(String number,
	    String replaceSpaces, String decimalsNumber) {

	int dotPosition = number.indexOf('.');

	String integerPart = "";

	String decimalPart = "";

	if (dotPosition > -1) {

	    integerPart = number.substring(0, dotPosition);

	    decimalPart = number.substring(dotPosition + 1);

	} else {

	    integerPart = number;

	    decimalPart = "0";

	}

	int replaceSpacesNumber = Integer.parseInt(replaceSpaces);

	String spaces = "";

	int s = 0;

	while (replaceSpacesNumber > s) {

	    spaces += " ";

	    s++;

	}

	int decimalsNumberValue = Integer.parseInt(decimalsNumber);

	int d = decimalPart.length();

	if (decimalsNumberValue < d) {

	    decimalPart = decimalPart.substring(0, decimalsNumberValue);

	}

	while (decimalsNumberValue > d) {

	    decimalPart += "0";

	    d++;

	}

	return integerPart + spaces + decimalPart;

    }

    public static String booleanToString(String bool) {

	return bool.equalsIgnoreCase("true") ? "X" : "";

    }

    public static String toUpperCase(String value) {

	if (value != null) {
	    return value.toUpperCase();
	} else {
	    return value;
	}

    }

    public static String toLowerCase(String value) {

	if (value != null) {
	    return value.toLowerCase();
	} else {
	    return value;
	}

    }

    // public static String test1(String value, String parameter) {
    //
    // if (value != null) {
    // return value + parameter;
    // } else {
    // return value;
    // }
    //
    // }
    //
    // public static String test2(String value, String parameter1, String
    // parameter2) {
    //
    // if (value != null) {
    // return value + parameter1 + parameter2;
    // } else {
    // return value;
    // }
    //
    // }

}

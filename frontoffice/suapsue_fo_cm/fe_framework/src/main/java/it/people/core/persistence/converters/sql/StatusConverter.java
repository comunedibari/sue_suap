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
package it.people.core.persistence.converters.sql;

import org.apache.ojb.broker.accesslayer.conversions.ConversionException;

public class StatusConverter extends LongConverter {

    private static Integer I_EDIT = new Integer(0);
    private static Integer I_SENT = new Integer(1);
    private static Integer I_SIGN = new Integer(2);
    private static Integer I_SIGN_CUSTOM_SUMMARY_ACTIVITY = new Integer(3);

    private static String S_SENT = new String("S_SENT");
    private static String S_EDIT = new String("S_EDIT");
    private static String S_SIGN = new String("S_SIGN");
    private static String S_SIGN_CUSTOM_SUMMARY_ACTIVITY = new String(
	    "S_SIGN_CUSTOM_SUMMARY_ACTIVITY");

    public StatusConverter() {
    }

    public Object javaToSql(Object source) throws ConversionException {

	if (source instanceof String) {
	    if (source.equals(S_SENT)) {
		return super.javaToSql(I_SENT);
	    }
	    if (source.equals(S_EDIT)) {
		return super.javaToSql(I_EDIT);
	    }
	    if (source.equals(S_SIGN_CUSTOM_SUMMARY_ACTIVITY)) {
		return super.javaToSql(I_SIGN_CUSTOM_SUMMARY_ACTIVITY);
	    } else {
		return super.javaToSql(I_SIGN);
	    }
	} else {
	    if (source == null)
		return source;
	    else
		throw new ConversionException(
			"Unable to convert object to a java.math.BigDecimal");
	}
    }

    public Object sqlToJava(Object source) throws ConversionException {
	if (source instanceof Number) {
	    if (((Long) super.sqlToJava(source)).intValue() == 0) {
		return S_EDIT;
	    }
	    if (((Long) super.sqlToJava(source)).intValue() == 1) {
		return S_SENT;
	    }
	    if (((Long) super.sqlToJava(source)).intValue() == 3) {
		return S_SIGN_CUSTOM_SUMMARY_ACTIVITY;
	    } else {
		return S_SIGN;
	    }
	} else {
	    if (source == null)
		return source;
	    else
		throw new ConversionException(
			"Unable to convert object to a java.lang.Boolean");
	}
    }
}

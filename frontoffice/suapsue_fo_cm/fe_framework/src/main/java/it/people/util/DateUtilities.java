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
 * Created on 15-mag-03
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package it.people.util;

import java.util.GregorianCalendar;

/**
 * @author zoppello
 * 
 *         Utilities for manipulating Date
 */
public class DateUtilities {

    public static GregorianCalendar getCurrentDate() {
	return new GregorianCalendar();
    }

    public static GregorianCalendar addYear(GregorianCalendar aCalendar, int i) {
	aCalendar.roll(GregorianCalendar.YEAR, true);
	aCalendar.add(GregorianCalendar.YEAR, i);
	return aCalendar;
    }

    public static GregorianCalendar addMonth(GregorianCalendar aCalendar, int i) {
	aCalendar.roll(GregorianCalendar.MONTH, true);
	aCalendar.add(GregorianCalendar.MONTH, i);
	return aCalendar;
    }

    public static GregorianCalendar addDay(GregorianCalendar aCalendar, int i) {
	aCalendar.add(GregorianCalendar.DATE, i);
	return aCalendar;
    }
}

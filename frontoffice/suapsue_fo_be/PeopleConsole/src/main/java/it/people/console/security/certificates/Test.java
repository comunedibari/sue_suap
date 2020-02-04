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
package it.people.console.security.certificates;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 20/giu/2011 17.41.26
 *
 */
public class Test {

	public static void main(String[] args) {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar calendar = Calendar.getInstance();
		Date validFrom = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_MONTH, 365);
		
		Date expire = calendar.getTime();
		
		System.out.println("validFrom: " + simpleDateFormat.format(validFrom));
		System.out.println("Expire: " + simpleDateFormat.format(expire));
		
		Test test = new Test();
		
		String value = "037006pp";
		
		System.out.println("Input = '" + value + " - Output = '" + test.doOtectStringPadding(value) + "'.");
		
		value = "037006, 037007, 037008";

		System.out.println("Input = '" + value + " - Output = '" + test.doOtectStringPadding(value) + "'.");
		
		String[] arrTest = new String[] {"1"};
		
		System.out.println(test.stringArrayToString(arrTest));
		
	}
	
	private String doOtectStringPadding(String value) {

		String buffer = value;
		
		int mod = buffer.length() % 8;
		
		if (mod == 0) {
			return buffer;
		} else {
			int padding = 8 - mod;
			for(int index = 0; index < padding; index++) {
				buffer += " ";
			}
			return buffer;
		}
		
	}
	
	private String stringArrayToString(String[] values) {
		
		String buffer = "[";
		
		for(int index = 0; index < values.length; index++) {
			buffer += values[index];
			if (index < (values.length - 1)) {
				buffer += ",";
			}
		}
		
		return buffer + "]";
		
	}
	
}

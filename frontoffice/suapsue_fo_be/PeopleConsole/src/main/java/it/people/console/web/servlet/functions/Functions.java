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
package it.people.console.web.servlet.functions;

import java.util.Map;

import it.people.console.beans.support.ListableFilter;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 08.27.42
 *
 */
public class Functions {

	public static boolean isListableFilter(Object filter) {
		
		boolean result = false;
		
		Class<?>[] filterObjectInterfaces = filter.getClass().getInterfaces();
		for(Class<?> clazz : filterObjectInterfaces) {
			if (clazz.isInterface() && clazz.getCanonicalName().equalsIgnoreCase(ListableFilter.class.getCanonicalName())) {
				result = true;
				break;
			}
		}
		
		return result;
		
	}

	public static String pagedListHolderValue(String key, Map<String, Object> row) {
		
		try {
			return String.valueOf(row.get(key));
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

	
}

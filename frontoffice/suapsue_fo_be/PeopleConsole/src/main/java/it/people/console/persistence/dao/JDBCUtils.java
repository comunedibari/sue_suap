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
package it.people.console.persistence.dao;

import it.people.console.enumerations.LogicalOperators;
import it.people.console.enumerations.Types;

import it.people.console.persistence.beans.support.FilterProperties;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 10/gen/2011 21.27.41
 *
 */
public class JDBCUtils {

	public static String applyFilterValue(FilterProperties filterProperties) {
		
		if (Types.CHAR.equals(filterProperties.getType()) || 
				Types.VARCHAR.equals(filterProperties.getType()) || 
				Types.LONGVARCHAR.equals(filterProperties.getType())) {
			boolean isLike = filterProperties.getOperator().equalsIgnoreCase(LogicalOperators.like.getValue());
			return isLike ? "'%" + sanitizeArgument(filterProperties.getValue()) + "%'" : "'" + sanitizeArgument(filterProperties.getValue()) + "'";
		}
		else {
			return filterProperties.getValue();
		}
		
	}
	
	public static String sanitizeArgument(String argument) {
		
		String result = argument;
		
		if (argument.indexOf("'") > 0) {
			result = argument.replace("'", "\\'");
		}
		
		return result;
		
	}
	
}

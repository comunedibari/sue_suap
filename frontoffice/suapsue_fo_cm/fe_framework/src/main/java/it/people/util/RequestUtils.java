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
package it.people.util;

import it.people.process.common.entity.AbstractEntity;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Oct 3, 2003 Time: 7:21:31 AM To
 * change this template use Options | File Templates.
 */
public class RequestUtils {
    // private Category cat = Category.getInstance(this.getClass().getName());

    public static HashMap getChangedProperties(Object bean, Enumeration names,
	    String prefix) {

	HashMap properties = new HashMap();

	while (names.hasMoreElements()) {
	    try {
		String propertyName = (String) names.nextElement();
		int lastDot = propertyName.lastIndexOf('.');
		String lastProp = null;
		Object innerBean = null;
		if (lastDot > 0) {
		    String innerBeanProp = propertyName.substring(0, lastDot);
		    lastProp = propertyName.substring(lastDot + 1);
		    innerBean = PropertyUtils.getProperty(bean, innerBeanProp);
		} else {
		    innerBean = bean;
		    lastProp = propertyName;
		}
		if ((innerBean != null)
			&& (innerBean instanceof AbstractEntity)) {
		    if (((AbstractEntity) innerBean).unchanged(lastProp)) {
			if (prefix != null && propertyName.startsWith(prefix))
			    properties
				    .put(propertyName
					    .substring(prefix.length() + 1),
					    PropertyUtils.getProperty(
						    innerBean, lastProp));
			else
			    properties.put(propertyName, PropertyUtils
				    .getProperty(innerBean, lastProp));
		    }
		}

	    } catch (Exception ex) {
		// cat.error(ex);
	    }
	}
	return properties;
    }

    public static HashMap getChangedProperties(Object bean,
	    HttpServletRequest request, String prefix) {
	return getChangedProperties(bean, request.getParameterNames(), prefix);
    }
}

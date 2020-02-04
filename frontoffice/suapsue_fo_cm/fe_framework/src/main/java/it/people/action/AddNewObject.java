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
package it.people.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Category;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Sep 28, 2003 Time: 8:54:22 PM <br>
 * <br>
 * Aggiunge un oggetto di tipo 'propertyName' al processo.
 */

public class AddNewObject extends Action {

    private Category cat = Category.getInstance(AddNewObject.class.getName());

    /**
     * 
     * @param mapping
     *            Oggetto contenente la "mappatura" delle azioni e dei forward
     * @param form
     *            Form inviata
     * @param request
     *            Request
     * @param response
     *            Respose
     * @return Restituisce il forward da eseguire a seconda dell'esito
     *         dell'azione
     * @throws Exception
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	super.execute(mapping, form, request, response);

	try {
	    String propertyName = request.getParameter("propertyName");
	    int lastPos = propertyName.lastIndexOf('.');
	    if (lastPos != -1 && lastPos < propertyName.length() - 1) {
		String beanProperty = propertyName.substring(0, lastPos);
		String methodProperty = propertyName.substring(lastPos + 1);

		Object bean = PropertyUtils.getProperty(form, beanProperty);
		if (bean != null) {
		    try {
			Method mthd = bean.getClass().getMethod(
				"new" + capitalize(methodProperty),
				new Class[0]);
			mthd.invoke(bean, new Object[0]);
			cat.debug("success");
			return mapping.findForward("success");
		    } catch (Exception mtdEx) {
			cat.error(mtdEx);
		    }
		}
	    }
	} catch (Exception ex) {
	    cat.error(ex);
	}
	return mapping.findForward("failed");
    }

    private static String capitalize(String s) {
	if (s.length() == 0) {
	    return s;
	}

	char chars[] = s.toCharArray();
	chars[0] = Character.toUpperCase(chars[0]);
	return new String(chars);
    }
}

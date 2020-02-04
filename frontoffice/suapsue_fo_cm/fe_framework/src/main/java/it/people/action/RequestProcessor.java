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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <p>
 * Title: People project
 * </p>
 * <p>
 * Description: People project
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Espin
 * </p>
 * 
 * @author Sergio Chiaravalli
 * @version 1.0
 */
public class RequestProcessor extends org.apache.struts.action.RequestProcessor {

    private Category cat = Category.getInstance(RequestProcessor.class
	    .getName());

    public static final String IGNORE_POPULATEBEAN = "__IgnorePopulateBean";

    protected void processPopulate(HttpServletRequest request,
	    HttpServletResponse response, ActionForm form, ActionMapping mapping)
	    throws ServletException {

	if (request.getAttribute(IGNORE_POPULATEBEAN) == null) {
	    super.processPopulate(request, response, form, mapping);
	}

    }
}

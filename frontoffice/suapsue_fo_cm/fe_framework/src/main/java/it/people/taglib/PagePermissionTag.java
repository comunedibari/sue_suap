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
package it.people.taglib;

import it.people.Step;
import it.people.core.PplPermission;
import it.people.core.PplPrincipal;
import it.people.core.PplSecurityManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by IntelliJ IDEA. User: sergio Date: Jan 5, 2004 Time: 4:19:35 PM To
 * change this template use Options | File Templates.
 */
public class PagePermissionTag extends TagSupport {

    public int doEndTag() throws JspException {
	/**
	 * todo: implementare la costruzione in request dell'oggetto
	 * PpLPermssion _ implementare un codice simile al seguente
	 */
	Step currentStep = null;
	PplPrincipal principal = null;
	PplPermission permission = PplSecurityManager.getInstance()
		.getPermission(principal, currentStep.getACL());

	pageContext.getRequest().setAttribute("PagePermission", permission);

	return 0;
    }
}

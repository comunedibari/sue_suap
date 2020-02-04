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
package it.people.console.web.servlet.tags.form;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 09.07.25
 *
 */
public class LogoutTag extends TagSupport {
	
	private static final long serialVersionUID = 6679186315546274105L;

	private String excludeActions;
	private String logoutAction;
	private String linkTitle;
	private String linkAlt; 
	
	/**
	 * @return the excludeActions
	 */
	public final String getExcludeActions() {
		return this.excludeActions;
	}

	/**
	 * @param excludeActions the excludeActions to set
	 */
	public final void setExcludeActions(String excludeActions) {
		this.excludeActions = excludeActions;
	}

	/**
	 * @return the logoutAction
	 */
	public final String getLogoutAction() {
		return this.logoutAction;
	}

	/**
	 * @param logoutAction the logoutAction to set
	 */
	public final void setLogoutAction(String logoutAction) {
		this.logoutAction = logoutAction;
	}

	/**
	 * @return the linkTitle
	 */
	public final String getLinkTitle() {
		return this.linkTitle;
	}

	/**
	 * @param linkTitle the linkTitle to set
	 */
	public final void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * @return the linkAlt
	 */
	public final String getLinkAlt() {
		return this.linkAlt;
	}

	/**
	 * @param linkAlt the linkAlt to set
	 */
	public final void setLinkAlt(String linkAlt) {
		this.linkAlt = linkAlt;
	}

	public int doStartTag() throws JspException {
		
		JspWriter writer = pageContext.getOut();
		try {
			boolean showLogout = true;
			String servletPath = ((HttpServletRequest)pageContext.getRequest()).getServletPath();
			int servletPathLastDot = servletPath.lastIndexOf('.');
			if (servletPathLastDot > 0 && servletPathLastDot < servletPath.length()) {
				if (servletPath.substring(servletPathLastDot + 1).equalsIgnoreCase(this.getExcludeActions())) {
					showLogout = false;
				}
			}
			if (showLogout) {
//				writer.write("<a href=\"" + this.getLogoutAction() + "\">" + this.getLinkTitle() + "</a>");
				writer.write("<input type=submit src=\"\" id=\"logout\" name=\"logout\" value=\"" 
						+ this.getLinkTitle() + "\" />");
			}
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
		
	}
		
}

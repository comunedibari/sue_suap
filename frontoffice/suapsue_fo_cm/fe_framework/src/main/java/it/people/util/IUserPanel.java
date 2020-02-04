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
/**
 * 
 */
package it.people.util;

import it.people.wrappers.IRequestWrapper;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         29/ott/2012 14:11:48
 */
public interface IUserPanel {

    public final static String USER_PANEL_PARAMETER_KEY = "usrpk";

    public void processPanel(IRequestWrapper request, String parameter);

    public enum ViewTypes {

	bugSignalling("v:bugSignalling"), sendSuggestion("v:sendSuggestion"), main(
		"v:main");

	private String viewName;

	private ViewTypes(final String viewName) {
	    this.setViewName(viewName);
	}

	/**
	 * @param viewName
	 *            the viewName to set
	 */
	private void setViewName(String viewName) {
	    this.viewName = viewName;
	}

	/**
	 * @return the viewName
	 */
	public final String getViewName() {
	    return this.viewName;
	}

    }

    public enum ActionTypes {

	submit("a:submit"), cancel("a:cancel");

	private String actionName;

	private ActionTypes(final String actionName) {
	    this.setActionName(actionName);
	}

	/**
	 * @param actionName
	 *            the actionName to set
	 */
	private void setActionName(String actionName) {
	    this.actionName = actionName;
	}

	/**
	 * @return the actionName
	 */
	public final String getActionName() {
	    return this.actionName;
	}

    }

}

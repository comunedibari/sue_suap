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
package it.people.filters;

import it.people.core.PplUserData;
import it.people.sirac.core.SiracConstants;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class RequiredUserDataVerifierFilter implements Filter {

    private static final Logger logger = Logger
	    .getLogger(RequiredUserDataVerifierFilter.class);

    private static String requiredUserDataActivationServiceURL = null;

    public void init(FilterConfig filterConfig) throws ServletException {

	if (logger.isInfoEnabled()) {
	    logger.info("Init RequiredUserDataVerifierFilter filter configuration...");
	}

	RequiredUserDataVerifierFilter
		.setRequiredUserDataActivationServiceURL(filterConfig
			.getInitParameter("requiredUserDataActivationServiceURL"));

	if (logger.isInfoEnabled()) {
	    logger.info("RequiredUserDataVerifierFilter filter configuration completed.");
	}

    }

    public void doFilter(ServletRequest _request, ServletResponse _response,
	    FilterChain _chain) throws IOException, ServletException {

	if (logger.isInfoEnabled()) {
	    logger.info("RequiredUserDataVerifierFilter filter execution...");
	}

	HttpServletRequest request = (HttpServletRequest) _request;
	HttpServletResponse response = (HttpServletResponse) _response;
	HttpSession session = ((HttpServletRequest) request).getSession();
	String queryString = request.getQueryString();
	String processName = "";

	HashMap<String, String> param = new HashMap<String, String>();
	if (queryString != null) {
	    String[] queryStringToken = queryString.split("&");
	    if (queryStringToken != null) {
		for (int i = 0; i < queryStringToken.length; i++) {
		    String[] par = queryStringToken[i].split("=");
		    if (par != null && par.length == 2) {
			param.put(par[0], par[1]);
		    }
		}
	    }
	}

	if (param.get("processName") != null) {
	    processName = (String) param.get("processName");
	}

	session.setAttribute("parametriServizio", param);

	boolean mustAskData = false;

	boolean isAnonymousUser = (session
		.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER) == null);

	if (!isAnonymousUser) {
	    PplUserData userData = (PplUserData) session
		    .getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);

	    if (userData.getEmailaddress() == null
		    || (userData.getEmailaddress() != null && userData
			    .getEmailaddress().trim().equalsIgnoreCase(""))) {
		mustAskData = true;
	    }
	}

	if (!mustAskData) {
	    _chain.doFilter(request, response);
	} else {
	    RequestDispatcher requestDispatcher = _request
		    .getRequestDispatcher(RequiredUserDataVerifierFilter
			    .getRequiredUserDataActivationServiceURL());
	    requestDispatcher.forward(request, response);
	}

	if (logger.isInfoEnabled()) {
	    logger.info("RequiredUserDataVerifierFilter filter executed.");
	}

	return;

	// SiracHelper.isAnonymousUser((String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER));
	// boolean isInitProcess = SiracHelper.isPeopleInitProcess(request);
	// boolean isReturnFromPayment =
	// "/returnFromPayment.do".equals(requestURL);
	// boolean isLoadProcess = (queryString!=null &&
	// queryString.indexOf("processId")>=0);
	// boolean isPostLoadProcess = "/postLoadProcess.do".equals(requestURL);
	// boolean mustShowSelectProfilePage =
	// (
	// (isInitProcess && !isLoadProcess && !isPostLoadProcess &&
	// !(isAnonymousAccessAllowed && isAnonymousUser2) &&
	// !isReturnFromPayment) ||
	// (isPostLoadProcess && !(isAnonymousAccessAllowed &&
	// isAnonymousUser2)) && !isReturnFromPayment);

    }

    public void destroy() {

	if (logger.isInfoEnabled()) {
	    logger.info("Destroying RequiredUserDataVerifierFilter filter...");
	}

    }

    private static String getRequiredUserDataActivationServiceURL() {
	return requiredUserDataActivationServiceURL;
    }

    private static void setRequiredUserDataActivationServiceURL(
	    String requiredUserDataActivationServiceURL) {
	RequiredUserDataVerifierFilter.requiredUserDataActivationServiceURL = requiredUserDataActivationServiceURL;
    }

}

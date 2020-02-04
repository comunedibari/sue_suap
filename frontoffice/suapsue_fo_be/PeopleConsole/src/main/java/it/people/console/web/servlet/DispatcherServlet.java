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
package it.people.console.web.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.people.console.config.ConsoleVersion;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.utils.WebUtils;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 30/gen/2011 19.22.00
 *
 */
public class DispatcherServlet extends
		org.springframework.web.servlet.DispatcherServlet {

	private static final long serialVersionUID = 7204319629961552941L;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		config.getServletContext().setAttribute(ConsoleVersion.VERSION_KEY, new ConsoleVersion());
	}

	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String from = WebUtils.getReferer(request);
		String to = request.getRequestURL().toString();

		String fromWithoutContext = "".equalsIgnoreCase(from) ? "" : from.substring(from.indexOf(request.getContextPath()) + request.getContextPath().length() + 1);
		String toWithoutContext = "".equalsIgnoreCase(to) ? "" : to.substring(to.indexOf(request.getContextPath()) + request.getContextPath().length() + 1);
		
		int queryStringStart = fromWithoutContext.indexOf('?');
		String fromPage = (queryStringStart > 0) ? fromWithoutContext.substring(0, queryStringStart) : fromWithoutContext;
		String fromQueryString = "";
		if (queryStringStart > 0) {
			fromQueryString = fromWithoutContext.substring(queryStringStart + 1);
		}

		queryStringStart = toWithoutContext.indexOf('?');
		String toPage = (queryStringStart > 0) ? toWithoutContext.substring(0, queryStringStart) : toWithoutContext;

		if (logger.isTraceEnabled()) {
			logger.trace("From: " + from);
			logger.trace("To: " + to);
			
			logger.trace("From cleaned: " + fromWithoutContext);
			logger.trace("To cleaned: " + toWithoutContext);
		}
		
		int fromPathIndex = fromWithoutContext.indexOf('/');
		int toPathIndex = toWithoutContext.indexOf('/');

		if (logger.isTraceEnabled()) {
			logger.trace("From cleaned path index: " + fromPathIndex);
			logger.trace("To cleaned path index: " + toPathIndex);
		}
		
		String mainFromPath = "";
		String mainToPath = "";
		if (fromPathIndex > 0) {
			mainFromPath = fromWithoutContext.substring(0,  fromPathIndex);
		}
		if (toPathIndex > 0) {
			mainToPath = toWithoutContext.substring(0,  toPathIndex);
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Main From path: " + mainFromPath);
			logger.trace("Main To path: " + mainToPath);
		}
		
		if (!mainToPath.equalsIgnoreCase(mainFromPath) || !toPage.equalsIgnoreCase(fromPage)) {
			if (!mainToPath.equalsIgnoreCase(mainFromPath)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Removing applied filters from session...");
				}
				HttpSession session = (HttpSession)request.getSession();
				session.removeAttribute(Constants.ControllerUtils.APPLIED_FILTERS_KEY);
				session.removeAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY);
			}
			updateSessionQueriesStringCache(request, fromPage, fromQueryString);
		}
		
		String toQueryString = request.getQueryString();
		boolean nocache = toQueryString != null && toQueryString.indexOf("&nocache") > 0;
		String cachedToPageQueryString = getQueryStringFromCache(request, toPage);
		if (!StringUtils.isEmptyString(cachedToPageQueryString) && !nocache) {
			clearSessionQueryString(request, toPage);
			response.sendRedirect(request.getContextPath() + "/" + toPage + "?" + cachedToPageQueryString);
		} else {
			super.doDispatch(request, response);
		}
		
	}
	
	private String getQueryStringFromCache(final HttpServletRequest request, final String toPage) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> queriesStringsCache = (Map<String, String>)request.getSession().getAttribute("queriesStringsCache");
		
		if (queriesStringsCache == null) {
			queriesStringsCache = new HashMap<String, String>();
		}
		
		String cachedQueryString = queriesStringsCache.get(toPage);
		
		return (cachedQueryString == null) ? "" : cachedQueryString;
		
	}
	
	private void clearSessionQueryString(final HttpServletRequest request, final String toPage) {

		@SuppressWarnings("unchecked")
		Map<String, String> queriesStringsCache = (Map<String, String>)request.getSession().getAttribute("queriesStringsCache");
		queriesStringsCache.put(toPage, "");
		
	}
	
	private void updateSessionQueriesStringCache(final HttpServletRequest request, final String fromPage, final String fromQueryString) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> queriesStringsCache = (Map<String, String>)request.getSession().getAttribute("queriesStringsCache");
		
		if (queriesStringsCache == null) {
			queriesStringsCache = (Map<String, String>)Collections.synchronizedMap(new HashMap<String, String>());
		}
		
		if (fromQueryString != null && !StringUtils.isEmptyString(fromQueryString)) {
			queriesStringsCache.put(fromPage, fromQueryString);
		}
		request.getSession().setAttribute("queriesStringsCache", queriesStringsCache);
		
	}
	
}

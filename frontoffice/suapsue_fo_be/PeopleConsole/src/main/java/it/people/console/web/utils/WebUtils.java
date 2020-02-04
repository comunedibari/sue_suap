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
package it.people.console.web.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import it.people.console.security.AbstractCommand;
import it.people.console.security.Command;
import it.people.console.utils.Constants;
import it.people.console.utils.StringUtils;
import it.people.console.web.servlet.tags.TagUtils;
import it.people.console.web.servlet.tags.TagsConstants;
import it.people.sirac.core.SiracConstants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 04/gen/2011 11.49.48
 *
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

	private static Logger logger = LoggerFactory.getLogger(WebUtils.class);
	
	private static final String PARAM_X_COORD_SUFFIX = ".x";

	private static final String PARAM_Y_COORD_SUFFIX = ".y";
	
	public static final String REFERER_HEADER = "referer";
	
	public static String sanitizeImagePath(String imagePath) {
		
		return (imagePath.startsWith("/")) ? imagePath : "/" + imagePath;
		
	}

	@SuppressWarnings("unchecked")
	public static boolean isPrefixParamInRequest(HttpServletRequest request, String parameter) {
		boolean result = false;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(parameter)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static boolean isParamInRequest(HttpServletRequest request, String parameter) {
		boolean result = false;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.equalsIgnoreCase(parameter) 
					|| paramName.equalsIgnoreCase(parameter + PARAM_X_COORD_SUFFIX) 
					|| paramName.equalsIgnoreCase(parameter + PARAM_Y_COORD_SUFFIX)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static String getRequestParamValue(HttpServletRequest request, String parameter) {
		
		return (isParamInRequest(request, parameter)) ? request.getParameter(parameter) : "";
		
	}

	public static String getImagesBaseURL(HttpServletRequest request) {

		return request.getContextPath() + "/images";
		
	}
	
	public static String[] parsePagedListHolderParam(String param) {
		
		Vector<String> result = new Vector<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(param, TagsConstants.LIST_HOLDER_PARAM_SEPARATOR);
		while(tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		
		return result.toArray(new String[result.size()]);
		
	}

	@SuppressWarnings("unchecked")
	public static String[] getPagedListHolderRequestParams(HttpServletRequest request) {
		Vector<String> result = new Vector<String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if (paramName.startsWith(TagsConstants.LIST_HOLDER_TABLE_PREFIX)) {
				String paramToAdd = clearParamCoordinates(paramName);
				if (!result.contains(paramToAdd)) {
					result.add(paramToAdd);
				}
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	public static String clearParamCoordinates(String param) {
		
		if (param.endsWith(PARAM_X_COORD_SUFFIX) || param.endsWith(PARAM_Y_COORD_SUFFIX)) {
			return param.substring(0, param.length() - 2);
		}
		else {
			return param;
		}
		
	}

	public static String writeAction(final Command command, final HttpServletRequest request) throws JspException {
		
		String result = "";
		
		if (command.getType() == AbstractCommand.Types.input) {
		
			if (!StringUtils.isEmptyString(command.getSrc())) {
				result += "<input type=\"image\" " + TagUtils.writeHtmlAttribute("name", command.getCommandAction().getAction()) 
					+ " " + TagUtils.writeHtmlAttribute("id", command.getId())
					+ " " + TagUtils.writeHtmlSrcAttribute("src", command.getSrc(), request)
					+ " />";
			} else {
				result += "<input type=\"submit\" " + TagUtils.writeHtmlAttribute("name", command.getCommandAction().getAction()) 
					+ " " + TagUtils.writeHtmlAttribute("id", command.getId())
					+ " " +  TagUtils.writeHtmlAttribute("value", command.getValue())
					+ " />";
			}

		}
			
		return result;
		
	}
	
	public static ParsedQueryString parseQueryString(String queryString) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Parsing query string '" + queryString +"'.");
		}
		
		ParsedQueryString result = new ParsedQueryString();
		
		if (queryString.startsWith("?")) {
			StringTokenizer keysValuesTokenizer = new StringTokenizer(queryString.substring(1), "&");
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + keysValuesTokenizer.countTokens() + " key-value token(s) for query string '" + queryString +"'.");
			}
			while(keysValuesTokenizer.hasMoreTokens()) {
				String keyValuePair = keysValuesTokenizer.nextToken();
				if (logger.isDebugEnabled()) {
					logger.debug("Tokenizing '" + keyValuePair + "' key-value pair.");
				}
				StringTokenizer keyValueTokenizer = new StringTokenizer(keyValuePair, "=");
				if (keyValueTokenizer.countTokens() == 2) {
					String key = keyValueTokenizer.nextToken();
					String value = keyValueTokenizer.nextToken();
					if (logger.isDebugEnabled()) {
						logger.debug("Tokenized key-value pair: key = '" + key + "'; value = '" + value + "'.");
					}
					result.getQueryStringValues().put(key, value);
				}
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to parse query string '" + queryString +"': is not a valid query string.");
			}
		}
		
		return result;
		
	}

	public static String getReferer(HttpServletRequest request) {

		String referer = request.getHeader(REFERER_HEADER);
		return  (referer == null) ? "" : referer;
		
	}

	/**
	 * @param request
	 */
	public static void logout(HttpServletRequest request) {
		
		request.getSession().invalidate();

		request.getSession().removeAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
		request.getSession().removeAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
		request.getSession().removeAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);

		SecurityContextHolder.getContext().setAuthentication(null);
		
	}
	
	/**
	 * <p>Return complete action path associated to request URL inspecting request referer header.
	 * @param request
	 * @return
	 */
	public static String getAction(HttpServletRequest request) {

		String result = "";
		
		if (logger.isDebugEnabled()) {
			logger.debug("Getting referer...");
		}		
		String referer = request.getHeader(REFERER_HEADER);
		
    	String scheme = request.getScheme();
    	String domain = request.getServerName();
    	String port = StringUtils.nullToEmpty(String.valueOf(request.getServerPort()));
    	String context = request.getContextPath();
    	String completeContextPath = scheme + "://" + domain + 
    		((StringUtils.isEmptyString(port)) ? "" : ":") + port + context;
		if (logger.isDebugEnabled()) {
			logger.debug("Scheme: " + scheme);
			logger.debug("Domain: " + domain);
			logger.debug("Port: " + port);
			logger.debug("Context: " + context);
			logger.debug("Complete context path: " + completeContextPath);
			logger.debug("Extracting action...");
		}

		try {
			URL refererUrl = new URL(referer);
			if (refererUrl.getPort() < 0 && !StringUtils.isEmptyString(port)) {
				referer = refererUrl.getProtocol() + "://" + refererUrl.getAuthority() +  ":" + 
					port + refererUrl.getPath() + 
					((!StringUtils.isEmptyString(refererUrl.getQuery()) ? "?" + 
							refererUrl.getQuery() : ""));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		result = referer.substring(completeContextPath.length());

		if (logger.isDebugEnabled()) {
			logger.debug("Extracted action = " + result);
		}
		
		return result;
		
	}

	/**
	 * @param model
	 * @param request
	 */
	public static void holdControllerModel(ModelMap model, HttpServletRequest request) {

        request.getSession().setAttribute(Constants.ControllerUtils.LOGOUT_TMP_MODEL_SESSION_KEY, model);
		
	}
	
}

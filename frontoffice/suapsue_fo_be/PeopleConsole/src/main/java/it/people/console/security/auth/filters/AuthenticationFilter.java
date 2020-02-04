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
package it.people.console.security.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import it.people.console.utils.Constants;
import it.people.sirac.core.SiracConstants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 25/giu/2011 10.35.41
 *
 */
public class AuthenticationFilter extends it.people.sirac.filters.SiracAuthenticationFilter {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	/* (non-Javadoc)
	 * @see it.people.sirac.filters.SiracAuthenticationFilter#destroy()
	 */
	@Override
	public void destroy() {

		if (logger.isDebugEnabled()) {
			logger.debug("Calling destroy super class method...");
		}
		super.destroy();
		
	}

	/* (non-Javadoc)
	 * @see it.people.sirac.filters.SiracAuthenticationFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Verifying if SIRAC filter switch is needed...");
		}
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		String userLogin = (String)httpServletRequest.getSession()
			.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
		if (userLogin == null || (userLogin != null && 
				!userLogin.equalsIgnoreCase(Constants.Security.ROOT_USER_LOGIN_IDP))) {
			if (logger.isDebugEnabled()) {
				logger.debug("SIRAC filter switch needed.");
			}
			super.doFilter(servletRequest, servletResponse, filterChain);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Found root user in security session, so no SIRAC filter switch is needed.");
				logger.debug("Calling next filer in chain...");
			}
			filterChain.doFilter(servletRequest, servletResponse);
		}
		
	}

	/* (non-Javadoc)
	 * @see it.people.sirac.filters.SiracAuthenticationFilter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Calling init filter super class method...");
		}
		super.init(filterConfig);
				
	}

}

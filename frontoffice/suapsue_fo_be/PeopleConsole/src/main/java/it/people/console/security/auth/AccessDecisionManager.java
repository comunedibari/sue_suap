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
package it.people.console.security.auth;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.FilterInvocation;

import it.people.console.config.ConsoleConfiguration;
import it.people.console.system.AbstractLogger;
import it.people.console.utils.Constants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/giu/2011 14.57.33
 *
 */
public class AccessDecisionManager extends AbstractLogger implements
		org.springframework.security.access.AccessDecisionManager {

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	public void decide(Authentication authentication, Object request,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {

		boolean grantAccess = false;
		FilterInvocation filterInvocation = (FilterInvocation)request;
		
		if (!userIsRootorConsoleAdmin(authentication.getAuthorities()) && 
				isRootandConsoleAdminAlwaysGrantAccess(filterInvocation)) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Requested path = '" + filterInvocation.getRequestUrl() + "'");
				logger.debug("Configured attributes for path = '" + configAttributes + "'");
				logger.debug("User authorities = '" + authentication.getAuthorities() + "'");
			}
	
			for(GrantedAuthority authority : authentication.getAuthorities()) {
				if (configAttributes.contains(new SecurityConfig(authority.getAuthority()))) {
					if (logger.isDebugEnabled()) {
						logger.debug("User authority '" + authority + "' match authority in attributes for path, granting access.");
					}
					grantAccess = true;
				}
			}
		} else {
			grantAccess = true;
		}
		
		if (!grantAccess) {
			if (logger.isDebugEnabled()) {
				logger.debug("No user authority matched authority in attributes for path, dening access.");
			}
			throw new AccessDeniedException("");
		}
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
	public boolean supports(ConfigAttribute configAttribute) {

		if (logger.isDebugEnabled()) {
			logger.debug("supports ConfigAttribute " + configAttribute);
		}

		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {

		if (logger.isDebugEnabled()) {
			logger.debug("supports Class<?> " + clazz);
		}
		
		return true;
		
	}

	/**
	 * @param userAuthorities
	 * @return
	 */
	private boolean userIsRootorConsoleAdmin(Collection<GrantedAuthority> userAuthorities) {
		GrantedAuthority rootAuthority = new GrantedAuthorityImpl(Constants.Security.ROOT_ROLE);
		GrantedAuthority consoleAdminAuthority = new GrantedAuthorityImpl(Constants.Security.CONSOLE_ADMIN_ROLE);
		return userAuthorities.contains(rootAuthority) || userAuthorities.contains(consoleAdminAuthority);
	}
	
	/**
	 * @param filterInvocation
	 * @return
	 */
	private boolean isRootandConsoleAdminAlwaysGrantAccess(FilterInvocation filterInvocation) {
		
		ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
		ConsoleConfiguration consoleConfiguration = (ConsoleConfiguration)servletContext.getAttribute(Constants.System.SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES);
		return consoleConfiguration.isRootAndConsoleAdminAlwaysGrantAccess();
		
	}
	
}

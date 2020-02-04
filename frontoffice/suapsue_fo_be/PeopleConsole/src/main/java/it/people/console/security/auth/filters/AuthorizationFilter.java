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
import java.util.Iterator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import it.people.console.security.auth.SIRACLoginAuthenticationToken;
import it.people.console.security.auth.exceptions.AuthenticationDbException;
import it.people.console.security.auth.exceptions.CredentialsIntegrityException;
import it.people.console.security.auth.exceptions.UnauthorizedUserException;
import it.people.core.PplUserData;
import it.people.sirac.core.SiracConstants;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 22/giu/2011 14.36.24
 *
 */
public class AuthorizationFilter extends GenericFilterBean {

	private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	private AuthenticationManager authenticationManager;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
    }
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		HttpSession session = httpServletRequest.getSession();
		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving logged in user data from SIRAC constants...");
		}
		PplUserData userData =  (PplUserData)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
		String userLogin = (String)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USER);
		if (logger.isDebugEnabled()) {
			logger.debug("Logged in user from SIRAC:\n" + 
			"\t Last name = '" + userData.getCognome() + "'\n" +
			"\t First name = '" + userData.getNome() + "'\n" +
			"\t Fiscal code = '" + userData.getCodiceFiscale() + "'\n" +
			"\t E-mail address = '" + userData.getEmailaddress() + "'\n");
			logger.debug("User IDP Login = '" + userLogin + "'");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("UserLogin = " + userLogin);
			logger.debug("Session ID = " + ((HttpServletRequest)request).getSession().getId());
			logger.debug("Verifying if authentication required...");
		}
		
		if (this.isAuthenticationRequired(userLogin)) {

			if (logger.isDebugEnabled()) {
				logger.debug("Authentication required.");
			}
			
			SIRACLoginAuthenticationToken authenticationToken = new SIRACLoginAuthenticationToken(userLogin, userData.getCodiceFiscale(), null);
			authenticationToken.setDetails(userData);
			
			Authentication authResult = null;
			
			try {
				authResult = this.getAuthenticationManager().authenticate(authenticationToken);
	            // Authentication success
	            if (logger.isDebugEnabled()) {
	                logger.debug("Authentication success: " + authResult.toString());
	            }

	            SecurityContextHolder.getContext().setAuthentication(authResult);
			} catch(AuthenticationDbException ae) {
				logger.error("", ae);
				SecurityContextHolder.getContext().setAuthentication(null);
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error.jsp");
				return;
			} catch(UnauthorizedUserException uue) {
				logger.error("", uue);
				SecurityContextHolder.getContext().setAuthentication(null);
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error.jsp");
				return;
			} catch(CredentialsIntegrityException cie) {
				logger.error("", cie);
				SecurityContextHolder.getContext().setAuthentication(null);
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error.jsp");
				return;
			}
			
			//TODO Redirection if auth error
			
		}
		
		filterChain.doFilter(request, response);
		
	}

	private boolean isAuthenticationRequired(String userLogin) {

		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

		if (logger.isDebugEnabled()) {
        	logger.debug("Thread ID = " + Thread.currentThread().getId());
			if (existingAuth != null) {
				logger.debug("existingAuth class = " + existingAuth.getClass());
				logger.debug("existingAuth name = " + existingAuth.getName());
				if (existingAuth.getAuthorities().isEmpty()) {
					logger.debug("Authorities is empty");
				} else {
					logger.debug("Logged user authorities:");
					Iterator<GrantedAuthority> authoritiesIterator = existingAuth.getAuthorities().iterator();
					while(authoritiesIterator.hasNext()) {
						GrantedAuthority grantedAuthority = authoritiesIterator.next();
						logger.debug("Authority: " + grantedAuthority.getAuthority());
					}
				}
			} else {
				logger.debug("Authentication from context is null");
			}
		}
		
        if (existingAuth instanceof SIRACLoginAuthenticationToken 
        		&& !existingAuth.getName().equals(userLogin)) {
            return true;
        } else if (existingAuth == null) {
        	return true;
        }
		
        return false;
        
	}

	/**
	 * @return the authenticationManager
	 */
	private AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}

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

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import it.people.core.PplUserData;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 23/giu/2011 11.35.27
 *
 */
public class SIRACLoginAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -3618209258521130779L;
	private Object principal = null;
	private Object credentials;
	private Object details;
	
	public SIRACLoginAuthenticationToken(Object principal, Object credentials, Collection<GrantedAuthority> authorities) {
		super(authorities);
		this.setCredentials(credentials);
		this.setPrincipal(principal);
		this.setAuthenticated(false);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.Authentication#getCredentials()
	 */
	public Object getCredentials() {
		
		return this.credentials;
		
	}

	/**
	 * @param credentials the credentials to set
	 */
	private void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.Authentication#getPrincipal()
	 */
	public Object getPrincipal() {
		
		return this.principal;
		
	}

	/**
	 * @param principal the principal to set
	 */
	private void setPrincipal(Object principal) {
		this.principal = principal;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AbstractAuthenticationToken#getDetails()
	 */
	@Override
	public Object getDetails() {
		return this.details;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AbstractAuthenticationToken#setDetails(java.lang.Object)
	 */
	@Override
	public void setDetails(Object details) {
		this.details = details;
	}

	/* (non-Javadoc)
     * @see org.springframework.security.authentication.AbstractAuthenticationToken#setAuthenticated(boolean)
     */
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        if (isAuthenticated) {
//            throw new IllegalArgumentException(
//                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        }

        super.setAuthenticated(isAuthenticated);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.authentication.AbstractAuthenticationToken#eraseCredentials()
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.setCredentials(null);
    }
	
}

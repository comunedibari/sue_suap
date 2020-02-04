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
package it.people.core;

public class PplSecurityManager {
    private static PplSecurityManager ourInstance;

    public synchronized static PplSecurityManager getInstance() {
	if (ourInstance == null) {
	    ourInstance = new PplSecurityManager();
	}
	return ourInstance;
    }

    private PplSecurityManager() {
    }

    /**
     * Recupera i permessi dell'utente dall'acl.
     * 
     * @param principal
     * @param ACL
     * @return
     */
    public PplPermission getPermission(PplPrincipal principal, PplACE[] ACL) {
	if (principal == null)
	    return null;
	if (ACL == null)
	    return PplPermission.ALL;
	for (int i = 0; i < ACL.length; i++) {
	    if (principal.like(ACL[i].getPrincipal()))
		return ACL[i].getPermission();
	}

	return null;
    }

    /**
     * Recupera i permessi degli utenti dall'acl.
     * 
     * @param principals
     * @param ACL
     * @return
     */
    public PplPermission getPermission(PplPrincipal[] principals, PplACE[] ACL) {
	if (ACL == null)
	    return PplPermission.ALL;

	PplPermission result = null;
	for (int i = 0; i < principals.length; i++)
	    result = (result == null) ? getPermission(principals[i], ACL)
		    : result.or(getPermission(principals[i], ACL));

	return result;
    }

    /**
     * Restituisce se un principal puo' eseguire la permission specificata.
     * 
     * @param permission
     * @param principals
     * @param ACL
     * @return
     */
    public boolean canDo(PplPermission permission, PplPrincipal[] principals,
	    PplACE[] ACL) {
	if (permission == null || principals == null)
	    return false;
	if (ACL == null)
	    return true;

	for (int i = 0; i < principals.length; i++)
	    if (canDo(permission, principals[i], ACL))
		return true;
	return false;
    }

    /**
     * Restituisce se il principal puo' eseguire la permission specificata.
     * 
     * @param permission
     * @param principal
     * @param ACL
     * @return
     */
    public boolean canDo(PplPermission permission, PplPrincipal principal,
	    PplACE[] ACL) {
	if (permission == null || principal == null)
	    return false;
	if (ACL == null)
	    return true;
	for (int i = 0; i < ACL.length; i++) {
	    if (principal.like(ACL[i].getPrincipal())
		    && ACL[i].getPermission().and(permission)
			    .equals(permission))
		return true;
	}

	return false;
    }

    public boolean canWrite() {
	return false;
    }

    public boolean canRead() {

	return false;
    }

}

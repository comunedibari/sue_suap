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

/**
 * 
 * User: sergio Date: Jan 2, 2004 Time: 6:04:16 PM <br>
 * <br>
 * Classe che rappresenta una ACE.
 */

public class PplACE {
    private PplPrincipal m_principal;
    private PplPermission permission;

    /**
     * Crea un ogetto ACE a partire da un princiapl e da una permission
     * 
     * @param p_principal
     *            Principal
     * @param p_permission
     *            Permission
     */
    public PplACE(PplPrincipal p_principal, PplPermission p_permission) {
	m_principal = p_principal;
	permission = p_permission;
    }

    /**
     * Crea un ogetto ACE a partire da un ruolo e da permission
     * 
     * @param role
     *            Ruolo
     * @param p_permission
     *            Permission
     */
    public PplACE(PplRole role, PplPermission p_permission) {
	m_principal = new PplPrincipal(null, role);
	permission = p_permission;
    }

    /**
     * Crea un ogetto ACE a partire da un utente e una permission
     * 
     * @param user
     *            User
     * @param p_permission
     *            Permission
     */
    public PplACE(PplUser user, PplPermission p_permission) {
	m_principal = new PplPrincipal(user.getUserID(), null);
	permission = p_permission;
    }

    public PplPrincipal getPrincipal() {
	return m_principal;
    }

    public void setPrincipal(PplPrincipal p_principal) {
	m_principal = p_principal;
    }

    public PplPermission getPermission() {
	return permission;
    }

    public void setPermission(PplPermission p_permission) {
	permission = p_permission;
    }
}

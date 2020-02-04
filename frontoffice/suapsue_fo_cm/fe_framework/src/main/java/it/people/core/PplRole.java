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

import java.io.Serializable;

/**
 * 
 * User: sergio Date: Dec 30, 2003 Time: 4:03:22 PM Ruolo associato ad un
 * 'PplPrincipal'.
 */
public class PplRole implements Serializable {
    private String m_name;
    private int m_code;

    private PplRole() {
	m_code = 0;
	m_name = "";
    }

    private PplRole(int code, String name) {
	m_code = code;
	m_name = name;
    }

    public String getName() {
	return m_name;
    }

    public void setName(String p_name) {
	m_name = p_name;
    }

    public int getCode() {
	return m_code;
    }

    public void setCode(int p_code) {
	m_code = p_code;
    }

    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PplRole))
	    return false;

	final PplRole pplRole = (PplRole) o;

	if (m_code != pplRole.m_code)
	    return false;
	if (!m_name.equals(pplRole.m_name))
	    return false;

	return true;
    }

    public int hashCode() {
	return m_name.hashCode();
    }

    public static PplRole valueOf(String roleName) {
	if (RICHIEDENTE.getName().equals(roleName))
	    return RICHIEDENTE;
	else if (CONIUGE.getName().equals(roleName))
	    return CONIUGE;
	else if (TECNICO.getName().equals(roleName))
	    return TECNICO;
	return null;
    }

    static public PplRole ANY = new PplRole(-1, "Any");
    static public PplRole NONE = new PplRole(0, "None");
    static public PplRole RICHIEDENTE = new PplRole(1, "Richiedente");
    static public PplRole CONIUGE = new PplRole(2, "Coniuge");
    static public PplRole TECNICO = new PplRole(3, "Tecnico");

}

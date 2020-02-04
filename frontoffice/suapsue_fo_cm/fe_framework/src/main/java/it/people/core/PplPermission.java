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
 * User: sergio Date: Jan 2, 2004 Time: 6:00:28 PM <br>
 * <br>
 * Classe che rappresenta le permission
 */
public class PplPermission implements Serializable {
    private int m_code;

    private PplPermission() {
	m_code = 0;
    }

    private PplPermission(int code) {
	m_code = code;
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
	if (!(o instanceof PplPermission))
	    return false;

	final PplPermission pplPermission = (PplPermission) o;

	if (m_code != pplPermission.m_code)
	    return false;

	return true;
    }

    /**
     * Realizza una funzione di AND tra due permission
     * 
     * @param permission
     *            Oggetto Permission
     * @return
     */
    public PplPermission and(PplPermission permission) {
	PplPermission resultPerm = new PplPermission(m_code);
	resultPerm.m_code &= permission.getCode();
	return resultPerm;
    }

    /**
     * Realizza una funzione di OR tra due permission
     * 
     * @param permission
     *            Oggetto Permission
     * @return
     */

    public PplPermission or(PplPermission permission) {
	PplPermission resultPerm = new PplPermission(m_code);
	resultPerm.m_code |= permission.getCode();
	return resultPerm;
    }

    public int hashCode() {
	return m_code;
    }

    static public PplPermission READ = new PplPermission(1);
    static public PplPermission WRITE = new PplPermission(2).or(READ);
    static public PplPermission SIGN = new PplPermission(4).or(READ);
    static public PplPermission SEND = new PplPermission(8).or(READ);

    static public PplPermission ALL = WRITE.or(SIGN).or(SEND);
}

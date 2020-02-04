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
package it.people.process.common.entity;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 19-set-2003 Time: 14.40.00 To
 * change this template use Options | File Templates.
 */
public class LuogoNascita extends AbstractEntity {

    private static final long serialVersionUID = -3778386521541586944L;

    private String m_place;
    private String m_commune;
    private String m_province;
    private String m_country;

    public LuogoNascita() {
	m_place = "";
	m_commune = "";
	m_province = "";
	m_country = "";
    }

    public LuogoNascita(String p_place, String p_commune, String p_province,
	    String p_country) {
	m_place = p_place;
	m_commune = p_commune;
	m_province = p_province;
	m_country = p_country;
    }

    public String getPlace() {
	return m_place;
    }

    public void setPlace(String p_place) {
	m_place = p_place;
    }

    public String getCommune() {
	return m_commune;
    }

    public void setCommune(String p_commune) {
	m_commune = p_commune;
    }

    public String getProvince() {
	return m_province;
    }

    public void setProvince(String p_province) {
	m_province = p_province;
    }

    public String getCountry() {
	return m_country;
    }

    public void setCountry(String p_country) {
	m_country = p_country;
    }

}

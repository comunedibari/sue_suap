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
package it.people.process.config;

/**
 * Created by IntelliJ IDEA. User: Luigi Corollo Date: 19-dic-2003 Time:
 * 10.32.07
 * 
 */
public class ConfigSender {

    private String m_commune;
    private String m_process;
    private Class m_className;
    private String m_mailAddress;
    private String m_pickupPassword;
    private String m_dummyPassword;

    // Comune_ID
    public String getCommune() {
	return m_commune;
    }

    public void setCommune(String p_commune) {
	this.m_commune = p_commune;
    }

    // Process_Name
    public String getProcess() {
	return m_process;
    }

    public void setProcess(String p_process) {
	this.m_process = p_process;
    }

    // ClassName
    public Class getClassName() {
	return m_className;
    }

    public void setClassName(Class p_className) {
	this.m_className = p_className;
    }

    // MailAddress
    public String getMailAddress() {
	return m_mailAddress;
    }

    public void setMailAddress(String p_mailAddress) {
	this.m_mailAddress = p_mailAddress;
    }

    // PickupPassword
    public String getPickupPassword() {
	return m_pickupPassword;
    }

    public void setPickupPassword(String p_pickupPassword) {
	this.m_pickupPassword = p_pickupPassword;
    }

    public String getDummyPassword() {
	return m_dummyPassword;
    }

    public void setDummyPassword(String m_dummyPassword) {
	this.m_dummyPassword = m_dummyPassword;
    }
}

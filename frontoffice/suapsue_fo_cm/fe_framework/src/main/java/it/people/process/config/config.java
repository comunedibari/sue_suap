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

public class config {

    private String m_process;
    private String m_type;
    private String m_commune;
    private String m_device;
    private Class m_classname;
    private String m_descr;

    // PROCESS
    public String getProcess() {
	return m_process;
    }

    public void setProcess(String p_process) {
	m_process = p_process;
    }

    // TYPE
    public String getType() {
	return m_type;
    }

    public void setType(String p_type) {
	this.m_type = p_type;
    }

    // COMMUNE
    public String getCommune() {
	return m_commune;
    }

    public void setCommune(String p_commune) {
	this.m_commune = p_commune;
    }

    // DEVICE
    public String getDevice() {
	return m_device;
    }

    public void setDevice(String p_device) {
	this.m_device = p_device;
    }

    // CLASSNAME
    public Class getClassname() {
	return m_classname;
    }

    public void setClassname(Class p_classname) {
	this.m_classname = p_classname;
    }

    // DESCR
    public String getDescr() {
	return m_descr;
    }

    public void setDescr(String p_descr) {
	this.m_descr = p_descr;
    }
}

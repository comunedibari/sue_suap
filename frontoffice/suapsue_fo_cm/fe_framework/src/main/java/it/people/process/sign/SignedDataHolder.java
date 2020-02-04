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
package it.people.process.sign;

public class SignedDataHolder {

    private String m_oid;
    private String m_fileName;
    private String m_filePath;
    private Long m_parentOid;

    public void setStepOid(String p_oid) {
	m_oid = p_oid;
    }

    public String getStepOid() {
	return m_oid;
    }

    public void setFileName(String p_fileName) {
	m_fileName = p_fileName;
    }

    public String getFileName() {
	return m_fileName;
    }

    public void setFilePath(String p_filePath) {
	m_filePath = p_filePath;
    }

    public String getFilePath() {
	return m_filePath;
    }

    public void setParentOid(Long p_parentOid) {
	m_parentOid = p_parentOid;
    }

    public Long getParentOid() {
	return m_parentOid;
    }

    public SignedDataHolder() {
    }

}

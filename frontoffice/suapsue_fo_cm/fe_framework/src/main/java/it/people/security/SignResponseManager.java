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
package it.people.security;

import it.people.process.AbstractPplProcess;
import it.people.process.sign.entity.SignedInfo;
import it.people.security.Exception.SignException;
import it.people.util.PKCS7Parser;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Category;

/**
 * 
 * User: sergio Date: Oct 3, 2003 Time: 4:04:35 PM <br>
 * <br>
 * Classe che interagisce con il server di firma per ottenere i dati firmati
 * dagli utenti
 */
public class SignResponseManager extends SignManager {

    private Category cat = Category.getInstance(SignResponseManager.class
	    .getName());
    private long m_policyID;
    private String m_userID;
    private String m_signedData_param = null;

    /**
     * 
     * @param request
     *            Request
     */
    public SignResponseManager(HttpServletRequest request) {
	this(request.getParameter(USERID), Long.parseLong(request
		.getParameter(POLICYID)), request.getParameter(SIGNED_DATA));
    }

    /**
     * @param request
     * @param pplProcess
     */
    public SignResponseManager(AbstractPplProcess pplProcess) {
	this(pplProcess.getOffLineSignUserID(), pplProcess
		.getOffLineSignPolicyID(), pplProcess.getOffLineSignedData());
    }

    /**
     * 
     * @param userID
     *            Id utente
     * @param policyID
     *            Id policy
     */
    public SignResponseManager(String userID, long policyID, String signedData) {
	m_policyID = policyID;
	m_userID = userID;
	m_signedData_param = signedData;
    }

    /**
     * Processa i dati firmati.
     * 
     * @return dati firmati.
     * @throws SignException
     */
    public SignedInfo process() throws SignException {
	try {
	    SignedInfo sInfo = new SignedInfo();
	    PKCS7Parser p7mParser = new PKCS7Parser(m_signedData_param);
	    p7mParser.decode(sInfo);

	    return sInfo;
	} catch (Exception ex) {
	    cat.error(ex);
	    ex.printStackTrace(System.out);
	}
	return null;
    }

    private StringBuffer readStream(InputStream input) {
	StringBuffer sb = new StringBuffer();
	try {
	    int ch;
	    while ((ch = input.read()) != -1) {
		sb.append((char) ch);
	    }
	} catch (IOException ex) {
	    cat.error(ex);
	    ex.printStackTrace(System.out);
	}
	return sb;
    }

    private void checkResponse(String response) throws SignException {
	if (!response.startsWith("SWCODE-"))
	    return;

	throw new SignException(response);
    }

}

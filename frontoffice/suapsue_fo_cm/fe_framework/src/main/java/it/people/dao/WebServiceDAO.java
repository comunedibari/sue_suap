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
/*
 * Created on 4-giu-04
 * Author Bernabei
 */
package it.people.dao;

import it.people.backend.client.WebServiceConnector;
import it.people.core.exception.ServiceException;
import it.people.util.ActivityLogger;
import it.people.vsl.exception.SendException;

import java.util.List;

import org.apache.log4j.Category;

/**
 * @author Bernabei Classe che invoca il Web Service associato all'operazione
 *         indicata nel parametro
 */
public class WebServiceDAO implements IDataAccess {

    private Category cat = Category.getInstance(WebServiceDAO.class.getName());

    private String opName = null;

    public WebServiceDAO(String opName) {
	this.opName = opName;
    }

    public String call(String inParameter, String comune, String userId,
	    String processName, Long processId) throws SendException {
	return call(null, inParameter, comune, userId, processName, processId);
    }

    /**
     * Implementazione della chiamata
     * 
     * @param in
     *            Collection contenente gli oggetti da inviare. Questi oggetti
     *            Verranno serializzati in XML , Devono estendere AbstractEntity
     * @param opName
     *            nome simbolico OPERAZIONE
     * @return Collection di oggetti risultato
     */
    public String call(List allegati, String xmlIn, String comune,
	    String userId, String processName, Long processId)
	    throws SendException {

	if (xmlIn == null || opName == null)
	    throw new SendException();

	WebServiceConnector connector = new WebServiceConnector();
	String ris = "";
	if (opName != null) {
	    // WEB SERVICE CALL
	    try {
		ActivityLogger.getInstance().log(comune, userId, processName,
			processId, "PRIMA DI INVOCARE IL WEB SERVICE",
			ActivityLogger.DEBUG);

		ris = connector.callWebService(allegati, "", opName, comune,
			userId, processName, processId, xmlIn);

		ActivityLogger.getInstance().log(comune, userId, processName,
			processId, "WEB SERVICE INVOCATO CON SUCCESSO",
			ActivityLogger.DEBUG);
	    } catch (ServiceException e) {
		cat.error(e);
		throw new SendException(e.getMessage());
	    }
	}
	return ris;
    }
}

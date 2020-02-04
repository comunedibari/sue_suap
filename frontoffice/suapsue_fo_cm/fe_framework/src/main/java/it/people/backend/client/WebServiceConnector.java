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
 * Created on 27-mag-04
 * Author Bernabei
 */
package it.people.backend.client;

import it.people.core.exception.ServiceException;
import it.people.peopleservice.ServiceSoap;
import it.people.peopleservice.ServiceSoapServiceLocator;
import it.people.util.PeopleProperties;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.rmi.RemoteException;
import org.apache.log4j.Category;

/**
 * @author Bernabei Questa classe rappresenta l'utility per invocare un servizio
 *         web lato client
 */
public class WebServiceConnector {

    private Category cat = Category.getInstance(WebServiceConnector.class
	    .getName());

    public WebServiceConnector() {
    }

    /**
     * @param serviceAddress
     * @param serviceName
     * @param comune
     *            codice del comune
     * @param userId
     * @param processName
     *            il nome DESCRITTIVO del processo es: "Pratica: ICI"
     * @param processId
     * @param xmlIn
     * @return String xml contenente il risultato del Web Service
     * @throws ServiceException
     */
    public String callWebService(String serviceAddress, String serviceName,
	    String comune, String userId, String processName, Long processId,
	    String xmlIn) throws ServiceException {
	return callWebService(null, serviceAddress, serviceName, comune,
		userId, processName, processId, xmlIn);
    }

    /**
     * Metodo che esegue la chiamata al Web Service
     * 
     * @param attachments
     *            Lista degli attachments (in questa implementazione non �
     *            utilizzato!)
     * @param serviceAddress
     *            Indirizzo del servizio
     * @param serviceName
     *            Nome logico del servizio
     * @param user
     *            :User
     * @param comune
     *            :City
     * @param xmlIn
     *            :String xml contenente i dati di invio
     * @return String xml contenente il risultato del Web Service
     * @throws ServiceException
     */
    public String callWebService(List attachments, String serviceAddress,
	    String serviceName, String comune, String userId,
	    String processName, Long processId, String xmlIn)
	    throws ServiceException {

	// processName non c'entra assolutamente nulla con il nome del processo
	// � in realt� il TITOLO della pratica!!!!!!
	String risultato = null;

	/*
	 * N.B. attachments � richiesto come parametro ma poi non �
	 * utilizzato
	 */

	try {
	    ServiceSoapServiceLocator locator = new ServiceSoapServiceLocator();
	    ServiceSoap service = locator.getServiceSoap(new URL(
		    PeopleProperties.PEOPLESERVICE_ADDRESS
			    .getValueString(comune)));

	    risultato = service
		    .process(comune, processName, serviceName, xmlIn);
	} catch (RemoteException e) {
	    cat.error(e);
	    throw new ServiceException(e.getMessage());
	} catch (javax.xml.rpc.ServiceException jre) {
	    cat.error(jre);
	    throw new ServiceException(jre.getMessage());
	} catch (Exception ex) {
	    cat.error(ex);
	    throw new ServiceException(ex.getMessage());
	}

	return risultato;
    }

    /**
     * Dato il path del file allegato estraggo il nome da spedire al BackEnd.
     * 
     * @param path
     * @return
     */
    private String estraiNome(String path) {
	String fileSperator = null;
	if (path != null && path.indexOf("/") > 0) {
	    fileSperator = "/";
	} else {
	    fileSperator = "\\";
	}
	StringTokenizer token = new StringTokenizer(path, fileSperator);
	String nomeFile = null;
	while (token.hasMoreTokens()) {
	    nomeFile = token.nextToken();
	}
	return nomeFile;

    }

}

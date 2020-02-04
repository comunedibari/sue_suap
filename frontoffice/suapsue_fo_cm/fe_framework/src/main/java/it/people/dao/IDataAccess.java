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

import it.people.core.exception.ServiceException;
import it.people.vsl.exception.SendException;

import java.util.List;

/**
 * @author Bernabei Interfaccia generica di accesso ai dati e al BACKEND da
 *         parte dell'FSL
 */
public interface IDataAccess {

    /**
     * Metodo per l'invocazione di un WEB SERVICE
     * 
     * @param inParameter
     * @param comune
     * @param userId
     * @param processName
     * @param processId
     * @return
     * @throws SendException
     */
    public String call(String inParameter, String comune, String userId,
	    String processName, Long processId) throws SendException,
	    ServiceException;

    /**
     * Metodo per l'invocazione di un WEB SERVICE con allegati
     * 
     * @param allegati
     *            Lista di oggetti Attachment
     * @param inParameter
     * @param comune
     * @param userId
     * @param processName
     * @param processId
     * @return
     * @throws SendException
     */
    public String call(List allegati, String inParameter, String comune,
	    String userId, String processName, Long processId)
	    throws SendException, ServiceException;
}

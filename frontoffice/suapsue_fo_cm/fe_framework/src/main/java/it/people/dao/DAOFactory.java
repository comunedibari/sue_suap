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

import org.apache.log4j.Category;

/**
 * @author Bernabei
 * 
 */
public class DAOFactory {

    private Category cat = Category.getInstance(DAOFactory.class.getName());

    private static DAOFactory ourInstance = null;

    public synchronized static DAOFactory getInstance() {
	if (ourInstance == null) {
	    ourInstance = new DAOFactory();
	}
	return ourInstance;
    }

    /**
     * Crea il DAO specifico all'operazione indicata
     * 
     * @param opName
     *            Nome operazione simbolico
     * @param comune
     * @param user
     * @return
     */
    public IDataAccess create(String opName, String communeId)
	    throws ServiceException {
	return new WebServiceDAO(opName);
    }
}

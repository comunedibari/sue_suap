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
 * Created on 27-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.people.process.workflow;

import javax.servlet.http.HttpSession;

import it.people.City;
import it.people.IProcess;
import it.people.process.workflow.exceptions.XMLWorkFlowException;

/**
 * @author thweb4
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IWorkFlow {

    /**
     * <p>
     * Restituisce l'interfaccia IProcess che rappresenta la struttura di un
     * processo definita nel file <Processo.getClass().getName()>.xml nella
     * directory workflow.
     * 
     * @param name
     *            , comune
     * @return
     * @throws XMLWorkFlowException
     * @since 2.0.2
     * @deprecated As of release 2.5.1, replaced by
     *             {@link #getProcess(HttpSession, String, City)}
     */
    @Deprecated
    public IProcess getProcess(String name, City comune)
	    throws XMLWorkFlowException;

    /**
     * <p>
     * Restituisce l'interfaccia IProcess che rappresenta la struttura di un
     * processo definita nel file <Processo.getClass().getName()>.xml nella
     * directory workflow.
     * 
     * @param session
     * @param name
     * @param comune
     * @return
     * @throws XMLWorkFlowException
     * @since 2.5.1
     */
    public IProcess getProcess(HttpSession session, String name, City comune)
	    throws XMLWorkFlowException;

}

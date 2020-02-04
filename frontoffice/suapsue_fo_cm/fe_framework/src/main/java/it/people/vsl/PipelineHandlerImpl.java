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
package it.people.vsl;

import it.people.core.persistence.exception.peopleException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 12-set-2003 Time: 15.25.43 To
 * change this template use Options | File Templates. <br>
 * <br>
 * Questa classe rappresenta come deve essere un handler della pipeline.
 */
public abstract class PipelineHandlerImpl implements PipelineHandler {

    protected PipelineHandlerImpl() {
	setFree(true);
	// m_dataQueue = new ArrayList();
    }

    /*
     * public PipelineData getData(int pos) throws IndexOutOfBoundsException {
     * // return (PipelineData) m_dataQueue.get(pos); }
     */

    public boolean isGuiHandler() {
	return false;
    }

    /*
     * public void putData(PipelineData p_plData) { m_dataQueue.add(p_plData); }
     */

    /**
     * Tramite questo metodo gli handler elaborano gli holder
     * 
     * @param holders
     *            Collezione di Holders da elaborare
     * @throws peopleException
     */
    public abstract void process(Collection holders) throws peopleException;

    public final void setPipeline(Pipeline pipeln) {
	m_parent = pipeln;
    }

    public final Pipeline getPipeline() {
	return m_parent;
    }

    // public void start() {}

    /*
     * public int getLength() { return m_dataQueue.size(); }
     */
    /**
     * 
     * @return Restituisce vero se l'handler � libero.
     */
    public final boolean isFree() {
	return m_free;
    }

    /**
     * 
     * @param free
     *            Parametro con cui settare lo stato dell'handler
     */
    public synchronized final void setFree(boolean free) {
	m_free = free;
    }

    /*
     * public PipelineData getTemporaryData() { return m_temporaryData; }
     * 
     * public void setTemporaryData(PipelineData p_temporaryData) {
     * m_temporaryData = p_temporaryData; }
     */

    /**
     * public void start(); public void complete(ActionListener al)
     */

    protected boolean m_free;
    private Pipeline m_parent;
}

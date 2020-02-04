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
/**
 * 
 */
package it.people.vsl;

import java.io.Serializable;
import java.sql.Timestamp;

import it.people.City;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         17/ott/2012 11:25:40
 */
public class UnsentProcessPipelineData implements Serializable {

    private static final long serialVersionUID = -3551426052110682409L;

    private Long oid;

    private Long submittedProcessOid;

    private SerializablePipelineData pipelineData;

    private Timestamp insertedTime;

    private City commune;

    public UnsentProcessPipelineData() {

    }

    /**
     * @return the oid
     */
    public final Long getOid() {
	return this.oid;
    }

    /**
     * @param oid
     *            the oid to set
     */
    public final void setOid(Long oid) {
	this.oid = oid;
    }

    /**
     * @return the submittedProcessOid
     */
    public final Long getSubmittedProcessOid() {
	return this.submittedProcessOid;
    }

    /**
     * @param submittedProcessOid
     *            the submittedProcessOid to set
     */
    public final void setSubmittedProcessOid(Long submittedProcessOid) {
	this.submittedProcessOid = submittedProcessOid;
    }

    /**
     * @return the pipelineData
     */
    public final SerializablePipelineData getPipelineData() {
	return this.pipelineData;
    }

    /**
     * @param pipelineData
     *            the pipelineData to set
     */
    public final void setPipelineData(SerializablePipelineData pipelineData) {
	this.pipelineData = pipelineData;
    }

    /**
     * @return the insertedTime
     */
    public final Timestamp getInsertedTime() {
	return this.insertedTime;
    }

    /**
     * @param insertedTime
     *            the insertedTime to set
     */
    public final void setInsertedTime(Timestamp insertedTime) {
	this.insertedTime = insertedTime;
    }

    /**
     * @return the commune
     */
    public final City getCommune() {
	return this.commune;
    }

    /**
     * @param commune
     *            the commune to set
     */
    public final void setCommune(City commune) {
	this.commune = commune;
    }

}

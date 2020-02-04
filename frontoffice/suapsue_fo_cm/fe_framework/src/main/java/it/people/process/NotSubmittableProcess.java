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
package it.people.process;

import java.sql.Timestamp;

import it.people.City;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         19/lug/2012 10:37:12
 */
public class NotSubmittableProcess {

    private Long oid;
    private String userID;
    private City commune;
    private Class processClass;
    private Timestamp creationTime;
    private String contentName;
    private String contentID;
    private String processName;
    private boolean delegate;

    public NotSubmittableProcess() {
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
     * @return the userID
     */
    public final String getUserID() {
	return this.userID;
    }

    /**
     * @param userID
     *            the userID to set
     */
    public final void setUserID(String userID) {
	this.userID = userID;
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

    /**
     * @return the processClass
     */
    public final Class getProcessClass() {
	return this.processClass;
    }

    /**
     * @param processClass
     *            the processClass to set
     */
    public final void setProcessClass(Class processClass) {
	this.processClass = processClass;
    }

    /**
     * @return the creationTime
     */
    public final Timestamp getCreationTime() {
	return this.creationTime;
    }

    /**
     * @param creationTime
     *            the creationTime to set
     */
    public final void setCreationTime(Timestamp creationTime) {
	this.creationTime = creationTime;
    }

    /**
     * @return the contentName
     */
    public final String getContentName() {
	return this.contentName;
    }

    /**
     * @param contentName
     *            the contentName to set
     */
    public final void setContentName(String contentName) {
	this.contentName = contentName;
    }

    /**
     * @return the contentID
     */
    public final String getContentID() {
	return this.contentID;
    }

    /**
     * @param contentID
     *            the contentID to set
     */
    public final void setContentID(String contentID) {
	this.contentID = contentID;
    }

    /**
     * @return the processName
     */
    public final String getProcessName() {
	return this.processName;
    }

    /**
     * @param processName
     *            the processName to set
     */
    public final void setProcessName(String processName) {
	this.processName = processName;
    }

    /**
     * @return the delegate
     */
    public final boolean isDelegate() {
	return this.delegate;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public final void setDelegate(boolean delegate) {
	this.delegate = delegate;
    }

}

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
package it.people.fsl.servizi.oggetticondivisi;

import java.io.Serializable;
import java.sql.Timestamp;

import it.people.City;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         08/nov/2012 17:46:57
 */
public class UserSignalledBug implements Serializable {

    private static final long serialVersionUID = 2223264751602397410L;

    private Long oid;

    private String subject;

    private String description;

    private String userId;

    private String firstName;

    private String lastName;

    private String eMail;

    private City commune;

    private Timestamp receivedDate;

    public UserSignalledBug() {

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
     * @return the subject
     */
    public final String getSubject() {
	return this.subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public final void setSubject(String subject) {
	this.subject = subject;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
	return this.description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the userId
     */
    public final String getUserId() {
	return this.userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public final void setUserId(String userId) {
	this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
	return this.firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public final void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public final String getLastName() {
	return this.lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public final void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * @return the eMail
     */
    public final String geteMail() {
	return this.eMail;
    }

    /**
     * @param eMail
     *            the eMail to set
     */
    public final void seteMail(String eMail) {
	this.eMail = eMail;
    }

    /**
     * @return the receivedDate
     */
    public final Timestamp getReceivedDate() {
	return this.receivedDate;
    }

    /**
     * @param receivedDate
     *            the receivedDate to set
     */
    public final void setReceivedDate(Timestamp receivedDate) {
	this.receivedDate = receivedDate;
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

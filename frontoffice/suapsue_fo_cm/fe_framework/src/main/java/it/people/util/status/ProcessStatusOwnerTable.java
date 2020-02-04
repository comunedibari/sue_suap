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
package it.people.util.status;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica - Genova
 *         31/ago/2011 21.20.31
 */
public class ProcessStatusOwnerTable {

    private String peopleId = null;
    private String firstName = null;
    private String lastName = null;
    private String taxCode = null;
    private String businessName = null;
    private String vatCode = null;
    private String info1 = "";
    private String info2 = "";

    public ProcessStatusOwnerTable() {

    }

    /**
     * @param peopleId
     * @param firstName
     * @param lastName
     * @param taxCode
     * @param businessName
     * @param vatCode
     */
    public ProcessStatusOwnerTable(String peopleId, String firstName,
	    String lastName, String taxCode, String businessName,
	    String vatCode, String info1, String info2) {
	this.peopleId = peopleId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.taxCode = taxCode;
	this.businessName = businessName;
	this.vatCode = vatCode;
	this.info1 = info1;
	this.info2 = info2;
    }

    /**
     * @return the peopleId
     */
    public final String getPeopleId() {
	return this.peopleId == null ? "" : this.peopleId;
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
	return this.firstName == null ? "" : this.firstName;
    }

    /**
     * @return the lastName
     */
    public final String getLastName() {
	return this.lastName == null ? "" : this.lastName;
    }

    /**
     * @return the taxCode
     */
    public final String getTaxCode() {
	return this.taxCode == null ? "" : this.taxCode;
    }

    /**
     * @return the businessName
     */
    public final String getBusinessName() {
	return this.businessName == null ? "" : this.businessName;
    }

    /**
     * @return the vatCode
     */
    public final String getVatCode() {
	return this.vatCode == null ? "" : this.vatCode;
    }

    /**
     * @return the isBusiness
     */
    public final boolean isBusiness() {
	return !org.apache.commons.lang.StringUtils.isEmpty(this.businessName);
    }

    /**
     * @return the info1
     */
    public final String getInfo1() {
	return this.info1;
    }

    /**
     * @param info1
     *            the info1 to set
     */
    public final void setInfo1(String info1) {
	this.info1 = info1;
    }

    /**
     * @return the info2
     */
    public final String getInfo2() {
	return this.info2;
    }

    /**
     * @param info2
     *            the info2 to set
     */
    public final void setInfo2(String info2) {
	this.info2 = info2;
    }

}

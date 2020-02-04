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
package it.people.console.dto;

import java.io.ByteArrayOutputStream;

import it.people.console.domain.Clearable;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 14/lug/2011 10.03.31
 *
 */
public class CertificateAccountDTO implements Clearable {

	private int userId;
	
	private String alias;
	
	private String password;
	
	private String passwordCheck;
	
	private String commonName;
	
	private String organizationUnit;
	
	private String organizationName;
	
	private String locality;
	
	private String stateName;
	
	private String country;
	
	private String eMail;
	
	private String validity;
	
	private String[] allowedNodes;
	
	private boolean passwordAutogeneration;

	private boolean sendDataByMail;
	
	private boolean sentMail;
	
	private ByteArrayOutputStream x509Certificate;
	
	public CertificateAccountDTO() {
		
	}

	/**
	 * @return the userId
	 */
	public final int getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the alias
	 */
	public final String getAlias() {
		return this.alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public final void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordCheck
	 */
	public final String getPasswordCheck() {
		return this.passwordCheck;
	}

	/**
	 * @param passwordCheck the passwordCheck to set
	 */
	public final void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	/**
	 * @return the commonName
	 */
	public final String getCommonName() {
		return this.commonName;
	}

	/**
	 * @param commonName the commonName to set
	 */
	public final void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * @return the organizationUnit
	 */
	public final String getOrganizationUnit() {
		return this.organizationUnit;
	}

	/**
	 * @param organizationUnit the organizationUnit to set
	 */
	public final void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	/**
	 * @return the organizationName
	 */
	public final String getOrganizationName() {
		return this.organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public final void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the locality
	 */
	public final String getLocality() {
		return this.locality;
	}

	/**
	 * @param locality the locality to set
	 */
	public final void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the stateName
	 */
	public final String getStateName() {
		return this.stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public final void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the country
	 */
	public final String getCountry() {
		return this.country;
	}

	/**
	 * @param country the country to set
	 */
	public final void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the eMail
	 */
	public final String geteMail() {
		return this.eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public final void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the validity
	 */
	public final String getValidity() {
		return this.validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public final void setValidity(String validity) {
		this.validity = validity;
	}

	/**
	 * @return the allowedNodes
	 */
	public final String[] getAllowedNodes() {
		return this.allowedNodes;
	}

	/**
	 * @param allowedNodes the allowedNodes to set
	 */
	public final void setAllowedNodes(String[] allowedNodes) {
		this.allowedNodes = allowedNodes;
	}

	/**
	 * @return the passwordAutogeneration
	 */
	public final boolean isPasswordAutogeneration() {
		return this.passwordAutogeneration;
	}

	/**
	 * @param passwordAutogeneration the passwordAutogeneration to set
	 */
	public final void setPasswordAutogeneration(boolean passwordAutogeneration) {
		this.passwordAutogeneration = passwordAutogeneration;
	}

	/**
	 * @return the x509Certificate
	 */
	public final ByteArrayOutputStream getX509Certificate() {
		return this.x509Certificate;
	}

	/**
	 * @param x509Certificate the x509Certificate to set
	 */
	public final void setX509Certificate(ByteArrayOutputStream x509Certificate) {
		this.x509Certificate = x509Certificate;
	}

	/**
	 * @return the sendDataByMail
	 */
	public final boolean isSendDataByMail() {
		return this.sendDataByMail;
	}

	/**
	 * @param sendDataByMail the sendDataByMail to set
	 */
	public final void setSendDataByMail(boolean sendDataByMail) {
		this.sendDataByMail = sendDataByMail;
	}

	/**
	 * @return the sentMail
	 */
	public final boolean isSentMail() {
		return this.sentMail;
	}

	/**
	 * @param sentMail the sentMail to set
	 */
	public final void setSentMail(boolean sentMail) {
		this.sentMail = sentMail;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setUserId(0);
		this.setAlias(null);
		this.setAllowedNodes(null);
		this.setCommonName(null);
		this.setPasswordCheck(null);
		this.setCountry(null);
		this.seteMail(null);
		this.setLocality(null);
		this.setOrganizationName(null);
		this.setOrganizationUnit(null);
		this.setPassword(null);
		this.setStateName(null);
		this.setValidity(null);
		this.setX509Certificate(null);
		this.setPasswordAutogeneration(false);
		this.setSendDataByMail(false);
		this.setSentMail(false);
	}

}

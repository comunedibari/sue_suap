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
package it.people.console.domain;

import java.io.Serializable;

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:30
 */
public class FENode extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3047505102493930704L;

	private Long id;
	
	private String name;
	
	private String feServiceURL;
	
	private String municipality;
	
	private String municipalityCode;

	private boolean delegationControlEnabled;
	
	private String delegationControlServiceURL;
	
	private String aooPrefix;
	
	private String error;

	private String selectedServicesLanguage;

	private String previousSelectedServicesLanguage;
	
	private String selectedServicesRegisterableLanguage;
	
	private String announcementMessage;
	
	private boolean showAnnouncement;
	
	private boolean onlineSign;
	
	private boolean offlineSign;
	
	public FENode(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public final Long getId() {
		return this.id;
	}
	
	public final void setId(Long nodeId) {
		this.id = nodeId;
	}
	
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getFeServiceURL() {
		return feServiceURL;
	}

	public final void setFeServiceURL(String feServiceURL) {
		this.feServiceURL = feServiceURL;
	}

	public final String getMunicipality() {
		return municipality;
	}

	public final void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public final String getMunicipalityCode() {
		return municipalityCode;
	}

	public final void setMunicipalityCode(String municipalityCode) {
		this.municipalityCode = municipalityCode;
	}

	public final boolean isDelegationControlEnabled() {
		return delegationControlEnabled;
	}

	public final void setDelegationControlEnabled(boolean delegationControlEnabled) {
		this.delegationControlEnabled = delegationControlEnabled;
	}

	public final String getDelegationControlServiceURL() {
		return delegationControlServiceURL;
	}

	public final void setDelegationControlServiceURL(
			String delegationControlServiceURL) {
		this.delegationControlServiceURL = delegationControlServiceURL;
	}

	public final String getAooPrefix() {
		return aooPrefix;
	}

	public final void setAooPrefix(String aooPrefix) {
		this.aooPrefix = aooPrefix;
	}

	/**
	 * @return the error
	 */
	public final String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public final void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the selectedServicesLanguage
	 */
	public final String getSelectedServicesLanguage() {
		return this.selectedServicesLanguage;
	}

	/**
	 * @param selectedServicesLanguage the selectedServicesLanguage to set
	 */
	public final void setSelectedServicesLanguage(String selectedServicesLanguage) {
		this.selectedServicesLanguage = selectedServicesLanguage;
	}

	/**
	 * @return the previousSelectedServicesLanguage
	 */
	public final String getPreviousSelectedServicesLanguage() {
		return this.previousSelectedServicesLanguage;
	}

	/**
	 * @param previousSelectedServicesLanguage the previousSelectedServicesLanguage to set
	 */
	public final void setPreviousSelectedServicesLanguage(
			String previousSelectedServicesLanguage) {
		this.previousSelectedServicesLanguage = previousSelectedServicesLanguage;
	}

	/**
	 * @return the selectedServicesRegisterableLanguage
	 */
	public final String getSelectedServicesRegisterableLanguage() {
		return this.selectedServicesRegisterableLanguage;
	}

	/**
	 * @param selectedServicesRegisterableLanguage the selectedServicesRegisterableLanguage to set
	 */
	public final void setSelectedServicesRegisterableLanguage(
			String selectedServicesRegisterableLanguage) {
		this.selectedServicesRegisterableLanguage = selectedServicesRegisterableLanguage;
	}
	
	/**
	 * @return the announcementMessage
	 */
	public String getAnnouncementMessage() {
		return announcementMessage;
	}

	/**
	 * @param announcementMessage the announcementMessage to set
	 */
	public void setAnnouncementMessage(String announcementMessage) {
		this.announcementMessage = announcementMessage;
	}
	
	/**
	 * @return the showAnnouncement
	 */
	public boolean isShowAnnouncement() {
		return showAnnouncement;
	}

	/**
	 * @param showAnnouncement the showAnnouncement to set
	 */
	public void setShowAnnouncement(boolean showAnnouncement) {
		this.showAnnouncement = showAnnouncement;
	}


	/**
	 * @return the onlineSign
	 */
	public boolean isOnlineSign() {
		return onlineSign;
	}

	/**
	 * @param onlineSign the onlineSign to set
	 */
	public void setOnlineSign(boolean onlineSign) {
		this.onlineSign = onlineSign;
	}

	/**
	 * @return the offlineSign
	 */
	public boolean isOfflineSign() {
		return offlineSign;
	}

	/**
	 * @param offlineSign the offlineSign to set
	 */
	public void setOfflineSign(boolean offlineSign) {
		this.offlineSign = offlineSign;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setAooPrefix(null);
		this.setDelegationControlEnabled(new Boolean(null));
		this.setDelegationControlServiceURL(null);
		this.setFeServiceURL(null);
		this.setMunicipality(null);
		this.setMunicipalityCode(null);
		this.setName(null);
		this.setId(null);
		this.setSelectedServicesLanguage(null);
		this.setSelectedServicesRegisterableLanguage(null);
		this.setPreviousSelectedServicesLanguage(null);
		this.setAnnouncementMessage(null);
		this.setShowAnnouncement(new Boolean(null));
		this.setOnlineSign(new Boolean(null));
		this.setOfflineSign(new Boolean(null));
	}

}

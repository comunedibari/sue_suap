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

/**
 * @author Riccardo Forafo'
 * @version 1.0
 * @created 02-dic-2010 15:17:30
 */
public class FEService extends AbstractBaseBean implements Clearable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5744525954549756590L;

	private Long id;
	
	private Long nodeId;
	
	private String reference;
	
	private String serviceName;

	private String beforeUpdateServiceName;
	
	private String municipality;
	
	private String area;
	
	private String subArea;
	
	private String _package;
	
	private String process;
	
	private Integer logLevel;
	
	private Integer serviceStatus;
	
	private Integer processSignEnabled;
	
	private Integer attachmentsInCitizenReceipt;
	
	private String nodeName;
	
	private Integer activationType;
	
	private Integer sendmailtoowner;

	private ProcessActivationType processActivationType = new ProcessActivationType();

	private String selectedServicesLanguage;

	private String previousSelectedServicesLanguage;
	
	private String selectedServicesRegisterableLanguage;
	
	private Integer embedAttachmentInXml;
	
	private boolean showPrivacyDisclaimer;
	
	private boolean privacyDisclaimerRequireAcceptance;
	
	private boolean onlineSign;
	
	private boolean offlineSign;
	
	//Selected tableId from combobox
	private String selectedTableId;
	
	
	public void finalize() throws Throwable {
		super.finalize();
	}

	public FEService(){

	}

	public final Long getId() {
		return this.id;
	}
	
	public final void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the nodeId
	 */
	public final Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public final void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the reference
	 */
	public final String getReference() {
		return this.reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public final void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the nodeName
	 */
	public final String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public final void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public final String getServiceName() {
		return serviceName;
	}

	public final void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public final String getMunicipality() {
		return municipality;
	}

	public final void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public final String getArea() {
		return area;
	}

	public final void setArea(String area) {
		this.area = area;
	}

	public final String getSubArea() {
		return subArea;
	}

	public final void setSubArea(String subArea) {
		this.subArea = subArea;
	}

	public final String get_package() {
		return _package;
	}

	public final void set_package(String package1) {
		_package = package1;
	}

	public final String getProcess() {
		return process;
	}

	public final void setProcess(String process) {
		this.process = process;
	}

	public final Integer getLogLevel() {
		return logLevel;
	}

	public final void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public final Integer getServiceStatus() {
		return serviceStatus;
	}

	public final void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * @return the processSignEnabled
	 */
	public final Integer getProcessSignEnabled() {
		return processSignEnabled;
	}

	/**
	 * @param processSignEnabled the processSignEnabled to set
	 */
	public final void setProcessSignEnabled(Integer processSignEnabled) {
		this.processSignEnabled = processSignEnabled;
	}

	/**
	 * @return the attachmentsInCitizenReceipt
	 */
	public final Integer getAttachmentsInCitizenReceipt() {
		return attachmentsInCitizenReceipt;
	}

	/**
	 * @param attachmentsInCitizenReceipt the attachmentsInCitizenReceipt to set
	 */
	public final void setAttachmentsInCitizenReceipt(Integer attachmentsInCitizenReceipt) {
		this.attachmentsInCitizenReceipt = attachmentsInCitizenReceipt;
	}

	public final ProcessActivationType getProcessActivationType() {
		return processActivationType;
	}

	public final void setProcessActivationType(
			ProcessActivationType processActivationType) {
		this.processActivationType = processActivationType;
		this.setActivationType(processActivationType.getActivationType().getCode());
	}

	/**
	 * @return the activationType
	 */
	public final Integer getActivationType() {
		return activationType;
	}

	/**
	 * @param activationType the activationType to set
	 */
	public final void setActivationType(Integer activationType) {
		this.activationType = activationType;
		this.getProcessActivationType().setActivationType(ProcessActivationTypeEnumeration.findActivationType(activationType));
	}

	/**
	 * @return the sendmailtoowner
	 */
	public final Integer getSendmailtoowner() {
		return this.sendmailtoowner;
	}

	/**
	 * @param sendmailtoowner the sendmailtoowner to set
	 */
	public final void setSendmailtoowner(Integer sendmailtoowner) {
		this.sendmailtoowner = sendmailtoowner;
	}

	/**
	 * @return the beforeUpdateServiceName
	 */
	public final String getBeforeUpdateServiceName() {
		return this.beforeUpdateServiceName;
	}

	/**
	 * @param beforeUpdateServiceName the beforeUpdateServiceName to set
	 */
	public final void setBeforeUpdateServiceName(String beforeUpdateServiceName) {
		this.beforeUpdateServiceName = beforeUpdateServiceName;
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
	 * @return the embedAttachmentInXml
	 */
	public Integer getEmbedAttachmentInXml() {
		return embedAttachmentInXml;
	}

	/**
	 * @param embedAttachmentInXml the embedAttachmentInXml to set
	 */
	public void setEmbedAttachmentInXml(Integer embedAttachmentInXml) {
		this.embedAttachmentInXml = embedAttachmentInXml;
	}


	/**
	 * @return the showPrivacyDisclaimer
	 */
	public boolean isShowPrivacyDisclaimer() {
		return showPrivacyDisclaimer;
	}

	/**
	 * @param showPrivacyDisclaimer the showPrivacyDisclaimer to set
	 */
	public void setShowPrivacyDisclaimer(boolean showPrivacyDisclaimer) {
		this.showPrivacyDisclaimer = showPrivacyDisclaimer;
	}

	/**
	 * @return the privacyDisclaimerRequireAcceptance
	 */
	public boolean isPrivacyDisclaimerRequireAcceptance() {
		return privacyDisclaimerRequireAcceptance;
	}

	/**
	 * @param privacyDisclaimerRequireAcceptance the privacyDisclaimerRequireAcceptance to set
	 */
	public void setPrivacyDisclaimerRequireAcceptance(
			boolean privacyDisclaimerRequireAcceptance) {
		this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
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

	/**
	 * @return the selectedTableId
	 */
	public String getSelectedTableId() {
		return selectedTableId;
	}

	/**
	 * @param selectedTableId the selectedTableId to set
	 */
	public void setSelectedTableId(String selectedTableId) {
		this.selectedTableId = selectedTableId;
	}

	/* (non-Javadoc)
	 * @see it.people.console.domain.Clearable#clear()
	 */
	public void clear() {
		this.setId(null);
		this.setNodeName(null);
		this.setServiceName(null);
		this.setMunicipality(null);
		this.setArea(null);
		this.setSubArea(null);
		this.set_package(null);
		this.setProcess(null);
		this.setLogLevel(null);
		this.setServiceStatus(null);
		this.setProcessSignEnabled(null);
		this.setAttachmentsInCitizenReceipt(null);
		this.setProcessActivationType(null);
		this.setActivationType(null);
		this.setSendmailtoowner(null);
		this.setBeforeUpdateServiceName(null);
		this.setSelectedServicesLanguage(null);
		this.setSelectedServicesRegisterableLanguage(null);
		this.setPreviousSelectedServicesLanguage(null);
		this.setReference(null);
		this.setEmbedAttachmentInXml(null);
		//Privacy
		this.setPrivacyDisclaimerRequireAcceptance(new Boolean(null));
		this.setShowPrivacyDisclaimer(new Boolean(null));
		//Sign online - offline
		this.setOnlineSign(new Boolean(null));
		this.setOfflineSign(new Boolean(null));
		//Tablevalues tableId combobox
		this.setSelectedTableId(null);
	}

}

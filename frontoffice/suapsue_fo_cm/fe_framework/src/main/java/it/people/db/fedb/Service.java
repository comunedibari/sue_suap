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
 * Created on 31-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.db.fedb;

import it.people.exceptions.PeopleDBException;

import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author FabMi
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Service {
    int id;
    String processName;
    String description;
    String processType;
    String communeId;
    boolean receiptMailAttachment;
    boolean signEnabled;
    int logId;
    boolean sendMailToOwner = true;
    // < CCD - 2012.03.18
    boolean showPrivacyDisclaimer = false;
    boolean privacyDisclaimerRequireAcceptance = false;
    boolean embedAttachmentInXml = false;
    Vector<String> requiredOperatorData = new Vector<String>();
    // CCD - 2012.03.18 >
    boolean onLineSign = true;
    boolean offLineSign = false;

    private boolean sendSummaryAttachmentWithOwnerReceipt = false;

    private Vector<String> enabledProcessors = new Vector<String>();

    private static Logger logger = Logger.getLogger(Service.class);

    /**
     * @deprecated utilizzare ServiceFactory.getEnabledServices()
     * @param communeId
     * @return
     * @throws PeopleDBException
     */
    public static Collection getEnabled(String communeId)
	    throws PeopleDBException {
	ServiceFactory factory = new ServiceFactory();
	return factory.getEnabledServices(communeId);
    }

    /**
     * @deprecated utilizzare ServiceFactory.getService()
     * @param processName
     * @param communeId
     * @return
     * @throws PeopleDBException
     */
    public static Service get(String processName, String communeId)
	    throws PeopleDBException {
	ServiceFactory factory = new ServiceFactory();
	return factory.getService(processName, communeId);
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return Returns the id.
     */
    public int getId() {
	return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return Returns the processType.
     */
    public String getProcessType() {
	return processType;
    }

    /**
     * @param processType
     *            The processType to set.
     */
    public void setProcessType(String processType) {
	this.processType = processType;
    }

    public String getCommuneId() {
	return communeId;
    }

    public void setCommuneId(String communeId) {
	this.communeId = communeId;
    }

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String processName) {
	this.processName = processName;
    }

    public int getLogId() {
	return logId;
    }

    public void setLogId(int logId) {
	this.logId = logId;
    }

    public boolean isReceiptMailAttachment() {
	return receiptMailAttachment;
    }

    public void setReceiptMailAttachment(boolean receiptMailAttachment) {
	this.receiptMailAttachment = receiptMailAttachment;
    }

    public boolean isSignEnabled() {
	return signEnabled;
    }

    public void setSignEnabled(boolean signEnabled) {
	this.signEnabled = signEnabled;
    }

    /**
     * @return the sendMailToOwner
     */
    public final boolean isSendMailToOwner() {
	return this.sendMailToOwner;
    }

    /**
     * @param sendMailToOwner
     *            the sendMailToOwner to set
     */
    public final void setSendMailToOwner(boolean sendMailToOwner) {
	this.sendMailToOwner = sendMailToOwner;
    }

    // < CCD - 2012.03.18

    /**
     * @return the showPrivacyDisclaimer
     */
    public final boolean isShowPrivacyDisclaimer() {
	return this.showPrivacyDisclaimer;
    }

    /**
     * @param showPrivacyDisclaimer
     *            the showPrivacyDisclaimer to set
     */
    public final void setShowPrivacyDisclaimer(boolean showPrivacyDisclaimer) {
	this.showPrivacyDisclaimer = showPrivacyDisclaimer;
    }

    /**
     * @return the privacyDisclaimerRequireAcceptance
     */
    public final boolean isPrivacyDisclaimerRequireAcceptance() {
	return this.privacyDisclaimerRequireAcceptance;
    }

    /**
     * @param privacyDisclaimerRequireAcceptance
     *            the privacyDisclaimerRequireAcceptance to set
     */
    public final void setPrivacyDisclaimerRequireAcceptance(
	    boolean privacyDisclaimerRequireAcceptance) {
	this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
    }

    /**
     * @return the requiredOperatorData
     */
    public final Vector<String> getRequiredOperatorData() {
	return this.requiredOperatorData;
    }

    /**
     * @param requiredOperatorData
     *            the requiredOperatorData to set
     */
    public final void setRequiredOperatorData(
	    Vector<String> requiredOperatorData) {
	this.requiredOperatorData = requiredOperatorData;
    }

    /**
     * @return the embedAttachmentInXml
     */
    public final boolean isEmbedAttachmentInXml() {
	return this.embedAttachmentInXml;
    }

    /**
     * @param embedAttachmentInXml
     *            the embedAttachmentInXml to set
     */
    public final void setEmbedAttachmentInXml(boolean embedAttachmentInXml) {
	this.embedAttachmentInXml = embedAttachmentInXml;
    }

    /**
     * @return the onLineSign
     */
    public final boolean isOnLineSign() {
	return this.onLineSign;
    }

    /**
     * @param onLineSign
     *            the onLineSign to set
     */
    public final void setOnLineSign(boolean onLineSign) {
	this.onLineSign = onLineSign;
    }

    /**
     * @return the offLineSign
     */
    public final boolean isOffLineSign() {
	return this.offLineSign;
    }

    /**
     * @param offLineSign
     *            the offLineSign to set
     */
    public final void setOffLineSign(boolean offLineSign) {
	this.offLineSign = offLineSign;
    }

    /**
     * @return the enabledProcessors
     */
    public final Vector<String> getEnabledProcessors() {
	return this.enabledProcessors;
    }

    /**
     * @param enabledProcessors
     *            the enabledProcessors to set
     */
    public final void setEnabledProcessors(Vector<String> enabledProcessors) {
	this.enabledProcessors = enabledProcessors;
    }

    /**
     * @param enabledProcessor
     */
    public final void addEnabledProcessors(String enabledProcessor) {
	if (this.getEnabledProcessors() == null) {
	    this.setEnabledProcessors(new Vector<String>());
	}
	this.getEnabledProcessors().add(enabledProcessor);
    }

    // CCD - 2012.03.18 >

}

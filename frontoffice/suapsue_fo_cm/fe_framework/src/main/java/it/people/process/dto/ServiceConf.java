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
package it.people.process.dto;

import it.people.db.fedb.Service;

import java.util.Vector;

/**
 * @author Riccardo Foraf - Engineering Ingegneria Informatica - Genova Jul 10,
 *         2013 11:25:08 AM
 */
public class ServiceConf {

    /**
	 * 
	 */
    private boolean receiptMailAttachment;

    /**
	 * 
	 */
    private boolean signEnabled;

    /**
	 * 
	 */
    private boolean sendMailToOwner = true;

    /**
	 * 
	 */
    private boolean showPrivacyDisclaimer = false;

    /**
	 * 
	 */
    private boolean privacyDisclaimerRequireAcceptance = false;

    /**
	 * 
	 */
    private boolean embedAttachmentInXml = false;

    /**
	 * 
	 */
    private boolean onLineSign = true;

    /**
	 * 
	 */
    private boolean offLineSign = false;

    /**
	 * 
	 */
    private Vector<String> requiredOperatorData = new Vector<String>();

    /**
	 * 
	 */
    private Vector<String> enabledProcessors = new Vector<String>();

    /**
     * @param receiptMailAttachment
     * @param signEnabled
     * @param sendMailToOwner
     * @param showPrivacyDisclaimer
     * @param privacyDisclaimerRequireAcceptance
     * @param embedAttachmentInXml
     * @param onLineSign
     * @param offLineSign
     * @param sendSummaryAttachmentWithOwnerReceipt
     * @param requiredOperatorData
     * @param enabledProcessors
     */
    public ServiceConf(final Service service) {
	this.setReceiptMailAttachment(service.isReceiptMailAttachment());
	this.setSignEnabled(service.isSignEnabled());
	this.setSendMailToOwner(service.isSendMailToOwner());
	this.setShowPrivacyDisclaimer(service.isShowPrivacyDisclaimer());
	this.setPrivacyDisclaimerRequireAcceptance(service
		.isPrivacyDisclaimerRequireAcceptance());
	this.setEmbedAttachmentInXml(service.isEmbedAttachmentInXml());
	this.setOnLineSign(service.isOnLineSign());
	this.setOffLineSign(service.isOffLineSign());
	this.setRequiredOperatorData(service.getRequiredOperatorData());
	this.setEnabledProcessors(service.getEnabledProcessors());
    }

    /**
     * @param receiptMailAttachment
     *            the receiptMailAttachment to set
     */
    private void setReceiptMailAttachment(boolean receiptMailAttachment) {
	this.receiptMailAttachment = receiptMailAttachment;
    }

    /**
     * @param signEnabled
     *            the signEnabled to set
     */
    private void setSignEnabled(boolean signEnabled) {
	this.signEnabled = signEnabled;
    }

    /**
     * @param sendMailToOwner
     *            the sendMailToOwner to set
     */
    private void setSendMailToOwner(boolean sendMailToOwner) {
	this.sendMailToOwner = sendMailToOwner;
    }

    /**
     * @param showPrivacyDisclaimer
     *            the showPrivacyDisclaimer to set
     */
    private void setShowPrivacyDisclaimer(boolean showPrivacyDisclaimer) {
	this.showPrivacyDisclaimer = showPrivacyDisclaimer;
    }

    /**
     * @param privacyDisclaimerRequireAcceptance
     *            the privacyDisclaimerRequireAcceptance to set
     */
    private void setPrivacyDisclaimerRequireAcceptance(
	    boolean privacyDisclaimerRequireAcceptance) {
	this.privacyDisclaimerRequireAcceptance = privacyDisclaimerRequireAcceptance;
    }

    /**
     * @param embedAttachmentInXml
     *            the embedAttachmentInXml to set
     */
    private void setEmbedAttachmentInXml(boolean embedAttachmentInXml) {
	this.embedAttachmentInXml = embedAttachmentInXml;
    }

    /**
     * @param onLineSign
     *            the onLineSign to set
     */
    private void setOnLineSign(boolean onLineSign) {
	this.onLineSign = onLineSign;
    }

    /**
     * @param offLineSign
     *            the offLineSign to set
     */
    private void setOffLineSign(boolean offLineSign) {
	this.offLineSign = offLineSign;
    }

    /**
     * @param requiredOperatorData
     *            the requiredOperatorData to set
     */
    private void setRequiredOperatorData(Vector<String> requiredOperatorData) {
	this.requiredOperatorData = requiredOperatorData;
    }

    /**
     * @param enabledProcessors
     *            the enabledProcessors to set
     */
    private void setEnabledProcessors(Vector<String> enabledProcessors) {
	this.enabledProcessors = enabledProcessors;
    }

    /**
     * @return the receiptMailAttachment
     */
    public final boolean isReceiptMailAttachment() {
	return receiptMailAttachment;
    }

    /**
     * @return the signEnabled
     */
    public final boolean isSignEnabled() {
	return signEnabled;
    }

    /**
     * @return the sendMailToOwner
     */
    public final boolean isSendMailToOwner() {
	return sendMailToOwner;
    }

    /**
     * @return the showPrivacyDisclaimer
     */
    public final boolean isShowPrivacyDisclaimer() {
	return showPrivacyDisclaimer;
    }

    /**
     * @return the privacyDisclaimerRequireAcceptance
     */
    public final boolean isPrivacyDisclaimerRequireAcceptance() {
	return privacyDisclaimerRequireAcceptance;
    }

    /**
     * @return the embedAttachmentInXml
     */
    public final boolean isEmbedAttachmentInXml() {
	return embedAttachmentInXml;
    }

    /**
     * @return the onLineSign
     */
    public final boolean isOnLineSign() {
	return onLineSign;
    }

    /**
     * @return the offLineSign
     */
    public final boolean isOffLineSign() {
	return offLineSign;
    }

    /**
     * @return the requiredOperatorData
     */
    public final Vector<String> getRequiredOperatorData() {
	return requiredOperatorData;
    }

    /**
     * @return the enabledProcessors
     */
    public final Vector<String> getEnabledProcessors() {
	return enabledProcessors;
    }

}

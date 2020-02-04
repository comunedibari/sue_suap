/**
 * 
 */
package it.people.process.sign.entity;

import java.io.Serializable;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         14/giu/2012 10:04:23
 */
public class OffLineSignProcess implements Serializable {

    private static final long serialVersionUID = 816123749209587641L;

    private String offLineSignDownloadedDocumentHash = "";

    private boolean waitingForOffLineSignedDocument = false;

    private String offLineSignedData = "";

    private long policyID = -1;

    private String userID;

    public OffLineSignProcess() {

    }

    /**
     * @return the offLineSignDownloadedDocumentHash
     */
    public final String getOffLineSignDownloadedDocumentHash() {
	return this.offLineSignDownloadedDocumentHash;
    }

    /**
     * @param offLineSignDownloadedDocumentHash
     *            the offLineSignDownloadedDocumentHash to set
     */
    public final void setOffLineSignDownloadedDocumentHash(
	    String offLineSignDownloadedDocumentHash) {
	this.offLineSignDownloadedDocumentHash = offLineSignDownloadedDocumentHash;
    }

    /**
     * @return the waitingForOffLineSignedDocument
     */
    public final boolean isWaitingForOffLineSignedDocument() {
	return this.waitingForOffLineSignedDocument;
    }

    /**
     * @param waitingForOffLineSignedDocument
     *            the waitingForOffLineSignedDocument to set
     */
    public final void setWaitingForOffLineSignedDocument(
	    boolean waitingForOffLineSignedDocument) {
	this.waitingForOffLineSignedDocument = waitingForOffLineSignedDocument;
    }

    /**
     * @return the offLineSignedData
     */
    public final String getOffLineSignedData() {
	return this.offLineSignedData;
    }

    /**
     * @param offLineSignedData
     * @param hash
     */
    public final void setOffLineSignedData(final String offLineSignedData,
	    final long policyID, final String userID, final String hash) {
	if (hash.equalsIgnoreCase(this.getOffLineSignDownloadedDocumentHash())) {
	    this.offLineSignedData = offLineSignedData;
	    this.policyID = policyID;
	    this.userID = userID;
	} else {
	    this.offLineSignedData = null;
	    this.policyID = -1;
	    this.userID = null;
	}
    }

    /**
     * @return the policyID
     */
    public final long getPolicyID() {
	return this.policyID;
    }

    /**
     * @return the userID
     */
    public final String getUserID() {
	return this.userID;
    }

}

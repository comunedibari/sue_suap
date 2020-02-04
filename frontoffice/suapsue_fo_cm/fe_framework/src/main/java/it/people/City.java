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
package it.people;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: acuffaro Date: 5-set-2003 Time: 10.56.13 To
 * change this template use Options | File Templates.
 */
public class City implements Serializable {
    private String oid;
    private String name;
    private String label;
    private String aooPrefix;
    private boolean onlineSign;
    private boolean offlineSign;

    /**
     * <p>
     * The announcementMessage to show to users.
     * 
     * @since 2.5.1
     */
    private String announcementMessage = "";

    /**
     * <p>
     * Set if the announcementMessage should be show to users.
     * 
     * @since 2.5.1
     */
    private boolean showAnnouncement = false;

    public String getOid() {
	return this.oid;
    }

    public void setOid(String p_oid) {
	this.oid = p_oid;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String cityName) {
	this.name = cityName;
    }

    public String getKey() {
	return getOid();
    }

    public void setKey(String key) {
	setOid(key);
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public String getAooPrefix() {
	return aooPrefix;
    }

    public void setAooPrefix(String aooPrefix) {
	this.aooPrefix = aooPrefix;
    }

    /**
     * @return the announcementMessage
     * @since 2.5.1
     */
    public final String getAnnouncementMessage() {
	return this.announcementMessage;
    }

    /**
     * @param announcementMessage
     *            the announcementMessage to set
     * @since 2.5.1
     */
    public final void setAnnouncementMessage(String announcementMessage) {
	this.announcementMessage = announcementMessage;
    }

    /**
     * @return the showAnnouncement
     * @since 2.5.1
     */
    public final boolean isShowAnnouncement() {
	return this.showAnnouncement;
    }

    /**
     * @param showAnnouncement
     *            the showAnnouncement to set
     * @since 2.5.1
     */
    public final void setShowAnnouncement(boolean showAnnouncement) {
	this.showAnnouncement = showAnnouncement;
    }

    /**
     * @return the onlineSign
     */
    public final boolean isOnlineSign() {
	return this.onlineSign;
    }

    /**
     * @param onlineSign
     *            the onlineSign to set
     */
    public final void setOnlineSign(boolean onlineSign) {
	this.onlineSign = onlineSign;
    }

    /**
     * @return the offlineSign
     */
    public final boolean isOfflineSign() {
	return this.offlineSign;
    }

    /**
     * @param offlineSign
     *            the offlineSign to set
     */
    public final void setOfflineSign(boolean offlineSign) {
	this.offlineSign = offlineSign;
    }

}

/**
 * 
 */
package it.people.util.info;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         13/lug/2012 15:22:59
 */
public enum ProcessInfoEnum {

    totalAttachmentsNumber("TOTAL_ATTACHMENTS", ""), signedAttachmentNumber(
	    "SIGNED_ATTACHMENTS", ""), unsignedAttachmentNumber(
	    "UNSIGNED_ATTACHMENTS", "");

    private String key;

    private String description;

    /**
     * @param key
     * @param description
     */
    private ProcessInfoEnum(String key, String description) {
	this.setKey(key);
	this.setDescription(description);
    }

    /**
     * @param key
     *            the key to set
     */
    private void setKey(String key) {
	this.key = key;
    }

    /**
     * @param description
     *            the description to set
     */
    private void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the key
     */
    public final String getKey() {
	return this.key;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
	return this.description;
    }

}

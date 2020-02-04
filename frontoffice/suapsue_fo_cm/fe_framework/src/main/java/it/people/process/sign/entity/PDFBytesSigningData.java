/**
 * 
 */
package it.people.process.sign.entity;

import it.people.util.MimeType;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 *         27/mag/2012 14:50:19
 */
public class PDFBytesSigningData extends SigningData {

    /**
     * @param key
     * @param friendlyName
     * @param signingData
     */
    public PDFBytesSigningData(final String key, final String friendlyName,
	    final byte[] signingData) {
	this.setKey(key);
	this.setFriendlyName(friendlyName);
	this.setObject(signingData);
    }

    /**
     * @param signingData
     */
    public PDFBytesSigningData(final byte[] signingData) {
	this.setObject(signingData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see it.people.process.sign.entity.SigningData#getBytes()
     */
    @Override
    public byte[] getBytes() {

	return (byte[]) this.getObject();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * it.people.process.sign.entity.SigningData#getBytes(it.people.util.MimeType
     * )
     */
    public byte[] getBytes(MimeType mimeType) {
	return this.getBytes();
    }

}

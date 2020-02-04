/**
 * 
 */
package it.people.feservice.beans;

import java.io.Serializable;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 13/mag/2012 09:57:40
 */
public class FeServiceChangeResult implements Serializable {

	private static final long serialVersionUID = 3006817756411387646L;

	private Long serviceId;
	
	private String communeId = "";

	private String _package = "";
	
	private boolean error = false;
	
	private String message = "";
	
	/**
	 * @return the communeId
	 */
	public final String getCommuneId() {
		return this.communeId;
	}

	/**
	 * @param communeId the communeId to set
	 */
	public final void setCommuneId(String communeId) {
		this.communeId = communeId;
	}

	/**
	 * @return the _package
	 */
	public final String get_package() {
		return this._package;
	}

	/**
	 * @param _package the _package to set
	 */
	public final void set_package(String _package) {
		this._package = _package;
	}

	/**
	 * @return the error
	 */
	public final boolean isError() {
		return this.error;
	}

	/**
	 * @param error the error to set
	 */
	public final void setError(boolean error) {
		this.error = error;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return this.message;
	}

	/**
	 * @param message the message to set
	 */
	public final void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the serviceId
	 */
	public final Long getServiceId() {
		return this.serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public final void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return new StringBuilder()
		.append("[service id = ")
		.append(this.getServiceId())
		.append("; communeId = ")
		.append(this.getCommuneId())
		.append("; service package = ")
		.append(this.get_package())
		.append("; deleted = ")
		.append(!this.isError())
		.append("; message = ")
		.append(this.getMessage())
		.append("]").toString();
		
	}
}

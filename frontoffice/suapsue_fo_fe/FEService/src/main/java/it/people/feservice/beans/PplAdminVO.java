/**
 * 
 */
package it.people.feservice.beans;

import java.io.Serializable;

/**
 * @author Riccardo Forafò - Engineering Ingegneria Informatica - Genova
 * 16/mag/2012 19:14:05
 */
public class PplAdminVO implements Serializable {

	private static final long serialVersionUID = -6317872465992763557L;

	private String userId;
	
	private String eMail;
	
	private String userName;
	
	private String[] adminCommuni = null;
	
	public PplAdminVO() {
		
	}

	/**
	 * @return the userId
	 */
	public final String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public final void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the userName
	 */
	public final String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public final void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the adminCommuni
	 */
	public final String[] getAdminCommuni() {
		return this.adminCommuni;
	}

	/**
	 * @param adminCommuni the adminCommuni to set
	 */
	public final void setAdminCommuni(String[] adminCommuni) {
		this.adminCommuni = adminCommuni;
	}
	
}

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
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 18.29.38
 *
 */
public class RootLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5375691284735662817L;

	private String firstName;
	
	private String lastName;
	
	private String eMail;
	
	private String password;

	private String passwordCheck;
	
	private boolean needUpdate = false;

	private boolean needLogin = false;
	
	private boolean defaultRoot = false;

	public RootLogin() {
		this.setFirstName(null);
		this.setLastName(null);
		this.seteMail(null);
		this.setPassword(null);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}


	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordCheck
	 */
	public String getPasswordCheck() {
		return passwordCheck;
	}

	/**
	 * @param passwordCheck the passwordCheck to set
	 */
	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	/**
	 * @return the needUpdate
	 */
	public boolean isNeedUpdate() {
		return needUpdate;
	}

	/**
	 * @param needUpdate the needUpdate to set
	 */
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	/**
	 * @return the needLogin
	 */
	public final boolean isNeedLogin() {
		return needLogin;
	}

	/**
	 * @param needLogin the needLogin to set
	 */
	public final void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}
	
	/**
	 * @return the defaultRoot
	 */
	public boolean isDefaultRoot() {
		return defaultRoot;
	}

	/**
	 * @param defaultRoot the defaultRoot to set
	 */
	public void setDefaultRoot(boolean defaultRoot) {
		this.defaultRoot = defaultRoot;
	}
}

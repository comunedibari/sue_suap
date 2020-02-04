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
package it.people.console.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 24/giu/2011 17.20.08
 *
 */
public class Root implements Serializable {

	private static final long serialVersionUID = 4142051593320693127L;

	private String firstName;
	
	private String lastName;
	
	private String description;
	
	private String eMail;

	private String password = "";
	
	private Timestamp lastLogin;
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param description
	 * @param eMail
	 * @param password
	 */
	public Root(String firstName, String lastName, String description, String eMail, String password) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDescription(description);
		this.seteMail(eMail);
		this.setPassword(password);
	}

	/**
	 * @param firstName the firstName to set
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public final void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public final String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public final String getLastName() {
		return lastName;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @return the eMail
	 */
	public final String geteMail() {
		return eMail;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @return the lastLogin
	 */
	public final Timestamp getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public final void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

}

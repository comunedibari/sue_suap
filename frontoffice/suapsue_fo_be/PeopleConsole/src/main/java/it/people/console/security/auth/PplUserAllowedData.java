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
package it.people.console.security.auth;

import java.util.ArrayList;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created Feb 7, 2013 9:24:47 AM
 *
 */
public class PplUserAllowedData<T> {

	private boolean allPermissions;
	
	private ArrayList<T> permissions;
	
	public PplUserAllowedData(boolean allPermissions, ArrayList<T> permissions) {
		this.setAllPermissions(allPermissions);
		this.setPermissions(permissions);
	}

	private void setAllPermissions(boolean allPermissions) {
		this.allPermissions = allPermissions;
	}

	private void setPermissions(ArrayList<T> permissions) {
		this.permissions = permissions;
	}

	public final boolean isAllPermissions() {
		return allPermissions;
	}

	public final ArrayList<T> getPermissions() {
		return permissions;
	}
	
}

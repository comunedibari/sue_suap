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
package it.people.feservice.exceptions;

import java.rmi.RemoteException;

/**
 * @author Riccardo Foraf� - Engineering Ingegneria Informatica S.p.A. - Sede di Genova
 *
 */
public class FEInterfaceExtException extends RemoteException {

	private static final long serialVersionUID = -3342513194984089620L;
	
	private String error = "";
	
	public FEInterfaceExtException() {
		super();
	}

	public FEInterfaceExtException(String error) {
		super(error);
		this.setError(error);
	}

	public FEInterfaceExtException(String error, Throwable t) {
		super(error, t);
		this.setError(error);
	}
	
	public String toString() {
		return this.getError();
	}
	
	public final String getError() {
		return error;
	}

	public final void setError(String error) {
		this.error = error;
	}

}

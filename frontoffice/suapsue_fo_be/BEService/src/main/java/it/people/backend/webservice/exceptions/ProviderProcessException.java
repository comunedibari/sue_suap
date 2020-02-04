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
package it.people.backend.webservice.exceptions;

public class ProviderProcessException extends Exception {

	private static final long serialVersionUID = 8232164478059093435L;
	
	private String error = "Error while processing request.";
	
	public ProviderProcessException() {
		super();
	}

	public ProviderProcessException(Exception e) {
		super(e);
	}

	public ProviderProcessException(Throwable t) {
		super(t);
	}

	public ProviderProcessException(Exception e, String error) {
		super(e);
		this.setError(error);
	}

	public ProviderProcessException(Throwable t, String error) {
		super(t);
		this.setError(error);
	}
	
	public String toString() {
		return this.toString();
	}
	
	public final String getError() {
		return error;
	}

	public final void setError(String error) {
		this.error = error;
	}

}

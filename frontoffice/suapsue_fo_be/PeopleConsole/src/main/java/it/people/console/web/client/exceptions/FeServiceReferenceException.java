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
package it.people.console.web.client.exceptions;

public class FeServiceReferenceException extends Exception {

	private static final long serialVersionUID = 2114016539222809637L;
	
	private String error = "Error while querying for the fe reference." ;
	
	public FeServiceReferenceException() {
		super();
	}

	public FeServiceReferenceException(String error) {
		super(error);
		this.setError(error);
	}

	public FeServiceReferenceException(Exception e) {
		super(e);
	}

	public FeServiceReferenceException(Exception e, String error) {
		super(error, e);
		this.setError(error);
	}

	public FeServiceReferenceException(Throwable t) {
		super(t);
	}

	public FeServiceReferenceException(Throwable t, String error) {
		super(error, t);
		this.setError(error);
	}
	
	public String toString() {
		return this.getError();
	}
	
	private String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}

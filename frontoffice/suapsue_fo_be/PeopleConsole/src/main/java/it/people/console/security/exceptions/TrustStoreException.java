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
package it.people.console.security.exceptions;

import java.security.cert.CertificateException;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 20/giu/2011 19.02.05
 *
 */
public class TrustStoreException extends CertificateException {

	private static final long serialVersionUID = -891915451587712705L;
	String error = "";
	
	public TrustStoreException() {
		super();
	}
	
	public TrustStoreException(String error) {
		super();
		this.error = error;
	}

	public TrustStoreException(Exception exception) {
		super(exception);
	}

	public TrustStoreException(Exception exception, String error) {
		super(error, exception);
		this.setError(error);
	}

	public TrustStoreException(Throwable throwable) {
		super(throwable);
	}

	public TrustStoreException(Throwable throwable, String error) {
		super(error, throwable);
		this.setError(error);
	}
	
	private void setError(String error) {
		this.error = error;
	}
	
	public String getError() {
		return this.error;
	}
	
}

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
package it.people.console.persistence.dbupgrade.exceptions;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/lug/2011 10.38.12
 *
 */
public class UpgradeQueueException extends Exception {

	private static final long serialVersionUID = -3811626259678884528L;
	
	private String error = "";
	
	public UpgradeQueueException() {
		super();
	}

	public UpgradeQueueException(String error) {
		super(error);
		this.setError(error);
	}

	public UpgradeQueueException(Exception exception) {
		super(exception);
	}

	public UpgradeQueueException(Exception exception, String error) {
		super(error, exception);
		this.setError(error);
	}

	public UpgradeQueueException(Throwable throwable) {
		super(throwable);
	}

	public UpgradeQueueException(Throwable throwable, String error) {
		super(error, throwable);
		this.setError(error);
	}
	
	public String getError() {
		return this.error;
	}
	
	private void setError(String error) {
		this.error = error;
	}
}

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
package it.people.console.dto;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 11/set/2012 08:41:03
 *
 */
public class FTPTransferResult {

	private boolean error;
	
	private String errorMessage;
	
	private String message;
	
	private String transferredFileName;
	
	/**
	 * 
	 */
	public FTPTransferResult() {
		this.setError(false);
		this.setErrorMessage("");
		this.setMessage("");
		this.setTransferredFileName("");
	}
	
	/**
	 * @param error
	 * @param message
	 */
	public FTPTransferResult(final boolean error, final String errorMessage, final String message) {
		this.setError(error);
		this.setErrorMessage(errorMessage);
		this.setMessage(message);
		this.setTransferredFileName("");
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
	 * @return the transferredFileName
	 */
	public final String getTransferredFileName() {
		return this.transferredFileName;
	}

	/**
	 * @param transferredFileName the transferredFileName to set
	 */
	public final void setTransferredFileName(String transferredFileName) {
		this.transferredFileName = transferredFileName;
	}

	/**
	 * @return the errorMessage
	 */
	public final String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}

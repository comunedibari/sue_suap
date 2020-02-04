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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import java.io.Serializable;
import java.util.ArrayList;

public class ErrorBean extends BaseBean implements Serializable{

	private static final long serialVersionUID = -1064212314717151531L;
	
	private boolean sendMailError;
	private boolean viewStackTrace;
	private boolean saveXML;
	
	private String linkBack;
	private String shortMessage;
	ArrayList stackTrace;
	
	public ErrorBean(){
		sendMailError = false;
		viewStackTrace = false;
		linkBack = "";
		saveXML = false;
		stackTrace = new ArrayList();
		shortMessage = "";
	}



	public ArrayList getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(ArrayList stackTrace) {
		this.stackTrace = stackTrace;
	}
	public void addStackTrace(String error){
		this.stackTrace.add(error);
	}

	public String getLinkBack() {
		return linkBack;
	}

	public void setLinkBack(String linkBack) {
		this.linkBack = linkBack;
	}

	public boolean isSaveXML() {
		return saveXML;
	}

	public void setSaveXML(boolean saveXML) {
		this.saveXML = saveXML;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}



	public boolean isSendMailError() {
		return sendMailError;
	}



	public void setSendMailError(boolean sendMailError) {
		this.sendMailError = sendMailError;
	}



	public boolean isViewStackTrace() {
		return viewStackTrace;
	}



	public void setViewStackTrace(boolean viewStackTrace) {
		this.viewStackTrace = viewStackTrace;
	}
	
}

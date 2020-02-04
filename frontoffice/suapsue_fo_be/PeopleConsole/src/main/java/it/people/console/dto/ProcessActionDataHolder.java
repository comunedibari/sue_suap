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

import javax.servlet.http.HttpServletRequest;

import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.security.AbstractCommand;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 13/lug/2011 17.19.05
 *
 */
public class ProcessActionDataHolder {

	private String pagedListHolderId;
	
	private AbstractCommand.CommandActions action;
	
	private EditableRowInputData editableRowInputData;
	
	private HttpServletRequest request;
	
	/**
	 * @param pagedListHolderId
	 * @param action
	 * @param editableRowInputData
	 * @param request
	 */
	public ProcessActionDataHolder(final String pagedListHolderId, 
			final AbstractCommand.CommandActions action, 
			final EditableRowInputData editableRowInputData, 
			final HttpServletRequest request) {
		this.setPagedListHolderId(pagedListHolderId);
		this.setAction(action);
		this.setEditableRowInputData(editableRowInputData);
		this.setRequest(request);
	}

	/**
	 * @param pagedListHolderId the pagedListHolderId to set
	 */
	private void setPagedListHolderId(String pagedListHolderId) {
		this.pagedListHolderId = pagedListHolderId;
	}

	/**
	 * @param action the action to set
	 */
	private void setAction(AbstractCommand.CommandActions action) {
		this.action = action;
	}

	/**
	 * @param editableRowInputData the editableRowInputData to set
	 */
	private void setEditableRowInputData(EditableRowInputData editableRowInputData) {
		this.editableRowInputData = editableRowInputData;
	}

	/**
	 * @param request the request to set
	 */
	private void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the pagedListHolderId
	 */
	public final String getPagedListHolderId() {
		return this.pagedListHolderId;
	}

	/**
	 * @return the action
	 */
	public final AbstractCommand.CommandActions getAction() {
		return this.action;
	}

	/**
	 * @return the editableRowInputData
	 */
	public final EditableRowInputData getEditableRowInputData() {
		return this.editableRowInputData;
	}

	/**
	 * @return the request
	 */
	public final HttpServletRequest getRequest() {
		return this.request;
	}
	
}

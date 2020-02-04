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
package it.people.console.persistence.beans.support;

import it.people.console.security.Command;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 16/gen/2011 18.29.06
 *
 */
public class EditableRowActions {

	private Command deleteAction;
	
	private Command editAction;
	
	private Command cancelAction;
	
	private Command saveAction;

	private Command cancelNewAction;
	
	private Command saveNewAction;
	
	private boolean deleteActionEnabled = true;
	
	public EditableRowActions(final Command deleteAction, final Command editAction, 
			final Command cancelAction, final Command saveAction, 
			final Command cancelNewAction, final Command saveNewAction) {
		this.setDeleteAction(deleteAction);
		this.setEditAction(editAction);
		this.setCancelAction(cancelAction);
		this.setSaveAction(saveAction);
		this.setCancelNewAction(cancelNewAction);
		this.setSaveNewAction(saveNewAction);
	}

	/**
	 * @param deleteAction the deleteAction to set
	 */
	private void setDeleteAction(Command deleteAction) {
		this.deleteAction = deleteAction;
	}

	/**
	 * @param editAction the editAction to set
	 */
	private void setEditAction(Command editAction) {
		this.editAction = editAction;
	}

	/**
	 * @param cancelAction the cancelAction to set
	 */
	private void setCancelAction(Command cancelAction) {
		this.cancelAction = cancelAction;
	}

	/**
	 * @param saveAction the saveAction to set
	 */
	private void setSaveAction(Command saveAction) {
		this.saveAction = saveAction;
	}

	/**
	 * @param cancelNewAction the cancelNewAction to set
	 */
	private void setCancelNewAction(Command cancelNewAction) {
		this.cancelNewAction = cancelNewAction;
	}

	/**
	 * @param saveNewAction the saveNewAction to set
	 */
	private void setSaveNewAction(Command saveNewAction) {
		this.saveNewAction = saveNewAction;
	}

	/**
	 * @return the deleteAction
	 */
	public final Command getDeleteAction() {
		return deleteAction;
	}

	/**
	 * @return the editAction
	 */
	public final Command getEditAction() {
		return editAction;
	}

	/**
	 * @return the cancelAction
	 */
	public final Command getCancelAction() {
		return cancelAction;
	}

	/**
	 * @return the saveAction
	 */
	public final Command getSaveAction() {
		return saveAction;
	}

	/**
	 * @return the cancelNewAction
	 */
	public final Command getCancelNewAction() {
		return cancelNewAction;
	}

	/**
	 * @return the saveNewAction
	 */
	public final Command getSaveNewAction() {
		return saveNewAction;
	}

	/**
	 * @return the deleteActionEnabled
	 */
	public final boolean isDeleteActionEnabled() {
		return this.deleteActionEnabled;
	}

	/**
	 * @param deleteActionEnabled the deleteActionEnabled to set
	 */
	public final void setDeleteActionEnabled(boolean deleteActionEnabled) {
		this.deleteActionEnabled = deleteActionEnabled;
	}

}

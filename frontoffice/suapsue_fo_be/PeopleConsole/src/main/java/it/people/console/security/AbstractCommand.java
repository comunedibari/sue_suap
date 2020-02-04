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
package it.people.console.security;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 14/gen/2011 22.17.06
 *
 */
public abstract class AbstractCommand implements Command {

	private String name = "";
	
	private String id = "";
	
	private String value = "";
	
	private String src = "";

	private String disabledSrc = "";
	
	private Types type;
	
	private String href = "";

	private String hrefTitle = "";
	
	private CommandActions commandAction;
	
	
	protected void setName(String name) {
		this.name = name;
	}

	protected void setId(String id) {
		this.id = id;
	}

	protected void setValue(String value) {
		this.value = value;
	}

	protected void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @param type the type to set
	 */
	protected void setType(Types type) {
		this.type = type;
	}

	/**
	 * @param href the href to set
	 */
	protected void setHref(String href) {
		this.href = href;
	}

	/**
	 * @param hrefTitle the hrefTitle to set
	 */
	protected void setHrefTitle(String hrefTitle) {
		this.hrefTitle = hrefTitle;
	}

	/**
	 * @param disabledSrc the disabledSrc to set
	 */
	protected void setDisabledStateSrc(String disabledSrc) {
		this.disabledSrc = disabledSrc;
	}
	
	/**
	 * @param commandAction the commandAction to set
	 */
	protected void setCommandAction(CommandActions commandAction) {
		this.commandAction = commandAction;
	}

	public final String getName() {
		return name;
	}

	public final String getId() {
		return id;
	}

	public final String getValue() {
		return value;
	}

	public final String getSrc() {
		return src;
	}
	
	/**
	 * @return the type
	 */
	public final Types getType() {
		return type;
	}

	/**
	 * @return the href
	 */
	public final String getHref() {
		return href;
	}

	/**
	 * @return the hrefTitle
	 */
	public final String getHrefTitle() {
		return hrefTitle;
	}

	/**
	 * @return the disabledSrc
	 */
	public final String getDisabledStateSrc() {
		return disabledSrc;
	}

	


	/**
	 * @return the commandAction
	 */
	public final CommandActions getCommandAction() {
		return commandAction;
	}




	public enum Types {
		
		input(0), link(1);
		
		private int type;
		
		private Types(int type) {
			this.setType(type);
		}

		/**
		 * @return the type
		 */
		public final int getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		private void setType(int type) {
			this.type = type;
		}
		
	}

	public enum CommandActions {
		
		delete("delete"), edit("edit"), cancel("cancel"), save("save"), 
		cancelNew("cancelNew"), saveNew("saveNew"), 
		viewLog("viewLog"), viewAuditConversations("viewAuditConversations"),
		auditStatistiche("auditStatistiche"),setDefault("setDefault"),
		userNotificationsError("userNotificationsError"),
		userNotificationsSuggestion("userNotificationsSuggestion");
		
		private String action;
		
		private CommandActions(String action) {
			this.setAction(action);
		}

		/**
		 * @return the action
		 */
		public final String getAction() {
			return action;
		}

		/**
		 * @param action the action to set
		 */
		private void setAction(String action) {
			this.action = action;
		}
		
		/**
		 * @param action
		 * @return
		 */
		public static CommandActions getCommandAction(String action) {
			
			if (action.equalsIgnoreCase(delete.getAction())) {
				return delete;
			}
			else if (action.equalsIgnoreCase(edit.getAction())) {
				return edit;
			}
			else if (action.equalsIgnoreCase(cancel.getAction())) {
				return cancel;
			}
			else if (action.equalsIgnoreCase(save.getAction())) {
				return save;
			}
			else if (action.equalsIgnoreCase(cancelNew.getAction())) {
				return cancelNew;
			}
			else if (action.equalsIgnoreCase(saveNew.getAction())) {
				return saveNew;
			}
			else if (action.equalsIgnoreCase(viewLog.getAction())) {
				return viewLog;
			}
			else if (action.equalsIgnoreCase(viewAuditConversations.getAction())) {
				return viewAuditConversations;
			}
			else if (action.equalsIgnoreCase(auditStatistiche.getAction())) {
				return auditStatistiche;
			}
			else if (action.equalsIgnoreCase(setDefault.getAction())) {
				return setDefault;
			}
			else if (action.equalsIgnoreCase(userNotificationsError.getAction())) {
				return userNotificationsError;
			}
			else if (action.equalsIgnoreCase(userNotificationsSuggestion.getAction())) {
				return userNotificationsSuggestion;
			}
			else {
				return null;
			}
			
		}
		
	}
	
}

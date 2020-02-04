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
package it.people.console.config;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 25/giu/2011 11.13.12
 *
 */
public final class ConsoleConfigurationCatalog {

	public static enum CatalogTypes {
		
		_array("Array"), 
		_boolean("Boolean"), 
		_byte("Byte"), 
		_character("Character"), 
		_double("Double"), 
		_float("Float"), 
		_integer("Integer"), 
		_long("Long"), 
		_short("Short"), 
		_string("String");

		private String value;
		
		/**
		 * @param value
		 */
		private CatalogTypes(String value) {
			this.setValue(value);
		}

		/**
		 * @param value the value to set
		 */
		private void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public final String getValue() {
			return value;
		}
		
	}
	
	public static final class RootLoginCatalog {
		
		public static final String ROOT_PASSWORD_MIN_LENGTH = "rootPasswordMinLength";

		public static final String ROOT_AND_CONSOLE_ADMIN_ALWAYS_GRANT_ACCESS = "rootAndConsoleAdminAlwaysGrantAccess";
		
	}

	public static final class MailSystemCatalog {
		
		public static final String MAIL_TRANSPORT_AUTH = "mailTransportAuth";
		
		public static final String MAIL_TRANSPORT_FROM = "mailTransportFrom";

		public static final String MAIL_TRANSPORT_HOST = "mailTransportHost";

		public static final String MAIL_TRANSPORT_PASSWORD = "mailTransportPassword";

		public static final String MAIL_TRANSPORT_PORT = "mailTransportPort";

		public static final String MAIL_TRANSPORT_PROTOCOL = "mailTransportProtocol";

		public static final String MAIL_TRANSPORT_USERNAME = "mailTransportUsername";

		public static final String MAIL_TRANSPORT_USESSL = "mailTransportUsessl";

		public static final String MAIL_TRANSPORT_USETLS = "mailTransportUsetls";
		
	}
	
	public static final class SecurityCatalog {
		
		public static final String ROOT_AND_CONSOLE_ADMIN_ALWAYS_GRANT_ACCESS = "rootAndConsoleAdminAlwaysGrantAccess";

		public static final String CERTIFICATES_STANDARD_VALIDITY = "certificatesStandardValidity";
		
	}
	
	public static final class MonitoringCatalog {
		
		public static final String MONITORING_FTP_HOST = "monitoringFTPHost";
		
		public static final String MONITORING_FTP_PASSWORD = "monitoringFTPPassword";
		
		public static final String MONITORING_FTP_PORT = "monitoringFTPPort";
		
		public static final String MONITORING_FTP_USER = "monitoringFTPUser";
		
	}
	
	public static final class SchedulerCatalog {
		
		public static final String CONSOLE_INFO_UPDATE_TRIGGER = "consoleInfoUpdateTrigger";
		
	}
	
}

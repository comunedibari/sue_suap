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
package it.people.console.utils;


/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica S.p.A.
 * @created 11/mag/2011 09.54.32
 *
 */
public final class Constants {

	public static final int UNBOUND_VALUE = 9999; //nodi

	public static final String UNBOUND_STRING_VALUE = "*"; //services 
	
	public final static class System { 
	
		public static final String SESSION_LAST_EXCEPTION_KEY = "LAST_EXCEPTION";
		
		public static final String SERVLET_CONTEXT_CONSOLE_CONFIGURATION_PROPERTIES = "servletContextConsoleConfigurationProperties";

		public static final String SERVLET_CONTEXT_TEMPORARY_PATH_EXISTS = "TEMPORARY_PATH_EXISTS";
		
		public static final String DEFAULT_ROOT_PASSWORD = "1234";
		
		public static final String ROOT = "/";

	}
	
	public final static class Mail {

		public static final String PROTOCOL_SMTP =  "smtp";

		public static final String PROTOCOL_SMTPS =  "smtps";

		public static final String SMTP_DEFAULT_PORT = "25";
		
	}
	
	public final static class Security {

		public static final String ROOT_ROLE = "ROOT";

		public static final String CONSOLE_ADMIN_ROLE = "CONSOLE_ADMIN";
		
		public static final String NODE_ADMIN_ROLE = "NODE_ADMIN";
		
		public static final String FE_SERVICES_ADMIN_ROLE = "FE_SERVICES_ADMIN";
		
		public static final String BE_SERVICES_ADMIN_ROLE = "BE_SERVICES_ADMIN";
		
		public static final String SECURITY_LOGS_VIEWER_ROLE = "SECURITY_LOGS_VIEWER";
		
		public static final String SECURITY_XML_LOGS_VIEWER_ROLE = "SECURITY_XML_LOGS_VIEWER";
		
		public static final String SECURITY_XML_LOGS_ASSIGNER_ROLE = "SECURITY_XML_LOGS_ASSIGNER";
		
		public static final String STATISTICS_VIEWER_ROLE = "STATISTICS_VIEWER";
		
		public static final String ADVICES_ADMIN_ROLE = "ADVICES_ADMIN";
		
		public static final String PEOPLE_ADMIN_ROLE = "PEOPLE_ADMIN";
		
		
		public static final String ROOT_USER_LOGIN_IDP = "rtorto00a01a326f@peopleconsole.it";
		
		public static final String ROOT_TAX_CODE = "rtorto00a01a326f";
		
		public static final String AUTHENTICATED_USER_DATA_SESSION_KEY = "it.people.sirac.authenticated_user_data";
				
	}

	public final static class Persistence {
	
	    public static final String DB_UNKNOWN_PRODUCT_NAME = "unknown";

	    public static final String ORACLE_DB = "oracle";

	    public static final String MYSQL_DB = "mysql";

	    public static final String HSQLDB_DB = "hsql";
		
	}

	public final static class DbUpgrade {
		
		public static final String SCRITP_NAME_PREFIX = "updateDb_sp_";

		public static final String SCRITP_VERSION_NUMBERS_SEPARATOR = "_";
		
		public static final String SCRITP_NAME_SUFFIX = ".sql";
		
	}
	
	public static class Queries {
		
		public static final String REGISTERED_NODES_QUERY = "registeredNodes.query";

		public static final String REGISTERED_NODES_CODES_QUERY = "registeredNodesCodes.query";
		
		public static final String REGISTERED_FE_SERVICES_QUERY = "registeredFEServices.query";
		
		public static final String REGISTERED_BE_SERVICES_QUERY = "registeredBEServices.query";
		
		public static final String REGISTERED_BE_SERVICES_URLS_AND_NODE_NAMES_QUERY = "registeredBEServicesUrlsAndNodeNames.query";
		
		public static final String ACCOUNT_EXISTS_QUERY = "accountExists.query";

		public static final String DELETE_ACCOUNT_QUERY1 = "deleteAccount.query1";

		public static final String DELETE_ACCOUNT_QUERY2 = "deleteAccount.query2";

		public static final String DELETE_ACCOUNT_QUERY3 = "deleteAccount.query3";

		public static final String DELETE_ACCOUNT_QUERY4 = "deleteAccount.query4";

		public static final String DELETE_ACCOUNT_QUERY5 = "deleteAccount.query5";

		public static final String DELETE_ACCOUNT_QUERY6 = "deleteAccount.query6";

		public static final String DELETE_ACCOUNT_QUERY8 = "deleteAccount.query8";
		
		public static final String DELETE_ACCOUNT_QUERY7 = "deleteAccount.query7";
		
		public static final String GET_ACCOUNT_DATA_QUERY = "getAccountData.query";

		
		
		public static final String GET_ACCOUNT_ALLOWED_NODES_QUERY = "getAccountAllowedNodes.query";

		public static final String GET_ACCOUNT_ALLOWED_FESERVICES_QUERY = "getAccountAllowedFEServices.query";

		public static final String GET_ACCOUNT_ALLOWED_BESERVICES_QUERY = "getAccountAllowedBEServices.query";

		public static final String GET_ACCOUNT_AUTHORITIES_QUERY = "getAccountAuthorities.query";

		public static final String GET_ACCOUNT_ALLOWED_PPLADMIN_NODES_QUERY = "getAccountAllowedPplAdminNodes.query";
		
		
		
		public static final String INSERT_USER_CERTIFICATE_QUERY = "insertUserCertificate.query";

		public static final String DELETE_USER_CERTIFICATE_QUERY = "deleteUserCertificate.query";

		public static final String UPDATE_CONFIGURATION_QUERY = "updateConfiguration.query";

		public static final String REGISTERED_NODES_WITH_BE_SERVICES_QUERY = "registeredNodesWithBEServices.query";
		
		public static final String REGISTERED_BE_SERVICES_ALL_DATA_QUERY = "registeredBEServicesAllData.query";

		public static final String NODE_REGISTERED_BE_SERVICES_ALL_DATA_QUERY = "nodeRegisteredBEServicesAllData.query";
		
		public static final String FRAMEWORK_MESSAGES_LOCALES = "frameworkMessagesLocales.query";

		public static final String FRAMEWORK_MESSAGES_LOCALE_BY_ID = "frameworkMessagesByLocale.query";
		
		public static final String FE_REGISTERED_SERVICES_PACKAGES = "feRegisteredServicesPackages.query";

		public static final String SERVICE_MESSAGES_LOCALE_BY_ID = "serviceMessagesByLocale.query";
		
		public static final String SERVICE_MESSAGES_LOCALE_BY_NODE_ID_LOCALE = "serviceMessagesByNodeIdLocale.query";

		public static final String SERVICE_MESSAGES_LOCALES = "serviceMessagesLocales.query";
		
		public static final String FRAMEWORK_MESSAGES_REGISTERABLE_LOCALES = "frameworkMessagesRegisterableLocales.query";
		
		public static final String SERVICE_MESSAGES_REGISTERABLE_LOCALES = "serviceMessagesRegisterableLocales.query";
		
		public static final String USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS = "userpreferences.pagedListHoldersSettings.query";

		public static final String USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_COUNT = "userpreferences.pagedListHoldersSettings.count.query";

		public static final String USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_UPDATE = "userpreferences.pagedListHoldersSettings.update.query";

		public static final String USER_PREFERENCES_PAGED_LIST_HOLDERS_SETTINGS_INSERT = "userpreferences.pagedListHoldersSettings.insert.query";

		public static final String FE_NODES_LIST_QUERY = "feNodesList.query";
		
		public static final String FE_NODES_AVAILABLE_SERVICES_QUERY = "feNodesAvailableServices.query";

		public static final String REGISTER_BUNDLE_QUERY = "registerBundle.query";

		public static final String SERVICE_MESSAGES_BY_NODE_ID_LOCALES = "serviceMessagesLocalesByNodeId.query";
		
		public static final String SERVICE_MESSAGES_BY_NODE_ID_REGISTERABLE_LOCALES = "serviceMessagesRegisterableLocalesByNodeId.query";
		
		public static final String SERVICE_MESSAGES_BUNDLE_REF_BY_NODE_ID_LOCALE_QUERY = "serviceMessagesBundleRefByNodeIdLocale.query";
		
		public static final String SERVICE_MESSAGE_KEY_BY_ID_QUERY = "serviceMessageKeyById.query";
	
		public static final String INSERT_BUNDLE_PROPERTY_QUERY = "insertBundleProperty.query";

		public static final String UPDATE_BUNDLE_PROPERTY_QUERY = "updateBundleProperty.query";
		
		public static final String SEARCH_BUNDLE_KEY_QUERY = "searchBundleKey.query";
		
		public static final String IS_REGISTERED_BE_SERVICE_QUERY = "isRegisteredBEService.query";
		
		public static final String NODES_ORPHANED_BE_SERVICES_QUERY = "nodesOrphanedBeServices.query";

		public static final String NODES_ORPHANED_BE_SERVICES_DELETION_QUERY = "nodesOrphanedBeServicesDeletion.query";
		
		public static final String BE_SERVICES_MASSIVE_CHANGE_QUERY = "beServicesMassiveChange.query";
		
		public static final String ALL_BE_SERVICES_MASSIVE_CHANGE_QUERY = "allBeServicesMassiveChange.query";

		public static final String TABLEVALUES_TABLEID_BY_PROCESS_NAME = "tableValuesTableIdByProcessName.query";
		
		public static final String TABLEVALUES_TABLEID_BY_PROCESS_AND_NODEID = "tableValuesTableIdByProcessNameAndNodeId.query";
		
		public static final String TABLEVALUES_TABLEID_BY_PROCESS_NODEID_NULL = "tableValuesTableIdByProcessNameAndNodeIdNull.query";
		
		public static final String TABLEVALUES_PROPERTIES_BY_TABLEVALUEREF = "tableValuesPropertiesByTablevalueRef.query";
	
		public static final String INSERT_TABLEVALUE_PROPERTY = "tableValuesPropertiesInsert.query";

		public static final String UPDATE_TABLEVALUE_PROPERTY = "tableValuesPropertiesUpdate.query";
		
		public static final String DELETE_TABLEVALUE_PROPERTIES = "tableValuesPropertiesDelete.query";
		
		//Audit Processors Queries
		public static final String AUDIT_PROCESSORS_FOR_SERVICE = "auditProcessorsForService.query";

		public static final String CHECK_EXISTS_SERVICE_AUDIT_PROCESSOR = "checkExistsServiceAuditProcessor.query";

		public static final String UPDATE_SERVICE_AUDIT_PROCESSOR = "updateServiceAuditProcesor.query";

		public static final String INSERT_SERVICE_AUDIT_PROCESSOR = "insertServiceAuditProcessor.query";

		public static final String ALL_FE_INTERFACES = "allFEInterfaces.query";
		
		public static final String FE_INTERFACES_BY_COMMUNE_LIST = "feInterfacesByCommuneList.query";
		
		
		//Velocity Templates Queries 
		public static final String VELOCITY_TEMPLATES_GENERIC = "velocityTemplateGeneric.query";
		
		public static final String VELOCITY_TEMPLATES_FOR_SERVICE = "velocityTemplatesForService.query";
		
		public static final String VELOCITY_TEMPLATES_FOR_NODE = "velocityTemplatesForNode.query";
		
	}
	
	public static class ControllerUtils {
		
		public static final String PROCESS_ACTION_HOLDER_DATA_KEY = "PROCESS_ACTION_HOLDER_DATA";
		
		public static final String DELETE_CONFIRMATION_REQUIRED = "DELETE_CONFIRMATION_REQUIRED";
		
		public static final String TEMPORARY_DOWNLOAD_FILE = "TEMPORARY_DOWNLOAD_FILE";
		
		public static final String CONTROLLERS_PROPERTIES_FILE = "/support/static/controllers.properties";

		public static final String LOGOUT_TMP_MODEL_SESSION_KEY = "LOGOUT_TMP_MODEL_SESSION";
		
		public static final String SHOW_LOGOUT_MODEL_ATTRIBUTE_KEY = "includeLogout";
		
		public static final String FE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION = "modifica";

		public static final String FE_SERVICES_CONTROLLER_MASSIVE_DELETE_ACTION = "elimina";

		public static final String BE_SERVICES_CONTROLLER_MASSIVE_PARAMETERS_CHANGE_ACTION = "modifica";

		public static final String BE_SERVICES_CONTROLLER_MASSIVE_DELETE_ACTION = "elimina";
		
		public static final String PAGE_KEY_MODEL_KEY = "pageKey";
		
		public static final String PAGE_INFO_TITLE_MODEL_KEY = "pageTitle";

		public static final String PAGE_INFO_SUBTITLE_MODEL_KEY = "pageSubTitle";
		
		public static final String APPLIED_FILTERS_KEY = "appliedFilters";

		public static final String DETAILS_STATUSES_KEY = "detailsStatuses";
		
	}

	public static class Reports {

		public static final String NOT_COMPILED_REPORTS_EXTENSION = ".jrxml";

		public static final String REPORT_PREVIEW_EXTENSION = ".rpw";
		
		public static final String REPORTS_BASE_PATH = "/support/jasper/";
		public static final String REPORTS_BASE_PATH_1 = "support";
		public static final String REPORTS_BASE_PATH_2 = "jasper";

		public static final String REPORTS_DEFAULT_NODE_PATH = "default";

		public static final String REPORTS_AUDITS_SUB_PATH = "audit";
		
		public static final String REPORT_FILE_PREVIEW_SESSION_KEY = "REPORT_FILE_PREVIEW";
		
	}
	
	public static class FEService {
		
	    public static final String SUBMIT_PROCESS_ID = "SUBMIT_PROCESS";
	    
	    public static final String SUBMIT_PROCESS_ADDRESS_UNDEFINED = "UNDEFINED";    
		
	}

	public static class PagedListHolders {

		public static final String USER_PREFERENCES_SESSION_KEY = "pagedListHoldersPref";
		
	}
	
	public static class PagedListHoldersIds {

		public static final String MAIL_SETTINGS = "mailSettings";
		public static final String SECURITY_SETTINGS = "securitySettings";
		public static final String MONITORING_SETTINGS = "monitoringSettings";
		public static final String SCHEDULER_SETTINGS = "schedulerSettings";
		public static final String ACCOUNTS_LIST = "accountsList";
		public static final String ACCOUNTS_WITHOUT_CERTIFICATES_LIST = "accountsWithoutCertificatesList";
		public static final String CERTIFICATE_ACCOUNTS_LIST = "certificateAccountsList";
		public static final String BE_SERVICES_LIST = "beServicesList";
		public static final String NODES_LIST = "nodesList";
		public static final String BE_REFERENCES_LIST = "beReferencesList";
		public static final String PARAMETERS_LIST = "parametersList";
		public static final String FE_SERVICES_LIST = "feServicesList";
		public static final String FRAMEWORK_LABELS_LIST = "frameworkLabelsList";
		public static final String SERVICE_LABELS_LIST = "serviceLabelsList";
		public static final String NODE_LABELS_LIST = "nodeLabelsList";
		public static final String TABLEVAUES_LIST = "tableValueList";
		public static final String AUDIT_PROCESSORS_LIST = "auditProcessorsList";
		public static final String MONITORING_INDICATORS_LIST = "monitoringIndicatorsList";
		public static final String PROCESSES_DELETION_LIST = "processesList";
		public static final String VELOCITY_TEMPLATES_LIST = "velocityTemplatesList";
		
		public static final String USER_NOTIFICATION_LIST = "usernotificationList";
		
	}

	public static class Bundles {
		
		public static final String FRAMEWORK_NODES_BUNDLE = "it.people.resources.FormLabels";
		
	}
	
	public static class Xml {

		public static final String ISO_8859_15_XML_PROLOG = "<?xml version=\"1.0\" encoding=\"iso-8859-15\"?>";
		
	}
	
}

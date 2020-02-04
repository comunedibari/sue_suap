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
package it.people.util;

import java.util.Properties;

import it.people.propertymgr.ApplicationProperties;
import it.people.propertymgr.ApplicationProperty;
import it.people.propertymgr.DBNodeProperties;
import it.people.propertymgr.PropertyParseException;
import it.people.propertymgr.parser.BooleanParser;
import it.people.propertymgr.parser.UrlParser;

public class PeopleProperties extends ApplicationProperties {
    private static PeopleProperties s_obj_instance = new PeopleProperties(
	    PeopleProperties.DEFAULT_SUFFIX);

    public synchronized static PeopleProperties getInstance() {
	return s_obj_instance;
    }

    private PeopleProperties(int i) {
	super(i);
    }

    /*
     * Inserire di seguito le propriet� da leggere dal file di properties o le
     * costanti del sistema.
     */

    /**
     * @deprecated
     */
    public static final ApplicationProperty PEOPLE_PROPERTIES_FILE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.properties.file", "people.properties", true,
		    new UrlParser());

    /**
     * @deprecated
     */
    public static final ApplicationProperty INTERTNAL_USER_REPOSITORY = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.internalUserRepository", true, new BooleanParser());

    public static final ApplicationProperty PPL_USER_MANAGER = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "people.pplUserManager",
		    true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty OJB_XML_REPOSITORY = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "ojb.xml.repository",
		    false, new UrlParser());

    public static final ApplicationProperty OJB_DB_ALIAS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "ojb.db.jcdalias", true);

    public static final ApplicationProperty UPLOAD_DIRECTORY = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "upload.directory", true);

    public static final ApplicationProperty WORKFLOW_DIRECTORY = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "workflow.directory",
		    true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty WEB_SERVICE_CONFIG_FILE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "people.webservice",
		    true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty WEB_SERVICE_TIMEOUT = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.webservice.timeout", true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty LOCAL_SERVICE_CONFIG_FILE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.localservice.filename", true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty WEB_SERVICE_AUTHENTICATION_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.webservice.authentication.address", true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty SIRAC_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "people.sirac.address",
		    true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty SIRAC_WEBSERVICE_ACCREDITAMENTO_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.sirac.webservice.accreditamento.address", true);

    public static final ApplicationProperty SIRAC_AUTHENTICATED_USER_DATA_ATTRIBUTE_NAME = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.sirac.authenticated.user.data.attribute.name", true);

    public static final ApplicationProperty SIRAC_ACCR_TIPO_QUALIFICA_CORRENTE_ATTRIBUTE_NAME = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.sirac.accr.tipo.qualificacorrente.attribute.name",
		    true);

    public static final ApplicationProperty PARAMETER_LOADER_CLASS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "parameterloader.class",
		    true);

    public static final ApplicationProperty TABLE_LOADER_CLASS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "tableloader.class",
		    true);

    public static final ApplicationProperty MESSAGE_BUNDLES_LOADER_CLASS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "messagebundlesloader.class", true);

    public static final ApplicationProperty WKHTMLTOPDF_SYSTEM_PATH = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "wkhtmltopdf.system.path", true);

    /**
     * @deprecated formato.data: per ricavare la data a partire da PplUserData
     *             utilizzare il costruttore Data(String dataString) della
     *             classe it.people.fsl.servizi.oggetticondivisi.tipibase.Data
     *             passandole la data ricavata da PplUserData.getDataNascita().
     *             Dall�istanza di
     *             it.people.fsl.servizi.oggetticondivisi.tipibase.Data �
     *             possibile ottenere java.util.Date con il metodo getDate().
     */
    public static final ApplicationProperty FORMATO_DATA = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "formato.data", true);

    public static final ApplicationProperty PEOPLESERVICE_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "peopleservice.address",
		    true);

    /**
     * @deprecated Il codice comune pu� essere ricavato da
     *             PeopleContext.getCommune().getKey() o da
     *             AbstractPplProcess.getCommune().getKey()
     */
    public static final ApplicationProperty CODICE_COMUNE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "codicecomune", true);

    /**
     * @deprecated
     */
    public static final ApplicationProperty MAILSERVER_OUTADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "mailserver.outaddress",
		    true);

    public static final ApplicationProperty FORMATO_DATA_AUTENTICAZIONE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "formato.dataAutenticazione", true);

    public static final ApplicationProperty SERVICES_LOG_DIR = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "services.log.dir", true);

    public static final ApplicationProperty SMTP_MAIL_SERVICEURL = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.serviceurl",
		    true);

    public static final ApplicationProperty SMTP_MAIL_USEAUTH = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.useauth",
		    true);

    public static final ApplicationProperty SMTP_MAIL_USERNAME = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.username",
		    true);

    public static final ApplicationProperty SMTP_MAIL_PASSWORD = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.password",
		    true);

    public static final ApplicationProperty SMTP_MAIL_SENDER = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.sender", true);

    public static final ApplicationProperty SMTP_MAIL_SENDER_BACKEND = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "smtp.mail.sender.backend", true);

    // ==========================================================================
    // Properties aggiunte per gestione connessione server smtp via SSL
    // Modifica effettuata da M. Pianciamore in data 27/04/2006

    public static final ApplicationProperty SMTP_MAIL_SERVICEPORT = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.serviceport",
		    true);

    public static final ApplicationProperty SMTP_MAIL_USESSL = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.usessl", true);

    public static final ApplicationProperty SMTP_MAIL_USETLS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "smtp.mail.usetls", true);

    // ==========================================================================

    public static final ApplicationProperty CODICE_PROGETTO = ApplicationProperty
	    .createApplicationProperty(s_obj_instance, "codice.progetto", true);

    public static final ApplicationProperty PENDING_PAYMENT_PAGE_REFRESH_TIME = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "pending.payment.page.refresh.time", true);

    public static final ApplicationProperty HOMEPAGE_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "comune.homepage.address", true);

    public static final ApplicationProperty SERVIZIPEOPLE_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "comune.servizipeople.address", true);

    /**
     * Dimensione massima complessiva espressa in KB per gli allegati inseriti
     * in una pratica.
     */
    public static final ApplicationProperty ATTACHMENT_MAX_TOTAL_SIZE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "attachment.max.total.size", true);

    public static final ApplicationProperty TABLE_LOADER_DEFAULT_CHARSET = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "tableloader.default.charset", true);

    public static final ApplicationProperty CODICI_ISTAT_SERVICE_ADDRESS = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "codiciistat.service.address", true);

    public static final ApplicationProperty MESSAGE_BUNDLES_EXPIRATION_UNIT = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "message.bundles.expiration.unit", true);

    public static final ApplicationProperty MESSAGE_BUNDLES_EXPIRATION_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "message.bundles.expiration.value", true);

    public static final ApplicationProperty PEOPLE_SENDERROR_NOTIFYPPLADMIN_EMAILADDRESS_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.sendError.notifyPplAdmin.emailAddress", false);

    public static final ApplicationProperty PEOPLE_GENERALERROR_NOTIFYPPLADMIN_EMAILADDRESS_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.generalError.notifyPplAdmin.emailAddress", false);

    public static final ApplicationProperty PEOPLE_BUGNOTIFIEDBYUSER_NOTIFYPPLADMIN_EMAILADDRESS_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.bugNotifiedByUser.notifyPplAdmin.emailAddress",
		    false);

    public static final ApplicationProperty PEOPLE_SUGGESTIONBYUSER_NOTIFYPPLADMIN_EMAILADDRESS_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.suggestionByUser.notifyPplAdmin.emailAddress",
		    false);

    public static final ApplicationProperty PEOPLE_NEWACCREDITATION_NOTIFYPPLADMIN_EMAILADDRESS_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.newAccreditation.notifyPplAdmin.emailAddress",
		    false);

    public static final ApplicationProperty PEOPLE_TMP_XML_DUMP_FOLDER_VALUE = ApplicationProperty
	    .createApplicationProperty(s_obj_instance,
		    "people.tmp.xml.dump.folder", true);

    static {
	try {
	    DBNodeProperties nodeProps = new DBNodeProperties();
	    getInstance().load(nodeProps.load());
	} catch (Exception ex) {
	}
    }

    /**
     * <p>
     * Only used for testing purpose by reflection
     * 
     * @param nodeProps
     */
    private void getInstance(Properties properties) {
	try {
	    getInstance().load(properties);
	} catch (PropertyParseException ignore) {
	}
    }

}

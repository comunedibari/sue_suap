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
/*
 * Creato il 10-lug-2006 da Cedaf s.r.l.
 *
 */
package it.people.filters.AdminAuthentication;

import it.progettopeople.people.peopleadministrators.AdministratorDocument;
import it.progettopeople.people.peopleadministrators.ParamDocument;
import it.progettopeople.people.peopleadministrators.PeopleadministratorsDocument;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public class XmlConfigReader implements AdminConfigReader {
    Logger logger = Logger.getLogger(XmlConfigReader.class);

    /**
     * Legge la configurazione degli amministratori dal file xml
     * 
     * @param initParameter
     *            HashMap dei parametri di configurazione, accetta come
     *            parametri: key = 'xmlConfigReaderFileName' � il nome del
     *            file xml contenente la configurazione
     */
    public AdminConfig get(Hashtable initParameter) throws AdminConfigException {

	// Lettura dei parametri di configurazione
	String configFileName = (String) initParameter
		.get("xmlConfigReaderFileName");
	if (configFileName == null) {
	    logger.fatal("Il parametro di configurazione xmlConfigReaderFileName, non � stato impostato correttamente");
	    throw new AdminConfigException(
		    "Il parametro di configurazione xmlConfigReaderFileName, non � stato impostato correttamente");
	}

	// Lettura del file xml di configurazione
	File xmlFile = new File(configFileName);

	if (!xmlFile.exists()) {
	    logger.fatal("Impossibile trovare il file '" + configFileName + "'");
	    throw new AdminConfigException("Impossibile trovare il file '"
		    + configFileName + "'");
	}

	try {
	    PeopleadministratorsDocument peopleAdminDoc = PeopleadministratorsDocument.Factory
		    .parse(xmlFile);

	    return copyConfig(peopleAdminDoc);

	} catch (IOException ioEx) {
	    logger.fatal("Errore nell'utilizzo del file '" + configFileName
		    + "'", ioEx);
	    throw new AdminConfigException("Errore nell'utilizzo del file '"
		    + configFileName + "'", ioEx);
	} catch (XmlException xmlEx) {
	    logger.fatal(
		    "Errore nel parsing del file '" + configFileName + "'",
		    xmlEx);
	    throw new AdminConfigException("Errore nel parsing del file '"
		    + configFileName + "'", xmlEx);
	} catch (Exception ex) {
	    logger.fatal("Errore generico.", ex);
	    throw new AdminConfigException("Errore generico.", ex);
	}
    }

    protected AdminConfig copyConfig(PeopleadministratorsDocument peopleAdminDoc) {
	AdminConfig adminConfig = new AdminConfig();

	// Copia la configurazione dall'xml
	AdministratorDocument.Administrator admins[] = peopleAdminDoc
		.getPeopleadministrators().getAdministratorArray();

	for (int i = 0; i < admins.length; i++) {
	    AdministratorDocument.Administrator admin = admins[i];
	    AdminConfigElement adminConfigElement = new AdminConfigElement();

	    // imposta lo user name
	    adminConfigElement.setUserName(admin.getUsername());

	    // imposta i codici dei comuni
	    String communeId[] = admin.getCommuneIdArray();
	    for (int j = 0; j < communeId.length; j++) {
		adminConfigElement.addCommuneCode(communeId[j]);
	    }

	    // imposta i paramtri per il people user
	    ParamDocument.Param param[] = admin.getParamArray();
	    for (int j = 0; j < param.length; j++) {
		ParamDocument.Param paramElem = param[j];
		adminConfigElement.putUserParameter(paramElem.getParamName(),
			paramElem.getParamValue());
	    }
	    adminConfig.add(adminConfigElement);
	}

	return adminConfig;
    }

}

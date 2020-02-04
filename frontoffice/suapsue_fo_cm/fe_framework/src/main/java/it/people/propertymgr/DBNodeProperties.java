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
 * DBNodeProperties.java
 *
 * Created on October 4, 2004, 6:54 PM
 */

package it.people.propertymgr;

import it.people.db.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Dirotta il caricamento e la memorizzazione delle Property del nodo su mysql.
 * Una volta caricate le properties andranno fornite ad un ApplicationProperties
 * per l'utilizzo nel framework.
 * 
 * @author manelli
 */
public class DBNodeProperties {

    private static Logger logger = Logger.getLogger(DBNodeProperties.class);

    /**
     * <p>
     * Only used for testing purpose by reflection
     * 
     * @param dbConnector
     * 
     */
    @SuppressWarnings("unused")
    private Properties load(DBConnector dbConnector) {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;
	Properties props = null;

	String query;

	try {
	    con = dbConnector.connect(DBConnector.FEDB);

	    query = "SELECT * FROM nodeconfiguration";

	    stat = con.createStatement();
	    rs = stat.executeQuery(query);
	    props = new Properties();

	    while (rs.next()) {
		String key = rs.getString("name");
		String value = rs.getString("value");

		// Verifica la presenza del codice comune
		String communeId = rs.getString("communeid");
		if (isValidCommuneId(communeId)) {
		    // Se il codice comune � presente lo aggiunge alla
		    // chiave della properties
		    key += "." + extractValidCommuneKey(communeId, key);
		}

		props.put(key, value);
	    }

	    return props;
	} catch (Exception e) {
	    logger.error(e);
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (SQLException e) {
		}
	    }

	    if (stat != null) {
		try {
		    stat.close();
		} catch (SQLException e) {
		}
	    }

	    if (con != null) {
		try {
		    con.close();
		} catch (SQLException e) {
		}
	    }
	}
	return null;
    }

    /**
     * Carica le properties per il servizio dal DB
     * 
     * @param idx
     *            l'indice del servizio (se -1 ci si riferisce al nodo people)
     * @return l'elenco delle property lette
     */
    public Properties load() {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;
	Properties props = null;

	String query;

	try {
	    con = DBConnector.getInstance().connect(DBConnector.FEDB);

	    query = "SELECT * FROM nodeconfiguration";

	    stat = con.createStatement();
	    rs = stat.executeQuery(query);
	    props = new Properties();

	    while (rs.next()) {
		String key = rs.getString("name");
		String value = rs.getString("value");

		// Verifica la presenza del codice comune
		String communeId = rs.getString("communeid");
		if (isValidCommuneId(communeId)) {
		    // Se il codice comune � presente lo aggiunge alla
		    // chiave della properties
		    key += "." + extractValidCommuneKey(communeId, key);
		}

		props.put(key, value);
	    }

	    return props;
	} catch (Exception e) {
	    logger.error(e);
	} finally {
	    if (rs != null) {
		try {
		    rs.close();
		} catch (SQLException e) {
		}
	    }

	    if (stat != null) {
		try {
		    stat.close();
		} catch (SQLException e) {
		}
	    }

	    if (con != null) {
		try {
		    con.close();
		} catch (SQLException e) {
		}
	    }
	}
	return null;
    }

    protected boolean isValidCommuneId(String communeId) {
	return communeId != null && !communeId.trim().equals("");
    }

    protected String extractValidCommuneKey(String communeId, String key) {

	String warningMessage = "Il codice comune inserito nella tabella "
		+ " fedb.nodeconfiguration per la properties con name = '"
		+ key + "'"
		+ " ha un formato che potrebbe essere fonte di problemi: ";

	String trimmedCommuneId = communeId.trim();
	if (!communeId.equals(trimmedCommuneId))
	    logger.warn(warningMessage
		    + "presenza di spazi prima e/o dopo il codice");

	try {
	    Integer.parseInt(trimmedCommuneId);
	} catch (NumberFormatException nfEx) {
	    logger.warn(warningMessage
		    + "codice non numerico, generalmente si utilizza il codice istat che � numerico");
	}

	if (trimmedCommuneId.length() != 6)
	    logger.warn(warningMessage
		    + "codice con lunghezza diversa da 6, generalmente si utilizza il codice istat la cui lunghezza � di sei cifre");

	return trimmedCommuneId;
    }
}

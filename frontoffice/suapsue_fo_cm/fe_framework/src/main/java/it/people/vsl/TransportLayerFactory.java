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
package it.people.vsl;

/**
 * <p>Title: TransportLayerFactory</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Espin S.p.A.</p>
 * @author Mazzotta Vincenzo
 * @version 1.0
 */

import it.people.City;
import it.people.db.DBConnector;
import it.people.vsl.exception.UnsupportedTransportLayerException;
import it.people.vsl.transport.SendMail;
import it.people.vsl.transport.TransportLayer;
import it.people.vsl.transport.WebServiceCall;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.log4j.Category;

public class TransportLayerFactory {
    private Category cat = Category.getInstance(TransportLayerFactory.class
	    .getName());
    private static TransportLayerFactory _instance;

    // This hashmap holds a hashmap for every city, the other hashmap is
    // usefull to find transport layer depending for process
    private HashMap _transportLayers;

    public synchronized static TransportLayerFactory getInstance() {
	if (_instance == null) {
	    _instance = new TransportLayerFactory();
	}

	return _instance;
    }

    public TransportLayerFactory() {
	_transportLayers = new HashMap();
    }

    public TransportLayer getTransportLayer(City comune, String processName)
	    throws UnsupportedTransportLayerException {

	HashMap transportByProcess = (HashMap) _transportLayers.get(comune
		.getOid());
	TransportLayer newTransportObject = null;

	if (transportByProcess == null) {
	    // Don't exist transportByProcess so i add
	    // a city HashMap of transport HashMap
	    try {
		newTransportObject = recoverTransportLayer(comune, processName);
	    } catch (Exception e) {
		cat.error(e);
	    }

	    if (newTransportObject == null)
		throw new UnsupportedTransportLayerException(
			comune.getOid(),
			processName,
			"Can't find Transport implementation, see your DB or classes for this transport layer.");
	    else {
		// Insert Trasport Object into city HashMap of new HashMap
		HashMap newTransportByProcess = new HashMap();
		newTransportByProcess.put(processName, newTransportObject);
		_transportLayers.put(comune.getOid(), newTransportByProcess);
	    }
	} else {
	    // transportByProcess is a HashMap
	    if (transportByProcess.get(processName) == null) {
		try {
		    newTransportObject = recoverTransportLayer(comune,
			    processName);
		    transportByProcess.put(processName, newTransportObject);
		    _transportLayers.put(comune.getOid(), transportByProcess);
		} catch (Exception e) {
		    throw new UnsupportedTransportLayerException(
			    comune.getOid(),
			    processName,
			    "Can't find Transport implementation, see your DB or classes for this transport layer.");
		}
	    } else
		newTransportObject = (TransportLayer) transportByProcess
			.get(processName);
	}
	if (newTransportObject != null)
	    newTransportObject.setComune(comune);
	return newTransportObject;
    }

    /**
     * Return rigth transportLayer className or default transportLayer for a
     * specific city and process
     * 
     * @param comune
     *            il comune di riferimento
     * @param processName
     *            il package del servizio
     */
    private TransportLayer recoverTransportLayer(City comune, String processName)
	    throws Exception {
	TransportLayer transportLayer = getConfiguration(comune, processName);
	return transportLayer;
    }

    private TransportLayer getConfiguration(City comune, String processName) {

	TransportLayer transportLayer;
	Connection conn = null;
	Statement stat = null;
	ResultSet rs = null;

	try {
	    conn = DBConnector.getInstance().connect(DBConnector.FEDB);

	    String query = "SELECT r.address raddress "
		    + "FROM reference r, service s "
		    + "WHERE s.id = r.serviceid " + "AND s.package = '"
		    + processName + "' " + "AND s.communeid = '"
		    + comune.getOid() + "' " + "AND r.name = 'SUBMIT_PROCESS'";

	    stat = conn.createStatement();
	    rs = stat.executeQuery(query);

	    if (rs.next()) {
		String address = rs.getString("raddress");
		if (!(address.equals("UNDEFINED"))) {
		    transportLayer = new SendMail(comune, processName, address);
		} else {
		    transportLayer = new WebServiceCall("SUBMIT_PROCESS");
		    transportLayer.setRealServiceName(processName);
		}
	    } else {
		return null;
	    }

	    return transportLayer;

	} catch (Exception e) {
	    cat.error(
		    "getConfiguration() - errore nella determinazione della configurazione",
		    e);
	} finally {
	    try {
		if (rs != null) {
		    rs.close();
		}
	    } catch (SQLException e) {
	    }
	    try {
		if (stat != null) {
		    stat.close();
		}
	    } catch (SQLException e) {
	    }
	    try {
		if (conn != null) {
		    conn.close();
		}
	    } catch (SQLException e) {
	    }
	}

	return null;
    }
}

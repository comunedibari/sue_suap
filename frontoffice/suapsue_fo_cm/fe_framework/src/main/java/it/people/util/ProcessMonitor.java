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
 * ProcessMonitor.java
 *
 * Created on October 5, 2004, 3:06 PM
 */

package it.people.util;

import it.people.db.DBConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Category;

/**
 * Monitorizza il DB del nodo per verificare se un determinato servizio ? attivo
 * o meno.
 * 
 * @author manelli
 */
public class ProcessMonitor {
    private Category cat = Category.getInstance(this.getClass().getName());

    /** Creates a new instance of ProcessMonitor */
    public ProcessMonitor() {
    }

    /**
     * testa se il servizio il cui package di riferimento ? packageName sia
     * attivo o meno
     * 
     * @param comuneId
     *            l'id del comune a cui corrisponde il servizio
     * @param packageName
     *            il nome del package che identifica il servizio
     * @return se il servizio ? in modalit? started o meno.
     */
    public boolean isStarted(String communeId, String packageName) {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	String query;

	try {
	    con = DBConnector.getInstance().connect(DBConnector.FEDB);

	    query = "SELECT statusid " + "FROM service " + "WHERE package = '"
		    + packageName + "'" + "AND communeid = '" + communeId + "'";

	    stat = con.createStatement();
	    rs = stat.executeQuery(query);

	    boolean started = false;
	    if (rs.next())
		started = rs.getInt("statusid") == 1;

	    if (!started)
		cat.info("Tentativo di avvio del servizio in stato di stop: "
			+ packageName);

	    return started;
	} catch (Exception e) {
	    cat.error(e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
	    } catch (SQLException e) {
	    }
	    try {
		if (stat != null)
		    stat.close();
	    } catch (SQLException e) {
	    }
	    try {
		if (con != null)
		    con.close();
	    } catch (SQLException e) {
	    }
	}
	return false;
    }
}

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
 * DBProperties.java
 *
 * Created on October 4, 2004, 4:04 PM
 */

package it.people.propertymgr;

import it.people.db.DBConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.log4j.Category;

/**
 * Permette ad un servizio people di caricare i parametri di configurazione da
 * DataBase.
 * 
 * @author manelli
 */
public class DBProperties {

    private Category cat = Category.getInstance(this.getClass().getName());
    protected Properties props;

    /**
     * Carica le properties per il servizio dal DB
     * 
     * @param idx
     *            l'indice del servizio (se -1 ci si riferisce al nodo people)
     * @return l'elenco delle property lette
     */
    public void load(int idx) {
	Connection con = null;
	Statement stat = null;
	ResultSet rs = null;

	String query;

	try {
	    con = DBConnector.getInstance().connect(DBConnector.FEDB);

	    query = "SELECT * FROM configuration WHERE serviceid = " + idx;

	    stat = con.createStatement();
	    rs = stat.executeQuery(query);
	    Properties props = new Properties();

	    while (rs.next()) {
		String key = rs.getString("name");
		String value = rs.getString("value");
		props.put(key, value);
	    }
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
    }

    /**
     * Ottiene le property caricate dal DB
     * 
     * @return le property del servizio caricati dal DB
     */
    public Properties getProperties() {
	return props;
    }
}

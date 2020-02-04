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
 * ServiceParameters.java
 *
 * Created on 22 novembre 2004, 12.18
 */
package it.people.util;

import it.people.annotations.DeveloperTaskStart;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.sql.*;
import org.apache.log4j.Category;

/**
 * 
 * @author mparigiani
 */
public class ServiceParameters implements Serializable {
    private int serviceId;
    private Hashtable configTable;
    private Category cat = Category.getInstance(this.getClass().getName());

    /** Creates a new instance of ServiceParameters */
    public ServiceParameters(String serviceName, String communeId) {

	this.configTable = new Hashtable();

	Statement statement = null;
	ResultSet res = null;
	Connection connection = null;

	try {
	    DBConnector dbC = DBConnector.getInstance();
	    connection = dbC.connect(DBConnector.FEDB);
	    statement = connection.createStatement();
	    String sql = "SELECT c.value cvalue, c.name cname "
		    + "FROM configuration c, service s "
		    + "WHERE c.serviceid = s.id " + "AND s.package = '"
		    + serviceName + "' " + "AND s.communeid = '" + communeId
		    + "'";

	    res = statement.executeQuery(sql);
	    String val = "";
	    String name = "";
	    while (res.next()) {
		val = res.getString("cvalue");
		name = res.getString("cname");
		configTable.put((String) name, (String) val);
	    }
	} catch (SQLException e) {
	    cat.error(e);
	} catch (PeopleDBException e) {
	    cat.error(e);
	} finally {
	    try {
		if (res != null)
		    res.close();
	    } catch (SQLException e) {
	    }
	    try {
		if (statement != null)
		    statement.close();
	    } catch (SQLException e) {
	    }
	    try {
		connection.close();
	    } catch (SQLException e) {
	    }
	}
    }

    public String get(String key) {
	String val = (String) configTable.get((String) key);
	return val;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getParametersHasMap() {

	return (Map<String, String>) this.configTable;

    }

    @DeveloperTaskStart(name = "Riccardo Foraf�", date = "18.08.2011", bugDescription = "Miglioramento", description = "Aggiunta la possibilit� di ottenere l'enumerazione dei parametri.")
    public Enumeration parameters() {

	return configTable.keys();

    }

}

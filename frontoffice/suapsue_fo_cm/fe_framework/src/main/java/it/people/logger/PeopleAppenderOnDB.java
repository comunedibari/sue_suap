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
 * PeopleAppenderOnDB.java
 *
 * Created on 8 novembre 2004, 18.21
 */

package it.people.logger;

import java.sql.*;
import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import org.apache.log4j.Category;

/**
 * 
 * @author mparigiani
 */
public class PeopleAppenderOnDB implements Appender {
    private Category cat = Category.getInstance(PeopleAppenderOnDB.class
	    .getName());
    private Logger logger;

    public PeopleAppenderOnDB(Logger logger) {
	this.logger = logger;
    }

    public void append(LoggerBean lb, int level) {
	Connection connection = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);

	    // Registra il messaggio di log
	    String insert = "INSERT INTO log_messages (idloglevel, communeid, servicepackage, data, messaggio) VALUES (?,?,?,?,?)";
	    PreparedStatement ps = connection.prepareStatement(insert);
	    ps.setInt(1, level);
	    ps.setString(2, lb.getIdcomune());
	    ps.setString(3, lb.getServicePackage());
	    ps.setDate(4, lb.getDate());
	    ps.setString(5, lb.getMessaggio());
	    ps.execute();

	} catch (SQLException e) {
	    cat.error(e);
	} catch (PeopleDBException e) {
	    cat.error(e);
	} finally {
	    if (rs != null)
		try {
		    rs.close();
		} catch (Exception ex) {
		}

	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}

	    if (preparedStatement != null)
		try {
		    preparedStatement.close();
		} catch (Exception ex) {
		}

	    if (connection != null)
		try {
		    connection.close();
		} catch (Exception ex) {
		}
	}
    }
}

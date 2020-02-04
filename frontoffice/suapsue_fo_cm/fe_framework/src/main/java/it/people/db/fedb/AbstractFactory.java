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
 * Creato il 27-dic-2006 da Cedaf s.r.l.
 *
 */
package it.people.db.fedb;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 */
public abstract class AbstractFactory {

    /**
     * 
     */
    public AbstractFactory() {
    }

    protected Collection getCollection(String query) throws SQLException,
	    PeopleDBException {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ArrayList list = new ArrayList();

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    statement = connection.createStatement();

	    resultSet = statement.executeQuery(query);

	    while (resultSet.next()) {
		list.add(getFromResultSet(resultSet, list));
	    }
	} finally {
	    if (resultSet != null)
		try {
		    resultSet.close();
		} catch (Exception ex) {
		}
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    if (connection != null)
		try {
		    connection.close();
		} catch (Exception ex) {
		}
	}

	return list;
    }

    public Collection getDynamicCollection(String query,
	    IDynamicCollection dynamicCollectionBuilder) throws SQLException,
	    PeopleDBException {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ArrayList list = new ArrayList();

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    statement = connection.createStatement();

	    resultSet = statement.executeQuery(query);
	    list = dynamicCollectionBuilder.getDynamicCollection(resultSet);

	} finally {
	    if (resultSet != null)
		try {
		    resultSet.close();
		} catch (Exception ex) {
		}
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    if (connection != null)
		try {
		    connection.close();
		} catch (Exception ex) {
		}
	}

	return list;
    }

    protected ResultSet getCollectionResultSet(String query)
	    throws SQLException, PeopleDBException {
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ArrayList list = new ArrayList();

	try {
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    statement = connection.createStatement();

	    resultSet = statement.executeQuery(query);

	} finally {
	    if (resultSet != null)
		try {
		    resultSet.close();
		} catch (Exception ex) {
		}
	    if (statement != null)
		try {
		    statement.close();
		} catch (Exception ex) {
		}
	    if (connection != null)
		try {
		    connection.close();
		} catch (Exception ex) {
		}
	}

	return resultSet;
    }

    protected abstract Object getFromResultSet(ResultSet resultSet,
	    ArrayList list) throws SQLException;
}

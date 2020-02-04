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
package it.people.db.steps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.people.db.DBConnector;
import it.people.exceptions.PeopleDBException;
import it.people.taglib.support.TaglibDbSupportQueriesFactory;

/**
 * @author Riccardo Forafo' - Engineering Ingegneria Informatica
 * 
 */
public class StepsFactory {

    private static Logger logger = LogManager.getLogger(StepsFactory.class);

    private static final String HAS_ON_LINE_HELP_ON_DB = "hasOnLineHelpOnDb";
    private static final String HAS_COMMON_ON_LINE_HELP_ON_DB = "hasCommonOnLineHelpOnDb";

    public static boolean hasOnLineHelpOnDb(String communeId, String _package,
	    String viewName, String activityId, String stepId) {

	boolean result = false;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	try {
	    if (logger.isDebugEnabled()) {
		logger.debug("Obtaining connection to FE database...");
	    }
	    connection = DBConnector.getInstance().connect(DBConnector.FEDB);
	    if (logger.isDebugEnabled()) {
		logger.debug("Connection to FE database obtained.");
	    }

	    String query = TaglibDbSupportQueriesFactory
		    .getQuery(HAS_ON_LINE_HELP_ON_DB);
	    preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setString(1, communeId);
	    preparedStatement.setString(2, _package);
	    preparedStatement.setString(3, viewName);
	    preparedStatement.setString(4, activityId);
	    preparedStatement.setString(5, stepId);
	    resultSet = preparedStatement.executeQuery();
	    if (resultSet.next()) {
		int count = resultSet.getInt(1);
		result = count > 0;
	    }
	    if (!result) {
		query = TaglibDbSupportQueriesFactory
			.getQuery(HAS_COMMON_ON_LINE_HELP_ON_DB);
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, _package);
		preparedStatement.setString(2, viewName);
		preparedStatement.setString(3, activityId);
		preparedStatement.setString(4, stepId);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
		    int count = resultSet.getInt(1);
		    result = count > 0;
		}
	    }

	} catch (PeopleDBException e) {
	    logger.error(e);
	} catch (SQLException e) {
	    logger.error(e);
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (preparedStatement != null) {
		    preparedStatement.close();
		}
		if (connection != null) {
		    connection.close();
		}
	    } catch (SQLException e) {
		logger.error(e);
	    }
	}

	return result;

    }

}

package it.people.console.web.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.people.console.system.AbstractLogger;

/**
 * 
 * Funzioni di utilità per il reperimento di dati comunemente utilizzati
 * 
 * @author gguidi - Jun 20, 2013
 *
 */
@Service
public class DataUtils extends AbstractLogger {
	
	@Autowired
	private DataSource dataSourcePeopleDB;
	
	/**
	 * @param id
	 * @return reference e codicecomune, parametri per la connessione
	 */
	public String[] getReferenceCommuneFromId(String id) {

		return getReferenceCommuneidFromColumn("id", id);
	}
	
	/**
	 * @param id
	 * @return reference e codicecomune, parametri per la connessione
	 */
	public String[] getReferenceCommuneFromCodiceComune(String comuneId) {

		return getReferenceCommuneidFromColumn("codicecomune", comuneId);
		
	}
	
	public String[] getReferenceCommuneidFromColumn(String column, String value) {

		String[] references = new String[2];

		String query = "SELECT codicecomune, reference FROM fenode WHERE "+column+" = "
				+ value;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSourcePeopleDB.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				references[0] = resultSet.getString("reference");
				references[1] = resultSet.getString("codicecomune");
			}
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return references;
	}

}

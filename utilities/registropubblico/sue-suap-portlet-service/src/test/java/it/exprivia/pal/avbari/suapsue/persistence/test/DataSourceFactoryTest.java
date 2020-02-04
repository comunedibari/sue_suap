package it.exprivia.pal.avbari.suapsue.persistence.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import it.exprivia.pal.avbari.suapsue.service.persistence.DataSourceFactory;

import org.junit.Test;

public class DataSourceFactoryTest {

	@Test
	public void testConnection() {
		try {
			Connection conn = DataSourceFactory.getDataSource().getConnection();
			assertTrue(conn != null);
			
			conn.close();
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
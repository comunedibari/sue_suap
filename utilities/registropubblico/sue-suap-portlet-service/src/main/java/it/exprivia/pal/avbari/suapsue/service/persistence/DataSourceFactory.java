package it.exprivia.pal.avbari.suapsue.service.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import it.exprivia.pal.util.PropertiesUtil;

public final class DataSourceFactory {
	
	private static MysqlDataSource ds;
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
	
	
	public final static DataSource getDataSource() throws SQLException {
		if (ds == null)
			init();
		
		return ds;
	}
	
	private static void init() throws SQLException {
		if (logger.isDebugEnabled())
			logger.debug("inizializzazione datasource");
				
		try {
			final Properties prop = PropertiesUtil.getProperties();
			
			ds = new MysqlDataSource();
			ds.setURL(prop.getProperty("jdbcUrl"));
			ds.setUser(prop.getProperty("username"));
			ds.setPassword(prop.getProperty("password"));
		} catch (IOException e) {
			throw new SQLException("errore inizializzando la datasource", e);
		}
	}
}
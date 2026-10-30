package it.wego.cross.mailservices.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import it.wego.cross.mailservices.utilities.myUtility;

public class MySQLDbcp2 {

	private static String userName;
	
	private static String password;
	
	private static String MySqlDriver;

	private static String MySqlUrl;
	
	private static DataSource dataSource = null;

	static {
		
		read_config();
		
		Properties properties = new Properties();
		properties.setProperty("user", userName);
		properties.setProperty("password", password);

		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(MySqlUrl, properties);

		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

		GenericObjectPoolConfig<PoolableConnection> config = new GenericObjectPoolConfig<>();
		config.setMaxTotal(25);
		config.setMaxIdle(10);
		config.setMinIdle(5);

		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory, config);
		poolableConnectionFactory.setPool(connectionPool);

		dataSource = new PoolingDataSource<>(connectionPool);

	}

	private static void read_config(){
		Properties configProperties;
		try {
			configProperties = myUtility.readConfigProperties("application.properties");
			userName = configProperties.getProperty("spring.datasource.username");
			password = configProperties.getProperty("spring.datasource.password");
			MySqlUrl = configProperties.getProperty("spring.datasource.url");
			MySqlDriver = configProperties.getProperty("spring.datasource.driver-class-name");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	/**
	 * @return the dataSource
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public static void setDataSource(DataSource dataSource) {
		MySQLDbcp2.dataSource = dataSource;
	}
		
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			/*
			for(int i=0; i<200; i++)
			{
				Connection connection2 = dataSource.getConnection();
				if(connection2 != null) {
					System.out.println("connection2 ok (" + i + ") " + connection2.getClientInfo());
				} else {
					System.out.println("connection2 Ko (" + i + ")");					
				}
				
				try { connection2.close(); } catch(Exception e) {}
			}
			*/
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from anagrafica");
			while (resultSet.next()) {
				System.out.println("empId:" + resultSet.getInt("Id"));
				System.out.println("empName:" + resultSet.getString("nome"));
				System.out.println("dob:" + resultSet.getDate("cognome"));
			}
		} finally {

			resultSet.close();
			statement.close();
			connection.close();
		}
	}

}
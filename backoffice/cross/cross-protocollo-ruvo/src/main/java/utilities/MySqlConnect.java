// Source code is decompiled from a .class file using FernFlower decompiler.
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnect {
	private static final String MAX_POOL = "250";
	private String DATABASE_DRIVER;
	private String DATABASE_URL;
	private String PORT;
	private String USERNAME;
	private String PASSWORD;
	private Connection connection;
	private Properties properties;

	public MySqlConnect() {
		this.getProperties();
	}

	private Properties getProperties() {
		if (this.properties == null) {
			this.properties = new Properties();
			myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");
			this.properties = utl.getProperties();
			this.DATABASE_DRIVER = this.properties.getProperty("jdbc.driverClassName");
			this.DATABASE_URL = this.properties.getProperty("jdbc.url.opencross");
			this.PORT = this.properties.getProperty("jdbc.port");
			this.USERNAME = this.properties.getProperty("jdbc.username");
			this.PASSWORD = this.properties.getProperty("jdbc.password");
		}

		return this.properties;
	}

	public Connection connect() throws SQLException {
		if (this.connection == null) {
			this.connection = DriverManager.getConnection(this.DATABASE_URL, this.USERNAME, this.PASSWORD);
		}

		return this.connection;
	}

	public void disconnect() {
		if (this.connection != null) {
			try {
				this.connection.close();
				this.connection = null;
			} catch (SQLException var2) {
				var2.printStackTrace();
			}
		}

	}

	public Connection getConnection() throws SQLException {
		return this.connect();
	}

	public void close(Connection conn) {
		this.disconnect();
	}
}

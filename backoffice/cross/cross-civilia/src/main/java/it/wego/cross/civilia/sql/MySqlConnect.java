package it.wego.cross.civilia.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import it.wego.cross.civilia.utilities.myUtility;

public class MySqlConnect {

    // init database constants
 
	private static final String MAX_POOL = "250";	
	private String DATABASE_DRIVER;
	private String DATABASE_URL;
	private String PORT;
	private String USERNAME;
	private String PASSWORD;
	
	private String CROSS_DATABASE_DRIVER;
	private String CROSS_DATABASE_URL;
	private String CROSS_PORT;
	private String CROSS_USERNAME;
	private String CROSS_PASSWORD;
    
	private Connection connection;
    private Properties properties;
    private Connection connection_cross;
    private Properties properties_cross;
    
    myUtility utl;

    public MySqlConnect() {
    	utl = new myUtility();
    	getProperties();
    	getProperties_cross();
	}

    // create properties
    private Properties getProperties() {
        if (properties == null) {
//            properties = new Properties();
            properties = utl.getProperties();
            DATABASE_DRIVER = properties.getProperty("jdbc.driverClassName");
            DATABASE_URL = properties.getProperty("jdbc.url");
            PORT = properties.getProperty("jdbc.port");
            USERNAME = properties.getProperty("jdbc.username");
            PASSWORD = properties.getProperty("jdbc.password");
        }
        return properties;
    }

    private Properties getProperties_cross() {
        if (properties_cross == null) {
//            properties_cross = new Properties();
            properties_cross = utl.getProperties();
            CROSS_DATABASE_DRIVER = properties_cross.getProperty("cross.jdbc.driverClassName");
            CROSS_DATABASE_URL = properties_cross.getProperty("cross.jdbc.url");
            CROSS_PORT = properties_cross.getProperty("cross.jdbc.port");
            CROSS_USERNAME = properties_cross.getProperty("cross.jdbc.username");
            CROSS_PASSWORD = properties_cross.getProperty("cross.jdbc.password");
        }
        return properties_cross;
    }

    // connect database
    private Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    // disconnect database
    private void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection connect_cross() {
        if (connection_cross == null) {
            try {
                Class.forName(CROSS_DATABASE_DRIVER);
                connection_cross = DriverManager.getConnection(CROSS_DATABASE_URL, CROSS_USERNAME, CROSS_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection_cross;
    }
    // disconnect database
    private void disconnect_cross() {
        if (connection_cross != null) {
            try {
            	connection_cross.close();
            	connection_cross = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public Connection getConnection() {        
        return connect();
    }
    public void close(Connection conn) {        
        disconnect();
    }
    public Connection getConnection_cross() {        
        return connect_cross();
    }
    public void close_cross(Connection conn) {        
        disconnect_cross();
    }

}
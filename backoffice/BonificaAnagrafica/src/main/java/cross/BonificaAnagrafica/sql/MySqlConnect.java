package cross.BonificaAnagrafica.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnect {

    // init database constants
 
	private static final String MAX_POOL = "250";	
	private String DATABASE_DRIVER;
	private String DATABASE_URL;
	private String PORT;
	private String USERNAME;
	private String PASSWORD;

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    public MySqlConnect() {
    	getProperties();
	}

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            this.DATABASE_DRIVER = properties.getProperty("jdbc.driverClassName");
            this.DATABASE_URL = properties.getProperty("jdbc.url");
            this.PORT = properties.getProperty("jdbc.port");
            this.USERNAME = properties.getProperty("jdbc.username");
            this.PASSWORD = properties.getProperty("jdbc.password");
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(this.DATABASE_DRIVER);
                connection = DriverManager.getConnection(this.DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
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
}
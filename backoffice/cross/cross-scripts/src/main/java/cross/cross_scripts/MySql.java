package cross.cross_scripts;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MySql implements Closeable {

	private static final Logger logger = LogManager.getLogger(MySql.class);
	private static String url;
	private static String user;
	private static String pwd;
	
	static MySql instance;
	private boolean autocommit;
	private Connection conn;
	
	private MySql(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");  
		conn = DriverManager.getConnection(url, user, pwd); 
	}
	
	public static MySql getInstance(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
		MySql.url = url;
		MySql.user = user;
		MySql.pwd = pwd;
		
		logger.warn("javax.persistence.jdbc: " + url);
		logger.warn("javax.persistence.jdbc: " + user);
		logger.warn("javax.persistence.jdbc: " + pwd);
		if(instance == null) {
			instance = new MySql(url, user, pwd);
		}
		return instance;
	}

	public static MySql getAlwaysNewInstance() throws SQLException, ClassNotFoundException {
		return new MySql(MySql.url, MySql.user, MySql.pwd);
	}

	
	public static MySql getInstance() {
		return instance;
	}

	public static void setInstance(MySql instance) {
		MySql.instance = instance;
	}

	public boolean isAutocommit() {
		return autocommit;
	}

	public void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStatement() throws SQLException {
		conn.setAutoCommit(autocommit);
		return conn.createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		conn.setAutoCommit(autocommit);
		return conn.prepareStatement(sql);
	}
	
	public void commit() throws SQLException {
		conn.commit();
	}
	
	public void rollback() throws SQLException {
		conn.rollback();
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
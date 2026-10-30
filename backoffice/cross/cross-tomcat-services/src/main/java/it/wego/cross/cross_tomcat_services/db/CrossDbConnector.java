package it.wego.cross.cross_tomcat_services.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CrossDbConnector {

	static CrossDbConnector instance;
	private boolean autocommit;
	private Connection conn;
	
	private CrossDbConnector(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");  
		conn = DriverManager.getConnection(url, user, pwd); 
	}
	
	public static CrossDbConnector getInstance(String url, String user, String pwd) throws SQLException, ClassNotFoundException {
		if(instance == null) {
			instance = new CrossDbConnector(url, user, pwd);
		}
		return instance;
	}

	
	public static CrossDbConnector getInstance() {
		return instance;
	}

	public static void setInstance(CrossDbConnector instance) {
		CrossDbConnector.instance = instance;
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
	
	public void commit() throws SQLException {
		conn.commit();
	}
	
	public void rollback() throws SQLException {
		conn.rollback();
	}
	
	public void close() throws SQLException {
		conn.close();
	}
}
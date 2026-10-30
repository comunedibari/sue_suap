package cross.cross_scripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.ave_151.exporter.Ave151ExporterRunner;

public class Main {

	private static Integer INDEX = 34;
	private static String script = "Ave151 Mover And DB";
	
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public Main() throws Exception{
		MySql mysql = null;
		CrossJpa jpa  = null;
		mysql = this.getConnection();
		jpa = getJpa();

		if (mysql != null || jpa != null) {

			System.out.println(INDEX + ") " + script);
			Thread.sleep(1000);
			(new Ave151ExporterRunner(mysql, jpa)).run();
		}

		if(mysql != null)
			mysql.close();
		if(jpa != null)
			jpa.close();
	}

	private MySql getConnection() {
		MySql mysql = null;
		try (InputStream input = new FileInputStream("cross-scripts.properties")) {

			Properties prop = new Properties();
			prop.load(input);

			String url = prop.getProperty("db.url");
			String user = prop.getProperty("db.user");
			String password = prop.getProperty("db.password");

			mysql = MySql.getInstance(url, user, password);

		} catch (IOException e) {
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		return mysql;
	}

	private CrossJpa getJpa() {
		CrossJpa jpa = null;
		try (InputStream input = new FileInputStream("cross-scripts.properties")) {
			
			Properties prop = new Properties();
			prop.load(input);
			
			String url = prop.getProperty("db.url");
			String user = prop.getProperty("db.user");
			String password = prop.getProperty("db.password");
			String driver = prop.getProperty("driver-class-name");
			
			jpa = CrossJpa.getInstance(url, user, password, driver);
			
		} catch (Exception e) {
			logger.error(e);
		} 
		return jpa;
	}

	private String getParameter(String name) {
		String parameter = null;
		try (InputStream input = new FileInputStream("cross-scripts.properties")) {
			
			Properties prop = new Properties();
			prop.load(input);
			
			parameter = prop.getProperty(name);
			
		} catch (Exception e) {
			logger.error(e);
		} 
		return parameter;
	}

	public static void main(String[] args){
		try {
			new Main();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}

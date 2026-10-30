package cross.cross_scripts.R9_CR.attachments;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class DimensionWriterThread implements Runnable {

	//only for production
	private static final String ABSOLUTE_PATH = "/usr/local/apache-tomcat-6.0.41/cross_files";

	private static final Logger logger = LogManager.getLogger(DimensionWriterThread.class);
	private MySql mysql;
	private List<String> list;
	private Integer min;
	private Integer max;


	public DimensionWriterThread(MySql mysql, List<String> list, Integer min, Integer max) {
		super();
		this.mysql = mysql;
		this.list = list;
		this.min = min;
		this.max = max;
		this.mysql.setAutocommit(true);
	}

	@Override
	public void run() {
		logger.error("Run MysqlThread " + this.min + "-"+this.max);
		for(int i= min; i<max;i++) {
			Matcher m = Pattern.compile("^(\\d+)\\s+(.+)\\s*$").matcher(list.get(i));
			if(m.find()) {
				String dimension = m.group(1);
				String path = m.group(2);
				//only for production
				if(path.startsWith("."))
					path = ABSOLUTE_PATH + path.substring(1);
				try(Statement readStmt = mysql.getStatement();){
//										logger.info("Search attachment with path:" + path);
//										logger.info(i);
					ResultSet q = readStmt.executeQuery("SELECT a.id from Allegati a where a.path_file = '"+path+"';");


					while(q.next()) {
						Integer id = q.getInt(1);
						logger.info("Found attachment:" + id);
						try(Statement writeStmt = mysql.getStatement();){
							writeStmt.execute("update Allegati set dimensione = "+dimension+" where id="+id+";");
						}
					}
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		logger.error("END MysqlThread " + this.min + "-"+this.max);
	}

}

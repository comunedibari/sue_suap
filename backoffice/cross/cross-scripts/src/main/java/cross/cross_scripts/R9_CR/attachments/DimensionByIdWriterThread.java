package cross.cross_scripts.R9_CR.attachments;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class DimensionByIdWriterThread implements Runnable {


	private static final Logger logger = LogManager.getLogger(DimensionByIdWriterThread.class);
	private MySql mysql;
	private List<String> list;
	private Integer min;
	private Integer max;


	public DimensionByIdWriterThread(MySql mysql, List<String> list, Integer min, Integer max) {
		super();
		this.mysql = mysql;
		this.list = list;
		this.min = min;
		this.max = max;
		this.mysql.setAutocommit(true);
	}

	@Override
	public void run() {
		logger.warn("Run MysqlThread " + this.min + "-"+this.max);
		for(int i= min; i<max;i++) {
			Matcher m = Pattern.compile("^(\\d+)\\s+(\\d+)\\s*$").matcher(list.get(i));
			if(m.find()) {
				String dimension = m.group(1);
				String attachmentsId = m.group(2);
				logger.info("dimensione = "+dimension+"; id="+attachmentsId+";");
				//only for production
				try(Statement writeStmt = mysql.getStatement();){
					writeStmt.execute("update Allegati set dimensione = "+dimension+" where id="+attachmentsId+";");
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		logger.warn("END MysqlThread " + this.min + "-"+this.max);
	}

}

package cross.cross_scripts.ave_115;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.MySql;

/**
 * extract all of owner from xml
 * 24-07-31 add a temporary table to save owner, removed file
 */
public class Deleter extends BaseHandler {

	public static final String STAGING_DELETE_DML = "delete from staging where id_staging in (select id_staging from pratica where id_pratica < :V_A and id_pratica > :V_B);";
	public static final String PAPERWORK_DELETE_DML = "delete from pratica where id_pratica < :V_A and id_pratica > :V_B;";
	
	private static final Logger logger = LogManager.getLogger(Deleter.class);

	public Deleter(MySql openCrossMySql) {
		super(openCrossMySql, null);
	} 

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		
		this.getMysql().setAutocommit(true);
		
		int min = 0;
		int max = 250000;
		int increment = 1000;
		
		logger.info("min: " + min);
		logger.info("max: " + max);

		for(int i = max; i > min; i-=increment) {
			process(i-increment , i);
		}
		this.getMysql().setAutocommit(false);
		logger.warn("END " + this.getClass().getName());
	}

	private void process(int a, int b) throws SQLException {
		String dmlStaging = STAGING_DELETE_DML.replace(":V_A", Integer.toString(b)).replace(":V_B", Integer.toString(a));
		String dmlPaperwork = PAPERWORK_DELETE_DML.replace(":V_A", Integer.toString(b)).replace(":V_B", Integer.toString(a));
		this.getMysql().setAutocommit(true);
		try(Statement stmt = this.getMysql().getStatement()){
			stmt.execute("set foreign_key_checks = 0;");
			stmt.execute(dmlStaging);
			stmt.execute(dmlPaperwork);
		}
	}
}

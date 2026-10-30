package cross.cross_scripts.ave_58;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.MySql;


/**
 * for attachment not bound with events
 */
public class DiskPathFixer extends BaseHandler{

	private static final Logger logger = LogManager.getLogger(DiskPathFixer.class);
	
	private String documentBase;
	private String attachmentBase;
	private String archiveDiskLabel = null;
	
	public DiskPathFixer(MySql mysql) {
		super(mysql, null);
	}

	@Override
	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		
		documentBase = getPath("documenti.fs.folder");
		attachmentBase = getPath("attachment.tmp.folder");
		
		archiveDiskLabel = getDiskLabel(DiskPathHandler.archiveDisk);
		
		String query = "select a.id as attId, a.dimensione as dimensione, a.path_file as path_file from allegati a "
				+ "where new_path_file is null and path_file is not null";

		consumeSelect(query, set -> {
			Integer attachmentId = null;
			String pathFile = null;
			BigDecimal dimensione = null;
			try {
				attachmentId = set.getInt("attId");
				dimensione = set.getBigDecimal("dimensione");
				pathFile = set.getString("path_file");

				logger.info("attachmentId " + attachmentId);
				logger.info("dimensione " + dimensione);
				logger.info("pathFile " + pathFile);
				
				handle(attachmentId, pathFile, dimensione);

			} catch (Exception e) {
				logger.error("attachmentId: " + attachmentId);
				throw new RuntimeException(e);
			}
		});
		logger.warn("end " + this.getClass().getName());
	}

	public void handle(Integer attachmentId, String pathFile, BigDecimal dimensione) throws SQLException, ClassNotFoundException {
		String base = null;
		if(pathFile != null && pathFile.length() > 0) {
			boolean check = true;
			if(pathFile.startsWith(documentBase))
				base = documentBase;
			else if(pathFile.startsWith(attachmentBase))
				base = attachmentBase;
			else {
				check = false;
				logger.error("verify " + pathFile + "from id " + attachmentId);
			}

			if(check) {
				String newBase = modifyBase(base, archiveDiskLabel);
				Path newPathFile = Paths.get(newBase + pathFile.replace(base, ""));
				logger.info(newPathFile.toString());
				try(Statement wstmt = mysql.getStatement()){
					update(wstmt, attachmentId, newPathFile.toString());
					updateDimension(wstmt, archiveDiskLabel, dimensione);
				}
			}
		}
	}

	private String getPath(String name) throws SQLException {
		String query = "select value from configuration where name = '"+name+"'";
		return executeSelect(query, set -> {
			String value = "";
			try {
				value = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return value;
		}).get(0);
	}

	private String getDiskLabel(int id) throws SQLException {
		String query = "select etichetta from disco where id = " + id;
		return executeSelect(query, set -> {
			String value = "";
			try {
				value = set.getString(1);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return value;
		}).get(0);
	}
	
	private BigDecimal getDiskDimension(String label) throws SQLException {
		String query = "select dimensione_occupata from disco where etichetta = '" + label +"'";
		return executeSelect(query, set -> {
			BigDecimal value = null;
			try {
				value = set.getBigDecimal(1);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return value;
		}).get(0);
	}

	private void update(Statement stmt, Integer attachmentId, String newPathFile)throws SQLException, ClassNotFoundException{
		String modPathFile = newPathFile.replaceAll("\\\\","\\\\\\\\");
		modPathFile = modPathFile.replaceAll("'", "\\\\'");
		String query = "update allegati set new_path_file = '"+modPathFile+"' where id = " + attachmentId+";";
		stmt.executeUpdate(query);
	}

	private void updateDimension(Statement stmt, String label, BigDecimal dimensione)throws SQLException, ClassNotFoundException{
		BigDecimal dimensioneOccupata = getDiskDimension(label);
		dimensioneOccupata = dimensioneOccupata.add(dimensione);
		String query = "update disco set dimensione_occupata=" + dimensioneOccupata + " where etichetta = '"+label+"'";
		stmt.executeUpdate(query);
	}
	
	protected String modifyBase(String base, String newFolder) {
		String newBase = null;
		Matcher m = Pattern.compile("^(.+)\\W(\\w+)$").matcher(base);
		if(m.find()) {
			String part1 = m.group(1);
			String part2 = m.group(2);
			newBase = part1 + File.separator + newFolder + File.separator + part2;
		}
		else
			throw new RuntimeException("check this base:" +base);
		return newBase;
	}
}

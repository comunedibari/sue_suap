package cross.cross_scripts.ave_58;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.MySql;

public class DiskPathHandler extends BaseHandler{

	private static final Logger logger = LogManager.getLogger(DiskPathHandler.class);
	private Map<Integer, String> attachmentsFolder = new HashMap<>();
	private String documentBase;
	private String attachmentBase;

	static public int archiveDisk = 1;
	static public int currentDisk = 2;

	Map<String,BigDecimal> dimensioneOccupate = new HashMap<>();

	public DiskPathHandler(MySql mysql) {
		super(mysql, null);
	}

	@Override
	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		documentBase = getPath("documenti.fs.folder");
		attachmentBase = getPath("attachment.tmp.folder");

		int min = min("id_pratica_evento", "pratiche_eventi");
		int max = max("id_pratica_evento", "pratiche_eventi");
		int block = 1000;

		logger.info("min " + min);
		logger.info("max " + max);

		for(int i =2016; i<2023; i++)
			attachmentsFolder.put(i, getDiskLabel(archiveDisk));
		for(int i =2023; i<2025; i++)
			attachmentsFolder.put(i, getDiskLabel(currentDisk));

		dimensioneOccupate.put(getDiskLabel(archiveDisk), new BigDecimal(0));
		dimensioneOccupate.put(getDiskLabel(currentDisk), new BigDecimal(0));
		
		for(int i = min; i <= max; i +=block) {
			int a = i;
			int b = i+block-1;
			logger.warn("betweeen " + a + " and " + b);
			process(a, b);
			try(Statement wstmt = mysql.getStatement()){
				wstmt.execute("COMMIT");
			}

		}
		logger.warn("END " + this.getClass().getName());
	}

	public void process(int a, int b) throws SQLException {
		String query = "select pe.id_pratica_evento as id_pratica_evento, pe.data_evento as data_evento, pea.id_allegato as attId, "
				+ "a.dimensione as dimensione, a.path_file as path_file "
				+ "from pratiche_eventi pe inner join pratiche_eventi_allegati pea on pe.id_pratica_evento = pea.id_pratica_evento "
				+ "inner join allegati a on pea.id_allegato = a.id "
				+ "where pe.id_pratica_evento between " + a + " and " + b +" "
				+ "order by pe.data_evento asc";

		consumeSelect(query, set -> {
			Integer attachmentId = null;
			Integer id = null;
			String pathFile = null;
			BigDecimal dimensione = null;
			LocalDate date = null;
			try {
				id = set.getInt("id_pratica_evento");
				attachmentId = set.getInt("attId");
				dimensione = set.getBigDecimal("dimensione");
				pathFile = set.getString("path_file");
				date = set.getDate("data_evento").toLocalDate();

				logger.info("id " + id);
				logger.info("attachmentId " + attachmentId);
				logger.info("date " + date);
				logger.info("dimensione " + dimensione);
				logger.info("pathFile " + pathFile);
				
				handle(attachmentId, date, pathFile, dimensione);

			} catch (Exception e) {
				logger.error("ERROR with id " + id + ", attachmentId: " + attachmentId);
				throw new RuntimeException(e);
			}
		});
	}

	public void handle(Integer attachmentId, LocalDate date, String pathFile, BigDecimal dimensione) throws SQLException, ClassNotFoundException {
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
				String newBase = modifyBase(base, attachmentsFolder.get(date.getYear()));
				Path newPathFile = Paths.get(newBase + pathFile.replace(base, ""));
				logger.info(newPathFile.toString());
				try(Statement wstmt = mysql.getStatement()){
					update(wstmt, attachmentId, newPathFile.toString());
					updateDimension(wstmt, attachmentsFolder.get(date.getYear()), dimensione);
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

	private void update(Statement stmt, Integer attachmentId, String newPathFile)throws SQLException, ClassNotFoundException{
		String modPathFile = newPathFile.replaceAll("\\\\","\\\\\\\\");
		modPathFile = modPathFile.replaceAll("'", "\\\\'");
		String query = "update allegati set new_path_file = '"+modPathFile+"' where id = " + attachmentId+";";
		stmt.executeUpdate(query);
	}

	private void updateDimension(Statement stmt, String label, BigDecimal dimensione)throws SQLException, ClassNotFoundException{
		
		dimensioneOccupate.put(label, dimensioneOccupate.get(label).add(dimensione));
		String query = "update disco set dimensione_occupata=" + dimensioneOccupate.get(label) + " where etichetta = '"+label+"'";
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

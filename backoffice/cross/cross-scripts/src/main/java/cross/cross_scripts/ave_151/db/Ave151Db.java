package cross.cross_scripts.ave_151.db;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_151.Ave151Constants;
import cross.cross_scripts.ave_151.Paperwork;
import cross.cross_scripts.ave_151.PaperworkEvent;

public class Ave151Db extends BaseHandler{

	public final static String FILE_IN_ERROR = "attachments_in_error_db";
	private final static String MOVER ="attachments_changer_db";
	private final static String MOVER_BARI ="attachments_bari_changer_db";
	private final static String OLD_PATHS ="oldPaths.txt";
	private final static String BASE_PATH ="."+File.separator+"sql";
	private final static String BASE_CHECKER_PATH ="."+File.separator+"checker";
	private final static int MAX_CHUNK = 10000;
	
	private static final Logger logger = LogManager.getLogger(Ave151Db.class);

	private int paperworkCounter = 0;
	private int attachmentsCounter = 0;

	private Map<Boolean,Integer>  counters;
	private Map<Boolean,Integer>  fileCounters;
	private Map<Boolean,Writer>  pws;
	
	private Writer  pwAttachmentInError;
	private Writer  oldPaths;
	int increment = 1000;

	private String documentsFolder;
	private String mailFolder;
	private String attachmentsFolder;
	private String bariDbName;

	public Ave151Db(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		logger.warn("Start " + this.getClass().getName());

		init();
		read();
		closer();
		
		logger.warn("COUNTER: total paperwork:" + paperworkCounter);
		logger.warn("COUNTER: total attachment:" + attachmentsCounter);
		logger.warn("end " + this.getClass().getName());
	}

	private void init() throws ClassNotFoundException, SQLException, IOException {
		pws = new HashMap<>();
		fileCounters = new HashMap<>();
		counters = new HashMap<>();

		checkPath(BASE_PATH);
		checkPath(BASE_CHECKER_PATH);
		
		//true: is bari, false isn't bari
		fileCounters.put(false, 1);
		fileCounters.put(true, 1);
		
		updateFilename(true);
		updateFilename(false);

		pwAttachmentInError = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(BASE_CHECKER_PATH+File.separator+FILE_IN_ERROR+".txt")));
		bariDbName = value("descrizione", "lk_comuni", "id_comune = "+Ave151Constants.ID_COMUNE_BARI);
		oldPaths = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(BASE_PATH+File.separator+OLD_PATHS)));
		
		attachmentsFolder = value("value","configuration","name = 'attachment.tmp.folder'");
		documentsFolder = value("value","configuration","name = 'documenti.fs.folder'");
		mailFolder = value("value","configuration","name = 'mail.folder'");
	}

	public void checkPath(String path) throws IOException{
		File directory = new File(path);
		if(!directory.exists())
			directory.mkdir();
	}
	
	public void updateFilename(boolean isBari) throws IOException{
		String name = (isBari?MOVER_BARI:MOVER);
		//true: is bari, false isn't bari
		if(pws.get(isBari) != null)
			pws.get(isBari).close();
		pws.put(isBari, new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(BASE_PATH+File.separator+name+"_"+fileCounters.get(isBari)+".sql"))));

		fileCounters.put(isBari, fileCounters.get(isBari)+1);
		counters.put(isBari, 1);
	}
	
	private void read() throws ClassNotFoundException, SQLException {
		int maxAttachment = max("id", "allegati");
		
		for(int iAttachmentId = maxAttachment; iAttachmentId > 0; iAttachmentId-=increment) {
			int currentMinAttachmentId = (iAttachmentId-increment+1);
			currentMinAttachmentId = (currentMinAttachmentId<=0?1:currentMinAttachmentId);		
			
			String query = Ave151Constants.QUERY_ATTACHMENTS
					.replace(Ave151Constants.MAX, ""+iAttachmentId)
					.replace(Ave151Constants.MIN, ""+currentMinAttachmentId);
			
//			logger.info("Attachments query: " + query);
			try(Statement stmt = mysql.getStatement()){
				consumeSelect(query, set -> {
					Integer attachmentId = null;
					String pathFile= null;
					try {
						attachmentId = set.getInt("id");
						pathFile = set.getString("pathFile");
						processAttachment(attachmentId, pathFile);
					}catch(Exception e) {
						logger.error("ERROR: with " + attachmentId, e);
						try {
							pwAttachmentInError.append("check: attachmentId:" + attachmentId+"\n");
						} catch (IOException e1) {
							
							e1.printStackTrace();
							throw new RuntimeException(e1);
						}
					}
				});
			}
		}
	}

	private void processAttachment(Integer attachmentId, String pathFile) throws Exception {
		attachmentsCounter++;
		String QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery = Ave151Constants.QUERY_PAPERWORK_EVENT_FROM_ATTACHMENT.replace(Ave151Constants.ATTACHMENT_ID, ""+attachmentId);
		logger.info("attachmentId: " + attachmentId);
//		logger.info("QUERY_PAPERWORK_EVENT_FROM_ATTACHMENT: " + QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery);

		List<PaperworkEvent> rets =  executeSelect(QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery, set -> {
			PaperworkEvent value = new PaperworkEvent();
			try {
				value.setDate(set.getDate("data_evento"));
				value.setPaperworkEventId(set.getInt("id_pratica_evento"));
				value.setPaperworkId(set.getInt("id_pratica"));
			} catch (SQLException e) {
				logger.error("QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery",e);
				throw new RuntimeException(e);
			}
			return value;
		});
		PaperworkEvent event = rets.get(0);
		
		String QUERY_PAPERWORKQuery = Ave151Constants.QUERY_PAPERWORK.replace(Ave151Constants.PAPERWORK_ID, ""+event.getPaperworkId());
//		logger.info("QUERY_PAPERWORK_EVENT_FROM_ATTACHMENT: " + QUERY_PAPERWORKQuery);

		Paperwork paperwork =  executeSelect(QUERY_PAPERWORKQuery, set -> {
			Paperwork value = new Paperwork();
			try {
				value.setId(set.getInt("id_pratica"));
				value.setTown(set.getString("descrizione"));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return value;
		}).get(0);
		
		if(event.getDate() == null) {
			throw new RuntimeException("date not found");
		}
		if(paperwork.getTown() == null) {
			throw new RuntimeException("town not found");
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(event.getDate());
		int anno = cal.get(Calendar.YEAR);
		rewriteAttachment(attachmentId, pathFile, paperwork.getTown(), anno);
	}
	
	private void rewriteAttachment(Integer attachmentId, String pathFile, String town, int year) throws Exception {

		oldPaths.append(""+attachmentId + " " + pathFile+"\n");
		String newFolder = town+Ave151Constants.SEPARATOR;
		if(bariDbName.equalsIgnoreCase(town)) { 
			if(year >= Ave151Constants.BARI_START_YEAR)
				newFolder += year+Ave151Constants.SEPARATOR;
			else
				newFolder += "pre"+Ave151Constants.BARI_START_YEAR+Ave151Constants.SEPARATOR;
		}

		String newPathFile = null;
		if(pathFile.startsWith(attachmentsFolder))
			newPathFile = pathFile.replace(attachmentsFolder, newPrefix(attachmentsFolder,newFolder));
		else if(pathFile.startsWith(documentsFolder))
			newPathFile = pathFile.replace(documentsFolder, newPrefix(documentsFolder,newFolder));
		else if(pathFile.startsWith(mailFolder))
			newPathFile = pathFile.replace(mailFolder, newPrefix(mailFolder,newFolder));
		else
			throw new RuntimeException("prefix not found!");

		boolean isBari = bariDbName.equals(town);
		counters.put(bariDbName.equals(town), counters.get(isBari)+1);
		if(counters.get(isBari) == MAX_CHUNK) 
			updateFilename(isBari);
		
		insertIntoTemplate(town, attachmentId, pathFile, getPath(newPathFile, 0));
	}

	private void insertIntoTemplate(String town, Integer attachmentId, String oldPathFile, String newPath) throws Exception {
			
		String QUERY_UPDATE_PATHQuery = Ave151Constants.QUERY_UPDATE_PATH
				.replace(Ave151Constants.ATTACHMENT_ID, ""+attachmentId)
				.replace(Ave151Constants.NEW_PATH_FILE, newPath.replaceAll("'", "''"))
				.replace(Ave151Constants.OLD_PATH_FILE, oldPathFile.replaceAll("'", "''"))
				;
		Writer pw = pws.get(bariDbName.equals(town));
		pw.append(QUERY_UPDATE_PATHQuery+";");
		pw.append("\n");
	}

	private String newPrefix(String prefix, String newFolder) {
		String newPrefix = null;
		Matcher matcher = Pattern.compile("^(.+/)((?:attachments|mail|documents))$").matcher(prefix);
		if(matcher.find()) {
			newPrefix = matcher.group(1) + newFolder+matcher.group(2);
		} else
			throw new RuntimeException("wrong prefix, check it");
		return newPrefix;
	}

	private String getPath(String path,int minusLevel) {
		StringBuilder ret = new StringBuilder();
		String[] token = path.split("["+Ave151Constants.SEPARATOR+"]{1,2}");
		for(int i = 0; i < token.length-minusLevel; i++) {
			if(token[i].length() > 0)
				ret.append(Ave151Constants.SEPARATOR+token[i]);
		}
		return ret.toString();
	}

	private void closer() throws IOException {
		logger.warn("Closing...");
		for(Writer pw:pws.values())
			pw.close();
		pwAttachmentInError.close();
		oldPaths.close();
	}
}

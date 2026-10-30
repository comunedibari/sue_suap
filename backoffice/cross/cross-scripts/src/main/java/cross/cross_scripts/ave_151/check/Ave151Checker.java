package cross.cross_scripts.ave_151.check;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import cross.cross_scripts.ave_151.Ave151Constants;
import cross.cross_scripts.ave_151.Paperwork;
import cross.cross_scripts.ave_151.PaperworkEvent;

public class Ave151Checker extends BaseHandler{

	private final static String BASE_PATH ="."+File.separator+"checker";
	private static final Logger logger = LogManager.getLogger(Ave151Checker.class);

	private int paperworkCounter = 0;
	private int attachmentsCounter = 0;

	private Writer  pwAttachmentInError;
	int increment = 1000;

	private String documentsFolder;
	private String mailFolder;
	private String attachmentsFolder;

	private List<Integer> ids;
	private String filename;
	public Ave151Checker(MySql mysql, CrossJpa jpa, String filename) {
		super(mysql, jpa);
		this.filename = filename;
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
		ids = new ArrayList<>();

		checkPath(BASE_PATH);
		
		pwAttachmentInError = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(BASE_PATH + File.separator+filename+"_from_check.txt")));
		
		attachmentsFolder = value("value","configuration","name = 'attachment.tmp.folder'");
		documentsFolder = value("value","configuration","name = 'documenti.fs.folder'");
		mailFolder = value("value","configuration","name = 'mail.folder'");
		
		ids = Files.lines(Paths.get(BASE_PATH + File.separator+filename+".txt")).map(l->{
			Matcher m = Pattern.compile("(\\d+)").matcher(l);
			if(m.find())
				return Integer.parseInt(m.group(1));
			else 
				throw new RuntimeException("not found");
		}).collect(Collectors.toList());
	}

	public void checkPath(String path) throws IOException{
		File directory = new File(path);
		if(!directory.exists())
			directory.mkdir();
	}
	
	private void read() throws ClassNotFoundException, SQLException, IOException {
		Map<String, List<String>> map = new HashMap<>();
		for(Integer id:ids) {
			try {
				logger.info("attachment: " + id);
				String QUERY_ATTACHMENT = Ave151Constants.QUERY_ATTACHMENT.replace(Ave151Constants.ATTACHMENT_ID, ""+id);
				List<String> pathFiles = executeSelectWithError(QUERY_ATTACHMENT, set -> {
					try {
						return set.getString("pathFile");
					} catch (SQLException e) {
						throw new RuntimeException("Path not found", e);
					}
				});
				if(pathFiles == null || pathFiles.size() == 0)
					throw new RuntimeException("Path not found");
				
				String pathFile = pathFiles.get(0);
				if(pathFile.startsWith(attachmentsFolder));
				else if(pathFile.startsWith(documentsFolder));
				else if(pathFile.startsWith(mailFolder));
				else
					throw new RuntimeException("prefix not found!");
				
				String QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery = Ave151Constants.QUERY_PAPERWORK_EVENT_FROM_ATTACHMENT.replace(Ave151Constants.ATTACHMENT_ID, ""+id);
				List<PaperworkEvent> rets =  executeSelectWithError(QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery, set -> {
					PaperworkEvent value = new PaperworkEvent();
					try {
						value.setDate(set.getDate("data_evento"));
						value.setPaperworkEventId(set.getInt("id_pratica_evento"));
						value.setPaperworkId(set.getInt("id_pratica"));
					} catch (SQLException e) {
						logger.error("QUERY_PAPERWORK_EVENT_FROM_ATTACHMENTQuery",e);
						throw new RuntimeException("events not found", e);
					}
					return value;
				});
				if(rets == null || rets.isEmpty())
					throw new RuntimeException("event not found");
				PaperworkEvent event = rets.get(0);
//				logger.info("\tevent:" + event);

				String QUERY_PAPERWORKQuery = Ave151Constants.QUERY_PAPERWORK.replace(Ave151Constants.PAPERWORK_ID, ""+event.getPaperworkId());
				List<Paperwork> paperworks =  executeSelectWithError(QUERY_PAPERWORKQuery, set -> {
					Paperwork value = new Paperwork();
					try {
						value.setId(set.getInt("id_pratica"));
						value.setTown(set.getString("descrizione"));
					} catch (SQLException e) {
						throw new RuntimeException("paperwork not found", e);
					}
					return value;
				});
				if(paperworks == null || paperworks.isEmpty())
					throw new RuntimeException("event not found");
				if(event.getDate() == null) {
					throw new RuntimeException("date not found");
				}
				if(paperworks.get(0).getTown() == null) {
					throw new RuntimeException("town not found");
				}
			}
			catch(Exception e) {
				List<String> list = map.get(e.getMessage());
				if(list == null) {
					
					list = new ArrayList<>();
					map.put(e.getMessage(), list);
				}
				if(id == null)
					System.out.println("");
				if((""+id).length() == 0)
					System.out.println("");
				list.add(""+id);
				
//				pwAttachmentInError.append("attachemnt: "+id+"\n\t"+e.getMessage()+"\n");
			}
		}
		for (Entry<String, List<String>> entry: map.entrySet()) {
			pwAttachmentInError.append("message: "+entry.getKey() + "\n");
			for(String id: entry.getValue()) {
				pwAttachmentInError.append("\tid: "+id + "\n");
			}
		}
	}

	private void closer() throws IOException {
		logger.warn("Closing...");
		pwAttachmentInError.close();
	}
}

package cross.cross_scripts.ave_74;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class RuvoDiPugliaAttachmentCopier extends BaseHandler{

	private static final String SOURCE_BASEE_PATH = "/usr/local/apache-tomcat-6.0.41";
	private static final String DEST_BASEE_PATH = "/tmp";
	private static final String COMMANDS = "-p";
	
	private static final Logger logger = LogManager.getLogger(RuvoDiPugliaAttachmentCopier.class);

	public RuvoDiPugliaAttachmentCopier(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		//input is the output of ruvo_di_pugliaExporter class
		CsvReader reader = new CsvReader("pratiche_eventi_allegati");
		
		List<List<String>> lines = reader.getLines();
		lines.remove(0);
		Writer  pw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(".//ruvo_di_puglia_attachments_copier.sh")));
		for(List<String> line: lines) {
			String pathFile = line.get(line.size()-1);
			String destDirectory = getDestDirectory(pathFile);
			String destPath = getDestPath(pathFile);
			pw.append("mkdir -p "+DEST_BASEE_PATH+destDirectory+" && ");
			pw.append("cp " +COMMANDS + " \"" + SOURCE_BASEE_PATH + pathFile + "\" \"" + DEST_BASEE_PATH + destPath+"\"\n");
		}
		pw.close();
		logger.warn("end " + this.getClass().getName());
	}

	private String getDestPath(String path) {
		StringBuilder ret = new StringBuilder();
		String[] token = path.split("[/]{1,2}");
		for(int i = 0; i < token.length; i++) {
			if(token[i].length() > 0)
				ret.append("/"+token[i]);
		}
		return ret.toString();
	}
	private String getDestDirectory(String path) {
		StringBuilder ret = new StringBuilder();
		String[] token = path.split("[/]{1,2}");
		for(int i = 0; i < token.length-1; i++) {
			if(token[i].length() > 0)
				ret.append("/"+token[i]);
		}
		return ret.toString();
	}
}

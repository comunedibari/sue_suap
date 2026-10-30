package cross.cross_scripts.R9_CR.attachments.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class DimensionFileCleaner {
	//only for production
	private static final String ABSOLUTE_PATH = "/usr/local/apache-tomcat-6.0.41/cross_files";

	private static final String SIZES_FILE = "sizetxt_prod.txt";

	private static final Logger logger = LogManager.getLogger(DimensionFileCleaner.class);

	private MySql mysql;
	private int threadNumber;
	private static Set<String> excludes = new HashSet(Arrays.asList("email", "unzip"));


	public DimensionFileCleaner(MySql mysql) {
		this.mysql = mysql;
	}

	public void run() throws Exception {
		logger.error("run DimensionFileCleaner");
		Map<String,String> list = readFile();
		int counter = 0; 
		int totalCounter = 0;
		try(Statement stmt = mysql.getStatement()){

			ResultSet s = stmt.executeQuery("select path_file from allegati where dimensione is not null && path_file is not null");
			while(s.next()) {
				totalCounter++;
				String path = s.getString(1);
				path = path.replaceAll("//", "/");
				String removed = list.remove(path.trim());
				if(removed != null) {
					counter++;
					logger.info("DELETE " + path + "(size: "+list.size()+")");
				}
				else
					logger.info("NOT FOUND " + path );
				logger.info("counter: " + counter + "/"+totalCounter);
			}
		}
		writeFile(list);
	}

	private Map<String,String> readFile() throws IOException{
		Map<String,String> list = new LinkedHashMap<>();
		try(BufferedReader bf = new BufferedReader(new FileReader(SIZES_FILE));){
			while(bf.ready()) {
				String line = bf.readLine().trim();
				Matcher m = Pattern.compile("^(\\d+)\\s+(.+)\\s*$").matcher(line);
				if(m.find()) {
					String dimension = m.group(1);
					String path = m.group(2);
					if(path.length() > 0) {
						boolean found = false;
						for(String ex: excludes)
							found |= path.contains(ex);
						if(!found) {
							if(path.contains("'"))
								path = path.replaceAll("'", "\\\\'");
							if(path.startsWith("."))
								path = ABSOLUTE_PATH + path.substring(1);
							list.put(path, line);
						}
					}
				}
			}
		}
		return list;
	}

	private void writeFile(Map<String,String> list) throws IOException{

		try(PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(".\\sizetxt_prod_new.txt")));){
			for(Entry<String, String> entry: list.entrySet()) {
				p.println(entry.getValue());
			}
		}
	}
}

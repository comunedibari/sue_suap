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
import utils.Constants;

public class DimensionFileCleanerAndMapper {
	//only for production
	private static final String ABSOLUTE_PATH = "/usr/local/apache-tomcat-6.0.41/cross_files";

	private static final String SIZES_FILE = "sizetxt_prod.txt";

	private static final Logger logger = LogManager.getLogger(DimensionFileCleanerAndMapper.class);

	private MySql mysql;
	private static Set<String> excludes = new HashSet(Arrays.asList("email", "unzip"));


	public DimensionFileCleanerAndMapper(MySql mysql) {
		this.mysql = mysql;
	}

	public void run() throws Exception {
		logger.warn("run "+ this.getClass().getName());
		Map<String,Node> mapOfPaths = readFile();
		int counter = 0; 
		int totalCounter = 0;
		try(Statement stmt = mysql.getStatement()){

			ResultSet s = stmt.executeQuery("select path_file from allegati where dimensione is not null && path_file is not null");
			while(s.next()) {
				totalCounter++;
				String path = s.getString(1);
				path = path.replaceAll("//", "/");
				Node removed = mapOfPaths.remove(path.trim());
				if(removed != null) {
					counter++;
					logger.info("DELETE " + path + "(size: "+mapOfPaths.size()+")");
				}
				else
					logger.info("NOT FOUND " + path );
				logger.info("counter: " + counter + "/"+totalCounter);
			}
		}
//		writeFile(mapOfPaths);
		
		try(Statement stmt = mysql.getStatement()){
			
			ResultSet s = stmt.executeQuery("select id, path_file from allegati where dimensione is null && path_file is not null");
			totalCounter = 0;
			counter = 0;
			while(s.next()) {
				totalCounter++;
				Integer id = s.getInt(1);
				String path = s.getString(2);
				path = path.replaceAll("//", "/");
				Node node = mapOfPaths.get(path);
				if(node != null) {
					counter++;
					node.setId(id);
				}
				logger.info("counter: " + counter + "/"+totalCounter);
				
			}
		}
		
		
		writeFile(mapOfPaths);
	}

	private Map<String,Node> readFile() throws IOException{
		Map<String,Node> list = new LinkedHashMap<>();
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
							
							list.put(path, new Node(path, Integer.parseInt(dimension)));
						}
					}
				}
			}
		}
		return list;
	}

	private void writeFile(Map<String,Node> list) throws IOException{

		try(PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(".\\"+ Constants.SIZES_FILE)));){
			for(Entry<String, Node> entry: list.entrySet()) {
				if(entry.getValue().getId() != null)
					p.println(entry.getValue().getDimension() + " " + entry.getValue().getId());
				else
					logger.error("NOT FOUND: " + entry.getValue().getPath());
			}
		}
	}
}

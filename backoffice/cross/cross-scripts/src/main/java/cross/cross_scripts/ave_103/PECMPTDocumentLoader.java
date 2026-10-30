package cross.cross_scripts.ave_103;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;

public class PECMPTDocumentLoader extends BaseHandler{
	private Map <String, Object> map;

	String OUTPUT_DIRECTORY = ".\\src\\main\\resources\\ave-103\\converted";
	String INPUT_DIRECTORY = ".\\src\\main\\resources\\ave-103\\input";
	private final static String PEC_EVENT_CODE = "PECMPT";

	private static final Logger logger = LogManager.getLogger(PECMPTDocumentLoader.class);

	public PECMPTDocumentLoader(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		Yaml yaml = new Yaml();
		InputStream inputStream = PECMPTDocumentLoader.class.getClassLoader().getResourceAsStream("ave-103\\configuration.yaml");
		map = (Map<String, Object>) yaml.load(inputStream);
	}

	@Override
	public void run() throws Exception {
		logger.warn("Start " + this.getClass().getName());
		List<Map<?,?>> fileMaps = (List<Map<?,?>>) this.map.get("files");
		try {

			Path path = Paths.get(INPUT_DIRECTORY);
			List<File> files = (List<File>) Stream.of(path.toFile().listFiles()).collect(Collectors.toList());

			for(File file: files) {		
				logger.info("File: " + file.getName());
				String base64 = encodeFileToBase64(file, OUTPUT_DIRECTORY);
				Optional<Map<?, ?>> currentMap = fileMaps.stream().filter(elem -> elem.get("name").equals(file.getName())).findFirst();
				if(!currentMap.isPresent())
					throw new RuntimeException("Wrong configuration for " + file.getName());

				String originalFileName = file.getName();
				String mimetype = (String)currentMap.get().get("mimetype");
				mimetype = (mimetype == null? "application/msword": mimetype);
				String output = (String)currentMap.get().get("output");
				output = (output == null? "PDF": output);

				String filename = originalFileName.replaceAll("\\.\\w+$", "");
				String description = (String)currentMap.get().get("descrizione");
				description = (description == null? filename: description);
				description = description.length() > 44? description.substring(0,45):description;

				insertIntoTemplate(base64, originalFileName , mimetype, output, filename + "." + output.toLowerCase(), description);

				int idTemplate = selectMaxId();
				if(idTemplate < 0)
					throw new RuntimeException("Wrong max value for " + file.getName());

				
				for(Integer idEvento: selectPecEvents()) {
					Integer idEnte = (Integer)currentMap.get().get("idEnte");
					insertIntoEventiTemplate(idEvento, idEnte, idTemplate);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String encodeFileToBase64(File inputFile, String outputFilePath) throws IOException {
		byte[] fileBytes = Files.readAllBytes(inputFile.toPath());
		String encodedString = Base64.getEncoder().encodeToString(fileBytes);
		//		Files.write(Paths.get(outputFilePath+File.separator+inputFile.getName()), encodedString.getBytes());
		return encodedString;
	}

	private void insertIntoTemplate(String base64file, String originalFilename, String mimeType, String output, String filenameOutput, String text) throws Exception {
		mysql.setAutocommit(true);
		try(Statement stmt = mysql.getStatement()){

			String query = "INSERT INTO templates (template,descrizione,nome_file,mime_type,file_output,nome_file_originale, complex_data) VALUES "+
					"('"+base64file.replace("'", "\\'")+"',"+
					"'"+text+"', "+
					"'"+filenameOutput + "', " +
					"'"+mimeType+"', " + 
					"'"+output+"', " +
					"'"+originalFilename+ "'" +
					", 1)";
			logger.warn("QUERY: " + query);
			stmt.execute(query);
		}
	}

	private int selectMaxId() throws SQLException {
		int max = -1;
		String query = "select max(id_template) from templates";
		try(Statement stmt = getMysql().getStatement();){
			ResultSet set = stmt.executeQuery(query);
			while(set.next()) {
				max = set.getInt(1);
			}
		}
		return max;
	}
	
	private List<Integer> selectPecEvents() throws SQLException {
		List<Integer> values = new ArrayList<>();
		String query = "select id_evento from processi_eventi where cod_evento = '"+PEC_EVENT_CODE+"'";
		try(Statement stmt = getMysql().getStatement();){
			ResultSet set = stmt.executeQuery(query);
			while(set.next()) {
				values.add( set.getInt(1));
			}
		}
		return values;
	}

	private void insertIntoEventiTemplate(int idEvento, int idEnte, int idTemplate) throws Exception {
		mysql.setAutocommit(true);
		try(Statement stmt = mysql.getStatement()){			
			String query = "insert into eventi_template(id_evento, id_ente, id_template) value ("+idEvento+", "+idEnte+", "+idTemplate+")";
			logger.warn("QUERY: " + query);
			stmt.execute(query);
		}
	}
}
package cross.cross_scripts.ave_74;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvReader {
	CSVReader reader;
	List<List<String>> lines;
	
	public CsvReader(String name) throws IOException{
		lines = new LinkedList<>();
		try {
			reader = new CSVReader(new InputStreamReader(
					new FileInputStream(".\\"+name+".csv"), StandardCharsets.UTF_8));

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				List<String> line = new LinkedList<>();
				// Process each line
				for (String token : nextLine) {
					line.add(token);
				}
				lines.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
	}

	public List<List<String>> getLines() {
		return lines;
	}
	
}

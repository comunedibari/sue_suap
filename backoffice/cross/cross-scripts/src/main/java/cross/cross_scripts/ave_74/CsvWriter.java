package cross.cross_scripts.ave_74;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

public class CsvWriter {
	CSVWriter writer ;
	
	public CsvWriter(String name) throws IOException{
		writer = new CSVWriter(new FileWriter(".\\"+name+".csv"));
	}
	
	public void append(List<String[]> data) {
        writer.writeAll(data);
	}
	
	public void close() throws IOException {
		writer.close();
	}
}

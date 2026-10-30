package cross.cross_scripts.R9_CR.attachments;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.MySql;

public class DimensionAttachmentsWriterByFileThread {

	private static final String SIZES_FILE = "sizetxt_prod.txt";
	
	private static final Logger logger = LogManager.getLogger(DimensionAttachmentsWriterByFileThread.class);

	private MySql mysql;
	private int threadNumber;
	private static Set<String> excludes = new HashSet(Arrays.asList("email", "unzip"));
	
	
	public DimensionAttachmentsWriterByFileThread(MySql mysql, String threadNumberStr) {
		this.mysql = mysql;
		this.threadNumber = Integer.parseInt(threadNumberStr);
		
	}

	public void run() throws Exception {
		logger.warn("run DimensionAttachmentsWriterByFileThread");
		logger.warn("SIZES_FILE:" + SIZES_FILE);
		
		mysql.setAutocommit(true);
		
		List<String> list = readFile();
		int blocks = threadNumber > 1 ? list.size()/threadNumber-1: list.size();
		logger.info("size:" + list.size());
		for(int i = 0; i < list.size(); i+=blocks) {
			int min = i;
			int max = (i+blocks) > (list.size())? list.size(): (i+blocks);
			DimensionWriterThread rc = new DimensionWriterThread(MySql.getAlwaysNewInstance(), list, min, max);
			Thread t = new Thread(rc);
			t.start();
			
		}
	}

	private List<String> readFile() throws IOException{
		List<String> list = new ArrayList();
		try(BufferedReader bf = new BufferedReader(new FileReader(SIZES_FILE));){
			while(bf.ready()) {
				String line = bf.readLine();
				if(line.length() > 0) {
					boolean found = false;
					for(String ex: excludes)
						found |= line.contains(ex);
					if(!found) {
						if(line.contains("'"))
							line = line.replaceAll("'", "\\\\'");
						list.add(line);
					}
				}
			}
		}
		return list;
	}

}
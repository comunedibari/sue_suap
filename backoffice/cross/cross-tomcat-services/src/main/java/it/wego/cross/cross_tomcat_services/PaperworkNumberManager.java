package it.wego.cross.cross_tomcat_services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import it.wego.cross.cross_tomcat_services.db.DbReader;
import it.wego.cross.cross_tomcat_services.utils.JNDINodeReader;
import it.wego.cross.cross_tomcat_services.utils.PairKey;
import it.wego.cross.cross_tomcat_services.utils.PaperworkNumberException;

public class PaperworkNumberManager 
{
	private static PaperworkNumberManager instance;

	//town,paperwork type -> number
	private Map<PairKey<Integer, Integer>, AtomicInteger> numberManager;
	
	public static synchronized PaperworkNumberManager getInstance() throws Exception{
		if(instance == null)
			instance = new PaperworkNumberManager();
		return instance;
	}

	public Integer getNewNumber(int townId, int paperworkTypeId) {
		Integer value = null;
		try {
			value = this.numberManager.get(new PairKey<>(townId, paperworkTypeId)).incrementAndGet();
		} catch(Exception e) {
			throw new PaperworkNumberException("Error unknown key:" + new PairKey<>(townId, paperworkTypeId), e);
		}
		return value;
	}
	
	private PaperworkNumberManager() throws Exception {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		s = currentRelativePath.toAbsolutePath().getParent().toString();
		File currentDirectory = new File(s);
		File serverFile = new File(currentDirectory.getAbsolutePath() + File.separator + "conf" + File.separator + "server.xml");
		
		JNDINodeReader jndi = new JNDINodeReader(serverFile);
		DbReader dbReader= new DbReader(jndi);
		toInstance(dbReader);
	}
	
	PaperworkNumberManager(DbReader dbReader) throws Exception {
		toInstance(dbReader);
	}
	
	private void toInstance(DbReader dbReader) throws Exception {
		numberManager = new ConcurrentHashMap<>();
		List<Integer> allPaperworkTypes = dbReader.findAllPaperworkTypes();
		List<Integer> institutions =dbReader.findAllInstitutionId();

		for (Integer institution : institutions) 
			for (Integer  type : allPaperworkTypes) 
				numberManager.put(new PairKey<>(institution,type), new AtomicInteger(0));

		Map<PairKey<Integer, Integer>, Integer> map = dbReader.findDataForCurrentYear();

		for(Entry<PairKey<Integer, Integer>, Integer> aggregateData: map.entrySet()) { 
			PairKey<Integer, Integer> curKey = new PairKey<>(aggregateData.getKey().getKey1(),aggregateData.getKey().getKey2());
			Integer counter = aggregateData.getValue();
			if(numberManager.containsKey(curKey))
				numberManager.put(curKey, new AtomicInteger(counter));
			else
				throw new PaperworkNumberException("Error unknown key:" + curKey);
		}
	}
	
	Integer getNumber(int institutionId, int paperworkTypeId) throws PaperworkNumberException{
		Integer value = null;
		try {
			value = this.numberManager.get(new PairKey<>(institutionId, paperworkTypeId)).get();
		} catch(Exception e) {
			throw new PaperworkNumberException(e);
		}
		return value;
	}
	
}

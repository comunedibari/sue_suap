package it.wego.cross.people;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

import it.wego.cross.people.dao.PendingProcessDao;
import it.wego.cross.people.dao.SubmittedProcessDao;
import it.wego.cross.people.utils.DatabaseEnum;
import it.wego.cross.people.utils.LogClass;

@Component
public class CrossBoFo {

	public static LogClass logger;

	Map<DatabaseEnum, EntityManagerFactory> dbs;
	
	public CrossBoFo(){
		dbs = new HashMap<>();
		dbs.put(DatabaseEnum.PEOPLE_G, Persistence.createEntityManagerFactory(DatabaseEnum.PEOPLE_G.get()));
		dbs.put(DatabaseEnum.PEOPLE_M, Persistence.createEntityManagerFactory(DatabaseEnum.PEOPLE_M.get()));
		dbs.put(DatabaseEnum.PEOPLE_BARI, Persistence.createEntityManagerFactory(DatabaseEnum.PEOPLE_BARI.get()));
	}
	public void changeAssociatedTechnicianAgent(String identificativoPratica, String idTechnician){
		for(EntityManagerFactory emf: dbs.values())
			changeAssociatedTechnicianAgent(emf, identificativoPratica, idTechnician);
	}

	public List<String> extractAssociatedTechnicianAgent(String identificativoPratica){
		List<String> technicians = new ArrayList<>();
		for(EntityManagerFactory emf: dbs.values())
			technicians.addAll(extractAssociatedTechnicianAgent(emf, identificativoPratica));
		
		return technicians;
	}

	private void changeAssociatedTechnicianAgent(EntityManagerFactory emf, String identificativoPratica, String idTechnician) {
		SubmittedProcessDao submittedProcessDao = new SubmittedProcessDao(emf);
		submittedProcessDao.modifyUserId(identificativoPratica, idTechnician);

		PendingProcessDao pendingProcessDao = new PendingProcessDao(emf);
		pendingProcessDao.modifyUserId(identificativoPratica, idTechnician);
	}
	
	private List<String> extractAssociatedTechnicianAgent(EntityManagerFactory emf, String identificativoPratica) {
		List<String> technicians = new ArrayList<>();
		SubmittedProcessDao submittedProcessDao = new SubmittedProcessDao(emf);
		technicians.addAll(submittedProcessDao.extractAssociatedTechnicianAgent(identificativoPratica));

		PendingProcessDao pendingProcessDao = new PendingProcessDao(emf);
		technicians.addAll(pendingProcessDao.extractAssociatedTechnicianAgent(identificativoPratica));

		List<String> ret = new ArrayList<>();
		for(String technician: technicians) 
			ret.add(technician.toUpperCase());
		return ret;
	}
	
	@PreDestroy
	public void closeConnections() {
		for(EntityManagerFactory emf: dbs.values()) {
			try {
				emf.close();
			}catch(Exception e) {
				LogClass.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}

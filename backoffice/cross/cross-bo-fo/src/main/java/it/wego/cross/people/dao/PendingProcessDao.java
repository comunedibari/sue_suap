package it.wego.cross.people.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.wego.cross.entity.people.PendingProcess;
import it.wego.cross.people.utils.BoFoTransaction;

@Repository
public class PendingProcessDao{
	
	@PersistenceContext
    private EntityManager em;
		
	public PendingProcessDao(){}

	public PendingProcessDao(EntityManagerFactory emf){
		em = emf.createEntityManager();
	}
	
	public void modifyUserId(final String processDataId, final String userId) {
		
		new BoFoTransaction(em) {

			@Override
			public void operations() {
				Query query = em.createNamedQuery("get_pending_process_by_process_data_id");
				query.setParameter("process_data_id", processDataId);
				PendingProcess pendingProcess = null;
				
				List<PendingProcess> list = query.getResultList();

				if(list.size() > 1)
					throw new RuntimeException("too much technician agent in pending_process with process_data_id: " + processDataId);
				else if (!list.isEmpty())
					pendingProcess = list.get(0);
				
				if(pendingProcess != null) {
					pendingProcess.setUserId(userId);
					em.merge(pendingProcess);
					em.flush();
				}
				
			}
			
		}.execute();

	}
	
	public List<String> extractAssociatedTechnicianAgent(final String processDataId) {
		Query query = em.createNamedQuery("get_technician_by_process_data_id");
		query.setParameter("process_data_id", processDataId);
		return query.getResultList();
	}
}

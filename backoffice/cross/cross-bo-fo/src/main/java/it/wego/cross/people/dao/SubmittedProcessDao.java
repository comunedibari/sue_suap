package it.wego.cross.people.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.wego.cross.entity.people.SubmittedProcess;
import it.wego.cross.people.utils.BoFoTransaction;

@Repository
public class SubmittedProcessDao{

	@PersistenceContext
	private EntityManager em;


	public SubmittedProcessDao(){}
	
	public SubmittedProcessDao(EntityManagerFactory emf){
		em = emf.createEntityManager();
	}

	public void modifyUserId(final String peopleProtocolId, final String userId) {
		new BoFoTransaction(em) {

			@Override
			public void operations() {
				Query query = em.createNamedQuery("get_submitted_process_by_people_protocol_id");
				query.setParameter("people_protocol_id", peopleProtocolId);
				SubmittedProcess submittedProcess = null;
			
				List<SubmittedProcess> list = query.getResultList();
				if(list.size() > 1)
					throw new RuntimeException("too much technician agent in submitted_process with people_protocol_id: " + submittedProcess);
				else if (!list.isEmpty())
					submittedProcess = list.get(0);
				
				if(submittedProcess != null) {
					submittedProcess.setUserId(userId);
					em.merge(submittedProcess);
					em.flush();

				}
			}

		}.execute();
	}
	
	public List<String> extractAssociatedTechnicianAgent(final String peopleProtocolId) {
		Query query = em.createNamedQuery("get_technician_by_people_protocol_id");
		query.setParameter("people_protocol_id", peopleProtocolId);
		return query.getResultList();
	}

}

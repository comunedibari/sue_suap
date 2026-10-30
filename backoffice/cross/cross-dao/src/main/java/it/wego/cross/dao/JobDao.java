package it.wego.cross.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class JobDao {


	@PersistenceContext
	private EntityManager em;

	public JobDao(EntityManager em) {
		this.em = em;
	}

	public JobDao() {}

	public Map<Integer, Long> extractEmailPerEventsWithStatus10(){
		String query = "SELECT e.id_pratica_evento, count(*)"
				+ "FROM pratiche_eventi pe inner join email e on e.id_pratica_evento = pe.id_pratica_evento inner join processi_eventi poe on pe.id_evento = poe.id_evento "
				+ "WHERE poe.flg_mail = 'S' AND pe.stato_mail != 10 "
				+ "GROUP BY e.id_pratica_evento";
		
		Query queryObj = this.em.createNativeQuery(query);
		
		List<Object[]> result = queryObj.getResultList();
		Map<Integer,Long> map = new HashMap<>();
		for(Object[] pair: result) {
			map.put((int)pair[0], (long)pair[1]);
		}
		return map;
	}
	
	public Long extractEmailSent(Integer idPraticaEvento){
		String query = "select count(*)\r\n"
				+ "from email e inner join email_consegnate ec on e.id_messaggio = ec.msgid "
				+ "where e.id_pratica_evento = ? and ec.oggetto_risposta like 'CONSEGNA: %'";
		
		Query queryObj = this.em.createNativeQuery(query);
		queryObj.setParameter(1, idPraticaEvento);
		return (Long) queryObj.getSingleResult();
	}

}

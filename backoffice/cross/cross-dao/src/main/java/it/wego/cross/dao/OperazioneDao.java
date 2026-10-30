package it.wego.cross.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.wego.cross.entity.Operazione;
import it.wego.cross.utils.Log;

@Repository
public class OperazioneDao {

	@PersistenceContext
	private EntityManager em;

	public Operazione readByCode(String opCode) {
		Log.SQL.info("CHIAMATO OperazioneDao.readByCode ");
		Query query = em.createNamedQuery("Operazione.findByOpCode");
		query.setParameter("codiceOperazione", opCode);
		List<Operazione> resultList = query.getResultList();
		return resultList.isEmpty() ? null : resultList.get(0);
	}
	
	public void insert(String opCode, String code, String description) {
		Log.SQL.info("CHIAMATO OperazioneDao.insert ");
		Operazione operation = new Operazione(opCode, code, description);
		em.persist(operation);
	}
}

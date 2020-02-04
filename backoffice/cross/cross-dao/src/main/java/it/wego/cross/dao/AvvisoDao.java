package it.wego.cross.dao;

import it.wego.cross.entity.Avviso;
import it.wego.cross.entity.Utente;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class AvvisoDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(Avviso avviso) throws Exception {
		Log.SQL.info("CHIAMATO AvvisoDao.insert ");
		em.persist(avviso);
		em.flush();

	}

	public void update(Avviso avviso) throws Exception {
		Log.SQL.info("CHIAMATO AvvisoDao.update ");
		em.merge(avviso);
		em.flush();
	}

	public void delete(Avviso avviso) throws Exception {
		Log.SQL.info("CHIAMATO AvvisoDao.delete");
		Avviso avvisoDaRimuovere = em.getReference(Avviso.class, avviso.getIdAvviso());
	    em.remove(avvisoDaRimuovere);
	}
	
	public Avviso findbyIdAvvisi(int idAvviso) throws Exception {
        Query query = em.createNamedQuery("Avviso.findByIdAvvisi");
        Log.SQL.info("CHIAMATO AvvisoDao.findbyIdAvvisi");
        query.setParameter("idAvviso", idAvviso);
        Avviso avviso = (Avviso) Utils.getSingleResult(query);
        return avviso;
    }
	
	public List<Avviso> findAll () throws Exception {
		Log.SQL.info("CHIAMATO AvvisiDao.findAll");
		Query query = em.createNamedQuery("Avviso.findAll");
		List<Avviso> avvisiList = query.getResultList();
		return avvisiList;
	}

	public List<Avviso> findAvvisiNonScaduti(Date dataAttuale) throws Exception  {
		Log.SQL.info("CHIAMATO AvvisoDao.findAvvisiNonScaduti");
		Query query = em.createNamedQuery("Avviso.findNonScaduti");
		query.setParameter("scadenza", dataAttuale);
		List<Avviso> avvisiList = query.getResultList();
		return avvisiList;
	}

}

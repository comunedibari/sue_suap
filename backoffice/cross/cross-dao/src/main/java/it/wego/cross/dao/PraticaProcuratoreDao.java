package it.wego.cross.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.PraticaProcuratore;
import it.wego.cross.utils.Utils;

@Repository
public class PraticaProcuratoreDao {

    @PersistenceContext
    private EntityManager em;

    public PraticaProcuratoreDao(EntityManager em) {
    	this.em = em;
    }
    
    public PraticaProcuratoreDao() {}
    
    public PraticaProcuratore getActiveTechnicianAgent(Integer idPratica) {
    	Query query = em.createNamedQuery("PraticaProcuratore.findActiveTechnicianAgent");
		query.setParameter("idPratica", idPratica);
		return (PraticaProcuratore) Utils.getSingleResult(query);
    }
    
    public void insert(PraticaProcuratore praticaProcuratore) {
    	em.persist(praticaProcuratore);
    }
    
    public void update(PraticaProcuratore praticaProcuratore) {
    	em.merge(praticaProcuratore);
    }
}

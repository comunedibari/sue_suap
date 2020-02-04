/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.wego.cross.entity.RisultatoCaricamentoPratiche;

/**
 *
 * @author giovanna
 */
//@Configurable
@Repository
public class RisultatoCaricamentoPraticheDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Object object) throws Exception {
        em.persist(object);
    }


    public void update(Object object) throws Exception {
        em.merge(object);
    }

    public void flush() throws Exception {
        em.flush();
    }

    public void delete(Object object) throws Exception {
        em.remove(object);
    }

    public List<RisultatoCaricamentoPratiche> findAll() {
        Query query = em.createQuery("SELECT e "
                + "FROM RisultatoCaricamentoPratiche e "
                + "ORDER BY e.idRisultatoCaricamento ASC");
        List<RisultatoCaricamentoPratiche> RisultatoCaricamentoPratiche = query.getResultList();
        return RisultatoCaricamentoPratiche;
    }

    
 

    
    public Long countAllRisultatoCaricamentoPratiche() {
        Query query = em.createQuery("SELECT COUNT(e) "
                + "FROM RisultatoCaricamentoPratiche e ");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    

    public RisultatoCaricamentoPratiche findByIdRisultatoCaricamentoPratiche(Integer id) {
        Query query = em.createNamedQuery("RisultatoCaricamentoPratiche.findByIdRisultatoCaricamento");
        query.setParameter("idRisultatoCaricamento", id);
        List<RisultatoCaricamentoPratiche> risultato = query.getResultList();
        if (risultato.size() > 0) {
            return risultato.get(0);
        } else {
            return null;
        }
    }
    
    public RisultatoCaricamentoPratiche findRisultatoCaricamentoPraticheByIdentificativoPratica(String identificativoPratiche) {
    	Query query = em.createNamedQuery("RisultatoCaricamentoPratiche.findByIdRisultatoCaricamento");
        query.setParameter("identifcativoPratica", identificativoPratiche);
        List<RisultatoCaricamentoPratiche> risultato = query.getResultList();
        if (risultato.size() > 0) {
            return risultato.get(0);
        } else {
            return null;
        }
    }


	

}

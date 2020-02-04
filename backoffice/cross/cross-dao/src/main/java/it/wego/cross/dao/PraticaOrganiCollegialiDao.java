/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.PraticaOrganiCollegiali;
import it.wego.cross.utils.Log;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author giuseppe
 */
//@Configurable
@Repository
public class PraticaOrganiCollegialiDao {


    @PersistenceContext
    private EntityManager em;

    public void insert(PraticaOrganiCollegiali praticaOrganiCollegiali) {
        Log.SQL.info("Inserisco la relazione tra pratica e organi collegiali");
        em.persist(praticaOrganiCollegiali);
    }  
    
    public PraticaOrganiCollegiali findById(Integer idPraticaOrganiCollegiali) {
        Query query = em.createNamedQuery("PraticaOrganiCollegiali.findByIdPraticaOrganiCollegiali");
        query.setParameter("idPraticaOrganiCollegiali", idPraticaOrganiCollegiali);
        List<PraticaOrganiCollegiali> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

}

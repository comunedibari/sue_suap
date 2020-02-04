/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.Staging;
import it.wego.cross.exception.CrossException;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
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
public class StagingDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Object object) throws Exception {
        em.persist(object);
    }

    public void merge(Object object) throws CrossException {
        em.merge(object);
    }

    public Staging findByCodStaging(Integer idStaging) {
        Query query = em.createNamedQuery("Staging.findByIdStaging");
        query.setParameter("idStaging", idStaging);

        Staging staging = (Staging) Utils.getSingleResult(query);
        return staging;
    }

    public List<Staging> findAll() {
        Query query = em.createNamedQuery("Staging.findAll");
        List<Staging> stagings = query.getResultList();
        return stagings;
    }
    
    public List<Integer> findForConversion() {
        Query query = em.createQuery("select c.idStaging from Staging c");
        List result = query.getResultList();
        List<Integer> record = new ArrayList<Integer>();
        for (Object o : result) {
            record.add((Integer) o);
        }
        return record;
    }
}

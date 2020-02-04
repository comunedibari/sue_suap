/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author giuseppe
 */
//@Configurable
@Repository
public class UsefulDao {

    @PersistenceContext
    private EntityManager em;

    public void flush() throws Exception {
        em.flush();
    }

    public void update(Object entity) {
        em.merge(entity);
    }
    
    public void refresh(Object entity) {
        em.refresh(entity);
    }
}

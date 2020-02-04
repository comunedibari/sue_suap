/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.DizionarioErrori;
import it.wego.cross.entity.Errori;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author giuseppe
 */
//@Configurable
@Repository
public class ErroriDao {

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

    public List<Errori> findAll() {
        Query query = em.createQuery("SELECT e "
                + "FROM Errori e "
                + "ORDER BY e.data DESC");
        List<Errori> errori = query.getResultList();
        return errori;
    }

    public List<Errori> findAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Errori> q = cb.createQuery(Errori.class);
        Root<Errori> c = q.from(Errori.class);
        q.select(c);
        
        ParameterExpression<Character> statoparam = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        String soloAttivi = filter.getTipoFiltro();
        if ("T".equals(soloAttivi)) {
            statoparam = cb.parameter(Character.class);
            Expression<String> path = c.get("status");
            predicates.add(cb.equal(path, statoparam));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Errori> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        if ("T".equals(soloAttivi)) {
            query.setParameter(statoparam, 'A');
        }
        List<Errori> errori = query.getResultList();
        return errori;
    }

//    public List<Errori> findAllActive() {
//        Query query = em.createQuery("SELECT e "
//                + "FROM Errori e "
//                + "WHERE e.status = 'A'"
//                + "ORDER BY e.data DESC");
//        List<Errori> errori = query.getResultList();
//        return errori;
//    }
    public DizionarioErrori findByCodErrore(String codErrore) {
        Query query = em.createNamedQuery("DizionarioErrori.findByCodErrore");
        query.setParameter("codErrore", codErrore);
        DizionarioErrori errore = (DizionarioErrori) Utils.getSingleResult(query);
        return errore;
    }

    public Long countErrori(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Errori> c = q.from(Errori.class);
        q.select(cb.count(c));
        ParameterExpression<Character> statoparam = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        String soloAttivi = filter.getTipoFiltro();
        if ("T".equals(soloAttivi)) {
            statoparam = cb.parameter(Character.class);
            Expression<String> path = c.get("status");
            predicates.add(cb.equal(path, statoparam));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(q);
        if ("T".equals(soloAttivi)) {
            query.setParameter(statoparam, 'A');
        }
        Long count = query.getSingleResult();
        return count;
    }

    public Long countAllErrori() {
        Query query = em.createQuery("SELECT COUNT(e) "
                + "FROM Errori e ");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long countAllErroriAttivi() {
        Query query = em.createQuery("SELECT COUNT(e) "
                + "FROM Errori e " + "WHERE e.status = 'A'");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Errori findByIdErrore(Integer id) {
        Query query = em.createNamedQuery("Errori.findByIdErrore");
        query.setParameter("idErrore", id);
        List<Errori> errore = query.getResultList();
        if (errore.size() > 0) {
            return errore.get(0);
        } else {
            return null;
        }
    }

}

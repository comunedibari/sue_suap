/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import com.google.common.base.Strings;
import it.wego.cross.dto.filters.GestioneDatiEstesiFilter;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.exception.CrossException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author giuseppe
 */
//@Configurable
@Repository
public class DefDatiEstesiDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Object object) throws CrossException {
        em.persist(object);
    }

    public void merge(Object object) throws CrossException {
        em.merge(object);
    }

    public void delete(Object object) throws CrossException {
        em.remove(object);
    }

    public DefDatiEstesi findById(Integer idDatiEstesi) {
        Query query = em.createNamedQuery("DefDatiEstesi.findByIdDatiEstesi");
        query.setParameter("idDatiEstesi", idDatiEstesi);
        List<DefDatiEstesi> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<DefDatiEstesi> findByTipoOggetto(LkTipoOggetto tipoOggetto) {
        Query query = em.createQuery("SELECT d "
                + "FROM DefDatiEstesi d "
                + "WHERE d.idTipoOggetto = :idTipoOggetto");
        query.setParameter("idTipoOggetto", tipoOggetto);
        return query.getResultList();
    }

    public List<DefDatiEstesi> findByTipoOggettoIstanza(LkTipoOggetto tipoOggetto, String idIstanza) {
        Query query = em.createQuery("SELECT d "
                + "FROM DefDatiEstesi d "
                + "WHERE d.idTipoOggetto = :idTipoOggetto AND d.idIstanza = :idIstanza");
        query.setParameter("idTipoOggetto", tipoOggetto);
        query.setParameter("idIstanza", idIstanza);
        return query.getResultList();
    }

    public DefDatiEstesi findByTipoOggettoIstanzaCodice(LkTipoOggetto tipoOggetto, String idIstanza, String codValue) {
        Query query = em.createQuery("SELECT d "
                + "FROM DefDatiEstesi d "
                + "WHERE d.idTipoOggetto = :idTipoOggetto "
                + "AND d.idIstanza = :idIstanza "
                + "AND d.codValue = :codValue");
        query.setParameter("idTipoOggetto", tipoOggetto);
        query.setParameter("idIstanza", idIstanza);
        query.setParameter("codValue", codValue);
        List<DefDatiEstesi> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<DefDatiEstesi> findAll() {
        Query query = em.createNamedQuery("DefDatiEstesi.findAll");
        List<DefDatiEstesi> objects = query.getResultList();
        return objects;
    }

    public Long countAllDefDatiEstesi(GestioneDatiEstesiFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<DefDatiEstesi> c = q.from(DefDatiEstesi.class);
        q.select(cb.countDistinct(c));
        Predicate p = null;
        if (!Strings.isNullOrEmpty(filter.getCodTipoOggetto())) {
            p = cb.equal(c.get("idTipoOggetto").get("codTipoOggetto"), filter.getCodTipoOggetto());
        }
        if (!Strings.isNullOrEmpty(filter.getIdIstanza())) {
            Expression<String> path = c.get("idIstanza");
            if (p != null) {
                p = cb.and(p, cb.like(cb.upper(path), "%"+filter.getIdIstanza().toUpperCase()+"%"));
            } else {
                p = cb.like(cb.upper(path), "%"+filter.getIdIstanza().toUpperCase()+"%");
            }
        }
        if (!Strings.isNullOrEmpty(filter.getCodValue())) {
            Expression<String> path = c.get("codValue");
            if (p != null) {
                p = cb.and(p, cb.like(cb.upper(path), "%"+filter.getCodValue().toUpperCase()+"%"));
            } else {
                p = cb.like(cb.upper(path), "%"+filter.getCodValue().toUpperCase()+"%");
            }
        }
        if (p != null) {
            q.where(p);
        }

        TypedQuery<Long> query = em.createQuery(q);
//        if (filter.getLimit() != null && filter.getOffset() != null) {
//            query.setMaxResults(filter.getLimit());
//            query.setFirstResult(filter.getOffset());
//        }
        return query.getSingleResult();
    }

    public List<DefDatiEstesi> findAllDefDatiEstesi(GestioneDatiEstesiFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DefDatiEstesi> q = cb.createQuery(DefDatiEstesi.class);
        Root<DefDatiEstesi> c = q.from(DefDatiEstesi.class);
        q.select(c);
        Predicate p = null;
        if (!Strings.isNullOrEmpty(filter.getCodTipoOggetto())) {
            p = cb.equal(c.get("idTipoOggetto").get("codTipoOggetto"), filter.getCodTipoOggetto());
        }
        if (!Strings.isNullOrEmpty(filter.getIdIstanza())) {
            Expression<String> path = c.get("idIstanza");
            if (p != null) {
                p = cb.and(p, cb.like(cb.upper(path), "%"+filter.getIdIstanza().toUpperCase()+"%"));
            } else {
                p = cb.like(cb.upper(path), "%"+filter.getIdIstanza().toUpperCase()+"%");
            }
        }
        if (!Strings.isNullOrEmpty(filter.getCodValue())) {
            Expression<String> path = c.get("codValue");
            if (p != null) {
                p = cb.and(p, cb.like(cb.upper(path), "%"+filter.getCodValue().toUpperCase()+"%"));
            } else {
                p = cb.like(cb.upper(path), "%"+filter.getCodValue().toUpperCase()+"%");
            }
        }
        if (p != null) {
            q.where(p);
        }

        TypedQuery<DefDatiEstesi> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        return query.getResultList();

    }
}

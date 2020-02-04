/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.OcPraticaCommissione;
import it.wego.cross.entity.OcPraticaCommissionePK;
import it.wego.cross.entity.OcSedutePratiche;
import it.wego.cross.entity.OrganiCollegiali;
import it.wego.cross.entity.OrganiCollegialiCommissione;
import it.wego.cross.entity.OrganiCollegialiSedute;
import it.wego.cross.entity.OrganiCollegialiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaOrganiCollegiali;
import it.wego.cross.utils.Log;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class OrganiCollegialiDao {

////////    String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
////////    Log.SQL.info ("Query: " + sql);
    
    @PersistenceContext
    private EntityManager em;

    public void insertOrganoCollegiale(OrganiCollegiali organiCollegiali) throws Exception {
        em.persist(organiCollegiali);
    }

    public void mergeOrganoCollegiale(OrganiCollegiali organiCollegiali) throws Exception {
        em.merge(organiCollegiali);
    }

    public void deleteOrganoCollegiale(OrganiCollegiali organiCollegiali) throws Exception {
        em.remove(organiCollegiali);
    }

    public OrganiCollegiali findById(Integer idOrganiCollegali) {
        Query query = em.createNamedQuery("OrganiCollegiali.findByIdOrganiCollegiali");
        query.setParameter("idOrganiCollegiali", idOrganiCollegali);
        List<OrganiCollegiali> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<OrganiCollegiali> findAll(Integer idEnte) {
        Query query = em.createNamedQuery("OrganiCollegiali.findAll");
        List<OrganiCollegiali> organiCollegiali = query.getResultList();
        return organiCollegiali;

    }

    public List<OrganiCollegiali> findAllOrdered(Integer idEnte) {
        Query query = em.createQuery("SELECT o FROM OrganiCollegiali o "
                + "JOIN o.idEnte e "
                + "WHERE e.idEnte = :idEnte "
                + "ORDER BY o.desOrganoCollegiale, e.descrizione");
        query.setParameter("idEnte", idEnte);
        List<OrganiCollegiali> organiCollegiali = query.getResultList();
        return organiCollegiali;
    }

    public Long countOrganiCollegiali(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OrganiCollegiali> c = q.from(OrganiCollegiali.class);
        q.select(cb.count(c));
        if (filter.getListaEnti() != null && !filter.getListaEnti().isEmpty()) {
            q.where(c.get("idEnte").get("idEnte").in(filter.getListaEnti()));
        }
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<OrganiCollegiali> findAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrganiCollegiali> q = cb.createQuery(OrganiCollegiali.class);
        Root<OrganiCollegiali> c = q.from(OrganiCollegiali.class);
        q.select(c);
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        if (filter.getListaEnti() != null && !filter.getListaEnti().isEmpty()) {
            q.where(c.get("idEnte").get("idEnte").in(filter.getListaEnti()));
        }
        TypedQuery<OrganiCollegiali> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<OrganiCollegiali> result = query.getResultList();
        return result;
    }

    public Long countOrganiCollegialiSedute(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OrganiCollegialiSedute> c = q.from(OrganiCollegialiSedute.class);
        q.select(cb.count(c));
        if (filter.getListaEnti() != null && !filter.getListaEnti().isEmpty()) {
            q.where(c.get("idOrganoCollegiale").get("idEnte").get("idEnte").in(filter.getListaEnti()));
        }
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<OrganiCollegialiSedute> findAllSeduteFiltered(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrganiCollegialiSedute> q = cb.createQuery(OrganiCollegialiSedute.class);
        Root<OrganiCollegialiSedute> c = q.from(OrganiCollegialiSedute.class);
        q.select(c);
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idOrganoCollegiale").get(filter.getOrderColumn())));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idOrganoCollegiale").get("idEnte").get("descrizione")));
            } else if ("dataConclusioneGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("dataConclusione")));
            } else if ("dataInizioGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("dataInizio")));
            } else if ("dataConvocazioneGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("dataConvocazione")));
            } else if ("desStatoSeduta".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idStatoSeduta").get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else {
            if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idOrganoCollegiale").get(filter.getOrderColumn())));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idOrganoCollegiale").get("idEnte").get("descrizione")));
            } else if ("dataConclusioneGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("dataConclusione")));
            } else if ("dataInizioGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("dataInizio")));
            } else if ("dataConvocazioneGrid".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("dataConvocazione")));
            } else if ("desStatoSeduta".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idStatoSeduta").get("descrizione")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        }
        if (filter.getListaEnti() != null && !filter.getListaEnti().isEmpty()) {
            q.where(c.get("idOrganoCollegiale").get("idEnte").get("idEnte").in(filter.getListaEnti()));
        }
        TypedQuery<OrganiCollegialiSedute> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<OrganiCollegialiSedute> result = query.getResultList();
        return result;
    }

    public void insertOrganoCollegialeTemplate(OrganiCollegialiTemplate organiCollegialiTemplate) throws Exception {
        em.persist(organiCollegialiTemplate);
    }

    public void mergeOrganoCollegialeTemplate(OrganiCollegialiTemplate organiCollegialiTemplate) throws Exception {
        em.merge(organiCollegialiTemplate);
    }

    public OrganiCollegialiTemplate findByIdTemplate(Integer idOrganiCollegaliTemplate) {
        Query query = em.createNamedQuery("OrganiCollegialiTemplate.findByIdOrganiCollegialiTemplate");
        query.setParameter("idOrganiCollegialiTemplate", idOrganiCollegaliTemplate);
        List<OrganiCollegialiTemplate> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<OrganiCollegialiTemplate> findAllTemplate() {
        Query query = em.createNamedQuery("OrganiCollegialiTemplate.findAll");
        List<OrganiCollegialiTemplate> resultList = query.getResultList();
        return resultList;

    }

    public List<OrganiCollegialiTemplate> findAllTemplateOrdered() {
        Query query = em.createQuery("SELECT o FROM OrganiCollegialiTemplate o JOIN o.idOrganiCollegiali oc ORDER BY oc.desOrganiCollegiali");
        List<OrganiCollegialiTemplate> resultList = query.getResultList();
        return resultList;
    }

// sedute
    public void insertOrganoCollegialeSedute(OrganiCollegialiSedute organiCollegialiSedute) throws Exception {
        em.persist(organiCollegialiSedute);
    }

    public void mergeOrganoCollegialeSedute(OrganiCollegialiSedute organiCollegialiSedute) throws Exception {
        em.merge(organiCollegialiSedute);
    }

    public OrganiCollegialiSedute findByIdSeduta(Integer idSeduta) {
        Query query = em.createNamedQuery("OrganiCollegialiSedute.findByIdSeduta");
        query.setParameter("idSeduta", idSeduta);
        List<OrganiCollegialiSedute> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Long countOrganiCollegialiSeduteCommissioneBase(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSeduta").get("idSeduta"), filter.getIdSeduta());
        p = cb.and(p, cb.isNull(c.get("ocSedutePratiche").get("idPraticaOrganiCollegiali")));
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<OcPraticaCommissione> findAllSedutePraticheCommissioneBaseFiltered(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OcPraticaCommissione> q = cb.createQuery(OcPraticaCommissione.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(c);
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSeduta").get("idSeduta"), filter.getIdSeduta());
        p = cb.and(p, cb.isNull(c.get("ocSedutePratiche").get("idPraticaOrganiCollegiali")));
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get("cognome")));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get("nome")));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else {
            if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get("cognome")));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get("nome")));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        }

        q.where(p);
        TypedQuery<OcPraticaCommissione> query = em.createQuery(q);

        if (filter.getLimit()
                != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<OcPraticaCommissione> result = query.getResultList();
        return result;
    }

    public Long countOrganiCollegialiSedutePratiche(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSeduta").get("idSeduta"), filter.getIdSeduta());

        p = cb.and(p, cb.equal(c.get("ocSedutePratiche").get("idSedutaPratica"), filter.getIdSedutaPratica()));

        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<OcPraticaCommissione> findAllSedutePraticheFiltered(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OcPraticaCommissione> q = cb.createQuery(OcPraticaCommissione.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(c);
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSeduta").get("idSeduta"), filter.getIdSeduta());

        p = cb.and(p, cb.equal(c.get("ocSedutePratiche").get("idSedutaPratica"), filter.getIdSedutaPratica()));

        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get("cognome")));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get("nome")));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else {
            if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get("cognome")));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get("nome")));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        }

        q.where(p);
        TypedQuery<OcPraticaCommissione> query = em.createQuery(q);

        if (filter.getLimit()
                != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<OcPraticaCommissione> result = query.getResultList();
        return result;
    }

    public OcPraticaCommissione findByIdSedutaPraticheAnagrafica(Integer idSedutaPratica, Integer idAnagrafica) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OcPraticaCommissione> q = cb.createQuery(OcPraticaCommissione.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(c);
        OcPraticaCommissionePK opk = new OcPraticaCommissionePK(idSedutaPratica, idAnagrafica);
        q.where(cb.equal(c.get("ocPraticaCommissionePK"), opk));
        TypedQuery<OcPraticaCommissione> query = em.createQuery(q);
        List<OcPraticaCommissione> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Long countOrganiCollegialiSedutePraticheAnagrafiche(Integer idSedutaPratica) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSedutaPratica"), idSedutaPratica);
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public Long countOrganiCollegialiCommissione(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OrganiCollegialiCommissione> c = q.from(OrganiCollegialiCommissione.class);

        q.select(cb.count(c));

        q.where(cb.equal(c.get("organiCollegiali").get("idOrganiCollegiali"), filter.getIdOrganiCollegiali()));

        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<OrganiCollegialiCommissione> findAllOrganiCollegialiFiltered(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrganiCollegialiCommissione> q = cb.createQuery(OrganiCollegialiCommissione.class);
        Root<OrganiCollegialiCommissione> c = q.from(OrganiCollegialiCommissione.class);

        q.select(c);

        if (filter.getOrderDirection()
                != null && filter.getOrderDirection().equals("asc")) {
            if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("organiCollegiali").get(filter.getOrderColumn())));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("organiCollegiali").get("idEnte").get("descrizione")));
            } else if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get(filter.getOrderColumn())));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("anagrafica").get(filter.getOrderColumn())));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else {
            if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("organiCollegiali").get(filter.getOrderColumn())));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("organiCollegiali").get("idEnte").get("descrizione")));
            } else if ("cognome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get(filter.getOrderColumn())));
            } else if ("nome".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("anagrafica").get(filter.getOrderColumn())));
            } else if ("desRuoloCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idRuoloCommissione").get("descrizione")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        }

        q.where(cb.equal(c.get("organiCollegiali").get("idOrganiCollegiali"), filter.getIdOrganiCollegiali()));

        TypedQuery<OrganiCollegialiCommissione> query = em.createQuery(q);

        if (filter.getLimit()
                != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<OrganiCollegialiCommissione> result = query.getResultList();
        return result;
    }

    public List<OrganiCollegialiCommissione> findAllOrganiCollegiali(Integer idOrganoCollegiale) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrganiCollegialiCommissione> q = cb.createQuery(OrganiCollegialiCommissione.class
        );
        Root<OrganiCollegialiCommissione> c = q.from(OrganiCollegialiCommissione.class);

        q.select(c);

        q.where(cb.equal(c.get("organiCollegiali").get("idOrganiCollegiali"), idOrganoCollegiale));
        TypedQuery<OrganiCollegialiCommissione> query = em.createQuery(q);
        List<OrganiCollegialiCommissione> result = query.getResultList();
        return result;
    }

    public OrganiCollegialiCommissione findByIdOrganoIdAnagrafica(Integer idOrganiCollegiali, Integer idAnagrafica) {
        Query query = em.createQuery("SELECT o FROM OrganiCollegialiCommissione o WHERE o.organiCollegialiCommissionePK.idOrganiCollegiali = :idOrganiCollegiali AND o.organiCollegialiCommissionePK.idAnagrafica = :idAnagrafica");
        query.setParameter("idOrganiCollegiali", idOrganiCollegiali);
        query.setParameter("idAnagrafica", idAnagrafica);
        List<OrganiCollegialiCommissione> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public void mergeOrganoCollegialeCommissione(OrganiCollegialiCommissione organiCollegialiCommissione) throws Exception {
        em.merge(organiCollegialiCommissione);
    }

    public void deleteOrganoCollegialeCommissione(OrganiCollegialiCommissione organiCollegialiCommissione) throws Exception {
        em.remove(organiCollegialiCommissione);
    }

    public void insertOrganoCollegialeCommissione(OrganiCollegialiCommissione organiCollegialiCommissione) throws Exception {
        em.persist(organiCollegialiCommissione);
    }

    // sedute commissione
    public void insertOrganoCollegialeSedutePratiche(OcSedutePratiche ocSedutePratiche) throws Exception {
        em.persist(ocSedutePratiche);
    }

    public void deleteOrganoCollegialeSedute(OrganiCollegialiSedute organiCollegialiSedute) throws Exception {
        em.remove(organiCollegialiSedute);
    }

    public void deleteOrganoCollegialeSedutePratiche(OcPraticaCommissione ocPraticaCommissione) throws Exception {
        em.remove(ocPraticaCommissione);
    }

    public void mergeOrganoCollegialeSedutePratiche(OcPraticaCommissione ocPraticaCommissione) throws Exception {
        em.merge(ocPraticaCommissione);
    }

    public void mergeOrganoCollegialeSedutePratiche(OcSedutePratiche ocSedutePratiche) throws Exception {
        em.merge(ocSedutePratiche);
    }

    public void insertOrganoCollegialeSedutePratiche(OcPraticaCommissione ocPraticaCommissione) throws Exception {
        em.persist(ocPraticaCommissione);
    }

    public Long countOrganiCollegialiSeduteCommissionePratiche(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<PraticaOrganiCollegiali> c = q.from(PraticaOrganiCollegiali.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("idOrganiCollegiali").get("idOrganiCollegiali"), filter.getIdOrganiCollegiali());
        p = cb.and(p, cb.isNull(c.get("idSeduta")));
        p = cb.or(p, cb.equal(c.get("idSeduta").get("idSeduta"), filter.getIdSeduta()));

        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<PraticaOrganiCollegiali> findOrganiCollegialiSeduteCommissionePratiche(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PraticaOrganiCollegiali> q = cb.createQuery(PraticaOrganiCollegiali.class);
        Root<PraticaOrganiCollegiali> c = q.from(PraticaOrganiCollegiali.class);
        q.select(c);
        Predicate p = cb.equal(c.get("idOrganiCollegiali").get("idOrganiCollegiali"), filter.getIdOrganiCollegiali());
        p = cb.and(p, cb.isNull(c.get("idSeduta")));
        p = cb.or(p, cb.equal(c.get("idSeduta").get("idSeduta"), filter.getIdSeduta()));

        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            if ("protocollo".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idPratica").get("protocollo")));
            } else if ("oggetto".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idPratica").get("oggettoPratica")));
            } else if ("dataRicezione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idPratica").get("dataRicezione")));
            } else if ("dataRichiestaCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("dataRichiesta")));
            } else if ("sequenza".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("ocSedutePraticheList").get("sequenza")));
            } else if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idOrganiCollegiali").get("desOrganoCollegiale")));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.asc(c.get("idOrganiCollegiali").get("idEnte").get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else {
            if ("protocollo".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idPratica").get("protocollo")));
            } else if ("oggetto".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idPratica").get("oggettoPratica")));
            } else if ("dataRicezione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idPratica").get("dataRicezione")));
            } else if ("dataRichiestaCommissione".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("dataRichiesta")));
            } else if ("sequenza".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("ocSedutePraticheList").get("sequenza")));
            } else if ("desOrganoCollegiale".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idOrganiCollegiali").get("desOrganoCollegiale")));
            } else if ("desEnte".equals(filter.getOrderColumn())) {
                q.orderBy(cb.desc(c.get("idOrganiCollegiali").get("idEnte").get("descrizione")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        }

        q.where(p);
        TypedQuery<PraticaOrganiCollegiali> query = em.createQuery(q);
        List<PraticaOrganiCollegiali> resultList = query.getResultList();
        return resultList;
    }

    public void deleteOrganoCollegialeSedutePratiche(OcSedutePratiche ocSedutePratiche) throws Exception {
        em.remove(ocSedutePratiche);
    }

    public void deleteOrganoCollegialeTemplate(OrganiCollegialiTemplate organiCollegialiTemplate) throws Exception {
        em.remove(organiCollegialiTemplate);
    }

    public OcSedutePratiche findByIdSedutaPratica(Integer idSedutaPratica) {
        Query query = em.createNamedQuery("OcSedutePratiche.findByIdSedutaPratica");
        query.setParameter("idSedutaPratica", idSedutaPratica);
        List<OcSedutePratiche> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public OcPraticaCommissione findOrganiCollegialiSeduteCommissioneAnagraficaBase(Integer idSeduta, Integer idAnagrafica) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OcPraticaCommissione> q = cb.createQuery(OcPraticaCommissione.class);
        Root<OcPraticaCommissione> c = q.from(OcPraticaCommissione.class);
        q.select(c);
        Predicate p = cb.equal(c.get("ocSedutePratiche").get("idSeduta").get("idSeduta"), idSeduta);
        p = cb.and(p, cb.isNull(c.get("ocSedutePratiche").get("idPraticaOrganiCollegiali")));
        p = cb.and(p, cb.equal(c.get("anagrafica").get("idAnagrafica"), idAnagrafica));
        q.where(p);
        TypedQuery<OcPraticaCommissione> query = em.createQuery(q);
        List<OcPraticaCommissione> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public OcSedutePratiche findByIdSedutaIdPraticaOC(Integer idSeduta, Integer idPraticaOrganiCollegiali) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OcSedutePratiche> q = cb.createQuery(OcSedutePratiche.class);
        Root<OcSedutePratiche> c = q.from(OcSedutePratiche.class);

        q.select(c);
        Predicate p = cb.equal(c.get("idSeduta").get("idSeduta"), idSeduta);
        if (idPraticaOrganiCollegiali == null) {
            p = cb.and(p, cb.isNull(c.get("idPraticaOrganiCollegiali")));
        } else {
            p = cb.and(p, cb.equal(c.get("idPraticaOrganiCollegiali").get("idPraticaOrganiCollegiali"), idPraticaOrganiCollegiali));
        }
        q.where(p);
        TypedQuery<OcSedutePratiche> query = em.createQuery(q);

        List<OcSedutePratiche> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Long countUseOrganoCollegiale(Integer idOrganiCollegiali) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<PraticaOrganiCollegiali> c = q.from(PraticaOrganiCollegiali.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("idOrganiCollegiali").get("idOrganiCollegiali"), idOrganiCollegiali);
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }
}

package it.wego.cross.dao;

import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.entity.UtenteRuoloProcedimento;
import it.wego.cross.entity.UtenteRuoloProcedimentoPK;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class AuthorizationDao {

    @PersistenceContext
    private EntityManager em;

    public void update(Object object) throws Exception {
        em.merge(object);
    }

    public void delete(Object objetc) throws Exception {
        em.remove(objetc);
    }
    
    public UtenteRuoloProcedimento referenceUtenteRuoloProcedimentoPK(UtenteRuoloProcedimento utenteRuoloProcedimento) {
        return em.getReference(UtenteRuoloProcedimento.class, utenteRuoloProcedimento.getUtenteRuoloProcedimentoPK());
    }

    public UtenteRuoloEnte getRuoloByKey(int idUtente, int idEnte, String codPermesso) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UtenteRuoloEnte> q = cb.createQuery(UtenteRuoloEnte.class);
        Root<UtenteRuoloEnte> c = q.from(UtenteRuoloEnte.class);
        q.select(c);
        Predicate p = cb.equal(c.get("idEnte").get("idEnte"), idEnte);
        p = cb.and(p, cb.equal(c.get("idUtente").get("idUtente"), idUtente));
        p = cb.and(p, cb.equal(c.get("codPermesso").get("codPermesso"), codPermesso));
        q.where(p);
        TypedQuery<UtenteRuoloEnte> query = em.createQuery(q);
        List<UtenteRuoloEnte> resultList = query.getResultList();
        String s = (String) query.unwrap(org.eclipse.persistence.jpa.JpaQuery.class).getDatabaseQuery().getSQLStrings().get(0);
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<UtenteRuoloEnte> getUtenteRuoloEnte(int idUtente, int idProc, int idEnte) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UtenteRuoloEnte> q = cb.createQuery(UtenteRuoloEnte.class);
        Root<UtenteRuoloEnte> c = q.from(UtenteRuoloEnte.class);
        q.select(c).distinct(true);
        Predicate p = cb.equal(c.get("procedimentiEntiList").get("idProc").get("idProc"), idProc);
        p = cb.and(p, cb.equal(c.get("procedimentiEntiList").get("idEnte").get("idEnte"), idEnte));
        p = cb.and(p, cb.equal(c.get("idUtente").get("idUtente"), idUtente));
        q.where(p);
        TypedQuery<UtenteRuoloEnte> query = em.createQuery(q);
        List<UtenteRuoloEnte> resultList = query.getResultList();
        String s = (String) query.unwrap(org.eclipse.persistence.jpa.JpaQuery.class).getDatabaseQuery().getSQLStrings().get(0);
        return resultList;

    }

    public Long countAbilitazioniSuRegistroImprese(Integer idUtente, String keyConfiguration, String valueConfiguration) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Utente> c = q.from(Utente.class);
        Join joinRuoli = c.join("utenteRuoloEnteList");
        Join joinEnti = joinRuoli.join("idEnte");
        Join joinConfiguration = joinEnti.join("configurationList");
        q.select(cb.countDistinct(joinEnti.get("idEnte")));
        Predicate p = cb.and(cb.equal(c.get("idUtente"), idUtente), cb.equal(cb.upper(joinConfiguration.get("value")), valueConfiguration.toUpperCase()), cb.equal(joinConfiguration.get("name"), keyConfiguration));
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        String s = (String) query.unwrap(org.eclipse.persistence.jpa.JpaQuery.class).getDatabaseQuery().getSQLStrings().get(0);
        return count;
    }

    public Long countAbilitazioniSuEnti(Integer idUtente) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Utente> c = q.from(Utente.class);
        Join joinRuoli = c.join("utenteRuoloEnteList");
        Join joinEnti = joinRuoli.join("idEnte");
        q.select(cb.countDistinct(joinEnti.get("idEnte")));
        Predicate p = cb.and(cb.equal(c.get("idUtente"), idUtente));
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        String s = (String) query.unwrap(org.eclipse.persistence.jpa.JpaQuery.class).getDatabaseQuery().getSQLStrings().get(0);
        return count;
    }

    public UtenteRuoloEnte getUtenteRuoloEnte(Integer idUtenteRuoloEnte) {
        Query query = em.createNamedQuery("UtenteRuoloEnte.findByIdUtenteRuoloEnte");
        query.setParameter("idUtenteRuoloEnte", idUtenteRuoloEnte);
        List<UtenteRuoloEnte> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public UtenteRuoloProcedimento getUtenteRuoloProcedimento(Integer idUtenteRuoloEnte, Integer idProcedimentoEnte) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UtenteRuoloProcedimento> q = cb.createQuery(UtenteRuoloProcedimento.class);
        Root<UtenteRuoloProcedimento> c = q.from(UtenteRuoloProcedimento.class);
        q.select(c);
        Predicate p = cb.equal(c.get("utenteRuoloProcedimentoPK").get("idUtenteRuoloEnte"), idUtenteRuoloEnte);
        p = cb.and(p, cb.equal(c.get("utenteRuoloProcedimentoPK").get("idProcEnte"), idProcedimentoEnte));
        q.where(p);
        TypedQuery<UtenteRuoloProcedimento> query = em.createQuery(q);
        List<UtenteRuoloProcedimento> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}

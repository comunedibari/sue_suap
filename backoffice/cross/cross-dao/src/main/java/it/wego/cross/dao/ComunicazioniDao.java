/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Comunicazione;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
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
public class ComunicazioniDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Comunicazione comunicazione) {
        Log.SQL.info("Inserisco la comunicazione");
        em.persist(comunicazione);
    }

    public Long countComunicazioniAttiveByUtente(Filter filter) {
        Log.SQL.info("Conto le comunicazioni attive per l'utente " + filter.getConnectedUser().getUsername());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Comunicazione> c = q.from(Comunicazione.class);
        ParameterExpression<Utente> utente = cb.parameter(Utente.class);
        ParameterExpression<String> status = cb.parameter(String.class);
        q.select(cb.count(c));
        Utente utenteConnesso = filter.getConnectedUser();
        List<UtenteRuoloEnte> ruoli = utenteConnesso.getUtenteRuoloEnteList();
        List<Enti> enti = new ArrayList<Enti>();
        Predicate visibileAUtente = null;
        if (ruoli != null && !ruoli.isEmpty()) {
            List<Predicate> ruoliPredicates = new ArrayList<Predicate>();
            for (UtenteRuoloEnte ruolo : ruoli) {
                if (!enti.contains(ruolo.getIdEnte().getIdEnte())) {
                    Predicate p = cb.and(cb.equal(c.get("idEnte"), ruolo.getIdEnte().getIdEnte()), cb.isNull(c.get("idPratica")));
                    ruoliPredicates.add(p);
                    enti.add(ruolo.getIdEnte());
                }
            }
            visibileAUtente = cb.or(ruoliPredicates.toArray(new Predicate[ruoliPredicates.size()]));
        }
        Predicate nonLetta = cb.equal(c.get("status"), status);
        q.where(cb.and(visibileAUtente, nonLetta));

        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(utente, utenteConnesso);
        query.setParameter(status, it.wego.cross.constants.Comunicazione.NON_LETTA);
        Long count = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Conto comunicazioni attive per utente (QUERY): " + sql);
        return count;
    }

    public List<Comunicazione> findComunicazioniAttiveByUtente(Filter filter) {
        Log.SQL.info("Ricerco le comunicazioni attive per l'utente " + filter.getConnectedUser().getUsername());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comunicazione> q = cb.createQuery(Comunicazione.class);
        Root<Comunicazione> c = q.from(Comunicazione.class);
        ParameterExpression<Utente> utente = cb.parameter(Utente.class);
        ParameterExpression<String> status = cb.parameter(String.class);
        Utente utenteConnesso = filter.getConnectedUser();
        List<UtenteRuoloEnte> ruoli = utenteConnesso.getUtenteRuoloEnteList();
        List<Enti> enti = new ArrayList<Enti>();
        Predicate visibileAUtente = null;
        if (ruoli != null && !ruoli.isEmpty()) {
            List<Predicate> ruoliPredicates = new ArrayList<Predicate>();
            for (UtenteRuoloEnte ruolo : ruoli) {
                if (!enti.contains(ruolo.getIdEnte().getIdEnte())) {
                    Predicate p = cb.equal(c.get("idEnte"), ruolo.getIdEnte().getIdEnte());
                    ruoliPredicates.add(p);
                    enti.add(ruolo.getIdEnte());
                }
            }
            visibileAUtente = cb.and(cb.or(ruoliPredicates.toArray(new Predicate[ruoliPredicates.size()])), cb.isNull(c.get("idPratica")));
        }
        Predicate nonLetta = cb.equal(c.get("status"), status);
//        q.where(cb.or(cb.and(inCaricoAUtente, nonLetta), cb.and(visibileAUtente, nonLetta)));
        q.where(cb.and(visibileAUtente, nonLetta));
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            Log.SQL.info("Ordino per " + filter.getOrderColumn() + " (" + filter.getOrderDirection() + ")");
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            Log.SQL.info("Ordino per data_comunicazione (DESC)");
            q.orderBy(cb.desc(c.get("dataComunicazione")));
        }

        TypedQuery<Comunicazione> query = em.createQuery(q);
        query.setParameter(utente, utenteConnesso);
        query.setParameter(status, it.wego.cross.constants.Comunicazione.NON_LETTA);
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        List<Comunicazione> comunicazioni = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Ricerca comunicazioni attive per utente (QUERY): " + sql);
        return comunicazioni;
    }

    public Comunicazione findByIdComunicazione(Integer idComunicazione) {
        Query query = em.createNamedQuery("Comunicazione.findByIdComunicazione");
        query.setParameter("idComunicazione", idComunicazione);
        Comunicazione comunicazione = (Comunicazione) Utils.getSingleResult(query);
        return comunicazione;
    }
}

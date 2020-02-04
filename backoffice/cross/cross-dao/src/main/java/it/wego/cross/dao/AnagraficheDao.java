package it.wego.cross.dao;

import it.wego.cross.constants.Constants;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Recapiti;
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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.stereotype.Repository;
/*
 * 
 * #author CS
 */

//@Configurable
@Repository
public class AnagraficheDao extends Paginator {

    @PersistenceContext
    private EntityManager em;

    public void insert(Anagrafica anagrafica) {
        em.persist(anagrafica);
    }

    public void update(Anagrafica anagrafica) {
        em.merge(anagrafica);
    }

    public void insert(Recapiti recapiti) {
        em.persist(recapiti);
    }

    public void update(Recapiti recapiti) {
        em.merge(recapiti);
    }

    public void remove(AnagraficaRecapiti recapiti) {
        em.remove(recapiti.getIdRecapito());
        em.remove(recapiti);
        em.flush();
    }

    public void insert(AnagraficaRecapiti anagraficaRecapiti) {
        em.persist(anagraficaRecapiti);
    }

    public void update(AnagraficaRecapiti anagraficaRecapiti) {
        em.merge(anagraficaRecapiti);
    }

    public Anagrafica findById(Integer id) {
        Query query = em.createNamedQuery("Anagrafica.findByIdAnagrafica");
        query.setParameter("idAnagrafica", id);
        Anagrafica anagrafica = (Anagrafica) Utils.getSingleResult(query);
        return anagrafica;
    }

    public Anagrafica findByCodiceFiscale(String codiceFiscale) {
        //^^CS MODIFICA cambio logica ora tutte le anagrafiche vengono cercate per CF
        //Query query = em.createQuery("SELECT a FROM Anagrafica a WHERE a.codiceFiscale = :codiceFiscale AND (a.tipoAnagrafica = 'F' OR a.tipoAnagrafica = 'P')");
        Query query = em.createQuery("SELECT a FROM Anagrafica a WHERE a.codiceFiscale = :codiceFiscale");
        query.setParameter("codiceFiscale", codiceFiscale);
        List<Anagrafica> anagrafiche = query.getResultList();
        Anagrafica anagrafica = null;
        if (anagrafiche.size() > 0) {
            anagrafica = anagrafiche.get(0);
        }
        return anagrafica;
    }

    public Anagrafica findByCodiceFiscaleNotId(String codiceFiscale, Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Anagrafica> q = cb.createQuery(Anagrafica.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(c);
        Predicate p = cb.equal(c.get("codiceFiscale"), codiceFiscale);
        p = cb.and(p, cb.notEqual(c.get("idAnagrafica"), id));
        q.where(p);
        TypedQuery<Anagrafica> query = em.createQuery(q);
        Anagrafica anag = (Anagrafica) Utils.getSingleResult(query);
        return anag;
    }

    public Anagrafica findByPartitaIvaNotId(String partitaIva, Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Anagrafica> q = cb.createQuery(Anagrafica.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(c);
        Predicate p = cb.equal(c.get("partitaIva"), partitaIva);
        p = cb.and(p, cb.notEqual(c.get("idAnagrafica"), id));
        q.where(p);
        TypedQuery<Anagrafica> query = em.createQuery(q);
        Anagrafica anag = (Anagrafica) Utils.getSingleResult(query);
        return anag;
    }

    public Anagrafica findByPartitaIva(String partitaIva) {
        Query query = em.createNamedQuery("Anagrafica.findByPartitaIva");
        query.setParameter("partitaIva", partitaIva);
        List<Anagrafica> anagrafiche = query.getResultList();
        Anagrafica anagrafica = null;
        if (anagrafiche.size() > 0) {
            anagrafica = anagrafiche.get(0);
        }
        return anagrafica;
    }

    public Anagrafica reference(Integer id) {
        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setIdAnagrafica(id);
        return em.getReference(Anagrafica.class, anagrafica);
    }

    public List<Anagrafica> findByCognome(String queryString) {
        Query query = em.createQuery("SELECT a "
                + "FROM Anagrafica a "
                + "WHERE a.cognome LIKE :cognome");
        query.setParameter("cognome", "%" + queryString + "%");
        List<Anagrafica> anagrafiche = query.getResultList();
        return anagrafiche;
    }

    public Recapiti findRecapitoById(Integer idRecapito) {
        Query query = em.createNamedQuery("Recapiti.findByIdRecapito");
        query.setParameter("idRecapito", idRecapito);

        Recapiti recapito = (Recapiti) Utils.getSingleResult(query);
        return recapito;
    }

    public AnagraficaRecapiti findAnagraficaRecapitoById(Integer idAnagraficaRecapito) {
        Query query = em.createNamedQuery("AnagraficaRecapiti.findByIdAnagraficaRecapito");
        query.setParameter("idAnagraficaRecapito", idAnagraficaRecapito);

        AnagraficaRecapiti anagraficaRecapito = (AnagraficaRecapiti) Utils.getSingleResult(query);
        return anagraficaRecapito;
    }

    public Long countAnagrafiche(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(cb.count(c));
        ParameterExpression<String> nomeParameter = null;
        ParameterExpression<String> cognomeParameter = null;
        ParameterExpression<String> denominazioneParameter = null;
        ParameterExpression<String> codiceFiscaleParameter = null;
        ParameterExpression<String> partitaIvaParameter = null;
        ParameterExpression<String> tipoAnagraficaParameter = null;
        ParameterExpression<String> varianteAnagrafica = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getNome() != null) {
            nomeParameter = cb.parameter(String.class);
            Expression<String> path = c.get("nome");
            predicates.add(cb.like(path, nomeParameter));
        }
        if (filter.getCognome() != null) {
            cognomeParameter = cb.parameter(String.class);
            Expression<String> path = c.get("cognome");
            predicates.add(cb.like(path, cognomeParameter));
        }
        if (filter.getDenominazione() != null) {
            denominazioneParameter = cb.parameter(String.class);
            Expression<String> path = c.get("denominazione");
            predicates.add(cb.like(path, denominazioneParameter));
        }
        if (filter.getCodiceFiscale() != null) {
            codiceFiscaleParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("codiceFiscale"), codiceFiscaleParameter));
        }
        if (filter.getPartitaIva() != null) {
            partitaIvaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("partitaIva"), partitaIvaParameter));
        }
        if (filter.getTipoAnagrafica() != null && !filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            tipoAnagraficaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("tipoAnagrafica"), tipoAnagraficaParameter));
        }
        if (filter.getTipoAnagrafica() != null && filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            varianteAnagrafica = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("varianteAnagrafica"), varianteAnagrafica));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getNome() != null) {
            String like = "%" + filter.getNome() + "%";
            query.setParameter(nomeParameter, like);
        }
        if (filter.getCognome() != null) {
            String like = "%" + filter.getCognome() + "%";
            query.setParameter(cognomeParameter, like);
        }
        if (filter.getDenominazione() != null) {
            String like = "%" + filter.getDenominazione() + "%";
            query.setParameter(denominazioneParameter, like);
        }
        if (filter.getCodiceFiscale() != null) {
            query.setParameter(codiceFiscaleParameter, filter.getCodiceFiscale());
        }
        if (filter.getPartitaIva() != null) {
            query.setParameter(partitaIvaParameter, filter.getPartitaIva());
        }
        if (filter.getTipoAnagrafica() != null && !filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            query.setParameter(tipoAnagraficaParameter, filter.getTipoAnagrafica());
        }
        if (filter.getTipoAnagrafica() != null && filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            query.setParameter(varianteAnagrafica, Constants.PERSONA_DITTAINDIVIDUALE);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public List<Anagrafica> findAnagrafiche(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Anagrafica> q = cb.createQuery(Anagrafica.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(c);
        ParameterExpression<String> nomeParameter = null;
        ParameterExpression<String> cognomeParameter = null;
        ParameterExpression<String> denominazioneParameter = null;
        ParameterExpression<String> codiceFiscaleParameter = null;
        ParameterExpression<String> partitaIvaParameter = null;
        ParameterExpression<String> tipoAnagraficaParameter = null;
        ParameterExpression<String> varianteAnagrafica = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getNome() != null) {
            nomeParameter = cb.parameter(String.class);
            Expression<String> path = c.get("nome");
            predicates.add(cb.like(path, nomeParameter));
        }
        if (filter.getCognome() != null) {
            cognomeParameter = cb.parameter(String.class);
            Expression<String> path = c.get("cognome");
            predicates.add(cb.like(path, cognomeParameter));
        }
        if (filter.getDenominazione() != null) {
            denominazioneParameter = cb.parameter(String.class);
            Expression<String> path = c.get("denominazione");
            predicates.add(cb.like(path, denominazioneParameter));
        }
        if (filter.getCodiceFiscale() != null) {
            codiceFiscaleParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("codiceFiscale"), codiceFiscaleParameter));
        }
        if (filter.getPartitaIva() != null) {
            partitaIvaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("partitaIva"), partitaIvaParameter));
        }
        if (filter.getTipoAnagrafica() != null && !filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            tipoAnagraficaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("tipoAnagrafica"), tipoAnagraficaParameter));
        }
        if (filter.getTipoAnagrafica() != null && filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            varianteAnagrafica = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("varianteAnagrafica"), varianteAnagrafica));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }

        TypedQuery<Anagrafica> query = em.createQuery(q);
        if (filter.getNome() != null) {
            String like = "%" + filter.getNome() + "%";
            query.setParameter(nomeParameter, like);
        }
        if (filter.getCognome() != null) {
            String like = "%" + filter.getCognome() + "%";
            query.setParameter(cognomeParameter, like);
        }
        if (filter.getDenominazione() != null) {
            String like = "%" + filter.getDenominazione() + "%";
            query.setParameter(denominazioneParameter, like);
        }
        if (filter.getCodiceFiscale() != null) {
            query.setParameter(codiceFiscaleParameter, filter.getCodiceFiscale());
        }
        if (filter.getPartitaIva() != null) {
            query.setParameter(partitaIvaParameter, filter.getPartitaIva());
        }
        if (filter.getTipoAnagrafica() != null && !filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            query.setParameter(tipoAnagraficaParameter, filter.getTipoAnagrafica());
        }
        if (filter.getTipoAnagrafica() != null && filter.getTipoAnagrafica().equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
            query.setParameter(varianteAnagrafica, Constants.PERSONA_DITTAINDIVIDUALE);
        }
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }

        List<Anagrafica> anagrafiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return anagrafiche;
    }

    public Recapiti findRecapitoNotifica(Anagrafica anagraficaDB) {
        //select distinct recapiti.* from 
        //pratica_anagrafica join recapiti as recapiti 
        //on (pratica_anagrafica.id_recapito_notifica=recapiti.id_recapito) 
        //ORDER BY pratica_anagrafica.id_recapito_notifica DESC LIMIT 1
        Query query = em.createQuery("SELECT pa.idRecapitoNotifica from PraticaAnagrafica pa WHERE pa.anagrafica = :anagrafica  ORDER BY pa.idRecapitoNotifica.idRecapito DESC");
        List<Recapiti> recapiti = query.getResultList();
        Recapiti recapito = new Recapiti();
        query.setMaxResults(1);
        query.setParameter("anagrafica", anagraficaDB);
        if (recapiti != null && !recapiti.isEmpty()) {
            recapito = recapiti.get(0);
        }
        return recapito;
    }

    public List<Anagrafica> findByDescrizioneAndPratica(String description, Pratica pratica) {
        Query query = em.createQuery("SELECT DISTINCT(a) "
                + "FROM Anagrafica a "
                + "JOIN a.praticaAnagraficaList pa "
                + "WHERE "
                + "(a.cognome LIKE UPPER(:cognome) "
                + "OR a.nome LIKE UPPER(:nome) "
                + "OR a.denominazione LIKE UPPER(:denominazione)) "
                + "AND pa.pratica = :pratica");
        query.setParameter("cognome", "%" + description.toUpperCase() + "%");
        query.setParameter("nome", "%" + description.toUpperCase() + "%");
        query.setParameter("denominazione", "%" + description.toUpperCase() + "%");
        query.setParameter("pratica", pratica);
        List<Anagrafica> anagrafiche = query.getResultList();
        return anagrafiche;
    }

    public LkTipoIndirizzo findTipoIndirizzoByAnagraficaRecapito(Anagrafica anagrafica, Recapiti recapito) {
        Query query = em.createQuery("SELECT a "
                + "FROM AnagraficaRecapiti a "
                + "WHERE a.idRecapito = :idRecapito "
                + "AND a.idAnagrafica = :idAnagrafica");
        query.setParameter("idAnagrafica", anagrafica);
        query.setParameter("idRecapito", recapito);

        AnagraficaRecapiti anagraficaRecapito = (AnagraficaRecapiti) Utils.getSingleResult(query);
        if (anagraficaRecapito != null) {
            return anagraficaRecapito.getIdTipoIndirizzo();
        } else {
            return null;
        }
    }

    public List<Anagrafica> searchAnagraficheLike(String ricerca) {
        String ricercaLike = "%" + ricerca + "%";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Anagrafica> q = cb.createQuery(Anagrafica.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(c).distinct(true);
        Join recapitiJoin = c.join("anagraficaRecapitiList", JoinType.LEFT).join("idRecapito");
        Expression<String> nome = c.get("cognome");
        Expression<String> cognome = c.get("nome");
        Expression<String> denominazione = c.get("denominazione");
        Expression<String> email = recapitiJoin.get("email");
        Expression<String> pec = recapitiJoin.get("pec");
        Predicate p = cb.or(
                cb.like(cb.lower(cognome), ricercaLike.toLowerCase()),
                cb.like(cb.lower(nome), ricercaLike.toLowerCase()),
                cb.like(cb.lower(denominazione), ricercaLike.toLowerCase()),
                cb.like(cb.lower(email), ricercaLike.toLowerCase()),
                cb.like(cb.lower(pec), ricercaLike.toLowerCase()));
        q.where(p);
        TypedQuery<Anagrafica> query = em.createQuery(q);
        List<Anagrafica> anagrafiche = query.getResultList();
        return anagrafiche;
    }

    public List<Anagrafica> findAllFisiche() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Anagrafica> q = cb.createQuery(Anagrafica.class);
        Root<Anagrafica> c = q.from(Anagrafica.class);
        q.select(c).distinct(true);
        List<Order> orderList = new ArrayList();
        Predicate p = cb.equal(c.get("tipoAnagrafica"), "F");
        q.where(p);

        orderList.add(cb.desc(c.get("cognome")));
        orderList.add(cb.desc(c.get("nome")));

        q.orderBy(orderList);

        TypedQuery<Anagrafica> query = em.createQuery(q);
        List<Anagrafica> anagrafiche = query.getResultList();
        return anagrafiche;
    }

}

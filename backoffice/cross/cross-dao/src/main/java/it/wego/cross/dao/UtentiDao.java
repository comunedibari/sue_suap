package it.wego.cross.dao;

import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.UtenteRuoloEnte;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
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

//@Configurable
@Repository
public class UtentiDao extends Paginator {

    @PersistenceContext
    private EntityManager em;

    public void update(Utente utente) throws Exception {
        em.merge(utente);
    }

    public void delete(Utente utente) throws Exception {
        em.remove(utente);
    }

    public Utente findUtenteByIdUtente(int idUtente) {
        Query query = em.createNamedQuery("Utente.findByIdUtente");
        query.setParameter("idUtente", idUtente);
        Utente utente = (Utente) Utils.getSingleResult(query);
        return utente;
    }

    public Utente findUtenteByCodiceFiscale(String codiceFiscale) {
        Query query = em.createNamedQuery("Utente.findByCodiceFiscale");
        query.setParameter("codiceFiscale", codiceFiscale);
        Utente utente = (Utente) Utils.getSingleResult(query);
        return utente;
    }

    public Utente findUtenteByUsername(String username) {
        Query query = em.createNamedQuery("Utente.findByUsername");
        query.setParameter("username", username);
        Utente utente = (Utente) Utils.getSingleResult(query);
        return utente;
    }

    public List<Utente> findAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
        Root<Utente> c = q.from(Utente.class);
        q.select(c);
        ParameterExpression<String> userStatusParam = null;
        ParameterExpression<String> nomeParam = null;
        ParameterExpression<String> cognomeParam = null;
        ParameterExpression<String> superuserParam = null;
        ParameterExpression<String> codicefiscaleParam = null;
        ParameterExpression<String> usernameParam = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if ((filter.getUserStatus() != null) && (!"".equals(filter.getUserStatus()))) {
            userStatusParam = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("status"), userStatusParam));
        }
        if ((filter.getNome() != null) && (!"".equals(filter.getNome()))) {
            nomeParam = cb.parameter(String.class);
            Expression nomeExp = c.get("nome");
            predicates.add(cb.like(cb.upper(nomeExp), nomeParam));
        }
        if ((filter.getCognome() != null) && (!"".equals(filter.getCognome()))) {
            cognomeParam = cb.parameter(String.class);
            Expression cognomeExp = c.get("cognome");
            predicates.add(cb.like(cb.upper(cognomeExp), cognomeParam));
        }
        if ((filter.getCodiceFiscale() != null) && (!"".equals(filter.getCodiceFiscale()))) {
            codicefiscaleParam = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("codiceFiscale"), codicefiscaleParam));
        }
        if ((filter.getUsername() != null) && (!"".equals(filter.getUsername()))) {
            usernameParam = cb.parameter(String.class);
            Expression usernameExp = c.get("username");
            predicates.add(cb.like(cb.upper(usernameExp), usernameParam));
        }
        if ((filter.getSuperuser() != null) && (!"".equals(filter.getSuperuser()))) {
            superuserParam = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("superuser"), superuserParam));
        }
        if (filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Utente> query = em.createQuery(q);
        if ((filter.getUserStatus() != null) && (filter.getUserStatus() != "")) {
            query.setParameter(userStatusParam, filter.getUserStatus());
        }
        if ((filter.getNome() != null) && (!"".equals(filter.getNome()))) {
            query.setParameter(nomeParam, "%" + filter.getNome().toUpperCase() + "%");
        }
        if ((filter.getCognome() != null) && (!"".equals(filter.getCognome()))) {
            query.setParameter(cognomeParam, "%" + filter.getCognome().toUpperCase() + "%");
        }
        if ((filter.getSuperuser() != null) && (!"".equals(filter.getSuperuser()))) {
            query.setParameter(superuserParam, filter.getSuperuser());
        }
        if ((filter.getCodiceFiscale() != null) && (!"".equals(filter.getCodiceFiscale()))) {
            query.setParameter(codicefiscaleParam, filter.getCodiceFiscale());
        }
        if ((filter.getUsername() != null) && (!"".equals(filter.getUsername()))) {
            query.setParameter(usernameParam, "%" + filter.getUsername().toUpperCase() + "%");
        }
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<Utente> utenti = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return utenti;
    }

    public Long countAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Utente> c = q.from(Utente.class);
        q.select(cb.count(c));
        ParameterExpression<String> userStatus = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getUserStatus() != null) {
            userStatus = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("status"), userStatus));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getUserStatus() != null) {
            query.setParameter(userStatus, filter.getUserStatus());
        }
        Long count = query.getSingleResult();
        return count;
    }

    public Long countUtenti() {
        Query query = em.createQuery("SELECT COUNT(u) FROM Utente u");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long countUtentiAbilitati(Pratica pratica) {
        Query query = em.createQuery("SELECT COUNT(ure.idUtente) "
                + "FROM Pratica p "
                + "JOIN p.idProcEnte proc "
                + "JOIN proc.utenteRuoloProcedimentoList urp "
                + "JOIN urp.utenteRuoloEnte ure "
                + "WHERE p = :pratica ");
        query.setParameter("pratica", pratica);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public List<Utente> findUtentiAbilitati(Pratica pratica, Filter filter) {
        String queryString = "SELECT DISTINCT ure.idUtente "
                + "FROM Pratica p "
                + "JOIN p.idProcEnte proc "
                + "JOIN proc.utenteRuoloProcedimentoList urp "
                + "JOIN urp.utenteRuoloEnte ure "
                + "WHERE p = :pratica ";
        if (filter.getOrderColumn() != null && filter.getOrderDirection() != null) {
            queryString = queryString + "ORDER BY ure.idUtente." + filter.getOrderColumn() + " " + filter.getOrderDirection();
        }
        Query query = em.createQuery(queryString);
        query.setParameter("pratica", pratica);
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }

        List<Utente> utenti = query.getResultList();
        return utenti;
    }

    public List<Utente> findUtentiAbilitatiAdmin(Pratica pratica, Filter filter) {
        String queryString = "SELECT DISTINCT ure.idUtente "
                + "FROM Pratica p "
                + "JOIN p.idProcEnte proc "
                + "JOIN proc.utenteRuoloProcedimentoList urp "
                + "JOIN urp.utenteRuoloEnte ure "
                + "WHERE p = :pratica and ure.codPermesso.codPermesso = :adminRole";
        if (filter.getOrderColumn() != null && filter.getOrderDirection() != null) {
            queryString = queryString + "ORDER BY ure.idUtente." + filter.getOrderColumn() + " " + filter.getOrderDirection();
        }
        Query query = em.createQuery(queryString);
        query.setParameter("pratica", pratica);
        query.setParameter("adminRole", PermissionConstans.AMMINISTRATORE);
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }

        List<Utente> utenti = query.getResultList();
        return utenti;
    }

    public List<Utente> findByUsernameNomeCognome(String searchString) {
        String queryString = "SELECT u "
                + "FROM Utente u "
                + "WHERE u.nome LIKE :nome "
                + "OR u.cognome LIKE :cognome "
                + "OR u.username = :username";
        Query query = em.createQuery(queryString);
        query.setParameter("nome", "%" + searchString + "%");
        query.setParameter("cognome", "%" + searchString + "%");
        query.setParameter("username", "%" + searchString + "%");
        List<Utente> utenti = query.getResultList();
        return utenti;
    }

    public List<Utente> findAllSystemSuperusers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> q = cb.createQuery(Utente.class);
        Root<Utente> c = q.from(Utente.class);
        q.select(c);
        Predicate utenteAttivo = cb.equal(c.get("status"), "ATTIVO");
        Predicate isSuperuser = cb.equal(c.get("superuser"), 'S');
        q.where(cb.and(utenteAttivo, isSuperuser));
        TypedQuery<Utente> query = em.createQuery(q);
        List<Utente> utenti = query.getResultList();
        return utenti;
    }

    public List<String> findAllSystemSuperusersUsername() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> q = cb.createQuery(String.class);
        Root<Utente> c = q.from(Utente.class);
        q.multiselect(c.get("username"));
        Predicate utenteAttivo = cb.equal(c.get("status"), "ATTIVO");
        Predicate isSuperuser = cb.equal(c.get("superuser"), 'S');
        q.where(cb.and(utenteAttivo, isSuperuser));
        TypedQuery<String> query = em.createQuery(q);
        List<String> utenti = query.getResultList();
        return utenti;
    }

    public List<UtenteRuoloEnte> findRuoliEnti(Integer idUtente) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UtenteRuoloEnte> q = cb.createQuery(UtenteRuoloEnte.class);
        Root<UtenteRuoloEnte> c = q.from(UtenteRuoloEnte.class);
        q.select(c);
        Predicate p = cb.equal(c.get("idUtente").get("idUtente"), idUtente);
        q.where(p);
        TypedQuery<UtenteRuoloEnte> query = em.createQuery(q);
        return query.getResultList();
    }
    
    public List<Utente> findUtentiByEnte(Enti idEnte) {
        String queryString = "SELECT DISTINCT u.idUtente "
                + "FROM UtenteRuoloEnte u "
                + "WHERE u.idEnte = :idEnte ";
        Query query = em.createQuery(queryString);
        query.setParameter("idEnte", idEnte);
    	List<Utente> utenti = query.getResultList();
		return utenti;
    }
}

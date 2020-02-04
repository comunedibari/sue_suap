package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.ProcedimentiEnti;
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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class EntiDao extends Paginator {

    @PersistenceContext
    private EntityManager em;

    public void insert(Enti ente) throws Exception {
        em.persist(ente);
    }

    public void update(Enti ente) throws Exception {
        em.merge(ente);
    }

    public void delete(Enti ente) throws Exception {
        em.remove(ente);
    }

    public List<Enti> findAll() {
        Query query = em.createNamedQuery("Enti.findAll");
        List<Enti> enti = query.getResultList();
        return enti;
    }

    public List<Enti> findAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Enti> q = cb.createQuery(Enti.class);
        Root<Enti> c = q.from(Enti.class);
        q.select(c);
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> codiceFiscale = null;
        ParameterExpression<String> partitaIva = null;
        ParameterExpression<String> descrizione = null;

        if (!Utils.e(filter.getCodiceFiscale())) {
            codiceFiscale = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("codiceFiscale"), codiceFiscale));
        }
        if (!Utils.e(filter.getPartitaIva())) {
            partitaIva = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("partitaIva"), partitaIva));
        }
        if (!Utils.e(filter.getDenominazione())) {
            descrizione = cb.parameter(String.class);
            Expression<String> desc = c.get("descrizione");
            predicates.add(cb.like(desc, descrizione));
        }
        if (filter.getListaEnti() != null && !filter.getListaEnti().isEmpty()) {
            predicates.add(c.get("idEnte").in(filter.getListaEnti()));
        }
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else if (filter.getOrderDirection() != null && filter.getOrderDirection().equalsIgnoreCase("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.asc(c.get("descrizione")));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Enti> query = em.createQuery(q);

        if (!Utils.e(filter.getCodiceFiscale())) {
            query.setParameter(codiceFiscale, filter.getCodiceFiscale());
        }
        if (!Utils.e(filter.getPartitaIva())) {
            query.setParameter(partitaIva, filter.getPartitaIva());
        }
        if (!Utils.e(filter.getDenominazione())) {
            query.setParameter(descrizione, "%" + filter.getDenominazione().trim() + "%");
        }

        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }

        List<Enti> enti = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return enti;
    }

    public Long countAll(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Enti> c = q.from(Enti.class);
        q.select(cb.count(c));
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> codiceFiscale = null;
        ParameterExpression<String> partitaIva = null;
        ParameterExpression<String> descrizione = null;
        if (!Utils.e(filter.getCodiceFiscale())) {
            codiceFiscale = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("codiceFiscale"), codiceFiscale));
        }
        if (!Utils.e(filter.getPartitaIva())) {
            partitaIva = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("partitaIva"), partitaIva));
        }
        if (!Utils.e(filter.getDenominazione())) {
            descrizione = cb.parameter(String.class);
            Expression<String> desc = c.get("descrizione");
            predicates.add(cb.like(desc, descrizione));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(q);
        if (!Utils.e(filter.getCodiceFiscale())) {
            query.setParameter(codiceFiscale, filter.getCodiceFiscale());
        }
        if (!Utils.e(filter.getPartitaIva())) {
            query.setParameter(partitaIva, filter.getPartitaIva());
        }
        if (!Utils.e(filter.getDenominazione())) {
            query.setParameter(descrizione, "%" + filter.getDenominazione().trim() + "%");
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public List<LkComuni> findByIdEnte(int idEnte, String comune) {
        //^^CS ELIMINA Query query = em.createNamedQuery("Enti.findByIdEnte");
        Query query = em.createQuery("Select l FROM Enti e JOIN e.lkComuniList l WHERE l.descrizione LIKE :comune AND e.idEnte = :idEnte");
        query.setParameter("idEnte", idEnte);
        query.setParameter("comune", "%" + comune.toUpperCase() + "%");
        List<LkComuni> comuni = (List<LkComuni>) query.getResultList();
        return comuni;
    }

    public Enti findByIdEnte(int idEnte) {
        Query query = em.createNamedQuery("Enti.findByIdEnte");
        query.setParameter("idEnte", idEnte);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;

    }

    public Enti findByCodiceCatastale(String codiceCatastale) {
        Query query = em.createNamedQuery("Enti.findByCodiceCatastale");
        query.setParameter("codiceCatastale", codiceCatastale);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;
    }

    public Enti findByUnitaOrganizzativa(String unitaOrganizzativa) {
        Query query = em.createNamedQuery("Enti.findByUnitaOrganizzativa");
        query.setParameter("unitaOrganizzativa", unitaOrganizzativa);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;
    }

    //^^CS AGGIUNTA
    public Enti findByCodiceFiscale(String cf) {
        Query query = em.createQuery("SELECT e "
                + " FROM Enti e "
                + " WHERE "
                + " e.codiceFiscale = :codiceFiscale ");
        query.setParameter("codiceFiscale", cf);
        Enti ente = (Enti) query.getSingleResult();
        return ente;
    }

    public List<LkComuni> findComuniCollegati(Integer idEnte, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkComuni> q = cb.createQuery(LkComuni.class);
        Root<LkComuni> c = q.from(LkComuni.class);
        Join<LkComuni, Enti> join = c.join("entiList");
        q.select(c);
        ParameterExpression<Integer> idEnteParameter = cb.parameter(Integer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(join.get("idEnte"), idEnteParameter));
        if (filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        }

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<LkComuni> query = em.createQuery(q);
        query.setParameter(idEnteParameter, idEnte);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
            query.setMaxResults(filter.getLimit());
        }

        List<LkComuni> comuni = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();

        Log.SQL.info("Query: " + sql);
        return comuni;
    }

    public Long countComuniCollegati(Integer idEnte, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<LkComuni> c = q.from(LkComuni.class);
        Join<LkComuni, Enti> join = c.join("entiList");
        q.select(cb.count(c));
        ParameterExpression<Integer> idEnteParameter = cb.parameter(Integer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(join.get("idEnte"), idEnteParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(idEnteParameter, idEnte);
        Long count = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public Long countComuniFiltrati(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<LkComuni> c = q.from(LkComuni.class);
        q.select(cb.count(c));
        ParameterExpression<String> codiceCatastaleParameter = null;
        ParameterExpression<String> descrizioneParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (!Utils.e(filter.getCodiceCatastale())) {
            codiceCatastaleParameter = cb.parameter(String.class);
            Expression<String> path = c.get("codCatastale");
            predicates.add(cb.like(path, codiceCatastaleParameter));
        }
        if (!Utils.e(filter.getDescrizioneComune())) {
            descrizioneParameter = cb.parameter(String.class);
            Expression<String> path = c.get("descrizione");
            predicates.add(cb.like(path, descrizioneParameter));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (!Utils.e(filter.getCodiceCatastale())) {
            String like = "%" + filter.getCodiceCatastale() + "%";
            query.setParameter(codiceCatastaleParameter, like);
        }
        if (!Utils.e(filter.getDescrizioneComune())) {
            String like = "%" + filter.getDescrizioneComune() + "%";
            query.setParameter(descrizioneParameter, like);
        }
        Long count = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public List<LkComuni> findComuniFiltrati(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkComuni> q = cb.createQuery(LkComuni.class);
        Root<LkComuni> c = q.from(LkComuni.class);
        q.select(c);
        ParameterExpression<String> codiceCatastaleParameter = null;
        ParameterExpression<String> descrizioneParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (!Utils.e(filter.getCodiceCatastale())) {
            codiceCatastaleParameter = cb.parameter(String.class);
            Expression<String> path = c.get("codCatastale");
            predicates.add(cb.like(path, codiceCatastaleParameter));
        }
        if (!Utils.e(filter.getDescrizioneComune())) {
            descrizioneParameter = cb.parameter(String.class);
            Expression<String> path = c.get("descrizione");
            predicates.add(cb.like(path, descrizioneParameter));
        }
        if (filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<LkComuni> query = em.createQuery(q);
        if (!Utils.e(filter.getCodiceCatastale())) {
            String like = "%" + filter.getCodiceCatastale() + "%";
            query.setParameter(codiceCatastaleParameter, like);
        }
        if (!Utils.e(filter.getDescrizioneComune())) {
            String like = "%" + filter.getDescrizioneComune() + "%";
            query.setParameter(descrizioneParameter, like);
        }
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
            query.setMaxResults(filter.getLimit());
        }

        List<LkComuni> comuni = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();

        Log.SQL.info("Query: " + sql);
        return comuni;
    }

    public LkComuni findByIdComune(Integer idComune) {
        Query query = em.createNamedQuery("LkComuni.findByIdComune");
        query.setParameter("idComune", idComune);

        LkComuni comune = (LkComuni) Utils.getSingleResult(query);
        return comune;
    }

    public List<LkComuni> genericComuniSearch(String descrizione, String codCatastale) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkComuni l "
                + "WHERE UPPER(l.descrizione) LIKE :descrizione "
                + "AND UPPER(l.codCatastale) LIKE :codCatastale");
        query.setParameter("descrizione", descrizione.toUpperCase());
        query.setParameter("codCatastale", codCatastale.toUpperCase());

        List<LkComuni> comuni = query.getResultList();
        return comuni;
    }

    public void collegaComuneEnte(Integer idEnte, Integer idComune) throws Exception {
        Enti ente = findByIdEnte(idEnte);
        LkComuni comune = findByIdComune(idComune);
        List<LkComuni> comuniCollegati = ente.getLkComuniList();
        if (!comuniCollegati.contains(comune)) {
            if (ente.getLkComuniList() == null) {
                comuniCollegati = new ArrayList<LkComuni>();
            }
            comuniCollegati.add(comune);
            ente.setLkComuniList(comuniCollegati);
            update(ente);
        }
    }

    public void scollegaComuneEnte(Integer idEnte, Integer idComune) throws Exception {
        Enti ente = findByIdEnte(idEnte);
        LkComuni comune = findByIdComune(idComune);
        List<LkComuni> comuniCollegati = ente.getLkComuniList();
        if (comuniCollegati.contains(comune)) {
            comuniCollegati.remove(comune);
            ente.setLkComuniList(comuniCollegati);
            update(ente);
        }
    }

    public List<Enti> findByDescription(String queryString) {
        Query query = em.createQuery("SELECT e "
                + "FROM Enti e "
                + "WHERE e.descrizione LIKE :descrizione");
        query.setParameter("descrizione", "%" + queryString + "%");
        List<Enti> enti = query.getResultList();
        return enti;
    }

    public Enti findEnteByIDComune(Integer id) {
        Query query = em.createQuery("SELECT e "
                + " FROM Enti e JOIN e.lkComuniList c "
                + " WHERE c.idComune = :id");
        query.setParameter("id", id);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;
    }

    public Enti findByIdentificativoSuap(String identificativoSuap) {
        Query query = em.createNamedQuery("Enti.findByIdentificativoSuap");
        query.setParameter("identificativoSuap", identificativoSuap);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;
    }

    public Enti findByCodEnte(String codEnte) {
        Query query = em.createNamedQuery("Enti.findByCodEnte");
        query.setParameter("codEnte", codEnte);
        Enti ente = (Enti) Utils.getSingleResult(query);
        return ente;
    }

    public Long countProcedimenti(Enti ente) {
        Query query = em.createQuery("SELECT COUNT(p) "
                + "FROM ProcedimentiEnti p "
                + "WHERE p.idEnte = :ente");
        query.setParameter("ente", ente);
        Long count = (Long) Utils.getSingleResult(query);
        return count;
    }

    public List<Enti> findAllEntiByProcedimento(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<ProcedimentiEnti> c = q.from(ProcedimentiEnti.class);
        Join joinEnti = c.join("idEnte");
        q.select(c).distinct(true);
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> codiceFiscale = null;
        ParameterExpression<String> partitaIva = null;
        ParameterExpression<String> descrizione = null;
        ParameterExpression<Integer> procedimento = null;

        if (!Utils.e(filter.getCodiceFiscale())) {
            codiceFiscale = cb.parameter(String.class);
            predicates.add(cb.equal(joinEnti.get("codiceFiscale"), codiceFiscale));
        }
        if (!Utils.e(filter.getPartitaIva())) {
            partitaIva = cb.parameter(String.class);
            predicates.add(cb.equal(joinEnti.get("partitaIva"), codiceFiscale));
        }
        if (!Utils.e(filter.getDenominazione())) {
            descrizione = cb.parameter(String.class);
            Expression<String> desc = joinEnti.get("descrizione");
            predicates.add(cb.like(desc, descrizione));
        }
        if (!Utils.e(filter.getIdProcedimento())) {
            procedimento = cb.parameter(Integer.class);
            Expression<Integer> idProc = c.get("idProc").get("idProc");
            predicates.add(cb.equal(idProc, procedimento));
        }
        if (filter.getOrderColumn() != null && !filter.getOrderColumn().trim().equals("")) {
            if (filter.getOrderDirection() != null && filter.getOrderDirection().equalsIgnoreCase("desc")) {
                q.orderBy(cb.desc(joinEnti.get(filter.getOrderColumn())));
            } else if (filter.getOrderDirection() != null && filter.getOrderDirection().equalsIgnoreCase("asc")) {
                q.orderBy(cb.asc(joinEnti.get(filter.getOrderColumn())));
            }
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<ProcedimentiEnti> query = em.createQuery(q);

        if (!Utils.e(filter.getCodiceFiscale())) {
            query.setParameter(codiceFiscale, filter.getCodiceFiscale());
        }
        if (!Utils.e(filter.getPartitaIva())) {
            query.setParameter(partitaIva, filter.getPartitaIva());
        }
        if (!Utils.e(filter.getDenominazione())) {
            query.setParameter(descrizione, "%" + filter.getDenominazione().trim() + "%");
        }
        if (!Utils.e(filter.getIdProcedimento())) {
            query.setParameter(procedimento, filter.getIdProcedimento());
        }
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }

        List<Enti> enti = null;
        List<ProcedimentiEnti> lpe = query.getResultList();
        if (lpe != null) {
            enti = new ArrayList<Enti>();
            for (ProcedimentiEnti pe : lpe) {
                enti.add(pe.getIdEnte());
            }
        }
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return enti;
    }

    public Long countAllEntiByProcedimento(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<ProcedimentiEnti> c = q.from(ProcedimentiEnti.class);
        Join joinEnti = c.join("idEnte");
        q.select(cb.count(c));
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> codiceFiscale = null;
        ParameterExpression<String> partitaIva = null;
        ParameterExpression<String> descrizione = null;
        ParameterExpression<Integer> procedimento = null;

        if (!Utils.e(filter.getCodiceFiscale())) {
            codiceFiscale = cb.parameter(String.class);
            predicates.add(cb.equal(joinEnti.get("codiceFiscale"), codiceFiscale));
        }
        if (!Utils.e(filter.getPartitaIva())) {
            partitaIva = cb.parameter(String.class);
            predicates.add(cb.equal(joinEnti.get("partitaIva"), codiceFiscale));
        }
        if (!Utils.e(filter.getDenominazione())) {
            descrizione = cb.parameter(String.class);
            Expression<String> desc = joinEnti.get("descrizione");
            predicates.add(cb.like(desc, descrizione));
        }
        if (!Utils.e(filter.getIdProcedimento())) {
            procedimento = cb.parameter(Integer.class);
            Expression<Integer> idProc = c.get("idProc").get("idProc");
            predicates.add(cb.equal(idProc, procedimento));
        }

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(q);

        if (!Utils.e(filter.getCodiceFiscale())) {
            query.setParameter(codiceFiscale, filter.getCodiceFiscale());
        }
        if (!Utils.e(filter.getPartitaIva())) {
            query.setParameter(partitaIva, filter.getPartitaIva());
        }
        if (!Utils.e(filter.getDenominazione())) {
            query.setParameter(descrizione, "%" + filter.getDenominazione().trim() + "%");
        }
        if (!Utils.e(filter.getIdProcedimento())) {
            query.setParameter(procedimento, filter.getIdProcedimento());
        }

        Long count = (Long) query.getSingleResult();
        return count;
    }

    public List<Enti> searchEntiLike(String ricerca) {
        String ricercaLike = "%" + ricerca + "%";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Enti> q = cb.createQuery(Enti.class);
        Root<Enti> c = q.from(Enti.class);
        q.select(c).distinct(true);
        Expression<String> descrizione = c.get("descrizione");
        Expression<String> email = c.get("email");
        Expression<String> pec = c.get("pec");
        Predicate p = cb.or(
                cb.like(cb.lower(descrizione), ricercaLike.toLowerCase()),
                cb.like(cb.lower(email), ricercaLike.toLowerCase()),
                cb.like(cb.lower(pec), ricercaLike.toLowerCase()));
        q.where(p);
        TypedQuery<Enti> query = em.createQuery(q);
        List<Enti> enti = query.getResultList();
        return enti;
    }
}

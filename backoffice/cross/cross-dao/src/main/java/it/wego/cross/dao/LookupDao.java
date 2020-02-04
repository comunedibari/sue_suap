package it.wego.cross.dao;

import it.wego.cross.entity.*;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class LookupDao {

    @PersistenceContext
    private EntityManager em;

    @Nullable
    public LkComuni findComuneByCodCatastale(String codCatastale) {
        LkComuni comune = null;
        Query query = em.createQuery("SELECT l "
                + "FROM LkComuni l "
                + "WHERE l.codCatastale = :codCatastale "
                + "AND l.dataFineValidita = ("
                + "SELECT MAX (lk.dataFineValidita)"
                + "FROM LkComuni lk "
                + "WHERE lk.codCatastale = :codCatastale)"
                + "AND l.dataFineValidita > :now");

        query.setParameter("codCatastale", codCatastale);
        query.setParameter("now", new Date());
        try {
            comune = (LkComuni) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return comune;
    }

    public LkComuni findComuneById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkComuni l "
                + "WHERE l.idComune = :id");
        query.setParameter("id", id);
        LkComuni comune = (LkComuni) Utils.getSingleResult(query);
        return comune;
    }

    public LkStatoPratica findStatoPraticaByCodice(String codice) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkStatoPratica l "
                + "WHERE l.codice=:codice");

        query.setParameter("codice", codice);
        return (LkStatoPratica) Utils.getSingleResult(query);
    }
    //^^CS AGGIUNTA

    public List<LkComuni> findComuniByDescrizione(String descrizione, Date date) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkComuni l "
                + "WHERE l.descrizione LIKE :descrizione AND "
                + "l.dataFineValidita > :date ORDER BY l.descrizione");

        query.setParameter("descrizione", "%" + descrizione.toUpperCase() + "%");
        query.setParameter("date", date);
        return (List<LkComuni>) query.getResultList();
    }

    @Nullable
    public List<LkComuni> findComuniByDescrizionePrecisa(String descrizione, Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkComuni> q = cb.createQuery(LkComuni.class);
        Root<LkComuni> c = q.from(LkComuni.class);
        q.select(c);
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> descrizioneParameter = cb.parameter(String.class);
        ParameterExpression<Date> dataParameter = cb.parameter(Date.class);
        Expression<String> descrizioneFieldr = c.get("descrizione");
        Expression<Date> dataField = c.get("dataFineValidita");
        predicates.add(cb.equal(cb.upper(descrizioneFieldr), descrizioneParameter));
        predicates.add(cb.greaterThan(dataField, dataParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Query query = em.createQuery(q);
        query.setParameter(descrizioneParameter, descrizione.toUpperCase());
        query.setParameter(dataParameter, date);
        return (List<LkComuni>) query.getResultList();
    }

    @Nullable
    public List<LkCittadinanza> findCittadinanzaByDescrizionePrecisa(String descrizione) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkCittadinanza> q = cb.createQuery(LkCittadinanza.class);
        Root<LkCittadinanza> c = q.from(LkCittadinanza.class);
        q.select(c);
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> descrizioneParameter = cb.parameter(String.class);
        Expression<String> descrizioneFieldr = c.get("descrizione");
        predicates.add(cb.equal(cb.upper(descrizioneFieldr), descrizioneParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Query query = em.createQuery(q);
        query.setParameter(descrizioneParameter, descrizione.toUpperCase());
        return (List<LkCittadinanza>) query.getResultList();
    }

    @Nullable
    public List<LkNazionalita> findNazionalitaByDescrizionePrecisa(String descrizione) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkNazionalita> q = cb.createQuery(LkNazionalita.class);
        Root<LkNazionalita> c = q.from(LkNazionalita.class);
        q.select(c);
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> descrizioneParameter = cb.parameter(String.class);
        Expression<String> descrizioneFieldr = c.get("descrizione");
        predicates.add(cb.equal(cb.upper(descrizioneFieldr), descrizioneParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Query query = em.createQuery(q);
        query.setParameter(descrizioneParameter, descrizione.toUpperCase());
        return (List<LkNazionalita>) query.getResultList();
    }

    public LkComuni findComuneByDescrizione(String descrizione, Date date) {
        List<LkComuni> listComuni = findComuniByDescrizione(descrizione, date);
        if (listComuni == null || listComuni.size() == 0) {
            return null;
        } else {
            return listComuni.get(0);
        }
    }
    //^^CS AGGIUNTA

    public List<LkProvincie> findProvinceByDescrizione(String descrizione) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkProvincie l "
                + "WHERE l.descrizione LIKE :descrizione "
                + "ORDER BY l.descrizione");

        query.setParameter("descrizione", "%" + descrizione.toUpperCase() + "%");
        return (List<LkProvincie>) query.getResultList();
    }
    //^^CS AGGIUNTA

    public LkComuni findComuneByDescrizione(String descrizione) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkComuni l "
                + "WHERE l.descrizione = :descrizione");

        query.setParameter("descrizione", descrizione.toUpperCase());
        List<LkComuni> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    //^^CS AGGIUNTA

    public List<LkCittadinanza> findCittadinanzaByDescrizione(String descrizione) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkCittadinanza l "
                + "WHERE l.descrizione LIKE :descrizione ORDER BY l.descrizione");
        query.setParameter("descrizione", "%" + descrizione.toUpperCase() + "%");
        return (List<LkCittadinanza>) query.getResultList();
    }

    //^^CS AGGIUNTA
    public LkDug findDugByCod(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkDug l "
                + "WHERE l.codDug = :codDug");
        query.setParameter("codDug", id);
        List<LkDug> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    public LkDug findDugById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkDug l "
                + "WHERE l.idDug = :id");
        query.setParameter("id", id);
        List<LkDug> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    public LkTipoUnita findTipoUnitaByCod(String codTipoUnita) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoUnita l "
                + "WHERE l.codTipoUnita = :codTipoUnita");
        query.setParameter("codTipoUnita", codTipoUnita);
        List<LkTipoUnita> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    public LkTipoUnita findTipoUnitaById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoUnita l "
                + "WHERE l.idTipoUnita = :id");
        query.setParameter("id", id);
        List<LkTipoUnita> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    public LkTipoParticella findTipoParticellaByCod(String id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoParticella l "
                + "WHERE l.codTipoParticella = :codTipoParticella");
        query.setParameter("codTipoParticella", id);
        List<LkTipoParticella> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    //^^CS AGGIUNTA

    public LkTipoParticella findTipoParticellaById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoParticella l "
                + "WHERE l.idTipoParticella = :idTipoParticella");
        query.setParameter("idTipoParticella", id);
        List<LkTipoParticella> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    public LkTipoQualifica findTipoQualificaById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoQualifica l "
                + "WHERE l.idTipoQualifica = :id");
        query.setParameter("id", id);
        List<LkTipoQualifica> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    @Nullable
    public LkTipoRuolo findTipoRuoloByDesc(String desc) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoRuolo l "
                + "WHERE l.descrizione = :descrizione");
        query.setParameter("descrizione", desc);
        List<LkTipoRuolo> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    //^^CS AGGIUNTA
    @Nullable
    public LkTipoRuolo findTipoRuoloById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoRuolo l "
                + "WHERE l.idTipoRuolo = :id");
        query.setParameter("id", id);
        LkTipoRuolo cod = (LkTipoRuolo) query.getSingleResult();
        return cod;
    }
    //^^CS AGGIUNTA

    public List<LkTipoRuolo> findAllTipoRuolo() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoRuolo l "
                + "ORDER BY l.descrizione ASC");

        return query.getResultList();
    }
    //^^CS AGGIUNTA

    public LkProvincie findLkProvinciaById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkProvincie l "
                + "WHERE l.idProvincie = :id");
        query.setParameter("id", id);
        List<LkProvincie> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    //^^CS AGGIUNTA

    public LkProvincie findLkProvinciaByDesc(String id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkProvincie l "
                + "WHERE l.descrizione = :id");
        query.setParameter("id", id);
        List<LkProvincie> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkProvincie findLkProvinciaByDescPrecisa(String descrizione, Boolean isInfocamere) {
        String queryString = "SELECT l "
                + "FROM LkProvincie l "
                + "WHERE UPPER(l.descrizione) = :descrizione AND l.dataFineValidita > :today ";
        if (isInfocamere != null && isInfocamere) {
            queryString = queryString + " AND l.flgInfocamere = 'S' ";
        }
        Query query = em.createQuery(queryString);
        query.setParameter("descrizione", descrizione.toUpperCase());
        query.setParameter("today", new Date());
        List<LkProvincie> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkTipoSistemaCatastale findIdTipoCatastaleByDesc(String desc) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoSistemaCatastale l "
                + "WHERE l.descrizione = :descrizione");
        query.setParameter("descrizione", desc);
        List<LkTipoSistemaCatastale> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkTipoSistemaCatastale findIdTipoCatastaleById(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoSistemaCatastale l "
                + "WHERE l.idTipoSistemaCatastale = :id");
        query.setParameter("id", id);
        List<LkTipoSistemaCatastale> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkStatoPratica findLkStatoPraticaByIdStatoPratica(Integer idStatoPratica) {
        Query query = em.createNamedQuery("LkStatoPratica.findByIdStatoPratica");
        query.setParameter("idStatoPratica", idStatoPratica);

        LkStatoPratica lookup = (LkStatoPratica) Utils.getSingleResult(query);
        return lookup;
    }

    public List<LkTipoSistemaCatastale> findAllTipoCatastale() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoSistemaCatastale l "
                + "ORDER BY l.descrizione  ASC");
        return (List<LkTipoSistemaCatastale>) query.getResultList();
    }

    public List<LkDug> findAllDug() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkDug l "
                + "ORDER BY l.descrizione  ASC");
        return (List<LkDug>) query.getResultList();
    }

    public List<LkTipoParticella> findAllLkTipoParticella() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoParticella l "
                + "ORDER BY l.descrizione ASC");
        return (List<LkTipoParticella>) query.getResultList();
    }

    public List<LkTipoUnita> findAllLkTipoUnita() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoUnita l "
                + "ORDER BY l.descrizione ASC ");
        return (List<LkTipoUnita>) query.getResultList();
    }

    public LkFormeGiuridiche findLkFormeGiuridicheById(Integer idFormeGiuridiche) {
        Query query = em.createNamedQuery("LkFormeGiuridiche.findByIdFormeGiuridiche");
        query.setParameter("idFormeGiuridiche", idFormeGiuridiche);

        LkFormeGiuridiche lookup = (LkFormeGiuridiche) Utils.getSingleResult(query);
        return lookup;
    }

    public LkCittadinanza findCittadinanzaByDesc(String desc) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkCittadinanza l "
                + "WHERE l.descrizione = :descrizione");
        query.setParameter("descrizione", desc);
        LkCittadinanza cod = new LkCittadinanza();
        List<LkCittadinanza> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<LkFormeGiuridiche> findLkFormeGiuridicheByDesc(String desc) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkFormeGiuridiche l "
                + "WHERE l.descrizione LIKE :descrizione ORDER BY l.descrizione");
        query.setParameter("descrizione", "%" + desc.toUpperCase() + "%");
        List<LkFormeGiuridiche> formeGiuridiche = query.getResultList();
        return formeGiuridiche;
    }

    public LkTipoIndirizzo findTipoIndirizzoByDesc(String desc) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoIndirizzo l "
                + "WHERE l.descrizione = :descrizione");
        query.setParameter("descrizione", desc);
        List<LkTipoIndirizzo> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkTipoIndirizzo findTipoIndirizzoById(Integer idTipoIndirizzo) {
        Query query = em.createNamedQuery("LkTipoIndirizzo.findByIdTipoIndirizzo");
        query.setParameter("idTipoIndirizzo", idTipoIndirizzo);
        LkTipoIndirizzo tipoIndirizzo = (LkTipoIndirizzo) Utils.getSingleResult(query);
        return tipoIndirizzo;
    }

    public List<LkTipoIndirizzo> findAllTipoIndirizzo() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoIndirizzo l "
                + "ORDER BY l.descrizione ASC");
        return query.getResultList();
    }

    public void update(Recapiti recapito) {
        em.merge(recapito);
    }

    public void insert(Recapiti recapito) {
        em.persist(recapito);
        em.flush();
    }

    public void update(PraticaAnagrafica praticaAnagrafica) {
        em.merge(praticaAnagrafica);
    }

    public void insert(PraticaAnagrafica praticaAnagrafica) {
        em.persist(praticaAnagrafica);
    }

    public LkComuni getReference(LkComuni comune) {
        return em.getReference(LkComuni.class, comune);
    }

    public Recapiti findRecapitoByAnaeTipo(Anagrafica anagrafica, LkTipoIndirizzo tipoindirizzo) {
        Query query = em.createQuery("SELECT l "
                + "FROM Recapiti l "
                + "WHERE l.idAnagrafica = :idAnagrafica AND"
                + "l.idTipoIndirizzo = :idTipoIndirizzo");
        query.setParameter("idAnagrafica", anagrafica);
        query.setParameter("idTipoIndirizzo", tipoindirizzo);
        List<Recapiti> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<LkStatoPratica> findAllLkStatoPratica() {
        Query query = em.createNamedQuery("LkStatoPratica.findAll");
        List<LkStatoPratica> lookup = query.getResultList();
        return lookup;
    }

    public List<LkCittadinanza> findCittadinanzeByDesc(String nome) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkCittadinanza l "
                + "WHERE l.descrizione LIKE :descrizione ORDER BY l.descrizione");

        query.setParameter("descrizione", "%" + nome.toUpperCase() + "%");
        return (List<LkCittadinanza>) query.getResultList();
    }

    public List<LkNazionalita> findNazionalitaByDesc(String nome) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkNazionalita l "
                + "WHERE l.descrizione LIKE :descrizione ORDER BY l.descrizione");

        query.setParameter("descrizione", "%" + nome.toUpperCase() + "%");
        List<LkNazionalita> nazionalita = query.getResultList();
        return nazionalita;
    }

    public LkStatiScadenze findStatoScadenzaById(String idStato) {
        Query query = em.createNamedQuery("LkStatiScadenze.findByIdStato");
        query.setParameter("idStato", idStato);
        LkStatiScadenze lookup = (LkStatiScadenze) Utils.getSingleResult(query);
        return lookup;
    }

    public List<LkStatiScadenze> findAllStatiScadenze() {
        Query query = em.createNamedQuery("LkStatiScadenze.findAll");
        List<LkStatiScadenze> lookup = query.getResultList();
        return lookup;
    }

    public LkCittadinanza findCittadinanzaByID(Integer id) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkCittadinanza l "
                + "WHERE l.idCittadinanza = :id ");

        query.setParameter("id", id);
        LkCittadinanza cittadinanza = (LkCittadinanza) query.getSingleResult();
        return cittadinanza;
    }

    public LkCittadinanza findCittadinanzaByCod(String cod) {
        Query query = em.createNamedQuery("LkCittadinanza.findByCodCittadinanza");

        query.setParameter("codCittadinanza", cod);
        List<LkCittadinanza> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public LkNazionalita findNazionalitaByID(Integer idNazionalita) {
        Query query = em.createNamedQuery("LkNazionalita.findByIdNazionalita");
        query.setParameter("idNazionalita", idNazionalita);
        LkNazionalita nazionalita = (LkNazionalita) Utils.getSingleResult(query);
        return nazionalita;
    }

    public LkNazionalita findNazionalitaByCod(String cod) {
        Query query = em.createNamedQuery("LkNazionalita.findByCodNazionalita");
        query.setParameter("codNazionalita", cod);
        List<LkNazionalita> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<LkProvincie> findProvinceInfocamereByDescrizione(String ricerca) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkProvincie> q = cb.createQuery(LkProvincie.class);
        Root<LkProvincie> c = q.from(LkProvincie.class);
        q.select(c);
        ParameterExpression<String> descrizioneParameter = cb.parameter(String.class);
        ParameterExpression<String> infocamereParameter = cb.parameter(String.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Expression<String> descrizione = c.get("descrizione");
        predicates.add(cb.like(descrizione, descrizioneParameter));
        Expression<String> flgInfocamereExpr = c.get("flgInfocamere");
        predicates.add(cb.equal(flgInfocamereExpr, infocamereParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<LkProvincie> query = em.createQuery(q);
        String like = "%" + ricerca.toUpperCase() + "%";
        query.setParameter(descrizioneParameter, like);
        query.setParameter(infocamereParameter, "S");
        List<LkProvincie> provincie = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return provincie;
    }

    public List<LkTipoCollegio> findLookupTipoCollegioByDescrizione(String descrizione) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoCollegio l "
                + "WHERE l.descrizione LIKE :descrizione ORDER BY l.descrizione");

        query.setParameter("descrizione", "%" + descrizione.toUpperCase() + "%");
        List<LkTipoCollegio> lookup = query.getResultList();
        return lookup;
    }

    public LkTipoCollegio findLookupTipoCollegioById(Integer idTipoCollegio) {
        Query query = em.createNamedQuery("LkTipoCollegio.findByIdTipoCollegio");
        query.setParameter("idTipoCollegio", idTipoCollegio);
        LkTipoCollegio tipoCollegio = (LkTipoCollegio) Utils.getSingleResult(query);
        return tipoCollegio;
    }

    public LkTipoCollegio findLookupTipoCollegioByCodiceCollegio(String codCollegio) {
        Query query = em.createNamedQuery("LkTipoCollegio.findByCodCollegio");
        query.setParameter("codCollegio", codCollegio);
        LkTipoCollegio tipoCollegio = (LkTipoCollegio) Utils.getSingleResult(query);
        return tipoCollegio;
    }

    public LkProvincie findLkProvinciaByCod(String codice, Boolean infocamere) {
        String queryString = "SELECT l "
                + "FROM LkProvincie l "
                + "WHERE l.codCatastale = :codCatastale";
        if (infocamere) {
            queryString = queryString + " AND l.flgInfocamere = 'S'";
        } else {
            queryString = queryString + " AND l.dataFineValidita > :today";
        }
        Query query = em.createQuery(queryString);
        query.setParameter("codCatastale", codice);
        if (!infocamere) {
            query.setParameter("today", new Date());
        }
        LkProvincie provincia = (LkProvincie) Utils.getSingleResult(query);
        return provincia;
    }

    public LkTipoIndirizzo findTipoIndirizzoByCod(String codTipoIndirizzo) {
        Query query = em.createNamedQuery("LkTipoIndirizzo.findByCodTipoIndirizzo");
        query.setParameter("codTipoIndirizzo", codTipoIndirizzo);

        LkTipoIndirizzo tipoIndirizzo = (LkTipoIndirizzo) Utils.getSingleResult(query);
        return tipoIndirizzo;
    }

    public List<LkTipoQualifica> findAllTipoQualifica() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoQualifica l "
                + "ORDER BY l.descrizione ASC");
        List<LkTipoQualifica> tipiQualifica = query.getResultList();
        return tipiQualifica;
    }

    public List<LkStatoPratica> findStatiPraticaByGruppo(String grpStatoPratica) {
        Query query = em.createNamedQuery("LkStatoPratica.findByGrpStatoPratica");
        query.setParameter("grpStatoPratica", grpStatoPratica);
        List<LkStatoPratica> stati = query.getResultList();
        return stati;
    }

    public List<LkTipiAttore> findAllTipiAttore() {
        Query query = em.createNamedQuery("LkTipiAttore.findAll");
        List<LkTipiAttore> attori = query.getResultList();
        return attori;
    }

    public LkTipiAttore findLkTipiAttoreById(String idTipoAttore) {
        Query query = em.createNamedQuery("LkTipiAttore.findByIdTipoAttore");
        query.setParameter("idTipoAttore", idTipoAttore);
        LkTipiAttore tipoAttore = (LkTipiAttore) Utils.getSingleResult(query);
        return tipoAttore;
    }

    public List<LkComuni> findComuneEnteByDesc(String descrizione) {
        Query query = em.createQuery("SELECT DISTINCT c "
                + " FROM Enti e JOIN e.lkComuniList c"
                + " WHERE UPPER(c.descrizione) LIKE :desc"
                + " ORDER BY c.descrizione ASC");
        //SELECT DISTINCT a FROM A a JOIN a.bList b WHERE b IS NOT NULL
        query.setParameter("desc", "%" + descrizione.trim().toUpperCase() + "%");
        List<LkComuni> comuni = (List<LkComuni>) query.getResultList();
        return comuni;
    }

    public List<LkScadenze> findAllScadenze() {
        Query query = em.createNamedQuery("LkScadenze.findAll");
        List<LkScadenze> scadenze = query.getResultList();
        return scadenze;
    }

    public List<LkTipoQualifica> findQualificaByCondizione(String condizione) {
        Query query = em.createNamedQuery("LkTipoQualifica.findByCondizione");
        query.setParameter("condizione", condizione);

        List<LkTipoQualifica> qualifiche = query.getResultList();
        return qualifiche;
    }

    public LkTipoRuolo findTipoRuoloByCodRuolo(String codRuolo) {
        Query query = em.createNamedQuery("LkTipoRuolo.findByCodRuolo");
        query.setParameter("codRuolo", codRuolo);

        LkTipoRuolo tipoRuolo = (LkTipoRuolo) Utils.getSingleResult(query);
        return tipoRuolo;
    }

    public List<LkTipoCollegio> findAllLkTipoCollegio() {
        Query query = em.createNamedQuery("LkTipoCollegio.findAll");
        List<LkTipoCollegio> collegi = query.getResultList();
        return collegi;
    }

    public List<LkProvincie> findAllProvince() {
        Query query = em.createNamedQuery("LkProvincie.findAll");
        List<LkProvincie> provincie = query.getResultList();
        return provincie;
    }

    public List<LkComuni> findAllEntiComuni() {
        Query query = em.createQuery("SELECT DISTINCT l "
                + "FROM LkComuni l "
                + "JOIN l.entiList e");
        List<LkComuni> comuni = query.getResultList();
        return comuni;
    }
    //^^CS AGGIUNTA

    public DizionarioErrori findDizionarioErroriByCod(String cod) {
        Query query = em.createQuery(" SELECT DISTINCT ds "
                + " FROM DizionarioErrori ds "
                + " WHERE "
                + " ds.codErrore = :cod");
        query.setParameter("cod", cod);
        DizionarioErrori ds = (DizionarioErrori) Utils.getSingleResult(query);
        return ds;
    }

    public List<LkProvincie> findAllProvinceAttive(Date dataFineValidita) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkProvincie l "
                + "WHERE l.dataFineValidita > :dataFineValidita");
        query.setParameter("dataFineValidita", dataFineValidita);
        List<LkProvincie> provincie = query.getResultList();
        return provincie;
    }

    public List<LkFormeGiuridiche> findAllLkFormeGiuridiche() {
        Query query = em.createNamedQuery("LkFormeGiuridiche.findAll");
        List<LkFormeGiuridiche> formegiuridiche = query.getResultList();
        return formegiuridiche;
    }

    public List<LkNazionalita> findAllNazionalita() {
        Query query = em.createNamedQuery("LkNazionalita.findAll");
        List<LkNazionalita> nazionalita = query.getResultList();
        return nazionalita;
    }

    public LkTipoQualifica findTipoQualificaByCodQualificaAndCondizione(String codiceQualifica, String condizione) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoQualifica l "
                + "WHERE l.codQualifica = :codQualifica "
                + "AND l.condizione = :condizione");
        query.setParameter("codQualifica", codiceQualifica);
        query.setParameter("condizione", condizione);
        LkTipoQualifica qualifica = (LkTipoQualifica) Utils.getSingleResult(query);
        return qualifica;
    }

    public List<LkStatoPratica> findStatoPraticaByGruppoDiversoDa(String gruppo) {
        Query query = em.createQuery("SELECT l "
                + "FROM LkStatoPratica l "
                + "WHERE l.grpStatoPratica != :gruppo");
        query.setParameter("gruppo", gruppo);
        List<LkStatoPratica> statoPratica = query.getResultList();
        return statoPratica;
    }

    public LkStatiMail findStatoMailByCodice(String codice) {
        Query query = em.createNamedQuery("LkStatiMail.findByCodice");
        query.setParameter("codice", codice);
        LkStatiMail statoMail = (LkStatiMail) query.getSingleResult();
        return statoMail;
    }

    public LkTipoOggetto findTipoOggettoById(Integer idTipoOggetto) {
        Query query = em.createNamedQuery("LkTipoOggetto.findByIdTipoOggetto");
        query.setParameter("idTipoOggetto", idTipoOggetto);
        LkTipoOggetto oggetto = (LkTipoOggetto) query.getSingleResult();
        return oggetto;
    }

    public LkTipoOggetto findTipoOggettoByCodice(String codTipoOggetto) {
        Query query = em.createNamedQuery("LkTipoOggetto.findByCodTipoOggetto");
        query.setParameter("codTipoOggetto", codTipoOggetto);
        LkTipoOggetto oggetto = (LkTipoOggetto) query.getSingleResult();
        return oggetto;
    }

    public List<LkTipoOggetto> findAllTipoOggetto() {
        Query query = em.createQuery("SELECT l FROM LkTipoOggetto l order by l.desTipoOggetto ASC");
        List<LkTipoOggetto> result = query.getResultList();
        return result;
    }

    public LkStatiPraticaOrganiCollegiali findStatiPraticaOrganiCollegialiByCodice(String codStatiPraticaOrganiCollegiali) {
        Query query = em.createNamedQuery("LkStatiPraticaOrganiCollegiali.findByCodciceStatoPraticaOrganiCollegiali");
        query.setParameter("codice", codStatiPraticaOrganiCollegiali);
        LkStatiPraticaOrganiCollegiali oggetto = (LkStatiPraticaOrganiCollegiali) query.getSingleResult();
        return oggetto;
    }

    public List<LkStatiPraticaOrganiCollegiali> findAllStatiPraticaOrganiCollegiali() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkStatiPraticaOrganiCollegiali> q = cb.createQuery(LkStatiPraticaOrganiCollegiali.class);
        Root<LkStatiPraticaOrganiCollegiali> c = q.from(LkStatiPraticaOrganiCollegiali.class);
        q.select(c);
        q.orderBy(cb.asc(c.get("desStatoPraticaOrganiCollegiali")));
        TypedQuery<LkStatiPraticaOrganiCollegiali> query = em.createQuery(q);
        List<LkStatiPraticaOrganiCollegiali> result = query.getResultList();
        return result;
    }

    public LkStatiSedute findStatiSeduteById(Integer idStatoSeduta) {
        Query query = em.createNamedQuery("LkStatiSedute.findByIdStatoSeduta");
        query.setParameter("idStatoSeduta", idStatoSeduta);
        List<LkStatiSedute> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<LkStatiSedute> findAllStatiSedute() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LkStatiSedute> q = cb.createQuery(LkStatiSedute.class);
        Root<LkStatiSedute> c = q.from(LkStatiSedute.class);
        q.select(c);
        q.orderBy(cb.asc(c.get("descrizione")));
        TypedQuery<LkStatiSedute> query = em.createQuery(q);
        List<LkStatiSedute> result = query.getResultList();
        return result;
    }

    public List<LkRuoliCommissione> findAllRuoloCommissione() {
        Query query = em.createQuery("SELECT l FROM LkRuoliCommissione l order by l.descrizione ASC");
        List<LkRuoliCommissione> result = query.getResultList();
        return result;
    }

    public LkRuoliCommissione findRuoloCommissione(Integer idRuoloCommissione) {
        Query query = em.createNamedQuery("LkRuoliCommissione.findByIdRuoloCommissione");
        query.setParameter("idRuoloCommissione", idRuoloCommissione);
        List<LkRuoliCommissione> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}

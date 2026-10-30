package it.wego.cross.dao;

import java.math.BigInteger;
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
import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DizionarioErrori;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkAssociazioneProcedimentoEnteUtenteSuap;
import it.wego.cross.entity.LkCittadinanza;
import it.wego.cross.entity.LkClassificazioneProcedimento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkDug;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkRuoliCommissione;
import it.wego.cross.entity.LkScadenze;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.entity.LkStatiPraticaOrganiCollegiali;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatiSedute;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipiAttore;
import it.wego.cross.entity.LkTipoCollegio;
import it.wego.cross.entity.LkTipoEndoProcedimentoSuap;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoInterventoSuap;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoPraticaProcessi;
import it.wego.cross.entity.LkTipoProcedimentoSuap;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.utils.DaoUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

//@Configurable
@Repository
public class LookupDao {

    @PersistenceContext
    private EntityManager em;

    public LookupDao(EntityManager em) {
    	this.em = em;
    }
    
    public LookupDao() {}
    
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
    
    public String findRuoloCiviliaByIdEtichetta(int id_etichetta) {
	String sqlString = "SELECT ruolo_etichetta_civilia_cross FROM opencross.ruoli_etichetta_civilia_cross WHERE id = ?";
    	
		Query query = em.createNativeQuery(sqlString);
        query.setParameter(1, id_etichetta);
        String ruolo_civilia = (String) query.getSingleResult(); 
        return ruolo_civilia;
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
                + "FROM LkTipoRuolo l WHERE l.hidden = 0 "
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
    
    public LkTipoPratica findLkTipoPraticaById(Integer idTipoPratica) {
        Query query = em.createNamedQuery("LkTipoPratica.findByIdTipoPratica");
        query.setParameter("idTipoPratica", idTipoPratica);

        LkTipoPratica lookup = (LkTipoPratica) Utils.getSingleResult(query);
        return lookup;
    }

    public LkTipoPratica findLkTipoPraticaByType(String tipoPratica) {
    	Query query = em.createNamedQuery("LkTipoPratica.findByType");
    	query.setParameter("tipoPratica", tipoPratica);
    	LkTipoPratica lookup = (LkTipoPratica) Utils.getSingleResult(query);
    	return lookup;
    }
    
    public LkTipoPraticaProcessi findByIdTipoPraticaProcessiById(Integer idProcesso) {
        Query query = em.createNamedQuery("LkTipoPraticaProcessi.findByIdTipoPraticaProcessiById");
        query.setParameter("idProcesso", idProcesso);

        LkTipoPraticaProcessi lookup = (LkTipoPraticaProcessi) Utils.getSingleResult(query);
        return lookup;
    }

    public Integer findByIdTipoPraticaProcessiByIdCustom(Integer idProcesso) {
    	String sqlString = "SELECT id_tipo_pratica FROM opencross.lk_tipo_pratica_processi WHERE id_processo = ?";
    	
		Query query = em.createNativeQuery(sqlString);
        query.setParameter(1, idProcesso);
        Integer lookup = (int) query.getSingleResult(); 
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

    public LkStatoPratica findStatusPraticaFromPratica(Integer idPratica) {
    	Query query = em.createNamedQuery("LkStatoPratica.findByIdPratica");
    	query.setParameter("idPratica", idPratica);
    	return (LkStatoPratica) query.getSingleResult();
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
    
    public LkTipoProcedimentoSuap findTipoProcedimentoSuapByDescSuap(String descrizioneTipoSuap) {
        Query query = em.createNamedQuery("LkTipoProcedimentoSuap.findByDescrizioneTipoProcedimentoSuap");
        query.setParameter("descrizioneTipoProcedimentoSuap", descrizioneTipoSuap);

        LkTipoProcedimentoSuap tipoProcedimentoSuap = (LkTipoProcedimentoSuap) Utils.getSingleResult(query);
        return tipoProcedimentoSuap;
    }
    
    public LkTipoInterventoSuap findTipoInterventoSuapByDescrizione(String descrizioneTipoIntervento) {
        Query query = em.createNamedQuery("LkTipoInterventoSuap.findByDescrizioneTipoInterventoSuap");
        query.setParameter("descrizioneTipoInterventoSuap", descrizioneTipoIntervento);

        LkTipoInterventoSuap tipoInterventoSuap = (LkTipoInterventoSuap) Utils.getSingleResult(query);
        return tipoInterventoSuap;
    }
    public LkTipoEndoProcedimentoSuap findTipoEndoProcedimentoSuapByCod(String codEndoProcedimento) {
        Query query = em.createNamedQuery("LkTipoEndoProcedimentoSuap.findByCodTipoEndoProcedimento");
        query.setParameter("codEndoProcedimento", codEndoProcedimento);

        LkTipoEndoProcedimentoSuap tipoEndoProcedimentoSuap = (LkTipoEndoProcedimentoSuap) Utils.getSingleResult(query);
        return tipoEndoProcedimentoSuap;
    }
    
    
    public List<LkTipoInterventoSuap> findAllTipoInterventoSuap() {
        Query query = em.createQuery("SELECT l FROM LkTipoInterventoSuap l order by l.descrizioneTipoInterventoSuap ASC");
        List<LkTipoInterventoSuap> result = query.getResultList();
        return result;
    }
    
    public List<LkTipoProcedimentoSuap> findAllTipoProcedimentoSuap() {
        Query query = em.createQuery("SELECT l FROM LkTipoProcedimentoSuap l order by l.descrizioneTipoProcedimentoSuap ASC");
        List<LkTipoProcedimentoSuap> result = query.getResultList();
        return result;
    }
    public List<LkClassificazioneProcedimento> findAllClassificazioneProcedimento() {
        Query query = em.createQuery("SELECT l FROM LkClassificazioneProcedimento l order by l.descrizioneClassificazioneProcedimento ASC");
        List<LkClassificazioneProcedimento> result = query.getResultList();
        return result;
    }
	public LkTipoProcedimentoSuap findLkTipoProcedimentoSUAPbyIdProcedimento(Integer idProcedimentoSuap) {
		Query query = em.createNamedQuery("LkTipoProcedimentoSuap.findByIdTipoProcedimentoSuap");
        query.setParameter("idTipoProcedimentoSuap", idProcedimentoSuap);

        LkTipoProcedimentoSuap lookup = (LkTipoProcedimentoSuap) Utils.getSingleResult(query);
        return lookup;
	}

	public LkTipoInterventoSuap findLkTipoInterventoSUAPbyIdIntervento(Integer idInterventoSuap) {
		Query query = em.createNamedQuery("LkTipoInterventoSuap.findByIdTipoInterventoSuap");
        query.setParameter("idTipoInterventoSuap", idInterventoSuap);

        LkTipoInterventoSuap lookup = (LkTipoInterventoSuap) Utils.getSingleResult(query);
        return lookup;
	}

	public LkClassificazioneProcedimento findClassificazioneProcedimentobyId(Integer idClassificazioneProcedimento) {
		Query query = em.createNamedQuery("LkClassificazioneProcedimento.findByIdClassificazioneProcedimento");
        query.setParameter("idClassificazioneProcedimento", idClassificazioneProcedimento);

        LkClassificazioneProcedimento lookup = (LkClassificazioneProcedimento) Utils.getSingleResult(query);
        return  lookup;
	}
	
	public List<LkTipoEndoProcedimentoSuap> findTipoEndoProcedimentoSuapByTipolgia(String tipologia){
    	Query query = em.createNamedQuery("LkTipoEndoProcedimentoSuap.findByTipologiaClassificazione");
        query.setParameter("tipologia", tipologia);
        return (List<LkTipoEndoProcedimentoSuap>)  query.getResultList();

    }
	 
	public LkAssociazioneProcedimentoEnteUtenteSuap findAssociazioneProcedimentoEnteUtenteSuap(String codProcedimentoSuap,Integer idEnte) {
		Query query = em.createNamedQuery("LkAssociazioneProcedimentoEnteUtenteSuap.findByCodProcedimentoSuapAndIdEnte");
        query.setParameter("codProcedimentoSuap", codProcedimentoSuap);
        query.setParameter("idEnte", idEnte);
        LkAssociazioneProcedimentoEnteUtenteSuap lookup = (LkAssociazioneProcedimentoEnteUtenteSuap) Utils.getSingleResult(query);
        return  lookup;
	}
 
    public List<LkTipoPratica> findAllTipoPratica() {
    	Query query = em.createNamedQuery("LkTipoPratica.findAll");
    	return query.getResultList();
    }

    public List<LkTipoPratica> findAllSortedTipoPratica() {
    	Query query = em.createNamedQuery("LkTipoPratica.findAllSorted");
    	return query.getResultList();
    }

    public List<LkTipoPratica> findSortedTipoPraticaByFlags(boolean searchAll) {
    	Query query = em.createNamedQuery("LkTipoPratica.findSortedByFlags");
    	query.setParameter("search_all", searchAll);
        return query.getResultList();
    }

    public List<String> findPersonalDataBySurname(String surname) {
    	Query query = em.createNamedQuery("Anagrafica.findCognomiByCognomeLike");
    	query.setParameter("cognome", surname+"%");
    	return query.getResultList();
    }

    public List<String> findPersonalDataByName(String name) {
    	Query query = em.createNamedQuery("Anagrafica.findNomiByNomeLike");
    	query.setParameter("nome", name+"%");
    	return query.getResultList();
    }

    public List<String> findPersonalDataWithDistinctSurnamesBySurnameAndName(String surname, String name) {
    	Query query = em.createNamedQuery("Anagrafica.findCognomiByNomeCognomeLike");
    	query.setParameter("cognome", surname+"%");
    	query.setParameter("nome", name+"%");
    	return query.getResultList();
    }

    public List<String> findPersonalDataWithDistinctNamesBySurnameAndName(String surname, String name) {
    	Query query = em.createNamedQuery("Anagrafica.findNomiByNomeCognomeLike");
    	query.setParameter("cognome", surname+"%");
    	query.setParameter("nome", name+"%");
    	return query.getResultList();
    }
    
    public List<Anagrafica> findPersonalDataByCompanyName(String companyName) {
    	Query query = em.createNamedQuery("Anagrafica.findByDenominazioneLike");
    	query.setParameter("denominazione", "%"+companyName+"%");
    	return query.getResultList();
    }
    
    public List<Anagrafica> findPersonalDataByTaxCode(String taxCode) {
    	Query query = em.createNamedQuery("Anagrafica.findByCodiceFiscaleLike");
    	query.setParameter("codiceFiscale", taxCode+"%");
    	return query.getResultList();
    }
    
    public List<Anagrafica> findPersonalDataByVatNumber(String vatNumber) {
    	Query query = em.createNamedQuery("Anagrafica.findByPartitaIvaLike");
    	query.setParameter("partitaIva", vatNumber+"%");
    	return query.getResultList();
    }
    
    public List<Anagrafica> findPersonalDataByTaxCodeOrVatNumber(String taxCode) {
    	Query query = em.createNamedQuery("Anagrafica.findByCodiceFiscalePartitaIvaLike");
    	query.setParameter("codiceFiscale", taxCode+"%");
    	query.setParameter("partitaIva", taxCode+"%");
    	return query.getResultList();
    }
    
    public LkTipoSistemaCatastale findByIdTipoSistemaCatastale(BigInteger idTipoSistemaCatastale) {
    	Query query = em.createNamedQuery("LkTipoSistemaCatastale.findByIdTipoSistemaCatastale");
    	query.setParameter("idTipoSistemaCatastale", idTipoSistemaCatastale);
    	return (LkTipoSistemaCatastale) query.getSingleResult();
    }
    
    public List<IndirizziIntervento> findCadastralAddressesByStreetName(String streetName, boolean isStartFrom) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<IndirizziIntervento> criteriaQuery = criteriaBuilder.createQuery(IndirizziIntervento.class);
		Root<IndirizziIntervento> root = criteriaQuery.from(IndirizziIntervento.class);
		criteriaQuery.select(root);
		criteriaQuery.distinct(true);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.like(root.get("indirizzo"), (isStartFrom?"":"%") + DaoUtils.setWildStringParameter(streetName)));
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<IndirizziIntervento> query =em.createQuery(criteriaQuery);
		query.setMaxResults(15);
		query.setFirstResult(0);
		
    	return query.getResultList();
    }
    
    public List<it.wego.cross.entity.Email> findEmailRecenti() {
    	  
        String sqlString = "SELECT "
        		+ "`id_email`, "
        		+ "`id_pratica_evento`,"
        		+ "`data_inserimento`,"
        		+ "`data_aggiornamento`,"
        		+ "`path_eml`,"
        		+ "`id_messaggio`, "
        		+ "`id_messaggio_pec`, "
        		+ "`email_destinatario`,"
        		+ "`corpo_email`,"
        		+ "`oggetto_email`, "
        		+ "`oggetto_risposta`,"
        		+ "`corpo_risposta`,"
        		+ "`stato`,"
        		+ "`gruppo`,"
        		+ "`tipo_destinazione`"
        		+ " FROM opencross.email"
        		+ " WHERE stato = 2"
        		+ " AND id_email = 1698"
        		+ " AND oggetto_risposta IS NULL"
        		+ " AND corpo_risposta IS NULL"
        		+ " ORDER BY data_inserimento DESC"
        		+ " LIMIT 0, 10000";
    	
		Query query = em.createNativeQuery(sqlString, it.wego.cross.entity.Email.class);
		
		return (List<it.wego.cross.entity.Email>) query.getResultList();
    }
    public int findEmail(String idMessaggio) {
    	int id_email = 0;
    	idMessaggio = "<126929010.1761707303697113.JavaMail.root@mail.pec.rupar.puglia.it>";
        String sqlString = "SELECT id_email"
        		+ " FROM email"
        		+ " WHERE stato = 2"
        		+ " AND id_messaggio = ?" 
        		+ " AND oggetto_risposta IS NULL"
        		+ " AND corpo_risposta IS NULL"
        		+ " LIMIT 1";
        
		Query query = em.createNativeQuery(sqlString);
        query.setParameter(1, idMessaggio);
        try {
        	id_email = (int) query.getSingleResult();
        } catch(Exception e) {
        	id_email = 0;
        }
        return id_email;		
    }
    
    @Transactional
    public void insertEmailConsegnate(String idMessaggio, String oggetto_risposta, String corpo_risposta) {
    
        String query = "INSERT INTO `opencross`.`email_consegnate`"
				+ "(`id_messaggio`,"
				+ "`oggetto_risposta`,"
				+ "`corpo_risposta`)"
				+ "VALUES "
				+ "(:f1, :f2, :f3)";
            
		em.createNativeQuery(query)
		   .setParameter("f1", idMessaggio)
		   .setParameter("f2", oggetto_risposta)
		   .setParameter("f3", corpo_risposta)
		   .executeUpdate();
    }

	public List<Enti> findSueInstitutionInTown(LkComuni town) {
		Query query = em.createNamedQuery("Enti.findBySueInstitutionInTown");
    	query.setParameter("town", town.getDescrizione());
    	return (List<Enti>) query.getResultList();
	}
}

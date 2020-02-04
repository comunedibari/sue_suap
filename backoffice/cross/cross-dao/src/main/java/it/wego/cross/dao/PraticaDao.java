package it.wego.cross.dao;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import it.wego.cross.constants.AnaTipiDestinatario;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.PermissionConstans;
import it.wego.cross.constants.StatiScadenzaConstants;
import it.wego.cross.constants.StatoPratica;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.EstrazioniAgibDTO;
import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;
import it.wego.cross.dto.PraticaBarDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.entity.view.PraticheAllegatiView;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.sql.query.EstrazioniPratiche;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Logger;
import it.wego.cross.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Configurable
@Repository
public class PraticaDao extends Paginator {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private UtentiDao utentiDao;

    public void insert(Pratica pratica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert Pratica ");
        em.persist(pratica);
    }

    public void insert(DatiCatastali datiCatastal) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert DatiCatastali");
        em.persist(datiCatastal);
    }

    public void insert(IndirizziIntervento indirizzoIntervento) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert IndirizzoIntervento");
        em.persist(indirizzoIntervento);
    }

    public void insert(NotePratica nota) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert NotePratica");
        em.persist(nota);
    }

    public void insert(PraticaProcedimenti praticaProcedimenti) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.salva PraticaProcedimenti ");
        em.persist(praticaProcedimenti);
    }

    public void insert(PraticheEventi eventoPratica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert PraticheEventi ");
        em.persist(eventoPratica);
    }

    public void insert(PraticheEventiAnagrafiche eventoPraticaAnagrafica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert PraticheEventiAnagrafiche ");
        em.persist(eventoPraticaAnagrafica);
    }

    public void update(Pratica pratica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update Pratica");
        em.merge(pratica);
    }

    public void update(PraticaAnagrafica praticaAnagrafica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update Pratica");
        em.merge(praticaAnagrafica);
    }

    public void insert(PraticaAnagrafica pratica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaAnagrafica.insert PraticaAnagrafica");
        em.persist(pratica);
    }

    public void update(DatiCatastali datiCatastali) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update DatiCatastali ");
        em.merge(datiCatastali);
    }

    public void update(IndirizziIntervento indirizziIntervento) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update IndirizziIntervento ");
        em.merge(indirizziIntervento);
    }

    public void update(PraticheProtocollo praticaProtocollo) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update PraticheProtocollo ");
        em.merge(praticaProtocollo);
    }

    public void insert(PraticheProtocollo praticaProtocollo) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update PraticheProtocollo ");
        em.persist(praticaProtocollo);
        em.flush();
    }

    public void insert(PraticaEventiProcedimenti praticaEventiProcedimenti) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.insert PraticaEventiProcedimenti ");
        em.persist(praticaEventiProcedimenti);
        em.flush();
    }

    public void update(PraticheEventi eventoPratica) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.update PraticheEventi");
        em.merge(eventoPratica);
    }

    public void delete(Object object) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.delete Object ");
        Pratica pratica = em.find(Pratica.class, ((Pratica) object).getIdPratica());
        em.remove(pratica);
    }

    public void deletePraticaAnagrafica(PraticaAnagrafica pa) throws Exception {
        PraticaAnagrafica p = em.find(PraticaAnagrafica.class, pa.getPraticaAnagraficaPK());
        em.remove(p);
    }

    @Nullable
    public Pratica findPratica(Integer idPratica) {
        Log.SQL.info("CHIAMATO PraticaDao.findPratica " + idPratica);
        Pratica pratica = em.find(Pratica.class, idPratica);
        return pratica;
    }

    public Pratica requirePraticaByIdPratica(Integer idPratica) {
        Pratica pratica = findPratica(idPratica);
        Preconditions.checkNotNull(pratica, "pratica non trovata per idPratica = %s", idPratica);
        return pratica;
    }

    public Pratica requirePraticaByCodicePratica(String codicePratica) {
        Pratica pratica = findPraticaByIdentificativo(codicePratica);
        Preconditions.checkNotNull(pratica, "pratica non trovata per codicePratica = %s", codicePratica);
        return pratica;
    }

    public Long countFiltrate(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.countFiltrate ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Pratica> c = q.from(Pratica.class);
        Join joinDatiCatastali = c.join("datiCatastaliList", JoinType.LEFT);
        q.select(cb.countDistinct(c));
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<String> foglioParameter = null;
        ParameterExpression<String> mappaleParameter = null;
        ParameterExpression<String> subalternoParameter = null;
        ParameterExpression<String> fascicoloParameter = null;
        ParameterExpression<LkComuni> comuneParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> statoPraticaParameter = null;
        ParameterExpression<Utente> utenteParameter = null;
        ParameterExpression<String> identificativoPraticheParameter = null;
        ParameterExpression<List> praticheDaEscludereParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;

        ParameterExpression<String> indirizzoParameter = null;
        ParameterExpression<String> civicoParameter = null;
        ParameterExpression<Integer> idTipoSistemaCatastaleParameter = null;
        ParameterExpression<Integer> idTipoUnitaParameter = null;
        ParameterExpression<Integer> idTipoParticellaParameter = null;
        ParameterExpression<String> comuneCensuarioParameter = null;
        ParameterExpression<String> estensioneParticellaParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<String> identificativoPraticaParameter = null;
        ParameterExpression<Integer> idPraticaParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (filter.getDataInizio() != null) {
            dataAperturaParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(c.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(c.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
//        if (!Utils.e(filter.getNumeroProtocollo())) {
//            numeroProtocolloParameter = cb.parameter(String.class);
//            predicates.add(cb.equal(c.get("protocollo"), numeroProtocolloParameter));
//        }
//        if (!Utils.e(filter.getNumeroFascicolo())) {
//            fascicoloParameter = cb.parameter(String.class);
//            predicates.add(cb.equal(c.get("protocollo"), fascicoloParameter));
//        }
        Expression<String> prot = c.get("protocollo");
        if (!Utils.e(filter.getNumeroProtocollo())) {
        	numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.like(prot, numeroProtocolloParameter));
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            fascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.like(prot, fascicoloParameter));
        }
        if (filter.getEnteSelezionato() != null) {
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticheParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticheParameter));
        }
        if (!Utils.e(filter.getIdComune())) {
            comuneParameter = cb.parameter(LkComuni.class);
            predicates.add(cb.equal(c.get("idComune"), comuneParameter));
        }

        if (filter.getIdStatoPratica() != null) {
            statoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(c.get("idStatoPratica"), statoPraticaParameter));
        }
//        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
//            utenteParameter = cb.parameter(Utente.class);
//            predicates.add(cb.equal(c.get("idUtente"), utenteParameter));
//        }
        if(filter.getIdOperatoreSelezionato() != null) {
        	utenteParameter = cb.parameter(Utente.class);
        	predicates.add(cb.equal(c.get("idUtente"), utenteParameter));
        }
        else {
        	if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
        		utenteParameter = cb.parameter(Utente.class);
                predicates.add(cb.equal(c.get("idUtente"), utenteParameter));
            }
        }

        //filtro sempre e comunque in base al ruolo
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }

        if (filter.getPraticheDaEscludere() != null) {
            praticheDaEscludereParameter = cb.parameter(List.class);
            List<Integer> praticheDaEscludere = new ArrayList<Integer>();
            for (LkStatoPratica statoPratica : filter.getPraticheDaEscludere()) {
                praticheDaEscludere.add(statoPratica.getIdStatoPratica());
            }
            predicates.add(cb.not(c.get("idStatoPratica").get("idStatoPratica").in(praticheDaEscludere)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Expression<String> codiceFiscale = c.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = c.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        Expression<String> indirizzo = c.get("indirizziInterventoList").get("indirizzo");
        if (filter.getIndirizzo() != null) {
            indirizzoParameter = cb.parameter(String.class);
            predicates.add(cb.like(cb.upper(indirizzo), indirizzoParameter));
        }
        Expression<String> civico = c.get("indirizziInterventoList").get("civico");
        if (filter.getCivico() != null) {
            civicoParameter = cb.parameter(String.class);
            predicates.add(cb.equal(civico, civicoParameter));
        }

        if (filter.getIdTipoSistemaCatastale() != null) {
            idTipoSistemaCatastaleParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoSistemaCatastale").get("idTipoSistemaCatastale"), idTipoSistemaCatastaleParameter));
        }

        if (filter.getIdTipoUnita() != null) {
            idTipoUnitaParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoUnita").get("idTipoUnita"), idTipoUnitaParameter));
        }
        if (filter.getIdTipoParticella() != null) {
            idTipoParticellaParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoParticella").get("idTipoParticella"), idTipoParticellaParameter));
        }

        if (filter.getFoglio() != null) {
            foglioParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("foglio"), foglioParameter));
        }
        if (filter.getMappale() != null) {
            mappaleParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("mappale"), mappaleParameter));
        }
        if (filter.getComuneCensuario() != null) {
            comuneCensuarioParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("comuneCensuario"), comuneCensuarioParameter));
        }
        if (filter.getEstensioneParticella() != null) {
            estensioneParticellaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("estensioneParticella"), estensioneParticellaParameter));
        }
        if (filter.getSubalterno() != null) {
            subalternoParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("subalterno"), subalternoParameter));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (filter.getIdPratica() != null) {
        	idPraticaParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idPratica"), idPraticaParameter));
        }
        if (filter.getOggetto() != null) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggettoPratica");
            predicates.add(cb.like(path, oggettoParameter));
        }

        predicates.add(cb.isNotNull(c.get("idUtente")));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, "%" + filter.getNumeroProtocollo() + "%");
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(fascicoloParameter, "%" + filter.getNumeroFascicolo() + "%");
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(statoPraticaParameter, filter.getIdStatoPratica());
        }
//        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso() && filter.getConnectedUser() != null) {
//            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
//            query.setParameter(utenteParameter, utenteConnesso);
//        }
        if(filter.getIdOperatoreSelezionato() != null) {
        	Utente operatoreSelezionato = utentiDao.findUtenteByIdUtente(filter.getIdOperatoreSelezionato());
            query.setParameter(utenteParameter, operatoreSelezionato);
        }
        else {
        	if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso() && filter.getConnectedUser() != null ) {
                Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
                query.setParameter(utenteParameter, utenteConnesso);
            }
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticheParameter, filter.getIdentificativoPratica());
        }
        if (filter.getFoglio() != null) {
            query.setParameter(foglioParameter, filter.getFoglio());
        }
        if (filter.getMappale() != null) {
            query.setParameter(mappaleParameter, filter.getMappale());
        }
        if (filter.getSubalterno() != null) {
            query.setParameter(subalternoParameter, filter.getSubalterno());
        }
        if (filter.getComuneCensuario() != null) {
            query.setParameter(comuneCensuarioParameter, filter.getComuneCensuario());
        }
        if (filter.getEstensioneParticella() != null) {
            query.setParameter(estensioneParticellaParameter, filter.getEstensioneParticella());
        }
        if (filter.getPraticheDaEscludere() != null) {
            List<Integer> statiPraticaDaEscludere = new ArrayList<Integer>();
            for (LkStatoPratica s : filter.getPraticheDaEscludere()) {
                statiPraticaDaEscludere.add(s.getIdStatoPratica());
            }
            query.setParameter(praticheDaEscludereParameter, statiPraticaDaEscludere);
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (!Utils.e(filter.getIdComune())) {
            LkComuni comune = lookupDao.findComuneById(filter.getIdComune());
            query.setParameter(comuneParameter, comune);
        }
        if (!Utils.e(filter.getIndirizzo())) {
            query.setParameter(indirizzoParameter, "%" + filter.getIndirizzo().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (!Utils.e(filter.getCivico())) {
            query.setParameter(civicoParameter, filter.getCivico());
        }
        if (!Utils.e(filter.getIdTipoSistemaCatastale())) {
            query.setParameter(idTipoSistemaCatastaleParameter, filter.getIdTipoSistemaCatastale());
        }
        if (!Utils.e(filter.getIdTipoUnita())) {
            query.setParameter(idTipoUnitaParameter, filter.getIdTipoUnita());
        }
        if (!Utils.e(filter.getIdTipoParticella())) {
            query.setParameter(idTipoParticellaParameter, filter.getIdTipoParticella());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
        	//identificativoPraticaParameter = cb.parameter(String.class);
            //predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticaParameter));
            
            identificativoPraticaParameter = cb.parameter(String.class);
            Expression<String> path = c.get("identificativoPratica");
            predicates.add(cb.like(path, identificativoPraticaParameter));
            
        }
        
        if (!Utils.e(filter.getIdPratica())) {
            query.setParameter(idPraticaParameter, filter.getIdPratica());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }

        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public Long countDaAssegnare(Filter filter) {
        //^^CS AGGIUNTA loggare la funzione chiamata, e' buona cosa farlo
        Log.SQL.info("CHIAMATO PraticaDao.countDaAssegnare ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(cb.countDistinct(c));
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> protocolloParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> statoPraticaParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getDataInizio() != null) {
            dataAperturaParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(c.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(c.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            protocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("protocollo"), protocolloParameter));
        }
        if (filter.getEnteSelezionato() != null) {
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getIdStatoPratica() != null) {
            statoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(c.get("idStatoPratica"), statoPraticaParameter));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggettoPratica");
            predicates.add(cb.like(path, oggettoParameter));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Expression<String> codiceFiscale = c.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = c.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }

        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        predicates.add(cb.isNull(c.get("idUtente")));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(protocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(statoPraticaParameter, filter.getIdStatoPratica());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public List<Pratica> findDaAssegnare(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.findDaAssegnare ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pratica> q = cb.createQuery(Pratica.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(c).distinct(true);
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> protocolloParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> idStatoPraticaParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getDataInizio() != null) {
            dataAperturaParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(c.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(c.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            protocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("protocollo"), protocolloParameter));
        }
        if (filter.getEnteSelezionato() != null) {
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getIdStatoPratica() != null) {
            idStatoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(c.get("idStatoPratica"), idStatoPraticaParameter));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggettoPratica");
            predicates.add(cb.like(path, oggettoParameter));
        }
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
           
        	//Aggiunto 18/01/2016
         if(filter.getOrderColumn().equalsIgnoreCase("ente")){
        		q.orderBy(cb.asc(c.get("idStaging").get("idEnte").get("descrizione")));
        	}else if(filter.getOrderColumn().equalsIgnoreCase("comune")){
        		q.orderBy(cb.asc(c.get("idComune").get("descrizione")));
//        	}else if(filter.getOrderColumn().equalsIgnoreCase("protocollo")){
//        		q.orderBy(cb.asc(c.get("identificativoPratica")));
        	}else{
        		q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        	}                 	
        	// q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
        	//Aggiunto il 18/01/2016
        	if(filter.getOrderColumn().equalsIgnoreCase("ente")){
        		q.orderBy(cb.desc(c.get("idStaging").get("idEnte").get("descrizione")));
        	}else if(filter.getOrderColumn().equalsIgnoreCase("comune")){
        		q.orderBy(cb.desc(c.get("idComune").get("descrizione")));
//        	}else if(filter.getOrderColumn().equalsIgnoreCase("protocollo")){
//        		q.orderBy(cb.desc(c.get("identificativoPratica")));
        	}else{
        		q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        	}         	
           // q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Expression<String> codiceFiscale = c.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = c.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }

        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        predicates.add(cb.isNull(c.get("idUtente")));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Pratica> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(protocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(idStatoPraticaParameter, filter.getIdStatoPratica());
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        List<Pratica> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public List<Pratica> findFiltrate(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.findFiltrate ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pratica> q = cb.createQuery(Pratica.class);
        Root<Pratica> c = q.from(Pratica.class);
        Join joinDatiCatastali = c.join("datiCatastaliList", JoinType.LEFT);
        q.select(c).distinct(true);
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> protocolloParameter = null;
        ParameterExpression<String> fascicoloParameter = null;
        ParameterExpression<String> identificativoPraticheParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> idStatoPraticaParameter = null;
        ParameterExpression<List> praticheDaEscluderParameter = null;
        ParameterExpression<Utente> idUtenteParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;
        ParameterExpression<String> foglioParameter = null;
        ParameterExpression<String> mappaleParameter = null;
        ParameterExpression<String> subalternoParameter = null;
        ParameterExpression<String> comuneCensuarioParameter = null;
        ParameterExpression<String> estensioneParticellaParameter = null;
        ParameterExpression<Integer> idTipoSistemaCatastaleParam = null;
        ParameterExpression<Integer> idTipoUnitaParam = null;
        ParameterExpression<Integer> idTipoParticellaParam = null;
        ParameterExpression<LkComuni> comuneParameter = null;

        ParameterExpression<String> indirizzoParameter = null;
        ParameterExpression<String> civicoParameter = null;
        ParameterExpression<Integer> idPraticaParameter = null;
        ParameterExpression<String> oggettoParameter = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getDataInizio() != null) {
            dataAperturaParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(c.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(c.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
        Expression<String> prot = c.get("protocollo");
        if (!Utils.e(filter.getNumeroProtocollo())) {
            protocolloParameter = cb.parameter(String.class);
            predicates.add(cb.like(prot, protocolloParameter));
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            fascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.like(prot, fascicoloParameter));
        }
        if (!Utils.e(filter.getEnteSelezionato())) {
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getIdStatoPratica() != null) {
            idStatoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(c.get("idStatoPratica"), idStatoPraticaParameter));
        }
        if(filter.getIdOperatoreSelezionato() != null) {
        	idUtenteParameter = cb.parameter(Utente.class);
        	predicates.add(cb.equal(c.get("idUtente"), idUtenteParameter));
        }
        else {
        	if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
                idUtenteParameter = cb.parameter(Utente.class);
                predicates.add(cb.equal(c.get("idUtente"), idUtenteParameter));
            }
        }
        
        //filtro sempre e comunque sui ruoli
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticheParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticheParameter));
        }
        if (!Utils.e(filter.getIdComune())) {
            comuneParameter = cb.parameter(LkComuni.class);
            predicates.add(cb.equal(c.get("idComune"), comuneParameter));
        }

        if (filter.getPraticheDaEscludere() != null) {
            praticheDaEscluderParameter = cb.parameter(List.class);
            List<LkStatoPratica> praticheDaEscludere = filter.getPraticheDaEscludere();
            Collection<Integer> ids = new ArrayList<Integer>();
            for (LkStatoPratica stato : praticheDaEscludere) {
                ids.add(stato.getIdStatoPratica());
            }
            predicates.add(cb.not(c.get("idStatoPratica").get("idStatoPratica").in(ids)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Expression<String> codiceFiscale = c.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = c.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        Expression<String> indirizzo = c.get("indirizziInterventoList").get("indirizzo");
        if (filter.getIndirizzo() != null) {
            indirizzoParameter = cb.parameter(String.class);
            predicates.add(cb.like(cb.upper(indirizzo), indirizzoParameter));
        }
        Expression<String> civico = c.get("indirizziInterventoList").get("civico");
        if (filter.getCivico() != null) {
            civicoParameter = cb.parameter(String.class);
            predicates.add(cb.equal(civico, civicoParameter));
        }
        if (filter.getIdTipoSistemaCatastale() != null) {
            idTipoSistemaCatastaleParam = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoSistemaCatastale").get("idTipoSistemaCatastale"), idTipoSistemaCatastaleParam));
        }

        if (filter.getIdTipoUnita() != null) {
            idTipoUnitaParam = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoUnita").get("idTipoUnita"), idTipoUnitaParam));
        }
        if (filter.getIdTipoParticella() != null) {
            idTipoParticellaParam = cb.parameter(Integer.class);
            predicates.add(cb.equal(joinDatiCatastali.get("idTipoParticella").get("idTipoParticella"), idTipoParticellaParam));
        }

        if (filter.getFoglio() != null) {
            foglioParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("foglio"), foglioParameter));
        }
        if (filter.getMappale() != null) {
            mappaleParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("mappale"), mappaleParameter));
        }
        if (filter.getComuneCensuario() != null) {
            comuneCensuarioParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("comuneCensuario"), comuneCensuarioParameter));
        }
        if (filter.getEstensioneParticella() != null) {
            estensioneParticellaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("estensioneParticella"), estensioneParticellaParameter));
        }
        if (filter.getSubalterno() != null) {
            subalternoParameter = cb.parameter(String.class);
            predicates.add(cb.equal(joinDatiCatastali.get("subalterno"), subalternoParameter));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (filter.getIdPratica() != null) {
        	idPraticaParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idPratica"), idPraticaParameter));
        }
        if (filter.getOggetto() != null) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggettoPratica");
            predicates.add(cb.like(path, oggettoParameter));
        }

        String orderCol = filter.getOrderColumn();
        Path path;
        if (orderCol.equalsIgnoreCase("idStatoPratica")) {
            Join joinStatoPratica = c.join("idStatoPratica");
            path = joinStatoPratica.get("descrizione");
        }
        //Aggiunto il 18/01/2016
         else if(orderCol.equalsIgnoreCase("ente")){    
        	 path=c.get("idStaging").get("idEnte").get("descrizione");
        } 
      //Aggiunto il 18/01/2016
         else if(orderCol.equalsIgnoreCase("fascicolo")){
        	 path=c.get("protocollo"); 
         } 
         else {
            path = c.get(orderCol);
        }
       
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(path));
        } else {
            q.orderBy(cb.desc(path));
        }
        predicates.add(cb.isNotNull(c.get("idUtente")));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Pratica> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(protocolloParameter, "%" + filter.getNumeroProtocollo() + "%");
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(fascicoloParameter, "%" + filter.getNumeroFascicolo() + "%");
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(idStatoPraticaParameter, filter.getIdStatoPratica());
        }
        if(filter.getIdOperatoreSelezionato() != null) {
        	Utente operatoreSelezionato = utentiDao.findUtenteByIdUtente(filter.getIdOperatoreSelezionato());
            query.setParameter(idUtenteParameter, operatoreSelezionato);
        }
        else {
        	if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso() && filter.getConnectedUser() != null ) {
                Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
                query.setParameter(idUtenteParameter, utenteConnesso);
            }
        }
        
        if (filter.getFoglio() != null) {
            query.setParameter(foglioParameter, filter.getFoglio());
        }
        if (filter.getMappale() != null) {
            query.setParameter(mappaleParameter, filter.getMappale());
        }
        if (filter.getSubalterno() != null) {
            query.setParameter(subalternoParameter, filter.getSubalterno());
        }
        if (filter.getComuneCensuario() != null) {
            query.setParameter(comuneCensuarioParameter, filter.getComuneCensuario());
        }
        if (filter.getEstensioneParticella() != null) {
            query.setParameter(estensioneParticellaParameter, filter.getEstensioneParticella());
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticheParameter, filter.getIdentificativoPratica());
        }
        if (filter.getPraticheDaEscludere() != null) {
            List<Integer> daEscludere = new ArrayList<Integer>();
            for (LkStatoPratica s : filter.getPraticheDaEscludere()) {
                daEscludere.add(s.getIdStatoPratica());
            }
            query.setParameter(praticheDaEscluderParameter, daEscludere);
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }

        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        if (!Utils.e(filter.getIdComune())) {
            LkComuni comune = lookupDao.findComuneById(filter.getIdComune());
            query.setParameter(comuneParameter, comune);
        }
        if (!Utils.e(filter.getIndirizzo())) {
            query.setParameter(indirizzoParameter, "%" + filter.getIndirizzo().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (!Utils.e(filter.getCivico())) {
            query.setParameter(civicoParameter, filter.getCivico());
        }
        if (!Utils.e(filter.getIdTipoSistemaCatastale())) {
            query.setParameter(idTipoSistemaCatastaleParam, filter.getIdTipoSistemaCatastale());
        }
        if (!Utils.e(filter.getIdTipoUnita())) {
            query.setParameter(idTipoUnitaParam, filter.getIdTipoUnita());
        }
        if (!Utils.e(filter.getIdTipoParticella())) {
            query.setParameter(idTipoParticellaParam, filter.getIdTipoParticella());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getIdPratica())) {
            query.setParameter(idPraticaParameter, filter.getIdPratica());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }

        List<Pratica> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public Long countDaAprireFiltrate(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.countDaAprireFiltrate ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(cb.countDistinct(c));
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> statoPraticaParameter = null;
        ParameterExpression<List> praticheDaEscluderParameter = null;
        ParameterExpression<Utente> idUtenteParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;

        if (filter.getDataInizio() != null) {
            dataAperturaParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(c.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(c.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("protocollo"), numeroProtocolloParameter));
        }
        if (filter.getEnteSelezionato() != null) {
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getIdStatoPratica() != null) {
            statoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(c.get("idStatoPratica"), statoPraticaParameter));
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            idUtenteParameter = cb.parameter(Utente.class);
            predicates.add(cb.equal(c.get("idUtente"), idUtenteParameter));
        }
        if (filter.getPraticheDaEscludere() != null) {
            praticheDaEscluderParameter = cb.parameter(List.class);
            Collection<LkStatoPratica> praticheDaEscludere = filter.getPraticheDaEscludere();
            predicates.add(cb.not(c.get("idStatoPratica").in(praticheDaEscludere)));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggettoPratica");
            predicates.add(cb.like(path, oggettoParameter));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Expression<String> codiceFiscale = c.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = c.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }

        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        predicates.add(cb.isNotNull(c.get("idUtente")));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(statoPraticaParameter, filter.getIdStatoPratica());
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(idUtenteParameter, utenteConnesso);
        }
        if (filter.getPraticheDaEscludere() != null) {
            query.setParameter(praticheDaEscluderParameter, filter.getPraticheDaEscludere());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        Long count = query.getSingleResult();
        return count;
    }

    public Long countScadenze(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.countScadenze ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Scadenze> c = q.from(Scadenze.class);
        q.select(cb.count(c));
        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<LkStatiScadenze> statoScadenzaParameter;
        ParameterExpression<Utente> utenteConnessoParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkComuni> comuneParameter = null;
        ParameterExpression<LkStatoPratica> idStatoPraticaParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getAnnoRiferimento() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            Expression<Integer> annoRiferimento = pratica.get("annoRiferimento");
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (filter.getDataInizio() != null) {
            Expression<Date> dataInizioScadenza = c.get("dataScadenza");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (filter.getDataFine() != null) {
            Expression<Date> dataFineScadenza = c.get("dataScadenza");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (filter.getIdStatoScadenza() != null) {
            statoScadenzaParameter = cb.parameter(LkStatiScadenze.class);
            predicates.add(cb.equal(c.get("idStato"), statoScadenzaParameter));
        } else {
            //Di default cerco le pratiche aperte
            LkStatiScadenze praticheAperte = lookupDao.findStatoScadenzaById("A");
            statoScadenzaParameter = cb.parameter(LkStatiScadenze.class);
            predicates.add(cb.equal(c.get("idStato"), praticheAperte));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("idPratica").get("protocollo"), numeroProtocolloParameter));
        }

        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            utenteConnessoParameter = cb.parameter(Utente.class);
            Expression<Utente> pratica = c.get("idPratica").get("idUtente");
            predicates.add(cb.equal(pratica, utenteConnessoParameter));
        }

        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Join joinScadenzaPratiche = c.join("idPratica");
            Join praticheAnagrafica = joinScadenzaPratiche.join("praticaAnagraficaList");
            Join anagraficaJoin = praticheAnagrafica.join("anagrafica");
            Expression<String> codiceFiscale = anagraficaJoin.get("codiceFiscale");
            Expression<String> partitaIva = anagraficaJoin.get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("idPratica").get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        //Filtro per ruolo utente
        if (filter.getConnectedUser() != null) {
            Join joinPratica = c.join("idPratica").join("idProcEnte");
            Join procedimentiUtenti = joinPratica.join("utenteRuoloProcedimentoList").join("utenteRuoloEnte");
            Predicate p = cb.equal(procedimentiUtenti.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }

        if (filter.getPraticheDaEscludere() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            List<LkStatoPratica> praticheDaEscludere = filter.getPraticheDaEscludere();
            Collection<Integer> ids = new ArrayList<Integer>();
            for (LkStatoPratica stato : praticheDaEscludere) {
                ids.add(stato.getIdStatoPratica());
            }
            predicates.add(cb.or(cb.not(pratica.get("idStatoPratica").get("idStatoPratica").in(ids)), cb.isNotNull(pratica.get("idUtente"))));
        }

        if (filter.getIdStatoPratica() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            idStatoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(pratica.get("idStatoPratica"), idStatoPraticaParameter));

        }
        if (!Utils.e(filter.getIdComune())) {
            Path<Pratica> pratica = c.get("idPratica");
            comuneParameter = cb.parameter(LkComuni.class);
            predicates.add(cb.equal(pratica.get("idComune"), comuneParameter));
        }
        if (!Utils.e(filter.getEnteSelezionato())) {
            Path<Pratica> pratica = c.get("idPratica");
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(pratica.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (filter.getIdStatoScadenza() != null) {
            query.setParameter(statoScadenzaParameter, filter.getIdStatoScadenza());
        } else {
            LkStatiScadenze scadenzaChiusa = lookupDao.findStatoScadenzaById(StatiScadenzaConstants.CHIUSA);
            query.setParameter(statoScadenzaParameter, scadenzaChiusa);
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(utenteConnessoParameter, utenteConnesso);
        }

        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (!Utils.e(filter.getIdComune())) {
            LkComuni comune = lookupDao.findComuneById(filter.getIdComune());
            query.setParameter(comuneParameter, comune);
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(idStatoPraticaParameter, filter.getIdStatoPratica());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        Long count = (Long) Utils.getSingleResult(query);
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public Long countPraticheProtocollo(Filter filter, Enti ente) {
        Log.SQL.info("CHIAMATO PraticaDao.countPraticheProtocollo ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<PraticheProtocollo> c = q.from(PraticheProtocollo.class);
        q.select(cb.count(c));
        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<String> numeroFascicoloParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> enteRiferimentoParameter = null;
        ParameterExpression<String> identificativoPraticaParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<Integer> annoRiferimentoParameter = null;

        //Verifico l'intervallo relativo alla data di ricezione
        if (!Utils.e(filter.getDataInizio())) {
            Expression<Date> dataInizioScadenza = c.get("dataProtocollazione");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (!Utils.e(filter.getDataFine())) {
            Expression<Date> dataFineScadenza = c.get("dataProtocollazione");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (!Utils.e(filter.getNumeroFascicolo())) {
            numeroFascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nFascicolo"), numeroFascicoloParameter));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nProtocollo"), numeroProtocolloParameter));
        }

        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggetto");
            predicates.add(cb.like(path, oggettoParameter));
        }

        if (!Utils.e(filter.getEnteRiferimento())) {
            enteRiferimentoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("enteRiferimento");
            predicates.add(cb.like(path, enteRiferimentoParameter));
        }
        Expression<Integer> annoRiferimento = c.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        //Imposto il filtro per unita organizzativa
        if (!filter.getSearchByUtenteConnesso()) {
            predicates.add(cb.isNotNull(c.get("destinatario")));
            predicates.add(cb.equal(c.get("destinatario"), ente.getUnitaOrganizzativa()));
        } else {
            List<String> entiAutorizzati = new ArrayList<String>();
            if (filter.getConnectedUser().getUtenteRuoloEnteList() != null) {
                List<Enti> enti = new ArrayList<Enti>();
                for (UtenteRuoloEnte ruolo : filter.getConnectedUser().getUtenteRuoloEnteList()) {
                    Enti e = ruolo.getIdEnte();
                    if (!enti.contains(e)) {
                        enti.add(e);
                        entiAutorizzati.add(e.getUnitaOrganizzativa());
                    }
                }
            }
            List<Predicate> uoValide = new ArrayList<Predicate>();
            for (String e : entiAutorizzati) {
                Predicate uo = cb.equal(c.get("destinatario"), e);
                uoValide.add(uo);
            }
            cb.in(cb.or(uoValide.toArray(new Predicate[uoValide.size()])));
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticaParameter));
        }

        if (!filter.getTipiDocumentiProtocollo().isEmpty()) {
            predicates.add(c.get("tipoDocumento").in(filter.getTipiDocumentiProtocollo()));
        }

        //Escludo tutte le pratiche già gestite in Cross
        Expression<Date> dataPresaInCaricoCross = c.get("dataPresaInCaricoCross");
        predicates.add(cb.isNull(dataPresaInCaricoCross));

        //AGGIUNTA AND idPratica == null, cioe che la pratica non e' stata completata
        Expression<Pratica> idPratica = c.get("idPratica");
        predicates.add(cb.isNull(idPratica));

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (!Utils.e(filter.getDataInizio())) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (!Utils.e(filter.getDataFine())) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(numeroFascicoloParameter, filter.getNumeroFascicolo());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }

        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getEnteRiferimento())) {
            query.setParameter(enteRiferimentoParameter, "%" + filter.getEnteRiferimento() + "%");
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticaParameter, filter.getIdentificativoPratica());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        Long count = (Long) Utils.getSingleResult(query);
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public Long countDocumentiProtocollo(Filter filter, Enti ente) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.countDocumentiProtocollo ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<PraticheProtocollo> c = q.from(PraticheProtocollo.class);
        c.join("idUtentePresaInCarico", JoinType.LEFT);
        q.select(cb.count(c));
        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<String> numeroFascicoloParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> enteRiferimentoParameter = null;
        ParameterExpression<String> identificativoPraticaParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Verifico l'intervallo relativo alla data di ricezione
        if (!Utils.e(filter.getDataInizio())) {
            Expression<Date> dataInizioScadenza = c.get("dataRicezione");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (!Utils.e(filter.getDataFine())) {
            Expression<Date> dataFineScadenza = c.get("dataRicezione");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (!Utils.e(filter.getNumeroFascicolo())) {
            numeroFascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nFascicolo"), numeroFascicoloParameter));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nProtocollo"), numeroProtocolloParameter));
        }

        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggetto");
            predicates.add(cb.like(path, oggettoParameter));
        }

        if (!Utils.e(filter.getEnteRiferimento())) {
            enteRiferimentoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("enteRiferimento");
            predicates.add(cb.like(path, enteRiferimentoParameter));
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticaParameter));
        }

        if (!filter.getTipiDocumentiProtocollo().isEmpty()) {
            //Imposto il filtro per tipologia di documento
            predicates.add(c.get("tipoDocumento").in(filter.getTipiDocumentiProtocollo()));
        }

        //Imposto il filtro per unita organizzativa
        if (!filter.getSearchByUtenteConnesso()) {
            if (ente.getUnitaOrganizzativa() != null) {
                predicates.add(cb.equal(c.get("destinatario"), ente.getUnitaOrganizzativa()));
            }
        } else {
            List<String> entiAutorizzati = new ArrayList<String>();
            if (filter.getConnectedUser().getUtenteRuoloEnteList() != null) {
                List<Enti> enti = new ArrayList<Enti>();
                for (UtenteRuoloEnte ruolo : filter.getConnectedUser().getUtenteRuoloEnteList()) {
                    Enti e = ruolo.getIdEnte();
                    if (!enti.contains(e)) {
                        enti.add(e);
                        entiAutorizzati.add(e.getUnitaOrganizzativa());
                    }
                }
            }
            List<Predicate> uoValide = new ArrayList<Predicate>();
            for (String e : entiAutorizzati) {
                Predicate uo = cb.equal(c.get("destinatario"), e);
                uoValide.add(uo);
            }
            cb.in(cb.or(uoValide.toArray(new Predicate[uoValide.size()])));
        }
        //Escludo tutte le pratiche già gestite in Cross
        Expression<Date> dataPresaInCaricoCross = c.get("dataPresaInCaricoCross");
        predicates.add(cb.isNull(dataPresaInCaricoCross));

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        if (!Utils.e(filter.getDataInizio())) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (!Utils.e(filter.getDataFine())) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(numeroFascicoloParameter, filter.getNumeroFascicolo());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }

        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getEnteRiferimento())) {
            query.setParameter(enteRiferimentoParameter, "%" + filter.getEnteRiferimento() + "%");
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticaParameter, filter.getIdentificativoPratica());
        }

        Long count = (Long) Utils.getSingleResult(query);
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public List<PraticheProtocollo> findDocumentiProtocollo(Filter filter, Enti ente) throws Exception {
        Log.SQL.info("CHIAMATO PraticaDao.findDocumentiProtocollo ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PraticheProtocollo> q = cb.createQuery(PraticheProtocollo.class);
        Root<PraticheProtocollo> c = q.from(PraticheProtocollo.class);
        c.join("idUtentePresaInCarico", JoinType.LEFT);
        q.select(c);
        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<String> numeroFascicoloParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> enteRiferimentoParameter = null;
        ParameterExpression<String> identificativoPraticaParameter = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Verifico l'intervallo relativo alla data di ricezione
        if (!Utils.e(filter.getDataInizio())) {
            Expression<Date> dataInizioScadenza = c.get("dataRicezione");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (!Utils.e(filter.getDataFine())) {
            Expression<Date> dataFineScadenza = c.get("dataRicezione");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (!Utils.e(filter.getNumeroFascicolo())) {
            numeroFascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nFascicolo"), numeroFascicoloParameter));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nProtocollo"), numeroProtocolloParameter));
        }

        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggetto");
            predicates.add(cb.like(path, oggettoParameter));
        }

        if (!Utils.e(filter.getEnteRiferimento())) {
            enteRiferimentoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("enteRiferimento");
            predicates.add(cb.like(path, enteRiferimentoParameter));
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticaParameter));
        }
        //Imposto il filtro per unita organizzativa
        if (!filter.getSearchByUtenteConnesso()) {
            if (ente.getUnitaOrganizzativa() != null) {
                predicates.add(cb.equal(c.get("destinatario"), ente.getUnitaOrganizzativa()));
            }
        } else {
            List<String> entiAutorizzati = new ArrayList<String>();
            if (filter.getConnectedUser().getUtenteRuoloEnteList() != null) {
                List<Enti> enti = new ArrayList<Enti>();
                for (UtenteRuoloEnte ruolo : filter.getConnectedUser().getUtenteRuoloEnteList()) {
                    Enti e = ruolo.getIdEnte();
                    if (!enti.contains(e)) {
                        enti.add(e);
                        entiAutorizzati.add(e.getUnitaOrganizzativa());
                    }
                }
            }
            List<Predicate> uoValide = new ArrayList<Predicate>();
            for (String e : entiAutorizzati) {
                Predicate uo = cb.equal(c.get("destinatario"), e);
                uoValide.add(uo);
            }
            cb.in(cb.or(uoValide.toArray(new Predicate[uoValide.size()])));
        }

        if (!filter.getTipiDocumentiProtocollo().isEmpty()) {
            //Imposto il filtro per tipologia di documento
            predicates.add(c.get("tipoDocumento").in(filter.getTipiDocumentiProtocollo()));
        }

        if (filter.getOrderDirection() != null && filter.getOrderColumn() != null && filter.getOrderDirection().equalsIgnoreCase("asc")) {
            if (filter.getOrderColumn().equals("desUtentePresaInCarico")) {
                q.orderBy(cb.asc(c.get("idUtentePresaInCarico").get("cognome")));
            } else if (filter.getOrderColumn().equals("anno")) {
                q.orderBy(cb.asc(c.get("annoRiferimento")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else if (filter.getOrderDirection() != null && filter.getOrderColumn() != null && filter.getOrderDirection().equalsIgnoreCase("desc")) {
            if (filter.getOrderColumn().equals("desUtentePresaInCarico")) {
                q.orderBy(cb.desc(c.get("idUtentePresaInCarico").get("cognome")));
            } else if (filter.getOrderColumn().equals("anno")) {
                q.orderBy(cb.desc(c.get("annoRiferimento")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        } else {
            //di default ordino per data ricezione crescente
            q.orderBy(cb.asc(c.get("dataRicezione")));
        }

        //Escludo tutte le pratiche già gestite in Cross
        Expression<Date> dataPresaInCaricoCross = c.get("dataPresaInCaricoCross");
        predicates.add(cb.isNull(dataPresaInCaricoCross));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<PraticheProtocollo> query = em.createQuery(q);
        if (!Utils.e(filter.getDataInizio())) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (!Utils.e(filter.getDataFine())) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(numeroFascicoloParameter, filter.getNumeroFascicolo());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }

        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getEnteRiferimento())) {
            query.setParameter(enteRiferimentoParameter, "%" + filter.getEnteRiferimento() + "%");
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticaParameter, filter.getIdentificativoPratica());
        }

        query.setMaxResults(filter.getLimit());
        query.setFirstResult(filter.getOffset());

        List<PraticheProtocollo> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public List<PraticheProtocollo> findPraticheProtocollo(Filter filter, Enti ente) {
        Log.SQL.info("CHIAMATO PraticaDao.findPraticheProtocollo ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PraticheProtocollo> q = cb.createQuery(PraticheProtocollo.class);
        Root<PraticheProtocollo> c = q.from(PraticheProtocollo.class);
        q.select(c);
        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<String> numeroFascicoloParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> enteRiferimentoParameter = null;
        ParameterExpression<String> identificativoPraticaParameter = null;
        ParameterExpression<Integer> annoRiferimento = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Verifico l'intervallo relativo alla data di ricezione
        if (!Utils.e(filter.getDataInizio())) {
            Expression<Date> dataInizioScadenza = c.get("dataProtocollazione");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (!Utils.e(filter.getDataFine())) {
            Expression<Date> dataFineScadenza = c.get("dataProtocollazione");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (!Utils.e(filter.getAnnoRiferimento())) {
            annoRiferimento = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("annoRiferimento"), annoRiferimento));
        }

        if (!Utils.e(filter.getNumeroFascicolo())) {
            numeroFascicoloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nFascicolo"), numeroFascicoloParameter));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("nProtocollo"), numeroProtocolloParameter));
        }

        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("oggetto");
            predicates.add(cb.like(path, oggettoParameter));
        }

        if (!Utils.e(filter.getEnteRiferimento())) {
            enteRiferimentoParameter = cb.parameter(String.class);
            Expression<String> path = c.get("enteRiferimento");
            predicates.add(cb.like(path, enteRiferimentoParameter));
        }

        if (!Utils.e(filter.getIdentificativoPratica())) {
            identificativoPraticaParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("identificativoPratica"), identificativoPraticaParameter));
        }

        if (!filter.getTipiDocumentiProtocollo().isEmpty()) {
            predicates.add(c.get("tipoDocumento").in(filter.getTipiDocumentiProtocollo()));
        }

        if (!Utils.e(filter.getOrderDirection()) && !Utils.e(filter.getOrderColumn()) && filter.getOrderDirection().equalsIgnoreCase("asc")) {
            if (filter.getOrderColumn().equals("desUtentePresaInCarico")) {
                q.orderBy(cb.asc(c.get("idUtentePresaInCarico").get("cognome")));
            } else {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            }
        } else if (!Utils.e(filter.getOrderDirection()) && !Utils.e(filter.getOrderColumn()) && filter.getOrderDirection().equalsIgnoreCase("desc")) {
            if (filter.getOrderColumn().equals("desUtentePresaInCarico")) {
                q.orderBy(cb.desc(c.get("idUtentePresaInCarico").get("cognome")));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        } else {
            //di default ordino per data protocollazione e id protocollo crescente
            List<Order> defaultOrder = new ArrayList<Order>();
            defaultOrder.add(cb.desc(c.get("annoRiferimento")));
            defaultOrder.add(cb.desc(c.get("nProtocollo")));
            q.orderBy(defaultOrder);
        }

        //Imposto il filtro per unita organizzativa
        if (!filter.getSearchByUtenteConnesso()) {
            predicates.add(cb.isNotNull(c.get("destinatario")));
            predicates.add(cb.equal(c.get("destinatario"), ente.getUnitaOrganizzativa()));
        } else {
            List<String> entiAutorizzati = new ArrayList<String>();
            if (filter.getConnectedUser().getUtenteRuoloEnteList() != null) {
                List<Enti> enti = new ArrayList<Enti>();
                for (UtenteRuoloEnte ruolo : filter.getConnectedUser().getUtenteRuoloEnteList()) {
                    Enti e = ruolo.getIdEnte();
                    if (!enti.contains(e)) {
                        enti.add(e);
                        entiAutorizzati.add(e.getUnitaOrganizzativa());
                    }
                }
            }
            List<Predicate> uoValide = new ArrayList<Predicate>();
            for (String e : entiAutorizzati) {
                Predicate uo = cb.equal(c.get("destinatario"), e);
                uoValide.add(uo);
            }
            cb.in(cb.or(uoValide.toArray(new Predicate[uoValide.size()])));
        }

        //Escludo tutte le pratiche già gestite in Cross
        Expression<Date> dataPresaInCaricoCross = c.get("dataPresaInCaricoCross");
        predicates.add(cb.isNull(dataPresaInCaricoCross));
        //Se idPratica == null, la pratica non e' stata portata in cross
        Expression<Pratica> idPratica = c.get("idPratica");
        predicates.add(cb.isNull(idPratica));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<PraticheProtocollo> query = em.createQuery(q);
        if (!Utils.e(filter.getDataInizio())) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (!Utils.e(filter.getDataFine())) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimento, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getNumeroFascicolo())) {
            query.setParameter(numeroFascicoloParameter, filter.getNumeroFascicolo());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }

        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getEnteRiferimento())) {
            query.setParameter(enteRiferimentoParameter, "%" + filter.getEnteRiferimento() + "%");
        }
        if (!Utils.e(filter.getIdentificativoPratica())) {
            query.setParameter(identificativoPraticaParameter, filter.getIdentificativoPratica());
        }

        query.setMaxResults(filter.getLimit());
        query.setFirstResult(filter.getOffset());

        List<PraticheProtocollo> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public PraticheProtocollo findPraticaProtocolloById(Integer idProtocollo) {
        Query query = em.createNamedQuery("PraticheProtocollo.findByIdProtocollo");
        query.setParameter("idProtocollo", idProtocollo);
        PraticheProtocollo pratica = (PraticheProtocollo) Utils.getSingleResult(query);
        return pratica;
    }

    public PraticheProtocollo findPraticaProtocolloByIdentificativoPratica(String identificativoPratica) {
        Query query = em.createNamedQuery("PraticheProtocollo.findByIdentificativoPratica");
        query.setParameter("identificativoPratica", identificativoPratica);
        PraticheProtocollo pratica = (PraticheProtocollo) Utils.getSingleResult(query);
        return pratica;
    }

    public PraticheProtocollo findPraticaProtocolloByAnnoProtocollo(Integer anno, String protocollo) {
        String qu = "SELECT p FROM PraticheProtocollo p WHERE"
                + " p.annoRiferimento = :anno AND "
                + " p.nProtocollo = :protocollo ";
        Query query = em.createQuery(qu);
        query.setParameter("anno", anno);
        query.setParameter("protocollo", protocollo);
        PraticheProtocollo pratica = (PraticheProtocollo) Utils.getSingleResult(query);
        return pratica;
    }

    public List<Scadenze> findScadenzeByIdPratica(Integer idPratica) {
        Log.SQL.info("CHIAMATO PraticaDao.findScadenzeByIdPratica ");
        Query query = em.createNamedQuery("Scadenze.findByIdPratica");
        query.setParameter("idPratica", idPratica);
        List<Scadenze> scadenze = query.getResultList();
        return scadenze;
    }

    public List<Scadenze> findScadenze(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.findScadenze ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Scadenze> q = cb.createQuery(Scadenze.class);
        Root<Scadenze> c = q.from(Scadenze.class);
        q.select(c).distinct(true);

        ParameterExpression<Date> dataInizioParameter = null;
        ParameterExpression<Date> dataFineParameter = null;
        ParameterExpression<String> numeroProtocolloParameter = null;
        ParameterExpression<LkStatiScadenze> statoScadenzaParameter;
        ParameterExpression<Utente> utenteConnessoParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkComuni> comuneParameter = null;
        ParameterExpression<LkStatoPratica> idStatoPraticaParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (filter.getAnnoRiferimento() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            Expression<Integer> annoRiferimento = pratica.get("annoRiferimento");
            annoRiferimentoParameter = cb.parameter(Integer.class);
            predicates.add(cb.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (filter.getPraticheDaEscludere() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            List<LkStatoPratica> praticheDaEscludere = filter.getPraticheDaEscludere();
            Collection<Integer> ids = new ArrayList<Integer>();
            for (LkStatoPratica stato : praticheDaEscludere) {
                ids.add(stato.getIdStatoPratica());
            }
            predicates.add(cb.or(cb.not(pratica.get("idStatoPratica").get("idStatoPratica").in(ids)), cb.isNotNull(pratica.get("idUtente"))));
        }
        if (filter.getIdStatoPratica() != null) {
            Path<Pratica> pratica = c.get("idPratica");
            idStatoPraticaParameter = cb.parameter(LkStatoPratica.class);
            predicates.add(cb.equal(pratica.get("idStatoPratica"), idStatoPraticaParameter));

        }
        if (!Utils.e(filter.getIdComune())) {
            Path<Pratica> pratica = c.get("idPratica");
            comuneParameter = cb.parameter(LkComuni.class);
            predicates.add(cb.equal(pratica.get("idComune"), comuneParameter));
        }
        if (!Utils.e(filter.getEnteSelezionato())) {
            Path<Pratica> pratica = c.get("idPratica");
            enteSelezionatoParameter = cb.parameter(Enti.class);
            predicates.add(cb.equal(pratica.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getDataInizio() != null) {
            Expression<Date> dataInizioScadenza = c.get("dataFineScadenza");
            dataInizioParameter = cb.parameter(Date.class);
            predicates.add(cb.greaterThanOrEqualTo(dataInizioScadenza, dataInizioParameter));
        }
        if (filter.getDataFine() != null) {
            Expression<Date> dataFineScadenza = c.get("dataFineScadenza");
            dataFineParameter = cb.parameter(Date.class);
            predicates.add(cb.lessThanOrEqualTo(dataFineScadenza, dataFineParameter));
        }

        if (filter.getIdStatoScadenza() != null) {
            statoScadenzaParameter = cb.parameter(LkStatiScadenze.class);
            predicates.add(cb.equal(c.get("idStato"), statoScadenzaParameter));
        } else {
            statoScadenzaParameter = cb.parameter(LkStatiScadenze.class);
            predicates.add(cb.notEqual(c.get("idStato"), statoScadenzaParameter));
        }

        if (!Utils.e(filter.getNumeroProtocollo())) {
            numeroProtocolloParameter = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("idPratica").get("protocollo"), numeroProtocolloParameter));
        }

        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            utenteConnessoParameter = cb.parameter(Utente.class);
            Expression<Utente> pratica = c.get("idPratica").get("idUtente");
            predicates.add(cb.equal(pratica, utenteConnessoParameter));
        }

        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = cb.parameter(String.class);
            Join joinScadenzaPratiche = c.join("idPratica");
            Join praticheAnagrafica = joinScadenzaPratiche.join("praticaAnagraficaList");
            Join anagraficaJoin = praticheAnagrafica.join("anagrafica");
            Expression<String> codiceFiscale = anagraficaJoin.get("codiceFiscale");
            Expression<String> partitaIva = anagraficaJoin.get("partitaIva");
            predicates.add(
                    cb.or(
                            cb.like(codiceFiscale, ricercaAnagraficaCF),
                            cb.like(partitaIva, ricercaAnagraficaCF)));
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = cb.parameter(String.class);
            Path<Anagrafica> anagrafica = c.get("idPratica").get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    cb.or(
                            cb.like(cb.upper(cb.trim(cb.concat(nome, cognome))), cb.upper(cb.trim(ricercaAnagraficaNome))),
                            cb.like(cb.upper(cb.trim(denominazione)), cb.upper(cb.trim(ricercaAnagraficaNome)))));
        }
        //Filtro per ruolo utente
        if (filter.getConnectedUser() != null) {
            Join joinPratica = c.join("idPratica").join("idProcEnte");
            Join procedimentiUtenti = joinPratica.join("utenteRuoloProcedimentoList").join("utenteRuoloEnte");
            Predicate p = cb.equal(procedimentiUtenti.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        if (filter.getOrderDirection() != null && filter.getOrderColumn() != null && filter.getOrderDirection().equalsIgnoreCase("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else if (filter.getOrderDirection() != null && filter.getOrderColumn() != null && filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else {
            //di default ordino per fine data scadenza crescente
            q.orderBy(cb.asc(c.get("dataFineScadenza")));
        }

        TypedQuery<Scadenze> query = em.createQuery(q);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataInizioParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataFineParameter, filter.getDataFine());
        }
        if (filter.getIdStatoScadenza() != null) {
            query.setParameter(statoScadenzaParameter, filter.getIdStatoScadenza());
        } else {
            LkStatiScadenze scadenzaChiusa = lookupDao.findStatoScadenzaById(StatiScadenzaConstants.CHIUSA);
            query.setParameter(statoScadenzaParameter, scadenzaChiusa);
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(numeroProtocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(utenteConnessoParameter, utenteConnesso);
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (!Utils.e(filter.getIdComune())) {
            LkComuni comune = lookupDao.findComuneById(filter.getIdComune());
            query.setParameter(comuneParameter, comune);
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(idStatoPraticaParameter, filter.getIdStatoPratica());
        }

        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        query.setMaxResults(filter.getLimit());
        query.setFirstResult(filter.getOffset());

        List<Scadenze> scadenze = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return scadenze;
    }

    public List<Pratica> findDaAprireFiltrate(Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.findDaAprireFiltrate ");
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Pratica> criteriaQuery = criteriaBuilder.createQuery(Pratica.class);
        Root<Pratica> praticaRoot = criteriaQuery.from(Pratica.class);
        criteriaQuery.select(praticaRoot);
        criteriaQuery.distinct(true);
        ParameterExpression<Date> dataAperturaParameter = null;
        ParameterExpression<Date> dataChiusuraParameter = null;
        ParameterExpression<String> protocolloParameter = null;
        ParameterExpression<Enti> enteSelezionatoParameter = null;
        ParameterExpression<LkStatoPratica> idStatoPraticaParameter = null;
        ParameterExpression<Utente> idUtenteParameter = null;
        ParameterExpression<Integer> annoRiferimentoParameter = null;
        ParameterExpression<String> oggettoParameter = null;
        ParameterExpression<String> ricercaAnagraficaCF = null;
        ParameterExpression<String> ricercaAnagraficaNome = null;

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getDataInizio() != null) {
            dataAperturaParameter = criteriaBuilder.parameter(Date.class);
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(praticaRoot.<Date>get("dataRicezione"), dataAperturaParameter));
        }
        if (filter.getDataFine() != null) {
            dataChiusuraParameter = criteriaBuilder.parameter(Date.class);
            predicates.add(criteriaBuilder.lessThanOrEqualTo(praticaRoot.<Date>get("dataRicezione"), dataChiusuraParameter));
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            protocolloParameter = criteriaBuilder.parameter(String.class);
            predicates.add(criteriaBuilder.equal(praticaRoot.get("protocollo"), protocolloParameter));
        }
        if (filter.getEnteSelezionato() != null) {
            enteSelezionatoParameter = criteriaBuilder.parameter(Enti.class);
            predicates.add(criteriaBuilder.equal(praticaRoot.get("idProcEnte").get("idEnte"), enteSelezionatoParameter));
        }
        if (filter.getIdStatoPratica() != null) {
            idStatoPraticaParameter = criteriaBuilder.parameter(LkStatoPratica.class);
            predicates.add(criteriaBuilder.equal(praticaRoot.get("idStatoPratica"), idStatoPraticaParameter));
        }
        predicates.add(criteriaBuilder.isNotNull(praticaRoot.get("idUtente")));
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            idUtenteParameter = criteriaBuilder.parameter(Utente.class);
            predicates.add(criteriaBuilder.equal(praticaRoot.get("idUtente"), idUtenteParameter));
        }
        Expression<Integer> annoRiferimento = praticaRoot.get("annoRiferimento");
        if (filter.getAnnoRiferimento() != null) {
            annoRiferimentoParameter = criteriaBuilder.parameter(Integer.class);
            predicates.add(criteriaBuilder.equal(annoRiferimento, annoRiferimentoParameter));
        }
        if (!Utils.e(filter.getOggetto())) {
            oggettoParameter = criteriaBuilder.parameter(String.class);
            Expression<String> path = praticaRoot.get("oggettoPratica");
            predicates.add(criteriaBuilder.like(path, oggettoParameter));
        }
        //Filtro per ruolo utente
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = praticaRoot.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = criteriaBuilder.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getOrderDirection() != null && filter.getOrderColumn() != null) {
            if (filter.getOrderDirection().equalsIgnoreCase("asc")) {
            	
            	//Aggiunto il 15/01/2016
            	if(filter.getOrderColumn().equalsIgnoreCase("ente")){
            		criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get("idStaging").get("idEnte").get("descrizione")));
            	}else if(filter.getOrderColumn().equalsIgnoreCase("comune")){
            		criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get("idComune").get("descrizione")));
//            	}else if(filter.getOrderColumn().equalsIgnoreCase("protocollo")){
//            		criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get("identificativoPratica")));
            	}else{
            		criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get(filter.getOrderColumn())));
            	}                
            	
            	//criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get(filter.getOrderColumn())));
                                   
            } else {

            	//Aggiunto il 15/01/2016
            	if(filter.getOrderColumn().equalsIgnoreCase("ente")){
            		criteriaQuery.orderBy(criteriaBuilder.desc(praticaRoot.get("idStaging").get("idEnte").get("descrizione")));
            	}else if(filter.getOrderColumn().equalsIgnoreCase("comune")){
            		criteriaQuery.orderBy(criteriaBuilder.desc(praticaRoot.get("idComune").get("descrizione")));
//            	}else if(filter.getOrderColumn().equalsIgnoreCase("protocollo")){
//            		criteriaQuery.orderBy(criteriaBuilder.desc(praticaRoot.get("identificativoPratica")));
            	}else{
            		criteriaQuery.orderBy(criteriaBuilder.desc(praticaRoot.get(filter.getOrderColumn())));
            	}  
            	           	            	
               // criteriaQuery.orderBy(criteriaBuilder.desc(praticaRoot.get(filter.getOrderColumn())));
            }
        } else {
            //Di default ordino per data ricezione
            criteriaQuery.orderBy(criteriaBuilder.asc(praticaRoot.get("dataRicezione")));
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            ricercaAnagraficaCF = criteriaBuilder.parameter(String.class);
            Expression<String> codiceFiscale = praticaRoot.get("praticaAnagraficaList").get("anagrafica").get("codiceFiscale");
            Expression<String> partitaIva = praticaRoot.get("praticaAnagraficaList").get("anagrafica").get("partitaIva");
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.like(codiceFiscale, ricercaAnagraficaCF),
                            criteriaBuilder.like(partitaIva, ricercaAnagraficaCF)));
        }

        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            ricercaAnagraficaNome = criteriaBuilder.parameter(String.class);
            Path<Anagrafica> anagrafica = praticaRoot.get("praticaAnagraficaList").get("anagrafica");
            Expression<String> nome = anagrafica.get("cognome");
            Expression<String> cognome = anagrafica.get("nome");
            Expression<String> denominazione = anagrafica.get("denominazione");
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.upper(criteriaBuilder.trim(criteriaBuilder.concat(nome, cognome))), criteriaBuilder.upper(criteriaBuilder.trim(ricercaAnagraficaNome))),
                            criteriaBuilder.like(criteriaBuilder.upper(criteriaBuilder.trim(denominazione)), criteriaBuilder.upper(criteriaBuilder.trim(ricercaAnagraficaNome)))));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Pratica> query = em.createQuery(criteriaQuery);
        if (filter.getDataInizio() != null) {
            query.setParameter(dataAperturaParameter, filter.getDataInizio());
        }
        if (filter.getDataFine() != null) {
            query.setParameter(dataChiusuraParameter, filter.getDataFine());
        }
        if (!Utils.e(filter.getNumeroProtocollo())) {
            query.setParameter(protocolloParameter, filter.getNumeroProtocollo());
        }
        if (filter.getIdStatoPratica() != null) {
            query.setParameter(idStatoPraticaParameter, filter.getIdStatoPratica());
        }
        if (filter.getEnteSelezionato() != null) {
            query.setParameter(enteSelezionatoParameter, filter.getEnteSelezionato());
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(idUtenteParameter, utenteConnesso);
        }
        if (!Utils.e(filter.getAnnoRiferimento())) {
            query.setParameter(annoRiferimentoParameter, filter.getAnnoRiferimento());
        }
        if (!Utils.e(filter.getOggetto())) {
            query.setParameter(oggettoParameter, "%" + filter.getOggetto() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaCF())) {
            query.setParameter(ricercaAnagraficaCF, "%" + filter.getRicercaAnagraficaCF() + "%");
        }
        if (!Utils.e(filter.getRicercaAnagraficaNome())) {
            query.setParameter(ricercaAnagraficaNome, "%" + filter.getRicercaAnagraficaNome().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        query.setMaxResults(filter.getLimit());
        query.setFirstResult(filter.getOffset());

        List<Pratica> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public List<PraticheEventi> findAllEventsForPratica(Integer idPratica) {
        Log.SQL.info("CHIAMATO PraticaDao.findAllEventsForPratica");
        Query query = em.createQuery("SELECT pe FROM PraticheEventi pe WHERE "
                + "pe.idPratica.idPratica = :idPratica "
                + "ORDER BY pe.dataEvento");
        query.setParameter("idPratica", idPratica);
        List<PraticheEventi> eventi = query.getResultList();
        return eventi;
    }

    public ProcessiEventi findByIdEvento(Integer idEvento) {
        Log.SQL.info("CHIAMATO PraticaDao.findByIdEvento ");
        Query query = em.createNamedQuery("ProcessiEventi.findByIdEvento");
        query.setParameter("idEvento", idEvento);

        ProcessiEventi pe = (ProcessiEventi) Utils.getSingleResult(query);
        return pe;
    }

    public Pratica findByIdentificativoEsterno(String identificativoEsterno) {
        Log.SQL.info("CHIAMATO PraticaDao.findByIdentificativoEsterno ");
        Query query = em.createNamedQuery("Pratica.findByIdentificativoEsterno");
        query.setParameter("identificativoEsterno", identificativoEsterno);
        List<Pratica> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public PraticheEventi getDettaglioPraticaEvento(Integer idPraticaEvento) {
        Log.SQL.info("CHIAMATO PraticaDao.getDettaglioPraticaEvento ");
        Query query = em.createNamedQuery("PraticheEventi.findByIdPraticaEvento");
        query.setParameter("idPraticaEvento", idPraticaEvento);

        PraticheEventi evento = (PraticheEventi) Utils.getSingleResult(query);
        return evento;
    }

    public List<PraticheEventi> getEventiRicezionePratichefiglie(PraticheEventi praticaEventoPadre) {
        Log.SQL.info("CHIAMATO PraticaDao.getEventiRicezionePratichefiglie ");
        Query query = em.createNamedQuery("PraticheEventi.findByPraticaEventoRef");
        query.setParameter("praticaEventoRef", praticaEventoPadre);

        List<PraticheEventi> eventiRicezionePraticheFiglie = query.getResultList();
        return eventiRicezionePraticheFiglie;
    }

    public ProcessiEventi getDettaglioProcessoEvento(Integer id) {
        Log.SQL.info("CHIAMATO PraticaDao.getDettaglioProcessoEvento ");
        Query query = em.createNamedQuery("ProcessiEventi.findByIdEvento");
        query.setParameter("idEvento", id);

        ProcessiEventi evento = (ProcessiEventi) Utils.getSingleResult(query);
        return evento;
    }

    //USER-AZ-AZUS
    public void getAnagrafichePratica(Pratica pratica, String tipoDestinatario, List<Anagrafica> anagrafiche) {
        Log.SQL.info("CHIAMATO PraticaDao.getAnagrafichePratica ");
        Anagrafica anagraficaLoop;
        char tipoAnagraficaLoop;

        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            anagraficaLoop = praticaAnagrafica.getAnagrafica();
            tipoAnagraficaLoop = anagraficaLoop.getTipoAnagrafica();
            if (tipoDestinatario.equalsIgnoreCase(AnaTipiDestinatario.UTENTE) && tipoAnagraficaLoop == Constants.FLAG_ANAGRAFICA_FISICA) {
                anagrafiche.add(praticaAnagrafica.getAnagrafica());
            }
            if (tipoDestinatario.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA) && tipoAnagraficaLoop == Constants.FLAG_ANAGRAFICA_AZIENDA) {
                anagrafiche.add(praticaAnagrafica.getAnagrafica());
            }
            if (tipoDestinatario.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA_UTENTE)) {
                anagrafiche.add(praticaAnagrafica.getAnagrafica());
            }
        }
    }
    //ELIMINA DATI CATASTILI PER LA STESSA PRATICA

    public void eliminaDatiCatastali(Pratica idPratica) {
        Query query = em.createQuery("DELETE from DatiCatastali a where a.idPratica = :id ");
        query.setParameter("id", idPratica);
        query.executeUpdate();
    }

    public void eliminaDatiCatastali(DatiCatastali datiCatastali) {
        em.remove(datiCatastali);
    }

    @Nullable
    public DatiCatastali findDatiCatastali(Integer id) {
        Log.SQL.info("CHIAMATO Daticatastali.findDatocatasttale " + id);
        DatiCatastali dato = em.find(DatiCatastali.class, id);
        return dato;
    }

    public void eliminaIndirizziIntervento(Pratica idPratica) {
        Query query = em.createQuery("DELETE from IndirizziIntervento a where a.idPratica = :id ");
        query.setParameter("id", idPratica);
        query.executeUpdate();
    }

    public void eliminaIndirizziIntervento(IndirizziIntervento indirizziIntervento) {
        em.remove(indirizziIntervento);
    }

    public IndirizziIntervento findIndirizziInterventoById(Integer id) {
        Log.SQL.info("CHIAMATO PraticaDao.findIndirizziInterventoById ");
        Query query = em.createQuery("SELECT s "
                + "FROM IndirizziIntervento s "
                + "WHERE "
                + "s.idIndirizzoIntervento = :id");
        query.setParameter("id", id);
        List<IndirizziIntervento> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public PraticaProcedimenti findPraticaProcedimenti(Integer idPratica, Integer idProcedimento) {
        Log.SQL.info("CHIAMATO PraticaDao.findPraticaProcedimenti ");
        Query query = em.createQuery("SELECT s "
                + "FROM PraticaProcedimenti s "
                + "WHERE s.praticaProcedimentiPK.idPratica = :idPratica "
                + "AND s.praticaProcedimentiPK.idProcedimento = :idProcedimento ");

        query.setParameter("idProcedimento", idProcedimento);
        query.setParameter("idPratica", idPratica);
        List<PraticaProcedimenti> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<PraticaProcedimenti> findPraticheProcedimentiByIdProcedimento(Integer idProcedimento) {
        Log.SQL.info("CHIAMATO PraticaDao.findPraticaProcedimenti ");
        Query query = em.createQuery("SELECT s "
                + "FROM PraticaProcedimenti s "
                + "WHERE s.praticaProcedimentiPK.idProcedimento = :idProcedimento ");

        query.setParameter("idProcedimento", idProcedimento);
        List<PraticaProcedimenti> resultList = query.getResultList();
        return resultList;
    }

    public PraticaProcedimenti findPraticaProcedimento(Integer idPratica, Integer idProcedimento, Integer idEnte) {
        Log.SQL.info("CHIAMATO PraticaDao.findPraticaProcedimento ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<PraticaProcedimenti> c = q.from(PraticaProcedimenti.class);
        q.select(c);
        Predicate p = cb.equal(c.get("praticaProcedimentiPK").get("idPratica"), idPratica);
        p = cb.and(p, cb.equal(c.get("praticaProcedimentiPK").get("idProcedimento"), idProcedimento));
        p = cb.and(p, cb.equal(c.get("praticaProcedimentiPK").get("idEnte"), idEnte));
        q.where(p);
        PraticaProcedimenti result = (PraticaProcedimenti) Utils.getSingleResult(em.createQuery(q));
        return result;
    }

    public List<PraticaProcedimenti> findAllPraticaByProcedimentoEnte(Integer idProcedimento, Integer idEnte) {
        Log.SQL.info("CHIAMATO PraticaDao.findAllPraticaByProcedimentoEnte ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<PraticaProcedimenti> c = q.from(PraticaProcedimenti.class);
        q.select(c);
        Predicate p = cb.equal(c.get("praticaProcedimentiPK").get("idProcedimento"), idProcedimento);
        p = cb.and(p, cb.equal(c.get("praticaProcedimentiPK").get("idEnte"), idEnte));
        q.where(p);
        List<PraticaProcedimenti> result = em.createQuery(q).getResultList();
        return result;
    }

    public Pratica findByProtocollo(String protocollo, Integer annoRiferimento) {
        Log.SQL.info("CHIAMATO PraticaDao.findByProtocollo ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<Pratica> c = q.from(Pratica.class);
        q.select(c);
        Predicate protocolloPredicate = cb.equal(c.get("protocollo"), protocollo);
        Predicate annoRiferimentoPredicate = cb.equal(c.get("annoRiferimento"), annoRiferimento);
        Predicate praticaPadreIsNullPredicate = cb.isNull(c.get("idPraticaPadre"));
        q.where(cb.and(protocolloPredicate, annoRiferimentoPredicate, praticaPadreIsNullPredicate));
        Pratica pratica = (Pratica) Utils.getSingleResult(em.createQuery(q));
        return pratica;
    }

    public Pratica findPraticaByIdentificativo(String identificativoPratica) {
        Log.SQL.info("CHIAMATO PraticaDao.findPraticaByIdentificativo ");
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.identificativoPratica = :identificativoPratica "
                + "ORDER BY p.idPratica ASC");
        query.setParameter("identificativoPratica", identificativoPratica);
        Pratica pratica = (Pratica) Utils.getSingleResult(query);
        return pratica;
    }

    public Pratica findPraticaByIdentificativo(String identificativoPratica, Integer idEnte) {
        Log.SQL.info("Cerca pratica per identificativo " + identificativoPratica + " e idEnte " + idEnte + " ");
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.identificativoPratica = :identificativoPratica AND "
                + " p.idProcEnte.idEnte.idEnte= :idEnte ");
        query.setParameter("identificativoPratica", identificativoPratica);
        query.setParameter("idEnte", idEnte);
        Pratica pratica = (Pratica) Utils.getSingleResult(query);
        return pratica;
    }

    public List<PraticheProtocollo> findByCodDocumento(String codDocumento) {
        Log.SQL.info("CHIAMATO PraticaDao.findByCodDocumento ");
        Query query = em.createNamedQuery("PraticheProtocollo.findByCodDocumento");
        query.setParameter("codDocumento", codDocumento);

        List<PraticheProtocollo> pratiche = query.getResultList();
        return pratiche;
    }

    public List<ScadenzeDaChiudereView> findScadenzeDaChiudere(Integer idEvento, Integer idPratica) {
        Query query = em.createQuery("SELECT s "
                + "FROM ScadenzeDaChiudereView s "
                + "WHERE s.idEvento = :idEvento AND "
                + "s.idPratica = :idPratica");
        query.setParameter("idEvento", idEvento);
        query.setParameter("idPratica", idPratica);

        List<ScadenzeDaChiudereView> scadenze = query.getResultList();
        return scadenze;
    }

    public Scadenze findScadenzeByIdScadenza(Integer idScadenza) {
        Query query = em.createNamedQuery("Scadenze.findByIdScadenza");
        query.setParameter("idScadenza", idScadenza);

        Scadenze scadenza = (Scadenze) Utils.getSingleResult(query);
        return scadenza;
    }

    public List<EventiTemplate> getTemplatesPerEvento(ProcessiEventi idEvento) {
        Log.APP.info("getTemplatesPerEvento: " + idEvento);
        Query query = em.createQuery("SELECT e "
                + "FROM EventiTemplate e "
                + "WHERE e.idEvento = :idEvento "
                + "AND e.idEnte = :idEnte ");
        query.setParameter("idEvento", idEvento);
        List<EventiTemplate> eventiTemplate = query.getResultList();
        return eventiTemplate;
    }

    public List<EventiTemplate> getTemplatesPerEnte(ProcessiEventi idEvento, Enti idEnte) {
        Log.APP.info("getTemplatesPerEnte: ProcessiEventi " + idEvento + ", Enti " + idEnte);
        Query query = em.createQuery("SELECT e "
                + "FROM EventiTemplate e "
                + "WHERE e.idEvento = :idEvento "
                + "AND e.idEnte = :idEnte ");
        query.setParameter("idEvento", idEvento);
        query.setParameter("idEnte", idEnte);

        List<EventiTemplate> eventiTemplate = query.getResultList();
        return eventiTemplate;
    }

    public List<EventiTemplate> getTemplatesPerProcedimento(ProcessiEventi idEvento, Enti idEnte, Procedimenti idProcedimento) {
        Log.APP.info("getTemplatesPerProcedimenti ProcessiEventi " + idEvento + ", Enti " + idEnte + ",Procedimenti " + idProcedimento);
        Query query = em.createQuery("SELECT e "
                + "FROM EventiTemplate e "
                + "WHERE e.idEvento = :idEvento "
                + "AND e.idEnte = :idEnte "
                + "AND e.idProcedimento = :idProcedimento");
        query.setParameter("idEvento", idEvento);
        query.setParameter("idEnte", idEnte);
        query.setParameter("idProcedimento", idProcedimento);

        List<EventiTemplate> eventiTemplate = query.getResultList();
        return eventiTemplate;
    }

    public List<ProcessiSteps> findNextSteps(Integer idPratica) {
        TypedQuery<ProcessiSteps> query = em.createQuery("SELECT ps "
                + "FROM Pratica p "
                + "JOIN p.praticheEventiList pev "
                + "JOIN p.idProcEnte pe "
                + "JOIN pe.idProcesso pr "
                + "JOIN pr.processiEventiList prev "
                + "JOIN prev.idEventoTriggerList ps "
                + "WHERE p.idPratica = :idPratica and pev.idEvento.idEvento = prev.idEvento "
                + "ORDER BY pev.idPraticaEvento", ProcessiSteps.class);
        query.setParameter("idPratica", idPratica);
        List<ProcessiSteps> steps = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return steps;
    }
    public List<ProcessiSteps> findNextStepsFixed(Integer idPratica, Integer idProcesso) {
//        TypedQuery<ProcessiSteps> query = em.createQuery("SELECT ps "
//                + "FROM Pratica p "
//                + "JOIN p.praticheEventiList pev "
//                //+ "JOIN p.idProcEnte pe "
//                + "JOIN pe.idProcesso pr "
//                + "JOIN pr.processiEventiList prev "
//                + "JOIN prev.idEventoTriggerList ps "
//                + "WHERE p.idPratica = :idPratica and pev.idEvento.idEvento = prev.idEvento and pr.idProcesso = :idProcesso "
//                + "ORDER BY pev.idPraticaEvento", ProcessiSteps.class);
//        query.setParameter("idPratica", idPratica);
//        query.setParameter("idProcesso", idProcesso);
        String sql = "SELECT " + 
        		"    processi_steps.tipo_operazione, " + 
        		"    processi_steps.id_evento_trigger, " + 
        		"    processi_steps.id_evento_result " + 
        		"FROM " + 
        		"	opencross.processi_eventi processi_eventi, " + 
        		"    opencross.pratiche_eventi pratiche_eventi, " + 
        		"    opencross.pratica pratica, " + 
        		"    opencross.procedimenti_enti procedimenti_enti, " + 
        		"    opencross.processi processi, " + 
        		"    opencross.processi_eventi processi_eventi_1, " + 
        		"    opencross.processi_steps processi_steps " + 
        		"WHERE " + 
        		"	(" + 
        		"		((pratica.id_pratica = ?) AND (processi_eventi.id_evento = processi_eventi_1.id_evento)) " + 
        		"	AND (" + 
        		"			(" + 
        		"				(" + 
        		"					(" + 
        		"						(" + 
        		"							(pratiche_eventi.id_pratica = pratica.id_pratica) AND (procedimenti_enti.id_proc_ente = pratica.id_proc_ente)" + 
        		"						)" + 
        		"						AND (processi.id_processo = ? )" + 
        		"					)" + 
        		"					AND (processi_eventi.id_evento = pratiche_eventi.id_evento)" + 
        		"				)" + 
        		"				AND (processi_eventi_1.id_processo = processi.id_processo)" + 
        		"			)  " + 
        		"			AND (processi_steps.id_evento_trigger = processi_eventi_1.id_evento)" + 
        		"		)" + 
        		"	)" + 
        		"ORDER BY pratiche_eventi.id_pratica_evento ASC";
        Query q = em.createNativeQuery(sql, ProcessiSteps.class);
        q.setParameter(1, idPratica);
        q.setParameter(2, idProcesso);
        List<ProcessiSteps> steps = q.getResultList();
//        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
//        Log.SQL.info("Query: " + sql);
        return steps;
    }
    public Scadenze findScadenzaPratica(Pratica pratica) {
        TypedQuery<Scadenze> query = em.createQuery("SELECT s "
                + "FROM Scadenze s "
                + "JOIN s.idAnaScadenza lk "
                + "WHERE s.idPratica = :pratica AND lk.flgScadenzaPratica = 'S'", Scadenze.class);
        query.setParameter("pratica", pratica);
        Scadenze scadenza = query.getSingleResult();
        return scadenza;
    }

    public List<Scadenze> findScadenzaPraticaAttive(Pratica pratica) {
        TypedQuery<Scadenze> query = em.createQuery("SELECT s "
                + "FROM Scadenze s "
                + "JOIN s.idStato lk "
                + "WHERE s.idPratica = :pratica AND lk.idStato = 'A'", Scadenze.class);
        query.setParameter("pratica", pratica);
        List<Scadenze> scadenze = query.getResultList();
        return scadenze;
    }

    public Date findMaxDataSincronizzazioneProtocollo() {
        Query query = em.createQuery("SELECT MAX(p.dataSincronizzazione) "
                + "FROM PraticheProtocollo p");
        Date dataMax = (Date) Utils.getSingleResult(query);
        return dataMax;
    }

    public IntervalloScadenzeMultiple findIntervalloScadenzeMultipleSospensione(Integer idPratica) {
        Query query = em.createQuery("SELECT "
                + "MIN(s.dataInizioScadenza), "
                + "MAX(s.dataFineScadenza) "
                + "FROM Scadenze s "
                + "JOIN s.idPraticaEvento pe "
                + "JOIN pe.idEvento ev "
                + "JOIN ev.statoPost sp "
                + "WHERE s.idStato.idStato = 'A' AND s.idPratica.idPratica = :idPratica AND "
                + "sp.codice = 'S'");
        query.setParameter("idPratica", idPratica);
        Object[] result = (Object[]) Utils.getSingleResult(query);
        if (result != null) {
            return new IntervalloScadenzeMultiple((Date) result[0], (Date) result[1]);
        } else {
            return null;
        }
    }

    public Pratica findByRegistroFascicoloProtocolloAnno(String codRegistro, String codFascicolo, String protocollo, Integer annoRiferimento) {
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.codRegistro = :codRegistro "
                + "AND p.protocollo = :codFascicolo "
                + "AND p.annoRiferimento = :annoRiferimento "
                + "AND p.idPraticaPadre IS NULL");
        query.setParameter("codRegistro", codRegistro);
        query.setParameter("codFascicolo", codFascicolo);
        query.setParameter("annoRiferimento", annoRiferimento);
        Pratica pratica = (Pratica) Utils.getSingleResult(query);
        return pratica;
    }

    public List<Pratica> findByRegistroFascicoloAnno(String codRegistro, String codFascicolo, Integer annoRiferimento) {
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.protocollo = :codFascicolo "
                + "AND p.codRegistro = :codRegistro "
                + "AND p.annoRiferimento = :annoRiferimento");
        query.setParameter("codRegistro", codRegistro);
        query.setParameter("codFascicolo", codFascicolo);
        query.setParameter("annoRiferimento", annoRiferimento);
        List<Pratica> pratica = query.getResultList();
        return pratica;
    }
    
    public List<Pratica> findPraticheDaRiprotocollare(String codFascicolo) {
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.protocollo = :codFascicolo "
                + "AND p.codRegistro IS NULL "
                + "AND p.annoRiferimento IS NULL");
        query.setParameter("codFascicolo", codFascicolo);
        List<Pratica> pratica = query.getResultList();
        return pratica;
    }

    public List<PraticaAnagrafica> findPraticaAnagraficaByTipoRuolo(Pratica pratica, LkTipoRuolo tipoRuolo) {
        Query query = em.createQuery("SELECT p "
                + "FROM PraticaAnagrafica p "
                + "WHERE p.praticaAnagraficaPK.idPratica = :idPratica "
                + "AND p.praticaAnagraficaPK.idTipoRuolo = :idTipoRuolo");
        query.setParameter("idPratica", pratica.getIdPratica());
        query.setParameter("idTipoRuolo", tipoRuolo.getIdTipoRuolo());
        List<PraticaAnagrafica> anagrafiche = query.getResultList();
        return anagrafiche;
    }

    public Pratica findByAnnoProtocollo(Integer anno, String numeroProtocollo) {
        Query query = em.createQuery("SELECT p "
                + " FROM Pratica p "
                + " WHERE "
                + " p.annoRiferimento = :anno AND "
                + " p.protocollo = :numeroProtocollo ");
        query.setParameter("anno", anno);
        query.setParameter("numeroProtocollo", numeroProtocollo);
        List<Pratica> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<PraticheAllegatiView> getAllegatiPratica(Pratica pratica) {
        Query query = em.createQuery("SELECT p "
                + "FROM PraticheAllegatiView p "
                + "WHERE p.idPratica = :idPratica");
        Integer idPratica = pratica.getIdPratica();
        query.setParameter("idPratica", idPratica);
        List<PraticheAllegatiView> allegati = query.getResultList();
        return allegati;
    }

    public PraticheProtocollo findPraticheProtocolloByRegistroFascicoloProtocolloAnnoTipo(String codRegistro, String fascicolo, Integer annoRiferimento, String tipoDocumentoPratica) {
        Query query = em.createQuery("SELECT p "
                + "FROM PraticheProtocollo p "
                + "WHERE p.annoRiferimento = :annoRiferimento "
                + "AND p.codRegistro = :codRegistro "
                + "AND p.nFascicolo = :nFascicolo "
                + "AND p.tipoDocumento = :tipoDocumento");
        query.setParameter("annoRiferimento", annoRiferimento);
        query.setParameter("codRegistro", codRegistro);
        query.setParameter("nFascicolo", fascicolo);
        query.setParameter("tipoDocumento", tipoDocumentoPratica);
        PraticheProtocollo pratica = (PraticheProtocollo) Utils.getSingleResult(query);
        return pratica;
    }

    public List<PraticaBarDTO> findScadenzaBar(Utente connectedUser) {
        Query query = em.createQuery("SELECT s.idPratica.idPratica, s.idPratica.codRegistro, s.idPratica.annoRiferimento, s.idPratica.protocollo "
                + "FROM Scadenze s "
                + "WHERE s.dataFineScadenza >= :today "
                + "AND s.dataFineScadenza < :next10Days "
                + "AND s.idPratica.idUtente = :utenteConnesso "
                + "AND s.idPratica.idStatoPratica.grpStatoPratica != :praticaChiusa");
        query.setParameter("today", new Date());
        query.setParameter("next10Days", Utils.addDaysToDate(new Date(), 10));
        query.setParameter("utenteConnesso", connectedUser);
        query.setParameter("praticaChiusa", "C");
        query.setFirstResult(0);
        query.setMaxResults(10);
        List<Object[]> pratiche = query.getResultList();
        List<PraticaBarDTO> bar = new ArrayList<PraticaBarDTO>();
        if (pratiche != null && !pratiche.isEmpty()) {
            for (Object[] pratica : pratiche) {
                PraticaBarDTO dto = new PraticaBarDTO();
                dto.setIdPratica((Integer) pratica[0]);
                String protocollo = "";
                if (pratica[1] != null && pratica[2] != null && pratica[3] != null) {
                    protocollo = (String) pratica[1] + "/" + (Integer) pratica[2] + "/" + (String) pratica[3];
                }
                dto.setProtocollo(protocollo);
                bar.add(dto);
            }
        }
        return bar;
    }

    public List<PraticaBarDTO> findPraticheDaAprireBar(Utente connectedUser) {
        Query query = em.createQuery("SELECT p.idPratica, p.codRegistro, p.annoRiferimento, p.protocollo "
                + "FROM Pratica p "
                + "WHERE p.idStatoPratica.codice = :praticaRicevuta "
                + "AND p.idUtente = :utenteConnesso");
        query.setParameter("praticaRicevuta", "R");
        query.setParameter("utenteConnesso", connectedUser);
        query.setFirstResult(0);
        query.setMaxResults(10);
        List<Object[]> pratiche = query.getResultList();
        List<PraticaBarDTO> bar = new ArrayList<PraticaBarDTO>();
        if (pratiche != null && !pratiche.isEmpty()) {
            for (Object[] pratica : pratiche) {
                PraticaBarDTO dto = new PraticaBarDTO();
                dto.setIdPratica((Integer) pratica[0]);
                String protocollo = "";
                if (pratica[1] != null && pratica[2] != null && pratica[3] != null) {
                    protocollo = (String) pratica[1] + "/" + (Integer) pratica[2] + "/" + (String) pratica[3];
                }
                dto.setProtocollo(protocollo);
                bar.add(dto);
            }
        }
        return bar;
    }

    public Pratica findByRifProtocolloEnte(String registro, String anno, String numero, Enti ente) {
        Query query = em.createQuery("SELECT p "
                + "FROM Pratica p "
                + "WHERE p.protocollo = :protocollo "
                + "AND p.codRegistro = :codRegistro "
                + "AND p.annoRiferimento = :annoRiferimento "
                + "AND p.idProcEnte.idEnte = :idEnte");
        query.setParameter("protocollo", numero);
        query.setParameter("codRegistro", registro);
        query.setParameter("annoRiferimento", Integer.valueOf(anno));
        query.setParameter("idEnte", ente);

        Pratica pratica = (Pratica) Utils.getSingleResult(query);
        return pratica;
    }

    public List<Pratica> findPratichePrimoLivelloByUtenteAndSearch(String idUtente, @Nullable String search) {
        String queryStr = "SELECT DISTINCT p FROM Pratica p JOIN p.praticaAnagraficaList pa WHERE p.idPraticaPadre IS NULL AND pa.lkTipoRuolo.codRuolo = 'R' AND LOWER(pa.anagrafica.codiceFiscale) = LOWER(:idUtente)";
        Query query;
        if (Strings.isNullOrEmpty(search)) {
            query = em.createQuery(queryStr);
        } else {
            query = em.createQuery(queryStr + " AND ( p.identificativoPratica LIKE :searchlike OR p.oggettoPratica LIKE :searchlike OR p.protocollo LIKE :searchlike OR p.responsabileProcedimento LIKE :searchlike OR p.idStatoPratica.descrizione LIKE :searchlike)");
            query.setParameter("searchlike", "%" + search + "%");
        }
        query.setParameter("idUtente", idUtente);
        return query.getResultList();
    }

    public List<Pratica> findPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.findDaAprireFiltrate ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pratica> q = cb.createQuery(Pratica.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(c).distinct(true);
        ParameterExpression<Utente> utenteConnessoExpression = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        List<LkStatoPratica> praticheChiuse = lookupDao.findStatiPraticaByGruppo(StatoPratica.CHIUSA);

        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            utenteConnessoExpression = cb.parameter(Utente.class);
            predicates.add(cb.equal(c.get("idUtente"), utenteConnessoExpression));
        }
        //Escludo le pratiche chiuse
        ParameterExpression<List> praticheDaEscludereParameter = cb.parameter(List.class);
        List<Integer> p = new ArrayList<Integer>();
        for (LkStatoPratica s : praticheChiuse) {
            p.add(s.getIdStatoPratica());
        }
        predicates.add(cb.not(c.get("idStatoPratica").get("idStatoPratica").in(p)));
        //Prendo le pratiche con lo stesso padre ...
        ParameterExpression<Pratica> praticaPadreExpression = cb.parameter(Pratica.class);
        predicates.add(cb.equal(c.get("idPraticaPadre"), praticaPadreExpression));
        //... e lo stesso ente
        ParameterExpression<Enti> enteRiferimentoExpression = cb.parameter(Enti.class);
        predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteRiferimentoExpression));

        if (filter.getOrderDirection() != null && filter.getOrderColumn() != null) {
            if (filter.getOrderDirection().equalsIgnoreCase("asc")) {
                q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
            } else {
                q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
            }
        } else {
            //Di default ordino per data ricezione
            q.orderBy(cb.asc(c.get("dataRicezione")));
        }

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Pratica> query = em.createQuery(q);

        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(utenteConnessoExpression, utenteConnesso);
        }
        query.setParameter(praticheDaEscludereParameter, p);
        query.setParameter(praticaPadreExpression, praticaPadre);
        query.setParameter(enteRiferimentoExpression, idEnte);

        query.setMaxResults(filter.getLimit());
        query.setFirstResult(filter.getOffset());

        List<Pratica> pratiche = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return pratiche;
    }

    public Long countPraticheFiglie(Pratica pratica) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(cb.count(c));
        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<Pratica> praticaPadreExpression = cb.parameter(Pratica.class);
        predicates.add(cb.equal(c.get("idPraticaPadre"), praticaPadreExpression));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(praticaPadreExpression, pratica);

        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public Long countPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter) {
        Log.SQL.info("CHIAMATO PraticaDao.countPraticheFiglie ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(cb.count(c)).distinct(true);
        ParameterExpression<Utente> utenteConnessoExpression = null;
        List<LkStatoPratica> praticheChiuse = lookupDao.findStatiPraticaByGruppo(StatoPratica.CHIUSA);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getConnectedUser() != null) {
            Join joinRuoli = c.join("idProcEnte").join("utenteRuoloEnteList");
            Predicate p = cb.equal(joinRuoli.get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            utenteConnessoExpression = cb.parameter(Utente.class);
            predicates.add(cb.equal(c.get("idUtente"), utenteConnessoExpression));
        }
        //Escludo le pratiche chiuse
        ParameterExpression<List> praticheDaEscludereParameter = cb.parameter(List.class);
        List<Integer> p = new ArrayList<Integer>();
        for (LkStatoPratica s : praticheChiuse) {
            p.add(s.getIdStatoPratica());
        }
        predicates.add(cb.not(c.get("idStatoPratica").get("idStatoPratica").in(p)));
        //Prendo le pratiche con lo stesso padre ...
        ParameterExpression<Pratica> praticaPadreExpression = cb.parameter(Pratica.class);
        predicates.add(cb.equal(c.get("idPraticaPadre"), praticaPadreExpression));
        //... e lo stesso ente
        ParameterExpression<Enti> enteRiferimentoExpression = cb.parameter(Enti.class);
        predicates.add(cb.equal(c.get("idProcEnte").get("idEnte"), enteRiferimentoExpression));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);

        if (filter.getSearchByUtenteConnesso() != null && filter.getSearchByUtenteConnesso()) {
            Utente utenteConnesso = utentiDao.findUtenteByIdUtente(filter.getConnectedUser().getIdUtente());
            query.setParameter(utenteConnessoExpression, utenteConnesso);
        }
        query.setParameter(praticheDaEscludereParameter, p);
        query.setParameter(praticaPadreExpression, praticaPadre);
        query.setParameter(enteRiferimentoExpression, idEnte);

        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public boolean isAdminSuPratica(Pratica pratica, Utente utente) {
        Query query = em.createQuery("SELECT ruoloEnte.codPermesso.codPermesso "
                + "FROM Utente u "
                + "JOIN u.utenteRuoloEnteList ruoloEnte "
                + "JOIN ruoloEnte.utenteRuoloProcedimentoList utenteProcedimentoEnte "
                + "JOIN utenteProcedimentoEnte.procedimentiEnti pe "
                + "WHERE u.idUtente = :idUtentePar "
                + "AND pe.idProcEnte = :idProcEntePar");
        query.setParameter("idProcEntePar", pratica.getIdProcEnte().getIdProcEnte());
        query.setParameter("idUtentePar", utente.getIdUtente());
        List<String> permessi = query.getResultList();
        if (permessi != null && !permessi.isEmpty()) {
            boolean result = false;
            for (String permesso : permessi) {
                if (permesso.equals(PermissionConstans.AMMINISTRATORE)) {
                    result = true;
                    break;
                }
            }
            return result;
        } else {
            return false;
        }
    }

    public PraticaAnagrafica findPraticaAnagraficaByKey(Integer idPratica, Integer idAnagrafica, Integer ruolo) {
        Query query = em.createQuery("SELECT p "
                + "FROM PraticaAnagrafica p "
                + "WHERE p.praticaAnagraficaPK.idPratica = :idPratica "
                + "AND p.praticaAnagraficaPK.idAnagrafica = :idAnagrafica "
                + "AND p.praticaAnagraficaPK.idTipoRuolo = :idTipoRuolo");
        query.setParameter("idPratica", idPratica);
        query.setParameter("idAnagrafica", idAnagrafica);
        query.setParameter("idTipoRuolo", ruolo);
        PraticaAnagrafica pa = (PraticaAnagrafica) Utils.getSingleResult(query);
        return pa;
    }

    public List<PraticheEventi> findEventoPratica(Pratica pratica, String codEvento) {
        Query query = em.createQuery("SELECT pe "
                + "FROM PraticheEventi pe "
                + "WHERE pe.idPratica = :pratica "
                + "AND pe.idEvento.codEvento = :codEvento ");
        query.setParameter("pratica", pratica);
        query.setParameter("codEvento", codEvento);
        return query.getResultList();
    }

    public Pratica getPraticaFiglia(Pratica pratica, Procedimenti procedimenti) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();

        Root<Pratica> c = q.from(Pratica.class);
        Predicate p = cb.equal(c.get("idPraticaPadre"), pratica);
        p = cb.and(p, cb.equal(c.get("idProcEnte").get("idProc"), procedimenti));

        q.where(p);
        q.select(c).distinct(true);
        Pratica result = (Pratica) Utils.getSingleResult(em.createQuery(q));
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public List<Pratica> findAllPraticheFiglie(Pratica pratica) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();

        Root<Pratica> c = q.from(Pratica.class);
        Predicate p = cb.equal(c.get("idPraticaPadre"), pratica);
        q.where(p);
        q.select(c).distinct(true);
        TypedQuery<Pratica> query = em.createQuery(q);
        List<Pratica> resultList = query.getResultList();
        return resultList;
    }

    public Pratica findByFascicoloAnno(String codFascicolo, Integer annoRiferimento, Integer idEnte) {
        Query query;
        if (idEnte == null) {
            Log.APP.warn("Metodo findByFascicoloAnno chiamato senza idEnte (DEPRECATO) continuo con query precedente. CallStack:\r\n" + Logger.getCurrentCallStack());
            query = em.createQuery("SELECT p "
                    + "FROM Pratica p "
                    + "WHERE p.protocollo = :codFascicolo "
                    + "AND p.annoRiferimento = :annoRiferimento "
                    + "AND p.idPraticaPadre IS NULL");
            query.setParameter("codFascicolo", codFascicolo);
            query.setParameter("annoRiferimento", annoRiferimento);
        } else {
            query = em.createQuery("SELECT p "
                    + "FROM Pratica p "
                    + "JOIN p.idProcEnte pe "
                    + "WHERE p.protocollo = :codFascicolo "
                    + "AND p.annoRiferimento = :annoRiferimento "
                    + "AND pe.idEnte.idEnte = :idEnte");
            query.setParameter("codFascicolo", codFascicolo);
            query.setParameter("annoRiferimento", annoRiferimento);
            query.setParameter("idEnte", idEnte);
        }
        List<Pratica> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public void aggiornaPraticheEvento(PraticheEventi eventoPratica, List<Allegati> allegati) {
        em.persist(eventoPratica);
        if (allegati != null && !allegati.isEmpty()) {
            for (it.wego.cross.entity.Allegati allegato : allegati) {
                if (allegato.getId() == null) {
                    allegato.setPraticheEventiAllegatiList(new ArrayList<PraticheEventiAllegati>());
                }
                PraticheEventiAllegati pea = new PraticheEventiAllegati();
                pea.setPraticheEventi(eventoPratica);
//            pea.setFlgIsPrincipale(allegato.getFileOrigine() ? "S" : "N");
                allegato.getPraticheEventiAllegatiList().add(pea);
                em.merge(allegato);
            }
        }
    }

    public List<Pratica> findPraticheNonProtocollate(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pratica> q = cb.createQuery(Pratica.class);
        Root<Pratica> c = q.from(Pratica.class);
        q.select(c);
        q.where(cb.or(
                cb.isNull(c.get("protocollo")),
                cb.equal(c.get("protocollo"), "")));
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        TypedQuery<Pratica> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<Pratica> pratiche = query.getResultList();
        return pratiche;
    }

    public Long countPraticheNonProtocollate() {
        Query query = em.createQuery("SELECT COUNT(p) FROM Pratica p WHERE p.protocollo IS NULL OR p.protocollo = :emptystring");
        query.setParameter("emptystring", "");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public void cancellaPratica(String identificativoPratica) {
        String queryString = "CALL delete_pratica('" + identificativoPratica + "')";
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
    }

    public Pratica findPraticaWithPraticaPadreAndEnteAndProcedimento(Integer idPratica, Integer idEnte, Integer idProcedimento) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();

        Root<Pratica> c = q.from(Pratica.class);
//        Join joinProcedimentiEnti = c.join("idProcEnte");
        Predicate p = cb.equal(c.get("idPraticaPadre").get("idPratica"), idPratica);
        p = cb.and(p, cb.equal(c.get("idProcEnte").get("idProc").get("idProc"), idProcedimento));
        p = cb.and(p, cb.equal(c.get("idProcEnte").get("idEnte").get("idEnte"), idEnte));

        q.where(p);
        q.select(c).distinct(true);

        TypedQuery<Pratica> query = em.createQuery(q);
        List<Pratica> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public Long countOccorrenzeAnagraficaSuPratiche(Anagrafica anagrafica) {
        Log.SQL.info("CHIAMATO PraticaDao.countFiltrate ");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<PraticaAnagrafica> c = q.from(PraticaAnagrafica.class);
        q.select(cb.countDistinct(c.get("pratica")));
        ParameterExpression<Anagrafica> anagraficaParameter = cb.parameter(Anagrafica.class);
        q.where(cb.equal(c.get("anagrafica"), anagraficaParameter));
        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(anagraficaParameter, anagrafica);
        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public void scollegaPratica(Integer idPratica, Integer idPraticaCollegata) throws Exception {
        Pratica pratica = findPratica(idPratica);
        List<Pratica> praticheAssociateOriginal = pratica.getPraticheAssociate();
        Pratica praticaCollegata = findPratica(idPraticaCollegata);
        if (!praticheAssociateOriginal.contains(praticaCollegata)) {
            throw new Exception("Non è presente nessun collegamento tra le due pratiche");
        }
        praticheAssociateOriginal.remove(praticaCollegata);
        update(pratica);
    }

    public void collegaPratica(Integer idPratica, Integer idPraticaCollegata) throws Exception {
        Pratica pratica = findPratica(idPratica);
        List<Pratica> praticheAssociateOriginal = pratica.getPraticheAssociate();
        Pratica praticaCollegata = findPratica(idPraticaCollegata);
        if (praticheAssociateOriginal.contains(praticaCollegata)) {
            throw new Exception("Le pratiche sono già collegate");
        }
        praticheAssociateOriginal.add(praticaCollegata);
        update(pratica);
    }

    public NotePratica findNotaPraticaById(Integer idNotaPratica) {
        Query query = em.createNamedQuery("NotePratica.findByIdNotePratica");
        query.setParameter("idNotePratica", idNotaPratica);

        NotePratica nota = (NotePratica) Utils.getSingleResult(query);
        return nota;
    }

    public static class IntervalloScadenzeMultiple {

        private final Date dataFine, dataInizio;

        public IntervalloScadenzeMultiple(Date dataInizio, Date dataFine) {
            this.dataFine = dataFine;
            this.dataInizio = dataInizio;
        }

        public Date getDataFine() {
            return dataFine;
        }

        public Date getDataInizio() {
            return dataInizio;
        }
    }

    public void aggiornaResponsabileProcedimentoSuPratiche(ProcedimentiEnti procedimentiEnti) {

        Query query = em.createQuery("UPDATE Pratica p set p.responsabileProcedimento = :responsabileProcedimento WHERE p.idProcEnte = :idProcEnte");
        query.setParameter("idProcEnte", procedimentiEnti);
        query.setParameter("responsabileProcedimento", procedimentiEnti.getResponsabileProcedimento());
        query.executeUpdate();

    }

	public PraticheEventiAllegati findPraticheEventiAllegatiByIdAllegato(Integer idAllegato) {
		Query query = em.createNamedQuery("PraticheEventiAllegati.findByIdAllegato");
        query.setParameter("idAllegato", idAllegato);

//        PraticheEventiAllegati pea = (PraticheEventiAllegati) Utils.getSingleResult(query);
        PraticheEventiAllegati pea = (PraticheEventiAllegati) (query.getResultList().get(0));
        return pea;
	}

	public List<EstrazioniCilaDTO> findPraticheCILA(Filter filter) throws Exception {
		
		List<EstrazioniCilaDTO> resultList = new ArrayList<EstrazioniCilaDTO>();

		try {
		String sqlString = EstrazioniPratiche.estazioneCilaSQL;
		Query query = em.createNativeQuery(sqlString);
		query.setParameter(2, filter.getDataInizio());
		query.setParameter(3, filter.getDataFine());
//		query.setParameter(1, 893);
		query.setParameter(1, filter.getEnteSelezionato().getIdEnte());
		if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
		
		List<Object[]> list = query.getResultList();
		if (list.size() != 0) {
			for (Object[] obj : list) {
				EstrazioniCilaDTO cila = new EstrazioniCilaDTO();
				cila.setId_Pratica(((Integer)obj[0]));
				cila.setIdentificativo_pratica((String) obj[1]);
				cila.setStato((String) obj[2]);
				cila.setProtocollo((String) obj[3]);
				cila.setIn_carico_a((String) obj[4]);
				cila.setIstruttore((String) obj[5]);
				cila.setData_ricezione((Date) obj[6]);
				cila.setData_protocollazione((Date) obj[7]);
				cila.setData_ric_integrazione((Date) obj[8]);
				cila.setData_sospensione((Date) obj[9]);
				cila.setData_parere_contrario((Date) obj[10]);
				cila.setData_chiusura((Date) obj[11]);
				
				resultList.add(cila);
			}
		}
		} catch (Exception e) {
		throw e;
		} finally {
		}
		return resultList;
	}
	
	public List<EstrazioniCilaDTO> listPraticheCILA(Filter filter) throws Exception {

		List<EstrazioniCilaDTO> resultList = new ArrayList<EstrazioniCilaDTO>();

		List<Object[]> list = new ArrayList<Object[]>();

		try {
			String sqlString = EstrazioniPratiche.estazioneCilaSQL;
			Query query = em.createNativeQuery(sqlString);
			query.setParameter(2, filter.getDataInizio());
			query.setParameter(3, filter.getDataFine());
//			query.setParameter(1, 893);
			query.setParameter(1, filter.getEnteSelezionato().getIdEnte());

			list = query.getResultList();
			if (list.size() != 0) {
				for (Object[] obj : list) {
					EstrazioniCilaDTO cila = new EstrazioniCilaDTO();
					cila.setId_Pratica(((Integer)obj[0]));
					cila.setIdentificativo_pratica((String) obj[1]);
					cila.setStato((String) obj[2]);
					cila.setProtocollo((String) obj[3]);
					cila.setIn_carico_a((String) obj[4]);
					cila.setIstruttore((String) obj[5]);
					cila.setData_ricezione((Date) obj[6]);
					cila.setData_protocollazione((Date) obj[7]);
					cila.setData_ric_integrazione((Date) obj[8]);
					cila.setData_sospensione((Date) obj[9]);
					cila.setData_parere_contrario((Date) obj[10]);
					cila.setData_chiusura((Date) obj[11]);
					
					resultList.add(cila);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return resultList;
	}

	public List<EstrazioniSciaDTO> findPraticheSCIA(Filter filter) throws Exception {
		List<EstrazioniSciaDTO> resultList = new ArrayList<EstrazioniSciaDTO>();

		try {
		String sqlString = EstrazioniPratiche.estazioneSciaSQL;
		Query query = em.createNativeQuery(sqlString);
		query.setParameter(2, filter.getDataInizio());
		query.setParameter(3, filter.getDataFine());
//		query.setParameter(1, 893);
		query.setParameter(1, filter.getEnteSelezionato().getIdEnte());
		
		if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
		
		List<Object[]> list = query.getResultList();
		if (list.size() != 0) {
			for (Object[] obj : list) {
				EstrazioniSciaDTO scia = new EstrazioniSciaDTO();
				scia.setId_Pratica(((Integer)obj[0]));
				scia.setIdentificativo_pratica((String) obj[1]);
				scia.setStato((String) obj[2]);
				scia.setProtocollo((String) obj[3]);
				scia.setIn_carico_a((String) obj[4]);
				scia.setIstruttore((String) obj[5]);
				scia.setData_ricezione((Date) obj[6]);
				scia.setData_protocollazione((Date) obj[7]);
				scia.setData_ric_integrazione((Date) obj[8]);
				scia.setData_divieto((Date) obj[9]);
				scia.setData_Integrazione((Date) obj[10]);
				scia.setData_parere_favorevole((Date) obj[11]);
				scia.setData_parere_contrario((Date) obj[12]);
				scia.setData_chiusura((Date) obj[13]);
				
				resultList.add(scia);
			}
		}
		} catch (Exception e) {
		throw e;
		} finally {
		}
		return resultList;
	}

	public List<EstrazioniSciaDTO> listPraticheSCIA(Filter filter) throws Exception {
		List<EstrazioniSciaDTO> resultList = new ArrayList<EstrazioniSciaDTO>();

		List<Object[]> list = new ArrayList<Object[]>();

		try {
			String sqlString = EstrazioniPratiche.estazioneSciaSQL;
			Query query = em.createNativeQuery(sqlString);
			query.setParameter(2, filter.getDataInizio());
			query.setParameter(3, filter.getDataFine());
//			query.setParameter(1, 893);
			query.setParameter(1, filter.getEnteSelezionato().getIdEnte());

			list = query.getResultList();
			if (list.size() != 0) {
				for (Object[] obj : list) {
					EstrazioniSciaDTO scia = new EstrazioniSciaDTO();
					scia.setId_Pratica(((Integer)obj[0]));
					scia.setIdentificativo_pratica((String) obj[1]);
					scia.setStato((String) obj[2]);
					scia.setProtocollo((String) obj[3]);
					scia.setIn_carico_a((String) obj[4]);
					scia.setIstruttore((String) obj[5]);
					scia.setData_ricezione((Date) obj[6]);
					scia.setData_protocollazione((Date) obj[7]);
					scia.setData_ric_integrazione((Date) obj[8]);
					scia.setData_divieto((Date) obj[9]);
					scia.setData_Integrazione((Date) obj[10]);
					scia.setData_parere_favorevole((Date) obj[11]);
					scia.setData_parere_contrario((Date) obj[12]);
					scia.setData_chiusura((Date) obj[13]);
					
					resultList.add(scia);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return resultList;
	}
	
	public List<EstrazioniPdcDTO> findPratichePDC(Filter filter) throws Exception {
		List<EstrazioniPdcDTO> resultList = new ArrayList<EstrazioniPdcDTO>();

		try {
		String sqlString = EstrazioniPratiche.estrazionePdcSQL;
		Query query = em.createNativeQuery(sqlString);
		query.setParameter(3, filter.getDataInizio());
		query.setParameter(4, filter.getDataFine());
//		query.setParameter(2, 893);
		query.setParameter(2, filter.getEnteSelezionato().getIdEnte());
		query.setParameter(1, 15);
		if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
		
		List<Object[]> list = query.getResultList();
		if (list.size() != 0) {
			for (Object[] obj : list) {
				EstrazioniPdcDTO pdc = new EstrazioniPdcDTO();
				pdc.setId_Pratica(((Integer)obj[0]));
				pdc.setIdentificativo_pratica((String) obj[1]);
				pdc.setStato((String) obj[2]);
				pdc.setProtocollo((String) obj[3]);
				pdc.setIn_carico_a((String) obj[4]);
				pdc.setIstruttore((String) obj[5]);
				pdc.setData_ricezione((Date) obj[6]);
				pdc.setData_protocollazione((Date) obj[7]);
				pdc.setData_avvio_pre_diniego((Date) obj[8]);
				pdc.setData_rice_contrDeduz_pre_diniego((Date) obj[9]);
				pdc.setData_rich_integr_art20((Date) obj[10]);
				pdc.setData_rice_integr_art20((Date) obj[11]);
				pdc.setData_rich_adempimenti((Date) obj[12]);
				pdc.setData_rice_adempimenti((Date) obj[13]);
				pdc.setData_rilascio((Date) obj[14]);
				pdc.setData_diniego_def((Date) obj[15]);
				pdc.setData_chiusura((Date) obj[16]);
				
				resultList.add(pdc);
			}
		}
		} catch (Exception e) {
		throw e;
		} finally {
		}
		return resultList;
	}

	public List<EstrazioniPdcDTO> listPratichePDC(Filter filter) throws Exception {
		List<EstrazioniPdcDTO> resultList = new ArrayList<EstrazioniPdcDTO>();

		List<Object[]> list = new ArrayList<Object[]>();

		try {
			String sqlString = EstrazioniPratiche.estrazionePdcSQL;
			Query query = em.createNativeQuery(sqlString);
			query.setParameter(3, filter.getDataInizio());
			query.setParameter(4, filter.getDataFine());
//			query.setParameter(2, 893);
			query.setParameter(2, filter.getEnteSelezionato().getIdEnte());
			query.setParameter(1, 15);

			list = query.getResultList();
			if (list.size() != 0) {
				for (Object[] obj : list) {
					EstrazioniPdcDTO pdc = new EstrazioniPdcDTO();
					pdc.setId_Pratica(((Integer)obj[0]));
					pdc.setIdentificativo_pratica((String) obj[1]);
					pdc.setStato((String) obj[2]);
					pdc.setProtocollo((String) obj[3]);
					pdc.setIn_carico_a((String) obj[4]);
					pdc.setIstruttore((String) obj[5]);
					pdc.setData_ricezione((Date) obj[6]);
					pdc.setData_protocollazione((Date) obj[7]);
					pdc.setData_avvio_pre_diniego((Date) obj[8]);
					pdc.setData_rice_contrDeduz_pre_diniego((Date) obj[9]);
					pdc.setData_rich_integr_art20((Date) obj[10]);
					pdc.setData_rice_integr_art20((Date) obj[11]);
					pdc.setData_rich_adempimenti((Date) obj[12]);
					pdc.setData_rice_adempimenti((Date) obj[13]);
					pdc.setData_rilascio((Date) obj[14]);
					pdc.setData_diniego_def((Date) obj[15]);
					pdc.setData_chiusura((Date) obj[16]);
					
					resultList.add(pdc);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return resultList;
	}

	public List<EstrazioniAgibDTO> findPraticheAGIB(Filter filter) throws Exception {
		List<EstrazioniAgibDTO> resultList = new ArrayList<EstrazioniAgibDTO>();

		try {
		String sqlString = EstrazioniPratiche.estrazioneAgibSQL;
		Query query = em.createNativeQuery(sqlString);
		query.setParameter(2, filter.getDataInizio());
		query.setParameter(3, filter.getDataFine());
//		query.setParameter(1, 893);
		query.setParameter(1, filter.getEnteSelezionato().getIdEnte());
		if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
		
		List<Object[]> list = query.getResultList();
		if (list.size() != 0) {
			for (Object[] obj : list) {
				EstrazioniAgibDTO agib = new EstrazioniAgibDTO();
				agib.setId_Pratica(((Integer)obj[0]));
				agib.setIdentificativo_pratica((String) obj[1]);
				agib.setStato((String) obj[2]);
				agib.setProtocollo((String) obj[3]);
				agib.setIn_carico_a((String) obj[4]);
				agib.setIstruttore((String) obj[5]);
				agib.setData_ricezione((Date) obj[6]);
				agib.setData_protocollazione((Date) obj[7]);
				agib.setData_ric_integrazione((Date) obj[8]);
				agib.setData_Integrazione((Date) obj[9]);
				agib.setData_parere_positivo((Date) obj[10]);
				agib.setData_parere_contrario((Date) obj[11]);
				agib.setData_chiusura((Date) obj[12]);
				
				resultList.add(agib);
			}
		}
		} catch (Exception e) {
		throw e;
		} finally {
		}
		return resultList;
	}

	public List<EstrazioniAgibDTO> listPraticheAGIB(Filter filter) throws Exception {
		List<EstrazioniAgibDTO> resultList = new ArrayList<EstrazioniAgibDTO>();

		List<Object[]> list = new ArrayList<Object[]>();

		try {
			String sqlString = EstrazioniPratiche.estrazioneAgibSQL;
			Query query = em.createNativeQuery(sqlString);
			query.setParameter(2, filter.getDataInizio());
			query.setParameter(3, filter.getDataFine());
//			query.setParameter(1, 893);
			query.setParameter(1, filter.getEnteSelezionato().getIdEnte());

			list = query.getResultList();
			if (list.size() != 0) {
				for (Object[] obj : list) {
					EstrazioniAgibDTO agib = new EstrazioniAgibDTO();
					agib.setId_Pratica(((Integer)obj[0]));
					agib.setIdentificativo_pratica((String) obj[1]);
					agib.setStato((String) obj[2]);
					agib.setProtocollo((String) obj[3]);
					agib.setIn_carico_a((String) obj[4]);
					agib.setIstruttore((String) obj[5]);
					agib.setData_ricezione((Date) obj[6]);
					agib.setData_protocollazione((Date) obj[7]);
					agib.setData_ric_integrazione((Date) obj[8]);
					agib.setData_Integrazione((Date) obj[9]);
					agib.setData_parere_positivo((Date) obj[10]);
					agib.setData_parere_contrario((Date) obj[11]);
					agib.setData_chiusura((Date) obj[12]);
					resultList.add(agib);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return resultList;
	}
	

	@Transactional
	public List<ProcessiEventi> getProcessiEventiChiusura(Integer idProcesso) throws Exception {
		List<ProcessiEventi> resultList = new ArrayList<ProcessiEventi>();

		try {
			if (idProcesso.equals(11) || idProcesso.equals(15)) {
				String sqlString = "SELECT s FROM ProcessiEventi s WHERE s.idProcesso.idProcesso = :idProcesso AND s.statoPost.idStatoPratica in (2,5);";
				Query query = em.createQuery(sqlString);
				query.setParameter("idProcesso", idProcesso);

				resultList = query.getResultList();
			}

		} catch (Exception e) {
			throw e;
		} finally {
		}
		return resultList;
	}

	@Transactional
	public void aggiornaPraticaArchivio(Pratica pratica) {
		em.merge(pratica);
	}
	
	
	}

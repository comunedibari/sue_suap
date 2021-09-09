package it.wego.cross.dao;

import it.wego.cross.beans.ProcedimentoPermessiBean;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.entity.view.ProcedimentiCollegatiView;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
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
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class ProcedimentiDao {

    @Autowired
    private AuthorizationDao authorizationDao;
    @PersistenceContext
    private EntityManager em;

    public void flush() throws Exception {
        em.flush();
    }

    public void salvaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception {
        em.merge(procedimentoEnte);
    }

    public void inserisciProcedimento(Procedimenti procedimento) throws Exception {
        em.persist(procedimento);
    }

    public void aggiornaProcedimento(Procedimenti procedimento) throws Exception {
        em.merge(procedimento);
    }

    public void inserisciProcedimentiTesti(ProcedimentiTesti procedimentiTesti) throws Exception {
        em.persist(procedimentiTesti);
    }

    public void aggiornaProcedimentiTesti(ProcedimentiTesti procedimentiTesti) throws Exception {
        em.merge(procedimentiTesti);
    }

    public void eliminaProcedimentoEnte(ProcedimentiEnti procedimentoEnte) throws Exception {
        ProcedimentiEnti toRemove = em.merge(procedimentoEnte);
        em.remove(toRemove);
    }

    public void delete(Object obj) throws Exception {
        em.remove(obj);
    }

    public void delete(ProcedimentiTesti testo) throws Exception {
        em.remove(testo);
    }

    public void delete(Procedimenti p) {
        em.remove(p);
    }

    public Procedimenti findProcedimentoByIdProc(Integer idProc) {
        Query query = em.createNamedQuery("Procedimenti.findByIdProc");
        query.setParameter("idProc", idProc);
        Procedimenti procedimento = (Procedimenti) Utils.getSingleResult(query);
        return procedimento;
    }

    public Procedimenti findProcedimentoByCodProc(String codProc) {
        Query query = em.createNamedQuery("Procedimenti.findByCodProc");
        query.setParameter("codProc", codProc);
        Procedimenti procedimento = (Procedimenti) Utils.getSingleResult(query);
        return procedimento;
    }

    public ProcedimentiEnti findProcedimentiEntiByProcedimentoEnte(int idEnte, int idProc) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcedimentiEnti p "
                + "WHERE p.idEnte.idEnte = :idEnte "
                + "AND p.idProc.idProc = :idProc");
        query.setParameter("idEnte", idEnte);
        query.setParameter("idProc", idProc);
        ProcedimentiEnti pe = (ProcedimentiEnti) Utils.getSingleResult(query);
        return pe;
    }

    public List<Procedimenti> findTutti() {
        Query query = em.createNamedQuery("Procedimenti.findAll");
        List<Procedimenti> procedimento = query.getResultList();
        return procedimento;
    }

    public Long countByCodEnte(int codEnte, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<ProcedimentiEnti> c = q.from(ProcedimentiEnti.class);
        q.select(cb.count(c));

        List<Predicate> predicates = new ArrayList<Predicate>();
        ParameterExpression<Integer> enteParameter = cb.parameter(Integer.class);
        predicates.add(cb.equal(c.get("idEnte").get("idEnte"), enteParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(enteParameter, codEnte);

        Long count = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public ProcedimentoPermessiBean getProcedimentoPermessiBean(Integer codUtente, Integer codEnte, String codPermesso, Integer codProcedimento, String lang) {
        Procedimenti procedimento = findProcedimentoByIdProc(codProcedimento);
        ProcedimentiEnti pe = findProcedimentiEntiByProcedimentoEnte(codEnte, codProcedimento);
        UtenteRuoloEnte utenteRuoloEnte = authorizationDao.getRuoloByKey(codUtente, codEnte, codPermesso);
        UtenteRuoloProcedimento utenteRuoloProcedimento = authorizationDao.getUtenteRuoloProcedimento(utenteRuoloEnte.getIdUtenteRuoloEnte(),pe.getIdProcEnte());
        ProcedimentoPermessiBean bean = new ProcedimentoPermessiBean();
        String descrizione = getDescrizioneProcedimento(codProcedimento, lang);
        bean.setNome(descrizione);
        bean.setTermini(procedimento.getTermini());
        bean.setCodEnte(codEnte);
        bean.setCodUtente(codUtente);
        bean.setCodProcedimento(codProcedimento);
        bean.setCodPermesso(descrizione);

        if (pe != null) {
            bean.setIdProcEnte(pe.getIdProcEnte());
        }
        if ( utenteRuoloProcedimento != null) {
            bean.setCodPermesso(utenteRuoloEnte.getCodPermesso().getCodPermesso());
            bean.setDescrizionePermesso(utenteRuoloEnte.getCodPermesso().getDescrizione());
            bean.setAbilitazione("ABILITATO");
        } else {
            bean.setAbilitazione("NON ABILITATO");
        }

        return bean;
    }

    public List<ProcedimentiLocalizzatiView> findAllProcedimentiLocalizzatiPaginated(String lang, Filter filter) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery c = cb.createQuery(Procedimenti.class);
        Root proc = c.from(Procedimenti.class);
        ListJoin procTesti = proc.joinList("procedimentiTestiList", JoinType.INNER);
        Join lingua = procTesti.join("lingue", JoinType.INNER);

        Path<Procedimenti> path = proc.get("idProc");
        CriteriaQuery<Procedimenti> select = c.select(proc);
        c.distinct(true);

        Subquery<Procedimenti> subquery = c.subquery(Procedimenti.class);
        Root procSubquery = subquery.from(Procedimenti.class);
        subquery.select(procSubquery.get("idProc")).distinct(true);
        ListJoin procTestiSubquery = procSubquery.joinList("procedimentiTestiList", JoinType.INNER);
        Join linguaSubquery = procTestiSubquery.join("lingue", JoinType.INNER);

        Predicate p = cb.equal(lingua.get("codLang"), lang);
        Predicate pSubquery = cb.equal(linguaSubquery.get("codLang"), lang);
        if (!Utils.e(filter.getProcedimento())) {
            p = cb.and(p, cb.like(cb.upper(procTesti.get("desProc")), "%" + filter.getProcedimento().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%"));
            pSubquery = cb.and(pSubquery, cb.like(cb.upper(procTestiSubquery.get("desProc")), "%" + filter.getProcedimento().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%"));
        }
        if (!Utils.e(filter.getTipoProcedimento())) {
            p = cb.and(p, cb.equal(proc.get("tipoProc"), filter.getTipoProcedimento()));
            pSubquery = cb.and(p, cb.equal(procSubquery.get("tipoProc"), filter.getTipoProcedimento()));
        }
        ListJoin procedimentiEnti = proc.joinList("procedimentiEntiList", JoinType.LEFT);
        ListJoin procedimentiEntiSubquery = procSubquery.joinList("procedimentiEntiList", JoinType.LEFT);
        //Se il filtro ha un idente allora si filtra in base all'ente e si mostrano solo i procedimenti attivi per qull'ente. Se il filtro ha invec eun ente selezionato allora
        //    l'informazione va usata insieme a "abilitato" o "non abilitato" altrimenti si mostrano comunque tutti. 
        if (filter.getEnteSelezionato() != null) {
            if (!Utils.e(filter.getStatoProcedimento())) {
                if (filter.getStatoProcedimento().equalsIgnoreCase("abilitato")) {
                    p = cb.and(p, cb.equal(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery)));
                } else if (filter.getStatoProcedimento().equalsIgnoreCase("nonabilitato")) {
                    // p = cb.and(p, cb.notEqual(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery).not()));
                }
            } else {
                p = cb.and(p, cb.and(cb.in(path).value(subquery)));

            }
        } else {
            if (filter.getIdEnte() != null) {
                p = cb.and(p, cb.equal(procedimentiEnti.get("idEnte").get("idEnte"), filter.getIdEnte()));
                pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getIdEnte()));
            }
            if (filter.getIdUtente() != null && filter.getEnteSelezionato() == null) {
                Integer idutente = filter.getIdUtente();
                ListJoin procedimentiUtenti = procedimentiEnti.joinList("utenteRuoloProcedimentoList", JoinType.LEFT);
                ListJoin procedimentiUtentiSubquery = procedimentiEntiSubquery.joinList("utenteRuoloProcedimentoList", JoinType.LEFT);
                if (filter.getStatoProcedimentoUtente() != null && filter.getStatoProcedimentoUtente().equalsIgnoreCase("abilitato")) {
                    p = cb.and(p, cb.equal(procedimentiUtenti.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiUtentiSubquery.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery)));
                } else if (filter.getStatoProcedimentoUtente() != null && filter.getStatoProcedimentoUtente().equalsIgnoreCase("non abilitato")) {
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiUtentiSubquery.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery).not()));
                }
            } else {
                p = cb.and(p, cb.and(cb.in(path).value(subquery)));
            }
        }
        subquery.where(pSubquery);

        String orderCol = filter.getOrderColumn();
        if (orderCol != null && !orderCol.equalsIgnoreCase("abilitato")) {
            if (orderCol.equals("desProc")) {
                path = procTesti.get(orderCol);
            } else if (orderCol.equalsIgnoreCase("idProcEnte")) {
                path = proc.get("idProc");
            } else {
                orderCol = "idProc";
                path = proc.get("idProc");
            }
            if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
                c.orderBy(cb.asc(path));
            } else if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("desc")) {
                c.orderBy(cb.desc(path));
            }
        } else if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc") && orderCol != null) {
            c.orderBy(cb.desc(cb.isTrue(cb.notEqual(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()))));

        } else if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("desc") && orderCol != null) {
            c.orderBy(cb.desc(cb.equal(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte())));
        }

        select.where(p);

        TypedQuery<Procedimenti> query = em.createQuery(c);
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        List<Procedimenti> procedimenti = query.getResultList();
        List<ProcedimentiLocalizzatiView> procLocalizzati = new ArrayList<ProcedimentiLocalizzatiView>();
        for (Procedimenti procedimento : procedimenti) {
            procLocalizzati.add(findProcedimentoLocalizzato(lang, procedimento.getIdProc()));
        }
        return procLocalizzati;
    }

    public ProcedimentiLocalizzatiView findProcedimentoLocalizzato(String lang, Integer idProc) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcedimentiLocalizzatiView p "
                + "WHERE p.codLang = :codLang "
                + "AND p.idProc = :idProc ");
        query.setParameter("codLang", lang);
        query.setParameter("idProc", idProc);
        ProcedimentiLocalizzatiView result = (ProcedimentiLocalizzatiView) Utils.getSingleResult(query);
        return result;
    }

    public Long countAllProcedimentiLocalizzati(String lang, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> c = cb.createQuery(Long.class);
        Root proc = c.from(Procedimenti.class);
        ListJoin procTesti = proc.joinList("procedimentiTestiList", JoinType.INNER);
        Join lingua = procTesti.join("lingue", JoinType.INNER);

        Path<Procedimenti> path = proc.get("idProc");
        CriteriaQuery<Long> select = c.select(cb.countDistinct(proc));

        Subquery<Procedimenti> subquery = c.subquery(Procedimenti.class);
        Root procSubquery = subquery.from(Procedimenti.class);
        subquery.select(procSubquery.get("idProc")).distinct(true);
        ListJoin procTestiSubquery = procSubquery.joinList("procedimentiTestiList", JoinType.INNER);
        Join linguaSubquery = procTestiSubquery.join("lingue", JoinType.INNER);

        Predicate p = cb.equal(lingua.get("codLang"), lang);
        Predicate pSubquery = cb.equal(linguaSubquery.get("codLang"), lang);
        if (!Utils.e(filter.getProcedimento())) {
            p = cb.and(p, cb.like(cb.upper(procTesti.get("desProc")), "%" + filter.getProcedimento().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%"));
            pSubquery = cb.and(pSubquery, cb.like(cb.upper(procTestiSubquery.get("desProc")), "%" + filter.getProcedimento().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%"));
        }
        if (!Utils.e(filter.getTipoProcedimento())) {
            p = cb.and(p, cb.equal(proc.get("tipoProc"), filter.getTipoProcedimento()));
            pSubquery = cb.and(p, cb.equal(procSubquery.get("tipoProc"), filter.getTipoProcedimento()));
        }
        ListJoin procedimentiEnti = proc.joinList("procedimentiEntiList", JoinType.LEFT);
        ListJoin procedimentiEntiSubquery = procSubquery.joinList("procedimentiEntiList", JoinType.LEFT);

        if (filter.getEnteSelezionato() != null) {
            if (!Utils.e(filter.getStatoProcedimento())) {
                if (filter.getStatoProcedimento().equalsIgnoreCase("abilitato")) {
                    p = cb.and(p, cb.equal(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery)));
                } else if (filter.getStatoProcedimento().equalsIgnoreCase("nonabilitato")) {
                    // p = cb.and(p, cb.notEqual(procedimentiEnti.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getEnteSelezionato().getIdEnte()));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery).not()));
                }
            } else {
                p = cb.and(p, cb.and(cb.in(path).value(subquery)));
            }
        } else {
            if (filter.getIdEnte() != null) {
                p = cb.and(p, cb.equal(procedimentiEnti.get("idEnte").get("idEnte"), filter.getIdEnte()));
                pSubquery = cb.and(pSubquery, cb.equal(procedimentiEntiSubquery.get("idEnte").get("idEnte"), filter.getIdEnte()));
            }
            if (filter.getIdUtente() != null && filter.getEnteSelezionato() == null) {
                Integer idutente = filter.getIdUtente();
                ListJoin procedimentiUtenti = procedimentiEnti.joinList("utenteRuoloProcedimentoList", JoinType.LEFT);
                ListJoin procedimentiUtentiSubquery = procedimentiEntiSubquery.joinList("utenteRuoloProcedimentoList", JoinType.LEFT);
                if (filter.getStatoProcedimentoUtente() != null && filter.getStatoProcedimentoUtente().equalsIgnoreCase("abilitato")) {
                    p = cb.and(p, cb.equal(procedimentiUtenti.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiUtentiSubquery.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery)));
                } else if (filter.getStatoProcedimentoUtente() != null && filter.getStatoProcedimentoUtente().equalsIgnoreCase("non abilitato")) {
                    pSubquery = cb.and(pSubquery, cb.equal(procedimentiUtentiSubquery.get("utenteRuoloEnte").get("idUtente").get("idUtente"), idutente));
                    p = cb.and(p, cb.and(cb.in(path).value(subquery).not()));
                }
            } else {
                p = cb.and(p, cb.and(cb.in(path).value(subquery)));
            }
        }
        subquery.where(pSubquery);

        select.where(p);

        TypedQuery<Long> query = em.createQuery(c);
        Long count = (Long) query.getSingleResult();
        return count;

    }

    public String getDescrizioneProcedimento(int codProcedimento, String lang) {
        Lingue lingua = findLinguaByCodLang(lang);
        Query query = em.createQuery("SELECT p FROM ProcedimentiTesti p WHERE p.procedimentiTestiPK.idProc = :idProc and p.lingue = :lingue");
        query.setParameter("idProc", codProcedimento);
        query.setParameter("lingue", lingua);

        ProcedimentiTesti pt = (ProcedimentiTesti) Utils.getSingleResult(query);
        if (pt != null) {
            return pt.getDesProc();
        } else {
            return "Descrizione procedimento mancante";
        }
    }

    public Lingue findLinguaByCodLang(String lang) {
        Query query = em.createNamedQuery("Lingue.findByCodLang");
        query.setParameter("codLang", lang);

        Lingue lingua = (Lingue) Utils.getSingleResult(query);
        return lingua;
    }

    public Lingue findLinguaById(Integer id) {
        Query query = em.createNamedQuery("Lingue.findByIdLang");
        query.setParameter("idLang", id);
        Lingue lingua = (Lingue) Utils.getSingleResult(query);
        return lingua;
    }

    public List<ProcedimentiCollegatiView> findProcedimentiByDescriptionAndIdEnte(String description, Integer idEnte) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcedimentiCollegatiView p "
                + "WHERE p.desProc LIKE :descrizione "
                + "AND p.idEnte = :idEnte "
                + "ORDER BY p.desProc, p.desEnte");
        query.setParameter("descrizione", "%" + description.toUpperCase() + "%");
        query.setParameter("idEnte", idEnte);
        List<ProcedimentiCollegatiView> procedimenti = query.getResultList();
        return procedimenti;
    }

    public List<ProcedimentiCollegatiView> findProcedimentiCollegatiByIdEnte(Integer idEnte) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcedimentiCollegatiView p "
                + "WHERE p.idEnte = :idEnte "
                + "ORDER BY p.desProc");
        query.setParameter("idEnte", idEnte);
        List<ProcedimentiCollegatiView> procedimenti = query.getResultList();
        return procedimenti;
    }

    public Long countProcessiEventiByIdProcesso(Integer idProcesso, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<ProcessiEventi> c = q.from(ProcessiEventi.class);
        q.select(cb.count(c));
        ParameterExpression<Integer> idProcessoParameter = cb.parameter(Integer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(c.get("idProcesso").get("idProcesso"), idProcessoParameter));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Long> query = em.createQuery(q);
        query.setParameter(idProcessoParameter, idProcesso);
        Long count = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    public List<ProcessiEventi> findByIdProcesso(Integer idProcesso, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProcessiEventi> q = cb.createQuery(ProcessiEventi.class);
        Root<ProcessiEventi> c = q.from(ProcessiEventi.class);
        q.select(c);
        ParameterExpression<Integer> idProcessoParameter = cb.parameter(Integer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(c.get("idProcesso").get("idProcesso"), idProcessoParameter));
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<ProcessiEventi> query = em.createQuery(q);
        query.setParameter(idProcessoParameter, idProcesso);
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        List<ProcessiEventi> processiEventi = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return processiEventi;
    }

    public List<ProcedimentiEnti> findProcedimentiEnti(Integer idComune) {
        Query query = em.createQuery("SELECT DISTINCT p "
                + " FROM ProcedimentiEnti p JOIN p.idEnte e JOIN e.lkComuniList c"
                + " WHERE c.idComune = :id "
                + " ORDER BY p.idEnte.descrizione ASC, p.idProc.codProc ASC");
        query.setParameter("id", idComune);
        List<ProcedimentiEnti> procedimentiEnti = (List<ProcedimentiEnti>) query.getResultList();
        return procedimentiEnti;
    }

    public ProcedimentiEnti findProcedimentiEntiByID(Integer id) {
        Query query = em.createQuery("SELECT DISTINCT p "
                + " FROM ProcedimentiEnti p "
                + " WHERE p.idProcEnte = :id ");
        query.setParameter("id", id);
        ProcedimentiEnti procedimentiEnti = (ProcedimentiEnti) Utils.getSingleResult(query);
        return procedimentiEnti;
    }
    
    public List<ProcedimentiEnti> findAllProcedimentiEntiByIdProcedimento(Integer idProcedimento) {
        Query query = em.createQuery("SELECT DISTINCT p "
                + " FROM ProcedimentiEnti p "
                + " WHERE p.idProc.idProc = :idProcedimento");
        query.setParameter("idProcedimento", idProcedimento);
        List<ProcedimentiEnti> procedimentiEnti = (List<ProcedimentiEnti>) query.getResultList();
        return procedimentiEnti;
    }

    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(String desc) {
        Query query = em.createQuery("SELECT DISTINCT p "
                + " FROM ProcedimentiEnti p JOIN p.idProc pro JOIN pro.procedimentiTestiList t "
                + " WHERE UPPER(t.desProc) LIKE :desc "
                + " ORDER BY t.desProc ASC");
        query.setParameter("desc", "%" + desc.toUpperCase() + "%");
        List<ProcedimentiEnti> procedimentiEnti = (List<ProcedimentiEnti>) query.getResultList();
        return procedimentiEnti;
    }

    public List<ProcedimentiEnti> findProcedimentiEntiByDescProcedimento(String desc, Utente user) {
        Query query = em.createQuery("SELECT DISTINCT p "
                + " FROM ProcedimentiEnti p JOIN p.idProc pro JOIN pro.procedimentiTestiList t "
                + " WHERE UPPER(t.desProc) LIKE :desc "
                + " ORDER BY t.desProc ASC");
        query.setParameter("desc", "%" + desc.toUpperCase() + "%");
        List<ProcedimentiEnti> procedimentiEnti = (List<ProcedimentiEnti>) query.getResultList();
        return procedimentiEnti;
    }

    public ProcedimentiTesti findProcedimentiTestiByPK(Integer idProc, Integer idLang) {
        Query query = em.createQuery(
                "SELECT DISTINCT p "
                + " FROM ProcedimentiTesti p "
                + " WHERE p.procedimentiTestiPK.idProc = :idProc "
                + " AND p.procedimentiTestiPK.idLang = :idLang ");
        query.setParameter("idProc", idProc);
        query.setParameter("idLang", idLang);
        ProcedimentiTesti procedimentiTesti = (ProcedimentiTesti) Utils.getSingleResult(query);
        return procedimentiTesti;
    }

    public List<ProcedimentiEnti> findAllProcedimentiEnti() {
        Query query = em.createNamedQuery("ProcedimentiEnti.findAll");
        List<ProcedimentiEnti> procedimentiEnti = query.getResultList();
        return procedimentiEnti;
    }

    public List<Enti> findEntiFromProcedimento(Integer idProc) {
        List<Enti> le = null;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<ProcedimentiEnti> c = q.from(ProcedimentiEnti.class);
        Join joinEnti = c.join("idEnte");
        q.select(c).distinct(true);
        q.orderBy(cb.asc(joinEnti.get("descrizione")));
        Predicate p = cb.equal(c.get("idProc").get("idProc"), idProc);
        q.where(p);
        List<ProcedimentiEnti> lpe = em.createQuery(q).getResultList();
        if (lpe != null) {
            le = new ArrayList<Enti>();
            for (ProcedimentiEnti pe : lpe) {
                le.add(pe.getIdEnte());
            }
        }
        return le;
    }

    public List<ProcedimentiLocalizzatiView> findAllProcedimentiLocalizzati(String lang) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<ProcedimentiLocalizzatiView> c = q.from(ProcedimentiLocalizzatiView.class);
        q.select(c).distinct(true);
        q.orderBy(cb.asc(c.get("desProc")));
        Predicate p = cb.equal(c.get("codLang"), lang);
        q.where(p);
        List<ProcedimentiLocalizzatiView> result = em.createQuery(q).getResultList();
        return result;
    }

    public Long countProcedimentiNonLocalizzatiAbilitati(String lang, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<ProcedimentiLocalizzatiView> c = q.from(ProcedimentiLocalizzatiView.class);
        q.select(cb.count(c));
        Predicate p = cb.equal(c.get("codLang"), lang);

        if (!Utils.e(filter.getProcedimento())) {
            Expression<String> path = c.get("desProc");
            Expression<String> param = cb.parameter(String.class, "%" + filter.getProcedimento() + "%");
            Expression<String> lowerPath = cb.lower(path);
            Expression<String> lowerParam = cb.lower(param);
            p = cb.and(p, cb.like(lowerPath, "%" + filter.getProcedimento().toLowerCase() + "%"));
            // p = cb.and(p, cb.like(lowerPath, lowerParam));
        }
        q.where(p);
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }

    public List<ProcedimentiLocalizzatiView> findProcedimentiNonLocalizzatiAbilitati(String lang, Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProcedimentiLocalizzatiView> q = cb.createQuery(ProcedimentiLocalizzatiView.class);
        Root<ProcedimentiLocalizzatiView> c = q.from(ProcedimentiLocalizzatiView.class);
        q.select(c);
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        Predicate p = cb.equal(c.get("codLang"), lang);

        if (!Utils.e(filter.getProcedimento())) {
            Expression<String> path = c.get("desProc");
//            Expression<String> param = cb.parameter(String.class, "%" + filter.getProcedimento() + "%");
            Expression<String> lowerPath = cb.lower(path);
//            Expression<String> lowerParam = cb.lower(param);

            // p = cb.and(p ,cb.like(cb.lower(c.<String>get("desProc")), "%"+filter.getProcedimento().toLowerCase()+"%"));
//             p = cb.and(p ,cb.like(lowerPath, "%"+filter.getProcedimento().toLowerCase()+"%"));
            p = cb.and(p, cb.like(lowerPath, "%" + filter.getProcedimento().toLowerCase() + "%"));

        }
        q.where(p);
        TypedQuery<ProcedimentiLocalizzatiView> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<ProcedimentiLocalizzatiView> procedimenti = query.getResultList();
        return procedimenti;
    }

    public List<ProcedimentiTesti> findProcedimentiTestiByIdProc(Integer idProc) {
        Query query = em.createNamedQuery("ProcedimentiTesti.findByIdProc");
        query.setParameter("idProc", idProc);
        List<ProcedimentiTesti> result = query.getResultList();
        return result;
    }

    public ProcedimentiTesti findProcedimentiTestiByIdProcAndLang(Integer idLang, Integer idProc) {
        Query query = em.createNamedQuery("ProcedimentiTesti.findByIdLangAndIdProc");
        query.setParameter("idProc", idProc);
        query.setParameter("idLang", idLang);
        List<ProcedimentiTesti> result = query.getResultList();
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<String> findDistinctTipiProcedimento() {
        Query query = em.createQuery("SELECT DISTINCT p.tipoProc FROM Procedimenti p");
        List<String> result = query.getResultList();
        return result;
    }

    public ProcedimentiEnti findProcedimentoEnteById(Integer id) {
        Query query = em.createQuery("SELECT p FROM ProcedimentiEnti p WHERE p.idProcEnte = :idProcEnte");
        query.setParameter("idProcEnte", id);
        List<ProcedimentiEnti> result = query.getResultList();
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<ProcedimentiEnti> findProcedimentiEntiByProcesso(Integer idProcesso) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<ProcedimentiEnti> c = q.from(ProcedimentiEnti.class);
        q.select(c).distinct(true);
        ParameterExpression<Integer> idProcessoParam = cb.parameter(Integer.class);
        Expression<Integer> idProcessoField = c.get("idProcesso").get("idProcesso");
        predicates.add(cb.equal(idProcessoField, idProcessoParam));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<ProcedimentiEnti> query = em.createQuery(q);
        query.setParameter(idProcessoParam, idProcesso);
        List<ProcedimentiEnti> res = query.getResultList();
        return res;
    }
    
    public ProcedimentiEnti findUfficiByProcEnte( int idProc,int idEnte) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcedimentiEnti p "
                + "WHERE p.idEnte.idEnte = :idEnte "
                + "AND p.idProc.idProc = :idProc");
        query.setParameter("idEnte", idEnte);
        query.setParameter("idProc", idProc);
        ProcedimentiEnti pe = (ProcedimentiEnti) Utils.getSingleResult(query);
        return pe;
    }
}

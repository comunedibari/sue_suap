/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.StepDTO;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiAnagrafica;
import it.wego.cross.entity.ProcessiEventiEnti;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.ProcessiSteps;
import it.wego.cross.entity.Scadenze;
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
public class ProcessiDao {

    public static final String TEMPLATE_TIPO_DOCUMENT = "DOCUMENT";
    public static final String TEMPLATE_TIPO_MAIL_OBJECT = "MAIL_OBJECT";
    public static final String TEMPLATE_TIPO_MAIL_BODY = "MAIL_BODY";
    @PersistenceContext
    private EntityManager em;

    public void insert(Processi processo) throws Exception {
        em.persist(processo);
    }

    public void insert(ProcessiEventi processiEventi) {
        em.persist(processiEventi);
    }

    public void update(ProcessiEventiScadenze scadenze) {
        em.merge(scadenze);
    }

    public void update(Processi processo) throws Exception {
        em.merge(processo);
    }

    public void delete(Processi processo) throws Exception {
        em.remove(processo);
    }

    public void delete(ProcessiEventi processoEvento) throws Exception {
        em.remove(processoEvento);
    }

    public void delete(ProcessiEventiScadenze scadenza) throws Exception {
        em.remove(scadenza);
    }

    public void insert(ProcessiSteps step) {
        em.persist(step);
    }

    public void update(ProcessiSteps step) throws Exception {
        em.merge(step);
    }

    public void delete(ProcessiSteps step) throws Exception {
        em.remove(step);
    }

    public void delete(ProcessiEventiAnagrafica processiEventiAnagrafica) throws Exception {
        em.remove(processiEventiAnagrafica);
    }

    public void delete(ProcessiEventiEnti processiEventiEnti) throws Exception {
        em.remove(processiEventiEnti);
    }

    public void insert(ProcessiEventiAnagrafica processiEventiAnagrafica) {
        em.persist(processiEventiAnagrafica);
    }

    public void insert(ProcessiEventiEnti processiEventiEnti) {
        em.persist(processiEventiEnti);
    }

    public Processi findProcessiById(Integer idProcesso) {
        Query query = em.createNamedQuery("Processi.findByIdProcesso");
        query.setParameter("idProcesso", idProcesso);
        Processi processo = (Processi) Utils.getSingleResult(query);
        return processo;
    }

    public Processi findProcessiByCodProcesso(String codProcesso) {
        Query query = em.createNamedQuery("Processi.findByCodProcesso");
        query.setParameter("codProcesso", codProcesso);
        Processi processo = (Processi) Utils.getSingleResult(query);
        return processo;
    }

    public ProcessiEventi findByIdEvento(Integer idEvento) {
        Query query = em.createNamedQuery("ProcessiEventi.findByIdEvento");
        query.setParameter("idEvento", idEvento);
        ProcessiEventi evento = (ProcessiEventi) Utils.getSingleResult(query);
        return evento;
    }

    public ProcessiEventi getEventoTriggerStep(ProcessiSteps processiSteps) throws Exception {
        ProcessiEventi processiEvento;
        processiEvento = (ProcessiEventi) Utils.getField(processiSteps, "id_evento_trigger");
        return processiEvento;
    }

    public ProcessiEventi getEventoResultStep(ProcessiSteps processiSteps) throws Exception {
        ProcessiEventi processiEvento;
        processiEvento = (ProcessiEventi) Utils.getField(processiSteps, "id_evento_result");
        return processiEvento;
    }

    public void insertScadenza(Scadenze scadenza) throws Exception {
        em.persist(scadenza);
    }

    public void aggiornaScadenza(Scadenze scadenza) throws Exception {
        em.merge(scadenza);
    }

    public void update(ProcessiEventi eventoDB) {
        em.merge(eventoDB);
    }

    public PraticheEventi findLastEventForPraticaEvento(Pratica pratica, ProcessiEventi evento) {
        Query query = em.createQuery(""
                + "SELECT pe FROM PraticheEventi pe "
                + "WHERE pe.idPratica = :idPratica "
                + "AND pe.dataEvento=( "
                + "SELECT max(pei.dataEvento) "
                + "FROM PraticheEventi pei "
                + "WHERE pei.idPratica = :idPratica "
                + ")");
        query.setParameter("idPratica", pratica);
        PraticheEventi praticaEvento = (PraticheEventi) Utils.getSingleResult(query);
        return praticaEvento;
    }

    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codEvento, Integer idProcesso) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcessiEventi p "
                + "WHERE p.codEvento = :codEvento "
                + "AND p.idProcesso.idProcesso = :idProcesso");
        query.setParameter("codEvento", codEvento);
        query.setParameter("idProcesso", idProcesso);

        ProcessiEventi pe = (ProcessiEventi) Utils.getSingleResult(query);
        return pe;
    }

    public EventiTemplate findEventiTemplateById(Integer idEventoTemplate) {
        Query query = em.createNamedQuery("EventiTemplate.findByIdEventoTemplate");
        query.setParameter("idEventoTemplate", idEventoTemplate);

        EventiTemplate evento = (EventiTemplate) Utils.getSingleResult(query);
        return evento;
    }

    public List<ProcessiEventi> findTuttiProcessiEventi() {
        /*Query query = em.createQuery("SELECT p "
         + "FROM ProcessiEventi p ");
         */
        Query query = em.createNamedQuery("ProcessiEventi.findAll");
        List<ProcessiEventi> eventi = query.getResultList();
        return eventi;
    }

    public List<ProcessiEventi> findAllProcessiEventi() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProcessiEventi> q = cb.createQuery(ProcessiEventi.class);
        Root<ProcessiEventi> c = q.from(ProcessiEventi.class);
        q.select(c);
        q.orderBy(cb.asc(c.get("desEvento")));
        TypedQuery<ProcessiEventi> query = em.createQuery(q);
        List<ProcessiEventi> eventi = query.getResultList();
        return eventi;
    }

    public Long countProcessi() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Processi> c = q.from(Processi.class);
        q.select(cb.count(c));
        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return count;
    }

    /**
     * ^^CS CONSOLE EVENTO
     */
    public List<Processi> findProcessi(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Processi> q = cb.createQuery(Processi.class);
        Root<Processi> c = q.from(Processi.class);
        ParameterExpression<String> processiDesc = null;
        List<Predicate> predicates = new ArrayList<Predicate>();

        q.select(c);
        if (!Utils.e(filter.getProcesso())) {
            processiDesc = cb.parameter(String.class);
            Expression<String> desProcesso = c.get("desProcesso");
            predicates.add(cb.like(cb.upper(desProcesso), processiDesc));
        }
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Processi> query = em.createQuery(q);
        if (!Utils.e(filter.getProcesso())) {
            query.setParameter(processiDesc, "%" + filter.getProcesso().trim().replace("  ", " ").toUpperCase().replace(" ", "%%") + "%");
        }
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<Processi> processi = query.getResultList();
        if (filter.getLimit() != null) {
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(c));

            Integer totalRecords = em.createQuery(cq).getSingleResult().intValue();
            Integer maxResult = filter.getLimit();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            filter.setTotale(totalRecords);
        }
        return processi;
    }

    public List<ProcessiEventi> findProcessiEventi(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProcessiEventi> q = cb.createQuery(ProcessiEventi.class);
        Root<ProcessiEventi> c = q.from(ProcessiEventi.class);
        q.select(c);
        if (filter.getOrderDirection() != null && filter.getOrderDirection().equals("asc")) {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        }
        TypedQuery<ProcessiEventi> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<ProcessiEventi> eventi = query.getResultList();
        if (filter.getLimit() != null) {
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(c));

            Integer totalRecords = em.createQuery(cq).getSingleResult().intValue();
            Integer maxResult = filter.getLimit();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            filter.setTotale(totalRecords);
        }
        return eventi;
    }

    public List<ProcessiEventi> findProcessiEventiByIdProcesso(Integer idProcesso) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcessiEventi p "
                + "WHERE p.idProcesso.idProcesso = :idProcesso");
        query.setParameter("idProcesso", idProcesso);

        List<ProcessiEventi> processi = query.getResultList();
        return processi;
    }

    public List<ProcessiEventi> findProcessiEventiByIdProcedimento(Integer idProcedimento) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcessiEventi p "
                + "WHERE p.idProcedimentoRiferimento.idProc = :idProcedimentoRiferimento");
        query.setParameter("idProcedimentoRiferimento", idProcedimento);
        List<ProcessiEventi> processi = query.getResultList();
        return processi;
    }

    public List<ProcessiSteps> findProcessiStepsByIdEventoTrigger(Integer idEvento) {
        Query query = em.createNamedQuery("ProcessiSteps.findByIdEventoTrigger");
        query.setParameter("idEventoTrigger", idEvento);

        List<ProcessiSteps> steps = query.getResultList();
        return steps;
    }

    public List<StepDTO> findStepsByIdEvento(Integer idEvento, Filter filter) {
        Query query = em.createNativeQuery("SELECT "
                + "pe.id_evento idEventoTrigger, "
                + "pe.cod_evento codEventoTrigger, "
                + "pe.des_evento desEventoTrigger, "
                + "pe1.id_evento idEventoResult, "
                + "pe1.cod_evento codEventoResult, "
                + "pe1.des_evento desEventoResult, "
                + "ps.tipo_operazione operazione "
                + "FROM processi_eventi pe "
                + "JOIN processi p ON pe.id_processo = p.id_processo "
                + "LEFT JOIN processi_eventi pe1 ON p.id_processo=pe1.id_processo "
                + "LEFT JOIN processi_steps ps ON ps.id_evento_result = pe1.id_evento "
                + "AND ps.id_evento_trigger = pe.id_evento "
                + "WHERE pe.id_evento = " + idEvento);
//                + "ORDER BY " + filter.getOrderColumn() + " " + filter.getOrderDirection()); //THIS MUST BE FIXED !!!!!
        query.setFirstResult(filter.getOffset());
        query.setMaxResults(filter.getOffset());
        List<Object[]> result = query.getResultList();
        List<StepDTO> steps = serializeStep(result);
        return steps;
    }

    private List<StepDTO> serializeStep(List<Object[]> result) {
        List<StepDTO> steps = new ArrayList<StepDTO>();
        for (Object[] o : result) {
            StepDTO dto = new StepDTO();
            dto.setIdEventoTrigger((Integer) o[0]);
            dto.setCodEventoTrigger((String) o[1]);
            dto.setDesEventoTrigger((String) o[2]);
            dto.setIdEventoResult((Integer) o[3]);
            dto.setCodEventoResult((String) o[4]);
            dto.setDesEventoResult((String) o[5]);
            dto.setOperazione((String) o[6]);
            steps.add(dto);
        }
        return steps;
    }

    public ProcessiSteps findByKey(Integer idEventoTrigger, Integer idEventoResult) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcessiSteps p "
                + "WHERE p.processiStepsPK.idEventoTrigger = :idEventoTrigger "
                + "AND p.processiStepsPK.idEventoResult = :idEventoResult");
        query.setParameter("idEventoTrigger", idEventoTrigger);
        query.setParameter("idEventoResult", idEventoResult);
        ProcessiSteps step = (ProcessiSteps) Utils.getSingleResult(query);
        return step;
    }

    public ProcessiEventiScadenze findScadenzaProcessoEvento(Integer idEvento, String idAnaScadenza) {
        Query query = em.createQuery("SELECT p "
                + "FROM ProcessiEventiScadenze p "
                + "WHERE p.processiEventiScadenzePK.idEvento = :idEvento "
                + "AND p.processiEventiScadenzePK.idAnaScadenza = :idAnaScadenza");
        query.setParameter("idEvento", idEvento);
        query.setParameter("idAnaScadenza", idAnaScadenza);
        ProcessiEventiScadenze pes = (ProcessiEventiScadenze) Utils.getSingleResult(query);
        return pes;
    }

    public List<ProcessiEventiScadenze> findScadenzaProcessoEventoByIdEvento(Integer idEvento) {
        Query query = em.createNamedQuery("ProcessiEventiScadenze.findByIdEvento");
        query.setParameter("idEvento", idEvento);
        List<ProcessiEventiScadenze> scadenze = query.getResultList();
        return scadenze;
    }

    public void flush() {
        em.flush();
    }

    public List<Processi> findAllProcessi() {
        Query query = em.createNamedQuery("Processi.findAll");
        List<Processi> processi = query.getResultList();
        return processi;
    }
}

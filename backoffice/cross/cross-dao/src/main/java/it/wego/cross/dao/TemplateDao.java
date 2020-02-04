package it.wego.cross.dao;

import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class TemplateDao extends Paginator {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LookupDao lookupDao;

    public void insert(Templates template) throws Exception {
        em.persist(template);
        em.flush();
    }

    public void update(Templates template) throws Exception {
        em.merge(template);
        em.flush();
    }

    public void insert(EventiTemplate eventiTemplate) throws Exception {
        em.persist(eventiTemplate);
        em.flush();
    }

    public void update(EventiTemplate eventiTemplate) throws Exception {
        em.merge(eventiTemplate);
        em.flush();
    }

    public void delete(EventiTemplate eventiTemplate) throws Exception {
        em.remove(eventiTemplate);
        em.flush();
    }

    public void delete(Templates template) throws Exception {
        em.remove(template);
        em.flush();
    }

    //^^CS MODIFICA
    public Templates getTemplatesPerID(Templates template) {
        Integer idTemplate = template.getIdTemplate();
        if (idTemplate != null) {
            return getTemplatesPerID(idTemplate);
        } else {
            return null;
        }
    }
    //^^CS MODIFICA

    public Templates getTemplatesPerID(Integer id) {
        Query query = em.createNamedQuery("Templates.findByIdTemplate");
        query.setParameter("idTemplate", id);
        List<Templates> l = query.getResultList();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }
    //^^CS MODIFICA

    public EventiTemplate getEventoTemplatesPerID(Integer id) {
        Query query = em.createNamedQuery("EventiTemplate.findByIdEventoTemplate");
        query.setParameter("idEventoTemplate", id);
        if (query.getResultList() != null && query.getResultList().size() > 0) {
            return (EventiTemplate) query.getSingleResult();
        }
        return null;
    }

    public List<EventiTemplate> getEventiTemplatesPerProcedimento(Integer idProcedimento) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery q = cb.createQuery();
        Root<EventiTemplate> c = q.from(EventiTemplate.class);
        q.select(c).distinct(true);
        ParameterExpression<Integer> idProcedimentoParam = cb.parameter(Integer.class);
        Expression<Integer> idProcedimentoField = c.get("idProcedimento").get("idProc");
        predicates.add(cb.equal(idProcedimentoField, idProcedimentoParam));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<EventiTemplate> query = em.createQuery(q);
        query.setParameter(idProcedimentoParam, idProcedimento);
        List<EventiTemplate> res = query.getResultList();
        return res;
    }

    public List<Templates> getTuttiTemplates(Filter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        //CriteriaQuery<Templates> q = cb.createQuery(Templates.class);
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<Templates> c = q.from(Templates.class);
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Expression<String> temp = cb.literal("template");
        q.multiselect(
                c.get("idTemplate").alias("idTemplate"),
                c.get("descrizione").alias("descrizione"),
                c.get("nomeFile").alias("nomeFile"),
                c.get("nomeFileOriginale").alias("nomeFileOriginale"),
                c.get("mimeType").alias("mimeType"),
                c.get("fileOutput").alias("fileOutput"));
        ParameterExpression<String> descrizione = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getDescrizioneTemplate() != null) {
            descrizione = cb.parameter(String.class);
            predicates.add(cb.like(cb.upper(c.<String>get("descrizione")), "%" + filter.getDescrizioneTemplate().toUpperCase() + "%"));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        if (filter.getOrderColumn() != null && filter.getOrderDirection() != null) {
            if (filter.getOrderDirection().toUpperCase().equals("DESC")) {
                q.orderBy(cb.desc(c.get("descrizione")));
            } else {
                q.orderBy(cb.asc(c.get("descrizione")));
            }
        } else {
            q.orderBy(cb.asc(c.get("descrizione")));
        }
        TypedQuery<Tuple> query = em.createQuery(q);

        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        if (filter.getLimit() != null) {
            cq.select(cb.count(c));
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            Integer totalRecords = em.createQuery(cq).getSingleResult().intValue();
            Integer maxResult = filter.getLimit();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            filter.setTotale(totalRecords);
        }

        List<Templates> teplates = new ArrayList<Templates>();
        for (Tuple t : query.getResultList()) {
            Templates teplate = new Templates();
            teplate.setDescrizione((String) t.get("descrizione"));
            teplate.setIdTemplate((Integer) t.get("idTemplate"));
            teplate.setNomeFile((String) t.get("nomeFile"));
            teplate.setNomeFileOriginale((String) t.get("nomeFileOriginale"));
            teplate.setMimeType((String) t.get("mimeType"));
            teplate.setFileOutput((String) t.get("fileOutput"));
            teplates.add(teplate);
        }

        return teplates;
    }

    public List<EventiTemplate> getTemplatesPerEnte(ProcessiEventi evento, Enti ente, Procedimenti procedimento) {
        Log.APP.info("getTemplatesPerEnte: idProcessiEvento " + (evento == null ? "null" : evento.getIdEvento()) + ", IdEnte " + (ente == null ? "null" : ente.getIdEnte()) + ", IdProcedimento " + (procedimento == null ? "null" : procedimento.getIdProc()));
        Query query;
        List<EventiTemplate> eventiTemplate;

        query = em.createQuery("SELECT e "
                + "FROM EventiTemplate e "
                + "WHERE e.idEvento = :idEvento "
                + "AND e.idEnte = :idEnte "
                + "AND e.idProcedimento = :idProcedimento");
        query.setParameter("idEvento", evento);
        query.setParameter("idEnte", ente);
        query.setParameter("idProcedimento", procedimento);
        eventiTemplate = query.getResultList();

        if (eventiTemplate.isEmpty()) {
            query = em.createQuery("SELECT e "
                    + "FROM EventiTemplate e "
                    + "WHERE e.idEvento = :idEvento "
                    + "AND e.idEnte = :idEnte "
                    + "AND e.idProcedimento IS NULL ");
            query.setParameter("idEvento", evento);
            query.setParameter("idEnte", ente);
            eventiTemplate = query.getResultList();
        }

        if (eventiTemplate.isEmpty()) {
            query = em.createQuery("SELECT e "
                    + "FROM EventiTemplate e "
                    + "WHERE e.idEvento = :idEvento "
                    + "AND e.idEnte IS NULL "
                    + "AND e.idProcedimento IS NULL ");
            query.setParameter("idEvento", evento);
            eventiTemplate = query.getResultList();
        }

        if (eventiTemplate.isEmpty()) {
            query = em.createQuery("SELECT e "
                    + "FROM EventiTemplate e "
                    + "WHERE e.idEvento IS NULL "
                    + "AND e.idEnte IS NULL "
                    + "AND e.idProcedimento IS NULL ");
            eventiTemplate = query.getResultList();
        }

        return eventiTemplate;
    }

    public List<EventiTemplate> findEventiTemplate(Filter filter) {
        Log.SQL.info("Eseguo i metodo findEventiTemplate");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EventiTemplate> q = cb.createQuery(EventiTemplate.class);
        Root<EventiTemplate> c = q.from(EventiTemplate.class);
        q.select(c);
        ParameterExpression<Integer> idEventiTemplate = null;
        ParameterExpression<Integer> idEnti = null;
//        ParameterExpression<Integer> idEvento = null;
        ParameterExpression<String> descEvento = null;
        ParameterExpression<Integer> idProcedimento = null;
        ParameterExpression<Integer> idTemplate = null;
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (filter.getIdEventoTemplate() != null) {
            idEventiTemplate = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idEventiTemplate").get("idEventiTemplate"), filter.getIdEventoTemplate()));
        }
        if (!Utils.e(filter.getDescEvento())) {
            descEvento = cb.parameter(String.class);
            predicates.add(cb.equal(c.get("idEvento").get("desEvento"), filter.getDescEvento()));
        }
        if (filter.getIdEnte() != null) {
            idEnti = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idEnte").get("idEnte"), filter.getIdEnte()));
        }
        if (filter.getIdProcedimento() != null) {
            idProcedimento = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idProcedimento").get("idProc"), filter.getIdProcedimento()));
        }
        if (filter.getIdTemplate() != null) {
            idTemplate = cb.parameter(Integer.class);
            predicates.add(cb.equal(c.get("idTemplate").get("idTemplate"), filter.getIdTemplate()));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        if (filter.getOrderColumn() != null && filter.getOrderDirection() != null) {

            if (filter.getOrderDirection().toUpperCase().equals("DESC")) {
                if (filter.getOrderColumn().equals("idEvento") || filter.getOrderColumn().equals("descEvento")) {
                    q.orderBy(cb.desc(c.get("idEvento").get("desEvento")));
                }
                if (filter.getOrderColumn().equals("idEnte") || filter.getOrderColumn().equals("descEnte")) {
                    q.orderBy(cb.desc(c.get("idEnte").get("descrizione")));
                }
                if (filter.getOrderColumn().equals("idProcedimento") || filter.getOrderColumn().equals("descProcedimento")) {
                    q.orderBy(cb.desc(c.get("idProcedimento").get("codProc")));
                }
                if (filter.getOrderColumn().equals("idTemplate") || filter.getOrderColumn().equals("descTemplate")) {
                    q.orderBy(cb.desc(c.get("idTemplate").get("descrizione")));
                }
            } else {
                if (filter.getOrderColumn().equals("idEvento") || filter.getOrderColumn().equals("descEvento")) {
                    q.orderBy(cb.asc(c.get("idEvento").get("desEvento")));
                }
                if (filter.getOrderColumn().equals("idEnte") || filter.getOrderColumn().equals("descEnte")) {
                    q.orderBy(cb.asc(c.get("idEnte").get("descrizione")));
                }
                if (filter.getOrderColumn().equals("idProcedimento") || filter.getOrderColumn().equals("descProcedimento")) {
                    q.orderBy(cb.asc(c.get("idProcedimento").get("procedimentiTestiList").get("desProc")));
                }
                if (filter.getOrderColumn().equals("idTemplate") || filter.getOrderColumn().equals("descTemplate")) {
                    q.orderBy(cb.asc(c.get("idTemplate").get("descrizione")));
                }
            }
        }
        TypedQuery<EventiTemplate> query = em.createQuery(q);
        if (filter.getIdEventoTemplate() != null) {
            query.setParameter(idEventiTemplate, filter.getIdEventoTemplate());
        }
        if (!Utils.e(filter.getDescEvento())) {
            query.setParameter(descEvento, filter.getDescEvento());
        }
        if (filter.getIdEnte() != null) {
            query.setParameter(idEnti, filter.getIdEnte());
        }
        if (filter.getIdProcedimento() != null) {
            query.setParameter(idProcedimento, filter.getIdProcedimento());
        }
        if (filter.getIdTemplate() != null) {
            query.setParameter(idTemplate, filter.getIdTemplate());
        }
        if (filter.getLimit() != null) {
            query.setMaxResults(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            query.setFirstResult(filter.getOffset());
        }
        List<EventiTemplate> eventiTemplate = query.getResultList();
        if (filter.getLimit() != null) {
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            cq.select(cb.count(c));
            cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            Integer totalRecords = em.createQuery(cq).getSingleResult().intValue();
            Integer maxResult = filter.getLimit();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            filter.setTotale(totalRecords);
        }
        return eventiTemplate;
    }

    public List<EventiTemplate> getTemplatesPerProcedimento(ProcessiEventi idEvento, Enti idEnte, Procedimenti idProcedimento) {
//        Log.APP.info("getTemplatesPerProcedimenti ProcessiEventi ");
        String qt = "SELECT e "
                + "FROM EventiTemplate e "
                + "WHERE e.idEvento = :idEvento ";
        if (idEnte != null) {
            qt += "AND e.idEnte = :idEnte ";
        } else {
            qt += "AND e.idEnte IS NULL";
        }

        if (idProcedimento != null) {
            qt += "AND e.idProcedimento = :idProcedimento ";
        } else {
            qt += "AND e.idProcedimento IS NULL ";
        }

        Query query = em.createQuery(qt);
        query.setParameter("idEvento", idEvento);
        if (idEnte != null) {
            query.setParameter("idEnte", idEnte);
        }
        if (idProcedimento != null) {
            query.setParameter("idProcedimento", idProcedimento);
        }
        List<EventiTemplate> eventiTemplate = query.getResultList();
        return eventiTemplate;
    }

    public List<EventiTemplate> findTemplateSuEvento(Integer idEvento, Integer idEnte, Integer idProcedimento, Integer idTemplate) {
        Log.SQL.info("Eseguo il metodo findTemplateSuEvento");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EventiTemplate> q = cb.createQuery(EventiTemplate.class);
        Root<EventiTemplate> c = q.from(EventiTemplate.class);
        q.select(c);

        ParameterExpression<Integer> idEventoParameter = cb.parameter(Integer.class);
        ParameterExpression<Integer> idEnteParameter = cb.parameter(Integer.class);
        ParameterExpression<Integer> idProcedimentiParameter = cb.parameter(Integer.class);
        ParameterExpression<Integer> idTemplatesParameter = cb.parameter(Integer.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(c.get("idEvento").get("idEvento"), idEvento));

        if (idEnte != null) {
            predicates.add(cb.equal(c.get("idEnte").get("idEnte"), idEnte));
        }
        if (idProcedimento != null) {
            predicates.add(cb.equal(c.get("idProcedimento").get("idProc"), idProcedimento));
        }
        if (idTemplate != null) {
            predicates.add(cb.equal(c.get("idTemplate").get("idTemplate"), idTemplate));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<EventiTemplate> query = em.createQuery(q);
        query.setParameter(idEventoParameter, idEvento);
        if (idEnte != null) {
            query.setParameter(idEnteParameter, idEnte);
        }
        if (idProcedimento != null) {
            query.setParameter(idProcedimentiParameter, idProcedimento);
        }
        if (idTemplate != null) {
            query.setParameter(idTemplatesParameter, idTemplate);
        }

        List<EventiTemplate> eventiTemplate = query.getResultList();
        return eventiTemplate;
    }
}

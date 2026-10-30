package it.wego.cross.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import it.wego.cross.constants.StatiEmail;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkStatiMail;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;

/**
 *
 * @author CS
 */
//@Configurable
@Repository
public class MailDao {

    @PersistenceContext
    private EntityManager em;

    public void flush() {
        em.flush();
    }

    public Email insert(Email email) {
        em.persist(email);

        return email;
    }

    public Email update(Email email) {
        em.merge(email);

        return email;
    }

    public Email findByIdEmail(Integer id) {
        Query query = em.createQuery("SELECT e "
                + "FROM Email e "
                + "WHERE e.idEmail = :id ");
        query.setParameter("id", id);
        return (Email) Utils.getSingleResult(query);
    }


    public Email findByIdEmailRispostaPec(Integer id) {
    	String mailQuery = 
   			 " SELECT "
    			+ "	e.`id_email`, "
    			+ "	e.`id_pratica_evento`,"
    			+ "	e.`data_inserimento`,"
    			+ "	e.`data_aggiornamento`,"
    			+ "	e.`path_eml`,"
    			+ "	e.`id_messaggio`,"
    			+ "	e.`id_messaggio_pec`,"
    			+ "	e.`email_destinatario`,"
    			+ "	e.`corpo_email`,"
    			+ "	e.`oggetto_email`,"
    			+ "	c.`oggetto_risposta`,"
    			+ "	c.`corpo_risposta`,"
    			+ "	e.`stato`,"
    			+ "	e.`gruppo`,"
    			+ "	e.`tipo_destinazione`"
    			+ " FROM `opencross`.`email` e "
    			+ " LEFT OUTER JOIN email_consegnate c ON (e.id_messaggio = c.msgid)"
    			+ " WHERE e.`id_email` = " + id.intValue();
    	               
		Query query = em.createNativeQuery(mailQuery, Email.class);
		Log.APP.info("\n findByIdEmailRispostaPec:" + mailQuery);
        return (Email) Utils.getSingleResult(query);
    }
	
    public List<Email> findByIdMessaggio(String idMessaggio) {
        Query query = em.createQuery("SELECT e "
                + "FROM Email e "
                + "WHERE e.idMessaggio = :idMessaggio|| e.messageIdPec= :idMessaggio "
                + "ORDER BY e.dataInserimento DESC ");
        query.setParameter("idMessaggio", idMessaggio);
        return query.getResultList();
    }

    public LkStatiMail findstatoByIdStato(Integer id) {
        Query query = em.createQuery("SELECT e "
                + "FROM LkStatiMail e "
                + "WHERE e.idStatiMail = :id ");
        query.setParameter("id", id);
        return (LkStatiMail) query.getResultList().get(0);
    }

    public LkStatiMail findstatoByCodStato(String cod) {
        Query query = em.createQuery("SELECT e "
                + "FROM LkStatiMail e "
                + "WHERE e.codice = :cod");
        query.setParameter("cod", cod);
        return (LkStatiMail) query.getResultList().get(0);
    }

    public LkStatiMail findstatoByDescrizioneStato(String cod) {
        Query query = em.createQuery("SELECT e "
                + "FROM LkStatiMail e "
                + "WHERE e.descrizione = :cod ");
        query.setParameter("cod", cod);
        return (LkStatiMail) query.getResultList().get(0);
    }

//    public List<Email> find(List<String> listaId) {
//        Log.APP.info("\n---------------\n");
//        Query query = em.createQuery(
//                " SELECT e "
//                + " FROM Email e "
//                + " WHERE "
//                + " (e.idMessaggio IN :listaId OR "
//                + " e.idMessaggioPec IN :listaId) AND "
//                + " e.stato.codice NOT IN :stati "
//                + " ORDER BY e.dataInserimento ");
//
//        List<String> stati = new ArrayList<String>();
//        stati.add(StatiEmail.CONFERMATA);
//        stati.add(StatiEmail.RISPEDITA);
//        query.setParameter("listaId", listaId);
//        query.setParameter("stati", stati);
//        Log.APP.info("\n---------------\n");
//        return query.getResultList();
//    }
    public List<Email> findByMessageIds(List<String> listaId) {
        Log.APP.info("\n---------------\n");
        Query query = em.createQuery(
                " SELECT e "
                + " FROM Email e "
                + " WHERE "
                + " e.idMessaggio IN :listaId ");

        query.setParameter("listaId", listaId);
        Log.APP.info("\n---------------\n");
        return query.getResultList();
    }

    public List<Email> findByEmailDestinazione(String emailDestinatario) {
        Query query = em.createQuery(
                " SELECT e "
                + " FROM Email e "
                + " WHERE "
                + " e.emailDestinatario = :emailDestinatario ");

        query.setParameter("emailDestinatario", emailDestinatario);
        return query.getResultList();
    }

    public List<Email> findByStato(List<String> listaId, String stato) {
        Query query = em.createQuery(
                " SELECT e "
                + " FROM Email e "
                + " WHERE "
                + " (e.idMessaggio IN :listaId OR "
                + " e.idMessaggioPec IN :listaId) AND "
                + " e.stato.codice = :stato "
                + " ORDER BY e.dataInserimento ");

        query.setParameter("listaId", listaId);
        query.setParameter("stato", stato);
        return query.getResultList();
    }

    public List<Email> findEmailInstaging() {
        Log.APP.info("\n---------------\n");
        Query query = em.createQuery(
                " SELECT e "
                + " FROM Email e "
                + " WHERE "
                + " e.stato.codice = :stati OR "
                + " e.stato IS NULL "
                + " ORDER BY e.dataInserimento ");
        query.setParameter("stati", StatiEmail.DA_INVIARE);
        Log.APP.info("\n---------------\n");
        return query.getResultList();
    }

    public List<Email> findMailByPaperworkEventId(Integer paperworkEventId) {
        Log.APP.info("call Email.findByIdPraticaEvento");
    	Query query = em.createNamedQuery("Email.findByIdPraticaEvento");
    	query.setParameter("idPraticaEvento", paperworkEventId);
    	return query.getResultList();
    }

    public List<Email> findEmailDaSpedire() {
        List<String> mailStatus = Arrays.asList("S");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Email> q = cb.createQuery(Email.class);
        Root<Email> c = q.from(Email.class);
        q.select(c);
        Expression<String> statoEmailExpression = c.get("stato").get("codice");
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(statoEmailExpression.in(mailStatus));
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Email> query = em.createQuery(q);
        List<Email> email = query.getResultList();
        return email;
    }

    public List<Email> findMailNonInviate(Filter filter, boolean isSuperuser) {
        /*
         Considero anche quelle "da inviare" perché potrebbero essere rimaste in uno stato "sospeso" da parte del workflow
         S = Da inviare
         E = Errore generico
         M = Errore del server
         */
        List<String> mailStatus = Arrays.asList("E", "M");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Email> q = cb.createQuery(Email.class);
        Root<Email> c = q.from(Email.class);
        q.select(c);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Expression<String> statoEmailExpression = c.get("stato").get("codice");
        predicates.add(statoEmailExpression.in(mailStatus));
        if (!isSuperuser) {
            //Se non sono superuser visualizzo solo le email collegate a pratiche in carico all'utente
            Join joinPraticheEventi = c.join("idPraticaEvento");
            Predicate p = cb.equal(joinPraticheEventi.get("idPratica").get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }
        if (filter.getOrderDirection().equalsIgnoreCase("desc")) {
            q.orderBy(cb.desc(c.get(filter.getOrderColumn())));
        } else {
            q.orderBy(cb.asc(c.get(filter.getOrderColumn())));
        }
        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Email> query = em.createQuery(q);
        if (filter.getLimit() != null && filter.getOffset() != null) {
            query.setMaxResults(filter.getLimit());
            query.setFirstResult(filter.getOffset());
        }
        List<Email> email = query.getResultList();
        return email;
    }

    public Long countMailNonInviate(Filter filter, boolean isSuperuser) {
        /*
         Considero anche quelle "da inviare" perché potrebbero essere rimaste in uno stato "sospeso" da parte del workflow
         S = Da inviare
         E = Errore generico
         M = Errore del server
         */
        List<String> mailStatus = Arrays.asList("E", "M");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Email> c = q.from(Email.class);
        q.select(cb.countDistinct(c));
        List<Predicate> predicates = new ArrayList<Predicate>();
        Expression<String> statoEmailExpression = c.get("stato").get("codice");
        predicates.add(statoEmailExpression.in(mailStatus));
        if (!isSuperuser) {
            //Se non sono superuser visualizzo solo le email collegate a pratiche in carico all'utente
            Join joinPraticheEventi = c.join("idPraticaEvento");
            Predicate p = cb.equal(joinPraticheEventi.get("idPratica").get("idUtente").get("idUtente"), filter.getConnectedUser().getIdUtente());
            predicates.add(p);
        }

        q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        TypedQuery<Long> query = em.createQuery(q);
        Long count = query.getSingleResult();
        return count;
    }
    public List<Object> findEmailConfigurationAllComuni(String idMessaggio) {
    	int id_email = 0;
    	String mailQuery = "SELECT 	 "
    			+ "	c.`id_ente`,  "
    			+ "	ec.`id_comune`,  "
    			+ "	e.descrizione AS descrizione_ente, "
    			+ "	lkc.descrizione AS descrizione_comune, "
    			+ "	c.`value`,  "
    			+ "	c.`note`"
    			+ " FROM ((((`opencross`.`configuration` c ) "
    			+ " JOIN `opencross`.`enti` e) "
    			+ " JOIN opencross.enti_comuni ec) "
    			+ " JOIN opencross.lk_comuni lkc) "
    			+ " WHERE ((e.id_ente = c.id_ente) "
    			+ " AND (ec.id_ente = e.id_ente) "
    			+ " AND (lkc.id_comune = ec.id_comune)) "
    			+ " AND c.name IN ('mail.host',  "
    			+ "'mail.imap.host', "
    			+ "'mail.username', "
    			+ "'mail.password', "
    			+ "'mail.imap.port', "
    			+ "'abilita.lettura.email', "
    			+ "'mail.config.cartellaCross', "
    			+ "'mail.config.cartellaLette')  "
    			+ " AND c.value IS NOT NULL "
    			+ " AND c.value <> '' "
    			+ " AND e.descrizione LIKE 'SUE%' "
    			+ "ORDER BY ec.id_comune ";
    	               
		Query query = em.createNativeQuery(mailQuery);
		List<Object> mailConfiguration = null;
        try {
        	mailConfiguration = (List<Object>) query.getSingleResult();
        } catch(Exception e) {
        	id_email = 0;
        }
        return mailConfiguration;		
    }

}

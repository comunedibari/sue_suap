/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.Messaggio;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author giuseppe
 */
//@Configurable
@Repository
public class MessaggiDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(Object object) throws Exception {
        em.persist(object);
    }

    public Long countMessaggiDaLeggere(Utente connectedUser) {
        Query query = em.createQuery("SELECT COUNT(m) "
                + "FROM Messaggio m "
                + "WHERE (m.idUtenteMittente = :utenteConnesso "
                + "OR m.idUtenteDestinatario = :utenteConnesso) "
                + "AND m.status = :status");
        query.setParameter("utenteConnesso", connectedUser);
        query.setParameter("status", "U");
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public List<Messaggio> findMessaggiByPratica(Pratica pratica) {
        Query query = em.createQuery("SELECT m "
                + "FROM Messaggio m "
                + "WHERE m.idPratica = :pratica "
                + "ORDER BY m.dataMessaggio DESC");
        query.setParameter("pratica", pratica);
        List<Messaggio> messaggi = query.getResultList();
        return messaggi;
    }
    
    
}

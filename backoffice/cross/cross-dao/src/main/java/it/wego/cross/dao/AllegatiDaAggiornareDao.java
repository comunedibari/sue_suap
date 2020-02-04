/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuseppe
 */
@Repository
public class AllegatiDaAggiornareDao {

    @PersistenceContext
    private EntityManager em;

    public List<Integer> findPraticheEventiConAllegatiDaAggiornare(Date startDate, Date endDate) {
        String jpql = "SELECT DISTINCT(p.idPraticaEvento) "
                + "FROM PraticheEventi p "
                + "JOIN p.praticheEventiAllegatiList pea "
                + "JOIN pea.allegati a "
                + "WHERE (p.protocollo IS NOT NULL AND p.protocollo != :emptyString) AND (a.pathFile IS NOT NULL OR a.file IS NOT NULL) "
                + "AND p.dataEvento >= :startDate AND p.dataEvento <= :endDate "
                + "ORDER BY p.idPraticaEvento";
        Query query = em.createQuery(jpql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("emptyString", "");

        List<Integer> praticheEventiDaAggiornare = query.getResultList();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return praticheEventiDaAggiornare;
    }

    public Long countPraticheEventiConAllegatiDaAggiornare(Date startDate, Date endDate) {
        String jpql = "SELECT COUNT(DISTINCT(p)) "
                + "FROM PraticheEventi p "
                + "JOIN p.praticheEventiAllegatiList pea "
                + "JOIN pea.allegati a "
                + "WHERE (p.protocollo IS NOT NULL AND p.protocollo != :emptyString) AND (a.pathFile IS NOT NULL OR a.file IS NOT NULL) "
                + "AND p.dataEvento >= :startDate AND p.dataEvento <= :endDate";
        Query query = em.createQuery(jpql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("emptyString", "");
        Long result = (Long) query.getSingleResult();
        String sql = query.unwrap(JpaQuery.class).getDatabaseQuery().getSQLString();
        Log.SQL.info("Query: " + sql);
        return result;
    }
}

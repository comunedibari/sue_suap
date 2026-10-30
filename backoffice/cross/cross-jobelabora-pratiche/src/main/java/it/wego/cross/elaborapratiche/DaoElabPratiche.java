package it.wego.cross.elaborapratiche;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.beans.EventoBean;

@Repository
public class DaoElabPratiche {

    @PersistenceContext
    private EntityManager em;
    
   
    public List<EventoBean> findPraticheDaRiassegnare() {
    	
        String sqlString = "SELECT "
				+ " p2.`id_pratica`, "
				+ " p2.`identificativo_pratica`, "
				+ " p2.id_utente as id_utente_pratica,"
				+ " pe2.id_utente as id_utente_evento,"
				+ " p2.`oggetto_pratica`, "
				+ " p2.`protocollo`, "
				+ " p2.`anno_riferimento`, "
				+ " p2.`id_stato_pratica`, "
				+ " p2.`data_chiusura`, "
				+ " p2.`id_processo`, "
				+ " p2.`id_utente`, "
				+ " p2.`id_comune`, "
				+ " p2.`data_ricezione`, "
				+ " p2.`data_protocollazione`, "
				+ " p2.`anno`, "
				+ " p2.`data_ultimo_evento_esterno`,"
				+ " pe2.id_pratica_evento AS id_pratica_evento,"
				+ " pe2.data_evento AS data_evento,"
				+ " pe2.protocollo AS protocollo_evento,"
				+ " pe2.data_protocollo AS data_protocollo_evento"
				+ " FROM ((pratica p2"
				+ " JOIN `pratiche_eventi` `pe2`)"
				+ " JOIN processi_eventi ppe2)"
				+ " WHERE p2.id_utente IN (250,306,307,308,309,360,364)"
				+ " AND `p2`.`id_pratica` = `pe2`.`id_pratica`"
				+ " AND `pe2`.`id_evento` = `ppe2`.`id_evento`"
				+ " AND  ppe2.cod_evento = 'PROCINT' "
				+ " AND pe2.id_pratica_evento = ("
				+ " SELECT MAX(pe.id_pratica_evento) "
				+ " FROM (`pratica` `p` "
				+ " JOIN `pratiche_eventi` `pe`) "
				+ " WHERE p.id_pratica = p2.id_pratica"
				+ " AND `p`.`id_pratica` = `pe`.`id_pratica`"
				+ "	)";
        //Query query = em.createNativeQuery(sqlString, it.wego.cross.entity.Email.class);
		Query query = em.createNativeQuery(sqlString);
        //query.setParameter(1, idMessaggio);
        return (List<EventoBean>) query.getResultList();		
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

}
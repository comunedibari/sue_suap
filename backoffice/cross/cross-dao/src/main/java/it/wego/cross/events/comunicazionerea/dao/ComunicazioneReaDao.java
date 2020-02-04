package it.wego.cross.events.comunicazionerea.dao;

import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.utils.Utils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class ComunicazioneReaDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(RiIntegrazioneRea integrazioneReaBean) throws Exception {
        em.persist(integrazioneReaBean);
    }

    public void merge(RiIntegrazioneRea integrazioneReaBean) throws Exception {
        em.merge(integrazioneReaBean);
    }

    public void delete(RiIntegrazioneRea integrazioneReaBean) throws Exception {
        em.remove(integrazioneReaBean);
    }

    public RiIntegrazioneRea findRiIntegrazioneReaByIdPratica(Integer idPratica) {
        Query query = em.createQuery("SELECT r "
                + "FROM RiIntegrazioneRea r "
                + "WHERE r.idPratica.idPratica = :idPratica");
        query.setParameter("idPratica", idPratica);
        RiIntegrazioneRea riIntegrazioneRea = (RiIntegrazioneRea) Utils.getSingleResult(query);
        return riIntegrazioneRea;
    }
}

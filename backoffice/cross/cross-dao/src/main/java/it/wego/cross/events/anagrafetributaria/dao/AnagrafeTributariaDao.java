package it.wego.cross.events.anagrafetributaria.dao;

import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkTipoIndirizzo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.events.anagrafetributaria.entity.AtRecordDettaglio;
import it.wego.cross.utils.Utils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class AnagrafeTributariaDao {

    @PersistenceContext
    private EntityManager em;

    public void insert(AtRecordDettaglio recordDettaglio) throws Exception {
        em.persist(recordDettaglio);
    }

    public void merge(AtRecordDettaglio recordDettaglio) throws Exception {
        em.merge(recordDettaglio);
    }

    public void delete(AtRecordDettaglio recordDettaglio) throws Exception {
        em.remove(recordDettaglio);
    }

    public List<AnagraficaRecapiti> findRecapitiByIdAnagrafica(Integer idAnagrafica) {
        Query query = em.createQuery("SELECT a "
                + "FROM AnagraficaRecapiti a "
                + "WHERE a.idAnagrafica.idAnagrafica = :idAnagrafica");
        query.setParameter("idAnagrafica", idAnagrafica);

        List<AnagraficaRecapiti> recapiti = query.getResultList();
        return recapiti;
    }

    public List<AnagraficaRecapiti> findRecapitiByIdAnagraficaIdTipoIndirizzo(Integer idAnagrafica, LkTipoIndirizzo tipoIndirizzo) {
        Query query = em.createQuery("SELECT a "
                + "FROM AnagraficaRecapiti a "
                + "WHERE a.idAnagrafica.idAnagrafica = :idAnagrafica "
                + "AND a.idTipoIndirizzo = :idTipoIndirizzo");
        query.setParameter("idAnagrafica", idAnagrafica);
        query.setParameter("idTipoIndirizzo", tipoIndirizzo);

        List<AnagraficaRecapiti> recapiti = query.getResultList();
        return recapiti;
    }

    public List<AtRecordDettaglio> findRecordDettaglio(String codFornitura, Integer annoRiferimento, Integer idSoggettoObbligato) {
        Query query = em.createQuery("SELECT a "
                + "FROM AtRecordDettaglio a "
                + "WHERE a.codFornitura = :codFornitura "
                + "AND a.annoRiferimento = :annoRiferimento "
                + "AND a.idPratica.idComune.idComune = :idComune");
        query.setParameter("codFornitura", codFornitura);
        query.setParameter("annoRiferimento", annoRiferimento);
        query.setParameter("idComune", idSoggettoObbligato);
        List<AtRecordDettaglio> records = query.getResultList();
        return records;

    }

    public List<AtRecordDettaglio> findRecordDettaglio(Integer idPratica, String codFornitura) {
        Query query = em.createQuery("SELECT a "
                + "FROM AtRecordDettaglio a "
                + "WHERE a.codFornitura = :codFornitura "
                + "AND a.idPratica.idPratica = :idPratica");
        query.setParameter("codFornitura", codFornitura);
        query.setParameter("idPratica", idPratica);
        List<AtRecordDettaglio> record = query.getResultList();
        return record;
    }

    public AtRecordDettaglio findRecordDettaglioBtId(Integer idDettaglio) {
        Query query = em.createNamedQuery("AtRecordDettaglio.findByIdAtRecordDettaglio");
        query.setParameter("idAtRecordDettaglio", idDettaglio);
        AtRecordDettaglio dettaglio = (AtRecordDettaglio) Utils.getSingleResult(query);
        return dettaglio;
    }

    public LkComuni findComuneSede(Integer idSoggetto) {
        Query query = em.createQuery("SELECT r.idComune "
                + "FROM Anagrafica a "
                + "JOIN a.anagraficaRecapitiList ar"
                + "JOIN ar.idRecapito r "
                + "WHERE a.idAnagrafica = :idAnagrafica "
                + "AND r.idTipoIndirizzo.codTipoIndirizzo = :tipoIndirizzo");
        query.setParameter("tipoIndirizzo", "SED");
        query.setParameter("idAnagrafica", idSoggetto);
        LkComuni comuneSede = (LkComuni) Utils.getSingleResult(query);
        return comuneSede;
    }

    public List<LkTipoSistemaCatastale> findAllTipoCatasto() {
        Query query = em.createQuery("SELECT l "
                + "FROM LkTipoSistemaCatastale l "
                + "WHERE l.descrizione IN ('ORDINARIO','TAVOLARE')");
        List<LkTipoSistemaCatastale> tipiCatasto = query.getResultList();
        return tipiCatasto;
    }
}

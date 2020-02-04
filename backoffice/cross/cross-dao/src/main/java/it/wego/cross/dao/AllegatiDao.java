package it.wego.cross.dao;

import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

//@Configurable
@Repository
public class AllegatiDao {

    @PersistenceContext
    private EntityManager em;

    public void insertAllegato(Allegati allegato) throws Exception {
        em.persist(allegato);
    }

    public void inserPraticheEventiAllegato(PraticheEventiAllegati pea) throws Exception{
        em.persist(pea);
    }
    
    public void mergeAllegato(Allegati allegato) throws Exception {
        em.merge(allegato);
    }

    public Allegati getAllegato(Integer idAllegato) {
        Query query = em.createNamedQuery("Allegati.findById");
        query.setParameter("id", idAllegato);

        Allegati allegato = (Allegati) Utils.getSingleResult(query);
        return allegato;
    }
    //^^CS AGGIUNTA

    public Allegati findAllegatoByNomeFile(String nomeFile, Integer idPratica) {
        Query query = em.createQuery(
                " SELECT a "
                + " FROM Allegati a "
                + " JOIN a.praticheEventiAllegatiList pea "
                + " JOIN pea.praticheEventi pe "
                + " WHERE "
                + " a.nomeFile = :nomeFile AND "
                + " pe.idPratica.idPratica = :idPratica ");
        query.setParameter("nomeFile", nomeFile);
        query.setParameter("idPratica", idPratica);
        Allegati allegato = (Allegati) Utils.getSingleResult(query);
        return allegato;
    }

    /**
     * Verifica che un file sia presente su una pratica
     * @param pratica
     * @param idFile
     * @return 
     */
    public boolean checkPraticaAllegato(Pratica pratica, Integer idFile) {
        Allegati allegato = getAllegato(idFile);
        List<Allegati> allegati = new ArrayList<Allegati>();
        if (pratica.getIdModello() != null) {
            allegati.add(pratica.getIdModello());
        }
        for (PraticheEventi pe : pratica.getPraticheEventiList()) {
            for (PraticheEventiAllegati pea : pe.getPraticheEventiAllegatiList()) {
                allegati.add(pea.getAllegati());
            }
        }
        return allegato != null && allegati.contains(allegato);
    }

    public Allegati findByIdFileEsterno(String idFileEsterno) {
        Query query = em.createNamedQuery("Allegati.findByIdFileEsterno");
        query.setParameter("idFileEsterno", idFileEsterno);

        List<Allegati> allegati = query.getResultList();
        Allegati allegato = null;
        //Se ci sono duplicati, prendo il primo: sul documentale corrispondono allo stesso file e sul
        //db potrebbero essere doppi a causa della condivisione
        if (allegati != null && !allegati.isEmpty()) {
            allegato = allegati.get(0);
        }
        return allegato;
    }
}

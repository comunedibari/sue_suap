/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
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
public class ConfigurationDao {


    @PersistenceContext
    private EntityManager em;

    public List<Configuration> findByName(String name) {
        Query query = em.createNamedQuery("Configuration.findByName");
        query.setParameter("name", name);
        List<Configuration> configurations = query.getResultList();
        return configurations;
    }
    
    public List<Configuration> findByName(String name, Integer idEnte, Integer idComune) {
        Query query = em.createQuery("SELECT c FROM Configuration c WHERE c.name = :name and c.id_ente.id_ente=:idComune and c.id_comune.id_comune=:idComune");
        query.setParameter("name", name);
        query.setParameter("idEnte", idEnte);
        query.setParameter("idComune", idComune);
        List<Configuration> configurations = query.getResultList();
        return configurations;
    }
    
    public List<Configuration> getAllConfiguration() {
        Query query = em.createNamedQuery("Configuration.findAll");
        List<Configuration> configurations = query.getResultList();
        return configurations;

    }

    public List<Configuration> findAll() {
        Query query = em.createNamedQuery("Configuration.findAll");
        List<Configuration> configurationList = query.getResultList();
        return configurationList;
    }
    
       public String getConfigurationValue(String key, Enti ente, LkComuni comune) {
        String queryString = "SELECT c.value "
                + "FROM Configuration c "
                + "WHERE c.name = :name ";
        if (ente != null) {
            queryString = queryString + " AND c.idEnte = :idEnte";
        }
        if (comune != null) {
            queryString = queryString + " AND c.idComune = :idComune";
        }
        Query query = em.createQuery(queryString);
        query.setParameter("name", key);
        if (ente != null) {
            query.setParameter("idEnte", ente);
        }
        if (comune != null) {
            query.setParameter("idComune", comune);
        }
        List<String> values = query.getResultList();
        if (values != null && !values.isEmpty()) {
            return values.get(0);
        } else {
            return null;
        }
    }

    public Configuration getConfiguration(String key, Enti ente, LkComuni comune) {
        String queryString = "SELECT c "
                + "FROM Configuration c "
                + "WHERE c.name = :name ";
        if (ente != null) {
            queryString = queryString + " AND c.idEnte = :idEnte";
        }
        if (comune != null) {
            queryString = queryString + " AND c.idComune = :idComune";
        }
        Query query = em.createQuery(queryString);
        query.setParameter("name", key);
        if (ente != null) {
            query.setParameter("idEnte", ente);
        }
        if (comune != null) {
            query.setParameter("idComune", comune);
        }
        List<Configuration> values = query.getResultList();
        if (values != null && !values.isEmpty()) {
            return values.get(0);
        } else {
            return null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import it.wego.cross.entity.PluginConfiguration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gabriele
 */
@Repository
public class PluginConfigurationDao {

    @PersistenceContext
    private EntityManager em;

    public List<PluginConfiguration> getAllPluginConfigurations() {
        Query query = em.createNamedQuery("PluginConfiguration.findAll");
        List<PluginConfiguration> pluginConfigurations = query.getResultList();
        return pluginConfigurations;

    }
}

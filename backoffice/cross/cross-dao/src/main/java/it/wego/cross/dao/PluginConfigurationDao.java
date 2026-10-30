/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.wego.cross.entity.Enti;
import it.wego.cross.entity.PluginConfiguration;
import it.wego.cross.utils.Utils;

/**
 *
 * @author gabriele
 */
@Repository
public class PluginConfigurationDao {

	public static final String TRUE_VALUE = "true";
	public static final String FALSE_VALUE = "false";
	
    @PersistenceContext
    private EntityManager em;

    public PluginConfiguration findByName(String name) {
        Query query = em.createNamedQuery("PluginConfiguration.findByName");
        query.setParameter("name", name);
        PluginConfiguration configurations = (PluginConfiguration) Utils.getSingleResult(query);
        return configurations;
    }
    
    @Transactional
    public void changeByName(String name, String value) {
    	PluginConfiguration configuration = findByName(name);
    	configuration.setValue(value);
    	em.merge(configuration);
    	em.flush();

    }
    
    public List<PluginConfiguration> getAllPluginConfigurations() {
        Query query = em.createNamedQuery("PluginConfiguration.findAll");
        List<PluginConfiguration> pluginConfigurations = query.getResultList();
        return pluginConfigurations;

    }

    public List<PluginConfiguration> getPluginConfigurationsByInstitution(Enti institution) {
    	Query query = em.createNamedQuery("PluginConfiguration.findByInstitution");
    	query.setParameter("institution", institution);
    	List<PluginConfiguration> pluginConfigurations = query.getResultList();
    	return pluginConfigurations;
    	
    }
}

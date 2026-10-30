package it.wego.cross.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.wego.cross.entity.PluginConfiguration;


public class PluginConfigurationDao {

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public List<PluginConfiguration> getAllPluginConfigurations() {
        Query query = em.createNamedQuery("PluginConfiguration.findAll");
        List<PluginConfiguration> pluginConfigurations = query.getResultList();
        return pluginConfigurations;

    }
}
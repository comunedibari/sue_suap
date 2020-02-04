/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.dao.PluginConfigurationDao;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PluginConfiguration;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationDao configurationDao;
    @Autowired
    private PluginConfigurationDao pluginConfigurationDao;
    private Map<String, String> configurationProperties = null;
    private Map<String, String> pluginConfigurationProperties = null;

    @Override
    public List<Configuration> findByName(String name) {
        List<Configuration> configurations = configurationDao.findByName(name);
        return configurations;
    }

    @Override
    public List<Configuration> findByName(String name, Integer idEnte, Integer idComune) {
        List<Configuration> configurations = configurationDao.findByName(name, idEnte, idComune);
        return configurations;
    }

    @Override
    public void resetCachedConfiguration() {
        configurationProperties = null;
        pluginConfigurationProperties = null;
    }

    @Override
    public String getCachedPluginConfiguration(String key, Integer idEnte, Integer idComune) {
        String value = getPluginConf(costruisciKey(key, idEnte, idComune));
        if (Utils.e(value)) {
            value = getPluginConf(costruisciKey(key, idEnte, null));
            if (Utils.e(value)) {
                value = getPluginConf(costruisciKey(key, null, null));
            }
        }
        return value;
    }

    @Override
    public String getCachedConfiguration(String key, Integer idEnte, Integer idComune) {
        String value = getConf(costruisciKey(key, idEnte, idComune));
        if (Utils.e(value)) {
            value = getConf(costruisciKey(key, idEnte, null));
            if (Utils.e(value)) {
                value = getConf(costruisciKey(key, null, null));
            }
        }
        return value;
    }

//    @Override
//    public String getCachedConfiguration(String key, Enti ente, LkComuni comune) {
//        Integer idEnte = ente == null ? null : ente.getIdEnte();
//        Integer idComune = comune == null ? null : comune.getIdComune();
//        return getCachedConfiguration(key, idEnte, idComune);
//    }

    private String getConf(String key) {
        if (configurationProperties == null) {
            configurationProperties = new HashMap<String, String>();
            List<Configuration> configurations = configurationDao.getAllConfiguration();
            for (Configuration parametro : configurations) {
                String conf = parametro.getName().trim() + "|";
                if (parametro.getIdComune() != null) {
                    conf += parametro.getIdComune().getIdComune();
                }
                conf += "|";
                if (parametro.getIdEnte() != null) {
                    conf += parametro.getIdEnte().getIdEnte();
                }
                if (parametro.getValue() != null) {
                    configurationProperties.put(conf, parametro.getValue().trim());
                }
            }
        }
        return configurationProperties.get(key.trim());
    }

    private String getPluginConf(String key) {
        if (pluginConfigurationProperties == null) {
            pluginConfigurationProperties = new HashMap<String, String>();
            List<PluginConfiguration> pluginConfigurations = pluginConfigurationDao.getAllPluginConfigurations();
            for (PluginConfiguration parametro : pluginConfigurations) {
                String conf = parametro.getName().trim() + "|";
                if (parametro.getIdComune() != null) {
                    conf += parametro.getIdComune().getIdComune();
                }
                conf += "|";
                if (parametro.getIdEnte() != null) {
                    conf += parametro.getIdEnte().getIdEnte();
                }
                if (parametro.getValue() != null) {
                    pluginConfigurationProperties.put(conf, parametro.getValue().trim());
                }
            }
        }
        return pluginConfigurationProperties.get(key.trim());
    }

    @Override
    public List<Integer> findIdEntiFromConfiguration(String key) {
        List<Configuration> configurations = configurationDao.findByName(key);
        List<Integer> idEnti = null;
        if (configurations != null && !configurations.isEmpty()) {
            idEnti = new ArrayList<Integer>();
            for (Configuration conf : configurations) {
                if (conf.getIdEnte() != null) {
                    idEnti.add(conf.getIdEnte().getIdEnte());
                }
            }
        }
        return idEnti;
    }

    @Override
    public Boolean isClearEnabled(Integer idEnte) {
        String isClearEnabled = getCachedConfiguration(SessionConstants.CLEAR_CLIENT_ENABLED, idEnte, null);
        if (StringUtils.isEmpty(isClearEnabled) || isClearEnabled.equalsIgnoreCase("FALSE")) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private static String costruisciKey(String key, Integer idEnte, Integer idComune) {
        String conf = key;
        conf += "|";
        if (idComune != null) {
            conf += idComune;
        }
        conf += "|";
        if (idEnte != null) {
            conf += idEnte;
        }
        return conf;
    }

    @Override
    public String getGestionePraticheColumnModel(Integer idEnte, Integer idComune) throws Exception {
        String dbColumnModelB64 = getCachedConfiguration(SessionConstants.GESTIONE_PRATICHE_COLUMN_MODEL, idEnte, idComune);
        String dbColumnModel;
        if (StringUtils.isNotEmpty(dbColumnModelB64)) {
            dbColumnModel = Utils.decodeB64(dbColumnModelB64);
        } else {
            dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("gestione_pratiche_default_column_model.js"));
        }

        return dbColumnModel;
    }

	@Override
	public String getEstrazioniCilaColumnModel(Integer idEnte, Integer idComune) throws Exception {
        String dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("estrazioni_cila_column_model.js"));

        return dbColumnModel;
	}
	
	@Override
	public String getEstrazioniSciaColumnModel(Integer idEnte, Integer idComune) throws Exception {
        String dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("estrazioni_scia_column_model.js"));

        return dbColumnModel;
	}
	
	@Override
	public String getEstrazioniPdcColumnModel(Integer idEnte, Integer idComune) throws Exception {
        String dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("estrazioni_pdc_column_model.js"));

        return dbColumnModel;
	}
	
	@Override
	public String getEstrazioniAgibColumnModel(Integer idEnte, Integer idComune) throws Exception {
        String dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("estrazioni_agib_column_model.js"));

        return dbColumnModel;
	}
	
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface ConfigurationService {

    public List<Configuration> findByName(String name);
    
    public List<Configuration> findByName(String name, Integer idEnte, Integer idComune);

    public void resetCachedConfiguration();

    public String getCachedConfiguration(String key, Integer idEnte, Integer idComune);
    
//    public String getCachedConfiguration(String key, Enti entePratica, LkComuni comunePratica);
    
    public String getCachedPluginConfiguration(String key, Integer idEnte, Integer idComune);

    public List<Integer> findIdEntiFromConfiguration(String key);
    
    public Boolean isClearEnabled(Integer idEnte);
        
    public String getGestionePraticheColumnModel(Integer idEnte, Integer idComune) throws Exception;

	public String getEstrazioniCilaColumnModel(Integer idEnte, Integer idComune) throws Exception;
	
	public String getEstrazioniSciaColumnModel(Integer idEnte, Integer idComune) throws Exception;

	public String getEstrazioniPdcColumnModel(Integer enteAcasoPerColumnModel, Integer idComune) throws Exception;

	public Object getEstrazioniAgibColumnModel(Integer enteAcasoPerColumnModel, Integer idComune) throws Exception;
}

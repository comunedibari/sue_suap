/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.anagrafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Anagrafica;
import it.wego.cross.service.ConfigurationService;

/**
 *
 * @author giuseppe
 */
public class GestioneAnagrafeComunalePGDemo implements GestioneAnagrafePersonaGiuridica {

    @Autowired
    private ConfigurationService configurationService;
    private static final String ANAGRAFE_ENDPOINT = "anagrafe.endpoint.persona.giuridica";
    private static Logger log = LoggerFactory.getLogger("plugin");

    @Override
    public Anagrafica search(String codiceFiscale, String partitaIva) {
        return null;
    }

    @Override
    public Boolean existRicercaAnagraficaPersonaGiuridica( Pratica pratica) {
        String endpoint = configurationService.getCachedConfiguration(ANAGRAFE_ENDPOINT, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
        if (endpoint != null) {
            return true;
        }
        return false;
    }

}

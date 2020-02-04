/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaFisica;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaGiuridica;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.views.CustomViews;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface PluginService {

    public GestioneAllegati getGestioneAllegati(Integer idEnte, Integer idComune) throws Exception;

    public GestioneProtocollo getGestioneProtocollo(Integer idEnte, Integer idComune) throws Exception;

    public GestionePratica getGestionePratica(Integer idEnte) throws Exception;

    public GestioneAnagrafePersonaFisica getGestioneAnagrafePersonaFisica(Integer idEnte, Integer idComune) throws Exception;

    public GestioneAnagrafePersonaGiuridica getGestioneAnagrafePersonaGiuridica(Integer idEnte, Integer idComune) throws Exception;

    public CustomViews getCustomViews(Integer idEnte, Integer idComune) throws Exception;
}

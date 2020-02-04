/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaFisica;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaGiuridica;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.views.CustomViews;
import javax.annotation.Nullable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public class PluginServiceImpl implements PluginService {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private ConfigurationService configurationService;

    @Override
    public GestioneAllegati getGestioneAllegati(Integer idEnte, Integer idComune) throws Exception {
        String documentPluginBeanId = configurationService.getCachedConfiguration(SessionConstants.DOCUMENTI_PLUGIN_ID, idEnte, idComune);
        if (StringUtils.isEmpty(documentPluginBeanId)) {
            //throw new Exception("Parametro '" + Constants.DOCUMENTI_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
            return null;
        } else {
            GestioneAllegati gestioneAllegati = (GestioneAllegati) appContext.getBean(documentPluginBeanId);
            return gestioneAllegati;
        }
    }

    @Override
    public GestioneProtocollo getGestioneProtocollo(Integer idEnte, Integer idComune) throws Exception {
        String protocolloPluginBeanId = configurationService.getCachedConfiguration(SessionConstants.PROTOCOLLO_PLUGIN_ID, idEnte, idComune);
        if (StringUtils.isEmpty(protocolloPluginBeanId)) {
//            throw new Exception("Parametro '" + Constants.PROTOCOLLO_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
            return null;
        } else {
            GestioneProtocollo gestioneProtocollo = (GestioneProtocollo) appContext.getBean(protocolloPluginBeanId);
            return gestioneProtocollo;
        }
    }

    @Override
    public GestionePratica getGestionePratica(Integer idEnte) throws Exception {
        String praticaPluginId = configurationService.getCachedConfiguration(SessionConstants.PRATICA_PLUGIN_ID, idEnte, null);
        if (StringUtils.isEmpty(praticaPluginId)) {
            //throw new Exception("Parametro '" + Constants.PRATICA_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
            return null;
        } else {
            GestionePratica gestionePratica = (GestionePratica) appContext.getBean(praticaPluginId);
            return gestionePratica;
        }
    }

    @Override
    public GestioneAnagrafePersonaFisica getGestioneAnagrafePersonaFisica(Integer idEnte, Integer idComune) throws Exception {
        String anagrafePluginBeanId = configurationService.getCachedConfiguration(SessionConstants.ANAGRAFE_PERSONA_FISICA_PLUGIN_ID, idEnte, idComune);
        if (StringUtils.isEmpty(anagrafePluginBeanId)) {
            //throw new Exception("Parametro '" + Constants.ANAGRAFE_PERSONA_FISICA_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
            return null;
        } else {
            GestioneAnagrafePersonaFisica gestioneAnagrafe = (GestioneAnagrafePersonaFisica) appContext.getBean(anagrafePluginBeanId);
            return gestioneAnagrafe;
        }
    }

    @Override
    public GestioneAnagrafePersonaGiuridica getGestioneAnagrafePersonaGiuridica(Integer idEnte, Integer idComune) throws Exception {
        String anagrafePluginBeanId = configurationService.getCachedConfiguration(SessionConstants.ANAGRAFE_PERSONA_GIURIDICA_PLUGIN_ID, idEnte, idComune);
        if (StringUtils.isEmpty(anagrafePluginBeanId)) {
            //throw new Exception("Parametro '" + Constants.ANAGRAFE_PERSONA_GIURIDICA_PLUGIN_ID + "' non valorizzato nella tabella configuration.");
            return null;
        } else {
            GestioneAnagrafePersonaGiuridica gestioneAnagrafe = (GestioneAnagrafePersonaGiuridica) appContext.getBean(anagrafePluginBeanId);
            return gestioneAnagrafe;
        }
    }

    @Nullable
    @Override
    public CustomViews getCustomViews(Integer idEnte, Integer idComune) throws Exception {
        String customViewsPluginId = configurationService.getCachedConfiguration(SessionConstants.CUSTOM_VIEW_PLUGIN_ID, idEnte, idComune);
        if (customViewsPluginId != null) {
            CustomViews customViews = (CustomViews) appContext.getBean(customViewsPluginId);
            return customViews;
        } else {
            return null;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.wego.cross.plugins;

import it.asitech.webservice.protocollo.ProtocolloServicePortTypeServiceLocator;
import it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub;
import it.wego.cross.constants.Genuit;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author piergiorgio
 */
public class GestioneAbstractGenuit {
        protected String endPoint, user, password, defaultSoggettoMezzo, defaultSoggettoTipo, aoo;
        
    @Autowired
    private ConfigurationService configurationService;
        
            protected ProtocolloServiceSoapBindingStub getService(Enti ente, LkComuni comune) throws Exception {
        ProtocolloServiceSoapBindingStub service;
        Integer idComune = (comune == null) ? null : comune.getIdComune();
        Integer idEnte = (ente == null) ? null : ente.getIdEnte();
        endPoint = configurationService.getCachedPluginConfiguration(Genuit.ENDOPOINT, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(endPoint)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.ENDOPOINT + "'");
        }
        user = configurationService.getCachedPluginConfiguration(Genuit.USER, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(user)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.USER + "'");
        }
        password = configurationService.getCachedPluginConfiguration(Genuit.PASSWORD, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(password)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.PASSWORD + "'");
        }
        defaultSoggettoMezzo = configurationService.getCachedPluginConfiguration(Genuit.SOGGETTO_MEZZO, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(defaultSoggettoMezzo)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.SOGGETTO_MEZZO + "'");
        }
        defaultSoggettoTipo = configurationService.getCachedPluginConfiguration(Genuit.SOGGETTO_TIPO, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(defaultSoggettoTipo)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.SOGGETTO_TIPO + "'");
        }
        aoo = configurationService.getCachedPluginConfiguration(Genuit.AOO, idEnte, idComune);
        if (org.apache.axis.utils.StringUtils.isEmpty(aoo)) {
            throw new Exception("Sulla tabella di configurazione non è stata valorizzata la chiave '" + Genuit.AOO + "'");
        }
        ProtocolloServicePortTypeServiceLocator proto = new ProtocolloServicePortTypeServiceLocator();
        proto.setProtocolloServiceEndpointAddress(endPoint);
        service = (ProtocolloServiceSoapBindingStub) proto.getProtocolloService();
        service.setTimeout(60000);
        return service;
    }
}

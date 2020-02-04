/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.serializer.ClearSerializer;
import it.wego.cross.webservices.client.clear.stubs.Application;
import it.wego.cross.webservices.client.clear.stubs.Application_Service;
import it.wego.cross.webservices.client.clear.stubs.Log;
import it.wego.cross.webservices.client.clear.stubs.PraticaSimoExtended;
import it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoExtended;
import it.wego.cross.webservices.client.clear.stubs.SimoCreaProcedimento;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public class ClearServiceImpl implements ClearService {

    @Autowired
    ConfigurationService configurationService;
    @Autowired
    ClearSerializer clearSerializer;

    @Override
    public Log creaProcedimentoRaw(SimoCreaProcedimento procedimento, Integer idEnte) throws Exception {
        Application simoService = getSimoService(idEnte);
        return simoService.simoCreaProcedimento(procedimento.getCodice(), procedimento.getDescrizione(), procedimento.getEventi().getValue());
    }

    @Override
    public Log creaPraticaRaw(PraticaSimoExtended pratica, Integer idEnte) throws Exception {
        Application simoService = getSimoService(idEnte);
        return simoService.praticaSimoExtended(pratica.getCodiceProtocollo(), 
                pratica.getCodiceProcedimentoRur(), 
                pratica.getIdentificazionePratica().getValue(), 
                pratica.getResponsabileProcedimento().getValue(), 
                pratica.getResponsabileIstruttoria().getValue(), 
                pratica.getImportoRichiesto().getValue(), 
                pratica.getOggetto(), 
                pratica.getRichiedenti().getValue(), 
                pratica.getLocalizzazioni().getValue(), 
                pratica.getDataOraRicevimento().getValue(), 
                pratica.getDataOraProtocollazione().getValue(),
                pratica.getDataAvvioMonitoraggio().getValue(), 
                pratica.getVerso(), 
                pratica.getCodiceUfficio().getValue(), 
                pratica.getCodiceProtocolloPrecedente().getValue());
    }

    @Override
    public Log creaEventoRaw(SimoAssociaEventoExtended evento, Integer idEnte) throws Exception {
        Application simoService = getSimoService(idEnte);
        return simoService.simoAssociaEventoExtended(evento.getCodiceProtocollo(),
                evento.getCodicePraticaSimo(),
                evento.getCodiceEvento(),
                evento.getAnnotazioniEvento(),
                evento.getEsito(),
                evento.getAnnotazioniEsito().getValue(),
                evento.getImportoFinanziato().getValue(),
                evento.getDataOraRicevimento(),
                evento.getDataOraProtocollazione(),
                evento.getVerso());
    }

    private Application getSimoService(Integer idEnte) throws Exception {
        String creaProcedimentoClientUrl = configurationService.getCachedConfiguration(SessionConstants.CLEAR_CLIENT_URL, idEnte, null);
        if (StringUtils.isEmpty(creaProcedimentoClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.CLEAR_CLIENT_URL + "' non trovata nella tabella Configurations.");
        }
        
        Application_Service clearService = new Application_Service(new URL(creaProcedimentoClientUrl+"?wsdl"));
        
        Application simoClient = clearService.getApplication();
        Client client = ClientProxy.getClient(simoClient);
//        client.getInInterceptors().add(new LoggingInInterceptor());
//        client.getOutInterceptors().add(new LoggingOutInterceptor());
        ((BindingProvider) simoClient).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, creaProcedimentoClientUrl);
        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(10000);
            policy.setReceiveTimeout(5000);
            policy.setAllowChunking(false);
            conduit.setClient(policy);
        }
        
        return simoClient;
    }

    @Override
    public Log creaProcedimento(ProcedimentiEnti procedimentoEnteCross) throws Exception {
        SimoCreaProcedimento procedimentoClear = clearSerializer.getProcedimentoClear(procedimentoEnteCross);
        Log creaProcedimentoResponse = creaProcedimentoRaw(procedimentoClear, procedimentoEnteCross.getIdEnte().getIdEnte());
        return creaProcedimentoResponse;
    }

    @Override
    public Log creaPratica(Pratica praticaCross) throws Exception {
        PraticaSimoExtended praticaClear = clearSerializer.getPraticaClear(praticaCross);
        Log creaPraticaResponse = creaPraticaRaw(praticaClear, praticaCross.getIdProcEnte().getIdEnte().getIdEnte());
        return creaPraticaResponse;
    }

    @Override
    public Log creaEvento(PraticheEventi eventoCross) throws Exception {
        SimoAssociaEventoExtended eventoClear = clearSerializer.getEventoClear(eventoCross);
        Log creaPraticaResponse = creaEventoRaw(eventoClear, eventoCross.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte());
        return creaPraticaResponse;
    }

}

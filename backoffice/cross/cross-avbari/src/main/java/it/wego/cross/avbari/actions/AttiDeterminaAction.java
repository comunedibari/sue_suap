/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.actions;

import it.wego.cross.avbari.atti.client.DeterminaServer;
import it.wego.cross.avbari.atti.client.DeterminaServerImplService;
import it.wego.cross.avbari.atti.client.GetAllegatiDetermina.AllegatiDeterminaRequest;
import it.wego.cross.avbari.atti.client.GetAllegatiDeterminaResponse;
import it.wego.cross.avbari.atti.client.GetAllegatiDeterminaResponse.Return.Allegati;
import it.wego.cross.avbari.atti.client.GetDocumentoDetermina.DocumentoDeterminaRequest;
import it.wego.cross.avbari.atti.client.GetDocumentoDeterminaResponse.Return;
import it.wego.cross.avbari.beans.ResponseGetAttiDeterminaBean;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Pratica;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author piergiorgio
 */
@Component
public class AttiDeterminaAction {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private PraticheService praticheService;

    private static final QName SERVICE_NAME = new QName("http://impl.server.ws.amministrazioneatti.avt.linksmt.it/", "DeterminaServerImplService");

    @Transactional(rollbackFor = Exception.class)
    public void execute(EventoBean eventoBean) throws Exception {
        Log.APP.info("Inizio metodo richiesta atti AV BARI");
        ResponseGetAttiDeterminaBean res = getAtti(eventoBean);
        if (!res.isOperazioneOK()) {
            throw new CrossException(res.getMessaggio());
        } else {
            workFlowService.gestisciProcessoEvento(eventoBean);
        }
        Log.APP.info("Fine metodo richiesta atti AV BARI");
    }

    private ResponseGetAttiDeterminaBean getAtti(EventoBean eventoBean) throws Exception {
        ResponseGetAttiDeterminaBean ret = new ResponseGetAttiDeterminaBean();

        ret = ricercaAtti(eventoBean);

        return ret;
    }

    private DocumentoDeterminaRequest creaRichiesta(EventoBean eventoBean, Integer idEnte, Integer idComune) throws Exception {
        DocumentoDeterminaRequest ret = new DocumentoDeterminaRequest();
        String s = (String) eventoBean.getMessages().get("annoRiferimento");
        ret.setAnnoDetermina(Integer.valueOf(s));
        s = (String) eventoBean.getMessages().get("numeroRiferimento");
        ret.setNumeroRegistro(Integer.valueOf(s));
        return ret;
    }

    private AllegatiDeterminaRequest creaRichiestaAllegati(EventoBean eventoBean, Integer idEnte, Integer idComune) throws Exception {
        AllegatiDeterminaRequest ret = new AllegatiDeterminaRequest();
        String s = (String) eventoBean.getMessages().get("annoRiferimento");
        ret.setAnnoDetermina(Integer.valueOf(s));
        s = (String) eventoBean.getMessages().get("numeroRiferimento");
        ret.setNumeroRegistro(Integer.valueOf(s));
        return ret;
    }

    private ResponseGetAttiDeterminaBean ricercaAtti(EventoBean eventoBean) throws Exception {
        Pratica pratica = praticheService.getPratica(eventoBean.getIdPratica());
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        Integer idComune = pratica.getIdComune().getIdComune();
        ResponseGetAttiDeterminaBean ret = cercaDocumentoBase(eventoBean, idEnte, idComune);
        if (ret.isOperazioneOK()) {
            ret = cercaAllegatiAtto(eventoBean, idEnte, idComune);
        }
        return ret;
    }

    private ResponseGetAttiDeterminaBean cercaDocumentoBase(EventoBean eventoBean, Integer idEnte, Integer idComune) throws Exception {
        ResponseGetAttiDeterminaBean ret = new ResponseGetAttiDeterminaBean();
        ret.setOperazioneOK(Boolean.FALSE);
        String address = configurationService.getCachedPluginConfiguration(AvBariConstants.ATTI_GET_DOCUMENTO_DETERMINA_ENDPOINT, idEnte, idComune);
        URL sitWsdlUrl = DeterminaServer.class.getResource("/it/wego/cross/avbari/atti/wsdl/determina.wsdl");

        DeterminaServerImplService ss = new DeterminaServerImplService(sitWsdlUrl);
        DeterminaServer port = ss.getDeterminaServerImplPort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        setConfClient(port);

        DocumentoDeterminaRequest request = creaRichiesta(eventoBean, idEnte, idComune);

        Return response = port.getDocumentoDetermina(request);

        if (response.getEsito() != null && response.getEsito().equalsIgnoreCase(AvBariConstants.ATTI_GET_DOCUMENTO_DETERMINA_ESITO_OK)) {
            ret.setOperazioneOK(Boolean.TRUE);
            getDocumentoBase(eventoBean, response);
        } else {
            ret.setOperazioneOK(Boolean.FALSE);
            ret.setMessaggio(response.getEsito());
        }

        return ret;
    }

    private void getDocumentoBase(EventoBean eventoBean, Return response) {
        Allegato allegato = new Allegato();
        List<Allegato> lista = new ArrayList<Allegato>();
        allegato.setNomeFile(response.getAllegato().getNomeFile());
        allegato.setTipoFile(response.getAllegato().getMimeType());
        allegato.setFile(response.getAllegato().getContenuto());
        allegato.setDescrizione("Determina " + eventoBean.getMessages().get("numeroRiferimento") + "/" + eventoBean.getMessages().get("annoRiferimento"));
        lista.add(allegato);
        eventoBean.setAllegati(lista);
    }

    private ResponseGetAttiDeterminaBean cercaAllegatiAtto(EventoBean eventoBean, Integer idEnte, Integer idComune) throws Exception {
        ResponseGetAttiDeterminaBean ret = new ResponseGetAttiDeterminaBean();
        ret.setOperazioneOK(Boolean.FALSE);
        String address = configurationService.getCachedPluginConfiguration(AvBariConstants.ATTI_GET_DOCUMENTO_DETERMINA_ENDPOINT, idEnte, idComune);
        URL sitWsdlUrl = DeterminaServer.class.getResource("/it/wego/cross/avbari/atti/wsdl/determina.wsdl");

        DeterminaServerImplService ss = new DeterminaServerImplService(sitWsdlUrl);
        DeterminaServer port = ss.getDeterminaServerImplPort();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        setConfClient(port);

        AllegatiDeterminaRequest request = creaRichiestaAllegati(eventoBean, idEnte, idComune);

        GetAllegatiDeterminaResponse.Return response = port.getAllegatiDetermina(request);

        if (response.getEsito() != null && response.getEsito().equalsIgnoreCase(AvBariConstants.ATTI_GET_DOCUMENTO_DETERMINA_ESITO_OK)) {
            ret.setOperazioneOK(Boolean.TRUE);
            getAllegati(eventoBean, response);
        } else {
            ret.setOperazioneOK(Boolean.TRUE);
        }
        return ret;

    }

    private void setConfClient(DeterminaServer port) {
        Client client = ClientProxy.getClient(port);
        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(10000);
            policy.setReceiveTimeout(5000);
            policy.setAllowChunking(false);
            conduit.setClient(policy);
        }
    }

    private void getAllegati(EventoBean eventoBean, GetAllegatiDeterminaResponse.Return response) {
        if (response.getAllegati() != null && !response.getAllegati().isEmpty()) {
            List<Allegato> lista = eventoBean.getAllegati();
            for (Allegati a : response.getAllegati()) {
                Allegato allegato = new Allegato();
                allegato.setNomeFile(a.getNomeFile());
                allegato.setTipoFile(a.getMimeType());
                allegato.setFile(a.getContenuto());
                allegato.setDescrizione("Allegato determina " + eventoBean.getMessages().get("numeroRiferimento") + "/" + eventoBean.getMessages().get("annoRiferimento") + "(" + a.getNomeFile() + ")");
                lista.add(allegato);
            }
        }
    }

}

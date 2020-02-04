/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.gov.impresainungiorno.schema.suap.ri.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.ri.RichiestaIscrizioneImpresaRiSPC;
import it.gov.impresainungiorno.schema.suap.ri.ServizioIntegrazioneSuapRi;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcRequest;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.*;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabri
 */
@Service
public class RegistroImpreseServiceImpl implements RegistroImpreseService {

    @Autowired
//    private ConfigurationDao configurationDao;
    private ConfigurationService configurationService;

    @Override
    public IscrizioneImpresaRiSpcResponse richiestaIscrizioneImpresa(String codiceFiscale) throws Exception {
        RichiestaIscrizioneImpresaRiSPC richiestaIscrizioneImpresaPortType = getRichiestaIscrizioneImpresaPortType();

        IscrizioneImpresaRiSpcRequest richiestaIntegrazioneRequest = new IscrizioneImpresaRiSpcRequest();
        richiestaIntegrazioneRequest.setCodiceFiscale(codiceFiscale);

        IscrizioneImpresaRiSpcResponse richiestaIntegrazioneResponse = richiestaIscrizioneImpresaPortType.iscrizioneImpresaRiSpc(richiestaIntegrazioneRequest);

        return richiestaIntegrazioneResponse;

    }

    private ServizioIntegrazioneSuapRi getServizioRegistroImprese() throws Exception {
        URL wsdlUrl = RegistroImpreseServiceImpl.class.getResource("/wsdl/registroimprese/ws/comunicazioniRea_implementazione_erogatore.wsdl");
        if (wsdlUrl == null) {
            throw new Exception("Impossibile individualre il WSDL del servizio di integrazione di Registro Imprese.");
        }

        ServizioIntegrazioneSuapRi registroImprese = new ServizioIntegrazioneSuapRi(wsdlUrl);
        return registroImprese;
    }

    @Override
    public RichiestaIscrizioneImpresaRiSPC getRichiestaIscrizioneImpresaPortType() throws Exception {
        RichiestaIscrizioneImpresaRiSPC richiestaIscrizioneImpresaPortType = getServizioRegistroImprese().getRichiestaIscrizioneImpresaRiSPC();
        String registroImpreseRichiestaIscrizioneImpresaClientUrl = configurationService.getCachedConfiguration(SessionConstants.REGISTRO_IMPRESE_RICHIESTA_ISCRIZIONE_URL,null,null);
        if (StringUtils.isEmpty(registroImpreseRichiestaIscrizioneImpresaClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.REGISTRO_IMPRESE_RICHIESTA_ISCRIZIONE_URL + "' non trovata nella tabella Configurations.");
        }
        ((BindingProvider) richiestaIscrizioneImpresaPortType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, registroImpreseRichiestaIscrizioneImpresaClientUrl);
        return richiestaIscrizioneImpresaPortType;
    }

    @Override
    public ProtocolloSUAP getComunicazioneSuapPortType() throws Exception {
        ProtocolloSUAP comunicazioneSuapPortType = getServizioRegistroImprese().getProtocolloSUAP();
        String registroImpreseComunicazioneSuapImpresaClientUrl = configurationService.getCachedConfiguration(SessionConstants.REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL,null,null);
        if (StringUtils.isEmpty(registroImpreseComunicazioneSuapImpresaClientUrl)) {
            throw new Exception("Chiave '" + SessionConstants.REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL + "' non trovata nella tabella Configurations.");
        }
        ((BindingProvider) comunicazioneSuapPortType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, registroImpreseComunicazioneSuapImpresaClientUrl);
        return comunicazioneSuapPortType;
    }
}

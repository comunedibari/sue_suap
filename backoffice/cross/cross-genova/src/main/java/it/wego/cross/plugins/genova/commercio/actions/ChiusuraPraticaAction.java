/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.genova.commercio.actions;

import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.genova.commercio.bean.ResponseWsBean;
import it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaServiceLocator;
import it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType;
import it.wego.cross.plugins.genova.commercio.utils.Utility;
import it.wego.cross.plugins.genova.commercio.xml.chiusura.richiesta.Chiusura;
import it.wego.cross.plugins.genova.commercio.xml.chiusura.risposta.RispostaChiusura;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author piergiorgio
 */
@Component
public class ChiusuraPraticaAction {

    @Autowired
    private ConfigurationService configurationService;

    public ResponseWsBean execute(Pratica pratica) throws Exception {
        ResponseWsBean ret = new ResponseWsBean();
        if (!Utils.e(pratica.getIdentificativoEsterno())) {
            ret = inviaCommercio(pratica);

        } else {
            ret.setOperazioneOK(Boolean.TRUE);
        }
        return ret;
    }

    private String creaRichiesta(Pratica pratica, Integer idEnte) throws Exception {
        Chiusura chiusura = new Chiusura();
        Chiusura.Sicurezza sicurezza = new Chiusura.Sicurezza();
        sicurezza.setPwd(configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_PASSWORD, idEnte, null));
        sicurezza.setUser(configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_USER, idEnte, null));
        chiusura.setSicurezza(sicurezza);
        Chiusura.Pratica prat = new Chiusura.Pratica();
        prat.setNumprat(pratica.getIdentificativoEsterno());
        chiusura.setPratica(prat);
        return Utils.marshall(chiusura);
    }

    private ResponseWsBean inviaCommercio(Pratica pratica) throws Exception {
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        Utility utilityService = new Utility();
        ResponseWsBean ret = new ResponseWsBean();
        ret.setOperazioneOK(Boolean.FALSE);
        ChiusuraPraticaServiceLocator locator = new ChiusuraPraticaServiceLocator();
        String address = configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_ENDPOINT_CHIUSURA, idEnte, null);
        // String address = "http://vm-tomcat.comune.genova.it:8080/Commercio/services/ChiusuraPratica";
        String servizio = "ChiusuraPratica";
        locator.setEndpointAddress(servizio, address);
        ChiusuraPratica_PortType portType = locator.getChiusuraPratica();
        String richiesta = creaRichiesta(pratica, idEnte);
        String result = portType.esegui(richiesta);
        ret = utilityService.checkRisposta(result);
        RispostaChiusura risposta = null;
        InputStream is = null;
        JAXBContext jc = null;
        Unmarshaller um = null;
        if (ret.isOperazioneOK()) {
            jc = JAXBContext.newInstance(RispostaChiusura.class);
            um = jc.createUnmarshaller();
            is = new ByteArrayInputStream(result.getBytes());
            risposta = (RispostaChiusura) um.unmarshal(is);
            if ("1111".equals(risposta.getEsito())) {
                ret.setOperazioneOK(Boolean.TRUE);
                ret.setMessaggio(risposta.getDescrizione());
            } else {
                ret.setOperazioneOK(Boolean.FALSE);
                ret.setMessaggio(risposta.getDescrizione());
            }
        }
        return ret;
    }

}

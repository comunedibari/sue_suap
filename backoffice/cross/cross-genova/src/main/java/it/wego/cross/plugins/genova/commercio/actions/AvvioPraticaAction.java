/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.plugins.genova.commercio.actions;

import com.google.common.base.Strings;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.genova.commercio.avviopratica.AvvioPraticaServiceLocator;
import it.wego.cross.plugins.genova.commercio.avviopratica.AvvioPratica_PortType;
import it.wego.cross.plugins.genova.commercio.bean.ResponseWsBean;
import it.wego.cross.plugins.genova.commercio.utils.Utility;
import it.wego.cross.plugins.genova.commercio.xml.RispostaErmes;
import it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta.Avvio;
import it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta.Avvio.Protocollo;
import it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta.Avvio.Sicurezza;
import it.wego.cross.plugins.genova.commercio.xml.avvio.risposta.RispostaAvvio;
import it.wego.cross.plugins.genova.commercio.xml.errore.connessione.RispostaConnessione;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author piergiorgio
 */
@Component
public class AvvioPraticaAction {

    @Autowired
    private ConfigurationService configurationService;

    public ResponseWsBean execute(Pratica pratica, String tipologiaProcedimento, String tipoProcedimento, PraticheEventi eventoProtocollo) throws Exception {
        Integer idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
        Utility utilityService = new Utility();
        ResponseWsBean ret = new ResponseWsBean();
        ret.setOperazioneOK(Boolean.FALSE);
        AvvioPraticaServiceLocator locator = new AvvioPraticaServiceLocator();
        String avvioPratica_address = configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_ENDPOINT_AVVIO, idEnte, null);
        String servizio = "AvvioPratica";
        locator.setEndpointAddress(servizio, avvioPratica_address);
        AvvioPratica_PortType portType = locator.getAvvioPratica();
        String richiesta = creaRichiesta(idEnte, pratica, tipologiaProcedimento, tipoProcedimento, eventoProtocollo);

        String result = portType.esegui(richiesta);
        ret = utilityService.checkRisposta(result);
        if (ret.isOperazioneOK()) {
            JAXBContext jc = JAXBContext.newInstance(RispostaAvvio.class, RispostaConnessione.class);
            Unmarshaller um = jc.createUnmarshaller();
            InputStream is = new ByteArrayInputStream(result.getBytes());
            
            RispostaErmes rispostaAvvio = (RispostaErmes) um.unmarshal(is);
            if ("1111".equals(rispostaAvvio.getEsito())) {
                ret.setOperazioneOK(Boolean.TRUE);
                ret.setMessaggio("Inserimento effettuato con successo");
                ret.setIdPraticaBO(rispostaAvvio.getDescrizione());
            } else {
                ret.setOperazioneOK(Boolean.FALSE);
                ret.setMessaggio(rispostaAvvio.getDescrizione());
            }
        }
        return ret;
    }

    private String creaRichiesta(Integer idEnte, Pratica pratica, String tipologiaProcedimento, String tipoProcedimento, PraticheEventi eventoProtocollo) throws Exception {
        String data = new SimpleDateFormat("yyyy/MM/dd").format(eventoProtocollo.getDataProtocollo());
        Avvio avvio = new Avvio();
        PraticaAnagrafica anag = trovaAnagrafica(pratica);
        if (anag != null) {
            if (pratica.getIndirizziInterventoList() != null && !pratica.getIndirizziInterventoList().isEmpty()) {
                Sicurezza sicurezza = new Avvio.Sicurezza();
                sicurezza.setPwd(configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_PASSWORD, idEnte, null));
                sicurezza.setUser(configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_USER, idEnte, null));
                avvio.setSicurezza(sicurezza);
                avvio.setDtpres(data);
                avvio.setIdsuap(String.valueOf(pratica.getIdPratica()));
                avvio.setTipocomm(tipologiaProcedimento);
                avvio.setTipoproc(tipoProcedimento);
                String tipoCommercioTest = configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_TIPOCOMMERCIO_TEST, idEnte, null);
                String tipoProcedimentoTest = configurationService.getCachedPluginConfiguration(Constants.COMMERCIO_TIPOPROCEDIMENTO_TEST, idEnte, null);
                if (tipoCommercioTest != null) {
                    avvio.setTipocomm(tipoCommercioTest);
                }
                if (tipoProcedimentoTest != null) {
                    avvio.setTipoproc(tipoProcedimentoTest);
                }
                it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta.Avvio.Anagrafica anagrafica = new it.wego.cross.plugins.genova.commercio.xml.avvio.richiesta.Avvio.Anagrafica();
                anagrafica.setCap(pratica.getIndirizziInterventoList().get(0).getCap());
                anagrafica.setCodcomres(Constants.COMMERCIO_CODICE_COMUNE);
                anagrafica.setCodfisc(anag.getAnagrafica().getCodiceFiscale().toUpperCase());
                anagrafica.setCodvia(pratica.getIndirizziInterventoList().get(0).getCodVia());
                anagrafica.setColciv(pratica.getIndirizziInterventoList().get(0).getColore());
                anagrafica.setDescomres(Constants.COMMERCIO_DESCRIZIONE_COMUNE);
                anagrafica.setNumciv(pratica.getIndirizziInterventoList().get(0).getCodCivico());
                anagrafica.setProv(Constants.COMMERCIO_PROVINCIA);
                if (anag.getAnagrafica().getDenominazione() != null && !Strings.isNullOrEmpty(anag.getAnagrafica().getDenominazione().trim())) {
                    anagrafica.setRagsoc(anag.getAnagrafica().getDenominazione());
                } else {
                    anagrafica.setRagsoc(anag.getAnagrafica().getCognome() + " " + anag.getAnagrafica().getNome());
                }
                if (String.valueOf(anag.getAnagrafica().getTipoAnagrafica()).equals(it.wego.cross.constants.Constants.PERSONA_GIURIDICA)) {
                    anagrafica.setTpAnag(Constants.COMMERCIO_DITTA);
                } else {
                    //anagrafica.setTpAnag(Constants.COMMERCIO_PF);
                    anagrafica.setTpAnag(Constants.COMMERCIO_DITTA);
                }
                avvio.setAnagrafica(anagrafica);
                Protocollo protocollo = new Protocollo();
                protocollo.setDtprot(data);
                String numero = null;
                String[] numProt = eventoProtocollo.getProtocollo().split("/");
                if (numProt.length == 3) {
                    numero = numProt[2];
                }
                if (numProt.length == 2) {
                    numero = numProt[1];
                }
                if (numProt.length == 1) {
                    numero = numProt[0];
                }
                if (numero != null) {
                    protocollo.setNumprot(numero);
                } else {
                    throw new CrossException("InvioBoCommercio - Manca il numero protocollo");
                }

                avvio.setProtocollo(protocollo);
            } else {
                throw new CrossException("InvioBoCommercio - Manca Indirizzo intervento");
            }
        } else {
            throw new CrossException("InvioBoCommercio - Manca anagrafica beneficiario");
        }
        String xml = Utils.marshall(avvio);
        Log.APP.info("Xml inviato al bo Commercio di Genova: " + xml);
        return xml;
    }

    private PraticaAnagrafica trovaAnagrafica(Pratica pratica) {
        PraticaAnagrafica anagrafica = null;
        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            if (praticaAnagrafica.getLkTipoRuolo().getCodRuolo().equalsIgnoreCase(TipoRuolo.BENEFICIARIO)) {
                anagrafica = praticaAnagrafica;
                break;
            }
        }
        return anagrafica;
    }
}

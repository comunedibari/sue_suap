/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.actions;

import com.google.common.base.Strings;
import it.wego.cross.avbari.beans.ResponseSitBean;
import it.wego.cross.avbari.constants.AvBariConstants;
import it.wego.cross.avbari.sit.client.IntegrazioneSueSuap;
import it.wego.cross.avbari.sit.client.IIntegrazioneSueSuap;
import it.wego.cross.avbari.sit.client.request.xsd.AnagraficaSIT;
import it.wego.cross.avbari.sit.client.request.xsd.AnagraficheSIT;
import it.wego.cross.avbari.sit.client.request.xsd.DatiCatastaliSIT;
import it.wego.cross.avbari.sit.client.request.xsd.DatoCatastaleSIT;
import it.wego.cross.avbari.sit.client.request.xsd.IndirizziInterventoSIT;
import it.wego.cross.avbari.sit.client.request.xsd.IndirizzoInterventoSIT;
import it.wego.cross.avbari.sit.client.request.xsd.PraticaSIT;
import it.wego.cross.avbari.sit.client.request.xsd.PraticaSIT.SegnaturaProtocollo;
import it.wego.cross.avbari.sit.client.request.xsd.ProcedimentiSIT;
import it.wego.cross.avbari.sit.client.request.xsd.ProcedimentoSIT;
import it.wego.cross.avbari.sit.client.response.xsd.RisultatoOperazione;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.dao.DefDatiEstesiDao;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PluginService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.wego.cross.dao.LookupDao;
import java.text.SimpleDateFormat;
import org.apache.xerces.dom.ElementNSImpl;

/**
 *
 * @author piergiorgio
 */
@Component
public class InvioPraticaSitAction {

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private DefDatiEstesiDao defDatiEstesiDao;
    @Autowired
    private LookupDao lookupDao;

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "IntegrazioneSueSuap");

    public ResponseSitBean execute(PraticheEventi praticaEvento) throws Exception {
        ResponseSitBean ret = new ResponseSitBean();

        ret = inviaPratica(praticaEvento);

        return ret;
    }

    public String creaRichiesta(PraticheEventi praticaEvento, Integer idEnte) throws Exception {
        return Utils.marshall(creaRichiesta(praticaEvento.getIdPratica(), praticaEvento, idEnte));
    }

    public PraticaSIT creaRichiesta(Pratica pratica, PraticheEventi praticaEvento, Integer idEnte) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DatiCatastali> listaCatasto = pratica.getDatiCatastaliList();
        List<IndirizziIntervento> listaIndirizzi = pratica.getIndirizziInterventoList();
        List<PraticaAnagrafica> listaAnagrafiche = pratica.getPraticaAnagraficaList();
        Enti sportello = pratica.getIdProcEnte().getIdEnte();
        Procedimenti procedimento = pratica.getIdProcEnte().getIdProc();
        LkComuni comune = pratica.getIdComune();
        List<PraticaProcedimenti> listaProcedimenti = pratica.getPraticaProcedimentiList();
        LkStatoPratica statoPratica = pratica.getIdStatoPratica();
        Utente utente = pratica.getIdUtente();
        PraticheEventi ricezione = pratica.getEventoRicezione();
        Protocollo protocollo;
        if (!Strings.isNullOrEmpty(ricezione.getProtocollo())) {
            GestioneProtocollo gp = pluginService.getGestioneProtocollo(idEnte, null);
            protocollo = gp.getProtocolloBean(ricezione);
        } else {
            Log.APP.info("InvioPraticaSit - non trovato protocollo");
            throw new CrossException("InvioPraticaSit - non trovato protocollo");
        }

        PraticaSIT praticaSit = new PraticaSIT();
        if (praticaEvento != null) {
            if (AvBariConstants.CODICE_EVENTO_APERTURA.equals(praticaEvento.getIdEvento().getCodEvento())) {
                praticaSit.setOperazione("I");
            }
            if (AvBariConstants.CODICE_EVENTO_CHIUSURA.equals(praticaEvento.getIdEvento().getCodEvento())) {
                praticaSit.setOperazione("U");
            }
        }
        praticaSit.setIdPratica(BigInteger.valueOf(pratica.getIdPratica().intValue()));
        praticaSit.setIdentificativoPratica(pratica.getIdentificativoPratica());
        praticaSit.setIdSportello(String.valueOf(sportello.getIdEnte()));
        praticaSit.setDesSportello(sportello.getDescrizione());
        praticaSit.setIdProcedimentoSuap(String.valueOf(procedimento.getIdProc()));
        praticaSit.setDesProcedimentoSuap(procedimento.getProcedimentiTestiByLang("it"));
        praticaSit.setOggetto(pratica.getOggettoPratica());
        praticaSit.setResponsabileProcedimento(pratica.getResponsabileProcedimento());
        if (utente != null) {
            praticaSit.setIstruttore(utente.getCognome() + " " + utente.getNome());
        } else {
            praticaSit.setIstruttore("");
        }
        praticaSit.setCodCatastaleComune(comune.getCodCatastale());
        praticaSit.setDesComune(comune.getDescrizione());
        SegnaturaProtocollo sp = new SegnaturaProtocollo();
        sp.setAnno(protocollo.getAnno());
        sp.setProtocollo(protocollo.getNumeroRegistrazione());
        sp.setRegistro(protocollo.getRegistro());
        praticaSit.setSegnaturaProtocollo(sp);
        if (protocollo.getDataRegistrazione() != null) {
            praticaSit.setDataProtocollo(sdf.format(protocollo.getDataRegistrazione()));
        } else {
            praticaSit.setDataProtocollo("");
        }
        if (pratica.getDataRicezione() != null) {
            praticaSit.setDataRicezione(sdf.format(pratica.getDataRicezione()));
        } else {
            praticaSit.setDataRicezione("");
        }
        praticaSit.setDatiCatastali(new DatiCatastaliSIT());
        for (DatiCatastali datoCatastale : listaCatasto) {
            DatoCatastaleSIT dc = new DatoCatastaleSIT();
            dc.setCodTipoUnita(datoCatastale.getIdTipoUnita().getCodTipoUnita());
            dc.setDesTipoUnita(datoCatastale.getIdTipoUnita().getDescrizione());
            dc.setCodiceSit(datoCatastale.getCodImmobile() != null ? datoCatastale.getCodImmobile() : "");
            dc.setFoglio(datoCatastale.getFoglio() != null ? datoCatastale.getFoglio() : "");
            if (datoCatastale.getIdImmobile() != null) {
                dc.setIdImmobile(String.valueOf(datoCatastale.getIdImmobile()));
            } else {
                dc.setIdImmobile("");
            }
            dc.setMappale(datoCatastale.getMappale() != null ? datoCatastale.getMappale() : "");
            dc.setSezione(datoCatastale.getSezione() != null ? datoCatastale.getSezione() : "");
            dc.setSubalterno(datoCatastale.getSubalterno() != null ? datoCatastale.getSubalterno() : "");
            praticaSit.getDatiCatastali().getDatoCatastaleSIT().add(dc);
        }

        praticaSit.setIndirizziInterventoSIT(new IndirizziInterventoSIT());
        for (IndirizziIntervento ind : listaIndirizzi) {
            IndirizzoInterventoSIT i = new IndirizzoInterventoSIT();
            i.setCap(ind.getCap() != null ? ind.getCap() : "");
            i.setCivico(ind.getCivico() != null ? ind.getCivico() : "");
            i.setCodiceSit(ind.getCodVia() != null ? ind.getCodVia() : "");
            if (ind.getIdIndirizzoIntervento() != null) {
                i.setIdIndirizzoIntervento(String.valueOf(ind.getIdIndirizzoIntervento()));
            } else {
                i.setIdIndirizzoIntervento("");
            }
            i.setIndirizzo(ind.getIndirizzo() != null ? ind.getIndirizzo() : "");
            i.setInternoLettera(ind.getInternoLettera() != null ? ind.getInternoLettera() : "");
            i.setInternoNumero(ind.getInternoNumero() != null ? ind.getInternoNumero() : "");
            i.setInternoScala(ind.getInternoScala() != null ? ind.getInternoScala() : "");
            i.setLocalita(ind.getLocalita() != null ? ind.getLocalita() : "");
            i.setPiano(ind.getPiano() != null ? ind.getPiano() : "");
            praticaSit.getIndirizziInterventoSIT().getIndirizzoInterventoSIT().add(i);
        }

        praticaSit.setProcedimentiSIT(new ProcedimentiSIT());
        for (PraticaProcedimenti pr : listaProcedimenti) {
            ProcedimentoSIT p = new ProcedimentoSIT();
            p.setCodProcedimento(pr.getProcedimenti().getCodProc());
            p.setDesProcedimento(pr.getProcedimenti().getProcedimentiTestiByLang("it"));
            p.setIdProcedimento(pr.getProcedimenti().getIdProc());
            praticaSit.getProcedimentiSIT().getProcedimentoSIT().add(p);
        }
        praticaSit.setCodStatoPratica(statoPratica.getGrpStatoPratica());
        praticaSit.setDesStatoPratica(statoPratica.getDescrizione());
        if (pratica.getDataChiusura() != null) {
            praticaSit.setDataChiusura(sdf.format(pratica.getDataChiusura()));
        } else {
            praticaSit.setDataChiusura("");
        }

        praticaSit.setAnagraficheSIT(new AnagraficheSIT());
        for (PraticaAnagrafica anag : listaAnagrafiche) {
            AnagraficaSIT a = new AnagraficaSIT();
            a.setCodiceFiscale(anag.getAnagrafica().getCodiceFiscale() != null ? anag.getAnagrafica().getCodiceFiscale() : "");
            a.setCognome(anag.getAnagrafica().getCognome() != null ? anag.getAnagrafica().getCognome() : "");
            a.setNome(anag.getAnagrafica().getNome() != null ? anag.getAnagrafica().getNome() : "");
            a.setDenominazione(anag.getAnagrafica().getDenominazione() != null ? anag.getAnagrafica().getDenominazione() : "");
            a.setIdAnagrafica(String.valueOf(anag.getAnagrafica().getIdAnagrafica().intValue()));
            a.setPartitaIva(anag.getAnagrafica().getPartitaIva() != null ? anag.getAnagrafica().getPartitaIva() : "");
            a.setTipoAnagrafica(String.valueOf(anag.getAnagrafica().getTipoAnagrafica()));
            if (anag.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.PROFESSIONISTA)) {
                if (anag.getIdTipoQualifica() != null) {
                    a.setCodTipoRuolo(anag.getIdTipoQualifica().getCodQualifica());
                    a.setDesTipoRuolo(anag.getIdTipoQualifica().getDescrizione());
                } else {
                    a.setCodTipoRuolo("A");
                    a.setDesTipoRuolo("Altro");
                }
            } else {
                a.setCodTipoRuolo(anag.getLkTipoRuolo().getCodRuolo());
                a.setDesTipoRuolo(anag.getLkTipoRuolo().getDescrizione());
            }
                praticaSit.getAnagraficheSIT().getAnagraficaSIT().add(a);
         }

        LkTipoOggetto tipoOggetto = lookupDao.findTipoOggettoByCodice(AvBariConstants.TIPO_OGGETTO);
        praticaSit.setFlgDeroga("N");
        praticaSit.setFlgSanatoria("N");
        praticaSit.setDataFineLavori("");
        praticaSit.setDataInizioLavori("");
        praticaSit.setDataFineLavoriPresunta("");
        if (tipoOggetto != null) {
            String istanza = String.valueOf(pratica.getIdPratica());
            List<DefDatiEstesi> lista = defDatiEstesiDao.findByTipoOggettoIstanza(tipoOggetto, istanza);
            if (lista != null && !lista.isEmpty()) {
                for (DefDatiEstesi de : lista) {
                    if (AvBariConstants.INTERVENTO_IN_DEROGA.equals(de.getCodValue())) {
                        praticaSit.setFlgDeroga(de.getValue());
                    }
                    if (AvBariConstants.INTERVENTO_IN_SANATORIA.equals(de.getCodValue())) {
                        praticaSit.setFlgSanatoria(de.getValue());
                    }
                    if (AvBariConstants.DATA_FINE_LAVORI.equals(de.getCodValue())) {
                        praticaSit.setDataFineLavori(de.getValue());
                    }
                    if (AvBariConstants.DATA_FINE_LAVORI_PRESUNTA.equals(de.getCodValue())) {
                        praticaSit.setDataFineLavoriPresunta(de.getValue());
                    }
                    if (AvBariConstants.DATA_INIZIO_LAVORI.equals(de.getCodValue())) {
                        praticaSit.setDataInizioLavori(de.getValue());
                    }
                }
            }
        }

        return praticaSit;
    }

    private ResponseSitBean inviaPratica(PraticheEventi praticaEvento) throws Exception {
        Integer idEnte = praticaEvento.getIdPratica().getIdProcEnte().getIdEnte().getIdEnte();
        ResponseSitBean ret = new ResponseSitBean();
        ret.setOperazioneOK(Boolean.FALSE);
        String address = configurationService.getCachedPluginConfiguration(AvBariConstants.SIT_ENDPOINT, idEnte, null);
        URL sitWsdlUrl = IntegrazioneSueSuap.class.getResource("/it/wego/cross/avbari/sit/wsdl/IntegrazioneSueSuap.svc.wsdl");

        IntegrazioneSueSuap ss = new IntegrazioneSueSuap(sitWsdlUrl);
        IIntegrazioneSueSuap port = ss.getBasicHttpBindingIIntegrazioneSueSuap();
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);

        Client client = ClientProxy.getClient(port);
        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(10000);
            policy.setReceiveTimeout(5000);
            policy.setAllowChunking(false);
            conduit.setClient(policy);
        }

        String request = creaRichiesta(praticaEvento, idEnte);

        String response = port.process(request);
        JAXBContext jc = JAXBContext.newInstance(RisultatoOperazione.class);
        Unmarshaller um = jc.createUnmarshaller();
        InputStream is = new ByteArrayInputStream(response.getBytes());
        RisultatoOperazione risposta = (RisultatoOperazione) um.unmarshal(is);
        if (risposta.getEsito() == null || "".equals(risposta.getEsito())) {
            ret.setOperazioneOK(Boolean.TRUE);
            ret.setMessaggio((String) risposta.getMessaggio());
        } else {
            ret.setOperazioneOK(Boolean.FALSE);
            ret.setMessaggio((String) risposta.getMessaggio());
        }
        return ret;
    }

}

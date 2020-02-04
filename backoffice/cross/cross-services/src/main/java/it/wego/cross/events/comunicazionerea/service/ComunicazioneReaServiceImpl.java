/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.service;

import it.gov.impresainungiorno.schema.suap.pratica.ProcuraSpeciale;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gov.impresainungiorno.schema.suap.rea.ComunicazioneREA;
import it.gov.impresainungiorno.schema.suap.ri.ProtocolloSUAP;
import it.infocamere.protocollo.ejb.AllegatoSUAP;
import it.infocamere.protocollo.ejb.AllegatoSUAPReaXml;
import it.infocamere.protocollo.ejb.AllegatoSUAPXml;
import it.infocamere.protocollo.ejb.RispostaREA;
import it.infocamere.protocollo.ejb.SoggettoSUAP;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.ConfigurationDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.PraticheAllegatiView;
import it.wego.cross.events.comunicazionerea.bean.AnagraficaPraticaDTO;
import it.wego.cross.events.comunicazionerea.bean.ComunicazioneReaDTO;
import it.wego.cross.events.comunicazionerea.dao.ComunicazioneReaDao;
import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.events.comunicazionerea.serializer.ComunicazioneReaSerializer;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.RegistroImpreseService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.dom.DOMSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public class ComunicazioneReaServiceImpl implements ComunicazioneReaService {

    @Autowired
    private ComunicazioneReaDao comunicazioneReaDao;
    @Autowired
    ConfigurationDao configurationDao;
    @Autowired
    PluginService pluginService;
    @Autowired
    PraticheService praticheService;
    @Autowired
    RegistroImpreseService registroImpreseService;
    @Autowired
    AnagraficheSerializer anagraficheSerializer;
    @Autowired
    ComunicazioneReaSerializer comunicazioneReaSerializer;
    @Autowired
    PraticaDao praticaDao;

    @Override
    public List<AnagraficaPraticaDTO> getAnagraficheFisichePraticaList(Pratica pratica) {
        List<AnagraficaPraticaDTO> anagraficheFisichePraticaList = new ArrayList<AnagraficaPraticaDTO>();

        AnagraficaPraticaDTO anagraficaLoop;
        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            anagraficaLoop = new AnagraficaPraticaDTO();
            anagraficaLoop.setIdAnagrafica(praticaAnagrafica.getAnagrafica().getIdAnagrafica());
            StringBuilder sb = new StringBuilder();
            if (praticaAnagrafica.getAnagrafica().getTipoAnagrafica() == Constants.FLAG_ANAGRAFICA_FISICA) {
                sb.append(praticaAnagrafica.getAnagrafica().getCognome());
                sb.append(" ");
                sb.append(praticaAnagrafica.getAnagrafica().getNome());
                sb.append(" ( ");
                sb.append(praticaAnagrafica.getAnagrafica().getCodiceFiscale());
                sb.append(" )");
                anagraficaLoop.setDescrizioneAngrafica(sb.toString());
                anagraficheFisichePraticaList.add(anagraficaLoop);
            }
        }
        return anagraficheFisichePraticaList;
    }

    @Override
    public List<AnagraficaPraticaDTO> getAnagraficheGiuridichePraticaList(Pratica pratica) {
        List<AnagraficaPraticaDTO> anagraficheGiuridichePraticaList = new ArrayList<AnagraficaPraticaDTO>();
        AnagraficaPraticaDTO anagraficaLoop;
        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            anagraficaLoop = new AnagraficaPraticaDTO();
            anagraficaLoop.setIdAnagrafica(praticaAnagrafica.getAnagrafica().getIdAnagrafica());
            StringBuilder sb = new StringBuilder();
            if (praticaAnagrafica.getAnagrafica().getTipoAnagrafica() != Constants.FLAG_ANAGRAFICA_FISICA) {
                sb.append(praticaAnagrafica.getAnagrafica().getDenominazione());
                sb.append(" ");
                sb.append(" ( ");
                sb.append(praticaAnagrafica.getAnagrafica().getCodiceFiscale());
                sb.append(" )");
                anagraficaLoop.setDescrizioneAngrafica(sb.toString());
                anagraficheGiuridichePraticaList.add(anagraficaLoop);
            }
        }
        return anagraficheGiuridichePraticaList;
    }

    @Override
    public List<Allegato> comunicazioneArrivoPraticaSuap(Pratica pratica, ComunicazioneReaDTO istruttoria, Utente user, String statoPratica) throws Exception {
        List<Allegato> allegatiResult = new ArrayList<Allegato>();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        if (pratica.getIdPraticaPadre() != null) {
            throw new Exception("La comunicazione al REA può essere fatta esclusivamente dal SUAP.");
        }
        PraticheEventi praticaEvento = praticaDao.getDettaglioPraticaEvento(istruttoria.getIdEventoPartenza());
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        //TODO: gestire il caso Comune su Ente
        GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(ente.getIdEnte(), null);

        ProtocolloSUAP comunicazioneSuapPortType = registroImpreseService.getComunicazioneSuapPortType();
        SoggettoSUAP soggett = new SoggettoSUAP();
        soggett.setCognomeAddetto(user.getCognome());
        soggett.setNomeAddetto(user.getNome());

        ComunicazioneREA comunicazioneRea = comunicazioneReaSerializer.serializeComunicazioneREA(pratica, praticaEvento, statoPratica);
        AllegatoSUAPReaXml allegatoSuapReaXml = new AllegatoSUAPReaXml();
        allegatoSuapReaXml.setDataHandler(new DOMSource(Utils.marshallToDocument(comunicazioneRea)));
        RiepilogoPraticaSUAP riepilogoPraticaSuap = null;
        AllegatoSUAPXml allegatoSuapXml = null;
        if (statoPratica.equalsIgnoreCase("istruttoria")) {
            riepilogoPraticaSuap = comunicazioneReaSerializer.serializeRiepilogoPraticaSUAP(pratica, praticaEvento);
            if (istruttoria.getIdAllegatoProcuraSpeciale() != null) {
                ProcuraSpeciale procuraSpeciale = comunicazioneReaSerializer.serializeProcuraSpeciale(riepilogoPraticaSuap.getIntestazione().getImpresa().getLegaleRappresentante().getCodiceFiscale(), pratica.getDataRicezione(), istruttoria.getIdAllegatoProcuraSpeciale());
                riepilogoPraticaSuap.getIntestazione().setProcuraSpeciale(procuraSpeciale);
            }
            allegatoSuapXml = new AllegatoSUAPXml();
            allegatoSuapXml.setDataHandler(new DOMSource(Utils.marshallToDocument(riepilogoPraticaSuap)));
        }

        List<AllegatoSUAP> allegatiSuap = new ArrayList<AllegatoSUAP>();
        AllegatoSUAP allegatoSuap;
        for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
            Allegati allegatoEvento = pea.getAllegati();
            allegatoSuap = new AllegatoSUAP();
            byte[] file = gestioneAllegati.getFileContent(allegatoEvento, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
            allegatoSuap.setAllegatoDataHandler(Utils.getDataHandlerFromByteArray(file, allegatoEvento.getTipoFile()));
            allegatoSuap.setName(allegatoEvento.getNomeFile());
            allegatiSuap.add(allegatoSuap);
        }

        byteArray.reset();
        Utils.marshall(comunicazioneRea, byteArray);
        Allegato suapReaXmlAllegato = new Allegato();
        suapReaXmlAllegato.setFile(byteArray.toByteArray());
        suapReaXmlAllegato.setDescrizione("Richiesta: Suap Rea");
        suapReaXmlAllegato.setProtocolla(Boolean.FALSE);
        suapReaXmlAllegato.setNomeFile("request_suap_rea.xml");
        suapReaXmlAllegato.setTipoFile("plain/text");
        allegatiResult.add(suapReaXmlAllegato);
        if (Log.WS.isTraceEnabled()) {
            Utils.storeXmlOnFileSystem(suapReaXmlAllegato.getFile(), Utils.getTempFile("xml"));
        }

        if (riepilogoPraticaSuap != null) {
            byteArray.reset();
            Utils.marshall(riepilogoPraticaSuap, byteArray);
            Allegato suapXmlAllegato = new Allegato();
            suapXmlAllegato.setFile(byteArray.toByteArray());
            suapXmlAllegato.setDescrizione("Richiesta: Riepilogo pratica SUAP");
            suapXmlAllegato.setProtocolla(Boolean.FALSE);
            suapXmlAllegato.setNomeFile("request_riepilogo_pratica_suap.xml");
            suapXmlAllegato.setTipoFile("plain/text");
            allegatiResult.add(suapXmlAllegato);
            if (Log.WS.isTraceEnabled()) {
                Utils.storeXmlOnFileSystem(suapXmlAllegato.getFile(), Utils.getTempFile("xml"));
            }
        }
        RispostaREA comunicazioneREA = comunicazioneSuapPortType.comunicazioneREA(soggett, null, allegatoSuapReaXml, allegatoSuapXml, allegatiSuap, true, true);

        byteArray.reset();
        Utils.marshall(comunicazioneREA, byteArray);
        Allegato comunicazioneReaAllegato = new Allegato();
        comunicazioneReaAllegato.setFile(byteArray.toByteArray());
        comunicazioneReaAllegato.setDescrizione("Risposta: Comunicazione REA");
        comunicazioneReaAllegato.setProtocolla(Boolean.FALSE);
        comunicazioneReaAllegato.setNomeFile("response_comunicazione_rea.xml");
        comunicazioneReaAllegato.setTipoFile("plain/text");
        allegatiResult.add(comunicazioneReaAllegato);
        if (Log.WS.isTraceEnabled()) {
            Utils.storeXmlOnFileSystem(comunicazioneReaAllegato.getFile(), Utils.getTempFile("xml"));
        }

        if (comunicazioneREA.getEsitoRichiesta() != 0) {
            throw new Exception(comunicazioneREA.getDettaglioEsitoRichiesta());
        }

        return allegatiResult;
    }

    @Override
    public RiIntegrazioneRea getIntegrazioneRea(Pratica pratica) {
        return comunicazioneReaDao.findRiIntegrazioneReaByIdPratica(pratica.getIdPratica());
    }

    @Override
    public List<AllegatoDTO> getAllegati(Pratica pratica) {
        List<PraticheAllegatiView> allegatiPratica = praticaDao.getAllegatiPratica(pratica);
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        if (allegatiPratica != null && !allegatiPratica.isEmpty()) {
            for (PraticheAllegatiView paw : allegatiPratica) {
                AllegatoDTO allegato = AllegatiSerializer.serialize(paw);
                allegati.add(allegato);
            }
        }
        return allegati;
    }
}

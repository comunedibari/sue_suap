/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Function;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EstrazioniAgibDTO;
import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaBarDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.ScadenzeSerializer;
import it.wego.cross.serializer.RecapitiSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.EventiSerializer;
import it.wego.cross.serializer.DettaglioComunicazioneSerializer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import it.wego.cross.actions.WorkflowAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.TipoRuolo;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.*;
import it.wego.cross.dao.PraticaDao.IntervalloScadenzeMultiple;
import it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.*;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.DatiCatastaliSerializer;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.RecapitoUtils;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Service
public class PraticheServiceImpl implements PraticheService {

    protected static final Integer ANNO_RIFERIMENTO_PARTENZA = 1840;

    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private ProcessiDao processiDao;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private DettaglioComunicazioneSerializer dettaglioComunicazioneSerializer;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private DatiCatastaliSerializer datiCatastaliSerializer;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    @Autowired
    private EventiSerializer eventiSerializer;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private WorkflowAction workflowAction;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private RecapitoUtils recapitoUtils;

    /*
     * trova tutte le pratiche assegnate ad un utente ma ancora in staging
     * @author CS
     */
    @Override
    public PraticaDTO dettaglioXmlPratica(Integer idPratica) throws Exception {

        Pratica pratica = praticaDao.findPratica(idPratica);
        String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXml = PraticaUtils.getPraticaFromXML(xmlPratica);
        PraticaDTO praticaDto = new PraticaDTO();
        praticaDto.setIdPratica(idPratica);

        praticaDto.setTipoMessaggio(pratica.getIdStaging().getTipoMessaggio());
        praticaDto.setDataApertura(pratica.getDataApertura());
        praticaDto.setDataChiusura(pratica.getDataChiusura());
        praticaDto.setDataRicezione(pratica.getDataRicezione());
        praticaDto.setIdentificativoPratica(pratica.getIdentificativoPratica());
        praticaDto.setOggettoPratica(pratica.getOggettoPratica());
        praticaDto.setProtocollo(pratica.getProtocollo());
// TODO: protocollo        
        //praticaDto.setFascicolo(pratica.getCodFascicolo());
        praticaDto.setRegistro(pratica.getCodRegistro());
        praticaDto.setAnnoRiferimento(pratica.getAnnoRiferimento());
        praticaDto.setIdPratica(pratica.getIdPratica());
        praticaDto.setResponsabileProcedimento(praticaXml.getResponsabileProcedimento());
        /* ^^CS AGGIUNTA PER CONFIGURAZIONE */
        praticaDto.setIdEnte(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        praticaDto.setIdComune(pratica.getIdComune().getIdComune());
        //Anagrafiche
        List<AnagraficaDTO> anagraficaList = new ArrayList<AnagraficaDTO>();
        for (it.wego.cross.xml.Anagrafiche anagraficaXml : praticaXml.getAnagrafiche()) {
            AnagraficaDTO anagrafica = new AnagraficaDTO();
            anagraficaUtils.copyXML2Anagrafica(anagraficaXml, anagrafica, pratica);
            anagraficaList.add(anagrafica);
        }
        praticaDto.setAnagraficaList(anagraficaList);

        //Procedimenti
        List<ProcedimentoDTO> procedimentiList = new ArrayList<ProcedimentoDTO>();
        for (it.wego.cross.xml.Procedimento procedimentoXml : praticaXml.getProcedimenti().getProcedimento()) {
            ProcedimentoDTO procedimento = new ProcedimentoDTO();
            procedimento.fromXML(procedimentoXml);
            procedimentiList.add(procedimento);
        }
        praticaDto.setProcedimentiList(procedimentiList);

        //^^CS NOTIFICA
        if (praticaXml.getNotifica() != null) {
            RecapitoDTO notifica = new RecapitoDTO();
            recapitoUtils.copyXML2Recapito(praticaXml.getNotifica(), notifica);
            praticaDto.setNotifica(notifica);
        }
        // ^^CS ALLEGATI
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        it.wego.cross.xml.Allegati allegatiXML = AllegatiSerializer.serialize(pratica);
        for (it.wego.cross.xml.Allegato allegatoXMl : allegatiXML.getAllegato()) {
            AllegatoDTO allegato = AllegatiSerializer.serializeAllegatoXML(allegatoXMl);
            allegati.add(allegato);
        }
        AllegatoDTO ripilogo = new AllegatoDTO();
        if (pratica.getIdModello() != null) {
            ripilogo.setIdAllegato(pratica.getIdModello().getId());
        }
        praticaDto.setRiepilogoPratica(ripilogo);
        praticaDto.setAllegatiList(allegati);

        GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        //^^CS DATI CATASTALI
        List<DatiCatastaliDTO> datiList = new ArrayList<DatiCatastaliDTO>();
        if (praticaXml.getDatiCatastali() != null && praticaXml.getDatiCatastali().getImmobile() != null) {
            for (it.wego.cross.xml.Immobile dati : praticaXml.getDatiCatastali().getImmobile()) {
                DatiCatastaliDTO datiDto = new DatiCatastaliDTO();
                datiCatastaliSerializer.serialize(pratica, dati, datiDto);
                datiDto.setIdPratica(praticaDto.getIdPratica());
                if (Utils.e(dati.getConfermato())) {
                    datiDto.setConfermato("false");
                }
                datiList.add(datiDto);
            }
        }
        praticaDto.setDatiCatastali(datiList);
        List<IndirizzoInterventoDTO> datiListIndirizzi = new ArrayList<IndirizzoInterventoDTO>();
        if (praticaXml.getIndirizziIntervento() != null && praticaXml.getIndirizziIntervento().getIndirizzoIntervento() != null) {
            for (it.wego.cross.xml.IndirizzoIntervento dati : praticaXml.getIndirizziIntervento().getIndirizzoIntervento()) {
                IndirizzoInterventoDTO datiDto = new IndirizzoInterventoDTO();
                datiCatastaliSerializer.serialize(pratica, dati, datiDto);
                datiDto.setIdPratica(idPratica);
                if (Utils.e(dati.getConfermato())) {
                    datiDto.setConfermato("false");
                }
                datiListIndirizzi.add(datiDto);
            }
        }
        praticaDto.setIndirizziIntervento(datiListIndirizzi);

        praticaDto.setComune(lookupService.getComuneDTOFromId(praticaDto.getIdComune()));

        praticaDto.setEsistenzaStradario(gp.getEsistenzaStradario(pratica));
        praticaDto.setEsistenzaRicercaCatasto(gp.getEsistenzaRicercaCatasto(pratica));

        return praticaDto;
    }

    @Override
    public Pratica getPratica(Integer idPratica) throws Exception {
        Preconditions.checkNotNull(idPratica);
        Pratica pratica = praticaDao.findPratica(idPratica);
        //TODO : modificare il  db in modo che questo non serva!
        List<PraticheEventi> praticheEventi = pratica.getPraticheEventiList();
        for (PraticheEventi praticaEvento : praticheEventi) {
            if ("".equals(praticaEvento.getDescrizioneEvento()) || praticaEvento.getDescrizioneEvento() == null) {
                if (praticaEvento.getIdEvento() != null) {
                    praticaEvento.setDescrizioneEvento(praticaEvento.getIdEvento().getDesEvento());
                }
            }
        }
        Preconditions.checkNotNull(pratica, "Pratica non trovata per id = %s", idPratica);
        return pratica;
    }

    @Override
    public List<EventoDTO> getEventiPratica(Pratica pratica) throws Exception {
        List<EventoDTO> eventi = new ArrayList<EventoDTO>();
        for (PraticheEventi pe : pratica.getPraticheEventiList()) {
            EventoDTO evento = eventiSerializer.serializeEvento(pe);
            eventi.add(evento);
        }
        return eventi;
    }

    @Override
    public EventoDTO getDettaglioPraticaEvento(Integer idEventoPratica) throws Exception {
        PraticheEventi dettaglio = praticaDao.getDettaglioPraticaEvento(idEventoPratica);
        EventoDTO evento = eventiSerializer.serializeDettaglioEvento(dettaglio);
        return evento;
    }

    @Override
    public EventoDTO getDettaglioProcessoEvento(Integer id) {
        ProcessiEventi dettaglio = praticaDao.getDettaglioProcessoEvento(id);
        EventoDTO evento = eventiSerializer.serializeEvento(dettaglio);
        return evento;
    }

    @Override
    public EventoDTO getEvento(Integer idEvento) {
        ProcessiEventi pe = praticaDao.findByIdEvento(idEvento);
        EventoDTO evento = eventiSerializer.serializeEvento(pe);
        return evento;
    }

    @Override
    public ProcessiEventi findProcessiEventi(Integer idEvento) {
        ProcessiEventi pe = praticaDao.findByIdEvento(idEvento);
        return pe;
    }

    @Override
    public Pratica getPraticaRoot(Pratica pratica) {
        Pratica praticaLoop = pratica;
        while (praticaLoop.getIdPraticaPadre() != null) {
            praticaLoop = praticaLoop.getIdPraticaPadre();
        }
        return praticaLoop;
    }

    @Override
    public Pratica getPraticaEnte(Pratica pratica, Enti ente) {
        return getPraticaEnteRec(getPraticaRoot(pratica), ente);
    }

    public Pratica getPraticaEnteRec(Pratica pratica, Enti ente) {
        if (pratica.getIdProcEnte().getIdEnte().equals(ente)) {
            return pratica;
        }
        if (pratica.getPraticaList().isEmpty()) {
            return null;
        }
        Pratica praticaLoopResult;
        for (Pratica praticaLoop : pratica.getPraticaList()) {
            praticaLoopResult = getPraticaEnteRec(praticaLoop, ente);
            if (praticaLoopResult != null && praticaLoopResult.getIdProcEnte().getIdEnte().equals(ente)) {
                return praticaLoopResult;
            }
        }
        return null;
    }

    @Override
    public List<AllegatoDTO> getAllegatiPratica(Integer idPratica) throws Exception {
        return getAllegatiPratica(getPratica(idPratica));
    }

    @Override
    public List<AllegatoDTO> getAllegatiPratica(Pratica pratica) {
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>();
        pratica.getPraticheEventiList();
        for (PraticheEventi praticaEvento : pratica.getPraticheEventiList()) {
            for (PraticheEventiAllegati pea : praticaEvento.getPraticheEventiAllegatiList()) {
                Allegati allegato = pea.getAllegati();
                AllegatoDTO a = new AllegatoDTO();
                a.setDescrizione(allegato.getDescrizione());
                a.setIdAllegato(allegato.getId());
                a.setNomeFile(allegato.getNomeFile());
                a.setTipoFile(allegato.getTipoFile());
                a.setIdFileEsterno(allegato.getIdFileEsterno());
                if (!allegati.contains(a)) {
                    allegati.add(a);
                }
            }
        }
        return allegati;
    }

    @Override
    public LkStatoPratica findLookupStatoPratica(Integer idLookup) {
        LkStatoPratica lookup = lookupDao.findLkStatoPraticaByIdStatoPratica(idLookup);
        return lookup;
    }

    @Override
    public List<PraticaNuova> findFiltrate(Filter filter) throws Exception {
        List<Pratica> praticheAperte = praticaDao.findFiltrate(filter);
        List<PraticaNuova> pratiche = new ArrayList<PraticaNuova>();
        for (Pratica pratica : praticheAperte) {
            PraticaNuova praticaNuova = praticheSerializer.serializePraticaNuova(pratica, filter.getConnectedUser());
            pratiche.add(praticaNuova);
        }
        return pratiche;
    }

    @Override
    public Long countFiltrate(Filter filter) {
        return praticaDao.countFiltrate(filter);
    }

    @Override
    public Pratica getPraticaByIdentificativo(String identificativoPratica) {
        Pratica pratica = praticaDao.findPraticaByIdentificativo(identificativoPratica);
        return pratica;
    }

    @Override
    public List<LkStatoPratica> findAllLookupStatoPratica() {
        List<LkStatoPratica> lookups = lookupDao.findAllLkStatoPratica();
        return lookups;
    }

    @Override
    public LkStatoPratica findLookupStatoPratica(String codiceStatoPratica) {
        LkStatoPratica lookups = lookupDao.findStatoPraticaByCodice(codiceStatoPratica);
        return lookups;
    }

    @Override
    public Long countDaAprire(Filter filter) {
        Long praticheDaAprire = praticaDao.countDaAprireFiltrate(filter);
        return praticheDaAprire;
    }

    @Override
    public List<PraticaNuova> findDaAprire(Filter filter) throws Exception {
        List<Pratica> praticheAperte = praticaDao.findDaAprireFiltrate(filter);
        List<PraticaNuova> pratiche = new ArrayList<PraticaNuova>();
        for (Pratica pratica : praticheAperte) {
            PraticaNuova praticaNuova = praticheSerializer.serializePraticaNuova(pratica, filter.getConnectedUser());
            pratiche.add(praticaNuova);
        }
        return pratiche;
    }

    @Override
    public Long countScadenze(Filter filter) {
        Long count = praticaDao.countScadenze(filter);
        return count;
    }

    @Override
    public List<ScadenzaDTO> findScadenze(Filter filter) {
        List<Scadenze> scadenze = praticaDao.findScadenze(filter);
        List<ScadenzaDTO> result = new LinkedList<ScadenzaDTO>();
        for (Scadenze scadenza : scadenze) {
            ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
            result.add(s);
        }
        return result;
    }

    @Override
    public List<LkStatiScadenze> findAllLookupScadenze() {
        List<LkStatiScadenze> scadenze = lookupDao.findAllStatiScadenze();
        return scadenze;
    }

    @Override
    public LkStatiScadenze findStatoScadenzaById(String id) {
        LkStatiScadenze scadenza = lookupDao.findStatoScadenzaById(id);
        return scadenza;
    }

    @Override
    public List<PraticaNuova> findDaAssegnare(Filter filter) throws Exception {
        List<Pratica> praticheAperte = praticaDao.findDaAssegnare(filter);
        List<PraticaNuova> pratiche = new ArrayList<PraticaNuova>();
        for (Pratica pratica : praticheAperte) {
            PraticaNuova praticaNuova = praticheSerializer.serializePraticaNuova(pratica, filter.getConnectedUser());
            pratiche.add(praticaNuova);
        }
        return pratiche;
    }

    @Override
    public Long countDaAssegnare(Filter filter) {
        Long count = praticaDao.countDaAssegnare(filter);
        return count;
    }

    @Override
    public PraticaProcedimenti findPraticaProcedimento(Integer idPratica, Integer idProcedimento, Integer idEnte) throws Exception {
        return praticaDao.findPraticaProcedimento(idPratica, idProcedimento, idEnte);
    }

    @Override
    public PraticaProcedimenti findEnteDaPraticaProcedimento(Integer idPratica, Integer idProcedimento) throws Exception {
        return praticaDao.findPraticaProcedimenti(idPratica, idProcedimento);
    }

    @Override
    public ProcessiEventi findProcessiEventiByCodEventoIdProcesso(String codiceEvento, Integer idProcesso) {
        return processiDao.findProcessiEventiByCodEventoIdProcesso(codiceEvento, idProcesso);
    }

    @Override
    public List<ScadenzeDaChiudereView> getScadenzeDaChiudere(Integer idEvento, Integer idPratica) {
        List<ScadenzeDaChiudereView> scadenze = praticaDao.findScadenzeDaChiudere(idEvento, idPratica);
        return scadenze;
    }

    @Override
    public Scadenze findScadenzeByIdScadenza(Integer idScadenza) {
        Scadenze scadenza = praticaDao.findScadenzeByIdScadenza(idScadenza);
        return scadenza;
    }

    @Override
    public IntervalloScadenzeMultiple findIntervalloScadenzeMultipleSospensione(Integer idPratica) {
        return praticaDao.findIntervalloScadenzeMultipleSospensione(idPratica);
    }

    @Override
    public Scadenze findScadenzaPratica(Pratica pratica) {
        return praticaDao.findScadenzaPratica(pratica);
    }

    @Override
    public List<Scadenze> findScadenzaPraticaAttive(Pratica pratica) {
        return praticaDao.findScadenzaPraticaAttive(pratica);
    }

    @Override
    public List<PraticaAnagrafica> findAziendePratica(Pratica pratica) {
        List<PraticaAnagrafica> aziendePratica = new ArrayList<PraticaAnagrafica>();
        for (PraticaAnagrafica praticaAnagrafica : pratica.getPraticaAnagraficaList()) {
            if (String.valueOf(praticaAnagrafica.getAnagrafica().getTipoAnagrafica()).equalsIgnoreCase("G")
                    && praticaAnagrafica.getLkTipoRuolo().getCodRuolo().equalsIgnoreCase(Constants.RUOLO_COD_BENEFICIARIO)) {
                aziendePratica.add(praticaAnagrafica);
            }
        }

        return aziendePratica;
    }

    @Override
    public Pratica findByRegistroFascicoloProtocolloAnno(String codRegistro, String fascicolo, String numeroProtocollo, Integer annoProtocollo) {
        return praticaDao.findByRegistroFascicoloProtocolloAnno(codRegistro, fascicolo, numeroProtocollo, annoProtocollo);
    }

    @Override
    public List<Pratica> findByRegistroFascicoloAnno(String codRegistro, String fascicolo, Integer annoProtocollo) {
        return praticaDao.findByRegistroFascicoloAnno(codRegistro, fascicolo, annoProtocollo);
    }
    
    @Override
    public List<Pratica> findPraticheDaRiprotocollare(String protocollo) {
        return praticaDao.findPraticheDaRiprotocollare(protocollo);
    }

    @Override
    public Pratica findByAnnoProtocollo(Integer anno, String numeroProtocollo) {
        return praticaDao.findByAnnoProtocollo(anno, numeroProtocollo);
    }

    @Override
    public List<PraticaBarDTO> findScadenzeForBar(Utente connectedUser) {
        List<PraticaBarDTO> scadenze = praticaDao.findScadenzaBar(connectedUser);
        return scadenze;
    }

    @Override
    public List<PraticaBarDTO> findDaAprireForBar(Utente connectedUser) {
        List<PraticaBarDTO> pratiche = praticaDao.findPraticheDaAprireBar(connectedUser);
        return pratiche;
    }

    @Override
    public List<Pratica> findPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter) {
        List<Pratica> pratiche = praticaDao.findPraticheFiglie(praticaPadre, idEnte, filter);
        return pratiche;
    }

    @Override
    public Long countPraticheFiglie(Pratica praticaPadre, Enti idEnte, Filter filter) {
        Long count = praticaDao.countPraticheFiglie(praticaPadre, idEnte, filter);
        return count;
    }

    @Override
    public PraticaAnagrafica findPraticaAnagraficaByKey(Integer idPratica, Integer idAnagrafica, Integer ruolo) {
        PraticaAnagrafica pratica = praticaDao.findPraticaAnagraficaByKey(idPratica, idAnagrafica, ruolo);
        return pratica;
    }

    @Override
    public Boolean praticaHasPastEvent(Pratica pratica, List<String> codEventoList) throws Exception {
        //TODO: E' tardi, sono stanco...sarebbe da fare una query a posta...
        for (String codEvento : codEventoList) {
            if (praticaHasPastEvent(pratica, codEvento)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean praticaHasPastEvent(Pratica pratica, String codEvento) throws Exception {
        List<PraticheEventi> praticheEventi = praticaDao.findEventoPratica(pratica, codEvento);
        return praticheEventi != null && praticheEventi.size() > 0;
    }

    @Override
    @Deprecated
    public DettaglioPraticaDTO getDettaglioPratica(Pratica pratica) throws Exception {
        DettaglioPraticaDTO dettaglio = new DettaglioPraticaDTO();
        Log.APP.info("Creo il dettaglio della pratica");
        Log.APP.info("ID " + pratica.getIdentificativoPratica());
        dettaglio.setIdPratica(pratica.getIdPratica());
        dettaglio.setIdentificativoPratica(pratica.getIdentificativoPratica());
        dettaglio.setDataRicezione(pratica.getDataRicezione());
        Log.APP.info("Protocollo " + pratica.getProtocollo());
        dettaglio.setNumeroProtocollo(pratica.getProtocollo() + "/" + pratica.getAnnoRiferimento());
        Log.APP.info("Oggetto " + pratica.getOggettoPratica());
        dettaglio.setOggetto(pratica.getOggettoPratica());
        Log.APP.info("Stato pratica: " + pratica.getIdStatoPratica().getDescrizione());
        dettaglio.setStatoPratica(pratica.getIdStatoPratica().getDescrizione());
        dettaglio.setCodiceStatoPratica(pratica.getIdStatoPratica().getGrpStatoPratica());
        List<AnagraficaMinifiedDTO> anagrafiche = new LinkedList<AnagraficaMinifiedDTO>();
        Log.APP.info("Trovate " + pratica.getPraticaAnagraficaList().size() + " anagrafiche");
        List<AnagraficaMinifiedDTO> richiedenti = new ArrayList<AnagraficaMinifiedDTO>();
        List<AnagraficaMinifiedDTO> beneficiari = new ArrayList<AnagraficaMinifiedDTO>();
        List<AnagraficaMinifiedDTO> altro = new ArrayList<AnagraficaMinifiedDTO>();
        for (PraticaAnagrafica pa : pratica.getPraticaAnagraficaList()) {
            Anagrafica a = pa.getAnagrafica();
            Log.APP.info("Preparo il dettaglio per l'anagrafica " + a.getIdAnagrafica());
            AnagraficaMinifiedDTO anagrafica = dettaglioComunicazioneSerializer.serializeAnagrafica(a);
            if (pa.getLkTipoRuolo() != null) {
                anagrafica.setRuolo(pa.getLkTipoRuolo().getDescrizione());
                anagrafica.setIdRuolo(pa.getLkTipoRuolo().getIdTipoRuolo());
            }
            if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                //Il richiedente e' sempre persona fisica
                anagrafica.setIsDittaIndividuale(Boolean.FALSE);
            } else {
                if (pa.getFlgDittaIndividuale() != null && pa.getFlgDittaIndividuale().equalsIgnoreCase("S")) {
                    anagrafica.setIsDittaIndividuale(Boolean.TRUE);
                } else {
                    anagrafica.setIsDittaIndividuale(Boolean.FALSE);
                }
            }
            Log.APP.info("Aggiungo l'anagrafica di " + anagrafica.getCodiceFiscale() + " " + anagrafica.getPartitaIVA());
            if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.RICHIEDENTE)) {
                richiedenti.add(anagrafica);
            } else if (pa.getLkTipoRuolo().getCodRuolo().equals(TipoRuolo.BENEFICIARIO)) {
                beneficiari.add(anagrafica);
            } else {
                altro.add(anagrafica);
            }
        }
        anagrafiche.addAll(richiedenti);
        anagrafiche.addAll(beneficiari);
        anagrafiche.addAll(altro);
        dettaglio.setAnagrafiche(anagrafiche);
        List<DatiCatastaliDTO> datiCatastali = new ArrayList<DatiCatastaliDTO>();
        Log.APP.info("Ci sono dati catastali? " + (pratica.getDatiCatastaliList() != null));
        GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
        if (pratica.getDatiCatastaliList() != null && !pratica.getDatiCatastaliList().isEmpty()) {
            Log.APP.info("Trovati " + pratica.getDatiCatastaliList().size() + " immobili");
            for (DatiCatastali dato : pratica.getDatiCatastaliList()) {
                DatiCatastaliDTO d = dettaglioComunicazioneSerializer.serializeDatoCatastale(dato);
                d.setUrlCatasto(gp.getUrlCatasto(dato, pratica));
                datiCatastali.add(d);
            }
        }
        dettaglio.setDatiCatastali(datiCatastali);

        List<IndirizzoInterventoDTO> indirizziIntervento = new ArrayList<IndirizzoInterventoDTO>();
        Log.APP.info("Ci sono indirizzi Intervento? " + (pratica.getIndirizziInterventoList() != null));
        if (pratica.getIndirizziInterventoList() != null) {
            Log.APP.info("Trovati " + pratica.getIndirizziInterventoList().size() + " indirizzi ");
            for (IndirizziIntervento dato : pratica.getIndirizziInterventoList()) {
                IndirizzoInterventoDTO d = dettaglioComunicazioneSerializer.serializeIndirizziIntervento(dato);
                d.setUrlCatasto(gp.getUrlCatastoIndirizzo(dato, pratica));
                indirizziIntervento.add(d);
            }
        }
        dettaglio.setIndirizziIntervento(indirizziIntervento);
        List<ProcedimentoDTO> procedimenti = new ArrayList<ProcedimentoDTO>();
        Log.APP.info("Ci sono procedimenti collegati alla pratica? " + (pratica.getPraticaProcedimentiList() != null));
        if (pratica.getPraticaProcedimentiList() != null) {
            Log.APP.info("Trovati " + pratica.getPraticaProcedimentiList().size() + " procedimenti collegati");
            for (PraticaProcedimenti pp : pratica.getPraticaProcedimentiList()) {
                Log.APP.info("La coppia pratica-procedimento è valorizzata? " + (pp != null));
                if (pp != null) {
                    Log.APP.info("C'è un procedimento collegato? " + (pp.getProcedimenti() != null));
                    Log.APP.info("C'è un ente collegato al procedimento? " + (pp.getEnti() != null));
                    if (pp.getProcedimenti() != null && pp.getEnti() != null) {
                        Log.APP.info("Serializzo il procedimento " + pp.getProcedimenti().getIdProc());
                        Log.APP.info("L'ente individuato è " + pp.getEnti().getDescrizione());
                        ProcedimentoDTO procedimento = dettaglioComunicazioneSerializer.serializeProcedimento(pp.getProcedimenti(), pp.getEnti());
                        dettaglioPraticaFiglia(procedimento, pp);
                        procedimenti.add(procedimento);
                    }
                }
            }
        }
        List<NotaDTO> notepratica = new ArrayList<NotaDTO>();
        if (pratica.getNotePraticaList() != null && pratica.getNotePraticaList().size() > 0) {
            for (NotePratica notaDB : pratica.getNotePraticaList()) {
                NotaDTO notaDTO = praticheSerializer.serialize(notaDB);
                notepratica.add(notaDTO);
            }
        }
        dettaglio.setNotePratica(notepratica);
        dettaglio.setProcedimenti(procedimenti);
        List<AllegatoDTO> allegati = new ArrayList<AllegatoDTO>(); // getAllegatiPratica(pratica);
        dettaglio.setAllegati(allegati);
        List<ScadenzaDTO> scadenze = new ArrayList<ScadenzaDTO>();
        Log.APP.info("Ci sono scadenze? " + (pratica.getScadenzeList() != null));
        if (pratica.getScadenzeList() != null) {
            List<Scadenze> scadenzePratica = sortScadenze(pratica.getScadenzeList());
            for (Scadenze scadenza : scadenzePratica) {
                ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
                scadenze.add(s);
            }
        }
        dettaglio.setScadenze(scadenze);
        List<EventoDTO> eventi = new ArrayList<EventoDTO>(); // getEventiPratica(pratica);
        dettaglio.setEventi(eventi);
        dettaglio.setEsistenzaStradario(gp.getEsistenzaStradario(pratica));
        dettaglio.setEsistenzaRicercaCatasto(gp.getEsistenzaRicercaCatasto(pratica));
        return dettaglio;
    }

    private void dettaglioPraticaFiglia(ProcedimentoDTO procedimento, PraticaProcedimenti pp) {
        Pratica praticaFiglia = praticaDao.getPraticaFiglia(pp.getPratica(), pp.getProcedimenti());
        if (praticaFiglia != null) {
            procedimento.setDesStato(praticaFiglia.getIdStatoPratica().getDescrizione());
            if (praticaFiglia.getIdUtente() != null) {
                procedimento.setDesUtente(praticaFiglia.getIdUtente().getCognome() + " " + praticaFiglia.getIdUtente().getNome());
            }
            if (praticaFiglia.getScadenzeList() != null) {
                Long giorni = null;
                for (Scadenze scadenza : praticaFiglia.getScadenzeList()) {
                    if (!scadenza.getIdStato().getGrpStatoScadenza().equalsIgnoreCase("C")) {
                        ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
                        if (giorni == null) {
                            giorni = s.getGiornirestanti();
                        } else if (giorni < s.getGiornirestanti()) {
                            giorni = s.getGiornirestanti();
                        }
                    }
                }
                if (giorni != null) {
                    procedimento.setGiorniScadenza(giorni.toString());
                }
            }
        }
    }

    @Override
    public List<ScadenzaDTO> getScadenzeDaChiudereDTOList(Integer idPratica, Integer idEvento) throws Exception {
        List<ScadenzaDTO> scadenzeDaChiudere = new ArrayList<ScadenzaDTO>();
        List<ScadenzeDaChiudereView> scadenze = getScadenzeDaChiudere(idEvento, idPratica);
        if (scadenze != null && !scadenze.isEmpty()) {
            for (ScadenzeDaChiudereView scadenza : scadenze) {
                ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
                scadenzeDaChiudere.add(s);
            }
        }
        return scadenzeDaChiudere;
    }

    @Override
    public Integer getIdProcedimentoSuap(PraticaDTO pratica) throws Exception {
        Enti ente = entiService.findByCodEnte(pratica.getCodEnte());
        if (ente.getTipoEnte().equals("ENTE INTERNO") || ente.getTipoEnte().equals("ENTE ESTERNO")) {
            if (pratica.getProcedimentiList() != null && pratica.getProcedimentiList().size() == 1) {
                return pratica.getProcedimentiList().get(0).getIdProcedimento();
            } else {
                throw new Exception("Specificare un solo procedimento");
            }
        } else {
            List<Procedimenti> procedimenti = new ArrayList<Procedimenti>();
            if (pratica.getProcedimentiList() != null && pratica.getProcedimentiList().size() > 0) {
                for (ProcedimentoDTO procedimento : pratica.getProcedimentiList()) {
                    Procedimenti proc = procedimentiService.getProcedimento(procedimento);
                    procedimenti.add(proc);
                }
            }
            GestionePratica gp = pluginService.getGestionePratica(ente.getIdEnte());
            Procedimenti procedimentoDiRiferimento = gp.getProcedimentoRiferimento(procedimenti);
            return procedimentoDiRiferimento.getIdProc();
        }
    }

    @Override
    public PraticaDTO serializePratica(PraticheProtocollo pratica, it.wego.cross.xml.Pratica xml) throws Exception {
        PraticaDTO dto = new PraticaDTO();
        if (xml.getIdEnte() != null) {
            dto.setIdEnte(xml.getIdEnte().intValue());
        }
        if (xml.getCodEnte() != null) {
            dto.setCodEnte(xml.getCodEnte());
        }
        if (!Utils.e(xml.getCodCatastaleComune())) {
            String codiceCatastale = xml.getCodCatastaleComune();
            ComuneDTO comuneSelezionato = comuniService.findComuneByCodCatastale(codiceCatastale);
            dto.setComune(comuneSelezionato);
        }
        dto.setDataRicezione(pratica.getIdStaging().getDataRicezione());
        dto.setIdentificativoPratica(pratica.getIdentificativoPratica());
        dto.setOggettoPratica(pratica.getOggetto());
        dto.setProtocollo(pratica.getNProtocollo());
        dto.setStato(pratica.getStato());
        if (xml.getNotifica() != null) {
            RecapitoDTO notifica = RecapitiSerializer.serialize(xml.getNotifica());
            dto.setNotifica(notifica);
        }
        if (xml.getProcedimenti() != null && xml.getProcedimenti().getProcedimento() != null
                && xml.getProcedimenti().getProcedimento().size() > 0) {
            List<ProcedimentoDTO> procedimenti = new ArrayList<ProcedimentoDTO>();
            for (it.wego.cross.xml.Procedimento p : xml.getProcedimenti().getProcedimento()) {
                ProcedimentoDTO procedimento = procedimentiService.serializeProcedimento(p);
                procedimenti.add(procedimento);
            }
            dto.setProcedimentiList(procedimenti);
        }
        return dto;
    }

    @Override
    public Pratica serializePratica(PraticheProtocollo praticaProtocollo, Staging staging, it.wego.cross.xml.Pratica praticaCross) throws Exception {
        //Salvo la pratica
        Pratica praticaDaSalvare = new Pratica();
        Log.WS.info("Identificativo pratica: " + praticaProtocollo.getIdentificativoPratica());
        praticaDaSalvare.setIdentificativoPratica(praticaProtocollo.getIdentificativoPratica());
        Log.WS.info("Fascicolo: " + praticaProtocollo.getNFascicolo());
        praticaDaSalvare.setProtocollo(praticaProtocollo.getNFascicolo());
        Log.WS.info("Registro: " + praticaProtocollo.getCodRegistro());
        praticaDaSalvare.setCodRegistro(praticaProtocollo.getCodRegistro());
        Log.WS.info("Data fascicolo: " + Utils.convertDataToString(praticaProtocollo.getDataFascicolo()));
        praticaDaSalvare.setDataProtocollazione(praticaProtocollo.getDataFascicolo());
        Log.WS.info("Anno riferimento: " + praticaProtocollo.getAnnoFascicolo());
        praticaDaSalvare.setAnnoRiferimento(praticaProtocollo.getAnnoFascicolo());
        Log.WS.info("Oggetto: " + praticaProtocollo.getOggetto());
        praticaDaSalvare.setOggettoPratica(praticaProtocollo.getOggetto());
        Log.WS.info("Data ricezione: " + Utils.dateTimeItalianFormat(praticaProtocollo.getDataRicezione()));
//        praticaDaSalvare.setDataRicezione(praticaProtocollo.getDataProtocollazione());
        praticaDaSalvare.setDataRicezione(praticaProtocollo.getDataRicezione());
        Log.WS.info("Id staging: " + staging.getIdStaging());
        praticaDaSalvare.setIdStaging(staging);
        //Comune
        LkComuni comune = comuniService.findLkComuneByCodCatastale(praticaCross.getCodCatastaleComune());
        Log.WS.info("Id Comune: " + comune.getIdComune());
        praticaDaSalvare.setIdComune(comune);
        //SUAP
        Enti ente = entiService.findByIdEnte(praticaCross.getIdEnte().intValue());
        Log.WS.info("ID ente: " + ente.getIdEnte());
        Procedimenti procedimentoSuap = procedimentiService.findProcedimentoByIdProc(Integer.valueOf(praticaCross.getIdProcedimentoSuap()));
        Log.WS.info("ID procedimento: " + procedimentoSuap.getIdProc());
        ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti(ente.getIdEnte(), procedimentoSuap.getIdProc());
        if (Strings.isNullOrEmpty(praticaDaSalvare.getResponsabileProcedimento())) {
            praticaDaSalvare.setResponsabileProcedimento(pe.getResponsabileProcedimento());
        }
        praticaDaSalvare.setIdProcEnte(pe);
//        praticaDaSalvare.getIdProcEnte().setIdEnte(ente);        
//        praticaDaSalvare.getIdProcEnte().setIdProc(procedimentoSuap);
        Processi processo = workFlowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        Log.WS.info("ID processo: " + processo.getIdProcesso());
        praticaDaSalvare.setIdProcesso(processo);
        return praticaDaSalvare;
    }

    @Override
    public it.wego.cross.xml.Recapito getNotifica(PraticaDTO pratica) {
        if (pratica.getNotifica() != null
                && ((!Utils.e(pratica.getNotifica().getDescComune()) && !Utils.e(pratica.getNotifica().getIndirizzo()))
                || (!Utils.e(pratica.getNotifica().getEmail())
                || (!Utils.e(pratica.getNotifica().getPec()))))) {
            RecapitoDTO notifica = pratica.getNotifica();
            LkTipoIndirizzo tipoNotifica = lookupService.findByCodTipoIndirizzo("NOT");
            if (notifica.getIdComune() != null) {
                LkComuni comune = lookupService.findComuneById(notifica.getIdComune());
                if (comune.getIdProvincia() != null) {
                    notifica.setIdProvincia(comune.getIdProvincia().getIdProvincie());
                    notifica.setDescProvincia(comune.getIdProvincia().getDescrizione());
                }
                if (comune.getIdStato() != null) {
                    notifica.setIdStato(comune.getIdStato().getIdStato());
                    notifica.setDescStato(comune.getIdStato().getDescrizione());
                }
            }
            it.wego.cross.xml.Recapito recapito = RecapitiSerializer.serialize(notifica, 1, tipoNotifica);
            return recapito;
        } else {
            return null;
        }
    }

    @Override
    public List<LkStatoPratica> getLookupsStatoPratica(String statoPratica) {
        List<LkStatoPratica> lookup = new ArrayList<LkStatoPratica>();
        lookup.add(findLookupStatoPratica(statoPratica));
        return lookup;
    }

    @Override
    public List<PraticaDTO> getPraticheNonProtocollate(Filter filter) {
        List<Pratica> praticheNonProtocollate = praticaDao.findPraticheNonProtocollate(filter);
        List<PraticaDTO> pratiche = new ArrayList<PraticaDTO>();
        if (praticheNonProtocollate != null && !praticheNonProtocollate.isEmpty()) {
            for (Pratica pratica : praticheNonProtocollate) {
                PraticaDTO dto = praticheSerializer.serialize(pratica);
                pratiche.add(dto);
            }
        }
        return pratiche;
    }

    @Override
    public Long countPraticheNonProtocollate() {
        Long count = praticaDao.countPraticheNonProtocollate();
        return count;
    }

    @Override
    public void cancellaPratica(String identificativoPratica) throws Exception {
        praticaDao.cancellaPratica(identificativoPratica);
    }

    @Override
    public void aggiornaPratica(Pratica pratica) throws Exception {
        praticaDao.update(pratica);
    }

    @Override
    public Pratica findByFascicoloAnno(String nFascicolo, Integer annoFascicolo, Integer idEnte) {
        return praticaDao.findByFascicoloAnno(nFascicolo, annoFascicolo, idEnte);
    }

    @Override
    public void startCommunicationProcess(Pratica pratica, PraticheEventi praticaEvento, EventoBean eventoBean) throws Exception {
//    public void startCommunicationProcess(Pratica pratica, PraticheEventi praticaEvento) throws Exception {
        Map<String, Object> variables = workflowAction.getVariabiliPratica(eventoBean, praticaEvento);
        //Visualizzo il task sulla tasklist
        variables.put(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK);
//        variables.put(WorkflowVariables.POTENZIALI_PROPRIETARI, workflowAction.getPotentialOwners(praticaEvento, true));
        runtimeService.startProcessInstanceByKey("cross_communication_process", variables);
    }

    @Override
    public void deletePraticaAnagrafica(PraticaAnagrafica pa) throws Exception {
        praticaDao.deletePraticaAnagrafica(pa);
    }

    @Override
    public void saveProcessoEvento(PraticheEventi evento) throws Exception {
        praticaDao.insert(evento);
    }

    @Override
    public void saveAnagraficaEvento(PraticheEventiAnagrafiche pea) throws Exception {
        praticaDao.insert(pea);
    }

    @Override
    public void savePraticaAnagrafica(PraticaAnagrafica pa) throws Exception {
        praticaDao.insert(pa);
    }

    @Override
    public void updatePraticaAnagrafica(PraticaAnagrafica pa) throws Exception {
        praticaDao.update(pa);
    }

    @Override
    public DatiCatastali findDatiCatastali(Integer idImmobile) {
        DatiCatastali dati = praticaDao.findDatiCatastali(idImmobile);
        return dati;
    }

    @Override
    public void eliminaDatiCatastali(DatiCatastali datoCatastale) {
        praticaDao.eliminaDatiCatastali(datoCatastale);
    }

    @Override
    public void saveDatiCatastali(DatiCatastali datiDB) throws Exception {
        praticaDao.insert(datiDB);
    }

    @Override
    public void updateDatiCatastali(DatiCatastali datiDB) throws Exception {
        praticaDao.update(datiDB);
    }

    @Override
    public IndirizziIntervento findIndirizziInterventoById(Integer idIndirizzoIntervento) {
        IndirizziIntervento indirizziIntervento = praticaDao.findIndirizziInterventoById(idIndirizzoIntervento);
        return indirizziIntervento;
    }

    @Override
    public void eliminaIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception {
        praticaDao.eliminaIndirizziIntervento(datiDB);
    }

    @Override
    public void saveIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception {
        praticaDao.insert(datiDB);
    }

    @Override
    public void updateIndirizzoIntervento(IndirizziIntervento datiDB) throws Exception {
        praticaDao.update(datiDB);
    }

    @Override
    public void salvaPraticaProcedimento(PraticaProcedimenti praticaProcedimenti) throws Exception {
        praticaDao.insert(praticaProcedimenti);
        usefulService.flush();
        usefulService.refresh(praticaProcedimenti);
    }

    @Override
    public Pratica findPraticaWithPraticaPadreAndEnteAndProcedimento(Integer idPratica, Integer idEnte, Integer idProcedimento) {
        return praticaDao.findPraticaWithPraticaPadreAndEnteAndProcedimento(idPratica, idEnte, idProcedimento);
    }

    @Override
    public PraticheEventi getPraticaEvento(Integer idPraticaEvento) {
        return praticaDao.getDettaglioPraticaEvento(idPraticaEvento);
    }

    @Override
    public void updatePraticaEvento(PraticheEventi evento) throws Exception {
        praticaDao.update(evento);
    }

    @Override
    public void startSendMailProcess(Email email) throws Exception {
        PraticheEventi praticaEvento = email.getIdPraticaEvento();
        Map<String, Object> variables = workflowAction.getVariabiliPratica(praticaEvento);
        variables.put(Workflow.TIPO_TASK, Workflow.TIPO_TASK_TASK);
        variables.put(Workflow.ID_EMAIL, email.getIdEmail());
        runtimeService.startProcessInstanceByKey("cross_send_mail_process", variables);
    }

    @Override
    public void aggiornaResponsabileProcedimentoSuPratiche(ProcedimentiEnti procedimentiEnti) throws Exception {
        praticaDao.aggiornaResponsabileProcedimentoSuPratiche(procedimentiEnti);
    }

    @Override
    public Scadenze findScadenzaById(Integer idScadenza) {
        return praticaDao.findScadenzeByIdScadenza(idScadenza);
    }

    @Override
    public List<String> popolaListaAnni() {
        List<String> anni = new ArrayList();
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        for (Integer anno = ANNO_RIFERIMENTO_PARTENZA; anno <= year; anno++) {
            anni.add(anno.toString());
        }
        return Lists.reverse(anni);
    }

    private List<Scadenze> sortScadenze(List<Scadenze> scadenze) {
        Ordering<Scadenze> primary = Ordering.natural().onResultOf(new Function<Scadenze, String>() {

            @Override
            public String apply(Scadenze input) {
                return input.getIdStato().getGrpStatoScadenza();
            }

        });
        Ordering<Scadenze> secondary = Ordering.natural()
                .onResultOf(new Function<Scadenze, Long>() {

                    @Override
                    public Long apply(Scadenze input) {
                        return input.getDataFineScadenza().getTime();
                    }
                });
        Ordering<Scadenze> compound = primary.compound(secondary);

        Collections.sort(scadenze, compound);
        return scadenze;
    }

	@Override
	public List<EstrazioniCilaDTO> findPraticheCILA(Filter filter) throws Exception {
		List<EstrazioniCilaDTO> pratiche = new ArrayList<EstrazioniCilaDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.findPraticheCILA(filter);
		return pratiche;
	}
	
	@Override
	public List<EstrazioniCilaDTO> listPraticheCILA(Filter filter) throws Exception {
		List<EstrazioniCilaDTO> pratiche = new ArrayList<EstrazioniCilaDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.listPraticheCILA(filter);
		return pratiche ;
	}

	@Override
	public List<EstrazioniSciaDTO> findPraticheSCIA(Filter filter) throws Exception {
		List<EstrazioniSciaDTO> pratiche = new ArrayList<EstrazioniSciaDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.findPraticheSCIA(filter);
		return pratiche;
	}
		
	@Override
	public List<EstrazioniSciaDTO> listPraticheSCIA(Filter filter) throws Exception {
		List<EstrazioniSciaDTO> pratiche = new ArrayList<EstrazioniSciaDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.listPraticheSCIA(filter);
		return pratiche;
	}
	
	@Override
	public List<EstrazioniPdcDTO> findPratichePDC(Filter filter) throws Exception {
		List<EstrazioniPdcDTO> pratiche = new ArrayList<EstrazioniPdcDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.findPratichePDC(filter);
		return pratiche;
	}
		
	@Override
	public List<EstrazioniPdcDTO> listPratichePDC(Filter filter) throws Exception {
		List<EstrazioniPdcDTO> pratiche = new ArrayList<EstrazioniPdcDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.listPratichePDC(filter);
		return pratiche;
	}

	@Override
	public List<EstrazioniAgibDTO> findPraticheAGIB(Filter filter) throws Exception {
		List<EstrazioniAgibDTO> pratiche = new ArrayList<EstrazioniAgibDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.findPraticheAGIB(filter);
		return pratiche;
	}

	@Override
	public List<EstrazioniAgibDTO> listPraticheAGIB(Filter filter) throws Exception {
		List<EstrazioniAgibDTO> pratiche = new ArrayList<EstrazioniAgibDTO>();
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.listPraticheAGIB(filter); 
		return pratiche;
	}

	@Override
	public List<ProcessiEventi> getProcessiEventiChiusura(Processi idProcesso) throws Exception {
		List<ProcessiEventi> pe = new ArrayList<ProcessiEventi>();
		pe = praticaDao.getProcessiEventiChiusura(idProcesso.getIdProcesso());
		return pe;
	}

	@Override
	public void aggiornaPraticaArchivio(Pratica pratica) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public PraticheEventiAllegati findPraticheEventiAllegatiByIdAllegato(Integer idAllegato) {
        return praticaDao.findPraticheEventiAllegatiByIdAllegato(idAllegato);
    }
	
	@Override
	public List<EstrazioniCilaDTO> listPraticheCILAToDo(Filter filter) throws Exception {
//		Date data = filter.getDataInizio();
//		filter.setDataFine(data);
		List<EstrazioniCilaDTO> pratiche = new ArrayList<EstrazioniCilaDTO>();
		List<EstrazioniCilaDTO> praticheRicevute = new ArrayList<EstrazioniCilaDTO>();
		List<EstrazioniCilaDTO> praticheResult = new ArrayList<EstrazioniCilaDTO>();
		int lavorate = 0;
		if(filter.getEnteSelezionato() != null)
			pratiche = praticaDao.listPraticheCILA(filter);
		if(!pratiche.isEmpty() && pratiche !=null) {
			int totali = pratiche.size();
			int venti = (int) Math.ceil((double) totali * 0.2);
			
			for (EstrazioniCilaDTO temp : pratiche) {
				if(!temp.getStato().equalsIgnoreCase("Ricevuta")) {
					lavorate++;
				}
				else {
					praticheRicevute.add(temp);
				}
			}
			
			if(lavorate < venti) {
				int countpraticheResult =  venti-lavorate;
				Collections.shuffle(praticheRicevute);
				for(int i=0; i<countpraticheResult; i++) {
					praticheResult.add(praticheRicevute.get(i));
				}
			}
		}
		return praticheResult ;
	}

	@Override
	public List<PraticheEventi> findPraticheEventiDaRiprotocollare() {
		// TODO Auto-generated method stub
		List<PraticheEventi> result = new ArrayList<PraticheEventi>();
		result = praticaDao.listPraticheEventi();
		
		return result;
	}
	
	
	
	
}
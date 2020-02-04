/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.Email;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaAnagraficaPK;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticaProcedimentiPK;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAnagrafiche;
import it.wego.cross.entity.PraticheEventiAnagrafichePK;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.DatiCatastaliSerializer;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.DatiCatastaliService;
import it.wego.cross.service.IndirizziInterventoService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.StagingService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Giuseppe
 */
@Component
public class SystemEventAction {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private AnagraficaAction anagraficaAction;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ProcessiService processiService;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private StagingService stagingService;
    @Autowired
    private DatiCatastaliService datiCatastaliService;
    @Autowired
    private DatiCatastaliSerializer datiCatastaliSerializer;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private IndirizziInterventoService indirizziInterventoService;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MessageSource messageSource;

    public static final String COLLEGA_ANAGRAFICA = "ANAG";
    public static final String RIMUOVI_ANAGRAFICA = "RANAG";
    public static final String COLLEGA_ENDOPROCEDIMENTO = "ENDO";
    public static final String RIMUOVI_ENDOPROCEDIMENTO = "RENDO";
    public static final String AGGIUNGI_DATO_CATASTALE = "ADDCATASTALE";
    public static final String MODIFICA_DATO_CATASTALE = "MODCATASTALE";
    public static final String RIMUOVI_DATO_CATASTALE = "DELCATASTALE";
    public static final String AGGIUNGI_INDIRIZZO_INTERVENTO = "ADDINDINT";
    public static final String MODIFICA_INDIRIZZO_INTERVENTO = "MODINDINT";
    public static final String RIMUOVI_INDIRIZZO_INTERVENTO = "DELINDINT";
    public static final String RIASSEGNA_PRATICA = "REASPRAT";
    public static final String ASSEGNA_PRATICA = "ASPRAT";
    public static final String MARCA_MAIL_COME_CONSEGNATA = "SENTMAIL";
    public static final String MAIL_REINVIATA = "RESENTMAIL";
    public static final String COLLEGA_PRATICA = "CPRAT";
    public static final String SCOLLEGA_PRATICA = "SPRAT";

    public static final ImmutableMap<String, String> SYSTEM_EVENTS
            = new ImmutableMap.Builder<String, String>()
            .put(COLLEGA_ANAGRAFICA, "Collegamento nuova anagrafica")
            .put(RIMUOVI_ANAGRAFICA, "Rimozione anagrafica")
            .put(COLLEGA_ENDOPROCEDIMENTO, "Collegamento nuovo endoprocedimento")
            .put(RIMUOVI_ENDOPROCEDIMENTO, "Rimozione endoprocedimento")
            .put(AGGIUNGI_DATO_CATASTALE, "Inserimento nuovo dato catastale")
            .put(RIMUOVI_DATO_CATASTALE, "Rimozione dato catastale")
            .put(AGGIUNGI_INDIRIZZO_INTERVENTO, "Inserimento nuovo indirizzo intervento")
            .put(RIMUOVI_INDIRIZZO_INTERVENTO, "Rimozione indirizzo intervento")
            .put(MODIFICA_DATO_CATASTALE, "Modifica dato catastale")
            .put(MODIFICA_INDIRIZZO_INTERVENTO, "Modifica indirizzo intervento")
            .put(RIASSEGNA_PRATICA, "Riassegnazione pratica")
            .put(ASSEGNA_PRATICA, "Assegnazione pratica")
            .put(MARCA_MAIL_COME_CONSEGNATA, "Email marcata forzatamente come consegnata")
            .put(MAIL_REINVIATA, "Email rispedita")
            .put(COLLEGA_PRATICA, "Collegamento pratica")
            .put(SCOLLEGA_PRATICA, "Scollegamento pratica")
            .build();

    @Transactional(rollbackFor = Exception.class)
    public void scollegaAnagraficaTransactional(PraticaAnagrafica pa, Utente utenteConnesso) {
        scollegaAnagrafica(pa, utenteConnesso);
    }

    public void scollegaAnagrafica(PraticaAnagrafica pa, Utente utenteConnesso) {
        try {
            Integer idPratica = pa.getPraticaAnagraficaPK().getIdPratica();
            Pratica pratica = praticheService.getPratica(idPratica);
            Integer idAnagrafica = pa.getPraticaAnagraficaPK().getIdAnagrafica();
            praticheService.deletePraticaAnagrafica(pa);
            PraticheEventi evento = salvaEventoGenerico(pratica, RIMUOVI_ANAGRAFICA, utenteConnesso);
            usefulService.flush();
            PraticheEventiAnagrafiche pea = new PraticheEventiAnagrafiche();
            PraticheEventiAnagrafichePK peaKey = new PraticheEventiAnagrafichePK();
            peaKey.setIdAnagrafica(idAnagrafica);
            peaKey.setIdPraticaEvento(evento.getIdPraticaEvento());
            pea.setPraticheEventiAnagrafichePK(peaKey);
            praticheService.saveAnagraficaEvento(pea);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore scollegando l'anagrafica", ex);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void inserisciCollegamentoPraticaAnagrafica(AnagraficaDaCollegareDTO anagrafica, Integer idPratica, Utente utenteConnesso, boolean updateStaging) throws Exception {
        inserisciCollegamentoPraticaAnagraficaNoTransactional(anagrafica, idPratica, utenteConnesso, updateStaging);
    }

    //Non è transactional perché viene chiamato solo da aggiornaAnagraficaStgTransactional in AnagraficaAction
    public void inserisciCollegamentoPraticaAnagraficaNoTransactional(AnagraficaDaCollegareDTO anagrafica, Integer idPratica, Utente utenteConnesso, boolean updateStaging) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
        LkTipoQualifica qualifica = null;
        if (anagrafica.getQualificaRichiedente() != null && anagrafica.getQualificaRichiedente() > 0) {
            qualifica = lookupService.findLkTipoQualifica(anagrafica.getQualificaRichiedente());
        }
        if (anagrafica.getQualificaTecnico() != null && anagrafica.getQualificaTecnico() > 0) {
            qualifica = lookupService.findLkTipoQualifica(anagrafica.getQualificaTecnico());
        }
        PraticaAnagrafica pa = new PraticaAnagrafica();
        PraticaAnagraficaPK key = new PraticaAnagraficaPK();
        key.setIdAnagrafica(anagrafica.getIdAnagrafica());
        key.setIdPratica(idPratica);
        key.setIdTipoRuolo(anagrafica.getRuolo());
        pa.setPraticaAnagraficaPK(key);
        pa.setDataInizioValidita(new Date());
        pa.setIdTipoQualifica(qualifica);
        if (anagrafica.getDittaIndividuale() != null && anagrafica.getDittaIndividuale().equalsIgnoreCase("S")) {
            pa.setFlgDittaIndividuale("S");
        } else {
            pa.setFlgDittaIndividuale("N");
        }
        praticheService.savePraticaAnagrafica(pa);
        PraticheEventi evento = salvaEventoGenerico(pratica, COLLEGA_ANAGRAFICA, utenteConnesso);
        usefulService.flush();
        PraticheEventiAnagrafiche pea = new PraticheEventiAnagrafiche();
        PraticheEventiAnagrafichePK peaKey = new PraticheEventiAnagrafichePK();
        peaKey.setIdAnagrafica(anagrafica.getIdAnagrafica());
        peaKey.setIdPraticaEvento(evento.getIdPraticaEvento());
        pea.setPraticheEventiAnagrafichePK(peaKey);
        praticheService.saveAnagraficaEvento(pea);
        if (updateStaging) {
            Anagrafica anag = anagraficheService.findAnagraficaById(anagrafica.getIdAnagrafica());
            AnagraficaDTO dto = AnagraficheSerializer.serializeAnagrafica(anag);
            Integer idRuolo = anagrafica.getRuolo();
            LkTipoRuolo tipoRuolo = lookupService.findLkTipoRuoloById(idRuolo);
            dto.setCodTipoRuolo(tipoRuolo.getCodRuolo());
            dto.setDesTipoRuolo(tipoRuolo.getDescrizione());
            dto.setIdTipoRuolo(tipoRuolo.getIdTipoRuolo());
            dto.setIdPratica(idPratica);
            dto.setDaRubrica("S");
            anagraficaAction.aggiungiAnagraficadaXML(dto);
            anagraficaAction.aggiornaAnagraficaStg(dto, utenteConnesso);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public PraticaProcedimenti gestisciEndoprocedimenti(Integer idPratica, Integer idProcedimentoEnte, String operation, Utente utenteConnesso) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
//        ProcedimentiEnti procedimentoEnte = procedimentiService.findProcedimentiEntyByKey(idProcedimentoEnte);
        ProcedimentiEnti procedimentoEnte = procedimentiService.requireProcedimentoEnte(idProcedimentoEnte, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
        //todo: sostituire con una query
        PraticaProcedimenti praticaProcedimentoToDelete = null;
        for (PraticaProcedimenti praticaProcedimento : pratica.getPraticaProcedimentiList()) {
            if (praticaProcedimento.getEnti().equals(procedimentoEnte.getIdEnte()) && praticaProcedimento.getProcedimenti().equals(procedimentoEnte.getIdProc())) {
                if ("ADD".equalsIgnoreCase(operation)) {
                    throw new CrossException("Il procedimento è già associato alla pratica per l'ente " + procedimentoEnte.getIdEnte().getDescrizione());
                } else {
                    praticaProcedimentoToDelete = praticaProcedimento;
                }
            }
        }
        String descProc = procedimentiService.getDescrizioneProcedimentoLingua(1, procedimentoEnte.getIdProc().getIdProc());
        if ("DELETE".equalsIgnoreCase(operation)) {
            if (praticaProcedimentoToDelete == null) {
                throw new CrossException("Impossibile eliminare il procedimento dalla pratica corrente.");
            }
            pratica.getPraticaProcedimentiList().remove(praticaProcedimentoToDelete);
            procedimentiService.eliminaPraticaProcedimento(praticaProcedimentoToDelete);
            String msg = messageSource.getMessage("pratica.endoprocedimenti.rimuovi.note", new Object[]{descProc}, Locale.getDefault());
            salvaEventoGenerico(pratica, RIMUOVI_ENDOPROCEDIMENTO, msg, utenteConnesso);
            return praticaProcedimentoToDelete;
        } else {
            PraticaProcedimenti praticaProcedimento = new PraticaProcedimenti(idPratica, procedimentoEnte.getIdProc().getIdProc(), procedimentoEnte.getIdEnte().getIdEnte());
            praticaProcedimento.setEnti(procedimentoEnte.getIdEnte());
            praticaProcedimento.setProcedimenti(procedimentoEnte.getIdProc());
            pratica.getPraticaProcedimentiList().add(praticaProcedimento);
            String msg = messageSource.getMessage("pratica.endoprocedimenti.aggiungi.note", new Object[]{descProc}, Locale.getDefault());
            salvaEventoGenerico(pratica, COLLEGA_ENDOPROCEDIMENTO, msg, utenteConnesso);
            return praticaProcedimento;
        }
    }

    private PraticheEventi salvaEventoGenerico(Pratica pratica, String codEvento, Utente utenteConnesso) throws Exception {
        return salvaEventoGenerico(pratica, codEvento, null, utenteConnesso);
    }

    private PraticheEventi salvaEventoGenerico(Pratica pratica, String codEvento, String note, Utente utenteConnesso) throws Exception {
        PraticheEventi evento = new PraticheEventi();
        evento.setIdPratica(pratica);
        String desEvento = SYSTEM_EVENTS.get(codEvento);
        ProcessiEventi evt = processiService.requireEventoDiSistema(codEvento, desEvento, pratica.getIdProcesso());
        evento.setIdEvento(evt);
        evento.setDataEvento(new Date());
        evento.setIdUtente(utenteConnesso);
        evento.setVisibilitaCross("S");
        evento.setVisibilitaUtente("N");
        evento.setDescrizioneEvento(desEvento);
        evento.setNote(note);
        praticheService.saveProcessoEvento(evento);
        return evento;
    }

    @Transactional(rollbackFor = Exception.class)
    public DatiCatastaliDTO eliminaDatiCatastale(DatiCatastaliDTO datiCatastaliDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.entity.DatiCatastali datiDB = null;
        if (datiCatastaliDTO.getIdImmobile() != null) {
            datiDB = praticheService.findDatiCatastali(datiCatastaliDTO.getIdImmobile());
            if (datiDB.getIdTipoUnita() != null) {
                datiCatastaliDTO.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
            }
            praticheService.eliminaDatiCatastali(datiDB);
        }
        it.wego.cross.xml.Immobile toRemove = null;
        if (datiDB != null) {
            toRemove = datiCatastaliService.trovaStrutturaXmlPerIdImmobile(datiDB, praticaXML.getDatiCatastali());
        }
        if (toRemove == null) {
            toRemove = datiCatastaliService.trovaStrutturaXmlPerCounter(datiCatastaliDTO, praticaXML.getDatiCatastali());
        }
        praticaXML.getDatiCatastali().getImmobile().remove(toRemove);
        Staging stg = praticaEnitity.getIdStaging();
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXML, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        stagingService.update(stg);

        String msg = messageSource.getMessage("pratica.datocatastale.rimuovi.note", new Object[]{
            Strings.nullToEmpty(datiCatastaliDTO.getDesTipoUnita()),
            Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
            Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
            Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
            Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
            Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
        },
                Locale.getDefault());
        salvaEventoGenerico(praticaEnitity, RIMUOVI_DATO_CATASTALE, msg, utenteConnesso);
        return datiCatastaliDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public DatiCatastaliDTO eliminaDatiCatastaleBancaDati(DatiCatastaliDTO datiCatastaliDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
        DatiCatastali datiDB = praticheService.findDatiCatastali(datiCatastaliDTO.getIdImmobile());
        if (datiDB.getIdTipoUnita() != null) {
            datiCatastaliDTO.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
        }
        praticheService.eliminaDatiCatastali(datiDB);
        String msg = messageSource.getMessage("pratica.datocatastale.rimuovi.note", new Object[]{
            Strings.nullToEmpty(datiCatastaliDTO.getDesTipoUnita()),
            Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
            Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
            Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
            Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
            Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
        },
                Locale.getDefault());
        salvaEventoGenerico(praticaEnitity, RIMUOVI_DATO_CATASTALE, msg, utenteConnesso);
        return datiCatastaliDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public DatiCatastaliDTO salvaDatoCatastale(DatiCatastaliDTO datiCatastaliDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.entity.DatiCatastali datiDB;
        boolean isUpdate = false;
        DatiCatastaliDTO datiCatastaliOrigine = new DatiCatastaliDTO();
        if (datiCatastaliDTO.getIdImmobile() == null) {
            // l'immobile è nuovo
            datiDB = new it.wego.cross.entity.DatiCatastali();
            datiCatastaliSerializer.serialize(datiCatastaliDTO, datiDB);
            datiDB.setIdPratica(praticaEnitity);
            if (datiCatastaliDTO.getIdTipoParticella() != null) {
                LkTipoParticella tipoParticella = lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella());
                datiDB.setIdTipoParticella(tipoParticella);
                datiCatastaliDTO.setDesTipoParticella(tipoParticella.getDescrizione());
//                datiDB.setIdTipoParticella(lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella()));
            }
            if (datiCatastaliDTO.getIdTipoSistemaCatastale() != null) {
//                datiDB.setIdTipoSistemaCatastale(lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale()));
                LkTipoSistemaCatastale tipoSistemaCatastale = lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale());
                datiDB.setIdTipoSistemaCatastale(tipoSistemaCatastale);
                datiCatastaliDTO.setDesSistemaCatastale(tipoSistemaCatastale.getDescrizione());
            }
            if (datiCatastaliDTO.getIdTipoUnita() != null) {
//                datiDB.setIdTipoUnita(lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita()));
                LkTipoUnita tipoUnita = lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita());
                datiDB.setIdTipoUnita(tipoUnita);
                datiCatastaliDTO.setDesTipoUnita(tipoUnita.getDescrizione());
            }
            praticheService.saveDatiCatastali(datiDB);
            usefulService.flush();
            datiCatastaliDTO.setIdImmobile(datiDB.getIdImmobile());
        } else {
            // l'immobile già esiste
            datiDB = praticheService.findDatiCatastali(datiCatastaliDTO.getIdImmobile());
            datiCatastaliOrigine = mapper.map(datiDB, DatiCatastaliDTO.class);
            if (datiDB.getIdTipoUnita() != null) {
                datiCatastaliOrigine.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
            }
            datiCatastaliSerializer.serialize(datiCatastaliDTO, datiDB);
            if (datiCatastaliDTO.getIdTipoParticella() != null) {
//                datiDB.setIdTipoParticella(lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella()));
                LkTipoParticella tipoParticella = lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella());
                datiDB.setIdTipoParticella(tipoParticella);
                datiCatastaliDTO.setDesTipoParticella(tipoParticella.getDescrizione());
            }
            if (datiCatastaliDTO.getIdTipoSistemaCatastale() != null) {
//                datiDB.setIdTipoSistemaCatastale(lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale()));
                LkTipoSistemaCatastale tipoSistemaCatastale = lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale());
                datiDB.setIdTipoSistemaCatastale(tipoSistemaCatastale);
                datiCatastaliDTO.setDesSistemaCatastale(tipoSistemaCatastale.getDescrizione());
            }
            if (datiCatastaliDTO.getIdTipoUnita() != null) {
//                datiDB.setIdTipoUnita(lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita()));
                LkTipoUnita tipoUnita = lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita());
                datiDB.setIdTipoUnita(tipoUnita);
                datiCatastaliDTO.setDesTipoUnita(tipoUnita.getDescrizione());
            }
            praticheService.updateDatiCatastali(datiDB);
            isUpdate = true;
        }
        // aggiorna l'xml
        if (praticaXML.getDatiCatastali() == null || praticaXML.getDatiCatastali().getImmobile().isEmpty()) {
            // struttura xml vuota
            Log.APP.info("Struttura dati catastali assente. La creo ...");
            Log.APP.info("Aggiungo il nuovo dato catastale nell'xml di staging");
            it.wego.cross.xml.Immobile datiXML = new it.wego.cross.xml.Immobile();
            Log.APP.info("Creo l'oggetto per l'xml");
            datiCatastaliSerializer.serialize(datiDB, datiXML);
            // datiXML.setCounter(Utils.bi(datiCatastaliDTO.getCounter()));
            praticaXML.setDatiCatastali(new it.wego.cross.xml.DatiCatastali());
            Log.APP.info("Aggiungo il dato catastale nell'xml");
            praticaXML.getDatiCatastali().getImmobile().add(datiXML);
            Log.APP.info("Aggiunto l'immobile " + datiXML.getIdImmobile());
        } else {
            // struttura xml già presente
            // cerco la struttura xml per idimmobile
            it.wego.cross.xml.Immobile datiXML = datiCatastaliService.trovaStrutturaXmlPerIdImmobile(datiDB, praticaXML.getDatiCatastali());
            if (datiXML != null) {
                // trovato per idImmobile, loaggiorno
                datiCatastaliSerializer.serialize(datiDB, datiXML);
                datiXML.setConfermato("true");
                Log.APP.info("Modifico l'immobile per idImmobile" + datiXML.getIdImmobile());
            } else {
                // non trovato per idImmobile, lo cerco per counter
                datiXML = datiCatastaliService.trovaStrutturaXmlPerCounter(datiCatastaliDTO, praticaXML.getDatiCatastali());
                if (datiXML != null) {
                    // trovato per counter
                    datiCatastaliSerializer.serialize(datiDB, datiXML);
                    datiXML.setConfermato("true");
                    Log.APP.info("Modifico l'immobile per counter" + datiXML.getIdImmobile());
                } else {
                    // non presente in struttura ne per idimmobile che per counter
                    datiXML = new it.wego.cross.xml.Immobile();
                    datiCatastaliSerializer.serialize(datiDB, datiXML);
                    datiXML.setConfermato("true");
                    praticaXML.getDatiCatastali().getImmobile().add(datiXML);
                    Log.APP.info("Aggiunto l'immobile " + datiXML.getIdImmobile());
                }
            }
        }
        Staging stg = stagingService.findById(praticaEnitity.getIdStaging().getIdStaging());
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXML, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        stagingService.update(stg);
        if (datiDB.getIdTipoSistemaCatastale() != null) {
            datiCatastaliDTO.setDesSistemaCatastale(datiDB.getIdTipoSistemaCatastale().getDescrizione());
        }
        if (datiDB.getIdTipoUnita() != null) {
            datiCatastaliDTO.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
        }
        if (datiDB.getIdTipoParticella() != null) {
            datiCatastaliDTO.setDesTipoParticella(datiDB.getIdTipoParticella().getDescrizione());
        }
        datiCatastaliDTO.setConfermato("true");
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        datiCatastaliDTO.setUrlCatasto(gp.getUrlCatasto(datiDB, praticaEnitity));
        if (isUpdate) {
            String msg = messageSource.getMessage("pratica.datocatastale.modifica.note", new Object[]{
                Strings.nullToEmpty(datiCatastaliOrigine.getDesTipoUnita()),
                Strings.nullToEmpty(datiCatastaliOrigine.getSezione()),
                Strings.nullToEmpty(datiCatastaliOrigine.getFoglio()),
                Strings.nullToEmpty(datiCatastaliOrigine.getMappale()),
                Strings.nullToEmpty(datiCatastaliOrigine.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliOrigine.getCategoria()),
                Strings.nullToEmpty(datiCatastaliDTO.getDesTipoUnita()),
                Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
                Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
                Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
                Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
            },
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, MODIFICA_DATO_CATASTALE, msg, utenteConnesso);
        } else {
            String msg = messageSource.getMessage("pratica.datocatastale.aggiungi.note", new Object[]{
                Strings.nullToEmpty(datiCatastaliDTO.getDesTipoUnita()),
                Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
                Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
                Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
                Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
            },
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, AGGIUNGI_DATO_CATASTALE, msg, utenteConnesso);
        }
        return datiCatastaliDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public DatiCatastaliDTO salvaDatiCatastaliBancaDati(DatiCatastaliDTO datiCatastaliDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
        it.wego.cross.entity.DatiCatastali datiDB;
        boolean isUpdate = false;
        DatiCatastaliDTO datiCatastaliOrigine = new DatiCatastaliDTO();
        if (datiCatastaliDTO.getIdImmobile() == null) {
            datiDB = new it.wego.cross.entity.DatiCatastali();
            datiCatastaliSerializer.serialize(datiCatastaliDTO, datiDB);
            datiDB.setIdPratica(praticaEnitity);
            if (datiCatastaliDTO.getIdTipoParticella() != null) {
                datiDB.setIdTipoParticella(lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella()));
            }
            if (datiCatastaliDTO.getIdTipoSistemaCatastale() != null) {
                datiDB.setIdTipoSistemaCatastale(lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale()));
            }
            if (datiCatastaliDTO.getIdTipoUnita() != null) {
                datiDB.setIdTipoUnita(lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita()));
            }
//            popolaLookUpDatiCatastali(datiDB, datiCatastaliDTO);
            praticheService.saveDatiCatastali(datiDB);
//            praticaDao.insert(datiDB);
            usefulService.flush();
            datiCatastaliDTO.setIdImmobile(datiDB.getIdImmobile());
        } else {
            datiDB = datiCatastaliService.findByIdImmobile(datiCatastaliDTO.getIdImmobile());
            datiCatastaliOrigine = mapper.map(datiDB, DatiCatastaliDTO.class);
            if (datiDB.getIdTipoUnita() != null) {
                datiCatastaliOrigine.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
            }
            datiCatastaliSerializer.serialize(datiCatastaliDTO, datiDB);
            if (datiCatastaliDTO.getIdTipoParticella() != null) {
                LkTipoParticella tipoParticella = lookupService.findTipoParticellaById(datiCatastaliDTO.getIdTipoParticella());
                datiDB.setIdTipoParticella(tipoParticella);
                datiCatastaliDTO.setDesTipoParticella(tipoParticella.getDescrizione());
            }
            if (datiCatastaliDTO.getIdTipoSistemaCatastale() != null) {
                LkTipoSistemaCatastale tipoSistemaCatastale = lookupService.findTipoSistemaCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale());
                datiDB.setIdTipoSistemaCatastale(tipoSistemaCatastale);
                datiCatastaliDTO.setDesSistemaCatastale(tipoSistemaCatastale.getDescrizione());
            }
            if (datiCatastaliDTO.getIdTipoUnita() != null) {
                LkTipoUnita tipoUnita = lookupService.findTipoUnitaById(datiCatastaliDTO.getIdTipoUnita());
                datiDB.setIdTipoUnita(tipoUnita);
                datiCatastaliDTO.setDesTipoUnita(tipoUnita.getDescrizione());
            }
            praticheService.updateDatiCatastali(datiDB);
            isUpdate = true;
        }
        datiCatastaliDTO.setConfermato("true");
        if (datiDB.getIdTipoParticella() != null) {
            datiCatastaliDTO.setDesTipoParticella(datiDB.getIdTipoParticella().getDescrizione());
        }
        if (datiDB.getIdTipoSistemaCatastale() != null) {
            datiCatastaliDTO.setDesSistemaCatastale(datiDB.getIdTipoSistemaCatastale().getDescrizione());
        }
        if (datiDB.getIdTipoUnita() != null) {
            datiCatastaliDTO.setDesTipoUnita(datiDB.getIdTipoUnita().getDescrizione());
        }
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        datiCatastaliDTO.setUrlCatasto(gp.getUrlCatasto(datiDB, praticaEnitity));
        if (isUpdate) {
            String msg = messageSource.getMessage("pratica.datocatastale.modifica.note", new Object[]{
                Strings.nullToEmpty(datiCatastaliOrigine.getDesTipoUnita()),
                Strings.nullToEmpty(datiCatastaliOrigine.getSezione()),
                Strings.nullToEmpty(datiCatastaliOrigine.getFoglio()),
                Strings.nullToEmpty(datiCatastaliOrigine.getMappale()),
                Strings.nullToEmpty(datiCatastaliOrigine.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliOrigine.getCategoria()),
                Strings.nullToEmpty(datiCatastaliDTO.getDesTipoUnita()),
                Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
                Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
                Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
                Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
            },
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, MODIFICA_DATO_CATASTALE, msg, utenteConnesso);
        } else {
            String msg = messageSource.getMessage("pratica.datocatastale.aggiungi.note", new Object[]{
                Strings.nullToEmpty(datiCatastaliDTO.getDesSistemaCatastale()),
                Strings.nullToEmpty(datiCatastaliDTO.getSezione()),
                Strings.nullToEmpty(datiCatastaliDTO.getFoglio()),
                Strings.nullToEmpty(datiCatastaliDTO.getMappale()),
                Strings.nullToEmpty(datiCatastaliDTO.getSubalterno()),
                Strings.nullToEmpty(datiCatastaliDTO.getCategoria())
            },
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, AGGIUNGI_DATO_CATASTALE, msg, utenteConnesso);
        }
        return datiCatastaliDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IndirizzoInterventoDTO eliminaIndirizziIntervento(IndirizzoInterventoDTO indirizzoInterventoDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.entity.IndirizziIntervento datiDB = null;
        it.wego.cross.xml.IndirizzoIntervento toRemove = null;
        if (indirizzoInterventoDTO.getIdIndirizzoIntervento() != null) {
            datiDB = praticheService.findIndirizziInterventoById(indirizzoInterventoDTO.getIdIndirizzoIntervento());
            praticheService.eliminaIndirizzoIntervento(datiDB);
            toRemove = indirizziInterventoService.trovaStrutturaXmlPerIdIndirizzoIntervento(datiDB, praticaXML.getIndirizziIntervento());
        }
        if (toRemove == null) {
            toRemove = indirizziInterventoService.trovaStrutturaXmlPerCounter(indirizzoInterventoDTO, praticaXML.getIndirizziIntervento());
        }
        praticaXML.getIndirizziIntervento().getIndirizzoIntervento().remove(toRemove);
        Staging stg = praticaEnitity.getIdStaging();
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXML, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        stagingService.update(stg);
        String msg = messageSource.getMessage("pratica.indirizzointervento.rimuovi.note", new Object[]{
            Strings.nullToEmpty(toRemove.getIndirizzo()),
            Strings.nullToEmpty(toRemove.getCivico()),
            Strings.nullToEmpty(toRemove.getLettera()),
            Strings.nullToEmpty(toRemove.getLocalita())},
                Locale.getDefault());
        salvaEventoGenerico(praticaEnitity, RIMUOVI_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
        return indirizzoInterventoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IndirizzoInterventoDTO salvaIndirizziIntervento(IndirizzoInterventoDTO indirizzoInterventoDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
        String xmlPratica = new String(praticaEnitity.getIdStaging().getXmlPratica());
        it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
        it.wego.cross.entity.IndirizziIntervento datiDB;
        boolean isUpdate = false;
        IndirizzoInterventoDTO indirizzoInterventoOrigine = new IndirizzoInterventoDTO();
        if (indirizzoInterventoDTO.getIdIndirizzoIntervento() == null) {
            // l'indirizzo non esistente
            datiDB = mapper.map(indirizzoInterventoDTO, it.wego.cross.entity.IndirizziIntervento.class);
            datiDB.setIdPratica(praticaEnitity);
            if (indirizzoInterventoDTO.getIdDug() != null) {
                datiDB.setIdDug(lookupService.findDugById(indirizzoInterventoDTO.getIdDug()));
            }
            praticheService.saveIndirizzoIntervento(datiDB);
            usefulService.flush();
            indirizzoInterventoDTO.setIdIndirizzoIntervento(datiDB.getIdIndirizzoIntervento());
        } else {
            // l'indirizzo già esiste
            datiDB = praticheService.findIndirizziInterventoById(indirizzoInterventoDTO.getIdIndirizzoIntervento());
            mapper.map(indirizzoInterventoDTO, datiDB);
            indirizzoInterventoOrigine = mapper.map(datiDB, IndirizzoInterventoDTO.class);
            if (indirizzoInterventoDTO.getIdDug() != null) {
                datiDB.setIdDug(lookupService.findDugById(indirizzoInterventoDTO.getIdDug()));
            }
            praticheService.updateIndirizzoIntervento(datiDB);
            isUpdate = true;
        }
        // aggiorna l'xml
        if (praticaXML.getIndirizziIntervento() == null || praticaXML.getIndirizziIntervento().getIndirizzoIntervento().isEmpty()) {
            // struttura xml vuota
            Log.APP.info("Struttura dati indirizzo intervento. La creo ...");
            Log.APP.info("Aggiungo il nuovo indirizzo intervento nell'xml di staging");
            Log.APP.info("Creo l'oggetto per l'xml");
            it.wego.cross.xml.IndirizzoIntervento datiXML = new it.wego.cross.xml.IndirizzoIntervento();
            indirizziInterventoService.serializzaEntityXmlindirizzoIntervento(datiDB, datiXML);
            praticaXML.setIndirizziIntervento(new it.wego.cross.xml.IndirizziIntervento());
            Log.APP.info("Aggiungo il indirizzo intervento nell'xml");
            praticaXML.getIndirizziIntervento().getIndirizzoIntervento().add(datiXML);
            Log.APP.info("Aggiunto l'indirizzo intervento " + datiXML.getIdIndirizzoIntervento());
        } else {
            // struttura xml già presente
            // cerco la struttura xml per idimmobile
            it.wego.cross.xml.IndirizzoIntervento datiXML = indirizziInterventoService.trovaStrutturaXmlPerIdIndirizzoIntervento(datiDB, praticaXML.getIndirizziIntervento());
            if (datiXML != null) {
                // trovato per idImmobile, loaggiorno
                indirizziInterventoService.serializzaEntityXmlindirizzoIntervento(datiDB, datiXML);
                datiXML.setConfermato("true");
                Log.APP.info("Modifico l'indirizzo intervento per idIndirizzoIntervento" + datiXML.getIdIndirizzoIntervento());
            } else {
                // non trovato per idImmobile, lo cerco per counter
                datiXML = indirizziInterventoService.trovaStrutturaXmlPerCounter(indirizzoInterventoDTO, praticaXML.getIndirizziIntervento());
                if (datiXML != null) {
                    // trovato per counter
                    indirizziInterventoService.serializzaEntityXmlindirizzoIntervento(datiDB, datiXML);
                    datiXML.setConfermato("true");
                    Log.APP.info("Modifico l'indirizzo intervento per counter" + datiXML.getIdIndirizzoIntervento());
                } else {
                    // non presente in struttura ne per idimmobile che per counter
                    datiXML = new it.wego.cross.xml.IndirizzoIntervento();
                    indirizziInterventoService.serializzaEntityXmlindirizzoIntervento(datiDB, datiXML);
                    datiXML.setConfermato("true");
                    praticaXML.getIndirizziIntervento().getIndirizzoIntervento().add(datiXML);
                    Log.APP.info("Aggiunto l'indirizzo intervento " + datiXML.getIdIndirizzoIntervento());
                }
            }
        }
        indirizzoInterventoDTO.setConfermato("true");
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        indirizzoInterventoDTO.setUrlCatasto(gp.getUrlCatastoIndirizzo(datiDB, praticaEnitity));
        if (datiDB.getIdDug() != null) {
            indirizzoInterventoDTO.setDesDug(datiDB.getIdDug().getDescrizione());
        }
        Staging stg = stagingService.findById(praticaEnitity.getIdStaging().getIdStaging());
        JAXBContext contextObj = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter praticaSTR = new StringWriter();
        marshallerObj.marshal(praticaXML, praticaSTR);
        stg.setXmlPratica(praticaSTR.toString().getBytes());
        stagingService.update(stg);
        if (isUpdate) {
            String msg = messageSource.getMessage("pratica.indirizzointervento.modifica.note", new Object[]{
                Strings.nullToEmpty(indirizzoInterventoOrigine.getIndirizzo()),
                Strings.nullToEmpty(indirizzoInterventoOrigine.getCivico()),
                Strings.nullToEmpty(indirizzoInterventoOrigine.getLettera()),
                Strings.nullToEmpty(indirizzoInterventoOrigine.getLocalita()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getIndirizzo()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getCivico()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getLettera()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getLocalita())},
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, MODIFICA_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
        } else {
            String msg = messageSource.getMessage("pratica.indirizzointervento.aggiungi.note", new Object[]{
                Strings.nullToEmpty(indirizzoInterventoDTO.getIndirizzo()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getCivico()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getLettera()),
                Strings.nullToEmpty(indirizzoInterventoDTO.getLocalita())},
                    Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, AGGIUNGI_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
        }

        return indirizzoInterventoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IndirizzoInterventoDTO inserisciIndirizzoInterventoBancaDati(IndirizzoInterventoDTO indirizzoInterventoDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
        it.wego.cross.entity.IndirizziIntervento datiDB = new it.wego.cross.entity.IndirizziIntervento();
        mapper.map(indirizzoInterventoDTO, datiDB);
        if (indirizzoInterventoDTO.getIdDug() != null) {
            datiDB.setIdDug(lookupService.findDugById(indirizzoInterventoDTO.getIdDug()));
        }
        datiDB.setIdPratica(praticaEnitity);
        praticheService.saveIndirizzoIntervento(datiDB);
//        praticaDao.insert(datiDB);
        usefulService.flush();
        indirizzoInterventoDTO.setIdIndirizzoIntervento(datiDB.getIdIndirizzoIntervento());
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        indirizzoInterventoDTO.setUrlCatasto(gp.getUrlCatastoIndirizzo(datiDB, praticaEnitity));
        indirizzoInterventoDTO.setIdPratica(praticaEnitity.getIdPratica());
        if (datiDB.getIdDug() != null) {
            indirizzoInterventoDTO.setDesDug(datiDB.getIdDug().getDescrizione());
        }
        String msg = messageSource.getMessage("pratica.indirizzointervento.aggiungi.note", new Object[]{
            Strings.nullToEmpty(indirizzoInterventoDTO.getIndirizzo()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getCivico()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLettera()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLocalita())},
                Locale.getDefault());
        salvaEventoGenerico(praticaEnitity, AGGIUNGI_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
        return indirizzoInterventoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IndirizzoInterventoDTO salvaIndirizziInterventoBancaDati(IndirizzoInterventoDTO indirizzoInterventoDTO, Utente utenteConnesso) throws Exception {
        Pratica praticaEnitity = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
        it.wego.cross.entity.IndirizziIntervento datiDB = praticheService.findIndirizziInterventoById(indirizzoInterventoDTO.getIdIndirizzoIntervento());
        IndirizzoInterventoDTO indirizzoInterventoOrigine = mapper.map(datiDB, IndirizzoInterventoDTO.class);
        mapper.map(indirizzoInterventoDTO, datiDB);
        if (indirizzoInterventoDTO.getIdDug() != null) {
            datiDB.setIdDug(lookupService.findDugById(indirizzoInterventoDTO.getIdDug()));
        }
        datiDB.setIdPratica(praticaEnitity);
        praticheService.updateIndirizzoIntervento(datiDB);
//        praticaDao.update(datiDB);
        GestionePratica gp = pluginService.getGestionePratica(praticaEnitity.getIdProcEnte().getIdEnte().getIdEnte());
        indirizzoInterventoDTO.setUrlCatasto(gp.getUrlCatastoIndirizzo(datiDB, praticaEnitity));
        indirizzoInterventoDTO.setIdPratica(praticaEnitity.getIdPratica());
        if (datiDB.getIdDug() != null) {
            indirizzoInterventoDTO.setDesDug(datiDB.getIdDug().getDescrizione());
        }
        String msg = messageSource.getMessage("pratica.indirizzointervento.modifica.note", new Object[]{
            Strings.nullToEmpty(indirizzoInterventoOrigine.getIndirizzo()),
            Strings.nullToEmpty(indirizzoInterventoOrigine.getCivico()),
            Strings.nullToEmpty(indirizzoInterventoOrigine.getLettera()),
            Strings.nullToEmpty(indirizzoInterventoOrigine.getLocalita()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getIndirizzo()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getCivico()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLettera()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLocalita())},
                Locale.getDefault());
        salvaEventoGenerico(praticaEnitity, MODIFICA_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
        return indirizzoInterventoDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public void eliminaIndirizzoIntervento(Integer idIndirizzoIntervento, Utente utenteConnesso) throws Exception {
        it.wego.cross.entity.IndirizziIntervento datiDB = praticheService.findIndirizziInterventoById(idIndirizzoIntervento);
        IndirizzoInterventoDTO indirizzoInterventoDTO = new IndirizzoInterventoDTO();
        mapper.map(datiDB, indirizzoInterventoDTO);
        Pratica praticaEntity = datiDB.getIdPratica();
        praticheService.eliminaIndirizzoIntervento(datiDB);
        String msg = messageSource.getMessage("pratica.indirizzointervento.rimuovi.note", new Object[]{
            Strings.nullToEmpty(indirizzoInterventoDTO.getIndirizzo()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getCivico()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLettera()),
            Strings.nullToEmpty(indirizzoInterventoDTO.getLocalita())},
                Locale.getDefault());
        salvaEventoGenerico(praticaEntity, RIMUOVI_INDIRIZZO_INTERVENTO, msg, utenteConnesso);
    }

    @Transactional
    public List<String> VerifyInsertProcedimentoEnte(Integer idPratica, Integer idProcedimento, Integer idEnte, List<String> erroriList, Utente utenteConnesso) throws Exception {
        PraticaProcedimenti praticaProcedimenti = praticheService.findPraticaProcedimento(idPratica, idProcedimento, idEnte);
        Pratica praticaEnitity = praticheService.getPratica(idPratica);
        if (praticaProcedimenti != null) {
            String msgError = messageSource.getMessage("error.select.procedimentoGiaPresente", null, Locale.getDefault());
            erroriList.add(msgError);
        } else {
            praticaProcedimenti = new PraticaProcedimenti();
            PraticaProcedimentiPK key = new PraticaProcedimentiPK();
            key.setIdEnte(idEnte);
            key.setIdPratica(idPratica);
            key.setIdProcedimento(idProcedimento);
            praticaProcedimenti.setPraticaProcedimentiPK(key);
            Log.APP.info("Salvo la relazione pratica-procedimento");
//            praticaDao.insert(praticaProcedimenti);
            praticheService.salvaPraticaProcedimento(praticaProcedimenti);
            String descProc = procedimentiService.getDescrizioneProcedimentoLingua(1, idProcedimento);
            String msg = messageSource.getMessage("pratica.endoprocedimenti.aggiungi.note", new Object[]{descProc}, Locale.getDefault());
            salvaEventoGenerico(praticaEnitity, COLLEGA_ENDOPROCEDIMENTO, msg, utenteConnesso);
        }
        return erroriList;
    }

    @Transactional
    public PraticheEventi riassegnaPratica(Pratica pratica, Utente precedenteAssegnatario, Utente nuovoAssegnatario, Utente utenteConnesso) throws Exception {
        pratica.setIdUtente(nuovoAssegnatario);
        praticheService.aggiornaPratica(pratica);
        String msg = messageSource.getMessage("pratica.riassegna.note", new Object[]{
            Strings.nullToEmpty(precedenteAssegnatario.getNome()),
            Strings.nullToEmpty(precedenteAssegnatario.getCognome()),
            Strings.nullToEmpty(nuovoAssegnatario.getNome()),
            Strings.nullToEmpty(nuovoAssegnatario.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(pratica, RIASSEGNA_PRATICA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

    //Non è transactional perché viene chiamato solo da Assegna pratica in PraticheAction
    public PraticheEventi assegnaPratica(Pratica pratica, Utente assegnatario, Utente utenteConnesso) throws Exception {
        pratica.setIdUtente(assegnatario);
        praticheService.aggiornaPratica(pratica);
        String msg = messageSource.getMessage("pratica.assegna.note", new Object[]{
            Strings.nullToEmpty(assegnatario.getNome()),
            Strings.nullToEmpty(assegnatario.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(pratica, ASSEGNA_PRATICA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

    //Non è transactional perché viene chiamato solo da markAsReceived in EmailAction
    public PraticheEventi marcaEmailComeConsegnata(Pratica pratica, Email email, Utente utenteConnesso) throws Exception {
        String msg = messageSource.getMessage("pratica.emailconsegnata.note", new Object[]{
            email.getOggettoEmail(),
            email.getEmailDestinatario(),
            Strings.nullToEmpty(utenteConnesso.getNome()),
            Strings.nullToEmpty(utenteConnesso.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(pratica, MARCA_MAIL_COME_CONSEGNATA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

    //Non è transactional perché viene chiamato solo da rispedisciEmail in EmailAction
    public PraticheEventi rispedisciEmail(Pratica pratica, Email email, Utente utenteConnesso) throws Exception {
        String msg = messageSource.getMessage("pratica.emailrispedita.note", new Object[]{
            Strings.nullToEmpty(email.getOggettoEmail()),
            Strings.nullToEmpty(email.getEmailDestinatario()),
            Strings.nullToEmpty(utenteConnesso.getNome()),
            Strings.nullToEmpty(utenteConnesso.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(pratica, MAIL_REINVIATA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

    //Non è transactional perché viene chiamato solo da collegaPratica in PraticheAction
    public PraticheEventi collegaPratica(Pratica praticaOrigine, Pratica praticaDestinazione, Utente utenteConnesso) throws Exception {
        String msg = messageSource.getMessage("pratica.praticacollegata.note", new Object[]{
            Strings.nullToEmpty(praticaOrigine.getCodRegistro()),
            Strings.nullToEmpty(praticaOrigine.getAnnoRiferimento() != null ? String.valueOf(praticaOrigine.getAnnoRiferimento()) : ""),
            Strings.nullToEmpty(praticaOrigine.getProtocollo()),
            Strings.nullToEmpty(praticaDestinazione.getCodRegistro()),
            Strings.nullToEmpty(praticaDestinazione.getAnnoRiferimento() != null ? String.valueOf(praticaOrigine.getAnnoRiferimento()) : ""),
            Strings.nullToEmpty(praticaDestinazione.getProtocollo()),
            Strings.nullToEmpty(utenteConnesso.getNome()),
            Strings.nullToEmpty(utenteConnesso.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(praticaOrigine, COLLEGA_PRATICA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

    //Non è transactional perché viene chiamato solo da scollegaPratica in PraticheAction
    public PraticheEventi scollegaPratica(Pratica praticaOrigine, Pratica praticaDestinazione, Utente utenteConnesso) throws Exception {
        String msg = messageSource.getMessage("pratica.praticascollegata.note", new Object[]{
            Strings.nullToEmpty(praticaOrigine.getCodRegistro()),
            Strings.nullToEmpty(praticaOrigine.getAnnoRiferimento() != null ? String.valueOf(praticaOrigine.getAnnoRiferimento()) : ""),
            Strings.nullToEmpty(praticaOrigine.getProtocollo()),
            Strings.nullToEmpty(praticaDestinazione.getCodRegistro()),
            Strings.nullToEmpty(praticaDestinazione.getAnnoRiferimento() != null ? String.valueOf(praticaOrigine.getAnnoRiferimento()) : ""),
            Strings.nullToEmpty(praticaDestinazione.getProtocollo()),
            Strings.nullToEmpty(utenteConnesso.getNome()),
            Strings.nullToEmpty(utenteConnesso.getCognome())},
                Locale.getDefault());
        PraticheEventi salvaEventoGenerico = salvaEventoGenerico(praticaOrigine, SCOLLEGA_PRATICA, msg, utenteConnesso);
        return salvaEventoGenerico;
    }

}

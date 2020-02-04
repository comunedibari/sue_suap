/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.grid.GridComunicazioniBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.ComunicazioneMinifiedDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.PraticaProcedimentiPKDTO;
import it.wego.cross.dto.dozer.ProcessoEventoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Comunicazione;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Recapiti;
import it.wego.cross.entity.Scadenze;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ScadenzeDaChiudereView;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.serializer.EventiSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ComunicazioniService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.SearchService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.bouncycastle.cms.CMSException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author giuseppe
 */
@Component
public class ComunicazioneAction {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private NotaAction notaAction;
    @Autowired
    private EmailAction emailAction;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    private ComunicazioniService comunicazioniService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    Mapper mapper;
    @Autowired
    private PraticheSerializer praticheSerializer;
    @Autowired
    private EventiSerializer eventiSerializer;
    private static final String COMUNICAZIONE_FILTER = "comunicazioneSearchFilter";
    private static final String ENTE = "E";
    private static final String ANAGRAFICA = "A";
    private static final String NOTIFICA = "N";

    public GridComunicazioniBean getComunicazioni(HttpServletRequest request, JqgridPaginator paginator) throws ParseException {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        GridComunicazioniBean json = new GridComunicazioniBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = (Filter) request.getSession().getAttribute(COMUNICAZIONE_FILTER);
        if (filter == null) {
            filter = filterSerializer.getSearchFilter(request);
        }
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setConnectedUser(utenteConnesso);
        Long countRighe = comunicazioniService.countComunicazioniUtente(filter);
        List<Comunicazione> comunicazioni = comunicazioniService.getComunicazioniUtente(filter);
        List<ComunicazioneMinifiedDTO> comunicazioniMinified = new ArrayList<ComunicazioneMinifiedDTO>();
        for (Comunicazione c : comunicazioni) {
            ComunicazioneMinifiedDTO dto = new ComunicazioneMinifiedDTO();
            dto.setDataComunicazione(Utils.dateTimeItalianFormat(c.getDataComunicazione()));
            dto.setIdComunicazione(c.getIdComunicazione());
            dto.setDescrizione(c.getIdPraticaEvento().getIdEvento().getDesEvento());
            dto.setEnte(c.getIdPraticaEvento().getIdPratica().getIdProcEnte().getIdEnte().getDescrizione());
            if (c.getIdPratica() != null) {
                dto.setPratica(c.getIdPratica().getIdentificativoPratica());
                dto.setUtente(c.getIdPratica().getIdUtente().getNome() + " " + c.getIdPratica().getIdUtente().getCognome());
            }
            comunicazioniMinified.add(dto);
        }
        Integer records = (comunicazioniMinified.size() < maxResult) ? comunicazioniMinified.size() : comunicazioniMinified.size() / maxResult;
        Integer totalRecords = countRighe.intValue();
        totalRecords = ((totalRecords / maxResult) == 0) ? 1 : ((totalRecords / maxResult) + 1);
        json.setPage(page);
        json.setRecords(records);
        json.setTotal(totalRecords);
        json.setRows(comunicazioniMinified);
        return json;
    }

    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    public ComunicazioneBean gestisciEvento(ComunicazioneDTO comunicazione, Pratica pratica, ProcessiEventi eventoProcesso, UtenteDTO utenteConnesso) throws Exception {
        boolean inviaEmail = comunicazione.getInviaEmail() != null && comunicazione.getInviaEmail().equalsIgnoreCase("OK");
        boolean visualizzaEvento = comunicazione.getVisualizzaEventoSuCross() != null && comunicazione.getVisualizzaEventoSuCross().equalsIgnoreCase("OK");
        boolean visualizzaPortale = comunicazione.getVisualizzaEventoPortale() != null && comunicazione.getVisualizzaEventoPortale().equalsIgnoreCase("OK");
        AttoriComunicazione destinatari = getAttoriComunicazione(comunicazione.getDestinatariIds());
        AttoriComunicazione mittenti = getAttoriComunicazione(comunicazione.getMittentiIds());
        EventoDTO evento = eventiSerializer.serializeEvento(eventoProcesso);
        boolean checkFlgFirmato = checkFlgFirmato(eventoProcesso.getFlgFirmato());
        List<Allegato> allegati = getAllegati(comunicazione.getAllegatiPresenti(),
                comunicazione.getAllegatiManuali(),
                comunicazione.getAllegatiDaProtocollo(),
                comunicazione.getAllegatoOriginale(),
                pratica.getIdProcEnte().getIdEnte(),
                pratica.getIdComune(),
                checkFlgFirmato);
        ComunicazioneBean cb = new ComunicazioneBean();
        List<ScadenzeDaChiudereView> scadenze = praticheService.getScadenzeDaChiudere(eventoProcesso.getIdEvento(), pratica.getIdPratica());
        if (scadenze != null && !scadenze.isEmpty()) {
            evento.setVisualizzaScadenzeDaChiudere("S");
        } else {
            evento.setVisualizzaScadenzeDaChiudere("N");
        }
        List<Integer> scadenzeDaChiudere = new ArrayList<Integer>();
        if (evento.getVisualizzaScadenzeDaChiudere().equals("S")) {
            int daChiudere = 0;
            if (comunicazione.getScadenzeDaChiudere() != null) {
                for (ScadenzaDTO scadenza : comunicazione.getScadenzeDaChiudere()) {
                    if (scadenza.getIdScadenza() != null) {
                        daChiudere++;
                        Scadenze s = praticheService.findScadenzeByIdScadenza(scadenza.getIdScadenza());
                        scadenzeDaChiudere.add(s.getIdScadenza());
                    }
                }
            }
            if (daChiudere == 0) {
                String errore = messageSource.getMessage("error.scadenzeDaChiudereEmpty", null, Locale.getDefault());
                throw new CrossException(errore);
            }
        }

        List<ProcedimentoDTO> procedimentoRiferimento = comunicazione.getProcedimentiRiferimento();
        //Posso selezionare un solo procedimento di riferimento
        if (procedimentoRiferimento != null && procedimentoRiferimento.size() == 1) {
            ProcedimentoDTO p = procedimentoRiferimento.get(0);
            Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(p.getIdProcedimento());
            cb.setIdProcedimento(procedimento.getIdProc());
        }

        if (comunicazione.getScadenzeCustom() != null && !comunicazione.getScadenzeCustom().isEmpty()) {
            cb.setScadenzeCustom(comunicazione.getScadenzeCustom());
        }
        //L'oggetto per la protocollazione coincide con l'oggetto della comunicazione, se è previsto l'invio dell'email, altrimenti
        //resta l'oggetto della pratica
        cb.setInviaMail(inviaEmail);
        if (inviaEmail) {
            //cb.setOggettoEmail(comunicazione.getEvento().getOggetto());
            cb.setOggettoProtocollo(comunicazione.getEvento().getOggetto());
            //cb.setCorpoEmail(comunicazione.getEvento().getContenuto());
        } else {
            cb.setOggettoProtocollo(pratica.getOggettoPratica());
        }
//        Utente utente = utentiDao.findUtenteByIdUtente(utenteConnesso.getIdUtente());
//        Utente utente = utentiDao.findUtenteByIdUtente(pratica.getIdUtente().getIdUtente());//utente a cui è in carico la pratica utenteConnesso.getIdUtente()
        Utente utente = utentiDao.findUtenteByIdUtente(utenteConnesso.getIdUtente());
        cb.setIdScadenzeDaChiudere(scadenzeDaChiudere);
        cb.setIdPratica(pratica.getIdPratica());
        cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
        cb.setIdUtente(utente.getIdUtente());//utente a cui sarà assegnata l'evento
        cb.setDestinatari(destinatari);
        cb.setMittenti(mittenti);
        cb.setVisibilitaCross(visualizzaEvento);
        cb.setVisibilitaUtente(visualizzaPortale);
        //cb.setOggetto(comunicazione.getEvento().getOggetto());
        cb.setNote(comunicazione.getEvento().getOggetto());
        cb.setAllegati(allegati);
        if (evento.getProtocollo().equals("S")) {
            String numeroprotocollo = comunicazione.getNumeroDiProtocollo();
            if (!Utils.e(numeroprotocollo)) {
                cb.setNumeroProtocollo(numeroprotocollo);
                cb.setDataProtocollo(comunicazione.getDataDiProtocollo());
            }
        }
        workFlowService.gestisciProcessoEvento(cb);
//        Integer idEventoPratica = cb.getIdEventoPratica();
//        return praticheService.getPraticaEvento(idEventoPratica);
        return cb;
    }

    @Transactional(rollbackFor = Exception.class)
    public ComunicazioneBean gestisciEvento(it.wego.cross.dto.dozer.forms.ComunicazioneDTO comunicazione, UtenteDTO utenteConnesso) throws Exception {
        Pratica pratica = praticheService.getPratica(comunicazione.getIdPratica());
        ProcessiEventi processoEvento = getProcessoEvento(comunicazione.getIdEvento());

        ProcessoEventoDTO processoEventoDTO = mapper.map(processoEvento, ProcessoEventoDTO.class);

        AttoriComunicazione destinatari = getAttoriComunicazione(comunicazione.getDestinatariIds());
        AttoriComunicazione mittenti = getAttoriComunicazione(comunicazione.getMittentiIds());
        boolean checkFlgFirmato = "S".equalsIgnoreCase(processoEventoDTO.getFlgFirmato());
        List<Allegato> allegati = getAllegati(comunicazione, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune(), checkFlgFirmato);

        ComunicazioneBean cb = new ComunicazioneBean();
        List<ScadenzeDaChiudereView> scadenze = praticheService.getScadenzeDaChiudere(processoEventoDTO.getIdEvento(), pratica.getIdPratica());

        List<Integer> scadenzeDaChiudere = new ArrayList<Integer>();
        if (scadenze != null && !scadenze.isEmpty()) {
            int daChiudere = 0;
            if (comunicazione.getScadenzeDaChiudere() != null) {
                for (ScadenzaDTO scadenza : comunicazione.getScadenzeDaChiudere()) {
                    if (scadenza.getIdScadenza() != null) {
                        daChiudere++;
                        Scadenze s = praticheService.findScadenzeByIdScadenza(scadenza.getIdScadenza());
                        scadenzeDaChiudere.add(s.getIdScadenza());
                    }
                }
            }
            if (daChiudere == 0) {
                String errore = messageSource.getMessage("error.scadenzeDaChiudereEmpty", null, Locale.getDefault());
                throw new CrossException(errore);
            }
        }

        if (comunicazione.getScadenzeCustom() != null && !comunicazione.getScadenzeCustom().isEmpty()) {
            cb.setScadenzeCustom(comunicazione.getScadenzeCustom());
        }
        //L'oggetto per la protocollazione coincide con l'oggetto della comunicazione, se è previsto l'invio dell'email, altrimenti
        //resta l'oggetto della pratica
        cb.setInviaMail(comunicazione.getInviaEmail());
        if (comunicazione.getInviaEmail()) {
            cb.setOggettoEmail(comunicazione.getOggetto());
            cb.setOggettoProtocollo(comunicazione.getOggetto());
            cb.setCorpoEmail(comunicazione.getContenuto());
        } else {
            cb.setOggettoProtocollo(pratica.getOggettoPratica());
        }
        Utente utente = utentiDao.findUtenteByIdUtente(utenteConnesso.getIdUtente());
        cb.setIdScadenzeDaChiudere(scadenzeDaChiudere);
        cb.setIdPratica(pratica.getIdPratica());
        cb.setIdPraticaProtocollo(comunicazione.getIdPraticaProtocollo());
        cb.setIdEventoProcesso(processoEvento.getIdEvento());
        cb.setIdUtente(utente.getIdUtente());
        cb.setDestinatari(destinatari);
        cb.setMittenti(mittenti);
        cb.setVisibilitaCross(Boolean.TRUE);
        cb.setVisibilitaUtente(comunicazione.getVisualizzaEventoPortale());
        cb.setNote(comunicazione.getNote());
        cb.setAllegati(allegati);
        String numeroprotocollo = comunicazione.getNumeroDiProtocollo();
        if (!Strings.isNullOrEmpty(numeroprotocollo)) {
            cb.setNumeroProtocollo(numeroprotocollo);
            cb.setDataProtocollo(comunicazione.getDataDiProtocollo());
        }
        cb.setPraticaProcedimentiSelected(caricaProcedimentiSelected(comunicazione, pratica));
        workFlowService.gestisciProcessoEvento(cb);
        return cb;
    }

//    @-Transactional
    private List<Allegato> getAllegati(it.wego.cross.dto.dozer.forms.ComunicazioneDTO comunicazione, Enti ente, LkComuni comune, boolean checkFlgFirmato) throws Exception {
        return getAllegati(comunicazione.getAllegatiPresenti(), comunicazione.getAllegatiManuali(), comunicazione.getAllegatiDaProtocollo(), comunicazione.getAllegatoOriginale(), ente, comune, checkFlgFirmato);
    }

//    @-Transactional
    private List<Allegato> getAllegati(List<AllegatoDTO> allegatiPresenti, List<AllegatoDTO> allegatiManuali, List<AllegatoDTO> allegatiDaProtocollo, AllegatoDTO allegatoOriginale, Enti ente, LkComuni comune, boolean checkFlgFirmato) throws Exception {
        List<Allegato> allegati = new ArrayList<Allegato>();
        //Allegati selezionati dalla tabella in creazione evento
        if (allegatiPresenti != null && !allegatiPresenti.isEmpty()) {
            for (AllegatoDTO a : allegatiPresenti) {
                Integer id = a.getIdAllegato();
                if (id != null) {
                    Allegati all = allegatiService.findAllegatoById(id);
                    Integer idComune = null;
                    if (comune != null) {
                        idComune = comune.getIdComune();
                    }
                    GestioneAllegati ga = pluginService.getGestioneAllegati(ente.getIdEnte(), idComune);
                    byte[] content = ga.getFileContent(all, ente, comune);
                    Allegato allegato = new Allegato();
                    allegato.setDescrizione(all.getDescrizione());
                    allegato.setFile(content);
                    allegato.setId(id);
                    allegato.setSpedisci(Boolean.TRUE);
                    allegato.setProtocolla(Boolean.TRUE);
                    allegato.setIdEsterno(all.getIdFileEsterno());
                    allegato.setNomeFile(all.getNomeFile());
                    allegato.setPathFile(all.getPathFile());
                    allegato.setTipoFile(all.getTipoFile());
                    allegati.add(allegato);
                }
            }
        }

        //Allegati caricati manualmente: eseguo il controllo di firma solo su questi
        if (allegatiManuali != null && !allegatiManuali.isEmpty()) {
            for (AllegatoDTO allegato : allegatiManuali) {
                Allegato a = new Allegato();
                a.setDescrizione(allegato.getDescrizione());
                a.setProtocolla(Boolean.TRUE);
                a.setSpedisci(!Utils.e(allegato.getAllegaAllaMail()));
                MultipartFile file = allegato.getFile();
                //questo if serve per InserimentoEventoWS per poter passare il content
                if (file != null) {
                    a.setFile(file.getBytes());
                    Log.APP.info("Nome file: " + file.getOriginalFilename());
                    a.setNomeFile(file.getOriginalFilename());
                    Log.APP.info("Tipo file: " + file.getContentType());
                    a.setTipoFile(file.getContentType());

                } else {
                    a.setFile(allegato.getFileContent());
                    Log.APP.info("Nome file: " + allegato.getNomeFile());
                    a.setNomeFile(allegato.getNomeFile());
                    Log.APP.info("Tipo file: " + allegato.getTipoFile());
                    a.setTipoFile(allegato.getTipoFile());
                }
                Log.APP.info("Verifica se l'allegato " + a.getNomeFile() + " e' firmato");
                if (checkFlgFirmato) {
                    if (FileUtils.getSigned(a.getFile()) == null) {
                        throw new CMSException("Il documento" + a.getNomeFile() + " non e' un file firmato valido.");
                    }
                }
                a.setFileOrigine(allegato.isPrincipale());
                allegati.add(a);
            }
        }
        //Allegati prelevati da protocollo
        if (allegatiDaProtocollo != null && !allegatiDaProtocollo.isEmpty()) {
            for (AllegatoDTO allegato : allegatiDaProtocollo) {
                Allegato a = new Allegato();
                a.setDescrizione(allegato.getDescrizione());
                a.setIdEsterno(allegato.getIdFileEsterno());
                a.setNomeFile(allegato.getNomeFile());
                a.setTipoFile(allegato.getTipoFile());
                Integer idComune = null;
                if (comune != null) {
                    idComune = comune.getIdComune();
                }
                GestioneAllegati ga = pluginService.getGestioneAllegati(ente.getIdEnte(), idComune);
                Allegati allegatiToDownload = new Allegati();
                allegatiToDownload.setIdFileEsterno(allegato.getIdFileEsterno());
                byte[] fileContent = ga.getFileContent(allegatiToDownload, ente, comune);
                a.setFile(fileContent);
                a.setProtocolla(Boolean.TRUE);
                a.setSpedisci(!Utils.e(allegato.getAllegaAllaMail()));
                allegati.add(a);
            }
        }

        if (allegatoOriginale != null && allegatoOriginale.getFile() != null && allegatoOriginale.getFile().getSize() > 0) {
            AllegatoDTO allegato = allegatoOriginale;
            Allegato a = new Allegato();
            a.setDescrizione(allegato.getDescrizione());
            a.setFile(allegato.getFile().getBytes());
            a.setNomeFile(allegato.getFile().getOriginalFilename());
            a.setTipoFile(!Strings.isNullOrEmpty(allegato.getFile().getContentType()) ? allegato.getFile().getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE);
            a.setProtocolla(Boolean.TRUE);
            a.setFileOrigine(Boolean.TRUE);
            a.setSpedisci(Boolean.TRUE);
            allegati.add(a);
        }
        return allegati;
    }

    private AttoriComunicazione getAttoriComunicazione(List<String> destinatariIds) throws Exception {
        AttoriComunicazione destinatari = null;
        destinatariIds = eliminaDuplicati(destinatariIds);
        if (destinatariIds != null && destinatariIds.size() > 0) {
            destinatari = new AttoriComunicazione();
            for (String d : destinatariIds) {
                String tipoAnagrafica = d.split("\\|")[0];
                Integer id = Integer.valueOf(d.split("\\|")[1]);
                if (tipoAnagrafica.equals(ENTE)) {
//                    Enti ente = entiService.findByIdEnte(id);
//                    if (!destinatari.getEnti().contains(ente)) {
                    destinatari.addEnte(id);
//                    }
                } else if (tipoAnagrafica.equals(ANAGRAFICA)) {
                    Recapiti recapito = anagraficheService.findRecapitoById(id);
                    if (recapito.getAnagraficaRecapitiList().get(0) != null) {
                        //Al recapito è collegata sempre e solo una anagrafica 
                        AnagraficaRecapiti anagraficaRecapiti = recapito.getAnagraficaRecapitiList().get(0);
//                        AnagraficaRecapito ar = new AnagraficaRecapito();
//                        ar.setAnagrafica(anagrafica);
//                        ar.setRecapito(recapito);
//                        if (!destinatari.getAnagrafiche().contains(ar)) {
                        destinatari.addAnagrafica(anagraficaRecapiti.getIdAnagraficaRecapito());
//                        }
                    } else {
                        throw new Exception("E' stata scelto un indirizzo che non è associato ad alcuna anagrafica");
                    }
                } else if (tipoAnagrafica.equals(NOTIFICA)) {
//                    Recapiti recapito = anagraficheService.findRecapitoById(id);
                    destinatari.setIdRecapitoNotifica(id);
                }
            }
        }
        return destinatari;
    }


    /* spostati da Comunicazione Controller*/
    //@-Transactional
    public ProcessiEventi getProcessoEvento(Integer idEvento) {
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(idEvento);
        return eventoProcesso;
    }

//    @-Transactional
//    public Enti getEnte(Integer idEnte) {
//        return entiService.findByIdEnte(idEnte);
//    }
    @Transactional
    public EventoDTO cambiaFlag(EventoDTO evento) throws Exception {
        return eventiService.cambiaFlag(evento);
    }

////////    @Transactional
////////    public void confermaEmail(NotaDTO nota, EmailDTO email) throws Exception {
////////        notaAction.aggiungiTransactional(nota);
////////        emailAction.cambiaStato(email);
////////    }
    @Transactional
    public void ripopolaDestinatari(ComunicazioneDTO cm, ComunicazioneDTO comunicazione) {
        cm.getEvento().setOggetto(comunicazione.getEvento().getOggetto());
        cm.getEvento().setContenuto(comunicazione.getEvento().getContenuto());
        cm.setNumeroDiProtocollo(comunicazione.getNumeroDiProtocollo());
        List<String> ids = new ArrayList<String>();
        AttoriComunicazione destinatariTMP = new AttoriComunicazione();

        if (comunicazione.getDestinatariIds() != null) {
            ids = comunicazione.getDestinatariIds();
        } else if (comunicazione.getMittentiIds() != null) {
            ids = comunicazione.getMittentiIds();
        }

        for (String idd : ids) {
            String tipoAnagrafica = idd.split("\\|")[0];
            Integer id = Integer.valueOf(idd.split("\\|")[1]);
            if (tipoAnagrafica.equals(ENTE)) {
//                Enti ente = entiService.findByIdEnte(id);
                destinatariTMP.addEnte(id);
            } else if (tipoAnagrafica.equals(ANAGRAFICA)) {
                Recapiti rec = anagraficheService.findRecapitoById(id);
                AnagraficaRecapiti anagraficaRecapito = rec.getAnagraficaRecapitiList().get(0);
//                AnagraficaRecapito ar = new AnagraficaRecapito();
//                ar.setAnagrafica(anagrafica);
//                ar.setRecapito(rec);
                destinatariTMP.addAnagrafica(anagraficaRecapito.getIdAnagraficaRecapito());
            }
        }
        cm.setDestinatari(searchService.serializeDestinatari(null, destinatariTMP));
        cm.setNumeroDiProtocollo(comunicazione.getNumeroDiProtocollo());
    }

    private boolean checkFlgFirmato(String flgFirmato) {
        if (flgFirmato != null) {
            return flgFirmato.equalsIgnoreCase("S");
        } else {
            return false;
        }
    }

//    @Transactional(rollbackFor = Exception.class)
    public void creaComunicazione(ComunicazioneBean cb, Enti ente) throws Exception {
        Log.APP.info("Creazione della comunicazione per l'ente");
        Comunicazione c = new Comunicazione();
        c.setDataComunicazione(new Date());
        PraticheEventi pe = praticheService.getPraticaEvento(cb.getIdEventoPratica());
        c.setIdPraticaEvento(pe);
        c.setStatus(it.wego.cross.constants.Comunicazione.NON_LETTA);
        c.setIdEnte(ente);
        comunicazioniService.salva(c);
    }

    private List<String> eliminaDuplicati(List<String> destinatariIds) {
        List<String> listaPulita = new ArrayList<String>();
        if (destinatariIds != null) {
            for (String destinatario : destinatariIds) {
                if (!listaPulita.contains(destinatario)) {
                    listaPulita.add(destinatario);
                }
            }
        }
        return listaPulita;
    }

    public GridPraticaNuovaBean getPraticheUtente(HttpServletRequest request, Integer idComunicazione, JqgridPaginator paginator) throws ParseException, Exception {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = (Filter) request.getSession().getAttribute(COMUNICAZIONE_FILTER);
        if (filter == null) {
            filter = filterSerializer.getSearchFilter(request);
        }
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setConnectedUser(utenteConnesso);
        Comunicazione comunicazione = comunicazioniService.findComunicazioneById(idComunicazione);
        Pratica praticaPadre = comunicazione.getIdPraticaEvento().getIdPratica();
        Long countPraticheFiglieCollegabili = praticheService.countPraticheFiglie(praticaPadre, comunicazione.getIdEnte(), filter);
        List<Pratica> praticheFiglieCollegabili = praticheService.findPraticheFiglie(praticaPadre, comunicazione.getIdEnte(), filter);
        List<PraticaNuova> praticheNuove = new ArrayList<PraticaNuova>();
        for (Pratica pratica : praticheFiglieCollegabili) {
            PraticaNuova nuova = praticheSerializer.serializePraticaNuova(pratica, utenteConnesso);
            praticheNuove.add(nuova);
        }
        Integer records = (praticheNuove.size() < maxResult) ? praticheNuove.size() : praticheNuove.size() / maxResult;
        Integer totalRecords = countPraticheFiglieCollegabili.intValue();
        totalRecords = ((totalRecords / maxResult) == 0) ? 1 : ((totalRecords / maxResult) + 1);
        json.setPage(page);
        json.setRecords(records);
        json.setTotal(totalRecords);
        json.setRows(praticheNuove);
        return json;
    }
//    private void dettaglioPraticaFiglia(ProcedimentoDTO procedimento, PraticaProcedimenti pp) {
//        Pratica praticaFiglia = praticaDao.getPraticaFiglia(pp.getPratica(), pp.getProcedimenti());
//        if (praticaFiglia != null) {
//            procedimento.setDesStato(praticaFiglia.getIdStatoPratica().getDescrizione());
//            if (praticaFiglia.getIdUtente() != null) {
//                procedimento.setDesUtente(praticaFiglia.getIdUtente().getCognome() + " " + praticaFiglia.getIdUtente().getNome());
//            }
//            if (praticaFiglia.getScadenzeList() != null) {
//                Long giorni = null;
//                for (Scadenze scadenza : praticaFiglia.getScadenzeList()) {
//                    if (!scadenza.getIdStato().getGrpStatoScadenza().equalsIgnoreCase("C")) {
//                        ScadenzaDTO s = ScadenzeSerializer.serialize(scadenza);
//                        if (giorni == null) {
//                            giorni = s.getGiornirestanti();
//                        } else if (giorni < s.getGiornirestanti()) {
//                            giorni = s.getGiornirestanti();
//                        }
//                    }
//                }
//                if (giorni != null) {
//                    procedimento.setGiorniScadenza(giorni.toString());
//                }
//            }
//        }
//    }

    private List<PraticaProcedimentiPKDTO> caricaProcedimentiSelected(it.wego.cross.dto.dozer.forms.ComunicazioneDTO comunicazione, Pratica pratica) throws Exception {
        List<PraticaProcedimentiPKDTO> lista = new ArrayList<PraticaProcedimentiPKDTO>();
        if (comunicazione.getProcedimentiIds() != null) {
            for (String id : comunicazione.getProcedimentiIds()) {
                PraticaProcedimenti pp = praticheService.findEnteDaPraticaProcedimento(pratica.getIdPratica(), Integer.valueOf(id));
                if (pp != null) {
                    PraticaProcedimentiPKDTO praticaProcedimento = mapper.map(pp.getPraticaProcedimentiPK(), PraticaProcedimentiPKDTO.class);
                    lista.add(praticaProcedimento);
                }
            }
        }
        return lista;
    }

    @Transactional(rollbackFor = Exception.class)
	public void aggiornaUtentePraticaIntegrazione(Pratica pratica, ComunicazioneBean cb) throws Exception {
		workFlowService.aggiornaUtentePraticaIntegrazione(pratica, cb);
		
	}
}

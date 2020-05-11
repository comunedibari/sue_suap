/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.grid.GridPraticheProtocolloBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.StatoPraticaProtocollo;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.PraticaProtocolloDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticaProcedimentiPK;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.webservices.mypage.syncronizer.MyPageSynchronizerImplService;
import it.wego.cross.webservices.mypage.syncronizer.MyPageSynchronizerService;
import it.wego.cross.webservices.mypage.syncronizer.stub.SetPraticaRequest;
import it.wego.cross.webservices.mypage.syncronizer.stub.SetPraticaResponse;
import it.wego.cross.xml.Anagrafiche;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class CaricamentoManualeAction {

    @Autowired
    private PraticheProtocolloService praticheProtocolloService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private ProcedimentiService procedimentiService;
//    @Autowired
//    private EntiService entiService;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private UsefulService usefulService;
//    @Autowired
//    private RecapitoUtils recapitoUtils;
    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private WorkFlowService workflowService;
    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private PraticheSerializer praticheSerializer;
//    @Autowired
//    private AnagraficheDao anagraficheDao;
    @Autowired
    private PraticaDao praticaDao;
    @Autowired
    private StagingDao stagingDao;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private AnagraficheSerializer anagraficheSerializer;
    private static final String IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO = "identificativoPraticaProtocolloDocumento";

    @Transactional
    public ComunicazioneBean creaPraticaManuale(PraticaDTO pratica, Utente utente) throws Exception {
        PraticheProtocollo praticaProtocollo;
        Staging staging;
        LkComuni comune = null;
        Enti ente = entiDao.findByIdEnte(pratica.getIdEnte());

        if (pratica.getComune() != null && pratica.getComune().getIdComune() != null && pratica.getComune().getIdComune() > 0) {
            Integer idComuneSelezionato = pratica.getComune().getIdComune();
            Log.APP.info("Comune selezionato: " + idComuneSelezionato);
            comune = lookupService.findComuneById(idComuneSelezionato);
            Log.APP.info("Codice catastale comune: " + comune.getCodCatastale());
        } else {
            throw new CrossException("Comune non selezionato");
        }

        if (pratica.getIdPraticaProtocollo() == null) {
            praticaProtocollo = new PraticheProtocollo();
            praticaProtocollo.setModalita(Constants.CARICAMENTO_MODALITA_MANUALE);
            staging = new Staging();
        } else {
            praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(pratica.getIdPraticaProtocollo());
            if (praticaProtocollo.getIdStaging() != null) {
                staging = praticaProtocollo.getIdStaging();
            } else {
                staging = new Staging();
            }
        }

        praticaProtocollo.setOggetto(pratica.getOggettoPratica());
        praticaProtocollo.setNProtocollo(pratica.getProtocollo());
        //Il numero di fascicolo coincide con quello del protocollo
        praticaProtocollo.setNFascicolo(pratica.getProtocollo());
        praticaProtocollo.setAnnoRiferimento(pratica.getAnnoRiferimento());
        //Non avendo l'informazione, considero l'anno del fascicolo coincidente con quello di riferimento
        praticaProtocollo.setAnnoFascicolo(pratica.getAnnoRiferimento());
        praticaProtocollo.setCodRegistro(pratica.getRegistro());
        praticaProtocollo.setDataRicezione(pratica.getDataRicezione());
        praticaProtocollo.setDestinatario(ente.getUnitaOrganizzativa());
        praticaProtocollo.setDataProtocollazione(pratica.getDataProtocollo());
        //Non avendo l'informazione, considero la data di creazione del protocollo coincidente a quella della protocollazione
        praticaProtocollo.setDataFascicolo(pratica.getDataProtocollo());
        praticaProtocollo.setTipoDocumento(Constants.CARICAMENTO_MANUALE_PRATICA);
        it.wego.cross.xml.Pratica praticaXML = getXmlPratica(praticaProtocollo, pratica);

        //se la pratica è manuale nn ci sono ancora nuove anagrafiche
        //se la pratica è da protocollo in questa fase le uniche anagrafiche presenti saranno quelle pescate dal protocollo (se presenti)
        //se la pratica è da caricamento file esterno (es. suapfvg) le devo mantenere
        if (praticaXML.getAnagrafiche() == null || praticaXML.getAnagrafiche().isEmpty()) {
            praticaXML.setAnagrafiche(new ArrayList<Anagrafiche>());
        }
        //Salvo lo staging
        Log.APP.info("Setto l'XML della pratica");
        praticaXML.setAllegati(new it.wego.cross.xml.Allegati());
        List<Allegati> allegati = new ArrayList<Allegati>();
        Allegati riepilogoPratica = null;
        if (pratica.getAllegatiList() != null && pratica.getAllegatiList().size() > 0) {
            for (AllegatoDTO allegato : pratica.getAllegatiList()) {
                Allegati allegatoEntityLoop = allegatiService.salvaAllegatoDB(allegato, ente, comune);
//                allegato.setPathFile(allegatoEntityLoop.getPathFile());
                allegatiDao.insertAllegato(allegatoEntityLoop);
                usefulService.flush();
                it.wego.cross.xml.Allegato allegatoXml = AllegatiSerializer.serializeXML(allegato);
                allegati.add(allegatoEntityLoop);
                if (allegatoXml.getRiepilogoPratica().equalsIgnoreCase("S")) {
                    riepilogoPratica = allegatoEntityLoop;
                }
                praticaXML.getAllegati().getAllegato().add(allegatoXml);
            }
        }

        if (Constants.CARICAMENTO_MODALITA_AUTOMATICA.equalsIgnoreCase(praticaProtocollo.getModalita()) || Constants.CARICAMENTO_MODALITA_DANUMERO.equalsIgnoreCase(praticaProtocollo.getModalita())) {
            DocumentoProtocolloResponse documentoProtocolloResponse = praticheProtocolloService.getDocumentoProtocollo(praticaProtocollo, ente);
            if (documentoProtocolloResponse != null) {
                List<SoggettoProtocollo> anagraficheProtocollo = documentoProtocolloResponse.getSoggetti();
                if (anagraficheProtocollo != null && anagraficheProtocollo.size() > 0) {
                    for (int i = 0; i < anagraficheProtocollo.size(); i++) {
                        SoggettoProtocollo soggettoProtocollo = anagraficheProtocollo.get(i);
                        Anagrafica anagraficaDatabase = anagraficheService.findAnagraficaByCodFiscale(soggettoProtocollo.getCodiceFiscale());
                        AnagraficaDTO dto;
                        if (anagraficaDatabase != null) {
                            dto = AnagraficheSerializer.serializeAnagrafica(anagraficaDatabase);
                            dto.setManuale(false);
                        } else {
                            dto = AnagraficheSerializer.serialize(soggettoProtocollo, i);
                            dto.setIdAnagrafica(null);
                            dto.setManuale(true);
                        }
                        dto.setCounter(i);
                        if (dto.getRecapiti() == null || dto.getRecapiti().isEmpty()) {
                            dto.setRecapiti(new ArrayList<RecapitoDTO>());
                            dto.getRecapiti().add(new RecapitoDTO());
                        }
                        it.wego.cross.xml.Anagrafica anagrafica = anagraficheSerializer.serialize(dto);
                        it.wego.cross.xml.Anagrafiche anagrafiche = new Anagrafiche();
                        anagrafiche.setAnagrafica(anagrafica);
                        praticaXML.getAnagrafiche().add(anagrafiche);
                    }
                }
            }
        }
        String xmlPratica = PraticaUtils.getXmlFromPratica(praticaXML);
        staging.setXmlPratica(xmlPratica.getBytes());
        staging.setDataRicezione(pratica.getDataRicezione());
        staging.setIdEnte(ente);
        staging.setTipoMessaggio(Constants.CARICAMENTO_MANUALE);
        if (pratica.getIdPratica() == null) {
            stagingDao.insert(staging);
            usefulService.flush();
            praticaProtocollo.setIdStaging(staging);
            praticaDao.insert(praticaProtocollo);
            usefulService.flush();
        } else {
            stagingDao.merge(staging);
            usefulService.flush();
            praticaProtocollo.setIdStaging(staging);
            praticheProtocolloService.aggiorna(praticaProtocollo);
            usefulService.flush();
        }

        Log.APP.info("Procedo alla serializzazione della pratica xml");

        Pratica praticaDaSalvare = praticheService.serializePratica(praticaProtocollo, staging, praticaXML);
        Log.APP.info("Setto il recapito di notifica alla pratica");
//        praticaDaSalvare.setIdRecapito(recapitoNotifica);
        Log.APP.info("Setto l'utente che ha in carico la pratica: " + utente.getUsername());
        praticaDaSalvare.setIdUtente(utente);
        Log.APP.info("Salvo la pratica");
        praticaDao.insert(praticaDaSalvare);
        usefulService.flush();
        Log.APP.info("Pratica salvata");
        //Popolo i procedimenti
        Log.APP.info("Salvo i procedimenti assocaiti alla pratica");
        List<it.wego.cross.xml.Procedimento> procedimentiPratica = praticaXML.getProcedimenti().getProcedimento();
        List<Enti> entiDestinatari = Arrays.asList(ente);
        List<PraticaProcedimenti> praticaProcedimentiList = new ArrayList<PraticaProcedimenti>();
        for (it.wego.cross.xml.Procedimento procedimento : procedimentiPratica) {
            PraticaProcedimenti praticaProcedimenti = new PraticaProcedimenti();
            //Presuppongo che coincida con l'ID inserito nell'anagrafica enti di CROSS
            PraticaProcedimentiPK key = new PraticaProcedimentiPK();
            Log.APP.info("Ente destinatario del procedimento: " + procedimento.getCodEnteDestinatario());
            key.setIdEnte(procedimento.getIdEnteDestinatario().intValue());
            Log.APP.info("Identificativo della pratica: " + praticaDaSalvare.getIdPratica());
            key.setIdPratica(praticaDaSalvare.getIdPratica());
            Procedimenti proc = procedimentiService.findProcedimentoByIdProc(procedimento.getIdProcedimento());
            Log.APP.info("Codice del procedimento da salvare: " + proc.getCodProc());
            key.setIdProcedimento(proc.getIdProc());
            praticaProcedimenti.setPraticaProcedimentiPK(key);
            Log.APP.info("Salvo la relazione pratica-procedimento");
            praticaDao.insert(praticaProcedimenti);
            usefulService.flush();
            usefulService.refresh(praticaProcedimenti);
            praticaProcedimentiList.add(praticaProcedimenti);
            Log.APP.info("Relazione pratica-procedimento salvata");
        }
        praticaDaSalvare.setPraticaProcedimentiList(praticaProcedimentiList);
        praticaDao.update(praticaDaSalvare);
        usefulService.flush();
        Log.APP.info("Aggiorno le informazioni sulla pratica del protocollo");
        Log.APP.info("Associo alla pratica del protocollo l'utente " + utente.getUsername());
        praticaProtocollo.setIdUtentePresaInCarico(utente);
        praticaProtocollo.setDataPresaInCaricoCross(new Date());
        praticaProtocollo.setStato(StatoPraticaProtocollo.PRESA_IN_CARICO);
        Log.APP.info("Aggiorno la pratica protocollo");
        praticheProtocolloService.aggiorna(praticaProtocollo);
        usefulService.flush();
        //Creo l'evento di ricezione
        Log.APP.info("Creo l'evento di ricezione");
        ComunicazioneBean cb = serializeWorkflowBean(praticaDaSalvare, entiDestinatari, praticaXML);
        usefulService.flush();

        praticaDaSalvare.setIdModello(riepilogoPratica);
        for (Allegati allegato : allegati) {
            Allegato allegatoPerComunicazione = AllegatiSerializer.serializeAllegato(allegato);
            allegatoPerComunicazione.setProtocolla((allegato.getIdFileEsterno() != null) ? Boolean.FALSE : Boolean.TRUE);
            allegatoPerComunicazione.setSpedisci(Boolean.TRUE);
            allegatoPerComunicazione.setId(allegato.getId());

            cb.addAllegato(allegatoPerComunicazione);
        }

        Log.WS.info("Ente SUAP: " + ente.getDescrizione());
        Procedimenti procedimentoSuap = procedimentiService.findProcedimentoByIdProc(Integer.valueOf(praticaXML.getIdProcedimentoSuap()));
        Log.WS.info("Procedimento SUAP: " + procedimentoSuap.getCodProc());
        Processi processo = workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        if (Strings.isNullOrEmpty(praticaDaSalvare.getResponsabileProcedimento())) {
            ProcedimentiEnti procedimentiEnti = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(ente.getIdEnte(), procedimentoSuap.getIdProc());
            praticaDaSalvare.setResponsabileProcedimento(procedimentiEnti.getResponsabileProcedimento());
        }
        ProcessiEventi eventoProcesso = praticheService.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_PRATICA, processo.getIdProcesso());
        String numeroDiProtocollo = null;
        if (!Utils.e(praticaProtocollo.getNProtocollo())) {
            numeroDiProtocollo = praticaProtocollo.getCodRegistro() + "/" + praticaProtocollo.getAnnoRiferimento() + "/" + praticaProtocollo.getNProtocollo();
        }

        cb.setIdUtente(utente.getIdUtente());
        cb.setNumeroProtocollo(numeroDiProtocollo);
        cb.setDataProtocollo(praticaProtocollo.getDataProtocollazione());
        cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
        cb.setOggetto(praticaXML.getOggetto());
        if (Utils.e(cb.getOggettoProtocollo())) {
            cb.setOggettoProtocollo(praticaXML.getOggetto());
        }
        if (Utils.e(praticaDaSalvare.getIdentificativoPratica())) {
            praticaDaSalvare.setIdentificativoPratica(cb.getNumeroProtocollo());
            usefulService.flush();
        }
        praticaDaSalvare.setProtocollo("");//Intini
        praticaDaSalvare.setCodRegistro(null);
        //praticaDaSalvare.setAnnoRiferimento(null);
        praticaDao.update(praticaDaSalvare);
        workflowService.gestisciProcessoEvento(cb);
        
        String myPageSynchronizer = configuration.getCachedConfiguration("MyPageSynchronizer.url", ente.getIdEnte(), comune.getIdComune());
        if (!Utils.e(myPageSynchronizer)) {
            Log.APP.info("Contatto MyPageSynchronizer: " + myPageSynchronizer);
            java.net.URL url = new java.net.URL(myPageSynchronizer);
            MyPageSynchronizerImplService service = new MyPageSynchronizerImplService(url);
            MyPageSynchronizerService port = service.getMyPageSynchronizerImplPort();
            praticaXML.setOggetto(praticaDaSalvare.getOggettoPratica());
            praticaXML.setDataRicezione(Utils.dateToXmlGregorianCalendar(praticaDaSalvare.getDataRicezione()));
            SetPraticaRequest pr = praticheSerializer.serializeMyPage(praticaXML, eventoProcesso, praticaDaSalvare.getIdProcEnte().getIdEnte().getIdEnte(), praticaDaSalvare.getIdComune().getIdComune());
            SetPraticaResponse respone = port.setPratica(pr);
            Log.APP.info(respone.getResponse());
        }
        return cb;
    }

    private it.wego.cross.xml.Pratica getXmlPratica(PraticheProtocollo praticaProtocollo, PraticaDTO praticaDTO) throws Exception {
        Log.APP.info("Verifico se è presente un riferimento allo staging");
        Staging staging = praticaProtocollo.getIdStaging();
        it.wego.cross.xml.Pratica pratica = new it.wego.cross.xml.Pratica();
        Log.APP.info("La pratica è già stata salvata? " + (staging != null));
        if (staging != null) {
            String xmlString = new String(staging.getXmlPratica());
            Log.APP.info(xmlString);
            pratica = PraticaUtils.getPraticaFromXML(xmlString);
        }
        Log.APP.info("Identificativo pratica: " + praticaProtocollo.getIdentificativoPratica());
        pratica.setIdentificativoPratica(praticaProtocollo.getIdentificativoPratica());
        Log.APP.info("Oggetto pratica: " + praticaProtocollo.getOggetto());
        pratica.setOggetto(praticaProtocollo.getOggetto());
        Log.APP.info("Id protocollo: " + praticaProtocollo.getIdProtocollo());
        pratica.setIdProtocolloManuale(Utils.bi(praticaProtocollo.getIdProtocollo()));
        Log.APP.info("Numero protocollo: " + praticaProtocollo.getNProtocollo());
        pratica.setProtocollo(praticaProtocollo.getNProtocollo());
        pratica.setAnno(String.valueOf(praticaProtocollo.getAnnoRiferimento()));
        pratica.setRegistro(String.valueOf(praticaProtocollo.getCodRegistro()));

        if (praticaDTO.getProcedimentoPratica() != null) {
            pratica.setIdProcedimentoSuap(praticaDTO.getProcedimentoPratica().getIdProcedimento().toString());
        }

        if (pratica.getProcedimenti() == null) {
            pratica.setProcedimenti(new it.wego.cross.xml.Procedimenti());
        }
        pratica.getProcedimenti().getProcedimento().clear();
        for (ProcedimentoDTO procedimento : praticaDTO.getProcedimentiList()) {
            pratica.getProcedimenti().getProcedimento().add(ProcedimentiSerializer.serializeXML(procedimentiDao.findProcedimentiEntiByID(procedimento.getIdProcedimentoEnte())));
        }

        if (praticaProtocollo.getDataProtocollazione() != null) {
            pratica.setDataProtocollo(Utils.dateToXmlGregorianCalendar(praticaProtocollo.getDataProtocollazione()));
        }
        if (praticaDTO.getComune() != null && praticaDTO.getComune().getIdComune() != null && praticaDTO.getComune().getIdComune() > 0) {
            Integer idComuneSelezionato = praticaDTO.getComune().getIdComune();
            Log.APP.info("Comune selezionato: " + idComuneSelezionato);
            LkComuni comune = lookupService.findComuneById(idComuneSelezionato);
            Log.APP.info("Codice catastale comune: " + comune.getCodCatastale());
            pratica.setCodCatastaleComune(comune.getCodCatastale());
            pratica.setDesComune(comune.getDescrizione());
        } else {
            throw new CrossException("Comune non selezionato");
        }
        Enti ente = entiDao.findByIdEnte(praticaDTO.getIdEnte());
        Integer enteSUAPSelezionato = praticaDTO.getIdEnte();
        pratica.setIdEnte(Utils.bi(enteSUAPSelezionato));
        pratica.setDesEnte(ente.getDescrizione());
        pratica.setEmailEnte(ente.getEmail());
        pratica.setPecEnte(ente.getPec());
        pratica.setFaxEnte(ente.getFax());
        pratica.setCapEnte(String.valueOf(ente.getCap()));
        pratica.setTelefonoEnte(ente.getTelefono());
        pratica.setIndirizzoEnte(ente.getIndirizzo());
        return pratica;
    }

    //<editor-fold defaultstate="collapsed" desc="ALTRI METODI">
    public GridAnagraficaBean getAnagrafiche(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridAnagraficaBean json = new GridAnagraficaBean();
        Utente authUser = utentiService.getUtenteConnesso(request);
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setConnectedUser(authUser);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        Long countRighe = anagraficheService.countAnagrafiche(filter);
        List<AnagraficaDTO> anagrafiche = cambiaDenominazione(anagraficheService.searchAnagrafiche(filter));
        Integer records = (anagrafiche.size() < maxResult) ? anagrafiche.size() : anagrafiche.size() / maxResult;
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(records);
        json.setTotal(totalRecords);
        json.setRows(anagrafiche);
        return json;
    }

    public GridPraticaNuovaBean getPraticheDaAssegnare(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        Integer idProtocollo = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO);
        PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(idProtocollo);
        String showAll = request.getParameter("search_all");
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setSearchByUtenteConnesso(Boolean.FALSE);
        if (showAll == null || !"S".equals(showAll)) {
            filter.setSearchByUtenteConnesso(Boolean.TRUE);
            filter.setNumeroFascicolo(praticaProtocollo.getNFascicolo());
            filter.setAnnoRiferimento(praticaProtocollo.getAnnoFascicolo());
        }
        Long countRighe = praticheService.countFiltrate(filter);
        List<PraticaNuova> pratiche = praticheService.findFiltrate(filter);
        Integer records = (pratiche.size() < maxResult) ? pratiche.size() : pratiche.size() / maxResult;
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(records);
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
    }

    @Transactional
    public GridPraticheProtocolloBean getDocumentiDaProtocollo(HttpServletRequest request, JqgridPaginator paginator, Enti ente) throws ParseException {
        GridPraticheProtocolloBean json = new GridPraticheProtocolloBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setSearchByUtenteConnesso(Boolean.FALSE);
//        filter.setTipoDocumentoProtocollo(tipoDocumento);
        try {
            Long countRighe = praticheProtocolloService.countDocumentiProtocollo(filter, ente.getIdEnte());
            List<PraticaProtocolloDTO> pratiche = praticheProtocolloService.findDocumentiProtocollo(filter, ente.getIdEnte());
            Integer pageNumber = countRighe.intValue();
            pageNumber = (pageNumber / maxResult == 0) ? 1 : ((pageNumber % maxResult == 0) ? (pageNumber / maxResult) : (pageNumber / maxResult + 1));
            json.setPage(page);
            json.setRecords(countRighe.intValue());
            json.setTotal(pageNumber);
            json.setRows(pratiche);
        } catch (Exception ex) {
            String errorMessage = "Si è verificato un errore recuperando i documenti da protocollo";
            Log.APP.error(errorMessage, ex);
            json.setPage(1);
            json.setRecords(0);
            json.setTotal(0);
            json.setErrors(Arrays.asList(errorMessage));
            json.setRows(new ArrayList<PraticaProtocolloDTO>());
        }
        return json;
    }

    public GridPraticheProtocolloBean getPraticheDaProtocollo(HttpServletRequest request, JqgridPaginator paginator, Enti ente) throws Exception {
        GridPraticheProtocolloBean json = new GridPraticheProtocolloBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = filterSerializer.getSearchFilter(request);
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);
        filter.setSearchByUtenteConnesso(Boolean.FALSE);
        //filter.setTipoDocumentoProtocollo(tipoDocumento);

        try {
            Long countRighe = praticheProtocolloService.countPraticheProtocollo(filter, ente.getIdEnte());
            List<PraticaProtocolloDTO> pratiche = praticheProtocolloService.findPraticheProtocollo(filter, ente.getIdEnte());
            Integer pageNumber = countRighe.intValue();
            pageNumber = (pageNumber / maxResult == 0) ? 1 : ((pageNumber % maxResult == 0) ? (pageNumber / maxResult) : (pageNumber / maxResult + 1));
            json.setPage(page);
            json.setRecords(countRighe.intValue());
            json.setTotal(pageNumber);
            json.setRows(pratiche);
        } catch (Exception ex) {
            String errorMessage = "Si è verificato un errore recuperando le pratiche da protocollo";
            Log.APP.error(errorMessage, ex);
            json.setPage(1);
            json.setRecords(0);
            json.setTotal(0);
            json.setErrors(Arrays.asList(errorMessage));
            json.setRows(new ArrayList<PraticaProtocolloDTO>());
        }
        return json;
    }

    private ComunicazioneBean serializeWorkflowBean(Pratica pratica, List<Enti> entiDestinatari, it.wego.cross.xml.Pratica praticaCross) throws Exception {
        ComunicazioneBean cb = new ComunicazioneBean();
        cb.setIdPratica(pratica.getIdPratica());
        AttoriComunicazione destinatari = new AttoriComunicazione();
        for (Enti ente : entiDestinatari){
            destinatari.addEnte(ente.getIdEnte());    
        }
        cb.setDestinatari(destinatari);
        cb.setVisibilitaCross(Boolean.TRUE);
        cb.setVisibilitaUtente(Boolean.TRUE);
        return cb;
    }

    private List<AnagraficaDTO> cambiaDenominazione(List<AnagraficaDTO> searchAnagrafiche) throws Exception {
        List<AnagraficaDTO> anagrafiche = new ArrayList<AnagraficaDTO>();
        for (AnagraficaDTO a : searchAnagrafiche) {
            if (a.getNome() != null && a.getCognome() != null && (a.getDenominazione() == null || "".equals(a.getDenominazione()))) {
                a.setDenominazione(a.getCognome() + " " + a.getNome());
            }
            anagrafiche.add(a);
        }
        return anagrafiche;
    }
    //</editor-fold>
}

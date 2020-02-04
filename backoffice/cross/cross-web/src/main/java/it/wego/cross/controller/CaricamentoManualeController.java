/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import it.wego.cross.actions.AllegatiAction;
import it.wego.cross.actions.CaricamentoManualeAction;
import it.wego.cross.actions.ComunicazioneAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheProtocolloAction;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.grid.GridEventiBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.grid.GridPraticheProtocolloBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaProtocolloDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.forms.CaricamentoManualeDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.exception.CrossException;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.views.CustomViews;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.LuoghiSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ComunicazioniService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.SearchService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.IsValid;
import it.wego.cross.xml.Procedimento;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author giuseppe
 */
@Controller
public class CaricamentoManualeController extends AbstractController {

    //PAGINE
    private static final String HOME_PRATICHE_PROTOCOLLO = "lista_pratiche_protocollo";
    private static final String HOME_DOCUMENTI_PROTOCOLLO = "lista_documenti_protocollo";
    private static final String REDIRECT_HOME_DOCUMENTI_PROTOCOLLO = "redirect:/documenti/nuovi/protocollo.htm";
    private static final String LISTA_PRATICHE_PROTOCOLLO = "lista_pratiche_per_protocollo";
    private static final String LISTA_PRATICHE_EVENTI_PROTOCOLLO = "lista_eventi_protocollo";
//    private static final String DETTAGLIO_DOCUMENTO_PROTOCOLLO = "documento_protocollo";
//    private static final String DETTAGLIO_EVENTO = "comunicazione_nuovo_evento";
    private static final String DETTAGLIO_EVENTO = "dettaglio_evento_protocollo";
//    private static final String CARICAMENTO_MANUALE_ANAGRAFICHE_VIEW = "documento_protocollo_anagrafiche";
    private static final String CERCA_ANAGRAFICA = "documento_protocollo_cerca_anagrafica";
    private static final String CARICAMENTO_DANUMERO = "caricamento_pratica_danumero";
    private static final String AJAX = "ajax";
//    private static final String VISUALIZZA_PRATICHE_DA_APRIRE = "redirect:/pratiche/nuove/apertura.htm";
    private static final String REDIRECT_VISUALIZZA_EVENTO = "redirect:/documenti/protocollo/pratica/eventi/visualizza.htm";
    //COSTANTI SESSIONE
    private static final String IDENTIFICATIVO_PRATICA_PROTOCOLLO = "identificativoPraticaProtocollo";
    private static final String IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO = "identificativoPraticaProtocolloDocumento";
    private static final String IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO = "identificativoPraticaSelezionataProtocollo";
    private static final String PRATICA_VALORIZZATA = "praticaValorizzata";
    private static final String ANAGRAFICHE_PRATICA = "anagrafichePratica";

    @Autowired
    private ComunicazioniService comunicazioneService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PraticheProtocolloService praticheProtocolloService;
    @Autowired
    private EntiService entiService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    AnagraficheService anagraficheService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private Validator validator;
    @Autowired
    private IsValid isValid;
    @Autowired
    private ComunicazioneAction action;
    @Autowired
    private ConfigurationService configuration;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private SearchService searchService;
    @Autowired
    private AllegatiAction allegatiAction;
    @Autowired
    private PraticheProtocolloAction praticheProtocolloAction;
    @Autowired
    private CaricamentoManualeAction caricamentoManualeAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private PraticheSerializer praticheSerializer;

    @RequestMapping("/pratiche/nuove/protocollo")
    public String listaPraticheDaProtocollo(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
        it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();

        clearSession(request.getSession());

        //TODO e chi lo mette in sessione ?
        Filter filter = (Filter) request.getSession().getAttribute("praticheNuoveProtocollo");
        if (filter == null) {
            filter = new Filter();
        }
        List<String> anniRiferimento = praticheService.popolaListaAnni();
        model.addAttribute("enteSelezionato", entePerCuiOpero);
        model.addAttribute("filtroRicerca", filter);
        model.addAttribute("anniRiferimento", anniRiferimento);
        String config = configuration.getCachedConfiguration(SessionConstants.PROTOCOLLO_MODALITA_CARICAMENTO, entePerCuiOpero.getIdEnte(), null);
        String caricamentoManuale = null;
        if (Utils.e(config)) {
            caricamentoManuale = null;
        } else if (Constants.CARICAMENTO_MODALITA_AUTOMATICA.equalsIgnoreCase(config)) {
            caricamentoManuale = null;
        } else if (Constants.CARICAMENTO_MODALITA_DANUMERO.equalsIgnoreCase(config)) {
            caricamentoManuale = "danumero";
        } else if (Constants.CARICAMENTO_MODALITA_MANUALE.equalsIgnoreCase(config)) {
            caricamentoManuale = "manuale";
        }
        model.addAttribute("caricamentoManuale", caricamentoManuale);
        String listaPraticheProtocollo = HOME_PRATICHE_PROTOCOLLO;
        try {
            CustomViews customPraticheProtocolloView = pluginService.getCustomViews(entePerCuiOpero.getIdEnte(), null);
            if (customPraticheProtocolloView != null) {
                String custom = customPraticheProtocolloView.getCustomPraticheDaProtocolloView();
                if (custom != null && !custom.trim().equals("")) {
                    listaPraticheProtocollo = custom;
                }
            }
        } catch (Exception ex) {
            Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
            Log.APP.error("Non è stato possibile caricare il plugin custom per la view delle pratiche da protocollo. Utilizzo il default", ex);
            //TO DO gestire il messaggio di errore
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_PROTOCOLLO, "Errore nell'esecuzione del controller /pratiche/nuove/protocollo", ex, null, utenteAutenticato);
            erroriAction.saveError(errore);
        }
        return listaPraticheProtocollo;
    }

    @RequestMapping("/pratiche/nuove/caricamento")
    public String caricamento(
            Model model,
            @RequestParam(value = "idPraticaProtocollo", required = false) Integer idPraticaProtocollo,
            @ModelAttribute("praticaCaricamentoManuale") CaricamentoManualeDTO praticaCaricamentoManuale,
            @ModelAttribute("message") Message message,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();

            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();

            if (idPraticaProtocollo != null && !praticaCaricamentoManuale.getInitialized()) {
                PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(idPraticaProtocollo);
                PraticaProtocolloDTO praticaProtocolloDTO = praticheSerializer.serialize(praticaProtocollo, entiService.findByIdEnte(entePerCuiOpero.getIdEnte()));
                praticaCaricamentoManuale.setIdPraticaProtocollo(idPraticaProtocollo);

                praticaCaricamentoManuale.setOggetto(praticaProtocolloDTO.getOggetto());
                praticaCaricamentoManuale.setProtocolloData(praticaProtocolloDTO.getDataRiferimento());
                if (praticaProtocolloDTO.getCodRegistro() != null && praticaProtocolloDTO.getAnnoRiferimento() != null && praticaProtocolloDTO.getnProtocollo() != null) {
                    praticaCaricamentoManuale.setProtocolloSegnatura(Joiner.on("/").join(praticaProtocolloDTO.getCodRegistro(), praticaProtocolloDTO.getAnnoRiferimento(), praticaProtocolloDTO.getnProtocollo()));
                }
                praticaCaricamentoManuale.setRicezioneData(praticaProtocolloDTO.getDataRicezione());
                praticaCaricamentoManuale.setInitialized(Boolean.TRUE);

                if (praticaProtocollo.getIdStaging() != null) {
                    String data = Utils.convertXmlCharset(new String(praticaProtocollo.getIdStaging().getXmlPratica(), "UTF-8"), "ISO-8859-1", "UTF-8");
                    it.wego.cross.xml.Pratica praticaFromXML = PraticaUtils.getPraticaFromXML(data);
                    if (!StringUtils.isEmpty(praticaFromXML.getCodCatastaleComune())) {
                        LkComuni comuneSelezionato = lookupService.findComuneByCodCatastale(praticaFromXML.getCodCatastaleComune());
                        praticaCaricamentoManuale.setComuneSelezionato(comuneSelezionato.getIdComune());
                    }
                    praticaCaricamentoManuale.setProcedimentoSelezionato(Integer.valueOf(praticaFromXML.getIdProcedimentoSuap()));
                    for (Procedimento endoProcedimento : praticaFromXML.getProcedimenti().getProcedimento()) {
                        Procedimenti procedimento = null;
                        if (endoProcedimento.getIdProcedimento() != 0) {
                            procedimento = procedimentiDao.findProcedimentoByIdProc(endoProcedimento.getIdProcedimento());
                        } else if (!StringUtils.isEmpty(endoProcedimento.getCodProcedimento())) {
                            procedimento = procedimentiDao.findProcedimentoByCodProc(endoProcedimento.getCodProcedimento());
                        }
                        if (procedimento != null) {
                            List<ProcedimentiEnti> procedimentiEnte = procedimentiDao.findAllProcedimentiEntiByIdProcedimento(procedimento.getIdProc());
                            if (procedimentiEnte.size() == 1) {
                                praticaCaricamentoManuale.getEndoprocedimentiSelezionati().add(procedimentiEnte.get(0).getIdProcEnte());
                            }
                        }
                    }

                }

                model.addAttribute("endoProcedimentiSelezionatiMap", Maps.toMap(praticaCaricamentoManuale.getEndoprocedimentiSelezionati(), new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer input) {
                        return true;
                    }
                }));

                List<AllegatoDTO> allegati;
                if (Constants.CARICAMENTO_MODALITA_MANUALE.equalsIgnoreCase(praticaProtocollo.getModalita()) || Constants.CARICAMENTO_MANUALE_PRATICA.equalsIgnoreCase(praticaProtocollo.getModalita())) {
                    allegati = praticheProtocolloService.getAllegatiProtocollo(praticaProtocollo);
                } else {

                    Integer idEnte = entePerCuiOpero.getIdEnte();
                    Enti ente = entiService.findByIdEnte(idEnte);

                    DocumentoProtocolloResponse documentoProtocolloResponse = praticheProtocolloService.getDocumentoProtocollo(praticaProtocollo, ente);
                    allegati = praticheProtocolloService.getAllegatiProtocollo(documentoProtocolloResponse);
                }
                praticaCaricamentoManuale.setAllegatiList(allegati);

            }
            model.addAttribute("entePerCuiOpero", entePerCuiOpero);
            return "caricamento_pratica_manuale";
        } catch (Exception ex) {
            Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
            Log.APP.error("Errore nel caricamento di pratiche nuove", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_CARICAMENTO, "errore nell'esecuzione del controller /pratiche/nuove/caricamento", ex, null, utenteAutenticato);
            erroriAction.saveError(errore);
        }
        return HOME_PRATICHE_PROTOCOLLO;
    }

    @RequestMapping("/pratiche/nuove/protocollo/ajax")
    public String listaPraticheDaProtocolloAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        HttpSession session = request.getSession();
        GridPraticheProtocolloBean json = new GridPraticheProtocolloBean();
        try {
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();

            Enti ente = entiService.findByIdEnte(entePerCuiOpero.getIdEnte());
            json = caricamentoManualeAction.getPraticheDaProtocollo(request, paginator, ente);
        } catch (Exception e) {
            Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
            List<Message> errori = new ArrayList<Message>();
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_PROTOCOLLO_AJAX, "errore nell'esecuzione del controller /pratiche/nuove/protocollo/ajax", e, null, utenteAutenticato);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/pratiche/caricamento/manuale")
    public ModelAndView caricamentoManualeSubmit(
            Model model,
            @ModelAttribute CaricamentoManualeDTO praticaCaricamentoManuale,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        Message messages = new Message();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) request.getSession().getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);

            Log.APP.info("Procedimento selezionato: " + praticaCaricamentoManuale.getProcedimentoSelezionato());
            if (praticaCaricamentoManuale.getProcedimentoSelezionato() == null) {
                throw new CrossException("Procedimento non selezionato");
            }

            ProcedimentiEnti procedimentoEnteSelezionato = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(ruoloSelezionato.getIdEnte().getIdEnte(), praticaCaricamentoManuale.getProcedimentoSelezionato());
            if (procedimentoEnteSelezionato == null || procedimentoEnteSelezionato.getIdProcesso() == null) {
                throw new CrossException("Il procedimento selezionato non ha nessun processo associato. Si prega di contattare l'amministratore.");
            }

            Log.APP.info("Comune selezionato: " + praticaCaricamentoManuale.getComuneSelezionato());
            if (praticaCaricamentoManuale.getComuneSelezionato() == null) {
                throw new CrossException("Comune non selezionato");
            }

            Log.APP.info("Oggetto selezionato: " + praticaCaricamentoManuale.getOggetto());
            if (Utils.e(praticaCaricamentoManuale.getOggetto())) {
                throw new CrossException("Oggetto non valorizzato");
            }

            Log.APP.info("Data ricezione selezionato: " + praticaCaricamentoManuale.getOggetto());
            if (praticaCaricamentoManuale.getRicezioneData() == null) {
                throw new CrossException("Data ricezione non valorizzata");
            }

            Log.APP.info("Segnatura protocollo selezionata: " + praticaCaricamentoManuale.getProtocolloSegnatura());
            if (praticaCaricamentoManuale.getProtocolloSegnatura() == null) {
                throw new CrossException("Valorizzare la segnatura di protocollo");
            }
            String[] protocolloSplittato = praticaCaricamentoManuale.getProtocolloSegnatura().split("/");
            if (protocolloSplittato.length != 3 || protocolloSplittato[0].isEmpty() || protocolloSplittato[2].isEmpty() || protocolloSplittato[2].isEmpty()) {
                throw new CrossException("Segnatura di protocollo non valida");
            }

            Log.APP.info("Segnatura protocollo selezionata: " + praticaCaricamentoManuale.getProtocolloData());
            if (praticaCaricamentoManuale.getProtocolloData() == null) {
                throw new CrossException("Valorizzare la data di protocollo");
            }

            Log.APP.info("Numero allegati caricati: " + praticaCaricamentoManuale.getAllegatiList().size());
            if (praticaCaricamentoManuale.getAllegatiList().isEmpty()) {
                throw new CrossException("Caricare almeno un allegato e impostare il flag 'Riepilogo Domanda'");
            }

            Boolean riepilogoPraticaSettato = Boolean.FALSE;
            for (AllegatoDTO allegato : praticaCaricamentoManuale.getAllegatiList()) {
                if (allegato.getModelloPratica()) {
                    riepilogoPraticaSettato = Boolean.TRUE;
                    break;
                }
            }
            Log.APP.info("RiepilogoPraticaSettato: " + riepilogoPraticaSettato);
            if (!riepilogoPraticaSettato) {
                throw new CrossException("Impostare il flag 'Riepilogo Domanda' su un documento");
            }

            LkComuni comune = lookupService.findComuneById(praticaCaricamentoManuale.getComuneSelezionato());
            Enti ente = entiService.findByIdEnte(ruoloSelezionato.getIdEnte().getIdEnte());

            PraticaDTO praticaDTO = new PraticaDTO();
            praticaDTO.setIdPraticaProtocollo(praticaCaricamentoManuale.getIdPraticaProtocollo());
            praticaDTO.setIdEnte(ruoloSelezionato.getIdEnte().getIdEnte());
            praticaDTO.setCodEnte(ruoloSelezionato.getIdEnte().getCodEnte());
            praticaDTO.setOggettoPratica(praticaCaricamentoManuale.getOggetto());
            praticaDTO.setComune(LuoghiSerializer.serialize(comune));
            praticaDTO.setDataRicezione(praticaCaricamentoManuale.getRicezioneData());
            praticaDTO.setProcedimentoPratica(new ProcedimentoDTO(praticaCaricamentoManuale.getProcedimentoSelezionato()));
            praticaDTO.setAllegatiList(praticaCaricamentoManuale.getAllegatiList());

            praticaCaricamentoManuale.getEndoprocedimentiSelezionati();
            String[] protoSplit = praticaCaricamentoManuale.getProtocolloSegnatura().split("/");
            if (protoSplit != null && protoSplit.length == 3) {
                if (Utils.isInteger(protoSplit[1])) {
                    praticaDTO.setRegistro(protoSplit[0]);
                    praticaDTO.setAnnoRiferimento(Integer.valueOf(protoSplit[1]));
                    praticaDTO.setProtocollo(protoSplit[2]);
                }
            }
            praticaDTO.setDataProtocollo(praticaCaricamentoManuale.getProtocolloData());

            if (praticaCaricamentoManuale.getEndoprocedimentiSelezionati() != null) {
                for (Integer procedimentoEnte : praticaCaricamentoManuale.getEndoprocedimentiSelezionati()) {
                    praticaDTO.getProcedimentiList().add(ProcedimentiSerializer.serialize(procedimentiDao.findProcedimentiEntiByID(procedimentoEnte)));
                }
            }

            validator.validate(praticaDTO, result);
            List<String> error = isValid.PraticaProtocollo(result, praticaDTO);
            if (error != null) {
                messages.setMessages(error);
                throw new Exception("Validation Exception");
            }

            ComunicazioneBean comunicazioneBean = caricamentoManualeAction.creaPraticaManuale(praticaDTO, utente);
            PraticheEventi praticaEvento = praticheService.getPraticaEvento(comunicazioneBean.getIdEventoPratica());

            praticheService.startCommunicationProcess(praticaEvento.getIdPratica(), praticaEvento, comunicazioneBean);
//            for (Map.Entry<String, Object> entry : comunicazioneBean.getMessages().entrySet()) {
//                if (entry.getKey().startsWith("EVENTO_SOTTO_PRATICA_")) {
//                    Integer idPraticaEventoSottoPratica = (Integer) entry.getValue();
//                    PraticheEventi praticaEventoSottoPratica = praticheService.getPraticaEvento(idPraticaEventoSottoPratica);
//                    praticheService.startCommunicationProcess(praticaEventoSottoPratica.getIdPratica(), praticaEventoSottoPratica, null);
//                }
//            }

//            PraticheProtocollo praticaProtocollo = caricamentoManualeAction.aggiungiPraticaManuale(praticaDTO);
//            caricamentoManualeAction.transactionalConfermaAnagraficaProtocollo(praticaDTO, utente);
//            return VISUALIZZA_PRATICHE_DA_APRIRE;
//            redirectAttributes.addFlashAttribute("praticaDTO", praticaDTO);
//            redirectAttributes.addFlashAttribute("praticaProtocolloDTO", praticheSerializer.serialize(praticaProtocollo));
//            redirectAttributes.addAttribute("idPraticaProtocollo", praticaProtocollo.getIdProtocollo());
//            return new ModelAndView("redirect:/pratica/nuove/protocollo/step1.htm");
            return new ModelAndView("redirect:/pratiche/nuove/apertura.htm");
        } catch (Exception ex) {
            if (ex instanceof CrossException || messages.getMessages().isEmpty()) {
                String err = (ex instanceof CrossException) ? ex.getMessage() : "Si è verificato un errore non gestito. Si prega di contattare l'assistenza.";
                messages.getMessages().add(err);
            }
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_CARICAMENTO_MANUALE, "errore nell'esecuzione del controller /pratiche/caricamento/manuale", ex, null, utente);
            erroriAction.saveError(errore);
            Log.APP.error(Joiner.on("; ").join(messages.getMessages()), ex);

            if (!Utils.e(praticaCaricamentoManuale.getIdPraticaProtocollo())) {
                redirectAttributes.addAttribute("idPraticaProtocollo", praticaCaricamentoManuale.getIdPraticaProtocollo());
            }
            redirectAttributes.addFlashAttribute("praticaCaricamentoManuale", praticaCaricamentoManuale);
            redirectAttributes.addFlashAttribute("message", messages);
            return new ModelAndView("redirect:/pratiche/nuove/caricamento.htm");
        }
    }

    @RequestMapping("/pratiche/caricamento/daNumeroProtocollo")
    public String daNumeroProtocollo(@ModelAttribute Message message) {
        return CARICAMENTO_DANUMERO;
    }

    @RequestMapping("/pratiche/caricamento/daNumeroProtocolloSubmit")
    public String daNumeroProtocolloSubmit(
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
        try {
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) request.getSession().getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);

            String numeroProtocollo = request.getParameter("numeroProtocollo");
            model.addAttribute("numeroProtocollo", numeroProtocollo);
            boolean error = false;
            if (Utils.e(numeroProtocollo)) {
                error = true;
            } else {
                String[] splitted = numeroProtocollo.split("/");
                if (splitted.length != 2) {
                    error = true;
                } else if (!StringUtils.isNumeric(splitted[1]) && !StringUtils.isNumeric(splitted[0])) {
                    error = true;
                }
            }
            if (error) {
                throw new CrossException("Immettere un numero protocollo della forma 55/2013");
            }
            String[] ss = numeroProtocollo.split("/");
            PraticheProtocollo pratica = praticheProtocolloAction.createPraticaDaProtocollo(Integer.valueOf(ss[1]), ss[0], ruoloSelezionato.getIdEnte().getIdEnte());
            if (pratica == null) {
                throw new CrossException("Impossibile trovare il protocollo riportato.");
            }
            if (pratica.getIdPratica() != null) {
                throw new CrossException("Questa pratica e' gia presente nel sistema.");
            }
            redirectAttributes.addAttribute("idPraticaProtocollo", pratica.getIdProtocollo());
            return "redirect:/pratiche/nuove/caricamento.htm";
//            return selezionaDocumentoDaProtocollo(model, request);
        } catch (Exception ex) {
            Message msg = new Message();
            if (ex instanceof CrossException) {
                msg.setMessages(Arrays.asList(ex.getMessage()));
            } else {
                msg.setMessages(Arrays.asList("Si è verificato un errore: " + ex.getMessage()));
            }
            Log.APP.error("Errore ", ex);
            erroriAction.saveError(erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_CARICAMENTO_DANUMEROPROTOCOLLO, "errore nell'esecuzione del controller /pratiche/caricamento/daNumeroProtocolloSubmit", ex, null, utenteAutenticato));
            redirectAttributes.addFlashAttribute("message", msg);
            return "redirect:/pratiche/caricamento/daNumeroProtocollo.htm";
        }
    }

    @RequestMapping("/documenti/nuovi/protocollo/cancella_documento")
    public String listaDocumentiDaProtocolloCancella(Model model, HttpServletRequest request, HttpServletResponse response) {
        return AJAX;
    }

    @RequestMapping("/documenti/nuovi/protocollo")
    public String listaDocumentiDaProtocollo(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
        clearSession(request.getSession());
        it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
        it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();
        clearSession(request.getSession());

        //String idEnte = request.getParameter("idEnte");
//        if (idEnte != null) {
        //Ho un ente di riferimento selezionato da form
        //session.setAttribute(SessionConstants.ENTE_SELEZIONATO, Integer.valueOf(idEnte));
        Filter filter = (Filter) request.getSession().getAttribute("documentiNuoviProtocollo");
        if (filter == null) {
            filter = new Filter();
        }

        model.addAttribute("enteSelezionato", entePerCuiOpero);
        model.addAttribute("filtroRicerca", filter);

        String listaDocumentiDaProtocollo = HOME_DOCUMENTI_PROTOCOLLO;
        try {
            CustomViews customPraticheProtocolloView = pluginService.getCustomViews(entePerCuiOpero.getIdEnte(), null);
            if (customPraticheProtocolloView != null) {
                String custom = customPraticheProtocolloView.getCustomDocumentiDaProtocolloView();
                if (custom != null && !custom.trim().equals("")) {
                    listaDocumentiDaProtocollo = custom;
                }
            }
        } catch (Exception ex) {
            Log.APP.error("Non è stato possibile caricare il plugin custom per la view delle pratiche da protocollo. Utilizzo il default", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO, "errore nell'esecuzione del controller /documenti/nuovi/protocollo", ex, null, utenteAutenticato);
            erroriAction.saveError(errore);
        }
        return listaDocumentiDaProtocollo;
//        } else {
//            List<EnteDTO> enti = null;
//            try {
//
//                Utente utente = utentiService.getUtenteConnesso(request);
//                enti = entiService.getEntiFromUtente(utente);
//            } catch (Exception ex) {
//                Log.APP.error("Non è stato possibile caricare il plugin custom per la view delle pratiche da protocollo. Utilizzo il default", ex);
//                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO, "errore nell'esecuzione del controller /documenti/nuovi/protocollo", ex);
//                erroriAction.saveError(errore);
//            }
//            if (enti == null) {
//                List<Message> errori = new ArrayList<Message>();
//                String msgError = "Non hai i permessi per accedere alla sezione";
//                Message error = new Message();
//                error.setMessages(Arrays.asList(msgError));
//                request.setAttribute("message", errori);
//                return "redirect:/index.htm";
//            } else if (!enti.isEmpty() && enti.size() > 1) {
//                model.addAttribute("enti", enti);
//                return HOME_DOCUMENTI_SELEZIONE_ENTE_PRINCIPALE;
//            } else {
//                HttpSession session = request.getSession();
//                session.setAttribute(SessionConstants.ENTE_SELEZIONATO, enti.get(0).getIdEnte());
//                Filter filter = (Filter) request.getSession().getAttribute("documentiNuoviProtocollo");
//                if (filter == null) {
//                    filter = new Filter();
//                }
//                model.addAttribute("filtroRicerca", filter);
//                String listaDocumentiDaProtocollo = HOME_DOCUMENTI_PROTOCOLLO;
//                try {
//                    CustomViews customPraticheProtocolloView = pluginService.getCustomViews(enti.get(0).getIdEnte(), null);
//                    if (customPraticheProtocolloView != null) {
//                        String custom = customPraticheProtocolloView.getCustomDocumentiDaProtocolloView();
//                        if (custom != null && !custom.trim().equals("")) {
//                            listaDocumentiDaProtocollo = custom;
//                        }
//                    }
//                } catch (Exception ex) {
//                    Log.APP.error("Non è stato possibile caricare il plugin custom per la view delle pratiche da protocollo. Utilizzo il default", ex);
//                    ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO, "errore nell'esecuzione del controller /documenti/nuovi/protocollo", ex);
//                    erroriAction.saveError(errore);
//                }
//                return listaDocumentiDaProtocollo;
//            }
//        }
    }

    @RequestMapping("/documenti/nuovi/protocollo/ajax")
    public String listaDocumentiDaProtocolloAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        HttpSession session = request.getSession();
        Utente utenteAutenticato = utentiService.getUtenteConnesso(request);
        GridPraticheProtocolloBean json = new GridPraticheProtocolloBean();
        try {
            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) session.getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();

            Enti ente = entiService.findByIdEnte(entePerCuiOpero.getIdEnte());
            json = caricamentoManualeAction.getDocumentiDaProtocollo(request, paginator, ente);
        } catch (ParseException ex) {
            List<Message> errori = new ArrayList<Message>();
            Log.APP.error("Errore reperendo le pratiche", ex);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_NUOVI_PROTOCOLLO_AJAX, "errore nell'esecuzione del controller /documenti/nuovi/protocollo", ex, null, utenteAutenticato);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/documenti/protocollo/pratica")
    public String selezionaPratica(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idProtocollo = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO);
        if (idProtocollo == null) {
            idProtocollo = Integer.valueOf(request.getParameter("idProtocollo"));
        }
        model.addAttribute("id_protocollo", idProtocollo);
        request.getSession().setAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO, idProtocollo);
        return LISTA_PRATICHE_PROTOCOLLO;
    }

    @RequestMapping("/documenti/protocollo/pratica/ajax")
    public String selezionaPraticaAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = caricamentoManualeAction.getPraticheDaAssegnare(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            List<Message> errori = new ArrayList<Message>();
            errori.add(error);
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_AJAX, "errore nell'esecuzione del controller /documenti/protocollo/pratica/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/documenti/protocollo/pratica/eventi")
    public String selezionaEvento(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Integer idPratica;
            String idPraticaString = request.getParameter("idPratica");
            if (!Strings.isNullOrEmpty(idPraticaString)) {
                idPratica = Integer.valueOf(request.getParameter("idPratica"));
            } else {
                idPratica = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO);
            }
            model.addAttribute("idPratica", idPratica);
            request.getSession().setAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO, idPratica);
//            request.getSession().setAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO, idPratica);
        } catch (NumberFormatException e) {
            Utente utente = utentiService.getUtenteConnesso(request);
            Log.APP.error("Caricando0 l'id della pratica", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI, "errore nell'esecuzione del controller /documenti/protocollo/pratica/eventi", e, null, utente);
            erroriAction.saveError(errore);
        }
        return LISTA_PRATICHE_EVENTI_PROTOCOLLO;
    }

    @RequestMapping("/documenti/protocollo/pratica/eventi/ajax")
    public String selezionaEventoAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) throws Exception {
        Integer idPratica = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO);
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = praticheService.getPratica(idPratica);
        try {
            List<EventoDTO> eventi = eventiService.getListaEventi(idPratica);
            List<EventoDTO> eventiDaVisualizzare = new ArrayList<EventoDTO>();
            if (eventi != null && !eventi.isEmpty()) {
                for (EventoDTO evento : eventi) {
                    if (evento.getVerso().equals("INPUT")) {
                        eventiDaVisualizzare.add(evento);
                    }
                }
            }
            GridEventiBean json = new GridEventiBean();
            json.setPage(1);
            json.setRecords(eventi != null ? eventi.size() : 0);
            json.setTotal(1);
            json.setRows(eventiDaVisualizzare);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            Log.APP.error("Caricando la lista degli eventi", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI, "errore nell'esecuzione del controller /documenti/protocollo/pratica/eventi/ajax", e, pratica, utente);
            erroriAction.saveError(errore);
        }
        return AJAX;
    }

    @RequestMapping("/documenti/protocollo/pratica/eventi/visualizza")
    //Comunicazioni in ingresso->Scelgo un documento-> Scelgo la pratica di appartenenza-> Visualizzo tutti gli eventi da scegliere
    public String visualizzaEvento(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer idPratica = (Integer) session.getAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO);
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = praticheService.getPratica(idPratica);
        try {
            session.setAttribute(SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
            Integer idProtocollo = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO);
            PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(idProtocollo);
            Integer idEvento = Integer.valueOf(request.getParameter("idEvento"));
            Enti ente = (Enti) session.getAttribute(SessionConstants.ENTE);
            EventoDTO evento = eventiService.getDettaglioEvento(idEvento, pratica);
            session.setAttribute(SessionConstants.EVENTO_SELEZIONATO, evento);
            UtenteDTO user = utentiService.getUtenteConnessoDTO(request);

            GestioneProtocollo gp = pluginService.getGestioneProtocollo(pratica.getIdProcEnte().getIdEnte().getIdEnte(), null);
            DocumentoProtocolloResponse documentiCaricati = gp.findByProtocollo(praticaProtocollo.getAnnoRiferimento(), praticaProtocollo.getNProtocollo(), pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
            Allegato documentoPrincipale = documentiCaricati.getAllegatoOriginale();
            List<Allegato> allegati = new ArrayList<Allegato>();
            if (documentiCaricati.getAllegati() != null && !documentiCaricati.getAllegati().isEmpty()) {
                allegati = documentiCaricati.getAllegati();
            }
            if (!allegati.contains(documentoPrincipale)) {
                allegati.add(documentoPrincipale);
            }
            List<AllegatoDTO> toAdd = new ArrayList<AllegatoDTO>();
            for (Allegato allegato : allegati) {
                Allegati a = AllegatiSerializer.serialize(allegato);
                allegatiAction.inserisciAllegato(a);
                AllegatoDTO allegatoDaAggiungere = AllegatiSerializer.serialize(a);
                allegatoDaAggiungere.setDaAggiungere(Boolean.TRUE);
                toAdd.add(allegatoDaAggiungere);
            }
            ComunicazioneDTO comunicazione = comunicazioneService.getDettaglioComunicazione(pratica, ente, evento, user);
            for (AllegatoDTO dto : toAdd) {
                comunicazione.getAllegatiDaProtocollo().add(dto);
            }
            comunicazione.setNumeroDiProtocollo(praticaProtocollo.getIdentificativoPratica());
            comunicazione.setDataDiProtocollo(praticaProtocollo.getDataProtocollazione());
            if (praticaProtocollo.getIdMittenteEnte() != null) {
                AttoriComunicazione destinatario = new AttoriComunicazione();
                destinatario.addEnte(praticaProtocollo.getIdMittenteEnte().getIdEnte());
                List<DestinatariDTO> dest = searchService.serializeDestinatari(pratica, destinatario);
                comunicazione.setDestinatari(dest);
            }
            model.addAttribute("comunicazione", comunicazione);
            model.addAttribute("idEvento", idEvento);
            return DETTAGLIO_EVENTO;
        } catch (Exception ex) {
            String err = "Si è verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire";
            Log.APP.error(err, ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_VISUALIZZA, "errore nell'esecuzione del controller /documenti/protocollo/pratica/eventi/visualizza", ex, pratica, utente);
            erroriAction.saveError(errore);
            // erroreInterno = err;
            return LISTA_PRATICHE_EVENTI_PROTOCOLLO;
        }
    }

    @RequestMapping("/documenti/protocollo/pratica/eventi/salva")
    public String salvaEvento(Model model, @ModelAttribute("comunicazione") ComunicazioneDTO comunicazione,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
        Utente utente = utentiService.getUtenteConnesso(request);
        List<String> errors = new ArrayList<String>();
        Pratica pratica = praticheService.getPratica(idPratica);
        Integer idProtocollo = (Integer) request.getSession().getAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO);
        PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(idProtocollo);
        try {
            if (comunicazione.getInviaEmail() != null && comunicazione.getInviaEmail().equalsIgnoreCase("OK")) {
                if (comunicazione.getDestinatariIds() == null || comunicazione.getDestinatariIds().isEmpty()) {
                    errors.add(messageSource.getMessage("error.destinatariEmpty", null, Locale.getDefault()));
                }
                if (Utils.e(comunicazione.getEvento().getOggetto())) {
                    errors.add(messageSource.getMessage("error.comunicazione.oggettoEmpty", null, Locale.getDefault()));
                }
                if (Utils.e(comunicazione.getEvento().getContenuto())) {
                    errors.add(messageSource.getMessage("error.comunicazione.contenutoEmpty", null, Locale.getDefault()));
                }
            }
            if (errors.size() > 0) {
                throw new Exception();
            }

            UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
            Integer idEvento = Integer.valueOf(request.getParameter("idEvento"));
            ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(idEvento);
            try {
                //Prevalorizzo il campo numero di protocollo
                Log.APP.info("Prevalorizzo il numero di protocollo con il valore " + praticaProtocollo.getIdentificativoPratica());
                comunicazione.setNumeroDiProtocollo(praticaProtocollo.getIdentificativoPratica());
                comunicazione.setDataDiProtocollo(praticaProtocollo.getDataProtocollazione());
                if (praticaProtocollo.getIdMittenteEnte() != null) {
                    //Se previsto un ente mittente, lo metto come mittente per registrare l'evento
                    List<String> mittenti = new ArrayList<String>();
                    mittenti.add("E|" + praticaProtocollo.getIdMittenteEnte().getIdEnte());
                    comunicazione.setMittentiIds(mittenti);
                }
                
                ComunicazioneBean cb = action.gestisciEvento(comunicazione, pratica, eventoProcesso, utenteConnesso);
                Integer idEventoPratica = cb.getIdEventoPratica();
                PraticheEventi evento =  praticheService.getPraticaEvento(idEventoPratica);
                praticheProtocolloAction.aggiornaProtocolloEvento(evento, praticaProtocollo.getIdentificativoPratica(), praticaProtocollo.getDataProtocollazione());
                //Aggiorno con il numero di pratica
                praticheProtocolloAction.aggiornaPraticaProtocollo(praticaProtocollo, pratica, utentiService.getUtenteConnesso(request));
            } catch (Exception ex) {
                String err = "Si è verificato un errore cercando di salvare l'evento";
                // erroreInterno = err;
                Log.APP.error(err, ex);
                if (ex.getMessage() != null && !ex.getMessage().equals("")) {
                    errors.add(ex.getMessage());
                } else {
                    errors.add("Si è verificato un errore cercando di salvare l'evento, contattare l'amministratore.");
                }
                Message msg = new Message();
                msg.setMessages(errors);
                model.addAttribute("message", msg);
                model.addAttribute("idEvento", idEvento);
                request.setAttribute("idEvento", idEvento);
                ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_SALVA, "errore nell'esecuzione del controller /documenti/protocollo/pratica/eventi/salva", ex, pratica, utente);
                erroriAction.saveError(errore);
                return REDIRECT_VISUALIZZA_EVENTO;
            }
            return REDIRECT_HOME_DOCUMENTI_PROTOCOLLO;
        } catch (Exception ex) {
            String err = "Si è verificato un errore cercando di salvare l'evento";
            // erroreInterno = err;//^^CS MODIFICA
            Log.APP.error(err, ex);
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_DOCUMENTI_PROTOCOLLO_PRATICA_EVENTI_SALVA, "errore nell'esecuzione del controller /documenti/protocollo/pratica/eventi/salva", ex, pratica, utente);
            erroriAction.saveError(errore);
            return REDIRECT_VISUALIZZA_EVENTO;
        }
    }

    private void clearSession(HttpSession session) {
        session.removeAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO_DOCUMENTO);
        session.removeAttribute(IDENTIFICATIVO_PRATICA_SELEZIONATA_PROTOCOLLO);
        session.removeAttribute(IDENTIFICATIVO_PRATICA_PROTOCOLLO);
        session.removeAttribute(ANAGRAFICHE_PRATICA);
        session.removeAttribute(PRATICA_VALORIZZATA);
        session.removeAttribute(SessionConstants.ENTE_SELEZIONATO);
        session.removeAttribute(SessionConstants.EVENTO_SELEZIONATO);
        session.removeAttribute(SessionConstants.ENTE);
        session.removeAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
        session.removeAttribute("praticheNuoveProtocollo");
        session.removeAttribute("documentiNuoviProtocollo");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    //TODO: verificare se necessario/utilizzato in cross
    @RequestMapping("/pratiche/nuove/anagrafica/aggiungi")
    public String aggiungiAnagrafica(Model model, HttpServletRequest request, HttpServletResponse response) {
        return CERCA_ANAGRAFICA;
    }

    //TODO: verificare se necessario/utilizzato in cross
    @RequestMapping("/pratiche/nuove/anagrafica/aggiungi/ajax")
    public String aggiungiAnagraficaAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridAnagraficaBean json = new GridAnagraficaBean();
        try {
            json = caricamentoManualeAction.getAnagrafiche(request, paginator);
        } catch (Exception e) {
            Message errori = new Message();
            String err = "Errore reperendo le pratiche";
            Log.APP.error(err, e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

}

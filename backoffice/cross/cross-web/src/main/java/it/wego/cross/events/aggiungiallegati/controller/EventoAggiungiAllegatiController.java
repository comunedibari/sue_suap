package it.wego.cross.events.aggiungiallegati.controller;

import it.wego.cross.actions.AllegatiAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.EventiAction;
import it.wego.cross.actions.UtentiAction;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.FileTypes;
import it.wego.cross.controller.AbstractController;
import static it.wego.cross.controller.EventoController.EVENTO_SCELTA;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoDTO;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.ComunicazioniService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.SearchService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author CS
 */
@Controller
public class EventoAggiungiAllegatiController extends AbstractController {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    protected Validator validator;
    @Autowired
    protected SearchService searchService;
    @Autowired
    private WorkFlowService workflowService;
    @Autowired
    private UtentiAction utentiAction;
    @Autowired
    private UtentiService utentiService;
    @Autowired
    ComunicazioniService comunicazioniService;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    EventiAction eventiAction;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    private Mapper mapper;
    @Autowired
    ComunicazioniService comunicazioneService;
    @Autowired
    ErroriAction erroriAction;
    @Autowired
    private AllegatiAction allegatiAction;

    private static final String REDIRECT_DETTAGLIO_PRATICA = "redirect:/pratiche/dettaglio.htm";
    private static final String index = "evento_aggiungiallegati";
    private static final String REDIRECT_INDEX = "redirect:/pratica/aggiungiallegati/index.htm";
    private static final String REDIRECT_EVENTO_SCELTA = "redirect:/pratica/evento/index.htm";

    @RequestMapping("/pratica/aggiungiallegati/index")
    public String index(
            @ModelAttribute("id_pratica_selezionata") Integer idPratica,
            @ModelAttribute("id_evento") Integer idEvento,
            @ModelAttribute("comunicazione") ComunicazioneDTO comunicazione,
            @ModelAttribute("errori") Message message,
            Model model,
            HttpServletRequest request) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            ProcessiEventi processoEvento = praticheService.findProcessiEventi(idEvento);
            String tipoprotocollo = processoEvento.getFlgProtocollazione();
            it.wego.cross.dto.dozer.UtenteDTO utenteDTO = mapper.map(utente, it.wego.cross.dto.dozer.UtenteDTO.class);
            PraticaDTO praticaDTO = mapper.map(pratica, PraticaDTO.class);
            ProcessoEventoDTO processoEventoDTO = mapper.map(praticheService.findProcessiEventi(idEvento), ProcessoEventoDTO.class);

            if (!comunicazione.getInitialized()) {
                comunicazioneService.initializeComunicazione(comunicazione, pratica, processoEvento, utente);
            }
            model.addAttribute("pratica", praticaDTO);
            model.addAttribute("utente", utenteDTO);
            model.addAttribute("processo_evento", processoEventoDTO);
            model.addAttribute("utenteConnesso", utenteDTO);
            model.addAttribute("tipoprotocollo", tipoprotocollo);
            if (comunicazione.getAllegatiManuali() != null) {
                model.addAttribute("allegatiManuali", comunicazione.getAllegatiManuali());
            }

            return index;
        } catch (Exception ex) {
            Log.APP.error("Si e' verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            model.addAttribute("message", new Message(errors));
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazione/index", ex, pratica, utente);
            erroriAction.saveError(err);
            return EVENTO_SCELTA;
        }
    }

    @RequestMapping("/pratica/aggiungiallegati/salva")
    public ModelAndView salvaEvento(
            @ModelAttribute("comunicazione") ComunicazioneDTO comunicazione,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            BindingResult result) {
        List<String> errors = new ArrayList<String>();
        Integer idPratica = comunicazione.getIdPratica();
        Integer idEvento = comunicazione.getIdEvento();
        EventoDTO eventoDaGestire = praticheService.getDettaglioProcessoEvento(idEvento);
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        Message msg = new Message();
        String resultUrl;
        try {
            //Riordino gli allegati eliminando quelli nulli che si sono caricati a seguito dell'eliminazione e del successivo errore
            if (comunicazione.getAllegatiManuali() != null) {
                comunicazione.getAllegatiManuali().removeAll(Collections.singleton(null));
                List<AllegatoDTO> newlist = new ArrayList<AllegatoDTO>();
                for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
                    if (allegato.getPathFile() != null) {
                        String path = allegato.getPathFile();
                        MultipartFile filea = fileUtils.getMultipartFile(allegato.getNomeFile(), path, "allegatoOriginale.file", allegato.getTipoFile(), false, 1024);
                        allegato.setFile(filea);
                        newlist.add(allegato);
                    }
                }
                comunicazione.setAllegatiManuali(newlist);
            }

            Log.APP.info("Dump Submit Evento:\n " + gson.toJson(comunicazione));

            Log.APP.info("Recupero la pratica con id " + idPratica);
            pratica = praticheService.getPratica(idPratica);
            Log.APP.info("Recupero l'evento con id " + idEvento);
            ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(eventoDaGestire.getIdEvento());

            List<ProcessiEventi> steps = workflowService.findAvailableEvents(pratica.getIdPratica());
            if (!steps.contains(eventoProcesso)) {
                String error = messageSource.getMessage("error.comunicazione.eventoNonAmmesso", null, Locale.getDefault());
                errors.add(error);
                return new ModelAndView(REDIRECT_EVENTO_SCELTA);
            }

            validator.validate(comunicazione, result);
            allegatiAction.salvaAllegati(eventoDaGestire, utente, pratica, comunicazione.getAllegatiManuali());

        } catch (Exception ex) {
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_AZIONE_SALVA, "Errore nell'esecuzione del controller /pratica/comunicazione/azione/salva", ex, pratica, utente);
            erroriAction.saveError(err);
            if (ex instanceof CrossException) {
                errors.add(ex.getMessage());
            } else {
                Log.APP.error("Si e' verificato un errore. Ritorno alla schermata della Comunicazione.", ex);
                errors.add(messageSource.getMessage("error.nongestito", null, null, Locale.getDefault()));
            }
        }
        if (errors.isEmpty()) {
            redirectAttributes.addAttribute("id_pratica", idPratica);
            redirectAttributes.addAttribute("active_tab", "#frame6");
            //cancello il path temporaneo dai documenti  che vado a caricare
            if (comunicazione.getAllegatiManuali() != null) {
                for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
                    allegato.setPathFile(null);
                }
            }
            if (comunicazione.getAllegatoOriginale() != null) {
                comunicazione.getAllegatoOriginale().setPathFile(null);
            }
//            fileUtils.cleanTempDirectory(idEnte, idComune);
            msg.setError(Boolean.FALSE);
            resultUrl = REDIRECT_DETTAGLIO_PRATICA;
        } else {
            redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
            redirectAttributes.addAttribute("id_evento", eventoDaGestire.getIdEvento());
            if (comunicazione.getAllegatiManuali() != null) {
                for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
                    allegato.setTipoFileCode(FileTypes.myMap.get(allegato.getTipoFile()));
                    if (allegato.getTipoFileCode() == null) {
                        allegato.setTipoFileCode(FileTypes.myMap.get("default"));
                    }
                }
            }
            redirectAttributes.addFlashAttribute("comunicazione", comunicazione);
            msg.setMessages(errors);
            msg.setError(Boolean.TRUE);
            resultUrl = REDIRECT_INDEX;
        }
        redirectAttributes.addFlashAttribute("message", msg);
        return new ModelAndView(resultUrl);

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionews.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.GenovaConstants;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.dto.dozer.forms.SueInvioWsDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.genova.actions.PluginGenovaAction;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.FormValidatorUtility;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
@Controller
public class InsertPraticaBoGenovaController extends AbstractController {

    private static final String COMUNICAZIONE_INVIA = "comunicazionews_invia";
    private static final String REDIRECT_GESTISCI_PRATICA = "redirect:/pratiche/gestisci.htm";
    private static final Logger log = LoggerFactory.getLogger("plugin");
    @Autowired
    private EventiService eventiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private PluginGenovaAction pluginGenovaAction;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Mapper mapper;

    @RequestMapping("/pratica/insertpraticabogenova/index")
    public ModelAndView index(
            @ModelAttribute("id_pratica_selezionata") Integer idPratica,
            @ModelAttribute("id_evento") Integer idEvento,
            @ModelAttribute SueInvioWsDTO sueInvioWsDTO,
            @ModelAttribute Message message,
            Model model) throws Exception {
//        request.getSession().setAttribute(GenovaConstants.EVENTO_SELEZIONATO, idEvento);

        Pratica praticafull = praticheService.getPratica(idPratica);
        it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(praticafull, it.wego.cross.dto.dozer.PraticaDTO.class);
        model.addAttribute("pratica", praticaDTO);
        model.addAttribute(GenovaConstants.EVENTO_SELEZIONATO, idEvento);
        List<String> anni = praticheService.popolaListaAnni();
        model.addAttribute("anniRiferimento", anni);

        return new ModelAndView(COMUNICAZIONE_INVIA);

    }

    private List<String> verificaSueInvioWsDTO(SueInvioWsDTO sueInvioWsDTO) {
//        List<String> errors = new ArrayList<String>();
        List<String> errori = new ArrayList<String>();

        if (!sueInvioWsDTO.getInvioSueWs()) {

            String codRegistro = sueInvioWsDTO.getCodiceRegistro();
            String codFascicolo = sueInvioWsDTO.getCodiceFascicolo();
            Integer anno = sueInvioWsDTO.getAnnoRiferimento();
            Date dataFascicolo = sueInvioWsDTO.getDataFascicolo();
            String respFascicolo = sueInvioWsDTO.getResponsabileProcedimento();
            String uffFascicolo = sueInvioWsDTO.getUfficioProcedimento();
            List<Character> caratteriNonAmessi = Arrays.asList('#', '§');
//            if (FormValidatorUtility.verificaCampoCompilato(codRegistro, caratteriNonAmessi) != null) {
//                errori.add("Il campo relativo al codice di registro " + FormValidatorUtility.verificaCampoCompilato(codRegistro, caratteriNonAmessi));
//            }
            if (FormValidatorUtility.verificaCampoCompilato(codFascicolo, caratteriNonAmessi) != null) {
                errori.add("Il campo relativo al codice di fascicolo " + FormValidatorUtility.verificaCampoCompilato(codFascicolo, caratteriNonAmessi));
            }
            if (Utils.e(dataFascicolo)) {
                errori.add("Il campo relativo alla data fascicolo non risulta compilato");
            }
            if (Utils.e(anno)) {
                errori.add("Il campo relativo all'anno di riferimento non risulta compilato");
            }
            if (FormValidatorUtility.verificaCampoCompilato(respFascicolo, caratteriNonAmessi) != null) {
                errori.add("Il campo relativo al responsabile di fascicolo " + FormValidatorUtility.verificaCampoCompilato(respFascicolo, caratteriNonAmessi));
            }
            if (FormValidatorUtility.verificaCampoCompilato(uffFascicolo, caratteriNonAmessi) != null) {
                errori.add("Il campo relativo all'ufficio di fascicolo " + FormValidatorUtility.verificaCampoCompilato(uffFascicolo, caratteriNonAmessi));
            }
        }
        return errori;
    }

    @RequestMapping("/pratica/insertpraticabogenova/salva")
    public ModelAndView salva(
            Model model,
            @ModelAttribute SueInvioWsDTO sueInvioWsDTO,
            HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {

            ProcessiEventi eventoProcesso = eventiService.findProcessiEventiById(sueInvioWsDTO.getIdEvento());
            Pratica pratica = praticheService.getPratica(sueInvioWsDTO.getIdPratica());
            Utente utente = utentiService.getUtenteConnesso(request);
            List<String> formErrors = verificaSueInvioWsDTO(sueInvioWsDTO);
            if (formErrors.isEmpty()) {
                EventoBean evento = pluginGenovaAction.salvaEvento(pratica, utente, eventoProcesso, sueInvioWsDTO);
                Pratica praticaUpdated = praticheService.getPratica(pratica.getIdPratica());
//                PraticheEventi lastPraticaEvento = null;
//                for (PraticheEventi praticaEventoLoop : praticaUpdated.getPraticheEventiList()) {
//                    if (lastPraticaEvento == null || praticaEventoLoop.getIdPraticaEvento() > lastPraticaEvento.getIdPraticaEvento()) {
//                        lastPraticaEvento = praticaEventoLoop;
//                    }
//                }
                PraticheEventi praticaEvento = praticheService.getPraticaEvento(evento.getIdEventoPratica());

                praticheService.startCommunicationProcess(praticaUpdated, praticaEvento, evento);

                model.addAttribute("messaggiooperazioneeffettuata", messageSource.getMessage("error.noerror", null, Locale.getDefault()));
                return new ModelAndView(REDIRECT_GESTISCI_PRATICA);
            } else {
                redirectAttributes.addAttribute("id_pratica_selezionata", sueInvioWsDTO.getIdPratica());
                redirectAttributes.addAttribute("id_evento", sueInvioWsDTO.getIdEvento());
                redirectAttributes.addFlashAttribute("sueInvioWsDTO", sueInvioWsDTO);
                redirectAttributes.addFlashAttribute("message", new Message(formErrors));
                return new ModelAndView("redirect:/pratica/insertpraticabogenova/index.htm");
            }
        } catch (Exception ex) {
            String msgError = messageSource.getMessage("genova.sendBoEdilizia.failure", null, Locale.getDefault());
            if (ex instanceof CrossException) {
                msgError = ex.getMessage();
            }
            log.error(msgError, ex);
            redirectAttributes.addAttribute("id_pratica_selezionata", sueInvioWsDTO.getIdPratica());
            redirectAttributes.addAttribute("id_evento", sueInvioWsDTO.getIdEvento());
            redirectAttributes.addFlashAttribute("sueInvioWsDTO", sueInvioWsDTO);
            redirectAttributes.addFlashAttribute("message", new Message(Arrays.asList(msgError)));
            return new ModelAndView("redirect:/pratica/insertpraticabogenova/index.htm");
        }
    }
}

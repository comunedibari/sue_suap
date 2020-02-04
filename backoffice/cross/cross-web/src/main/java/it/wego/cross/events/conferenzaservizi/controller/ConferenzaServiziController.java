/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.conferenzaservizi.controller;

import it.wego.cross.actions.ConferenzaServiziAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.forms.OrganiCollegialiDTO;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.PraticaOrganoCollegialeDTO;
import it.wego.cross.dto.dozer.ProcessoEventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.OrganiCollegialiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author piergiorgio
 */
@Controller
@Scope("session")
public class ConferenzaServiziController extends AbstractController {

    private static final String EVENTO_SCELTA = "evento_scelta";
    private static final String REDIRECT_DETTAGLIO_PRATICA = "redirect:/pratiche/dettaglio.htm";
    private static final String REDIRECT_ATTIVA_CONFERENZA_SERVIZI_INDEX = "redirect:/pratica/attivaConferenzaServizi/index.htm";
    private static final String STATO_ORGANO_COLLEGIALE_ATTIVO = "A";
    @Autowired
    @Qualifier("user")
    private UtenteDTO loggedUser;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private OrganiCollegialiService organiCollegialiService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ConferenzaServiziAction conferenzaServiziAction;

    @RequestMapping("/pratica/attivaConferenzaServizi/index")
    public String index(
            @RequestParam(value = "id_pratica_selezionata", required = true) Integer idPratica,
            @RequestParam(value = "id_evento", required = true) Integer idEvento,
            @ModelAttribute("praticaOrganoCollegiale") PraticaOrganoCollegialeDTO praticaOrganoCollegialeDTO,
            @ModelAttribute("message") Message message,
            Model model,
            HttpServletRequest request) {
        Utente utente = utentiService.getUtente(loggedUser.getIdUtente());
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            ProcessoEventoDTO processoEventoDTO = mapper.map(praticheService.findProcessiEventi(idEvento), ProcessoEventoDTO.class);
            PraticaDTO praticaDTO = mapper.map(pratica, PraticaDTO.class);
            List<OrganiCollegialiDTO> organiCollegialiList = organiCollegialiService.findAllOragniCollegialiOrdered(pratica.getIdEnteProcEnteInteger());
            PraticaOrganoCollegialeDTO pocDTO = new PraticaOrganoCollegialeDTO();
            pocDTO.setIdEvento(idEvento);
            pocDTO.setIdPratica(idPratica);
            pocDTO.setIdOrganiCollegiali(praticaOrganoCollegialeDTO.getIdOrganiCollegiali());
            pocDTO.setDataRichiesta(praticaOrganoCollegialeDTO.getDataRichiesta());
            String tipoprotocollo = processoEventoDTO.getFlgProtocollazione();
            model.addAttribute("processo_evento", processoEventoDTO);
            model.addAttribute("pratica", praticaDTO);
            model.addAttribute("organiCollegialiList", organiCollegialiList);
            model.addAttribute("praticaOrganoCollegiale", pocDTO);
            model.addAttribute("tipoprotocollo", tipoprotocollo);
            return "attivaconferenzaservizi_nuovo_evento";
        } catch (Exception ex) {
            Log.APP.error("Si e' verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            model.addAttribute("message", new Message(errors));
            return EVENTO_SCELTA;
        }
    }

    @RequestMapping("/pratica/attivaConferenzaServizi/azione/salva")
    public ModelAndView salvaEvento(
            @ModelAttribute("praticaOrganoCollegiale") PraticaOrganoCollegialeDTO praticaOrganoCollegialeDTO,
            RedirectAttributes redirectAttributes,
            BindingResult result) throws IOException {
        List<String> errors = new ArrayList<String>();
        Integer idPratica = praticaOrganoCollegialeDTO.getIdPratica();
        Integer idEvento = praticaOrganoCollegialeDTO.getIdEvento();
        Pratica pratica = null;
        Message msg = new Message();
        String resultUrl;
        try {
            Log.APP.info("Recupero la pratica con id " + idPratica);
            pratica = praticheService.getPratica(idPratica);
            praticaOrganoCollegialeDTO.setCodiceStatoPraticaOrganiCollegiali(STATO_ORGANO_COLLEGIALE_ATTIVO);
            if (praticaOrganoCollegialeDTO.getIdOrganiCollegiali() == null) {
                errors.add(messageSource.getMessage("conferenzaservizi.error.oranganocollegiale.empty", null, Locale.getDefault()));
            }
            if (praticaOrganoCollegialeDTO.getDataRichiesta() == null) {
                errors.add(messageSource.getMessage("conferenzaservizi.error.dataattivazione.empty", null, Locale.getDefault()));
            }
            if (errors.isEmpty()) {
                EventoBean eventoBean = conferenzaServiziAction.gestisciEvento(praticaOrganoCollegialeDTO, loggedUser);
                PraticheEventi praticaEvento = praticheService.getPraticaEvento(eventoBean.getIdEventoPratica());
                
                praticheService.startCommunicationProcess(praticaEvento.getIdPratica(), praticaEvento, eventoBean);
                
                redirectAttributes.addAttribute("id_pratica", idPratica);
                redirectAttributes.addAttribute("active_tab", "#frame6");
                msg.setError(Boolean.FALSE);
                resultUrl = REDIRECT_DETTAGLIO_PRATICA;
            } else {
                redirectAttributes.addFlashAttribute("praticaOrganoCollegiale", praticaOrganoCollegialeDTO);
                redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
                redirectAttributes.addAttribute("id_evento", idEvento);
                msg.setError(Boolean.TRUE);
                msg.setMessages(errors);
                resultUrl = REDIRECT_ATTIVA_CONFERENZA_SERVIZI_INDEX;
            }
        } catch (Exception ex) {
            if (ex instanceof CrossException) {
                errors.add(ex.getMessage());
            } else {
                Log.APP.error("Si e' verificato un errore. Ritorno alla schermata della Conferenza dei servizi.", ex);
                errors.add(messageSource.getMessage("error.nongestito", null, null, Locale.getDefault()));
            }
            redirectAttributes.addFlashAttribute("praticaOrganoCollegiale", praticaOrganoCollegialeDTO);
            redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
            redirectAttributes.addAttribute("id_evento", idEvento);
            msg.setMessages(errors);
            msg.setError(Boolean.TRUE);
            resultUrl = REDIRECT_ATTIVA_CONFERENZA_SERVIZI_INDEX;
        }
        redirectAttributes.addFlashAttribute("message", msg);
        return new ModelAndView(resultUrl);

    }
}

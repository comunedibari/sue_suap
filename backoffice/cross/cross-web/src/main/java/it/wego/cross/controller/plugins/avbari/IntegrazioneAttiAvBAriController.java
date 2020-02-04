/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller.plugins.avbari;

import it.wego.cross.avbari.actions.AttiDeterminaAction;
import it.wego.cross.avbari.beans.form.RequestFormAttiBean;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.PraticheService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import it.wego.cross.utils.Log;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author giuseppe
 */
@Controller
@Scope("session")
public class IntegrazioneAttiAvBAriController extends AbstractController {

    private static final String EVENTO_SCELTA = "evento_scelta";
    private static final String INTEGRAZIONE_ATTI_INDEX = "integrazioneAttiAvBari_index";
    private static final String REDIRECT_DETTAGLIO_PRATICA = "redirect:/pratiche/dettaglio.htm";
    private static final String REDIRECT_INTEGRAZIONE_ATTI_INDEX = "redirect:/pratica/integrazioneAttiAvBari/index.htm";
    @Autowired
    @Qualifier("user")
    private UtenteDTO loggedUser;
    @Autowired
    private Mapper mapper;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AttiDeterminaAction attiDeterminaAction;

    @RequestMapping("/pratica/integrazioneAttiAvBari/index")
    public String index(@RequestParam(value = "id_pratica_selezionata", required = true) Integer idPratica,
            @RequestParam(value = "id_evento", required = true) Integer idEvento,
            @ModelAttribute("datiRichiestaAtti") RequestFormAttiBean requestFormAttiBean,
            @ModelAttribute("message") Message message,
            Model model,
            HttpServletRequest request) {
        Utente utente = utentiService.getUtente(loggedUser.getIdUtente());
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            PraticaDTO praticaDTO = mapper.map(pratica, PraticaDTO.class);
            ProcessoEventoDTO processoEventoDTO = mapper.map(praticheService.findProcessiEventi(idEvento), ProcessoEventoDTO.class);
            String tipoprotocollo = processoEventoDTO.getFlgProtocollazione();
            requestFormAttiBean.setIdPratica(idPratica);
            requestFormAttiBean.setIdEvento(idEvento);
            Integer year = Calendar.getInstance().get(Calendar.YEAR);
            if (requestFormAttiBean.getAnnoRiferimento() == null) {
                requestFormAttiBean.setAnnoRiferimento(String.valueOf(year));
            }
            model.addAttribute("processo_evento", processoEventoDTO);
            model.addAttribute("pratica", praticaDTO);
            model.addAttribute("tipoprotocollo", tipoprotocollo);
            model.addAttribute("datiRichiestaAtti", requestFormAttiBean);
            return INTEGRAZIONE_ATTI_INDEX;
        } catch (Exception ex) {
            Log.APP.error("Si e' verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            model.addAttribute("message", new Message(errors));
            return EVENTO_SCELTA;
        }
    }

    @RequestMapping("/pratica/integrazioneAttiAvBari/azione/salva")
    public ModelAndView salvaEvento(
            @ModelAttribute("datiRichiestaAtti") RequestFormAttiBean requestFormAttiBean,
            RedirectAttributes redirectAttributes,
            BindingResult result) throws IOException {
        List<String> errors = new ArrayList<String>();
        Integer idPratica = requestFormAttiBean.getIdPratica();
        Integer idEvento = requestFormAttiBean.getIdEvento();
        Utente utente = utentiService.getUtente(loggedUser.getIdUtente());
        Pratica pratica = null;
        Message msg = new Message();
        String resultUrl;
        try {
            Log.APP.info("Recupero la pratica con id " + idPratica);
            pratica = praticheService.getPratica(idPratica);

            if (requestFormAttiBean.getAnnoRiferimento() == null
                    || !requestFormAttiBean.getAnnoRiferimento().trim().matches("[0-9]{4}")) {
                errors.add(messageSource.getMessage("avbari.error.annodetermina", null, Locale.getDefault()));
            }
            if (requestFormAttiBean.getNumeroRiferimento() == null
                    || !requestFormAttiBean.getNumeroRiferimento().trim().matches("[0-9]+")) {
                errors.add(messageSource.getMessage("avbari.error.numerodetermina", null, Locale.getDefault()));
            }
            if (errors.isEmpty()) {
                EventoBean eventoBean = new EventoBean();
                eventoBean.getMessages().put("annoRiferimento", requestFormAttiBean.getAnnoRiferimento().trim());
                eventoBean.getMessages().put("numeroRiferimento", requestFormAttiBean.getNumeroRiferimento().trim());
                ProcessiEventi processoEvento = praticheService.findProcessiEventi(idEvento);
                eventoBean.setIdPratica(pratica.getIdPratica());
                eventoBean.setIdUtente(utente.getIdUtente());
                eventoBean.setIdEventoProcesso(processoEvento.getIdEvento());
                eventoBean.setVisibilitaCross(Boolean.TRUE);
                attiDeterminaAction.execute(eventoBean);
                redirectAttributes.addAttribute("id_pratica", idPratica);
                redirectAttributes.addAttribute("active_tab", "#frame6");
                msg.setError(Boolean.FALSE);
                resultUrl = REDIRECT_DETTAGLIO_PRATICA;
            } else {
                redirectAttributes.addFlashAttribute("datiRichiestaAtti", requestFormAttiBean);
                redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
                redirectAttributes.addAttribute("id_evento", idEvento);
                msg.setError(Boolean.TRUE);
                msg.setMessages(errors);
                resultUrl = REDIRECT_INTEGRAZIONE_ATTI_INDEX;
            }
        } catch (Exception ex) {
            if (ex instanceof CrossException) {
                errors.add(ex.getMessage());
            } else {
                Log.APP.error("Si e' verificato un errore. Ritorno alla schermata di gestione evento.", ex);
                errors.add(messageSource.getMessage("error.nongestito", null, null, Locale.getDefault()));
            }
            redirectAttributes.addFlashAttribute("datiRichiestaAtti", requestFormAttiBean);
            redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
            redirectAttributes.addAttribute("id_evento", idEvento);
            msg.setMessages(errors);
            msg.setError(Boolean.TRUE);
            resultUrl = REDIRECT_INTEGRAZIONE_ATTI_INDEX;
        }
        redirectAttributes.addFlashAttribute("message", msg);
        return new ModelAndView(resultUrl);

    }

}

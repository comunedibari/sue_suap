/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Strings;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.EventiAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author CS
 */
@Controller
public class EventoController extends AbstractController {

    public static final String EVENTO_SCELTA = "evento_scelta";
    public static final String REDIRECT_HOME_PAGE = "redirect:/pratiche/gestisci.htm";
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private EventiAction eventiAction;

    @RequestMapping("/pratica/evento/index")
    public String nuovaComunicazione(Model model,
            @RequestParam(value = "id_pratica", required = false) Integer idPratica,
            @RequestParam(value = "verso", required = false) String verso,
            @RequestParam(value = "id_protocollo", required = false) Integer idProtocollo,
            HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            HttpSession session = request.getSession();
            if (idPratica == null) {
                idPratica = (Integer) session.getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            }
            if (session.getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA) == null){
                session.setAttribute(SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
            }
            pratica = praticheService.getPratica(idPratica);
            List<EventoDTO> eventi = eventiService.getListaEventi(pratica.getIdPratica(), verso);
//            pratica.setProtocollo(pratica.getProtocollo() + "/" + pratica.getAnnoRiferimento());
            model.addAttribute("eventi", eventi);
            model.addAttribute("pratica", pratica);
            model.addAttribute("verso", verso);
            model.addAttribute("id_protocollo", idProtocollo);
            return EVENTO_SCELTA;
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di creare un evento sulla pratica", ex);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_EVENTO_INDEX, "Errore nell'esecuzione del controller /pratica/evento/index", ex, pratica, utente);
            erroriAction.saveError(err);
            return REDIRECT_HOME_PAGE;
        }
    }

    @RequestMapping("/pratica/evento/crea")
    public String creaEvento(Model model, HttpServletRequest request, HttpServletResponse response) {
        Pratica pratica = null;
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            Integer idEvento = Integer.valueOf(request.getParameter("id_evento"));
            ProcessiEventi eventoProcesso = workFlowService.findProcessiEventiById(idEvento);
            pratica = praticheService.getPratica(idPratica);
            String verso = request.getParameter("verso");
            String versoParam = !Strings.isNullOrEmpty(verso) ? "&verso=" + verso : "";
            String idProtocollo = request.getParameter("id_protocollo");
            String idProtocolloParam = !Strings.isNullOrEmpty(verso) ? "&id_protocollo=" + idProtocollo : "";
            if (Utils.e(eventoProcesso.getFunzioneApplicativa()) || Constants.NOP_EVENT.equalsIgnoreCase(eventoProcesso.getFunzioneApplicativa())) {
                EventoBean eventoBean = new EventoBean();
                eventoBean.setIdPratica(idPratica);
                eventoBean.setIdEventoProcesso(idEvento);
                eventoBean.setIdUtente(utente.getIdUtente());
                eventiAction.gestisciProcessoEvento(eventoBean);
                return "redirect:/pratiche/dettaglio.htm?id_pratica=" + idPratica + versoParam + idProtocolloParam;
            } else {
                return "redirect:/pratica/" + eventoProcesso.getFunzioneApplicativa() + "/index.htm?" + SessionConstants.ID_PRATICA_SELEZIONATA + "=" + idPratica + "&id_evento=" + idEvento + versoParam + idProtocolloParam;
            }
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            Log.APP.error("Si è verificato un errore cercando di creare un evento sulla pratica", ex);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_EVENTO_INDEX, "Errore nell'esecuzione del controller /pratica/evento/crea", ex, pratica, utente);
            erroriAction.saveError(err);
            return EventoController.EVENTO_SCELTA;
        }
    }
}

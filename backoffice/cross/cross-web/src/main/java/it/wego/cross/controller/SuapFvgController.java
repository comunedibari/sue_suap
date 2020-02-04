/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.SuapFvgAction;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.Utente;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.utils.Log;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author giuseppe
 */
@Controller
public class SuapFvgController extends AbstractController {

    private static final String INDEX = "suapfvg_index";

    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private SuapFvgAction suapFvgAction;
    @Autowired
    private PraticheProtocolloService praticheProtocolloService;

    @RequestMapping("/suapfvg/index")
    public String nuove(Model model, HttpServletRequest request, HttpServletResponse response) {
        return INDEX;
    }

    @RequestMapping("/suapfvg/upload")
    public ModelAndView upload(
            Model model,
            @RequestParam("filePratica") MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            PraticheProtocollo praticaProtocollo;

            it.wego.cross.dto.dozer.UtenteRuoloEnteDTO ruoloSelezionato = (it.wego.cross.dto.dozer.UtenteRuoloEnteDTO) request.getSession().getAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            it.wego.cross.dto.dozer.EnteDTO entePerCuiOpero = ruoloSelezionato.getIdEnte();

            String identificativoPratica = file.getOriginalFilename().replaceAll(".pdf.p7m", "");
            praticaProtocollo = praticheProtocolloService.findPraticaProtocolloByIdentificativoPratica(identificativoPratica);
            if (praticaProtocollo != null) {
                if ("IN_CARICO".equalsIgnoreCase(praticaProtocollo.getStato())) {
                    throw new Exception("Il file caricato è già stato utilizzato per creare una pratica.");
                } else {
                    Message message = new Message(Boolean.TRUE, Arrays.asList("Il file caricato fa riferimento alla seguente pratica in staging."));
                    message.setWarning(Boolean.TRUE);
                    redirectAttributes.addFlashAttribute(message);
                }
            } else {
                praticaProtocollo = suapFvgAction.salvaPratica(file, entePerCuiOpero, utente);
            }

            redirectAttributes.addAttribute("idPraticaProtocollo", praticaProtocollo.getIdProtocollo());
            return new ModelAndView("redirect:/pratiche/nuove/caricamento.htm");

        } catch (Exception ex) {
            Log.APP.error("Errore durante il salvataggio della pratica Suap in RETE", ex);
            Message error = new Message();
            error.setMessages(Arrays.asList(ex.getMessage()));
            request.setAttribute("message", error);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD, "Errore nell'esecuzione del controller /comunica/upload", ex, null, utente);
            erroriAction.saveError(erroredto);
            return new ModelAndView(INDEX);
        }
    }

}

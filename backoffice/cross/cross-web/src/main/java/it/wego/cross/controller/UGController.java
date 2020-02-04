/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dto.dozer.UtenteRuoloEnteDTO;
import it.wego.cross.entity.Utente;
import it.wego.cross.interceptor.SelectUGInterceptor;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Gabriele
 */
@Controller
public class UGController extends AbstractController {

    public static final String UG_SELECT_CURRENT_VIEW = "ug_select_current_view";

    @Autowired
    ErroriAction erroriAction;

    @RequestMapping("/ug/select_current")
    public String selectCurrentUG(
            Model model,
            @RequestParam(value = "previous_url", required = true) String previousUrl,
            HttpServletRequest request) throws Exception {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            HttpSession session = request.getSession();

            List<it.wego.cross.dto.dozer.EnteDTO> entiPerCuiOpero = new ArrayList<it.wego.cross.dto.dozer.EnteDTO>();
            it.wego.cross.dto.dozer.UtenteDTO utenteDTO = (it.wego.cross.dto.dozer.UtenteDTO) session.getAttribute(SessionConstants.UTENTE_CONNESSO_FULL);

            UtenteRuoloEnteDTO ruoloPerCuiOpero = null;
            for (UtenteRuoloEnteDTO ruoloDto : utenteDTO.getUtenteRuoloEnteList()) {
                if (!entiPerCuiOpero.contains(ruoloDto.getIdEnte())) {
                    entiPerCuiOpero.add(ruoloDto.getIdEnte());
                    ruoloPerCuiOpero = ruoloDto;
                }
            }

            //se c'è una sola unità di gestione, nn tedio l'utente chiedendo di confermarmela
            if (entiPerCuiOpero.size() == 1) {
                session.setAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG, ruoloPerCuiOpero);
                return "redirect:" + Utils.decodeB64(previousUrl);
            } else {
                model.addAttribute("previousUrl", previousUrl);
                model.addAttribute("enti", entiPerCuiOpero);
                return UG_SELECT_CURRENT_VIEW;
            }

        } catch (Exception ex) {
            erroriAction.saveError(erroriAction.getError(it.wego.cross.constants.Error.ERRORE_UG_SELECT, "Errore nell'esecuzione del controller /ug/select_current", ex, null, utente));
            return ErroriController.INTERNAL_SERVER_ERRROR;
        }

    }

    @RequestMapping("/ug/select_current_submit")
    public ModelAndView selectCurrentUGSubmit(
            Model model,
            @RequestParam(value = "previousUrl") String previousUrl,
            @RequestParam(value = "idEnte") Integer idEnte,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            HttpSession session = request.getSession();

            it.wego.cross.dto.dozer.UtenteDTO utenteDTO = (it.wego.cross.dto.dozer.UtenteDTO) session.getAttribute(SessionConstants.UTENTE_CONNESSO_FULL);

            session.removeAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG);
            
            for (UtenteRuoloEnteDTO ruoloDto : utenteDTO.getUtenteRuoloEnteList()) {
                if (ruoloDto.getIdEnte().getIdEnte().equals(idEnte)) {
                    session.setAttribute(SelectUGInterceptor.CURRENT_SELECTED_UG, ruoloDto);
                    break;
                }
            }
            
            return new ModelAndView("redirect:" + Utils.decodeB64(previousUrl));
        } catch (Exception ex) {
            erroriAction.saveError(erroriAction.getError(it.wego.cross.constants.Error.ERRORE_UG_SELECT, "Errore nell'esecuzione del controller /ug/select_current_submit", ex, null, utente));

            redirectAttributes.addAttribute("previous_url", previousUrl);
            return new ModelAndView(UG_SELECT_CURRENT_VIEW);
        }

    }
}

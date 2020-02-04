/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.collegaanagrafica.controller;

import it.wego.cross.controller.AbstractController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author giuseppe
 */
@Controller
public class CollegaAnagraficaController extends AbstractController{
    
    private static final String index = "collegaanagrafica_view";
    
    @RequestMapping("/pratica/collegaanagrafica/index")
    public String index(Model model, @ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        return index;
    }
    
}

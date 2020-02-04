package it.wego.cross.controller;


import it.wego.cross.service.ConfigurationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Controller
public class ResetConfigurazione {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping("/resetConfigurazione")
    public String resetConfigurazione(Model model, HttpServletRequest request, HttpServletResponse response) {
        configurationService.resetCachedConfiguration();
        return "reset-ok";
    }
    
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/services";
    }
}

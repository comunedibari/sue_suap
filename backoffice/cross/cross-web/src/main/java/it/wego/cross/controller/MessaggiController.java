/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author giuseppe
 */
@Controller
public class MessaggiController extends AbstractController {
    
    private static final String MESSAGGI_INDEX = "messaggi_index";
    
    @RequestMapping("/messaggi/index")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return MESSAGGI_INDEX;
    }
    
}

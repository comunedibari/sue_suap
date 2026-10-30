/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.wego.cross.constants.SessionConstants;
import it.wego.cross.entity.Avviso;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.AvvisoService;
import it.wego.cross.session.MultiSubSessionManager;
import it.wego.cross.utils.Log;

/**
 *
 * @author CS
 */
@Controller
public class Index extends AbstractController {
	
	@Autowired
	AvvisoService avvisiService;

    @RequestMapping("/index")
    public String cross_index(Model model, HttpServletRequest request, HttpServletResponse response) throws CrossException {
    	HttpSession session = request.getSession();
    	Object attribute = session != null? session.getAttribute(SessionConstants.FROM_LOGIN): null;
    	if(session == null || attribute == null || (boolean) attribute) {
    		if(session != null)
    			session.setAttribute(SessionConstants.FROM_LOGIN, false);
    		model.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.generateId());
    	}
    		
    	//Aggiunto il 25/01/2016   	
    	try {
    		List<Avviso> avvisiList= new ArrayList<Avviso>();  
        	Date dateNow=new Date();
			avvisiList=avvisiService.findAvvisiNonScaduti(dateNow);
			request.setAttribute("avvisiList", avvisiList);
		} catch (Exception e) {
			Log.SQL.error("Errore nella chiamata del metoto avvisiService.findAvvisiNonScaduti" +e);
			e.printStackTrace();
		}
    	
    	
        return "cross_index";
    }
}

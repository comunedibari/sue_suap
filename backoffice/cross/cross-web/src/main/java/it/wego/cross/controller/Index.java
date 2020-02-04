/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.entity.Avviso;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.AvvisoService;
import it.wego.cross.utils.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

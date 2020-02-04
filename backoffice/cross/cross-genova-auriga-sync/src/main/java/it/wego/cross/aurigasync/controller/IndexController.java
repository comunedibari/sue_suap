/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.aurigasync.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import it.wego.cross.aurigasync.bean.Response;
import it.wego.cross.aurigasync.service.AurigaSyncService;

/**
 *
 * @author Giuseppe
 */
@Controller
public class IndexController {

    @Autowired
    private AurigaSyncService aurigaSyncService;
    private static final Logger log = LoggerFactory.getLogger("aurigasync");

    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @RequestMapping("/start")
    public @ResponseBody
    Response startSync(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = true) boolean deleteFile) {
        Response response = new Response();
        try {
            log.info("[START] aggiornamentoDocumenti");
            aurigaSyncService.startSynchronization(startDate, endDate, deleteFile, false);
            log.info("[END] aggiornamentoDocumenti");
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Operazione eseguita correttamente");
        } catch (Exception ex) {
            response.setMessage("Si è verificato un errore: " + ex.getMessage());
            log.error("Errore avviando la sincronizzazione", ex);
        }
        return response;
    }

    @RequestMapping("/simulate")
    public @ResponseBody
    Response simulateSync(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = true) boolean deleteFile) {
        Response response = new Response();
        try {
            log.info("[START] aggiornamentoDocumenti");
            aurigaSyncService.startSynchronization(startDate, endDate, deleteFile, true);
            log.info("[END] aggiornamentoDocumenti");
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Operazione eseguita correttamente");
        } catch (Exception ex) {
            response.setMessage("Si è verificato un errore: " + ex.getMessage());
            log.error("Errore avviando la simulazione", ex);
        }
        return response;
    }
}

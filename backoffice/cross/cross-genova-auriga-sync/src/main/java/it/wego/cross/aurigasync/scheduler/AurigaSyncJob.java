/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.aurigasync.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.wego.cross.aurigasync.service.AurigaSyncService;

/**
 *
 * @author Giuseppe
 */
public class AurigaSyncJob implements Job {
    
    @Autowired
    private AurigaSyncService aurigaSyncService;
    private static final Logger log = LoggerFactory.getLogger("aurigasync");
    private static final boolean DELETE_FILE = true;
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        try {
            log.info("[START] aggiornamentoDocumenti");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar yesterdayDate = Calendar.getInstance();
            yesterdayDate.add(Calendar.DATE, -1);
            String yesterday = dateFormat.format(yesterdayDate.getTime());
            Calendar tomorrowDate = Calendar.getInstance();
            tomorrowDate.add(Calendar.DATE, +1);
            String tomorrow = dateFormat.format(tomorrowDate.getTime());
            log.info("Sincronizzo i documenti caricati tra " + yesterday + " e " + tomorrow);
            aurigaSyncService.startSynchronization(yesterday, tomorrow, DELETE_FILE, true);
            log.info("[END] aggiornamentoDocumenti");
        } catch (Exception ex) {
            log.error("Errore creando la data", ex);
        }
    }
    
}

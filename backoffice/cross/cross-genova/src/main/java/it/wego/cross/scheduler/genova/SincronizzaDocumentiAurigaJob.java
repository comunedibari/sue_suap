/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.scheduler.genova;

import it.wego.cross.genova.actions.PluginGenovaAction;
import it.wego.cross.utils.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Giuseppe
 */
public class SincronizzaDocumentiAurigaJob implements Job {

    @Autowired
    private PluginGenovaAction pluginGenovaAction;
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Log.SCHEDULER.info("Avvio scheduler recupero documenti Auriga...");
        Log.SCHEDULER.info("Ricerca pratiche con allegati da aggiornare");
//        pluginGenovaAction.aggiornaAllegatiPerAuriga(false);
    }
}

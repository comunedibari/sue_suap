/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.scheduler;

import it.wego.cross.utils.Log;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Override necessario per permettere la corretta chiusura dei thread degli scheduler
 * Corregge l'errore nei log di Tomcat:
 * The web application [/cross] appears to have started a thread named [scheduler_Worker-1] but has failed to stop it. This is very likely to create a memory leak.
 * 
 * https://jira.terracotta.org/jira/browse/QTZ-192
 * @author Giuseppe
 */
public class CrossSchedulerFactoryBean extends SchedulerFactoryBean {

    private static final int SHUTDOWN_TIMEOUT = 2000;

    @Override
    public void destroy() throws SchedulerException {
        Log.SCHEDULER.debug("Start shutdown of Quartz scheduler factory bean");
        super.destroy();
        try {
            Log.SCHEDULER.debug("wait " + SHUTDOWN_TIMEOUT + " ms to shutdown Quartz");
            Thread.sleep(SHUTDOWN_TIMEOUT);
            Log.SCHEDULER.debug("Quartz scheduler shutdown completed");
        } catch (InterruptedException e) {
            Log.SCHEDULER.error("", e);
        }
    }
}

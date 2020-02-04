/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Giuseppe
 */
@Component
public class SchedulerAction {

    @Autowired
    private PraticheProtocolloService praticheProtocolloService;

    @Transactional(rollbackFor = Exception.class)
    public void salva(PraticheProtocollo praticaProtocollo) throws Exception {
        Log.SCHEDULER.info("Inserisco il record in banca dati...");
        praticheProtocolloService.inserisci(praticaProtocollo);
        Log.SCHEDULER.info("... fatto!");
    }

}

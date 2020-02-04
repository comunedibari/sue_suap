/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.sit.events;

import it.wego.cross.avbari.actions.InvioPraticaSitAction;
import it.wego.cross.avbari.beans.ResponseSitBean;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio
 */
@Repository("integrazioneSITAvBari")
public class IntegrazioneSitAvBari extends AbstractEvent {

    @Autowired
    private InvioPraticaSitAction invioPraticaSitAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Log.APP.info("Inizio metodo integrazione SIT AV Bari");
        PraticheEventi praticaEvento = praticheService.getPraticaEvento(eb.getIdEventoPratica());
        ResponseSitBean res = invioPraticaSitAction.execute(praticaEvento);
        if (!res.isOperazioneOK()) {
            throw new Exception(res.getMessaggio());
        }
        Log.APP.info("Fine metodo integrazione SIT AV Bari");
    }

}

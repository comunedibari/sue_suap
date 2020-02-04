/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events;

import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Pratica;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gabriele
 */
@Repository("nop_event")
public class NopEvent extends AbstractEvent {

    @Autowired
    private PraticheAction praticheAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        ComunicazioneBean cb = new ComunicazioneBean(eb);
        praticheAction.updateStatoPratica(pratica, cb);
        praticheAction.updateScadenzePratica(pratica, cb);
    }

}

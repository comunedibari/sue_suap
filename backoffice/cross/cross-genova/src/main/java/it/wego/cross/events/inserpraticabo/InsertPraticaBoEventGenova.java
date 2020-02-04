/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.inserpraticabo;

import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Pratica;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuseppe
 */
@Repository("insertpraticabogenova")
public class InsertPraticaBoEventGenova extends AbstractEvent {

    @Autowired
    private PraticheAction praticheAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        ComunicazioneBean cb = new ComunicazioneBean();
        cb.setIdEventoPratica(eb.getIdEventoPratica());
        cb.setIdEventoProcesso(eb.getIdEventoProcesso());
        cb.setDataEvento(eb.getDataEvento());
        praticheAction.updateStatoPratica(pratica, cb);
        praticheAction.updateScadenzePratica(pratica, cb);
    }

}

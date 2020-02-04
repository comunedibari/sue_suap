/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.chiusuracommerciogenova;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Pratica;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.plugins.genova.commercio.actions.ChiusuraPraticaAction;
import it.wego.cross.plugins.genova.commercio.bean.ResponseWsBean;
import it.wego.cross.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio
 */
@Repository("chiusuraCommercioGenova")
public class ChiusuraCommercioGenova extends AbstractEvent {

    @Autowired
    private ChiusuraPraticaAction chiusuraPraticaAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Log.APP.info("Inizio metodo ChiusuraCommercio Genova");
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        ResponseWsBean res = chiusuraPraticaAction.execute(pratica);
        if (!res.isOperazioneOK()) {
            throw new Exception(res.getMessaggio());
        }
        Log.APP.info("Fine metodo ChiusuraCommercio Genova");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.factory;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;

/**
 *
 * @author Giuseppe
 */
public class EventCreator {

    public static EventoBean createEventoBean(ProcessiEventi evento) {
        String tipoEvento = evento.getFunzioneApplicativa();
        //Maledetto Java6 e il mancato supporto agli switch sulle stringhe...
        if ("comunicazione".equals(tipoEvento)) {
            return new ComunicazioneBean();
        } else {
            return new EventoBean();
        }
    }
}

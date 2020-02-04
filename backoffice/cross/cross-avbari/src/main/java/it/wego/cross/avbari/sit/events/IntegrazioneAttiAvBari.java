/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.avbari.sit.events;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.events.AbstractEvent;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio
 */
@Repository("integrazioneAttiAvBari")
public class IntegrazioneAttiAvBari extends AbstractEvent {

    @Override
    public void execute(EventoBean eb) throws Exception {

    }

}

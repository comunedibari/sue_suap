/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.event;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.events.AbstractEvent;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Beppe
 */
@Repository("comunicazionerea_rifiutata")
public class PraticaRifiutataEvent extends AbstractEvent {

    @Override
    public void execute(EventoBean eb) throws Exception {
    }
    
}

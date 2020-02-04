/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.test;

import it.wego.cross.events.AbstractEvent;
import it.wego.cross.beans.EventoBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gabriele
 */
@Repository("test")
public class TestEvent extends AbstractEvent {

    @Override
    public void execute(EventoBean eb) throws Exception {
        System.out.println("EVENTO APPLICATIVO TEST");
    }

}

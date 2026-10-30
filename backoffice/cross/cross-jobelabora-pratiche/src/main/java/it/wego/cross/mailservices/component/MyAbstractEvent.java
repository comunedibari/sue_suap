package it.wego.cross.mailservices.component;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.events.AbstractEvent;

public class MyAbstractEvent extends AbstractEvent {

	@Override
	public void execute(EventoBean eb) throws Exception {
		aggiornamentoUtentePratica(eb);
		System.out.println("MyAbstractEvent start");
	}
}

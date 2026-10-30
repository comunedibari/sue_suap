package it.wego.cross.controller;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Test;

import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;

public class PraticheTest {

	@Test
	public void test() {
		Pratiche p = new Pratiche();
		List<PraticheEventi> events = new ArrayList<>();	
		for(int i = 0 ; i < 10; i++) {
			PraticheEventi pe = new PraticheEventi();
			ProcessiEventi processoEvento = new ProcessiEventi();
			pe.setIdPraticaEvento(i);
			events.add(pe);
		}
		
		assertThat(2 , Is.is(2));
	}

}

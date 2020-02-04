package it.exprivia.pal.avbari.suapsue.service.test;

import static org.junit.Assert.*;
import it.exprivia.pal.avbari.suapsue.dto.FiltriRicercaPratica;
import it.exprivia.pal.avbari.suapsue.dto.Pratica;
import it.exprivia.pal.avbari.suapsue.service.PraticaService;
import it.exprivia.pal.avbari.suapsue.service.ServiceLocator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

public class PraticaServiceTest {
	
	private static PraticaService service;
	
	private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@BeforeClass
	public static void init() {
		try {
			service = ServiceLocator.getPraticaService();
		} catch (Throwable e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testFind() {
		try {
			FiltriRicercaPratica filtri = new FiltriRicercaPratica();
			filtri.setIdSportello(new Long(1));
			filtri.setDataInizio(df.parse("01/01/2015"));
			filtri.setDataFine(df.parse("22/05/2015"));
			
			Collection<Pratica> elenco = service.find(filtri);
			assertTrue(elenco != null);
		} catch (Throwable e) {
			fail(e.getLocalizedMessage());
		}
	}
}
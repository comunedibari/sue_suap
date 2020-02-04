package it.exprivia.pal.avbari.suapsue.service.test;

import static org.junit.Assert.*;

import it.exprivia.pal.avbari.suapsue.dto.SportelloEnte;
import it.exprivia.pal.avbari.suapsue.dto.TipoPratica;
import it.exprivia.pal.avbari.suapsue.service.ServiceLocator;
import it.exprivia.pal.avbari.suapsue.service.SportelloService;

import java.sql.SQLException;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

public class SportelloServiceTest {
	
	private static SportelloService service;
	
	
	@BeforeClass
	public static void init() {
		try {
			service = ServiceLocator.getSportelloService();
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testFindAll() {
		try {
			Collection<SportelloEnte> sportelli = service.findAll();
			assertEquals(58, sportelli.size());
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testFindByTipoSUAP() {
		try {
			Collection<SportelloEnte> sportelli = service.findByTipo(TipoPratica.SUAP);
			assertEquals(29, sportelli.size());
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testFindByTipoSUE() {
		try {
			Collection<SportelloEnte> sportelli = service.findByTipo(TipoPratica.SUE);
			assertEquals(29, sportelli.size());
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	@Test
	public void testFindById() {
		try {
			SportelloEnte sportello = service.findById(1L);
			assertEquals("Bari", sportello.getDenominazione());
		} catch (SQLException e) {
			fail(e.getLocalizedMessage());
		}
	}
}
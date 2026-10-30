package it.wego.cross.dao.praticaDao.daAssegnare;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.core.classloader.annotations.PrepareForTest;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.Utente;
import mocks.EntityManagerMock;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class PraticaDataTest {

	EntityManagerMock entityManager;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Prepare anno, numero pratica and tipoPratica", 
				prepareFilter(),
			"((((idUtente equal 1 and tipoPratica equal it.wego.cross.entity.LkTipoPratica[ id_tipo_pratica=1 ]) and anno equal 2021) and numeroPratica equal 123456) and idUtente isNull)" },
			});
	}

	private Filter filter;
	private String expectedConditions;

	public PraticaDataTest(String name, Filter filter, String expectedConditions) {
		super();
		this.filter = filter;
		this.expectedConditions = expectedConditions;
	}

	@Before
	public void init() {
		entityManager = new EntityManagerMock();
	}

	@Test
	public void shouldBeCount() {
		PraticaDao dao = new PraticaDao(entityManager.getEntityManger(), entityManager.getLookUpDao());
		dao.countDaAssegnare(filter);
		assertThat(entityManager.getPredicateManager().getOperations().toString(), Is.is("["+expectedConditions+"]"));
	}

	@Test
	public void shouldBeFind() {
		PraticaDao dao = new PraticaDao(entityManager.getEntityManger(), entityManager.getLookUpDao());
		dao.findDaAssegnare(filter);
		assertThat(entityManager.getPredicateManager().getOperations().toString(), Is.is("["+expectedConditions+"]"));
	}

	private static Filter prepareFilter() {
		Filter f = new Filter();
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		f.setAnnoPratica(2021);
		f.setNumeroPratica(123456);
		LkTipoPratica tipoPratica = new LkTipoPratica();
		tipoPratica.setIdTipoPratica(1);
		f.setIdTipoPratica(tipoPratica);
		return f;
	}

}
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
import it.wego.cross.dto.enums.LogicalOperator;
import it.wego.cross.dto.filters.CadastralAddress;
import it.wego.cross.dto.filters.CadastralAddresses;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Utente;
import mocks.EntityManagerMock;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class DaAssegnareTest {

	EntityManagerMock entityManager;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Basic", 
				prepareEmptyFilter(),
			"idUtente isNull" },
			});
	}

	private Filter filter;
	private String expectedConditions;

	public DaAssegnareTest(String name, Filter filter, String expectedConditions) {
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
		assertThat(entityManager.getAddictionalExpression().toString(), Is.is("[countDistinct]"));
		assertThat(entityManager.isDistinct(), Is.is(false));
	}

	@Test
	public void shouldBeFind() {
		PraticaDao dao = new PraticaDao(entityManager.getEntityManger(), entityManager.getLookUpDao());
		dao.findDaAssegnare(filter);
		assertThat(entityManager.getPredicateManager().getOperations().toString(), Is.is("["+expectedConditions+"]"));
		assertThat(entityManager.isDistinct(), Is.is(true));
	}

	private static Filter prepareEmptyFilter() {
		Filter f = new Filter();
		return f;
	}
}
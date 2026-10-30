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
public class CadastralAddressesTest {

	EntityManagerMock entityManager;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Empty cadastral address", 
				prepareEmptyFilter(),
			"(idUtente equal 1 and idUtente isNull)" },
			{ "Empty cadastral address fields", 
				prepareEmptyFieldsFilter(),
			"(idUtente equal 1 and idUtente isNull)" },
//			{ "Single cadastral address", 
//				prepareSingleCadastralStreetFilter(),
//			"(indirizzo like %VIA%PRINCIPALE% and civico equal 1), (idUtente equal 1 and idUtente isNull)" },
		});
	}

	private Filter filter;
	private String expectedConditions;

	public CadastralAddressesTest(String name, Filter filter, String expectedConditions) {
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

	private static Filter prepareEmptyFilter() {
		Filter f = new Filter();
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareEmptyFieldsFilter() {
		Filter f = new Filter();
		CadastralAddress address = new CadastralAddress().setStreetNumber(null).setStreetName(null).setTownId(null).setTownName(null);
		f.setCadastralAddresses(new CadastralAddresses(LogicalOperator.AND, Arrays.asList(address)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareSingleCadastralStreetFilter() {
		Filter f = new Filter();
		CadastralAddress address = new CadastralAddress().setStreetNumber("1").setStreetName("Via Principale").setTownId("7066").setTownName("MOLFETTA");
		f.setCadastralAddresses(new CadastralAddresses(LogicalOperator.AND, Arrays.asList(address)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareMultiCadastralStreetFilter() {
		Filter f = new Filter();
		CadastralAddress address1 = new CadastralAddress().setStreetNumber("1").setStreetName("Via Principale 1").setTownId("7066").setTownName("MOLFETTA");
		CadastralAddress address2 = new CadastralAddress().setStreetNumber("1").setStreetName("Via Principale 2").setTownId("7066").setTownName("MOLFETTA");
		f.setCadastralAddresses(new CadastralAddresses(LogicalOperator.AND, Arrays.asList(address1, address2)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareMultiOrCadastralStreetFilter() {
		Filter f = new Filter();
		CadastralAddress address1 = new CadastralAddress().setStreetNumber("1").setStreetName("Via Principale 1").setTownId("7066").setTownName("MOLFETTA");
		CadastralAddress address2 = new CadastralAddress().setStreetNumber("1").setStreetName("Via Principale 2").setTownId("7066").setTownName("MOLFETTA");
		f.setCadastralAddresses(new CadastralAddresses(LogicalOperator.OR, Arrays.asList(address1, address2)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}
}
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
import it.wego.cross.dto.filters.CadastralData;
import it.wego.cross.dto.filters.CadastralDataList;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Utente;
import mocks.EntityManagerMock;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class CadastralDataTest {

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
			{ "Basic single cadastral data", 
				prepareSingleCadastralDataFilter(),
			"((idUtente equal 1 and ((((idTipoUnita equal 111 and foglio equal sheet) and mappale equal 102) and subalterno equal 1) and sezione equal section)) and idUtente isNull)" },

		});
	}

	private Filter filter;
	private String expectedConditions;

	public CadastralDataTest(String name, Filter filter, String expectedConditions) {
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
		PraticaDao dao = new PraticaDao(entityManager.getEntityManger());
		dao.countDaAssegnare(filter);
		assertThat(entityManager.getPredicateManager().getOperations().toString(), Is.is("["+expectedConditions+"]"));
	}

	@Test
	public void shouldBeFind() {
		PraticaDao dao = new PraticaDao(entityManager.getEntityManger());
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
		CadastralData cadastralData = new CadastralData().setMap(null).setSubordinate(null);
		f.setCadastralDataList(new CadastralDataList(LogicalOperator.AND, Arrays.asList(cadastralData)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareSingleCadastralDataFilter() {
		Filter f = new Filter();
		f.setIdTipoSistemaCatastale(1);
		CadastralData cadastralData = new CadastralData().setUnitTypeId("111").setSection("section").setSheet("sheet").setMap("102").setSubordinate("1");
		f.setCadastralDataList(new CadastralDataList(LogicalOperator.AND, Arrays.asList(cadastralData)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareMultiBasicCadastralDataFilter() {
		Filter f = new Filter();
		f.setIdTipoSistemaCatastale(1);
		CadastralData cadastralData1 = new CadastralData().setMap("102").setSubordinate("1");
		CadastralData cadastralData2 = new CadastralData().setMap("103").setSubordinate("2");
		f.setCadastralDataList(new CadastralDataList(LogicalOperator.AND, Arrays.asList(cadastralData1, cadastralData2)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareMultiTavolareCadastralDataFilter() {
		Filter f = new Filter();
		f.setIdTipoSistemaCatastale(3);
		f.setIdTipoParticella(2);
		CadastralData cadastralData1 = new CadastralData().setMap("102").setSubordinate("123");
		CadastralData cadastralData2 = new CadastralData().setMap("103").setSubordinate("125");
		f.setCadastralDataList(new CadastralDataList(LogicalOperator.AND, Arrays.asList(cadastralData1, cadastralData2)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}
	private static Filter prepareMultiOrTavolareCadastralDataFilter() {
		Filter f = new Filter();
		f.setIdTipoSistemaCatastale(3);
		f.setIdTipoParticella(2);
		CadastralData cadastralData1 = new CadastralData().setMap("102").setSubordinate("123");
		CadastralData cadastralData2 = new CadastralData().setMap("103").setSubordinate("125");
		f.setCadastralDataList(new CadastralDataList(LogicalOperator.OR, Arrays.asList(cadastralData1, cadastralData2)));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}
}
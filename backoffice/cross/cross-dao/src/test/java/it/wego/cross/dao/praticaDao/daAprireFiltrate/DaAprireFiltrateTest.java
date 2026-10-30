package it.wego.cross.dao.praticaDao.daAprireFiltrate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.powermock.core.classloader.annotations.PrepareForTest;

import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.TestUtils;
import it.wego.cross.dao.PraticaDaoTest;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.LkStatoPratica;
import mocks.h2.H2DbManager;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class DaAprireFiltrateTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static Connection connection;
	private static H2DbManager dbLoader;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Basic", 
				prepareEmptyFilter(),
			"FROM pratica t0 LEFT OUTER JOIN dati_catastali t1 ON (t1.id_pratica = t0.id_pratica) WHERE ((t0.id_stato_pratica = ?) AND NOT ((t0.id_utente IS NULL)))" },
		});
	}

	private Filter filter;
	private String expectedQuery;

	public DaAprireFiltrateTest(String name, Filter filter, String expectedQuery) {
		super();
		this.filter = filter;
		this.expectedQuery = expectedQuery;
	}

	@BeforeClass
	public static void initAll() throws SQLException, IOException {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:test");
		emf =Persistence.createEntityManagerFactory("my-persistence-unit");
		em= emf.createEntityManager();		
		em.getTransaction().begin();
		connection = em.unwrap(Connection.class);
		dbLoader = new H2DbManager(connection);
		dbLoader.init();
	};

	@AfterClass
	public static void destroyAll() throws SQLException {
		if(em.getTransaction().isActive())
			em.getTransaction().rollback();
		if(em.isOpen())
			em.clear();
		if(emf.isOpen())
			emf.close();
		connection.close();
	}


	@Test
	public void shouldBeCount() {
		PraticaDaoTest dao = new PraticaDaoTest(em);
		dao.countDaAprireFiltrate(filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));
	}

	@Test
	public void shouldBeFind() {
		PraticaDaoTest dao = new PraticaDaoTest(em);
		dao.findDaAprireFiltrate(filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));
	}


	private static Filter prepareEmptyFilter() {
		Filter f = new Filter();

		LkStatoPratica lookup = new LkStatoPratica();
		lookup.setCodice("R");
		lookup.setDescrizione("Ricevuta");
		lookup.setGrpStatoPratica("R");
		lookup.setIdStatoPratica(3);
		f.setIdStatoPratica(lookup);
		return f;
	}
}
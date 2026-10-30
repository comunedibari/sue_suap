package it.wego.cross.dao.praticaDao.filtrate;

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
import it.wego.cross.entity.Utente;
import mocks.h2.H2DbManager;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class FiltrateTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static Connection connection;
	private static H2DbManager dbLoader;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Empty", 
				prepareEmptyFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE (((tx.id_utente = ?) AND NOT ((tx.id_utente IS NULL))) AND ((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))))" },
			{ "With register by event", 
				prepareFilterByRegister(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx, pratiche_eventi tx WHERE (((tx.protocollo LIKE ? AND (tx.id_utente = ?)) AND NOT ((tx.id_utente IS NULL))) AND (((tx.id_pratica = tx.id_pratica) AND (tx.id_proc_ente = tx.id_proc_ente)) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))))" },
			{ "with paperwork Subject", 
				prepareFilterByPaperworkSubject(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND tx.oggetto_pratica LIKE ?) AND NOT ((tx.id_utente IS NULL))) AND ((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))))" },
		});
	}

	private Filter filter;
	private String expectedQuery;

	public FiltrateTest(String name, Filter filter, String expectedQuery) {
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
		dao.countFiltrate(filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));
	}

	@Test
	public void shouldBeFind() {
		PraticaDaoTest dao = new PraticaDaoTest(em);
		dao.findFiltrate(filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));
	}

	private static Filter prepareEmptyFilter() {
		Filter f = new Filter();
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		f.setOrderColumn("idPratica");
		return f;
	}

	private static Filter prepareFilterByRegister() {
		Filter f = new Filter();
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		f.setRegisterNumberByEvent("ABC");
		f.setOrderColumn("idPratica");
		return f;
	}

	private static Filter prepareFilterByPaperworkSubject() {
		Filter f = new Filter();
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		f.setOggetto("provare");
		f.setOrderColumn("idPratica");
		return f;
	}
}
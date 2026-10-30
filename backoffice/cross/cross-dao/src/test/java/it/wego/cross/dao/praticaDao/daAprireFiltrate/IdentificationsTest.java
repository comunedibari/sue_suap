package it.wego.cross.dao.praticaDao.daAprireFiltrate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsStringIgnoringCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import it.wego.cross.dto.enums.LogicalOperator;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.filters.Identification;
import it.wego.cross.dto.filters.Identifications;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import mocks.h2.H2DbManager;

@PrepareForTest(PraticaDao.class)
@RunWith(Parameterized.class)
public class IdentificationsTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static Connection connection;
	private static H2DbManager dbLoader;

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

			{ "Basic", 
				prepareEmptyFilter(),
			"pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE (((tx.id_utente = ?) AND NOT ((tx.id_utente IS NULL))) AND ((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))))" },
			{ "prepareEmptyFieldsFilter", 
				prepareEmptyFieldsFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx, pratica_anagrafica tx, anagrafica tx WHERE (((tx.id_utente = ?) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareNameSurnameFilter", 
				prepareNameSurnameFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND (tx.cognome LIKE ? AND tx.nome LIKE ?)) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareCompanyNameFilter", 
				prepareCompanyNameFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND tx.denominazione LIKE ?) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareFullNameFilter", 
				prepareFullNameFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND ((tx.cognome LIKE ? AND tx.nome LIKE ?) AND tx.denominazione LIKE ?)) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareTwoSubjectFilter", 
				prepareTwoSubjectFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND ((tx.cognome LIKE ? AND tx.nome LIKE ?) OR (tx.cognome LIKE ? AND tx.nome LIKE ?))) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareTwoSubjectFilter2", 
				prepareTwoSubjectFilter2(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND ((tx.cognome LIKE ? AND tx.nome LIKE ?) OR tx.denominazione LIKE ?)) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
			{ "prepareTwoSubjectFilterInAnd", 
				prepareTwoSubjectFilterInAnd(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND (EXISTS (SELECT ? FROM pratica_anagrafica tx, anagrafica tx WHERE (((tx.id_pratica = tx.id_pratica) AND (tx.cognome LIKE ? AND tx.nome LIKE ?)) AND (tx.id_anagrafica = tx.id_anagrafica)))  AND EXISTS (SELECT ? FROM anagrafica tx, pratica_anagrafica tx WHERE (((tx.id_pratica = tx.id_pratica) AND tx.denominazione LIKE ?) AND (tx.id_anagrafica = tx.id_anagrafica))) )) AND NOT ((tx.id_utente IS NULL))) AND ((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))))" },
			{ "prepareTaxCodeFilter", 
				prepareTaxCodeFilter(),
			"FROM pratica tx LEFT OUTER JOIN dati_catastali tx ON (tx.id_pratica = tx.id_pratica), utente_ruolo_procedimento tx, pratica_anagrafica tx, anagrafica tx, procedimenti_enti tx, utente_ruolo_ente tx WHERE ((((tx.id_utente = ?) AND (tx.codice_fiscale LIKE ? OR tx.partita_iva LIKE ?)) AND NOT ((tx.id_utente IS NULL))) AND ((((tx.id_proc_ente = tx.id_proc_ente) AND ((tx.id_proc_ente = tx.id_proc_ente) AND (tx.id_utente_ruolo_ente = tx.id_utente_ruolo_ente))) AND (tx.id_pratica = tx.id_pratica)) AND (tx.id_anagrafica = tx.id_anagrafica)))" },
		});
	}


	private Filter filter;
	private String expectedQuery;

	public IdentificationsTest(String name, Filter filter, String expectedQuery) {
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
	public void shouldBeFromCount() throws SQLException, IOException {
		PraticaDaoTest dao = new PraticaDaoTest(em);
		Long count = dao.countDaAprireFiltrate(this.filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));

	}

	@Test
	public void shouldBeFromFind() throws SQLException, IOException {
		PraticaDaoTest dao = new PraticaDaoTest(em);
		dao.findDaAprireFiltrate(this.filter);
		assertThat(TestUtils.replaceAlias(dao.getSql(), "tx"), (containsStringIgnoringCase(TestUtils.replaceAlias(expectedQuery, "tx"))));
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
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, null, null, null))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareNameSurnameFilter() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, "Rossi", "Mario", null))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareCompanyNameFilter() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, null, null, "caffetteria"))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareFullNameFilter() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, "Rossi", "Mario", "caffetteria"))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareTwoSubjectFilter() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, "Rossi", "Mario", null),
				new Identification(null, "Verdi", "Maria", null))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareTwoSubjectFilter2() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification(null, "Rossi", "Mario", null),
				new Identification(null, null, null, "caffettiera"))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareTwoSubjectFilterInAnd() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.AND, Arrays.asList(new Identification(null, "Rossi", "Mario", null),
				new Identification(null, null, null, "caffettiera"))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);
		return f;
	}

	private static Filter prepareTaxCodeFilter() {
		Filter f = new Filter();
		f.setIdentifications(new Identifications(LogicalOperator.OR, Arrays.asList(new Identification("09320730154", null, null, null))));
		Utente utente = new Utente();
		utente.setIdUtente(1);
		f.setConnectedUser(utente);;
		return f;
	}

}
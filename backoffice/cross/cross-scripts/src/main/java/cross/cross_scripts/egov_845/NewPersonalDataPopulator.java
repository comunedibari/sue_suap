package cross.cross_scripts.egov_845;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;

import javax.persistence.EntityManager;

import org.dozer.DozerBeanMapper;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import it.people.core.Logger;
import it.wego.cross.actions.AnagraficaAction;
import it.wego.cross.actions.SystemEventAction;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.service.LookupServiceImpl;
import it.wego.cross.service.ProcessiServiceImpl;
import it.wego.cross.service.UsefulServiceImpl;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.RecapitoUtils;

public class NewPersonalDataPopulator extends BaseHandler{

	private final static String QUERY = "select pa.id_pratica, a.id_anagrafica, pa.id_tipo_ruolo, pa.id_tipo_qualifica "
			+ "from anagrafica a inner join anagrafica_recapiti ar on a.id_anagrafica = ar.id_anagrafica "
			+ "inner join recapiti r on ar.id_recapito = r.id_recapito\r\n"
			+ "inner join pratica_anagrafica pa on pa.id_anagrafica = a.id_anagrafica "
			+ "where a.denominazione like 'tim%' and a.codice_fiscale = '00488410010' and r.indirizzo = 'Via Gaetano Negri' and r.n_civico = 1";
	
	
	private final static String QUERY_TEST = "select pa.id_pratica, a.id_anagrafica, pa.id_tipo_ruolo, pa.id_tipo_qualifica "
			+ "from anagrafica a inner join anagrafica_recapiti ar on a.id_anagrafica = ar.id_anagrafica "
			+ "inner join recapiti r on ar.id_recapito = r.id_recapito\r\n"
			+ "inner join pratica_anagrafica pa on pa.id_anagrafica = a.id_anagrafica "
			+ "where pa.id_pratica = 210982 and pa.id_anagrafica = 5472 and pa.id_tipo_ruolo = 4";
	
	private DozerBeanMapper mapper= new DozerBeanMapper();
	private EntityManager em;
	private LookupDao lookupDao;
	private PraticaDao praticaDao;
	private StagingDao stagingDao;
	private UtentiDao utentiDao;
	private AnagraficheDao anagraficheDao;
	private AnagraficheServiceImpl anagraficheService;
	private RecapitoUtils recapitoUtils;
	private AnagraficaUtils anagraficaUtils;
	private LookupServiceImpl lookupService;
	private AnagraficaAction anagraficaAction;
	private UsefulDao usefulDao;
	private UsefulServiceImpl usefulServiceImpl;
	private SystemEventAction systemEventAction;
	private ProcessiServiceImpl processiService;
	private ProcessiDao processiDao;

	public NewPersonalDataPopulator(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
		Path path = Paths.get("entity-dto-mappings.xml");
		
		em = jpa.get();

		utentiDao = new UtentiDao(em);
		lookupDao = new LookupDao(em);
		praticaDao = new PraticaDao(em);
		stagingDao = new StagingDao(em);
		anagraficheDao = new AnagraficheDao(em);
		usefulDao = new UsefulDao(em);
		processiDao = new ProcessiDao(em);
		processiService = new ProcessiServiceImpl(processiDao);
		usefulServiceImpl = new UsefulServiceImpl(usefulDao);
		anagraficaUtils = new AnagraficaUtils(recapitoUtils, lookupDao);
		mapper.setMappingFiles(Arrays.asList("file:"+path.toAbsolutePath().toString()));
		anagraficaAction = new AnagraficaAction(anagraficheService, lookupService, lookupDao, praticaDao, stagingDao, anagraficheDao, anagraficaUtils, usefulServiceImpl);
		systemEventAction = new SystemEventAction(praticaDao, anagraficheDao, lookupDao, processiService, anagraficheService, anagraficaAction, anagraficaUtils, usefulServiceImpl);
        
	}

	@Override
	public void run() throws Exception {
//		searchAndProcess(QUERY_TEST);

		searchAndProcess(QUERY);
	}

	public void searchAndProcess(String query) throws SQLException {
				

		consumeSelect(query, set->{
			try {
				Integer idPratica = set.getInt(1);
				Integer idAnagrafica = set.getInt(2);
				Integer idRuolo = set.getInt(3);
				Integer idQualifica = null;
				Integer tmp = set.getInt(4);
				if(tmp > 0)
					idQualifica = tmp;
				addPersonalData(idPratica, idAnagrafica, idQualifica, idRuolo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void search(String query) throws SQLException {
		
		
		consumeSelect(query, set->{
			try {
				Integer idPratica = set.getInt(1);
				Integer idAnagrafica = set.getInt(2);
				Integer idRuolo = set.getInt(3);
				Integer idQualifica = null;
				Integer tmp = set.getInt(4);
				if(tmp > 0)
					idQualifica = tmp;
				Logger.info("idPratica: " + idPratica);
				Logger.info("idAnagrafica: " + idAnagrafica);
				Logger.info("idRuolo: " + idRuolo);
				Logger.info("idQualifica: " + idQualifica);
				Logger.info("-------------");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public void addPersonalData(Integer idPratica, Integer idAnagrafica, Integer qualifica, Integer ruolo) {
		try {
			this.em.getTransaction().begin();
			AnagraficaDaCollegareDTO anagrafica = new AnagraficaDaCollegareDTO();
			anagrafica.setIdAnagrafica(idAnagrafica);
			anagrafica.setQualificaTecnico(qualifica);
			anagrafica.setRuolo(ruolo);

			Utente utenteConnesso = utentiDao.findUtenteByIdUtente(1);
			
			systemEventAction.inserisciCollegamentoPraticaAnagraficaNoTransactional(anagrafica, idPratica, utenteConnesso, true);
			this.em.getTransaction().commit();
		} catch (Exception e) {
			this.em.getTransaction().rollback();
		}
	}

}

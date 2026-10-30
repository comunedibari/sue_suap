package cross.cross_scripts.reports.addresses_comparing;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.actions.AnagraficaAction;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.controller.Pratiche;
import it.wego.cross.dao.AnagraficheDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.serializer.DettaglioComunicazioneSerializer;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.service.LookupServiceImpl;
import it.wego.cross.services.PraticheServiceImpl;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.RecapitoUtils;

public class AddressesComparing {

	private static final Logger logger = LogManager.getLogger(AddressesComparing.class);
	private DozerBeanMapper mapper= new DozerBeanMapper();

	private EntityManager em;
	private LookupDao lookupDao;
	private PraticaDao praticaDao;
	private StagingDao stagingDao;
	private AnagraficheDao anagraficheDao;
	private DettaglioComunicazioneSerializer dettaglioComunicazioneSerializer;
	private PraticheServiceImpl praticheService;
	private AnagraficheServiceImpl anagraficheService;
	private Pratiche pratiche;
	private RecapitoUtils recapitoUtils;
	private AnagraficaUtils anagraficaUtils;
	private LookupServiceImpl lookupService;
	private AnagraficaAction anagraficaAction;
	Report report;
	
	public AddressesComparing(CrossJpa jpa) {
		em = jpa.get();

		lookupDao = new LookupDao(em);
		praticaDao = new PraticaDao(em);
		stagingDao = new StagingDao(em);
		anagraficheDao = new AnagraficheDao(em);
		Path path = Paths.get("entity-dto-mappings.xml");
		mapper.setMappingFiles(Arrays.asList("file:"+path.toAbsolutePath().toString()));
		dettaglioComunicazioneSerializer = new DettaglioComunicazioneSerializer(mapper);
		this.recapitoUtils = new RecapitoUtils(lookupDao);
		this.anagraficaUtils = new AnagraficaUtils(recapitoUtils);
		praticheService = new PraticheServiceImpl(praticaDao, lookupDao, dettaglioComunicazioneSerializer, anagraficaUtils, recapitoUtils);
		anagraficheService = new AnagraficheServiceImpl(anagraficheDao, anagraficaUtils, recapitoUtils, praticaDao, stagingDao);
		pratiche = new Pratiche(praticheService, anagraficheService, mapper, praticaDao);
		lookupService = new LookupServiceImpl(lookupDao);
		anagraficaAction = new AnagraficaAction(anagraficheService, lookupService, lookupDao, praticaDao);
		report = new Report();
		report.createSheet("indirizzi");

	}

	public void run() throws Exception {

		Query q = em.createNativeQuery("SELECT id_pratica "
				+ "FROM Pratica p "
				+ "WHERE p.id_stato_pratica is not null and p.id_stato_pratica = 3 and p.id_utente is null "
				+ "ORDER BY p.id_pratica desc");
		
		List<Integer> idPratiche= q.getResultList();
		for(Integer idPratica: idPratiche) {
			logger.info("Elaborazione Pratica: " + idPratica);
			handlePaperwork(idPratica);
		}
//		int idPratica = 209605;
//
//		handlePaperwork(idPratica);
		report.close("reportIndirizzi");	
	}

	private void handlePaperwork(int idPratica) throws Exception {
		PraticaDTO praticaDto = pratiche.aperturaDettaglio(idPratica);
		logger.info("Numero Pratica: "+ praticaDto.getIdPratica());

		
		for(AnagraficaDTO a: praticaDto.getAnagraficaList()) {
			String confermata = a.getConfermata();
			logger.info("\tnumero anagrafica: "+ a.getIdAnagrafica());
			logger.info("\tcounter: "+ a.getCounter());
			logger.info("\tconfermata: "+ a.getConfermata());

			if(confermata == null || confermata.length() == 0) {
				//				System.out.println(a);
				AnagraficaDTO anagrafica = new AnagraficaDTO();
				anagrafica.setCodiceFiscale(a.getCodiceFiscale());
				anagrafica.setIdPratica(praticaDto.getIdPratica());
				anagrafica.setIdAnagrafica(a.getIdAnagrafica());
				anagrafica.setIdTipoRuolo(a.getIdTipoRuolo());
				anagrafica.setCounter(a.getCounter());
				anagrafica.setPartitaIva(a.getPartitaIva());
				AnagraficaDTO anagraficaDB = anagraficheService.getAnagrafica(anagrafica);
				GridAnagraficaBean json = anagraficaAction.getListaAnagraficheDaConfrontare(anagrafica, anagraficaDB);
				report(report, idPratica, json.getRows().get(0), json.getRows().get(1));

			}
		}
	}

	public void report(Report report, int idPratica, AnagraficaDTO anagraficaXml,AnagraficaDTO  anagraficaDb) throws Exception {

		RecapitoDTO recapitoDb = new RecapitoDTO();
		RecapitoDTO recapitoXml = new RecapitoDTO();
		if(!anagraficaDb.getRecapiti().isEmpty()) {
			recapitoDb = anagraficaDb.getRecapiti().get(0);
			recapitoXml = anagraficaXml.getRecapiti().get(0);
		}
		report.createRow(idPratica,recapitoXml.getCounter() , anagraficaDb.getIdAnagrafica() ,recapitoDb.getIndirizzo() , recapitoXml.getIndirizzo());
	}
}
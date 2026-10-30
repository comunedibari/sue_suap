package cross.cross_scripts.reports.personal_data_comparing;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

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
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.StatoDTO;
import it.wego.cross.serializer.DettaglioComunicazioneSerializer;
import it.wego.cross.service.AnagraficheServiceImpl;
import it.wego.cross.service.LookupServiceImpl;
import it.wego.cross.services.PraticheServiceImpl;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.RecapitoUtils;
import utils.AddressesComparator;

public class PersonalDataReportHandler {

	private static final Logger logger = LogManager.getLogger(PersonalDataReportHandler.class);
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
	public PersonalDataReportHandler(CrossJpa jpa) {
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
		report.close("report.xls");	
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
				report.createSheet(""+idPratica+"_"+a.getIdAnagrafica()+"_"+a.getCounter());
				//				System.out.println(a);
				AnagraficaDTO anagrafica = new AnagraficaDTO();
				anagrafica.setCodiceFiscale(a.getCodiceFiscale());
				anagrafica.setIdPratica(praticaDto.getIdPratica());
				anagrafica.setIdAnagrafica(a.getIdAnagrafica());
				anagrafica.setIdTipoRuolo(a.getIdTipoRuolo());
				anagrafica.setCounter(a.getCounter());
				anagrafica.setPartitaIva(a.getPartitaIva());
				AnagraficaDTO anagraficaXml = anagraficheService.getAnagrafica(anagrafica);
				GridAnagraficaBean json = anagraficaAction.getListaAnagraficheDaConfrontare(anagrafica, anagraficaXml);
				//0=XML - 1=DB
				report(report, json.getRows().get(0), json.getRows().get(1));

			}
		}
	}

	public void report(Report report, AnagraficaDTO anagraficaXml, AnagraficaDTO  anagraficaDb) throws Exception {
		BiPredicate<Object, Object> bp1 = (xml, db)-> {
			boolean equal = (xml == null && db != null);
			if(xml != null && db != null)
				equal = ((String)db).equalsIgnoreCase((String)xml);
			return equal;
		};
		BiFunction<Object, Object, String> bf1 =(xml, db)->{
			if(xml == null && db != null)
				return "solo su db";
			return "";
		};
		
		report.createRow("Tipo anagrafica:", anagraficaXml.getTipoAnagrafica() , anagraficaDb.getTipoAnagrafica());
		report.createRow("Partecipa come D.I.", anagraficaXml.getFlgIndividuale() , anagraficaDb.getFlgIndividuale());
		report.createRow("Ruolo", anagraficaXml.getIdTipoRuolo() , anagraficaDb.getIdTipoRuolo());
		report.createRow("Codice fiscale", anagraficaXml.getCodiceFiscale() , anagraficaDb.getCodiceFiscale());
		report.createRow("P.IVA", anagraficaXml.getPartitaIva() , anagraficaDb.getPartitaIva(), bp1, bf1);
		report.createRow("Nome", anagraficaXml.getNome() , anagraficaDb.getNome());
		report.createRow("Cognome", anagraficaXml.getCognome() , anagraficaDb.getCognome());
		report.createRow("Sesso", anagraficaXml.getSesso() , anagraficaDb.getSesso(), bp1, bf1);
		report.createRow("Cittadinanza", anagraficaXml.getDesCittadinanza() , anagraficaDb.getDesCittadinanza(), bp1, bf1);
		report.createRow("Nazionalita", anagraficaXml.getDesNazionalita() , anagraficaDb.getDesNazionalita(), bp1, bf1);
		report.createRow("Data di nascita", anagraficaXml.getDataNascita() , anagraficaDb.getDataNascita());
		
		report.createRow("Comune di nascita", anagraficaXml.getComuneNascita(), anagraficaDb.getComuneNascita(),
				(xml, db)-> db != null && xml != null && ((ComuneDTO)db).getIdComune() != null && ((ComuneDTO)db).getIdComune().equals(((ComuneDTO)xml).getIdComune())
				);
		
		ComuneDTO comuneXml = anagraficaXml.getComuneNascita();
		ComuneDTO comuneDb = anagraficaDb.getComuneNascita();
		report.createRow("Stato di nascita", comuneXml==null?null:comuneXml.getStato(), comuneDb==null?null:comuneDb.getStato(),
				(xml, db)-> db != null && xml != null && ((StatoDTO)db).getIdStato() != null && ((StatoDTO)db).getIdStato().equals(((StatoDTO)xml).getIdStato())
				);
		report.createRow("Provincia nascita", comuneXml==null?null:comuneXml.getProvincia(),comuneDb==null?null: comuneDb.getProvincia(),
				(xml, db)-> db != null && xml != null &&  ((ProvinciaDTO)db).getIdProvincie() != null && ((ProvinciaDTO)db).getIdProvincie().equals(((ProvinciaDTO)xml).getIdProvincie())
				);
	
		report.createRow("località nascita", anagraficaXml.getLocalitaNascita() , anagraficaDb.getLocalitaNascita());
		report.createRow("Denominazione", anagraficaXml.getDenominazione(), anagraficaDb.getDenominazione());
		report.createRow("Forma Giuridica", anagraficaXml.getDesFormaGiuridica(), anagraficaDb.getDesFormaGiuridica());

		report.createRow("Provincia CCIAA", anagraficaXml.getProvinciaCciaa(), anagraficaDb.getProvinciaCciaa(), 
				(xml, db)-> ((ProvinciaDTO)db).getCodCatastale() != null && ((ProvinciaDTO)db).getCodCatastale().equals(((ProvinciaDTO)xml).getCodCatastale())
				);
//		report.createRow("In attesa R.E.A.", anagraficaXml.getFlgAttesaIscrizioneRea(), anagraficaDb.getFlgAttesaIscrizioneRea());
//		report.createRow("N.Iscrizione R.E.A.", anagraficaXml.getnIscrizioneRea(), anagraficaDb.getnIscrizioneRea());
//		report.createRow("Data iscrizione R.E.A", anagraficaXml.getDataIscrizioneRea(), anagraficaDb.getDataIscrizioneRea());
//		report.createRow("In attesa R.I", anagraficaXml.getFlgAttesaIscrizioneRi(), anagraficaDb.getFlgAttesaIscrizioneRi());
//		report.createRow("N.Iscrizione R.I.", anagraficaXml.getnIscrizioneRi(), anagraficaDb.getnIscrizioneRi());

//		report.createRow("Data iscrizione R.I.", anagraficaXml.getDataIscrizioneRi(), anagraficaDb.getDataIscrizioneRi());
//		report.createRow("Tipo Collegio", anagraficaXml.getDesTipoCollegio(), anagraficaDb.getDesTipoCollegio());
//		report.createRow("Numero Albo Prof", anagraficaXml.getNumeroIscrizione(), anagraficaDb.getNumeroIscrizione());
//		report.createRow("Provincia albo", anagraficaXml.getProvinciaIscrizione(), anagraficaDb.getProvinciaIscrizione());

		RecapitoDTO recapitoXml = new RecapitoDTO();
		RecapitoDTO recapitoDb = new RecapitoDTO();
		if(!anagraficaXml.getRecapiti().isEmpty()) {
			recapitoXml = anagraficaXml.getRecapiti().get(0);
			recapitoDb = anagraficaDb.getRecapiti().get(0);
		}
//		report.createRow("idRecapito", recapitoDb.getIdRecapito() , recapitoXml.getIdRecapito());
		report.createRow("Tipo Indirizzo", recapitoXml.getIdTipoIndirizzo() , recapitoDb.getIdTipoIndirizzo());
		report.createRow("Indirizzo", recapitoXml.getIndirizzo() , recapitoDb.getIndirizzo(), 
				(xml, db) -> new AddressesComparator().compare((String)db, (String)xml));
		report.createRow("Civico", recapitoXml.getnCivico() , recapitoDb.getnCivico());
		report.createRow("Cap", recapitoXml.getCap() , recapitoDb.getCap());
		report.createRow("Comune", recapitoXml.getDescComune() , recapitoDb.getDescComune());
		report.createRow("Provincia", recapitoXml.getIdProvincia() , recapitoDb.getIdProvincia());
		report.createRow("Stato", recapitoXml.getIdStato() , recapitoDb.getIdStato());
		report.createRow("Telefono", recapitoXml.getTelefono() , recapitoDb.getTelefono());
		report.createRow("Cellulare", recapitoXml.getCellulare() , recapitoDb.getCellulare());
		report.createRow("Pec", recapitoXml.getPec() , recapitoDb.getPec());
		report.createRow("Email", recapitoXml.getEmail() , recapitoDb.getEmail());

	}
}
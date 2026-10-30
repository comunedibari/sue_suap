package cross.cross_scripts.cadastral_data_opening_paperworks;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.entity.DatiCatastali;
import it.wego.cross.entity.IndirizziIntervento;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Staging;
import it.wego.cross.utils.PraticaUtils;
import javax.persistence.Query;

public class XmlPaperworkHandler {

	private static final Logger logger = LogManager.getLogger(XmlPaperworkHandler.class);
	private DozerBeanMapper mapper= new DozerBeanMapper();

	private EntityManager em;
	private LookupDao lookupDao;
	private PraticaDao praticaDao;
	private StagingDao stagingDao;

	public XmlPaperworkHandler(CrossJpa jpa) {
		em = jpa.get();

		lookupDao = new LookupDao(em);
		praticaDao = new PraticaDao(em);
		stagingDao = new StagingDao(em);

		Path path = Paths.get("entity-dto-mappings.xml");
		mapper.setMappingFiles(Arrays.asList("file:"+path.toAbsolutePath().toString()));
	}

	public void run() throws Exception {

		Query q = em.createNativeQuery("SELECT id_pratica "
				+ "FROM Pratica p "
				+ "WHERE p.id_stato_pratica is not null and p.id_stato_pratica = 3");
		
		List<Integer> idPratiche= q.getResultList();
		for(Integer idPratica: idPratiche) {
			logger.info("Elaborazione Pratica: " + idPratica);
			handlePaperwork(idPratica);
		}

	}

	private void handlePaperwork(Integer paperworkId) throws Exception {
		Pratica pratica = praticaDao.findPratica(paperworkId);
		String xmlPratica = new String(pratica.getIdStaging().getXmlPratica());
		it.wego.cross.xml.Pratica praticaXML = PraticaUtils.getPraticaFromXML(xmlPratica);
		saveCadastralDataFromXML(praticaXML, pratica);
		saveAddressFromXML(praticaXML, pratica);
	}


	public void saveAddressFromXML(it.wego.cross.xml.Pratica paperworkCross, Pratica paperwork) throws Exception {
		logger.info("Start saveAddressFromXML");
		List<IndirizziIntervento> updatedAddresses = new LinkedList<>();
		boolean update = false;
		if(paperworkCross.getIndirizziIntervento() != null) {
			em.getTransaction().begin();
			try {
				for(it.wego.cross.xml.IndirizzoIntervento addressCross: paperworkCross.getIndirizziIntervento().getIndirizzoIntervento()) {
					if(addressCross.getIdIndirizzoIntervento() == null) {
						update = true;
						IndirizziIntervento address = mapper.map(addressCross, IndirizziIntervento.class);
						address.setIdPratica(paperwork);
						praticaDao.insert(address);
						em.flush();
						logger.info("Start saveAddressFromXML: new id: " + address.getIdIndirizzoIntervento());
						addressCross.setIdIndirizzoIntervento(BigInteger.valueOf(address.getIdIndirizzoIntervento()));
						updatedAddresses.add(address);
					}
				}
				logger.info("Start saveAddressFromXML: update: " + update);
				if(update) {
					paperwork.setIndirizziInterventoList(updatedAddresses);
					Staging staging = paperwork.getIdStaging();
					String xmlPratica = PraticaUtils.getXmlFromPratica(paperworkCross);
					staging.setXmlPratica(xmlPratica.getBytes());
					stagingDao.merge(staging);
				}
				em.getTransaction().commit();
			} catch(Exception e) {
				logger.error("Error during saving of addresses",e);
				em.getTransaction().rollback();
			}
		}
		logger.info("end saveAddressFromXML");
	}

	public void saveCadastralDataFromXML(it.wego.cross.xml.Pratica paperworkCross, Pratica paperwork) throws Exception {
		logger.info("Start saveCadastralDataFromXML");
		List<DatiCatastali> updateCadastralDataList = new LinkedList<>();
		boolean update = false;
		if(paperworkCross.getDatiCatastali() != null) {
			em.getTransaction().begin();
			try {
				for(it.wego.cross.xml.Immobile cadastralDataCross: paperworkCross.getDatiCatastali().getImmobile()) {
					if(cadastralDataCross.getIdImmobile() == null) {
						update = true;
						DatiCatastali cadastralData = mapper.map(cadastralDataCross, DatiCatastali.class);
						if(cadastralDataCross.getIdTipoSistemaCatastale() != null) {
							LkTipoSistemaCatastale cadastralSystemType = lookupDao.findByIdTipoSistemaCatastale(cadastralDataCross.getIdTipoSistemaCatastale());
							cadastralData.setIdTipoSistemaCatastale(cadastralSystemType);
						}
						if(cadastralDataCross.getIdTipoUnita() != null) {
							LkTipoUnita unitType = lookupDao.findTipoUnitaById(cadastralDataCross.getIdTipoUnita().intValue());
							cadastralData.setIdTipoUnita(unitType);
						}
						if(cadastralDataCross.getIdTipoParticella() != null) {
							LkTipoParticella particella = lookupDao.findTipoParticellaById(cadastralDataCross.getIdTipoParticella().intValue());
							cadastralData.setIdTipoParticella(particella);
						}
						cadastralData.setIdPratica(paperwork);
						praticaDao.insert(cadastralData);
						em.flush();
						logger.info("Start saveAddressFromXML: new id: " + cadastralData.getIdImmobile());
						updateCadastralDataList.add(cadastralData);
						cadastralDataCross.setIdImmobile(BigInteger.valueOf(cadastralData.getIdImmobile()));
					}
				}
	
				logger.info("Start saveAddressFromXML: update: " + update);
				if(update) {
					paperwork.setDatiCatastaliList(updateCadastralDataList);	
					Staging staging = paperwork.getIdStaging();
					String xmlPratica = PraticaUtils.getXmlFromPratica(paperworkCross);
					staging.setXmlPratica(xmlPratica.getBytes());
					stagingDao.merge(staging);
				}
				em.getTransaction().commit();
			} catch(Exception e) {
				logger.error("Error during saving of cadastral data",e);
				em.getTransaction().rollback();
			}
		}
		logger.info("end saveCadastralDataFromXML");
	}

}
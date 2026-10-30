package cross.cross_scripts.reports.ave_31;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;

public class COMDINAMMReportHandler {

	private static String DES_EVENTO = "(AMM) INVIO AVVIO DINIEGO 10 bis";
	private static final Logger logger = LogManager.getLogger(COMDINAMMReportHandler.class);
	private DozerBeanMapper mapper= new DozerBeanMapper();

	private EntityManager em;
	private PraticaDao praticaDao;
	private Report report;

	public COMDINAMMReportHandler(CrossJpa jpa) {
		em = jpa.get();
		praticaDao = new PraticaDao(em);
		report = new Report();
	}

	public void run() throws Exception {

		Query q = em.createNativeQuery("SELECT id_pratica "
				+ "FROM pratica p "
				+ "WHERE p.id_tipo_pratica = 16 and  p.data_ricezione >= '2019-01-01' and "
				+ "exists(select 1 from pratiche_eventi pe inner join processi_eventi pre on pe.id_evento = pre.id_evento where p.id_pratica = pe.id_pratica and pre.cod_evento = 'COMDINAMM') "
				+ "order by p.id_pratica desc");

		List<Integer> idPratiche= q.getResultList();
		System.out.println(idPratiche.size());

		report.createSheet("Estrazione");
		for(Integer idPratica: idPratiche) {
			logger.info("Elaborazione Pratica: " + idPratica);
			handlePaperwork(idPratica);
		}
		
		report.close("report");	
	}

	private void handlePaperwork(int idPratica) throws Exception {
		Pratica pratica = praticaDao.findPratica(idPratica);
		List<PraticheEventi> eventiSelezionati = new ArrayList<>();

		for(PraticheEventi pe: pratica.getPraticheEventiList()) {
			if(pe.getIdEvento().getCodEvento().equalsIgnoreCase("COMDINAMM"))
				eventiSelezionati.add(pe);
		}	

//		if(eventiSelezionati.size() > 1)
//			System.out.println("fra");
		
		PraticheEventi lastEvent = null;
		for(PraticheEventi pe:eventiSelezionati) {
			if(lastEvent == null || lastEvent.getDataEvento().compareTo(pe.getDataEvento())<0)
				lastEvent = pe;
		}
		
		boolean found = false;
		for(PraticheEventi pe: pratica.getPraticheEventiList()) {
			found = found || (pe.getDataEvento().compareTo(lastEvent.getDataEvento())> 0 && "integrazione".equalsIgnoreCase(pe.getIdUtente().getCodiceFiscale()));
		}

		if(!found) {

			for(PraticheEventi pe: eventiSelezionati) {
				report.createRow(pratica, pe);
			}
		}

	}

}
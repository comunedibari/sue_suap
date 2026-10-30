package cross.cross_scripts.reports.egov_670;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.service.AnagraficheServiceImpl;

public class ContactsReportHandler {

	private static final Logger logger = LogManager.getLogger(ContactsReportHandler.class);
	
	private EntityManager em;
	private AnagraficheServiceImpl anagraficheServiceImpl;
	private Report report;

	public ContactsReportHandler(CrossJpa jpa) {
		em = jpa.get();
		anagraficheServiceImpl = new AnagraficheServiceImpl(em);
		report = new Report();
	}

	public void run() throws Exception {

		Query q = em.createNativeQuery("select id_anagrafica\r\n"
				+ "from anagrafica a1\r\n"
				+ "where \r\n"
				+ "exists(select 1\r\n"
				+ "	from anagrafica a inner join anagrafica_recapiti ar on a.id_anagrafica = ar.id_anagrafica  where a1.id_anagrafica = a.id_anagrafica)\r\n"
				+ "and\r\n"
				+ "not exists(select 1\r\n"
				+ "	from anagrafica_recapiti ar inner join recapiti r on r.id_recapito = ar.id_recapito \r\n"
				+ "    where a1.id_anagrafica = ar.id_anagrafica and length(r.pec) is not null and length(r.pec) > 0\r\n"
				+ ")\r\n"
				+ "order by a1.id_anagrafica desc;");

		List<Integer> idAnagrafiche= q.getResultList();
		System.out.println(idAnagrafiche.size());

		report.createSheet("Estrazione");
		for(Integer idAnagrafica: idAnagrafiche) {
			logger.debug("Elaborazione Pratica: " + idAnagrafica);
			handlePersonalDatta(idAnagrafica);
		}
		logger.debug("fine elaborazione");
		report.close("report");	
	}

	private void handlePersonalDatta(int idAnagrafica) throws Exception {
		Anagrafica anagrafica = anagraficheServiceImpl.findAnagraficaById(idAnagrafica);
		report.createRow(anagrafica);
		
	}
}

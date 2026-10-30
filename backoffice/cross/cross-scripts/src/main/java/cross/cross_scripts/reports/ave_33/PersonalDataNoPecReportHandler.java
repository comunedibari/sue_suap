package cross.cross_scripts.reports.ave_33;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.service.AnagraficheServiceImpl;

public class PersonalDataNoPecReportHandler {

	private static final Logger logger = LogManager.getLogger(PersonalDataNoPecReportHandler.class);
	
	private EntityManager em;
	private AnagraficheServiceImpl anagraficheServiceImpl;
	private Report report;

	public PersonalDataNoPecReportHandler(CrossJpa jpa) {
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
			logger.error("Elaborazione anagrafica: " + idAnagrafica);
			handlePersonalDatta(idAnagrafica);
		}
		logger.debug("fine elaborazione");
		report.close("report");	
	}

	private void handlePersonalDatta(int idAnagrafica) throws Exception {
		Anagrafica anagrafica = anagraficheServiceImpl.findAnagraficaById(idAnagrafica);
		if(anagrafica.getAnagraficaRecapitiList().size() == 1) {
			Query q = em.createNativeQuery("select id_tipo_ruolo from Pratica_Anagrafica p where p.id_anagrafica = ?");
			q.setParameter(1, anagrafica.getIdAnagrafica());
			List<Integer> roles = q.getResultList();
			boolean isBeneficiary = false;
			boolean isApplicant = false;
			boolean isTechAgent = false;
			boolean isBuilder = false;
			for(Integer role: roles) {
				isBeneficiary = isBeneficiary || role.equals(2);
				isApplicant = isApplicant || role.equals(1);
				isTechAgent = isTechAgent || role.equals(3);
				isBuilder = isBuilder || role.equals(4);
			}
			report.createRow(anagrafica, isBeneficiary, isApplicant, isTechAgent, isBuilder);
		}
		
	}
}

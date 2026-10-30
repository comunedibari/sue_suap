package cross.cross_scripts.R9_CR.attachments;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.CrossJpa;
import it.wego.cross.entity.Allegati;

public class DimensionAttachmentsWriter {

	private static final Logger logger = LogManager.getLogger(DimensionAttachmentsWriter.class);

	private EntityManager em;

	
	public DimensionAttachmentsWriter(CrossJpa jpa) {
		em = jpa.get();
	}

	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		Integer min = min();
		Integer max = max();
		int increment = 5000;
		for(int i = min; i < max;i+=increment) {
			Query q = em.createQuery("SELECT a from Allegati a where a.id between :min and :max and a.dimensione is null;");
			q.setParameter("min", i);
			q.setParameter("max", i+increment);
			List<Allegati> idPratiche= q.getResultList();

			for(Allegati allegato: idPratiche) {
				em.getTransaction().begin();
				try {
					logger.info("Elaborazione allegato: " + allegato);
					handlePaperwork(allegato);
					em.getTransaction().commit();
				} catch(Exception e){
					logger.error(e);
					em.getTransaction().rollback();
				}
			}
		}

	}

	private void handlePaperwork(Allegati allegati) throws Exception {
		long length = 0;
		byte[] file = allegati.getFile();
		if(file == null) {
			File f = new File(allegati.getPathFile());
			length = f.length();
		} else {
			length = new Long(file.length);
		}
		allegati.setDimensione(length);

		em.merge(allegati);
	}

	private Integer min() throws ClassNotFoundException, SQLException {
		int number = -1;
		Query q = em.createNativeQuery("SELECT min(id) from allegati");

		return new Integer((int) q.getSingleResult());
	}

	private int max() throws ClassNotFoundException, SQLException {
		int number = -1;
		Query q = em.createNativeQuery("SELECT max(id) from allegati");

		return new Integer((int) q.getSingleResult());
	}

}
package cross.cross_scripts.ave_58;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import it.wego.cross.entity.Allegati;

public class AttachmentDimensionFixer extends BaseHandler{

	private static final Logger logger = LogManager.getLogger(AttachmentDimensionFixer.class);
	private EntityManager em;

	public AttachmentDimensionFixer(CrossJpa jpa) {
		super(null, jpa);
		em = getJpa().get();
	}


	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());

		Query q = em.createQuery("SELECT a from Allegati a where a.dimensione is null;");

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

		logger.warn("END " + this.getClass().getName());
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
}

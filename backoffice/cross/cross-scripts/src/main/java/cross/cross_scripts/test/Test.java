package cross.cross_scripts.test;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.CrossJpa;
import cross.cross_scripts.MySql;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;

public class Test extends BaseHandler{
	
	private static final Logger logger = LogManager.getLogger(Test.class);

	private EntityManager em;
	private PraticaDao praticaDao;
	
	public Test(MySql mysql, CrossJpa jpa) {
		super(mysql, jpa);
	}

	@Override
	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		em = jpa.get();
		praticaDao = new PraticaDao(em);
		Pratica pratica = praticaDao.findPratica(298497);
		PraticheEventi praticheEventi = null;
		for(PraticheEventi pe: pratica.getPraticheEventiList()) {
			if(pe.getProtocollo() != null && pe.getProtocollo().equals("c_a662/2024/271249"))
				praticheEventi = pe;
		}
		
		
		System.out.println(praticheEventi);
		System.out.println(pratica);
		
		logger.warn("End run " + this.getClass().getName());
	}
}

package cross.cross_scripts;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrossJpa {
	
	private static final Logger logger = LogManager.getLogger(CrossJpa.class);
	@PersistenceContext
	private EntityManager em;	
	private EntityManagerFactory emf;
	
	private static CrossJpa instance;
	
	public synchronized static CrossJpa getInstance(String datasource, String user, String pwd, String driver) {
		if(instance == null)
			instance = new CrossJpa(datasource, user, pwd, driver);
		return instance;
	}
	
	private CrossJpa(String datasource, String user, String pwd, String driver) {
		Map<String, String> properties = new HashMap<>();					
		properties.put("javax.persistence.jdbc.url", datasource);
		properties.put("javax.persistence.jdbc.user", user);
		properties.put("javax.persistence.jdbc.password", pwd);
		properties.put("javax.persistence.jdbc.driver", driver);

		logger.warn("javax.persistence.jdbc: " + datasource);
		logger.warn("javax.persistence.jdbc: " + user);
		logger.warn("javax.persistence.jdbc: " + pwd);
		logger.warn("javax.persistence.jdbc: " + driver);
		emf = Persistence.createEntityManagerFactory("it.wego.cross-opencross", properties);
	}
	
	public EntityManager get() {
		return emf.createEntityManager();
	}
	
	public void close() {
		emf.close();
	}
}

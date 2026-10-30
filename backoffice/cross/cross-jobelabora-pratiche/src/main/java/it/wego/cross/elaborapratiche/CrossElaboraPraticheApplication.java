package it.wego.cross.elaborapratiche;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.UtentiDao;
import it.wego.cross.elaborapratiche.utilities.myUtility;
import it.wego.cross.service.MailService;


@Configuration
@ImportResource(value = { "classpath:spring/applicationContext.xml" })
@EntityScan("it.wego.cross.entity")
@EnableJpaRepositories("it.wego.cross.dao")
// @ComponentScan(basePackages = { "it.wego.cross.actions" })
@SpringBootApplication
public class CrossElaboraPraticheApplication 
{
	private static final Logger LogClass = LoggerFactory.getLogger(CrossElaboraPraticheApplication.class.getName());

	@PersistenceContext
	private EntityManager em;	
	private EntityManagerFactory emf;

	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private PraticaDao praticaDao;
	@Autowired
	private MailService mailService;    

	@Override
	protected void finalize() {
/*		
		try {			
			em.close();
			emf.close();

		} catch (Exception e) {
			// ...
		}
*/		
	}

	@Bean
	public org.dozer.Mapper mapper() {
		return new DozerBeanMapper();
	}

	@Bean(name = "lookupDao")
	LookupDao lookupDao() {return new LookupDao();}

	@Bean(name = "utentiDao")
	UtentiDao utentiDao() {return new UtentiDao();}

	public static void main(String[] args) {
		
		//SpringApplication.run(CrossElaboraPraticheApplication.class, args);
	}

	public void elaboraPratiche() {
		Properties appl_properties;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();


		try {
		
			appl_properties = myUtility.readConfigProperties("application.properties");

			String conf_datasource =  appl_properties.getProperty("spring.datasource.url");
			String conf_username =  appl_properties.getProperty("spring.datasource.username");
			String conf_password = appl_properties.getProperty("spring.datasource.password");
			String conf_driver = appl_properties.getProperty("spring.datasource.driver-class-name");

			System.out.println("EXECUTE - Read application properties " +  dateFormat.format(cal.getTime()));

			Map<String, String> properties = new HashMap<String, String>();					
			properties.put("javax.persistence.jdbc.url", conf_datasource);
			properties.put("javax.persistence.jdbc.user", conf_username);
			properties.put("javax.persistence.jdbc.password", conf_password);
			properties.put("javax.persistence.jdbc.driver", conf_driver);
	
			System.out.println("EXECUTE - application properties - spring.datasource.url:" +  
					conf_datasource + " time:" + dateFormat.format(cal.getTime()));
			System.out.println("EXECUTE - application properties - spring.datasource.username:" +  
					conf_username + " time:" + dateFormat.format(cal.getTime()));
			System.out.println("EXECUTE - application properties - spring.datasource.url:" +  
					conf_password + " time:" + dateFormat.format(cal.getTime()));
			System.out.println("EXECUTE - application properties - spring.datasource.url:" +  
					conf_driver + " time:" + dateFormat.format(cal.getTime()));

			emf = Persistence.createEntityManagerFactory("it.wego.cross-opencross", properties);
			System.out.println("EXECUTE - createEntityManagerFactory " +  dateFormat.format(cal.getTime()));
			
			em = emf.createEntityManager();
	
			System.out.println("EXECUTE - createEntityManager " +  dateFormat.format(cal.getTime()));

			praticaDao = new PraticaDao(em);

			System.out.println("EXECUTE - SetEntityManager " +  dateFormat.format(cal.getTime()));

			//mailService.gestioneEmail();

			System.out.println("EXECUTE - call mailService.gestioneEmail() " +  dateFormat.format(cal.getTime()));

		} catch (Exception e) {
			e.printStackTrace();
		}		

	}
}

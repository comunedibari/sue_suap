package it.wego.cross.mailservices.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.wego.cross.mailservices.ScheduledTaskInboxGiornaliero;
import it.wego.cross.mailservices.component.MailReaderCross;
import it.wego.cross.mailservices.sql.DaoConfigCross;

@RestController
public class MailController {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskInboxGiornaliero.class.getName());

	@RequestMapping("/crossmailelabpuntuale")
    public String crossmailelabpuntuale(
    		@RequestParam(value = "ente") String ente,
    		@RequestParam(value = "anno") String anno,
    		@RequestParam(value = "mese") String mese,
    		@RequestParam(value = "giorno") String giorno) {
    	
    	logger.info("RUN CONTROLLER crossmailelabpuntuale - Elabora mail inbox cross giornaliero --->>> START ente:" 
				+ ente 
				+ " anno:" + anno 
				+ " mese:" + mese
				+ " giorno:" + giorno);

    	int IdEnte = 0, iAnno = 0, iMese = 0, iGiorno = 0;
    	
    	try { IdEnte = Integer.parseInt(ente); } catch (NumberFormatException e) { IdEnte = 0;}
    	try { iAnno = Integer.parseInt(anno); } catch (NumberFormatException e) { iAnno = 0;}
    	try { iMese = Integer.parseInt(mese); } catch (NumberFormatException e) { iMese = 0;}
    	try { iGiorno = Integer.parseInt(giorno); } catch (NumberFormatException e) { iGiorno = 0;}
    	
		
    	logger.info("RUN CONTROLLER crossmailelabpuntuale after parsing - Elabora mail inbox cross giornaliero --->>> START anno:" 
				+ anno 
				+ " mese:" + mese
				+ " giorno:" + giorno
				);

		Calendar cal = new GregorianCalendar();
		
		DaoConfigCross dao = new DaoConfigCross(); 

		MailReaderCross p = new MailReaderCross();

		p.setDao(dao);
						    	
    	try {
			p.elaboraInboxMassivo(IdEnte, iAnno, iMese, iGiorno);
			
	    	logger.info("RUN THREAD - Elabora mail inbox cross giornaliero <<<--- END ente:" 
    				+ ente 
    				+ " anno:" + anno 
    				+ " mese:" + mese
    				+ " giorno:" + giorno);
		} catch (SQLException e) {
		
			logger.info("RUN THREAD - Elabora mail inbox cross giornaliero <<<--- ERROR anno:" 
    				+  anno 
    				+ " mese:" + mese
    				+ " giorno:" + giorno
    				+ " ERROR: " + e.getMessage());
			
			e.printStackTrace();
		}

		return "crossmailelabpuntuale END!";
    }
}
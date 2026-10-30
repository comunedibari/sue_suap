package it.wego.cross.mailservices.scheduler;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.component.MailReaderCross;
import it.wego.cross.mailservices.component.MailReaderCrossWeb;
import it.wego.cross.mailservices.sql.DaoConfigCross;
import it.wego.cross.mailservices.utilities.myUtility;

public class JobElaboraByMessageId implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JobElaboraByMessageId.class.getName());
	
	
	@Override
	public void run() {

		DaoConfigCross dao = new DaoConfigCross(); 

		MailReaderCrossWeb p = new MailReaderCrossWeb();

		p.setDao(dao);
				
		try {
			p.elaboraInboxByMessageId(2001, "822050833.163.1721635781888@mail.pec.rupar.puglia.it");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**/
	public static void main(String[] args) {
		Properties configProperties;
		try {
			configProperties = myUtility.readConfigProperties("application.properties");
			JobElaboraByMessageId p = new JobElaboraByMessageId();
			p.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**/
}

package it.wego.cross.mailservices.scheduler;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.component.MailReaderCross;
import it.wego.cross.mailservices.sql.DaoConfigCross;

public class JobElaboraMeseInCorso implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JobElaboraMensileMassivo.class.getName());
	
	private int anno_elaborazione;
	private int mese;
	private int giorno;

	public JobElaboraMeseInCorso(int anno_elaborazione, int mese, int giorno){
		this.setAnno_elaborazione(anno_elaborazione);
		this.setMese(mese);
		this.setGiorno(giorno);
	}
	
	/**
	 * @return the anno_elaborazione
	 */
	public int getAnno_elaborazione() {
		return anno_elaborazione;
	}

	/**
	 * @param anno_elaborazione the anno_elaborazione to set
	 */
	public void setAnno_elaborazione(int anno_elaborazione) {
		this.anno_elaborazione = anno_elaborazione;
	}

	/**
	 * @return the mese
	 */
	public int getMese() {
		return mese;
	}

	/**
	 * @param mese the mese to set
	 */
	public void setMese(int mese) {
		this.mese = mese;
	}
	
	/**
	 * @return the giorno
	 */
	public int getGiorno() {
		return giorno;
	}

	/**
	 * @param giorno the giorno to set
	 */
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	@Override
	public void run() {
		logger.info("RUN THREAD - Elabora mail inbox cross giornaliero --->>> START anno:" 
				+ this.getAnno_elaborazione() 
				+ " mese:" + this.getMese()
				+ " giorno:" + this.getGiorno()
				);

		Calendar cal = new GregorianCalendar();
		
		DaoConfigCross dao = new DaoConfigCross(); 

		MailReaderCross p = new MailReaderCross();

		p.setDao(dao);
				
		//LOR Solo x Test
		// if(giorno == 31 && anno_elaborazione == 2024 && mese == 5) {p.executeGiornaliero(anno_elaborazione, mese, giorno);}
		p.executeGiornaliero(anno_elaborazione, mese, giorno);
		
		logger.info("RUN THREAD - Elabora mail inbox cross giornaliero <<<--- END anno:" 
				+  this.getAnno_elaborazione() 
				+ " mese:" + this.getMese()
				+ " giorno:" + this.getGiorno());
	}
}

package it.wego.cross.mailservices.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.component.MailReaderCross;
import it.wego.cross.mailservices.sql.DaoConfigCross;
import it.wego.cross.mailservices.utilities.myUtility;

public class JobElaboraMensileMassivo implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(JobElaboraMensileMassivo.class.getName());
	
	private String anno_elaborazione;
	private int mese;

	public JobElaboraMensileMassivo(String anno_elaborazione, int mese){
		this.setAnno_elaborazione(anno_elaborazione);
		this.setMese(mese);
	}
	
	/**
	 * @return the anno_elaborazione
	 */
	public String getAnno_elaborazione() {
		return anno_elaborazione;
	}

	/**
	 * @param anno_elaborazione the anno_elaborazione to set
	 */
	public void setAnno_elaborazione(String anno_elaborazione) {
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
		
	@Override
	public void run() {
		int[] mesi_31_giorni = {1,3,5,7,8,10,12};
		int[] mesi_30_giorni = {4,6,9,11};
		int[] mesi_28_29_giorni = {2};
		
		Calendar cal = new GregorianCalendar();
		
		DaoConfigCross dao = new DaoConfigCross(); 

		MailReaderCross p = new MailReaderCross();

		p.setDao(dao);

		logger.info("RUN THREAD - Elabora mail inbox cross massivo --->>> START anno:" + this.getAnno_elaborazione() + " mese:" + this.getMese());
		dao.loggerDB("RUN THREAD - Elabora mail inbox cross massivo --->>> START anno:" + this.getAnno_elaborazione() + " mese:" + this.getMese());
				
		List<String> list_annoelab = myUtility.getTokens(anno_elaborazione);
		
		for(String anno_elab : list_annoelab) {
			String msgTrace = " Sto elaborando l'anno:" + anno_elab;
			logger.info(msgTrace);
			dao.loggerDB(msgTrace);
			
			for(int giorno=0; giorno<31; giorno++)
			{
				if(IntStream.of(mesi_30_giorni).anyMatch(x -> x == mese)) {
					if((giorno+1)>30) break;
				}
	
				if(IntStream.of(mesi_28_29_giorni).anyMatch(x -> x == mese)) {
					if((giorno+1)>29) break;
				}
	
				msgTrace = " Sto elaborando il giorno:" + (giorno+1) + " mese:" + mese + " anno:" + anno_elab;
				logger.info(msgTrace);
				dao.loggerDB(msgTrace);

				Date today = MailReaderCross.parseDate((giorno+1)+"/"+mese+"/"+anno_elab.trim());
				cal.setTime(today);
	
				Date finalDate = cal.getTime();
				System.out.println("finalDate:" + finalDate);
				dao.loggerDB("finalDate:" + finalDate.toString());
				
				p.executeMassivo(Integer.valueOf(anno_elab.trim()).intValue(), mese, (giorno+1));
			}
		}
		
		logger.info("RUN THREAD - Elabora mail inbox cross massivo <<<--- END anno:" +  this.getAnno_elaborazione() + " mese:" + this.getMese());
		dao.loggerDB("RUN THREAD - Elabora mail inbox cross massivo <<<--- END anno:" +  this.getAnno_elaborazione() + " mese:" + this.getMese());
	}
	/**/
	public static void main(String[] args) {
		Properties configProperties;
		try {
			configProperties = myUtility.readConfigProperties("application.properties");
			String anno_elab = configProperties.getProperty("anno.elaborazione").trim();
			JobElaboraMensileMassivo p = new JobElaboraMensileMassivo(anno_elab, 1);
			p.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**/
}

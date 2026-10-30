package it.wego.cross.mailservices.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.wego.cross.mailservices.sql.DaoConfigCross;
import it.wego.cross.mailservices.utilities.myUtility;

public class GetAsyncEmails {
	private static final Logger logger = LoggerFactory.getLogger(GetAsyncEmails.class.getName());

	/*
	class GetAsyncEmails extends AsyncTask<String, Integer, Message[]>{
	
	@Override
	protected Message[] doInBackground(String... args) {
	    // TODO Auto-generated method stub
	    Message[] messages = MailHelper.getAllMail(args[0], args[1]);
	
	    return messages;
	}
	
	protected void onPostExecute(Message[] result){
	    if(result.length > 1){
	        Message message = result[0];
	        String content = MailHelper.getMessageHTML(message);        
	        System.out.println("#### DEBUG####" + content);
	    }
	}
	*/
	public static void main(String args[]) {
		Properties configProperties;
		
/*
* 		Date date = new Date(); 
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europa/Roma"));

		cal.setTime(date);
		int anno = cal.get(Calendar.YEAR);
		int mese = cal.get(Calendar.MONTH);		
		int oggi_giorno = cal.get(Calendar.DAY_OF_MONTH);
*/		
		int numero_giorni_da_elaborare = 0;
		int giorno_elab = 0;

		Calendar mydate = new GregorianCalendar();
		String mystring = "2024-03-14";
		Date thedate;
		try {
								
			thedate = new SimpleDateFormat("yyyy-M-dd", Locale.ITALY).parse(mystring);
	
			mydate.setTime(thedate);
			//breakdown
			System.out.println("mydate -> "+mydate);
			System.out.println("year   -> "+mydate.get(Calendar.YEAR));
			System.out.println("month  -> "+mydate.get(Calendar.MONTH));
			System.out.println("dom    -> "+mydate.get(Calendar.DAY_OF_MONTH));
			System.out.println("dow    -> "+mydate.get(Calendar.DAY_OF_WEEK));
			System.out.println("hour   -> "+mydate.get(Calendar.HOUR));
			System.out.println("minute -> "+mydate.get(Calendar.MINUTE));
			System.out.println("second -> "+mydate.get(Calendar.SECOND));
			System.out.println("milli  -> "+mydate.get(Calendar.MILLISECOND));
			System.out.println("ampm   -> "+mydate.get(Calendar.AM_PM));
			System.out.println("hod    -> "+mydate.get(Calendar.HOUR_OF_DAY));
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		try {
			DaoConfigCross dao = new DaoConfigCross();		

			configProperties = myUtility.readConfigProperties("application.properties");
			numero_giorni_da_elaborare = Integer.valueOf(configProperties.getProperty("numero.giorni.elaborazione.giornaliera"));
			
			String dataCorrente = dao.queryString("SELECT CURRENT_DATE() as data_corrente", "data_corrente");
			//String dataCorrente = dao.queryString("SELECT DATE_FORMAT(NOW(),'%Y-03-01') as data_corrente", "data_corrente");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd", Locale.ITALY);
			LocalDate date = LocalDate.parse(dataCorrente, formatter);
			int anno = date.getYear(); 
			int giorno = date.getDayOfMonth();
			int mese = date.getMonthValue(); 
						
		    System.out.println("ANNO:" + anno + " MESE:" + mese + " GIORNO:" + giorno);
	
	    	giorno_elab = giorno; 
		    for(int i=0; i<(numero_giorni_da_elaborare+1); i++)
			{
		    	if(giorno_elab<1) {
		    		//Calendar cal = Calendar.getInstance();
		    		mese--;
		    		if(mese == 0){
		    			mese = 12;giorno_elab=31;
		    		} else {
		    			String last_day_previous_month = dao.queryString("SELECT LAST_DAY(CURDATE() - INTERVAL 1 MONTH) as last_day_previous_month", "last_day_previous_month");
		    			date = LocalDate.parse(last_day_previous_month, formatter);
		    			anno = date.getYear(); 
		    			giorno_elab = giorno = date.getDayOfMonth();
		    			mese = date.getMonthValue();		    			
		    		}
		    	} 
		    		
			    System.out.println("executeMassivo ANNO:" + anno + " MESE:" + mese + " GIORNO:" + giorno_elab);
				//p.executeMassivo(anno, mese, giorno_elab));
		    	giorno_elab--;
			}
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("executeMassivo END");

	}

}
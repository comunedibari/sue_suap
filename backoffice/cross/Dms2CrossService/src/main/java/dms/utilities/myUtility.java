package dms.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class myUtility {

	public Properties properties;

	public HashMap<String, String> map;

	public final static String PROP_FILE_NAME = "dms2cross.application.properties";

	public final static String LOG4J_FILENAME = "dms2cross.log4j.properties";

	public myUtility() {
		map = new HashMap<>();
		properties = new Properties();
		this.readApplicationProperties(PROP_FILE_NAME);
		this.readApplicationProperties(LOG4J_FILENAME);
		String nomeFile = "";
		nomeFile = properties.getProperty("logfile");
		LogClass.setFileName(nomeFile);
		LogClass.trace("NomeFileLog:" + nomeFile + ".log");
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the map
	 */
	public HashMap<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	/**
	 * @param propertiesName
	 * @return
	 * @throws Exception
	 * 
	 *             Ritorna il properties passato il cui nome viene passato in
	 *             input
	 */
	public static Properties readConfigProperties(String propertiesName)
			throws Exception {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream resourceAsStream = classLoader
				.getResourceAsStream(propertiesName);
		Properties properties = new Properties();
		properties.load(resourceAsStream);
		return properties;
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 * 
	 *             Ritorna il valore passato in input prendendolo dal file
	 *             application.properties
	 */
	public static String getApplicationPropertyValue(String key)
			throws Exception {
		Properties configProperties = myUtility.readConfigProperties("dms2cross.application.properties");
		String url = configProperties.getProperty(key);
		return url;
	}

	/**
	 * @return
	 * 
	 *         Ritorna la data di oggi al tempo 00:00:00 in formato sql.Date
	 */
	public static java.sql.Date getTodaySQLDate() {
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // your util date
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your
		// sql
		// date
		return sqlDate;
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Converte util.Date in sql.Date
	 */
	public static java.sql.Date dateToSQLDate(Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public java.sql.Date getCurrentDatetime() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}
	/**
	 * @return
	 * 
	 *         Torna ieri nel formato util.Date
	 */
	public static Date getIeri() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * @param num
	 * @return
	 * 
	 *         Torna la data in formato util.Date meno il numero di giorni
	 *         indietro passato in input
	 */
	public static Date getDataByNumeroGiorniIndietro(int num) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -num);
		return cal.getTime();
	}


	/**
	 * @param date
	 * @return
	 * 
	 *         Trasforma la data passata in input util.Date in una stringa con
	 *         formato: "dd/MM/yyyy
	 */
	public static String dateToString(Date date) {
		String result = "";
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String format = formatter.format(date);
			result = format;
		}
		return result;
	}

	/**
	 * @param date
	 * @return
	 * @throws Exception
	 * 
	 *             Trasforma la data passata in input in Stringa formato:
	 *             dd/MM/yyyy in formato util.Date
	 */
	public static Date parseDate(String date) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return (Date) formatter.parse(date);
	}

	/**
	 * @param date
	 * @return
	 * @throws Exception
	 * 
	 *             Trasforma la data passata in input in Stringa formato:
	 *             dd-MM-yyyy in formato util.Date
	 */
	public static Date parseDateNoEscape(String date) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return (Date) formatter.parse(date);
	}

	/**
	 * @param hour
	 * @return
	 * @throws Exception
	 * 
	 *             Trasforma la data passata in input in Stringa formato:
	 *             dd/MM/yyyy HH:mm:ss in formato util.Date
	 */
	public static Date parseDateHHMMSS(String hour) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return (Date) formatter.parse(hour);
	}

	/**
	 * @param date
	 * @param days
	 * @return
	 * 
	 *         Toena la data in formato util.Date avendo in input un' altra data
	 *         e il numero di giorni da aggiungere a tale data
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Trasforma da util.Date a Timestamp
	 */
	public static Timestamp dateToTimestamp(Date date) {
		Timestamp dataTimeStamp = new Timestamp(date.getTime());
		return dataTimeStamp;
	}

	/**
	 * @return
	 * 
	 *         Ritona la data in formato util.Date di oggi ma con tempo 00:00:00
	 */
	public static Date getToTimeDayMidNight() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		return now.getTime();
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Torna true se il giorno passato e' oggi
	 */
	public static Boolean dayIsEqualToToDay(Date date) {
		Boolean response = false;
		String dateToString = dateToString(date);
		String dateToStringOggi = dateToString(getToTimeDayMidNight());
		if (dateToStringOggi.equals(dateToString)) {
			response = true;
		}
		return response;
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Torna true se il giorno passato e' prima di oggi
	 */
	public static Boolean dayIsBeforeToToDay(Date date) {
		Boolean response = false;
		Calendar c = Calendar.getInstance();

		// set the calendar to start of today
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// and get that as a Date
		Date today = c.getTime();

		// user-specified date which you are testing
		// let's say the components come from a form or something
		int year = 2011;
		int month = 5;
		int dayOfMonth = 20;

		// reuse the calendar to set user specified date
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		// test your condition
		if (date.before(today)) {
			response = true;
		} else {
			response = false;
		}
		return response;
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Torna in formato Stringa HH:mm l' ora della data passata in input
	 */
	public static String timeStampToString(Timestamp date) {
		String result = "";
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			String format = formatter.format(date);
			result = format;
		}
		return result;
	}

	/**
	 * @param date
	 * @return
	 * 
	 *         Torna in formato Stringa HH:mm l' ora della data passata in input
	 */
	public static String fullTimeStampToString(Timestamp date) {
		String result = "";
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String format = formatter.format(date);
			result = format;
		}
		return result;
	}

	/**
	 * @param dataOraChiamata
	 * @return
	 * 
	 *         Converte da Stringa DDMMYYY a stringa YYYYMMDDHHMMSS
	 */
	public static String formatDDMMYYYtoYYYYMMDDHHMMSS(String dataOraChiamata) {
		String[] split = dataOraChiamata.split("-");
		String giorno = "";
		String anno = "";
		String mese = "";
		for (int i = 0; i < split.length; i++) {
			giorno = split[0];
			mese = split[1];
			anno = split[2];
		}
		String nuovaData = anno + "-" + mese + "-" + giorno;
		nuovaData = nuovaData + " 00:00:00";
		return nuovaData;
	}

	/**
	 * @param dataOraChiamata
	 * @return
	 * @throws Exception
	 * 
	 *             Converte da Stringa Converte da Stringa DDMMYYYYHHMMSS in
	 *             Timestamp
	 */
	public static Timestamp convertDDMMYYYYHHMMSStiTimestamp(
			String dataOraChiamata) throws Exception {
		dataOraChiamata = dataOraChiamata + " 00:00:00";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dataD = (Date) formatter.parse(dataOraChiamata);
		Timestamp timestamp = new Timestamp(dataD.getTime());
		return timestamp;
	}

	/**
	 * Metodo utilizzato per convertire inputStream in array di byte
	 * 
	 * @param InputStream
	 *            - input stream del file
	 * @return byte[] - array di byte che compone il file
	 */
	public byte[] convertInputStreamToByteArray(InputStream isFile)
			throws IOException {
		byte[] buffer = new byte[512];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bytesRead;
		while ((bytesRead = isFile.read(buffer)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}

		return baos.toByteArray(); // IOUtils.toByteArray(isFile);
	}

	/**
	 * Aggiunge n giorni ad una data passata e ritorna la nuova data
	 * 
	 * @param int days
	 * @param Date
	 *            dt
	 * @return Date - la nuova data costruita
	 */
	public static Date addDaysToDate(int n, Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, n);
		dt = c.getTime();
		return dt;
	}

	public static boolean isInt(String str) {

		try {
			int iCheck = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public static boolean checkIsNumeric(String str)
	{
		for (char c : str.toCharArray())
		{
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}

	public static boolean checkIsNumericWithdecimal(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}  
	}
	public static int convertDecimalToInteger(String str) { 
		int result = 0;
		try {  
			double d = Double.parseDouble(str.replaceAll(",", "."));
			result = (int) d;
			return result;
		} catch(NumberFormatException e){  
			return result;  
		}  
	}
	public static boolean checkIsNumericFormat(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	public String getValuePropertiesMap(String key)
	{
		String value = "";
		this.getMap().get(key);
		return value;
	}

	public String getValueProperties(String key)
	{
		String value = this.getProperties().getProperty(key);
		return value;
	}

	public void readApplicationProperties(String fileProperties) {

		InputStream inputStream;

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(fileProperties);

			if (inputStream != null) {
				properties.load(inputStream);
			} else
				throw new FileNotFoundException("property file '" + fileProperties + "' not found in the classpath");

			inputStream.close();

			map = new HashMap<String, String>();
			for (String key : properties.stringPropertyNames()) {
				map.put(key, properties.getProperty(key));
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}	    
	}

	public void readLog4jProperties() {	    
		String nomeFileLog;

		InputStream inputStream;

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(LOG4J_FILENAME);

			if (inputStream != null) {
				properties.load(inputStream);
			} else
				throw new FileNotFoundException("property file '" + LOG4J_FILENAME + "' not found in the classpath");

			inputStream.close();

			map = new HashMap<>();

			nomeFileLog = properties.getProperty("logfile");	        

			map.put("nomeFileLog", nomeFileLog);

			LogClass.setFileName(nomeFileLog);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}	    
	}

	public static String convert_to_utf8(String str) {
		final StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			final char ch = str.charAt(i);
			if (ch <= 127) 
			{
				stringBuilder.append(ch);
			}
			else 
			{
				stringBuilder.append(String.format("%02x", (int)ch));
			}
		}
		String s = stringBuilder.toString();
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		String strUTF_8 =new String(data, StandardCharsets.UTF_8);
		return strUTF_8;
	}

	public static void main(String[] args) {
		//HashMap<String, String> hashmap = new HashMap<String, String>();
		HashMap<String, String> hashmap = null;

		/*
		String descr = "Segnalazione Certificata Inizio Attivita\u0027";
		String strA_ISO_8859_1_i = new String(descr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);

		System.out.println("ISO_8859_1 strA est = "+ strA_ISO_8859_1_i + "\n String_ISO_8859_1To_UTF_8 = " + convert_to_utf8(strA_ISO_8859_1_i));
		
		descr = convert_to_utf8(descr);
		
		String str = "445,5";
		System.out.println("isInt: " + myUtility.isInt(str) 
		+ " checkIsNumeric: "+ myUtility.checkIsNumeric(str)
		+ " checkIsNumericFormat: "+ myUtility.checkIsNumericFormat(str)
		+ " checkIsNumericWithdecimal: "+ myUtility.checkIsNumericWithdecimal(str)
				);

		int result = myUtility.convertDecimalToInteger(str);
		*/
		myUtility utl = new myUtility();
		String getAllPratiche = utl.getValueProperties("dms.getAllPratiche");
		String urlDettPratica = utl.getValueProperties("dms.getDettaglioPratica");
		String getAllegatoPratica = utl.getValueProperties("dms.getAllegatoPratica");
		String getAllegatiById = utl.getValueProperties("dms.getAllegatiById");
		System.out.println(" getAllPratiche: " + getAllPratiche + 
				" urlallPraticche: " + urlDettPratica +
				" getAllegatoPratica: " + getAllegatoPratica +
				" getAllegatiById: " + getAllegatiById);
		
		/*		
		myUtility cls = new myUtility();
		//		cls.readLog4jProperties();
		cls.readApplicationProperties(PROP_FILE_NAME);
		//		cls.readApplicationProperties(LOG4J_FILENAME);

		logger.trace("Fine caricamento file properties");
		hashmap = cls.getMap();
		Set entrySet = hashmap.entrySet();		 
		Iterator it = entrySet.iterator();

		// Iterate through HashMap entries(Key-Value pairs)
		System.out.println("HashMap Key-Value Pairs : ");
		while(it.hasNext()){
			Map.Entry me = (Map.Entry)it.next();
			System.out.println("Key is: "+me.getKey() + 
					" & " + 
					" value is: "+me.getValue());
		}

		 */
		System.out.println("END");
	}

}

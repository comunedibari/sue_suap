package it.wego.cross.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.dom4j.Document;
import org.apache.commons.lang3.StringUtils;
import utilities.LogClass;

public class myUtility {

	public Properties properties;

	public static LogClass logger;

	public HashMap<String, String> map;

	private String PROP_FILE_NAME;

	private String LOG4J_FILENAME;

	public myUtility(String propfile, String log4jfile) {
		map = new HashMap<>();
		properties = new Properties();

		this.PROP_FILE_NAME = propfile;
		this.LOG4J_FILENAME = propfile;
		this.readApplicationProperties(propfile);
		this.readApplicationProperties(log4jfile);
		String nomeFile = "";
		nomeFile = properties.getProperty("logfile");
		LogClass.setFileName(nomeFile);
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
		Properties configProperties = myUtility.readConfigProperties("bonifica.application.properties");
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

	public static java.sql.Date getCurrentDatetime() {
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

	public static Date parseDateTime(String date) throws Exception {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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

	public static boolean findInString(String word, String text)
	{
		String tmpTextLowercase = text.toLowerCase();
		return tmpTextLowercase.indexOf(word.toLowerCase()) > -1;
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
	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public void readLog4jProperties() {	    
		String nomeFileLog;

		InputStream inputStream;

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(this.LOG4J_FILENAME);

			if (inputStream != null) {
				properties.load(inputStream);
			} else
				throw new FileNotFoundException("property file '" + LOG4J_FILENAME + "' not found in the classpath");

			inputStream.close();

			map = new HashMap<>();

			nomeFileLog = properties.getProperty("logfile");	        

			map.put("nomeFileLog", nomeFileLog);

			logger.setFileName(nomeFileLog);

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

	public static String get_format_date(String date) {
		String formato = null;
		Matcher matcher = null;
		Pattern pattern = null;

		ArrayList formatDates = new ArrayList();

		formatDates.add(date);

		String regex_slash = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"; // dd/MM/yyyy
		String regex_minus = "^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$"; // dd-MM-yyyy

		pattern = Pattern.compile(regex_slash);
		matcher = pattern.matcher(date);
		if(matcher.matches()) 
		{
			formato = "dd/MM/yyyy";
		} else {
			pattern = Pattern.compile(regex_minus);
			matcher = pattern.matcher(date);
			if(matcher.matches()) {formato = "dd/MM/yyyy";} else {formato = "yyyy-MM-dd";}
		}
		//System.out.println(date +" : "+ matcher.matches());
		return formato;
	}
	public static String replaceNull(String input) {
		return input == null ? "" : input;
	}

	public static String setString(String inputString, String stringToReplace, String stringToReplaceWith) {

		int length = stringToReplace.length();
		int inputLength = inputString.length();

		int startingIndexofTheStringToReplace = inputString.indexOf(stringToReplace);

		String finalString = inputString.substring(0, startingIndexofTheStringToReplace) + stringToReplaceWith
				+ inputString.substring(startingIndexofTheStringToReplace + length, inputLength);

		return finalString;

	}
	public static String replaceSingleQuote(String input) {
		return input.replaceAll("\\\'", "\\\\\\\'");
	}

	public static String getLocalTime() {
		String localTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY);
		Calendar cal = Calendar.getInstance();
		localTime = sdf.format(cal.getTime());
		return localTime;
	}
	public static String getLocalTimeZone() {
		String localTimeZone = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ITALY);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		localTimeZone = sdf.format(new Date());
		return localTimeZone;
	}


	public static XMLGregorianCalendar getXMLGregorianCalendar() throws DatatypeConfigurationException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date date = new Date();
		GregorianCalendar gcal = new GregorianCalendar();
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

		return date2;
	}

	public static byte[] readFile(File file) {
		byte[] fileContent = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			int fileSize = bis.available();
			fileContent = new byte[fileSize];
			bis.read(fileContent);
			if(bis!=null) {
				bis.close();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static byte[] zipMultipleFiles (File[] files) {
		byte[] filesInZip = null;
		ByteArrayOutputStream bOutStream = new ByteArrayOutputStream();
		ZipOutputStream zOutStream = new ZipOutputStream(bOutStream);
		for(int i=0;i<files.length;i++) {
			File tempFile = files[i];
			byte[] fileContentInBytes = readFile(tempFile);
			if(fileContentInBytes!=null && fileContentInBytes.length>0)
			{
				String eachFileName = tempFile.getName();
				ZipEntry ze = new ZipEntry(eachFileName);
				try {
					zOutStream.putNextEntry(ze);
					zOutStream.write(fileContentInBytes,0, fileContentInBytes.length);
					zOutStream.closeEntry();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			zOutStream.finish();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		filesInZip = bOutStream.toByteArray();
		try {
			zOutStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return filesInZip;
	}

	public static void writeFileInBytes(byte[] ContentInBytes, String filename) {
		try {
			FileOutputStream fout = new FileOutputStream("/tmp/" + filename);
			fout.write(ContentInBytes);
			if(fout!=null) {
				fout.close();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void zipFiles(String filename, ArrayList<String> files) throws IOException {	
		ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));

		byte[] buffer = new byte[10000];

		for (int i = 0; i < files.size(); i++) {
			
			File f = new File("/tmp/"+files.get(i));

			if (f.exists()) {
				
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
				
				zip.putNextEntry(new ZipEntry(files.get(i)));

				boolean done = false;

				while (!done) {
					int num = in.read(buffer);

					if (num > 0) {
						zip.write(buffer, 0, num);
					}

					done = (num < buffer.length);
				}

				in.close();
			}
		}

		zip.flush();
		zip.close();		
	}
	public static int randInt() {

		int min = 80001, max = 899999999;
		
	    Random rand = new Random();
	    
	    int randomNum = 0;
	    
	    randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	public static String removeExtension(String s) {

	    String separator = System.getProperty("file.separator");
	    String filename;

	    int lastSeparatorIndex = s.lastIndexOf(separator);
	    if (lastSeparatorIndex == -1) {
	        filename = s;
	    } else {
	        filename = s.substring(lastSeparatorIndex + 1);
	    }

	    int extensionIndex = filename.lastIndexOf(".");
	    if (extensionIndex == -1)
	        return filename;

	    return filename.substring(0, extensionIndex);
	}	
	public static String getWithoutExtension(String fileFullPath){
	    return fileFullPath.substring(0, fileFullPath.lastIndexOf('.'));
	}
	public static void creaFileTimbrato(String PathFileNew, byte[] fileAllegato) {
		try {
			File file = new File(PathFileNew);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			} 
			FileOutputStream writer = new FileOutputStream(PathFileNew);
			writer.write(fileAllegato);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

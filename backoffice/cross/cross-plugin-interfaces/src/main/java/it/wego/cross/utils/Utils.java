/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

//import it.gov.impresainungiorno.schema.suap.pratica.*;
//import it.gov.impresainungiorno.schema.suap.ricevuta.RicevutaPraticaSUAP;
//import it.wego.cross.beans.ErroreBean;
import it.wego.cross.constants.SessionConstants;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

/**
 *
 * @author giuseppe
 */
public class Utils {

    public static final String ITALIAN_DATE_FORMAT = "dd/MM/yyyy";
    public static final String ITALIAN_DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String US_DATE_FORMAT = "yyyy-MM-dd";
    static SimpleDateFormat italianSdf = new SimpleDateFormat(ITALIAN_DATE_FORMAT);
    static SimpleDateFormat italianSdtf = new SimpleDateFormat(ITALIAN_DATETIME_FORMAT);
    static SimpleDateFormat usSdf = new SimpleDateFormat(US_DATE_FORMAT);
//    public static ErroreBean serializeErroreBean(boolean esito, Errori errore) {
//        ErroreBean bean = new ErroreBean();
//        if (esito) {
//            bean.setCorrect(esito);
//            return bean;
//        } else {
//            bean.setCorrect(esito);
//            bean.setErrore(errore);
//            return bean;
//        }
//    }
    public static String getStackTrace(Throwable trowable) {
        if (trowable != null) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            trowable.printStackTrace(printWriter);
            return result.toString();
        } else {
            return null;
        }
    }

    public static String limitText(String text, int limitChars) {
        if (text.length() <= limitChars) {
            return text;
        } else {
            String newText = text.substring(0, limitChars - 5);
            return newText + " ...";
        }
    }

    public static Object getSingleResult(Query query) {
        try {
            return query.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (NoResultException e) {
            return null;
        }
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]+(\\.[\\w\\-]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }

    /**
     * ***********************************************
     * Funzione per il controllo del codice fiscale. Linguaggio: Java. Versione
     * del: 2002-07-07 http://www.icosaedro.it/cf-pi/cf-java.txt
     * ************************************************
     */
    public static String ControllaCF(String cf) {
        int i, s, c;
        String cf2;
        int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
            11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        if (cf.length() == 0) {
            return "";
        }
        if (cf.length() != 16) {
            return "La lunghezza del codice fiscale non è\n"
                    + "corretta: il codice fiscale dovrebbe essere lungo\n"
                    + "esattamente 16 caratteri.";
        }
        cf2 = cf.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                return "Il codice fiscale contiene dei caratteri non validi:\n"
                        + "i soli caratteri validi sono le lettere e le cifre.";
            }
        }
        s = 0;
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s = s + setdisp[c - 'A'];
        }
        if (s % 26 + 'A' != cf2.charAt(15)) {
            return "Il codice fiscale non è corretto:\n"
                    + "il codice di controllo non corrisponde.";
        }
        return "";
    }

//    public static EntityManager getEM() {
//        EntityManager em = emf.createEntityManager();
//        return em;
//    }
    public static Date addDaysToDate(int days) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        date = cal.getTime();
        return date;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        date = cal.getTime();
        return date;
    }

    public static boolean isNonNegativeInteger(String value) {
        try {
            Integer x = Integer.valueOf(value);
            if (x.intValue() >= 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static HashMap<String, Integer> getPagination(Query query, String currentPage) {
        HashMap<String, Integer> pagination = new HashMap<String, Integer>();

        if (currentPage == null || !Utils.isNonNegativeInteger(currentPage)) {
            currentPage = "1";
        }
        int currentPageInt = Integer.valueOf(currentPage);
        int fromRow = (currentPageInt - 1) * SessionConstants.ROW_PER_PAGE;
        int toRow = fromRow + SessionConstants.ROW_PER_PAGE;

        int size = ((Long) query.getSingleResult()).intValue();

        int numPages = (int) (Math.ceil(size / (double) SessionConstants.ROW_PER_PAGE));

        pagination.put("fromRow", fromRow);
        pagination.put("toRow", toRow);
        pagination.put("size", size);
        pagination.put("numPages", numPages);
        pagination.put("currentPage", currentPageInt);

        return pagination;
    }

    public static String dateItalianFormat(Date date) {
        return italianSdf.format(date);
    }

    public static String dateTimeItalianFormat(Date date) {
        return italianSdtf.format(date);
    }

    public static Date italianFormatToDate(String date) throws ParseException {
        return italianSdf.parse(date);
    }
    
    public static Date usFormatToDate(String date) throws ParseException {
        return usSdf.parse(date);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static String getAttribute(HttpServletRequest request, String key) {
        Object obj = request.getAttribute(key);
        if (obj == null) {
            return "";
        } else {
            return (String) obj;
        }
    }

    //verifica se un oggetto è o null o stringa vuota
    public static boolean e(Object o) {
        return ((o == null) || (o.toString().trim().equals("")));
    }

    public static String trim(String string) {
        if (string == null) {
            return "";
        } else {
            return string.trim();
        }
    }

    public static String implode(List o, String sep) {
        return StringUtils.join(o, sep);
    }

    public static String itDateToEnDate(String enDate) throws ParseException {
        SimpleDateFormat sdfIt = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sdfIt.parse(enDate));
    }

    public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date data) throws DatatypeConfigurationException {
        if (data == null) {
            return null;
        }
        XMLGregorianCalendar xmlcalendar;
        GregorianCalendar gc = new GregorianCalendar();

        gc.setTimeInMillis(data.getTime());
        xmlcalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        return xmlcalendar;
    }

    public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar data) {
    	if(data == null) {
            return null;
        }else {
        Date converted = data.toGregorianCalendar().getTime();
        return converted;
        }
    }

    public static long getDaysBetweenDates(Date from, Date to) {
        long startTime = from.getTime();
        long endTime = to.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return diffDays;
//        int days = (int) ((from.getTime() - to.getTime()) / (1000 * 60 * 60 * 24));
//        return days;
    }

    public static int elapsed(Calendar before, Calendar after, int field) {
        Calendar beforeClone = (Calendar) before.clone(); // Otherwise changes are been reflected.
        Calendar afterClone = (Calendar) after.clone(); // Otherwise changes are been reflected.

        beforeClone.set(Calendar.HOUR, 0);
        beforeClone.set(Calendar.AM_PM, Calendar.AM);
        beforeClone.set(Calendar.MINUTE, 0);
        beforeClone.set(Calendar.SECOND, 0);

        afterClone.set(Calendar.HOUR, 0);
        beforeClone.set(Calendar.AM_PM, Calendar.AM);
        afterClone.set(Calendar.MINUTE, 0);
        afterClone.set(Calendar.SECOND, 1);

        int elapsed = -1;
        while (!beforeClone.after(afterClone)) {
            beforeClone.add(field, 1);
            elapsed++;
        }
        return elapsed;
    }

    public static XMLGregorianCalendar annoProtocolloStringToXmlGregorianCalendar(String stringa) throws ParseException, DatatypeConfigurationException {
        DateFormat formatter = new SimpleDateFormat("yyyy");
        XMLGregorianCalendar xmlcalendar;
        Date data = (Date) formatter.parse(stringa);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        xmlcalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        return xmlcalendar;
    }
    
    public static XMLGregorianCalendar ItalianDateStringToXmlGregorianCalendar(String stringa) throws ParseException, DatatypeConfigurationException {
        DateFormat formatter = new SimpleDateFormat(ITALIAN_DATE_FORMAT);
        XMLGregorianCalendar xmlcalendar;
        Date data = (Date) formatter.parse(stringa);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        xmlcalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        return xmlcalendar;
    }    

//    public static DataSource createAttachment(String fileContent, String fileName) throws Exception {
//        ByteArrayDataSource ds = null;
//        if (fileContent != null && !fileContent.equals("") && fileName != null && !fileName.equals("")) {
//            byte[] data = fileContent.getBytes();
//            String contentType = Utils.getContentType(fileContent, fileName);
//            ds = new ByteArrayDataSource(data, contentType);
//            ds.setName(fileName);
//        }
//        return ds;
//    }
    public static String getConfiguration(EntityManager em, String codSportello, String key) {
        Query query = em.createNamedQuery("SELECT c FROM Configuration c WHERE c.sportello = :sportello and c.name=:key");
        query.setParameter("sportello", codSportello);
        query.setParameter("key", key);
        it.wego.cross.entity.Configuration configuration = (it.wego.cross.entity.Configuration) Utils.getSingleResult(query);
        if (configuration != null) {
            return configuration.getValue();
        } else {
            return "";
        }
    }

    public static String convertDateToProtocolloString(Date date) throws UnsupportedEncodingException {
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(date);
        return URLEncoder.encode(dateString, "UTF-8");
    }

    /**
     * ^^CS AGGIUNTA
     */
    public static String convertDataToString(Date date) {
        if (date != null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(date);
        }
        return null;
    }

    public static String convertDataOraToString(Date date) {
        if (date != null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return formatter.format(date);
        }
        return null;
    }

    public static String encodeStringInURLFormat(String stringa) throws UnsupportedEncodingException {
        return URLEncoder.encode(stringa, "UTF-8");
    }

    public static String escapeNullString(String stringa) {
        if (stringa == null) {
            return "";
        } else {
            return stringa;
        }
    }

    public static String readFileAsString(String filePath) throws java.io.IOException {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    public static Date add(Date data, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(data.getTime()));
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String generateRandomNumber() {
        int maxdigits = 10; // Change to needed # of digits
        StringBuilder result = new StringBuilder();
        Random r = new Random(); // Seed with what you feel is appropriate
        for (int i = 0; i < maxdigits; i++) {
            result.append(r.nextInt(10)); // Append a number from 0 to 9
        }
        String key = result.toString();
        return key;
    }

    /**
     * @param integer
     * @param integer0
     * @return 
     */
    @SuppressWarnings("null")
    public static boolean equals(Integer integer, Integer integer0) {
        if (integer == null && integer0 == null) {
            return true;
        }
        if ((integer != null && integer0 == null)
                || (integer == null && integer0 != null)) {
            return false;
        }
        return integer.equals(integer0);
    }

    /**
     * ^^CS AGGIUNTA
     */
    public static boolean equals(String stringa, String stringa0) {
        if ((stringa == null || stringa.equals(""))
                && (stringa0 == null || stringa0.equals(""))) {
            return true;
        }
        if (((stringa != null && !stringa.equals("")) && (stringa0 == null || stringa0.equals("")))
                || ((stringa == null && stringa.equals("")) && (stringa0 != null || !stringa0.equals("")))) {
            return false;
        }
        return stringa.equals(stringa0);
    }

    public static void storeXmlOnFileSystem(String xml) throws IOException {
        AdminConfiguration config = AdminConfiguration.getInstance();
        String tmpFolder = config.getTempFolder();
        String fileName = tmpFolder + getTimestampString();
        File file = new File(fileName);
        storeXmlOnFileSystem(xml, file);
    }

    public static void storeXmlOnFileSystem(String xml, File file) throws IOException {
        FileUtils.writeStringToFile(file, xml);
    }

    public static void storeXmlOnFileSystem(byte[] xml, File file) throws IOException {
        FileUtils.writeByteArrayToFile(file, xml);
    }

    private static String getTimestampString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String timestampString = format.format(new Date());
        return timestampString;
    }
//    
//    public static String getContentType(byte[] data, String nomeFile) throws Exception {
//        InputStream is = new ByteArrayInputStream(data);
//        ContentHandler contenthandler = new BodyContentHandler();
//        Metadata metadata = new Metadata();
//        metadata.set(Metadata.RESOURCE_NAME_KEY, nomeFile);
//        Parser parser = new AutoDetectParser();
//        parser.parse(is, contenthandler, metadata, null);
//        String contentType = metadata.get(Metadata.CONTENT_TYPE);
//        return contentType;
//    }

//    public static String getContentType(String file, String nomeFile) throws Exception {
//        return getContentType(file.getBytes("UTF-8"), nomeFile);
//    }
    public static File dumpXmlPratica(String xml) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        AdminConfiguration config = AdminConfiguration.getInstance();
        String tmpFolder = config.getTempFolder();
        Date dateNow = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss.SSS");
        String timestamp = dateformat.format(dateNow);
        File dir = new File(tmpFolder);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = tmpFolder + File.separator + timestamp + ".xml";
        Log.APP.info("Salvataggio del file XML con nome " + fileName);
        File file = null;
        PrintWriter pw = null;
        try {
            file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
            pw = new PrintWriter(osw);
            pw.println(xml);
            Log.APP.info("XML salvato correttamente!");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return file;
    }
//
//    public static boolean modificaParametri(String codErrore) {
//        if (codErrore.equals(DizionarioErroriDao.WEB_SERVICE_SPORTELLI_ERRORE_SERVIZIO)) {
//            return true;
//        }
//        return false;
//    }
    /*
     * @author Gabriele
     */

    public static Boolean and(Boolean... values) {
        Boolean result = Boolean.TRUE;
        for (Boolean value : values) {
            //^^CS AGGIUNTA
            if (value == null) {
                value = false;
            }
            result = result && value;
        }
        return result;
    }
    /*
     * @author CS
     */

    public static Boolean or(Boolean... values) {
        Boolean result = Boolean.FALSE;
        for (Boolean value : values) {
            //^^CS AGGIUNTA
            if (value == null) {
                value = false;
            }
            result = result || value;
        }
        return result;
    }

    public static int sub(Date date1, Date date2) {
        long millisecondiFraDueDate = date1.getTime() - date2.getTime();
        int diffGiorni = (int) Math.round(millisecondiFraDueDate / 86400000.0);
        return diffGiorni;
    }

    public static String decodeB64(String data) throws Exception {
        return new String(Base64.decodeBase64(data.getBytes("UTF-8")), "UTF-8");
    }

    public static String encodeB64(String data) throws Exception {
        return new String(Base64.encodeBase64(data.getBytes("UTF-8")), "UTF-8");
    }

    public static Object getField(Object instance, String dbColumnName) throws Exception {
        Class myClass = instance.getClass();
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            JoinColumn annotation = field.getAnnotation(javax.persistence.JoinColumn.class);
            if (annotation != null && annotation.name().equals(dbColumnName)) {
                String fieldName = field.getName();
                fieldName = StringUtils.capitalize(fieldName);
                String methodName = "get" + fieldName;
                Method method = myClass.getMethod(methodName);
                Object obj = method.invoke(instance);
                return obj;
            }
        }
        return null;
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static BigInteger bi(Integer i) {
        if (i == null) {
            return null;
        } else {
            return BigInteger.valueOf(i);
        }
    }

    public static Integer ib(BigInteger i) {
        if (i == null) {
            return null;
        } else {
            return i.intValue();
        }
    }

    public static Character Char(String i) {
        if (i == null) {
            return null;
        } else {
            return i.charAt(0);
        }
    }

    public static String Char(char i) {
        return Character.toString(i);
    }

    public static String Char(Character i) {
        if (i == null) {
            return null;
        }
        return Character.toString(i);
    }

    @SuppressWarnings("UseSpecificCatch")
    public static String convertXmlCharset(String xml, String charsetIn, String charsetOut) throws Exception {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document parse = dBuilder.parse(new ByteArrayInputStream(xml.getBytes(charsetIn)));
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, charsetOut);
            DOMSource source = new DOMSource(parse);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
            return out.toString(charsetOut);
        } catch (Exception e) {
            return xml;
        }
    }

    /**
     *
     * @param i
     * @return 
     */
    public static String flag(Character i) {
        if (i == null) {
            return "N";
        } else {
            if (i.equals('S') || i.equals('N')) {
                return i.toString();
            } else {
                return "N";
            }
        }
    }

    public static Character flag(String i) {
        if (i == null) {
            return 'N';
        } else {

            if (i.equals("S") || i.equals("N")) {
                return i.charAt(0);
            } else {
                return 'N';
            }
        }
    }

    public static Character flag(Boolean i) {
        if (i == null) {
            return 'N';
        } else if (!i) {
            return 'N';
        }
        return 'S';
    }

    public static String flagString(Boolean i) {
        return flag(i).toString();
    }

    public static String flagString(String i) {
        return flag(i).toString();
    }

    public static Boolean flagB(String i) {
        if (i == null || i.equals("")) {
            return false;
        }
        if (i.equals("N")) {
            return false;
        }
        if (i.equals("S")) {
            return true;
        }
        return false;
    }

    public static Boolean flagB(Character i) {
        if (i == null || i.equals("")) {
            return false;
        }
        if (i.equals('N')) {
            return false;
        }
        if (i.equals('S')) {
            return true;
        }
        return false;
    }

    public static String marshall(Object obj) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter st = new StringWriter();
        marshaller.marshal(obj, st);

        return st.toString();
    }

    public static void marshall(Object obj, OutputStream os) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, os);
    }

    public static Document marshallToDocument(Object obj) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();

        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, doc);

        return doc;
    }

    public static String marshall(Object objToMarshall, Class classToInstance) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(classToInstance);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter st = new StringWriter();
        marshaller.marshal(objToMarshall, st);
        return st.toString();
    }

    public static File getTempFile(String extension) throws IOException {
        if (extension == null) {
            extension = ".txt";
        }
        UUID idFile = UUID.randomUUID();
        return File.createTempFile(idFile.toString(), "." + extension);
    }

    public static String documentToString(Document doc) throws TransformerConfigurationException, TransformerException {
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }

    public static Document removeNamespacesFromDocument(Document doc) throws Exception {
        Source source = new DOMSource(doc);
        DOMResult domResult = new DOMResult();

        InputStream template = Utils.class.getResourceAsStream("/remove-namespaces.xsl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(template));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, domResult);
        Document result = (Document) domResult.getNode();

        return result;

    }

    public static String removeNamespacesFromDocument2(Document doc) throws Exception {
        Source source = new DOMSource(doc);
        DOMResult domResult = new DOMResult();

        InputStream template = Utils.class.getResourceAsStream("/remove-namespaces.xsl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(template));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        transformer.transform(source, new StreamResult(writer));
        String xmlOutput = new String(writer.toByteArray());

        return xmlOutput;

    }

    public static Source getSourceFromByteArray(byte[] byteArray) {
        ByteArrayInputStream allegatoSuapInputStream = new ByteArrayInputStream(byteArray);
        StreamSource allegatoSuapStreamSource = new StreamSource(allegatoSuapInputStream);
        return allegatoSuapStreamSource;
    }

    public static DataHandler getDataHandlerFromByteArray(byte[] byteArray, String fileType) {
        ByteArrayDataSource allegatoDataSource = new ByteArrayDataSource(byteArray, fileType);
        DataHandler allegatoDataHandler = new DataHandler(allegatoDataSource);
        return allegatoDataHandler;
    }

    public static String normalizeFileName(String originalString) {
        if (originalString.trim().length() > 0) {
            originalString = originalString.trim().replaceAll("[^a-zA-Z0-9.]+", "_");
        }
        return originalString;
    }

    public static String leadingDigitString(String originalString, Integer leadingDigit, Integer resultStringLenght) {
        String formatted = String.format("%" + leadingDigit + resultStringLenght + "d", Integer.valueOf(originalString));
        return formatted;
    }

    public static String extendString(String originalString, Integer resultLenght, String replacementCharacter) {
        return extendString(originalString, resultLenght, replacementCharacter, Boolean.TRUE);
    }

    public static String truncate(String stringToTruncate, int length) {
        if (stringToTruncate != null && !"".equals(stringToTruncate.trim())) {
            if (stringToTruncate.length() > length) {
                return stringToTruncate.substring(0, stringToTruncate.length() - 1);
            } else {
                return stringToTruncate;
            }
        } else {
            return null;
        }
    }

    public static String extendString(String originalString, Integer resultLenght, String replacementCharacter, Boolean rightAlignament) {
        if (rightAlignament) {
            return StringUtils.leftPad(originalString, resultLenght, replacementCharacter);
        } else {
            return StringUtils.rightPad(originalString, resultLenght, replacementCharacter);
        }
    }

    public static Boolean True(String stringa) {
        if (Utils.e(stringa)) {
            return Boolean.FALSE;
        } else if (stringa.toLowerCase().equals("true")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isInteger(String stringToCheck) {
        try {
            if (stringToCheck != null) {
                Integer.parseInt(stringToCheck);
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String createRedirectUrl(HttpServletRequest request, String redirect, Map<String, String> params) {
        if (redirect.startsWith("/")) {
            redirect = request.getContextPath() + redirect;
        }
        if (redirect.contains("?")) {
            redirect = redirect + "&";
        } else {
            redirect = redirect + "?";
        }

        for (String key : params.keySet()) {
            redirect += key + "=" + params.get(key) + "&";
        }
        if (redirect.endsWith("&") || redirect.endsWith("?")) {
            redirect = redirect.substring(0, redirect.length() - 1);
        }

        return redirect;
    }

    public static Map<String, String> getObjectFieldsMap(Object obj) throws Exception {
        Map<String, String> fieldsMap = new HashMap<String, String>();

        String stringResult;
        for (Method method : obj.getClass().getMethods()) {
            if (method.getName().toUpperCase().startsWith("GET") && !method.getName().equalsIgnoreCase("GETCLASS")) {
                Object result = method.invoke(obj);
                stringResult = (result == null) ? "" : result.toString();
                fieldsMap.put(method.getName(), stringResult);
            }
        }
        return fieldsMap;
    }

    public static List<String> getObjectFieldsValueList(Object obj) throws Exception {
        List<String> fieldValueList = new ArrayList<String>();
        Map<String, String> mapFields = getObjectFieldsMap(obj);
        fieldValueList.addAll(mapFields.values());
        fieldValueList.removeAll(Collections.singleton(null));
        fieldValueList.removeAll(Collections.singleton(""));

        return fieldValueList;
    }

    public static String emptyIfNull(Object str) {
        if (str != null) {
            return str.toString().trim();
        } else {
            return "";
        }
    }
    
    public static File[] getFileList(String identificativoPratica,String attachmentsFolder) throws IOException {
        String dirPath = attachmentsFolder+"/"+identificativoPratica+"/";
        
    	File dir = new File(dirPath);   

        File[] fileList = dir.listFiles();
        return fileList;
    }
    
    public static File[] getFolderFile(String dirPath) throws IOException {
    	File file = new File(dirPath);
    	
    	 File[] files = file.listFiles();
    	 for (File fileXml : files) {
				if (fileXml.isDirectory()) {
					getFolderFile(fileXml.getAbsolutePath());
				} 
			}
        return files;
    }
    
    public static void listf(String directoryName,List<File> files ) {
        File directory = new File(directoryName);
       
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        try {
        if(fList != null)
            for (File file : fList) {      
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory())
					
						{
						    listf(file.getCanonicalPath(),files);

					                }
            }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    
    
    public static File[] getFileXml(File[] directories) throws IOException {
    	File[] xmlFiles = null;
    	File[] fileReturn = null;
    	File folderDest = new File(directories[0].getParent()+"/xml");
    	if(!folderDest.exists()) {
    		folderDest.mkdir();
    	
	    	for(int i = 0;i<directories.length;i++) {
	    		File dir = new File(directories[i].getCanonicalPath());
	    		
	    		xmlFiles = dir.listFiles(new FilenameFilter() {
	
	            @Override
	            public boolean accept(File folder, String name) {
	                return name.endsWith(".XML");
	            }
	        });
	    		for(int j = 0;j<xmlFiles.length;j++) {
	    			if(xmlFiles[j].isFile()) {
	    				File destChild = new File(folderDest, xmlFiles[j].getName());
	    				copyFile(xmlFiles[j], destChild);
	    				
	    			}
	    		}
	    	
	    	
	    	}
    }
    	fileReturn = folderDest.listFiles();
    	
    	return fileReturn;
    	//return xmlFiles;
    }
    
    public static byte[] readByteArrayFromFile(File file) throws IOException {
    	byte[] bytesArray = new byte[(int) file.length()]; 

    	  FileInputStream fis = new FileInputStream(file);
    	  fis.read(bytesArray); //read file into bytes[]
    	  fis.close();
    				
    	  return bytesArray;
    }
    	
    
    
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip folder specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            if (destDir.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        FileInputStream fis = new FileInputStream(zipFilePath);
        ZipInputStream zis = new ZipInputStream(fis);
        
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String filePath = destDirectory + File.separator + zipEntry.getName();
            if (!zipEntry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zis, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
        }      
        zis.close();
    }
    public static File[] getFileNotXml(File[] directories) throws IOException {
    	File[] xmlFiles = null;
    	File[] fileReturn = null;
    	File folderDest = new File(directories[0].getParent()+"/notxml");
    	if(!folderDest.exists()) {
    		folderDest.mkdir();
    	}
    	for(int i = 0;i<directories.length;i++) {
    		File dir = new File(directories[i].getCanonicalPath());
    		
    		xmlFiles = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File folder, String name) {
                return name.endsWith(".XML");
            }
        });
    		for(int j = 0;j<xmlFiles.length;j++) {
    			if(xmlFiles[j].isFile()) {
    				File destChild = new File(folderDest, xmlFiles[j].getName());
    				copyFile(xmlFiles[j], destChild);
    				
    			}
    		}
    	
    	
    	}
    	fileReturn = folderDest.listFiles();
    	return fileReturn;
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
         
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
         
        return destFile;
    }
    
    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        
          
        return convFile;
      }

    private long folderSize(File directory) {
	    long length = 0;
	    if (directory.exists() == true) {
		    for (File file : directory.listFiles()) {
		        if (file.isFile())
		            length += file.length();
		        else
		            length += folderSize(file);
		    }
	    }
	    return length;
	}

     
    public static void copyFile(File source, File dest) throws IOException {
    	
		if (!dest.exists()) {

			dest.createNewFile();

		}

		InputStream in = null;

		OutputStream out = null;

		try {

			in = new FileInputStream(source);

			out = new FileOutputStream(dest);

			byte[] buf = new byte[1024];

			int len;

			while ((len = in.read(buf)) > 0) {

				out.write(buf, 0, len);

			}

		}

		finally {

			in.close();

			out.close();

		}

   	}
    public static boolean isThisDateValid(String dateToValidate, String dateFromat){
		
		if(dateToValidate == null){
			return false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		//sdf.setLenient(false);
		
		try {
			
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
		
		} catch (ParseException e) {
			
			//e.printStackTrace();
			return false;
		}
		
		return true;
	}
    
    public static Date getFormattedDateString(String date) throws Exception {
    	Date dateOutput = null;
    	try {
    		if(isThisDateValid(date, ITALIAN_DATE_FORMAT)) {
    			dateOutput = italianFormatToDate(date);
    		}
    		if(isThisDateValid(date, US_DATE_FORMAT)) {
    			dateOutput = usFormatToDate(date);
    		}
    		
    	}catch(Exception e) {
    		throw e;
    	}
    	return dateOutput;
    }
    
    public static boolean searchXml(File[] xmlFiles,String identificativoFile) {
    	boolean trovato = false;
    	for (File fileXml : xmlFiles) {
			String nomeFileXml = fileXml.getName().substring(0, fileXml.getName().indexOf("." ));
			if(identificativoFile.equals(nomeFileXml)) {
				trovato = true;
				break;
			}
		}
    	return trovato;
    }
    
    public static File getFileXmlTrovato (File[] xmlFiles,String identificativoFile) {
    	File xmlTrovato = null;
    	for (File fileXml : xmlFiles) {
			String nomeFileXml = fileXml.getName().substring(0, fileXml.getName().indexOf("." ));
			if(identificativoFile.equals(nomeFileXml)) {
				xmlTrovato = fileXml;
			}
		}
    	return xmlTrovato;
    }
    
    public  static String decodingAccentati(String oggetto) {
    	//trova nella input string il carattere che può essere accentato e sostituiscilo con quello corretto attraverso una mappa
    	if(oggetto.indexOf("A¿")!=-1) {
    		oggetto =oggetto.replace("A¿", "à");
		}
		if(oggetto.indexOf("A²")!=-1) {
			oggetto = oggetto.replace("A²", "ò");
		}
		if(oggetto.indexOf("à²")!=-1) {
			oggetto =oggetto.replace("à²", "ò");
		}
		if(oggetto.indexOf("Ã")!=-1) {
			oggetto =oggetto.replace("Ã", "à");
		}
		if(oggetto.indexOf("Â¿")!=-1) {
			oggetto =oggetto.replace("Â¿", "à");
		}
		if(oggetto.indexOf("¿")!=-1) {
			oggetto  = oggetto.replace("¿", "");
		}
		if(oggetto.indexOf("²")!=-1) {
			oggetto  = oggetto.replace("²", "");
		}
		if(oggetto.contains("Nicolà")) {
			oggetto = oggetto.replace("à", "ò");
		}
		
        return oggetto;
    }
    
    public static File[] getFileAllegatiStampe(String path) throws IOException {
    	File dir = new File(path);
    	String[] filters = {"PDF", "DOC", "RTF"};

    	File [] files = dir.listFiles(new FileFilter(filters));

    	return files;
    }
    
    public static byte[] fileToByteArray(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	 }
}
	 
	


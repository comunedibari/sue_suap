/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.xml.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author giuseppe
 */
public class DateAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final String ITALIAN_DATE_FORMAT = "dd/MM/yyyy";
    public static final String ITALIAN_DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String US_DATE_FORMAT = "yyyy-MM-dd";
    static SimpleDateFormat italianSdf = new SimpleDateFormat(ITALIAN_DATE_FORMAT);
    static SimpleDateFormat italianSdtf = new SimpleDateFormat(ITALIAN_DATETIME_FORMAT);
    static SimpleDateFormat usSdf = new SimpleDateFormat(US_DATE_FORMAT);
    

    @Override
    public XMLGregorianCalendar unmarshal(String v) throws Exception {
        XMLGregorianCalendar xmlcalendar = null;
//        Date data = (Date) dateFormat.parse(v);
        Date data = getFormattedDateString(v);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        xmlcalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        return xmlcalendar;
    }

    @Override
    public String marshal(XMLGregorianCalendar v) throws Exception {
        if (v != null) {
            Date date = v.toGregorianCalendar().getTime();
            return dateFormat.format(date);
        } else {
            return null;
        }
    }
    
    public Date getFormattedDateString(String date) throws Exception {
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
    
    public static Date italianFormatToDate(String date) throws ParseException {
        return italianSdf.parse(date);
    }
    
    public static Date usFormatToDate(String date) throws ParseException {
        return usSdf.parse(date);
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
}

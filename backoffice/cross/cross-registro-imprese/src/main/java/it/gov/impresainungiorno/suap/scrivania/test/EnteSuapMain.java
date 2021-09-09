package it.gov.impresainungiorno.suap.scrivania.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP.InfoSchema;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP_Service;

public class EnteSuapMain {

	public static void main(String[] args) {
		
		PddServiceSUAP_Service service = null;
		
		try {
			service = new PddServiceSUAP_Service(new URL("https://suapnewcl.impresainungiorno.gov.it/bosuap/pddService"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		PddServiceSUAP port = service.getPddPortSUAP();
		CooperazioneEnteSUAP ces = new CooperazioneEnteSUAP();
		
		try {
			ces.setInfoSchema(setInfoSchema());
			ces.setIntestazione(null);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		ces.getAllegato();
		
		port.inviaEnteSUAP(ces);

	}
	
	public static InfoSchema setInfoSchema() throws DatatypeConfigurationException {
		CooperazioneEnteSUAP.InfoSchema value = new CooperazioneEnteSUAP.InfoSchema();
		value.setVersione("1.1.0");
		XMLGregorianCalendar date1 = null;
		XMLGregorianCalendar data = null;
		data = dateToXmlGregorianCalendar(new Date());
		value.setData(data);
//		value.setData(new Date());
		System.out.println("Date1: "+date1);
		return value;
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

}

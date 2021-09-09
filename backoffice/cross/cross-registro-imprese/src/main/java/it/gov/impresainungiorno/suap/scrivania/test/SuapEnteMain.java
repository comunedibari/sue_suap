package it.gov.impresainungiorno.suap.scrivania.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.InfoSchema;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.FormaGiuridica;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.gov.impresainungiorno.suap.scrivania.PddServiceEnte;
import it.gov.impresainungiorno.suap.scrivania.PddServiceEnte_Service;

public class SuapEnteMain {

	public static void main(String[] args) throws IOException {
		
		PddServiceEnte_Service service = null;
		try {
			service = new PddServiceEnte_Service(new URL("http://localhost:8081/cross-ws/services/PddServiceEnte"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		PddServiceEnte port = service.getPddPortEnte();
		CooperazioneSUAPEnte cse = new CooperazioneSUAPEnte();
		
		try {
			cse.setInfoSchema(setInfoSchema());
			cse.setIntestazione(setIntestazione());
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		List<AllegatoCooperazione> listAllegato = setAllegato();
		for(AllegatoCooperazione allegato :listAllegato) {
			cse.getAllegato().add(allegato);
		}
		
		port.inviaSUAPEnte(cse);
		
		System.out.println("Port: "+ service.getPddPortEnte());

	}
	
	public static InfoSchema setInfoSchema() throws DatatypeConfigurationException {
		CooperazioneSUAPEnte.InfoSchema value = new CooperazioneSUAPEnte.InfoSchema();
		value.setVersione("1.1.0");
		//Date data = new Date();
		XMLGregorianCalendar date1 = null;
		//date1 = dateToXmlGregorianCalendar(data);
		//date1 = XMLGregorianCalendarImpl.createDate(2018, 04, 23, 02);
		//date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-04-23+02:00");
		XMLGregorianCalendar data = null;
		data = dateToXmlGregorianCalendar(new Date());
//		value.setData(new Date());
	//	value.setData(data);
		System.out.println("Date1: "+date1);
		return value;
	}
	
	public static Intestazione setIntestazione() throws DatatypeConfigurationException {
		Intestazione intestaz = new Intestazione();
		
		EstremiEnte em = new EstremiEnte();
		em.setCodiceAmministrazione("");
		em.setCodiceAoo("");
		em.setPec("xx@xxProva.it");
		em.setValue("UFFICI COMUNE DI XX");
		intestaz.getEnteDestinatario().add(em);
		
		EstremiSuap es = new EstremiSuap();
		es.setCodiceAmministrazione("c_h620");
		es.setCodiceAoo("RO-SUPRO");
		es.setCodiceCatastale("");
		es.setIdentificativoSuap(BigInteger.valueOf(3691));
		es.setValue("SUAP del Comune di xxx");
		intestaz.setSuapCompetente(es);
		intestaz.setCodicePratica("SLLMHL72L12E038T-17042020-1134");

		AnagraficaImpresa impresa = new AnagraficaImpresa();
		FormaGiuridica fg = new FormaGiuridica();
		fg.setCategoria("");
		fg.setCodice("AF");
		fg.setValue("ALTRE FORME");
		impresa.setFormaGiuridica(fg);
		impresa.setRagioneSociale("Bianchi Mario");
		impresa.setCodiceFiscale("SLLMHL72L12E038T");
		
		/*
		CodiceREA cr = new CodiceREA();
		cr.setProvincia("RO");
		cr.setValue("ROVIGO");
		impresa.setCodiceREA(cr);
		*/
		IndirizzoConRecapiti indirizzo = new IndirizzoConRecapiti();
		Stato s = new Stato();
		s.setCodice("I");
		s.setValue("Italia");
		indirizzo.setStato(s);
		
		Provincia prov = new Provincia();
		prov.setSigla("RO");
		prov.setValue("ROVIGO");
		indirizzo.setProvincia(prov);
		
		Comune com = new Comune();
		com.setValue("ROVIGO'");
		com.setCodiceCatastale("F842");
		indirizzo.setComune(com);
		indirizzo.setCap("44444");
		indirizzo.setToponimo("via");
		indirizzo.setDenominazioneStradale("SA");
		indirizzo.setNumeroCivico("2");
		impresa.setIndirizzo(indirizzo);
		
		AnagraficaRappresentante lr = new AnagraficaRappresentante();
		lr.setCognome("BIANCHI");
		lr.setNome("MARIO");
		lr.setCodiceFiscale("SLLMHL72L12E038T");
		
		Carica carica = new Carica();
		carica.setCodice("PRP");
		carica.setValue("PROPRIETARIO");
		lr.setCarica(carica);
		impresa.setLegaleRappresentante(lr);
		intestaz.setImpresa(impresa);

		OggettoComunicazione op = new OggettoComunicazione();
		op.setTipoIntervento(TipoIntervento.ALTRO);
		op.setTipoProcedimento("SCIA");
		op.setValue(
				"Richiesta di autorizzazione per ");
		intestaz.setOggettoPratica(op);
		
		ProtocolloSUAP pps = new ProtocolloSUAP();
		pps.setCodiceAmministrazione("ROPRO");
		pps.setCodiceAoo("SUAP");
		pps.setNumeroRegistrazione("4544");
		XMLGregorianCalendar dataRegistrazione = null;
		dataRegistrazione = dateToXmlGregorianCalendar(new Date());
		pps.setDataRegistrazione(new Date());
//		pps.setDataRegistrazione(dataRegistrazione);
		intestaz.setProtocolloPraticaSuap(pps);
		OggettoCooperazione oc = new OggettoCooperazione();
		oc.setTipoCooperazione("IASINOLTRO");
		oc.setValue(
				"Comunicazione SUAP pratica n. SLLMHL72L12E038T-17042020-1133 - SUAP 3173 - SLLMHL72L12E038T Bianchi luigi");
		intestaz.setOggettoComunicazione(oc);
		intestaz.setTestoComunicazione("testo");

		ProtocolloSUAP prot = new ProtocolloSUAP();
		prot.setCodiceAmministrazione("ROPRO");
		prot.setCodiceAoo("SUAP");
		XMLGregorianCalendar dataRegistrazione2  = dateToXmlGregorianCalendar(new Date());
//		prot.setDataRegistrazione(dataRegistrazione2);
		prot.setDataRegistrazione(new Date());
		prot.setNumeroRegistrazione("4445");
		
		intestaz.setProtocollo(prot);	
		return intestaz;
	}
	
	public static List<AllegatoCooperazione> setAllegato() throws IOException {
		List<AllegatoCooperazione> list = new ArrayList<AllegatoCooperazione>();
		String pathName = "C:\\SLLMHL72L12E038T-17042020-1133.SUAP";
		
		File[] arrayFile = getFileAllegati(pathName);
		for(File file : arrayFile) {
			AllegatoCooperazione allegatoCooperazione = new AllegatoCooperazione();
			allegatoCooperazione.setCod("SUDOC");
			
			allegatoCooperazione.setNomeFile(file.getName());
			allegatoCooperazione.setNomeFileOriginale(file.getName());
			allegatoCooperazione.setDescrizione("Descrittore pratica XML");
			
			allegatoCooperazione.setDimensione(BigInteger.valueOf(file.length()));
			allegatoCooperazione.setMime("application/pkcs7");
			allegatoCooperazione.setMimeBase("application/pkcs7");
			
			//allegatoCooperazione.setTipo(tipoAllegato);
			//allegatoCooperazione.setEmbeddedFileRef(null);
			allegatoCooperazione.setEmbeddedFileRef(getDataHandler(pathName+File.separator+file.getName()));
			list.add(allegatoCooperazione);
		}
		return list;
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
	 
	 public static DataHandler getDataHandler(String path) {
		 javax.activation.FileDataSource fileDataSource =new javax.activation.FileDataSource(path);
		 javax.activation.DataHandler dataHandler = new javax.activation.DataHandler(fileDataSource);
		 return dataHandler;
	 }
	 
	 public static File[] getFileAllegati(String path) throws IOException {
	    	File dir = new File(path);

	    	File [] files = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".PDF") || name.endsWith(".p7m");
				}
			});

	    	return files;
	    }

}

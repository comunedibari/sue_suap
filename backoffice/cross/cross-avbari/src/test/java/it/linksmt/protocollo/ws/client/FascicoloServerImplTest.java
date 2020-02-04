package it.linksmt.protocollo.ws.client;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import it.wego.cross.avbari.linksmt.fascicolo.CreazioneFascicolo;
import it.wego.cross.avbari.linksmt.fascicolo.CreazioneFascicoloResponse.Return;
import it.wego.cross.avbari.linksmt.fascicolo.FascicoloServer;
import it.wego.cross.avbari.linksmt.fascicolo.FascicoloServerImplService;
import org.junit.Ignore;

import org.junit.Test;
@Ignore
public class FascicoloServerImplTest {
	
//	private static final int ID_UTENTE = 17489; 
	private static final String WSDL_PATH = "http://web99.linksmt.it/adoc/fascicolo?wsdl";
	private static final String USERNAME="Exprivia2014"; 
	private static final String PASSWORD="exprivIa";
	
	@Test
       @Ignore
	public void testCreazioneFascicolo() throws Exception{
		
		try {
			URL baseUrl = FascicoloServerImplService.class.getResource(".");
			URL url = new URL(baseUrl,WSDL_PATH);
			QName qName = new QName("http://impl.server.ws.protocollo.linksmt.it/", "FascicoloServerImplService");
			
			FascicoloServerImplService fascicoloServerImplService = new FascicoloServerImplService(url,qName);
			FascicoloServer fascicoloServer = fascicoloServerImplService.getFascicoloServerImplPort();
			
			Map<String, Object> req_ctx = ((BindingProvider) fascicoloServer).getRequestContext();
			req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSDL_PATH);
			Map<String, List<String>> headers = new HashMap<String, List<String>>();
			headers.put("Username", Collections.singletonList(USERNAME));
			headers.put("Password", Collections.singletonList(PASSWORD));
			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
			
			CreazioneFascicolo.NuovoFascicoloRequest creazioneRequest = new CreazioneFascicolo.NuovoFascicoloRequest();
			
			creazioneRequest.setAutore("Exprivia2014");
			creazioneRequest.setDescrizione("Fascicolo Prova 1");
			creazioneRequest.setIdNodoPadre("");
			creazioneRequest.setIdNodoTitolario("15");
			creazioneRequest.setIdModelloFascicolo(3047); // id modello fascicolo
			creazioneRequest.setProfilo(17489); // id profilo
			creazioneRequest.setRiservato(false); 
			
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			Date data = new Date();
			gregorianCalendar.setTime(data);
			XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
			
			creazioneRequest.setStartDate(startDate);
			//creazioneRequest.setEndDate(value); // not required
			
			Return response = fascicoloServer.creazioneFascicolo(creazioneRequest);
			
			if (response != null && response.getErrore() != null) {
				fail(response.getErrore().getDescrizione());
				System.out.println(response.getErrore().getDescrizione());
			}
			
		}
		catch (Exception e) {
			fail(e.getMessage());
			System.out.println(e.getMessage());
		}
	}

}

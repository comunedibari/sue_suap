package it.cle.protocollo.documento.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import it.linksmt.protocollo.richiestasoap.Allegato;
import it.linksmt.protocollo.richiestasoap.Destinatario;
import it.linksmt.protocollo.richiestasoap.Mittente;
import it.linksmt.protocollo.ws.server.GetProtocollo.ProtocolloInformazioniRequest;
import it.linksmt.protocollo.ws.server.GetProtocolloResponse.Return;
import it.linksmt.protocollo.ws.server.ProtocolloServer;
import it.linksmt.protocollo.ws.server.impl.ProtocolloServerImplService;

public class TestMain {

	private static final QName SERVICE_NAME = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
	
	public static void main(String[] args) throws IOException {
		  URL wsdlURL = new URL("http://10.10.1.4:8081/adoc/protocollo?wsdl");
		  ProtocolloServerImplService ss = new ProtocolloServerImplService(wsdlURL, SERVICE_NAME);
	        ProtocolloServer port = ss.getProtocolloServerImplPort(); 
	        Map<String, Object> requestContext2 = ((BindingProvider)port).getRequestContext();
	        Map<String,List<String>> headers = new HashMap<String,List<String>>();
	        headers.put("Username", Collections.singletonList("test.protocollo"));
	        headers.put("Password", Collections.singletonList("AvmtB.2018"));
	        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
		ProtocolloInformazioniRequest requestProtocollo = new ProtocolloInformazioniRequest();
		String anno = "2020";
		long nProtocollo = 246054;
		requestProtocollo.setAnno(anno);
		requestProtocollo.setNumeroProtocollo(nProtocollo);
		Return r = port.getProtocollo(requestProtocollo);
		List<Allegato> allegati = r.getAllegati();
		String amministrazione = r.getAmministrazione();
		String areaOrganizzativaOmogenea = r.getAreaOrganizzativaOmogenea();
		XMLGregorianCalendar dataProtocollo = r.getDataProtocollo();
		List<Destinatario> destinatari = r.getDestinatari();
		Mittente mittente = r.getMittente();
		Long numeroProtocollo = r.getNumeroProtocollo();
		String oggetto = r.getOggetto();
		if (r.getErrore() != null)
			throw new Error(r.getErrore().getDescrizione());
		else {
			if (r.getDocumento() != null) {
				byte[] decodedContenuto = Base64.decodeBase64(r.getDocumento().getContenuto());
				FileUtils.writeByteArrayToFile(new File(r.getDocumento().getNomeFile()),decodedContenuto);
			}
		}
		
		System.out.println("oggetto : " + oggetto );
		System.out.println("numeroProtocollo : " + numeroProtocollo );
		System.out.println("dataProtocollo : " + dataProtocollo );
		System.out.println("mittente : " + mittente.getPersonaFisica().getNome() + " " +
				mittente.getPersonaFisica().getCognome() + " " + mittente.getPersonaFisica().getCodiceFiscale());
		System.out.println("amministrazione : " + amministrazione );
		System.out.println("areaOrganizzativaOmogenea : " + areaOrganizzativaOmogenea );
		if (!destinatari.isEmpty())
			System.out.println("Destinatari: ");
		for (Destinatario dest : destinatari) {
			System.out.println("persona fisica:" + dest.getPersonaFisica());
			System.out.println("amministrazione:" + dest.getAmministrazione());
			System.out.println("area organizzativa omogenea:" + dest.getAreaOrganizzativaOmogenea());
		}
		if (r.getDocumento() != null)
			System.out.println("documento: " + r.getDocumento().getNomeFile());
		if(!allegati.isEmpty())
			System.out.println("elenco allegati :");
		for(Allegato allegato : allegati) {
			allegato.getDocumento().getNomeFile();
		}
	}

}

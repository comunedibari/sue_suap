package it.cle.firmaremota.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.IOUtils;

import it.eng.tz.avtmb.wsdl.stampsign.DocumentStampSignAuthPt;
import it.eng.tz.avtmb.wsdl.stampsign.DocumentStampSignPt;
import it.eng.tz.avtmb.wsdl.stampsign.Stampsign;
import it.eng.tz.avtmb.xsd.stampsign.Client;
import it.eng.tz.avtmb.xsd.stampsign.ComposizioneDocumento;
import it.eng.tz.avtmb.xsd.stampsign.DocumentInResponseType;
import it.eng.tz.avtmb.xsd.stampsign.Errore;
import it.eng.tz.avtmb.xsd.stampsign.ExtraMeta;
import it.eng.tz.avtmb.xsd.stampsign.RequestType;
import it.eng.tz.avtmb.xsd.stampsign.Risultato;
import it.eng.tz.avtmb.xsd.stampsign.Sign;
import it.eng.tz.avtmb.xsd.stampsign.SingleStampSizeType;
import it.eng.tz.avtmb.xsd.stampsign.StampSign;
import it.eng.tz.avtmb.xsd.stampsign.StampSignAuthRequest;
import it.eng.tz.avtmb.xsd.stampsign.StampSignAuthResponse;
import it.eng.tz.avtmb.xsd.stampsign.StampSignRequest;
import it.eng.tz.avtmb.xsd.stampsign.StampSignResponse;
import it.eng.tz.avtmb.xsd.stampsign.TipoDocumento;
import it.eng.tz.avtmb.xsd.stampsign.Token;

public class TestMain {
	private static final QName SERVICE_NAME = new QName("http://stampsign.wsdl.avtmb.tz.eng.it", "stampsign");
	public static void main(String[] args) throws Exception {
		  URL url = null;
	        try {
	            url = new URL("file:resources/stampsignwsdl.wsdl");
	        } catch (MalformedURLException e) {
	            java.util.logging.Logger.getLogger(Stampsign.class.getName())
	                .log(java.util.logging.Level.INFO, 
	                     "Can not initialize the default wsdl from {0}", "file:resources/stampsignwsdl.wsdl");
	        }
		 URL wsdlURL = url;
	      
	        Stampsign ss = new Stampsign(wsdlURL, SERVICE_NAME);
     
	        try {
				{

				
				String accessToken = "";
			        
				        //firma e timbro
				        StampSignRequest stampRequest = new StampSignRequest();
				        stampRequest.setRequestType(RequestType.BOTH);
				        stampRequest.setId("");
				        StampSign stampSignRequest = new StampSign();
				        stampSignRequest.setClientId("");
				        stampSignRequest.setAuthority("");
				        stampSignRequest.setIdentificativoDocumento("doc");
				        stampSignRequest.setComposizioneDocumento(ComposizioneDocumento.PAGINA_SINGOLA);
				       XMLGregorianCalendar dataXml =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-04T08:22:55");
				        stampSignRequest.setDataDocumento(dataXml);
				        stampSignRequest.setIriAmministrazione("http://en.unesco.org/");
				        stampSignRequest.setOggettoDocumento("TEST MUNICIPIA");
				        stampSignRequest.setTipoDocumento(TipoDocumento.ALLEGATO);
				        DataHandler dh = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\esempio_PDF.pdf"); 
				        stampSignRequest.setFileDocumento(dh);
				        ExtraMeta em = new ExtraMeta();
				        em.setTitoloAmministrazioneProdotto("Bari");
				        em.setIriAmministrazioneConservato("http://www.bari.it");
				        em.setTitoloAmministrazioneConservato("Comune di Bari");
				        em.setModalitaVerifica("EngTimbro");
				        XMLGregorianCalendar dataFVXml =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2021-02-22T08:22:55");
				        em.setDataFineValidita(dataFVXml);
				        XMLGregorianCalendar dataFVeXml =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2021-02-22T08:22:55");
				        em.setDataFineVerifica(dataFVeXml);
				        stampSignRequest.setExtraMetaDoc(em);
				        stampSignRequest.setDocumentInResponse(DocumentInResponseType.NO);
				        stampSignRequest.setTransactionId("");
				        stampSignRequest.setSigner("");
				        stampSignRequest.setSingleStampSize(SingleStampSizeType.QUARANTA);
				        stampRequest.setStampSign(stampSignRequest);
				        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
				        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
				        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
				        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
				        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://host:8080/ressvr/soap/stampsign");
				        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
				        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
				        Errore errore = stampResponse.getErrore();
						if (errore != null)
							throw new Exception ("Errore " + errore.getCodice() + " " + errore.getMessaggio());
				        Risultato risultato = stampResponse.getRisultato();
				        InputStream is = risultato.getDownloadFileContent().getInputStream();
				        OutputStream os = new FileOutputStream(new File("resources/testft/esempio_PDF.pdf.p7m"));
				        // This will copy the file from the two streams
				        IOUtils.copy(is, os);
				        System.out.println("file salvato nella cartella resources/testft");

				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	 public static DataHandler getDataHandler(String filePath) {
		 javax.activation.FileDataSource fileDataSource =new javax.activation.FileDataSource(filePath);
		 javax.activation.DataHandler dataHandler = new javax.activation.DataHandler(fileDataSource);
		 return dataHandler;
	 }
}

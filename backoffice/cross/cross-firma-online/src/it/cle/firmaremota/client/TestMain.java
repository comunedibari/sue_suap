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
//	        DocumentStampSignAuthPt port = ss.getDocumentStampSignAuthBinding();  
//	        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
//	        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsignauth");
//	        requestContext.put(BindingProvider.USERNAME_PROPERTY, "BARI_TIMBRO");
//	        requestContext.put(BindingProvider.PASSWORD_PROPERTY, "timbroDigitale123$!");
//	        Map<String, List<String>> headers = new HashMap<String, List<String>>();
//	        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	        
	        try {
				{
//				System.out.println("Invoking documentStampSignAuth...");
//				StampSignAuthRequest stampAuthRequest = new StampSignAuthRequest();
//				Client client = new Client();
//				client.setAoo("c_a662");
//				client.setClientId("BARI_TIMBRO");
//				stampAuthRequest.setClient(client);
//				StampSignAuthResponse stampAuthResponse = port.documentStampSignAuth(stampAuthRequest);
//				Token token = stampAuthResponse.getToken();
//				System.out.println(token.getAccessToken());
//				String accessToken = token.getAccessToken();
//				System.out.println(token.getExpiresIn());
//				System.out.println(token.getJti());
//				System.out.println(token.getOrganization());
//				System.out.println(token.getScope());
//				System.out.println(token.getTokenType());
				
				String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGltYnJvX2RpZ2l0YWxlX3Jlc291cmNlX3NlcnZlciJdLCJzY29wZSI6WyJyZWFkIl0sIm9yZ2FuaXphdGlvbiI6IkJBUklfVElNQlJPbkJMdyIsImV4cCI6MTY0MTE3NDMxMSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9URF9DTElFTlQiXSwianRpIjoiNTViNWIwNjMtZDQxZC00YjQ4LTliYmYtMGYxMTc3ZjQyNDk2IiwiY2xpZW50X2lkIjoiQkFSSV9USU1CUk8ifQ.a_4NR_6bef3C7CKPsY19rHMR_7NSfCQrFEQ8gdfjRlY";
				//test 1 cades delega
//	        StampSignRequest stampRequest = new StampSignRequest();
//	        stampRequest.setRequestType(RequestType.SIGN);
//	        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//	        Sign sign = new Sign();
//	        sign.setClientId("BARI_TIMBRO");
//	        sign.setTransactionId("83247823794");
//	        sign.setSigner("a.cantatore");
//	        sign.setTipoFirma("cades");
//	        DataHandler dh = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test2.txt");
//	        sign.setFileDocumento(dh);
//	        stampRequest.setSign(sign);
//	        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
//	        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
//	        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
//	        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//	        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//	        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
//	        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
//	        Risultato risultato = stampResponse.getRisultato();
//	        String fileName = risultato.getDownloadFileName();
//	        InputStream is = risultato.getDownloadFileContent().getInputStream();
//	        OutputStream os = new FileOutputStream(new File("resources/test2.txt.p7m"));
//	        // This will copy the file from the two streams
//	        IOUtils.copy(is, os);

				//test2 cades otp
//				StampSignRequest stampRequest2 = new StampSignRequest();
//				stampRequest2.setRequestType(RequestType.SIGN);
//				stampRequest2.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//				Sign sign2 = new Sign();
//				sign2.setClientId("test_dgs");
//				sign2.setTransactionId("83247823794");
//				sign2.setSigner("dan_esp");
//				sign2.setOtp("0973487814");
//				DataHandler dh2 = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test2.txt");
//				sign2.setFileDocumento(dh2);
//				stampRequest2.setSign(sign2);
//				DocumentStampSignPt portSign2 = ss.getDocumentStampSignBinding();
//				Map<String, Object> requestContext22 = ((BindingProvider) portSign2).getRequestContext();
//				Map<String, List<String>> headersSign2 = new HashMap<String, List<String>>();
//				headersSign2.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//				requestContext22.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//				requestContext22.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign2);
//				StampSignResponse stampResponse2 = portSign2.documentStampSign(stampRequest2);
//				Errore errore = stampResponse2.getErrore();
//				if (errore != null)
//					throw new Exception(errore.getMessaggio());
//					
//				Risultato risultato2 = stampResponse2.getRisultato();
//				InputStream is2 = risultato2.getDownloadFileContent().getInputStream();
//				OutputStream os2 = new FileOutputStream(new File("resources/test2/test2.txt.p7m"));
//				// This will copy the file from the two streams
//				IOUtils.copy(is2, os2);
				
				//test3 pades delega
//				StampSignRequest stampRequest3 = new StampSignRequest();
//				stampRequest3.setRequestType(RequestType.SIGN);
//				stampRequest3.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//				Sign sign3 = new Sign();
//				sign3.setClientId("BARI_TIMBRO");
//				sign3.setTransactionId("83247823794");
//				sign3.setSigner("a.cantatore");
//				sign3.setTipoFirma("pades");
//				DataHandler dh3 = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test3.pdf");
//				sign3.setFileDocumento(dh3);
//				stampRequest3.setSign(sign3);
//				DocumentStampSignPt portSign3 = ss.getDocumentStampSignBinding();
//				Map<String, Object> requestContext3 = ((BindingProvider) portSign3).getRequestContext();
//				Map<String, List<String>> headersSign3 = new HashMap<String, List<String>>();
//				headersSign3.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//				requestContext3.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//				requestContext3.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign3);
//				StampSignResponse stampResponse3 = portSign3.documentStampSign(stampRequest3);
//				Errore error3 = stampResponse3.getErrore();
//				if (error3 != null)
//					throw new Exception(error3.getMessaggio());
//					
//				Risultato risultato3 = stampResponse3.getRisultato();
//				InputStream is3 = risultato3.getDownloadFileContent().getInputStream();
//				OutputStream os3 = new FileOutputStream(new File("resources/test3/test3.pdf"));
//				// This will copy the file from the two streams
//				IOUtils.copy(is3, os3);
//				System.out.println("file salvato nella cartella resources/test3");
				
				//test 4 otp pades
//				StampSignRequest stampRequest4 = new StampSignRequest();
//				stampRequest4.setRequestType(RequestType.SIGN);
//				stampRequest4.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//				Sign sign4 = new Sign();
//				sign4.setClientId("test_dgs");
//				sign4.setTransactionId("83247823794");
//				sign4.setSigner("dan_esp");
//				sign4.setOtp("0973487814");
//				DataHandler dh4 = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test.pdf");
//				sign4.setFileDocumento(dh4);
//				stampRequest4.setSign(sign4);
//				DocumentStampSignPt portSign4 = ss.getDocumentStampSignBinding();
//				Map<String, Object> requestContext4 = ((BindingProvider) portSign4).getRequestContext();
//				Map<String, List<String>> headersSign4 = new HashMap<String, List<String>>();
//				headersSign4.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//				requestContext4.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//				requestContext4.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign4);
//				StampSignResponse stampResponse4 = portSign4.documentStampSign(stampRequest4);
//				Errore errore4 = stampResponse4.getErrore();
//				if (errore4 != null)
//					throw new Exception(errore4.getMessaggio());
//					
//				Risultato risultato4 = stampResponse4.getRisultato();
//				InputStream is4 = risultato4.getDownloadFileContent().getInputStream();
//				OutputStream os4 = new FileOutputStream(new File("resources/test4/test4.pdf"));
//				// This will copy the file from the two streams
//				IOUtils.copy(is4, os4);
				
				
				//test 5
//				 StampSignRequest stampRequest = new StampSignRequest();
//			        stampRequest.setRequestType(RequestType.SIGN);
//			        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//			        Sign sign = new Sign();
//			        sign.setClientId("non censito");
//			        sign.setTransactionId("83247823794");
//			        sign.setSigner("a.cantatore");
//			        sign.setTipoFirma("cades");
//			        DataHandler dh = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test2.txt");
//			        sign.setFileDocumento(dh);
//			        stampRequest.setSign(sign);
//			        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
//			        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
//			        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
//			        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//			        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//			        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
//			        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
//			        Errore error5 = stampResponse.getErrore();
//					if (error5 != null)
//						throw new Exception("Errore " + error5.getCodice() + " " + error5.getMessaggio());
//			        Risultato risultato = stampResponse.getRisultato();
//			        String fileName = risultato.getDownloadFileName();
//			        InputStream is = risultato.getDownloadFileContent().getInputStream();
//			        OutputStream os = new FileOutputStream(new File("resources/test2.txt.p7m"));
//			        // This will copy the file from the two streams
//			        IOUtils.copy(is, os);
			        
			        //test 6
//			        StampSignRequest stampRequest = new StampSignRequest();
//			        stampRequest.setRequestType(RequestType.SIGN);
//			        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//			        Sign sign = new Sign();
//			        sign.setClientId("BARI_TIMBRO");
//			        sign.setTransactionId("83247823794");
//			        sign.setSigner("a.cantatore");
//			        sign.setOtp("111111");
//			        DataHandler dh = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test2.txt");
//			        sign.setFileDocumento(dh);
//			        stampRequest.setSign(sign);
//			        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
//			        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
//			        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
//			        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//			        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//			        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
//			        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
//			        Errore error6 = stampResponse.getErrore();
//					if (error6 != null)
//						throw new Exception("Errore " + error6.getCodice() + " " + error6.getMessaggio());
//			        Risultato risultato = stampResponse.getRisultato();
//			        String fileName = risultato.getDownloadFileName();
//			        InputStream is = risultato.getDownloadFileContent().getInputStream();
//			        OutputStream os = new FileOutputStream(new File("resources/test2.txt.p7m"));
//			        // This will copy the file from the two streams
//			        IOUtils.copy(is, os);
			        
			        //test7
//			        StampSignRequest stampRequest2 = new StampSignRequest();
//					stampRequest2.setRequestType(RequestType.SIGN);
//					stampRequest2.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//					Sign sign2 = new Sign();
//					sign2.setClientId("test_dgs_1");
//					sign2.setTransactionId("83247823794");
//					sign2.setSigner("dan_esp");
//					sign2.setOtp("1111111");
//					DataHandler dh2 = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test2.txt");
//					sign2.setFileDocumento(dh2);
//					stampRequest2.setSign(sign2);
//					DocumentStampSignPt portSign2 = ss.getDocumentStampSignBinding();
//					Map<String, Object> requestContext22 = ((BindingProvider) portSign2).getRequestContext();
//					Map<String, List<String>> headersSign2 = new HashMap<String, List<String>>();
//					headersSign2.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//					requestContext22.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//					requestContext22.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign2);
//					StampSignResponse stampResponse2 = portSign2.documentStampSign(stampRequest2);
//					Errore errore = stampResponse2.getErrore();
//					if (errore != null)
//						throw new Exception ("Errore " + errore.getCodice() + " " + errore.getMessaggio());
//						
//					Risultato risultato2 = stampResponse2.getRisultato();
//					InputStream is2 = risultato2.getDownloadFileContent().getInputStream();
//					OutputStream os2 = new FileOutputStream(new File("resources/test2/test2.txt.p7m"));
//					// This will copy the file from the two streams
//					IOUtils.copy(is2, os2);
					
					//test8
//					 StampSignRequest stampRequest = new StampSignRequest();
//				        stampRequest.setRequestType(RequestType.SIGN);
//				        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
//				        Sign sign = new Sign();
//				        sign.setClientId("BARI_TIMBRO");
//				        sign.setTransactionId("83247823794");
//				        sign.setSigner("nonCensito");
//				        sign.setTipoFirma("pades");
//				        DataHandler dh = getDataHandler("C:\\SUESUAP\\WS_BO\\ClientFirmaRemota\\resources\\test3.pdf");
//				        sign.setFileDocumento(dh);
//				        stampRequest.setSign(sign);
//				        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
//				        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
//				        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
//				        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
//				        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
//				        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
//				        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
//				        Errore errore = stampResponse.getErrore();
//						if (errore != null)
//							throw new Exception ("Errore " + errore.getCodice() + " " + errore.getMessaggio());
//				        Risultato risultato = stampResponse.getRisultato();
//				        String fileName = risultato.getDownloadFileName();
//				        InputStream is = risultato.getDownloadFileContent().getInputStream();
//				        OutputStream os = new FileOutputStream(new File("resources/test3.pdf"));
//				        // This will copy the file from the two streams
//				        IOUtils.copy(is, os);
			        
				        
				        //firma e timbro
				        StampSignRequest stampRequest = new StampSignRequest();
				        stampRequest.setRequestType(RequestType.BOTH);
				        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
				        StampSign stampSignRequest = new StampSign();
				        stampSignRequest.setClientId("BARI_TIMBRO");
				        stampSignRequest.setAuthority("comune.bari");
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
				        stampSignRequest.setTransactionId("37239482462");
				        stampSignRequest.setSigner("a.cantatore");
				        stampSignRequest.setSingleStampSize(SingleStampSizeType.QUARANTA);
				        stampRequest.setStampSign(stampSignRequest);
				        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
				        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
				        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
				        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
				        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsign");
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

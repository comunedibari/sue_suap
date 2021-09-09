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

public class TestAuthMain {
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
	        DocumentStampSignAuthPt port = ss.getDocumentStampSignAuthBinding();  
	        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
	        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.10.1.12:8080/ressvr/soap/stampsignauth");
	        requestContext.put(BindingProvider.USERNAME_PROPERTY, "BARI_TIMBRO");
	        requestContext.put(BindingProvider.PASSWORD_PROPERTY, "timbroDigitale123$!");
	        Map<String, List<String>> headers = new HashMap<String, List<String>>();
	        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	        
	        try {
				{
				System.out.println("Invoking documentStampSignAuth...");
				StampSignAuthRequest stampAuthRequest = new StampSignAuthRequest();
				Client client = new Client();
				client.setAoo("c_a662");
				client.setClientId("BARI_TIMBRO");
				stampAuthRequest.setClient(client);
				StampSignAuthResponse stampAuthResponse = port.documentStampSignAuth(stampAuthRequest);
				Token token = stampAuthResponse.getToken();
				System.out.println(token.getAccessToken());
				String accessToken = token.getAccessToken();
				System.out.println(token.getExpiresIn());
				System.out.println(token.getJti());
				System.out.println(token.getOrganization());
				System.out.println(token.getScope());
				System.out.println(token.getTokenType());
				System.out.println(accessToken);
				
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

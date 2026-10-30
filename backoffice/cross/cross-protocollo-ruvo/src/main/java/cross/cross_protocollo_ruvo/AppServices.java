package cross.cross_protocollo_ruvo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import operazioni.AssegnazioneInterna;
import operazioni.DatiEntrata;
import operazioni.MittenteEsterno;
import operazioni.NewUD;
import operazioni.RegistrazioneDaDare;
import operazioni.UO;
import operazioni.VersioneElettronica;
import utilities.LogClass;
import utilities.myUtility;


/**
 * Hello world!
 *
 */
public class AppServices 
{
	static final String START_TAG = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	static final String START_TAG_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	static final String END_TAG = "]]>";
	private static final QName SERVICE_NAME = new QName("http://addunitadoc.webservices.repository2.auriga.eng.it", "WSAddUdService");

	/*
	 * String operation: "D" = Decode input from Base64,"E" = Encode input to Base64   
	 * String input
	 */
	private String Encode_Decode_Base64(String operation, String inputString, byte[] inputByte) {
		String output = "";

		if(operation.equals("SD")) {
			byte[] decodedBytes = Base64.getDecoder().decode(inputString);
			output = new String(decodedBytes);
			System.out.println(decodedBytes);
		}

		if(operation.equals("BD")) {
			byte[] encodedBytes = Base64.getEncoder().encode(inputByte);
			output = new String(encodedBytes);
			System.out.println(output);
		}		 

		if(operation.equals("SE")) {
			String encodedString = Base64.getEncoder().encodeToString(inputString.getBytes());
			output = encodedString;
		}

		if(operation.equals("SD")) {
			byte[] decodedBytes = Base64.getDecoder().decode(inputString);
			String decodedString = new String(decodedBytes);
			output = decodedString;
		}		
		return output;
	}
	public static byte[] encrypt_SHA1(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}

	static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * Verifies file's SHA1 checksum
	 * @param Filepath and name of a file that is to be verified
	 * @param testChecksum the expected checksum
	 * @return true if the expeceted SHA1 checksum matches the file's SHA1 checksum; false otherwise.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static boolean verifyChecksum(String file, String testChecksum) throws NoSuchAlgorithmException, IOException
	{
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(file);

		byte[] data = new byte[1024];
		int read = 0; 
		while ((read = fis.read(data)) != -1) {
			sha1.update(data, 0, read);
		};
		byte[] hashBytes = sha1.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < hashBytes.length; i++) {
			sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		String fileHash = sb.toString();

		return fileHash.equals(testChecksum);
	}

	private static String jaxbObjectToXML(NewUD protocolloRuvo) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(NewUD.class);
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

			StringWriter sw = new StringWriter();
			m.marshal(protocolloRuvo, sw);
			xmlString = sw.toString();

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	/*
	public void protocolla_cxf(String xmlProtocolloRuvo) {
		myUtility utl = new myUtility();
		String Url = utl.getValueProperties("url");
		URL wsdlURL;
		//URL wsdlURL = WSAddUdService.WSDL_LOCATION;

		try {
			LogClass.trace("PROTOCOLLO RUVO - protocolla START");
			wsdlURL = new URL(Url);

			String codApplicazione = utl.getValueProperties("cod.applicazione");
			String istanzaApplicazione = utl.getValueProperties("istanza.applicazione");
			String username = utl.getValueProperties("username");
			String password = utl.getValueProperties("password");

			WSAddUdService ss = new WSAddUdService(wsdlURL, SERVICE_NAME);
			WSIAddUd port = ss.getWSAddUdPort();  

			{
				System.out.println("Invoking serviceOperation...");
				protocolloRuvo.protocolla.Service _serviceOperation_service = new protocolloRuvo.protocolla.Service();

				_serviceOperation_service.setCodApplicazione(codApplicazione);
				_serviceOperation_service.setIstanzaApplicazione(istanzaApplicazione);
				_serviceOperation_service.setUserName(username);
				_serviceOperation_service.setPassword(password);		

				//String ws_xml = START_TAG + START_TAG_XML + xmlProtocolloRuvo + END_TAG;
				String ws_xml = START_TAG_XML + xmlProtocolloRuvo;
				LogClass.trace("PROTOCOLLO RUVO - protocolla xmlinput:" + ws_xml);

				String ws_xml_decode = START_TAG_XML + xmlProtocolloRuvo;
				LogClass.trace("PROTOCOLLO RUVO - ws_xml_decode:" + ws_xml_decode);

				LogClass.trace("PROTOCOLLO RUVO - ws_xml:" + ws_xml);

				_serviceOperation_service.setXml(ws_xml);
				byte[] ws_SHA1 = encrypt_SHA1(ws_xml_decode);

				LogClass.trace("PROTOCOLLO RUVO - SHA1:" + ws_SHA1);

				String ws_SHA1_BASE64 = Encode_Decode_Base64("BD", null, ws_SHA1);
				LogClass.trace("PROTOCOLLO RUVO - SHA1 BASE64:" + ws_SHA1_BASE64);

				_serviceOperation_service.setHash(ws_SHA1_BASE64);
				protocolloRuvo.protocolla.ServiceResponse _serviceOperation__return = port.serviceOperation(_serviceOperation_service);
				System.out.println("serviceOperation.result=" + _serviceOperation__return);

				LogClass.trace("PROTOCOLLO RUVO - protocolla END");
			}

		} catch (MalformedURLException e) {
			System.out.println("ERRORE MalformedURLException");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("ERRORE NoSuchAlgorithmException");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERRORE Exception");
			e.printStackTrace();
		}
		System.exit(0);

	}
*/
	public void protocolla_ws_ruvo(String xmlProtocolloRuvo) {
		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");
		String Url = utl.getValueProperties("url");
		URL wsdlURL;
		//URL wsdlURL = WSAddUdService.WSDL_LOCATION;

		LogClass.trace("PROTOCOLLO RUVO - protocolla START");

		String codApplicazione = utl.getValueProperties("cod.applicazione");
		String istanzaApplicazione = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");
		
/*************************************************
 * OK!
		try {
			wsdlURL = new URL(Url);

			{
				System.out.println("Invoking serviceOperation...");

				WSAddUdSoapBindingStub protocolla = new WSAddUdSoapBindingStub();				
				
				_serviceOperation_service.setCodApplicazione(codApplicazione);
				_serviceOperation_service.setIstanzaApplicazione(istanzaApplicazione);
				_serviceOperation_service.setUserName(username);
				_serviceOperation_service.setPassword(password);		

				//String ws_xml = START_TAG + START_TAG_XML + xmlProtocolloRuvo + END_TAG;
				String ws_xml = START_TAG_XML + xmlProtocolloRuvo;
				LogClass.trace("PROTOCOLLO RUVO - protocolla xmlinput:" + ws_xml);

				String ws_xml_decode = START_TAG_XML + xmlProtocolloRuvo;
				LogClass.trace("PROTOCOLLO RUVO - ws_xml_decode:" + ws_xml_decode);

				LogClass.trace("PROTOCOLLO RUVO - ws_xml:" + ws_xml);

				_serviceOperation_service.setXml(ws_xml);
				byte[] ws_SHA1 = encrypt_SHA1(ws_xml_decode);

				LogClass.trace("PROTOCOLLO RUVO - SHA1:" + ws_SHA1);

				String ws_SHA1_BASE64 = Encode_Decode_Base64("BD", null, ws_SHA1);
				LogClass.trace("PROTOCOLLO RUVO - SHA1 BASE64:" + ws_SHA1_BASE64);

				_serviceOperation_service.setHash(ws_SHA1_BASE64);
				
				WSAddUdSoapBindingStub.s
				
				protocolloRuvo.protocolla.ServiceResponse _serviceOperation__return = port.serviceOperation(_serviceOperation_service);
				System.out.println("serviceOperation.result=" + _serviceOperation__return);

				LogClass.trace("PROTOCOLLO RUVO - protocolla END");
			}

		} catch (MalformedURLException e) {
			System.out.println("ERRORE MalformedURLException");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("ERRORE NoSuchAlgorithmException");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERRORE Exception");
			e.printStackTrace();
		}
*********************************/			
		System.exit(0);

	}
/*
	public static void main(String args[]) throws Exception {

		AppServices client = new AppServices();	
		NewUD protocolloRuvo = new NewUD();


		RegistrazioneDaDare RegistrazioneDaDareObject = new RegistrazioneDaDare();
		RegistrazioneDaDareObject.setCategoriaReg("PG");
		protocolloRuvo.setRegistrazioneDaDare(RegistrazioneDaDareObject);

		protocolloRuvo.setOggettoUD("TEST PROGETTO SOAPUI PER CMBA");

		VersioneElettronica VersioneElettronicaObject = new VersioneElettronica();
		VersioneElettronicaObject.setNroAttachmentAssociato("1");
		VersioneElettronicaObject.setNomeFile("Test.pdf");
		VersioneElettronicaObject.setFlagVersPubblicata("1");
		protocolloRuvo.setVersioneElettronica(VersioneElettronicaObject);

		protocolloRuvo.setTipoProvenienza("E");

		DatiEntrata DatiEntrataObject = new DatiEntrata();
		DatiEntrataObject.setDataOraArrivo("2023-07-11T09:30:47Z");
		MittenteEsterno MittenteEsternoObject = new MittenteEsterno();
		MittenteEsternoObject.setFlagFisica("0");
		MittenteEsternoObject.setDenominazione_Cognome("XXXYYYYY S.P.A.");
		MittenteEsternoObject.setCodiceFiscale("02345678901");
		DatiEntrataObject.setMittenteEsterno(MittenteEsternoObject);

		protocolloRuvo.setDatiEntrata(DatiEntrataObject);

		AssegnazioneInterna AssegnazioneInternaObject = new AssegnazioneInterna();
		AssegnazioneInternaObject.setFlagPerConoscenza("0");
		UO UOObject = new UO();

		String  xml = "<NewUD>"; 
		xml = xml + "<RegistrazioneDaDare><CategoriaReg>" + RegistrazioneDaDareObject.getCategoriaReg() + "</CategoriaReg></RegistrazioneDaDare>";
		xml = xml + "<OggettoUD>" + protocolloRuvo.getOggettoUD() + "</OggettoUD>";

		xml = xml + "<VersioneElettronica>";
		xml = xml + "<NroAttachmentAssociato>" + VersioneElettronicaObject.getNroAttachmentAssociato() + "</NroAttachmentAssociato>";
		xml = xml + "<NomeFile>" + VersioneElettronicaObject.getNomeFile() + "</NomeFile>";
		xml = xml + "<FlagVersPubblicata>" + VersioneElettronicaObject.getFlagVersPubblicata() + "</FlagVersPubblicata>";
		xml = xml + "</VersioneElettronica>";
		xml = xml + "<TipoProvenienza>" + protocolloRuvo.getTipoProvenienza()+ "</TipoProvenienza>";
		xml = xml + "<DatiEntrata>";
		xml = xml + "<DataOraArrivo>" + DatiEntrataObject.getDataOraArrivo()+ "</DataOraArrivo>";
		xml = xml + "<MittenteEsterno>";
		xml = xml + "<FlagFisica>" + MittenteEsternoObject.getFlagFisica()+ "</FlagFisica>";
		xml = xml + "<Denominazione_Cognome>" + MittenteEsternoObject.getDenominazione_Cognome()+ "</Denominazione_Cognome>";
		xml = xml + "<CodiceFiscale>" + MittenteEsternoObject.getCodiceFiscale()+ "</CodiceFiscale>";
		xml = xml + "</MittenteEsterno>";
		xml = xml + "</DatiEntrata>";
		xml = xml + "<AssegnazioneInterna>";
		xml = xml + "<UO>";
		xml = xml + "<LivelloUO Nro=\"1\" Codice=\"4\"/>";
		xml = xml + "<LivelloUO Nro=\"2\" Codice=\"2\"/>";
		xml = xml + "</UO>";
		xml = xml + "<FlagPerConoscenza>" + AssegnazioneInternaObject.getFlagPerConoscenza()+ "</FlagPerConoscenza>";
		xml = xml + "</AssegnazioneInterna>";
		xml = xml + "</NewUD>"; 

		client.protocolla_ws_ruvo(xml);
	}
*/	
}

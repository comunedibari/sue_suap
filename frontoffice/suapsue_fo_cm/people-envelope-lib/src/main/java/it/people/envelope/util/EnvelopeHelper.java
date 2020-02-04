/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/
package it.people.envelope.util;

import it.people.PeopleConstants;
import it.people.core.PplUserData;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.RequestEnvelope;
import it.people.envelope.beans.Allegato;
import it.people.envelope.beans.CredenzialiUtenteCertificate;
import it.people.envelope.beans.EstremiOperatore;
import it.people.envelope.beans.EstremiPersonaFisica;
import it.people.envelope.beans.EstremiPersonaGiuridica;
import it.people.envelope.beans.EstremiRichiedente;
import it.people.envelope.beans.EstremiTitolare;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.Recapito;
import it.people.process.AbstractPplProcess;
import it.people.process.data.AbstractData;
import it.people.sirac.accr.beans.AbstractProfile;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.util.IdentificatoreUnivoco;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlTokenSource;

/**
 * Clesse contenente metodi statici di utilit� utilizzati nel processo di conversione da tracciato xml a Envelope bean e viceversa.
 *
 */
public class EnvelopeHelper {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(EnvelopeHelper.class);
	
	/**
   * Converte in Calendar l'oggetto Date passato come parametro
	 * @param date
	 * @return Calendar corrispondente all'oggetto Date passato come parametro
	 */
	public static Calendar DateToCalendar(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc;
	}
	
  /**
   * Converte in Date l'oggetto Calendar passato come parametro
   * @param calendar
   * @return Date corrispondente all'oggetto Calendar passato come parametro
   */
	public static Date CalendarToDate(Calendar calendar) {
		return calendar.getTime();
	}
  
  /**
   * Valida l'oggetto XmlBeans passato come parametro utilizzando le XmlOptions specificate.<br> 
   * Visualizza eventuali errori di validazione se il log � impostato su debug. 
   * @param xmlObj
   * @param opt
   * @return true se la validazione ha successo
   */
  public static boolean validateXmlObj(XmlObject xmlObj, XmlOptions opt) {
    ArrayList errorList = new ArrayList();
    opt.setErrorListener(errorList);
    
      boolean isValid = validateAndShowXMLHelper(xmlObj, opt);
      
      // If the XML isn't valid, loop through the listener's contents,
      // printing contained messages.
      if (!isValid) showValidationErrorList(errorList);
      
      return isValid;
  }

  
  /**
   * Effettua il parsing con XmlBeans della stringa passata come parametro.<br>
   * Valida l'oggetto XmlBeans ottenuto utilizzando le XmlOptions specificate.<br> 
   * Visualizza eventuali errori di validazione se il log � impostato su debug. 
   * @param xmlObjString
   * @param opt
   * @return true se la validazione ha successo
   */
  public static boolean parseXMLAndValidate(String xmlObjString, XmlOptions opt) {
    ArrayList errorList = new ArrayList();
    opt.setErrorListener(errorList);

      boolean isValid = parseXMLAndValidateHelper(xmlObjString, opt);
      
      // If the XML isn't valid, loop through the listener's contents,
      // printing contained messages.
      if (!isValid) showValidationErrorList(errorList);
      
      return isValid;
  }
  
  protected static boolean validateAndShowXMLHelper(XmlObject xmlObj, XmlOptions opt) {
    if (logger.isDebugEnabled()) {
      logger.debug("Validating XML Object...");
    }
      String xmlObjString = xmlObj.xmlText(opt);
      
      boolean isValid = xmlObj.validate(opt);
      
      SchemaType st = xmlObj.schemaType();
      

    if (logger.isDebugEnabled()) {
      logger.debug("Schema Type is: " + st.toString());
      logger.debug("Full Java Name is: "
          + st.getFullJavaName());
      //logger.debug("Short Java Name is: " + st.getShortJavaName());
      logger.debug("DocumentElement QName is: "
          + st.getDocumentElementName());
      logger.debug("Document :\n\n" + xmlObjString);
      logger.debug("Document is Valid = " + isValid);
    }
     
      return isValid;
  }
  
  protected static boolean parseXMLAndValidateHelper(String xmlObjString, XmlOptions opt) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Parsing XML text...");
    }  
    
      XmlObject xmlObj= null;
    try {
      xmlObj = XmlObject.Factory.parse(xmlObjString, opt);
    } catch (XmlException e) {
      logger.error("parseXMLandValidate threw an Exception:", e);
      return false;
    }
      
      SchemaType st = xmlObj.schemaType();
      boolean isValid = xmlObj.validate(opt);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Schema Type is: " + st.toString());
        logger.debug("Full Java Name is: " + st.getFullJavaName());
        //logger.debug("Short Java Name is: " + st.getShortJavaName());
        logger.debug("DocumentElement QName is: " + st.getDocumentElementName());
        logger.debug("Document :\n\n" + xmlObjString);
        logger.debug("Document is Valid = " + isValid);
      }  
      
      return isValid;
  }
  
  /**
   * @return Default XMLOptions (setSavePrettyPrint + setSaveAggressiveNamespaces)
   */
  public static XmlOptions getDefaultXmlOptions() {
    XmlOptions opt = new XmlOptions();
    opt.setSavePrettyPrint();
    opt.setSaveAggresiveNamespaces();

    return opt;
  }
  
   /**
   * Gets the formatted character-encoded string representation of an
   * XmlTokenSource using the specified XmlOptions.
   * 
   * @param xmlTokenSource - typically an XmlBean object
   * @param encoding - the desired character encoding
   * @param opt - XmlOptions to be used during parsing
   * @return String ready for transmission
   * @throws Exception
   */
  public static String getEncodedXmlText(XmlTokenSource xmlTokenSource,
      String encoding, XmlOptions opt) throws Exception {
    // Setup various properties of the XML instance document
     
    //xmlTokenSource.documentProperties().setVersion("1.0");
    
    XmlOptions xmlOptions;
    if (opt==null) xmlOptions = new XmlOptions();
    else xmlOptions = opt;
    
    //xmlOptions.setUseDefaultNamespace();
    //xmlOptions.setSavePrettyPrint();
    //xmlOptions.setSaveAggresiveNamespaces();

    if (encoding != null) {
      xmlTokenSource.documentProperties().setEncoding(encoding);
      xmlOptions.setCharacterEncoding(encoding);
    }
    // Format to a buffer and read it back into a string
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    xmlTokenSource.save(bos, xmlOptions);
    
    String result = null;
    if (encoding==null) result = new String(bos.toByteArray());
    else result = bos.toString(encoding);
    //return bos.toString(encoding);
    return result;
  }
  
  /**
   * Gets the formatted character-encoded string representation of an
   * XmlTokenSource.
   * 
   * @param xmlTokenSource - typically an XmlBean object
   * @param encoding - the desired character encoding
   * @return String ready for transmission
   * @throws Exception
   */
  public static String getEncodedXmlText(XmlTokenSource xmlTokenSource,
      String encoding) throws Exception {
    return getEncodedXmlText(xmlTokenSource, encoding, null);
  }
  
  /**
   * Inserisce il testo codificato in Base64 passato come parametro nell'elemento rappresentato 
   * dall'oggetto XmlBeans specificato
   * @param xobj - XmlBeans object
   * @param bytesB64 - bytearray contenente il testo in base64 da inserire
   * @throws Exception - Se il testo passato non � in base64 
   * oppure se si verifica un'eccezione durante l'inserimento nell'oggetto XmlBeans.
   */
  public static void insertTextB64(XmlObject xobj, byte[] bytesB64) throws Exception {
    if (!Base64.isArrayByteBase64(bytesB64))
      throw new Exception("EnvelopeHelper::insertTextB64:: argument is not Base 64 Encoded.");
    XmlCursor xcursor = xobj.newCursor();           
    xcursor.toFirstContentToken();           
    xcursor.insertChars(new String(bytesB64));
    
    xcursor.dispose();

  }

  /**
   * Estrae dall'elemento rappresentato dall'oggetto XmlBeans specificato il contenuto text, 
   * che deve essere codificato in base64.
   * @param xobj - XmlBeans object
   * @return bytearray contenente il testo in Base64 estratto dall'elemento 
   * @throws Exception - Se il testo estratto non � in base64 
   * oppure se si verifica un'eccezione durante le operazioni sull'oggetto XmlBeans.
   */
  public static byte[] extractTextB64(XmlObject xobj) throws Exception {
    XmlCursor xcursor = xobj.newCursor();           
    xcursor.toFirstContentToken();           
    byte[] contentBytesB64 = xcursor.getChars().getBytes();

    if (!Base64.isArrayByteBase64(contentBytesB64))
      throw new Exception("EnvelopeHelper::insertTextB64:: argument is not Base 64 Encoded.");

    xcursor.dispose();
    return contentBytesB64;
    

  }
  
 
  public static IRequestEnvelope createEnvelopeFromPplProcessAndSession(AbstractPplProcess process, HttpSession session) throws Exception {
  	  	
   	// 	creo una nuova busta
  	IRequestEnvelope busta = new RequestEnvelope();
  	
  	AbstractData processData = (AbstractData)process.getData();
  	IdentificatoreUnivoco idUnivoco = processData.getIdentificatoreUnivoco();
  	
  	// converto gli oggetti people in oggetti envelope
  	it.people.envelope.beans.IdentificatoreUnivoco identificatoreUnivoco = 
  		   new it.people.envelope.beans.IdentificatoreUnivoco(idUnivoco.getCodiceSistema().getCodiceIdentificativoOperazione(), 
  		  		                                                idUnivoco.getCodiceProgetto(), 
  		  		                                                idUnivoco.getCodiceSistema().getCodiceAmministrazione(),
  		  		                                                idUnivoco.getCodiceSistema().getNomeServer(), 
  		  		                                                it.people.sirac.util.Utilities.parseDateString(idUnivoco.getDataDiRegistrazione()));
  		  		                                                
  	
  	IdentificatoreDiRichiesta idRichiesta = new IdentificatoreDiRichiesta(identificatoreUnivoco);
  	
  	ProfiliDelegaHelper profiliDelegaHelper = new ProfiliDelegaHelper(session);
  	ProfiloPersonaFisica profiloRichiedente = profiliDelegaHelper.getProfiloRichiedente();
  	
  	ProfiloPersonaFisica profiloOperatore = profiliDelegaHelper.getProfiloOperatore();
  	EstremiOperatore estremiOperatore = new EstremiOperatore(profiloOperatore.getNome(), profiloOperatore.getCognome(), profiloOperatore.getCodiceFiscale(), 
  			profiloOperatore.getDomicilioElettronico());
  	
//  	ProfiloPersonaFisica profiloRichiedente = (ProfiloPersonaFisica)session.getAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE);
  	EstremiRichiedente estremiRichiedente = new EstremiRichiedente(profiloRichiedente.getNome(), profiloRichiedente.getCognome(), profiloRichiedente.getCodiceFiscale(), 
  			profiloRichiedente.getDomicilioElettronico());
  	
    EstremiTitolare estremiTitolare = null;
//  	AbstractProfile profiloTitolare = (AbstractProfile)session.getAttribute(SiracConstants.SIRAC_ACCR_TITOLARE);
    AbstractProfile profiloTitolare = profiliDelegaHelper.getProfiloTitolare();
    estremiTitolare = getEstremiTitolareFromAbstractProfile(profiloTitolare);
    
//  	ProfiloPersonaFisica profiloTitolarePF;
//  	ProfiloPersonaGiuridica profiloTitolarePG;
//  	if (profiloTitolare.isPersonaFisica()) {
//  			profiloTitolarePF = (ProfiloPersonaFisica) profiloTitolare;
//  			estremiTitolare = new EstremiTitolare(new EstremiPersonaFisica(profiloTitolarePF.getNome(), profiloTitolarePF.getCognome(), profiloTitolarePF.getCodiceFiscale()));
//  	} else if (profiloTitolare.isPersonaGiuridica()) {
//  			profiloTitolarePG = (ProfiloPersonaGiuridica) profiloTitolare;
//        estremiTitolare = new EstremiTitolare(new EstremiPersonaGiuridica(profiloTitolarePG.getDenominazione(), profiloTitolarePG.getCodiceFiscale()));    
//    } 
  	
  	EstremiTitolare estremiSoggettoDelegato = null;
//	String tipoQualificaSess = (String)session.getAttribute(SiracConstants.SIRAC_ACCR_TIPOQUALIFICA);
//	String descrQualificaSess = (String)session.getAttribute(SiracConstants.SIRAC_ACCR_DESCRQUALIFICA);
//	Accreditamento accrSel = (Accreditamento)session.getAttribute(SiracConstants.SIRAC_ACCR_ACCRSEL);
	
	// Popola gli estremi del soggetto delegato
	
	// Se il codice fiscale intermediario associato all'accreditamento corrente 
	// coincide con il codice fiscale dell'operatore/richiedente allora il soggetto delegato
	// � una persona fisica
//	if ((tipoQualificaSess != null) && tipoQualificaSess.equalsIgnoreCase("Intermediario")) {
//		// Nel caso di intermediario il delegante � la persona giuridica rappresentata (quindi ad esempio il CAF)
//		String ragioneSocialeIntermediario = accrSel.getProfilo().getDenominazione();
//		String cfIntermediario = accrSel.getProfilo().getCodiceFiscaleIntermediario();
//		EstremiPersonaGiuridica estremiIntermediarioPG = 
//			new EstremiPersonaGiuridica(ragioneSocialeIntermediario, cfIntermediario);
//		
//		estremiSoggettoDelegato = new EstremiTitolare(estremiIntermediarioPG);
//	} else {
//		// In tutti gli altri casi il soggetto delegante � il richiedente
//		estremiSoggettoDelegato = 
//			new EstremiTitolare(estremiRichiedente);
//	}

  	AbstractProfile profiloSoggettoDelegato = profiliDelegaHelper.getProfiloDelegato();
  	estremiSoggettoDelegato = getEstremiTitolareFromAbstractProfile(profiloSoggettoDelegato);
  	
  	
  	busta.setCodiceDestinatario((String)session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE_ID));
    busta.setIdentificatoreDiRichiesta(idRichiesta);
    busta.setEstremiRichiedente(estremiRichiedente);
    busta.setEstremiTitolare(estremiTitolare);
    busta.setEstremiOperatore(estremiOperatore);
    busta.setContestoServizio(process.getProcessName());
    
    // Inserisce nella busta gli estremi del soggetto delegato
    busta.setEstremiSoggettoDelegato(estremiSoggettoDelegato);
    
    busta.setForceSkipCheckDelega(profiliDelegaHelper.getForceSkipCheckDelega());
    
    
//    busta.setNomeServizio()
//    busta.setContenuto()
    
    byte[] credenzialiBase64 = SiracHelper.getCredenzialiCertificateUtenteAutenticatoBase64(session).getBytes();
    
    Allegato credenzialiFirmateAllegato = new Allegato(
        "Credenziali.xml", 
        "Credenziali firmate del richiedente", 
        Base64.decodeBase64(credenzialiBase64),
        null, Allegato.TIPO_ALLEGATO_FIRMATO);
    
    CredenzialiUtenteCertificate credenzialiCertificate = new CredenzialiUtenteCertificate(estremiRichiedente.getCodiceFiscale(), credenzialiFirmateAllegato);
    busta.setMittente(credenzialiCertificate);
    PplUserData userData = (PplUserData)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
    Recapito recapitoEmail = new Recapito(userData.getNome()+" "+userData.getCognome(),Recapito.PRIORITA_RECAPITO_PRINCIPALE);
    recapitoEmail.setIndirizzoEmail(userData.getEmailaddress());
    busta.addRecapito(recapitoEmail);
    
  	return busta;
  	
  }
  
    // --------------------------------------------------------------------------------------
     // Metodi di utilit�
     //
      
      
      protected static void showValidationErrorList(ArrayList errorList) {
        StringBuffer errorMessages = new StringBuffer();
        
        for (int i = 0; i < errorList.size(); i++)
        {
            XmlError error = (XmlError)errorList.get(i);
            
            errorMessages.append("\n");
            errorMessages.append("Message: " + error.getMessage() + "\n");
            errorMessages.append("Location of invalid XML: " + 
                  error.getCursorLocation().xmlText() + "\n");
            
        }
      
        if (logger.isDebugEnabled()) {
      logger
          .debug("Errori Riscontrati durante la validazione:\n"
              + errorMessages.toString());
        }
        
      }
      
      public static String getClassDir(String className) {
        if (!className.startsWith("/")) {
          className = "/" + className;
        }
        className = className.replace('.', '/');
        className = className + ".class";
        
        java.net.URL classUrl =
          //this.getClass().getResource(className);
          EnvelopeHelper.class.getResource(className);
        if (classUrl != null) {
          System.out.println("\nClass '" + className +
              "' found in \n'" + classUrl.getFile() + "'");
        } else {
          System.out.println("\nClass '" + className +
              "' not found in \n'" +
              System.getProperty("java.class.path") + "'");
        }
        
        String path = classUrl.getPath();
        return path.substring(1);
      }
    
    public String getTempDirPath(String className, String relativePath) {
      return new java.io.File(new java.io.File(getClassDir(this.getClass().getName())).getParent()+"/" + relativePath).getPath();
    }
    
    /** 
     * Given a file name, attempt to find the file name in the class
     * path.  If the file name is found, return the absolute path on
     * the local file system.  If the file is not found in the class
     * path, return null.
     * 
     * @param filename the file name for the resource
     */
     public static String getPathForResource( Object obj, String filename ) {
       ClassLoader loader = obj.getClass().getClassLoader();
       URL url = loader.getResource( filename );
       String path = null;
       if (url != null) {
           path = url.getFile();
       }
       return path;
     } // getPathForResource
     
     public static String getPathForResource( String filename ) {
       return getPathForResource(new EnvelopeHelper(), filename);
     }


    
    /**
     * Legge dal filesystem il file con filename specificato e restituisce il contenuto in un byte array
     * @param filename
     * @return byte array con il contenuto del file specificato
     * @throws Exception
     */
    public static byte[] readRawBytesFromFilename(String filename) throws Exception {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      InputStream is = new FileInputStream(filename);
      byte[] buf = new byte[16384];
      int len= 0;
      while ((len = is.read(buf)) > 0) {
        out.write(buf,0,len);
      }
      return out.toByteArray();
    }
    
    /**
     * Legge il contenuto della risorsa specificata (raggiungibile utilizzando il classloader dell'oggetto passato come parametro).<br>
     * Restituisce il contenuto in un byte array.
     * @param rootObj - root object utilizzato per ottenere il classloader di riferimento.
     * @param relativeResourcePath - Path relativo nel classpath per la risorsa
     * @return byte array con il contenuto della risorsa specificata
     * @throws Exception
     */
    public static byte[] readRawBytesFromResource(Object rootObj, String relativeResourcePath) throws Exception {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      //InputStream is = this.getClass().getResourceAsStream(relativeResourcePath);
      InputStream is = rootObj.getClass().getResourceAsStream(relativeResourcePath);
      byte[] buf = new byte[16384];
      int len= 0;
      while ((len = is.read(buf)) > 0) {
        out.write(buf,0,len);
      }
      return out.toByteArray();
    }
    
    protected static EstremiTitolare getEstremiTitolareFromAbstractProfile(AbstractProfile aProfile) throws Exception {
    	EstremiTitolare estremiTitolare = null;
    	if (aProfile.isPersonaFisica()) {
			ProfiloPersonaFisica profiloPF = (ProfiloPersonaFisica) aProfile;
			estremiTitolare = new EstremiTitolare(new EstremiPersonaFisica(
					profiloPF.getNome(),
					profiloPF.getCognome(), 
					profiloPF.getCodiceFiscale(), 
					profiloPF.getDomicilioElettronico()));
		} else if (aProfile.isPersonaGiuridica()) {
			ProfiloPersonaGiuridica profiloPG = (ProfiloPersonaGiuridica) aProfile;
			
			if (profiloPG.getRappresentanteLegale() != null) {
				EstremiPersonaFisica rl = new EstremiPersonaFisica(profiloPG.getRappresentanteLegale().getNome(), 
						profiloPG.getRappresentanteLegale().getCognome(), profiloPG.getRappresentanteLegale().getCodiceFiscale(), 
						profiloPG.getRappresentanteLegale().getDomicilioElettronico());
				estremiTitolare = new EstremiTitolare(new EstremiPersonaGiuridica(
						profiloPG.getDenominazione(), 
						profiloPG.getCodiceFiscale(), 
						profiloPG.getDomicilioElettronico(), rl));
			} else {
				estremiTitolare = new EstremiTitolare(new EstremiPersonaGiuridica(
						profiloPG.getDenominazione(), 
						profiloPG.getCodiceFiscale(), 
						profiloPG.getDomicilioElettronico()));
			}
			
		} 
    	return estremiTitolare;
    }
}

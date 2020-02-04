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

  Licenza:      Licenza Progetto PEOPLE
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
package it.people.envelope.test;

import it.people.envelope.EnvelopeFactory_modellazioneb116_envelopeb002;
import it.people.envelope.IEnvelope;
import it.people.envelope.IEnvelopeFactory;
import it.people.envelope.IRequestEnvelope;
import it.people.envelope.IResponseEnvelope;
import it.people.envelope.RequestEnvelope;
import it.people.envelope.ResponseEnvelope;
import it.people.envelope.beans.Allegato;
import it.people.envelope.beans.ContenutoBusta;
import it.people.envelope.beans.CredenzialiUtenteCertificate;
import it.people.envelope.beans.Eccezione;
//import it.people.envelope.beans.EstremiPersonaFisica;
import it.people.envelope.beans.EstremiOperatore;
import it.people.envelope.beans.EstremiPersonaGiuridica;
import it.people.envelope.beans.EstremiRichiedente;
import it.people.envelope.beans.EstremiTitolare;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.IdentificatoreUnivoco;
import it.people.envelope.beans.Nota;
import it.people.envelope.beans.Recapito;
import it.people.envelope.util.EnvelopeHelper;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * TestCase JUnit per la libreria di gestione delle buste di richiesta e di risposta
 *
 */
public class EnvelopeTest extends TestCase {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(EnvelopeTest.class);
  
//  public static final String TESTFILE_DIR_PROPERTY = "it.people.envelope.test.testfiledirectory";
//  public static final String ENVELOPE_PROPERTIES_FILE = "envelope.properties";
//  
//  protected String testFileDir = null;
  
  protected String nomeServizio = null;
  protected String contestoServizio = null;
  protected String xmlContentFileName = null;
  
  protected final static boolean TEST_PASSED = true;
  protected final static boolean TEST_FAILED = false;

//---------------------------------------------------------------------------------------------
//  Testcase constructor
  public EnvelopeTest(String testMethodName, String nomeServizio) {
    super(testMethodName);
    this.nomeServizio = nomeServizio;
  }

  public EnvelopeTest(String testMethodName, String nomeServizio, String contestoServizio, String xmlContentFileName) {
    super(testMethodName);
    this.nomeServizio = nomeServizio;
    this.contestoServizio = contestoServizio;
    this.xmlContentFileName = xmlContentFileName;
  }

//---------------------------------------------------------------------------------------------
//  Testcase setup
  
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void runBare() throws Throwable {
    try {
      if (logger.isDebugEnabled()) {
        logger.debug(getTestHeader());
      }
      super.runBare();
      
      if (logger.isDebugEnabled()) {
        logger.debug(getTestFooter(TEST_PASSED));
      }
    } catch (Throwable e) {
      //e.printStackTrace();
      if (logger.isDebugEnabled()) {
        logger.debug(getTestFooter(TEST_FAILED));
        throw e;
      }
      //fail(e.getMessage());
      
    }
  }
  
//---------------------------------------------------------------------------------------------
// Test Methods
  
  
  public void testCreateEnvelopeBeanFromXML() throws Exception {
      byte[] xmlContent = EnvelopeHelper.readRawBytesFromFilename(this.xmlContentFileName);
      IEnvelope envelopeBean =       
        createEnvelopeBeanFromXmlTextHelper(new String(xmlContent));
      if (logger.isDebugEnabled()) {
        logger.debug("Envelope bean created:\n" + envelopeBean + "\n");
      }
  }

  public void testCreateEnvelopeBeanFromInvalidXml() throws Exception {
     try {
       testCreateEnvelopeBeanFromXML();
      fail("An exception was expected.");
      
     } catch (Exception e) {
       logger.debug(e);
     }
   }
  
  public void testCreateRichiestaInvioXml() throws Exception {
    
        IEnvelope requestEnvelopeBean = 
          createTestRequestEnvelopeBeanHelper(
              this.nomeServizio,
              this.contestoServizio,
              this.xmlContentFileName);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Request Envelope bean created:\n" + requestEnvelopeBean + "\n");
          logger.debug("Creating Envelope XML....");
        }
        String requestEnvelopeXmlText = 
          createEnvelopeXmlFromEnvelopeBeanHelper((IRequestEnvelope)requestEnvelopeBean);
        
        junit.framework.Assert.assertTrue(EnvelopeHelper.parseXMLAndValidate(requestEnvelopeXmlText, EnvelopeHelper.getDefaultXmlOptions()));
        if (logger.isDebugEnabled()) {
          logger.debug("Envelope XML created an validated.");
        }
        
        if (logger.isDebugEnabled()) {
          logger.debug("Parsing created xml to reconstruct original Envelope Bean......");
        }

        IRequestEnvelope requestEnvelopeBean2 = 
          (IRequestEnvelope)createEnvelopeBeanFromXmlTextHelper(requestEnvelopeXmlText);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Checking reconstructed bean wrt original bean......");
        }
        assertEquals(requestEnvelopeBean, requestEnvelopeBean2);
        
  }

  public void testCreateRispostaInvioXml_Ricevuta() throws Exception {
      IdentificatoreDiRichiesta idRichiesta = 
        new IdentificatoreDiRichiesta(
            new IdentificatoreUnivoco(
                "PNBWER56A45F205N-dsarty-1234567", "PRJ001", "feF205", "SERVER001", new Date()));
      
      byte[] contenutoBytes = EnvelopeHelper.readRawBytesFromFilename(this.xmlContentFileName);
      
      ContenutoBusta contenuto = new ContenutoBusta(contenutoBytes);
      
      IResponseEnvelope responseBean = 
        new ResponseEnvelope(contenuto, this.nomeServizio, idRichiesta);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Response Envelope bean created:\n" + responseBean + "\n");
        logger.debug("Creating Envelope XML....");
      }
      
      IEnvelopeFactory envelopeFactory = 
        new EnvelopeFactory_modellazioneb116_envelopeb002();
      
      
      String xmlText = envelopeFactory.createEnvelopeXmlText(responseBean);
      
      junit.framework.Assert.assertTrue(EnvelopeHelper.parseXMLAndValidate(xmlText, EnvelopeHelper.getDefaultXmlOptions()));
      if (logger.isDebugEnabled()) {
        logger.debug("Envelope XML created an validated.");
      }
      
      if (logger.isDebugEnabled()) {
        logger.debug("Parsing created xml to reconstruct original Envelope Bean......");
      }

      IResponseEnvelope responseEnvelopeBean2 = 
        (IResponseEnvelope)createEnvelopeBeanFromXmlTextHelper(xmlText);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Checking reconstructed bean wrt original bean......");
      }
      assertEquals(responseBean, responseEnvelopeBean2);
      
  }
  
  public void testCreateRispostaInvioXml_Eccezione() throws Exception {
      // Crea un bean di risposta contenente una eccezione
      IdentificatoreDiRichiesta idRichiesta = 
        new IdentificatoreDiRichiesta(
            new IdentificatoreUnivoco(
                "PNBWER56A45F205N-dsarty-1234567", "PRJ001", "feF205", "SERVER001", new Date()));
            
      Eccezione eccezione = new Eccezione(Eccezione.TIPO_ECCEZIONE_COMUNICAZIONE, 1, idRichiesta);
      
      Nota nota = new Nota();

      String[] message = new String[1];

      message[0] = "Nota Eccezione[0]";

      nota.setDescrizioneArray( message );

      eccezione.addNota( nota );


      ContenutoBusta contenuto = new ContenutoBusta(eccezione);
      
      IResponseEnvelope responseBean = new ResponseEnvelope(contenuto, nomeServizio, idRichiesta);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Response Envelope:\n" + responseBean + "\n");
      }
      
      String xmlText = createEnvelopeXmlFromEnvelopeBeanHelper(responseBean);
      
      junit.framework.Assert.assertTrue(EnvelopeHelper.parseXMLAndValidate(xmlText, EnvelopeHelper.getDefaultXmlOptions()));
      if (logger.isDebugEnabled()) {
        logger.debug("Envelope XML created an validated.");
      }
      
      if (logger.isDebugEnabled()) {
        logger.debug("Parsing created xml to reconstruct original Envelope Bean......");
      }

      IResponseEnvelope responseEnvelopeBean2 = 
        (IResponseEnvelope)createEnvelopeBeanFromXmlTextHelper(xmlText);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Checking reconstructed bean wrt original bean......");
      }
      assertEquals(responseBean, responseEnvelopeBean2);

  }
  
  //--------------------------------------------------------------------------------------------
  // Metodi di supporto all'esecuzione dei test
  
  /**
   * Metodo Helper per la creazione di un bean di test contenente la busta di richiesta per il tracciato ed il servizio specificati.
   * Tutti gli elementi della busta non passati come parametri sono definiti all'interno del codice del metodo.  
   * @param nomeServizio - Nome del servizio
   * @param contestoServizio - Contesto del servizio
   * @param xmlContentBytes - Byte array contenente il tracciato xml della richiesta
   * @return bean che implementa l'interfaccia IRequestEnvelope e definisce il contenuto della busta di richiesta 
   * @throws Exception - Se si verificano eccezioni durante la creazione delle diverse parti del bean
   */
  protected IRequestEnvelope createTestRequestEnvelopeBeanHelper(
      String nomeServizio, 
      String contestoServizio, 
      byte[] xmlContentBytes) throws Exception {
    IRequestEnvelope requestBean = new RequestEnvelope();
    
      requestBean.setContenuto(new ContenutoBusta(xmlContentBytes));
      requestBean.setNomeServizio(nomeServizio);
      requestBean.setContestoServizio(contestoServizio);
      
      // Codice amministrazione destinataria. Secondo direttive CNIPA
      // dovrebbe avere il seguente formato c_<codicebelfiorecomune>, ma la modellazione
      // attualmente non consente l'uso di '_' nel codice amministrazione
      requestBean.setCodiceDestinatario("F205"); 
      
      requestBean.setIdentificatoreDiRichiesta( 
        new IdentificatoreDiRichiesta(
            new IdentificatoreUnivoco(
                "PNBWER56A45F205N-dsarty-1234567", "PRJ001", "feF205", "SERVER001", new Date())));

      requestBean.setEstremiOperatore( 
    	        new EstremiOperatore("operatore", "operatore", "QWEASD45A33F205P", "operatore@people.it"));
      
      requestBean.setEstremiRichiedente( 
        new EstremiRichiedente("richiedente", "richiedente", "QWEASD45A33F205P", "richiedente@people.it"));
      
      requestBean.setEstremiTitolare(
        new EstremiTitolare(requestBean.getEstremiRichiedente().getEstremiPersonaFisica())); 
      
//      EstremiTitolare soggettoDelegatoPF = 
//    	  new EstremiTitolare(new EstremiPersonaFisica("nomedelegato", "cognomedelegato", "QWEASD45A33F205P"));
      
      EstremiTitolare soggettoDelegatoPG = 
    	  new EstremiTitolare(new EstremiPersonaGiuridica("ragionesocialedelegato", "09322288576"));
      
      //requestBean.setEstremiSoggettoDelegato(soggettoDelegatoPF);
      requestBean.setEstremiSoggettoDelegato(soggettoDelegatoPG);

      requestBean.setMittente( 
        new CredenzialiUtenteCertificate(
            requestBean.getEstremiRichiedente().getCodiceFiscale(), 
            new Allegato(
                "Credenziali.xml", 
                "Credenziali firmate del richiedente", 
                new String("fjhgfjhgfjhjqwer5435").getBytes(),
                null, Allegato.TIPO_ALLEGATO_FIRMATO)));
      
      Recapito recapito = new Recapito("ReferenteRecapito", Recapito.PRIORITA_RECAPITO_PRINCIPALE);
      recapito.setIndirizzoEmail("aa@bb.cdef");
      //recapito.setRecapitoPostaleTestuale("Recapito postale testuale");
      
      requestBean.addRecapito(recapito);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Test Request Envelope bean creato per servizio :" + nomeServizio + " (contesto " + contestoServizio + ").");
      }
      return requestBean;
  }
  
  /**
   * Metodo Helper per la creazione di un bean di test IRequestEnvelope contenente la busta di richiesta per il tracciato ed il servizio specificati. 
   * @param nomeServizio - Nome del servizio
   * @param contestoServizio - Contesto del servizio
   * @param contentFileName - Path assoluto del file contenente il tracciato xml della richiesta
   * @return bean che implementa l'interfaccia IRequestEnvelope e definisce il contenuto della busta di richiesta 
   * @throws Exception - Se si verificano eccezioni durante la creazione delle diverse parti del bean
   */
  protected IRequestEnvelope createTestRequestEnvelopeBeanHelper(
      String nomeServizio, 
      String contestoServizio, 
      String contentFileName) throws Exception {
    byte[] xmlContentBytes = EnvelopeHelper.readRawBytesFromFilename(contentFileName);
    return createTestRequestEnvelopeBeanHelper(nomeServizio, contestoServizio, xmlContentBytes);
  }
  
  protected IResponseEnvelope createTestResponseEnvelopeBeanHelper(
      String nomeServizio, 
      ContenutoBusta contenutoBusta) throws Exception {
    
    IdentificatoreDiRichiesta idRichiesta = 
      new IdentificatoreDiRichiesta(
          new IdentificatoreUnivoco(
              "OP001", "PRJ001", "feF205", "SERVER001", new Date()));
    
      IResponseEnvelope responseBean = new ResponseEnvelope(contenutoBusta, nomeServizio, idRichiesta);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Test Response Envelope bean creato per servizio :" + nomeServizio + ".");
      }
      
      return responseBean;
  }

  protected IResponseEnvelope createTestResponseEnvelopeBeanHelper(
      String nomeServizio, 
      String responseXmlFileName) throws Exception {
    
    IdentificatoreDiRichiesta idRichiesta = 
      new IdentificatoreDiRichiesta(
          new IdentificatoreUnivoco(
              "OP001", "PRJ001", "feF205", "SERVER001", new Date()));
    
      byte[] xmlContentBytes = EnvelopeHelper.readRawBytesFromFilename(responseXmlFileName);
      
      ContenutoBusta contenutoBusta = new ContenutoBusta(xmlContentBytes);
      
      IResponseEnvelope responseBean = new ResponseEnvelope(contenutoBusta, nomeServizio, idRichiesta);
      
      return responseBean;
  }

  
  /**
   * Crea una busta xml contenente una richiesta o una risposta di invio utilizzando il contenuto <br>
   * del bean (di richiesta o di risposta) passato come parametro.<br>
   * @param envelopeBean - Bean con i dati da utilizzare per popolare la busta xml.
   * @return Stringa contenente il tracciato xml della busta 
   * @throws Exception - Se si verifica una eccezione durante la costruzione della busta
   */
  protected String createEnvelopeXmlFromEnvelopeBeanHelper(IEnvelope envelopeBean) throws Exception {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Input Bean :\n" + envelopeBean + "\n");
    }
    
    IEnvelopeFactory envelopeFactory = 
      new EnvelopeFactory_modellazioneb116_envelopeb002();
    
    String xmlText = envelopeFactory.createEnvelopeXmlText(envelopeBean);
    
    if (logger.isDebugEnabled()) {
      //logger.debug("Envelope xml creato:\n" + xmlText + "\n");
      logger.debug("Envelope xml creato.");
    }
    
    return xmlText;
  }
  
  /**
   * Crea un envelope bean di richiesta o di risposta partendo dalla busta xml passata come parametro.<br>
   * 
   * @param envelopeXmlString - Stringa contenente il tracciato xml dell'envelope di richiesta o di risposta.
   * @return - Envelope Bean di richiesta (IRequestEnvelope) o di risposta (IResponseEnvelope)
   * @throws Exception - Se si verifica un'eccezione durante il parsing del tracciato xml o durate la costruzione del bean
   */
  protected IEnvelope createEnvelopeBeanFromXmlTextHelper(String envelopeXmlString) throws Exception {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Input Document :\n" + envelopeXmlString + "\n");
    }
         
    IEnvelopeFactory envelopeFactory = 
      new EnvelopeFactory_modellazioneb116_envelopeb002();
    
    IEnvelope envelopeBean = envelopeFactory.createEnvelopeBean(envelopeXmlString);
    
    if (envelopeBean.isRequestEnvelope()) {
      envelopeBean = (IRequestEnvelope) envelopeBean;
    } else if (envelopeBean.isResponseEnvelope()) {
      envelopeBean = (IResponseEnvelope) envelopeBean;
    } else {
      throw new Exception("Envelope bean non valido");
    }

    if (logger.isDebugEnabled()) {
      //logger.debug("Envelope bean creato :\n" + envelopeBean + "\n");
      logger.debug("Envelope bean creato.");
    }
    
    return envelopeBean;
  
  }
  
  
  protected String getTestHeader() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n**************************************************************************");
    sb.append("\n                              BEGIN TEST");
    sb.append("\nTest Method: " + getName());
    sb.append("\nNome servizio: " + nomeServizio);
    sb.append("\nContesto servizio: " + contestoServizio);
    sb.append("\nContent File name: " + xmlContentFileName);
    sb.append("\n**************************************************************************\n");
    sb.append("\n");
    return sb.toString();
  }

  protected String getTestFooter(boolean passed) {
    StringBuffer sb = new StringBuffer();
    sb.append("\n**************************************************************************");
    sb.append("\n                              END TEST");
    sb.append("\nTest Method: " + getName());
    sb.append("\nNome servizio: " + nomeServizio);
    sb.append("\nContesto servizio: " + contestoServizio);
    sb.append("\nContent File name: " + xmlContentFileName);
    sb.append("\nTest result: " + (passed ? "PASSED" : "FAILED"));
    sb.append("\n**************************************************************************");
    sb.append("\n");
    return sb.toString();
  }


}

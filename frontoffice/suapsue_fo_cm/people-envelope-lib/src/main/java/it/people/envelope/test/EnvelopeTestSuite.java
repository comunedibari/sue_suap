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

import it.people.envelope.util.EnvelopeHelper;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestSuite JUnit per la libreria di gestione delle buste di richiesta e di risposta
 *
 */
public class EnvelopeTestSuite {
  
  public static final String TESTFILE_DIR_PROPERTY = "it.people.envelope.test.testfiledirectory";
  public static final String ENVELOPE_PROPERTIES_FILE = "envelope.properties";
  
  protected static String testFileDir = null;

  public static Test suite() throws Exception {
    TestSuite suite = new TestSuite("Test for it.people.envelope.test");
    //$JUnit-BEGIN$
    //-----------------------------------------------------------------------------------
    //Test creazione envelope bean partendo da un tracciato xml contenente una busta di richiesta
    suite.addTest(
        new EnvelopeTest(
            "testCreateEnvelopeBeanFromXML", 
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            getTestFileDir() + "/envelope/TestRichiestaInvio_RichiestaRimborsoICI.xml"));
    //-----------------------------------------------------------------------------------
    //Test creazione envelope bean partendo da una busta xml di risposta contenente una ricevuta
    suite.addTest(
        new EnvelopeTest(
            "testCreateEnvelopeBeanFromXML", 
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            getTestFileDir() + "/envelope/TestRispostaInvio_Ricevuta_RichiestaRimborsoICI.xml"));
    
  //-----------------------------------------------------------------------------------
  //Test creazione envelope bean partendo da una busta xml di risposta contenente un'eccezione
  suite.addTest(
      new EnvelopeTest(
          "testCreateEnvelopeBeanFromXML", 
          "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
          "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
          getTestFileDir() + "/envelope/TestRispostaInvio_Eccezione_RichiestaRimborsoICI.xml"));
  
    //-----------------------------------------------------------------------------------
    // Test di creazione di una busta xml di richiesta o di riposta a partire da un tracciato xml di un servizio
    //
    // Test creazione busta xml di richiesta per servizio RichiestaRimborsoICI
    suite.addTest(
        new EnvelopeTest(
            "testCreateRichiestaInvioXml", 
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            getTestFileDir() + "/tracciatiservizi/TestRichiestaRimborsoICI.xml"));
    //
    // Test creazione busta xml di risposta per servizio RichiestaRimborsoICI
    suite.addTest(
        new EnvelopeTest(
            "testCreateRispostaInvioXml_Ricevuta", 
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
            getTestFileDir() + "/tracciatiservizi/TestRicevuta_RichiestaRimborsoICI.xml"));
    //
    // Test creazione di una busta xml di risposta contenente un'eccezione
    suite.addTest(
        new EnvelopeTest(
            "testCreateRispostaInvioXml_Eccezione", 
            "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici"));
//    //-----------------------------------------------------------------------------------
//    // Test creazione di una  busta xml di richiesta per servizio pagamentotosapinmodalitaanonimaeguidata
//    suite.addTest(
//        new EnvelopeTest(
//            "testCreateRichiestaInvioXml", 
//            "it.people.fsl.servizi.fiscali.tosap.pagamentotosapinmodalitaanonimaeguidata",
//            "it.people.fsl.servizi.fiscali.tosap.pagamentotosapinmodalitaanonimaeguidata",
//            getTestFileDir() + 
//            "/tracciatiservizi/osap/it.people.fsl.servizi.fiscali.tosap.pagamentotosapinmodalitaanonimaeguidata/osap.visualizzazioneimportodapagareosap_input.xml"));
//    //
//    // Test creazione di una busta xml di risposta per servizio pagamentotosapinmodalitaanonimaeguidata
//    suite.addTest(
//        new EnvelopeTest(
//            "testCreateRispostaInvioXml_Ricevuta", 
//            "it.people.fsl.servizi.fiscali.tosap.pagamentotosapinmodalitaanonimaeguidata",
//            null,
//            getTestFileDir() + 
//            "/tracciatiservizi/osap/it.people.fsl.servizi.fiscali.tosap.pagamentotosapinmodalitaanonimaeguidata/osap.visualizzazioneimportodapagareosap_output.xml"));
  //-----------------------------------------------------------------------------------
  //Test TENTATIVO di creazione envelope bean partendo da un tracciato xml non valido 
  //(ovvero non contenente una busta di richiesta o risposta): 
  //  Il test ha successo se viene sollevata un'eccezione
  suite.addTest(
      new EnvelopeTest(
          "testCreateEnvelopeBeanFromInvalidXml", 
          "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
          "it.people.fsl.servizi.fiscali.ici.istanzadirimborsoici",
          getTestFileDir() + "/tracciatiservizi/TestRichiestaRimborsoICI.xml"));
  //-----------------------------------------------------------------------------------
    //$JUnit-END$
    return suite;
  }
  
  /**
   * @return il path assoluto della directory contenente i file xml da utilizzare per i test
   */
  protected static String getTestFileDir() throws Exception {
    if (testFileDir == null) setTestFileDir();
    return testFileDir;
  }

  
  protected static void setTestFileDir() throws Exception {
    String fileDirProperty = System.getProperty(TESTFILE_DIR_PROPERTY);
    if (fileDirProperty == null) {
      Properties props = new Properties();
//      InputStream is = this.getClass().getResourceAsStream("/"+ ENVELOPE_PROPERTIES_FILE);
//      FileInputStream fis = new FileInputStream();
      String propFilePath = EnvelopeHelper.getPathForResource(ENVELOPE_PROPERTIES_FILE);
      props.load(new FileInputStream(propFilePath));
      testFileDir = props.getProperty(TESTFILE_DIR_PROPERTY);
      File fileDirPath = new File (testFileDir);
      if (!fileDirPath.isAbsolute()) {
        testFileDir = new File(new File(propFilePath).getParent() + "/" + testFileDir).getCanonicalPath();
      }
      if (!new File(testFileDir).isDirectory()) throw new Exception("Path per directory contenente file per test non valido");
      
      System.setProperty(TESTFILE_DIR_PROPERTY, testFileDir);
            
    }

  }
  
  public static void main(String args[]) throws Exception { 
    junit.textui.TestRunner.run(suite());
  }


}

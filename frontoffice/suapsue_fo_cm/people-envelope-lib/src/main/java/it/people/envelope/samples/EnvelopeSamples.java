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
package it.people.envelope.samples;

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
import it.people.envelope.beans.EstremiRichiedente;
import it.people.envelope.beans.EstremiTitolare;
import it.people.envelope.beans.IdentificatoreDiRichiesta;
import it.people.envelope.beans.IdentificatoreUnivoco;
import it.people.envelope.beans.Recapito;
import it.people.envelope.util.EnvelopeHelper;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Esempi di creazione di envelope beans e di generazione e parsing di tracciati xml
 *
 */
public class EnvelopeSamples {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(EnvelopeSamples.class);

  /**
   * Esempio creazione di un bean contenente la busta di richiesta per il tracciato ed il servizio specificati.
   * Tutti gli elementi della busta non passati come parametri sono definiti all'interno del codice del metodo.  
   * @param nomeServizio - Nome del servizio
   * @param contestoServizio - Contesto del servizio
   * @param xmlContent - Stringa contenente il tracciato xml della richiesta
   * @return bean che implementa l'interfaccia IRequestEnvelope e definisce il contenuto della busta di richiesta 
   * @throws Exception - Se si verificano eccezioni durante la creazione delle diverse parti del bean
   */
  public IRequestEnvelope createRequestEnvelopeBeanSample(
      String nomeServizio, 
      String contestoServizio, 
      String xmlContent) throws Exception {
    IRequestEnvelope requestBean = new RequestEnvelope();

    ContenutoBusta contenutoBusta = null;
    
    // in alternativa si pu� mettere un blocco try-catch
    if (xmlContent != null) contenutoBusta = new ContenutoBusta(xmlContent);

    requestBean.setContenuto(contenutoBusta);
    
    requestBean.setNomeServizio(nomeServizio);
    requestBean.setContestoServizio(contestoServizio);
    // Codice amministrazione destinataria. Secondo direttive CNIPA
    // dovrebbe avere il seguente formato c_<codicebelfiorecomune>, ma la modellazione
    // attualmente non consente l'uso di '_' nel codice amministrazione
    requestBean.setCodiceDestinatario("F205"); 
    
    requestBean.setIdentificatoreDiRichiesta( 
      new IdentificatoreDiRichiesta(
          new IdentificatoreUnivoco(
              "OP001", "PRJ001", "feF205", "SERVER001", new Date())));
    
    requestBean.setEstremiRichiedente( 
      new EstremiRichiedente("nome", "cognome", "QWEASD45A33F205P"));
    
    requestBean.setEstremiTitolare(
      new EstremiTitolare(requestBean.getEstremiRichiedente().getEstremiPersonaFisica())); 
    
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
   * Esempio creazione di un bean contenente la busta di risposta per il servizio specificato.
   * @param nomeServizio - Nome del servizio
   * @param xmlContent - Stringa contenente il tracciato xml della risposta
   * @return bean che implementa l'interfaccia IRequestEnvelope e definisce il contenuto della busta di risposta
   * @throws Exception
   */
  public IResponseEnvelope createResponseEnvelopeBeanSample(
      String nomeServizio, 
      String xmlContent) throws Exception {
    
    IdentificatoreDiRichiesta idRichiesta = 
      new IdentificatoreDiRichiesta(
          new IdentificatoreUnivoco(
              "OP001", "PRJ001", "feF205", "SERVER001", new Date()));
    
      ContenutoBusta contenutoBusta = null;
      
      // in alternativa all'if si pu� mettere un blocco try-catch
      if (xmlContent != null) contenutoBusta = new ContenutoBusta(xmlContent);
      
      IResponseEnvelope responseBean = new ResponseEnvelope(contenutoBusta, nomeServizio, idRichiesta);
      
      return responseBean;
  }

  /**
   * Esempio creazione di un bean di risposta contenente un'eccezione per il servizio specificato.
   * @param nomeServizio
   * @return ResponseEnvelopeBean popolato con l'eccezione
   * @throws Exception
   */
  public IResponseEnvelope createResponseEnvelopeBean_EccezioneSample(
      String nomeServizio) throws Exception {
      // Crea un bean di risposta contenente una eccezione
      IdentificatoreDiRichiesta idRichiesta = 
        new IdentificatoreDiRichiesta(
            new IdentificatoreUnivoco(
                "PNBWER56A45F205N-dsarty-1234567", "PRJ001", "feF205", "SERVER001", new Date()));
            
      Eccezione eccezione = new Eccezione(Eccezione.TIPO_ECCEZIONE_COMUNICAZIONE, 1, idRichiesta);
      ContenutoBusta contenuto = new ContenutoBusta(eccezione);
      
      IResponseEnvelope responseBean = new ResponseEnvelope(contenuto, nomeServizio, idRichiesta);
      
      if (logger.isDebugEnabled()) {
        logger.debug("Response Envelope:\n" + responseBean + "\n");
      }
      
      return responseBean;
  }
  
  /**
   * Crea una busta xml contenente una richiesta o una risposta di invio utilizzando il contenuto <br>
   * del bean (di richiesta o di risposta) passato come parametro.<br>
   * @param envelopeBean - Bean con i dati da utilizzare per popolare la busta xml.
   * @return Stringa contenente il tracciato xml della busta 
   * @throws Exception - Se si verifica una eccezione durante la costruzione della busta
   */
  protected String createEnvelopeXmlFromEnvelopeBeanSample(IEnvelope envelopeBean) throws Exception {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Input Bean :\n" + envelopeBean + "\n");
    }
    
    IEnvelopeFactory envelopeFactory = 
      new EnvelopeFactory_modellazioneb116_envelopeb002();
    
    String xmlText = envelopeFactory.createEnvelopeXmlText(envelopeBean);
    
    EnvelopeHelper.parseXMLAndValidate(xmlText, EnvelopeHelper.getDefaultXmlOptions());
    
    return xmlText;
  }
  
  /**
   * Crea un envelope bean (di richiesta o di risposta) con contenuto null
   * e lo popola successivamente con il contenuto cml passato come parametro.<br>
   * Il tipo di bean creato dipende dal valore del parametro beanType: 0=request, altro=response<br>
   * 
   * @param beanType - Tipo di Envelope bean da creare (0=request, altro=response)
   * @param xmlContent - Stringa contenente il tracciato xml da inserire nel bean.
   * @throws Exception Se si verifica un'eccezione durante il parsing del tracciato xml o durate la costruzione del bean
   */
  public void populateEnvelopeBeanWithContentSample(int beanType, String xmlContent) throws Exception {
    
    IEnvelope envelopeBean = null;
    
    // crea un Envelope bean di richiesta o di risposta con contenuto null
    if (beanType==0) {// richiesta
      envelopeBean = createRequestEnvelopeBeanSample("servizio1","servizio1", null);
    } else { // risposta
      envelopeBean = createResponseEnvelopeBeanSample("servizio1", null);
    }
    logger.debug("Envelope bean: \n" + envelopeBean);
    
    // Generazione del tracciato xml dell'envelope
    IEnvelopeFactory envelopeFactory = 
      new EnvelopeFactory_modellazioneb116_envelopeb002();
    
    String envelopexml = envelopeFactory.createEnvelopeXmlText(envelopeBean);
    //logger.debug("Envelope xml: \n" + envelopexml);
    // La validazione fallisce visto che l'elemento Contenuto � vuoto
    EnvelopeHelper.parseXMLAndValidate(envelopexml, EnvelopeHelper.getDefaultXmlOptions());

    // Ricostruisce un secondo envelope beran partendo dal tracciato xml appena generato
    IEnvelope bean2 = envelopeFactory.createEnvelopeBean(envelopexml);
    logger.debug("Envelope bean ricostruito: \n" + bean2);
    
    // Aggiunge al bean originario il contenuto xml
    logger.debug("Aggiunta di un contenuto in corso...");
    
    ContenutoBusta newContent = null;
    
    try {
      newContent = new ContenutoBusta(xmlContent);
    } catch (Exception e) {
      //se il costruttore solleva una eccezione il contenuto rimane null
    }
    
    if (envelopeBean.isRequestEnvelope()) {
      ((IRequestEnvelope)envelopeBean).setContenuto(newContent);
      
    } else if (envelopeBean.isResponseEnvelope()) {
      ((IResponseEnvelope)envelopeBean).setContenuto(newContent);
      
    } else throw new Exception("Envelope non valido");
    
    logger.debug("Envelope bean con contenuto: \n" + envelopeBean);
    
    envelopexml = envelopeFactory.createEnvelopeXmlText(envelopeBean);
    //logger.debug("Envelope xml con contenuto: \n" + envelopexml);

    // La validazione adesso deve andare a buon fine
    EnvelopeHelper.parseXMLAndValidate(envelopexml, EnvelopeHelper.getDefaultXmlOptions());
    
  }

  
  /**
   * Crea un envelope bean (di richiesta o di risposta) partendo dalla busta xml passata come parametro.<br>
   * Il tipo di bean creato dipende dalla tipologia di busta xml.<br>
   * 
   * @param envelopeXmlString - Stringa contenente il tracciato xml dell'envelope di richiesta o di risposta.
   * @return - Envelope Bean di richiesta (IRequestEnvelope) o di risposta (IResponseEnvelope)
   * @throws Exception - Se si verifica un'eccezione durante il parsing del tracciato xml o durate la costruzione del bean
   */
  protected IEnvelope createEnvelopeBeanFromXmlTextHelper(String envelopeXmlString) throws Exception {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Input Document :\n" + envelopeXmlString + "\n");
    }
         
    EnvelopeHelper.parseXMLAndValidate(envelopeXmlString, EnvelopeHelper.getDefaultXmlOptions());
    
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
      logger.debug("Envelope bean creato :\n" + envelopeBean + "\n");
      
    }
    
    return envelopeBean;
  
  }
  
  
  
  public static void main(String[] args) throws Exception {
    EnvelopeSamples samples = new EnvelopeSamples();
    
    samples.
    populateEnvelopeBeanWithContentSample(0, 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<test>test</test>");


  }



}
      


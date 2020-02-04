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
package it.people.envelope;

import it.people.b002.serviziCondivisi.envelope.RichiestadiInvioDocument;

//import it.diviana.egov.b116.serviziCondivisi.invio.RispostadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.RispostadiInvioDocument;

import it.people.envelope.exceptions.InvalidEnvelopeException;
import it.people.envelope.exceptions.NotAnEnvelopeException;
import it.people.envelope.util.EnvelopeHelper;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;


/**
 * Factory per la creazione di una busta di richiesta o risposta partendo da un bean IRequestEnvelope o IResponseEnvelope<br>
 * e per la creazione di un IRequestEnvelope o IResponseEnvelope partendo da una busta di richiesta o di risposta.<br> 
 * Versione modellazione di riferimento: build 116 03/08/2006<br>
 * Versione envelope di riferimento: build 002<br>
 *
   * @see it.people.envelope.IEnvelopeFactory
 */
public class EnvelopeFactory_modellazioneb116_envelopeb002 implements IEnvelopeFactory {
  /**
   * Logger for this class
   */
  private static final Logger logger = Logger
      .getLogger(EnvelopeFactory_modellazioneb116_envelopeb002.class);
	
  // Inizializzazione 
  EnvelopeXMLFragmentFactory_modellazioneb116_envelopeb002 parserJavaObjToXBeanXmlObj = 
    new EnvelopeXMLFragmentFactory_modellazioneb116_envelopeb002();
	EnvelopeBeansFactory_modellazioneb116_envelopeb002 parserXBeanXmlObjToJavaObj = 
    new EnvelopeBeansFactory_modellazioneb116_envelopeb002();
  
  protected String envelopeXmlString = null;
  protected XmlObject envelopeXmlObj = null;
  protected IEnvelope envelopeBean = null;


	//-----------------------------------------------------------------------------
	//   Metodi interfaccia IEnvelopeFactory
  

  /** 
   * @see it.people.envelope.IEnvelopeFactory#createEnvelopeXmlText(it.people.envelope.IEnvelope)
   */
  public String createEnvelopeXmlText(IEnvelope envelopeBean) throws Exception {
    String xmlText = null;
    XmlObject xmlObj = null;
    
    if (envelopeBean.isRequestEnvelope()) {
      xmlObj = 
        parserJavaObjToXBeanXmlObj.
          createRichiestadiInvioDocumentXmlObj((IRequestEnvelope) envelopeBean);
    } else if (envelopeBean.isResponseEnvelope()) {
      xmlObj =
        parserJavaObjToXBeanXmlObj.
          createRispostadiInvioDocumentXmlObj((IResponseEnvelope) envelopeBean);

    } else {
        logger.error(
            "createEnvelopeXMLText(IEnvelope) - Envelope non valido: non � di tipo IRequestEnvelope o IResponseEnvelope : envelopeBean="
                    + envelopeBean, null);
      throw new Exception("createEnvelopeXMLText(IEnvelope): Envelope non valido: non � di tipo IRequestEnvelope o IResponseEnvelope");
    }
    
//    if (!EnvelopeHelper.validateXmlObj(xmlObj, EnvelopeHelper.getDefaultXmlOptions())) {
//      throw new Exception("EnvelopeFactory_modellazioneb116_envelopeb002::createEnvelopeXmlText:: XML Creato da envelope Bean non valido.");
//    }
    

    if (logger.isDebugEnabled()) {
      logger
          .debug("createEnvelopeXmlText(IEnvelope) - Xbean XMLObject creato.");
    }
    
//    XmlOptions opt = EnvelopeHelper.getDefaultXmlOptions();
//    // TODO: Verificare se ci sono problemi di enconding con il contenuto xml
//    //opt.setCharacterEncoding("ISO-8859-2");
//    
//    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    try {
//      xmlObj.save(baos,opt);
//      
//   } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//   // TODO: Verificare se ci sono problemi di enconding con il contenuto xml
//   //xmlText = new String(baos.toByteArray(), "UTF-8");
//   xmlText = new String(baos.toByteArray());
    // Cosi non funziona perch� l'encoding non risulta impostato nell'xmlobject
    // Occorre specificare esplicitamente l'encoding da usare
    //xmlText = EnvelopeHelper.getEncodedXmlText(xmlObj, xmlObj.documentProperties().getEncoding());
    XmlOptions opt = EnvelopeHelper.getDefaultXmlOptions();
    xmlText = EnvelopeHelper.getEncodedXmlText(xmlObj, "UTF-8", opt);

    logger.debug("createEnvelopeXmlText(IEnvelope) - Envelope XML:/n"+xmlText);
    
    return xmlText;
  }

  /** 
   * @see it.people.envelope.IEnvelopeFactory#createEnvelopeBean(java.lang.String)
   */
  //public IEnvelope createEnvelopeBean(String envelopeXmlText) throws Exception {
  public IEnvelope createEnvelopeBean(String envelopeXmlText) throws InvalidEnvelopeException, NotAnEnvelopeException, Exception {
    IEnvelope envelopeBean = null;
    XmlObject xmlObj = XmlObject.Factory.parse(envelopeXmlText, EnvelopeHelper.getDefaultXmlOptions());

    //if (xmlObj instanceof RichiestadiInvioDocument) {
    if (xmlObj.schemaType().equals(RichiestadiInvioDocument.type)) {
      try {
        RichiestadiInvioDocument richiestaDocumentXmlObj =
          (RichiestadiInvioDocument)xmlObj.changeType(RichiestadiInvioDocument.type);

        envelopeBean =
          parserXBeanXmlObjToJavaObj.createRequestEnvelopeBean(richiestaDocumentXmlObj);
      } catch (Exception ex) {
        logger.error(
            "createEnvelopeBean(String) - Contenuto della RichiestadiInvio XML non valido. Documento XML=\n"
                    + envelopeXmlText, null);
        throw new InvalidEnvelopeException("Contenuto della RichiestadiInvio XML non valido.");
      }
      
    } else 
      //if (xmlObj instanceof RispostadiInvioDocument) {
      if (xmlObj.schemaType().equals(RispostadiInvioDocument.type)) {
      
        try {
          RispostadiInvioDocument rispostaDocumentXmlObj =
            (RispostadiInvioDocument)xmlObj.changeType(RispostadiInvioDocument.type);
          envelopeBean =
            parserXBeanXmlObjToJavaObj.createResponseEnvelopeBean(rispostaDocumentXmlObj);
        } catch (Exception ex) {
          logger.error(
              "createEnvelopeBean(String) - Contenuto della RispostadiInvio XML non valido. Documento XML=\n"
                      + envelopeXmlText, null);
          throw new InvalidEnvelopeException("Contenuto della RichiestadiInvio XML non valido.");
        }
    } else {
      logger.error(
          "createEnvelopeBean(String) - Documento XML non valido: non � di tipo RispostadiInvio o RichiestadiInvio. Documento XML=\n"
                  + envelopeXmlText, null);
      //throw new Exception("createEnvelopeBean(String): Documento XML non valido: non � di tipo RispostadiInvio o RichiestadiInvio.");
      throw new NotAnEnvelopeException("createEnvelopeBean(String): Documento XML non valido: non � di tipo RispostadiInvio o RichiestadiInvio.");
      
    }
    return envelopeBean;
  }

}

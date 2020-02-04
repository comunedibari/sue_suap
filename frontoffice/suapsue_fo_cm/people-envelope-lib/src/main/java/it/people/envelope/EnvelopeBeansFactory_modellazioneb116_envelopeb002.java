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
package it.people.envelope;

import it.diviana.egov.b116.cnipa.SceltaTitolare;
import it.diviana.egov.b116.infrastruttura.AnyType;
import it.diviana.egov.b116.oggettiCondivisi.AllegatoFirmatoDigitalmente;
import it.diviana.egov.b116.oggettiCondivisi.AllegatoIncorporato;
import it.diviana.egov.b116.oggettiCondivisi.AllegatononFirmato;
import it.diviana.egov.b116.oggettiCondivisi.CivicoEsternoNumerico;
import it.diviana.egov.b116.oggettiCondivisi.Comune;
import it.diviana.egov.b116.oggettiCondivisi.CredenzialiUtenteCertificate;
import it.diviana.egov.b116.oggettiCondivisi.Eccezione;
import it.diviana.egov.b116.oggettiCondivisi.EstremiPersonaGiuridica;
import it.diviana.egov.b116.oggettiCondivisi.EstremidellaPersona;
import it.diviana.egov.b116.oggettiCondivisi.FileAllegato;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatorediProtocollo;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatorediRichiesta;
import it.diviana.egov.b116.oggettiCondivisi.InformazioniProgetto;
import it.diviana.egov.b116.oggettiCondivisi.Nota;
import it.diviana.egov.b116.oggettiCondivisi.RecapitoPostale;
import it.diviana.egov.b116.oggettiCondivisi.RecapitoTelefonico;
import it.diviana.egov.b116.oggettiCondivisi.RichiestaC2G;
import it.diviana.egov.b116.oggettiCondivisi.SceltaContenuto;
import it.diviana.egov.b116.oggettiCondivisi.SceltaInformazioniValidazione;
//import it.diviana.egov.b116.serviziCondivisi.InformazioniperVerificaDelega;
import it.diviana.egov.b116.serviziCondivisi.RispostadiInvio;

//import it.diviana.egov.b116.serviziCondivisi.invio.RispostadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.RispostadiInvioDocument;

import it.people.b002.serviziCondivisi.envelope.InformazioniperVerificaDelega;

import it.people.b002.serviziCondivisi.envelope.InformazioniperProtocollazione;
import it.people.b002.serviziCondivisi.envelope.Recapito;
import it.people.b002.serviziCondivisi.envelope.RichiestadiInvio;
import it.people.b002.serviziCondivisi.envelope.RichiestadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.SoggettoPersonaFisica;
import it.people.envelope.util.EnvelopeHelper;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class EnvelopeBeansFactory_modellazioneb116_envelopeb002 {
  
  String msgPrefix = getClass().getName();

	
	//-----------------------------------------------------------------------------
	//   Metodi di supporto per creazione bean componenti di IEnvelope partendo da elementi di base XML Beans  
	
  
  public it.people.envelope.beans.IdentificatoreDiProtocollo createIdentificatorediProtocolloBean(
      IdentificatorediProtocollo idProtocolloXmlObj) {
    
    String codiceAmministrazione = idProtocolloXmlObj.getCodiceAmministrazione();
    String codiceAOO = idProtocolloXmlObj.getCodiceAOO();
    Date dataRegistrazione = 
      EnvelopeHelper.CalendarToDate(idProtocolloXmlObj.getDatadiRegistrazione());
    long numeroRegistrazione = idProtocolloXmlObj.getNumerodiRegistrazione().longValue();
    
    it.people.envelope.beans.IdentificatoreDiProtocollo idProtocolloBean =
      new it.people.envelope.beans.IdentificatoreDiProtocollo(
          codiceAmministrazione, codiceAOO, numeroRegistrazione, dataRegistrazione);
    
    return idProtocolloBean;
  }
  
  public it.people.envelope.beans.IdentificatoreDiRichiesta createIdentificatorediRichiestaBean(
      IdentificatorediRichiesta idRichiestaXmlObj) {
    IdentificatoreUnivoco idUnivocoXmlObj = idRichiestaXmlObj.getIdentificatoreUnivoco();
    
    String codiceOperazione = idUnivocoXmlObj.getCodiceIdentificativoOperazione();
    String codiceSistema_nomeServer = idUnivocoXmlObj.getCodiceSistema().getNomeServer();
    String codiceSistema_codiceAmministrazione = idUnivocoXmlObj.getCodiceSistema().getCodiceAmministrazione();
    String codiceProgetto = idUnivocoXmlObj.getCodiceProgetto();
    Date dataRegistrazione = EnvelopeHelper.CalendarToDate(idUnivocoXmlObj.getDatadiRegistrazione());
    
    it.people.envelope.beans.IdentificatoreUnivoco idUnivocoBean =
      new it.people.envelope.beans.IdentificatoreUnivoco(
          codiceOperazione, codiceProgetto, 
          codiceSistema_codiceAmministrazione, codiceSistema_nomeServer, dataRegistrazione);
    
    it.people.envelope.beans.IdentificatoreDiRichiesta idRichiestaBean =
      new it.people.envelope.beans.IdentificatoreDiRichiesta(idUnivocoBean);
    
    if (idRichiestaXmlObj.isSetIdentificatorediProtocollo()) {
      IdentificatorediProtocollo idProtocolloXmlObj = idRichiestaXmlObj.getIdentificatorediProtocollo();
      idRichiestaBean.setIdentificatoreDiProtocollo(createIdentificatorediProtocolloBean(idProtocolloXmlObj));
    }
    return idRichiestaBean;
  }
  
  public it.people.envelope.beans.EstremiPersonaFisica createEstremiPersonaFisicaBean(
      EstremidellaPersona estremiPFXmlObj, it.people.b002.serviziCondivisi.envelope.InformazioniProfili ipXmlObj) throws Exception {
    
    String codiceFiscale = estremiPFXmlObj.getCodiceFiscale();
    String cognome = estremiPFXmlObj.getCognome();
    String nome = estremiPFXmlObj.getNome();
    
    if (ipXmlObj != null && ipXmlObj.getTitolare() != null && ipXmlObj.getTitolare().getPersonaFisica() != null && ipXmlObj.getTitolare().getPersonaFisica().getEMail() != null) {
    	String eMail = ipXmlObj.getTitolare().getPersonaFisica().getEMail();
        it.people.envelope.beans.EstremiPersonaFisica estremiPersonaFisicaBean = 
        	      new it.people.envelope.beans.EstremiPersonaFisica(nome, cognome, codiceFiscale, eMail);
        return estremiPersonaFisicaBean;
    } else {
        it.people.envelope.beans.EstremiPersonaFisica estremiPersonaFisicaBean = 
        	      new it.people.envelope.beans.EstremiPersonaFisica(nome, cognome, codiceFiscale);
        return estremiPersonaFisicaBean;
    }
    
  }
  
 
  public it.people.envelope.beans.EstremiPersonaGiuridica createEstremiPersonaGiuridicaBean(
      EstremiPersonaGiuridica estremiPGXmlObj, it.people.b002.serviziCondivisi.envelope.InformazioniProfili ipXmlObj) throws Exception {
    
    String codiceFiscalePG = estremiPGXmlObj.getCodiceFiscalePersonaGiuridica();
    String denominazione = estremiPGXmlObj.getDenominazioneoRagioneSociale();
    
    if (ipXmlObj != null && ipXmlObj.getTitolare() != null && ipXmlObj.getTitolare().getPersonaGiuridica() != null && ipXmlObj.getTitolare().getPersonaGiuridica().getEMail() != null) {
    	String eMail = ipXmlObj.getTitolare().getPersonaGiuridica().getEMail();
        
        if (ipXmlObj.getTitolare().getPersonaGiuridica().getRappresentanteLegale() != null) {
        	
        	SoggettoPersonaFisica spf = ipXmlObj.getTitolare().getPersonaGiuridica().getRappresentanteLegale();
        	it.people.envelope.beans.EstremiPersonaFisica rappresentanteLegale = new it.people.envelope.beans.EstremiPersonaFisica(spf.getNome(), spf.getCognome(), 
        			spf.getCodiceFiscale(), spf.getEMail());
            it.people.envelope.beans.EstremiPersonaGiuridica estremiPersonaGiuridicaBean = 
          	      new it.people.envelope.beans.EstremiPersonaGiuridica(denominazione, codiceFiscalePG, eMail, rappresentanteLegale);
    	    return estremiPersonaGiuridicaBean;
        } else {
            it.people.envelope.beans.EstremiPersonaGiuridica estremiPersonaGiuridicaBean = 
          	      new it.people.envelope.beans.EstremiPersonaGiuridica(denominazione, codiceFiscalePG, eMail);
    	    return estremiPersonaGiuridicaBean;
        }
        	    
    } else {
        it.people.envelope.beans.EstremiPersonaGiuridica estremiPersonaGiuridicaBean = 
        	      new it.people.envelope.beans.EstremiPersonaGiuridica(denominazione, codiceFiscalePG);
        	    
        	    return estremiPersonaGiuridicaBean;
    }
  }
  
  public it.people.envelope.beans.EstremiRichiedente createEstremiRichiedenteBean(
      EstremidellaPersona estremiPFXmlObj, it.people.b002.serviziCondivisi.envelope.InformazioniProfili ipXmlObj) throws Exception {
    
    String codiceFiscale = estremiPFXmlObj.getCodiceFiscale();
    String cognome = estremiPFXmlObj.getCognome();
    String nome = estremiPFXmlObj.getNome();

    if (ipXmlObj != null && ipXmlObj.getRichiedente() != null && ipXmlObj.getRichiedente().getEMail() != null) {
    	String eMail = ipXmlObj.getRichiedente().getEMail();
        it.people.envelope.beans.EstremiRichiedente richiedenteBean =
        	      new it.people.envelope.beans.EstremiRichiedente(nome, cognome, codiceFiscale, eMail);
        	    
        	    return richiedenteBean; 
    } else {
        it.people.envelope.beans.EstremiRichiedente richiedenteBean =
        	      new it.people.envelope.beans.EstremiRichiedente(nome, cognome, codiceFiscale);
        	    
        	    return richiedenteBean; 
    }
  }

  public it.people.envelope.beans.EstremiOperatore createEstremiOperatoreBean(
		  it.people.b002.serviziCondivisi.envelope.InformazioniProfili ipXmlObj) throws Exception {

	  it.people.envelope.beans.EstremiOperatore operatoreBean = null;
	  
	  if (ipXmlObj != null && ipXmlObj.getOperatore() != null) {
		  String codiceFiscale = ipXmlObj.getOperatore().getCodiceFiscale();
		  String cognome = ipXmlObj.getOperatore().getCognome();
		  String nome = ipXmlObj.getOperatore().getNome();
		  String eMail = ipXmlObj.getOperatore().getEMail();
		  operatoreBean = new it.people.envelope.beans.EstremiOperatore(nome, cognome, codiceFiscale, eMail);
	  }

	  return operatoreBean; 
  }
  
  public it.people.envelope.beans.EstremiTitolare createEstremiTitolareBean(
      SceltaTitolare sceltaTitolareXmlObj, it.people.b002.serviziCondivisi.envelope.InformazioniProfili ipXmlObj) throws Exception {
    
    it.people.envelope.beans.EstremiTitolare titolareBean = null;
    
    if (sceltaTitolareXmlObj.isSetEstremidellaPersona()) {
      titolareBean = 
        new it.people.envelope.beans.EstremiTitolare(
            createEstremiPersonaFisicaBean(sceltaTitolareXmlObj.getEstremidellaPersona(), ipXmlObj));
    } else if (sceltaTitolareXmlObj.isSetEstremiPersonaGiuridica()) {
      titolareBean = 
        new it.people.envelope.beans.EstremiTitolare(
            createEstremiPersonaGiuridicaBean(sceltaTitolareXmlObj.getEstremiPersonaGiuridica(), ipXmlObj));
     }
    return titolareBean; 
  }


  
  public it.people.envelope.beans.Allegato createAllegato(
      AllegatoFirmatoDigitalmente allegatoXmlObj) throws Exception {
    it.people.envelope.beans.Allegato allegatoBean = 
      new it.people.envelope.beans.Allegato(
          allegatoXmlObj.getNomeFile(),
          allegatoXmlObj.getDescrizione(),
          allegatoXmlObj.getContenuto(),
          null,
          it.people.envelope.beans.Allegato.TIPO_ALLEGATO_FIRMATO);
    return allegatoBean;
  }
  
  public it.people.envelope.beans.Allegato createAllegato(
      AllegatononFirmato allegatoXmlObj) throws Exception {
    it.people.envelope.beans.Allegato allegatoBean = 
      new it.people.envelope.beans.Allegato(
          allegatoXmlObj.getNomeFile(),
          allegatoXmlObj.getDescrizione(),
          allegatoXmlObj.getContenuto(),
          null,
          it.people.envelope.beans.Allegato.TIPO_ALLEGATO_NON_FIRMATO);
    return allegatoBean;
  }
  
  public it.people.envelope.beans.Allegato createAllegato(
      AllegatoIncorporato allegatoXmlObj) throws Exception {
    it.people.envelope.beans.Allegato allegatoBean = 
      new it.people.envelope.beans.Allegato(
          null,
          allegatoXmlObj.getDescrizione(),
          allegatoXmlObj.getContenuto(),
          null,
          it.people.envelope.beans.Allegato.TIPO_ALLEGATO_INCORPORATO);
    return allegatoBean;
  }
  
  public it.people.envelope.beans.Allegato createAllegato(
      FileAllegato allegatoXmlObj) throws Exception {
    it.people.envelope.beans.Allegato allegatoBean = 
      new it.people.envelope.beans.Allegato(
          null,
          allegatoXmlObj.getDescrizione(),
          null,
          allegatoXmlObj.getURI(),
          it.people.envelope.beans.Allegato.TIPO_ALLEGATO_FILE_ALLEGATO);
    return allegatoBean;
  }


  
  public it.people.envelope.beans.Nota createNota(
      Nota notaXmlObj) throws Exception {
    it.people.envelope.beans.Nota notaBean = 
      new it.people.envelope.beans.Nota();
    
    if (notaXmlObj.sizeOfDescrizioneArray()>0)
      notaBean.setDescrizioneArray(notaXmlObj.getDescrizioneArray());
    
    if (notaXmlObj.isSetDocBook())
      notaBean.setURI_DocBook(notaXmlObj.getDocBook().getURI());
    
    
    it.people.envelope.beans.Allegato allegato = null;
    
    if (notaXmlObj.isSetAllegatoFirmatoDigitalmente()) {
      allegato = createAllegato(notaXmlObj.getAllegatoFirmatoDigitalmente()); 
    }else if (notaXmlObj.isSetAllegatononFirmato()) {
      allegato = createAllegato(notaXmlObj.getAllegatononFirmato()); 
    }else if (notaXmlObj.isSetAllegatoIncorporato()) {
      allegato = createAllegato(notaXmlObj.getAllegatoIncorporato()); 
    }else if (notaXmlObj.isSetFileAllegato()) {
      allegato = createAllegato(notaXmlObj.getFileAllegato()); 
    }
    
    if (allegato != null) notaBean.setAllegato(allegato);
    
    return notaBean;
  }
  
  public it.people.envelope.beans.Eccezione createEccezioneBean(
      Eccezione eccezioneXmlObj) throws Exception {
    
    it.people.envelope.beans.IdentificatoreDiRichiesta idRichiesta = 
      createIdentificatorediRichiestaBean(eccezioneXmlObj.getIdentificatorediRichiesta());
    long codice = eccezioneXmlObj.getCodice().longValue();
    String tipo = eccezioneXmlObj.getTipo().toString();
    
    it.people.envelope.beans.Eccezione eccezioneBean =
      new it.people.envelope.beans.Eccezione(tipo, codice, idRichiesta);
    
    if (eccezioneXmlObj.sizeOfNotaArray()>0) {
      Nota[] notaXmlObjArray = eccezioneXmlObj.getNotaArray();
      for (int i=0; i<notaXmlObjArray.length; i++) {
        Nota curNotaXmlObj = notaXmlObjArray[i];
        eccezioneBean.addNota(createNota(curNotaXmlObj));
      }
    }
    return eccezioneBean;
  }
  
  
	public it.people.envelope.beans.ContenutoBusta createContenutoBustaBean (
      SceltaContenuto sceltaContenutoXmlObj) throws Exception {
    
      it.people.envelope.beans.ContenutoBusta contenutoBusta = null;
    if (sceltaContenutoXmlObj.isSetAnyType()) {
      AnyType contenutoXmlObj = sceltaContenutoXmlObj.getAnyType();
      

//      String contenutoXmlText = EnvelopeHelper.
//        //getEncodedXmlText(contenutoXmlObj, contenutoXmlObj.documentProperties().getEncoding());
//      getEncodedXmlText(
//          contenutoXmlObj, 
//          contenutoXmlObj.documentProperties().getEncoding(), 
//          new XmlOptions().setSaveInner());
//      
////      XmlObject xmlObj = XmlObject.Factory.parse(contenutoXmlText);
////      contenutoXmlText = EnvelopeHelper.
////          getEncodedXmlText(
////              xmlObj, 
////              xmlObj.documentProperties().getEncoding(), 
////            new XmlOptions().setSaveInner());
      
      // Approccio alternativo: per evitare l'inclusione del tag <xml-fragment> forza l'estrazione
      // del nodo interno e costruisce un nuovo documento
      
//      org.apache.xmlbeans.XmlCursor cursor = contenutoXmlObj.newCursor();
//      cursor.toFirstContentToken();  //si sposta all'inizio dell'elemento anyType
//      //cursor.toFirstChild(); 
//      cursor.toNextToken(); //si sposta all'inizio dell'elemento root del contenuto
//      org.w3c.dom.Node node = cursor.newDomNode();
//      XmlObject newRoot = XmlObject.Factory.parse(node, new XmlOptions().setDocumentType(XmlObject.type));
//      //TODO Attenzione ai problemi di encoding!!!
//      newRoot.documentProperties().setEncoding("UTF-8");
//      
//      //XmlOptions opt = EnvelopeHelper.getDefaultXmlOptions();
//      XmlOptions opt = new XmlOptions();
//      
//      
//      String contenutoXmlText = EnvelopeHelper.
//      //getEncodedXmlText(contenutoXmlObj, contenutoXmlObj.documentProperties().getEncoding());
//        getEncodedXmlText(
//            newRoot, 
//            newRoot.documentProperties().getEncoding(),
//            opt);
      // Ulteriore approccio alternativo: il documento trasportato � codificato in base64
      byte[] contenutoBytesB64 = EnvelopeHelper.extractTextB64(contenutoXmlObj);
      
      // verifica che il contenuto sia effettivamente codificato in Base64
      if (!Base64.isArrayByteBase64(contenutoBytesB64)) throw new Exception(msgPrefix + "createContenutoBustaBean: Contenuto non valido: non � codificato in Base64.");
      
      String contenutoXmlText = new String(Base64.decodeBase64(contenutoBytesB64));

      
      contenutoBusta = new it.people.envelope.beans.ContenutoBusta(contenutoXmlText);
    } else if (sceltaContenutoXmlObj.isSetEccezione()) {
      Eccezione eccezioneXmlObj = sceltaContenutoXmlObj.getEccezione();
      it.people.envelope.beans.Eccezione eccezioneBean = createEccezioneBean(eccezioneXmlObj);
      contenutoBusta = new it.people.envelope.beans.ContenutoBusta(eccezioneBean);
    } else  {
      // 28-08-2006 - Eliminata eccezione per consentire il trasporto di un contenuto xml vuoto
      //throw new Exception(msgPrefix + "createContenutoBustaBean: Contenuto in XML Fragment non valido");
      //contenutoBusta = new it.people.envelope.beans.ContenutoBusta((String)null);
      contenutoBusta = null;
    }
    return contenutoBusta; 
  }
  
  public it.people.envelope.beans.CredenzialiUtenteCertificate createCredenzialiUtenteCertificateBean(
      CredenzialiUtenteCertificate credenzialiXmlObj) throws Exception {
    
    it.people.envelope.beans.CredenzialiUtenteCertificate credenzialiBean =
      new it.people.envelope.beans.CredenzialiUtenteCertificate(
          credenzialiXmlObj.getCodiceFiscale(), createAllegato(credenzialiXmlObj.getCredenzialiFirmate()));
    
    return credenzialiBean;
  }
  
  public it.people.envelope.beans.Comune createComuneBean(
      Comune comuneXmlObj) {
    
    it.people.envelope.beans.Comune comuneBean = null;
    
    if (comuneXmlObj.isSetSigladiProvinciaISTAT()) {
      comuneBean = new it.people.envelope.beans.Comune(
          comuneXmlObj.getNome(), comuneXmlObj.getSigladiProvinciaISTAT().toString());
    } else {
      comuneBean = new it.people.envelope.beans.Comune(comuneXmlObj.getNome());
    }
    
    return comuneBean;
  }
  
  public it.people.envelope.beans.RecapitoPostale createRecapitoPostaleBean(
      RecapitoPostale recapitoPostaleXmlObj) throws Exception {
    
    it.people.envelope.beans.RecapitoPostale recapitoPostaleBean = 
      new it.people.envelope.beans.RecapitoPostale();
    
    recapitoPostaleBean.setComune(createComuneBean(recapitoPostaleXmlObj.getComune()));
    
    if (recapitoPostaleXmlObj.isSetCAP()) {
      recapitoPostaleBean.setCap(recapitoPostaleXmlObj.xgetCAP().getStringValue());
    }
    if (recapitoPostaleXmlObj.isSetFrazioneComunale()) {
      recapitoPostaleBean.setFrazioneComunale(recapitoPostaleXmlObj.getFrazioneComunale());
    }
    
    it.people.envelope.beans.IndirizzoStrutturato indirizzoBean = null;
    
    String toponimo_dug = recapitoPostaleXmlObj.getToponimo().getDUG().toString();
    String toponimo_denominazione = recapitoPostaleXmlObj.getToponimo().getDenominazione();
    
    it.people.envelope.beans.Toponimo toponimo =
      new it.people.envelope.beans.Toponimo(toponimo_dug, toponimo_denominazione);
    
    if (recapitoPostaleXmlObj.isSetCivicoChilometrico()) {
      indirizzoBean = 
        new it.people.envelope.beans.IndirizzoStrutturato(
            recapitoPostaleXmlObj.getCivicoChilometrico(), toponimo);
    }
    if (recapitoPostaleXmlObj.isSetCivicoEsternoNumerico()) {
      CivicoEsternoNumerico cenXmlObj = recapitoPostaleXmlObj.getCivicoEsternoNumerico();
      int numeroCivico = cenXmlObj.getNumero().intValue();
      it.people.envelope.beans.CivicoEsternoNumerico cenBean =
        new it.people.envelope.beans.CivicoEsternoNumerico(numeroCivico);
      if (cenXmlObj.isSetLettera()) {
        cenBean.setLettera(cenXmlObj.getLettera());
      }
      if (cenXmlObj.isSetEsponente()) {
        cenBean.setEsponente(cenXmlObj.getEsponente());
      }
      indirizzoBean = 
        new it.people.envelope.beans.IndirizzoStrutturato(
            cenBean, toponimo);
    }
    recapitoPostaleBean.setIndirizzoStrutturato(indirizzoBean);
    return recapitoPostaleBean;
  }

  
  public it.people.envelope.beans.Recapito createRecapitoBean(
      Recapito recapitoXmlObj) throws Exception {
    
    String referente = recapitoXmlObj.getReferente();
    String priorita = recapitoXmlObj.getPriorita().toString();
    
    it.people.envelope.beans.Recapito recapitoBean = 
      new it.people.envelope.beans.Recapito(referente, priorita);
    
    if (recapitoXmlObj.isSetTipo()) {
      recapitoBean.setTipo(recapitoXmlObj.getTipo().toString());
    }
    
    if (recapitoXmlObj.sizeOfNotaArray()>0) {
      Nota[] notaXmlObjArray = recapitoXmlObj.getNotaArray();
      for (int i=0; i<notaXmlObjArray.length; i++) {
        Nota curNotaXmlObj = notaXmlObjArray[i];
        recapitoBean.addNota(createNota(curNotaXmlObj));
      }
    }
    
    if (recapitoXmlObj.isSetIndirizzoemail()) {
      recapitoBean.setIndirizzoEmail(recapitoXmlObj.getIndirizzoemail());
    
    } else if (recapitoXmlObj.isSetRecapitoPostale()) {
        recapitoBean.setRecapitoPostale(createRecapitoPostaleBean(recapitoXmlObj.getRecapitoPostale()));
      
    } else if (recapitoXmlObj.isSetRecapitoTelefonico()) {
      RecapitoTelefonico recapitoTelefonicoXmlObj = 
        recapitoXmlObj.getRecapitoTelefonico();
      
      it.people.envelope.beans.RecapitoTelefonico recapitoTelefonicoBean = null;
      
      String modalita = recapitoTelefonicoXmlObj.getModalita().toString();
      
      if (recapitoTelefonicoXmlObj.isSetNumeroTelefonicoEstero()) {
        recapitoTelefonicoBean =
          new it.people.envelope.beans.RecapitoTelefonico(
              modalita, 
              new it.people.envelope.beans.NumeroTelefonicoEstero(
                  recapitoTelefonicoXmlObj.getNumeroTelefonicoEstero().getPrefissoInternazionale(),
                  recapitoTelefonicoXmlObj.getNumeroTelefonicoEstero().getNumero()));
      } else if (recapitoTelefonicoXmlObj.isSetNumeroTelefonicoNazionale()) {
        recapitoTelefonicoBean =
          new it.people.envelope.beans.RecapitoTelefonico(
              modalita, 
              new it.people.envelope.beans.NumeroTelefonicoNazionale(
                  recapitoTelefonicoXmlObj.getNumeroTelefonicoNazionale().getPrefisso(),
                  recapitoTelefonicoXmlObj.getNumeroTelefonicoNazionale().getNumero()));
        
      }
      recapitoBean.setRecapitoTelefonico(recapitoTelefonicoBean);
      
    } else if (recapitoXmlObj.isSetRecapitoPostaleTestuale()) {
      recapitoBean.setRecapitoPostaleTestuale(recapitoXmlObj.getRecapitoPostaleTestuale());
    }
    
      return recapitoBean;
  }
    
  public it.people.envelope.beans.InformazioniDiValidazione createInformazionidiValidazioneBean(
      SceltaInformazioniValidazione infoValidazioneXMLObj) {
    it.people.envelope.beans.InformazioniDiValidazione infoValidazioneBean = 
      new it.people.envelope.beans.InformazioniDiValidazione();
    
    InformazioniProgetto infoProgettoXMLObj = infoValidazioneXMLObj.getInformazioniProgetto();
    it.people.envelope.beans.InformazioniProgetto infoProgettoBean =
      new it.people.envelope.beans.InformazioniProgetto(
          infoProgettoXMLObj.getProgetto(), 
          infoProgettoXMLObj.getXSDURI(),
          infoProgettoXMLObj.getBuild());
    
    infoValidazioneBean.setXsduri(infoValidazioneXMLObj.getXSDURI());
    infoValidazioneBean.setInformazioniProgetto(infoProgettoBean);
    
    return infoValidazioneBean;
  }

  
  public it.people.envelope.IRequestEnvelope createRequestEnvelopeBean(
      RichiestadiInvioDocument richiestaDocumentXmlObj) throws Exception {
    it.people.envelope.IRequestEnvelope requestEnvelopeBean =
      new it.people.envelope.RequestEnvelope();
    
    RichiestadiInvio richiestaXmlObj = richiestaDocumentXmlObj.getRichiestadiInvio();
    RichiestaC2G richiestaC2GXmlObj = richiestaXmlObj.getTipoComunicazione().getRichiestaC2G();
    InformazioniperVerificaDelega informazioniperVerificaDelegaXmlObj = richiestaXmlObj.getInformazioniperVerificaDelega();
    IdentificatorediRichiesta identificatorediRichiestaXmlObj = richiestaXmlObj.getIdentificatorediRichiesta();
    
    
    // Imposta il contenuto della busta
    requestEnvelopeBean.setContenuto(createContenutoBustaBean(richiestaXmlObj.getContenuto()));
    // Imposta il codice dell'amministrazione destinataria
    requestEnvelopeBean.setCodiceDestinatario(richiestaC2GXmlObj.getDestinatario());
    // Imposta il nome del servizio
    requestEnvelopeBean.setNomeServizio(richiestaXmlObj.getNomeServizio());
    // Imposta il contesto del servizio
    requestEnvelopeBean.setContestoServizio(informazioniperVerificaDelegaXmlObj.getContestoServizio());
    // Imposta l'identificatore di richiesta
    requestEnvelopeBean.setIdentificatoreDiRichiesta(createIdentificatorediRichiestaBean(identificatorediRichiestaXmlObj));
    // Imposta le credenziali del Mittente
    requestEnvelopeBean.setMittente(createCredenzialiUtenteCertificateBean(richiestaC2GXmlObj.getMittente()));
    
    requestEnvelopeBean.setEstremiOperatore(createEstremiOperatoreBean(richiestaXmlObj.getInformazioniProfili()));
    
    //Imposta il titolare prelevando le info in InformazioniperVerificaDeleghe
    requestEnvelopeBean.setEstremiTitolare(createEstremiTitolareBean(informazioniperVerificaDelegaXmlObj.getTitolare(), 
    		richiestaXmlObj.getInformazioniProfili()));
    
    // Revisione: 04/06/2007 - Aggiunto attributo soggettoDelegato
    //Imposta il soggetto delegato
    requestEnvelopeBean.setEstremiSoggettoDelegato(
    		createEstremiTitolareBean(
    				informazioniperVerificaDelegaXmlObj.getEstremiSoggettoDelegato(), richiestaXmlObj.getInformazioniProfili()));
    
    // Revisione: 16/11/2007 - Aggiunto attributo forceSkipCheckDelega
    // Specifica se il controllo deleghe deve essere saltato (es. nel caso di accreditamento come Rapp. Legale PG
    requestEnvelopeBean.setForceSkipCheckDelega(informazioniperVerificaDelegaXmlObj.getForceSkipCheckDeleghe());
    
    if (richiestaXmlObj.isSetInformazioniperProtocollazione()) {
      InformazioniperProtocollazione informazioniperProtocollazioneXmlObj = richiestaXmlObj.getInformazioniperProtocollazione();
      requestEnvelopeBean.setEstremiRichiedente(createEstremiRichiedenteBean(informazioniperProtocollazioneXmlObj.getRichiedente(), 
    		  richiestaXmlObj.getInformazioniProfili()));
      
      // Verifica che i titolare specificati in InformazioniperProtocollazione 
      // e in InformazioniPerVerificaDeleghe coincidano
      it.people.envelope.beans.EstremiTitolare titolaredainfoProtocollazione = 
        createEstremiTitolareBean(informazioniperProtocollazioneXmlObj.getTitolare(), richiestaXmlObj.getInformazioniProfili());
        it.people.envelope.beans.EstremiTitolare titolaredainfoVerificaDeleghe = 
          createEstremiTitolareBean(informazioniperVerificaDelegaXmlObj.getTitolare(), richiestaXmlObj.getInformazioniProfili());
        if (!titolaredainfoProtocollazione.equals(titolaredainfoVerificaDeleghe)) 
          throw new Exception("createRequestEnvelopeBean()::Exception: Titolare specificato in InformazioniperProtocollazione diverso da titolare specificato in InformazioniperVerificaDeleghe");
      if (informazioniperProtocollazioneXmlObj.sizeOfRecapitoArray()>0) {
        Recapito[] recapitoXmlObjArray = informazioniperProtocollazioneXmlObj.getRecapitoArray();
        for(int i=0; i<recapitoXmlObjArray.length; i++) {
          Recapito curRecapitoXmlObj = recapitoXmlObjArray[i];
          requestEnvelopeBean.addRecapito(createRecapitoBean(curRecapitoXmlObj));
        }
      }
      
      // Imposta il protocollo in uscita se presente
//      if (informazioniperProtocollazioneXmlObj.isSetIdentificatorediProtocollo()) {
//        requestEnvelopeBean.
//          setProtocolloInUscita(
//              createIdentificatorediProtocolloBean(informazioniperProtocollazioneXmlObj.getIdentificatorediProtocollo()));
//      }
    }
    
    if (richiestaXmlObj.isSetInformazioniValidazione()) {
      requestEnvelopeBean.
        setInformazioniDiValidazione(
            createInformazionidiValidazioneBean(richiestaXmlObj.getInformazioniValidazione()));
    }
    
    
    
     return requestEnvelopeBean;
  }

  public it.people.envelope.IResponseEnvelope createResponseEnvelopeBean(
      RispostadiInvioDocument rispostaDocumentXmlObj) throws Exception {
    
    RispostadiInvio rispostaXmlObj = rispostaDocumentXmlObj.getRispostadiInvio();
    
    it.people.envelope.beans.ContenutoBusta contenutoBean =
      createContenutoBustaBean(rispostaXmlObj.getContenuto());
    String nomeServizio = rispostaXmlObj.getNomeServizio();
    
    it.people.envelope.beans.IdentificatoreDiRichiesta identificatoreDiRichiestaBean =
      createIdentificatorediRichiestaBean(rispostaXmlObj.getIdentificatorediRichiesta());
      
    it.people.envelope.IResponseEnvelope responseEnvelopeBean = null;
    
    if (rispostaXmlObj.isSetInformazioniValidazione()) {
      responseEnvelopeBean = 
        new it.people.envelope.ResponseEnvelope(contenutoBean, nomeServizio, identificatoreDiRichiestaBean, createInformazionidiValidazioneBean(rispostaXmlObj.getInformazioniValidazione()));
    } else {
      responseEnvelopeBean = 
        new it.people.envelope.ResponseEnvelope(contenutoBean, nomeServizio, identificatoreDiRichiestaBean);
    }
    return responseEnvelopeBean;
  }

	

	

}

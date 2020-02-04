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

import org.apache.commons.codec.binary.Base64;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import it.diviana.egov.b116.infrastruttura.AnyType;
import it.diviana.egov.b116.oggettiCondivisi.CivicoEsternoNumerico;
import it.diviana.egov.b116.oggettiCondivisi.Colore;
import it.diviana.egov.b116.oggettiCondivisi.DUG;
import it.diviana.egov.b116.oggettiCondivisi.NumeroTelefonicoEstero;
import it.diviana.egov.b116.oggettiCondivisi.NumeroTelefonicoNazionale;
import it.diviana.egov.b116.oggettiCondivisi.RecapitoPostale;
import it.diviana.egov.b116.oggettiCondivisi.RecapitoTelefonico;
import it.diviana.egov.b116.oggettiCondivisi.Toponimo;
import it.diviana.egov.b116.oggettiCondivisi.Comune;
import it.diviana.egov.b116.oggettiCondivisi.SigladiProvinciaISTAT;
import it.diviana.egov.b116.oggettiCondivisi.RecapitoTelefonico.Modalita;
import it.diviana.egov.b116.oggettiCondivisi.Nota;
import it.diviana.egov.b116.oggettiCondivisi.AllegatoIncorporato;
import it.diviana.egov.b116.oggettiCondivisi.AllegatononFirmato;
import it.diviana.egov.b116.oggettiCondivisi.FileAllegato;
import it.diviana.egov.b116.cnipa.SceltaTitolare;
import it.diviana.egov.b116.oggettiCondivisi.CodiceFiscalePersonaGiuridica;
import it.diviana.egov.b116.oggettiCondivisi.DocBook;
import it.diviana.egov.b116.oggettiCondivisi.Eccezione;
import it.diviana.egov.b116.oggettiCondivisi.EstremiPersonaGiuridica;
import it.diviana.egov.b116.oggettiCondivisi.EstremidellaPersona;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatoreUnivoco;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatorediProtocollo;
import it.diviana.egov.b116.oggettiCondivisi.IdentificatorediRichiesta;
import it.diviana.egov.b116.oggettiCondivisi.AllegatoFirmatoDigitalmente;
import it.diviana.egov.b116.oggettiCondivisi.CredenzialiUtenteCertificate;
import it.diviana.egov.b116.oggettiCondivisi.RichiestaC2G;
import it.diviana.egov.b116.oggettiCondivisi.SceltaContenuto;
import it.diviana.egov.b116.oggettiCondivisi.SceltaTipoComunicazione;
import it.diviana.egov.b116.oggettiCondivisi.InformazioniProgetto;
import it.diviana.egov.b116.oggettiCondivisi.SceltaInformazioniValidazione;
//import it.diviana.egov.b116.serviziCondivisi.InformazioniperVerificaDelega;
import it.diviana.egov.b116.serviziCondivisi.RispostadiInvio;

//import it.diviana.egov.b116.serviziCondivisi.invio.RispostadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.RispostadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.InformazioniperVerificaDelega;

//import it.diviana.egov.b116.oggettiCondivisi.Recapito;
//import it.diviana.egov.b116.oggettiCondivisi.Recapito.Priorita;
//import it.diviana.egov.b116.oggettiCondivisi.Recapito.Tipo;
import it.people.b002.serviziCondivisi.envelope.Recapito;
import it.people.b002.serviziCondivisi.envelope.Recapito.Priorita;
import it.people.b002.serviziCondivisi.envelope.Recapito.Tipo;

import it.people.b002.serviziCondivisi.envelope.InformazioniperProtocollazione;
import it.people.b002.serviziCondivisi.envelope.RichiestadiInvioDocument;
import it.people.b002.serviziCondivisi.envelope.RichiestadiInvio;

import it.people.envelope.util.EnvelopeHelper;

import java.math.BigInteger;

public class EnvelopeXMLFragmentFactory_modellazioneb116_envelopeb002 {
  
  String msgPrefix = getClass().getName();
  //-----------------------------------------------------------------------------
  //   Metodi di supporto per creazione elementi di base Busta 
  // partendo da elementi di un bean IEnvelope
  
  public IdentificatorediProtocollo createIdentificatorediProtocolloXmlObj(
      it.people.envelope.beans.IdentificatoreDiProtocollo idProtocollo) {
    IdentificatorediProtocollo idProtocolloXMLObj = IdentificatorediProtocollo.Factory.newInstance();
    
    idProtocolloXMLObj.setCodiceAmministrazione(idProtocollo.getCodiceAmministrazione());
    idProtocolloXMLObj.setCodiceAOO(idProtocollo.getCodiceAOO());
    idProtocolloXMLObj.setDatadiRegistrazione(EnvelopeHelper.DateToCalendar(idProtocollo.getDataDiRegistrazione()));
    idProtocolloXMLObj.setNumerodiRegistrazione(BigInteger.valueOf(idProtocollo.getNumeroDiRegistrazione()));
    return idProtocolloXMLObj;
  }
  
  
  public IdentificatorediRichiesta createIdentificatorediRichiestaXmlObj(
      it.people.envelope.beans.IdentificatoreUnivoco idUnivoco, 
      it.people.envelope.beans.IdentificatoreDiProtocollo idProtocollo) {
    IdentificatorediRichiesta idr = IdentificatorediRichiesta.Factory.newInstance();
    
    IdentificatoreUnivoco idUnivocoXMLObj = idr.addNewIdentificatoreUnivoco();
    idUnivocoXMLObj.addNewCodiceSistema();
    
    idUnivocoXMLObj.setCodiceProgetto(idUnivoco.getCodiceProgetto());
    idUnivocoXMLObj.getCodiceSistema().setCodiceAmministrazione(idUnivoco.getCodiceSistema_codiceAmministrazione());
    idUnivocoXMLObj.getCodiceSistema().setNomeServer(idUnivoco.getCodiceSistema_nomeServer());
    idUnivocoXMLObj.setDatadiRegistrazione(EnvelopeHelper.DateToCalendar(idUnivoco.getDataDiRegistrazione()));
    idUnivocoXMLObj.setCodiceIdentificativoOperazione(idUnivoco.getCodiceIdentificativoOperazione());
    if (idProtocollo != null) {
      IdentificatorediProtocollo idProtocolloXMLObj = createIdentificatorediProtocolloXmlObj(idProtocollo);
        idr.setIdentificatorediProtocollo(idProtocolloXMLObj);
    }
    return idr;
  }
  
  public IdentificatorediRichiesta createIdentificatorediRichiestaXmlObj(
      it.people.envelope.beans.IdentificatoreDiRichiesta idRichiesta) {
    
    it.people.envelope.beans.IdentificatoreUnivoco idUnivoco = idRichiesta.getIdentificatoreUnivoco();
    it.people.envelope.beans.IdentificatoreDiProtocollo idProtocollo = idRichiesta.getIdentificatoreDiProtocollo();
    
    return createIdentificatorediRichiestaXmlObj(idUnivoco, idProtocollo);
    
  }

  public EstremidellaPersona createEstremiPersonaFisicaXmlObj(
      it.people.envelope.beans.EstremiPersonaFisica estremiPersonaFisica) {
    
    EstremidellaPersona epfXMLObj = EstremidellaPersona.Factory.newInstance();
    
    epfXMLObj.setCodiceFiscale(estremiPersonaFisica.getCodiceFiscale());
    epfXMLObj.setNome(estremiPersonaFisica.getNome());
    epfXMLObj.setCognome(estremiPersonaFisica.getCognome());
    
    return epfXMLObj;
  }
  
  public EstremiPersonaGiuridica createEstremiPersonaGiuridicaXmlObj(
      it.people.envelope.beans.EstremiPersonaGiuridica estremiPersonaGiuridica) {
    EstremiPersonaGiuridica epgXMLObj = EstremiPersonaGiuridica.Factory.newInstance();
    
    CodiceFiscalePersonaGiuridica cfpgXMLObj = CodiceFiscalePersonaGiuridica.Factory.newInstance();
    cfpgXMLObj.setStringValue(estremiPersonaGiuridica.getCodiceFiscalePG());
    
    epgXMLObj.xsetCodiceFiscalePersonaGiuridica(cfpgXMLObj);
    
    epgXMLObj.setDenominazioneoRagioneSociale(estremiPersonaGiuridica.getDenominazioneoRagioneSocialePG());
    
    return epgXMLObj;
  }
  
  public SceltaTitolare createSceltaTitolareXmlObj(
      it.people.envelope.beans.EstremiTitolare estremiTitolareObj) {
    
    SceltaTitolare estremiTitolareXMLObj = SceltaTitolare.Factory.newInstance();
    
    if (estremiTitolareObj.isEstremiPersonaFisica()) {
//      EstremidellaPersona estremiPersonaXMLObj = estremiTitolareXMLObj.addNewEstremidellaPersona();
//      estremiPersonaXMLObj.setCodiceFiscale(estremiTitolareObj.getEstremiPersonaFisica().getCodiceFiscale());
//      estremiPersonaXMLObj.setNome(estremiTitolareObj.getEstremiPersonaFisica().getNome());
//      estremiPersonaXMLObj.setCognome(estremiTitolareObj.getEstremiPersonaFisica().getCognome());
      
      estremiTitolareXMLObj.setEstremidellaPersona(createEstremiPersonaFisicaXmlObj(estremiTitolareObj.getEstremiPersonaFisica()));
    } else {  // Persona Giuridica
      estremiTitolareXMLObj.setEstremiPersonaGiuridica(createEstremiPersonaGiuridicaXmlObj(estremiTitolareObj.getEstremiPersonaGiuridica()));
    }
    return estremiTitolareXMLObj;
    
  }

  public XmlObject createAllegatoXmlObj(
      it.people.envelope.beans.Allegato allegato) throws Exception {
    XmlObject resultXMLObj = null;
    
    if (allegato.isFileAllegato()) {
      resultXMLObj = FileAllegato.Factory.newInstance();
      ((FileAllegato)resultXMLObj).setDescrizione(allegato.getDescrizione());
      ((FileAllegato)resultXMLObj).setURI(allegato.getURI());
      
    } else if (allegato.isAllegatoIncorporato()) {
      resultXMLObj = AllegatoIncorporato.Factory.newInstance();
      ((AllegatoIncorporato)resultXMLObj).setDescrizione(allegato.getDescrizione());
      ((AllegatoIncorporato)resultXMLObj).setContenuto(allegato.getContenuto());
      
    } else if (allegato.isAllegatoFirmato()) {
      resultXMLObj = AllegatoFirmatoDigitalmente.Factory.newInstance();
      ((AllegatoFirmatoDigitalmente)resultXMLObj).setNomeFile(allegato.getNomeFile());
      ((AllegatoFirmatoDigitalmente)resultXMLObj).setDescrizione(allegato.getDescrizione());
      ((AllegatoFirmatoDigitalmente)resultXMLObj).setContenuto(allegato.getContenuto());
      
    } else if (allegato.isAllegatoNonfirmato()) {
      resultXMLObj = AllegatononFirmato.Factory.newInstance();
      ((AllegatononFirmato)resultXMLObj).setNomeFile(allegato.getNomeFile());
      ((AllegatononFirmato)resultXMLObj).setDescrizione(allegato.getDescrizione());
      ((AllegatononFirmato)resultXMLObj).setContenuto(allegato.getContenuto());
      
    } else throw new Exception(msgPrefix + "::createAllegatoXMLObj:: Tipo allegato non valido: " + allegato.getTipoAllegato());
    
    return resultXMLObj;
  }

  public Nota createNotaXmlObj(
      it.people.envelope.beans.Nota nota) throws Exception {
    Nota notaXMLObj = Nota.Factory.newInstance();
    
    if (nota.isSetDescrizioneArray()) {
      notaXMLObj.setDescrizioneArray(nota.getDescrizioneArray());
    }
    
    if (nota.isSetAllegato()) {
      it.people.envelope.beans.Allegato allegatoNota = nota.getAllegato();
      if (allegatoNota.isAllegatoIncorporato()) {
        notaXMLObj.setAllegatoIncorporato((AllegatoIncorporato)createAllegatoXmlObj(allegatoNota));
      } else  if (allegatoNota.isAllegatoFirmato()) {
        notaXMLObj.setAllegatoFirmatoDigitalmente((AllegatoFirmatoDigitalmente)createAllegatoXmlObj(allegatoNota));
      } else  if (allegatoNota.isAllegatoNonfirmato()) {
        notaXMLObj.setAllegatononFirmato((AllegatononFirmato)createAllegatoXmlObj(allegatoNota));
      } else  if (allegatoNota.isFileAllegato()) {
        notaXMLObj.setFileAllegato((FileAllegato)createAllegatoXmlObj(allegatoNota));
      } else throw new Exception(msgPrefix + "::createNotaXMLObj:: Tipo allegato non valido: " + allegatoNota.getTipoAllegato());
    }
    
    if (nota.isSetURIDocBook()) {
      DocBook docBook = DocBook.Factory.newInstance();
      docBook.setURI(nota.getURI_DocBook());
      notaXMLObj.setDocBook(docBook);
    }
    return notaXMLObj;
  }
  
  public Nota[] createNotaArrayXmlObj(
      it.people.envelope.beans.Nota[] note) throws Exception{
    Nota[] noteXMLObj = null;  
    if (note==null) return null;
    else {
      noteXMLObj = new Nota[note.length];
      for(int i=0; i<note.length; i++) {
        noteXMLObj[i] = createNotaXmlObj(note[i]);
      }
    }
    return noteXMLObj;
  }
  
  public Comune createComuneXmlObj(
      it.people.envelope.beans.Comune comune) {
    
    Comune comuneXMLObj = Comune.Factory.newInstance();
    comuneXMLObj.setNome(comune.getNome());
    if (comune.isSetSiglaDiProvinciaISTAT()) {
      comuneXMLObj.setSigladiProvinciaISTAT(SigladiProvinciaISTAT.Enum.forString(comune.getSiglaDiProvinciaISTAT()));
    }
    
    return comuneXMLObj;
  }

  public RecapitoPostale createRecapitoPostaleXmlObj(
      it.people.envelope.beans.RecapitoPostale recapitoPostale)  throws Exception {
    if (recapitoPostale==null) 
      throw 
      new Exception(msgPrefix + "::createRecapitoPostaleXMLObj:: Recapito non valido: " + recapitoPostale);
    RecapitoPostale recapitoPostaleXMLObj = RecapitoPostale.Factory.newInstance();
    
    recapitoPostaleXMLObj.setCAP(new BigInteger(recapitoPostale.getCap()));
    recapitoPostaleXMLObj.setFrazioneComunale(recapitoPostale.getFrazioneComunale());
    recapitoPostaleXMLObj.setComune(createComuneXmlObj(recapitoPostale.getComune()));
    
    it.people.envelope.beans.IndirizzoStrutturato indirizzoObj = 
      recapitoPostale.getIndirizzoStrutturato();
    if (indirizzoObj.isTipoCivicoEsternoChilometrico()) {
      
      recapitoPostaleXMLObj.setCivicoChilometrico(indirizzoObj.getCivicoEsternoChilometrico());
      
    } else if (indirizzoObj.isTipoCivicoEsternoNumerico()) {
      CivicoEsternoNumerico cenXMLObj = CivicoEsternoNumerico.Factory.newInstance();
      it.people.envelope.beans.CivicoEsternoNumerico cenObj = 
        indirizzoObj.getCivicoEsternoNumerico();
      cenXMLObj.setColore(Colore.Enum.forString(cenObj.getColore()));
      cenXMLObj.setNumero(BigInteger.valueOf(cenObj.getNumero()));
      if (cenObj.isLetteraOEsponente()) {
        if (cenObj.isLettera()) {
          cenXMLObj.setLettera(cenObj.getLettera());
        } else  { // esponente 
          cenXMLObj.setEsponente(cenObj.getEsponente());
        }
      }
      
      recapitoPostaleXMLObj.setCivicoEsternoNumerico(cenXMLObj);
      
    } else 
      throw new Exception(msgPrefix + "::createRecapitoPostaleXMLObj:: Recapito non valido: " + recapitoPostale);
    
    Toponimo toponimoXMLObj = Toponimo.Factory.newInstance();
    it.people.envelope.beans.Toponimo toponimoObj = indirizzoObj.getToponimo();
    
    toponimoXMLObj.setDenominazione(toponimoObj.getDenominazione());
    toponimoXMLObj.setDUG(DUG.Enum.forString(toponimoObj.getDug()));
    
    recapitoPostaleXMLObj.setToponimo(toponimoXMLObj);
    
    return recapitoPostaleXMLObj;
    
  }
  
//  public IndirizzoStrutturatoCompleto createIndirizzoStrutturatoCompletoXMLObj(
//      it.people.envelope.beans.IndirizzoStrutturatoCompleto indirizzoStrutturatoCompleto) {
//    
//    IndirizzoStrutturatoCompleto indirizzoStrutturatoCompletoXMLObj = 
//      IndirizzoStrutturatoCompleto.Factory.newInstance();
//    
//    indirizzoStrutturatoCompletoXMLObj.
//    
//    return indirizzoStrutturatoCompletoXMLObj;
//    
//  }

  
  public RecapitoTelefonico createRecapitoTelefonicoXmlObj(
      it.people.envelope.beans.RecapitoTelefonico recapitoTelefonicoObj)  throws Exception {
    
    RecapitoTelefonico recTelXMLObj = RecapitoTelefonico.Factory.newInstance();
    
    recTelXMLObj.setModalita(Modalita.Enum.forString(recapitoTelefonicoObj.getModalita()));
    if (recapitoTelefonicoObj.isNumeroTelefonicoEstero()) {
      NumeroTelefonicoEstero nteXMLObj = NumeroTelefonicoEstero.Factory.newInstance();
      nteXMLObj.setNumero(recapitoTelefonicoObj.getNumeroTelefonicoEstero().getNumero());
      nteXMLObj.setPrefissoInternazionale(recapitoTelefonicoObj.getNumeroTelefonicoEstero().getPrefissoInternazionale());

      recTelXMLObj.setNumeroTelefonicoEstero(nteXMLObj);
      
    } else if (recapitoTelefonicoObj.isNumeroTelefonicoNazionale()) {
      
      NumeroTelefonicoNazionale ntnXMLObj = NumeroTelefonicoNazionale.Factory.newInstance();
      ntnXMLObj.setNumero(recapitoTelefonicoObj.getNumeroTelefonicoNazionale().getNumero());
      ntnXMLObj.setPrefisso(recapitoTelefonicoObj.getNumeroTelefonicoNazionale().getPrefisso());
      
      recTelXMLObj.setNumeroTelefonicoNazionale(ntnXMLObj);
      
      
    } else 
      throw new Exception(msgPrefix + "::createRecapitoTelefonicoXMLObj:: Recapito Telefonico non valido: " + recapitoTelefonicoObj);
    
    return recTelXMLObj;
  }

  public Recapito createRecapitoXmlObj(
      it.people.envelope.beans.Recapito recapito) throws Exception {
    Recapito recapitoXMLObj = Recapito.Factory.newInstance();
    
    Nota[] note = createNotaArrayXmlObj(recapito.getNotaArray());
    
    if (note != null) recapitoXMLObj.setNotaArray(note);
    recapitoXMLObj.setPriorita(Priorita.Enum.forString(recapito.getPriorita()));
    recapitoXMLObj.setReferente(recapito.getReferente());
    recapitoXMLObj.setTipo(Tipo.Enum.forString(recapito.getTipo()));
    
    if (recapito.isRecapitoIndirizzoEmail()) {
      recapitoXMLObj.setIndirizzoemail(recapito.getIndirizzoEmail());
      
    } else if (recapito.isRecapitoPostaleTestuale()) {
      recapitoXMLObj.setRecapitoPostaleTestuale(recapito.getRecapitoPostaleTestuale());
      
    } else if (recapito.isRecapitoPostale()) {
      recapitoXMLObj.setRecapitoPostale(createRecapitoPostaleXmlObj(recapito.getRecapitoPostale()));
      
    } else if (recapito.isRecapitoTelefonico()) {
      recapitoXMLObj.setRecapitoTelefonico(createRecapitoTelefonicoXmlObj(recapito.getRecapitoTelefonico()));
      
    } else {
      throw new Exception(msgPrefix + "::createRecapitoXMLObj:: Recapito non valido: " + recapito);
    }
    
    return recapitoXMLObj;
  }
  
  public Recapito[] createRecapitoArrayXmlObj(
      it.people.envelope.beans.Recapito[] recapitoArray) throws Exception {
    Recapito[] recapitoArrayXMLObj = null;  
    if (recapitoArray==null) return null;
    else {
      recapitoArrayXMLObj = new Recapito[recapitoArray.length];
      for(int i=0; i<recapitoArray.length; i++) {
        recapitoArrayXMLObj[i] = createRecapitoXmlObj(recapitoArray[i]);
      }
    }
    return recapitoArrayXMLObj;

  }
  
  public Eccezione createEccezioneXmlObj(
      it.people.envelope.beans.Eccezione eccezione) throws Exception {
    Eccezione eccezioneXmlObj = Eccezione.Factory.newInstance();
    
    eccezioneXmlObj.
      setIdentificatorediRichiesta(
          createIdentificatorediRichiestaXmlObj(eccezione.getIdentificatoreDiRichiesta()));
    
    eccezioneXmlObj.setTipo(Eccezione.Tipo.Enum.forString(eccezione.getTipo()));
    eccezioneXmlObj.setCodice(BigInteger.valueOf(eccezione.getCodice()));
    if (eccezione.getNotaArray() != null) {
      eccezioneXmlObj.setNotaArray(createNotaArrayXmlObj(eccezione.getNotaArray()));
    }
    return eccezioneXmlObj;
   
  }
  
  public SceltaContenuto createContenutoXmlObj(
      it.people.envelope.beans.ContenutoBusta contenutoBusta, XmlOptions opt) throws Exception {
    // Crea un elemento SceltaContenuto vuoto
    SceltaContenuto scXmlObj = SceltaContenuto.Factory.newInstance();
    
    if (contenutoBusta != null) {
      if (contenutoBusta.isContenutoXml()) {
        //    28-08-2006 - Check su contenuto xml null per consentire il trasporto di un contenuto vuoto senza sollevare eccezioni.
        //if (contenutoBusta.getContenutoXML() != null) {
        
          // Nota: non funziona con Factory.newInstance, ma solo con la add, 
          // altrimenti solleva una disconnectedException
          AnyType anyContent = scXmlObj.addNewAnyType();
          
          
          // Sostituito da codice successivo che codifica il contenuto in base64
          //XmlObject contentXmlObj = XmlObject.Factory.parse(contenutoBusta.getContenutoXML(), opt);
          //anyContent.set(contentXmlObj);
          
          byte[] contenutoXmlB64 = Base64.encodeBase64Chunked(contenutoBusta.getContenutoXML().getBytes());
          EnvelopeHelper.insertTextB64(anyContent, contenutoXmlB64);
          
        //}

        
       
      } else if (contenutoBusta.isEccezione()) {
        scXmlObj.setEccezione(createEccezioneXmlObj(contenutoBusta.getEccezione()));
      } else {
        throw new Exception(msgPrefix + "::createContenutoXMLObj:: Contenuto non valido: " + contenutoBusta);
      }
      
    }

    
    return scXmlObj;
  }
  
  public SceltaContenuto createContenutoXmlObj(
      it.people.envelope.beans.ContenutoBusta contenutoBusta) throws Exception {
    return createContenutoXmlObj(contenutoBusta, EnvelopeHelper.getDefaultXmlOptions());
  }


  public SceltaInformazioniValidazione createInformazioniValidazioneXmlObj(
      it.people.envelope.beans.InformazioniDiValidazione infoValidazioneObj) {
    SceltaInformazioniValidazione infoValidazioneXMLObj = SceltaInformazioniValidazione.Factory.newInstance();
    
    InformazioniProgetto infoProgettoXMLObj = InformazioniProgetto.Factory.newInstance();
    infoProgettoXMLObj.setXSDURI(infoValidazioneObj.getInformazioniProgetto().getXsduri());
    infoProgettoXMLObj.setProgetto(infoValidazioneObj.getInformazioniProgetto().getProgetto());
    infoProgettoXMLObj.setBuild(infoValidazioneObj.getInformazioniProgetto().getBuild());
    
    infoValidazioneXMLObj.setXSDURI(infoValidazioneObj.getXsduri());
    infoValidazioneXMLObj.setInformazioniProgetto(infoProgettoXMLObj);
    
    return infoValidazioneXMLObj;
  }

  public SceltaTipoComunicazione createTipoComunicazioneXmlObj(
      it.people.envelope.beans.CredenzialiUtenteCertificate credenzialiMittente, String codiceDestinatario) {
    SceltaTipoComunicazione tipoComunicazione = SceltaTipoComunicazione.Factory.newInstance();
    
    RichiestaC2G richiestaC2G = tipoComunicazione.addNewRichiestaC2G();
    CredenzialiUtenteCertificate mittente = richiestaC2G.addNewMittente();
    AllegatoFirmatoDigitalmente credFirmateMittente = mittente.addNewCredenzialiFirmate();
    
    richiestaC2G.setDestinatario(codiceDestinatario);
    mittente.setCodiceFiscale(credenzialiMittente.getCodiceFiscale());
    credFirmateMittente.setContenuto(credenzialiMittente.getCredenzialiFirmate().getContenuto());
//    credFirmateMittente.setDescrizione("Credenziali firmate Mittente");
//    credFirmateMittente.setNomeFile("Credenzialifirmate." + credenzialiMittente.getCodiceFiscale());
    credFirmateMittente.setDescrizione(credenzialiMittente.getCredenzialiFirmate().getDescrizione());
    credFirmateMittente.setNomeFile(credenzialiMittente.getCredenzialiFirmate().getNomeFile());
  
    return tipoComunicazione;
  }
  
  /**
 * @param contestoServizio
 * @param titolare
 * @param soggettoDelegato
 * @return
 */
  public InformazioniperVerificaDelega createInformazioniperVerificaDelegaXmlObj(
      String contestoServizio, 
      it.people.envelope.beans.EstremiTitolare titolare,
      it.people.envelope.beans.EstremiTitolare soggettoDelegato,
      boolean forceSkipCheckDelega) {
    InformazioniperVerificaDelega ivdXmlObj = InformazioniperVerificaDelega.Factory.newInstance();
    
    ivdXmlObj.setTitolare(createSceltaTitolareXmlObj(titolare));
    ivdXmlObj.setContestoServizio(contestoServizio);
    ivdXmlObj.setEstremiSoggettoDelegato(createSceltaTitolareXmlObj(soggettoDelegato));
    ivdXmlObj.setForceSkipCheckDeleghe(forceSkipCheckDelega);
    return ivdXmlObj;
  }
  
  public InformazioniperProtocollazione createInformazioniperProtocollazioneXmlObj(
      it.people.envelope.beans.EstremiPersonaFisica richiedente, 
      it.people.envelope.beans.EstremiTitolare titolare,
      it.people.envelope.beans.Recapito[] recapitoArray) throws Exception {
    InformazioniperProtocollazione ippXmlObj = InformazioniperProtocollazione.Factory.newInstance();
    
    ippXmlObj.setRichiedente(createEstremiPersonaFisicaXmlObj(richiedente));
    ippXmlObj.setTitolare(createSceltaTitolareXmlObj(titolare));
    ippXmlObj.setRecapitoArray(createRecapitoArrayXmlObj(recapitoArray));
    return ippXmlObj;
  }
  
//  public InformazioniperProtocollazione createInformazioniperProtocollazioneXmlObj(
//      it.people.envelope.beans.EstremiPersonaFisica richiedente, 
//      it.people.envelope.beans.EstremiTitolare titolare,
//      it.people.envelope.beans.Recapito[] recapitoArray,
//      it.people.envelope.beans.IdentificatoreDiProtocollo protocolloInUscita) throws Exception {
//    InformazioniperProtocollazione ippXmlObj =
//      createInformazioniperProtocollazioneXmlObj(richiedente, titolare, recapitoArray);
//    
//    if (protocolloInUscita != null) {
//      ippXmlObj.setIdentificatorediProtocollo(createIdentificatorediProtocolloXmlObj(protocolloInUscita));
//    }
//    
//    return ippXmlObj;
//  }
  
  public RichiestadiInvioDocument createRichiestadiInvioDocumentXmlObj(
      IRequestEnvelope requestEnvelope) throws Exception {
    RichiestadiInvioDocument richiestadocXmlObj = RichiestadiInvioDocument.Factory.newInstance();
    RichiestadiInvio richiestaXmlObj = richiestadocXmlObj.addNewRichiestadiInvio();
    
    richiestaXmlObj.setContenuto(createContenutoXmlObj(requestEnvelope.getContenuto()));
    richiestaXmlObj.setNomeServizio(requestEnvelope.getNomeServizio());
    
    if (requestEnvelope.getInformazioniDiValidazione()!= null) {
      richiestaXmlObj.
        setInformazioniValidazione(
            createInformazioniValidazioneXmlObj(requestEnvelope.getInformazioniDiValidazione()));
    }
    richiestaXmlObj.setIdentificatorediRichiesta(
        createIdentificatorediRichiestaXmlObj(requestEnvelope.getIdentificatoreDiRichiesta()));
    
    it.people.envelope.beans.CredenzialiUtenteCertificate credenzialiMittente = 
      requestEnvelope.getMittente();
    String codiceAmministrazioneDestinataria = requestEnvelope.getCodiceDestinatario();
    
    richiestaXmlObj.
      setTipoComunicazione(createTipoComunicazioneXmlObj(credenzialiMittente, codiceAmministrazioneDestinataria));
    
//    richiestaXmlObj.
//      setInformazioniperVerificaDelega(
//          createInformazioniperVerificaDelegaXmlObj(requestEnvelope.getContestoServizio(), requestEnvelope.getEstremiTitolare()));
    // Revisione: 04/06/2007: Aggiunto soggetto delegato
    richiestaXmlObj.
    setInformazioniperVerificaDelega(
        createInformazioniperVerificaDelegaXmlObj(
        		requestEnvelope.getContestoServizio(), 
        		requestEnvelope.getEstremiTitolare(),
        		requestEnvelope.getEstremiSoggettoDelegato(),
        		requestEnvelope.getForceSkipCheckDelega()));
    
    it.people.envelope.beans.EstremiOperatore operatore = 
    	      requestEnvelope.getEstremiOperatore();
    it.people.envelope.beans.EstremiRichiedente richiedente = 
      requestEnvelope.getEstremiRichiedente();
    it.people.envelope.beans.EstremiTitolare titolare = 
      requestEnvelope.getEstremiTitolare();
    it.people.envelope.beans.Recapito[] recapitoArray =
      requestEnvelope.getRecapiti();
    
    //Per ora imposta sempre le informazioni per protocollazione 
    // (nonostante siano indicate come opzionali nello schema xml)
    richiestaXmlObj.setInformazioniperProtocollazione(
        createInformazioniperProtocollazioneXmlObj(richiedente, titolare, recapitoArray));
    
    //Imposta il protocollo in uscita se definito
//    if (requestEnvelope.getProtocolloInUscita() != null) {
//      richiestaXmlObj.getInformazioniperProtocollazione().
//        setIdentificatorediProtocollo(
//            createIdentificatorediProtocolloXmlObj(requestEnvelope.getProtocolloInUscita()));
//    }

    richiestaXmlObj.setInformazioniProfili(createInformazioniProfiliXmlObj(operatore, richiedente, titolare));
    
    return richiestadocXmlObj;

  }
  
  public RispostadiInvioDocument createRispostadiInvioDocumentXmlObj(IResponseEnvelope responseEnvelope) throws Exception {
    RispostadiInvioDocument rispostadocXmlObj = RispostadiInvioDocument.Factory.newInstance();
    RispostadiInvio rispostaXmlObj = rispostadocXmlObj.addNewRispostadiInvio();
    
    it.people.envelope.beans.ContenutoBusta contenuto =
      responseEnvelope.getContenuto();
    String nomeServizio = responseEnvelope.getNomeServizio();
    it.people.envelope.beans.IdentificatoreDiRichiesta identificatoreRichiesta =
      responseEnvelope.getIdentificatoreDiRichiesta();
    it.people.envelope.beans.InformazioniDiValidazione infoValidazione =
      responseEnvelope.getInformazioniDiValidazione();
    
    rispostaXmlObj.setContenuto(createContenutoXmlObj(contenuto));
    rispostaXmlObj.setNomeServizio(nomeServizio);
    rispostaXmlObj.setIdentificatorediRichiesta(createIdentificatorediRichiestaXmlObj(identificatoreRichiesta));
    
    if (infoValidazione!= null) {
      rispostaXmlObj.setInformazioniValidazione(createInformazioniValidazioneXmlObj(infoValidazione));
    }
    return rispostadocXmlObj;

  }

  public it.people.b002.serviziCondivisi.envelope.InformazioniProfili createInformazioniProfiliXmlObj(
		  it.people.envelope.beans.EstremiPersonaFisica operatore,
		  it.people.envelope.beans.EstremiPersonaFisica richiedente, 
	      it.people.envelope.beans.EstremiTitolare titolare) throws Exception {
	  
	  it.people.b002.serviziCondivisi.envelope.InformazioniProfili isXmlObj = it.people.b002.serviziCondivisi.envelope.InformazioniProfili.Factory.newInstance();

	  if (operatore != null) {
		  it.people.b002.serviziCondivisi.envelope.SoggettoPersonaFisica sopXmlObj = isXmlObj.addNewOperatore();
		  sopXmlObj.setCognome(operatore.getCognome());
		  sopXmlObj.setNome(operatore.getNome());
		  sopXmlObj.setCodiceFiscale(operatore.getCodiceFiscale());
		  sopXmlObj.setEMail(operatore.getEMail());
	  }
	  
	  it.people.b002.serviziCondivisi.envelope.SoggettoPersonaFisica spfXmlObj = isXmlObj.addNewRichiedente();
	  spfXmlObj.setCognome(richiedente.getCognome());
	  spfXmlObj.setNome(richiedente.getNome());
	  spfXmlObj.setCodiceFiscale(richiedente.getCodiceFiscale());
	  spfXmlObj.setEMail(richiedente.getEMail());
	  	  
	  it.people.b002.serviziCondivisi.envelope.InformazioniProfili.Titolare titolareXmlObj = isXmlObj.addNewTitolare();
	  
	  if (titolare.isEstremiPersonaFisica()) {
		  
		  it.people.b002.serviziCondivisi.envelope.SoggettoPersonaFisica tspfXmlObj = titolareXmlObj.addNewPersonaFisica();
		  tspfXmlObj.setCognome(titolare.getEstremiPersonaFisica().getCognome());
		  tspfXmlObj.setNome(titolare.getEstremiPersonaFisica().getNome());
		  tspfXmlObj.setCodiceFiscale(titolare.getEstremiPersonaFisica().getCodiceFiscale());
		  tspfXmlObj.setEMail(titolare.getEstremiPersonaFisica().getEMail());
		  
	  } else {

		  it.people.b002.serviziCondivisi.envelope.SoggettoPersonaGiuridica tspgXmlObj = titolareXmlObj.addNewPersonaGiuridica();
		  tspgXmlObj.setDenominazioneoRagioneSociale(titolare.getEstremiPersonaGiuridica().getDenominazioneoRagioneSocialePG());
		  tspgXmlObj.setCodiceFiscalePersonaGiuridica(titolare.getEstremiPersonaGiuridica().getCodiceFiscalePG());
		  tspgXmlObj.setEMail(titolare.getEstremiPersonaGiuridica().getEMail());
		  
		  if (titolare.getEstremiPersonaGiuridica().getRappresentanteLegale() != null) {
			  it.people.b002.serviziCondivisi.envelope.SoggettoPersonaFisica tspgRlXmlObj = tspgXmlObj.addNewRappresentanteLegale();
			  tspgRlXmlObj.setCognome(titolare.getEstremiPersonaGiuridica().getRappresentanteLegale().getCognome());
			  tspgRlXmlObj.setNome(titolare.getEstremiPersonaGiuridica().getRappresentanteLegale().getNome());
			  tspgRlXmlObj.setCodiceFiscale(titolare.getEstremiPersonaGiuridica().getRappresentanteLegale().getCodiceFiscale());
			  tspgRlXmlObj.setEMail(titolare.getEstremiPersonaGiuridica().getRappresentanteLegale().getEMail());
		  }
		  
	  }

	    return isXmlObj;
	    
	  }
  

  
}

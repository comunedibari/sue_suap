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

import it.people.envelope.beans.IdentificatoreDiProtocollo;
import it.people.envelope.beans.IdentificatoreUnivoco;

import java.util.Date;

/**
 * Decorator per la classe IdentificatoreUnivoco.<br>
 * Aggiunge la possibilit� di creazione di un Identificatore di Protocollo partendo
 * dai valori degli attributi dell'identificatore univoco. 
 *
 */
public class IdentificatoreUnivocoDecorator extends IdentificatoreUnivoco {
  
  /**
   * Costruisce un IdentificatoreUnivocoDecorator con il codiceOperazione, codiceProgetto, 
   * codiceAmministrazione, nomeServer e dataRegistrazione specificati  
   * @param codiceOperazione
   * @param codiceProgetto
   * @param codiceAmministrazione
   * @param nomeServer
   * @param dataRegistrazione
   */
  public IdentificatoreUnivocoDecorator(
      String codiceOperazione, 
      String codiceProgetto, 
      String codiceAmministrazione, 
      String nomeServer, 
      Date dataRegistrazione) {
    super(codiceOperazione, codiceProgetto, codiceAmministrazione, nomeServer, dataRegistrazione);
  }

  /**
   * Costruisce un IdentificatoreUnivocoDecorator partendo da un IdentificatoreUnivoco esistente
   * @param idUnivoco IdentificatoreUnivoco da decorare
   */
  public IdentificatoreUnivocoDecorator(IdentificatoreUnivoco idUnivoco) {
    super(idUnivoco.getCodiceIdentificativoOperazione(),
          idUnivoco.getCodiceProgetto(),
          idUnivoco.getCodiceSistema_codiceAmministrazione(),
          idUnivoco.getCodiceSistema_nomeServer(),
          idUnivoco.getDataDiRegistrazione());
  }
  
  /**
   * Crea un bean IdentificatoreDiProtocollo popolato con il contenuto dell'IdentificatoreUnivoco decorato dall'oggetto IdentificatoreUnivocoDecorator.<br>
   * Gli attributi dell'identificatore di protocollo sono valorizzati come segue:<br>
   * <ul>
   *   <li>Codice amministrazione e Codice AOO inizializzati al valore restituito da getCodiceSistema_codiceAmministrazione() (definito nella classe IdentificatoreUnivoco)</li>
   *   <li>Data registrazione inizializzato con il valore restituito da  getDataDiRegistrazione()(definito nella classe IdentificatoreUnivoco)</li>
   *   <li>Identificatore di protocollo inizializzato con il valore restituito dal metodo getProgressivoNumericoOperazione()(definito nella classe IdentificatoreUnivoco)</li> 
   * </ul> 
   * @return IdentificatoreDiProtocollo popolato con il contenuto dell'IdentificatoreUnivoco
   * @throws Exception
   * @see it.people.envelope.beans.IdentificatoreDiProtocollo
   */
  public IdentificatoreDiProtocollo toIdentificatoreDiProtocollo() {
    String proto_codiceAmministrazione = getCodiceSistema_codiceAmministrazione();
    //String proto_codiceAOO = getCodiceSistema_codiceAmministrazione();
    String proto_codiceAOO = getCodiceAOOFromCodiceIdentificativoOperazione(codiceIdentificativoOperazione);
    if(proto_codiceAOO == null){
    	proto_codiceAOO = getCodiceSistema_codiceAmministrazione(); // FIX per retrocompatibilit� con formato identificatore People-1.2.3
    }
    Date proto_dataRegistrazione = getDataDiRegistrazione();
    long proto_idProtocollo = 0;
    try {
      //proto_idProtocollo = Long.parseLong(getProgressivoNumericoOperazione());
      proto_idProtocollo = getProgressivoNumericoOperazione();
   } catch (NumberFormatException nfe) {
     //ignore
   }
    IdentificatoreDiProtocollo idProtocollo = 
      new IdentificatoreDiProtocollo(
          proto_codiceAmministrazione, 
          proto_codiceAOO, 
          proto_idProtocollo, 
          proto_dataRegistrazione);
    return idProtocollo;
  }
  
  /**
   * Estrae il progressivo numerico dalla stringa contenente il codice identificativo operazione completo
   * e lo restituisce al chiamante. <br>
   * La struttura di riferimento del codice identificativo operazione � la seguente:<br>
   *  <code> 'codice fiscale-identificatore univoco alfanumerico-progressivonumerico' </code><br>
   * Il progressivo numerico viene estratto dal fondo della stringa (tutti i caratteri dopo l'ultimo '-').<br>
   * Se non � presente un identificativo numerico nella porzione di stringa selezionata 
   * viene restituito 0.<br>
   * Non viene effettuata alcuna verifica sulla lunghezza sul progressivo numerico
   * (ad esempio se il progressivo ha una lunghezza maggiore di 8 caratteri  viene restituito inalterato).<br> 
   * @return long contenente il progressivo numerico estratto dal codice identificativo operazione
   */
  public long getProgressivoNumericoOperazione() {
//    String zeroPrefix = "0000000";
    int posProgressivoNumerico = codiceIdentificativoOperazione.lastIndexOf("-");
    
    String progNumericoStringInitial = 
      codiceIdentificativoOperazione.substring(posProgressivoNumerico+1);
    
    long progNumericoLong = 0;
    
    try {
      progNumericoLong = Long.parseLong(progNumericoStringInitial);
   } catch (NumberFormatException nfe) {
     //return zeroPrefix;
     return (long)0;
   }
//    String progNumericoStringFinal = 
//      zeroPrefix +  new Long(Long.parseLong(progNumericoStringInitial)).toString();
//    return progNumericoStringFinal.substring(progNumericoStringFinal.length()-zeroPrefix.length());
    return progNumericoLong;
  }
  
  public String getCodiceAOOFromCodiceIdentificativoOperazione(String codiceOperazione) {
    int firstSeparatorPos = codiceOperazione.indexOf('-');
    int lastSeparatorPos = codiceOperazione.lastIndexOf('-');
    if(firstSeparatorPos >= lastSeparatorPos){
    	return null;  // FIX per retrocompatibilit� con People-1.2.3
    } else {
    	return codiceOperazione.substring(firstSeparatorPos+1, lastSeparatorPos); 
    }
  }
  
  
  
  public static void main(String[] args) throws Exception {
    IdentificatoreUnivoco idUnivoco = 
        new IdentificatoreUnivoco(
            "PNBWER56A45F205N-AOO-001-1234567", "PRJ001", "feF205", "SERVER001", new Date());
    
    IdentificatoreUnivocoDecorator idUnivocoDecorator = 
      new IdentificatoreUnivocoDecorator(idUnivoco);
    
    System.out.println("Identificatore univoco: " + idUnivoco);
    
    IdentificatoreDiProtocollo idProto = idUnivocoDecorator.toIdentificatoreDiProtocollo();
    
    System.out.println("Identificatore di protocollo estratto da identificatore univoco: "  
        + idProto);
    
    System.out.println("Numero di protocollo da identificatore univoco (zero padded string): " + idProto.getNumeroDiRegistrazioneString());
    System.out.println("Numero di protocolo valido (in base a specifiche CNIPA - 7 cifre numeriche): " + idProto.isValidNumeroRegistrazione());

  }


}

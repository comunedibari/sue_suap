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
package it.people.envelope.beans;

import java.util.Date;

public final class IdentificatoreDiProtocollo {
    
    private String codiceAOO;
    private String codiceAmministrazione;
    private long numeroDiRegistrazione;
    private Date dataDiRegistrazione;
    
    
    
    
    /**
     * Costruisce un identificatore di protocollo con il codice amministrazione, codice AOO, numero di registrazione e data di registrazione specificati.  
     * @param codiceAmministrazione
     * @param codiceAOO
     * @param numeroRegistrazione 
     * @param dataRegistrazione
     */
    public IdentificatoreDiProtocollo(
        String codiceAmministrazione, 
        String codiceAOO, 
        long numeroRegistrazione, 
        Date dataRegistrazione) {
      this.codiceAmministrazione = codiceAmministrazione;
      this.codiceAOO = codiceAOO;
      this.numeroDiRegistrazione = numeroRegistrazione;
      this.dataDiRegistrazione = dataRegistrazione;
       //if (!validate()) throw new Exception("IdentificatoreDiProtocollo():: Identificatore di protocollo non valido");
    }
    
//    public IdentificatoreDiProtocollo(
//        String codiceAmministrazione, 
//        String codiceAOO, 
//        String numeroRegistrazioneString, 
//        Date dataRegistrazione) {
//      this(codiceAmministrazione, 
//          codiceAOO, 
//          Long.parseLong(numeroRegistrazioneString), 
//          dataRegistrazione);
//      //validate();
//    }


    /**
     * @return true se l'oggetto rappresenta un numero di protocollo valido (campi non nulli e numero di registrazione valido secondo le specifiche CNIPA)
     */
    public boolean validate() {
      return (codiceAOO != null) && 
             (codiceAmministrazione != null) &&
//             (String.valueOf(numeroDiRegistrazione).length()<=7) && // verifica che la lunghezza del numero di registrazione sia <= 7 caratteri, come richiesto da CNIPA
//             numeroDiRegistrazione>=0 &&
             isValidNumeroRegistrazione() &&
             (dataDiRegistrazione != null);
    }
    
    /**
     * @return true se il numero di registrazione � un numero valido in base alle specifiche CNIPA (stringa numerica di lunghezza <= a 7 caratteri)
     */
    public boolean isValidNumeroRegistrazione() {
      return 
       ((String.valueOf(numeroDiRegistrazione).length()<=7) && // verifica che la lunghezza del numero di registrazione sia <= 7 caratteri, come richiesto da CNIPA
      numeroDiRegistrazione>=0); 

    }
    
    /**
     * Restituisce il codice amministrazione associato all'identificatore di protocollo.
     * @return codice amministrazione associato all'identificatore di protocollo.
     */
    public String getCodiceAmministrazione() {
        return codiceAmministrazione;
    }
    /**
     * Imposta il codice di amministrazione associato all'identificatore di protocollo.
     * @param codiceAmministrazione
     */
    public void setCodiceAmministrazione(String codiceAmministrazione) {
        this.codiceAmministrazione = codiceAmministrazione;
    }
    /**
     * Restituisce il codice AOO associato all'identificatore di protocollo.
     * @return codice AOO associato all'identifcatore di protocollo.
     */
    public String getCodiceAOO() {
        return codiceAOO;
    }
    /**
     * Imposta il codice AOO associato all'identificatore di protocollo.
     * @param codiceAOO
     */
    public void setCodiceAOO(String codiceAOO) {
        this.codiceAOO = codiceAOO;
    }
    /**
     * Restituisce la data di registrazione associata all'identificatore di protocollo.
     * @return data di registrazione associata all'identifcatore di protocollo.
     */
    public Date getDataDiRegistrazione() {
        return dataDiRegistrazione;
    }
    /**
     * Imposta la data di registrazione associata all'identificatore di protocollo.
     * @param dataDiRegistrazione
     */
    public void setDataDiRegistrazione(Date dataDiRegistrazione) {
        this.dataDiRegistrazione = dataDiRegistrazione;
    }
    /**
     * Restituisce il numero di registrazione associato all'identificatore di protocollo.
     * @return numero di registrazione associato all'identificatore di protocollo.
     */
    public long getNumeroDiRegistrazione() {
        return numeroDiRegistrazione;
    }
    
    /**
     * Restituisce il numero di registrazione (in formato stringa e riampito con zeri in testa se la lunghezza � inferiore a 7 caratteri) 
     * associato all'identificatore di protocollo.
     * @return numero di registrazione in formato stringa.
     */
    public String getNumeroDiRegistrazioneString() {
      String zeroPrefix = "0000000";
      String numeroRegistrazioneString = String.valueOf(numeroDiRegistrazione);
      if (numeroRegistrazioneString.length()>=7) return numeroRegistrazioneString;
      String numeroRegistrazionePaddedString = zeroPrefix +  numeroRegistrazioneString;
      
      return numeroRegistrazionePaddedString.
              substring(numeroRegistrazionePaddedString.length()-zeroPrefix.length());
  }
    
    /**
     * Imposta il numero di registrazione associato all'identificatore di protocollo.
     * @param numeroDiRegistrazione
     */
    public void setNumeroDiRegistrazione(long numeroDiRegistrazione) {
        this.numeroDiRegistrazione = numeroDiRegistrazione;
    }

    /**
     * Imposta il numero di registrazione associato all'identificatore di protocollo con la stringa numerica passata come parametro.
     * @param numeroDiRegistrazioneString
     * @throws Exception - se il parametro passato non � una stringa numerica
     */
    public void setNumeroDiRegistrazioneString(String numeroDiRegistrazioneString) throws Exception {
    try {
      this.numeroDiRegistrazione = Long.parseLong(numeroDiRegistrazioneString);
    } catch (NumberFormatException nfe) {
      //return zeroPrefix;
      throw new Exception("IdentificatoreDiProtocollo::setNumeroDiRegistrazioneString:: La stringa fornita non � un numero di registrazione valido");
    }
  }

//    public String toString() {
//      String s = "";
//      if (codiceAmministrazione != null) {
//          s += codiceAmministrazione + "/";
//      }
//      if (codiceAOO != null) {
//          s += codiceAOO + "/";
//      }
//      if (dataDiRegistrazione != null) {
//          s += new SimpleDateFormat("dd-MM-yyyyZ").format(dataDiRegistrazione) + "/";
//      }
////      if (numeroDiRegistrazione != 0) {
////          s += numeroDiRegistrazione;
////      }
//      s += getNumeroDiRegistrazioneString();
//      if (s.charAt(s.length()-1) == '/') 
//          return s.substring(0, s.length()-1);
//      else return s;
//  }

    /**
     * Returns <code>true</code> if this <code>IdentificatoreDiProtocollo</code> is the same as the o argument.
     *
     * @return <code>true</code> if this <code>IdentificatoreDiProtocollo</code> is the same as the o argument.
     */
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null) {
        return false;
      }
      if (o.getClass() != getClass()) {
        return false;
      }
      IdentificatoreDiProtocollo castedObj = (IdentificatoreDiProtocollo) o;
      return ((this.codiceAOO == null
        ? castedObj.codiceAOO == null
        : this.codiceAOO.equals(castedObj.codiceAOO))
        && (this.codiceAmministrazione == null
          ? castedObj.codiceAmministrazione == null
          : this.codiceAmministrazione.equals(castedObj.codiceAmministrazione))
        && (this.numeroDiRegistrazione == castedObj.numeroDiRegistrazione) && (this.dataDiRegistrazione == null
        ? castedObj.dataDiRegistrazione == null
        : this.dataDiRegistrazione.equals(castedObj.dataDiRegistrazione)));
    }

    /**
     * 
     * @return The string representation of this object
     */
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[IdentificatoreDiProtocollo:");
      buffer.append(" codiceAOO: ");
      buffer.append(codiceAOO);
      buffer.append(" codiceAmministrazione: ");
      buffer.append(codiceAmministrazione);
      buffer.append(" numeroDiRegistrazione: ");
      buffer.append(numeroDiRegistrazione);
      buffer.append(" numeroDiRegistrazione (zero padded string): ");
      buffer.append(getNumeroDiRegistrazioneString());
      buffer.append(" dataDiRegistrazione: ");
      buffer.append(dataDiRegistrazione);
      buffer.append("]");
      return buffer.toString();
    }

}

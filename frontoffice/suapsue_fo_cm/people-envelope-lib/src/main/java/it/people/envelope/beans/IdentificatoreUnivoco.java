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
package it.people.envelope.beans;

import java.util.Date;

public class IdentificatoreUnivoco {
	
	protected String codiceProgetto;
	protected String codiceSistema_nomeServer;
	protected String codiceSistema_codiceAmministrazione;
	protected Date dataDiRegistrazione;
	protected String codiceIdentificativoOperazione;
	
	
  
  /**
   * @param codiceOperazione
   * @param codiceProgetto
   * @param codiceAmministrazione
   * @param nomeServer
   * @param dataRegistrazione
   */
  public IdentificatoreUnivoco(
      String codiceOperazione, 
      String codiceProgetto, 
      String codiceAmministrazione, 
      String nomeServer, 
      Date dataRegistrazione) {
    codiceIdentificativoOperazione = codiceOperazione;
    this.codiceProgetto = codiceProgetto;
    codiceSistema_codiceAmministrazione = codiceAmministrazione;
    codiceSistema_nomeServer = nomeServer;
    this.dataDiRegistrazione = dataRegistrazione;
  }
  public String getCodiceIdentificativoOperazione() {
		return codiceIdentificativoOperazione;
	}
	public void setCodiceIdentificativoOperazione(
			String codiceIdentificativoOperazione) {
		this.codiceIdentificativoOperazione = codiceIdentificativoOperazione;
	}
	public String getCodiceProgetto() {
		return codiceProgetto;
	}
	public void setCodiceProgetto(String codiceProgetto) {
		this.codiceProgetto = codiceProgetto;
	}
	public String getCodiceSistema_codiceAmministrazione() {
		return codiceSistema_codiceAmministrazione;
	}
	public void setCodiceSistema_codiceAmministrazione(
			String codiceSistema_codiceAmministrazione) {
		this.codiceSistema_codiceAmministrazione = codiceSistema_codiceAmministrazione;
	}
	public String getCodiceSistema_nomeServer() {
		return codiceSistema_nomeServer;
	}
	public void setCodiceSistema_nomeServer(String codiceSistema_nomeServer) {
		this.codiceSistema_nomeServer = codiceSistema_nomeServer;
	}
	public Date getDataDiRegistrazione() {
		return dataDiRegistrazione;
	}
	public void setDataDiRegistrazione(Date dataDiRegistrazione) {
		this.dataDiRegistrazione = dataDiRegistrazione;
	}
  
  /**
   * 
   * @return The string representation of this object
   */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[IdentificatoreUnivoco:");
		buffer.append(" codiceProgetto: ");
		buffer.append(codiceProgetto);
		buffer.append(" codiceSistema_nomeServer: ");
		buffer.append(codiceSistema_nomeServer);
		buffer.append(" codiceSistema_codiceAmministrazione: ");
		buffer.append(codiceSistema_codiceAmministrazione);
		buffer.append(" DataDiRegistrazione: ");
		buffer.append(dataDiRegistrazione);
		buffer.append(" codiceIdentificativoOperazione: ");
		buffer.append(codiceIdentificativoOperazione);
		buffer.append("]");
		return buffer.toString();
	}
  /**
   * Returns <code>true</code> if this <code>IdentificatoreUnivoco</code> is the same as the o argument.
   *
   * @return <code>true</code> if this <code>IdentificatoreUnivoco</code> is the same as the o argument.
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
    IdentificatoreUnivoco castedObj = (IdentificatoreUnivoco) o;
    return ((this.codiceProgetto == null
      ? castedObj.codiceProgetto == null
      : this.codiceProgetto.equals(castedObj.codiceProgetto))
      && (this.codiceSistema_nomeServer == null
        ? castedObj.codiceSistema_nomeServer == null
        : this.codiceSistema_nomeServer
          .equals(castedObj.codiceSistema_nomeServer))
      && (this.codiceSistema_codiceAmministrazione == null
        ? castedObj.codiceSistema_codiceAmministrazione == null
        : this.codiceSistema_codiceAmministrazione
          .equals(castedObj.codiceSistema_codiceAmministrazione))
      && (this.dataDiRegistrazione == null
        ? castedObj.dataDiRegistrazione == null
        : this.dataDiRegistrazione.equals(castedObj.dataDiRegistrazione)) && (this.codiceIdentificativoOperazione == null
      ? castedObj.codiceIdentificativoOperazione == null
      : this.codiceIdentificativoOperazione
        .equals(castedObj.codiceIdentificativoOperazione)));
  }
		
	
	

}



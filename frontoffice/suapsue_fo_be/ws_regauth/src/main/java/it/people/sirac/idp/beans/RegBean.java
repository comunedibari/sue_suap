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


package it.people.sirac.idp.beans;

public class RegBean  implements java.io.Serializable {
  
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String eMail;
    private String indirizzoResidenza;
    private String capResidenza;
    private String cittaResidenza;
    private String provinciaResidenza;
    private String statoResidenza;
    private String lavoro;
    private String indirizzoDomicilio;
    private String capDomicilio;
    private String cittaDomicilio;
    private String provinciaDomicilio;
    private String statoDomicilio;
    private String dataNascita;
    private String luogoNascita;
    private String statoNascita;
    private String provinciaNascita;
    private String sesso;
    private String telefono;
    private String cellulare;
    private String titolo;
    private String domicilioElettronico;
    private String idComune;
    private String cartaIdentita;
    private String password;
    private String pin;
    private String status;
    private String timestampRegistrazione;
    private String timestampAttivazione;
    private String ruolo;
    private String territorio;
    
    public String getCapDomicilio() {
      return capDomicilio;
    }
    public void setCapDomicilio(String capDomicilio) {
      this.capDomicilio = capDomicilio;
    }
    public String getCapResidenza() {
      return capResidenza;
    }
    public void setCapResidenza(String capResidenza) {
      this.capResidenza = capResidenza;
    }
    public String getCartaIdentita() {
      return cartaIdentita;
    }
    public void setCartaIdentita(String cartaIdentita) {
      this.cartaIdentita = cartaIdentita;
    }
    public String getCellulare() {
      return cellulare;
    }
    public void setCellulare(String cellulare) {
      this.cellulare = cellulare;
    }
    public String getCittaDomicilio() {
      return cittaDomicilio;
    }
    public void setCittaDomicilio(String cittaDomicilio) {
      this.cittaDomicilio = cittaDomicilio;
    }
    public String getCittaResidenza() {
      return cittaResidenza;
    }
    public void setCittaResidenza(String cittaResidenza) {
      this.cittaResidenza = cittaResidenza;
    }
    public String getCodiceFiscale() {
      return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale) {
      this.codiceFiscale = codiceFiscale;
    }
    public String getCognome() {
      return cognome;
    }
    public void setCognome(String cognome) {
      this.cognome = cognome;
    }
    public String getDataNascita() {
      return dataNascita;
    }
    public void setDataNascita(String dataNascita) {
      this.dataNascita = dataNascita;
    }
    public String getDomicilioElettronico() {
      return domicilioElettronico;
    }
    public void setDomicilioElettronico(String domicilioElettronico) {
      this.domicilioElettronico = domicilioElettronico;
    }
    public String getEmail() {
      return eMail;
    }
    public void setEmail(String eMail) {
      this.eMail = eMail;
    }
    public String getIdComune() {
      return idComune;
    }
    public void setIdComune(String idComune) {
      this.idComune = idComune;
    }
    public String getIndirizzoDomicilio() {
      return indirizzoDomicilio;
    }
    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
      this.indirizzoDomicilio = indirizzoDomicilio;
    }
    public String getIndirizzoResidenza() {
      return indirizzoResidenza;
    }
    public void setIndirizzoResidenza(String indirizzoResidenza) {
      this.indirizzoResidenza = indirizzoResidenza;
    }
    public String getLavoro() {
      return lavoro;
    }
    public void setLavoro(String lavoro) {
      this.lavoro = lavoro;
    }
    public String getLuogoNascita() {
      return luogoNascita;
    }
    public void setLuogoNascita(String luogoNascita) {
      this.luogoNascita = luogoNascita;
    }
    public String getNome() {
      return nome;
    }
    public void setNome(String nome) {
      this.nome = nome;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public String getPin() {
      return pin;
    }
    public void setPin(String pin) {
      this.pin = pin;
    }
    public String getProvinciaDomicilio() {
      return provinciaDomicilio;
    }
    public void setProvinciaDomicilio(String provinciaDomicilio) {
      this.provinciaDomicilio = provinciaDomicilio;
    }
    public String getProvinciaNascita() {
      return provinciaNascita;
    }
    public void setProvinciaNascita(String provinciaNascita) {
      this.provinciaNascita = provinciaNascita;
    }
    public String getProvinciaResidenza() {
      return provinciaResidenza;
    }
    public void setProvinciaResidenza(String provinciaResidenza) {
      this.provinciaResidenza = provinciaResidenza;
    }
    public String getSesso() {
      return sesso;
    }
    public void setSesso(String sesso) {
      this.sesso = sesso;
    }
    public String getStatoDomicilio() {
      return statoDomicilio;
    }
    public void setStatoDomicilio(String statoDomicilio) {
      this.statoDomicilio = statoDomicilio;
    }
    public String getStatoNascita() {
      return statoNascita;
    }
    public void setStatoNascita(String statoNascita) {
      this.statoNascita = statoNascita;
    }
    public String getStatoResidenza() {
      return statoResidenza;
    }
    public void setStatoResidenza(String statoResidenza) {
      this.statoResidenza = statoResidenza;
    }
    public String getTelefono() {
      return telefono;
    }
    public void setTelefono(String telefono) {
      this.telefono = telefono;
    }
    public String getTitolo() {
      return titolo;
    }
    public void setTitolo(String titolo) {
      this.titolo = titolo;
    }
    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
    	this.status = status;
    }
    public String getTimestampRegistrazione() {
      return timestampRegistrazione;
    }
    public void setTimestampRegistrazione(String timestampRegistrazione) {
    	this.timestampRegistrazione = timestampRegistrazione;
    }
    public String getTimestampAttivazione() {
      return timestampAttivazione;
    }
    public void setTimestampAttivazione(String timestampAttivazione) {
    	this.timestampAttivazione = timestampAttivazione;
    }

    
    /**
	 * @return the ruolo
	 */
	public String getRuolo() {
		return this.ruolo;
	}
	/**
	 * @param ruolo the ruolo to set
	 */
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	/**
	 * @return the territorio
	 */
	public String getTerritorio() {
		return this.territorio;
	}
	/**
	 * @param territorio the territorio to set
	 */
	public void setTerritorio(String territorio) {
		this.territorio = territorio;
	}
	/**
     * @generated by CodeSugar http://sourceforge.net/projects/codesugar */
    
    public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("[RegBean:");
      buffer.append(" nome: ");
      buffer.append(nome);
      buffer.append(" cognome: ");
      buffer.append(cognome);
      buffer.append(" codiceFiscale: ");
      buffer.append(codiceFiscale);
      buffer.append(" eMail: ");
      buffer.append(eMail);
      buffer.append(" indirizzoResidenza: ");
      buffer.append(indirizzoResidenza);
      buffer.append(" capResidenza: ");
      buffer.append(capResidenza);
      buffer.append(" cittaResidenza: ");
      buffer.append(cittaResidenza);
      buffer.append(" provinciaResidenza: ");
      buffer.append(provinciaResidenza);
      buffer.append(" statoResidenza: ");
      buffer.append(statoResidenza);
      buffer.append(" lavoro: ");
      buffer.append(lavoro);
      buffer.append(" indirizzoDomicilio: ");
      buffer.append(indirizzoDomicilio);
      buffer.append(" capDomicilio: ");
      buffer.append(capDomicilio);
      buffer.append(" cittaDomicilio: ");
      buffer.append(cittaDomicilio);
      buffer.append(" provinciaDomicilio: ");
      buffer.append(provinciaDomicilio);
      buffer.append(" statoDomicilio: ");
      buffer.append(statoDomicilio);
      buffer.append(" dataNascita: ");
      buffer.append(dataNascita);
      buffer.append(" luogoNascita: ");
      buffer.append(luogoNascita);
      buffer.append(" statoNascita: ");
      buffer.append(statoNascita);
      buffer.append(" provinciaNascita: ");
      buffer.append(provinciaNascita);
      buffer.append(" sesso: ");
      buffer.append(sesso);
      buffer.append(" telefono: ");
      buffer.append(telefono);
      buffer.append(" cellulare: ");
      buffer.append(cellulare);
      buffer.append(" titolo: ");
      buffer.append(titolo);
      buffer.append(" domicilioElettronico: ");
      buffer.append(domicilioElettronico);
      buffer.append(" idComune: ");
      buffer.append(idComune);
      buffer.append(" cartaIdentita: ");
      buffer.append(cartaIdentita);
      buffer.append(" password: ");
      buffer.append(password);
      buffer.append(" pin: ");
      buffer.append(pin);
      buffer.append(" status: ");
      buffer.append(status);
      buffer.append(" ruolo: ");
      buffer.append(ruolo);
      buffer.append(" territorio: ");
      buffer.append(territorio);
      buffer.append(" timestampRegistrazione: ");
      buffer.append(timestampRegistrazione);
      buffer.append(" timestampAttivazione: ");
      buffer.append(timestampAttivazione);
      buffer.append("]");
      return buffer.toString();
    }

 }

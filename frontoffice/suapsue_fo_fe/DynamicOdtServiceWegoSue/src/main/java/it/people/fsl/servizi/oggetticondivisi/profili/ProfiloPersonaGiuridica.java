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
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.oggetticondivisi.profili;

import java.io.Serializable;

/**
 * @author max
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProfiloPersonaGiuridica implements Serializable {

  /** codice fiscale  */
  protected String codiceFiscale;
  
  /** partita iva  */
  protected String partitaIva; 
  
  /** libero */
  protected String descrizione;
  
  /** domicilio elettronico accreditamento */
  protected String domicilioElettronico;
  
  /** denominazione o ragione sociale intermediario (usato solo per alcune qualifiche) */
  protected String denominazione;
  
  /** sede legale intermediario (usato solo per alcune qualifiche) */
  protected String sedeLegale;
  
  protected ProfiloPersonaFisica rappresentanteLegale;
   
  /**
   * 
   */
  public ProfiloPersonaGiuridica() {
    clear();
  }

  public ProfiloPersonaGiuridica(ProfiloPersonaGiuridica ppg){
	  this.codiceFiscale = ppg.codiceFiscale;
	  this.partitaIva = ppg.partitaIva;
	  this.descrizione = ppg.descrizione;
	  this.domicilioElettronico = ppg.domicilioElettronico;
	  this.denominazione = ppg.denominazione;
	  this.sedeLegale = ppg.sedeLegale;
	  this.rappresentanteLegale = new ProfiloPersonaFisica(ppg.rappresentanteLegale);
  }
  
  public void clear() {
      codiceFiscale = "";
      partitaIva = "";
      denominazione = "";
      sedeLegale = "";
      domicilioElettronico = "";
      descrizione = "";
      rappresentanteLegale = new ProfiloPersonaFisica();
  }

  /**
   * @return Returns the codiceFiscale.
   */
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  /**
   * @param codiceFiscale The codiceFiscale to set.
   */
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }
  /**
   * @return Returns the denominazione.
   */
  public String getDenominazione() {
    return denominazione;
  }
  /**
   * @param denominazione The denominazione to set.
   */
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }
  /**
   * @return Returns the descrizione.
   */
  public String getDescrizione() {
    return descrizione;
  }
  /**
   * @param descrizione The descrizione to set.
   */
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }
  /**
   * @return Returns the domicilioElettronico.
   */
  public String getDomicilioElettronico() {
    return domicilioElettronico;
  }
  /**
   * @param domicilioElettronico The domicilioElettronico to set.
   */
  public void setDomicilioElettronico(String domicilioElettronico) {
    this.domicilioElettronico = domicilioElettronico;
  }
  /**
   * @return Returns the partitaIva.
   */
  public String getPartitaIva() {
    return partitaIva;
  }
  /**
   * @param partitaIva The partitaIva to set.
   */
  public void setPartitaIva(String partitaIva) {
    this.partitaIva = partitaIva;
  }
  /**
   * @return Returns the rappresentanteLegale.
   */
  public ProfiloPersonaFisica getRappresentanteLegale() {
    return rappresentanteLegale;
  }
  /**
   * @param rappresentanteLegale The rappresentanteLegale to set.
   */
  public void setRappresentanteLegale(ProfiloPersonaFisica rappresentanteLegale) {
    this.rappresentanteLegale = rappresentanteLegale;
  }
  /**
   * @return Returns the sedeLegale.
   */
  public String getSedeLegale() {
    return sedeLegale;
  }
  /**
   * @param sedeLegale The sedeLegale to set.
   */
  public void setSedeLegale(String sedeLegale) {
    this.sedeLegale = sedeLegale;
  }
  
}

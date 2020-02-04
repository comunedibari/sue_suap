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
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

public class ProfessionistaBean extends PersonaBean 
{
  public ProfessionistaBean()
  {
    iscrittoCollegioOrdine="";
    provinciaCollegioOrdine="";
    numeroIscrizione="";
    studio = new StudioBean();
    codiceMotivazioneRappresentanza = "";
    descrizioneMotivazioneRappresentanza = "";
    altraMotivazione = "";
    agiscePerContoDi="";
    personaFisica = new DichiaranteBean();
    personaGiuridica = new AttivitaBean();
    codiceAlbo = "";
    descrizioneAlbo = "";
    altroAlbo = "";
    numeroIscrizioneAlbo = "";
    dataIscrizioneAlbo = "";
    provinciaIscrizioneAlbo = "";
    delega = new DelegaBean();
  }
  private String iscrittoCollegioOrdine;
  private String provinciaCollegioOrdine;
  private String numeroIscrizione;
  private StudioBean studio;
  private String codiceMotivazioneRappresentanza;
  private String descrizioneMotivazioneRappresentanza;
  private String altraMotivazione;
  private String agiscePerContoDi;
  private DichiaranteBean personaFisica;
  private AttivitaBean personaGiuridica;  
  private String codiceAlbo;
  private String descrizioneAlbo;
  private String altroAlbo;
  private String numeroIscrizioneAlbo;
  private String dataIscrizioneAlbo;
  private String provinciaIscrizioneAlbo;
  private DelegaBean delega;
  
  public void setIscrittoCollegioOrdine(String iscrittoCollegioOrdine)
  {
    this.iscrittoCollegioOrdine = iscrittoCollegioOrdine;
  }


  public String getIscrittoCollegioOrdine()
  {
    return iscrittoCollegioOrdine;
  }


  public void setProvinciaCollegioOrdine(String provinciaCollegioOrdine)
  {
    this.provinciaCollegioOrdine = provinciaCollegioOrdine;
  }


  public String getProvinciaCollegioOrdine()
  {
    return provinciaCollegioOrdine;
  }


  public void setNumeroIscrizione(String numeroIscrizione)
  {
    this.numeroIscrizione = numeroIscrizione;
  }


  public String getNumeroIscrizione()
  {
    return numeroIscrizione;
  }


  public void setStudio(StudioBean studio)
  {
    this.studio = studio;
  }


  public StudioBean getStudio()
  {
    return studio;
  }


public String getAltraMotivazione() {
    return altraMotivazione;
}


public void setAltraMotivazione(String altraMotivazione) {
    this.altraMotivazione = altraMotivazione;
}


public String getCodiceMotivazioneRappresentanza() {
    return codiceMotivazioneRappresentanza;
}


public void setCodiceMotivazioneRappresentanza(String codiceMotivazioneRappresentanza) {
    this.codiceMotivazioneRappresentanza = codiceMotivazioneRappresentanza;
}


public String getDescrizioneMotivazioneRappresentanza() {
    return descrizioneMotivazioneRappresentanza;
}


public void setDescrizioneMotivazioneRappresentanza(String descrizioneMotivazioneRappresentanza) {
    this.descrizioneMotivazioneRappresentanza = descrizioneMotivazioneRappresentanza;
}


public String getAgiscePerContoDi() {
    return agiscePerContoDi;
}


public void setAgiscePerContoDi(String agiscePerContoDi) {
    this.agiscePerContoDi = agiscePerContoDi;
}


public DichiaranteBean getPersonaFisica() {
    return personaFisica;
}


public void setPersonaFisica(DichiaranteBean personaFisica) {
    this.personaFisica = personaFisica;
}


public AttivitaBean getPersonaGiuridica() {
    return personaGiuridica;
}


public void setPersonaGiuridica(AttivitaBean persoanaGiuridica) {
    this.personaGiuridica = persoanaGiuridica;
}


public String getAltroAlbo() {
    return altroAlbo;
}


public void setAltroAlbo(String altroAlbo) {
    this.altroAlbo = altroAlbo;
}


public String getCodiceAlbo() {
    return codiceAlbo;
}


public void setCodiceAlbo(String codiceAlbo) {
    this.codiceAlbo = codiceAlbo;
}


public String getDescrizioneAlbo() {
    return descrizioneAlbo;
}


public void setDescrizioneAlbo(String descrizioneAlbo) {
    this.descrizioneAlbo = descrizioneAlbo;
}


public String getDataIscrizioneAlbo() {
    return dataIscrizioneAlbo;
}


public void setDataIscrizioneAlbo(String dataIscrizioneAlbo) {
    this.dataIscrizioneAlbo = dataIscrizioneAlbo;
}


public String getNumeroIscrizioneAlbo() {
    return numeroIscrizioneAlbo;
}


public void setNumeroIscrizioneAlbo(String numeroIscrizioneAlbo) {
    this.numeroIscrizioneAlbo = numeroIscrizioneAlbo;
}


public String getProvinciaIscrizioneAlbo() {
    return provinciaIscrizioneAlbo;
}


public void setProvinciaIscrizioneAlbo(String provinciaIscrizioneAlbo) {
    this.provinciaIscrizioneAlbo = provinciaIscrizioneAlbo;
}


public DelegaBean getDelega() {
    return delega;
}


public void setDelega(DelegaBean delega) {
    this.delega = delega;
}

}

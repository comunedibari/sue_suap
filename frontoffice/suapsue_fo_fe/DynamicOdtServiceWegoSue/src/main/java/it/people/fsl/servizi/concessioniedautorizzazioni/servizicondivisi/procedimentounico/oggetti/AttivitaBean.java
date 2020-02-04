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

public class AttivitaBean extends PersonaBean 
{
  public static final int LEGALE_RAPPRESENTANTE = 0;
  public static final int PROCURATORE = 1;
  public static final int DELEGATO = 2;
  public AttivitaBean()
  {
    denominazione="";
    inQualitaDi="";
    inQualitaDiPersonaFisica = "";
    inQualitaDiAziendaNoProfit = "";
    sede=new SedeBean();
    codiceMotivazioneRappresentanza = "";
    descrizioneMotivazioneRappresentanza = "";
    altraMotivazione = "";
    tribunale=new LuogoBean();
    cameraCommercio=new LuogoBean();
    rappresentanteLegale = new RappresentanteLegaleBean();
  }
  private String denominazione;
  private String inQualitaDi;
  private String inQualitaDiPersonaFisica;
  private String inQualitaDiAziendaNoProfit;
  private SedeBean sede;
  private String codiceMotivazioneRappresentanza;
  private String descrizioneMotivazioneRappresentanza;
  private String altraMotivazione;
  private LuogoBean tribunale;
  private LuogoBean cameraCommercio;
  private RappresentanteLegaleBean rappresentanteLegale;
  
  public void setDenominazione(String denominazione)
  {
    this.denominazione = denominazione;
  }


  public String getDenominazione()
  {
    return denominazione;
  }


  public void setInQualitaDi(String inQualitaDi)
  {
    this.inQualitaDi=inQualitaDi;
  }


  public String getInQualitaDi()
  {
    return inQualitaDi;
  }


  public void setSede(SedeBean sede)
  {
    this.sede = sede;
  }


  public SedeBean getSede()
  {
    return sede;
  }


/**
 * @return Returns the inQualitaDiAziendaNoProfit.
 */
public String getInQualitaDiAziendaNoProfit() {
    return inQualitaDiAziendaNoProfit;
}


/**
 * @param inQualitaDiAziendaNoProfit The inQualitaDiAziendaNoProfit to set.
 */
public void setInQualitaDiAziendaNoProfit(String inQualitaDiAziendaNoProfit) {
    this.inQualitaDiAziendaNoProfit = inQualitaDiAziendaNoProfit;
}


/**
 * @return Returns the inQualitaDiPersonaFisica.
 */
public String getInQualitaDiPersonaFisica() {
    return inQualitaDiPersonaFisica;
}


/**
 * @param inQualitaDiPersonaFisica The inQualitaDiPersonaFisica to set.
 */
public void setInQualitaDiPersonaFisica(String inQualitaDiPersonaFisica) {
    this.inQualitaDiPersonaFisica = inQualitaDiPersonaFisica;
}


public String getDescrizioneMotivazioneRappresentanza() {
    return descrizioneMotivazioneRappresentanza;
}


public void setDescrizioneMotivazioneRappresentanza(String descrizioneMotivazioneRappresentanza) {
    this.descrizioneMotivazioneRappresentanza = descrizioneMotivazioneRappresentanza;
}


public String getCodiceMotivazioneRappresentanza() {
    return codiceMotivazioneRappresentanza;
}


public void setCodiceMotivazioneRappresentanza(String codiceMotivazioneRappresentanza) {
    this.codiceMotivazioneRappresentanza = codiceMotivazioneRappresentanza;
}


public String getAltraMotivazione() {
    return altraMotivazione;
}


public void setAltraMotivazione(String altraMotivazione) {
    this.altraMotivazione = altraMotivazione;
}


public LuogoBean getCameraCommercio() {
    return cameraCommercio;
}


public void setCameraCommercio(LuogoBean cameraCommercio) {
    this.cameraCommercio = cameraCommercio;
}


public LuogoBean getTribunale() {
    return tribunale;
}


public void setTribunale(LuogoBean tribunale) {
    this.tribunale = tribunale;
}


public RappresentanteLegaleBean getRappresentanteLegale() {
    return rappresentanteLegale;
}


public void setRappresentanteLegale(RappresentanteLegaleBean rappresentanteLegale) {
    this.rappresentanteLegale = rappresentanteLegale;
}
}

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


public class PersonaBean extends BaseBean 
{
  public PersonaBean()
  {
    cognome="";
    nome="";
    luogoNascita="";
    dataNascita="";
    provinciaNascita="";
    codiceFiscale="";
    statoNascita="";
    partitaIva="";
    residenza= new IndirizzoBean();
    sesso="";
  }
  private String cognome;
  private String nome;
  private String luogoNascita;
  private String dataNascita;
  private String provinciaNascita;
  private String statoNascita;
  private String codiceFiscale;
  private String partitaIva;
  private IndirizzoBean residenza;
  private String sesso;


  public void setCognome(String cognome)
  {
    this.cognome = cognome;
  }


  public String getCognome()
  {
    return cognome;
  }


  public void setNome(String nome)
  {
    this.nome = nome;
  }


  public String getNome()
  {
    return nome;
  }


  public void setLuogoNascita(String luogoNascita)
  {
    this.luogoNascita = luogoNascita;
  }


  public String getLuogoNascita()
  {
    return luogoNascita;
  }


  public void setDataNascita(String dataNascita)
  {
    this.dataNascita = dataNascita;
  }


  public String getDataNascita()
  {
    return dataNascita;
  }


  public void setProvinciaNascita(String provinciaNascita)
  {
    this.provinciaNascita = provinciaNascita;
  }


  public String getProvinciaNascita()
  {
    return provinciaNascita;
  }


  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }


  public String getCodiceFiscale()
  {
    return codiceFiscale;
  }


  public void setResidenza(IndirizzoBean residenza)
  {
    this.residenza = residenza;
  }


  public IndirizzoBean getResidenza()
  {
    return residenza;
  }


  public void setStatoNascita(String statoNascita)
  {
    this.statoNascita = statoNascita;
  }


  public String getStatoNascita()
  {
    return statoNascita;
  }


  public void setPartitaIva(String partitaIva)
  {
    this.partitaIva = partitaIva;
  }


  public String getPartitaIva()
  {
    return partitaIva;
  }


public String getSesso() {
    return sesso;
}


public void setSesso(String sesso) {
    this.sesso = sesso;
}
}

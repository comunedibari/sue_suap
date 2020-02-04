package it.cefriel.people.ssl;

import java.io.Serializable;
import java.util.HashMap;

public class CardUserData
  implements Serializable
{
  private String nome = null;
  private String cognome = null;
  private String codiceFiscale = null;
  private String email = null;
  private String codiceCarta = null;
  private String tipoCarta = null;
  private String dataNascita = null;
  private String hashDatiPersonali = null;
  private String datePattern = null;
  
  public CardUserData()
  {
    this.nome = "";
    this.cognome = "";
    this.codiceFiscale = "";
    this.email = "";
    this.codiceCarta = "";
    this.tipoCarta = "";
    this.dataNascita = "";
    this.datePattern = "";
  }
  
  public CardUserData(HashMap certificateInfoMap)
  {
    String codiceFiscale = (String)certificateInfoMap.get("CodiceFiscale");
    if (codiceFiscale == null) {
      setCodiceFiscale("");
    } else {
      setCodiceFiscale(codiceFiscale);
    }
    String nome = (String)certificateInfoMap.get("Nome");
    if (nome == null) {
      setNome("");
    } else {
      setNome(nome);
    }
    String cognome = (String)certificateInfoMap.get("Cognome");
    if (cognome == null) {
      setCognome("");
    } else {
      setCognome(cognome);
    }
    String email = (String)certificateInfoMap.get("Email");
    if (email == null) {
      setEmail("");
    } else {
      setEmail(email);
    }
    String codiceCarta = (String)certificateInfoMap.get("CodiceCarta");
    if (codiceCarta == null) {
      setCodiceCarta("");
    } else {
      setCodiceCarta(codiceCarta);
    }
    String tipoCarta = (String)certificateInfoMap.get("TipoCarta");
    if (tipoCarta == null) {
      setTipoCarta("");
    } else {
      setTipoCarta(tipoCarta);
    }
    String dataNascita = (String)certificateInfoMap.get("DataDiNascita");
    if (dataNascita == null) {
      setDataNascita("");
    } else {
      setDataNascita(dataNascita);
    }
    String hashDatiPersonali = (String)certificateInfoMap.get("HashDatiPersonali");
    if (hashDatiPersonali == null) {
      setHashDatiPersonali("");
    } else {
      setHashDatiPersonali(hashDatiPersonali);
    }
    String datePattern = (String)certificateInfoMap.get("DatePattern");
    if (datePattern == null) {
      setDatePattern("");
    } else {
      setDatePattern(datePattern);
    }
  }
  
  public CardUserData(String _nome, String _cognome, String _cf, String _email, String _codiceCarta, String _dataNascita, String _hashDatiPersonali, String _datePattern)
  {
    this.nome = _nome;
    this.cognome = _cognome;
    this.codiceFiscale = _cf;
    this.email = _email;
    this.codiceCarta = _codiceCarta;
    this.dataNascita = _dataNascita;
    this.hashDatiPersonali = _hashDatiPersonali;
    this.datePattern = _datePattern;
  }
  
  public void setCognome(String c)
  {
    this.cognome = c;
  }
  
  public void setNome(String n)
  {
    this.nome = n;
  }
  
  public void setDataNascita(String data)
  {
    this.dataNascita = data;
  }
  
  public void setCodiceFiscale(String cf)
  {
    this.codiceFiscale = cf;
  }
  
  public void setCodiceCarta(String codCarta)
  {
    this.codiceCarta = codCarta;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public void setTipoCarta(String tipo)
  {
    this.tipoCarta = tipo;
  }
  
  public void setHashDatiPersonali(String hash)
  {
    this.hashDatiPersonali = hash;
  }
  
  public void setDatePattern(String datePattern)
  {
    this.datePattern = datePattern;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public String getCognome()
  {
    return this.cognome;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public String getTipoCarta()
  {
    return this.tipoCarta;
  }
  
  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  
  public String getCodCarta()
  {
    return this.codiceCarta;
  }
  
  public String getDataNascita()
  {
    return this.dataNascita;
  }
  
  public String getHashDatiPersonali()
  {
    return this.hashDatiPersonali;
  }
  
  public String getDatePattern()
  {
    return this.datePattern;
  }
}


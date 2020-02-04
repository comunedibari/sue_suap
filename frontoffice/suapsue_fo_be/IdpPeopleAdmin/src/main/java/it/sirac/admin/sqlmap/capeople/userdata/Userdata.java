package it.sirac.admin.sqlmap.capeople.userdata;

import java.sql.Timestamp;

public class Userdata
{
  private String codiceFiscale;
  private String cartaIdentita;
  private String nome;
  private String cognome;
  private String dataNascita;
  private String capDomicilio;
  private String capResidenza;
  private String cittaDomicilio;
  private String cittaResidenza;
  private String indirizzoDomicilio;
  private String indirizzoResidenza;
  private String lavoro;
  private String luogoNascita;
  private String provinciaDomicilio;
  private String provinciaNascita;
  private String provinciaResidenza;
  private String sesso = "M";
  private String statoDomicilio;
  private String statoResidenza;
  private String statoNascita;
  private String telefono;
  private String cellulare;
  private String titolo;
  private String email;
  private String domicilioElettronico;
  private String idComune;
  private String password;
  private boolean passwordChanged = false;
  private String pin;
  private boolean pinChanged = false;
  private Timestamp dataRegistrazione;
  private Timestamp dataAttivazione;
  private String status;
  
  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  
  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }
  
  public String getCartaIdentita()
  {
    return this.cartaIdentita;
  }
  
  public void setCartaIdentita(String cartaIdentita)
  {
    this.cartaIdentita = cartaIdentita;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public void setNome(String nome)
  {
    this.nome = nome;
  }
  
  public String getCognome()
  {
    return this.cognome;
  }
  
  public void setCognome(String cognome)
  {
    this.cognome = cognome;
  }
  
  public String getDataNascita()
  {
    return this.dataNascita;
  }
  
  public void setDataNascita(String dataNascita)
  {
    this.dataNascita = dataNascita;
  }
  
  public String getCapDomicilio()
  {
    return this.capDomicilio;
  }
  
  public void setCapDomicilio(String capDomicilio)
  {
    this.capDomicilio = capDomicilio;
  }
  
  public String getCapResidenza()
  {
    return this.capResidenza;
  }
  
  public void setCapResidenza(String capResidenza)
  {
    this.capResidenza = capResidenza;
  }
  
  public String getCittaDomicilio()
  {
    return this.cittaDomicilio;
  }
  
  public void setCittaDomicilio(String cittaDomicilio)
  {
    this.cittaDomicilio = cittaDomicilio;
  }
  
  public String getCittaResidenza()
  {
    return this.cittaResidenza;
  }
  
  public void setCittaResidenza(String cittaResidenza)
  {
    this.cittaResidenza = cittaResidenza;
  }
  
  public String getIndirizzoDomicilio()
  {
    return this.indirizzoDomicilio;
  }
  
  public void setIndirizzoDomicilio(String indirizzoDomicilio)
  {
    this.indirizzoDomicilio = indirizzoDomicilio;
  }
  
  public String getIndirizzoResidenza()
  {
    return this.indirizzoResidenza;
  }
  
  public void setIndirizzoResidenza(String indirizzoResidenza)
  {
    this.indirizzoResidenza = indirizzoResidenza;
  }
  
  public String getLavoro()
  {
    return this.lavoro;
  }
  
  public void setLavoro(String lavoro)
  {
    this.lavoro = lavoro;
  }
  
  public String getLuogoNascita()
  {
    return this.luogoNascita;
  }
  
  public void setLuogoNascita(String luogoNascita)
  {
    this.luogoNascita = luogoNascita;
  }
  
  public String getProvinciaDomicilio()
  {
    return this.provinciaDomicilio;
  }
  
  public void setProvinciaDomicilio(String provinciaDomicilio)
  {
    this.provinciaDomicilio = provinciaDomicilio;
  }
  
  public String getProvinciaNascita()
  {
    return this.provinciaNascita;
  }
  
  public void setProvinciaNascita(String provinciaNascita)
  {
    this.provinciaNascita = provinciaNascita;
  }
  
  public String getProvinciaResidenza()
  {
    return this.provinciaResidenza;
  }
  
  public void setProvinciaResidenza(String provinciaResidenza)
  {
    this.provinciaResidenza = provinciaResidenza;
  }
  
  public String getSesso()
  {
    return this.sesso;
  }
  
  public void setSesso(String sesso)
  {
    this.sesso = sesso;
  }
  
  public String getStatoDomicilio()
  {
    return this.statoDomicilio;
  }
  
  public void setStatoDomicilio(String statoDomicilio)
  {
    this.statoDomicilio = statoDomicilio;
  }
  
  public String getStatoResidenza()
  {
    return this.statoResidenza;
  }
  
  public void setStatoResidenza(String statoResidenza)
  {
    this.statoResidenza = statoResidenza;
  }
  
  public String getStatoNascita()
  {
    return this.statoNascita;
  }
  
  public void setStatoNascita(String statoNascita)
  {
    this.statoNascita = statoNascita;
  }
  
  public String getTelefono()
  {
    return this.telefono;
  }
  
  public void setTelefono(String telefono)
  {
    this.telefono = telefono;
  }
  
  public String getCellulare()
  {
    return this.cellulare;
  }
  
  public void setCellulare(String cellulare)
  {
    this.cellulare = cellulare;
  }
  
  public String getTitolo()
  {
    return this.titolo;
  }
  
  public void setTitolo(String titolo)
  {
    this.titolo = titolo;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String eMail)
  {
    this.email = eMail;
  }
  
  public String getDomicilioElettronico()
  {
    return this.domicilioElettronico;
  }
  
  public void setDomicilioElettronico(String domicilioElettronico)
  {
    this.domicilioElettronico = domicilioElettronico;
  }
  
  public String getIdComune()
  {
    return this.idComune;
  }
  
  public void setIdComune(String idComune)
  {
    this.idComune = idComune;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    if ((this.password != null) && (!this.password.equals(password))) {
      this.passwordChanged = true;
    } else {
      this.passwordChanged = false;
    }
    this.password = password;
  }
  
  public String getPin()
  {
    return this.pin;
  }
  
  public void setPin(String pin)
  {
    if ((this.pin != null) && (!this.pin.equals(pin))) {
      this.pinChanged = true;
    } else {
      this.pinChanged = false;
    }
    this.pin = pin;
  }
  
  public Timestamp getDataRegistrazione()
  {
    return this.dataRegistrazione;
  }
  
  public void setDataRegistrazione(Timestamp dataRegistrazione)
  {
    this.dataRegistrazione = dataRegistrazione;
  }
  
  public Timestamp getDataAttivazione()
  {
    return this.dataAttivazione;
  }
  
  public void setDataAttivazione(Timestamp dataAttivazione)
  {
    this.dataAttivazione = dataAttivazione;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public boolean isPasswordChanged()
  {
    return this.passwordChanged;
  }
  
  public boolean isPinChanged()
  {
    return this.pinChanged;
  }
}

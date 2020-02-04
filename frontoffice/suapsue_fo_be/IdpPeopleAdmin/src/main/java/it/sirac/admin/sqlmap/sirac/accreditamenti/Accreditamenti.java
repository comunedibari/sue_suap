package it.sirac.admin.sqlmap.sirac.accreditamenti;

public class Accreditamenti
{
  private String codiceFiscale;
  private Integer idAccreditamento;
  private String idComune;
  private String idQualifica;
  private String domicilioElettronico;
  private String codicefiscaleIntermediario;
  private String partitaivaIntermediario;
  private String descrizione;
  private String timestampCertificazione;
  private String sedeLegale;
  private String denominazione;
  private Boolean attivo;
  private Boolean deleted;
  private String autoCertificazioneFilename;
  private byte[] autoCertificazione;
  
  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  
  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }
  
  public Integer getIdAccreditamento()
  {
    return this.idAccreditamento;
  }
  
  public void setIdAccreditamento(Integer idAccreditamento)
  {
    this.idAccreditamento = idAccreditamento;
  }
  
  public String getIdComune()
  {
    return this.idComune;
  }
  
  public void setIdComune(String idComune)
  {
    this.idComune = idComune;
  }
  
  public String getIdQualifica()
  {
    return this.idQualifica;
  }
  
  public void setIdQualifica(String idQualifica)
  {
    this.idQualifica = idQualifica;
  }
  
  public String getDomicilioElettronico()
  {
    return this.domicilioElettronico;
  }
  
  public void setDomicilioElettronico(String domicilioElettronico)
  {
    this.domicilioElettronico = domicilioElettronico;
  }
  
  public String getCodicefiscaleIntermediario()
  {
    return this.codicefiscaleIntermediario;
  }
  
  public void setCodicefiscaleIntermediario(String codicefiscaleIntermediario)
  {
    this.codicefiscaleIntermediario = codicefiscaleIntermediario;
  }
  
  public String getPartitaivaIntermediario()
  {
    return this.partitaivaIntermediario;
  }
  
  public void setPartitaivaIntermediario(String partitaivaIntermediario)
  {
    this.partitaivaIntermediario = partitaivaIntermediario;
  }
  
  public String getDescrizione()
  {
    return this.descrizione;
  }
  
  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }
  
  public String getTimestampCertificazione()
  {
    return this.timestampCertificazione;
  }
  
  public void setTimestampCertificazione(String timestampCertificazione)
  {
    this.timestampCertificazione = timestampCertificazione;
  }
  
  public String getSedeLegale()
  {
    return this.sedeLegale;
  }
  
  public void setSedeLegale(String sedeLegale)
  {
    this.sedeLegale = sedeLegale;
  }
  
  public String getDenominazione()
  {
    return this.denominazione;
  }
  
  public void setDenominazione(String denominazione)
  {
    this.denominazione = denominazione;
  }
  
  public Boolean getAttivo()
  {
    return this.attivo;
  }
  
  public void setAttivo(Boolean attivo)
  {
    this.attivo = attivo;
  }
  
  public Boolean getDeleted()
  {
    return this.deleted;
  }
  
  public void setDeleted(Boolean deleted)
  {
    this.deleted = deleted;
  }
  
  public String getAutoCertificazioneFilename()
  {
    return this.autoCertificazioneFilename;
  }
  
  public void setAutoCertificazioneFilename(String autoCertificazioneFilename)
  {
    this.autoCertificazioneFilename = autoCertificazioneFilename;
  }
  
  public byte[] getAutoCertificazione()
  {
    return this.autoCertificazione;
  }
  
  public void setAutoCertificazione(byte[] autoCertificazione)
  {
    this.autoCertificazione = autoCertificazione;
  }
}

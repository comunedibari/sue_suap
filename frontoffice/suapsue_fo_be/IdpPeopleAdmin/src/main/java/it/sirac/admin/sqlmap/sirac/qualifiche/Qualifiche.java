package it.sirac.admin.sqlmap.sirac.qualifiche;

public class Qualifiche
{
  private String idQualifica;
  private String descrizione;
  private String tipoQualifica;
  private Boolean autoCertificabile;
  private Boolean hasRappresentanteLegale;
  
  public String getIdQualifica()
  {
    return this.idQualifica;
  }
  
  public void setIdQualifica(String idQualifica)
  {
    this.idQualifica = idQualifica;
  }
  
  public String getDescrizione()
  {
    return this.descrizione;
  }
  
  public void setDescrizione(String descrizione)
  {
    this.descrizione = descrizione;
  }
  
  public String getTipoQualifica()
  {
    return this.tipoQualifica;
  }
  
  public void setTipoQualifica(String tipoQualifica)
  {
    this.tipoQualifica = tipoQualifica;
  }
  
  public Boolean getAutoCertificabile()
  {
    return this.autoCertificabile;
  }
  
  public void setAutoCertificabile(Boolean autoCertificabile)
  {
    this.autoCertificabile = autoCertificabile;
  }
  
  public Boolean getHasRappresentanteLegale()
  {
    return this.hasRappresentanteLegale;
  }
  
  public void setHasRappresentanteLegale(Boolean hasRappresentanteLegale)
  {
    this.hasRappresentanteLegale = hasRappresentanteLegale;
  }
}

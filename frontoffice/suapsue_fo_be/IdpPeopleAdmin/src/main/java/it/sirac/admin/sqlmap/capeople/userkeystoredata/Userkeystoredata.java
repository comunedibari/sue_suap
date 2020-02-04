package it.sirac.admin.sqlmap.capeople.userkeystoredata;

public class Userkeystoredata
{
  private String codiceFiscale;
  private String pin;
  private byte[] keyStore;
  
  public String getCodiceFiscale()
  {
    return this.codiceFiscale;
  }
  
  public void setCodiceFiscale(String codiceFiscale)
  {
    this.codiceFiscale = codiceFiscale;
  }
  
  public byte[] getKeyStore()
  {
    return this.keyStore;
  }
  
  public void setKeyStore(byte[] keyStore)
  {
    this.keyStore = keyStore;
  }
  
  public String getPin()
  {
    return this.pin;
  }
  
  public void setPin(String pin)
  {
    this.pin = pin;
  }
}

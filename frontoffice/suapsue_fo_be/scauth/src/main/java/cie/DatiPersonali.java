package cie;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;

public class DatiPersonali
  implements Serializable
{
  public static final int DATI_RAW_LENGTH = 400;
  String nome;
  String cognome;
  String sesso;
  String statura;
  String comuneEmittente;
  String comuneResidenza;
  String comuneNascita;
  String indirizzo;
  String dataNascita;
  String codiceFiscale;
  String dataEmissione;
  String dataScadenza;
  String cittadinanza;
  String attoNascita;
  String statoEsteroNascita;
  boolean espatrio;
  private byte[] datiPersonaliRaw;
  private int index;
  
  public DatiPersonali(byte[] datiPersonaliRaw)
    throws Exception
  {
    this.datiPersonaliRaw = datiPersonaliRaw;
    parseDatiPersonaliRaw();
  }
  
  private void parseDatiPersonaliRaw()
  {
    if (this.datiPersonaliRaw == null) {
      return;
    }
    this.comuneEmittente = copyString(this.datiPersonaliRaw, 8, 4);
    this.dataEmissione = copyString(this.datiPersonaliRaw, 14, 8);
    this.dataScadenza = copyString(this.datiPersonaliRaw, 24, 8);
    this.index = 32;
    this.cognome = copyToEnd();
    this.index += 2;
    this.nome = copyToEnd();
    this.index += 3;
    int len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.dataNascita = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.sesso = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.statura = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = 16;
    this.codiceFiscale = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.cittadinanza = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.comuneNascita = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.statoEsteroNascita = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = convertHexToByte(this.datiPersonaliRaw[(this.index - 1)]);
    this.attoNascita = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    len = this.datiPersonaliRaw[(this.index - 1)] - 48;
    this.comuneResidenza = copyString(this.datiPersonaliRaw, this.index, len);
    this.index += len;
    len = getLen(this.datiPersonaliRaw, this.index);
    if (len < 16) {
      this.index += 1;
    } else {
      this.index += 2;
    }
    this.indirizzo = copyString(this.datiPersonaliRaw, this.index, len);
    this.index = (this.index + len + 2);
    this.espatrio = (this.datiPersonaliRaw[this.index] == 83);
  }
  
  public HashMap parseDatiPersonaliRawFixed(byte[] datiPersonaliRaw0, String cardType)
  {
    if (datiPersonaliRaw0 == null) {
      return null;
    }
    int datiPersonaliDataLength = Integer.parseInt(new String(
      datiPersonaliRaw0, 0, 6), 16);
    
    int datiPersonaliRawLength = 0;
    if (cardType.equalsIgnoreCase("CIE")) {
      datiPersonaliRawLength = Integer.parseInt(new String(datiPersonaliRaw0, 0, 6), 16);
    } else if (cardType.equalsIgnoreCase("CNS")) {
      datiPersonaliRawLength = Integer.parseInt(new String(datiPersonaliRaw0, 0, 6), 16) + 6;
    }
    byte[] datiPersonaliRaw = new byte[datiPersonaliDataLength];
    System.arraycopy(datiPersonaliRaw0, 6, datiPersonaliRaw, 0, datiPersonaliDataLength);
    System.out.println("Dati Personali (len=" + datiPersonaliDataLength + ") = " + new String(datiPersonaliRaw));
    
    String[] personalDataAttributeNames = 
      { "comuneEmittente", "dataEmissione", "dataScadenza", "cognome", "nome", "dataNascita", "sesso", 
      "statura", "codiceFiscale", "codiceCittadinanza", "comuneNascita", "statoEsteroNascita", 
      "estremiAttoNascita", "comuneResidenza", "indirizzoResidenza", "validitaEspatrio" };
    
    HashMap datiPersonaliMap = new HashMap();
    
    int curFieldLength = 0;
    int curFieldOffset = 0;
    int curFieldNameIndex = 0;
    
    String curFieldName = null;
    String curFieldValue = null;
    while ((curFieldOffset < datiPersonaliDataLength) && (curFieldNameIndex < personalDataAttributeNames.length))
    {
      curFieldLength = Integer.parseInt(new String(
        datiPersonaliRaw, curFieldOffset, 2), 16);
      
      curFieldOffset += 2;
      if (curFieldLength > 0)
      {
        curFieldName = personalDataAttributeNames[curFieldNameIndex];
        curFieldValue = new String(datiPersonaliRaw, curFieldOffset, curFieldLength);
      }
      else
      {
        curFieldName = personalDataAttributeNames[curFieldNameIndex];
        curFieldValue = "";
      }
      datiPersonaliMap.put(curFieldName, curFieldValue);
      
      System.out.println("Offset = " + curFieldOffset + " Len Campo = " + curFieldLength + " Nome Attributo = " + curFieldName + " Valore = " + curFieldValue);
      
      curFieldOffset += curFieldLength;
      curFieldNameIndex++;
    }
    return datiPersonaliMap;
  }
  
  public void show()
  {
    System.out.println(" NOME:                    ".concat(
      String.valueOf(String.valueOf(this.nome))));
    System.out.println(" COGNOME:                 ".concat(
      String.valueOf(String.valueOf(this.cognome))));
    System.out.println(" SESSO:                   ".concat(
      String.valueOf(String.valueOf(this.sesso))));
    System.out.println(" STATURA:                 ".concat(
      String.valueOf(String.valueOf(this.statura))));
    System.out.println(" COMUNE EMITTENTE:        ".concat(
      String.valueOf(String.valueOf(this.comuneEmittente))));
    System.out.println(" COMUNE DI RESIDENZA:     ".concat(
      String.valueOf(String.valueOf(this.comuneResidenza))));
    System.out.println(" COMUNE DI NASCITA:       ".concat(
      String.valueOf(String.valueOf(this.comuneNascita))));
    System.out.println(" INDIRIZZO:               ".concat(
      String.valueOf(String.valueOf(this.indirizzo))));
    System.out.println(" DATA DI NASCITA:         ".concat(
      String.valueOf(String.valueOf(this.dataNascita))));
    System.out.println(" CODICE FISCALE:          ".concat(
      String.valueOf(String.valueOf(this.codiceFiscale))));
    System.out.println(" DATA DI EMISSIONE:       ".concat(
      String.valueOf(String.valueOf(this.dataEmissione))));
    System.out.println(" DATA DI SCADENZA:        ".concat(
      String.valueOf(String.valueOf(this.dataScadenza))));
    System.out.println(" CITTADINANZA:            ".concat(
      String.valueOf(String.valueOf(this.cittadinanza))));
    System.out.println(" ATTO DI NASCITA:         ".concat(
      String.valueOf(String.valueOf(this.attoNascita))));
    System.out.println(" STATO ESTERO DI NASCITA: ".concat(
      String.valueOf(String.valueOf(this.statoEsteroNascita))));
    System.out.print(" VALIDA PER ESPATRIO:     ");
    if (this.espatrio) {
      System.out.println("Si\n");
    } else {
      System.out.println("No\n");
    }
  }
  
  private String copyString(byte[] date, int index, int len)
  {
    byte[] dest = new byte[len];
    System.arraycopy(date, index, dest, 0, len);
    return new String(dest);
  }
  
  private String copyToEnd()
  {
    int indexEnd = this.index + 2;
    int len=0;
    for (len = 0; this.datiPersonaliRaw[indexEnd] != 48; len++) {
      indexEnd++;
    }
    if (len > 15)
    {
      String strApp = copyString(this.datiPersonaliRaw, this.index + 2, len);
      this.index = (this.index + len + 1);
      return strApp;
    }
    String strApp = copyString(this.datiPersonaliRaw, this.index + 1, len + 1);
    this.index = (this.index + len + 1);
    return strApp;
  }
  
  private byte convertHexToByte(byte val)
  {
    if (val < 65) {
      return (byte)(val - 48);
    }
    return (byte)(val - 65 + 10);
  }
  
  private byte getLen(byte[] date, int index)
  {
    byte digit1 = date[index];
    byte digit2 = date[(index + 1)];
    if (digit2 < 64) {
      return (byte)(digit2 - 48 + 16 * (digit1 - 48));
    }
    return (byte)(digit1 - 48);
  }
  
  public String getNome()
  {
    if (this.nome == null) {
      parseDatiPersonaliRaw();
    }
    return this.nome;
  }
  
  public String getCognome()
  {
    if (this.cognome == null) {
      parseDatiPersonaliRaw();
    }
    return this.cognome;
  }
  
  public String getCodiceFiscale()
  {
    if (this.codiceFiscale == null) {
      parseDatiPersonaliRaw();
    }
    return this.codiceFiscale;
  }
  
  public String getSesso()
  {
    if (this.codiceFiscale == null) {
      parseDatiPersonaliRaw();
    }
    return this.sesso;
  }
  
  public String getAttoNascita()
  {
    return this.attoNascita;
  }
  
  public String getCittadinanza()
  {
    return this.cittadinanza;
  }
  
  public String getComuneEmittente()
  {
    return this.comuneEmittente;
  }
  
  public String getComuneNascita()
  {
    return this.comuneNascita;
  }
  
  public String getComuneResidenza()
  {
    return this.comuneResidenza;
  }
  
  public String getDataEmissione()
  {
    return this.dataEmissione;
  }
  
  public String getDataNascita()
  {
    return this.dataNascita;
  }
  
  public String getDataScadenza()
  {
    return this.dataScadenza;
  }
  
  public byte[] getDatiPersonaliRaw()
  {
    return this.datiPersonaliRaw;
  }
  
  public boolean isEspatrio()
  {
    return this.espatrio;
  }
  
  public String getIndirizzo()
  {
    return this.indirizzo;
  }
  
  public String getStatoEsteroNascita()
  {
    return this.statoEsteroNascita;
  }
  
  public String getStatura()
  {
    return this.statura;
  }
}

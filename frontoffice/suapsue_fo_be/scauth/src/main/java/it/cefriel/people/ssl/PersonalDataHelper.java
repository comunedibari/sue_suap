package it.cefriel.people.ssl;

import it.people.sirac.authentication.beans.PplUserDataExtended;
import it.people.sirac.idp.authentication.AuthenticationClientAdapter;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.util.Utilities;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PersonalDataHelper
{
  private static final Logger logger = LoggerFactory.getLogger(PersonalDataHelper.class);
  
  public static Map parseDatiPersonaliRawFixed(byte[] datiPersonaliRaw0, String cardType)
  {
    if (datiPersonaliRaw0 == null) {
      return null;
    }
    int datiPersonaliLength = 0;
    if ("CIE".equalsIgnoreCase(cardType)) {
      datiPersonaliLength = Integer.parseInt(new String(
        datiPersonaliRaw0, 0, 6), 16);
    } else if (("CNS".equalsIgnoreCase(cardType)) || 
      ("CRS".equalsIgnoreCase(cardType))) {
      datiPersonaliLength = Integer.parseInt(new String(
        datiPersonaliRaw0, 0, 6), 16) - 6;
    }
    byte[] datiPersonaliRaw = new byte[datiPersonaliLength];
    System.arraycopy(datiPersonaliRaw0, 6, datiPersonaliRaw, 0, 
      datiPersonaliLength);
    
    String[] personalDataAttributeNames = { "ComuneEmittente", 
      "DataEmissione", "DataScadenza", "cognome", "nome", 
      "dataNascita", "sesso", "Statura", "codiceFiscale", 
      "CodiceCittadinanza", "luogoNascita", "statoEsteroNascita", 
      "EstremiAttoNascita", "cittaResidenza", "indirizzoResidenza", 
      "ValiditaEspatrio" };
    
    HashMap datiPersonaliMap = new HashMap();
    
    int curFieldLength = 0;
    int curFieldOffset = 0;
    int curFieldNameIndex = 0;
    
    String curFieldName = null;
    String curFieldValue = null;
    while ((curFieldOffset < datiPersonaliLength) && 
      (curFieldNameIndex < personalDataAttributeNames.length))
    {
      curFieldLength = Integer.parseInt(new String(datiPersonaliRaw, 
        curFieldOffset, 2), 16);
      
      curFieldOffset += 2;
      if (curFieldLength > 0)
      {
        curFieldName = personalDataAttributeNames[curFieldNameIndex];
        curFieldValue = new String(datiPersonaliRaw, curFieldOffset, 
          curFieldLength);
      }
      else
      {
        curFieldName = personalDataAttributeNames[curFieldNameIndex];
        curFieldValue = "";
      }
      datiPersonaliMap.put(curFieldName, curFieldValue);
      
      System.out.println("Offset = " + curFieldOffset + " Len Campo = " + 
        curFieldLength + " Nome Attributo = " + curFieldName + 
        " Valore = " + curFieldValue);
      
      curFieldOffset += curFieldLength;
      curFieldNameIndex++;
    }
    return datiPersonaliMap;
  }
  
  public static RegBean getUserDataFromStorage(String codiceFiscale, AuthenticationClientAdapter authWS)
    throws Exception
  {
    if (codiceFiscale == null) {
      throw new Exception("getUserDataFromStorage - Codice fiscale non valido: " + codiceFiscale);
    }
    RegBean rb = new RegBean();
    try
    {
      rb = authWS.getUserDataWithCodiceFiscale(codiceFiscale);
    }
    catch (RemoteException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("getUserDataFromStorage - errore durante la lettura dei dati dallo storage: " + e.getMessage());
        throw e;
      }
    }
    return rb;
  }
  
  public static RegBean mapToRegBean(Map attributesMap)
  {
    RegBean rb = new RegBean();
    
    rb.setNome(getStringAttributeNotNull((String)attributesMap.get("nome")));
    rb.setCognome(getStringAttributeNotNull((String)attributesMap.get("cognome")));
    rb.setCodiceFiscale(getStringAttributeNotNull((String)attributesMap.get("codiceFiscale")));
    rb.setSesso(getStringAttributeNotNull((String)attributesMap.get("sesso")));
    rb.setDataNascita(getStringAttributeNotNull((String)attributesMap.get("dataNascita")));
    rb.setIndirizzoResidenza(getStringAttributeNotNull((String)attributesMap.get("indirizzoResidenza")));
    rb.setIndirizzoDomicilio(getStringAttributeNotNull((String)attributesMap.get("indirizzoDomicilio")));
    rb.setCittaResidenza(getStringAttributeNotNull((String)attributesMap.get("cittaResidenza")));
    rb.setCittaDomicilio(getStringAttributeNotNull((String)attributesMap.get("cittaDomicilio")));
    rb.setLuogoNascita(getStringAttributeNotNull((String)attributesMap.get("luogoNascita")));
    rb.setCapResidenza(getStringAttributeNotNull((String)attributesMap.get("capResidenza")));
    rb.setCapDomicilio(getStringAttributeNotNull((String)attributesMap.get("capDomicilio")));
    rb.setProvinciaResidenza(getStringAttributeNotNull((String)attributesMap.get("provinciaResidenza")));
    rb.setProvinciaDomicilio(getStringAttributeNotNull((String)attributesMap.get("provinciaDomicilio")));
    rb.setProvinciaNascita(getStringAttributeNotNull((String)attributesMap.get("provinciaNascita")));
    rb.setStatoResidenza(getStringAttributeNotNull((String)attributesMap.get("statoResidenza")));
    rb.setStatoDomicilio(getStringAttributeNotNull((String)attributesMap.get("statoDomicilio")));
    rb.setStatoNascita(getStringAttributeNotNull((String)attributesMap.get("statoNascita")));
    rb.setLavoro(getStringAttributeNotNull((String)attributesMap.get("lavoro")));
    rb.setTelefono(getStringAttributeNotNull((String)attributesMap.get("telefono")));
    rb.setCellulare(getStringAttributeNotNull((String)attributesMap.get("cellulare")));
    rb.setEmail(getStringAttributeNotNull((String)attributesMap.get("emailAddress")));
    rb.setTitolo(getStringAttributeNotNull((String)attributesMap.get("titolo")));
    rb.setIdComune(getStringAttributeNotNull((String)attributesMap.get("idComuneRegistrazione")));
    rb.setCartaIdentita(getStringAttributeNotNull((String)attributesMap.get("cartaIdentita")));
    rb.setPassword(getStringAttributeNotNull((String)attributesMap.get("password")));
    rb.setPin(getStringAttributeNotNull((String)attributesMap.get("pin")));
    rb.setDomicilioElettronico(getStringAttributeNotNull((String)attributesMap.get("domicilioElettronico")));
    
    return rb;
  }
  
  public static Map regBeanToMap(RegBean rb)
  {
    Map map = new HashMap();
    
    map.put("nome", getStringAttributeNotNull(rb.getNome()));
    map.put("cognome", getStringAttributeNotNull(rb.getCognome()));
    map.put("codiceFiscale", getStringAttributeNotNull(rb.getCodiceFiscale()));
    map.put("sesso", getStringAttributeNotNull(rb.getSesso()));
    map.put("dataNascita", getStringAttributeNotNull(rb.getDataNascita()));
    map.put("indirizzoResidenza", getStringAttributeNotNull(rb.getIndirizzoResidenza()));
    map.put("indirizzoDomicilio", getStringAttributeNotNull(rb.getIndirizzoDomicilio()));
    map.put("cittaResidenza", getStringAttributeNotNull(rb.getCittaResidenza()));
    map.put("cittaDomicilio", getStringAttributeNotNull(rb.getCittaDomicilio()));
    map.put("luogoNascita", getStringAttributeNotNull(rb.getLuogoNascita()));
    map.put("capResidenza", getStringAttributeNotNull(rb.getCapResidenza()));
    map.put("capDomicilio", getStringAttributeNotNull(rb.getCapDomicilio()));
    map.put("provinciaResidenza", getStringAttributeNotNull(rb.getProvinciaResidenza()));
    map.put("provinciaDomicilio", getStringAttributeNotNull(rb.getProvinciaDomicilio()));
    map.put("provinciaNascita", getStringAttributeNotNull(rb.getProvinciaNascita()));
    map.put("statoResidenza", getStringAttributeNotNull(rb.getStatoResidenza()));
    map.put("statoDomicilio", getStringAttributeNotNull(rb.getStatoDomicilio()));
    map.put("statoNascita", getStringAttributeNotNull(rb.getStatoNascita()));
    map.put("lavoro", getStringAttributeNotNull(rb.getLavoro()));
    map.put("telefono", getStringAttributeNotNull(rb.getTelefono()));
    map.put("cellulare", getStringAttributeNotNull(rb.getCellulare()));
    map.put("emailAddress", getStringAttributeNotNull(rb.getEmail()));
    map.put("titolo", getStringAttributeNotNull(rb.getTitolo()));
    map.put("idComuneRegistrazione", getStringAttributeNotNull(rb.getIdComune()));
    map.put("cartaIdentita", getStringAttributeNotNull(rb.getCartaIdentita()));
    map.put("password", getStringAttributeNotNull(rb.getPassword()));
    map.put("pin", getStringAttributeNotNull(rb.getPin()));
    map.put("domicilioElettronico", getStringAttributeNotNull(rb.getDomicilioElettronico()));
    
    return map;
  }
  
  public static PplUserDataExtended authentication_getUserDataWithCodiceFiscale(String COD_FIS, AuthenticationClientAdapter wsAuth)
    throws Exception
  {
    RegBean rb = null;
    if (logger.isDebugEnabled())
    {
      logger.debug("authentication_getUserDataWithCodiceFiscale() - -------------------------------");
      logger
        .debug("authentication_getUserDataWithCodiceFiscale() - Richiesta profilo per COD_FIS: " + 
        COD_FIS);
    }
    PplUserDataExtended userData = null;
    try
    {
      rb = wsAuth.getUserDataWithCodiceFiscale(COD_FIS);
      
      userData = fillUserData(rb);
      if (logger.isDebugEnabled()) {
        logger.debug("authentication_getUserDataWithCodiceFiscale()" + 
          userData.toString());
      }
    }
    catch (RemoteException re)
    {
      if (logger.isDebugEnabled()) {
        logger.debug("authentication_getUserDataWithCodiceFiscale()" + re.getMessage());
      }
    }
    return userData;
  }
  
  public static PplUserDataExtended authentication_getUserDataWithCIE(String CIE_ID, AuthenticationClientAdapter wsAuth)
    throws Exception
  {
    RegBean rb = null;
    if (logger.isDebugEnabled())
    {
      logger.debug("authentication_getUserDataWithCIE() - -------------------------------");
      logger
        .debug("authentication_getUserDataWithCIE() - Richiesta profilo per CIE: " + 
        CIE_ID);
    }
    PplUserDataExtended userData = null;
    try
    {
      rb = wsAuth.getUserDataWithCIE(CIE_ID);
      

      userData = fillUserData(rb);
      if (logger.isDebugEnabled()) {
        logger.debug("authentication_getUserData()" + 
          userData.toString());
      }
    }
    catch (RemoteException re)
    {
      if (logger.isDebugEnabled()) {
        logger.debug("authentication_getUserData()" + re.getMessage());
      }
    }
    return userData;
  }
  
  protected static PplUserDataExtended fillUserData(RegBean rb)
  {
    PplUserDataExtended userData = new PplUserDataExtended();
    
    userData.setNome(rb.getNome());
    userData.setCognome(rb.getCognome());
    userData.setDataNascita(rb.getDataNascita());
    userData.setCodiceFiscale(rb.getCodiceFiscale());
    userData.setEmailaddress(rb.getEmail());
    userData.setCapDomicilio(rb.getCapDomicilio());
    userData.setCapResidenza(rb.getCapResidenza());
    userData.setCittaDomicilio(rb.getCittaDomicilio());
    userData.setCittaResidenza(rb.getCittaResidenza());
    userData.setIndirizzoDomicilio(rb.getIndirizzoDomicilio());
    userData.setIndirizzoResidenza(rb.getIndirizzoResidenza());
    userData.setLavoro(rb.getLavoro());
    userData.setLuogoNascita(rb.getLuogoNascita());
    userData.setProvinciaDomicilio(rb.getProvinciaDomicilio());
    userData.setProvinciaNascita(rb.getProvinciaNascita());
    userData.setProvinciaResidenza(rb.getProvinciaResidenza());
    userData.setSesso(rb.getSesso());
    userData.setStatoDomicilio(rb.getStatoDomicilio());
    userData.setStatoResidenza(rb.getStatoResidenza());
    userData.setStatoNascita(rb.getStatoNascita());
    userData.setTelefono(rb.getTelefono());
    userData.setTitolo(rb.getTitolo());
    userData.setCellulare(rb.getCellulare());
    userData.setIdComuneRegistrazione(rb.getIdComune());
    
    return userData;
  }
  
  public static Map createAttributesMapfromUserData(CardUserData userData)
  {
    HashMap attrMap = new HashMap();
    attrMap.put("nome", getStringAttributeNotNull(userData.getNome()));
    attrMap
      .put("cognome", 
      getStringAttributeNotNull(userData.getCognome()));
    attrMap.put("codiceFiscale", getStringAttributeNotNull(userData
      .getCodiceFiscale()));
    attrMap.put("emailAddress", getStringAttributeNotNull(userData
      .getEmail()));
    
    String dateString = getStringAttributeNotNull(userData.getDataNascita());
    
    String recognizedDate = "";
    try
    {
      recognizedDate = Utilities.formatDateToString(Utilities.parseDateString(dateString));
    }
    catch (Exception localException) {}
    attrMap.put("dataNascita", recognizedDate);
    
    return attrMap;
  }
  
  public static Map createAttributesMapfromPplUserDataExtended(PplUserDataExtended userData)
  {
    HashMap attrMap = new HashMap();
    attrMap.put("Nome", getStringAttributeNotNull(userData.getNome()));
    attrMap.put("Cognome", getStringAttributeNotNull(userData.getCognome()));
    attrMap.put("DataNascita", getStringAttributeNotNull(userData.getDataNascita()));
    attrMap.put("CodiceFiscale", getStringAttributeNotNull(userData.getCodiceFiscale()));
    attrMap.put("EmailAddressPersonale", getStringAttributeNotNull(userData.getEmailaddress()));
    attrMap.put("CapDomicilio", getStringAttributeNotNull(userData.getCapDomicilio()));
    attrMap.put("CapResidenza", getStringAttributeNotNull(userData.getCapResidenza()));
    attrMap.put("CittaDomicilio", getStringAttributeNotNull(userData.getCittaDomicilio()));
    attrMap.put("CittaResidenza", getStringAttributeNotNull(userData.getCittaResidenza()));
    attrMap.put("IndirizzoDomicilio", getStringAttributeNotNull(userData.getIndirizzoDomicilio()));
    attrMap.put("IndirizzoResidenza", getStringAttributeNotNull(userData.getIndirizzoResidenza()));
    attrMap.put("Lavoro", getStringAttributeNotNull(userData.getLavoro()));
    attrMap.put("LuogoNascita", getStringAttributeNotNull(userData.getLuogoNascita()));
    attrMap.put("ProvinciaDomicilio", getStringAttributeNotNull(userData.getProvinciaDomicilio()));
    attrMap.put("ProvinciaNascita", getStringAttributeNotNull(userData.getProvinciaNascita()));
    attrMap.put("ProvinciaResidenza", getStringAttributeNotNull(userData.getProvinciaResidenza()));
    attrMap.put("Sesso", getStringAttributeNotNull(userData.getSesso()));
    attrMap.put("StatoDomicilio", getStringAttributeNotNull(userData.getStatoDomicilio()));
    attrMap.put("StatoResidenza", getStringAttributeNotNull(userData.getStatoResidenza()));
    attrMap.put("StatoNascita", getStringAttributeNotNull(userData.getStatoNascita()));
    attrMap.put("Titolo", getStringAttributeNotNull(userData.getTitolo()));
    attrMap.put("Telefono", getStringAttributeNotNull(userData.getTelefono()));
    attrMap.put("Cellulare", getStringAttributeNotNull(userData.getCellulare()));
    
    return attrMap;
  }
  
  private static String getStringAttributeNotNull(String attribute)
  {
    if (attribute == null) {
      return "";
    }
    return attribute;
  }
  
  public static RegBean fillUserDataFromCardWithUserDataFromStorage(RegBean dataFromCard, RegBean dataFromStorage)
  {
    dataFromCard.setEmail(dataFromStorage.getEmail());
    dataFromCard.setCapDomicilio(dataFromStorage.getCapDomicilio());
    dataFromCard.setCapResidenza(dataFromStorage.getCapResidenza());
    dataFromCard.setCittaResidenza(dataFromStorage.getCittaResidenza());
    dataFromCard.setCittaDomicilio(dataFromStorage.getCittaDomicilio());
    dataFromCard.setLuogoNascita(dataFromStorage.getLuogoNascita());
    dataFromCard.setIndirizzoResidenza(dataFromStorage.getIndirizzoResidenza());
    dataFromCard.setIndirizzoDomicilio(dataFromStorage.getIndirizzoDomicilio());
    dataFromCard.setLavoro(dataFromStorage.getLavoro());
    dataFromCard.setProvinciaDomicilio(dataFromStorage.getProvinciaDomicilio());
    dataFromCard.setProvinciaNascita(dataFromStorage.getProvinciaNascita());
    dataFromCard.setProvinciaResidenza(dataFromStorage.getProvinciaResidenza());
    dataFromCard.setStatoDomicilio(dataFromStorage.getStatoDomicilio());
    dataFromCard.setStatoResidenza(dataFromStorage.getStatoResidenza());
    dataFromCard.setStatoNascita(dataFromStorage.getStatoNascita());
    dataFromCard.setLavoro(dataFromStorage.getLavoro());
    dataFromCard.setTelefono(dataFromStorage.getTelefono());
    dataFromCard.setCellulare(dataFromStorage.getCellulare());
    dataFromCard.setTitolo(dataFromStorage.getTitolo());
    dataFromCard.setIdComune(dataFromStorage.getIdComune());
    dataFromCard.setCartaIdentita(dataFromStorage.getCartaIdentita());
    dataFromCard.setDomicilioElettronico(dataFromStorage.getDomicilioElettronico());
    dataFromCard.setPassword(dataFromStorage.getPassword());
    dataFromCard.setPin(dataFromStorage.getPin());
    
    String nomeFromCard = dataFromCard.getNome();
    if ((nomeFromCard == null) || ("".equalsIgnoreCase(nomeFromCard))) {
      dataFromCard.setNome(dataFromStorage.getNome());
    }
    String cognomeFromCard = dataFromCard.getCognome();
    if ((cognomeFromCard == null) || ("".equalsIgnoreCase(cognomeFromCard))) {
      dataFromCard.setCognome(dataFromStorage.getCognome());
    }
    String codiceFiscaleFromCard = dataFromCard.getCodiceFiscale();
    if ((codiceFiscaleFromCard == null) || ("".equalsIgnoreCase(codiceFiscaleFromCard))) {
      dataFromCard.setCodiceFiscale(dataFromStorage.getCodiceFiscale());
    }
    String sessoFromCard = dataFromCard.getSesso();
    if ((sessoFromCard == null) || ("".equalsIgnoreCase(sessoFromCard))) {
      dataFromCard.setSesso(dataFromStorage.getSesso());
    }
    String dataNascitaFromCard = dataFromCard.getDataNascita();
    if ((dataNascitaFromCard == null) || ("".equalsIgnoreCase(dataNascitaFromCard))) {
      dataFromCard.setDataNascita(dataFromStorage.getDataNascita());
    }
    return dataFromCard;
  }
  
  public static PplUserDataExtended fillUserDataFromCertificateWithUserDataFromDB(CardUserData dataFromCertificate, PplUserDataExtended dataFromDB)
  {
    PplUserDataExtended pplUserData = new PplUserDataExtended();
    
    pplUserData.setEmailaddress(dataFromDB.getEmailaddress());
    pplUserData.setCapDomicilio(dataFromDB.getCapDomicilio());
    pplUserData.setCapResidenza(dataFromDB.getCapResidenza());
    pplUserData.setCittaDomicilio(dataFromDB.getCittaDomicilio());
    pplUserData.setIndirizzoDomicilio(dataFromDB.getIndirizzoDomicilio());
    pplUserData.setLavoro(dataFromDB.getLavoro());
    pplUserData.setLuogoNascita(dataFromDB.getLuogoNascita());
    pplUserData.setProvinciaDomicilio(dataFromDB.getProvinciaDomicilio());
    pplUserData.setProvinciaNascita(dataFromDB.getProvinciaNascita());
    pplUserData.setProvinciaResidenza(dataFromDB.getProvinciaResidenza());
    pplUserData.setStatoDomicilio(dataFromDB.getStatoDomicilio());
    pplUserData.setStatoResidenza(dataFromDB.getStatoResidenza());
    pplUserData.setTelefono(dataFromDB.getTelefono());
    

    pplUserData.setCittaResidenza(dataFromDB.getCittaResidenza());
    pplUserData.setIndirizzoResidenza(dataFromDB.getIndirizzoResidenza());
    pplUserData.setSesso(dataFromDB.getSesso());
    

    String nome = dataFromCertificate.getNome();
    if ((nome == null) || (nome.equalsIgnoreCase(""))) {
      pplUserData.setNome(dataFromDB.getNome());
    } else {
      pplUserData.setNome(nome);
    }
    String cognome = dataFromCertificate.getCognome();
    if ((cognome == null) || (cognome.equalsIgnoreCase(""))) {
      pplUserData.setCognome(dataFromDB.getCognome());
    } else {
      pplUserData.setCognome(cognome);
    }
    String codiceFiscale = dataFromCertificate.getCodiceFiscale();
    if ((codiceFiscale == null) || (codiceFiscale.equalsIgnoreCase(""))) {
      pplUserData.setCodiceFiscale(dataFromDB.getCodiceFiscale());
    } else {
      pplUserData.setCodiceFiscale(codiceFiscale);
    }
    String dataNascita = dataFromCertificate.getDataNascita();
    if ((dataNascita == null) || (dataNascita.equalsIgnoreCase(""))) {
      pplUserData.setDataNascita(dataFromDB.getDataNascita());
    } else {
      pplUserData.setDataNascita(dataNascita);
    }
    return pplUserData;
  }
}


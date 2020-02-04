package it.sirac.admin.sqlmap.capeople.userdata;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.SqlMapClient;
import it.sirac.admin.common.TableDAO;
import it.sirac.admin.faces.Manager;
import it.sirac.admin.sqlmap.capeople.IbatisCAPeople;
import it.sirac.admin.sqlmap.capeople.userkeystoredata.KeystoreGenerator;
import it.sirac.admin.sqlmap.capeople.userkeystoredata.Userkeystoredata;
import it.sirac.admin.sqlmap.util.MailSender;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserdataDAO
  implements TableDAO
{
	private static Logger logger = LoggerFactory.getLogger(UserdataDAO.class);
  public static final String ERROR_INSERT = "Errore nell'inserimento dell'utente";
  public static final String ERROR_UPDATE = "Errore nell'aggiornamento dell'utente";
  public static final String ERROR_DELETE = "Errore nella cancellazione dell'utente";
  public static final String ERROR_PIN = "Errore nella generazione del nuovo pin";
  public static final String ERROR_SMTP = "Errore durante l'invio e-mail";
  public static final String ERROR_DUPLICATE = "L'utente che si sta inserendo risulta gi� presente";
  private static final String CONFIGURATION_FILE = "/properties/userdata.properties";
  private SqlMapClient sqlMap = null;
  private boolean ascending;
  private String newPwd = null;
  private String newPin = null;
  private Userkeystoredata newUserKeystoreData = null;
  private String sortColumn = null;
  private HashMap headers;
  private String whereClause = null;
  private Properties properties = null;
  private String error;
  private Date newDataRegistrazione = null;
  private Date newDataAttivazione = null;
  
  public String getError()
  {
    return this.error;
  }
  
  public void setError(String error)
  {
    this.error = error;
  }
  
  public UserdataDAO()
  {
    this.sqlMap = IbatisCAPeople.getInstance();
    logger.debug("UserdataDAO() - Inizializzazione...");
    this.properties = loadProperties("/properties/userdata.properties");
    logger.debug("UserdataDAO() - Inizializzazione completata.");
  }
  
  public boolean isShowPin()
  {
    String showPin = this.properties.getProperty("users.showPin");
    return Boolean.valueOf(showPin).booleanValue();
  }
  
  public boolean isShowPassword()
  {
    String showPassword = this.properties.getProperty("users.showPassword");
    return Boolean.valueOf(showPassword).booleanValue();
  }
  
  public boolean mustSendNewPwdMail()
  {
    String sendMail = this.properties.getProperty("users.sendNewPwdMail");
    return Boolean.valueOf(sendMail).booleanValue();
  }
  
  public boolean mustSendNewPinMail()
  {
    String sendMail = this.properties.getProperty("users.sendNewPinMail");
    return Boolean.valueOf(sendMail).booleanValue();
  }
  
  public boolean mustSendNewUserMail()
  {
    String sendMail = this.properties.getProperty("users.sendNewUserMail");
    return Boolean.valueOf(sendMail).booleanValue();
  }
  
  public List getList()
  {
    List list = null;
    try
    {
      if (this.sqlMap != null) {
        list = this.sqlMap.queryForList("getUserdataList", null);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public List getPaginatedList(int exclude, int maxrows)
  {
    logger.debug("getPaginatedList() - start");
    List list = null;
    try
    {
      if (this.sqlMap != null)
      {
        Map queryParameters = new HashMap();
        queryParameters.put("exclude", new Integer(exclude));
        queryParameters.put("max", new Integer(maxrows));
        if (this.sortColumn != null)
        {
          int columnIndex = Integer.parseInt(this.sortColumn.substring(3));
          String columnName = (String)getColumnName().get(columnIndex);
          queryParameters.put("column", columnName);
          if (this.ascending) {
            queryParameters.put("ascending", "ASC");
          } else {
            queryParameters.put("ascending", "DESC");
          }
        }
        else
        {
          queryParameters.put("column", "cognome");
          queryParameters.put("ascending", "ASC");
        }
        if (this.whereClause != null) {
          queryParameters.put("whereClause", this.whereClause);
        }
        list = this.sqlMap.queryForList("getUserdataList", queryParameters);
      }
      logger.debug("getPaginatedList() - end");
    }
    catch (Exception e)
    {
      logger.error("getPaginatedList() - Errore durante la costruzione dell'elenco utenti. Causa: " + e.getMessage());
      logger.debug("getPaginatedList() - end");
      e.printStackTrace();
    }
    return list;
  }
  
  public int getRowsCount()
  {
    try
    {
      Map queryParameters = new HashMap();
      if (this.whereClause != null) {
        queryParameters.put("whereClause", this.whereClause);
      }
      return ((Integer)this.sqlMap.queryForObject("getUserdataCount", queryParameters)).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  public String insert(Object current)
  {
    logger.debug("insert() - start");
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null)
    {
      try
      {
        Properties globalProperties = new Properties();
        URL globalUrl = getClass().getResource("/properties/app.properties");
        globalProperties.load(globalUrl.openStream());
        
        Timestamp now = new Timestamp(this.newDataRegistrazione.getTime());
        ((Userdata)current).setDataAttivazione(now);
        ((Userdata)current).setDataRegistrazione(now);
        

        generateNewPassword();
        logger.debug("insert() - Generata nuova password");
        



        generateNewPin();
        logger.debug("insert() - Generato nuovo PIN e nuovo keystore.");
        


        ((Userdata)current).setIdComune(globalProperties.getProperty("idcomune"));
        if ((((Userdata)current).getIndirizzoDomicilio() == null) || ("".equals(((Userdata)current).getIndirizzoDomicilio()))) {
          ((Userdata)current).setIndirizzoDomicilio(((Userdata)current).getIndirizzoResidenza());
        }
        if ((((Userdata)current).getCapDomicilio() == null) || ("".equals(((Userdata)current).getCapDomicilio()))) {
          ((Userdata)current).setCapDomicilio(((Userdata)current).getCapResidenza());
        }
        if ((((Userdata)current).getCittaDomicilio() == null) || ("".equals(((Userdata)current).getCittaDomicilio()))) {
          ((Userdata)current).setCittaDomicilio(((Userdata)current).getCittaResidenza());
        }
        if ((((Userdata)current).getProvinciaDomicilio() == null) || ("".equals(((Userdata)current).getProvinciaDomicilio()))) {
          ((Userdata)current).setProvinciaDomicilio(((Userdata)current).getProvinciaResidenza());
        }
        if ((((Userdata)current).getStatoDomicilio() == null) || ("".equals(((Userdata)current).getStatoDomicilio()))) {
          ((Userdata)current).setStatoDomicilio(((Userdata)current).getStatoResidenza());
        }
        logger.debug("insert() - Inizio aggiornamento dati su DB");
        logger.debug("insert() - Inserimento nuovo profilo per l'utente " + ((Userdata)current).getCodiceFiscale() + " in corso...");
        sqlMap.insert("insertUserdata", current);
        logger.debug("insert() - Inserimento nuovo profilo completato.");
        
        logger.debug("insert() - Rimozione precedente keystore per l'utente " + ((Userdata)current).getCodiceFiscale() + " dal database in corso...");
        sqlMap.delete("deleteUserkeystoredata", this.newUserKeystoreData);
        logger.debug("insert() - Rimozione completata.");
        
        logger.debug("insert() - Inserimento del nuovo keystore per l'utente " + ((Userdata)current).getCodiceFiscale() + " nel database in corso...");
        sqlMap.insert("insertUserkeystoredata", this.newUserKeystoreData);
        logger.debug("insert() - Inserimento del nuovo keystore completato.");
        
        logger.debug("insert() - Fine aggiornamento dati su DB");
      }
      catch (NestedSQLException ex)
      {
        String cause = ex.getCause().getMessage();
        if (cause.startsWith("Duplicate entry"))
        {
          setError("L'utente che si sta inserendo risulta gi� presente");
          logger.error("insert() - Errore durante l'aggiornamento dei dati su DB: utente gi� presente.");
        }
        else
        {
          setError("Errore nell'inserimento dell'utente");
          logger.error("insert() - Errore durante l'aggiornamento dei dati su DB. Causa: " + ex.getMessage());
        }
        return "failed";
      }
      catch (Exception e)
      {
        setError("Errore nell'inserimento dell'utente");
        logger.error("insert() - Si � verificato un errore: " + e.getMessage());
        return "failed";
      }
      if (mustSendNewUserMail()) {
        try
        {
          logger.debug("insert() - Invio mail per comunicazione attivazione nuova utenza in corso...");
          sendNewUserMail((Userdata)current);
          logger.debug("insert() - Invio mail per comunicazione attivazione nuova utenza completato.");
        }
        catch (RuntimeException e)
        {
          logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
          e.printStackTrace();
        }
      }
    }
    setError("");
    logger.error("insert() - end.");
    return "success";
  }
  
  public String update(Object current)
  {
    logger.debug("update() - start");
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null)
    {
      try
      {
        logger.debug("update() - Aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database...");
        sqlMap.update("updateUserdata", current);
        logger.debug("update() - Aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database completato.");
        if (this.newUserKeystoreData != null)
        {
          logger.debug("update() - Aggiornamento del keystore dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database...");
          sqlMap.delete("deleteUserkeystoredata", this.newUserKeystoreData);
          sqlMap.insert("insertUserkeystoredata", this.newUserKeystoreData);
          logger.debug("update() - Aggiornamento del keystore dell'utente completato.");
        }
      }
      catch (SQLException e)
      {
        logger.error("update() - Errore durante l'aggiornamento del profilo dell'utente " + ((Userdata)current).getCodiceFiscale() + " nel database. Causa: " + e.getMessage());
        logger.debug("update() - end");
        setError("Errore nell'aggiornamento dell'utente");
        return "failed";
      }
    }
    else
    {
      logger.error("update() - Errore durante la lettura delle informazioni di configurazione per l'accesso al database");
      setError("Errore nell'aggiornamento dell'utente");
      return "failed";
    }
    if ((((Userdata)current).isPasswordChanged()) && (mustSendNewPwdMail())) {
      try
      {
        logger.debug("insert() - Invio mail per comunicazione variazione password in corso...");
        sendNewPwdMail((Userdata)current);
        logger.debug("insert() - Invio mail per comunicazione variazione password completata.");
      }
      catch (Exception e)
      {
        logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
        logger.debug("update() - end");
        setError("Errore durante l'invio e-mail");
        return "failed";
      }
    }
    if ((((Userdata)current).isPinChanged()) && (mustSendNewPinMail())) {
      try
      {
        logger.debug("insert() - Invio mail per comunicazione variazione PIN in corso...");
        sendNewPinMail((Userdata)current);
        logger.debug("insert() - Invio mail per comunicazione variazione PIN completata.");
      }
      catch (Exception e)
      {
        logger.error("insert() - Errore durante l'invio mail. Causa: " + e.getMessage());
        logger.debug("update() - end");
        setError("Errore durante l'invio e-mail");
        return "failed";
      }
    }
    setError("");
    logger.debug("update() - end");
    return "success";
  }
  
  public String delete(Object current)
  {
    logger.debug("delete() - start");
    SqlMapClient sqlMap = IbatisCAPeople.getInstance();
    if (sqlMap != null) {
      try
      {
        logger.debug("delete() - Rimozione dati con profilo utente dal database in corso...");
        sqlMap.delete("deleteUserdata", current);
        logger.debug("delete() - Rimozione dati con profilo utente dal database completata.");
        Userkeystoredata keyStoreData = new Userkeystoredata();
        keyStoreData.setCodiceFiscale(((Userdata)current).getCodiceFiscale());
        logger.debug("delete() - Rimozione del keystore di firma dal database in corso...");
        sqlMap.delete("deleteUserkeystoredata", keyStoreData);
        logger.debug("delete() - Rimozione del keystore di firma dal database completata.");
      }
      catch (SQLException e)
      {
        logger.error("delete() - Errore durante la rimozione dei dati del profilo utente dal database. Causa: " + e.getMessage());
        logger.debug("delete() - end");
        setError("Errore nella cancellazione dell'utente");
        return "failed";
      }
    }
    logger.debug("delete() - end");
    setError("");
    return "success";
  }
  
  public boolean isAscending()
  {
    return this.ascending;
  }
  
  public void setAscending(boolean ascending)
  {
    this.ascending = ascending;
  }
  
  public String getSortColumn()
  {
    return this.sortColumn;
  }
  
  public void setSortColumn(String sortColumn)
  {
    this.sortColumn = sortColumn;
  }
  
  public HashMap getHeaders()
  {
    if (this.headers == null)
    {
      this.headers = new HashMap();
      this.headers.put("codiceFiscale", "Codice Fiscale");
      
      this.headers.put("nome", "Nome");
      this.headers.put("cognome", "Cognome");
      this.headers.put("sesso", "Sesso");
      this.headers.put("dataNascita", "Data nascita");
      this.headers.put("luogoNascita", "Comune nascita");
      this.headers.put("provinciaNascita", "Prov. nascita");
      














      this.headers.put("email", "Email");
      this.headers.put("domicilioElettronico", "Domicilio Elettronico");
      


      this.headers.put("dataRegistrazione", "Data Registrazione");
      this.headers.put("dataAttivazione", "Data Attivazione");
      this.headers.put("status", "Stato");
    }
    return this.headers;
  }
  
  public Vector getColumnName()
  {
    Vector columnName = new Vector();
    columnName.add("CODICE_FISCALE");
    columnName.add("CARTA_IDENTITA");
    columnName.add("NOME");
    columnName.add("COGNOME");
    columnName.add("DATA_NASCITA");
    columnName.add("CAP_DOMICILIO");
    columnName.add("CAP_RESIDENZA");
    columnName.add("CITTA_DOMICILIO");
    columnName.add("CITTA_RESIDENZA");
    columnName.add("INDIRIZZO_DOMICILIO");
    columnName.add("INDIRIZZO_RESIDENZA");
    columnName.add("LAVORO");
    columnName.add("LUOGO_NASCITA");
    columnName.add("PROVINCIA_DOMICILIO");
    columnName.add("PROVINCIA_NASCITA");
    columnName.add("PROVINCIA_RESIDENZA");
    columnName.add("SESSO");
    columnName.add("STATO_DOMICILIO");
    columnName.add("STATO_RESIDENZA");
    columnName.add("STATO_NASCITA");
    columnName.add("TELEFONO");
    columnName.add("CELLULARE");
    columnName.add("TITOLO");
    columnName.add("E_MAIL");
    columnName.add("DOMICILIO_ELETTRONICO");
    columnName.add("ID_COMUNE");
    columnName.add("PASSWORD");
    columnName.add("PIN");
    columnName.add("DATA_REGISTRAZIONE");
    columnName.add("DATA_ATTIVAZIONE");
    columnName.add("STATUS");
    return columnName;
  }
  
  public String getWhereClause()
  {
    return this.whereClause;
  }
  
  public void setWhereClause(String whereClause)
  {
    this.whereClause = whereClause;
  }
  
  public String generateNewPassword()
  {
    logger.debug("generateNewPassword() - start");
    this.newPwd = generatePassword();
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null) {
      ((Userdata)tableManager.getCurrent()).setPassword(this.newPwd);
    }
    logger.debug("generateNewPassword() - end");
    setError("");
    return "success";
  }
  
  public String generateNewPin()
  {
    logger.debug("generateNewPin() - start");
    this.newPin = generatePin();
    Manager tableManager = (Manager)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tableManager");
    if (tableManager != null) {
      try
      {
        this.newUserKeystoreData = new Userkeystoredata();
        this.newUserKeystoreData.setCodiceFiscale(((Userdata)tableManager.getCurrent()).getCodiceFiscale());
        


        ((Userdata)tableManager.getCurrent()).setPin(this.newPin);
        logger.debug("generateNewPin() - Generazione di un nuovo keystore l'utente " + ((Userdata)tableManager.getCurrent()).getCodiceFiscale() + " in corso...");
        this.newUserKeystoreData.setKeyStore(generateKeyStore((Userdata)tableManager.getCurrent()));
        logger.debug("generateNewPin() - Generazione di un nuovo keystore completata.");
        this.newUserKeystoreData.setPin(this.newPin);
        


        logger.debug("generateNewPin() - end");
        setError("");
        return "success";
      }
      catch (Exception e)
      {
        logger.error("generateNewPin() - Errore durante il processo di generazione del nuovo PIN. Causa: " + e.getMessage());
        logger.debug("generateNewPin() - end");
        setError("Errore nella generazione del nuovo pin");
        return "failed";
      }
    }
    logger.error("generateNewPin() - Errore durante il processo di generazione del nuovo PIN.");
    logger.debug("generateNewPin() - end");
    setError("Errore nella generazione del nuovo pin");
    return "failed";
  }
  
  private void sendNewUserMail(Userdata current)
  {
    logger.debug("sendNewUserMail() - start");
    Properties properties = loadProperties("/properties/userdata.properties");
    properties.setProperty("mail.destination", current.getEmail());
    String bodyTemplate = properties.getProperty("mail.newUser.message");
    
    Object[] arguments = { current.getCognome() + " " + current.getNome(), current.getDomicilioElettronico(), current.getPassword(), current.getPin() };
    




    String body = MessageFormat.format(bodyTemplate, arguments);
    
    properties.setProperty("mail.newUser.message", body);
    properties.setProperty("mail.subject", properties.getProperty("mail.newUser.subject"));
    properties.setProperty("mail.message", properties.getProperty("mail.newUser.message"));
    
    MailSender mailSender = new MailSender();
    try
    {
      mailSender.initialize(properties);
      mailSender.sendMail();
    }
    catch (Exception e)
    {
      logger.error("sendNewUserMail() - Errore durante l'invio mail. Causa: " + e.getMessage());
      logger.debug("sendNewUserMail() - end");
      e.printStackTrace();
    }
    logger.debug("sendNewUserMail() - end");
  }
  
  private void sendNewPwdMail(Userdata current)
    throws Exception
  {
    logger.debug("sendNewPwdMail() - start");
    Properties properties = loadProperties("/properties/userdata.properties");
    properties.setProperty("mail.destination", current.getDomicilioElettronico());
    String bodyTemplate = properties.getProperty("mail.newPwd.message");
    
    Object[] arguments = { current.getCognome() + " " + current.getNome(), current.getPassword() };
    String body = MessageFormat.format(bodyTemplate, arguments);
    
    properties.setProperty("mail.newPwd.message", body);
    properties.setProperty("mail.subject", properties.getProperty("mail.newPwd.subject"));
    properties.setProperty("mail.message", properties.getProperty("mail.newPwd.message"));
    
    MailSender mailSender = new MailSender();
    try
    {
      mailSender.initialize(properties);
      mailSender.sendMail();
    }
    catch (Exception e)
    {
      logger.error("sendNewPwdMail() - Errore durante l'invio mail. Causa: " + e.getMessage());
      logger.debug("sendNewPwdMail() - end");
      throw e;
    }
    logger.debug("sendNewPwdMail() - end");
  }
  
  private void sendNewPinMail(Userdata current)
    throws Exception
  {
    Properties properties = loadProperties("/properties/userdata.properties");
    logger.debug("sendNewPinMail() - start");
    properties.setProperty("mail.destination", current.getDomicilioElettronico());
    String bodyTemplate = properties.getProperty("mail.newPin.message");
    
    Object[] arguments = { current.getCognome() + " " + current.getNome(), current.getPin() };
    String body = MessageFormat.format(bodyTemplate, arguments);
    
    properties.setProperty("mail.newPin.message", body);
    properties.setProperty("mail.subject", properties.getProperty("mail.newPin.subject"));
    properties.setProperty("mail.message", properties.getProperty("mail.newPin.message"));
    
    MailSender mailSender = new MailSender();
    try
    {
      mailSender.initialize(properties);
      mailSender.sendMail();
    }
    catch (Exception e)
    {
      logger.error("Errore durante l'invio mail: " + e.getMessage());
      logger.debug("sendNewPinMail() - end");
      throw e;
    }
    logger.debug("sendNewPinMail() - end");
  }
  
  public String getNewPin()
  {
    this.newPin = generatePin();
    return this.newPin;
  }
  
  public String getNewPassword()
  {
    this.newPwd = generatePassword();
    return this.newPwd;
  }
  
  public Date getNewDataRegistrazione()
  {
    this.newDataRegistrazione = new Date();
    return this.newDataRegistrazione;
  }
  
  public Date getNewDataAttivazione()
  {
    this.newDataAttivazione = new Date();
    return this.newDataAttivazione;
  }
  
  protected String generatePassword()
  {
    logger.debug("generatePassword() - start");
    


    Random rnd = new Random();
    StringBuffer pwd = new StringBuffer(8);
    String block1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
    for (int i = 0; i < 8; i++) {
      pwd.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789".charAt(rnd.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789".length())));
    }
    logger.debug("generatePassword() - end");
    return pwd.toString();
  }
  
  protected String generatePin()
  {
    logger.debug("generatePin() - start");
    Random rnd = new Random();
    
    StringBuffer pin = new StringBuffer(10);
    String numbers = "0123456789";
    for (int i = 0; i < 9; i++) {
      pin.append("0123456789".charAt(rnd.nextInt(10)));
    }
    logger.debug("generatePin() - end");
    return pin.toString();
  }
  
  private byte[] generateKeyStore(Userdata user)
  {
    logger.debug("generateKeyStore() - start");
    String caKeyStoreFilename = this.properties.getProperty("users.CAKeystoreFile");
    String caKeyStorePassword = this.properties.getProperty("users.CAKeystorePassword");
    String caKeyStoreAlias = this.properties.getProperty("users.CAKeystoreAlias");
    String caKeyStoreType = this.properties.getProperty("users.CAKeystoreType");
    byte[] result = null;
    try
    {
      InputStream keyStoreFile = getClass().getResourceAsStream(caKeyStoreFilename);
      KeyStore caKeyStore = KeyStore.getInstance(caKeyStoreType);
      caKeyStore.load(keyStoreFile, caKeyStorePassword.toCharArray());
      
      KeyStore keyStore = KeystoreGenerator.createP12(user.getCodiceFiscale(), caKeyStore, caKeyStoreAlias, caKeyStorePassword, user.getPin(), user.getDomicilioElettronico(), user.getNome(), user.getCognome());
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      keyStore.store(baos, user.getPin().toCharArray());
      baos.flush();
      byte[] keystoreBytes = baos.toByteArray();
      baos.close();
      result = keystoreBytes;
    }
    catch (Exception e)
    {
      logger.error("generateKeyStore() - Errore durante la generazione del nuovo keystore. Causa: " + e.getMessage());
      logger.debug("generateKeyStore() - end");
      e.printStackTrace();
    }
    logger.debug("generateKeyStore() - end");
    return result;
  }
  
  private Properties loadProperties(String resourceName)
  {
    Properties properties = new Properties();
    URL url = getClass().getResource(resourceName);
    try
    {
      logger.debug("loadProperties() - Lettura parametri da file di configurazione in corso...");
      properties.load(url.openStream());
      logger.debug("loadProperties() - Lettura parametri completata.");
      return properties;
    }
    catch (IOException e)
    {
      logger.error("loadProperties() - Errore durante la lettura parametri dal file di configurazione. Causa: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}

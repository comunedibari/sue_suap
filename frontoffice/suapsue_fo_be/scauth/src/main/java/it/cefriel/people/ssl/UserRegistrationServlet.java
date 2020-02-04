package it.cefriel.people.ssl;

import it.people.sirac.authdataholder.AuthDataHolderFactory;
import it.people.sirac.authdataholder.IAuthDataHolder;
import it.people.sirac.authdataholder.IAuthDataHolderFactory;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResKeystoreBean;
import it.people.sirac.idp.beans.ResRegBean;
import it.people.sirac.idp.registration.RegistrationClientAdapter;
import it.people.sirac.util.Utilities;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class UserRegistrationServlet
  extends HttpServlet
{
  private static final Logger logger = LoggerFactory.getLogger(UserRegistrationServlet.class);
  protected String registrationPage = null;
  protected String registrationConfirmationPage = null;
  protected String registrationErrorPage = null;
  protected String portAddress_prefix = null;
  protected int timeout = 120000;
  protected String wsreg_address = null;
  protected RegistrationClientAdapter regWS = null;
  protected String mode = null;
  protected String caKeyStoreFilename = null;
  protected String caKeyStorePassword = null;
  protected String caKeyStoreAlias = null;
  
  protected String generatePassword()
  {
    Random rnd = new Random();
    StringBuffer pwd = new StringBuffer(8);
    String block1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    if (logger.isDebugEnabled()) {
      logger.debug("generatePassword() - start");
    }
    for (int i = 0; i < 8; i++) {
      pwd.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(rnd.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length())));
    }
    if (logger.isDebugEnabled()) {
      logger.debug("generatePassword() - end pwd = " + pwd.toString());
    }
    return pwd.toString();
  }
  
  protected String generatePin()
  {
    Random rnd = new Random();
    
    StringBuffer pin = new StringBuffer(10);
    String numbers = "0123456789";
    if (logger.isDebugEnabled()) {
      logger.debug("generatePin() - start");
    }
    for (int i = 0; i < 9; i++) {
      pin.append("0123456789".charAt(rnd.nextInt(10)));
    }
    if (logger.isDebugEnabled()) {
      logger.debug("generatePin() - end - pin = " + pin.toString());
    }
    return pin.toString();
  }
  
  protected boolean formDataComplete(RegBean rb)
  {
    boolean completeResidenza = false;
    boolean completeDomicilio = false;
    boolean completeNascita = false;
    boolean completeAltri = false;
    
    completeResidenza = 
    
      (!"".equalsIgnoreCase(rb.getIndirizzoResidenza())) && 
      (!"".equalsIgnoreCase(rb.getCittaResidenza())) && 
      (!"".equalsIgnoreCase(rb.getCapResidenza())) && 
      (!"".equalsIgnoreCase(rb.getProvinciaResidenza())) && 
      (!"".equalsIgnoreCase(rb.getStatoResidenza()));
    

    logger.debug("completeResidenza = " + completeResidenza);
    if ((!"".equalsIgnoreCase(rb.getIndirizzoDomicilio())) || 
      (!"".equalsIgnoreCase(rb.getCittaDomicilio())) || 
      (!"".equalsIgnoreCase(rb.getCapDomicilio())) || 
      (!"".equalsIgnoreCase(rb.getProvinciaDomicilio())) || 
      (!"".equalsIgnoreCase(rb.getStatoDomicilio()))) {}
    completeDomicilio = 
    






      (!"".equalsIgnoreCase(rb.getIndirizzoDomicilio())) && 
      (!"".equalsIgnoreCase(rb.getCittaDomicilio())) && 
      (!"".equalsIgnoreCase(rb.getCapDomicilio())) && 
      (!"".equalsIgnoreCase(rb.getProvinciaDomicilio())) && 
      (!"".equalsIgnoreCase(rb.getStatoDomicilio()));
    


    logger.debug("completeDomicilio = " + completeDomicilio);
    
    completeNascita = 
    
      (!"".equalsIgnoreCase(rb.getLuogoNascita())) && 
      (!"".equalsIgnoreCase(rb.getProvinciaNascita())) && 
      (!"".equalsIgnoreCase(rb.getStatoNascita()));
    

    logger.debug("completeNascita = " + completeNascita);
    
    completeAltri = !"".equalsIgnoreCase(rb.getEmail());
    
    logger.debug("completeAltri = " + completeAltri);
    
    return (completeResidenza) && (completeDomicilio) && (completeNascita) && (completeAltri);
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    logger.debug("doPost() - start");
    
    X509Certificate[] sessionCerts = (X509Certificate[])request
      .getAttribute("javax.servlet.request.X509Certificate");
    if ((sessionCerts == null) || (sessionCerts.length == 0))
    {
      logger.error("doPost() - Nessun certificato trovato in sessione");
      SiracHelper.forwardToErrorPageWithRuntimeException(request, 
        response, null, 
        "Nessun certificato trovato in sessione.");
    }
    String sessionCertificateB64 = (String)request.getSession().getAttribute("X509Cert");
    byte[] sessionCertificateBytes = Base64.decodeBase64(sessionCertificateB64.getBytes());
    
    X509Certificate sessionCertificate = sessionCerts[0];
    
    logger.debug("doPost() - Certificato SSL trovato e decodificato correttamente.");
    try
    {
      if (!Utilities.byteArrayEqual(sessionCertificateBytes, sessionCertificate.getEncoded()))
      {
        logger.error("doPost() - Il certificato in sessione non coincide con quello utilizzato per attivare il processo di registrazione.");
        SiracHelper.forwardToErrorPageWithRuntimeException(request, 
          response, null, 
          "Il certificato in sessione non coincide con quello utilizzato per attivare il processo di registrazione.");
      }
    }
    catch (Exception ex)
    {
      logger.error("doPost() - Si � verificato un errore durante la lettura del certificato presente in sessione.");
      SiracHelper.forwardToErrorPageWithRuntimeException(request, 
        response, null, 
        "Si � verificato un errore durante la lettura del certificato presente in sessione.");
    }
    String authDataHolderXMLText = null;
    String authResponseBase64 = (String)request.getSession().getAttribute("authResponse");
    if (Base64.isArrayByteBase64(authResponseBase64.getBytes())) {
      authDataHolderXMLText = new String(Base64.decodeBase64(authResponseBase64.getBytes()));
    } else {
      authDataHolderXMLText = authResponseBase64;
    }
    if ((authDataHolderXMLText != null) && (!"".equalsIgnoreCase(authDataHolderXMLText)))
    {
      logger.debug("doPost() - AuthResponse presente in request:");
      logger.debug(authDataHolderXMLText);
    }
    else
    {
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        null, 
        "Errore: Informazioni di autenticazione mancanti. Impossibile procedere con l'autenticazione.");
    }
    IAuthDataHolderFactory adhf = new AuthDataHolderFactory();
    
    IAuthDataHolder authDataHolder = null;
    try
    {
      logger.debug("doPost() - Parsing dell'AuthDataHolder XML in corso...");
      authDataHolder = adhf.createAuthDataHolder(authDataHolderXMLText);
      logger.debug("doPost() - Parsing dell'AuthDataHolder completato correttamente.");
    }
    catch (Exception e)
    {
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        e, 
        "Errore: Informazioni di autenticazione non valide. Impossibile procedere con l'autenticazione.");
    }
    Map attributesMap = authDataHolder.getSubjectUserAttributes();
    String target = authDataHolder.getTargetURL();
    
    boolean userAlreadyRegistered = this.regWS.isUserRegistered((String)attributesMap.get("codiceFiscale"));
    if (!userAlreadyRegistered)
    {
      logger.debug("doPost() - Nuovo utente: registrazione");
      this.mode = "registration";
    }
    else
    {
      logger.debug("doPost() - Utente gia' esistente: modifica");
      this.mode = "update";
    }
    boolean hasRegistrationData = Boolean.valueOf(request.getParameter("hasRegistrationData")).booleanValue();
    if (!hasRegistrationData)
    {
      logger.debug("doPost() - Forward verso pagina di registrazione: " + this.registrationPage);
      
      request.setAttribute("attributesMap", attributesMap);
      request.setAttribute("target", target);
      request.setAttribute("mode", this.mode);
      
      RequestDispatcher rd = request.getRequestDispatcher(this.registrationPage);
      rd.forward(request, response);
      if (logger.isDebugEnabled()) {
        logger.debug("doPost() - end");
      }
      return;
    }
    logger.debug("doPost() - Lettura dati dalla pagina di registrazione...");
    
    RegBean rb = PersonalDataHelper.mapToRegBean(attributesMap);
    

    rb.setIdComune((String)request.getSession().getAttribute("idComuneRegistrazione"));
    

    String cartaIdentita = request.getParameter("cartaIdentita");
    rb.setCartaIdentita(cartaIdentita == null ? "" : cartaIdentita);
    rb.setEmail(request.getParameter("emailAddress"));
    rb.setDomicilioElettronico(request.getParameter("emailAddress"));
    
    String indirizzoResidenza = request.getParameter("indirizzoResidenza");
    String comuneResidenza = request.getParameter("cittaResidenza");
    String capResidenza = request.getParameter("capResidenza");
    String provinciaResidenza = request.getParameter("provinciaResidenza");
    String statoResidenza = request.getParameter("statoResidenza");
    
    rb.setIndirizzoResidenza(indirizzoResidenza);
    rb.setCittaResidenza(comuneResidenza);
    rb.setProvinciaResidenza(provinciaResidenza);
    rb.setCapResidenza(capResidenza);
    rb.setStatoResidenza(statoResidenza);
    
    rb.setLavoro(request.getParameter("lavoro"));
    rb.setLuogoNascita(request.getParameter("luogoNascita"));
    rb.setProvinciaNascita(request.getParameter("provinciaNascita"));
    rb.setStatoNascita(request.getParameter("statoNascita"));
    rb.setTelefono(request.getParameter("telefono"));
    rb.setCellulare(request.getParameter("cellulare"));
    rb.setTitolo(request.getParameter("titolo"));
    
    String indirizzoDomicilio = request.getParameter("indirizzoDomicilio");
    String comuneDomicilio = request.getParameter("cittaDomicilio");
    String capDomicilio = request.getParameter("capDomicilio");
    String provinciaDomicilio = request.getParameter("provinciaDomicilio");
    String statoDomicilio = request.getParameter("statoDomicilio");
    
    rb.setIndirizzoDomicilio(indirizzoDomicilio);
    rb.setCittaDomicilio(comuneDomicilio);
    rb.setProvinciaDomicilio(provinciaDomicilio);
    rb.setCapDomicilio(capDomicilio);
    rb.setStatoDomicilio(statoDomicilio);
    if (formDataComplete(rb))
    {
      ResRegBean res = null;
      ResKeystoreBean resKeystoreBean = null;
      if (!userAlreadyRegistered)
      {
        logger.debug("doPost() - Nuovo utente: generazione password e pin...");
        String password = generatePassword();
        String pin = generatePin();
        if ("".equalsIgnoreCase(rb.getIndirizzoDomicilio()))
        {
          rb.setIndirizzoDomicilio(rb.getIndirizzoResidenza());
          rb.setCittaDomicilio(rb.getCittaResidenza());
          rb.setCapDomicilio(rb.getCapResidenza());
          rb.setProvinciaDomicilio(rb.getProvinciaResidenza());
          rb.setStatoDomicilio(rb.getStatoResidenza());
        }
        rb.setPassword(password);
        rb.setPin(pin);
        try
        {
          logger.debug("doPost() - Nuovo utente: generazione keystore per firma remota...");
          
          KeyStore caKeyStore = KeyStore.getInstance("PKCS12");
          caKeyStore.load(new FileInputStream(this.caKeyStoreFilename), this.caKeyStorePassword.toCharArray());
          
          KeyStore keyStore = KeystoreGenerator.createP12(rb.getCodiceFiscale(), caKeyStore, this.caKeyStoreAlias, this.caKeyStorePassword, rb.getPin(), rb.getDomicilioElettronico(), rb.getNome(), rb.getCognome());
          
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          keyStore.store(baos, rb.getPin().toCharArray());
          baos.flush();
          byte[] keystoreBytes = baos.toByteArray();
          baos.close();
          String keystoreB64 = new String(Base64.encodeBase64(keystoreBytes));
          logger.debug("doPost() - Nuovo utente: creazione account in corso...");
          res = this.regWS.executeRegistration(rb);
          logger.debug("doPost() - Nuovo utente: creazione account completato.");
          logger.debug("doPost() - Nuovo utente: inserimento keystore per firma remota in corso...");
          resKeystoreBean = this.regWS.insertNewKeystoreData(rb.getCodiceFiscale(), rb.getPin(), keystoreB64);
          logger.debug("doPost() - Nuovo utente: inserimento keystore per firma remota completato.");
          
          logger.debug("doPost() - Nuovo utente: attivazione utente in corso...");
          res = this.regWS.activateUser(rb.getCodiceFiscale());
          logger.debug("doPost() - Nuovo utente: attivazione utente completata.");
        }
        catch (RemoteException ex)
        {
          SiracHelper.forwardToErrorPageWithRuntimeException(
            request, 
            response, 
            ex, 
            "Errore durante l'invocazione del Web Service di registrazione.");
        }
        catch (Exception e)
        {
          SiracHelper.forwardToErrorPageWithRuntimeException(
            request, 
            response, 
            e, 
            "Errore durante la creazione/memorizzazione del keystore per la firma remota.");
        }
      }
      else
      {
        try
        {
          logger.debug("doPost() - Utente gia' presente in archivio: aggiornamento profilo in corso...");
          res = this.regWS.updateRegistration(rb);
          logger.debug("doPost() - Utente gia' presente in archivio: aggiornamento completato.");
        }
        catch (RemoteException ex)
        {
          SiracHelper.forwardToErrorPageWithRuntimeException(
            request, 
            response, 
            ex, 
            "Errore durante l'invocazione del Web Service di registrazione.");
        }
      }
      request.setAttribute("registrationData", rb);
      request.setAttribute("regResponse", res);
      request.setAttribute("mode", this.mode);
      String serviceResourcePage = null;
      if ("FAILED".equalsIgnoreCase(res.getEsito())) {
        serviceResourcePage = this.registrationErrorPage;
      } else if ("OK".equalsIgnoreCase(res.getEsito())) {
        serviceResourcePage = this.registrationConfirmationPage;
      }
      logger.debug("doPost() - Forward a pagina di conferma: " + this.registrationConfirmationPage);
      
      RequestDispatcher rd = request.getRequestDispatcher(serviceResourcePage);
      rd.forward(request, response);
    }
    else
    {
      attributesMap = PersonalDataHelper.regBeanToMap(rb);
      logger.debug("doPost() - Non sono stati inseriti tutti i dati richiesti. Ritorno a form di registrazione.");
      request.setAttribute("error", "error");
      request.setAttribute("attributesMap", attributesMap);
      request.setAttribute("target", target);
      request.setAttribute("mode", this.mode);
      
      RequestDispatcher rd = request.getRequestDispatcher(this.registrationPage);
      rd.forward(request, response);
      
      return;
    }
  }
  
  public void init(ServletConfig config)
    throws ServletException
  {
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - start");
    }
    this.portAddress_prefix = config.getInitParameter("portAddress_prefix");
    
    this.wsreg_address = config.getServletContext().getInitParameter("wsreg_address");
    try
    {
      this.regWS = new RegistrationClientAdapter(this.wsreg_address);
      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - Registration Web Service Address configurato corretamente ( " + this.wsreg_address + ")");
      }
    }
    catch (Exception e)
    {
      throw new ServletException("Impossibile inizializzare indirizzo web service di registrazione: " + this.wsreg_address);
    }
    this.timeout = Integer.parseInt(config.getInitParameter("timeout"));
    this.registrationPage = config.getInitParameter("registrationPage");
    this.registrationConfirmationPage = config.getInitParameter("registrationConfirmationPage");
    this.registrationErrorPage = config.getInitParameter("registrationErrorPage");
    
    this.caKeyStoreFilename = config.getServletContext().getRealPath(config.getInitParameter("caKeyStoreFilename"));
    this.caKeyStorePassword = config.getInitParameter("caKeyStorePassword");
    this.caKeyStoreAlias = config.getInitParameter("caKeyStoreAlias");
  }
}


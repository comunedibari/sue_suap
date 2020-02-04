package it.cefriel.people.ssl;

import it.cefriel.utility.security.PKCSHelper;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.idp.authentication.AuthenticationClientAdapter;
import it.people.sirac.idp.beans.ComuneBean;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.registration.RegistrationClientAdapter;
import it.people.sirac.util.CodiceFiscaleParser;
import it.people.sirac.util.Utilities;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

public class SmartCardPersonalDataConsumerServlet
  extends HttpServlet
{
  private static final Logger logger = LoggerFactory.getLogger(SmartCardPersonalDataConsumerServlet.class);
  protected boolean matchWithStoredProfileRequired = false;
  protected int timeout = 120000;
  protected String idComune = null;
  protected String portAddress_prefix = null;
  protected String authDataConsumerServicePostResponsePage = null;
  protected String cardType = null;
  protected String mode = null;
  protected String ca_domain_suffix = null;
  protected String target = null;
  protected String targetServiceForwardURL = null;
  protected String targetServiceTransferMode = null;
  protected String targetServicePOSTURL = null;
  protected String authDataConsumerServiceName = null;
  protected CardUserData datiFromCertificate = null;
  protected byte[] sessionCertificateBytes = null;
  protected String wsauth_address = null;
  protected String wsreg_address = null;
  protected AuthenticationClientAdapter authWS = null;
  protected RegistrationClientAdapter regWS = null;
  
  protected boolean hasDatiPersonali(HttpServletRequest request)
  {
    return request.getParameter("DatiPersonaliRaw") != null;
  }
  
  protected String getStringAttributeNotNull(String attribute)
  {
    if (attribute == null) {
      return "";
    }
    return attribute;
  }
  
  protected void readSessionParameters(HttpServletRequest request)
  {
    this.datiFromCertificate = ((CardUserData)request.getSession().getAttribute(
      "CardData"));
    this.cardType = ((String)request.getSession().getAttribute("CardType"));
    this.portAddress_prefix = ((String)request.getSession().getAttribute(
      "PortAddressPrefix"));
    this.idComune = ((String)request.getSession().getAttribute(
      "IdComune"));
    this.ca_domain_suffix = ((String)request.getSession().getAttribute(
      "CaDomainSuffix"));
    this.timeout = ((Integer)request.getSession().getAttribute("Timeout"))
      .intValue();
    this.mode = ((String)request.getSession().getAttribute("mode"));
    if ("authentication".equalsIgnoreCase(this.mode)) {
      this.target = ((String)request.getSession().getAttribute("TARGET"));
    } else if ("registration".equalsIgnoreCase(this.mode)) {
      this.target = request.getSession().getServletContext()
        .getInitParameter("registration_targetServiceURL");
    }
    this.targetServiceForwardURL = ((String)request.getSession().getAttribute(
      "authDataConsumerServiceForwardURL"));
    this.targetServiceTransferMode = ((String)request.getSession().getAttribute(
      "authDataConsumerServiceTransferMode"));
    this.targetServicePOSTURL = ((String)request.getSession().getAttribute(
      "authDataConsumerServicePOSTURL"));
    this.authDataConsumerServicePostResponsePage = 
      ((String)request.getSession().getAttribute("authDataConsumerServicePostResponsePage"));
    this.authDataConsumerServiceName = 
      ((String)request.getSession().getAttribute("authDataConsumerServiceName"));
    this.matchWithStoredProfileRequired = new Boolean(
      (String)request.getSession().getAttribute("matchWithStoredProfileRequired"))
      .booleanValue();
    String sessionCertificateB64 = (String)request.getSession()
      .getAttribute("X509Cert");
    this.sessionCertificateBytes = Base64.decodeBase64(sessionCertificateB64
      .getBytes());
  }
  
  protected void checkSessionData(HttpServletRequest request, HttpServletResponse response)
  {
    X509Certificate[] sessionCerts = (X509Certificate[])request
      .getAttribute("javax.servlet.request.X509Certificate");
    if ((sessionCerts == null) || (sessionCerts.length == 0))
    {
      logger.error("doPost() - Nessun certificato trovato in sessione");
      SiracHelper.forwardToErrorPageWithRuntimeException(request, 
        response, null, 
        "Nessun certificato trovato in sessione.");
    }
    X509Certificate sessionCertificate = sessionCerts[0];
    try
    {
      if (!Utilities.byteArrayEqual(this.sessionCertificateBytes, sessionCertificate.getEncoded()))
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
    if ((this.datiFromCertificate == null) || (this.portAddress_prefix == "") || 
      (this.ca_domain_suffix == "") || (this.target == "") || (this.authDataConsumerServicePostResponsePage == "")) {
      SiracHelper.forwardToErrorPageWithRuntimeException(request, 
        response, null, "Errore: dati in sessione mancanti.");
    }
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost_helper(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost_helper(request, response);
  }
  
  public void doPost_helper(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    readSessionParameters(request);
    
    checkSessionData(request, response);
    
    RegBean userDataFromCard = null;
    RegBean userDataFromStorage = null;
    RegBean userData = null;
    Map cardAttributesMap = new HashMap();
    Map certificateAttributesMap = new HashMap();
    


    certificateAttributesMap.put("codiceFiscale", getStringAttributeNotNull(this.datiFromCertificate.getCodiceFiscale()));
    certificateAttributesMap.put("cognome", getStringAttributeNotNull(this.datiFromCertificate.getCognome()));
    certificateAttributesMap.put("nome", getStringAttributeNotNull(this.datiFromCertificate.getNome()));
    certificateAttributesMap.put("dataNascita", getStringAttributeNotNull(this.datiFromCertificate.getDataNascita()));
    certificateAttributesMap.put("emailAddress", getStringAttributeNotNull(this.datiFromCertificate.getEmail()));
    if (hasDatiPersonali(request))
    {
      byte[] rawPersonalDataFromSmartCard = request.getParameter("DatiPersonaliRaw").getBytes();
      if (logger.isDebugEnabled()) {
        logger.debug("doPost_helper(HttpServletRequest, HttpServletResponse) - Elaborazione dati personali carta in corso. Tipo carta: " + this.cardType);
      }
      byte[] computedHash = (byte[])null;
      byte[] base64computedHash = (byte[])null;
      


      byte[] certificateHash = this.datiFromCertificate.getHashDatiPersonali().getBytes();
      if (("CNS".equalsIgnoreCase(this.cardType)) || 
        ("CRS".equalsIgnoreCase(this.cardType)))
      {
        computedHash = PKCSHelper.getSHA1Digest(rawPersonalDataFromSmartCard);
      }
      else if ("CIE".equalsIgnoreCase(this.cardType))
      {
        int datiPersonaliLength = 0;
        datiPersonaliLength = Integer.parseInt(new String(
          rawPersonalDataFromSmartCard, 0, 6), 16) + 6;
        byte[] datiPersonaliRaw = new byte[datiPersonaliLength];
        System.arraycopy(rawPersonalDataFromSmartCard, 0, 
          datiPersonaliRaw, 0, datiPersonaliLength);
        computedHash = PKCSHelper.getSHA1Digest(datiPersonaliRaw);
      }
      else
      {
        computedHash = certificateHash;
      }
      base64computedHash = Base64.encodeBase64(computedHash);
      if (!this.datiFromCertificate.getHashDatiPersonali().equals(
        new String(base64computedHash)))
      {
        logger.error("doPost() - Personal data hash code does not match with certificate hash code.");
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "Errore: Hash dati personali non coincidente con hash contenuto in certificato carta.");
      }
      else
      {
        logger.debug("doPost() - Verifica Hashcode dati personali: OK (Hashcode calcolato sui dati personali coincidente con hashcode contenuto in certificato)");
      }
      Map personalDataAttributesMap = PersonalDataHelper.parseDatiPersonaliRawFixed(
        rawPersonalDataFromSmartCard, this.cardType);
      
      cardAttributesMap = Utilities.mergeMaps(certificateAttributesMap, personalDataAttributesMap);
      try
      {
        ComuneBean comuneResidenza = this.regWS.getComuneByCodiceBelfiore((String)cardAttributesMap.get("cittaResidenza"));
        cardAttributesMap.put("cittaResidenza", comuneResidenza.getNome());
        cardAttributesMap.put("capResidenza", comuneResidenza.getCap());
        cardAttributesMap.put("provinciaResidenza", comuneResidenza.getProvincia());
      }
      catch (Exception ex)
      {
        logger.error("Errore: impossibile risalire al comune di residenza a partire dai dati personali. Eccezione: " + ex.getMessage());
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          ex, 
          "Errore: impossibile risalire al comune di residenza a partire dai dati personali.");
      }
    }
    else
    {
      try
      {
        String cf = null;
        if (cardAttributesMap.isEmpty()) {
          cf = (String)certificateAttributesMap.get("codiceFiscale");
        } else {
          cf = (String)cardAttributesMap.get("codiceFiscale");
        }
        Date dataNascita = CodiceFiscaleParser.getDataNascita(cf);
        String dataNascitaString = Utilities.formatDateToString(dataNascita);
        certificateAttributesMap.put("dataNascita", getStringAttributeNotNull(dataNascitaString));
      }
      catch (Exception e2)
      {
        logger.error("doPost() - Errore durante la ricostruzione della data di nascita dal codice fiscale.");
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "Errore durante la ricostruzione della data di nascita dal codice fiscale.");
      }
      try
      {
        certificateAttributesMap.put("sesso", CodiceFiscaleParser.getSesso(this.datiFromCertificate.getCodiceFiscale()));
      }
      catch (Exception e)
      {
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "Errore: il codice fiscale non � valido. Codice fiscale = " + this.datiFromCertificate.getCodiceFiscale());
      }
      cardAttributesMap = certificateAttributesMap;
    }
    userDataFromCard = PersonalDataHelper.mapToRegBean(cardAttributesMap);
    try
    {
      Date dataDiNascita = Utilities.parseDateString(userDataFromCard.getDataNascita(), this.datiFromCertificate.getDatePattern());
      userDataFromCard.setDataNascita(Utilities.formatDateToString(dataDiNascita));
    }
    catch (Exception e1)
    {
      logger.error("SmartCardPersonalDataConsumerServlet - Non � stato possibile formattare correttamente la data di nascita.");
    }
    try
    {
      String codiceBelfiore = userDataFromCard.getCodiceFiscale().substring(11, 15);
      ComuneBean comuneNascita = this.regWS.getComuneByCodiceBelfiore(codiceBelfiore);
      if (comuneNascita.isValid())
      {
        userDataFromCard.setLuogoNascita(comuneNascita.getNome());
        userDataFromCard.setProvinciaNascita(comuneNascita.getProvincia());
      }
      else
      {
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "Errore: il comune di nascita desunto dal codice fiscale � sconosciuto.");
      }
    }
    catch (Exception ex)
    {
      logger.error("Errore: impossibile risalire al comune di nascita a partire dal codice fiscale. Eccezione: " + ex.getMessage());
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        ex, 
        "Errore: impossibile risalire al comune di nascita a partire dal codice fiscale " + userDataFromCard.getCodiceFiscale());
    }
    if ((!"".equals(this.datiFromCertificate.getCodiceFiscale())) && 
      (!this.datiFromCertificate.getCodiceFiscale().equalsIgnoreCase(
      userDataFromCard.getCodiceFiscale()))) {
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        null, 
        "Errore: il codice fiscale presente nel certificato in sessione non coincide con quello letto dai dati personali della carta.");
    }
    if ("authentication".equalsIgnoreCase(this.mode))
    {
      if (logger.isDebugEnabled()) {
        logger.debug("User authentication");
      }
      if (this.matchWithStoredProfileRequired)
      {
        try
        {
          userDataFromStorage = PersonalDataHelper.getUserDataFromStorage(userDataFromCard.getCodiceFiscale(), this.authWS);
        }
        catch (Exception ex)
        {
          logger.error("doPost() - Exception" + ex.getMessage());
          SiracHelper.forwardToErrorPageWithRuntimeException(request, 
            response, null, 
            "Il codice fiscale " + userDataFromCard.getCodiceFiscale() + " presente nel certificato non corrisponde ad alcun utente registrato.");
        }
      }
      else
      {
        userDataFromStorage = userDataFromCard;
        if ("CIE".equalsIgnoreCase(this.datiFromCertificate.getTipoCarta())) {
          userDataFromStorage.setCartaIdentita(this.datiFromCertificate.getCodCarta());
        }
      }
      userData = 
        PersonalDataHelper.fillUserDataFromCardWithUserDataFromStorage(
        userDataFromCard, 
        userDataFromStorage);
      
      String authStatus = "urn:people:names:authenticationstatus:success";
      String authResponseB64 = null;
      String authType = "STRONG";
      String authMethod = "urn:oasis:names:tc:SAML:1.0:am:HardwareToken";
      String userID = userData.getCodiceFiscale() + "@" + this.ca_domain_suffix;
      
      Map attributesMap = PersonalDataHelper.regBeanToMap(userData);
      try
      {
        authResponseB64 = SiracHelper.prepareAuthDataResponseBase64(
          userID, attributesMap, authType, authMethod, this.target, 
          authStatus);
      }
      catch (Exception e)
      {
        SiracHelper.forwardToErrorPageWithRuntimeException(request, 
          response, e, 
          "Impossibile trasferire i dati di autenticazione.");
      }
      try
      {
        SiracHelper.transferAuthResponseToTargetService(
          authResponseB64, this.authDataConsumerServiceName, 
          this.targetServiceTransferMode, this.targetServiceForwardURL, 
          this.targetServicePOSTURL, 
          this.authDataConsumerServicePostResponsePage, request, 
          response);
      }
      catch (Exception e)
      {
        String errorMessage = null;
        if ((!this.targetServiceTransferMode.equalsIgnoreCase("FORWARD")) && 
          (!this.targetServiceTransferMode.equalsIgnoreCase("POST"))) {
          errorMessage = 
            "Impossibile trasferire i dati di autenticazione. Modalit� di trasferimento verso Target Service (" + this.authDataConsumerServiceName + ") non valida: " + this.targetServiceTransferMode;
        } else {
          errorMessage = "Impossibile trasferire i dati di autenticazione";
        }
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          e, 
          errorMessage);
      }
    }
    else if ("registration".equalsIgnoreCase(this.mode))
    {
      if (logger.isDebugEnabled()) {
        logger.debug("Modalit� di registrazione utenti");
      }
      if (this.regWS.isUserRegistered(userDataFromCard.getCodiceFiscale()))
      {
        try
        {
          logger.debug("Utente gi� presente in archivio. Accesso ai dati del profilo dal database...");
          userDataFromStorage = PersonalDataHelper.getUserDataFromStorage(userDataFromCard.getCodiceFiscale(), this.authWS);
          logger.debug("... accesso ai dati del profilo completato.");
        }
        catch (Exception ex)
        {
          logger.error("doPost() - Exception" + ex.getMessage());
          SiracHelper.forwardToErrorPageWithRuntimeException(request, 
            response, null, 
            "Errore durante la lettura del profilo utente.");
        }
      }
      else
      {
        logger.debug("Utente non presente in archivio: creazione nuovo account.");
        userDataFromStorage = userDataFromCard;
        if ("CIE".equalsIgnoreCase(this.datiFromCertificate.getTipoCarta())) {
          userDataFromStorage.setCartaIdentita(this.datiFromCertificate.getCodCarta());
        }
      }
      userData = PersonalDataHelper.fillUserDataFromCardWithUserDataFromStorage(
        userDataFromCard, 
        userDataFromStorage);
      
      Map attributesMap = PersonalDataHelper.regBeanToMap(userData);
      
      String authStatus = "urn:people:names:authenticationstatus:success";
      String authResponseB64 = null;
      String authType = "STRONG";
      String authMethod = "urn:oasis:names:tc:SAML:1.0:am:HardwareToken";
      String userID = userData.getCodiceFiscale() + "@" + this.ca_domain_suffix;
      if (this.datiFromCertificate.getTipoCarta().equalsIgnoreCase("CIE")) {
        attributesMap.put("cartaIdentita", this.datiFromCertificate.getCodCarta());
      }
      try
      {
        logger.debug("Preparazione AuthDataResponse...");
        authResponseB64 = SiracHelper.prepareAuthDataResponseBase64(
          userID, attributesMap, authType, authMethod, this.target, 
          authStatus);
      }
      catch (Exception e)
      {
        SiracHelper.forwardToErrorPageWithRuntimeException(request, 
          response, e, 
          "Impossibile trasferire i dati di autenticazione.");
      }
      try
      {
        logger.debug("Invio AuthDataResponse a pagina di registrazione...");
        request.getSession().setAttribute("authResponse", authResponseB64);
        SiracHelper.transferAuthResponseToTargetService(
          authResponseB64, this.authDataConsumerServiceName, 
          this.targetServiceTransferMode, this.targetServiceForwardURL, 
          this.targetServicePOSTURL, 
          this.authDataConsumerServicePostResponsePage, request, 
          response);
      }
      catch (Exception e)
      {
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "Impossibile trasferire i dati di registrazione. Modalit� di trasferimento verso Target Service (" + 
          this.authDataConsumerServiceName + 
          ") non valida: " + 
          this.targetServiceTransferMode);
      }
    }
    else if (logger.isDebugEnabled())
    {
      logger.debug("doPost() - Modalit� sconosciuta: " + this.mode);
    }
  }
  
  public void init(ServletConfig config)
    throws ServletException
  {
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - start");
    }
    this.wsauth_address = config.getServletContext().getInitParameter("wsauth_address");
    this.wsreg_address = config.getServletContext().getInitParameter("wsreg_address");
    try
    {
      this.authWS = new AuthenticationClientAdapter(this.wsauth_address);
      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - Authentication Web Service Address configurato corretamente ( " + this.wsauth_address + ")");
      }
    }
    catch (Exception e)
    {
      throw new ServletException("Impossibile inizializzare indirizzo web service di autenticazione: " + this.wsauth_address);
    }
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
  }
}


package it.cefriel.people.ssl;

import it.cefriel.utility.security.X509CertificateHelper;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.smartcardprofile.ISmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfile;
import it.people.sirac.smartcardprofile.SmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfiles;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
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

public class SmartCardAuthSSLServlet
  extends HttpServlet
{
  private static final Logger logger = LoggerFactory.getLogger(SmartCardAuthSSLServlet.class);
  public String crlFilesLocation = "";
  private CardUserData cardUserData = null;
  private SmartCardProfile smartCardProfile = null;
  private String smartCardProfilesXmlFile = null;
  private SmartCardProfiles smartCardProfiles = null;
  private String jarCodebaseURL = null;
  private String smartCardPersonalDataConsumerURL = null;
  protected int timeout = 120000;
  protected String portAddress_prefix = "";
  protected String ca_domain_suffix = "";
  protected String alwaysShowAppletGUI = "false";
  protected String authentication_authDataConsumerServiceForwardURL = null;
  protected String authentication_authDataConsumerServicePOSTURL = null;
  protected String authentication_authDataConsumerServiceTransferMode = null;
  protected String registration_authDataConsumerServiceForwardURL = null;
  protected String registration_authDataConsumerServicePOSTURL = null;
  protected String registration_authDataConsumerServiceTransferMode = null;
  protected String authDataConsumerService_postResponsePage = null;
  protected String matchWithStoredProfileRequired;
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String target = request.getParameter("TARGET");
    String idComune = request.getParameter("idComuneRegistrazione");
    

    String mode = request.getParameter("mode");
    
    X509Certificate[] sessionCerts = (X509Certificate[])request
      .getAttribute("javax.servlet.request.X509Certificate");
    if ((sessionCerts == null) || (sessionCerts.length == 0))
    {
      logger.error("doPost() - Nessun certificato trovato in sessione.");
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        null, 
        "doPost() - Nessun certificato trovato in sessione.");
    }
    X509Certificate sessionCertificate = sessionCerts[0];
    try
    {
      this.smartCardProfile = CertificateUtils.getCertificateProfile(
        sessionCerts[0], this.smartCardProfiles);
      if (this.smartCardProfile == null)
      {
        logger.error("SmartCardAuthSSLServlet.doPost() - Impossibile trovare un profilo corrispondente al certificato in sessione");
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "SmartCardAuthSSLServlet.doPost() - Impossibile trovare un profilo corrispondente al certificato in sessione.");
      }
      if (this.smartCardProfile.isCertificateChainCheckEnabled()) {
        if (!X509CertificateHelper.isCertificateChainValid(sessionCerts))
        {
          logger.error("SmartCardAuthSSLServlet.doPost() - La catena di certificati in sessione non � valida.");
          SiracHelper.forwardToErrorPageWithRuntimeException(
            request, 
            response, 
            null, 
            "SmartCardAuthSSLServlet.doPost() - La catena di certificati in sessione non � valida.");
        }
      }
      if ((this.smartCardProfile.isCRLCheckEnabled()) && 
        (CertificateUtils.isRevoked(sessionCertificate, 
        this.smartCardProfile, this.crlFilesLocation)))
      {
        logger.error("doPost() - Il certificato in session � stato revocato.");
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "SmartCardAuthSSLServlet.doPost() - Il certificato in session � stato revocato.");
      }
      this.cardUserData = CertificateUtils.getUserInfo(sessionCertificate, 
        this.smartCardProfile);
      String cardType = this.smartCardProfile.getCardType();
      
      request.getSession().setAttribute("CardData", this.cardUserData);
      request.getSession().setAttribute("CardType", cardType);
      request.getSession().setAttribute("PortAddressPrefix", this.portAddress_prefix);
      request.getSession().setAttribute("Timeout", new Integer(this.timeout));
      request.getSession().setAttribute("CaDomainSuffix", this.ca_domain_suffix);
      request.getSession().setAttribute("TARGET", target);
      request.getSession().setAttribute("mode", mode);
      request.getSession().setAttribute("X509Cert", new String(Base64.encodeBase64(sessionCertificate.getEncoded())));
      
      request.setAttribute("cardType", cardType);
      request.setAttribute("alwaysShowAppletGUI", this.alwaysShowAppletGUI);
      request.setAttribute("smartCardPersonalDataConsumerURL", this.smartCardPersonalDataConsumerURL);
      request.setAttribute("jarCodebaseURL", this.jarCodebaseURL);
      if ("authentication".equalsIgnoreCase(mode))
      {
        request.getSession().setAttribute(
          "authDataConsumerServiceForwardURL", 
          this.authentication_authDataConsumerServiceForwardURL);
        request.getSession().setAttribute(
          "authDataConsumerServiceTransferMode", 
          this.authentication_authDataConsumerServiceTransferMode);
        request.getSession().setAttribute(
          "authDataConsumerServicePOSTURL", 
          this.authentication_authDataConsumerServicePOSTURL);
        request.getSession().setAttribute(
          "authDataConsumerServicePostResponsePage", 
          this.authDataConsumerService_postResponsePage);
        
        request.getSession().setAttribute(
          "authDataConsumerServiceType", "authentication");
        request.getSession().setAttribute(
          "authDataConsumerServiceName", 
          "Intersite Transfer Service");
        request.getSession().setAttribute(
          "matchWithStoredProfileRequired", this.matchWithStoredProfileRequired);
      }
      else if ("registration".equalsIgnoreCase(mode))
      {
        request.getSession().setAttribute(
          "authDataConsumerServiceForwardURL", 
          this.registration_authDataConsumerServiceForwardURL);
        request.getSession().setAttribute(
          "authDataConsumerServiceTransferMode", 
          this.registration_authDataConsumerServiceTransferMode);
        request.getSession().setAttribute(
          "authDataConsumerServicePOSTURL", 
          this.registration_authDataConsumerServicePOSTURL);
        request.getSession().setAttribute(
          "authDataConsumerServicePostResponsePage", 
          this.authDataConsumerService_postResponsePage);
        
        request.getSession().setAttribute(
          "authDataConsumerServiceType", "registration");
        request.getSession().setAttribute(
          "authDataConsumerServiceName", "Registration Service");
        request.getSession().setAttribute(
          "matchWithStoredProfileRequired", "false");
        request.getSession().setAttribute(
          "idComuneRegistrazione", idComune);
      }
      else
      {
        logger.error("doPost() - Valore non consentito per il parametro 'mode': " + mode);
        SiracHelper.forwardToErrorPageWithRuntimeException(
          request, 
          response, 
          null, 
          "SmartCardAuthSSLServlet.doPost() -  Valore non consentito per il parametro 'mode': " + mode);
      }
      String cardPersonalDataReaderService = null;
      if ((cardType.equalsIgnoreCase("CIE")) || 
        (cardType.equalsIgnoreCase("CNS"))) {
        cardPersonalDataReaderService = "/readCIEPersonalData.jsp";
      } else if (cardType.equalsIgnoreCase("CRS")) {
        cardPersonalDataReaderService = "/readCRSPersonalData.jsp";
      } else {
        cardPersonalDataReaderService = null;
      }
      if (cardPersonalDataReaderService != null)
      {
        RequestDispatcher rd = request
          .getRequestDispatcher(cardPersonalDataReaderService);
        rd.forward(request, response);
      }
      else
      {
        RequestDispatcher rd = request
          .getRequestDispatcher(this.smartCardPersonalDataConsumerURL);
        rd.forward(request, response);
      }
    }
    catch (Exception e)
    {
      logger.error("SmartCardAuthSSLServlet.doPost() - Si � verificata un'eccezione.");
      SiracHelper.forwardToErrorPageWithRuntimeException(
        request, 
        response, 
        null, 
        "SmartCardAuthSSLServlet.doPost() -  Si � verificata un'eccezione.");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("doPost() - end");
    }
  }
  
  public void init(ServletConfig config)
    throws ServletException
  {
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - start");
    }
    ServletContext ctx = config.getServletContext();
    
    String smartCardProfilesXmlFileRelativePath = config.getInitParameter("smartCardProfilesXmlFile");
    this.smartCardProfilesXmlFile = ctx
      .getRealPath(smartCardProfilesXmlFileRelativePath);
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- smartCardProfilesXmlFile: " + 
        this.smartCardProfilesXmlFile);
    }
    String crlFilesLocationRelativePath = config.getInitParameter("crlFilesLocation");
    this.crlFilesLocation = ctx.getRealPath(crlFilesLocationRelativePath);
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- crlFilesLocation: " + 
        this.crlFilesLocation);
    }
    this.alwaysShowAppletGUI = config.getInitParameter("alwaysShowAppletGUI");
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - alwaysShowAppletGUI: " + 
        this.alwaysShowAppletGUI);
    }
    this.jarCodebaseURL = config.getInitParameter("jarCodebaseURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - jarCodebaseURL: " + this.jarCodebaseURL);
    }
    this.smartCardPersonalDataConsumerURL = config.getInitParameter("smartCardPersonalDataConsumerURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - smartCardPersonalDataConsumerURL: " + 
        this.smartCardPersonalDataConsumerURL);
    }
    this.matchWithStoredProfileRequired = config.getInitParameter("matchWithStoredProfileRequired");
    if (this.matchWithStoredProfileRequired == null) {
      this.matchWithStoredProfileRequired = "true";
    }
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - matchWithStoredProfileRequired: " + 
        this.matchWithStoredProfileRequired);
    }
    ISmartCardProfileFactory smartCardProfileFactory = 
      SmartCardProfileFactory.getInstance();
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - Got factory!");
    }
    try
    {
      this.smartCardProfiles = smartCardProfileFactory
        .loadSmartCardProfilesFromXml(new FileInputStream(
        this.smartCardProfilesXmlFile));
      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - Smartcard profiles XML correctly found and loaded.");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error("SmartCardAuthServlet::init() - error parsing SmartCardProfileXML");
    }
    this.portAddress_prefix = config.getInitParameter("portAddress_prefix");
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - portAddress_prefix: " + 
        this.portAddress_prefix);
    }
    this.timeout = Integer.parseInt(config.getInitParameter("timeout"));
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - timeout: " + this.timeout);
    }
    this.ca_domain_suffix = config.getServletContext().getInitParameter(
      "CA-People.domain.suffix");
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - CA-People.domain.suffix: " + 
        this.ca_domain_suffix);
    }
    this.authentication_authDataConsumerServiceForwardURL = config.getInitParameter("authentication_authDataConsumerServiceForwardURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- authentication_authDataConsumerServiceForwardURL: " + 
        this.authentication_authDataConsumerServiceForwardURL);
    }
    this.authentication_authDataConsumerServicePOSTURL = config.getInitParameter("authentication_authDataConsumerServicePOSTURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- authentication_authDataConsumerServicePOSTURL: " + 
        this.authentication_authDataConsumerServicePOSTURL);
    }
    this.authentication_authDataConsumerServiceTransferMode = config.getServletContext().getInitParameter(
      "authentication_authDataConsumerServiceTransferMode");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- authentication_authDataConsumerServiceTransferMode: " + 
        this.authentication_authDataConsumerServiceTransferMode);
    }
    this.registration_authDataConsumerServiceForwardURL = config.getInitParameter("registration_authDataConsumerServiceForwardURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- registration_authDataConsumerServiceForwardURL: " + 
        this.registration_authDataConsumerServiceForwardURL);
    }
    this.registration_authDataConsumerServicePOSTURL = config.getInitParameter("registration_authDataConsumerServicePOSTURL");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- registration_authDataConsumerServicePOSTURL: " + 
        this.registration_authDataConsumerServicePOSTURL);
    }
    this.registration_authDataConsumerServiceTransferMode = config.getServletContext().getInitParameter(
      "registration_authDataConsumerServiceTransferMode");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- registration_authDataConsumerServiceTransferMode: " + 
        this.registration_authDataConsumerServiceTransferMode);
    }
    this.authDataConsumerService_postResponsePage = config.getInitParameter("authDataConsumerService_postResponsePage");
    if (logger.isDebugEnabled()) {
      logger.debug("init(ServletConfig)- authDataConsumerService_postResponsePage: " + 
        this.authDataConsumerService_postResponsePage);
    }
  }
}


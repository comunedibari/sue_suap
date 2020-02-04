package it.cefriel.people.ssl;

import it.cefriel.people.exceptions.security.CertificateRevokedException;
import it.cefriel.people.exceptions.security.CertificateUnknownException;
import it.cefriel.people.exceptions.security.NoAlternativeCRLDistributionPointsException;
import it.cefriel.people.exceptions.security.NoCRLDistributionPointsException;
import it.cefriel.people.exceptions.security.UnableToUpdateCRLException;
import it.cefriel.utility.io.FileHelper;
import it.cefriel.utility.security.X509CRLHelper;
import it.cefriel.utility.security.X509CertificateHelper;
import it.people.sirac.smartcardprofile.SmartCardProfile;
import it.people.sirac.smartcardprofile.SmartCardProfiles;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Principal;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CertificateUtils
{
  private static final Logger logger = LoggerFactory.getLogger(CertificateUtils.class);
  
  public static void updateCRL(String CRLfileName, X509Certificate cert, SmartCardProfile smartCardProfile)
    throws NoAlternativeCRLDistributionPointsException, NoCRLDistributionPointsException, UnableToUpdateCRLException
  {
    boolean isCrlCheckOnCardEnabled = smartCardProfile.isCRLCheckOnCardEnabled();
    
    List distributionPoints = null;
    if (isCrlCheckOnCardEnabled)
    {
      distributionPoints = X509CertificateHelper.getCRLDistributionPoints(cert);
      if ((distributionPoints == null) || (distributionPoints.size() == 0))
      {
        logger.error("CertificateUtils::updateCRL - no CRL distribution points found on the card");
        throw new NoCRLDistributionPointsException("CertificateUtils::updateCRL - no CRL distribution points found on the card");
      }
    }
    else
    {
      String[] alternativeDistributionPoints = smartCardProfile.getCRLAlternateDistributionPoints();
      if (alternativeDistributionPoints.length > 0)
      {
        distributionPoints = new ArrayList();
        for (int i = 0; i < alternativeDistributionPoints.length; i++) {
          distributionPoints.add(alternativeDistributionPoints[i]);
        }
      }
      else
      {
        logger.error("CertificateUtils::updateCRL - no alternative CRL distribution points found in the configuration file");
        throw new NoAlternativeCRLDistributionPointsException("CertificateUtils::updateCRL - no alternative CRL distribution points found in the configuration file");
      }
    }
    boolean crlUpdated = false;
    for (int i = 0; i < distributionPoints.size(); i++)
    {
      String crlDistributionPoint = (String)distributionPoints.get(i);
      byte[] CRLbytes = X509CRLHelper.downloadCRL(crlDistributionPoint);
      if (CRLbytes.length > 0)
      {
        FileHelper.writeBytesToFile(CRLfileName, CRLbytes);
        crlUpdated = true;
        break;
      }
    }
    if (!crlUpdated)
    {
      logger.error("CertificateUtils::updateCRL - unable to update the CRL from the distribution points specified!");
      throw new UnableToUpdateCRLException("CertificateUtils::updateCRL - unable to update the CRL from the distribution points specified!");
    }
  }
  
  public static CardUserData getUserInfo(X509Certificate sessionCert)
    throws CertificateRevokedException
  {
    String issuerCN = X509CertificateHelper.getIssuerCN(sessionCert);
    String certDN = sessionCert.getSubjectDN().getName().toUpperCase();
    
    CardUserData datiUtente = new CardUserData();
    try
    {
      if ((issuerCN.equals("InfoCamere Servizi di Certificazione")) || 
        (issuerCN.equals("InfoCamere Servizi di Certificazione 2")))
      {
        if (!X509CRLHelper.isRevoked("C:\\CERTS\\INFOCAMERE\\CRL01.CRL", sessionCert))
        {
          StringTokenizer st = new StringTokenizer(certDN, ",");
          datiUtente.setTipoCarta("INFOCAMERE CNS");
          StringTokenizer st2;
          for (; st.hasMoreTokens(); st2.hasMoreTokens())
          {
            String token = st.nextToken();
            st2 = new StringTokenizer(token, "=");
            String first = st2.nextToken();
            if (st2.hasMoreTokens())
            {
              String second = st2.nextToken();
              if (first.equals(" GIVENNAME"))
              {
                datiUtente.setNome(second);
              }
              else if (first.equals(" SURNAME"))
              {
                datiUtente.setCognome(second);
              }
              else if (first.equals(" EMAILADDRESS"))
              {
                datiUtente.setEmail(second);
              }
              else if (first.equals(" SERIALNUMBER"))
              {
                datiUtente.setCodiceFiscale(second);
              }
              else if (first.equals(" CN"))
              {
                StringTokenizer st3 = new StringTokenizer(second, "/");
                while (st3.hasMoreTokens())
                {
                  String cnElement = st3.nextToken();
                  if (cnElement.charAt(0) == '"')
                  {
                    cnElement = st3.nextToken();
                    int pos = cnElement.indexOf(".");
                    datiUtente.setCodiceCarta(cnElement.substring(0, pos - 1));
                    break;
                  }
                }
              }
            }
          }
        }
        else
        {
          throw new CertificateRevokedException("The certificate is revoked!");
        }
      }
      else if (issuerCN.equals("POSTECOM CA1"))
      {
        if (!X509CRLHelper.isRevoked("file://C:\\CERTS\\POSTECOM\\CRL0", sessionCert))
        {
          int pos = certDN.indexOf("=");
          String temp = certDN.substring(pos + 1);
          String temp2 = temp.substring(1, temp.length());
          StringTokenizer st = new StringTokenizer(temp2, ",");
          datiUtente.setTipoCarta("Poste");
          String firstToken = st.nextToken();
          StringTokenizer st2 = new StringTokenizer(firstToken, "/");
          while (st2.hasMoreTokens())
          {
            String token = st2.nextToken();
            StringTokenizer st2_1 = new StringTokenizer(token, "=");
            String first = st2_1.nextToken();
            String second = st2_1.nextToken();
            if (first.equals("C")) {
              datiUtente.setCognome(second);
            } else if (first.equals("N")) {
              datiUtente.setNome(second);
            } else if (first.equals("D")) {
              datiUtente.setDataNascita(second);
            }
          }
          String secondToken = st.nextToken();
          StringTokenizer st3 = new StringTokenizer(secondToken, "=");
          while (st3.hasMoreTokens())
          {
            String first = st3.nextToken();
            String second = st3.nextToken();
            if (first.equals(" EMAILADDRESS")) {
              datiUtente.setEmail(second);
            }
          }
          String thirdToken = st.nextToken();
          StringTokenizer st4 = new StringTokenizer(thirdToken, "=");
          while (st4.hasMoreTokens())
          {
            String first = st4.nextToken();
            if (first.equals(" CN"))
            {
              String subToken = st4.nextToken();
              StringTokenizer st5 = new StringTokenizer(subToken, "/");
              while (st5.hasMoreTokens())
              {
                st5.nextToken();
                st5.nextToken();
                datiUtente.setCodiceFiscale(st5.nextToken());
                datiUtente.setCodiceCarta(st5.nextToken());
              }
            }
          }
        }
        else
        {
          throw new CertificateRevokedException("The certificate is revoked!");
        }
      }
      else if (issuerCN.equals("SUBCA-EMISSIONE1-MI"))
      {
        datiUtente.setTipoCarta("Carta d'identit� elettronica");
        StringTokenizer st = new StringTokenizer(certDN, ",");
        String token = st.nextToken();
        StringTokenizer st2 = new StringTokenizer(token, "=");
        String first = st2.nextToken();
        if (first.equals("CN"))
        {
          String second = st2.nextToken();
          second = second.substring(1, second.length());
          int pos = second.indexOf(".");
          datiUtente.setCodiceCarta(second.substring(0, pos - 1));
        }
      }
      else if (issuerCN.equals("CNS CA Cittadini"))
      {
        datiUtente.setTipoCarta("TRUSTITALIA CNS");
        StringTokenizer st = new StringTokenizer(certDN, ",");
        while (st.hasMoreTokens())
        {
          String token = st.nextToken();
          StringTokenizer st2 = new StringTokenizer(token, "=");
          String first = st2.nextToken();
          if (first.equals(" CN"))
          {
            String second = st2.nextToken();
            second = second.substring(1, second.length());
            int pos = second.indexOf("/");
            int pos2 = second.indexOf(".");
            datiUtente.setCodiceFiscale(second.substring(0, pos));
            datiUtente.setCodiceCarta(second.substring(pos + 1, pos2));
          }
        }
      }
      else if ((issuerCN.equals("Regione Lombardia Certification Authority Cittadini Virtuale")) || (issuerCN.equals("Regione Lombardia Certification Authority Cittadini")))
      {
        if (!X509CRLHelper.isRevoked("file://C:\\CERTS\\CRS\\CRL_MCPV_CRL1.crl", sessionCert))
        {
          datiUtente.setTipoCarta("Carta Regionale dei Servizi");
          StringTokenizer st = new StringTokenizer(certDN, ",");
          while (st.hasMoreTokens())
          {
            String token = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(token, "=");
            String first = st2.nextToken();
            if (first.equals("CN"))
            {
              String second = st2.nextToken();
              second = second.substring(1, second.length());
              int pos = second.indexOf("/");
              int pos2 = second.indexOf(".");
              datiUtente.setCodiceFiscale(second.substring(0, pos));
              datiUtente.setCodiceCarta(second.substring(pos + 1, pos2));
            }
          }
        }
      }
      else
      {
        datiUtente.setTipoCarta("Carta sconosciuta");
      }
    }
    catch (IOException e)
    {
      if (logger.isDebugEnabled()) {
        logger.error("ERROR!" + e.getMessage());
      }
    }
    catch (CertificateException e)
    {
      if (logger.isDebugEnabled()) {
        logger.error("ERROR!" + e.getMessage());
      }
    }
    catch (CRLException e)
    {
      if (logger.isDebugEnabled()) {
        logger.error("ERROR!" + e.getMessage());
      }
    }
    return datiUtente;
  }
  
  public static SmartCardProfile getCertificateProfile(X509Certificate sessionCert, SmartCardProfiles smartCardProfiles)
  {
    SmartCardProfile smartCardProfile = null;
    
    String issuerCN = X509CertificateHelper.getIssuerCN(sessionCert);
    String certDN = sessionCert.getSubjectDN().getName();
    try
    {
      smartCardProfile = smartCardProfiles.getProfileByIssuerAndCertDN(issuerCN, certDN);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return smartCardProfile;
  }
  
  public static CardUserData getUserInfo(X509Certificate sessionCert, SmartCardProfile smartCardProfile)
    throws CertificateRevokedException, CertificateUnknownException, CertificateExpiredException, CertificateNotYetValidException
  {
    String issuerCN = X509CertificateHelper.getIssuerCN(sessionCert);
    String certDN = sessionCert.getSubjectDN().getName();
    try
    {
      sessionCert.checkValidity();
    }
    catch (CertificateExpiredException e1)
    {
      logger.error("CertificateUtils::getUserInfo() - Certificate expired.");
      throw new CertificateExpiredException("Certificate Issuer: " + issuerCN + " - Certificate DN: " + certDN);
    }
    catch (CertificateNotYetValidException e1)
    {
      logger.error("CertificateUtils::getUserInfo() - Certificate not yet valid.");
      throw new CertificateNotYetValidException("Certificate Issuer: " + issuerCN + " - Certificate DN: " + certDN);
    }
    HashMap certificateInfoMap = null;
    try
    {
      certificateInfoMap = SmartCardProfiles.parseCertDN(certDN, smartCardProfile.getDNPattern());
    }
    catch (Exception e)
    {
      logger.error("CertificateUtils::getUserInfo() - Unknown certificate received.");
      throw new CertificateUnknownException("Certificate Issuer: " + issuerCN + " - Certificate DN: " + certDN);
    }
    certificateInfoMap.put("TipoCarta", smartCardProfile.getCardType());
    certificateInfoMap.put("DatePattern", smartCardProfile.getDatePattern());
    
    return new CardUserData(certificateInfoMap);
  }
  
  public static boolean isRevoked(X509Certificate certificate, SmartCardProfile smartCardProfile, String crlFileLocation)
  {
    boolean isRevoked = true;
    

    boolean isCrlInternalUpdateIntervalOverridden = smartCardProfile.isCRLInternalUpdateIntervalOverridden();
    
    String crlFileName = smartCardProfile.getCRLFilename();
    int updateIntervalHours = 0;
    if (isCrlInternalUpdateIntervalOverridden) {
      updateIntervalHours = smartCardProfile.getCRLUpdateIntervalHours();
    }
    String crlPathname = crlFileLocation + File.separator + crlFileName;
    if (!X509CRLHelper.isCRLFileUpToDate(crlPathname, updateIntervalHours)) {
      try
      {
        updateCRL(crlPathname, certificate, smartCardProfile);
      }
      catch (NoAlternativeCRLDistributionPointsException e1)
      {
        logger.error("CertificateUtils::isRevoked() - CRL is out-of-date and cannot be updated due to missing alternative distribution points!");
      }
      catch (NoCRLDistributionPointsException e1)
      {
        logger.error("CertificateUtils::isRevoked() - CRL is out-of-date and cannot be updated due to missing distribution points on the card!");
      }
      catch (UnableToUpdateCRLException e1)
      {
        logger.error("CertificateUtils::isRevoked() - CRL is out-of-date and cannot be updated due to unknown problem!");
        e1.printStackTrace();
      }
    }
    try
    {
      if (!X509CRLHelper.isRevoked(crlPathname, certificate)) {
        isRevoked = false;
      }
    }
    catch (FileNotFoundException e)
    {
      logger.error("CertificateUtils::isRevoked() - The specified file for the CRL does not exist!");
    }
    catch (CRLException e)
    {
      logger.error("CertificateUtils::isRevoked() - Problems reading the CRL file!");
    }
    catch (Exception e)
    {
      logger.error("CertificateUtils::isRevoked() - Unexpected error!");
      e.printStackTrace();
    }
    return isRevoked;
  }
  
  public static void main(String[] args)
  {
    try
    {
      FileInputStream fis = new FileInputStream("C:\\Certs\\certificato_crs_fvg.cer");
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate)cf.generateCertificate(fis);
      System.out.println("DN: " + cert.getSubjectDN().toString());
      System.out.println("Issuer: " + cert.getIssuerDN().toString());
      List dp = X509CertificateHelper.getCRLDistributionPoints(cert);
      for (int i = 0; i < dp.size(); i++) {
        System.out.println(dp.get(i));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}

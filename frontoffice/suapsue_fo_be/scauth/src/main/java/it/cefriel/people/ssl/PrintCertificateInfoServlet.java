package it.cefriel.people.ssl;

import it.people.sirac.core.SiracHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.security.cert.X509Certificate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PrintCertificateInfoServlet
  extends HttpServlet
{
  private static final Logger logger = LoggerFactory.getLogger(PrintCertificateInfoServlet.class);
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
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
    
    PrintWriter out = response.getWriter();
    out.write("Issuer DN:   " + sessionCertificate.getIssuerDN().getName());
    out.write("\n\nSubject DN: " + sessionCertificate.getSubjectDN().getName());
    out.flush();
    out.close();
  }
}


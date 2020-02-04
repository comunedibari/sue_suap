package it.people.user.filter;

import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.core.PplUserData;
import it.people.core.exception.ServiceException;
import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility.XPathReader;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloPersonaGiuridica;
import it.people.fsl.servizi.oggetticondivisi.profili.ProfiloTitolare;
import it.people.process.GenericProcess;
import it.people.sirac.authentication.beans.PplUserDataExtended;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyAccessFilter
  implements Filter
{
	private static Logger logger = LoggerFactory.getLogger(LegacyAccessFilter.class);
  private static final boolean debug = true;
  private GenericProcess process;
  HashMap hm = new HashMap();
  private FilterConfig filterConfig = null;
  
  public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain _chain)
    throws IOException, ServletException
  {
    log("LegacyAccessFilter:doFilter()");
    

    HttpServletRequest request = (HttpServletRequest)_request;
    HttpServletResponse response = (HttpServletResponse)_response;
    HttpSession session = request.getSession();
    String nomeServizio = this.filterConfig.getInitParameter("nameService");
    String idBookmark = request.getParameter("idBookmark");
    if (session.getAttribute("pplProcess") != null)
    {
      this.process = ((GenericProcess)session.getAttribute("pplProcess"));
      if ((this.process.getProcessName() != null) && (this.process.getProcessName().equalsIgnoreCase(nomeServizio.toLowerCase())))
      {
        this.hm.put("sedeLegale", "SEDE LEGALE");
        this.hm.put("idSedeLegale", "SL");
        boolean isAnonymousUser = session.getAttribute("it.people.sirac.authenticated_user") == null;
        if (!isAnonymousUser) {
          try
          {
            ProcessData processData = (ProcessData)this.process.getData();
            if (idBookmark != null)
            {
              session.setAttribute("leggiWS", "S");
            }
            else if ((processData.getCustomObject() == null) || ((session.getAttribute("leggiWS") != null) && (session.getAttribute("leggiWS").equals("S"))))
            {
              session.setAttribute("leggiWS", "N");
              String codiceFiscale = getCodiceFiscale(processData);
              
              String responseResidente = callAnagraficaWS(processData, codiceFiscale, session, true);
              if (!valorizzaProfiloTitolareAutenticato(processData, responseResidente))
              {
                String responseNonresidente = callAnagraficaWS(processData, codiceFiscale, session, false);
                if (!valorizzaProfiloTitolareAutenticato(processData, responseNonresidente))
                {
                  PplUser peopleUser = PeopleContext.create(request).getUser();
                  this.hm.put("tipologiaUtente", "UNKN");
                  this.hm.put("codiceFiscale", UtilHelper.getCodiceFiscaleFromProfiloOperatore(session));
                  this.hm.put("nome", peopleUser.getUserData().getNome());
                  this.hm.put("cognome", peopleUser.getUserData().getCognome());
                  this.hm.put("dataNascita", peopleUser.getUserData().getDataNascita());
                  this.hm.put("email", peopleUser.getUserData().getEmailaddress());
                  this.hm.put("sesso", peopleUser.getUserData().getSesso());
                }
              }
              String tipoQualificaSess = (String)session.getAttribute("it.people.sirac.accr.tipoQualificaCorrente");
              if ((tipoQualificaSess != null) && (!tipoQualificaSess.equals("Utente")))
              {
                this.hm.put("intermediarioNome", processData.getProfiloRichiedente().getNome());
                this.hm.put("intermediarioCognome", processData.getProfiloRichiedente().getCognome());
                this.hm.put("intermediarioEmail", processData.getProfiloRichiedente().getDomicilioElettronico());
              }
              processData.setCustomObject(this.hm);
            }
          }
          catch (Throwable ex)
          {
            logger.error("", ex);
          }
        }
      }
    }
    Throwable problem = null;
    try
    {
      _chain.doFilter(request, response);
    }
    catch (Throwable t)
    {
      problem = t;
      t.printStackTrace();
    }
    if (problem != null)
    {
      if ((problem instanceof ServletException)) {
        throw ((ServletException)problem);
      }
      if ((problem instanceof IOException)) {
        throw ((IOException)problem);
      }
      sendProcessingError(problem, response);
    }
  }
  
  private String getCodiceFiscale(ProcessData processData)
  {
    ProfiloTitolare titolare = processData.getProfiloTitolare();
    if (titolare.getProfiloTitolarePF() != null) {
      return titolare.getProfiloTitolarePF().getCodiceFiscale();
    }
    if (titolare.getProfiloTitolarePG() != null)
    {
      setInformazioniAzienda(processData, titolare.getProfiloTitolarePG());
      return titolare.getProfiloTitolarePG().getRappresentanteLegale().getCodiceFiscale();
    }
    if (processData.getProfiloRichiedente() != null) {
      return processData.getProfiloRichiedente().getCodiceFiscale();
    }
    logger.error("ricerca codice fiscale");
    return "";
  }
  
  private void setInformazioniAzienda(ProcessData processData, ProfiloPersonaGiuridica profiloTitolarePG)
  {
    this.hm.put("aziendaCodiceFiscale", profiloTitolarePG.getCodiceFiscale());
    this.hm.put("aziendaRagioneSociale", profiloTitolarePG.getDenominazione());
    this.hm.put("aziendaPartitaIVA", profiloTitolarePG.getPartitaIva());
    this.hm.put("aziendaSedeLegale", profiloTitolarePG.getSedeLegale());
    this.hm.put("aziendaDomicilioElettronico", profiloTitolarePG.getDomicilioElettronico());
  }
  
  private String callAnagraficaWS(ProcessData processData, String codiceFiscale, HttpSession session, boolean residente)
    throws ServiceException
  {
    String statoResidenza = "";
    if (residente)
    {
      statoResidenza = "RES";
      this.hm.put("tipologiaUtente", "ANAG");
    }
    else
    {
      statoResidenza = "NON_RES";
      this.hm.put("tipologiaUtente", "SEAP");
    }
    PplUserDataExtended userdata = (PplUserDataExtended)session.getAttribute("it.people.sirac.authenticated_user_data");
    String samplexml = "<PrecompilazioneBean><Input><CampoPrecompilazioneBean><Codice>STATO</Codice><Descrizione>" + statoResidenza + "</Descrizione>" + "</CampoPrecompilazioneBean>" + "<CampoPrecompilazioneBean>" + "<Codice>COD_CODFISCALE</Codice>" + "<Descrizione>" + codiceFiscale + "</Descrizione>" + "</CampoPrecompilazioneBean>" + "</Input>" + "<CodEnte>" + userdata.getIdComuneRegistrazione() + "</CodEnte>" + "</PrecompilazioneBean>";
    try
    {
      return this.process.callService("WSANAGRAFE", samplexml);
    }
    catch (Exception ex)
    {
      logger.error("", ex);
    }
    return "";
  }
  
  private String getXPathFromCodice(String codice)
  {
    StringBuffer result = new StringBuffer();
    result.append("/PrecompilazioneBean/Output/CampoPrecompilazioneBean[Codice[.='").append(codice).append("']]/Descrizione[1]");
    return result.toString();
  }
  
  private boolean valorizzaProfiloTitolareAutenticato(ProcessData processData, String responseResidente)
  {
    XPathReader xpr = new XPathReader(responseResidente);
    String DESCRIZIONE_ERRORE = "/PrecompilazioneBean/DescrizioneErrore";
    String CODICE_FISCALE = getXPathFromCodice("COD_CODFISCALE");
    String errore = xpr.readElementString(DESCRIZIONE_ERRORE);
    if ((errore == null) || ("".equals(errore)))
    {
      String codiceFiscale = xpr.readElementString(CODICE_FISCALE);
      if ((codiceFiscale != null) && (!"".equals(codiceFiscale)))
      {
        this.hm.put("codiceFiscale", codiceFiscale);
        this.hm.put("cognome", xpr.readElementString(getXPathFromCodice("COD_COGNOME")));
        this.hm.put("matricola", xpr.readElementString(getXPathFromCodice("COD_MATRICOLA")));
        this.hm.put("nome", xpr.readElementString(getXPathFromCodice("COD_NOME")));
        this.hm.put("sesso", xpr.readElementString(getXPathFromCodice("COD_SESSO")));
        this.hm.put("dataNascita", xpr.readElementString(getXPathFromCodice("COD_DATANASCITA")));
        this.hm.put("indirizzo", xpr.readElementString(getXPathFromCodice("COD_INDIRIZZO")));
        this.hm.put("codiceCittadinanza", xpr.readElementString(getXPathFromCodice("CODCITTADINANZA")));
        this.hm.put("cittadinanza", xpr.readElementString(getXPathFromCodice("CITTADINANZA")));
        this.hm.put("codiceStatoNascita", xpr.readElementString(getXPathFromCodice("CODSTATONASCITA")));
        this.hm.put("statoNascita", xpr.readElementString(getXPathFromCodice("STATONASCITA")));
        this.hm.put("codiceProvinciaNascita", xpr.readElementString(getXPathFromCodice("CODPROVINCIANASCITA")));
        this.hm.put("provinciaNascita", xpr.readElementString(getXPathFromCodice("PROVINCIANASCITA")));
        this.hm.put("codiceComuneNascita", xpr.readElementString(getXPathFromCodice("CODCOMUNENASCITA")));
        this.hm.put("comuneNascita", xpr.readElementString(getXPathFromCodice("COMUNENASCITA")));
        this.hm.put("statoResidenza", xpr.readElementString(getXPathFromCodice("STATORES")));
        this.hm.put("codiceStatoResidenza", xpr.readElementString(getXPathFromCodice("CODSTATORES")));
        this.hm.put("internoResidenza", xpr.readElementString(getXPathFromCodice("INTERNORES")));
        this.hm.put("codiceProvinciaResidenza", xpr.readElementString(getXPathFromCodice("CODPROVINCIARES")));
        this.hm.put("provinciaResidenza", xpr.readElementString(getXPathFromCodice("PROVINCIARES")));
        this.hm.put("codiceComuneResidenza", xpr.readElementString(getXPathFromCodice("CODCOMUNERES")));
        this.hm.put("comuneResidenza", xpr.readElementString(getXPathFromCodice("COMUNERES")));
        this.hm.put("capResidenza", xpr.readElementString(getXPathFromCodice("CAPRES")));
        this.hm.put("viaResidenza", xpr.readElementString(getXPathFromCodice("VIARES")));
        this.hm.put("codiceViaResidenza", xpr.readElementString(getXPathFromCodice("CODVIARES")));
        this.hm.put("civicoResidenza", xpr.readElementString(getXPathFromCodice("CIVICORES")));
        this.hm.put("letteraResidenza", xpr.readElementString(getXPathFromCodice("LETTERARES")));
        this.hm.put("coloreResidenza", xpr.readElementString(getXPathFromCodice("COLORERES")));
        this.hm.put("fax", xpr.readElementString(getXPathFromCodice("FAX")));
        this.hm.put("telefono", xpr.readElementString(getXPathFromCodice("TELEFONO")));
        this.hm.put("email", xpr.readElementString(getXPathFromCodice("EMAIL")));
        
        return true;
      }
      return false;
    }
    return false;
  }
  
  public FilterConfig getFilterConfig()
  {
    return this.filterConfig;
  }
  
  public void setFilterConfig(FilterConfig filterConfig)
  {
    this.filterConfig = filterConfig;
  }
  
  public void destroy() {}
  
  public void init(FilterConfig filterConfig)
  {
    this.filterConfig = filterConfig;
    if (filterConfig != null) {
      log("LegacyAccessFilter:Initializing filter");
    }
  }
  
  public String toString()
  {
    if (this.filterConfig == null) {
      return "LegacyAccessFilter()";
    }
    StringBuffer sb = new StringBuffer("LegacyAccessFilter(");
    sb.append(this.filterConfig);
    sb.append(")");
    

    return sb.toString();
  }
  
  private void sendProcessingError(Throwable t, ServletResponse response)
  {
    String stackTrace = getStackTrace(t);
    if ((stackTrace != null) && (!stackTrace.equals(""))) {
      try
      {
        response.setContentType("text/html");
        PrintStream ps = new PrintStream(response.getOutputStream());
        PrintWriter pw = new PrintWriter(ps);
        pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
        

        pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
        pw.print(stackTrace);
        pw.print("</pre></body>\n</html>");
        pw.close();
        ps.close();
        response.getOutputStream().close();
      }
      catch (Exception ex) {}
    } else {
      try
      {
        PrintStream ps = new PrintStream(response.getOutputStream());
        t.printStackTrace(ps);
        ps.close();
        response.getOutputStream().close();
      }
      catch (Exception ex) {}
    }
  }
  
  public static String getStackTrace(Throwable t)
  {
    String stackTrace = null;
    try
    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      pw.close();
      sw.close();
      stackTrace = sw.getBuffer().toString();
    }
    catch (Exception ex) {}
    return stackTrace;
  }
  
  public void log(String msg)
  {
    this.filterConfig.getServletContext().log(msg);
  }
}

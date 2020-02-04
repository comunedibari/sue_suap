/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
/*

  Licenza:	    Licenza Progetto PEOPLE
  Fornitore:    CEFRIEL
  Autori:       M. Pianciamore, P. Selvini

  Questo codice sorgente � protetto dalla licenza valida nell'ambito del
  progetto PEOPLE. La propriet� intellettuale di questo codice � e rester�
  esclusiva di "CEFRIEL Societ� Consortile a Responsabilit� Limitata" con
  sede legale in via Renato Fucini 2, 20133 Milano (MI).

  Disclaimer:

  COVERED CODE IS PROVIDED UNDER THIS LICENSE ON AN "AS IS" BASIS, WITHOUT
  WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, WITHOUT 
  LIMITATION, WARRANTIES THAT THE COVERED CODE IS FREE OF DEFECTS, MERCHANTABLE,
  FIT FOR A PARTICULAR PURPOSE OR NON-INFRINGING. THE ENTIRE RISK AS TO THE
  QUALITY AND PERFORMANCE OF THE COVERED CODE IS WITH YOU. SHOULD ANY COVERED
  CODE PROVE DEFECTIVE IN ANY RESPECT, YOU (NOT THE INITIAL DEVELOPER OR ANY
  OTHER CONTRIBUTOR) ASSUME THE COST OF ANY NECESSARY SERVICING, REPAIR OR
  CORRECTION.
    
*/

/*
 * Created on Jan 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.idppeople.web;

import it.people.core.PplUserData;
import it.people.sirac.authentication.beans.PplUserDataExtended;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.idp.authentication.AuthenticationClientAdapter;
import it.people.sirac.idp.beans.RegBean;
import it.people.sirac.idp.beans.ResAuthBean;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class IDPLoginServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(it.idppeople.web.IDPLoginServlet.class);
  
  protected String portAddress_prefix = ""; 
  
  protected String wsauth_address;
  protected AuthenticationClientAdapter authWS = null;
  
  protected int timeout=120000;

  protected String idp_domain_suffix="";
  
  protected String keystorePath = "";
  protected String certificateAlias = "";
  protected char[] keystorePassword = "".toCharArray();
  
  protected KeyStore ks = null;


  protected String postResponsePage = null;

  protected String loginPage = null;

  protected String intersiteTransferServiceForwardURL = null; // Aggiunto il 31-08-2005
  
  protected String intersiteTransferServicePOSTURL = null; // Aggiunto il 31-08-2005
  
  protected String intersiteTransferServiceTransferMode = null; // Aggiunto il 31-08-2005
  
  protected String smartCardLoginRedirectURL = null;

    /**
     * Constructor of the object.
     */
    public IDPLoginServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param request the request send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doPost(request,response);
        
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param request the request send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
      if (logger.isDebugEnabled()) {
        logger.debug("doPost() - start");
      }
        
      boolean hasLoginData = Boolean.valueOf((String)request.getParameter("hasLoginData")).booleanValue();
      
        //TO BE FIXED: Con apache la requestURL riportata da Tomcat non � corretta (riporta https://mandrake.cefriel.it:8000 invece di https://mandrake.cefriel.it:9443
        String requestURL = request.getRequestURL().toString(); // Es. "http://localhost/people/initProcess.do"
        String queryString = request.getQueryString();

        String userID = (String)request.getParameter("uid");
        String userpwd = (String)request.getParameter("upwd");
        // Legge il parametro con la pagina target su cui fare il post dei dati di autenticazione
        String target = (String)request.getParameter("TARGET");
        
        // FIX 2005-05-30 & 2005-08-31 START (M. Pianciamore)
        // Imposta il tipo di autenticazione richiesta sulla base del valore del parametro authType specificato sulla query string 
        String authTypeInRequest = (String)request.getParameter("authType");
        String authType = null;
        String authMethod = null;
        String authStatus = null;
        
        //boolean forwardToError = false;
        
        //se il parametro target non � presente nella query string solleva una eccezione
        if (target==null) {
          SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, "Parametro TARGET mancante in request: \n" + requestURL);
        }
        
        // Se il parametro authType non � presente nella query String 
        // cerca di stabilire il tipo di autenticazione richiesta in base all' URL specificato nella richiesta
        String requestedResource = request.getServletPath();
        if (authTypeInRequest==null && ! "/login".equals(requestedResource)) {
        	authType=authMethod=null;
        	
        	if ("/loginWeak".equals(requestedResource)) {
            authTypeInRequest=authType=SiracConstants.SIRAC_AUTH_TYPE_WEAK;
            authMethod = SiracConstants.AUTHENTICATION_METHOD_PWD;
          } else if ("/loginStrong".equals(requestedResource)) {
            authTypeInRequest=authType=SiracConstants.SIRAC_AUTH_TYPE_STRONG;
            authMethod = SiracConstants.AUTHENTICATION_METHOD_PIN;
          } else { 
            authTypeInRequest=authType=SiracConstants.SIRAC_AUTH_TYPE_UNKNOWN;
            authMethod = null;
            SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, "Risorsa sconosciuta: " + requestURL);
          }
        } else {
          if (SiracConstants.SIRAC_AUTH_TYPE_WEAK.equalsIgnoreCase(authTypeInRequest)) {
            authType = SiracConstants.SIRAC_AUTH_TYPE_WEAK;
            authMethod = SiracConstants.AUTHENTICATION_METHOD_PWD;
          } else if (SiracConstants.SIRAC_AUTH_TYPE_STRONG.equalsIgnoreCase(authTypeInRequest)) {
            authType = SiracConstants.SIRAC_AUTH_TYPE_STRONG;
            authMethod = SiracConstants.AUTHENTICATION_METHOD_PIN;
          } else {
            authType=SiracConstants.SIRAC_AUTH_TYPE_UNKNOWN;
            authMethod = null;
            if (logger.isDebugEnabled()) {
              logger.debug("Errore. Il tipo di autenticazione richiesta non è supportato (" + authTypeInRequest + ")");
            }
            SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, "Errore. Il tipo di autenticazione richiesta non è supportato (" + authTypeInRequest + ")");
          }
        }

        // Imposta il valore di authType nella request che verr� utilizzato dalla login.jsp
        request.setAttribute("authType", authType);
        
        // Imposta nella request il valore di smartcardLoginRedirectURL utiilizzato dalla login.jsp
        
        String smartcardLoginRedirectPage = null;
        
        if (smartCardLoginRedirectURL.indexOf("?") !=-1) {  
          smartcardLoginRedirectPage = smartCardLoginRedirectURL + "&" + queryString;
        } else {
          smartcardLoginRedirectPage = smartCardLoginRedirectURL + "?" + queryString;
          
        }
        request.setAttribute("smartcardLoginRedirectURL", smartcardLoginRedirectPage);
        
        // FIX 2005-05-30 & 2005-08-31 END
        
        if (!hasLoginData) {
          request.setAttribute("upwd", "");
          //RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
          RequestDispatcher rd = request.getRequestDispatcher(loginPage);
          rd.forward(request, response);

          if (logger.isDebugEnabled()) {
            logger.debug("doPost() - end");
          }
          return;
        }

        String codiceFiscale = SiracHelper.getCodiceFiscale(userID);
        String idIDP = SiracHelper.getIdCA(userID);
        
        if (!idp_domain_suffix.equals(idIDP)) {
          request.setAttribute("upwd", "");
          request.setAttribute("errorMessage", "Indispensabile specificare nello userName il suffisso che identifica l'IDP-People (" + idp_domain_suffix + ").");
          //RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
          RequestDispatcher rd = request.getRequestDispatcher(loginPage);
          
          rd.forward(request, response);

          if (logger.isDebugEnabled()) {
            logger.debug("doPost() - end");
          }
          return;
        }
        
          
        // Nota: in questo esempio si utilizza lo stesso web service per l'autenticazione con pwd e per quella con pin
        if (!externalAuthenticate(codiceFiscale,userpwd, authType)) {
          request.setAttribute("upwd", "");
          request.setAttribute("errorMessage", "Autenticazione Fallita.");
          //RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
          RequestDispatcher rd = request.getRequestDispatcher(loginPage);
          rd.forward(request, response);
          if (logger.isDebugEnabled()) {
            logger.debug("doPost() - Autenticazione fallita per l'utente " + codiceFiscale);
          }
          return;
        } 
        PplUserDataExtended userData = null;
        // Retrieve userData by invoking remote WS
        try {
          userData = authentication_getUserData(codiceFiscale, userpwd);
        } catch (Exception e) {
          logger.error("doPost() - Exception" + e.getMessage());
          SiracHelper.forwardToErrorPageWithRuntimeException(request, response, e, 
              "Impossibile recuperare il profilo di registrazione dell'utente " + userID);
        }
        
        Map attributesMap = createAttributesMapfromPplUserDataExtended(userData);

// ------ BEGIN FIX 31-08-2005 -------------------------
//        SAMLDataHolderBean samlDataHolder = new SAMLDataHolderBean();
//        
//        samlDataHolder.setAttributesMap(attributesMap);
//        samlDataHolder.setUserID(userID);
//        samlDataHolder.setTargetURL(target);
//        
//        try {
//          samlDataHolder.setAuthenticationMethod(SAMLHelper.createAuthMetdod(authType));
//        } catch (Exception e1) {
//          logger.error("doPost() - Exception" + e1.getMessage());
//          response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }
//        
//        request.setAttribute("reqSAMLDataHolder", samlDataHolder);
//        
//        String serviceResource = postResponsePage; // Es. "/IntersiteTransferServlet";
//        RequestDispatcher rd = request.getRequestDispatcher(serviceResource);
//        
//        rd.forward(request, response);

        
/* Codice Spostato in SiracHelper.prepareAuthResponseBase64    
     
      // Utilizzo della classe AuthDataHolder per passare l'xml contenente i dati di autenticazione
      IAuthDataHolder authDataHolder = new AuthDataHolder();
      authDataHolder.setAuthenticationResponseStatus(SiracConstants.AUTHENTICATION_STATUS_SUCCESS);
      authDataHolder.setAuthenticationType(authType);
      authDataHolder.setTargetURL(target);
      authDataHolder.setSubjectUserID(userID);
      if (attributesMap != null) {
        authDataHolder.setSubjectUserAttributes(attributesMap);
      }
      if (authMethod != null) {
        authDataHolder.setAuthenticationMethod(authMethod);
      }
      
      IAuthDataHolderFactory adhf = new AuthDataHolderFactory();
      String authDataHolderXMLText = null;
      
      try {
        authDataHolderXMLText = adhf.createAuthDataHolderXMLText(authDataHolder);
  
        if (logger.isDebugEnabled()) {
          logger.debug("doPost() -  : authDataholderXMLText = "
              + authDataHolderXMLText);
        }
      } catch (Exception e1) {
        //e1.printStackTrace();
        logger.error(
           "doPost() - Impossibile creare XML contenente i dati di autenticazione",e1);
  
        SiracHelper.forwardToErrorPageWithRuntimeException(request, response, e1, "Impossibile trasferire i dati di autenticazione.");
      }
      
      // L'xml contenente i dati di autenticazione e gli attributi utente � stato creato ed � disponibile 
      // Encoding in base 64
      String authResponseB64 = new String(Base64.encodeBase64Chunked(authDataHolderXMLText.getBytes()));
*/      
        authStatus = SiracConstants.AUTHENTICATION_STATUS_SUCCESS;
        String authResponseB64 = null;
        
        try {
          authResponseB64 = SiracHelper.prepareAuthDataResponseBase64(userID, attributesMap,authType, authMethod, target, authStatus);
        } catch (Exception e) {
          //e.printStackTrace();
          SiracHelper.forwardToErrorPageWithRuntimeException(request, response, e, "Impossibile trasferire i dati di autenticazione.");
        }
        
/*    Spostato in SiracHelper.PostOrForwardAuthResponseToTargetService 
      
      if (logger.isDebugEnabled()) {
        logger.debug("doPost() -  : Trasferimento della authentication response a IntersiteTransferService. Modalit� di trasferimento selezionata :  " +
            intersiteTransferServiceTransferMode);
      }
      if ("FORWARD".equals(intersiteTransferServiceTransferMode)) {
        if (logger.isDebugEnabled()) {
          logger.debug("doPost() -  : IntersiteTransferService Forward URL:  " +
              intersiteTransferServiceForwardURL);
        }
        String serviceResource = intersiteTransferServiceForwardURL; // Es. "/IntersiteTransferService";
        request.setAttribute("authResponse", authResponseB64);
  
        RequestDispatcher rd = request.getRequestDispatcher(serviceResource);
        rd.forward(request, response);
        
      } else if ("POST".equals(intersiteTransferServiceTransferMode)) {
        if (logger.isDebugEnabled()) {
          logger.debug("doPost() -  : IntersiteTransferService POST URL:  " +
              intersiteTransferServicePOSTURL);
          logger.debug("doPost() -  : IntersiteTransferService POST Response Page:  " +
              postResponsePage);
        }
        AuthenticationResponseBean authRespBean = new AuthenticationResponseBean();
        authRespBean.setResponse(authResponseB64);
        authRespBean.setFormAction(intersiteTransferServicePOSTURL);
        
        String serviceResource = postResponsePage; // Es. "/PostAuthResponse.jsp";
        request.setAttribute("authResponse", authRespBean);
        RequestDispatcher rd = request.getRequestDispatcher(serviceResource);
        rd.forward(request, response);
      } else {
        SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, 
            "Impossibile trasferire i dati di autenticazione. Modalit� di trasferimento verso IntersiteTransferService non valida: " + 
            intersiteTransferServiceTransferMode);
      }
*/
        try {
          SiracHelper.transferAuthResponseToTargetService(authResponseB64, "Intersite Transfer Service", intersiteTransferServiceTransferMode,
                                                   intersiteTransferServiceForwardURL, intersiteTransferServicePOSTURL, postResponsePage, 
                                                   request, response);
        } catch (Exception e) {
          SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, 
              "Impossibile trasferire i dati di autenticazione. Modalit� di trasferimento verso IntersiteTransferService non valida: " + 
              intersiteTransferServiceTransferMode);
        }
// -------- END FIX 31-08-2005 --------------------------
      if (logger.isDebugEnabled()) {
        logger.debug("doPost() - end");
      }
    }


    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occure
     */
    public void init(ServletConfig config) throws ServletException {
      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - start");
      }

        this.portAddress_prefix = config.getInitParameter("portAddress_prefix");
        
        this.wsauth_address = config.getInitParameter("wsauth_address");
        try {
          this.authWS = new AuthenticationClientAdapter(wsauth_address);
          if (logger.isDebugEnabled()) {
            logger.debug("init(config) - Authentication Web Service Address configurato corretamente ( " + wsauth_address +")");
          }
        } catch (Exception e) {
          
          throw new ServletException("Impossibile inizializzare indirizzo web service di autenticazione: " + wsauth_address);
        }
        
        this.timeout = Integer.parseInt(config.getInitParameter("timeout"));
        this.idp_domain_suffix = config.getServletContext().getInitParameter("IDP-People.domain.suffix");
        
        this.postResponsePage = config.getInitParameter("postResponsePage");
        
        this.loginPage = config.getInitParameter("loginPage");
        
        this.intersiteTransferServiceForwardURL= 
          config.getInitParameter("intersiteTransferServiceForwardURL"); // Aggiunto il 31-08-2005
        
        this.intersiteTransferServicePOSTURL = 
          config.getInitParameter("intersiteTransferServicePOSTURL"); // Aggiunto il 31-08-2005
        
        this.intersiteTransferServiceTransferMode = 
          config.getServletContext().getInitParameter("intersiteTransferServiceTransferMode"); // Aggiunto il 31-08-2005

        this.smartCardLoginRedirectURL = config.getInitParameter("smartcardLoginRedirectURL");
        
        /*
        this.keystorePath = config.getInitParameter("keystorePath");
        this.certificateAlias = config.getInitParameter("certificateAlias");
        this.keystorePassword = config.getInitParameter("keystorePassword").toCharArray();
        
        

        try {
          ks = KeyStore.getInstance("JKS");
          //ks.load(new FileInputStream(keystorePath), keystorePassword);
          ServletContext ctx = config.getServletContext();
          String keystoreRealPath = ctx.getRealPath(keystorePath);
          ks.load(new FileInputStream(keystoreRealPath), keystorePassword);
        } catch (KeyStoreException e) {
          logger.error("init(config) - exception", e);
          //e.printStackTrace();
          throw new ServletException(e);
        } catch (NoSuchAlgorithmException e) {
          logger.error("init(config) - exception: " + e.getMessage());
          //e.printStackTrace();
          throw new ServletException(e);
        } catch (CertificateException e) {
          logger.error("init(config) - exception: " + e.getMessage());
          //e.printStackTrace();
          throw new ServletException(e);
        } catch (FileNotFoundException e) {
          logger.error("init(config) - exception: " + e.getMessage());
          //e.printStackTrace();
          throw new ServletException(e);
        } catch (IOException e) {
          logger.error("init(config) - exception: " + e.getMessage());
          //e.printStackTrace();
          throw new ServletException(e);
        }

      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - end");
      }
      */
    }

//--------------------------------------------------------------------------
    
    public boolean externalAuthenticate(String userID, String credential, String authType) {
      
      if (logger.isDebugEnabled()) {
        logger.debug("externalAuthenticate() - externalAuthenticate(): user= " + userID);
      } 
      
      // Authenticate the user by invoking the Authentication Web service
      boolean basicAuthResult = false; 
      ResAuthBean resAuthBean = null;
      
      try {
        if (SiracConstants.SIRAC_AUTH_TYPE_WEAK.equals(authType)) {
          resAuthBean = authWS.executeBasicAuthentication(userID, credential);
        } else if (SiracConstants.SIRAC_AUTH_TYPE_STRONG.equals(authType)) {
          resAuthBean = authWS.executePINAuthentication(userID, credential);
        } else {
          logger.error("externalAuthenticate() - Tipo di autenticazione non supportato: " + authType);
          return false;
          
        }

      } catch (RemoteException e) {
        logger.error("An Exception has been generated", e);
        return false;
      }

      String resEsito = resAuthBean.getEsito();
      String resErrorCode = resAuthBean.getErrorCode();
      String resUserID = resAuthBean.getUserId();
      
      String msgAuthType = (SiracConstants.SIRAC_AUTH_TYPE_WEAK.equals(authType)) ? "password" : "pin"; 
      String msgAuthOK = "Utente " + userID + " autenticato correttamente con "  + msgAuthType + ".";
      if ("OK".equals(resEsito) && userID.equalsIgnoreCase(resUserID)) {
        //System.out.println();

        if (logger.isDebugEnabled()) {
          logger.debug("externalAuthenticate() -  : " + msgAuthOK);
        }

        basicAuthResult = true;
      } else {  // Autenticazione fallita
        System.out.println("Autenticazione utente " + userID + " con password fallita. ErrorCode: " + resErrorCode);
        basicAuthResult = false;
      }  

      return basicAuthResult; 
    }

    public PplUserDataExtended authentication_getUserData(String userID, String password) throws Exception {

//      it.people.sirac.authentication.webservice.MakeAuthenticationSoapBindingStub binding;
//      try {
//        java.net.URL portAddress = new java.net.URL(portAddress_prefix+"/Authentication");
//          binding = (it.people.sirac.authentication.webservice.MakeAuthenticationSoapBindingStub)
//                        new it.people.sirac.authentication.webservice.MakeAuthenticationServiceLocator().getAuthentication(portAddress);
//      }
//      catch (javax.xml.rpc.ServiceException jre) {
//          if(jre.getLinkedCause()!=null)
//              logger.error("authentication_getUserData(userID = " + userID
//                  + ", password = " + password + ") - exception", jre
//                  .getLinkedCause());
//          throw new Exception("JAX-RPC ServiceException caught: " + jre);
//      }
//
//      // Time out after a minute
//      binding.setTimeout(timeout);
//
//      // invoke operation
//      it.people.sirac.beans.xml_soap_regBean.webservice.RegBean rb = null;
      
      if (logger.isDebugEnabled()) {
        logger
            .debug("authentication_getUserData() - -------------------------------");
        logger
            .debug("authentication_getUserData() - Richiesta profilo per utente: "
                + userID);
      }
 //   PplUserData userData = null;
//      try {
//        rb = binding.getUserData(userID, password);
//        
//        userData = fillUserData(rb);
//        if (logger.isDebugEnabled()) {
//          logger.debug("authentication_getUserData()" + userData.toString());
//        }
//        
//      } catch (java.rmi.RemoteException re) {
//        if (logger.isDebugEnabled()) {
//          logger.debug("authentication_getUserData()" + re.getMessage());
//        }
//      }
      PplUserDataExtended userData = null;
      RegBean rb = null;
    try {
      rb = authWS.getUserDataWithCodiceFiscale(userID);
      userData = fillUserData(rb);
    } catch (Exception e) {
      throw new Exception("Errore durante l'estrazione del profilo dell'utente: " + e.getMessage(), e);
    }
      
      return userData;
  }

//  public PplUserData fillUserData(it.people.sirac.beans.xml_soap_regBean.webservice.RegBean rb) {
    public PplUserDataExtended fillUserData(RegBean rb) {
      PplUserDataExtended userData = new PplUserDataExtended();
      
      userData.setNome(rb.getNome());
      userData.setCognome(rb.getCognome());
      userData.setDataNascita(rb.getDataNascita());
      userData.setCodiceFiscale(rb.getCodiceFiscale());
//      FIX 2007-05-24: in PplUserData deve finire il domicilio elettronico e non l'email personale
      userData.setEmailaddress(rb.getDomicilioElettronico());
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
      userData.setCellulare(rb.getCellulare());
      userData.setTitolo(rb.getTitolo());
      userData.setIdComuneRegistrazione(rb.getIdComune());
      userData.setRuolo(rb.getRuolo());
      userData.setTerritorio(rb.getTerritorio());

      return userData;
    }
    
//    FINE CODICE DI ESEMPIO PER INTERFACCIAMENTO CON VERSIONE PRECEDENTE SIRAC    
//  --------------------------------------------------------------------------

    public Map createAttributesMapfromPplUserData(PplUserData userData) {
      HashMap attrMap = new HashMap();
      attrMap.put("nome", getStringAttributeNotNull(userData.getNome()));
      attrMap.put("cognome", getStringAttributeNotNull(userData.getCognome()));
      attrMap.put("dataNascita", getStringAttributeNotNull(userData.getDataNascita()));
      attrMap.put("codiceFiscale", getStringAttributeNotNull(userData.getCodiceFiscale()));
      attrMap.put("emailAddress", getStringAttributeNotNull(userData.getEmailaddress()));
      attrMap.put("capDomicilio", getStringAttributeNotNull(userData.getCapDomicilio()));
      attrMap.put("capResidenza", getStringAttributeNotNull(userData.getCapResidenza()));
      attrMap.put("cittaDomicilio", getStringAttributeNotNull(userData.getCittaDomicilio()));
      attrMap.put("cittaResidenza", getStringAttributeNotNull(userData.getCittaResidenza()));
      attrMap.put("indirizzoDomicilio", getStringAttributeNotNull(userData.getIndirizzoDomicilio()));
      attrMap.put("indirizzoResidenza", getStringAttributeNotNull(userData.getIndirizzoResidenza()));
      attrMap.put("lavoro", getStringAttributeNotNull(userData.getLavoro()));
      attrMap.put("luogoNascita", getStringAttributeNotNull(userData.getLuogoNascita()));
      attrMap.put("provinciaDomicilio", getStringAttributeNotNull(userData.getProvinciaDomicilio()));
      attrMap.put("provinciaNascita", getStringAttributeNotNull(userData.getProvinciaNascita()));
      attrMap.put("provinciaResidenza", getStringAttributeNotNull(userData.getProvinciaResidenza()));
      attrMap.put("sesso", getStringAttributeNotNull(userData.getSesso()));
      attrMap.put("statoDomicilio", getStringAttributeNotNull(userData.getStatoDomicilio()));
      attrMap.put("statoResidenza", getStringAttributeNotNull(userData.getStatoResidenza()));
      attrMap.put("telefono", getStringAttributeNotNull(userData.getTelefono()));
      attrMap.put("titolo", getStringAttributeNotNull(userData.getTitolo()));
      attrMap.put("ruolo", getStringAttributeNotNull(userData.getRuolo()));
      attrMap.put("territorio", getStringAttributeNotNull(userData.getTerritorio()));
      
      return attrMap;
    }
    
    public Map createAttributesMapfromPplUserDataExtended(PplUserDataExtended userData) {
      HashMap attrMap = new HashMap();
      attrMap.put("nome", getStringAttributeNotNull(userData.getNome()));
      attrMap.put("cognome", getStringAttributeNotNull(userData.getCognome()));
      attrMap.put("dataNascita", getStringAttributeNotNull(userData.getDataNascita()));
      attrMap.put("codiceFiscale", getStringAttributeNotNull(userData.getCodiceFiscale()));
      attrMap.put("emailAddress", getStringAttributeNotNull(userData.getEmailaddress()));
      attrMap.put("capDomicilio", getStringAttributeNotNull(userData.getCapDomicilio()));
      attrMap.put("capResidenza", getStringAttributeNotNull(userData.getCapResidenza()));
      attrMap.put("cittaDomicilio", getStringAttributeNotNull(userData.getCittaDomicilio()));
      attrMap.put("cittaResidenza", getStringAttributeNotNull(userData.getCittaResidenza()));
      attrMap.put("indirizzoDomicilio", getStringAttributeNotNull(userData.getIndirizzoDomicilio()));
      attrMap.put("indirizzoResidenza", getStringAttributeNotNull(userData.getIndirizzoResidenza()));
      attrMap.put("lavoro", getStringAttributeNotNull(userData.getLavoro()));
      attrMap.put("luogoNascita", getStringAttributeNotNull(userData.getLuogoNascita()));
      attrMap.put("provinciaDomicilio", getStringAttributeNotNull(userData.getProvinciaDomicilio()));
      attrMap.put("provinciaNascita", getStringAttributeNotNull(userData.getProvinciaNascita()));
      attrMap.put("provinciaResidenza", getStringAttributeNotNull(userData.getProvinciaResidenza()));
      attrMap.put("sesso", getStringAttributeNotNull(userData.getSesso()));
      attrMap.put("statoDomicilio", getStringAttributeNotNull(userData.getStatoDomicilio()));
      attrMap.put("statoResidenza", getStringAttributeNotNull(userData.getStatoResidenza()));
      attrMap.put("telefono", getStringAttributeNotNull(userData.getTelefono()));
      attrMap.put("titolo", getStringAttributeNotNull(userData.getTitolo()));
      attrMap.put("idComuneRegistrazione", getStringAttributeNotNull(userData.getIdComuneRegistrazione()));
      attrMap.put("ruolo", getStringAttributeNotNull(userData.getRuolo()));
      attrMap.put("territorio", getStringAttributeNotNull(userData.getTerritorio()));
      
      return attrMap;
    }
    
    private String getStringAttributeNotNull(String attribute) {
    	if (attribute==null) return "";
    	return attribute;
    }


    public void setPortAddressPrefix(String portAddress_prefix) {
    	this.portAddress_prefix = portAddress_prefix;
    }

    public String getPortAddressPrefix() {
    	return portAddress_prefix;
    }
  
}

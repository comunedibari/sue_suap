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
 * Created on 18-gen-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.sirac.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.util.Utilities;

public class AuthGatewayServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AuthGatewayServlet.class);
    
    String weakLoginRedirect= null;
    String strongLoginRedirect = null;

  /**
   * Constructor of the object.
   */
  public AuthGatewayServlet() {
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

    doPost(request, response);
    
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
    
    //String ca_domain_suffix = getServletContext().getInitParameter("CA-People.domain.suffix");
    
//    String idCA = SiracRegistry.getCAPeople().getId();
//    String caURL = SiracRegistry.getCAPeople().getUrl();

    
//    String requestURL = request.getRequestURL().toString(); // Es. "http://localhost/people/initProcess.do"
//    
//    String queryString = request.getQueryString();
     
   // Legge i parametri passati nel caso la form di autenticazione 
   // venga presentata dal SIRAC Gateway. In questo caso la CA-People deve fare solo l'autenticazione  
   
//    String userID = (String)request.getParameter("uid");
//   String userpwd = (String)request.getParameter("upwd");
   // Legge il parametro con la pagina target su dovr� essere fatto 
   // successivamente il post dei dati di autenticazione
   String serviceTargetRedirect = (String)request.getParameter("TARGET");
   String authType = (String)request.getParameter("AuthRequest");
   
   boolean isWeakAuthRequest = (SiracConstants.SIRAC_AUTH_TYPE_WEAK.equals(authType));
   boolean isStrongAuthRequest = (SiracConstants.SIRAC_AUTH_TYPE_STRONG.equals(authType));
   
//   String requestPath = (String)request.getContextPath();
//   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+requestPath+"/";
   
   String redirectPage = null;
   
   if (isWeakAuthRequest) {
       redirectPage = this.weakLoginRedirect;
   } else if (isStrongAuthRequest) {
       redirectPage = this.strongLoginRedirect;
   } else {
	   SiracHelper.forwardToErrorPageWithRuntimeException(request, response, null, "Impossibile determinare l'indirizzo dell'Identity Provider.");
	   return;
   }
   
   String serviceTargetQueryString = serviceTargetRedirect;
   
   String encodedServiceTargetQueryString = null;
   String decodedServiceTargetQueryString = null;
  try {
    encodedServiceTargetQueryString = new URLCodec().encode(serviceTargetQueryString);
    decodedServiceTargetQueryString = new URLCodec().decode(serviceTargetQueryString);
  } catch (EncoderException e) {
    e.printStackTrace();
  } catch (DecoderException e) {
    e.printStackTrace();
  }
    if (logger.isDebugEnabled()) {
      logger.debug("doPost() -  : serviceTargetQueryString = "
          + serviceTargetQueryString + ", encodedServiceTargetQueryString = "
          + encodedServiceTargetQueryString);
      logger.debug("decodedServiceTargetQueryString = " + decodedServiceTargetQueryString);
      logger.debug("redirectPage = " + redirectPage);
    }

   HashMap queryRedirectParameters = new HashMap();
   
   //queryRedirectParameters.put("authType", authType);
   
   //queryRedirectParameters.put("TARGET", requestURL + "?" + "ep="+encodedServiceTargetQueryString); 
   //queryRedirectParameters.put("TARGET", requestURL + "?" + encodedServiceTargetQueryString); 
   queryRedirectParameters.put("TARGET", encodedServiceTargetQueryString);
   
   //RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
   //rd.forward(request, response);
   
   if (redirectPage.indexOf("?") !=-1) {  // Fix 30/05/2005
     redirectPage = redirectPage + "&" + Utilities.createQueryString(queryRedirectParameters);
   } else {
     redirectPage = redirectPage + "?" + Utilities.createQueryString(queryRedirectParameters);
     
   }
   if (logger.isDebugEnabled()) {
     logger.debug("doPost() -  : Redirecting to " + redirectPage);
   }
   response.sendRedirect(redirectPage);

  }

  /**
   * Initialization of the servlet. <br>
   *
   * @throws ServletException if an error occure
   */
  public void init(ServletConfig config) throws ServletException {
      this.weakLoginRedirect = config.getInitParameter("weakLoginRedirect");
      this.strongLoginRedirect = config.getInitParameter("strongLoginRedirect");
  }
//-----------------------------------------------------------------------
//  private String createQueryString(Map parameters) {
//      if (parameters == null) return "";
//      StringBuffer sb = new StringBuffer();
//      int npar = 0;
//      Iterator it = parameters.keySet().iterator();
//      while (it.hasNext()) {
//         String parameterName = (String)it.next();
//         String parameterValue = (String)parameters.get(parameterName);
//         sb.append( (npar++==0) ? "" : "&");
//         sb.append(parameterName + "=" + parameterValue);
//      }
//      return sb.toString();
//  }
}

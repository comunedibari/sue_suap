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
package it.idppeople.web;

import it.people.sirac.core.SiracHelper;
import it.people.sirac.core.UserProfileConstants;
import it.people.sirac.idp.beans.ResRegBean;
import it.people.sirac.idp.registration.RegistrationClientAdapter;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ChangePasswordServlet extends HttpServlet {

	public static final String PASSWORD_MISMATCH = "Le password inserite non coincidono.";
	public static final String PASSWORD_TOO_SHORT = "La password deve essere di almeno 8 caratteri.";
	public static final String OLD_PASSWORD_INCORRECT = "La vecchia password non è corretta.";
	public static final String SAME_PASSWORD = "La nuova password non può coincidere con la vecchia.";
	
	
	String changePasswordPage = null;
	String changePasswordConfirmationPage = null;
	String wsreg_address = null;
	RegistrationClientAdapter regWS = null;
		
	private static Logger logger = LoggerFactory.getLogger(ChangePasswordServlet.class);
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
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
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String codiceFiscale = request.getParameter(UserProfileConstants.CODICE_FISCALE);
		String target = request.getParameter("TARGET");
		boolean hasPasswordData = Boolean.valueOf((String)request.getParameter("hasPasswordData")).booleanValue();
		String path = request.getContextPath();
		String urlPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/ChangePasswordService"; 
		
		if(!hasPasswordData){
			// se � la prima volta che la servlet viene invocata, rimanda alla pagina di cambio pwd
			request.setAttribute("nextStep", urlPath);
			request.setAttribute("target", target);
			request.setAttribute(UserProfileConstants.CODICE_FISCALE, codiceFiscale);
			RequestDispatcher rd = request.getRequestDispatcher(changePasswordPage);
	    rd.forward(request, response);
	    return;
		} else {
			// altrimenti leggi i dati immessi nel form di cambio pwd
			String oldPwd = request.getParameter("oldPwd");
			String newPwd = request.getParameter("newPwd");
			String confirmPwd = request.getParameter("confirmPwd");
			
			// controlla che le due nuove password inserite coincidano
			if(!newPwd.equals(confirmPwd)){
				request.setAttribute(UserProfileConstants.CODICE_FISCALE, codiceFiscale);
				request.setAttribute("message", PASSWORD_MISMATCH);
				request.setAttribute("nextStep", urlPath);
				request.setAttribute("target", target);
				RequestDispatcher rd = request.getRequestDispatcher(changePasswordPage);
		    rd.forward(request, response);
		    return;
			}
			if(newPwd.equals(oldPwd)){
				request.setAttribute(UserProfileConstants.CODICE_FISCALE, codiceFiscale);
				request.setAttribute("message", SAME_PASSWORD);
				request.setAttribute("nextStep", urlPath);
				request.setAttribute("target", target);
				RequestDispatcher rd = request.getRequestDispatcher(changePasswordPage);
		    rd.forward(request, response);
		    return;
			}
			// controlla che la password inserita sia di almeno 8 caratteri 
			if(newPwd.length() < 8){
				request.setAttribute(UserProfileConstants.CODICE_FISCALE, codiceFiscale);
				request.setAttribute("message", PASSWORD_TOO_SHORT);
				request.setAttribute("nextStep", urlPath);
				request.setAttribute("target", target);
				RequestDispatcher rd = request.getRequestDispatcher(changePasswordPage);
		    rd.forward(request, response);
		    return;
			}
			// invoca il WS di registrazione che effettua il controllo di correttezza
			// della vecchia password prima di cambiarla.
			try{
				ResRegBean res = regWS.changePassword(codiceFiscale, oldPwd, newPwd);
				if("FAILED".equalsIgnoreCase(res.getEsito())){
					request.setAttribute(UserProfileConstants.CODICE_FISCALE, codiceFiscale);
					request.setAttribute("message", res.getMessaggio());
					request.setAttribute("nextStep", urlPath);
					request.setAttribute("target", target);
					RequestDispatcher rd = request.getRequestDispatcher(changePasswordPage);
			    rd.forward(request, response);
			    return;
				} else {
					request.setAttribute("target", target);
					RequestDispatcher rd = request.getRequestDispatcher(changePasswordConfirmationPage);
			    rd.forward(request, response);
			    return;
				}
			} catch(RemoteException ex){
				logger.error("doPost() - Si è verificato un errore nella comunicazione con il servizio di registrazione.");
				SiracHelper.forwardToErrorPageWithRuntimeException(request,
						response, ex,
						"Si è verificato un errore nella comunicazione con il servizio di registrazione.");
			}
			
		}
		
	}

	public void init(ServletConfig config) throws ServletException {
    if (logger.isDebugEnabled()) {
      logger.debug("init(config) - start");
    }
    
    this.changePasswordPage = config.getInitParameter("changePasswordPage");
    this.changePasswordConfirmationPage = config.getInitParameter("changePasswordConfirmationPage");
	
    this.wsreg_address = config.getServletContext().getInitParameter("wsreg_address");
    
    try {
      this.regWS = new RegistrationClientAdapter(wsreg_address);
      if (logger.isDebugEnabled()) {
        logger.debug("init(config) - Registration Web Service Address configurato corretamente ( " + wsreg_address +")");
      }
    } catch (Exception e) {
      throw new ServletException("Impossibile inizializzare indirizzo web service di registrazione: " + wsreg_address);
    }
    
	}
	
	 
}

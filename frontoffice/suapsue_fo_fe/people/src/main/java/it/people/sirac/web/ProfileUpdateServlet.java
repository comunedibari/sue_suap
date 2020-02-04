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
package it.people.sirac.web;

import it.people.fsl.servizi.admin.sirac.accreditamento.steps.utility.UtilHelper;
import it.people.sirac.accr.beans.ProfiloPersonaFisica;
import it.people.sirac.accr.beans.ProfiloPersonaGiuridica;
import it.people.sirac.authentication.beans.PplUserDataExtended;
import it.people.sirac.authentication.beans.ProfiloRichiedenteTitolareBean;
import it.people.sirac.core.SiracConstants;
import it.people.sirac.core.SiracHelper;
import it.people.sirac.filters.AuthFilterRequestWrapper;
import it.people.sirac.util.Utilities;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileUpdateServlet extends HttpServlet {

	private static Logger logger = LoggerFactory.getLogger(ProfileUpdateServlet.class);

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    doPost(request, response);
    return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("doPost() - start");
    }

    HttpSession session = request.getSession();
    // se la pagina non � ancora stata visualizzata per questo accesso al servizio, imposto un attributo
    // in sessione per segnalare che a questo punto la pagina � stata visualizzata e consentire al filtro
    // di modifica del profilo di stabilire che non dovr� visualizzarla nuovamente
    boolean updatePageAlreadyShown = session.getAttribute(UtilHelper.SIRAC_UPDATE_PAGE_ALREADYSHOWN)!=null;
    if(!updatePageAlreadyShown) {
    	session.setAttribute(UtilHelper.SIRAC_UPDATE_PAGE_ALREADYSHOWN, "true");
    } 
	
    String resource = request.getParameter(SiracConstants.SIRAC_TARGET_SERVICE_ATTRIBUTE_NAME);
    try{
    	// ricostruisce il bean con i dati dei profili leggendo le informazioni provenienti dalla pagina
    	// di aggiornamento del profilo
    	ProfiloRichiedenteTitolareBean profiliBean = getProfiliFromRequest(request);
    	
    	// si procede con l'aggiornamento degli attributi di sesione relativi ai profili, cos�
    	// come impostato dall'utente nella pagina di modifica, inviata a questa servlet
    	updateUserSessionAttribute(session, profiliBean);
    } catch(Exception ex){
    	String errorMsg = "Impossibile aggiornare il contesto di autenticazione. Causa: " + ex.getMessage();
    	logger.error("doPost() - " + errorMsg);
    	SiracHelper.forwardToErrorPageWithRuntimeException(request, response, ex, errorMsg);
    	return;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("doPost() - Forwarding to "+ resource);
      logger.debug("doPost() - end");
    }
    // viene recuperata la richiesta originaria fatta dall'utente e si inoltra al destinatario originale
    request = new AuthFilterRequestWrapper((HttpServletRequest)request, session.getServletContext());
    RequestDispatcher rd = request.getRequestDispatcher(resource);
    rd.forward(request, response);
  }

//----------------------------------------------------------------------------

  protected void updateUserSessionAttribute(HttpSession session, ProfiloRichiedenteTitolareBean profiliBean) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("updateUserSessionAttribute() - start");
      logger.debug("updateUserSessionAttribute() - Updating session attributes related to the authenticated user...");
    }

	session.setAttribute(SiracConstants.SIRAC_ACCR_RICHIEDENTE, profiliBean.getProfiloRichiedente());

	if(profiliBean.getProfiloTitolare().isPersonaFisica()){
		session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, (ProfiloPersonaFisica)profiliBean.getProfiloTitolare());
	} else {
		session.setAttribute(SiracConstants.SIRAC_ACCR_TITOLARE, (ProfiloPersonaGiuridica)profiliBean.getProfiloTitolare());
	}

	PplUserDataExtended userdata = (PplUserDataExtended)session.getAttribute(SiracConstants.SIRAC_AUTHENTICATED_USERDATA);
	userdata.setEmailaddress(profiliBean.getDomicilioElettronicoAssociazione());
	session.removeAttribute("currentLoggedUser");
    if (logger.isDebugEnabled()) {
      logger.debug("updateUserSessionAttribute() - end");
    }
  }

  protected ProfiloRichiedenteTitolareBean getProfiliFromRequest(HttpServletRequest request) throws Exception {
	  ProfiloRichiedenteTitolareBean profiliBean = new ProfiloRichiedenteTitolareBean();
	  ProfiloPersonaFisica richiedente = new ProfiloPersonaFisica();
	  richiedente.setCodiceFiscale(request.getParameter("profiloRichiedenteCodiceFiscale"));
	  richiedente.setNome(request.getParameter("profiloRichiedenteNome"));
	  richiedente.setCognome(request.getParameter("profiloRichiedenteCognome"));
	  richiedente.setDataNascitaString(request.getParameter("profiloRichiedenteDataNascitaString"));
	  richiedente.setLuogoNascita(request.getParameter("profiloRichiedenteLuogoNascita"));
	  richiedente.setProvinciaNascita(request.getParameter("profiloRichiedenteProvinciaNascita"));
	  richiedente.setSesso(request.getParameter("profiloRichiedenteSesso"));
	  richiedente.setIndirizzoResidenza(request.getParameter("profiloRichiedenteIndirizzoResidenza"));
	  richiedente.setDomicilioElettronico(request.getParameter("profiloRichiedenteDomicilioElettronico"));
	  
	  profiliBean.setProfiloRichiedente(richiedente);
	  
	  if(request.getParameter("profiloTitolareCodiceFiscale") != null && !"".equals(request.getParameter("profiloTitolareCodiceFiscale"))) {
		  if(request.getParameter("profiloTitolarePartitaIva")==null) {
			  ProfiloPersonaFisica titolarePF = new ProfiloPersonaFisica();
			  titolarePF.setCodiceFiscale(request.getParameter("profiloTitolareCodiceFiscale"));
			  titolarePF.setNome(request.getParameter("profiloTitolareNome"));
			  titolarePF.setCognome(request.getParameter("profiloTitolareCognome"));
			  titolarePF.setDataNascitaString(request.getParameter("profiloTitolareDataNascitaString"));
			  titolarePF.setLuogoNascita(request.getParameter("profiloTitolareLuogoNascita"));
			  titolarePF.setProvinciaNascita(request.getParameter("profiloTitolareProvinciaNascita"));
			  titolarePF.setSesso(request.getParameter("profiloTitolareSesso"));
			  titolarePF.setIndirizzoResidenza(request.getParameter("profiloTitolareIndirizzoResidenza"));
			  titolarePF.setDomicilioElettronico(request.getParameter("profiloTitolareDomicilioElettronico"));

			  profiliBean.setProfiloTitolare(titolarePF);
		  } else {
			  ProfiloPersonaGiuridica titolarePG = new ProfiloPersonaGiuridica();
			  titolarePG.setCodiceFiscale(request.getParameter("profiloTitolareCodiceFiscale"));
			  titolarePG.setPartitaIva(request.getParameter("profiloTitolarePartitaIva"));
			  titolarePG.setDenominazione(request.getParameter("profiloTitolareDenominazione"));
			  titolarePG.setSedeLegale(request.getParameter("profiloTitolareSedeLegale"));
			  titolarePG.setDomicilioElettronico(request.getParameter("profiloTitolareDomicilioElettronico"));
			  ProfiloPersonaFisica ppfRL = new ProfiloPersonaFisica();
			  ppfRL.setNome(request.getParameter("profiloTitolareRappresentanteLegaleNome"));
			  ppfRL.setCognome(request.getParameter("profiloTitolareRappresentanteLegaleCognome"));
			  ppfRL.setCodiceFiscale(request.getParameter("profiloTitolareRappresentanteLegaleCodiceFiscale"));
			  ppfRL.setDomicilioElettronico(request.getParameter("profiloTitolareRappresentanteLegaleDomicilioElettronico"));
			  ppfRL.setDataNascita(Utilities.parseDateString("01/01/1970"));
			  ppfRL.setDataNascitaString("01/01/1970");
			  ppfRL.setSesso("M");
			  titolarePG.setRappresentanteLegale(ppfRL);
			  profiliBean.setProfiloTitolare(titolarePG);
		  }
	  } else {
		  profiliBean.setProfiloTitolare(richiedente);
	  }
	  profiliBean.setDomicilioElettronicoAssociazione(request.getParameter("domicilioElettronico"));
	  return profiliBean;
  }
}


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
 * Created on 12-mag-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.filters;

import it.people.City;
import it.people.PeopleConstants;
import it.people.core.CommuneManager;
import it.people.core.persistence.exception.peopleException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author Michele Fabbri - Cedaf s.r.l.
 * 
 *         Il filtro ha la funzione di verificare che sia stato selezionato un
 *         comune e nel caso non lo sia ridirezionare alla pagina di selezione.
 */
public class CommuneFilter implements Filter {
    public static final String INVOKED_URL_ATTRIBUTE_NAME = "invokedUrl";
    public static final String SELECTING_COMMUNE_ATTRIBUTE_NAME = "selectingCommune";
    public static final String COMMUNE_CODES_LIST_FILTER_ATTRIBUTE_NAME = "communeCodesListFilter";

    private static final Logger logger = Logger.getLogger(CommuneFilter.class);

    private FilterConfig filterConfig = null;
    protected String peopleFSLHostURL = null;
    protected String selectCommunePageURL = null;
    protected HashSet excludedMapping = new HashSet();
    protected HashSet excludedExtensionMapping = new HashSet();

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
	this.filterConfig = filterConfig;
	this.peopleFSLHostURL = filterConfig.getServletContext()
		.getInitParameter(
			PeopleConstants.CONTEXT_PARAMETER_NAME_HOSTURL);
	this.selectCommunePageURL = filterConfig
		.getInitParameter("selectCommunePageURL");

	// Elenco delle estensioni su cui non filtrare
	if (filterConfig.getInitParameter("excludeExtensionMapping") != null) {
	    StringTokenizer tokenizer = new StringTokenizer(
		    filterConfig.getInitParameter("excludeExtensionMapping"));
	    while (tokenizer.hasMoreTokens()) {
		excludedExtensionMapping.add(tokenizer.nextToken());
	    }
	}

	// Elenco degli url su cui non filtrare
	try {
	    Enumeration paramNames = filterConfig.getInitParameterNames();
	    while (paramNames.hasMoreElements()) {
		String paramName = (String) paramNames.nextElement();
		// Sono considerati validi tutti i parametri che iniziano
		// per 'excludeMapping'
		if (paramName.startsWith("excludeMapping")) {
		    String excludedUrl = filterConfig
			    .getInitParameter(paramName);
		    if (excludedUrl != null && !"".equals(excludedUrl))
			this.excludedMapping.add(excludedUrl);
		}
	    }
	} catch (Exception ex) {
	    logger.error(
		    "init() - errore nella lettura degli url da escludere", ex);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest servletRequest,
	    ServletResponse servletResponse, FilterChain filterChain)
	    throws IOException, ServletException {

	if (logger.isDebugEnabled())
	    logger.debug("doFilter() - start");

	HttpServletRequest request = (HttpServletRequest) servletRequest;
	HttpServletResponse response = (HttpServletResponse) servletResponse;
	HttpSession session = ((HttpServletRequest) request).getSession();

	// Imposta la lingua per l'utente
	// La lingua dell'utente � impostata direttamente dal framework Struts
	// nell'attributo di sessione "org.apache.struts.action.LOCALE"
	// session.setAttribute(PeopleConstants.USER_LOCALE_KEY,
	// request.getLocale());

	if (isUrlExcluded(request)) {
	    if (logger.isDebugEnabled())
		logger.debug("L'url '" + request.getRequestURI()
			+ "' � escluso dal mapping");
	    filterChain.doFilter(request, response);
	    return;
	}

	boolean selectingCommune = request
		.getParameter(SELECTING_COMMUNE_ATTRIBUTE_NAME) != null;
	if (selectingCommune) {
	    String communeCode = request.getParameter("communeCode");

	    // comune selezionato
	    if (logger.isDebugEnabled()) {
		logger.debug("doFilter() - comune in selezione");
	    }

	    // Salva in sessione il comune selezionato
	    try {
		City city = CommuneManager.getInstance().get(communeCode);
		setSession(session, city);
	    } catch (peopleException ex) {
		String errorMessage = "Impossibile caricare le informazioni dell'ente";
		logger.error("doFilter() - " + errorMessage, ex);
		throw new ServletException(errorMessage, ex);
	    }

	    filterChain.doFilter(request, response);
	} else if (session.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE) == null
		&& !selectingCommune) {
	    String destinationUrl = this.selectCommunePageURL;

	    // non e' ancora stato selezionato un comune
	    if (logger.isDebugEnabled())
		logger.debug("doFilter() - nessun comune selezionato, redirezione a '"
			+ destinationUrl + "'");

	    // Carica i comuni
	    Collection cities = loadCommuni();

	    if (cities.size() == 1) {
		// � presente un solo comune, in questo caso �
		// automaticamente
		// selezionato l'unico comune presente.
		City city = (City) cities.iterator().next();
		setSession(session, city);
		filterChain.doFilter(request, response);
	    } else {
		request.setAttribute("communi", cities);

		// ridireziona alla pagina di selezione del comune
		String invokedUrl = getCompleteRequestURL(request);
		request.setAttribute(CommuneFilter.INVOKED_URL_ATTRIBUTE_NAME,
			invokedUrl);
		forward(destinationUrl, request, response);
	    }

	} else {
	    // il comune e' gia' stato selezionato
	    if (logger.isDebugEnabled()) {
		City city = (City) session
			.getAttribute(PeopleConstants.SESSION_NAME_COMMUNE);
		logger.debug("doFilter() - chiave comune selezionato "
			+ city.getKey());
	    }

	    filterChain.doFilter(request, response);
	}
    }

    /**
     * Salva in sessione il comune selezionato
     * 
     * @param session
     * @param city
     */
    protected void setSession(HttpSession session, City city) {
	// Utilizzata dal frameowork people
	session.setAttribute(PeopleConstants.SESSION_NAME_COMMUNE, city);
	// Utilizzata dai filtri del sirac
	session.setAttribute(PeopleConstants.SESSION_NAME_COMMUNE_ID,
		city.getKey());
    }

    protected boolean isUrlExcluded(HttpServletRequest request) {

	int ultimaOccorrenzaDelPunto = request.getServletPath()
		.lastIndexOf(".");
	if (ultimaOccorrenzaDelPunto != -1) {
	    String extension = request.getServletPath().substring(
		    ultimaOccorrenzaDelPunto + 1);
	    if (this.excludedExtensionMapping.contains(extension))
		return true;
	}

	// Esclude gli url configurati per il filtro
	boolean urlEscluso = this.excludedMapping.contains(request
		.getServletPath());
	if (logger.isDebugEnabled()) {
	    logger.debug("isUrlExcluded() - url richiesto = '"
		    + request.getServletPath() + "' - "
		    + (urlEscluso ? " Escluso" : " Non escluso"));
	}
	return urlEscluso;
    }

    /**
     * Carica tutti i comuni
     * 
     * @return Collection di comuni (istanze di City)
     * @throws ServletException
     */
    protected Collection loadCommuni() throws ServletException {
	try {
	    return CommuneManager.getInstance().getAll();
	} catch (peopleException pex) {
	    String errorMessage = "Errore nel caricamento dei comuni";
	    logger.error("loadCommuni() - " + errorMessage, pex);
	    throw new ServletException(errorMessage, pex);
	}
    }

    /**
     * Carica solo i comuni specificati nel filtro
     * 
     * @param communeListFilter
     *            Collection di String contenenti i codici comune
     * @return Collection di comuni (istanze di City)
     * @throws ServletException
     */
    protected Collection loadCommuni(Collection communeListFilter)
	    throws ServletException {
	try {
	    CommuneManager communeManager = CommuneManager.getInstance();
	    ArrayList communes = new ArrayList();
	    for (Iterator iter = communeListFilter.iterator(); iter.hasNext();) {
		String communeCode = (String) iter.next();
		City commune = communeManager.get(communeCode);
		if (commune != null)
		    communes.add(commune);
	    }
	    return communes;
	} catch (peopleException pex) {
	    String errorMessage = "Errore nel caricamento dei comuni";
	    logger.error("loadCommuni() - " + errorMessage, pex);
	    throw new ServletException(errorMessage, pex);
	}
    }

    protected void forward(String destinationUrl, HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	RequestDispatcher requestDispatcher = filterConfig.getServletContext()
		.getRequestDispatcher(destinationUrl);
	requestDispatcher.forward(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    protected String getCompleteRequestURL(HttpServletRequest request) {
	// PATCH per Proxy, NAT, etc.
	String requestURL = this.peopleFSLHostURL + request.getRequestURI();
	String queryString = request.getQueryString();
	String completeURL = requestURL
		+ ((queryString != null) ? "?" + queryString : "");
	return completeURL;
    }
}
